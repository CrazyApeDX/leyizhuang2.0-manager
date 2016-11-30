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
        <!-- js -->
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/client/js/order_pay_type.js"></script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl"> 
        <!-- 头部 -->
        <header>
            <a class="back" href="/order"></a>
            <p>支付方式</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 充值 -->
        <article class="balance-recharge">
            <!-- 充值方式 -->
            <#if pay_type_list??>
                <section class="pay-way">
                    <#list pay_type_list as item>
                        <div class="paylist">
                            <div class="left pre-deposit">
                                <img class="way" width="78" height="40" src="${item.coverImageUri!''}" alt="支付方式">
                            </div>
                            <div class="zfb">
                                <div class="div1"><span>${item.title!''}</span></div>
                                <div class="div2">${item.info!''}</div>
                            </div>
                            <div class="right">
                                <img width="30" id="${item.id?c}" height="30" class="check" <#if payTypeId??&&payTypeId?c==item.id?c>src="/client/images/x_icon_checked.png"<#else>src="/client/images/x_icon_check.png"</#if> alt="">
                            </div>
                        </div>
                    </#list>
                    <#if cashOndelivery??>
                        <div id="cashOndelivery" class="paylist" <#if cashOndelivery.id??&&payTypeId??&&cashOndelivery.id?c==payTypeId?c>style="display:block"<#else>style="display:none;"</#if>>
                            <div class="left pre-deposit">${cashOndelivery.title!''}</div>
                            <div class="right" style="float:right;">
                                <img width="30" id="${cashOndelivery.id?c}" height="30" class="check" <#if cashOndelivery.id??&&payTypeId??&&cashOndelivery.id?c==payTypeId?c>src="/client/images/x_icon_checked.png"<#else>src="/client/images/x_icon_check.png"</#if> alt="">
                            </div>
                        </div>
                    </#if>
                    <a class="btn-next" href="javascript:confirm();">确定</a>
                </section>
            </#if>
            <#-- 使用一个隐藏标签用于存储当前选中的支付方式 -->
            <input type="hidden" id="selectedType" value="<#if payTypeId??>${payTypeId?c}</#if>">
        </article>
        <script type="text/javascript">   
            $(".pay-way .paylist").click(function(){
                $(this).find("img.check").attr("src","/client/images/x_icon_checked.png");
                <#-- 切换隐藏标签的值 -->
                var id = $(this).find("img.check").attr("id");
                $("#selectedType").val(id);
                $(this).siblings().find("img.check").attr("src","/client/images/x_icon_check.png");
            });
        </script>
        <!-- 充值 END -->
        <#-- 以局部刷新的方式来获取货到付款 -->
        <#if cashOndelivery??>
            <a class="next-page" href="javascript:cashOnDelivery();">其他支付</a>
        </#if>
    </body>
</html>