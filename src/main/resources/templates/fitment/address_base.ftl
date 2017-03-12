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
        <link rel="stylesheet" type="text/css" href="/client/css/x_order_manage.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_my_wealth.css"/>
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/fitment/js/add_address.js"></script>
        <script type="text/javascript">
        function showKeyPress(evt) {
        	 evt = (evt) ? evt : window.event
        	 return checkSpecificKey(evt.keyCode);
        	}

        	function checkSpecificKey(keyCode) {
        	    var specialKey = "~!/@$%\^&*=\+\|[{}];:\'\"<.>\\?";//Specific Key list ~/@#$%^&*=+\|[{}];:'"<.>
        	    var realkey = String.fromCharCode(keyCode);
        	    var flg = false;
        	 flg = (specialKey.indexOf(realkey) >= 0);
        	  if (flg) {
        	        //alert('请勿输入特殊字符: ' + realkey);
        	        return false;
        	    }
        	    return true;
        	}
        	document.onkeypress = showKeyPress;
        </script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">
        <div class="win_add" id="win_box">
            <#include "/client/add_address_detail.ftl">
        </div>
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>编辑收货地址</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 添加收货地址 -->
        <article class="edit-shipping-address">
            <form>
                <div class="edit-info">
                    <label>收货人：</label>
                    <input type="text" id="receiverName" value="">
                </div>
                <div class="edit-info">
                    <label>手机号码：</label>
                    <input type="text" id="receiverMobile" value="">
                </div>
                <div class="edit-info" onclick="getDistrict();">
                    <label>所在地区：</label>
                    <a class="edit-city" id="add_btn">${employee.cityTitle!''}</a>
                </div>
                <div class="edit-info">
                    <label>详细地址：</label>
                    <input type="text" id="detailAddress" value="">
                </div>
                <article class="delive-ways">
	                <article class="psfs">
		                <div class="div11">
		                    <label id="dateLabel">送货日期</label>
		                    <input type="date" id="theTime" value="${deliveryInfo.EARLY_DATE}">
		                </div>
		                <div class="div11">
		                    <label id="timeLabel">送货时间</label>
		                    <a class="btn-select" id="btn_select" style="background:none;">
		                        <span class="cur-select">${deliveryInfo.EARLY_TIME}:30-${deliveryInfo.EARLY_TIME + 1}:30</span>
		                        <select>
		                           <#list 9..18 as item>
		                                <option <#if deliveryInfo.EARLY_TIME==item>selected="selected"</#if> value="${item}">${item}:30-${item+1}:30</option>
		                            </#list>
		                        </select>
		                    </a>
		                </div>
		            </article>
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
                <input style="background-color:#cc1421;" class="btn-submit-save" type="button" value="保存" onclick="saveAddress();">
            </form>
        </article>
        <!-- 添加收货地址 END -->
        
        <input type="hidden" id="user_city" value="${employee.cityTitle!''}">
        <input type="hidden" id="early_date" value="${deliveryInfo.EARLY_DATE}">
        <input type="hidden" id="early_time" value="${deliveryInfo.EARLY_TIME}">
        <input type="hidden" id="detailTime" value="${deliveryInfo.EARLY_TIME}">
    </body>
</html>