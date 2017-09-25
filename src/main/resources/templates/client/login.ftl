<!DOCTYPE html>
<html>
<head>
        <style type="text/css">
            html,body{width:100%;height: 100%;}
        </style>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="copyright" content="" />
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <meta charset="utf-8">
        <title>登录</title>
        
        <link rel="stylesheet" type="text/css" href="/client/css/base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        
        <script type="text/javascript" src="/client/js/angular.js"></script>
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/client/js/rich_lee.js"></script>
        <script>
            var app = angular.module("app",[]);
            app.config(function($httpProvider){
                $httpProvider.defaults.transformRequest = function(data){
                    return $.param(data);
                }
            });
            var ctrl = app.controller("ctrl",function($scope,$http){
                $scope.user = {
                    username : "",
                    password : ""
                };
                $scope.submitForm = function(){
                    wait();
                    $.ajax({
                        url:"/login/check",
                        type:"post",
                        timeout:10000,
                        data:{
                            username:$scope.user.username,
                            password:$scope.user.password
                        },
                        error:function(XMLHttpRequest, textStatus, errorThrown){
                           close(1);
                           warning("亲，您的网速不给力啊");   
                        },
                        success:function(res){
                            if(0 == res.status){
                                window.location.href = "/";
                            }else{
                                close(1);
                                warning(res.message);
                            }
                        }
                    });
                }
                $scope.regist = function(){
                    window.location.href = "/regist"
                }
            });
        </script>
    </head>
    <script type="text/javascript">
        document.getElementsByTagName('html')[0].style.fontSize = window.screen.width/10+'px';
    </script>
    <body ng-app="app">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <header class="top_head">
            <div class="head_back" style="background:none;"></div>
            <div class="head_title">登录</div>
        </header>
        <section class="onload_content" ng-controller="ctrl">
            <div class="reg_logo"><img src="/client/images/big_logo.png" /></div>
            <form class="on_content" name="loginForm" ng-submit="submitForm()">               
                <dl>
                    <dt><input type="text" name="username" ng-model="user.username" ng-pattern="/^1\d{10}$/" placeholder="手机号码" ng-required="true" /></dt>
                    <dt><input type="password" name="password" ng-model="user.password" ng-minlength="6" placeholder="用户密码" ng-maxlength="20"  ng-required="true"/></dt>
                    <dd><input type="submit" ng-class="{'valid':loginForm.$valid,'invalid':loginForm.$invalid}" ng-disabled="loginForm.$invalid" value="登录" /></dd>
                    <dd><a href="/return/password">忘记密码</a><span></span></dd>
                    <dd><a href="/regist">注册</a></dd>
                </dl>                       
            </form>
        </section>
    </body>
</html>