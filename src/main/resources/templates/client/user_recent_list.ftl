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
        <link rel="stylesheet" type="text/css" href="/client/css/x_gu_sales.css"/>
        <!-- js -->
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/client/js/touch-0.2.14.min.js"></script>    
    </head>
    <body class="bgc-f3f4f6">
        <!-- 头部 -->
        <header>
            <a class="back" href="/user"></a>
            <p>浏览记录</p>
        </header>
        <!-- 头部 END -->
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">
        
        <!-- 浏览记录 -->
        <article class="my-collect">
            <ul class="switch-title-group">
                <li <#if selected_rule??&&selected_rule==0>class="current"</#if>><a href="/user/recent?param=0-${rule1!'0'}-${rule2!'0'}-${rule3!'0'}">默认</a></li>
                <li <#if selected_rule??&&selected_rule==1>class="current"</#if>><a href="/user/recent?param=1-${rule1!'0'}-${rule2!'0'}-${rule3!'0'}">按价格</a></li>
                <li <#if selected_rule??&&selected_rule==2>class="current"</#if>><a href="/user/recent?param=2-${rule1!'0'}-${rule2!'0'}-${rule3!'0'}">按销量</a></li>
            </ul>
            <!-- 列表 -->
            <article class="art-list-group">
                <#if recent_list??>
                    <#list recent_list as item>
                        <section class="list-group" id="${item.id?c}">
                            <a class="target-list-group" href="/goods/detail/${item.goodsId?c}">
                                <div class="swipe"></div>
                                <div class="img">
                                    <img src="${item.goodsCoverImageUri!''}" alt="产品图片">
                                </div>
                                <div class="product-info">
                                    <div class="div1">${item.goodsTitle!''}</div>
                                    <label>${item.sku!''}</label>
                                    <div class="div2">￥
                                        <span>
                                        <#if ("priceListItem"+item_index)?eval??&&("priceListItem"+item_index)?eval.salePrice??>
                                            ${("priceListItem"+item_index)?eval.salePrice?string("0.00")}
                                        <#else>
                                            0.00
                                        </#if>
                                        </span>
                                    </div>
                                </div>
                            </a>
                            <a class="btn-backspace" href="#"></a>
                        </section>
                    </#list>
                </#if>
            </article>
        </article>
        <script type="text/javascript">
        $(function touch(){
            $(".list-group .swipe").on('touchmove', function(ev){
                ev.preventDefault(); // 阻止冒泡事件
            });
            // 向左滑动
            $(".list-group .swipe").on('swipeleft', function(ev){
                 $(this).parent().parent().addClass("selected");
            });
            // 向右滑动
            $(".list-group .swipe").on('swiperight', function(ev){
                $(this).parent().parent().removeClass("selected");
            });
            // 点击删除
            $(".btn-backspace").click(function(){
                <#-- 获取id参数 -->
                var id = $(this).parent(".list-group").attr("id");
                <#-- 开启等待图标 -->
                wait();
                $.ajax({
                    url : "/user/delete/recent",
                    method : "post",
                    timeout : 10000,
                    data : {
                        id : id
                    },
                    error : function(XMLHttpRequest, textStatus, errorThrown) {
                        close(100);
                        warning("亲，您的网速不给力啊");
                    },
                    success : function(res) {
                        <#-- 关闭等待图标 -->
                        close(100);
                        if(0 == res.status){
                            $("#" + id).remove();
                        }
                        if(-1 == res.status){
                            warning("亲，您的网速不给力啊");
                            console.debug(res.message);
                        }
                    }
                });         
            });
        });
        </script>
    <!-- 浏览记录 END -->
    </body>
</html>