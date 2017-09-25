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
        <style>
            .checked{
                float: right;
                margin: 6px 0 6px 5px;
                width: 21px;
                height: 21px;
                border: 1px solid #ddd;
            }
        </style>
    </head>
    <body class="bgc-f3f4f6">
            <!-- 头部 -->
            <header>
                <a class="back" href="/order"></a>
                <p>商品清单</p>
            </header>
            <!-- 头部 END -->
            
        <!-- 商品清单 -->
        <article class="pro-listing">
            <#if order??&&order.orderGoodsList??>
                <!-- 商品列表 -->
                <#list order.orderGoodsList as item>
                    <section class="sec1">
                        <div class="img"><img src="${item.goodsCoverImageUri!''}" alt="产品图片"></div>
                        <div class="product-info">
                            <div class="descript">${item.goodsTitle!''}</div>
                            <div class="choose-num">
                                <!-- 数量选择 -->
                                <div class="numbers">数量：<span><#if item.quantity??>${item.quantity}<#else>0</#if></span></div>
                                <div class="price">￥
                                    <#if item.price??&&item.quantity??>
                                        <span>${(item.price * item.quantity)?string("0.00")}</span>
                                    <#else>
                                        <span>0.00</span>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </section>
                </#list>
            </#if>
            <!-- 赠品 -->
            <#if order??&&order.presentedList??>
                <#if order.presentedList?size gt 0>
                    <!-- 商品列表 -->
                    <#list order.presentedList as item>
                        <section class="sec1">
                            <div class="img"><img src="${item.goodsCoverImageUri!''}" alt="产品图片"></div>
                            <div class="product-info">
                                <div class="descript">${item.goodsTitle!''}</div>
                                <div class="choose-num">
                                    <!-- 数量选择 -->
                                    <div class="numbers">数量：<span><#if item.quantity??>${item.quantity}<#else>0</#if></span></div>
                                    <div class="price">赠品</div>
                                    <#--<div class="checked"></div>-->
                                </div>
                            </div>
                        </section>
                    </#list>
                </#if>
            </#if>
            <!-- 小辅料 -->
            <#if order??&&order.giftGoodsList??&&order.giftGoodsList?size gt 0>
                <#list order.giftGoodsList as item>
                    <section class="sec1">
                        <div class="img"><img src="${item.goodsCoverImageUri!''}" alt="产品图片"></div>
                        <div class="product-info">
                            <div class="descript">${item.goodsTitle!''}</div>
                            <div class="choose-num">
                                <!-- 数量选择 -->
                                <div class="numbers">数量：<span><#if item.quantity??>${item.quantity}<#else>0</#if></span></div>
                                <div class="price">小辅料</div>
                                <#--<div class="checked"></div>-->
                            </div>
                        </div>
                    </section>
                </#list>
            </#if>
            <script type="text/javascript">
                $(function(){
                    var onOff = true;
                    $(".checked").click(function(){
                        if(onOff){
                            $(this).addClass("active");
                        }else{
                            $(this).removeClass("active");
                        }
                        onOff = !onOff;
                    });
                });
            </script>
            <!-- 总计 -->
            <section class="zonge">
                <#--总计：<span><#if order??&&order.orderGoodsList??&&order.presentedList??&&order.giftGoodsList??>${(order.orderGoodsList?size+order.presentedList?size+order.giftGoodsList?size)?eval}<#else>0</#if></span>件&nbsp;&nbsp;-->
                                        总额：<span><#if order??&&order.totalPrice??>${order.totalPrice?string("0.00")}<#else>0.00</#if></span>元
            </section>
        </article>
        <!-- 商品清单 END -->
    </body>
</html>