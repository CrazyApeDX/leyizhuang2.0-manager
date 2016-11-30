<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商品挑选</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js?skin=idialog"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
    //窗口API
    var api = frameElement.api, W = api.opener;
    api.button({
        name: '确定',
        focus: true,
        callback: function () {
            submitForm();
            return false;
        }
    }, {
        name: '取消'
    });
    

    //页面加载完成执行
    $(function () {
        if ($(api.data).length > 0) {
            var parentObj = $(api.data).parent().parent(); //取得节点父对象            
            //开始赋值
            $("#txtItemPricelist_Title").val($(parentObj).find("input[id='title']").val()); 
            $("#txtItemPricelist_Id").val($(parentObj).find("input[id='id']").val()); 
            $("#txtItemPricelist_salePrice").val($(parentObj).find("input[id='salePrice']").val()); 
            $("#txtItemPricelist_realSalePrice").val($(parentObj).find("input[id='realSalePrice']").val()); 
            $("#txtItemPricelist_stockPrice").val($(parentObj).find("input[id='stockPrice']").val()); 
            $("#txtItemPricelist_realStockPrice").val($(parentObj).find("input[id='realStockPrice']").val()); 
            $("#txtItemPricelist_dispatch").val($(parentObj).find("input[id='dispatch']").val()); 
            $("#txtItemPricelist_companyName").val($(parentObj).find("input[id='companyName']").val()); 
            //radio赋值
            var radioChecked = $(parentObj).find("input[id='isCommendIndex']").val();
            var radios = document.getElementsByName("isCommendIndex");
            for (var i=0; i<radios.length;i++)
            {
             	if(radioChecked == radios[i].value)
             	{
             		radios[i].checked = true;
             		break;
             	}
            }
           
        }
    });

    //创建促销赠品窗口
    function show_goods_select_dialog(obj) {
        var objNum = arguments.length;
        var zengpinDialog = $.dialog({
            id: 'zengpinhDialogId',
            lock: true,
            max: false,
            min: false,
            title: "促销赠品",
            content: 'url:/Verwalter/goods/list/dialog/gift',
            width: 800,
            height: 550
        });
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            zengpinDialog.data = obj;
        }
    }
    
    //删除促销赠品节点
    function del_goods_gift(obj) {
        $(obj).parent().parent().remove();
    }
    
    //提交表单处理
    function submitForm() {
        //验证表单
        if ($("#txtItemPricelist_Title").val() == ""
          || $("#txtItemPricelist_Id").val() == ""
          || $("#txtItemPricelist_salePrice").val() == "" 
          || $("#txtItemPricelist_realSalePrice").val() == ""
          || $("#txtItemPricelist_stockPrice").val() == ""
          || $("#txtItemPricelist_realStockPrice").val() == "") {
            W.$.dialog.alert('请填写完整信息！', function () { $("#txtItemPricelist_Title").focus(); }, api);
            return false;
        }
        
           var reg = new RegExp("^[0-9]+(.[0-9]{2})?$");  
	       var obj1 = document.getElementById("txtItemPricelist_salePrice");  
	       var obj2 = document.getElementById("txtItemPricelist_realSalePrice");  
	       var obj3 = document.getElementById("txtItemPricelist_stockPrice");  
	       var obj4 = document.getElementById("txtItemPricelist_realStockPrice");  
	    if(!reg.test(obj1.value) ){  
            W.$.dialog.alert('请输入数字(最多2位小数)！', function () { $("#txtItemPricelist_salePrice").focus(); }, api);
            return false;
        }
	    if(!reg.test(obj2.value) ){  
            W.$.dialog.alert('请输入数字(最多2位小数)！', function () { $("#txtItemPricelist_realSalePrice").focus(); }, api);
            return false;
        }
        if(!reg.test(obj3.value) ){  
            W.$.dialog.alert('请输入数字(最多2位小数)！', function () { $("#txtItemPricelist_stockPrice").focus(); }, api);
            return false;
        }
        if(!reg.test(obj4.value) ){  
            W.$.dialog.alert('请输入数字(最多2位小数)！', function () { $("#txtItemPricelist_realStockPrice").focus(); }, api);
            return false;
        }        
                    
	    //zhangji 根据name获取radio输入的值
		 var radioObjects = document.getElementsByName("isCommendIndex");
		 var checkObject = 0;
		 for(var i=0; i<radioObjects.length; i++ ){
		 	if(radioObjects[i].checked)
		 	{
		 		checkObject = i;
		 		break;
		 	}
		 }
        
        //添加或修改
        if ($(api.data).length > 0) {
        
            var parentObj = $(api.data).parent().parent();

            
        //验证商品id是否重复       zhangji 
        <#if pricelist?? && pricelist.priceItemList??>
        	<#list pricelist.priceItemList as item>
        		<#if item.goodsId??>
			        if ($("#txtItemPricelist_Id").val() == ${item.goodsId?c} && $("#txtItemPricelist_Id").val() != parentObj.find("input[id='id']").val()) {
			            W.$.dialog.alert('该商品已存在！', function () { $("#txtItemPricelist_Id").focus(); }, api);
			            return false;
			        }
			      </#if>
			  </#list>
		 </#if>	
		 
            parentObj.find("input[id='title']").val($("#txtItemPricelist_Title").val());
            parentObj.find("input[id='id']").val($("#txtItemPricelist_Id").val());
            parentObj.find("input[id='salePrice']").val($("#txtItemPricelist_salePrice").val());
            parentObj.find("input[id='realSalePrice']").val($("#txtItemPricelist_realSalePrice").val());
            parentObj.find("input[id='stockPrice']").val($("#txtItemPricelist_stockPrice").val());
            parentObj.find("input[id='realStockPrice']").val($("#txtItemPricelist_realStockPrice").val());
            parentObj.find("input[id='dispatch']").val($("#txtItemPricelist_dispatch").val());
            parentObj.find("input[id='isCommendIndex']").val(radioObjects[checkObject].value);
            parentObj.find("input[id='companyName']").val($("#txtItemPricelist_companyName").val());
        } else {
        
       	//验证商品id是否重复
        <#if pricelist?? && pricelist.priceItemList??>
        	<#list pricelist.priceItemList as item>
        		<#if item.goodsId??>
			        if ($("#txtItemPricelist_Id").val() == ${item.goodsId?c}) {
			            W.$.dialog.alert('该商品已存在！', function () { $("#txtItemPricelist_Id").focus(); }, api);
			            return false;
			        }
			      </#if>
			  </#list>
		 </#if>	
		 
		 
            var trHtml = '<tr class="td_c">'
            + '<td><input name="priceItemList[${total!'0'}].id" type="hidden" value="" />'
            + '<input type="text" name="priceItemList[${total!'0'}].sortId" class="td-input" value="99" style="width:90%;" /></td>'
            + '<td><input type="text" id="id" name="priceItemList[${total!'0'}].goodsId" class="td-input" value="' + $("#txtItemPricelist_Id").val() + '" style="width:90%;" /></td>'
            + '<td><input type="text" id="title" name="priceItemList[${total!'0'}].goodsTitle" class="td-input" value="' + $("#txtItemPricelist_Title").val() + '" style="width:90%;" /></td>'
        //zhangji
            + '<td><input type="text" id="salePrice" name="priceItemList[${total!'0'}].salePrice" class="td-input" value="' + $("#txtItemPricelist_salePrice").val() + '" style="width:90%;" /></td>'
            + '<td><input type="text" id="realSalePrice" name="priceItemList[${total!'0'}].realSalePrice" class="td-input" value="' + $("#txtItemPricelist_realSalePrice").val() + '" style="width:90%;" /></td>'
            + '<td><input type="text" id="stockPrice" name="priceItemList[${total!'0'}].stockPrice" class="td-input" value="' + $("#txtItemPricelist_stockPrice").val() + '" style="width:90%;" /></td>'
            + '<td><input type="text" id="realStockPrice" name="priceItemList[${total!'0'}].realStockPrice" class="td-input" value="' + $("#txtItemPricelist_realStockPrice").val() + '" style="width:90%;" /></td>'
            + '<input type="hidden" id="dispatch" name="priceItemList[${total!'0'}].dispatch" class="td-input" value="' + $("#txtItemPricelist_dispatch").val() + '" style="width:90%;" />'
            + '<input type="hidden" id="priceListName" name="priceItemList[${total!'0'}].priceListName" class="td-input" value="' + '${pricelist.name!''}' + '"  />'
            + '<input type="hidden" id="cityName" name="priceItemList[${total!'0'}].cityName" class="td-input" value="' + '${pricelist.cityName!''}' + '"  />'
            + '<input type="hidden" id="priceListNumber" name="priceItemList[${total!'0'}].priceListNumber" class="td-input" value="' + '${pricelist.priceListNumber!''}' + '"  />'
            + '<input type="hidden" id="priceListId" name="priceItemList[${total!'0'}].priceListId" class="td-input" value="' + '<#if pricelist??>${pricelist.id?c!''}</#if>' + '"  />'
            + '<input type="hidden" id="companyName" name="priceItemList[${total!'0'}].companyName"class="td-input" value="' + $("#txtItemPricelist_companyName").val() + '" style="width:90%;"  />'
            + '<input type="hidden" id="isCommendIndex" name="priceItemList[${total!'0'}].isCommendIndex"class="td-input" value="' + radioObjects[checkObject].value + '" style="width:90%;"  />'
            + '<td>'
            + '<i class="icon"></i>'
            + '<a title="编辑" class="img-btn edit operator" onclick="show_goods_gift_dialog(this);">编辑</a>'
            + '<a title="删除" class="img-btn del operator" onclick="del_goods_gift(this);">删除</a>'
            + '</td>'
            + '</tr>'
            //如果是窗口调用则添加到窗口
            if (!api.get('dialogChannel') || !api.get('dialogChannel')) {
                $("#var_box_gift", W.document).append(trHtml);
                $("#totalGift", W.document).val(parseInt($("#totalGift", W.document).val())+1);
            } else {
                $("#var_box_gift", api.get('dialogChannel').document).append(trHtml);
                $("#totalGift", W.document).val(parseInt($("#totalGift", W.document).val())+1);
            }
        }
        api.close();
    }
    
    $(function () {
        $(".itemzengpin_select").click(function () {

            var itemzengpin_title = $(this).attr("itemzengpin_title");
            var itemzengpin_id = $(this).attr("itemzengpin_id");


            $("#txtItemPricelist_Title").val(itemzengpin_title);
            $("#txtItemPricelist_Id").val(itemzengpin_id);
        });
    });
