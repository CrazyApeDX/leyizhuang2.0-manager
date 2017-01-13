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
        <script src="/client/js/seller_order.js" type="text/javascript"></script>
    </head>
    <script type="text/javascript">
        $(function(){
            win_colo_temp();//颜色调理窗口窗口
            <#-- win_cla();//分类弹窗-->        
            footer();//底部居中
            var aBox = $('.lei_box01');
            var aBtn = $('.lei_box');
            for(var i=0;i<aBtn.length;i++){
                go(aBtn[i]);
            };
            function go(obj){
                var oBtn = obj.children[0];
                var aShow = obj.getElementsByTagName('div'); 
                var onOff = true;
                for(var i=0;i<aShow.length;i++){
                    aShow[i].style.display = 'none';
                };
                oBtn.onclick = function(){
                    if(onOff){
                        for(var i=0;i<aShow.length;i++){
                        aShow[i].style.display = 'block';
                            console.log(0)
                        };
                    }else{
                        for(var i=0;i<aShow.length;i++){
                            aShow[i].style.display = 'none';
                        };  
                    };
                    onOff=!onOff;
                };
            };
        });
        
        function getGoods(id){
            var classes = $("#"+id+"div").attr("class");
            if(classes.indexOf("empty") > -1){
                <#-- 开启等待图标 -->
                wait();
                $.ajax({
                    url:"/goods/normal/get",
                    type:"post",
                    timeout:10000,
                    data:{
                        categoryId:id
                    },
                    error:function(){
                        close(1);
                        warning("亲，您的网速不给力啊");
                    },
                    success:function(res){
                        close(1);
                        $("#"+id+"div").html(res);
                        $("#"+id+"div").removeClass("empty");
                        $("#"+id+"div").addClass("unEmpty");
                        $("#"+id+"div").addClass("visible");
                    }
                });
            }else{
                if(classes.indexOf("visible") > -1){
                    $("#"+id+"div").css("display","none");
                    $("#"+id+"div").removeClass("visible");
                }else{
                    $("#"+id+"div").css("display","block");
                    $("#"+id+"div").addClass("visible");
                }
            }
        }
    </script>
    <body>
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">   
        <#-- 引入会员用户信息列表 -->
        <#include "/client/order_user_list.ftl">
        <div>
            <#-- 引入下单方式选择滑动窗 -->
            <#include "/client/common_shopping_type.ftl">
            <!--
                作者：rich
                描述：侧边栏 滑动
            -->
            <div id="color_package_select">
                <#include "/client/color_package.ftl">
            </div>
            <!--
                描述：调颜色
            -->
            <#include "/client/common_search_header.ftl">
            <!--
                作者：rich
                描述：头部结束
            -->
            <#if level_one_categories??>
                <#-- 遍历一级分类 -->
                <#list level_one_categories as level_one>
                    <section class="lei_box">
                        <h3 class="lei_title">${level_one.title!''}</h3>
                        <#if ("level_two_categories"+level_one_index)?eval??>
                            <#-- 遍历二级分类 -->
                            <#list ("level_two_categories"+level_one_index)?eval as level_two>
                                <div class="lei_box01">
                                    <div class="box01_title" onclick="getGoods(${level_two.categoryId?c});">${level_two.title!''}</div>
                                    <div id="${level_two.categoryId?c}div" class="empty">
                                    </div>
                                </div>
                            </#list>
                        </#if>
                    </section>
                </#list>
            </#if>
            <div class="index_test_box03"></div>
            
            
            <div class="go_buy">
                <a style="background:#ffaa00;width:50%;" href="javascript:addCart();">加入购物车</a>
                <p style="width: 50%;" onclick="window.location.href='/user/selected?history=%2Fgoods%2Fnormal%2Flist'">我的购物车(<span id="select_num">${selected_number!'0'}</span>)</p> 
                <!-- <a href="javascript:seller.checkCart();">去结算</a> -->
            </div>
            <#include "/client/common_footer.ftl">
        </div>      
    </body>
</html>
