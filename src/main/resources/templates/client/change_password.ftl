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
<style>
.valid{
    background: #cc1421;
}

.invalid{
    background: #999999; 
}
</style>
</head>
<body class="bgc-f3f4f6" ng-app="app" ng-controller="ctrl">

    <#-- 引入警告提示样式 -->
    <#include "/client/common_warn.ftl">
    <#-- 引入等待提示样式 -->
    <#include "/client/common_wait.ftl">
    
    <!-- 头部 -->
    <header>
        <p>设置新密码</p>
    </header>
    <!-- 头部 END -->
    
    <!-- 设置新密码 -->
    <article class="change-password">
        <form name="changeForm" ng-submit="check();">
            <div class="title">设置新密码</div>
            <input class="password" name="password" ng-model="info.password" type="password" ng-minlength="6" ng-maxlength="20" ng-required="true">
            <div class="title">确认新密码</div>
            <input class="password" name="repassword" ng-model="info.repassword" type="password"  ng-minlength="6" ng-maxlength="20" ng-required="true">
            <input class="btn-submit-save" type="submit" ng-class={"valid":changeForm.$valid,"invalid":changeForm.$invalid} value="确定修改" ng-disabled="changeForm.$invalid">
        </form>
    </article>
    <!-- 设置新密码 END -->

</body>
<script type="text/javascript" src="/client/js/angular.js"></script>
<script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
<script type="text/javascript">
    var app = angular.module("app",[]);
    var ctrl = app.controller("ctrl",function($scope){
        $scope.info = {
            username:"${phone!''}",
            password:"",
            repassword:""
        }    
        
        $scope.check = function(){
            if($scope.info.password!==$scope.info.repassword){
                warning("两次输入的密码不一致");
                return;
            }
            
            wait();
            $.ajax({
                url:"/password/save",
                type:"post",
                data:{
                    username:$scope.info.username,
                    password:$scope.info.password
                },
                error:function(){
                  close(1);
                  warning("亲，您的网速不给力啊");  
                },
                success:function(res){
                    close(1);
                    if(-1 === res.status){
                        warning(res.message);
                        return;
                    }
                    warning("修改成功");
                    window.location.href="/";
                }
            })
        }
    });
</script>
</html>