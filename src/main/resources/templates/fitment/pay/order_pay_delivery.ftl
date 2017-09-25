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
        <script type="text/javascript" src="/fitment/js/order_delivery.js"></script>
        <style>
        	.floor_input {margin-left:30px;height:24px;line-height:24px;border:#EEEEEE 1px solid;padding-left:10px;width:30px;}
        </style>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            	<p>配送信息</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 选择配送方式 -->
        <article class="delive-ways">
            <!-- 送货上门 -->
            <article class="psfs">
                <div class="div11">
                    <label id="dateLabel">送货日期</label>
                    <input type="date" id="theTime" value="${order.deliveryDate}">
                </div>
                <div class="div11">
                    <label id="timeLabel">送货时间</label>
                    <a class="btn-select" id="btn_select">
                        <span class="cur-select">${order.deliveryTime}:30-${(order.deliveryTime+1)?eval}:30</span>
                        <select>
                           <#list 9..18 as item>
                                <option <#if order.deliveryTime??&&order.deliveryTime==item>selected="selected"</#if> value="${item}">${item}:30-${item+1}:30</option>
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
            <ol>
                <li>
                    <div class="div11">
                        <label id="diyLabel">楼层</label>
                    	<input type="text" id="floor" class="floor_input" value="${order.floor!"1"}">
                    </div> 
                </li>
            </ol>
        </article>
        <a class="btn-next" href="javascript:submit(${order.id?c});">确定</a>
        <input type="hidden" id="earlyDate" value="${order.earlyDate!''}">
        <input type="hidden" id="earlyTime" value="${order.earlyTime!''}">
        <input type="hidden" id="detailTime" value="${order.deliveryTime!'9'}">
    </body>
</html>