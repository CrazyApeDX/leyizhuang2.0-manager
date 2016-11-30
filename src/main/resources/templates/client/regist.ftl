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
        <title>注册</title>
        
        <link rel="stylesheet" type="text/css" href="/client/css/base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        
        <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=bdd6b0736678f88ed49be498bff86754"></script>
        <script type="text/javascript" src="/client/js/map.js"></script>
        <script type="text/javascript" src="/client/js/angular.js"></script>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/rich_lee.js" type="text/javascript"></script>
        <script>
            window.onload=function(){
                showCityInfo();
            }
            
            function changeCity(){
                var optionvalue = $('.reg_content dt select').find('option:selected').text();
                console.debug(optionvalue);
                $('#my_box').text(optionvalue);
            }
            //创建一个包含所有城市信息的数组
            var cities = [
                <#if regions??>
                    <#list regions as item>
                        "${item.cityName!''}",
                    </#list>
                </#if>
            ];     
            
            var app = angular.module("regist",[]);
            var ctrl = app.controller("ctrl",function($scope,$http,$location){
                $scope.regions = [
                    <#if regions??>
                        <#list regions as item>
                            {name:"${item.cityName!''}"},
                        </#list>
                    </#if>
                ];
                
                
                $scope.time = 0;
                
                $scope.infos = [{
                    phone:"",
                    name:"",
                    code:"",
                    password:"",
                    repassword:"",
                    referPhone:"",
                    identityType: 0,
                    diySiteName:"归属门店：默认门店"
                }]
                
                $scope.changeSms = function(){
                    $("#sendSys").val("重新获取（"+$scope.time+"）");
                    $scope.time = $scope.time-1;
                    if(-1 == $scope.time){
                        $("#sendSys").val("发送验证码");
                        $scope.time = 0;
                    }else{
                        setTimeout($scope.changeSms,1000);
                    }
                }
                
                $scope.checkRefer = function(){
                    var check = /^1\d{10}$/;
                    if(!check.test($scope.infos.referPhone)){
                        return;
                    }
                    var cityInfo = $("#my_box").html();
                    var data = {
                        referPhone:$scope.infos.referPhone,
                        cityInfo:cityInfo
                    };
                    var config = {};
                    wait();
                    if(check.test($scope.infos.referPhone)){
                        $.post("/regist/refer/check",data,function(res){
                            close(500);
                            $("#diyName").html("归属门店："+res.message);
                        });
                    }
                }
                
                $scope.smscode = function(){
                    var cityInfo = $("#my_box").html();
                    if(0 != $scope.time){
                        return;
                    }
                    $scope.time = 60;
                    wait();
                    $.ajax({
                        url:"/regist/send/code",
                        type:"post",
                        data:{
                            cityInfo:cityInfo,
                            phone:$scope.infos.phone
                        },
                        timeout:10000,
                        error:function(XMLHttpRequest, textStatus, errorThrown){
                            close(1);
                            $scope.time = 0;
                            warning("亲，您的网速不给力啊！");
                        },
                        success:function(res){
                            close(1);
                            if(0 == res.status){
                                if(00==res.code){
                                    setTimeout($scope.changeSms,1000);
                                }else{
                                    $scope.time = 0;
                                    warning("验证码发送失败！")
                                    console.debug(res.code);
                                }
                            }else{
                                close(1);
                                $scope.time = 0;
                                warning(res.message);
                            }
                        }
                    });
                }
                
                <#-- 提交注册表单的方法 -->
                $scope.submitForm = function(){
                    var cityInfo = $("#my_box").html();
                    var config = {};
                    wait();
                    console.log($scope.infos.password);
                    $.ajax({
                        url:"/regist/save",
                        type:"post",
                        data:{
                            cityInfo:cityInfo,
                            name:$scope.infos.name,
                            phone:$scope.infos.phone,
                            code:$scope.infos.code,
                            password:$scope.infos.password,
                            repassword:$scope.infos.repassword,
                            referPhone:$scope.infos.referPhone,
                            diySiteName:$scope.infos.diySiteName,
                            identityType: $scope.infos.identityType
                        },
                        timeout:10000,
                        error:function(XMLHttpRequest, textStatus, errorThrown){
                            close(1);
                            warning("亲，您的网速不给力啊！");
                        },
                        success:function(res){
                            close(1);
                            if(0==res.status){
                                window.location.href="/";
                            }else{
                                warning(res.message);
                            }
                        }
                        
                    });
                }
            });
        </script>
    </head>
    <script type="text/javascript">
        document.getElementsByTagName('html')[0].style.fontSize = window.screen.width/10+'px';
    </script>
    <body ng-app="regist">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <div id="mapContainer" style="display:none;"></div>
        <header class="top_head">
            <a href="javascript:history.go(-1);"><div class="head_back"></div></a>
            <div class="head_title">注册</div>
        </header>
        <div id="map" style="display:block"></div>
        <section class="onload_content" ng-controller="ctrl">
            <div class="reg_logo"><img src="/client/images/big_logo.png" /></div>
            <form class="reg_content" name="registForm" ng-submit="submitForm()">              
                <dl>
                    <dt>
                        <div class="my_sele" id="my_box">定位中...</div>
                        <select onchange="changeCity();" calss="my_box" id="my_city">
                            <#if regions??>
                                <#list regions as item>
                                    <option value="${item.cityName!''}">${item.cityName!''}</option>
                                </#list>
                            </#if>
                        </select>
                    </dt>
                    <dd><input type="text" ng-model="infos.name" ng-required="true" ng-minlength="2" ng-maxlength="8" placeholder="姓名" ></dd>
                    <dd><input type="text" name="phone" ng-model="infos.phone" ng-pattern="/^1\d{10}$/" placeholder="手机号码" ng-required="true"/></dd>
                    <dt>
                        <input type="text" name="code" ng-model="infos.code" ng-minlength="4" ng-maxlength="4" placeholder="手机验证码" ng-required="true"/>
                        <input id="sendSys" class="sms_button" type="button" ng-click="smscode()" ng-class="{'sms_valid':registForm.phone.$valid,'sms_invalid':registForm.phone.$invalid}" ng-disabled="registForm.phone.$invalid" value="发送验证码" />
                    </dt>
                    <dd><input type="password" name="password" ng-model="infos.password" placeholder="默认密码：123456（可修改）" ng-minlength="6" ng-maxlength="20" /></dd>
                    <dd><input type="password" name="repassword" ng-model="infos.repassword" placeholder="重复密码：123456（可修改）" ng-minlength="6" ng-maxlength="20" /></dd>
                    <dd><input type="text" name="referPhone" ng-model="infos.referPhone" ng-change="checkRefer();" placeholder="推荐人电话" /></dd>
                    <dd><span id="diyName">归属门店：</span></dd>
                    <dd>
	                	<input type="radio" style="width:5%;display:block;float:left;" value="0" ng-model="infos.identityType" name="identityType" ng-checked="true"> <label style="display:block;float:left;margin-top:5%">&nbsp;&nbsp;会员</label>
	                	<input type="radio" style="width:5%;display:block;float:left;margin-left:5%;" value="1" ng-model="infos.identityType" name="identityType"> <label style="display:block;float:left;margin-top:5%">&nbsp;&nbsp;非会员</label>
	                	<#--
	                	<input type="radio" style="width:5%" value="1" ng-model="infos.identityType" name="identityType"> 非会员
	                	-->
            		</dd>
                    <dd><input type="submit" ng-class="{'valid':registForm.$valid,'invalid':registForm.$invalid}" ng-disabled="registForm.$invalid" value="注册" /></dd>
                    <dd></dd>
                </dl>                       
            </form>
        </section>
    </body>
</html>
        