</script>
</head>

<body>
<div class="div-content">
    <dl>
      <dt>商品ID<b style="color:red;">*</b></dt>
      <dd>
        <input type="text" id="txtItemPricelist_Id" class="input normal">
        <span class="Validform_checktip">*</span>
      </dd>
    </dl> 
    <dl>
      <dt>商品标题<b style="color:red;">*</b></dt>
      <dd>
        <input type="text" id="txtItemPricelist_Title" class="input normal">
        <span class="Validform_checktip">*</span>
      </dd>
    </dl>
    
    <dl>
      <dt>虚拟销售价<b style="color:red;">*</b></dt>
      <dd>
        <input type="text" id="txtItemPricelist_salePrice" class="input normal"> 元
        <span class="Validform_checktip">*</span>
      </dd>
    </dl>
    <dl>
      <dt>实际销售价<b style="color:red;">*</b></dt>
      <dd>
        <input type="text" id="txtItemPricelist_realSalePrice" class="input normal"> 元
        <span class="Validform_checktip">*</span>
      </dd>
    </dl>
    <dl>
      <dt>虚拟进货价<b style="color:red;">*</b></dt>
      <dd>
        <input type="text" id="txtItemPricelist_stockPrice" class="input normal"> 元
        <span class="Validform_checktip">*</span>
      </dd>
    </dl>
    <dl>
      <dt>实际进货价<b style="color:red;">*</b></dt>
      <dd>
        <input type="text" id="txtItemPricelist_realStockPrice" class="input normal"> 元
        <span class="Validform_checktip">*</span>
      </dd>
    </dl>
    <dl>
      <dt>发货地点</dt>
      <dd>
    		<input type="text" id="txtItemPricelist_dispatch" class="input normal"> 
        	<span class="Validform_checktip">*</span>
      </dd>
    </dl>    
    <dl>
      <dt>所属分公司名称</dt>
      <dd>
    		<input type="text" id="txtItemPricelist_companyName" class="input normal"> 
        	<span class="Validform_checktip">*</span>
      </dd>
    </dl>        
    <dl>
        <dt>是否推荐</dt>
        <dd>
            <input type="radio" name="isCommendIndex" value="true" ><label>推荐</label>
            <input type="radio" name="isCommendIndex" value="false" checked="checked"><label>不推荐</label>
        
        <#--
            <div class="rule-multi-radio multi-radio">
                <span id="rblStatus" style="display: none;">
                    <input type="radio" name="isCommendIndex" value="true" ><label>推荐</label>
                    <input type="radio" name="isCommendIndex" value="false" ><label>不推荐</label>
                </span>
            </div>
          -->  
        </dd>
    </dl>    
