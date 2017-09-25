<#if all_order_list??>
    <div id="all_orders" class="some_orders" style="background: #f3f4f6">
        <#list all_order_list.content as item>
            <ol class="order-list">
                <li class="li1">
                    <label>订单号：<span>${item.orderNumber!''}</span></label>
                    <div class="species">
                        <#if item.statusId??>
                            <#switch item.statusId>
                                <#case 2>待付款<#break>
                                <#case 3>待发货<#break>
                                <#case 4>待签收<#break>
                                <#case 5>已完成<#break>
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
                            <div class="product-info"">
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
                                	<#--
                                	<a href="">立即评价</a>
                                	-->
                                </#if>
	                        <#break>
                            <#case 6>
                                <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                <#if (!item.isRefund?? || !item.isRefund) && item.orderGoodsList?size gt 0 >
                                	<#if !(item.isCoupon??&&item.isCoupon)>
                                		<a href="javascript:checkReturn(${item.id?c});">申请退货</a>
                            		<#else>
                            			<a href="/coupon/return?orderId=${item.id?c}">申请退货</a>
                                	</#if>
                                	<#--
                                	<a href="">立即评价</a>
                                	-->
                                </#if>
                            <#break>
                            <#case 7>
                                <a href="/user/order/detail/${item.id?c}">订单详情</a>
                                <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                            <#break>
                            <#case 9>
                            	<a href="/user/order/detail/${item.id?c}">订单详情</a>
                            <#break>
                            <#case 10>
                            	<a href="/user/order/detail/${item.id?c}">订单详情</a>
                            <#break>
                            <#case 11>
                            	<a href="/user/order/detail/${item.id?c}">订单详情</a>
                            	<#--
                                <a href="javascript:win_yes('是否确定删除？','deleteOrder(${item.id?c})');">删除订单</a>
                                -->
                            <#break>
                            <#case 12>
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