<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>优惠券</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/coupon/distributed/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
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
function downloaddate()
{
    var begain = $("#begain").val();
    var end = $("#end").val();
    var diyCode = $("#diyCode").val();
    var city = $("#cityId").val();
    if(begain==""){
    	$.dialog.confirm("将导出全部数据,请确认导出?", function () {
    		location.href="/Verwalter/coupon/downdata?begindata="+ begain + "&enddata=" + end + "&diyCode=" + diyCode+ "&cityId=" + city;
    		return;
        });
    }else{
    	location.href="/Verwalter/coupon/downdata?begindata="+ begain + "&enddata=" + end + "&diyCode=" + diyCode+ "&cityId=" + city;
    }
}
function failureMsg(msg,objId){
	$.dialog.confirm(msg, function () {
		 __doPostBack(objId, '');
    });
	return false;
}
</script>
<!--导航栏-->
<div class="location">
  <a href="/Verwalter/coupon/distributed/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>优惠券</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
        <#--
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/coupon/edit"><i></i><span>新增</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
      </ul>
        -->
      <ul class="icon-list">    
      	
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>      
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        <li><a onclick="return failureMsg('失效记录后不可恢复，您确定吗？','btnFailure')" id="btnFailure" class="del" href="javascript:__doPostBack('btnFailure','')"><i></i><span>失效</span></a></li>
      </ul>
      <div class="menu-list">
      		<div class="rule-single-select">
                        <select name="typeId" onchange="javascript:setTimeout(__doPostBack('changeType',''), 0)">
                            <!-- 修改前
                            <option value="0" <#if !typeId?? || typeId==0>selected="selected"</#if>>所有类型</option>
                            <#if couponType_list??>
                                <#list couponType_list as cou>
                                    <option value="${cou.id?c}" <#if typeId?? && typeId==cou.id>selected="selected"</#if>>${cou.title!''}</option>
                                </#list>
                            </#if> 
                            -->
                            <option value="0" <#if !typeId?? || typeId==0>selected="selected"</#if>>选择劵类型</option>
                             <option value="3" <#if !typeId?? || typeId==3>selected="selected"</#if>>产品券</option>                           
                            <option value="2" <#if !typeId?? || typeId==2>selected="selected"</#if>>产品现金券</option>
                            <option value="1" <#if !typeId?? || typeId==1>selected="selected"</#if>>通用现金券</option>                                                        
                     	</select>
            </div>
            <div class="rule-single-select">
                        <select name="isUsed" onchange="javascript:setTimeout(__doPostBack('',''), 0)">
                            <option value="0" <#if !isUsed?? || isUsed==0>selected="selected"</#if>>是否使用</option>                           
                            <option value="1" <#if !isUsed?? || isUsed==1>selected="selected"</#if>>已使用</option>
                            <option value="2" <#if !isUsed?? || isUsed==2>selected="selected"</#if>>未使用</option>                             
                        </select>
            </div>
            
      </div>
    </div>
      <div class="r-list">
              <input name="keywords" type="text" class="keyword" value="<#if keywords??>${keywords!''}</#if>">
                <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
      </div>
      <div style="clear: both;">
                <#if cityList?? && cityList?size gt 0 >
            	<div class="odiv" style="float:left;width:250px;"><span class="span1">城市名称：</span>
                	<div class="rule-single-select">
                        <select name="cityId" id="cityId">
                        <option value="0" >请选择</option>      
                        <#list cityList as city>
                        	<option value="${city.id }" >${city.cityName }</option>
                        </#list>
                        </select>
            		</div>
            	</div>
            	</#if> 
            	<#if diySiteList?? && diySiteList?size gt 0 >
               <div class="odiv" style="float:left;width:250px;"><span class="span1">门店名称：</span>
                	<div class="rule-single-select">
                        <select name="diyCode" id="diyCode">
                        <option value="" >请选择</option>      
                        <#list diySiteList as diySite>
                        	<option value="${diySite.title }" >${diySite.title }</option>
                        </#list>
                        </select>
            		</div>
            	</div>
            	</#if> 
                                                领取时间:
                <input name="orderStartTime" id="begain" type="text" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " />
                <input name=orderEndTime id="end" type="text" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " />
               
                <a style="color:black;line-height: 30px;margin-left: 20px;" href="javascript:downloaddate();">领用记录报表下载</a>
      </div>
   
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th width="8%">选择</th>
    <th align="left"> 优惠券名称
                    </th>
    <#--<th align="left" width="10%"><div class="rule-single-select">
                        <select name="diysiteId" onchange="javascript:setTimeout(__doPostBack('changeDiysite',''), 0)">
                            <option value="0" <#if !diysiteId?? || diysiteId==0>selected="selected"</#if>>所有同盟店</option>
                            <#if tdDiySite_list??>
                                <#list tdDiySite_list as diysite>
                                    <option value="${diysite.id?c}" <#if diysiteId?? && diysiteId==diysite.id>selected="selected"</#if>>${diysite.title!''}</option>
                                </#list>
                            </#if>                             
                        </select>
                    </div>
                    </th>-->
    <#--<th align="left" width="15%">种类</th>               -->
    <th align="left" width="15%">账号</th>
    <th align="left" width="15%">使用时间</th>
    <#--<th align="left" width="11%">车牌</th>-->
    <th align="left" width="15%">领取时间</th>
    <th align="left" width="17%">有效截止时间</th>
    <th align="left" width="8%">是否失效</th>
    <th align="left" width="8%">是否使用
                    </th>
    <#--
    <th align="left" width="12%">排序</th>
    <th width="10%">操作</th>
    -->
  </tr>

    <#if coupon_page??>
        <#list coupon_page.content as item>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${item_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${item.id?c}">
                </td>
                <td>${item.typeTitle!""}</td>
                <#--<td>
                
                	<#list couponType_list as cou>
                       <#if item.typeCategoryId?? && item.typeCategoryId==cou.id>${cou.title!''}</#if>
                    </#list>
	                
                </td>-->
                <#--<td>${item.diySiteTitle!""}</td>-->
                <td>${item.username!""}</td>
                <td><#if item.useTime??>${item.useTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
                <#--<td>${item.carCode!""}</td>-->
                <td><#if item.getTime??>${item.getTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
                <td><#if item.expireTime??>${item.expireTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
               <td>
		               <#if item.isOutDate??>
		                        <#if item.isOutDate>
		                            已失效
		                        <#else>
		                           未失效 
		                        </#if>
		                    </#if>
               </td>
               <td>
                    <#if item.isUsed??>
                        <#if item.isUsed>
                            已使用
                        <#else>
                           未使用 
                        </#if>
                    </#if>
                </td>
                <#--
                <td><input name="listSortId" type="text" value="${item.sortId!""}" class="sort" onkeydown="return checkNumber(event);"></td>
                <td align="center">
                    <a href="/Verwalter/coupon/edit?id=${item.id}">修改</a>
                </td>
                -->
              </tr>
        </#list>
    </#if>
 
  </tbody>
</table>

<!--/列表-->

<!--内容底部-->
<#if coupon_page??>
	<#assign PAGE_DATA=coupon_page />
	<#include "/site_mag/list_footer.ftl" />
</#if>
<!--/内容底部-->
</form>

</body></html>