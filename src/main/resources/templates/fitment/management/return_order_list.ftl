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
        .l-list{
            width: 5%;
            white-space:nowrap;
        }
        .r-list{
            width:95%;
        }
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
<form name="form1" method="post" action="/Verwalter/fitment/retuenOrder/list" id="form1">
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
            if(begain == "")
            {
                alert("请选择开始时间！");
                return;
            }
            if(type == 0)
            {
                location.href="/Verwalter/order/downdata?begindata="+ begain + "&enddata=" + end+"&diyCode="+diyCode+"&city="+city;
            }
            else if (type == 1)
            {
                location.href="/Verwalter/order/downdatagoods?begindata="+ begain + "&enddata=" + end+"&diyCode="+diyCode+"&city="+city;
            }else if(type==2){
                location.href="/Verwalter/order/downdatapay?begindata="+ begain + "&enddata=" + end+"&diyCode="+diyCode+"&city="+city;
            }
            else
            {
                return;
            }
            return;
            $.ajax({
                type: "GET",
                url: "/Verwalter/order/downdata",
                data: {"begindata":begain, "enddata":end},
                dataType: "json",
                success: function(data){
                    alert("成功");
                },
                error:function(data){
                    alert(data);
                }
            });
        }
    </script>
    <!--导航栏-->
    <div class="location">
        <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
        <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
        <i class="arrow"></i>
        <a><span>对外合作</span></a>
        <i class="arrow"></i>
        <a><span>装饰公司退单</span></a>
        <i class="arrow"></i>
	    <#if statusId??>
	        <#if 0==statusId>
	            <a><span>全部订单</span></a>
	        <#elseif 1==statusId>
	            <a><span>待确认订单</span></a>
	        <#elseif 2==statusId>
	            <a><span>待付款订单</span></a>
	        <#elseif 3==statusId>
	            <a><span>待发货订单</span></a>
	        <#elseif 4==statusId>
	            <a><span>待收货订单</span></a>
	        <#elseif 5==statusId>
	            <a><span>待评价订单</span></a>
	        <#elseif 6==statusId>
	            <a><span>已完成订单</span></a>
	        <#elseif 7==statusId>
	            <a><span>已取消订单</span></a>
	        <#elseif 8==statusId>
	            <a><span>用户删除订单</span></a>
	        </#if>
	    </#if>
    </div>
    <!--/导航栏-->
    <!--工具栏-->
    <div class="toolbar-wrap">
        <div id="floatHead" class="toolbar">
            <div class="l-list">
                <ul class="icon-list">
                <#--
                    <li>
                        <a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a>
                    </li>
                <#if statusId?? && 1==statusId>
                    <li>
                        <a onclick="return ExePostBack('btnConfirm','确认后将进入待发货状态，是否继续？');" class="save" href="javascript:__doPostBack('btnConfirm','')"><i></i><span>确认订单</span></a>
                    </li>
                <#elseif statusId?? && 7==statusId || statusId?? && 8==statusId>
                    <li>
                        <a onclick="return ExePostBack('btnDelete','删除后订单将无法恢复，是否继续？');" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除订单</span></a>
                    </li>
                </#if>
                </ul>
                -->
            </div>

            <div class="r-list">
                <div class="odiv">  <span class="span1">开始时间：</span>
                    <input name="orderStartTime" id="begain" type="text" value="${orderStartTime!"" }" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " />
                </div>
                <div class="odiv">   <span class="span1">结束时间：</span>
                    <input name="orderEndTime" id="end" type="text" value="${orderEndTime!"" }" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " />
                </div>

				<#--
                <div  class="odiv"><span class="span1">会员姓名：</span><input value="${realName!"" }" name="realName" type="text" class="input" value="${realName!"" }"></div>
                <div  class="odiv"><span class="span1">收货人电话：</span><input value="${shippingPhone!"" }" name="shippingPhone" type="text" class="input"></div>
                <div class="odiv" ><span class="span1">预约送货时间：</span><input value="${deliveryTime!"" }" name="deliveryTime" type="text" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" "></div>
                <div class="odiv" ><span class="span1">导购姓名：</span><input value="${sellerRealName!"" }" name="sellerRealName" type="text" class="input"></div>
                <div class="odiv" ><span class="span1">收货地址：</span><input value="${shippingAddress!"" }" name="shippingAddress" type="text" class="input"></div>
                <div class="odiv" ><span class="span1">会员电话：</span><input value="${userPhone!"" }" name="userPhone" type="text" class="input"></div>
                <div class="odiv" ><span class="span1">收货人姓名：</span><input value="${shippingName!"" }" name="shippingName" type="text" class="input"></div>
				-->

				<#--
                <div class="odiv" style="float:left;width:310px;"><span class="span1">订单状态：</span>
                   <div class="rule-single-select">
                       <select name="orderStatusId">
                           <option value="0" >请选择</option>                           
                           <option value="1" >待确定订单</option>
                           <option value="2" >代付款订单</option> 
                           <option value="3" >待发货订单</option>                           
                           <option value="4" >待收货订单</option>
                           <option value="5" >待评价订单</option>
                           <option value="6" >已完成订单</option>                           
                           <option value="7" >已取消订单</option>
                           <option value="8" >用户删除订单</option>
                           <option value="9" >订单退货中</option>                           
                           <option value="10" >退货确认</option>
                           <option value="11" >订单退货取消</option>  
                           <option value="12" >退货完成</option>                          
                       </select>
                   </div>
               </div>
               -->
               <!--
               <div class="odiv" ><span class="span1">中转仓库：</span><input name="tt" type="text" class="input"></div>
 <div class="odiv" ><span class="span1">实际送货时间：</span><input value="${sendTime!"" }" name="sendTime" type="text" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" "></div>
