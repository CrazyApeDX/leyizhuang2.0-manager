<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>变更记录</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
$(function(){
	var theForm = document.getElementById("theForm");
	if (!theForm) {
	    theForm = document.getElementById("theForm");
	}
});
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}
function downloaddate()
{
    var begain = $("#begain").val();
    var end = $("#end").val();
    var diyCode = $("#diyCode").val();
    var city = $("#cityCode").val();
    var keywords = $("#keywords").val();
    var type = $("#type").val();
    if(begain==""){
    	$.dialog.confirm("将导出全部数据,请确认导出?", function () {
    		window.open("/Verwalter/balance/downdata?begindata="+ begain + "&enddata=" + end + "&diyCode=" + diyCode+ "&cityId=" + city
    		+ "&keywords=" + keywords+ "&type=" + type);
    		return;
        });
    }else{
    	window.open("/Verwalter/balance/downdata?begindata="+ begain + "&enddata=" + end + "&diyCode=" + diyCode+ "&cityId=" + city
    	+ "&keywords=" + keywords+ "&type=" + type);
    }
}
</script>
<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" id="theForm" method="post" action="/Verwalter/delivery/fee/change/log/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
</div>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>运费管理</span>
  <i class="arrow"></i>
  <span>变更记录</span>  
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
	<div id="floatHead" class="toolbar" style="position: static; top: 42px;">
		<div class="r-list">
			<input name="keywords" type="text" class="keyword" value="${keywords!''}">
			<a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('lbtnSearch','')">查询</a>
		</div>
	</div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th align="center" width="20%">管理员</th>
    <th align="center" width="20%">订单号</th>
    <th align="center" width="20%">操作时间</th>
    <th align="center" width="20%">更改前</th>
    <th align="center" width="20%">更改后</th>
  </tr>

    <#if logPage??>
        <#list logPage.content as item>
            <tr>
            	<td align="center">${item.manager!''}</td>
            	<td align="center">${item.orderNumber!''}</td>
            	<td align="center">
            		<#if item.operationTime??>
						${item.operationTime?string("yyyy-MM-dd HH:mm:ss")}
					</#if>
				</td>
            	<td align="center">${(item.oldFee!0)?string("0.00")}</td>
            	<td align="center">${(item.newFee!0)?string("0.00")}</td>
        	<tr>
        </#list>
    </#if>
     
</tbody>
</table>

<!--列表-->

<!--内容底部-->
<#assign PAGE_DATA=logPage />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>
<!-- <form id="upload" action="/Verwalter/upload" enctype="multipart/form-data" method="post">
            <input type="file" onchange="upload()" name="Filedata" id="clickFile">
        	</form> -->

</body></html>