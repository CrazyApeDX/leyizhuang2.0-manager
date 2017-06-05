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
        }
    });
    
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
        
        if ($("#txtItemZengPin_Num").val() == "") {
            W.$.dialog.alert('请填写商品数量！', function () { $("#txtItemZengPin_Num").focus(); }, api);
            return false;
        }
        
        //添加或修改
        if ($(api.data).length > 0) {
            var parentObj = $(api.data).parent().parent();
            parentObj.find("input[id='title']").val($("#txtItemZengPin_Title").val());
            parentObj.find("input[id='id']").val($("#txtItemZengPin_Id").val());
        } else {
            var trHtml = '<tr class="td_c">'
            + '<td><input type="hidden" name="details[${total!'0'}].goodSku" value="' + $("#txtItemZengPin_Code").val() + '"  /><input type="text" id="id" name="details[${total!'0'}].goodId" class="td-input" value="' + $("#txtItemZengPin_Id").val() + '" style="width:90%;border: none;" readonly /></td>'
            + '<td><input type="text" id="title" name="details[${total!'0'}].goodTitle" class="td-input" value="' + $("#txtItemZengPin_Title").val() + '" style="width:90%;border: none;" readonly /></td>'
            + '<td><input type="text" id="number" name="details[${total!'0'}].num" class="td-input number" value="' + $("#txtItemZengPin_Num").val() + '" style="width:90%;" /></td>'
            + '<td>'
            + '<i class="icon"></i>'
            + '<a title="删除" class="img-btn del operator" onclick="del_goods_comb(this);">删除</a>'
            + '</td>'
            + '</tr>'
            //如果是窗口调用则添加到窗口
            if (!api.get('dialogChannel') || !api.get('dialogChannel')) {
                $("#var_box_comb", W.document).append(trHtml);
                $("#totalComb", W.document).val(parseInt($("#totalComb", W.document).val())+1);
            } else {
                $("#var_box_comb", api.get('dialogChannel').document).append(trHtml);
                $("#totalComb", W.document).val(parseInt($("#totalComb", W.document).val())+1);
            }
        }
        api.close();
    }
    
    $(function () {
        $(".itemzengpin_select").click(function () {

            var itemzengpin_title = $(this).attr("itemzengpin_title");
            var itemzengpin_id = $(this).attr("itemzengpin_id");
            var itemzengpin_code = $(this).attr("itemzengpin_code");

            $("#txtItemZengPin_Title").val(itemzengpin_title);
            $("#txtItemZengPin_Id").val(itemzengpin_id);
            $("#txtItemZengPin_Code").val(itemzengpin_code);
        });
    });
</script>
</head>

<body>
<div class="div-content">
    <input type="hidden" id="txtItemZengPin_CoverImageUri" class="input normal">
    <dl>
      <dt>商品ID</dt>
      <dd>
        <input type="text" id="txtItemZengPin_Id" class="input normal">
        <span class="Validform_checktip">*</span>
      </dd>
    </dl> 
    <dl>
      <dt>商品标题</dt>
      <dd>
        <input type="text" id="txtItemZengPin_Title" class="input normal">
        <span class="Validform_checktip">*</span>
      </dd>
    </dl>
    <dl>
      <dt>商品数量</dt>
      <dd>
        <input type="text" id="txtItemZengPin_Num" class="input normal"> 件
        <span class="Validform_checktip">*</span>
      </dd>
    </dl>
    <dl>
      <dt>商品SKU</dt>
      <dd>
        <input type="text" id="txtItemZengPin_Code" class="input normal"> 件
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
            <th align="left" width="12%">商品编码</th>
            <th align="left" width="12%">所属类别</th>
            <th align="left" width="16%">发布时间</th>    
        </tr>
        
        <#if goods_page??>
            <#list goods_page.content as goods>
                <tr>
                    <td>
                        <a class="itemzengpin_select" style="cursor:pointer;" itemzengpin_title="${goods.title!""} ${goods.version!""} ${goods.color!""} ${goods.capacity!""} ${goods.saleType!""}" itemzengpin_id="${goods.id?c}" itemzengpin_code="${goods.code!""}" >${goods.title!""} ${goods.version!""} ${goods.color!""} ${goods.capacity!""} ${goods.saleType!""}</a></td>
                    <td>${goods.code!""}</td>
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