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
		<title>送货上楼</title>
		
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
			<p>送货上楼</p>				
		</div>
		<form id="upstairsForm" method="post" action="/order/upstairs">
			<ul class="my_add">
				<li class="li01">
					<label>上楼方式</label>
					<span>
						<select id="type" name="type" style="height:50px;line-height:50px;font-size:0.9em;margin-right:150px;">
							<option value="不上楼" <#if order.upstairsType=="不上楼">selected="true"</#if>>不上楼</option>
							<option value="步梯" <#if order.upstairsType=="步梯">selected="true"</#if>>步梯</option>
							<option value="电梯" <#if order.upstairsType=="电梯">selected="true"</#if>>电梯</option>
						</select>
					</span>
				</li>
				<li class="li02">
					<label>楼层</label>
					<input id="floor" name="floor" placeholder="楼层" type="text" value="${order.floor!'1'}" />
				</li>
			</ul>
			<input type="button" class="button_by_dx" value="确定" onclick="submitForm();">
		</form>
		<script type="text/javascript">
			var submitForm = function() {
				var floor = $("#floor").val();
				if (!parseInt(floor) == floor || floor < 1) {
					warning("请输入一个正确的楼层数");
				}
				$("#upstairsForm").submit();
			}
		</script>	
	</body>
</html>
