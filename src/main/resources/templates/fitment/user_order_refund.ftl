<!DOCTYPE html>
<html>
	<head>
		<style type="text/css">
		</style>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="copyright" content="" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta charset="utf-8">
		<title>申请退货</title>
		
		<link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
		
		<script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
		<script src="/client/js/rich_lee.js" type="text/javascript"></script>
		<script src="/client/js/angular.js" type="text/javascript"></script>
	</head>
	<style type="text/css">
		.fen_goodbox{width: 100%;}
		.fen_goodbox dl:nth-of-type(1){
			margin: 0px;
		}
		.fen_goodbox dl{
			width: 90%;
			padding: 0 5%;
			background: white;
			height: 82px;
			margin-top: 10px;
			float: left;
		}
		.fen_goodbox dl dd{
			max-width: 78%;
			margin-left: 2%;
		}
		.fen_goodbox dl dd .fen_div01{
			float: left;
			width: 50%;
		}
		.fen_goodbox dl dd .fen_div01 a{
			float: left;
		}
		.fen_goodbox dl dd .fen_div01 a:nth-of-type(2){
			border-left: none;
			border-right:#DDDDDD 1px solid ;
			margin: 0px;
		}
		.fen_goodbox dl dd .fen_div01 a:nth-of-type(1){
			border-right: none;
			border-left: #DDDDDD 1px solid;
			margin: 0px;
		}
		.fen_goodbox dl dd .fen_div01 input{
			float: left;
		}
		.fen_goodbox dl dd .fen_div02{
			float: right;
			width: 50%;
			text-align: right;
		}
		.fen_goodbox dl dd .fen_div02 a{
			float: right;
		}
		.fen_goodbox dl dt{
			width: 50px;
			height: 50px;
		}
		.fen_goodbox dl dt a{
			width: 50px;
			height: 50px;
		}
		.fen_goodbox dl dt a img{
			width: 50px;
			height: 50px;
			margin: 0px;
		}
		.sub{
			width: 100%;
			height: 50px;
			background: rgb(204, 20, 33);
			color: white;
			font-size: 1.2em;
			border: none;
			position: fixed;
			left: 0px;
			bottom: 0px;
		}
	</style>
	<script type="text/javascript">
		var data = "";
		window.onload = function(){
			(function(){
				var oHtml = document.getElementsByTagName('html')[0];
				var win_hi =document.documentElement.clientHeight;
				var doc_hi =document.documentElement.offsetHeight;
				if(doc_hi>=win_hi){
					oHtml.style.height = doc_hi + 'px';
				}else{
					oHtml.style.height = win_hi + 'px';
				};
			})();
		};
		var app = angular.module("app",[]);
		var ctrl = app.controller("ctrl",function($scope,$http){
			$scope.goods = [
				<#if order.orderGoodsList??&&order.orderGoodsList?size gt 0>
					<#list order.orderGoodsList as item>
						<#if item??>
							{
								id : ${item.goodsId?c},
								title : "${item.goodsTitle!''}",
								sku : "${item.sku!''}",
								img : "${item.goodsCoverImageUri!''}",
								quantity : <#if item.quantity??>${item.quantity?c}<#else>0</#if>,
								reQuantity : 0,
								price : ${item.realPrice?string("0.00")},
								unit : ${item.realPrice?string("0.00")},
								total : this.quantity * this.unit
							}
							<#-- 判断是否添加逗号 -->
							<#if item_index!=order.orderGoodsList?size-1>
								,
							</#if>
						</#if>
					</#list>
				</#if>
			]
			$scope.remark = "";	
			$scope.total = 0.00;
			$scope.countTotal = function(){
				var all = 0.00;
				for(var i = 0; i < $scope.goods.length; i++){
					if($scope.goods[i] && $scope.goods[i].unit && $scope.goods[i].reQuantity){
						all += ($scope.goods[i].unit * $scope.goods[i].reQuantity);
					}
				}
				$scope.total = all.toFixed(2);
			}	
			$scope.delete = function(index){
				var number = this.goods[index].reQuantity;
				if(number > 0){
					this.goods[index].reQuantity = Number(this.goods[index].reQuantity) - 1;
					this.goods[index].total -= this.goods[index].unit;
					this.countTotal();
				}
			}
			
			$scope.add = function(index){
				var number = this.goods[index].reQuantity;
				if(number < this.goods[index].quantity){
					this.goods[index].reQuantity = Number(this.goods[index].reQuantity) + 1;
					this.goods[index].total += this.goods[index].unit;
					this.countTotal();
				}
			}
			
				
			$scope.change = function(index){
				if($('#'+index)[0].value.length>=1){
					$('#'+index)[0].value=$('#'+index)[0].value.replace(/[^1-9]/g,'');
				}else{
					$('#'+index)[0].value=$('#'+index)[0].value.replace(/\D/g,'');
				}
				var number = $('#'+index).val();
				if(number <= this.goods[index].quantity && number!=""){
					this.goods[index].reQuantity = number;
					this.goods[index].total = this.goods[index].unit*number;
					this.countTotal();
				}else{
					$('#'+index).val(0);
					this.goods[index].reQuantity = 0;
					this.goods[index].total = 0;
					this.countTotal();
				}
			}
			
			$scope.get_return_detail = function(){
				$("#info_detail").html("");
				$("#info_detail").append("<li> ￥" + $scope.total + " 信用金</li>");
				windowoc('win_yn_return',true);
			}

			$scope.send = function(){
				wait();
				
				data = "";
				for(var i = 0; i < this.goods.length; i++){
					if(0 !== this.goods[i].reQuantity){
						data += (this.goods[i].id + "-" + this.goods[i].reQuantity + "-" + this.goods[i].unit + ".");
					}
				}
				if("" === data){
					close(1);
					win_no_return();
					warning("请填写要退商品数量");
					return;
				}
				
				<#--
				var remark = document.getElementById("remark").value;
				
				if (!remark) {
					close(1);
					warning('请输入退货原因');
					return;
				}
				
				if (remark.length > 255) {
					close(1);
					warning('退货原因最多只能有255个字符');
					return;
				}
				-->
				
				window.location.href = "/fit/refund/address/${order.id?c}/" + data
				
				$.ajax({
					type:"post",
					url:"/fit/order/refund",
					data:{
						id : ${order.id?c},
						infos : data
					},
					error:function(){
						close(1);
						warning("亲，您的网速不给力啊");
					},
					success:function(res){
						
					}
				});
			}
		});
		
	</script>
	<script>
        function win_no_return(){  
            $('.turn_div').fadeOut(600);
        };
        
		function order_return(){
            var he = ($(window).height() - $('.turn_div div').height())/2 - 50;
            $('.turn_div div').css({marginTop:he});
            $('.turn_div').fadeIn(600);
        };
	</script>
	<body style="background: #f3f4f6;height: 100%;" ng-app="app" ng-controller="ctrl">
		<#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
       	<#include "/client/common_wait.ftl"> 
       	<#-- 引入退款明细窗口 -->
		<#include "/client/return_detail_list.ftl">
   	    <div class="turn_div">
			<input type="hidden" value="" id="orderId" name="id">
	        <div style="height:205px;margin-top:160px;">                   
	            <p id="title">退货原因</p>
	            <#--
	            <select name="remark" id="remark" style="width:100%;outline:none;line-height:30px;-webkit-appearance:none;font-size:0.8em;padding:5px;">
	            	<#if reason_list??>
	            		<#list reason_list as item>
			            	<option value="${item.title!''}">${item.title!''}</option>
		            	</#list>
	            	</#if>
	            <select>
	            -->
	            
	            <textarea name="remark" id="remark" style="background-color:#f3f4f6"></textarea>

	            <span>
	                <input type="button" name="" id="" value="是" ng-click="send();" />
	                <input onclick="javascript:win_no_return();" type="button" value="否" />
	            </span>             
	        </div>
	    </div>
		<div class="sec_header">
			<a href="javascript:history.go(-1);" class="back"></a>
			<p>申请退货</p>	
			<a></a>
		</div>
		<div class="fen_goodbox" style="margin-bottom: 50px;">				
			<dl ng-repeat="item in goods">
				<dt>
					<a>
						<img src="{{item.img}}">
					</a>
				</dt>
				<dd>
					<p>{{item.title}}（{{item.sku}}）</p>
					<div class="fen_div01">
						<a ng-click="delete($index);">-</a>
						<input type="text" name="" id="{{$index}}" ng-model="item.reQuantity" ng-keyup="change($index,this);">
						<a ng-click="add($index);">+</a>
					</div>
					<div class="fen_div02">
							<a>￥{{item.unit}}</a>
					</div>
				</dd>
			</dl>
			<#--	
			<span style="
				margin:10px;
				float:left;
				color:#cc1421;
				display:block;
			">总金额：￥{{total | number:2}}</span>
			-->
			<div class="sub" style="position:fixed;">
			<input style="width:50%;height:100%;border:none;background:#ffaa00;color:white;font-size:1em;float:left;display:block;" type="button" ng-click="get_return_detail();" value="退款明细" />
			<input style="width:50%;height:100%;border:none;background:#cc1421;color:white;font-size:1em;float:left;display:block;" type="button" ng-click="send();" value="去退货" />
			</div>
		</div>
	
	</body>
</html>
