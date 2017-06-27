<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>用户管理</title>
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
<form name="form1" id="theForm" method="post" action="/Verwalter/balance/list" id="form1">
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
  <span>会员管理</span>
  <i class="arrow"></i>
  <span>预存款变更记录</span>  
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
      <ul class="icon-list">
      </ul>
      <div class="menu-list">      
        <div class="rule-single-select single-select">
        <select name="type" id="type" onchange="javascript:setTimeout(__doPostBack('type',''), 0)" style="display: none;">
            <option <#if !type??>selected="selected"</#if> value="">变更类型</option>
            <option <#if type?? && type==0>selected="selected"</#if> value="0">充值</option>
            <option <#if type?? && type==1>selected="selected"</#if> value="1">提现</option>
            <option <#if type?? && type==3>selected="selected"</#if> value="3">消费</option>
            <option <#if type?? && type==4>selected="selected"</#if> value="4">退款</option>
            <option <#if type?? && type==2>selected="selected"</#if> value="2">管理员变更</option>
            <option <#if type?? && type==5>selected="selected"</#if> value="5">经销差价</option>
        </select>
        </div>
      </div>
      
      	<#if cityList?? && cityList?size gt 0 >
          	<div class="menu-list">
              	<div class="rule-single-select">
                       <select name="cityCode" id="cityCode" onchange="javascript:setTimeout(__doPostBack('changeCity',''), 0)">
                       <option value="" >选择城市</option>      
                       <#list cityList as city>
                       	<option value="${city.sobIdCity?c }" <#if cityCode?? && cityCode==city.sobIdCity>selected</#if> >${city.cityName }</option>
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
                       	<option value="${diySite.id?c }" <#if diyCode?? && diyCode==diySite.id>selected</#if> >${diySite.title }</option>
                       </#list>
                       </select>
           		</div>
           	</div>
           	</#if>     
           	<div class="menu-list">
              	  变更时间:
                <input name="startTime" id="begain" type="text" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " value="${startTime!'' }" />
                <input name="endTime" id="end" type="text" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " value="${endTime!'' }" />
               
           	</div>
    </div>
    <div class="r-list">
      <input name="keywords" id="keywords" type="text" class="keyword" value="${keywords!""}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
      <a style="color:black;line-height: 30px;margin-left: 20px;" target="_blank" href="javascript:downloaddate();">报表下载</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th align="center" width="10%">用户名</th>
    <th align="left" width="5%">类型</th>
    <th align="left" width="8%">金额变化</th>
    <#--<th align="left" width="8%">变更后余额</th>-->
	<th align="left" width="8%">变更后可提现</th>
	<th align="left" width="8%">变更后不可提现</th>
	<th align="left" width="8%">变更后总额</th>
    <th align="left" width="10%">变更时间</th>
    <th align="center" width="5%">是否成功</th>
    <th align="left" width="10%">操作描述</th>
    <th align="left" width="10%">涉及单号</th>
  </tr>

    <#if balance_page??>
        <#list balance_page.content as item>
            <tr>
            	<td align="center">${item.username!''}</td>
                <td align="left">
                	<#switch item.type>
                		<#case 0>
                			充值
                		<#break>
                		<#case 1>
                			提现
                		<#break>
                		<#case 2>
                			管理员修改
                		<#break>
                		<#case 3>
                			消费
                		<#break>
                		<#case 4>
                			退款
                		<#break>
                		<#case 5>
                			经销差价
                		<#break>
                		<#case 6>
                			经销差价
                		<#break>
                	</#switch>
                </td>
                <td align="left"><#if item.type==1 || item.type==3 || (item.type==5&&item.reason!='消费返还经销差价')>-</#if><#if item.money??>${item.money?string("0.00")}</#if></td>
                <td align="left"><#if item.cashLeft??>${item.cashLeft?string("0.00")}<#else>暂无</#if></td>
                <td align="left"><#if item.unCashLeft??>${item.unCashLeft?string("0.00")}<#else>暂无</#if></td>
                <td align="left"><#if item.allLeft??>${item.allLeft?string("0.00")}<#else>暂无</#if></td>
                <td align="left"><#if item.createTime??>${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
				<td align="center"><#if item.isSuccess??&&item.isSuccess>完成<#else>未完成</#if></td>
				<td align="left">${item.reason!''}</td>
				<td align="left">${item.orderNumber!''}</td>
        </#list>
    </#if>
     
</tbody>
</table>

<!--列表-->

<!--内容底部-->
<#assign PAGE_DATA=balance_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>
<!-- <form id="upload" action="/Verwalter/upload" enctype="multipart/form-data" method="post">
            <input type="file" onchange="upload()" name="Filedata" id="clickFile">
        	</form> -->

</body></html>