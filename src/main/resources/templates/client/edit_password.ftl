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
        <script type="text/javascript" src="/client/js/angular.js"></script>
        <script type="text/javascript" src="/client/js/edit_password.js"></script>
    </head>
    <body class="bgc-f3f4f6" ng-app="editPwd">
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
        <article class="change-password" ng-controller="ctrl">
            <form name="editPwdForm" ng-submit="submitForm()">
                <div class="title">输入原密码</div>
                <input class="password" type="password" placeholder="请输入原密码" ng-required="true" ng-minlength="6" ng-maxlength="20" ng-model="param.old_password" name="old_password" id="old_password">
                <div class="title">设置新密码</div>
                <input class="password" type="password" placeholder="请输入新密码" ng-required="true" ng-minlength="6" ng-maxlength="20" ng-model="param.new_password" name="new_password" id="new_password">
                <div class="title">确认新密码</div>
                <input class="password" type="password" placeholder="请确认新密码" ng-required="true" ng-minlength="6" ng-maxlength="20" ng-model="param.re_password" name="re_password" id="re_password">
                <input ng-class={"invalid":editPwdForm.$invalid,"valid":editPwdForm.$valid} ng-disabled="editPwdForm.$invalid" type="submit" style="background-color:#cc1421;" class="btn-submit-save" value="确定修改">
            </form>
        </article>
        <!-- 设置新密码 END -->
    </body>
</html>