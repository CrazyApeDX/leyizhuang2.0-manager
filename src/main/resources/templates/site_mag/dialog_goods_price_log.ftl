<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>改价纪录</title>
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
        name: '关闭',
        focus: true
    });
</script>
</head>

<body>
<form name="form1" method="get" action="/Verwalter/goods/price/log" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input type="hidden" name="goodsId" id="__VIEWSTATE" value="${goodsId!""}">
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
<!--文字列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tbody>
        <tr class="odd_bg">
            <th align="center" width="8%">ID</th>
            <th align="center">商品名称</th>
            <th align="center" width="20%">价格</th>
            <th align="center" width="16%">改价时间</th>    
            <th align="center" width="14%">编辑人</th> 
        </tr>
        
        <#if price_log_page??>
            <#list price_log_page.content as item>
                <tr>
                    <td align="center">${item.id!""}</td>
                    <td>${item.goodsTitle!""}</td>
                    <td align="center">${item.price?string("0.00")}
                        <#if item.originPrice?? && item.price gt item.originPrice>
                            <span style="color:#F00;">↑</span>
                        <#elseif item.originPrice?? && item.price < item.originPrice>
                            <span style="color:#0D0;">↓</span>
                        </#if>
                     <#if item.originPrice??>（原价${item.originPrice?string("0.00")}）</#if></td>
                    <td align="center">${item.createTime!""}</td>
                    <td align="center">${item.operator!""}</td>
                </tr>
            </#list>
        </#if>
</tbody>
</table>

<!--/文字列表-->

<!--内容底部-->
<#assign PAGE_DATA=price_log_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>

</body>
</html>