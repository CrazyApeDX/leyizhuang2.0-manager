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
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_my_wealth.css"/>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/index.js" type="text/javascript"></script>
        <script type="text/javascript">
            window.onload = function(){
                footer();
            }
        </script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入公共购物方式选择滑动窗口 -->
        <#include "/client/common_shopping_type.ftl">
        
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>我的财富</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 我的财富 -->
        <article class="mine-wealth">
            <section class="wallet-balance">
                <a href="/user/wallet">
                    <div class="div1">钱包余额 <span><#if user??&&user.balance??>${user.balance?string("0.00")}<#else>0.00</#if>元</span></div>
                    <div class="div2"></div>
                </a>
            </section>
            
            <section class="sec2">
                <div class="div1">
                    <a href="/user/recharge">充值</a>
                </div>
                <div class="div2">
                    <a href="/user/deposit">提现</a>
                </div>
            </section>
            
            <#if user.userType==1||user.userType==2>
	            <section class="coupon">
	                <a href="/user/credit">
	                    <div class="div1" style="background-image: url(/client/images/x_icon_credit_cert.png)">我的信用金</div>
	                    <div class="div2"><#if user.credit??>${user.credit?string("0.00")}<#else>0</#if></div>
	                </a>
	            </section>
            </#if>
            <section class="coupon cash-coupon">
                <a href="/user/coupon/0">
                    <div class="div1">我的现金劵</div>
                    <div class="div2">￥<#if no_product_total??>${no_product_total?string("0.00")}<#else>0</#if></div>
                </a>
            </section>
            <section class="coupon">
                <a href="/user/coupon/1">
                    <div class="div1">我的产品劵</div>
                    <div class="div2"><#if totalNumber??>${totalNumber!'0'}</#if>张</div>
                </a>
            </section>
        </article>
        <!-- 我的财富 END -->
        
        <div class="clear h66"></div>
        
        <!-- 底部 -->
        <#include "/client/common_footer.ftl">
        <!-- 底部 END -->
    </body>
</html>