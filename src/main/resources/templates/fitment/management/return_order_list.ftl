<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>退货单</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>
<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/fitment/retuenOrder/list/" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
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
function downloaddate(type)
{
    var begain = $("#begain").val();
    var end = $("#end").val();
    var diyCode = $("#diyCode").val();
    var city = $("#city").val();
    if(begain==""){
    	$.dialog.confirm("将导出全部数据,请确认导出?", function () {
    		downloaddateurl(type,"/Verwalter/returnNote/downdatareturnorder?begindata="+ begain + "&enddata=" + end + "&diyCode=" + diyCode+ "&city=" + city);
    		return;
        });
    }else{
    	downloaddateurl(type,"/Verwalter/returnNote/downdatareturnorder?begindata="+ begain + "&enddata=" + end + "&diyCode=" + diyCode+ "&city=" + city);
    }
    
   
}
function downloaddateurl(type,url){
	if(type == 0)
    {
		location.href=url;
    }
}
</script>
    <!--导航栏-->
    <div class="location">
        <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
        <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
        <i class="arrow"></i>
        <a><span>退货单管理</span></a>
        <i class="arrow"></i>
        <span>退货单列表</span>
          
    </div>
    <!--/导航栏-->
    <!--工具栏-->
    <div class="toolbar-wrap">
	  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
	    <div class="l-list">
	      <ul class="icon-list">
	      </ul>
	           	
	           	<div class="menu-list">
	              	 <span class="span1">开始时间：</span>
	                <input name="orderStartTime" id="begain" type="text" value="${orderStartTime!"" }" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " />
	                <span class="span1">结束时间：</span>
	                <input name="orderEndTime" id="end" type="text" value="${orderEndTime!"" }" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " />
	           	<#if companyList?? && companyList?size gt 0 >
	                <div class="rule-single-select">
                       <select name="company" id="company" onchange="javascript:setTimeout(__doPostBack('changeDiy',''), 0)">
                       <option value="" >选择装饰公司</option>      
                       <#list companyList as companys>
                       	<option value="${companys.code!'' }" <#if company?? && company==companys.code>selected</#if>>${companys.name }</option>
                       </#list>
                       </select>
	           		</div>
	           	</div>
	           </#if>
	    </div>
	    
	    <div class="r-list">
	      <input name="keywords" id="keywords" type="text" class="keyword" value="${keywords!""}">
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
            退货单号
        </th>
        <th align="left">
            订单状态
        </th>
         <th align="left" width="12%">
            申请用户
        </th>
        <th align="left" width="12%">
            门店名称
        </th>
        <th align="left" width="10%">
            退货原因
        </th>
        <th align="left" width="10%">
            申请时间
        </th>
        <th width="8%">
            确认时间
        </th>
        <th width="8%">
            操作
        </th>
    </tr>

    <#if returnNote_page??>
        <#list returnNote_page.content as returnNote>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${returnNote_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${returnNote.id?c}">
                </td>
                <td>
                    <a href="/Verwalter/returnNote/returnNote/edit?id=${returnNote.id?c}">${returnNote.returnNumber!""}</a></td>
                <td>${returnNote.statusName!""}</td>
                <td>${returnNote.username!""}</td>
                <td>${returnNote.diySiteTitle!""}</td>
                <td>${returnNote.remarkInfo!""}</td>
                <td><#if returnNote.orderTime??>${returnNote.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
                <td align="center">
                   <#if returnNote.checkTime??>${returnNote.checkTime?string("yyyy-MM-dd HH:mm:ss")}</#if>
                </td>
                
                <td align="center">
                    <a href="/Verwalter/returnNote/returnNote/edit?id=${returnNote.id?c}">详细</a>
                </td>
            </tr>
        </#list>
    </#if>
</tbody>
</table>
        
<!--/列表-->
<!--内容底部-->
<#assign PAGE_DATA=returnNote_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>