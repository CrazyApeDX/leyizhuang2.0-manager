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
        <!-- 1-7新增文字描述页面样式 本段样式放在x_my_wealth.css末即可-->
        <style>
            .word-title {
                margin-top: 2%;
                padding: 2%;
                width: 96%;
                font-size: 1.2em;
            }
            .word-time {
                margin-bottom: 2%;
                padding: 0 2%;
                width: 96%;
                color: #999;
            }
            .word-description {
                margin: 0 auto;
                padding: 2%;
                width: 92%;
                min-height: 300px;
                background-color: #fff;
            }
        </style>
    </head>
    <body class="bgc-f3f4f6">
    
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>文字描述</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 文字描述 -->
        <div class="word-title">${article.title!''}</div>
        <div class="word-time"><#if article??&&article.createTime??>${article.createTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
        <article class="word-description">${article.content!''}</article>
        <!-- 文字描述 END -->
    
    </body>
</html>