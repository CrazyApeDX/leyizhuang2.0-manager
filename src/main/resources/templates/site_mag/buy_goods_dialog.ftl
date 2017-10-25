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

        //添加或修改
        if ($(api.data).length > 0) {
            var parentObj = $(api.data).parent().parent();
            parentObj.find("input[id='title']").val($("#txtItemZengPin_Title").val());
            parentObj.find("input[id='id']").val($("#txtItemZengPin_Id").val());
            parentObj.find("input[id='price']").val($("#txtItemZengPin_Price").val());
            parentObj.find("input[id='image']").val($("#txtItemZengPin_CoverImageUri").val());
        } else {
            var trHtml = '<tr class="td_c">'
            + '<td><input name="combList[${total!'0'}].id" type="hidden" value="" />'
            + '<input name="combList[${total!'0'}].coverImageUri" type="hidden" value="' + $("#txtItemZengPin_CoverImageUri").val() + '" />'
            + '<input type="text" name="combList[${total!'0'}].sortId" class="td-input" value="99" style="width:90%;" /></td>'
            + '<td><input type="text" id="id" name="combList[${total!'0'}].goodsId" class="td-input" value="' + $("#txtItemZengPin_Id").val() + '" style="width:90%;" /></td>'
            + '<td><input type="text" id="title" name="combList[${total!'0'}].goodsTitle" class="td-input" value="' + $("#txtItemZengPin_Title").val() + '" style="width:90%;" /></td>'
            + '<td><input type="text" id="number" name="combList[${total!'0'}].number" class="td-input" value="' + $("#txtItemZengPin_CurrentPrice").val() + '" style="width:90%;" /></td>'
            + '<td><input type="text" id="price" name="combList[${total!'0'}].goodsPrice" class="td-input" value="' + $("#txtItemZengPin_Price").val() + '" style="width:90%;" /></td>'
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
//        api.close();
        wipeForm();
    }
    function wipeForm(){
        $("#txtItemZengPin_Title").val("");
        $("#txtItemZengPin_Id").val("");
        $("#txtItemZengPin_Price").val("");
        $("#txtItemZengPin_CurrentPrice").val("");
        $("#txtItemZengPin_CoverImageUri").val("");

    }
    $(function () {
        $(".itemzengpin_select").click(function () {

            var itemzengpin_title = $(this).attr("itemzengpin_title");
            var itemzengpin_id = $(this).attr("itemzengpin_id");
            var itemzengpin_price = $(this).attr("itemzengpin_price");
            var itemzengpin_image = $(this).attr("itemzengpin_image");
            var itemzengpin_currentPrice = $(this).attr("itemzengpin_currentPrice");

            $("#txtItemZengPin_Title").val(itemzengpin_title);
            $("#txtItemZengPin_Id").val(itemzengpin_id);
            $("#txtItemZengPin_Price").val(itemzengpin_price);
            $("#txtItemZengPin_CurrentPrice").val(itemzengpin_currentPrice);
            $("#txtItemZengPin_CoverImageUri").val(itemzengpin_image);
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
      <dt>商品原价</dt>
      <dd>
        <input type="text" id="txtItemZengPin_Price" class="input normal"> 元
        <span class="Validform_checktip">*</span>
      </dd>
    </dl>
    <dl>
      <dt>商品数量</dt>
      <dd>
        <input type="text" id="txtItemZengPin_CurrentPrice" class="input normal"> 件
        <span class="Validform_checktip">*商品数量(必填)</span>
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

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tbody>
        <tr class="odd_bg">
            <th align="left">标题</th>
            <th align="left" width="12%">商品编码</th>
            <th align="left" width="12%">所属类别</th>
            <th align="left" width="12%">销售价</th>
            <th align="left" width="16%">发布时间</th>
        </tr>

        <#if goods_page??>
            <#list goods_page as goods>
            	<#if goods.salePrice?? && goods.salePrice != 0>
	                <tr>
	                    <td>
	                        <a class="itemzengpin_select" style="cursor:pointer;" itemzengpin_title="${goods.title!""} ${goods.version!""} ${goods.color!""} ${goods.capacity!""} ${goods.saleType!""}" itemzengpin_id="${goods.id?c}" itemzengpin_price="<#if goods.salePrice??>${goods.salePrice?string("#.##")}<#else>0.00</#if>" itemzengpin_image="${goods.coverImageUri!''}">${goods.title!""} ${goods.version!""} ${goods.color!""} ${goods.capacity!""} ${goods.saleType!""}</a></td>
	                    <td>${goods.code!""}</td>
	                    <td>${goods.categoryTitle!""}</td>
	                    <td><#if goods.salePrice??>${goods.salePrice?string("#.##")}<#else>0.00</#if></td>
	                    <td>${goods.onSaleTime!""}</td>
	                </tr>

                </#if>
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