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
        <!-- js -->
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/client/js/order_pay.js"></script>
        <script>
        		
        	 $(function(){
        	 	var flag = $("#flag").val();
        	 	if("Y"!=flag){
        	 		window.location.href="/order?back=1";
        	 	}else{
        	 		$("#flag").val('');
        	 	}
        		
        	});
        </script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl"> 
        <#-- 引入会员信息列表 -->
        <div id="info_window">
            <#include "/client/order_user_list.ftl">
        </div>
        <!-- 头部 -->
        <header>
            <a class="back" href="/"></a>
            <p>填写订单</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 填写订单 -->
        <article>
            <!-- 用户信息 -->
            <#if order.deliverTypeTitle!='门店自提' && !(order.isCoupon??&&order.isCoupon)>
	            <#if order??&&order.shippingName??&&order.shippingPhone??&&order.shippingAddress??>
	                <div class="receiver-info">    
	                    <div class="div1">
	                        <div class="div1-1">收货人：<span>${order.shippingName!''}</span></div>
	                        <div class="div1-2">电话：
	                            <span>
	                                <#if order.shippingPhone??&&order.shippingPhone?length==11>
	                                    ${order.shippingPhone[0..2]}****${order.shippingPhone[7..10]}
	                                </#if>
	                            </span>
	                        </div>
	                    </div>
	                    <a class="go-target" href="/order/change/address">
	                        <div class="div2">收货地址：<span>${order.shippingAddress!''}</span></div>
	                    </a>
	                </div>
	            <#else>
	                <a href="/order/add/address" style="background:#ffaa00;display:block;text-align:center;line-height:30px;width:92%;margin:0 4%;font-size:1.2em;color:white;border-radius:4px;margin-top:15px;">添加收货地址+</a>
	            </#if>
            </#if>
            <!-- 编辑订单 -->
            <article class="fill-order-list">
                <!-- 商品清单 -->
                <#if order??&&order.orderGoodsList??>
                    <section class="pro-list">
                        <a class="div1" href="/order/selected">
                            <#list order.orderGoodsList as item>
                                <#-- 此处实际只能放入3个已选的图片 -->
                                <#if item_index lt 3>
                                    <div class="img"><img src="${item.goodsCoverImageUri!''}" alt="产品图片"></div>
                                </#if>
                            </#list>
                            <div class="total">共${totalGoods!''}件</div>
                        </a>
                    </section>
                </#if>
                <!-- 送货上门 -->
                <#if !(order.isCoupon??&&order.isCoupon)>
	                <section class="delivery">
	                    <div class="div1">
	                        <label>配送方式</label>
	                        <a class="delivery-method" href="/order/delivery">${order.deliverTypeTitle!''}</a>
	                    </div>
	                    <div class="div2">${order.deliveryDate!''}  ${order.deliveryDetailId!''}:30-${(order.deliveryDetailId+1)?eval}:30</div>
	                </section>
                </#if>
                <!-- 支付方式 -->
                <section class="pay-method">
                    <label>支付方式</label>
                    <a class="target" href="/order/paytype">${order.payTypeTitle!''}</a>
                </section>
                <!-- 发票信息 -->
                <section class="invoice-info">
                    <label>门店信息</label>
                    <div>${order.diySiteName!''}（${order.diySitePhone!''}）</div>
                </section>
                <!-- 发票信息 -->
                <#if order.sellerRealName??>
	                <section class="invoice-info">
	                    <label>服务导购</label>
	                   	<a class="target" href="/order/delivery">${order.sellerRealName!'暂无'}</a>
	                </section>
                </#if>
                <!-- 留言 -->
                <section class="leave-message" <#--暂时隐藏 <#if user??&&user.userType==1>style="height: 80px;" </#if> --> >
                    <input id="remark" onblur="userRemark('${order.remark!''}');" type="text" maxlength="200" value="${order.remark!''}" placeholder="给商家留言">
					<#-- 暂时隐藏
					<#if user??&&user.userType==1>
						 <input id="otherIncome" onblur="sellerOtherIncome('${order.otherIncome!''}');" type="text" value="${order.otherIncome!'' }" placeholder="其他收入">
					</#if>                    
					-->
                </section>
                <!-- 优惠劵 -->
                <section class="coupon">
                    <div class="div1">
                        <label>产品劵</label>
                        <a class="target" <#if (product_coupon_list??&&product_coupon_list?size gt 0&&!(order??&&order.isCoupon??&&order.isCoupon))>href="/order/coupon/1"</#if>>
                            <#if order??&&order.isCoupon??&&order.isCoupon>
                                                                                禁止使用
                            <#else>
                                <#if product_coupon_list??&&product_coupon_list?size gt 0>
                                    <#if product_used??>
                                        ${product_used}张
                                    <#else>
                                                                                               未使用
                                    </#if>
                                <#else>
                                                                                    无可用
                                </#if>
                            </#if>
                        </a>
                    </div>
                    <div class="div1">
                        <label>现金劵</label>
                        <a class="target" <#if (no_product_coupon_list??&&no_product_coupon_list?size gt 0)>href="/order/coupon/0"</#if>>
                        	<#--
                            <#if order??&&order.isCoupon??&&order.isCoupon>
                                                                                禁止使用
                            <#else>
                            -->
                                <#if no_product_coupon_list??&&no_product_coupon_list?size gt 0>
                                    <#if no_product_used??>
                                        ${no_product_used}张
                                    <#else>
                                                                                               未使用
                                    </#if>
                                <#else>
                                                                                    无可用
                                </#if>
                            <#--
                            </#if>
                            -->
                        </a>
                    </div>
                    <div class="div1">
                        <label>预存款</label>
                        <#if !(max??)>
                            <#assign max=0.00>
                        </#if>
                        <a class="target" <#if !(isCoupon??&&isCoupon==false)>href="/order/user/balance?max=${max?string("0.00")}"</#if>>
                        	<#--
                            <#if isCoupon??&&isCoupon==false>
                                                                                禁止使用
                            <#else>
                            -->
                                <#if order??&&order.actualPay??>${order.actualPay?string("0.00")}<#else>0.00</#if>
                            <#--
                            </#if>
                            -->
                        </a>
                    </div>
                </section>
                <!-- 商品费用 -->
                <section class="pro-price">
                    <div class="div1">
                        <label>商品价值</label>
                        <div>￥<span><#if order??&&order.totalGoodsPrice??>${order.totalGoodsPrice?string("0.00")}<#else>0.00</#if><span></div>
                    </div>
                    <div class="div1">
                        <label>运费</label>
                        <div>￥<#if order??&&order.deliverFee??&&!(isFree??&&isFree)>${order.deliverFee?string("0.00")}<#else>0.00</#if></div>
                    </div>
                    <#if order??&&order.activitySubPrice??&&order.activitySubPrice gt 0>
	                    <div class="div1">
	                        <label>促销扣减</label>
	                        <div>￥<span><#if order??&&order.activitySubPrice??>-${order.activitySubPrice?string("0.00")}<#else>-0.00</#if><span></div>
	                    </div>
                    </#if>
                </section>
            </article>
        </article>
        <!-- 填写订单 END -->
        
        <div class="clear h50"></div>
        
        <!-- 底部 -->
        <footer class="fill-order-foot">
            <div class="disbur">还需支付：￥
                <span id="order_total_price"><#if order.totalPrice??>${order.totalPrice?string("0.00")}<#else>0.00</#if></span>
            </div>
            <#--<a class="btn-clearing" id="buyNow" href="javascript:orderPay();">去支付</a>-->
            <a class="btn-clearing" id="buyNow" href="javascript:confirmPay();">去支付</a>
        </footer>
        <!-- 底部 END -->
        <input type="hidden" id="flag" name="flag" value="${flag!''}" >
    </body>
</html>