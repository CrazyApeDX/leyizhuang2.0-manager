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
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_my_wealth.css"/>
        <!-- js -->
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/index.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(function(){
                win_cla();
                footer();
            });
        </script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入公共购物方式选择滑动窗口 
        <#include "/client/common_shopping_type.ftl">
        -->
        <#-- 引入公共警告窗口 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">
        
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>余额充值</p>
            <#--<a class="save" href="#">使用说明</a>-->
        </header>
        <!-- 头部 END -->
        
        <!-- 充值 -->
        <article class="balance-recharge">
            <section class="recharge" style="border:none;">
                <div>
                    <span class="sp1">充值</span>
                    <input type="text" id="cash" placeholder="请输入充值金额">
                    <span class="sp2">元</span>
                </div>
            </section>
            <!-- 充值方式 -->
            <section class="pay-way">
            	<#if payType_list?? && payType_list?size gt 0>
        			<#list payType_list as item>
		                <div class="paylist">
		                    <div class="left">
		                        <img class="way" width="78" height="40" src="${item.coverImageUri!''}" alt="${item.title!''}">
		                    </div>
		                    <div class="zfb">
		                        <div class="div1">${item.title!''}</div>
		                        <div class="div2">${item.info!''}</div>
		                    </div>
		                    <div class="right">
		                    	<#if item_index == 0>
		                        	<img width="30" id="${item.id?c}" height="30" class="check" src="/client/images/x_icon_checked.png" alt="">
		                        <#else>
		                        	<img width="30" id="${item.id?c}" height="30" class="check" src="/client/images/x_icon_check.png" alt="">
		                        </#if>
		                    </div>
		                </div>
	                </#list>
                </#if>
            </section>
            <a class="btn-next" href="javascript:recharge.confirm();">下一步</a>
        </article>
        <script type="text/javascript">
            $(".pay-way .paylist").click(function(){
                $(this).find("img.check").attr("src","/client/images/x_icon_checked.png");
                $(this).siblings().find("img.check").attr("src","/client/images/x_icon_check.png");
                var id = $(this).find("img.check").attr("id");
                if(id){
                	recharge.type[id] = true;
                }
                
               	for(var key in recharge.type){
               		if(key && key !== id){
               			recharge.type[key] = false;
               		}
               	}
            });
        </script>
        <!-- 充值 END -->
        <div class="clear h66"></div>
        <!-- 底部 -->
        <#include "/client/common_footer.ftl">
        <!-- 底部 END -->
    </body>
    <script>
    	<#-- 创建一个命名空间 -->
    	var recharge = {
    		urls : {
    			<#if payType_list?? && payType_list?size gt 0>
    				<#list payType_list as item>
    					${item.id?c} : "${item.title!''}"
    					<#if item_index != (payType_list?size - 1)>
    						,
    					</#if> 
    				</#list>
				</#if>
    		},
    		<#-- 所有的支付方式 -->
    		type : {
    			<#if payType_list?? && payType_list?size gt 0>
    				<#list payType_list as item>
    					<#if item?? && item_index == 0> 
	    					${item.id?c} : true
    					</#if>
    					<#if item?? && item_index != 0> 
    						${item.id?c} : false
    					</#if>
    					<#if item_index != (payType_list?size - 1)>
    						,
    					</#if>
    				</#list>
    			</#if>
    		},
    		<#-- 提交充值申请的方法 -->
    		confirm : function(){
    			<#-- 判断输入的金额是否为一个数字 -->
    			var cash = $("#cash").val();
    			if("" === cash || isNaN(cash)){
    				warning("请填写正确的充值金额");
    				return;
    			}
				
				<#-- 获取支付方式 -->    	
				var pay = 0;		
				for(var key in this.type){
					if(key && this.type[key] && this.type[key] === true){
						pay = key;
					}
				}
				
				
				
				<#-- 获取支付名称 -->
				var title = this.urls[pay];
				
				<#-- 发送异步请求生成充值单 -->
				$.ajax({
					url : "/user/create/recharge",
					type : "post",
					data : {
						money : cash,
						title : title					
					},
					error:function(){
						warning("亲，您的网速不给力啊");
					},
					success:function(res){
						if(0 == res.status){
							switch(title){
								case "微信支付":
								break;
								case "支付宝":
									window.location.href = "/pay/alipay?number=" + res.number + "&type=1";
								break;
								case "银行卡":
									window.location.href = "/pay/union?number=" + res.number + "&type=1";
								break;
							}
						}else{
							warning(res.message);
						}
					}
				});
				
				
    		}
    	}
    </script>
</html>