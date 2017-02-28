<!DOCTYPE html>
<html lang="zh-CN" class="bgc-f3f4f6">
    <head>
        <meta charset="UTF-8">
        <meta name="keywords" content="">
        <meta name="copyright" content="" />
        <meta name="description" content="">
        <meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0"> 
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <title>乐易装</title>
        <!-- css -->
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_order_manage.css"/>
        <!-- js -->
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/fitment/js/order_pay.js"></script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl"> 
        <!-- 头部 -->
        <header>
            <a class="back" href="/fit/audit/order"></a>
            <p>填写订单</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 填写订单 -->
        <article>
            <!-- 收货地址信息 -->
            <div class="receiver-info">    
                <div class="div1">
                    <div class="div1-1">收货人：<span>${order.receiveName!''}</span></div>
                    <div class="div1-2">电话：
                        <span>
                            <#if order.receivePhone??&&order.receivePhone?length==11>
                                ${order.receivePhone[0..2]}****${order.receivePhone[7..10]}
                            </#if>
                        </span>
                    </div>
                </div>
                <a class="go-target" style="background:none;">
                    <div class="div2">收货地址：<span>${order.receiveAddress!''}</span></div>
                </a>
            </div>
            <!-- 编辑订单 -->
            <article class="fill-order-list">
                <!-- 商品清单 -->
                <#if order??&&order.orderGoodsList??>
                    <section class="pro-list">
                        <a class="div1" href="/fit/pay/goods/${order.id?c}">
                            <#list order.orderGoodsList as item>
                                <#-- 此处实际只能放入3个已选的图片 -->
                                <#if item_index lt 3>
                                    <div class="img"><img src="${item.goodsCoverImageUri!''}" alt="产品图片"></div>
                                </#if>
                            </#list>
                            <div class="total">共${order.orderGoodsList?size}件</div>
                        </a>
                    </section>
                </#if>
                <!-- 送货上门 -->
                <section class="delivery">
                    <div class="div1">
                        <label>配送信息</label>
                        <a class="delivery-method" href="/fit/pay/delivery/${order.id?c}">送货上门（${order.floor!"1"}楼）</a>
                    </div>
                    <div class="div2">${order.deliveryDate!''} ${order.deliveryTime}:30-${order.deliveryTime + 1}:30</div>
                </section>
                <!-- 送货上楼 -->
                <#if order.deliverTypeTitle??&&order.deliverTypeTitle!='门店自提'>
	                <section class="pay-method">
	                    <label>送货上楼</label>
	                    <a class="target" href="/order/upstairs">
	                    	${order.upstairsType!''}
	                    	<#if order.upstairsType??&&order.upstairsType=='步梯'>
	                    		${order.floor!'1'}楼
	                    	</#if>
	                    </a>
	                </section>
                </#if>
                
                <!-- 留言 -->
                <input type="hidden" id="_order_dev" value="${order.id?c}">
                <section class="leave-message" <#--暂时隐藏 <#if user??&&user.userType==1>style="height: 80px;" </#if> --> >
                    <input id="remark" onblur="userRemark('${order.remark!''}');" type="text" maxlength="200" value="${order.remark!''}" placeholder="给商家留言">
                </section>
                <!-- 优惠劵 -->
                <section class="coupon">
                    <div class="div1">
                        <label>当前可用信用金：${(credit!"0")?string("0.00")}</label>
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
                    <div class="div1">
                        <label>上楼费</label>
                        <div>￥<#if order??&&order.upstairsFee??>${order.upstairsFee?string("0.00")}<#else>0.00</#if></div>
                    </div>
                    <#if order??&&order.activitySubPrice??&&order.activitySubPrice gt 0>
	                    <div class="div1">
	                        <label>促销扣减</label>
	                        <div>￥<span><#if order??&&order.activitySubPrice??>-${order.activitySubPrice?string("0.00")}<#else>-0.00</#if><span></div>
	                    </div>
                    </#if>
                    <div class="div1">
                        <label>协议减免</label>
                        <div style="color:green;">￥-${(order.totalGoodsPrice - order.totalPrice)?string("0.00")}</div>
                    </div>
                </section>
            </article>
        </article>
        <!-- 填写订单 END -->
        
        <div class="clear h50"></div>
        
        <!-- 底部 -->
        <footer class="fill-order-foot">
            <div class="disbur">还需支付：￥
                <span id="order_total_price">
                	${(order.totalPrice + order.upstairsLeftFee)?string("0.00")}
    			</span>
            </div>
            <a class="btn-clearing" id="buyNow" href="javascript:finish(${order.id?c});">去支付</a>
        </footer>
        <script type="text/javascript">
        	var finish = function(id) {
        		wait();
        		$.ajax({
        			url: "/fit/order/finish",
        			method: "POST",
        			data: {
        				id: id
        			},
        			success: function(res) {
        				if ("SUCCESS" == res.actionCode) {
        					window.location.href = "/fit/audit/order";
        				} else {
        					close(1);
        					warning(res.content);
        				}
        			}
        		})
        	}
        </script>
    </body>
</html>