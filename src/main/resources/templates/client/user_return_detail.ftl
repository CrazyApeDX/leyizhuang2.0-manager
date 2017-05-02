<!DOCTYPE html>
<html lang="zh-CN" class="bgc-f3f4f6">
    <head>
        <meta charset="UTF-8">
        <meta name="keywords" content="">
        <meta name="copyright" content="" />
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <title>退货单详情</title>
        <!-- css -->
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_order_manage.css"/>
    </head>
    <body class="bgc-f3f4f6">
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>退货单详情</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 订单详情 -->
        <#if returnNote??>
            <article class="order-details">
                <!-- 订单列表 -->
                <ol class="order-list" style="margin-top: 0;">
                    <li class="li1">
                        <label>退货单号：<span>${returnNote.returnNumber!''}</span></label>
                        <div class="species c-ff8e08">
                            ${returnNote.statusName }
                        </div>
                    </li>
                    <li class="li1">
                        <label>原订单号：<span>${returnNote.orderNumber!''}</span></label>
                    </li>
                    <#if returnNote.returnGoodsList??>
                        <#list returnNote.returnGoodsList as item>
                            <li class="li2 bdbt-n">
                                <div class="img"><img src="${item.goodsCoverImageUri!''}" alt="产品图片"></div>
                                <div class="product-info">
                                    <div class="div1">${item.goodsTitle!''}<#if item.isCoupon??&&item.isCoupon>【券】</#if>
                                    </div>
                                    <div class="div1">${item.sku!''}</div>
                                    <div class="div2">￥<span><#if item.price??>${item.price?string("0.00")}<#else>0.00</#if></span><label>数量：<span>${item.quantity!'0'}</span></label></div>
                                </div>
                            </li>
                        </#list>
                    </#if>
                    
                    <li class="li5">退货金额：<div class="div1"><p>￥<span><#if returnNote.turnPrice??>${returnNote.turnPrice?string("0.00")}<#else>0.00</#if></span></p></div></li>
                    <li class="li5">退货方式：${returnNote.turnTypeName!''}</li>
                    <li class="li5" style="overflow: visible;height: auto;">退货原因：${returnNote.remarkInfo!''}</li>
                    <li class="li5">服务导购：${returnNote.sellerRealName!''}</li>
                    <li class="li5">导购电话：<#if sellerUser?? && sellerUser.username??> ${sellerUser.username!''} </#if></li>
                    <li class="li5">退货时间： ${returnNote.orderTime!''} </li>
                    <li class="li5">取消退货时间： ${returnNote.cancelReturnTime!''} </li>
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
                </ol>   
            </article>
            <!-- 订单详情 END -->
                    
            <!-- 配送信息 -->
            <article class="delivery-info">
               <#if returnNote.statusId==2>
                <div class="title">
                    <span>配送信息</span>
                    <a href="/user/order/map?oid=${returnNote.id?c}" style="float:right;margin-right:10px;font:1em;color:#999999;" >查看地图</a>
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
    </body>
</html>