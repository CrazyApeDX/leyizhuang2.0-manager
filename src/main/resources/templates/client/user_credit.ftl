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
        <link rel="stylesheet" type="text/css" href="/client/css/x_my_wealth.css"/>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/index.js" type="text/javascript"></script>
    </head>
    <body class="bgc-f3f4f6">
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>信用金</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 我的钱包 -->
        <article class="my-wallet">
            <!-- 总资产 -->
            <section class="total-assets">
                <div class="title">信用金（<#if user.credit??>${user.credit?string("0.00")}<#else>0.00</#if>元）</a></div>
            </section>
            <!-- tips -->
            <#--
            <section class="tips">提示：部分返现金额、现金赠额只可购物使用不能提现</section>
            -->
            <!-- 余额明细 -->
            <#if logPage?? && logPage.content?size gt 0>
                <section class="balance-details">
                    <div class="title">信用金明细</div>
                        <#list logPage.content as item>
                        	<div class="details-list">
                                <div class="div1">
                                    <div class="c666">
                                    	<#switch item.type>
                                    		<#case "CONSUME">订单消费<#break>
                                    		<#case "CANCEL">订单取消<#break>
                                    		<#case "REJECT">订单拒签<#break>
                                    		<#case "REPAY">订单还款<#break>
                                    	</#switch>
                                    </div>
                                    <div class="c999">变更前：<#if item.brforeChange??>${item.brforeChange?string("0.00")}<#else>0.00</#if></div>
                                    <div class="c999">变更后：<#if item.afterChange??>${item.afterChange?string("0.00")}<#else>0.00</#if></div>
                                </div>
                                <div class="div2">
                                    <div class="c999"><#if item.changeTime??>${item.changeTime?string("yyyy-MM-dd")}</#if></div>
                                    <!-- 字体颜色：提现为黄色yellow，充值为绿色green -->
                                    <div class="<#if (item.changeValue gt 0)>green<#else>yellow</#if>"><#if item.changeValue??>${item.changeValue?string("0.00")}<#else>0.00</#if></div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </section>
            </#if>
        </article>
        <!-- 我的钱包 END -->
        
        <div class="clear h66"></div>
        
        <!-- 底部 -->
        <#include "/client/common_footer.ftl">
        <!-- 底部 END -->
    </body>
</html>