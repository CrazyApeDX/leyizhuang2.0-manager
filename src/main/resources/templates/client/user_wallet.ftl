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
        <script type="text/javascript">
            window.onload = function(){
                footer();
            }
        </script>
    </head>
    <body class="bgc-f3f4f6">
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>我的钱包</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 我的钱包 -->
        <article class="my-wallet">
            <!-- 总资产 -->
            <#if user??>
                <section class="total-assets">
                    <div class="title">资金（<#if user.balance??>${user.balance?string("0.00")}<#else>0.00</#if>元）</a></div>
                    <div class="recharge-withdrawal">
                        <div class="div2">
                            <div>不可提现余额：</div>
                            <div><#if user.unCashBalance??>${user.unCashBalance?string("0.00")}<#else>0.00</#if></div>
                        </div>
                        <div class="div1">
                            <div>可提现余额：</div>
                            <div><#if user.cashBalance??>${user.cashBalance?string("0.00")}<#else>0.00</#if><a href="/user/deposit">提现&gt;&gt;</a></div>
                        </div>
                    </div>
                </section>
            </#if>
            <!-- tips -->
            <section class="tips">提示：部分返现金额、现金赠额只可购物使用不能提现</section>
            <!-- 余额明细 -->
            <#if balance_log_page??>
                <section class="balance-details">
                    <div class="title">余额明细</div>
                        <#list balance_log_page.content as item>
                        <#-- 不显示总余额 -->
                        <#if item.balanceType??&&item.balanceType!=0>
                            <#if item.type??&&item.type==0>
                                <div class="details-list">
                                    <div class="div1">
                                        <div class="c666">充值</div>
                                        <#--<div class="c999">${item.balanceTypeName }：<#if item.balance??>${item.balance?string("0.00")}<#else>0.00</#if></div>-->
                                        <div class="c999">变更后余额：<#if item.allLeft??>${item.allLeft?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                    <div class="div2">
                                        <div class="c999"><#if item.finishTime??>${item.finishTime?string("yyyy-MM-dd")}</#if></div>
                                        <!-- 字体颜色：提现为黄色yellow，充值为绿色green -->
                                        <div class="green">+ <#if item.money??>${item.money?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                </div>
                            </#if>
                            <#if item??&&item.type??&&item.type==1>
                                <div class="details-list">
                                    <div class="div1">
                                        <div class="c666">提现</div>
                                        <#--<div class="c999">${item.balanceTypeName }：<#if item.balance??>${item.balance?string("0.00")}<#else>0.00</#if></div>-->
                                        <div class="c999">变更后余额：<#if item.allLeft??>${item.allLeft?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                    <div class="div2">
                                        <div class="c999"><#if item.finishTime??>${item.finishTime?string("yyyy-MM-dd")}</#if></div>
                                        <!-- 字体颜色：提现为黄色yellow，充值为绿色green -->
                                        <div class="yellow">- <#if item.money??>${item.money?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                </div>
                            </#if>
                            <#if item??&&item.type??&&item.type==2>
                                <div class="details-list">
                                    <div class="div1">
                                        <div class="c666">管理员手动修改</div>
                                        <#--<div class="c999">${item.balanceTypeName }：<#if item.balance??>${item.balance?string("0.00")}<#else>0.00</#if></div>-->
                                        <div class="c999">变更后余额：<#if item.allLeft??>${item.allLeft?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                    <div class="div2">
                                        <div class="c999"><#if item.finishTime??>${item.finishTime?string("yyyy-MM-dd")}</#if></div>
                                        <!-- 字体颜色：提现为黄色yellow，充值为绿色green -->
                                        <div class="<#if (item.money gt 0)>green<#else>yellow</#if>"><#if item.money??>${item.money?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                </div>
                            </#if>
                            <#if item??&&item.type??&&item.type==3>
                                <div class="details-list">
                                    <div class="div1">
                                        <div class="c666">订单支付使用</div>
                                        <div style="font-size: 0.9em;"><#if item.orderNumber??>${item.orderNumber }</#if></div>
                                        <#--<div class="c999">${item.balanceTypeName }：<#if item.balance??>${item.balance?string("0.00")}<#else>0.00</#if></div>-->
                                        <div class="c999">变更后余额：<#if item.allLeft??>${item.allLeft?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                    <div class="div2">
                                        <div class="c999"><#if item.finishTime??>${item.finishTime?string("yyyy-MM-dd")}</#if></div>
                                        <!-- 字体颜色：提现为黄色yellow，充值为绿色green -->
                                        <div class="yellow">- <#if item.money??>${item.money?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                </div>
                            </#if>
                            <#if item??&&item.type??&&item.type==4>
                                <div class="details-list">
                                    <div class="div1">
                                        <div class="c666">${item.reason }</div>
                                        <div style="font-size: 0.9em;"><#if item.orderNumber??>${item.orderNumber }</#if></div>
                                        <#--<div class="c999">${item.balanceTypeName }：<#if item.balance??>${item.balance?string("0.00")}<#else>0.00</#if></div>-->
                                        <div class="c999">变更后余额：<#if item.allLeft??>${item.allLeft?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                    <div class="div2">
                                        <div class="c999"><#if item.finishTime??>${item.finishTime?string("yyyy-MM-dd")}</#if></div>
                                        <!-- 字体颜色：提现为黄色yellow，充值为绿色green -->
                                        <div class="yellow">+ <#if item.money??>${item.money?string("0.00")}<#else>0.00</#if></div>
                                    </div>
                                </div>
                            </#if>
                        </#if>
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