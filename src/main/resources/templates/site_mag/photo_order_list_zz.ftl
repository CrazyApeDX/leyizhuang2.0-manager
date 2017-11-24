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
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<style>
table{
        table-layout:fixed;
}
td {
      white-space:nowrap;
      overflow:hidden;
      text-overflow: ellipsis;
}
</style>

<script type="text/javascript">
    $(function () {
    });
</script>
</head>

<body class="mainbody">
<div style="left: 0px; top: 0px; visibility: hidden; position: absolute;" class=""><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1"  action="/Verwalter/photo/order/list/zz" id="form1">
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
        theForm.submit();
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
    <div class="l-list">
      <ul class="icon-list">
      </ul>
      <div class="menu-list">
        <div class="rule-single-select">
            <select name="status" onchange="javascript:setTimeout(__doPostBack('status', ''), 0)">
                <option <#if status??><#else>selected="selected"</#if> value="">订单状态</option>
                
                <option value="WAITING" <#if status?? && "WAITING"==status>selected="selected"</#if> >待处理</option>
            
            	<option value="ACTIONING" <#if status?? && "ACTIONING"==status>selected="selected"</#if> >处理中</option>
            
            	<option value="FINISHING" <#if status?? && "FINISHING"==status>selected="selected"</#if> >已完成</option>
                   
                <option value="INVALID" <#if status?? && "INVALID"==status>selected="selected"</#if> >已作废</option> 
            </select>
        </div>
        <div class="rule-single-select">
            <select name="cityInfo" onchange="javascript:setTimeout(__doPostBack('cityInfo', ''), 0)">
            	<!--
                <option <#if cityInfo??><#else>selected="selected"</#if> value="">城市</option>-->
            
            	<option value="郑州市" <#if cityInfo?? && "郑州市"==cityInfo>selected="selected"</#if> >郑州市</option>
                    
            </select>
        </div>
        
      </div>
    </div>
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${keywords!''}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('lbtnSearch','')">查询</a>
      <a id="lbtnViewImg" title="图像列表视图" class="img-view" href="javascript:__doPostBack('lbtnViewImg','')"></a>
      <a id="lbtnViewTxt" title="文字列表视图" class="txt-view" href="javascript:__doPostBack('lbtnViewTxt','')"></a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--文字列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
<tbody>
    <tr class="odd_bg">
        <th align="left" width="5%">序号</th>
        <th align="left" width="5%">城市</th>
        <th align="left" width="10%">门店</th>
        <th align="left" width="5%">客户名</th>
        <th align="left" width="10%">手机号名</th>
        <th align="left" width="10%">创建时间</th>
        <th align="left" width="10%">开始处理时间</th>
        <th align="left" width="10%">处理结束时间</th>
        <th align="left" width="20%">订单号</th>
        <th align="left" width="5%">状态</th>
        <th width="8%">操作</th>
    </tr>
    
    <#if photoOrderPage?? && photoOrderPage.content??>
    <#list photoOrderPage.content as order>
        <tr>
            <td>${order.id?c}</td>
            <td>${order.city!""}</td>
            <td>${order.diySiteName!""}</td>
            <td>${order.userRealName!""}</td>
            <td>${order.username!""}</td>
            <td>${order.createTime!""}</td>
            <td>${order.startActionTime!""}</td>
            <td>${order.finishTime!""}</td>
            <td>${order.orderNumber!""}</td>
            <td><#if order.status?? && order.status == 'WAITING'>
            		<label style="color: red">待处理</label>
            	<#elseif order.status?? && order.status == 'ACTIONING'>
            		处理中
            	<#elseif order.status?? && order.status == 'FINISHING'>
            		已完成
            	<#elseif order.status?? && order.status == 'INVALID'>
            		已作废
            	</#if>
            </td>
            <td align="center">
                <#if order.status?? && order.status == 'WAITING'>
            		<a href="/Verwalter/photo/order/toprocess/0?id=${order.id?c}" style="color: red">处理</a>
            	<#elseif order.status?? && order.status == 'ACTIONING'>
            		<a href="/Verwalter/photo/order/toprocess/1?id=${order.id?c}">查看</a>
            	<#elseif order.status?? && order.status == 'FINISHING'>
            		<a href="/Verwalter/photo/order/toprocess/1?id=${order.id?c}">查看</a>
            	<#elseif order.status?? && order.status == 'INVALID'>
            		<a href="/Verwalter/photo/order/toprocess/1?id=${order.id?c}">查看</a>
            	</#if>
            </td>
        </tr>
    </#list>
    </#if>
</tbody>
</table>

<!--/文字列表-->

<!--图片列表-->

<!--/图片列表-->

<!--内容底部-->
<#assign PAGE_DATA=photoOrderPage />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>
</body>
</html>