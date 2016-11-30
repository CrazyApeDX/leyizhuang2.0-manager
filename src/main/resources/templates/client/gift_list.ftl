<!DOCTYPE html>
<html>
	<head>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="copyright" content="" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta charset="utf-8">
		<title>查看物流</title>
		
		<link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/swiper.min.css"/>
		<script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
		<style>
			.win_yn{
				overflow: hidden;
				position: fixed;
				left: 0px;
				top: 0px;
				width: 100%;
				height: 0px;
				background: rgba(0,0,0,0.3);
				/*display: block;*/
			}
			.win_yn .my_left{
				overflow: hidden;
				width:80%;
				padding: 0 5%;
				padding-bottom: 90px;
				padding-top: 14px;
				height: 230px;
				margin: auto;
				border-radius: 8px;
				background:white;
				margin-top: 64px;
			}
			.win_yn .my_left .close{
				width:100%;
				height: 26px;
				position: absolute;
				bottom:16px;
				left: 0px;
			}
			.win_yn .my_left .sub{
				border: none;
				outline: none;
				color: white;
				width: 60px;
				height: 26px;
				border-radius: 4px;
				background: #cc1421;
				margin: auto;
				-webkit-box-shadow: 0px 1px 2px 1px rgba(204,20,33,0.8);
			}
			.win_yn .my_left .btn_no{
				position: absolute;
				bottom:10px;
				border: none;
				outline: none;
				color: white;
				width: 60px;
				height: 26px;
				border-radius: 4px;
				right: 30px;
				background: #cc1421;
			}
			.win_yn .swiper-slide{
				overflow: hidden;
				line-height: 20px;
				height: 60px;
				border-bottom: #EEEEEE 1px dashed;
			}
			.win_yn .swiper-slide input{
				float: left;
				width: 16px;
				height: 16px;
				margin-top:20px;
			}
			.win_yn .swiper-slide div{
				float: left;
				/*padding:0 2%;*/
				margin-top: 10px;
			}
			.my_left .swiper-container {
		        width: 100%;
		        height: 230px;
		    }
  			/* add419_title */
  			.add419_title{
  				width: 100%;
  				text-align: center;
  				line-height: 30px;
  				font-size: 1.2rem;
  			}
  			.add419_box_title{
  				width: 100%;
  			}
  			.add419_box_title h3{
  				float: left;
  				width: 33.3%;
  			}
  			.add419_box_title h3:nth-of-type(1){
  				text-align: left;
  			}
  			.add419_box_title h3:nth-of-type(2){
  				text-align: center;
  			}
  			.add419_box_title h3:nth-of-type(3){
  				text-align: right;
  			}
  			.add419_box{
  				width: 100%;
  			}
  			.add419_box span{
  				float: left;
  				width: 13%;
  				text-align: center;
  				height: 20px;
  				overflow: hidden;
  			}
  			.add419_box span:nth-of-type(2){
  				padding: 0 2%;
  				width:70%;
  			}
		</style>
	</head>
	<script type="text/javascript">
		function win_yes_gift(){
			$('#gift_window').animate({height:'100%'})
		};
		function win_no_gift(){	
			$('#gift_window').animate({height:0})
		};
		$(function(){
			var le=($('.win_yn .my_left .close').width()-$('.win_yn .my_left .sub').width())/2;
			$('.win_yn .my_left .sub').css({marginLeft:le});
		});
		
	
	</script>
		<div id="gift_window" class="win_yn" style="z-index:999;">
			<div class="my_left" id="gift_left">
				<div class="close">
					<input class="sub" type="submit" name="" id="" value="关闭" onclick="win_no_gift()" />
				</div>
				<div class="add419_title">我的赠品</div>
	    		<div class="add419_box_title">
			    	<h3>编号</h3>
			    	<h3>名称</h3>
			    	<h3>数量</h3>
	    		</div>
		    <div class="swiper-container" id="gift_swiper">
		        <div class="swiper-wrapper">
		        	<#if present??>
		        		<#list present as item>
		        			<#if item??>
					            <div class="swiper-slide">
							    	<div class="box" style="width: 100%;">
							    		<div class="add419_box">
							    			<span>${item.sku!''}</span>
							    			<span>${item.goodsTitle!''}</span>
							    			<span>${item.quantity!'0'}</span>
							    		</div>
							    	</div>
					            </div>
				            </#if>
			            </#list>
	            	</#if>
		        	<#if gift??>
		        		<#list gift as item>
		        			<#if item??>
					            <div class="swiper-slide">
							    	<div class="box" style="width: 100%;">
							    		<div class="add419_box">
							    			<span>${item.sku!''}</span>
							    			<span>${item.goodsTitle!''}</span>
							    			<span>${item.quantity!'0'}</span>
							    		</div>
							    	</div>
					            </div>
				            </#if>
			            </#list>
	            	</#if>
		        </div>
		    </div>
		    <script src="/client/js/swiper.min.js"></script>
		    <script>
			    var swiper = new Swiper('#gift_swiper', {
			        pagination: '#gift_left .swiper-pagination',
			        paginationClickable: true,
			        direction: 'vertical',
			        slidesPerView: 4
			    });
		    </script>
		</div>
	</div>
</html>
