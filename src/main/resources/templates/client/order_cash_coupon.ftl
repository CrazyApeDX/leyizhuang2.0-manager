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
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_order_manage.css"/>
        <!-- js -->
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/client/js/order_cash_coupon.js"></script>
        <script type="text/javascript">
            window.onload = function(){
               // $(".select-coupons .check").height($(".select-coupons .coupon").height());
                var aBox = $('.lei_box01');
                var aBtn = $('.lei_box');
                for(var i=0;i<aBtn.length;i++){
                    go(aBtn[i]);
                };
                function go(obj){
                    var oBtn = obj.children[0];
                    var aShow = obj.getElementsByTagName('div'); 
                    var onOff = true;
                    for(var i=0;i<aShow.length;i++){
                        aShow[i].style.display = 'none';
                    };
                    oBtn.onclick = function(){
                        if(onOff){
                            for(var i=0;i<aShow.length;i++){
                            aShow[i].style.display = 'block';
                                console.log(0)
                            };
                        }else{
                            for(var i=0;i<aShow.length;i++){
                                aShow[i].style.display = 'none';
                            };  
                        };
                        onOff=!onOff;
                    };
                };
            };
    </script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <!-- 头部 -->
        <header>
            <a class="back" href="/order"></a>
            <p>选择现金券</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 选择产品劵 -->
        <article class="select-coupons">
            <#if brand_list??>
                <#list brand_list as brand>
                    <div class="lei_box">
                        <h3 class="lei_title">${brand.title!''}</h3>
                        <div class="lei_box01">
                            <#if brand.id??&&("coupons"+brand.id?c)?eval??>
                                <#list ("coupons"+brand.id?c)?eval as item>
                                    <section id="${item.id?c}">
                                        <div id="check${item.id?c}" 
                                            <#if no_product_used??>
                                                <#assign isUsed=false>
                                                <#list no_product_used as used>
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
                                            <img id="img${item.id?c}" src=<#if isUsed??&&isUsed==true>"/client/images/x_icon_checked_red.png"<#else>"/client/images/x_icon_check.png"</#if> alt="">
                                        </div>
                                        <div class="coupon">
                                            <img class="bg" src="/client/images/x_bg_select_coupons.png" alt="">
                                            <div class="floatbox">
                                                <div class="pro-price">￥<span><#if item??&&item.price??>${item.price?string("0")}<#else>0.00</#if></span></div>
                                                <div class="tips"  style="margin-left:80px;float: none;overflow: hidden;">
                                                    <div class="div1">现金劵</div>
                                                    <span class="div2" id="name${item.id?c }" style="font-size:1.2em">${item.goodsName!""}</span>
                                                    <script type="text/javascript">
                                                        if(parseInt(($('#name${item.id?c}').width()+15))>=parseInt($('#name${item.id?c}').parent().width())){
                                                        	$('#name${item.id?c}').css('font-size','0.9em');
                                                        	$('#name${item.id?c}').prev().css('line-height','normal');
                                                        }
                                                    </script>
                                                </div>        
                                                <div class="clear"></div>
                                                <div class="time">有效期：<span><#if item.getTime??>${item.getTime?string("yyyy-MM-dd")}</#if></span> - <span><#if item.expireTime??>${item.expireTime?string("yyyy-MM-dd")}</#if></span></div>
                                            </div>
                                        </div>
                                    </section>
                                </#list>
                            </#if>
                        </div>
                    </div>
                </#list>
            </#if>
        </article>
        <!-- 选择产品劵 END -->
    </body>
</html>