</div>

<form name="form1" method="post" action="" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input type="hidden" name="total" value="${total!"0"}">
</div>

<script type="text/javascript">
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
</script>
<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 312px;">
    <div class="l-list">
      <ul class="icon-list"></ul>
      <div class="menu-list">
        <div class="rule-single-select single-select">
            <select name="categoryId" onchange="javascript:setTimeout(__doPostBack('categoryId',''), 0)" id="ddlCategoryId" style="display: none;">
                <option <#if !categoryId??>selected="selected"</#if> value="">所有类别</option>
                <#if category_list??>
                    <#list category_list as cat>
                        <option value="${cat.id}" <#if categoryId?? && categoryId==cat.id>selected="selected"</#if> >${cat.title!""}</option>
                    </#list>
                </#if>
            </select>
        </div>        
      </div>
    </div>
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${keywords!""}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>      
    </div>
  </div>
</div>
<!--/工具栏-->

<!--文字列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tbody>
        <tr class="odd_bg">
            <th align="left">标题</th>
            <th align="left" width="20%">所属类别</th>
            <th align="left" width="16%">发布时间</th>    
        </tr>
        
        <#if goods_page??>
            <#list goods_page.content as goods>
                <tr>
                    <td>
                        <a class="itemzengpin_select" style="cursor:pointer;" itemzengpin_title="${goods.title!""} ${goods.version!""} ${goods.color!""} ${goods.capacity!""} ${goods.saleType!""}" itemzengpin_id="${goods.id!""}" itemzengpin_price="${goods.salePrice?string("#.##")}" itemzengpin_image="${goods.coverImageUri!''}">${goods.title!""} ${goods.version!""} ${goods.color!""} ${goods.capacity!""} ${goods.saleType!""}</a></td>
                    <td>${goods.categoryTitle!""}</td>
                    <td>${goods.onSaleTime!""}</td>
                </tr>
            </#list>
        </#if>
</tbody>
</table>

<!--/文字列表-->

<!--内容底部-->
<#assign PAGE_DATA=goods_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>

</body>
</html>