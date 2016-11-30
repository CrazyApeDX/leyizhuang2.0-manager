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
       
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1)"></a>
            <p>现金卷使用说明</p>
        </header>
        <!-- 头部 END -->
		<div id="cashCouponGuideDiv">
			<#if cashCouponGuide??>${cashCouponGuide!""}</#if>
		</div>
        <#include "/client/common_footer.ftl">
        <!-- 底部 END -->
    </body>
</html>