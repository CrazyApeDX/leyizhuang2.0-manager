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
        <link rel="stylesheet" type="text/css" href="/client/css/x_account_settings.css"/>
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
    </head>
    <body class="bgc-f3f4f6">
        
        <!-- 头部 -->
        <header>
            <a class="back" href="/order"></a>
            <p>选择收货地址</p>
            <a class="save fz2" href="/order/add/address" style="font-size:3em;">+</a>
        </header>
        <!-- 头部 END -->
        
        <!-- 选择收货地址 -->
        <dl class="shipping-address">
            <#if address_list??>        
                <#list address_list as item>
                    <dt onclick="window.location.href='/order/address/check/${item.id?c}'">
                        <div class="address">
                            <div class="div1">
                                <span class="c-fd9c11"><#if item.isDefaultAddress??&&item.isDefaultAddress>（默认）</#if></span>
                                <span>${item.receiverName!''}</span>
                                <span>${item.receiverMobile!''}</span>
                            </div>
                            <div class="div2 c999">${item.detailAddress!''}</div>
                        </div>
                        <div class="editable">
                        <a class="a2" href="/user/address/1?returnPage=1&id=${item.id?c}&realUserId=${realUserId?c}">修改</a>
                    </div>
                    </dt>
                </#list>
            </#if>
        </dl>
        <!-- 选择收货地址 END -->
    </body>
</html>