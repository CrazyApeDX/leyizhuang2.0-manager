<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
            html,body{width:100%;height: 100%;}

        </style>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="copyright" content="" />
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <meta charset="utf-8">
        <title>我的订单</title>
        
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/xiuxiu.css"/>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/index.js" type="text/javascript"></script>
        <script src="/client/js/user_order.js" type="text/javascript"></script>
        <script src="/client/js/Validform_v5.3.2_min.js" type="text/javascript"></script>

    </head>
    <script type="text/javascript">
    <!--
    $(document).ready(function(){
            $("#form1").Validform({
                tiptype:4, 
                ajaxPost:true,
                callback:function(data){
                    warning(data.message);
                    if(data.code==0)
                    {
                        win_no();
                        window.location.reload();
                    }
                }
            });
     })
     -->
     
          function order_return(id){
            var he = ($(window).height() - $('.turn_div div').height())/2 - 50;
            $('.turn_div div').css({marginTop:he});   
            $('.turn_div').fadeIn(600);
            $("#orderId").attr("value",id);
        };
        
        function win_no_return(){  
            $('.turn_div').fadeOut(600);
        };
        
        <#--
	        function win_no_turn(){  
	            $('.turn_div').fadeOut(600);
	        };
        -->
        
        window.onload = function(){
            footer();
        }
        
        <#-- 模糊查询订单的方法 -->
        function searchOrder(){
            <#-- 获取查询关键词 -->
            var keywords = $("#keywords").val();
            if("" === keywords){
                return;
            }
            <#-- 开启等待图标 -->
            wait();
            $.ajax({
                url : "/user/order/search",
                type : "post",
                timeout : 20000,
                data:{
                    keywords : keywords
                },
                error:function(){
                    close(1);
                    warning("亲，您的网速不给力啊");
                },
                success:function(res){
                    $("#user_all_order").html(res);
                    $("#all").click();
                    close(1);
                }
            });
        }
        
        function checkReturn(id) {
        	if (id) {
        		wait();
        		
        		$.ajax({
        			url: '/user/can/return',
        			method: 'POST',
        			data: {
        				id: id
        			},
        			success: function(res) {
        				if (0 === res.status) {
        					window.location.href = '/user/order/return?orderId=' + id;
        				} else {
        					close(1);
        					warning('您还有欠款未还清，不能够退货');
        				}
        			},
        			error : function() {
        				warning("亲，您的网速不给力啊");
        				close(1);
        			}
        		});
        	}
        }
    </script>
    <!--  <style type="text/css"> 
    #slideDown{margin-top: 0;width: 100%;} 
         #slideDown1,#slideDown2{width: 100%;height: 70px;;background: #e9f4f7;display: none;} 
        #slideDown1{height: 20px;} 
        #slideDown1>p,#slideDown2>p{margin: 20px auto;text-align:center;font-size: 14px;color: #37bbf5;} 
    </style>  -->
    <body style="background: #f3f4f6;">
    <div class="turn_div">
        <form id="form1" action="/user/order/return" method="post">
          <input type="hidden" value="" id="orderId" name="id">
        <div>                   
            <p id="title">退货原因</p>
            <textarea name="remark"></textarea>
            <span>
              <#--
                    <input type="hidden" name="turnType" id="" value="1" />                   
                <section>
                    <input type="radio" name="turnType" id="" value="1" checked="checked"/>
                    <label>到店退货</label>
                </section>
                <section>
                    <input type="radio" name="turnType" id="" value="2" />                   
                    <label>物流取货</label>
                </section>
                <input onclick="win_no();" type="button" name="" id="" value="否" />
                -->
                <input type="submit" name="" id="" value="是" />
                <input onclick="javascript:win_no_return();" type="button" value="否" />
            </span>             
        </div>
        </form>
    </div>
        <#-- 引入公共confirm窗口 -->
        <#include "/client/common_confirm.ftl">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <#-- 引入公共购物方式选择滑动窗口 -->
        <#include "/client/common_shopping_type.ftl">
        <div id="content">
            <div class="sec_header">
                <a class="back" href="/user"></a>
                <p>我的订单</p>             
            </div>
            
            <section class="my_order">
                <input id="typeId" type="hidden" value="${typeId!'0'}">
                <div class="searchbox bgc-f3f4f6 bdt">
                    <input type="text" id="keywords" placeholder="订单号/会员信息/收货人信息">
                    <a href="javascript:searchOrder();"></a>
                </div>          
                <!-- 订单管理 -->
                <ul class="order-nav">
                    <li id="all"><a>全部</a></li>
                    <li id="unpayed"><a>待付款</a></li>
                    <li id="undeliver"><a>待发货</a></li>
                    <li id="unsignin"><a>待收货</a></li>
                    <li id="uncommend"><a>待评价</a></li>
                </ul>
                
                <!-- 订单分类 -->
                <article class="orders-species"> 
                    <div id="user_all_order">
                        <#include "/client/user_all_order.ftl">
                    </div>
                    
                    <#if undeliver_order_list??>
                        <div id="undeliver_orders" class="some_orders">
                            <#list undeliver_order_list as item>
                                <ol class="order-list">
                                    <li class="li1">
                                        <label>订单号：<span>${item.orderNumber!''}</span></label>
                                        <div class="species">
                                            <#if item.statusId??>
                                                <#switch item.statusId>
                                                    <#case 2>待付款<#break>
                                                    <#case 3>待发货<#break>
                                                    <#case 4>待签收<#break>
                                                    <#case 5>待评价<#break>
                                                    <#case 6>已完成<#break>
                                                    <#case 7>已取消<#break>
                                                    <#case 9>退货中<#break>
                                                    <#case 10>退货确认<#break>
                                                    <#case 11>退货取消<#break>
                                                    <#case 12>退货完成<#break>
                                                </#switch>
                                            </#if>
                                        </div>
                                    </li>
                                    <#if item.orderGoodsList??>
                                        <#list item.orderGoodsList as goods>
                                            <li class="li2">
                                                <div class="img"><img src="${goods.goodsCoverImageUri!''}" alt="产品图片"></div>
                                                <div class="product-info">
                                                    <div class="div1">${goods.goodsTitle!''}</div>
                                                    <div class="div2">￥<span><#if goods.price??>${goods.price?string("0.00")}<#else>0.00</#if></span>&nbsp;&nbsp;<label>数量：<span>${goods.quantity!'0'}</span></label></div>
                                                </div>
                                            </li>
                                        </#list>
                                    </#if>
                                    <div style="width:80%;margin-left:3%;line-height:30px">
                                        <div>订单总额：<font style="color:red;">￥<#if item.totalPrice??>${item.totalPrice?string("0.00")}</#if></font></div>
                                        <div>下单时间：<#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
                                    </div>
                                    <div class="li3">
                                        <#if item.statusId??>
                                            <#switch item.statusId>
                                                <#case 2>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--<#if !(item.isCoupon??&&item.isCoupon)>-->
													<#if (!item.cashPay?? || item.cashPay == 0)&&(!item.posPay?? || item.posPay == 0)&&(!item.backOtherPay?? || item.backOtherPay==0)>
                                                    	<a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');">取消订单</a>
                                                    </#if>
                                                    <a href="/order?id=${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
                                                    <#--
                                                    <#if user_type??>
                                                        <#if user_type!=1>
                                                        <a href="/order?id=${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
                                                        </#if>
                                                    </#if>
                                                    -->
                                                <#break>
                                                <#case 3>
                                                	
                                                    <#--<#if !(item.isCoupon??&&item.isCoupon)>-->
													<#if (!item.cashPay?? || item.cashPay == 0)&&(!item.posPay?? || item.posPay == 0)&&(!item.backOtherPay?? || item.backOtherPay==0)>
                                                    	<a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');">取消订单</a>
                                                    </#if>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                <#break>
                                                <#case 4>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#if item.deliverTypeTitle!='门店自提'>
                                                    <a href="/user/order/map?oid=${item.id?c}">物流详情</a>
                                                    </#if>
                                                    <!-- <a href="javascript:win_yes('是否确定收货？','confirmAccipt(${item.id?c})');">确认收货</a> -->
                                                <#break>
                                                <#case 5>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#if (!item.isRefund?? || !item.isRefund) && item.orderGoodsList?size gt 0 >
                                                    	<#if !(item.isCoupon??&&item.isCoupon)>
                                                    		<a href="javascript:checkReturn(${item.id?c});">申请退货</a>
                                                		<#else>
                                                			<a href="/coupon/return?orderId=${item.id?c}">申请退货</a>
                                                    	</#if>
                                                    	<a href="">立即评价</a>
                                                    </#if>
                                                <#break>
                                                <#case 6>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--
                                                    <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                                                    -->
                                                <#break>
                                                <#case 7>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--
                                                    <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                                                    -->
                                                <#break>
                                            </#switch>
                                        </#if>
                                    </div>
                                </ol>
                            </#list>
                        </div>
                    </#if>
                    
                    <#if unpayed_order_list??>
                        <div id="unpayed_orders"  class="some_orders">
                            <#list unpayed_order_list as item>
                                <ol class="order-list">
                                    <li class="li1">
                                        <label>订单号：<span>${item.orderNumber!''}</span></label>
                                        <div class="species">
                                            <#if item.statusId??>
                                                <#switch item.statusId>
                                                    <#case 2>待付款<#break>
                                                    <#case 3>待发货<#break>
                                                    <#case 4>待签收<#break>
                                                    <#case 5>待评价<#break>
                                                    <#case 6>已完成<#break>
                                                    <#case 7>已取消<#break>
                                                    <#case 9>退货中<#break>
                                                    <#case 10>退货确认<#break>
                                                    <#case 11>退货取消<#break>
                                                    <#case 12>退货完成<#break>
                                                </#switch>
                                            </#if>
                                        </div>
                                    </li>
                                    <#if item.orderGoodsList??>
                                        <#list item.orderGoodsList as goods>
                                            <li class="li2">
                                                <div class="img"><img src="${goods.goodsCoverImageUri!''}" alt="产品图片"></div>
                                                <div class="product-info">
                                                    <div class="div1">${goods.goodsTitle!''}</div>
                                                    <div class="div2">￥<span><#if goods.price??>${goods.price?string("0.00")}<#else>0.00</#if></span>&nbsp;&nbsp;<label>数量：<span>${goods.quantity!'0'}</span></label></div>
                                                </div>
                                            </li>
                                        </#list>
                                    </#if>
                                    <div style="width:80%;margin-left:3%;line-height:30px">
                                        <div>订单总额：<font style="color:red;">￥<#if item.totalPrice??>${item.totalPrice?string("0.00")}</#if></font></div>
                                        <div>下单时间：<#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
                                    </div>
                                    <div class="li3">
                                        <#if item.statusId??>
                                            <#switch item.statusId>
                                                <#case 2>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    
                                                    <#--<#if !(item.isCoupon??&&item.isCoupon)>-->
													<#if !item.cashPay??&&!item.posPay??&&!item.backOtherPay??>
                                                    	<a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');">取消订单</a>
                                                    </#if>
                                                    <a href="/order?id=${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
                                                    <#--
                                                    <#if user_type??>
                                                        <#if user_type!=1>
                                                        <a href="/order?id=${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
                                                        </#if>
                                                    </#if>
                                                    -->
                                                <#break>
                                                <#case 3>
                                                	<#--<#if !(item.isCoupon??&&item.isCoupon)>-->
													<#if !item.cashPay??&&!item.posPay??&&!item.backOtherPay??>
                                                    	<a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');">取消订单</a>
                                                    </#if>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                <#break>
                                                <#case 4>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#if item.deliverTypeTitle!='门店自提'>
                                                    <a href="/user/order/map?oid=${item.id?c}">物流详情</a>
                                                    </#if>
                                                    <!-- <a href="javascript:win_yes('是否确定收货？','confirmAccipt(${item.id?c})');">确认收货</a> -->
                                                <#break>
                                               	<#case 5>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#if (!item.isRefund?? || !item.isRefund) && item.orderGoodsList?size gt 0 >
                                                    	<#if !(item.isCoupon??&&item.isCoupon)>
                                                    		<a href="javascript:checkReturn(${item.id?c});">申请退货</a>
                                                		<#else>
                                                			<a href="/coupon/return?orderId=${item.id?c}">申请退货</a>
                                                    	</#if>
                                                    	<a href="">立即评价</a>
                                                    </#if>
                                                <#break>
                                                <#case 6>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--
                                                    <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                                                    -->
                                                <#break>
                                                <#case 7>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--
                                                    <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                                                    -->
                                                <#break>
                                            </#switch>
                                        </#if>
                                    </div>
                                </ol>
                            </#list>
                        </div>
                    </#if>
                    
                    <#if unsignin_order_list??>
                        <div id="unsignin_orders"  class="some_orders">
                            <#list unsignin_order_list as item>
                                <ol class="order-list">
                                    <li class="li1">
                                        <label>订单号：<span>${item.orderNumber!''}</span></label>
                                        <div class="species">
                                            <#if item.statusId??>
                                                <#switch item.statusId>
                                                    <#case 2>待付款<#break>
                                                    <#case 3>待发货<#break>
                                                    <#case 4>待签收<#break>
                                                    <#case 5>待评价<#break>
                                                    <#case 6>已完成<#break>
                                                    <#case 7>已取消<#break>
                                                    <#case 9>退货中<#break>
                                                    <#case 10>退货确认<#break>
                                                    <#case 11>退货取消<#break>
                                                    <#case 12>退货完成<#break>
                                                </#switch>
                                            </#if>
                                        </div>
                                    </li>
                                    <#if item.orderGoodsList??>
                                        <#list item.orderGoodsList as goods>
                                            <li class="li2">
                                                <div class="img"><img src="${goods.goodsCoverImageUri!''}" alt="产品图片"></div>
                                                <div class="product-info">
                                                    <div class="div1">${goods.goodsTitle!''}</div>
                                                    <div class="div2">￥<span><#if goods.price??>${goods.price?string("0.00")}<#else>0.00</#if></span>&nbsp;&nbsp;<label>数量：<span>${goods.quantity!'0'}</span></label></div>
                                                </div>
                                            </li>
                                        </#list>
                                    </#if>
                                    <div style="width:80%;margin-left:3%;line-height:30px">
                                        <div>订单总额：<font style="color:red;">￥<#if item.totalPrice??>${item.totalPrice?string("0.00")}</#if></font></div>
                                        <div>下单时间：<#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
                                    </div>
                                    <div class="li3">
                                        <#if item.statusId??>
                                            <#switch item.statusId>
                                                <#case 2>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--<#if !(item.isCoupon??&&item.isCoupon)>-->
                                                    <#if !item.cashPay??&&!item.posPay??&&!item.backOtherPay??>
                                                    	<a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');">取消订单</a>
                                                    </#if>
                                                    <a href="/order?id=${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
                                                    <#--
                                                    <#if user_type??>
                                                        <#if user_type!=1>
                                                        <a href="/order?id=${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
                                                        </#if>
                                                    </#if>
                                                    -->
                                                <#break>
                                                <#case 3>
                                                	<#--<#if !(item.isCoupon??&&item.isCoupon)>-->
                                                	<#if !item.cashPay??&&!item.posPay??&&!item.backOtherPay??>
                                                    	<a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');">取消订单</a>
                                                    </#if>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                <#break>
                                                <#case 4>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#if item.deliverTypeTitle!='门店自提'>
                                                    <a href="/user/order/map?oid=${item.id?c}">物流详情</a>
                                                    </#if>
                                                    <!-- <a href="javascript:win_yes('是否确定收货？','confirmAccipt(${item.id?c})');">确认收货</a> -->
                                                <#break>
                                                <#case 5>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#if (!item.isRefund?? || !item.isRefund) && item.orderGoodsList?size gt 0 >
                                                    	<#if !(item.isCoupon??&&item.isCoupon)>
                                                    		<a href="javascript:checkReturn(${item.id?c});">申请退货</a>
                                                		<#else>
                                                			<a href="/coupon/return?orderId=${item.id?c}">申请退货</a>
                                                    	</#if>
                                                    	<a href="">立即评价</a>
                                                    </#if>
                                                <#break>
                                                <#case 6>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--
                                                    <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                                                    -->
                                                <#break>
                                                <#case 7>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--
                                                    <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                                                    -->
                                                <#break>
                                            </#switch>
                                        </#if>
                                    </div>
                                </ol>
                            </#list>
                        </div>
                    </#if>
                    
                    <#if uncomment_order_list??>
                        <div id="uncomment_orders"  class="some_orders">
                            <#list uncomment_order_list as item>
                                <ol class="order-list">
                                    <li class="li1">
                                        <label>订单号：<span>${item.orderNumber!''}</span></label>
                                        <div class="species">
                                            <#if item.statusId??>
                                                <#switch item.statusId>
                                                    <#case 2>待付款<#break>
                                                    <#case 3>待发货<#break>
                                                    <#case 4>待签收<#break>
                                                    <#case 5>待评价<#break>
                                                    <#case 6>已完成<#break>
                                                    <#case 7>已取消<#break>
                                                    <#case 9>退货中<#break>
                                                    <#case 10>退货确认<#break>
                                                    <#case 11>退货取消<#break>
                                                    <#case 12>退货完成<#break>
                                                </#switch>
                                            </#if>
                                        </div>
                                    </li>
                                    <#if item.orderGoodsList??>
                                        <#list item.orderGoodsList as goods>
                                            <li class="li2">
                                                <div class="img"><img src="${goods.goodsCoverImageUri!''}" alt="产品图片"></div>
                                                <div class="product-info">
                                                    <div class="div1">${goods.goodsTitle!''}</div>
                                                    <div class="div2">￥<span><#if goods.price??>${goods.price?string("0.00")}<#else>0.00</#if></span>&nbsp;&nbsp;<label>数量：<span>${goods.quantity!'0'}</span></label></div>
                                                </div>
                                            </li>
                                        </#list>
                                    </#if>
                                    <div style="width:80%;margin-left:3%;line-height:30px">
                                        <div>订单总额：<font style="color:red;">￥<#if item.totalPrice??>${item.totalPrice?string("0.00")}</#if></font></div>
                                        <div>下单时间：<#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
                                    </div>
                                    <div class="li3">
                                        <#if item.statusId??>
                                            <#switch item.statusId>
                                                <#case 2>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--<#if !(item.isCoupon??&&item.isCoupon)>-->
                                                    <#if !item.cashPay??&&!item.posPay??&&!item.backOtherPay??>
                                                    	<a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');">取消订单</a>
                                                    </#if>
                                                    <a href="/order?id=${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
                                                    <#--
                                                    <#if user_type??>
                                                        <#if user_type!=1>
                                                        <a href="/order?id=${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
                                                        </#if>
                                                    </#if>
                                                    -->
                                                <#break>
                                                <#case 3>
                                                	<#--<#if !(item.isCoupon??&&item.isCoupon)>-->
                                                	<#if !item.cashPay??&&!item.posPay??&&!item.backOtherPay??>
                                                    	<a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');">取消订单</a>
                                                    </#if>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                <#break>
                                                <#case 4>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#if item.deliverTypeTitle!='门店自提'>
                                                    <a href="/user/order/map?oid=${item.id?c}">物流详情</a>
                                                    </#if>
                                                    <!-- <a href="javascript:win_yes('是否确定收货？','confirmAccipt(${item.id?c})');">确认收货</a> -->
                                                <#break>
                                                <#case 5>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#if (!item.isRefund?? || !item.isRefund) && item.orderGoodsList?size gt 0 >
                                                    	<#if !(item.isCoupon??&&item.isCoupon)>
                                                    		<a href="javascript:checkReturn(${item.id?c});">申请退货</a>
                                                		<#else>
                                                			<a href="/coupon/return?orderId=${item.id?c}">申请退货</a>
                                                    	</#if>
                                                    	<a href="">立即评价</a>
                                                    </#if>
                                                <#break>
                                                <#case 6>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--
                                                    <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                                                    -->
                                                <#break>
                                                <#case 7>
                                                    <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                                    <#--
                                                    <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                                                    -->
                                                <#break>
                                            </#switch>
                                        </#if>
                                    </div>
                                </ol>
                            </#list>
                        </div>
                    </#if>
                </article>
                <!-- 用户订单 END -->                           
            </section>

            
            <div class="index_test_box02"></div>
            <#include "/client/common_footer.ftl">
        </div>  
        <script type="text/javascript"> 
        /*  //第一步：下拉过程 
         function slideDownStep1(dist){  // dist 下滑的距离，用以拉长背景模拟拉伸效果 
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown2.style.display = "none"; 
             slideDown1.style.display = "block"; 
             slideDown1.style.height = (parseInt("20px") - dist) + "px"; 
         } 
         //第二步：下拉，然后松开， 
         function slideDownStep2(){  
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown1.style.display = "none"; 
             slideDown1.style.height = "20px"; 
             slideDown2.style.display = "block"; 
             //刷新数据 
             //location.reload(); 
         } 
         //第三步：刷新完成，回归之前状态 
         function slideDownStep3(){  
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown1.style.display = "none"; 
             slideDown2.style.display = "none"; 
         }  */
        /*  //第一步：上划过程 
         function slideUpStep1(dist){  // dist 下滑的距离，用以拉长背景模拟拉伸效果 
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown2.style.display = "none"; 
             slideDown1.style.display = "block"; 
             //slideDown1.style.height = (parseInt("50px") - dist) + "px"; 
             slideDown1.style.height = 20 + "px";
         } 
         //第二步：上划，然后松开， 
         function slideUpStep2(){  
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown1.style.display = "n; 
             slideDown2.style.height = "20px"; 
             slideDown2.style.display = "block"; 
             //刷新数据 
             //location.reload(); 
         } 
         //第三步：刷新完成，回归之前状态 
         function slideUpStep3(){  
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown1.style.display = "none"; 
             slideDown2.style.display = "none"; 
             console.log('ajax');
         } 
          
         //下滑刷新调用 
         k_touch("all_orders","y"); 
         //contentId表示对其进行事件绑定，way==>x表示水平方向的操作，y表示竖直方向的操作 
         function k_touch(contentId,way){  
             var _start = 0, 
                 _end = 0, 
                 _content = document.getElementById(contentId); 
             _content.addEventListener("touchstart",touchStart,false); 
             _content.addEventListener("touchmove",touchMove,false); 
             _content.addEventListener("touchend",touchEnd,false); 
             function touchStart(event){  
                 //var touch = event.touches[0]; //这种获取也可以，但已不推荐使用 
         
                 var touch = event.targetTouches[0]; 
                 if(way == "x"){  
                     _start = touch.pageX; 
                 }else{  
                     _start = touch.pageY; 
                 } 
             } 
             function touchMove(event){  
                 var touch = event.targetTouches[0]; 
                 if(way == "x"){  
                     _end = (_start - touch.pageX); 
                 }else{  
                     _end = (_start - touch.pageY); 
                     //下滑才执行操作 
                     if(_end < 0){ 
                        // slideDownStep1(_end); 
                     } else{
                        slideUpStep1(_end); 
                     }
                 } 
         
             } 
             function touchEnd(event){  
                 if(_end >0){  
                     //console.log("左滑或上滑  "+(parseInt(_start)+parseInt(_end)));
                     var totaliscoll=parseInt(_start)+parseInt(_end);
                    slideUpStep2();
                     //刷新成功则 
                     //模拟刷新成功进入第三步 
                     if(parseInt($("#"+contentId).height())<parseInt(totaliscoll)){
                         slideUpStep3(); 
                     }
                      /* setTimeout(function(){  
                         slideUpStep3(); 
                     },2500);   */
               //  }else{  
                     //console.log("右滑或下滑"+_end); 
                     /* slideDownStep2(); 
                     //刷新成功则 
                     //模拟刷新成功进入第三步 
                     setTimeout(function(){  
                         slideDownStep3(); 
                     },2500);  */
                // } 
            // } 
        // }  */
 </script>    
    </body>
</html>
