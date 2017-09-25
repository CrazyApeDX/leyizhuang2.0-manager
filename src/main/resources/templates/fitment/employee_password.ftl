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
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>设置新密码</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 设置新密码 -->
        <article class="change-password">
            <form name="editPwdForm">
                <div class="title">输入原密码</div>
                <input class="password" type="password" placeholder="请输入原密码" name="oldPassword" id="oldPassword">
                <div class="title">设置新密码</div>
                <input class="password" type="password" placeholder="请输入新密码" name="newPassword" id="newPassword">
                <div class="title">确认新密码</div>
                <input class="password" type="password" placeholder="请确认新密码" name="rePassword" id="rePassword">
                <input type="button" style="background-color:#cc1421;" class="btn-submit-save" value="确定修改" onclick="javascript:editPassword();">
            </form>
        </article>
        <!-- 设置新密码 END -->
        <script type="text/javascript">
        	var editPassword = function() {
        		var oldPassword = $("#oldPassword").val();
        		var newPassword = $("#newPassword").val();
        		var rePassword = $("#rePassword").val();
        		
        		if (!oldPassword) {
        			warning("请输入原密码");
        			return;
        		} else if (!newPassword) {
        			warning("请输入新密码");
        			return;
        		} else if (!rePassword) {
        			warning("请确认新密码");
        			return;
        		} else if (oldPassword.length < 6 || oldPassword.length > 20) {
        			warning("原密码的长度为6-20位");
        			return;
        		} else if (newPassword.length < 6 || newPassword.length > 20) {
        			warning("新密码的长度为6-20位");
        			return;
        		} else if (newPassword !== rePassword) {
        			warning("两次输入的密码不一致");
        			return;
        		}
        		
        		wait();
        		$.ajax({
        			url: "/fit/password",
        			method: "POST",
        			data: {
        				oldPassword: oldPassword,
        				newPassword: newPassword
        			},
        			success: function(res) {
        				if (res.actionCode === "SUCCESS") {
        					window.location.href = "/fit/employee";
        				} else {
        					close(1);
        					warning(res.content);
        				}
        			}
        		})
        		
        	}
        </script>
    </body>
</html>