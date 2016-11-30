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
    .invalid {
        border-color:#99999;
        color:#99999;
    }
    
    .valid{
        color: #cc1421;
        border-color: #cc1421;
    }
    
    .check{
        background: #cc1421;
    }
    
</style>
</head>
<body class="bgc-f3f4f6" ng-app="app">
    <#-- 引入警告提示样式 -->
    <#include "/client/common_warn.ftl">
    <#-- 引入等待提示样式 -->
    <#include "/client/common_wait.ftl">  
    <!-- 头部 -->
    <header>
        <a class="back" href="javascript:history.go(-1);"></a>
        <p>找回密码</p>
    </header>
    <!-- 头部 END -->
    
    <!-- 验证码 -->
    <article class="security-code" ng-controller="ctrl">
        <form name="passwordForm" ng-submit="check();">
            <div class="get-code">
                <label>手机号码</label>
                <input ng-model="info.phone" name="phone" type="text" placeholder="您的手机号码" ng-pattern="/^1\d{10}$/" ng-required="true">
            </div>
            <div class="get-code">
                <label>短信验证码</label>
                <input ng-model="info.smscode" name="code" type="text" placeholder="短信验证码" ng-minlength="4" ng-maxlength="4" ng-required="true">
                <input type="button" value="发送验证码" ng-click="test();" id="btn-send-code" ng-class={"invalid":passwordForm.phone.$invalid,"valid":passwordForm.phone.$valid} ng-disabled="passwordForm.phone.$invalid">
            </div>
            <input type="submit" value="下一步" ng-class={"check":passwordForm.$valid&&isTouch} class="btn-submit-save" ng-disabled="passwordForm.$invalid">
        </form>
    </article>
    <!-- 验证码 END -->
</body>
<script type="text/javascript" src="/client/js/angular.js"></script>
<script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
<script type="text/javascript">
    var app = angular.module("app",[]);
    var ctrl = app.controller("ctrl",function($scope,$http){
        <#-- 定义基础数据 -->
        $scope.isTouch = false;        
        $scope.time = 60;
        $scope.info = {
            phone:"",
            smscode:"",
        }
        
        <#-- 发送验证码成功之后的倒计时 -->
        $scope.changeSms = function(){
            $("#btn-send-code").val("重试（"+$scope.time+"）");
            $scope.time = $scope.time-1;
            if(-1 == $scope.time){
                $("#btn-send-code").val("发送验证码");
                $scope.time = 60;
            }else{
                setTimeout($scope.changeSms,1000);
            }
        }
        
        <#-- 发送验证码 -->
        $scope.test = function(){
            if(60!==$scope.time){
                return;
            }
            $scope.isTouch = true;
            <#-- 开启等待图标 -->
            wait();
            $.ajax({
                url:"/password/code",
                type:"post",
                data:{
                    phone:$scope.info.phone
                },
                error:function(){
                   close(1);
                   warning("亲，您的网速不给力啊");
                },
                success:function(res){
                    close(1);
                    <#-- 判断验证码是否发送成功 -->
                    close(1);
                    if(0 == res.status){
                        if(00==res.code){
                            setTimeout($scope.changeSms,1000);
                        }else{
                            warning("验证码发送失败！")
                            console.debug(res.code);
                        }
                    }else{
                        close(1);
                        warning(res.message);
                    }                 
                }
            });
            
            <#-- 提交数据进行验证 -->
            $scope.check = function(){
                wait();
                $.ajax({
                    url:"/sms/check",
                    type:"post",
                    data:{
                        phone:$scope.info.phone,
                        code:$scope.info.smscode
                    },
                    error:function(){
                        close(1);
                        warning("亲，您的网速不给力啊");
                    },
                    success:function(res){
                        close(1);
                        if(-1 == res.status){
                            warning(res.message);
                            return;
                        }
                        window.location.href="/change/password?phone="+res.phone;
                    }
                });
            }
        }
    });
</script>
</html>