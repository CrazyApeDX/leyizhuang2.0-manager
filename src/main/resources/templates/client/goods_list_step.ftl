<!DOCTYPE html>
<html>
    <head>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="copyright" content="" />
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta charset="utf-8">
    <title>乐易装首页</title>
    
    <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
    <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
    
    <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
    <script src="/client/js/index.js" type="text/javascript"></script>
    <script src="/client/js/goods_list.js" type="text/javascript"></script>
    <script src="/client/js/goods_list_step.js" text="text/javascript"></script>
    <script src="/client/js/seller_order.js" type="text/javascript"></script>
    </head>
    <script type="text/javascript">
    window.onload = function(){
    	win_cla();//分类弹窗		
    	fen_scroll();
    	footer();
    	<#if one_level_category_id??>
    	   change(${one_level_category_id?c});
    	</#if>
    };
    </script>
    <body>
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">
        <#-- 引入会员用户信息列表 -->
        <#include "/client/order_user_list.ftl">
        <div>
            <#include "/client/common_shopping_type.ftl">
            <!--
            	作者：rich
            	描述：侧边栏 滑动
            -->
            <div id="color_package_select">
                <#include "/client/color_package.ftl">
            </div>
            
            <#include "/client/common_search_header.ftl">
            
            <section class="fen_contnet">
                <#-- 一级分类的div -->
                <div id="level_one">
                    <#include "/client/level_one_category.ftl">
                </div>
                
                <#-- 二级分类的div -->
                <div id="level_two">
                    <#include "/client/level_two_category.ftl">
                </div>
                
                <div id="goods">
                    <#include "/client/step_goods.ftl">
                </div>
            </section>
            
            <div class="go_buy">
                <a style="background:#ffaa00;width:50%;" href="javascript:addCart();">加入购物车</a>
                <p style="width:50%;" onclick="window.location.href='/user/selected?history=%2Fgoods%2Fstep%2Flist'">我的购物车(<span id="select_num">${selected_number!'0'}</span>)</p>
                <!-- <a href="javascript:seller.checkCart();">去结算</a> -->
            </div>
            <#include "/client/common_footer.ftl">
        </div>		
    </body>
</html>
