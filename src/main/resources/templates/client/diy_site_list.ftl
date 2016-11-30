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
        <link rel="stylesheet" type="text/css" href="/client/css/x_order_manage.css"/>
        <style>
            .stores-select .hide-img .address {width: 100%;}
        </style>
        <!-- js -->
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
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
                <a class="close" style="width:100%;" onclick="pupclose()">关闭</a>
            </div>
        </div>
        <script type="text/javascript">
            var diysite_arry = [
                <#if diysite_list??>
                    <#list diysite_list as item>
                        {
                            "title":"${item.title!''}",
                            "isDirect":<#if item.status??>
                                           <#switch item.status>
                                               <#case 0>"直营"<#break>
                                               <#case 1>"加盟"<#break>
                                               <#case 2>"虚拟"<#break>
                                               <#case 3>"第三方"<#break>
                                           </#switch>
                                       <#else>
                                            "无"
                                       </#if>,
                            "address":"${item.address!'无'}",
                            "serviceTele":"${item.serviceTele!'00000000000'}"
                        }
                        <#if (item_index+1)!=diysite_list?size>
                            ,
                        </#if>
                    </#list>
                </#if>
            ]
            $("#bg").height($(window).height());
            function pupopen(index){
                <#-- 设置属性 -->
                document.getElementById("diy_title").innerHTML = diysite_arry[index].title;
                document.getElementById("diy_attr").innerHTML = diysite_arry[index].isDirect;
                document.getElementById("diy_address").innerHTML = diysite_arry[index].address;
                document.getElementById("diy_phone").innerHTML = diysite_arry[index].serviceTele + "<a href='tel://"+ diysite_arry[index].serviceTele +"'>(点击拨打)</a>";
                
                
                document.getElementById("bg").style.display="block";
                document.getElementById("pop-up").style.display="block" ;
            }
            function pupclose(){
                document.getElementById("bg").style.display="none";
                document.getElementById("pop-up").style.display="none" ;
            }
        </script>
        <!--弹窗 END-->
        
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>门店选择</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 门店选择 -->
        <article class="stores-select">
            <#if diysite_list??>
                <#list diysite_list as item>
                    <section class="hide-img" onclick="pupopen(${item_index})">
                        <div class="address">
                            <div class="div1">${item.title!''}</div>
                            <div class="div2">地址：${item.address!'无'}</div>
                        </div>
                    </section>
                </#list>
            </#if>
        </article>
        <!-- 门店选择 END -->
    </body>
</html>