-->               
            <!-- <#if cityList?? && cityList?size gt 0 >-->
                <div class="odiv" style="float:left;width:310px;"><span class="span1">城市名称：</span>
                    <div class="rule-single-select">
                        <select name="city" id="city" onchange="javascript:setTimeout(__doPostBack('changeCity',''), 0)" >
                            <option value="" >请选择</option>
                            <#list cityList as city>
                                <option value="${city.cityName!'' }" <#if cityname?? && cityname==city.cityName!''>selected</#if> >${city.cityName!'' }</option>
                            </#list>
                        </select>
                    </div>
                </div>
           <!--  </#if>-->

            <#if diySiteList?? && diySiteList?size gt 0 >
                <div class="odiv" style="float:left;width:310px;"><span class="span1">门店名称：</span>
                    <div class="rule-single-select">
                        <select name="diyCode" id="diyCode" onchange="javascript:setTimeout(__doPostBack('changeDiy',''), 0)" >
                            <option value="" >请选择</option>
                            <#list diySiteList as diySite>
                                <option value="${diySite.storeCode!'' }" <#if diyCode?? && diyCode==diySite.storeCode!''>selected</#if> >${diySite.title!'' }</option>
                            </#list>
                        </select>
                    </div>
                </div>
            </#if>
            <#--
				<div class="odiv" style="float:left;width:310px;"><span class="span1">配送方式：</span>
                    <div class="rule-single-select">
                        <select name="deliveryType" id="deliveryType" onchange="javascript:setTimeout(__doPostBack('changeDeliveryType',''), 0)" >
                            <option value="" >请选择</option>
                            <option value="送货上门" <#if deliveryType?? && deliveryType=='送货上门'>selected</#if> >送货上门</option>
                            <option value="门店自提" <#if deliveryType?? && deliveryType=='门店自提'>selected</#if> >门店自提</option>
                        </select>
                    </div>
                </div>
            -->
				
                <div class="odiv" style="width:350px;float:right"><div style="float:left;"><span class="span1">订单号：</span><input name="keywords" type="text" class="input" value="${orderNumber!"" }">
                </div>
                    <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
                    <div>

                    </div></div>
                    
             <#if companyList?? && companyList?size gt 0 >      
				<div class="odiv" style="float:left;width:310px;"><span class="span1">装饰公司：</span>
                    <div class="rule-single-select">
                        <select name="company" id="company" onchange="javascript:setTimeout(__doPostBack('changeCompany',''), 0)" >
                            <option value="" >请选择</option>
                            <#list companyList as companys>
                                <option value="${companys.id?c }" <#if company?? && company!='' && company?eval==companys.id>selected</#if> >${companys.name }</option>
                            </#list>
                        </select>
                    </div>
                </div>
            </#if>
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
                  	  订单号
                </th>
                <th align="left" width="10%">
               	     会员账号
                </th>
                <th align="left" width="8%">
            	        会员名
                </th>
            <#--<th align="left" width="8%">
          	      收货人
            </th>-->
                <th align="left" width="10%">
              	      支付方式
                </th>
                <th align="left" width="10%">
             	       配送方式
                </th>
                <th width="8%">
            	        订单状态
                </th>
                <th width="10%">
          		          总金额
                </th>
                <th align="left" width="10%">
                  	下单时间
                </th>
                <th width="8%">
       			             装饰公司
                </th>
            </tr>

            <#if order_page??>
                <#list order_page.content as order>
                <tr>
                    <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${order_index}" >
                    </span>
                        <input type="hidden" name="listId" id="listId" value="${order.id?c}">
                    </td>
                    <td>
                        <a href="/Verwalter/order/edit?id=${order.id?c}&statusId=${statusId!'0'}">${order.orderNumber!""}<#if order.isCoupon??&&order.isCoupon>（券订单）</#if></a></td>
                <#-- 显示订单真正的用户   历史数据没有真实用户 -->
                    <#if order.realUserUsername??>
                        <td>${(order.realUserUsername!"")?replace("FIT","")}</td>
                        <td>${order.realUserRealName!""}</td>
                    <#else>
                        <td>${order.username!""}</td>
                        <td><#if name_map??>${name_map[order.username]!''}</#if></td>
                    </#if>
               
                    <td>${order.payTypeTitle!""}</td>
                    <td>${order.deliverTypeTitle!""}</td>
                    <td align="center">
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
                    </td>
                    <td align="center" width="10%"><font color="#C30000"><#if order??&&order.totalPrice??>${order.totalPrice?string("currency")}<#else>0.00</#if></font></td>
                    <td><#if order.orderTime??>${order.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
                    <td align="center">
                    
                    ${order.diySiteName}
                    </td>
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
