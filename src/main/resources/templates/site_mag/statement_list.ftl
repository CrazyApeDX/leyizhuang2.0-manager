<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>待付款订单</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
.r-list .odiv{
	float:left;
	margin-bottom:10px;
	width:310px;}
.r-list .odiv span.span1{
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
<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/statement/goodsInOut/list/${statusId!"0"}" id="form1">
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
    var city = $("#cityName").val();
    if(begain == "")
    {
        alert("请选择开始时间！");
        return;
    }
    window.open("/Verwalter/statement/downdata?begindata="+ begain + "&enddata=" + end+"&diyCode="+diyCode+"&cityName="+city+"&statusId="+type);
}

</script>
    <!--导航栏-->
    <div class="location">
        <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
        <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
        <i class="arrow"></i>
        <a><span>报表管理</span></a>
        <i class="arrow"></i>
            <#if statusId??>
                <#if 0==statusId>
                    <span>配送单出退货报表</span>
                <#elseif 1==statusId>
                    <span>代收款报表</span>
                <#elseif 2==statusId>
                    <span>收款报表</span>
                <#elseif 3==statusId>
                    <span>销售明细报表</span>
                <#elseif 4==statusId>
                    <span>退货报表</span>
                <#elseif 5==statusId>
                    <span>领用记录报表</span>
                <#elseif 6==statusId>
                    <span>自提单出退货报表</span>
                <#elseif 7==statusId>
                    <span>销量报表</span>
                <#elseif 8==statusId>
                    <span>欠款报表</span>
                <#elseif 10==statusId>
                    <span>活跃会员报表</span>
                <#elseif 11==statusId>
                    <span>配送考核报表</span>
                <#elseif 12==statusId>
                    <span>加盟商对账报表</span>    
                <#elseif 14==statusId>
                    <span>乐易装华润运费报表</span>
                <#elseif 15==statusId>
                    <span>乐易装华润运费报表(备用)</span>    
                </#if>
            </#if> 
    </div>
    <!--/导航栏-->
    <!--工具栏-->
    <div class="toolbar-wrap">
        <div id="floatHead" class="toolbar">

				<div class="r-list" style="width: 100%;">
					<div class="odiv">
						<span class="span1">开始时间：</span> <input name="orderStartTime"
							id="begain" type="text" value="${orderStartTime!"" }" class="input date"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})"
							datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/"
							errormsg="请选择正确的日期" sucmsg=" " />
							<input type="hidden" name="oldOrderStartTime" id="oldOrderStartTime" value="${oldOrderStartTime!"" }" /> 
					</div>
					<div class="odiv">
						<span class="span1">结束时间：</span> <input name="orderEndTime"
							id="end" type="text" value="${orderEndTime!"" }" class="input date"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})"
							datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/"
							errormsg="请选择正确的日期" sucmsg=" " />
							<input type="hidden" name="oldOrderEndTime" id="oldOrderEndTime" value="${oldOrderEndTime!"" }" /> 
					</div>
					
					<#if cityList?? && cityList?size gt 0 >
					<div class="odiv" style="float: left; width: 310px;">
						<span class="span1">城市名称：</span>
						<div class="rule-single-select">
							<select name="cityName" id="cityName" onchange="javascript:setTimeout(__doPostBack('changeCity',''), 0)">
								<option value="">请选择</option> 
								<#list cityList as city>
								<option value="${city.cityName }"<#if cityName?? && cityName==city.cityName>selected</#if> >${city.cityName }</option>
								</#list>
							</select>
						</div>
					</div>
					</#if>
							
					<#if diySiteList?? && diySiteList?size gt 0 && statusId != 11 >
					<div class="odiv" style="float: left; width: 310px;">
						<span class="span1">门店名称：</span>
						<div class="rule-single-select">
							<select name="diyCode" id="diyCode" onchange="javascript:setTimeout(__doPostBack('changeDiy',''), 0)">
								<option value="" >请选择</option> 
								<#list diySiteList as diySite>
								<option city="${diySite.city!'' }" value="${diySite.storeCode }"<#if diyCode?? && diyCode==diySite.storeCode>selected</#if> >${diySite.title }</option>
								</#list>
							</select>
						</div>
					</div>
					</#if> 
					
					<div class="odiv" style="width: 420px; float: right">
						<div style="float: left;">
							<span class="span1">订单号：</span><input name="keywords" type="text"
								class="input" value="${orderNumber!"" }">
						</div>
						<a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a> 
						<a style="color: black;" href="javascript:downloaddate(${statusId!'' });" class="a1">报表下载</a> 
					</div>

				</div>
			</div>
    <!--/工具栏-->
    <!--列表-->
    
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
<tbody>
    <tr class="odd_bg">
     <th align="center" width="10%">
            主单号
        </th>
        <#if statusId!=1>
        <th align="center" width="10%">
            分单号
        </th>
        </#if>
        <#if statusId!=1 && statusId!=2>
        <th align="left" width="10%">
            会员账号
        </th>
        <#else>
        <th align="left" width="10%">
            配送员账号
        </th>
        </#if>
        <th align="left" width="10%">
            配送方式
        </th>
        <th width="8%">
            订单状态
        </th>
        <#if statusId==1>
        <th width="10%">
            代收金额
        </th>
        <#else>
        <th width="10%">
            总金额
        </th>
        </#if>
        
        <th align="left" width="10%">
            下单时间
        </th>
        <th width="8%">
            门店
        </th>
        <th width="8%">
            城市
        </th>
    </tr>

    <#if order_page??>
        <#list order_page.content as order>
            <tr>
            	<#if statusId==4>
            	<td align="center">${order.returnNumber!""}</td>
            	<#else>
            	<td align="center">${order.mainOrderNumber!""}</td>
            	</#if>
           		<#if statusId!=1>
                <td align="center">${order.orderNumber!""}</td>
                </#if>
                <#if statusId!=1>
		        	<td>${order.username!""}</td>
		        <#else>
		        	<td>${order.deliveryPhone!""}</td>
		        </#if>
                <td>${order.deliverTypeTitle!""}</td>
                <td align="center">
                	<#if statusId!=4>
	                    <#if order.statusId??>
	                        <#if 1==order.statusId>
	                            <span>待确认订单</span>
	                        <#elseif 2==order.statusId>
	                            <span>待付款订单</span>
	                        <#elseif 3==order.statusId>
	                            <span>待发货订单</span>
	                        <#elseif 4==order.statusId>
	                            <span>待收货订单</span>
	                        <#elseif 5==order.statusId>
	                            <span>待评价订单</span>
	                        <#elseif 6==order.statusId>
	                            <span>已完成订单</span>
	                        <#elseif 7==order.statusId>
	                            <span>已取消订单</span>
	                        <#elseif 8==order.statusId>
	                            <span>用户删除订单</span>
	                        <#elseif 9==order.statusId>
	                            <span>订单退货中</span>
	                        <#elseif 10==order.statusId>
	                            <span>退货确认</span>
	                        <#elseif 11==order.statusId>
	                            <span>订单退货取消</span>
	                        <#elseif 12==order.statusId>
	                            <span>退货完成</span>
	                        </#if>
	                    </#if>
	                <#else>
	                    <#if order.statusId??>
	                        <#if 1==order.statusId>
	                            <span>确认退货单</span>
	                        <#elseif 2==order.statusId>
	                            <span>通知物流</span>
	                        <#elseif 3==order.statusId>
	                            <span>验货确认</span>
	                        <#elseif 4==order.statusId>
	                            <span>确认退款</span>
	                        <#elseif 5==order.statusId>
	                            <span>已完成</span>
	                        </#if>
	                    </#if>
                    </#if>
                </td>
                
                 <#if statusId==1>
                    <td align="center" width="10%"><font color="#C30000"><#if order??&& order.payPrice??>${order.payPrice?string("currency")}<#else>0.00</#if></font></td>
                 <#elseif statusId==2>
                    <td align="center" width="10%"><font color="#C30000"><#if order?? && order.totalGoodsPrice??>${order.totalGoodsPrice?string("currency")}<#else>0.00</#if></font></td>
                 <#elseif statusId==4>
                    <td align="center" width="10%"><font color="#C30000"><#if order?? && order.turnPrice??>${order.turnPrice?string("currency")}<#else>0.00</#if></font></td>
                 <#else>
                    <td align="center" width="10%"><font color="#C30000"><#if order??&&order.totalPrice??>${order.totalPrice?string("currency")}<#else>0.00</#if></font></td>
                 </#if>
                <td><#if order.orderTime??>${order.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
                <td align="center">
                    ${order.diySiteName!""}
                </td>
				<td align="center">${order.cityName!""}</td>
			</tr>
        </#list>
    </#if>
</tbody>
</table>
        
<!--/列表-->
<!--内容底部-->
<#if order_page??>
<#assign PAGE_DATA=order_page />
<#include "/site_mag/list_footer.ftl" />
</#if>
<!--/内容底部-->
</form>


</body></html>
