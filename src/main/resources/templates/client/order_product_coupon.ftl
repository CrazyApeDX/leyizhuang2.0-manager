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
        <script type="text/javascript" src="/client/js/order_product_coupon.js"></script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <!-- 头部 -->
        <header>
            <a class="back" href="/order?count=1"></a>
            <p>选择产品劵</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 选择产品劵 -->
        <article class="select-coupons">
            <#if product_coupon_list??>
                <#list product_coupon_list as item>
                    <section onclick="javascript:selectCoupon(${item.id?c});" id="${item.id?c}">
                        <div  
                            <#if product_used??>
                                <#assign isUsed=false>
                                <#list product_used as used>
                                    <#if used?c==item.id?c>
                                        <#assign isUsed=true>
                                    </#if>
                                </#list>
                            </#if>
                            <#if isUsed??&&isUsed==true>
                                class="check checked"
                            <#else>
                                class="check"
                            </#if>
                        >
                            <img src=<#if isUsed??&&isUsed==true>"/client/images/x_icon_checked_red.png"<#else>"/client/images/x_icon_check.png"</#if> alt="">
                        </div>
                        <div class="coupon">
                            <img class="bg" src="/client/images/x_bg_select_coupons.png" alt="">
                            <div class="floatbox">
                                <img class="pro-pic" src="${item.picUri!''}" alt="">
                                <div class="tips" style="margin-left:80px;float: none;overflow: hidden;">
                                    <div class="div1">产品劵</div>
                                    <div class="div2" id="name${item.id?c }" style="font-size:1.2em" >${item.goodsName!''}</div>
                                    <script type="text/javascript">
                                       if(parseInt(($('#name${item.id?c}').width()+15))>=parseInt($('#name${item.id?c}').parent().width())){
                                           $('#name${item.id?c}').css('font-size','0.9em');
                                       }
                                    </script>
                                </div>
                                <div class="clear"></div>
                                <div class="time">有效期：<span><#if item.getTime??>${item.getTime?string("yyyy.MM.dd")}</#if></span> - <span><#if item.expireTime??>${item.expireTime?string("yyyy.MM.dd")}</#if></span></div>
                            </div>
                        </div>
                    </section>
                </#list>
            </#if>
        </article>
        <!-- 选择产品劵 END -->
        
    </body>
</html>