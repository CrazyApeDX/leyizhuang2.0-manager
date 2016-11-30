<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>优惠券类型</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/coupon/module/list" id="form1">
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
  <a href="/Verwalter/coupon/module/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>优惠券类型</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<#--
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/coupon/module/edit"><i></i><span>新增</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
        <#if tdManagerRole?? && tdManagerRole.isSys>
            <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        </#if>
        <div class="menu-list">
	        <div class="rule-single-select">
	            <select name="cityId" onchange="javascript:setTimeout(__doPostBack('cityId', ''), 0)">
	                <option <#if cityId??&&cityId==0>selected="selected"</#if> value="0">所有城市</option>
	                <#if city_list??>
	                    <#list city_list as c>
	                        <option value="${c.sobIdCity?c}" <#if cityId?? && c.sobIdCity==cityId>selected="selected"</#if> >${c.cityName!""}</option>
	                    </#list>
	                </#if>
	            </select>
	        </div>
        </div>
		<div class="r-list">
      		<input name="keywords" type="text" class="keyword" value="${keywords!''}">
      		<a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('lbtnSearch','')">查询</a>
  		</div>
      </ul>
    </div>
  </div>
</div>
-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/coupon/module/edit"><i></i><span>新增</span></a></li>
        <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <#if tdManagerRole?? && tdManagerRole.isSys>
            <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        </#if>
      </ul>
      <div class="menu-list">
        <div class="rule-single-select">
            <select name="cityId" onchange="javascript:setTimeout(__doPostBack('categoryId', ''), 0)">
                <option <#if cityId??&&cityId==0>selected="selected"</#if> value="0">所有城市</option>
                <#if city_list??>
                    <#list city_list as c>
                        <option value="${c.sobIdCity?c}" <#if cityId?? && c.sobIdCity==cityId>selected="selected"</#if> ><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.cityName!""}</option>
                    </#list>
                </#if>
            </select>
        </div>
      </div>
    </div>
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${keywords!''}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('lbtnSearch','')">查询</a>
	  <#--
      <a id="lbtnViewImg" title="图像列表视图" class="img-view" href="javascript:__doPostBack('lbtnViewImg','')"></a>
      <a id="lbtnViewTxt" title="文字列表视图" class="txt-view" href="javascript:__doPostBack('lbtnViewTxt','')"></a>
      -->
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th width="8%">选择</th>
    <th align="left">名称</th>
    <th align="left">城市</th>
    <th align="left">商品名称</th>
    <th align="left">SKU</th>
    <th width="10%">价值</th>
    <th align="center" width="12%">排序</th>
    <th width="10%">操作</th>
  </tr>

    <#if module_page?? && module_page.content?? && module_page.content?size gt 0>
        <#list module_page.content as item>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${item_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${item.id?c}">
                </td>
                <td><a href="/Verwalter/coupon/module/edit?id=${item.id?c}">${item.title!""}</a></td>
                <td>${item.cityTitle!""}</td>
                <td>${item.goodsTitle!""}</td>
                <td>${item.sku!""}</td>
                <td align="center"><#if item.price??>${item.price?string("0.00")}</#if></td>
                <td align="center"><input name="listSortId" type="text" value="${item.sortId!""}" class="sort" onkeydown="return checkNumber(event);"></td>
                <td align="center">
                    <a href="/Verwalter/coupon/module/edit?id=${item.id?c}">修改</a>
                </td>
              </tr> 
        </#list>
    </#if>
 
</tbody></table>

<!--/列表-->
<!--内容底部-->
<#if module_page??>
<#assign PAGE_DATA=module_page />
<#include "/site_mag/list_footer.ftl" />
</#if>
<!--/内容底部-->
</form>
</body>
</html>