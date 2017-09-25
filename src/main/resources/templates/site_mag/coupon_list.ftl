<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>优惠券</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/coupon/list" id="form1">
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
  <a href="/Verwalter/coupon/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>优惠券</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/coupon/edit"><i></i><span>新增</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
      </ul>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th width="8%">选择</th>
    <th align="left">优惠券类型</th>
    <th align="left">优惠券名称</th>
    <th align="left">发放类型</th>
    <th align="left">剩余数量</th>
    <th align="left">添加时间</th>
    <th align="left">排序</th>
    <th width="10%">操作</th>
  </tr>

    <#if coupon_page??>
        <#list coupon_page.content as item>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${item_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${item.id?c}">
                </td>
                <td>
                <#switch item.typeCategoryId>
                <#case 1>
                	通用现金券
                	<#break>	
                <#case 2>
                	指定商品现金券
                	<#break>
                <#case 3>
                    产品券
                    <#break>	
                <#default>
                	无类别	
                </#switch>			
                </td>
                <td><a href="/Verwalter/coupon/edit?id=${item.id?c}">${item.typeTitle!''}</a></td>
                <td>
                <#if item.typeId?? && item.typeId==2>用户抢券<#else>手动发券</#if>
                </td>
                <td><#if item.leftNumber??>${item.leftNumber?c}<#else>0</#if></td>
                <td><#if item.addTime??>${item.addTime?string('yyyy-MM-dd HH:mm')}</#if></td>
                <td><input name="listSortId" type="text" disabled="" value="${item.sortId!""}" class="sort" onkeydown="return checkNumber(event);"></td>
                <td align="center">
                    <#if item.typeId?? && item.typeId==1><a href="/Verwalter/coupon/grant/${item.id?c}" >发放</a>&nbsp/&nbsp;</#if>
                    <a href="/Verwalter/coupon/edit?id=${item.id?c}">查看</a>
                </td>
              </tr>
        </#list>
    </#if>
 
  </tbody>
</table>

<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=coupon_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>

</body></html>