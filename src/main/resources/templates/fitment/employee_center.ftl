<!DOCTYPE html>
<html>
	<head>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="copyright" content="" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta charset="utf-8">
		<title>个人中心</title>
		
		<link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
		
		<script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
		<script src="/client/js/index.js" type="text/javascript"></script>
	</head>
	<script type="text/javascript">
		window.onload = function(){
			turn_hei($('.det_banner'),0.8); 
			$('.det_banner img').width($(window).width());
			footer();
		}
	</script>
	<body>
		<div style="background: #f4f4f4;">
            <#include "/client/common_shopping_type.ftl">
			<div class="sec_header">
				<a class="back" href="javascript:history.go(-1);"></a>
				<p>个人中心</p>
				<span class="per_header" onclick="window.location.href='/fit/employee/info'"></span>
			</div>
			<section class="per_center">
				<div class="per_title">
					<dl  onclick="window.location.href='/fit/employee/info'">
						<dt>
							<div>
								<img src="/client/images/per_titleimg01.png"  />
							</div>
						</dt>
						<dd>
							<p>${employee.mobile!''}</p>
							<p>${employee.name!''}</p>
						</dd>
						<dt style="float:right;margin-right:20px;color:white;margin-top:20px;">
						  <label>账号信息、设置></label>
						<dt>
					</dl>
					<#--
					<ul>
						<li>
							<a href="/user/collect">
								<span><#if collect_list??>${collect_list?size}<#else>0</#if></span>
								<p>我的收藏</p>
							</a>
						</li>
						<li>
							<a href="/user/selected">
								<span>${number!'0'}</span>
								<p>我的已选</p>
							</a>
						</li>
						<li>
							<a href="/user/recent">
								<span><#if recent_list??>${recent_list?size}<#else>0</#if></span>
								<p>我的足迹</p>
							</a>
						</li>
					</ul>
					-->				
				</div>
				<div class="per_box">
					<div class="per_order" onclick="window.location.href='/fit/history/0'">
						<p>我的订单</p>						
						<img src="/client/images/index_guide_right.png" />
						<span>查看全部订单</span>
					</div>
					<div class="per_icon">
						<a href="/fit/history/2">
							<span></span>
							<p>待付款</p>
						</a>
						<a href="/fit/history/3">
							<span></span>
							<p>待发货</p>
						</a>
						<a href="/fit/history/4">
							<span></span>
							<p>待收货</p>
						</a>
						<a href="/fit/history/5">
							<span></span>
							<p>已完成</p>
						</a>
					</div>
					<div class="index_test_box"></div>
					
					<#if employee??&&employee.isMain>
						<ul>
							<li>
								<a href="/fit/credit">
									<p>信用金</p>							
									<img src="/client/images/index_guide_right.png" />
									<span></span>
								</a>
							</li>
						</ul>
					</#if>
				</div>
			</section>
			<#include "/fitment/common_footer.ftl">
        </div>      
	</body>
</html>
