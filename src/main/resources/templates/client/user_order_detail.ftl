<!DOCTYPE html>
<html lang="zh-CN" class="bgc-f3f4f6">
    <head>
        <meta charset="UTF-8">
        <meta name="keywords" content="">
        <meta name="copyright" content="" />
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <title>乐易装</title>
        <!-- css -->
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_order_manage.css"/>
        <style type="text/css">
        	.order_row{
        	    height: 30px;
			    line-height: 30px;
			    color: #999;
			    text-indent: 2%;
        	}
        	.order_right{
	        	float: right;
	   			text-align: right;
	   			margin-right: 2%;
	   			color: #cc1421;
        	}
        	.order_right span{
	   			color: black;
        	}
        	.order_border{
        		border-bottom: 1px dashed #ddd;
        	}
        	.total_pay{
        		height: 40px;
			    line-height: 40px;
			    font-weight: bold;
    			font-size: medium;
        	}
        </style>
    </head>
    <body class="bgc-f3f4f6">
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>订单详情</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 订单详情 -->
        <#if order??>
            <article class="order-details">
                <!-- 用户信息 -->
                <#if order.deliverTypeTitle??&&order.deliverTypeTitle!='门店自提'>
                <div class="user-info">
                    <div class="div1">
                        <div class="div1-1">收货人: <span>${order.shippingName!''}</span></div>
                        <div class="div1-2">电话：<span>${order.shippingPhone!''}</span></div>
                    </div>
                    <div class="div2">收货地址：<span>${order.shippingAddress!''}</span></div>
                </div>
                </#if>
                <!-- 订单列表 -->
                <ol class="order-list">
                    <li class="li1">
                        <label>订单号：<span>${order.orderNumber!''}<#if order.isCoupon??&&order.isCoupon>（券）</#if></span></label>
                        <div class="species c-ff8e08">
                            <#if order.statusId??>
                                <#switch order.statusId>
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
                    <li>
                        <#if order.remarkInfo??>
                            <label>提示：<span>${order.remarkInfo!''}</span></label>
                        </#if>
                    </li>
                    <#if order.orderGoodsList??>
                        <#list order.orderGoodsList as item>
                            <li class="li2 bdbt-n" style="height: auto;">
                                <div class="img"><img src="${item.goodsCoverImageUri!''}" alt="产品图片"></div>
                                <div class="product-info" style="height: auto;">
                                    <div class="div1">${item.goodsTitle!''}<#if item.isCoupon??&&item.isCoupon>【券】</#if>
                                    	<#-- 判断是否是退货中 -->
                                    	<#if returnNote??>
                                    		<#list returnNote.returnGoodsList as returnGood>
                                    			<#if returnGood.sku==item.sku><span class="order_biao">退</span><#break></#if>
                                    			
                                    		</#list>
                                    	</#if>
                                    </div>
                                    <div class="div1">${item.sku!''}</div>
                                    <div class="div2">￥<span><#if item.price??>${item.price?string("0.00")}<#else>0.00</#if></span><label>数量：<span>${item.quantity!'0'}</span></label></div>
                                </div>
                            </li>
                        </#list>
                    </#if>
                    <!-- 赠品 -->
                    <#if order.presentedList??>
                        <#list order.presentedList as item>
                            <li class="li2 bdbt-n" style="height: auto;">
                                <div class="img"><img src="${item.goodsCoverImageUri!''}" alt="产品图片"></div>
                                <div class="product-info" style="height: auto;">
                                    <div class="div1">${item.goodsTitle!''}<span style="color:red"><#if item.isCoupon??&&item.isCoupon>【赠品-券】<#else>【赠品】</#if></span>
                                    	<#-- 判断是否是退货中 -->
                                    	<#if returnNote??>
                                    		<#list returnNote.returnGoodsList as returnGood>
                                    			<#if returnGood.sku==item.sku><span class="order_biao">退</span><#break></#if>
                                    			
                                    		</#list>
                                    	</#if>
                                    </div>
                                    <div class="div1">${item.sku!''}</div>
                                    <div class="div2">￥<span><#if item.price??>${item.price?string("0.00")}<#else>0.00</#if></span><label>数量：<span>${item.quantity!'0'}</span></label></div>
                                </div>
                            </li>
                        </#list>
                    </#if>
                    <#if order.presentedList??>
                        <#list order.giftGoodsList as item>
                            <li class="li2 bdbt-n" style="height: auto;">
                                <div class="img"><img src="${item.goodsCoverImageUri!''}" alt="产品图片"></div>
                                <div class="product-info" style="height: auto;">
                                    <div class="div1">${item.goodsTitle!''}<span style="color:red;font-size:0.9em;"><#if item.isCoupon??&&item.isCoupon>【小辅料-券】<#else>【小辅料】</#if></span>
                                    	<#-- 判断是否是退货中 -->
                                    	<#if returnNote??>
                                    		<#list returnNote.returnGoodsList as returnGood>
                                    			<#if returnGood.sku==item.sku><span class="order_biao">退</span><#break></#if>
                                    		</#list>
                                    	</#if>
                                    </div>
                                    <div class="div1">${item.sku!''}</div>
                                    <div class="div2">￥<span><#if item.price??>${item.price?string("0.00")}<#else>0.00</#if></span><label>数量：<span>${item.quantity!'0'}</span></label></div>
                                </div>
                            </li>
                        </#list>
                    </#if>
                    <#-- 退货金额 -->
                    <#if order.statusId==9 || order.statusId=10>
                    	<#if returnNote??>
                    		<li class="li5">退货金额：<div class="div1"><p>￥<span><#if returnNote.turnPrice??>${returnNote.turnPrice?string("0.00")}<#else>0.00</#if></span></p></div></li>
                    	</#if>
                    </#if>
                    <#-- 代付款订单和取消订单特殊处理 
                    <#if order.statusId==2 || order.statusId==7>
                    <li class="li5">支付方式：${order.payTypeTitle!''}<div class="div1">商品总额：<p>￥<span><#if order.totalGoodsPrice??>${order.totalGoodsPrice?string("0.00")}<#else>0.00</#if></span></p></div></li>
                    <#else>
                    <li class="li5">支付方式：${order.payTypeTitle!''}<div class="div1">订单总额：<p>￥<span><#if order.totalPrice??>${order.totalPrice?string("0.00")}<#else>0.00</#if></span></p></div></li>
                    </#if>
                    -->
                    <li class="li5">支付方式：${order.payTypeTitle!''}<div class="div1">商品总额：<p>￥<span><#if order.totalGoodsPrice??>${order.totalGoodsPrice?string("0.00")}<#else>0.00</#if></span></p></div></li>
					<li class="li5">运费：<#if order.deliverFee??>${order.deliverFee?string("0.00")}<#else>0.00</#if></li>
                    <li class="li5">是否代下单：<#if order.isSellerOrder??&&order.isSellerOrder>是<#else>否</#if></li>
                    <li class="li5" style="overflow: visible;height: auto;">订单备注：${order.remark!''}</li>
                    <li class="li5">服务导购：${order.sellerRealName!''}</li>
                    <li class="li5">导购电话：${order.sellerUsername!''}</li>
                    <li class="li5">使用现金券：<div class="div1">券金额：<p>￥<span><#if order.cashCoupon??>${order.cashCoupon?string("0.00")}<#else>0.00</#if></span></p></div></li>
                    <#-- 代付款订单和取消订单特殊处理 -->
                    <#if order.statusId==2 || order.statusId==7>
                    <li class="li6"><span>使用产品券：</span><div class="div1"><p>${couponTitles!''}</p></div></li>
                    <#else>
                    <li class="li6"><span>使用产品券：</span><div class="div1"><p>${order.productCoupon!''}</p></div></li>
                    </#if>
                    
                    <style type="text/css">
                        .li6 {
                            height: auto;
                            line-height: 40px;
                            color: #999;
                            border-top: 1px solid #ddd;
                        }
                        .li6 > span {width:30%;float:left;}
                        .li6 > .div1 {width:70%; float:left; color:#666;}
                    </style>
                    <li class="li5">配送人：<#if opUser??>${opUser.realName!''}</#if><#if opUser??&&opUser.username??>(<a href="tel:${opUser.username!''}">${opUser.username!''}</a>)</#if></li>
               		<li class="li5">配送仓库：<#if tdWareHouse??>${tdWareHouse.whName!''}</#if></li>
                </ol>   
            </article>
            <article class="order-list">
            	<#-- 代付款订单和取消订单特殊处理 -->
                <#if order.statusId==2 || order.statusId==7>
            		<#if order.actualPay?? && order.actualPay!=0>
	            	<div class="order_row">预存款<div class="order_right">${order.actualPay?string("currency") }</div></div>
	            	</#if>
            	<#else>
	            	<#if order.cashBalanceUsed?? && order.cashBalanceUsed!=0>
	            	<div class="order_row">可提现预存款<div class="order_right">${order.cashBalanceUsed?string("currency") }</div></div>
	            	</#if>
	           		<#if order.unCashBalanceUsed?? && order.unCashBalanceUsed!=0>
	           		<div class="order_row">不可提现预存款<div class="order_right">${order.unCashBalanceUsed?string("currency") }</div></div>
	           		</#if>
            	</#if>
	           	<#if order.otherPay?? && order.otherPay!=0>
	           	<div class="order_row">第三方支付<div class="order_right">${order.otherPay?string("currency") }</div></div>
	           	</#if>
	           	<#if order.cashPay?? && order.cashPay!=0>
	           	<div class="order_row">现金支付<div class="order_right">${order.cashPay?string("currency") }</div></div>
	           	</#if>
	           	<#if order.posPay?? && order.posPay!=0>
	           	<div class="order_row">pos支付<div class="order_right">${order.posPay?string("currency") }</div></div>
	           	</#if>
				<#if order.backOtherPay?? && order.backOtherPay!=0>
	           	<div class="order_row">其他支付<div class="order_right">${order.backOtherPay?string("currency") }</div></div>
	           	</#if>
           		<div class="order_border"></div>
           		<div class="order_row total_pay"><div class="order_right"><span>实付款 ：</span>${totolPayment?string("currency") }</div></div>
            </article>
            <!-- 订单详情 END -->
                    
            <!-- 配送信息 -->
            <article class="delivery-info" style="margin-bottom:60px;">
                <#if order.statusId==4>
                <div class="title">
                    <span>配送信息</span>
                    <a href="/user/order/map?oid=${order.id?c}" style="float:right;margin-right:10px;font:1em;color:#999999;" >查看地图</a>
                </div>
                <!-- 物流信息 -->
                <#--<div class="estimated-time">预计到达时间：2015-11-30</div>-->
                    <ul class="delivery-pro">
                        <li class="active">
                            <div class="progress-pic"></div>
                            <div class="progress-ifo">
                                <div>已出库</div>
                                <div></div>
                            </div>
                        </li>
                        <#if tdUser??>
                            <li class="last">
                                <div class="progress-pic"></div>
                                <div class="progress-ifo">
                                    <div>${geoInfo.formattedAddress!''}——${tdUser.realName!''}（${tdUser.username!''}）正在派件</div>
                                    <div>${geoInfo.time?string("0.00")}</div>
                                </div>
                            </li>
                        </#if>
                    </ul>
                </#if>
            </article>
            <!-- 配送信息 END -->
        </#if>
		<#if !(order.isCoupon??&&order.isCoupon==true)>
	        <footer>
	        	<a class="btn-clearing" href="/goods/again?orderId=${orderId?c}">再来一单</a>
	        </footer>
        </#if>
    </body>
</html>