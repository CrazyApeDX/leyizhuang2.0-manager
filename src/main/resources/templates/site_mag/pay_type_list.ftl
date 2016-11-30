<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>支付方式</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/order/setting/pay/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
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

<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>支付方式</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/order/setting/pay/edit"><i></i><span>新增</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        <li style="padding-left:5px"><a  class="btn-search" href="/Verwalter/order/setting/codDistrict/list"><i></i><span>货到付款支持地区</span></a></li>
      </ul>
    </div>
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${keywords!''}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody><tr class="odd_bg">
    <th width="8%">选择</th>
    <th align="left" width="15%">名称</th>
    <th align="left" width="15%">图标</th>
    <th align="left">支付描述</th>
    <th align="left" width="12%">排序</th>
    <th width="8%">是否启用</th>
    <th width="10%">操作</th>
  </tr>
    
    <#if pay_type_page??>
        <#list pay_type_page.content as item>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input type="checkbox" name="listChkId" value="${item_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${item.id?c}">
                </td>
                <td><a href="/Verwalter/order/setting/pay/edit?id=${item.id?c}">${item.title!""}</a></td>
                <#if item.coverImageUri?? && item.coverImageUri!="">
                <td><img width="120" src="${item.coverImageUri!""}"></td>
                <#else>
                <td>-</td>
                </#if>
                <td>${item.info!""}</td>
                <td><input name="listSortId" type="text" value="${item.sortId!""}" class="sort" onkeydown="return checkNumber(event);"></td>
                <td align="center"><#if item.isEnable?? && item.isEnable>是<#else>否</#if></td>
                <td align="center">
                    <a href="/Verwalter/order/setting/pay/edit?id=${item.id?c}">修改</a>
                </td>
            </tr>
        </#list>
    </#if>  
</tbody>
</table>

<!--/列表-->
<!--内容底部-->
<#assign PAGE_DATA=pay_type_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body>
</html>