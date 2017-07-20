<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>装饰公司列表</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody">
	<form name="form1" method="post" action="/Verwalter/fitment/promotion/list" id="form1">
		<div>
			<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
			<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
			<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
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
		<div class="location" style="position: static; top: 0px;">
			<a href="javascript:history.back(-1);" class="back">
				<i></i>
				<span>返回上一页</span>
			</a>
			<a href="/Verwalter/center" class="home">
				<i></i>
				<span>首页</span>
			</a>
			<i class="arrow"></i>
			<span>对外合作</span>
			<i class="arrow"></i>
			<span>装饰公司</span>  
		</div>
		<!--/导航栏-->

		<!--工具栏-->
		<div class="toolbar-wrap">
			<div id="floatHead" class="toolbar" style="position: static; top: 42px;">
				<div class="l-list">
					<ul class="icon-list">
							<div class="r-list">
						      <input name="keywords" id="keywords" type="text" class="keyword" value="${keywords!""}">
						      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
						    </div>
					</ul>
				</div>
			</div>
		</div>
		<!--/工具栏-->

		<!--列表-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  			<tbody>
				<tr class="odd_bg">
					<th  width="10%">选择</th>
					<th align="left" width="14%">名称</th>
					<th align="left" width="10%">编码</th>
					<th align="left" width="12%">当前现金返利额度</th>
					<th align="left" width="5%">冻结</th>
					<th align="center" width="10%">操作</th>
				</tr>

    			<#if companyPage??>
        			<#list companyPage.content as item>
			            <tr>
			                <td align="center">
			                    <span class="checkall" style="vertical-align:middle;">
			                        <input id="listChkId" type="checkbox" name="listChkId" value="${item_index}" >
			                    </span>
			                    <input type="hidden" name="listId" id="listId" value="${item.id?c}">
			                </td>
			                <td align="left">
			                	<a href="/Verwalter/fitment/company/edit/${item.id?c}">${item.name!""}</a>
		                	</td>
			                <td align="left">${item.code!""}</td>
			                <td align="left">
			                	<#if item.promotionMoney??>${item.promotionMoney?string("0.00")}<#else>0.00</#if>
			                </td>
			              
			                <td align="left">
			                	<#if item.frozen==true>
			                		<font color="red">是</font>
			                	<#else>
			                		<font color="green">否</font>
			                	</#if>
			                </td>
			                
			                <td align="center">
			                	<a href="/Verwalter/fitment/promotion/goUpdate?id=${item.id?c}">变更</a>
			                </td>
			            </tr>
        			</#list>
    			</#if>
			</tbody>
		</table>
		<!--/列表-->

		<!--内容底部-->
		<#if companyPage??>
			<#assign PAGE_DATA=companyPage />
			<#include "/fitment/management/list_footer.ftl" />
		</#if>
		<!--/内容底部-->
	</form>
</body>
</html>