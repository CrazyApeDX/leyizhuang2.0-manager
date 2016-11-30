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
		<title>用户预存款</title>
		
		<link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
		
		<script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
		<script src="/client/js/rich_lee.js" type="text/javascript"></script>
	</head>
	<style type="text/css">
		.sec_header .add{
			float: left;
			height: 50px;
			width: 18%;
			background: url(img/sec_header_icon.png) no-repeat center;
			background-size: 14px;
		}
		.my_add{
			width: 100%;
		}
		.my_add li{
			height: 50px;
			line-height: 50px;
			padding:0 5%;
			background: white;
			margin-top: 10px;
		}
		.my_add li label{
			float: left;
		}
		.my_add li span{
			float: right;
		}
		.my_add li input{
			float: left;
			width: 60%;
			height: 30px;
			border: #EEEEEE 1px solid;
			border-radius: 4px;
			margin-top: 10px;
			margin-left: 30px;
			padding-left: 10px;
		}
		.button_by_dx{
			display: block;
		    margin: 4% auto;
		    width: 96%;
		    height: 40px;
		    line-height: 40px;
		    font-size: 1.5em;
		    color: #fff;
		    background: #cc1421;
		    border-radius: 3px;
		    border:none;
		}
	</style>
	<body style="background: #f3f4f6;">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">
		<div class="sec_header">
			<a class="back" href="/order"></a>
			<p>预存款使用</p>				
		</div>
		<ul class="my_add">
			<li class="li01">
				<label>预存款总额</label>
				<span>￥<#if user??&&user.balance??>${user.balance?string("0.00")}<#else>0.00</#if></span>
			</li>
			<li class="li01">
				<label>本单可使用</label>
				<span>￥<#if max??>${max?string("0.00")}<#else>0.00</#if></span>
			</li>
			<li class="li02">
				<label>当前使用</label>
				<input placeholder="预存款使用额" type="text" id="used_balance" value="<#if order??&&order.actualPay??>${order.actualPay?string("0.00")}<#else></#if>" />
			</li>
		</ul>
		<input type="button" class="button_by_dx" value="确定" onclick="orderBalance.confirm();">	
	</body>
	<script type="text/javascript">
		var orderBalance = {
			balance : <#if user??&&user.balance??>${user.balance?string("0.00")}<#else>0.00</#if>,
			max : <#if max??>${max?string("0.00")}<#else>0.00</#if>,
			getE : function(id){
				return document.getElementById(id);
			},
			confirm : function(){
				var usedBalance = this.getE("used_balance").value;
				
				<#-- 首先判断输入的值是不是一个数字 -->
				if(isNaN(usedBalance)){
					warning("亲，请输入一个正确的数字");
					return;
				}
				
				<#-- 再次判断输入的值是否大于0 -->
				if(usedBalance<0){
					warning("亲，不能输入负数");
					return;
				}
				
				<#-- 再次判断输入的值是否大于了用户的余额 -->
				if(usedBalance > this.balance){
					warning("亲，您输入的值大于了您的余额");
					return;
				}
				
				<#-- 最后判断输入的值是否大于了当前订单的最大使用限额 -->
				if(usedBalance > this.max){
					warning("亲，您当前能使用的预<br>存款最多为"+this.max+"元");
					return;
				}
				<#-- 跳转页面，保存预存款使用的信息 -->
				window.location.href = "/order/balance/confirm?used=" + usedBalance;
			}	
		};
	</script>
</html>
