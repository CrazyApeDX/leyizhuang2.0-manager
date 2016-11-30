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
        <title>退换货</title>
        
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/xiuxiu.css"/>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
		<script src="/client/js/index.js" type="text/javascript"></script>
    </head>
    <script type="text/javascript">
	    window.onload = function(){
	        footer();
	    }
        <#-- 模糊查询订单的方法 -->
        function searchOrder(){
            <#-- 获取查询关键词 -->
            var keywords = $("#keywords").val();
            if("" === keywords){
                return;
            }
            <#-- 开启等待图标 -->
            wait();
            $.ajax({
                url : "/user/return/search",
                type : "post",
                timeout : 20000,
                data:{
                    keywords : keywords
                },
                error:function(){
                    close(1);
                    warning("亲，您的网速不给力啊");
                },
                success:function(res){
                    $("#user_all_order").html(res);
                    close(1);
                }
            });
        }
    </script>
    
    <body style="background: #f3f4f6;">
    
        <#-- 引入公共confirm窗口 -->
        <#include "/client/common_confirm.ftl">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <#-- 引入公共购物方式选择滑动窗口 -->
        <#include "/client/common_shopping_type.ftl">
        <div id="content">
            <div class="sec_header">
                <a class="back" href="/user"></a>
                <p>退换货</p>             
            </div>
            
            <section class="my_order">
                <div class="searchbox bgc-f3f4f6 bdt">
                    <input type="text" id="keywords" placeholder="订单号">
                    <a href="javascript:searchOrder();"></a>
                </div>          
                
                <!-- 订单分类 -->
                <article class="orders-species"> 
                    <div id="user_all_order">
                        <#include "/client/user_all_return.ftl">
                    </div>
                </article>
                <!-- 用户订单 END -->                           
            </section>
            <div class="index_test_box02"></div>
            <#include "/client/common_footer.ftl">
        </div>  
    </body>
</html>
