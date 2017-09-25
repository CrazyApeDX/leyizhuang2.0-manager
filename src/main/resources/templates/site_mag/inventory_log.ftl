<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>日志管理</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<style type="text/css">
.odiv{
	float:left;
	margin-bottom:10px;
	width:310px;}
 .odiv span.span1{
	display:block;
	float:left;
	width:116px;
	height:32px;
	line-height:32px;
	text-align:right;
}
.a1{
	float:left;
	margin-top:6px;
	margin-left:20px;
}

</style>
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/goods/inventory/log" id="form1">
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
    function downloaddate(type)
    {
    	var begain = $("#begain").val();
    	var end = $("#end").val();
        var keywords = $("#keywords").val();
        var diyCode = $("#siteId").val();
        var city = $("#regionId").val();
        if(begain == "")
        {
            alert("请选择开始时间！");
            return;
        }
        if(null==city){
        	city="";
        }
        window.open("/Verwalter/goods/diysiteLogDowndata?keywords="+ keywords + "&diyCode="+diyCode+"&cityCode="+city+"&type="+type+"&begindata="+ begain + "&enddata=" + end);
    }
</script>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>系统用户</span>
  <i class="arrow"></i>
  <span>日志列表</span>  
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <!--  <#li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除日志</span></a></li>-->
      </ul>
      <div class="menu-list">
         <#if !is_diy_site_bool??>
            <div class="rule-single-select">
                <select id="regionId" name="regionId" onchange="javascript:setTimeout(__doPostBack('categoryId', ''), 0)">
                    <option <#if !regionId??>selected="selected"</#if> value="" >所有城市</option>
                    <#if city_list??>
                        <#list city_list as c>
                            <option value='${c.sobIdCity?c}' <#if regionId?? && c.sobIdCity==regionId>selected="selected"</#if> >${c.cityName!""}</option>
                        </#list>
                    </#if>
                </select>
            </div>
            </#if>
            <#if site_list??>
            <div class="rule-single-select">
                <select id="siteId" name="siteId" >
                	<#if !is_diy_site_bool??>
                	<option <#if !siteId??>selected="selected"</#if> value="" >所有门店</option>
                	 </#if>
                        <#list site_list as site>
                            <option value='${site.id?c}' <#if siteId?? && site.id==siteId>selected="selected"</#if> >${site.title!""}</option>
                        </#list>
                    
                </select>
            </div>
            </#if>
        </div>
        <div class="odiv">
						<span class="span1">开始时间：</span> <input name="orderStartTime"
							id="begain" type="text" value="${orderStartTime!"" }" class="input date"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})"
							datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/"
							errormsg="请选择正确的日期" sucmsg=" " />
					</div>
					<div class="odiv">
						<span class="span1">结束时间：</span> <input name="orderEndTime"
							id="end" type="text" value="${orderEndTime!"" }" class="input date"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})"
							datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/"
							errormsg="请选择正确的日期" sucmsg=" " />
					</div>
    </div>
    <div class="r-list">
    			
     <input id="keywords" name="keywords" type="text" class="keyword" value="${keywords!''}"/>
     <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('lbtnSearch','')">查询</a>
     <#if !is_diy_site_bool??> <a style="color:black;line-height: 30px;margin-left: 20px;" href="javascript:downloaddate(1)">城市进销报表下载</a></#if>
      <a style="color:black;line-height: 30px;margin-left: 20px;" href="javascript:downloaddate(2)">门店进销报表下载</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th width="8%">选择</th>
   	<!-- <th align="left" width="8%">用户名</th> -->
    <th align="left" width="8%">类型</th>
    <th align="left" width="8%">所属门店或城市</th>
    <th align="left" width="15%">产品编码</th>
    <th align="left" >产品名</th>
    <th align="left" width="5%">改变数量</th>
    <th align="left" width="15%">改变时间</th>
    <th align="left" width="10%">关联单号</th>
  </tr>

    <#if log_page??>
        <#list log_page.content as item>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${item_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${item.id?c}">
                </td>
                <!-- <td align="left">${item.manager!""}</td> -->
                <td align="left"><#if item.changeType??>${item.changeType!""}<#else>无</#if></td>
                <td align="left"><#if item.diySiteTitle??>${item.diySiteTitle!'无'}<#else>${item.regionName!'无'}</#if></td>
                <td align="left">${item.goodsSku!""}</td>
                <td align="left">${item.goodsTitle!""}</td>
                <td align="left">${item.changeValue!""}</td>
                <td align="left"><#if item.changeDate??>${item.changeDate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
                <td align="left">${item.orderNumber!""}</td>
            </tr>
        </#list>
    </#if>
</tbody>
</table>

<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=log_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>