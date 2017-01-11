<!DOCTYPE html>
<html>
<head>
        <style type="text/css">
            html,body{width:100%;height: 100%;}
        </style>
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <meta charset="utf-8">
        <title>登录</title>
        
        <link rel="stylesheet" type="text/css" href="/client/css/base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/client/js/rich_lee.js"></script>
    </head>
    <script type="text/javascript">
        document.getElementsByTagName('html')[0].style.fontSize = window.screen.width/10+'px';
    </script>
    <body>
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <header class="top_head">
            <div class="head_back" style="background:none;"></div>
            <div class="head_title">登录</div>
        </header>
        <section class="onload_content">
            <div class="reg_logo"><img src="/client/images/big_logo.png" /></div>
            <form class="on_content" name="loginForm">               
                <dl>
                    <dt><input id="identity" type="text" name="username" ng-pattern="" placeholder="手机号码"/></dt>
                    <dt><input id="password" type="password" name="password" placeholder="用户密码" /></dt>
                    <dd><input id="button" type="button" class="valid" value="登录" /></dd>
	                    <dd>
	                    	<#--
	                    	<a href="/return/password">忘记密码</a><span></span>
	                    	-->
	                    </dd>
	                    <dd>
	                    	<#--
	                    		<a href="/regist">注册</a>
	                    	-->
	                    </dd>
                </dl>                       
            </form>
        </section>
    <script type="text/javascript">
    	$('#button').bind('click', function() {
    		var identity = $('#identity').val();
    		var password = $('#password').val();
    		
    		if (!identity) {
    			return;
    		} else if (!/^1\d{10}$/.test(identity)) {
    			warning('请输入正确的手机号码');
    			return;
    		} else if (!password) {
    			return;
    		}
    		
    		$.ajax({
    			method: 'POST',
    			url: '/fit/session',
    			data: {
    				mobile: identity,
    				password: password
    			},
    			error: function() {
    				warning('亲，您的网速不给力啊');
    			},
    			success: function(result) {
    				if (result.actionCode === 'FAILURE') {
    					warning(result.content);
    				} else {
    					window.location.href = '/fit'
    				}
    			}
    		});
    	});
    </script>
    </body>
</html>