<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>投诉咨询</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/user/suggestion/list" id="form1">
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
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>投诉与咨询</span>
  <i class="arrow"></i>
  <span>投诉咨询</span>  
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <#if tdManagerRole?? && tdManagerRole.isSys>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        </#if>
      </ul>
    	<div class="menu-list">
        	<span style="margin:9px 5px 0 5px;float:left; font-size:12px;">筛选查找：</span>
		      <div class="input-date" style="width:204px;">
		      	<span style="margin:9px 5px 0 5px;float:left; font-size:12px;" >从</span>
		        <input  name="date_1" type="text" style="font-size:12px;" value="<#if date_1??>${date_1}</#if>" class="input date" onfocus="WdatePicker({dateFmt:&#39;yyyy-MM-dd&#39;})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
		        <i>日期</i>
		      </div>		
	      		
		      <div class="input-date" style="width:204px;">
		      	<span style="margin:9px 5px 0 5px;float:left; font-size:12px;" >至</span>
		        <input  name="date_2" type="text" style="font-size:12px;" value="<#if date_2??>${date_2}</#if>" class="input date" onfocus="WdatePicker({dateFmt:&#39;yyyy-MM-dd&#39;})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
		        <i>日期</i>
		      </div>			   
		      
		<div class="rule-single-select single-select">
            <select name="categoryId" onchange="javascript:__doPostBack('','')" id="ddlCategoryId" style="display: none;">
                <option <#if categoryId??><#else>selected="selected"</#if> value="">所有类别</option>
                <#if category_list??>
                    <#list category_list as c>
                        <option value="${c.id!""}" <#if categoryId?? && c.id==categoryId>selected="selected"</#if> >${c.name!""}</option>
                    </#list>
                </#if>
            </select>
        </div>
           
	    </div>
	    
	    <div class="r-list">
	    	<span style="float:left;font-size:12px;margin:9px 0 0 10px;">关键字：</span>
		    <input name="keywords" type="text" class="keyword" value="${keywords!''}">
		    <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
	    </div>        
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>

    <#if user_suggestion_page??>
        <#list user_suggestion_page.content as suggestion>
            <tr>
                <td class="comment">
                  <div class="title">
                    <span class="note">
                        <i>
                        	<#if ("username_" + suggestion.id)?eval??>
                        		<#assign username = ("username_" + suggestion.id)?eval>
                        		${username!''}
                        	</#if>		
                        </i>
                        <i>${suggestion.createTime!""}</i>
                        <i class="reply">
                            <a href="/Verwalter/user/suggestion/edit?id=${suggestion.id?c}">回复</a>
                        </i>
                    </span>
                    <b>
                        <span class="checkall" style="vertical-align:middle;">
                            <input id="listChkId" type="checkbox" name="listChkId" value="${suggestion_index}" >
                        </span>
                        <input type="hidden" name="listId" id="listId" value="${suggestion.id?c}">
                    </b>
                  </div>
                  <div class="ask">
                    ${suggestion.content!""}
                    <#if suggestion.isAnswered?? && suggestion.isAnswered>
                        <div class="answer">
                            <b>管理员回复：</b>${suggestion.answerContent!""}
                            <span class="time">${suggestion.answerTime!""}</span>
                        </div>
                    </#if>
                  </div>
                </td>
            </tr>
        </#list>
    </#if>
     
</tbody>
</table>

<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=user_suggestion_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>