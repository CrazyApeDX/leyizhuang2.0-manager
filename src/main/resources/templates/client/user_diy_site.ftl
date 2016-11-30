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
        
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/user_diy_site.js" type="text/javascript"></script>
        <style>
            input{
                -webkit-appearance:none;
            }
            
            select{
                -webkit-appearance:none;
            }
            #info_window{
             	overflow: hidden;
			    width: 80%;
			    padding: 0 5%;
			    padding-bottom: 40px;
			    padding-top: 23px;
			    height: 240px;
			    margin: auto;
			    border-radius: 8px;
			    background: white;
			    margin-top: 64px;
            }   
            #site_by_district_div{
		    height: 25px;
		    line-height: 25px;
		    font-size: 0.9em;
		    color: #666;
		    background: #fff;
            border: 1px solid #ddd;
            }
            .swiper-container .stores-list{
            padding: 2%;
		    height: 0.9em;
		    line-height: 0.9em;
		    font-size: 0.6em;
		    color: #666;
		    background: #fff;
            border-bottom: #EEEEEE 1px dashed;
            overflow: hidden;
            font-weight: normal;
            }
            #info_window .swiper-container{
            	border: none;
            	height:180px;
            	overflow:hidden;
            }
            #info_window .btn_no{
            	margin: auto;
			    border: none;
			    outline: none;
			    color: white;
			    width: 60px;
			    height: 26px;
			    border-radius: 4px;
			    background: #cc1421;
			    z-index: 99999;
			    border: none;
            }
            #diy_window{
             	overflow: hidden;
			   	position: fixed;
			    left: 0px;
			    top: 0px;
			    width: 100%;
			    height: 0;
			    background: rgba(0,0,0,0.3);
			    /* display: none; */
			    z-index: 8;
			    padding: 0;
            }
              
        </style>
    </head>
    <body class="bgc-f3f4f6">
        <!--弹窗-->
        <div id="bg"></div>
        <div id="pop-up">
            <div id="diy_title" class="div1">金科云湖天都自提柜</div>
            <div class="div2">门店属性</div>
            <div id="diy_attr" class="div3">直营</div>
            <div class="div2">详细地址</div>
            <div id="diy_address" class="div3">重庆是九龙坡区科园一路（轻轨3号线石桥铺站）</div>
            <div class="div2">电话号码</div>
            <div id="diy_phone" class="div3">15213266666<a href="#">（点击拨打）</a></div>
            <div class="div4">
                <a class="close" onclick="pupclose()">关闭</a>
                <a id="diy_check" class="select" onclick="pupclose()">选择</a>
            </div>
        </div>
         <!--弹窗 END-->
    
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
		<#-- 引入公共导购列表弹窗 -->
		<#include "/client/user_seller_list.ftl">
		
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>门店归属</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 门店归属 -->
        <article class="home-stores">
        
        	<div id="diy_window">
	        	<div id="info_window">
		                <div id="site_by_district_div">
		                    <select id="site_district" onchange="changeDistrict();" style="width:98%;height:25px;font-size:0.9em;color:#666;margin:0 1%;border:none;background:url('/client/images/icon_bottom.png') no-repeat right;">
		                        <#if district_list??>
		                            <#list district_list as item>
		                                <option value="${item.id?c}">${item.name!''}</option>
		                            </#list>
		                        </#if>
		                    </select>
		                </div>
		                <div class="swiper-container">
		                <span class="swiper-wrapper" id="site_by_district" style="margin-top:10px;display:block;" >
		                    <#include "/client/site_in_district.ftl">
		                </span>
		                </div>
		                <div style="position:relative;bottom:8px;left:0px;width:100%;text-align:center;border: none"> 
				            <input class="btn_no" type="button" name="" id="" value="关闭" onclick="info_no()">
				        </div>
		        </div>
	        </div>
            <#if user??>
                <div>当前所属门店：<span id="now_diy">${user.diyName!''}</span></div>
                <div>当前服务导购：<span id="now_seller">${user.sellerName!''}</span></div>
                <input id="diyId" type="hidden" value="${user.upperDiySiteId?c}">
                <input id="sellerId" type="hidden" value="<#if user.sellerId??>${user.sellerId?c}<#else>0</#if>">
            </#if>
            <input style="background-color:#EA5D0E;border-radius:3px;" class="btn-submit-save" type="button" value="修改门店归属" onclick="info_yes(1000);">
            <input style="background-color:#EA5D0E;border-radius:3px;" class="btn-submit-save" type="button" value="修改导购"  onclick="service.getSeller();">
        </article>
        <!-- 门店归属 END -->
        
    </body>
	<script type="text/javascript">
		<#-- 创建一个命名空间 -->
		var service = {
			<#-- 获取导购列表的方法 -->
			getSeller : function(){
				<#-- 判断当前是否是虚拟门店，若是虚拟门店则不能修改导购 -->
				var diyId = $("#diyId").val();
				var sellerId = $("#sellerId").val();
				
				if(0 === sellerId){
					return;
				}
				
				<#-- 开启等待图标 -->
				wait();
				
				$.ajax({
					url : "/user/seller/get",
					type : "post",
					data : {
						diyId : diyId
					},
					timeout : 20000,
					error : function(){
						close(1);
						warning("亲，您的网速不给力啊");
					},
					success : function(res){
						$("#changeInfo").html(res);
						close(1);
						win_yes();
					}
				});
			},
			<#-- 根据关键字查找销顾的方法 -->
			searchSeller : function(){
				var diyId = $("#diyId").val();
				var keywords = $("#keywords").val();
				
				$.ajax({
					url : "/user/seller/search",
					type : "post",
					data : {
						diyId : diyId,
						keywords : keywords
					},
					timeout : 20000,
					error : function(){
						close(1);
						warning("亲，您的网速不给力啊");
					},
					success : function(res){
						$("#changeInfo").html(res);
						close(1);
					}
				});
			},
			<#-- 确定选择销顾的方法 -->
			selectSeller : function(sellerId){
				if(sellerId && !isNaN(sellerId)){
					<#-- 开启等待图标 -->
					wait();
					$.ajax({
						url : "/user/seller/select",
						type : "post",
						data : {
							sellerId : sellerId
						},
						error : function(){
							close(1);
							warning("亲，您的网速不给力啊");
						},
						success : function(res){
							if(0 === res.status){
								close(1);
								var name = res.name;
								$("#now_seller").html(name);
								win_no();
								warning("操作成功");
								$("#sellerId").val(sellerId);
							}else{
								close(1);
								warning(res.message);
							}
						}
					});
				}
			},
			<#-- 显示门店归属方法 -->
			showDiySite : function(){
				$('#site_by_district_div').show();
				$('#site_by_district').show();
				
			}
		};
		
	</script>
</html>