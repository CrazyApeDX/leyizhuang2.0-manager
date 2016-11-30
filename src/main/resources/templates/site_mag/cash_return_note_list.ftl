<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>用户管理</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
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

</script>
<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" id="theForm" method="post" action="/Verwalter/cash/return/note/list" id="form1">
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
  <span>订单管理</span>
  <i class="arrow"></i>
  <span>退款申请列表</span>  
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
      <ul class="icon-list">
      </ul>
      <#--
      <div class="menu-list">      
        <div class="rule-single-select single-select">
        <select name="type" onchange="javascript:setTimeout(__doPostBack('type',''), 0)" style="display: none;">
            <option <#if !type??>selected="selected"</#if> value="">变更类型</option>
            <option <#if type?? && type==0>selected="selected"</#if> value="0">充值</option>
            <option <#if type?? && type==1>selected="selected"</#if> value="1">提现</option>
            <option <#if type?? && type==3>selected="selected"</#if> value="3">消费</option>
            <option <#if type?? && type==4>selected="selected"</#if> value="4">退款</option>
        </select>
        </div>
      </div>
      -->
      <#--
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
           	-->
			<div class="menu-list">
              	<div class="rule-single-select">
                   <select name="isOperatedNum" onchange="javascript:setTimeout(__doPostBack('changeOperate',''), 0)">
                   		<option <#if isOperatedNum??&&isOperatedNum==0>selected="selected"</#if> value="0">是否打款</option>
						<option <#if isOperatedNum??&&isOperatedNum==1>selected="selected"</#if> value="1">已打款</option>
						<option <#if isOperatedNum??&&isOperatedNum==2>selected="selected"</#if> value="2">未打款</option>
                   </select>
           		</div>
           	</div>
    </div>
    <div class="r-list">
      <input name="keywords" placeholder="用户/主单/分单/退货单" type="text" class="keyword" value="${keywords!""}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
  	<th align="center" width="10%">申请用户</th>
    <th align="left" width="10%">申请时间</th>
    <th align="center" width="10%">涉及主单</th>
    <th align="center" width="10%">分单号</th>
    <th align="center" width="10%">退货单号</th>
    <th align="center" width="10%">打款金额</th>
    <th align="left" width="10%">打款方式</th>
    <th align="left" width="10%">是否处理</th>
    <th align="left" width="10%">完成时间</th>
    <th align="center" width="10%">操作</th>
  </tr>

    <#if cashReturnNote_page??>
        <#list cashReturnNote_page.content as item>
            <tr>
            	<td align="center">${item.username!''}</td>
            	<td align="left"><#if item.createTime??>${item.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
            	<td align="left" style="color:green;">${item.mainOrderNumber!''}</td>
            	<td align="left" style="color:red;">${item.orderNumber!''}</td>
            	<td align="left" style="color:blue;">${item.returnNoteNumber!''}</td>
            	<td align="center"><#if item.money??>${item.money?string("0.00")}</#if></td>
            	<td align="left">${item.typeTitle!''}</td>
            	<td align="left"><#if item.isOperated><font color="green">已处理</font><#else><font color="red">未处理</font></#if></td>
            	<td align="left"><#if item.finishTime??>${item.finishTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
            	<td align="center">
            		<#if item.isOperated>
            			<a>已经打款</a>
            		<#else>
            			<a href="javascript:showConfirm('${item.orderNumber!''}','${item.mainOrderNumber!''}',${item.id?c});">打款</a>
            		</#if>
            	</td>
        </#list>
    </#if>
     
</tbody>
</table>

<!--列表-->

<!--内容底部-->
<#assign PAGE_DATA=cashReturnNote_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>
<#--
<form id="upload" action="/Verwalter/upload" enctype="multipart/form-data" method="post">
<input type="file" onchange="upload()" name="Filedata" id="clickFile">
</form> 
-->

</body>
<script>
	function showConfirm(orderNumber,mainOrderNumber,id){
		$.dialog.confirm("订单" + orderNumber + "（主单号：" + mainOrderNumber + "）是否确认已经打款",function(check){
			if(check){
				__doPostBack('returnMoney',id)
			}
		});
	}
</script>
</html>