<!DOCTYPE html>
<html lang="zh-CN" class="bgc-f3f4f6">
    <head>
        <style>
            body,html{width:100%;height:100%;overflow:hidden;background:red;}
            #container {width:100%; height: 100%; }
        </style>
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
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=bdd6b0736678f88ed49be498bff86754"></script>
        <script type="text/javascript">
        <#if !map_y??&&!map_x??>
        show();
        </#if>
        function show()
        {alert("暂时没有信息")}
        </script>
        <style type="text/css">
        .info{
        	line-height: 2em;
        	text-indent:2em;
        	background-color: white;
        }
        </style>
    </head>
    <body class="bgc-f3f4f6">
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>快递位置</p>
        </header>
        <div class="info">配送员：<#if user??>${user.realName!'' }</#if></div>
        <div class="info">配送员电话：<#if user??>${user.username!'' }</#if></div>
        <div class="info">配送仓库：${whName!'' }</div>
        <div class="br"></div>
        <div id="container"></div>
        <div id="container"></div>
        <script type="text/javascript">
            var map = new AMap.Map('container',{
            zoom: 13,
            center: [<#if map_y??>${map_y}<#else>116.39</#if>,<#if map_y??>${map_x}<#else>39.9</#if>]
            });
            <#if map_y??&&map_x??>
            var marker = new AMap.Marker({
            position: [<#if map_y??>${map_y}<#else>116.480983</#if>,<#if map_y??>${map_x}<#else>39.989628</#if>],
            map:map
            }); 
            </#if>
       </script>
    </body>
</html>