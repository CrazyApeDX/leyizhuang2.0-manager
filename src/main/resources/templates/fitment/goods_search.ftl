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
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/rich_new.css"/>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/fitment/js/goods_list.js" type="text/javascript"></script>
    </head>
        <script type="text/javascript">
            $(function(){
            	<#--
	                win_cla();//分类弹窗		
	                footer();
	                my_check();//模拟checkbox
	                fen_scroll()
	                win_colo($('.fen_div02 span'));//颜色调理窗口窗口
	                -->
                html_hi();
                function html_hi(){
                    var oHtml = document.getElementsByTagName('html')[0];
                    var win_hi = window.screen.height;
                    var doc_hi =document.documentElement.offsetHeight;
                    if(doc_hi>=win_hi){
                    	oHtml.style.height = doc_hi + 'px';
                    }else{
                    	oHtml.style.height = win_hi + 'px';
                    };
                };
            });
        </script>
    <body style="background: #f3f4f6;height: 100%;">
        <div>
            <#-- 引入警告提示样式 -->
            <#include "/client/common_warn.ftl">
            <#-- 引入等待提示样式 -->
            <#include "/client/common_wait.ftl">  
            <#--
            <#include "/client/common_shopping_type.ftl">
            <!--
            	作者：rich
            	描述：侧边栏 滑动
            -->
            <#-- 引入调色滑动窗口 -->
            <div id="color_package_select">
                <#include "/client/color_package.ftl">
            </div>
            
            <#-- 引入公共的搜索头部 -->
            <#include "/fitment/common_search_header.ftl">
            
            <!--
            	作者：rich
            	描述：头部结束
            -->
            
            <!--
            	作者：rich
            	描述：nav_end
            -->    
            <#--
            <ul class="switch-title-group">
                <li style="width:45%; " <#if selected_rule??&&selected_rule==0>class="current"</#if>><a href="/goods/search?param=0-${rule1!'0'}-${rule2!'0'}-${rule3!'0'}&keywords=${keywords!''}">默认</a></li>
                <li <#if selected_rule??&&selected_rule==1>class="current"</#if>><a href="/goods/search?param=1-${rule1!'0'}-${rule2!'0'}-${rule3!'0'}&keywords=${keywords!''}">按价格</a></li>
                <li style="width:45%; " <#if selected_rule??&&selected_rule==2>class="current"</#if>><a href="/goods/search?param=2-${rule1!'0'}-${rule2!'0'}-${rule3!'0'}&keywords=${keywords!''}">按销量</a></li>
            </ul>
            -->
            
            <div class="new_goodbox" style="margin-bottom: 110px;">					
                <#if goodsList??>
                    <#list goodsList as item>
                        <dl>
                            <dt>
                                <a>
                                    <img src="${item.coverImageUri!''}" />
                                </a>
                            </dt>
                            <dd style="float: left;max-width: 90%;width: 70%">
                                <p style="width: 60%">${item.title!''}</p>
                                <div class="fen_div01" style="float: right;margin-top: 15px;width: auto;">
                                    <#-- 用户存储指定商品的库存 -->
                    				<input type="hidden" id="inventory${item.id?c}" value="<#if item??&&item.inventory??>${item.inventory?c}<#else>0</#if>">
                                    <a href="javascript:changeQuantity(${item.id?c},'add')">+</a>
                                    <input min="0" type="text" class="goodsSelectedQuantity" id="quantity${item.id?c}" value="0" onkeyup="keyup(this)" onafterpaste="afterpaste(this)" onchange="javascript:changeQuantity(${item.id?c});" />
                                    <a href="javascript:changeQuantity(${item.id?c},'delete');">-</a>
                                </div>
                                <div class="fen_div02">
                                    <a>￥${item.price?string("0.00")}</a>
                                </div>
                            </dd>
                        </dl>
                    </#list>
                </#if>
            </div>
            <div class="go_buy">
                <a style="background:#ffaa00;width:50%;" href="javascript:addCart();">加入购物车</a>
                <p style="width: 50%;" onclick="window.location.href='/fit/cart'">我的购物车(<span id="select_num">${selected!'0'}</span>)</p> 
                <!-- <a href="javascript:seller.checkCart();">去结算</a> -->
            </div>
            <#include "/fitment/common_footer.ftl">
        </div>
    </body>
</html>
