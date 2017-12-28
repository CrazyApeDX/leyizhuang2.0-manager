<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>内容列表</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<style type="text/css">
.r-list .odiv{
	float:left;
	margin-bottom:10px;
	width:225px;
	font-size: 15px;
}
.r-list .odiv span.span1{
	display:block;
	float:left;
	width:50px;
	height:32px;
	line-height:32px;
	text-align:right;
}

</style>
<script type="text/javascript">
    $(function () {
    	$("#begin").val($("#oldStartTime").val());
    	$("#end").val($("#oldEndTime").val());
    });
</script>
</head>

<body class="mainbody">
<div style="left: 0px; top: 0px; visibility: hidden; position: absolute;" class=""><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/allocation/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
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
        if(checkDate()) {
        	theForm.submit();
        }
    }
}

//判断活动结束时间不能小于开始时间
function checkDate(){
	var beginDate= $('#begin').val();
	var finishDate=$('#end').val();
	if(finishDate!='' && beginDate!='' && finishDate <= beginDate){
		alert("亲,开始时间必须大于结束时间");
		return false;
	} else {
		return true;
	}
}

   document.onkeydown = function(event){
        if((event.keyCode || event.which) == 13){
            __doPostBack('btnSearch','')
        }
   }
</script>

<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>内容列表</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="r-list" style="width: 100%;">
		<#if cities??&&cities?size gt 0>
			<div class="odiv" style="float: left; width: 170px;">
				<span class="span1">城市：</span>
				<div class="rule-single-select">
					<select name="cityId" id="cityId" onchange="javascript:setTimeout(__doPostBack('',''), 0)">
						<option value="">请选择</option>
						<#list cities as item>
							<option value="${item.id}" <#if cityId?? && cityId==item.id>selected</#if>>${item.cityName}</option>
						</#list>
					</select>
				</div>
			</div>
		</#if>
		
		<#if cities??&&cities?size gt 0>
			<div class="odiv" style="float: left; width: 170px;">
				<span class="span1">门店：</span>
				<div class="rule-single-select">
					<select name="diySiteId" id="diySiteId" onchange="javascript:setTimeout(__doPostBack('',''), 0)">
						<option value="">请选择</option>
						<#if diySites??&&diySites?size gt 0>
							<#list diySites as item>
								<option value="${item.id}" <#if diySiteId?? && diySiteId==item.id>selected</#if>>${item.title}</option>
							</#list>
						</#if>
					</select>
				</div>
			</div>
		</#if>
		
		<div class="odiv" style="float: left; width: 210px;">
			<span class="span1" style="width: 90px;">调出/调入：</span>
			<div class="rule-single-select">
				<select name="allocationType" id="allocationType" onchange="javascript:setTimeout(__doPostBack('',''), 0)">
					<option value="">请选择</option>
					<option value="1" <#if allocationType?? && allocationType==1>selected</#if>>调出</option>
					<option value="2" <#if allocationType?? && allocationType==2>selected</#if>>调入</option>
				</select>
			</div>
		</div>
		
		<div class="odiv" style="float: left; width: 170px;">
			<span class="span1">状态：</span>
			<div class="rule-single-select">
				<select name="status" id="status" onchange="javascript:setTimeout(__doPostBack('',''), 0)">
					<option value="">请选择</option>
					<option value="1" <#if status?? && status==1>selected</#if>>新建</option>
					<option value="2" <#if status?? && status==2>selected</#if>>已出库</option>
					<option value="3" <#if status?? && status==3>selected</#if>>已入库</option>
					<option value="4" <#if status?? && status==4>selected</#if>>已作废</option>
				</select>
			</div>
		</div>
		
		<div class="odiv">
			<span class="span1" style="width: 30px;">从：</span> <input name="startTime"
				id="begin" type="text" value="${orderStartTime!"" }" class="input date"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})"
				datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/"
				errormsg="请选择正确的日期" sucmsg=" " />
				<input type="hidden" name="oldStartTime" id="oldStartTime" value="${oldStartTime!"" }" /> 
		</div>
		<div class="odiv">
			<span class="span1" style="width: 30px;">至：</span> <input name="endTime"
				id="end" type="text" value="${orderEndTime!"" }" class="input date"
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})"
				datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/"
				errormsg="请选择正确的日期" sucmsg=" " />
				<input type="hidden" name="oldEndTime" id="oldEndTime" value="${oldEndTime!"" }" /> 
		</div>
		
		<div class="odiv" style="width: 300px; float: left">
			<div style="float: left;">
				<span class="span1">单号：</span><input name="number" type="text"
					class="input" value="${number!"" }">
			</div>
			<a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a> 
		</div>

	</div>
  </div>
</div>
<!--/工具栏-->

<!--文字列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
<tbody>
    <tr class="odd_bg">
        <th align="center" width="25%">单号</th>
        <th align="left" width="15%">城市</th>
        <th align="left" width="15%">调出门店</th>
        <th align="left" width="15%">调入门店</th>
        <th align="left" width="10%">状态</th>
        <th align="left" width="20%">修改日期</th>
    </tr>
    
    <#if allocation_page?? && allocation_page.content??>
    <#list allocation_page.content as allocation>
        <tr>
            <td align="center"><a href="/Verwalter/allocation/edit?id=${allocation.id?c}">${allocation.number!''}</td>
            <td><a href="/Verwalter/allocation/edit?id=${allocation.id?c}">${allocation.cityName!"无"}</a></td>
            <td><a href="/Verwalter/allocation/edit?id=${allocation.id?c}">${allocation.allocationFromName!"无"}</td>
            <td><a href="/Verwalter/allocation/edit?id=${allocation.id?c}"><#if allocation.allocationFrom??>${allocation.allocationToName!''}</#if></td>
            <td><a href="/Verwalter/allocation/edit?id=${allocation.id?c}">${allocation.statusDisplay!"无"}</td>
            <td>${allocation.updatedTime!"无"}</td>
        </tr>
    </#list>
    </#if>
</tbody>
</table>

<!--/文字列表-->

<!--图片列表-->

<!--/图片列表-->

<!--内容底部-->
<#assign PAGE_DATA=allocation_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>
</body>
</html>