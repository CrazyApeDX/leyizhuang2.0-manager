<!DOCTYPE html>
<html ng-app="app">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<title>乐易装</title>
        <link rel="stylesheet" type="text/css" href="/client/css/base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <script type="text/javascript">
		    document.getElementsByTagName('html')[0].style.fontSize = window.screen.width/10+'px';
		</script>
	</head>
	<body ng-controller="mainController">
		
		<div id="container">
		</div>
		
		<script src="//cdn.bootcss.com/angular.js/1.4.8/angular.min.js"></script>
		<script src="//cdn.bootcss.com/angular.js/1.4.8/angular-route.min.js"></script>
		<script src="//cdn.bootcss.com/angular-local-storage/0.5.0/angular-local-storage.min.js"></script>
		<script type="text/javascript">
			var app = angular.module('app', []);
			app.controller('mainController', ['$scope', function($scope) {
			}]);
		</script>
	</body>
</html>