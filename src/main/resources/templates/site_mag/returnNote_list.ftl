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
<form name="form1" method="post" action="/Verwalter/returnNote/returnNote/list/" id="form1">
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
        <div id="floatHead" class="toolbar">
            <div class="l-list">
                <ul class="icon-list">
                    <li>
                        <a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a>
                    </li>
                    <#--
                    <li>
                        <a onclick="return ExePostBack('btnDelete','删除后将无法恢复，是否继续？');" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除退货单</span></a>
                    </li>
                    -->
                    
                </ul>
                <div class="menu-list">
	              	<div class="rule-single-select">
	                       <select name="statusId" id="statusId" onchange="javascript:setTimeout(__doPostBack('changeStatusId',''), 0)">
	                       <option value="" >订单状态</option>      
	                       	<option value="1" <#if statusId?? && statusId==1>selected</#if> >待通知物流</option>
	                       	<option value="2" <#if statusId?? && statusId==2>selected</#if> >待取货</option>
	                       	<#--<option value="3" <#if statusId?? && statusId==3>selected</#if> >待退款</option>
	                       	<option value="3" <#if statusId?? && statusId==3>selected</#if> >待确认收货</option>-->
	                       	<option value="4" <#if statusId?? && statusId==4>selected</#if> >待退款</option>
	                       	<option value="5" <#if statusId?? && statusId==5>selected</#if> >已完成</option>
	                       	<option value="6" <#if statusId?? && statusId==6>selected</#if> >退货取消</option>
	                       </select>
	           		</div>
	           	</div>
                 <#if cityList?? && cityList?size gt 0 >
          	<div class="menu-list">
              	<div class="rule-single-select">
                       <select name="city" id="cityCode" onchange="javascript:setTimeout(__doPostBack('changeCity',''), 0)">
                       <option value="" >选择城市</option>      
                       <#list cityList as city>
                       	<option value="${city.cityName }" <#if cityName?? && cityName==city.cityName>selected</#if> >${city.cityName }</option>
                       </#list>
                       </select>
           		</div>
           	</div>
           	</#if>
            <#if diySiteList?? && diySiteList?size gt 0 >
            <div class="menu-list" >
                <div class="rule-single-select">
                       <select name="diyCode" id="diyCode" onchange="javascript:setTimeout(__doPostBack('changeDiy',''), 0)">
                       <option value="" >选择门店</option>      
                       <#list diySiteList as diySite>
                       	<option value="${diySite.id }" <#if diyCode?? && diyCode==diySite.id>selected</#if> >${diySite.title }</option>
                       </#list>
                       </select>
           		</div>
           	</div>
           	</#if> 
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
            退货单号
        </th>
        <th align="left">
            订单状态
        </th>
         <th align="left" width="12%">
            申请用户
        </th>
        <th align="left" width="12%">
           用户名称
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
                <td><#if name_map??>${name_map[returnNote.username]!''}</#if></td>
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