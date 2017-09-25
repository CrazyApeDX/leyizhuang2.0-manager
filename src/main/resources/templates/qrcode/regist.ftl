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
        
        <script type="text/javascript" src="/client/js/angular.js"></script>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/rich_lee.js" type="text/javascript"></script>
        <script type="text/javascript">
	        document.getElementsByTagName('html')[0].style.fontSize = window.screen.width/10+'px';
	    </script>
    </head>
    <body ng-app="qrcode">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <header class="top_head">
            <a href="javascript:history.go(-1);"><div class="head_back"></div></a>
            <div class="head_title">注册</div>
        </header>
        <section class="onload_content" ng-controller="regist">
            <div class="reg_logo"><img src="/client/images/big_logo.png" /></div>
            <form class="reg_content" name="qrcodeForm" ng-submit="qrRegist();">              
                <dl>
                    <dd><input type="text" ng-model="data.name" ng-required="true" ng-minlength="2" ng-maxlength="8" placeholder="姓名" ></dd>
                    <dd><input type="text" name="phone" ng-model="data.phone" ng-pattern="/^1\d{10}$/" placeholder="手机号码" ng-required="true"/></dd>
                    <dd><span id="diyName">推荐人：{{data.sellerName}}</span></dd>
                    <dd><span id="diyName">归属门店：{{data.diySiteName}}</span></dd>
                    <dd><input type="submit" ng-class="{'valid':qrcodeForm.$valid,'invalid':qrcodeForm.$invalid}" value="提交" ng-disabled="qrcodeForm.$invalid" /></dd>
                    <dd></dd>
                </dl>                       
            </form>
        </section>
    <script type="text/javascript">
    	var qrcode = angular.module('qrcode', []);
    	var registCtrl = qrcode.controller('regist', ['$scope', function($scope) {
    		$scope.data = {
    			name: '',
    			phone: '',
    			sellerName: '${seller!''}',
    			diySiteName: '${diySiteName!''}'
    		}
    		
    		$scope.qrRegist = function() {
    			wait();
				$.ajax({
					method: 'POST',
					url: '/qrcode/regist',
					data: this.data,
					timeout: 20000,
					error: function() {
						close(1);
						warning('亲，您的网速不给力啊');
					},
					success: function(res) {
						if (0 === Number(res.status)) {
							window.location.href = '/qrcode/success'; 
						} else {
							close(1);
							warning(res.message);
						}
					}
				});    		
    		}
    	}]);
    </script>
    </body>
</html>
        
