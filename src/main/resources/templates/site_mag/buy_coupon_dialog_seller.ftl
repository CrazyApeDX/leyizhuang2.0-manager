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
            $("#txtItemZengPin_Title").val($(parentObj).find("input[id='title']").val()); 
            $("#txtItemZengPin_Id").val($(parentObj).find("input[id='id']").val()); 
            $("#txtItemZengPin_Price").val($(parentObj).find("input[id='price']").val());
            $("#txtItemZengPin_CurrentPrice").val($(parentObj).find("input[id='currentPrice']").val());
            $("#txtItemZengPin_CoverImageUri").val($(parentObj).find("input[id='image']").val());
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
            title: "商品组合",
            content: 'url:/Verwalter/buy/coupon/by/seller/dialog?username=' + $("#username").val(),
            width: 800,
            height: 550
        });
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            zengpinDialog.data = obj;
        }
    }
    
    //删除促销赠品节点
    function del_goods_comb(obj) {
        $(obj).parent().parent().remove();
    }
    
    //提交表单处理
    function submitForm() {
        //验证表单
        if ($("#txtItemZengPin_Title").val() == "") {
            W.$.dialog.alert('请填写组合商品标题！', function () { $("#txtItemZengPin_Title").focus(); }, api);
            return false;
        }
        
        if ($("#txtItemZengPin_CurrentPrice").val() == "") {
            W.$.dialog.alert('请填写商品数量！', function () { $("#txtItemZengPin_CurrentPrice").focus(); }, api);
            return false;
        }
        $("#seller_name", W.document).append("<dd id='sellerRealName' name='sellerRealName'>"+$("#seller_name").val()+"</dd>")
        $("#seller_phone", W.document).append("<dd id='sellerUsername' name='sellerUsername'>"+$("#seller_phone").val()+"</dd>")
        $("#hidden_seller_username", W.document).val($("#seller_phone").val())
        $("#hidden_flag", W.document).val($("#flag").val())
        api.close();
    }
    
    $(function () {
        $(".itemzengpin_select").click(function () {
            var seller_name = $(this).attr("itemzengpin_title");
            var seller_id = $(this).attr("itemzengpin_id");
            var seller_phone = $(this).attr("itemzengpin_price");
            $("#seller_name").val(seller_name);
            $("#seller_id").val(seller_id);
            $("#seller_phone").val(seller_phone);
        });
    });
</script>
</head>

<body>
<div class="div-content">
    <input type="hidden" id="txtItemZengPin_CoverImageUri" class="input normal">
    <dl>
      <dt>导购ID</dt>
      <dd>
        <input type="text" id="seller_id" class="input normal">
        <span class="Validform_checktip">*</span>
      </dd>
    </dl> 
    <dl>
      <dt>导购姓名</dt>
      <dd>
        <input type="text" id="seller_name" class="input normal">
        <span class="Validform_checktip">*</span>
      </dd>
    </dl>
    <dl>
      <dt>导购电话</dt>
      <dd>
        <input type="text" id="seller_phone" class="input normal">
        <span class="Validform_checktip">*</span>
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
<input type="text" id="flag" name="flag" value="${flag!""}"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tbody>
        <tr class="odd_bg">
            <th align="center" width="30%">导购ID</th>
            <th align="center" width="30%">导购姓名</th>
            <th align="center" width="40%">导购电话</th>
        </tr>
        
        <#if seller_page??>
            <#list seller_page.content as seller>
            	<tr>
	                <td width="30%" align="center">${seller.id!""}</td>
	                <td width="30%" align="center"><a class="itemzengpin_select" style="cursor:pointer;" itemzengpin_title="${seller.realName!''}" itemzengpin_id="${seller.id?c}" itemzengpin_price="${seller.username}"> ${seller.realName!"" }</a></td>
	                <td width="40%" align="center">${seller.username!""}</td> 
	            </tr>
            </#list>
        </#if>
</tbody>
</table>

<!--/文字列表-->

<!--内容底部-->
<#--
<#if goods_page??>
<#assign PAGE_DATA=goods_page />
<#include "/site_mag/list_footer.ftl" />
</#if>
-->
<!--/内容底部-->
</form>

</body>
</html>