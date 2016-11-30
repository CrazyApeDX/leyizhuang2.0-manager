<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>价目表</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>
<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/pricelist/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
<input type="hidden" name="type" id="type" value="<#if type??>${type}<#else>pricelist</#if>" >
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
        <a><span>价目表管理</span></a>
        <i class="arrow"></i>
        <span>价目表列表</span>
          
    </div>
    <!--/导航栏-->
    <!--工具栏-->
    <div class="toolbar-wrap">
        <div id="floatHead" class="toolbar">
            <div class="l-list">
                <ul class="icon-list">
                    <li><a class="add" href="/Verwalter/pricelist/edit?__VIEWSTATE=${__VIEWSTATE!""}"><i></i><span>新增</span></a></li>
      				<li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>              
                    <li>
                        <a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a>
                    </li>
                    <li>
                        <a onclick="return ExePostBack('btnDelete','删除后将无法恢复，是否继续？');" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a>
                    </li>
                    
                </ul>
            </div>
            <div class="r-list">
                <input name="keywords" type="text" class="keyword">
                <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
            </div>
        </div>
    </div>
    <!--/工具栏-->
    <!--列表-->
    
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
<tbody>
    <tr class="odd_bg">
        <th width="8%">
            选择
        </th>
        <th align="left">
            价目表编号
        </th>
        <th align="left" width="">
            价目表名称
        </th>
        <th align="left" width="">
           所属城市id
        </th>
        <th align="left" width="">
           开始时间
        </th>
        <th align="left" width="12%">结束时间</th>
        <th width="12%">操作</th>            
    </tr>

    <#if pricelist_page??>
        <#list pricelist_page.content as pricelist>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${pricelist_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${pricelist.id?c}">
                </td>
                <td>
                    <#if pricelist.listHeaderId??>${pricelist.listHeaderId?c}</#if>
                </td>
                <td>${pricelist.name!""}</td>
                <td><#if pricelist.cityId??>${pricelist.cityId?c}<#else>无效</#if></td>
                <td>${pricelist.startDateActive!""}</td>
	            <td>${pricelist.endDateActive!""}</td>
	            <td align="center">
	                <a href="/Verwalter/pricelist/itemEdit?id=${pricelist.id?c}">编辑商品</a>
	            </td>                
            </tr>
        </#list>
    </#if>
</tbody>
</table>
        
<!--/列表-->
<!--内容底部-->
<#assign PAGE_DATA=pricelist_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>