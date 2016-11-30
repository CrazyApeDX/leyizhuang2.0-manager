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
        <link rel="stylesheet" type="text/css" href="/client/css/x_my_wealth.css"/>
        <!-- js -->
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/client/js/order_delivery.js"></script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">
        <#-- 引入资源弹窗 -->
        <div id="info_window">
            <#include "/client/order_delivery_list.ftl">
        </div>
        <!-- 头部 -->
        <header>
            <a class="back" href="/order"></a>
            <#if isCoupon??&&isCoupon>
            	<p>选择服务导购</p>
            <#else>
            	<p>选择配送方式</p>
            </#if>
        </header>
        <!-- 头部 END -->
        
        <!-- 选择配送方式 -->
        <article class="delive-ways">
        	<#if !(isCoupon??&&isCoupon)>
	            <ul>
	                <li id="delivery1" <#if deliveryId??&&deliveryId==1>class="active"</#if>><a>送货上门</a></li>
	                <li id="delivery2" <#if deliveryId??&&deliveryId==2>class="active"</#if>><a>门店自提</a></li>
	            </ul>   
	            <!-- 配送方式 -->
	            <!-- 送货上门 -->
	            <article class="psfs">
	                <div class="div11">
	                    <label id="dateLabel"><#if deliveryId??&&deliveryId==1>预约日期<#elseif deliveryId??&&deliveryId==2>提货日期</#if></label>
	                    <input type="date" id="theTime" onchange="changeTime();" min="${limitDay!''}"  value=${deliveryDate!''}>
	                </div>
	                <div class="div11">
	                    <label id="timeLabel"><#if deliveryId??&&deliveryId==1>预约时间<#elseif deliveryId??&&deliveryId==2>提货时间</#if></label>
	                    <a class="btn-select" id="btn_select">
	                        <span class="cur-select">${deliveryDetailId}:30-${(deliveryDetailId+1)?eval}:30</span>
	                        <select>
	                           <#list 9..18 as item>
	                                <option <#if deliveryDetailId??&&deliveryDetailId==item>selected="selected"</#if> value="${item}">${item}:30-${item+1}:30</option>
	                            </#list>
	                        </select>
	                    </a>
	                </div>
	            </article>
	            <script>
	            var $$ = function (id) {
	                return document.getElementById(id);
	            }
	            window.onload = function () {
	                var btnSelect = $$("btn_select");
	                var curSelect = btnSelect.getElementsByTagName("span")[0];
	                var oSelect = btnSelect.getElementsByTagName("select")[0];
	                var aOption = btnSelect.getElementsByTagName("option");
	                oSelect.onchange = function () {
	                    var text=oSelect.options[oSelect.selectedIndex].text;
	                    var itemId = oSelect.options[oSelect.selectedIndex].value;
	                    $("#detailTime").val(itemId);
	                    curSelect.innerHTML = text;
	                }
	            } 
	            </script>
            </#if>
            <ol>
                <li>
                    <div class="div11">
                        <label id="diyLabel"><#if deliveryId??&&deliveryId==1>归属门店<#elseif deliveryId??&&deliveryId==2>提货门店</#if></label>
                        <a class="target" id="diySite_info" <#if !(canChangeSeller??&&canChangeSeller==false)>href="javascript:getInfo(0);"</#if>><#if diySite??>${diySite.title!''}</#if></a>
                    </div>
                    <#--
                    <#if diy_list??>
                        <article class="stores-select">
                            <#list diy_list as item>
                                <section id="diySite${item.id?c}">
                                    <img class="visi" <#if item.id?c==diySiteId?c>src="/client/images/x_icon_checked.png"<#else>src="/client/images/x_icon_check.png"</#if> alt="">
                                    <div class="address">
                                        <div class="div1">${item.title!''}</div>
                                        <div class="div2">${item.address!''}</div>
                                    </div>
                                </section>
                            </#list>
                        </article>
                    </#if>
                    -->
                </li>
            </ol>
            <ol>
                <li>
                    <div class="div11">
                        <label id="diyLabel">服务导购</label>
                        <a class="target" id="seller_info" style="height:100%;width:70%;" <#if !(canChangeSeller??&&canChangeSeller==false)>href="javascript:getInfo(1);"</#if>>${seller!'暂无'}</a>
                    </div> 
                </li>
            </ol>
            <script type="text/javascript">   
            $(".stores-select section").click(function(){
                $(this).find("img.visi").attr("src","/client/images/x_icon_checked.png");
                <#-- 切换隐藏标签的值 -->
                var id = $(this).attr("id");
                id = id.replace("diySite","");
                $("#diySite").val(id);
                $(this).siblings().find("img.visi").attr("src","/client/images/x_icon_check.png");
            });
            
            <#-- 获取门店信息或者导购信息的方法 -->
            function getInfo(type){
            	<#--增加门店字段 -->
            	var diySiteName= $("#diySite_info").html();
                wait();
                console.log(1);
                <#-- type的值为0，代表获取的门店的信息，type的值为1，代表获取的导购的信息 -->
                $.ajax({
                    url:"/order/get/info",
                    timeout:10000,
                    type:"POST",
                    data:{
                        type:type,
                        diySiteName:diySiteName
                    },
                    error:function(){
                        close(1);
                        warning("亲，您的网速不给力啊");
                    },
                    success:function(res){
                        close(1);
                        <#-- 实现异步刷新 -->
                        $("#info_window").html(res);
                        win_yes();
                    }
                });
            }
           </script>
            <!-- 配送方式 END -->
            <script type="text/javascript">
            $('ul').on('click','a',function(){
                var $self = $(this);//当前a标签
                var $active = $self.closest('li');//当前点击li
                var theId = $active.attr("id").replace("delivery","");
                $("#type").val(theId);
                var index = $active.prevAll('li').length;//当前索引
                $active.addClass('active').siblings('li').removeClass('active');
                
                var title = $active.html().replace("");
                if(title.indexOf("送货上门") > -1){
                    $("#dateLabel").html("预约日期");
                    $("#timeLabel").html("预约时间");
                    $("#diyLabel").html("归属门店");
                } 
                
                if(title.indexOf("门店自提") > -1){
                    $("#dateLabel").html("提货日期");
                    $("#timeLabel").html("提货时间");
                    $("#diyLabel").html("提货门店");
                }               
            });
            </script>
        </article>
        <!-- 选择配送方式 END -->
        <#-- 以下放置几个隐藏标签用于存储配送信息 -->
        <#-- 配送方式 -->
        <input type="hidden" id="type" value="${deliveryId!''}">
        <#-- 配送日期 -->
        <input type="hidden" id="date" value="${deliveryDate!''}">
        <#-- 配送具体时间 -->
        <input type="hidden" id="detailTime" value="${deliveryDetailId!''}">
        <#-- 配送门店id -->
        <input type="hidden" id="diySite" value="${diySiteId!''}">
        <#-- 最小日期 -->
        <input type="hidden" id="limitDay" value="${limitDay!''}">
        <#-- 最小时间端id -->
        <input type="hidden" id="limitId" value="${limitHour!''}">
        <#-- 最小时间文字说明 -->
        <input type="hidden" id="limitTime" value="${earlyDate!''}">
        <a class="btn-next" href="javascript:submit();">确定</a>
    </body>
</html>