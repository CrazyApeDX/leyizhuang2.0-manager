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
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/client/js/order_add_address.js"></script>
        <style>
        .edit-shipping-address .edit-info select {
            overflow: hidden;
            float: right;
            text-align:right;
            width: 60%;
            height: 30px;
            line-height: 30px;
            background: url(/client/images/x_icon_select_bg.png) no-repeat right center;
            -webkit-background-size: 12px auto;
            background-size: 12px auto;
            border: none;
            white-space: nowrap;
            -webkit-box-sizing: border-box;
            box-sizing: border-box;
            -webkit-appearance:none;
            appearance:none;
            direction:rtl;
          }

        </style>
         <script type="text/javascript">
        function showKeyPress(evt) {
        	 evt = (evt) ? evt : window.event
        	 return checkSpecificKey(evt.keyCode);
        	}

        	function checkSpecificKey(keyCode) {
        	    var specialKey = "~!/@$%\^&*=\+\|[{}];:\'\"<.>\\?";//Specific Key list # / - : ; () * .
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
        <!-- 头部 -->
        <header>
            <a class="back" href="/order"></a>
            <p>添加收货地址</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 添加收货地址 -->
        <article class="edit-shipping-address">
            <form>
                <div class="edit-info">
                    <label>收货人：</label>
                    <input type="text" id="receiveName" value="">
                </div>
                <div class="edit-info">
                    <label>手机号码：</label>
                    <input type="text" id="receiveMobile" value="${user.username!''}">
                </div>
                <div class="edit-info">
                    <label>所在城市：</label>
                    <a class="edit-city">${user.cityName!''}</a>
                </div>
                <!-- 1-4修改 -->
                <div class="edit-info">
                    <label>行政区划：</label>
                    <select id="district" onchange="changeDistrict();">
                        <#if district_list??>
                            <#list district_list as item>
                                <option value=${item.id?c}>${item.name!''}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
                <div class="edit-info">
                    <label>行政街道：</label>
                    <div id="subdistrict">
                        <#include "/client/order_address_subdistrict.ftl">
                    </div>
                </div>
                <div class="edit-info">
                    <label>详细地址：</label>
                    <input type="text" id="detail">
                </div>
                <input style="background-color:#cc1421;" class="btn-submit-save" type="button" onclick="saveAddress();" value="保存">
            </form>
        </article>
    <!-- 添加收货地址 END -->
    </body>
</html>