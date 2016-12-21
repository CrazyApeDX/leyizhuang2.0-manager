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
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=bdd6b0736678f88ed49be498bff86754"></script>
<script type="text/javascript">

var map, geolocation;

//加载地图，调用浏览器定位服务
map = new AMap.Map('container');

setInterval("timer()", 1000 * 60 * 5);
    
function timer() {
    map.plugin('AMap.Geolocation', function() {
        geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位，默认:true
            timeout: 2000          //超过10秒后停止定位，默认：无穷大
        });
        map.addControl(geolocation);
        geolocation.getCurrentPosition();
        AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
    });
    
    var geocoder;
    
    //解析定位结果
    function onComplete(data) {
    	AMap.service('AMap.Geocoder',function(){//回调函数
	        //实例化Geocoder
	        geocoder = new AMap.Geocoder({
	            city: "010" //城市，默认：“全国”
	        });
	        
			var lnglatXY=[data.position.getLng(), data.position.getLat()];//地图上所标点的坐标
			
	        geocoder.getAddress(lnglatXY, function(status, result) {
	            if (status === 'complete' && result.info === 'OK') {
	               //获得了有效的地址信息:
	               warning(result.regeocode.formattedAddress);
	               
	               $.ajax({ 
						url: "/delivery/geo/submit", 
						type: "post",
						dataType: "json",
						data: 
						{
							"longitude": data.position.getLng(), 
							"latitude": data.position.getLat(),
							"accuracy": data.accuracy,
							"isConverted": data.isConverted,
							"formattedAddress" : result.regeocode.formattedAddress
						},
						success: function(data)
						{
				        	if (data.code != 0)
				        	{
				        		warning(data.message);
				        	}
				  		}
					});
	            }else{
	               //获取地址失败
	            }
	        });
	    })
    }
}
</script>
<script>
// 确认送达
function submitDelivery(id)
{
	if (ajaxable) {
		ajaxable = false;
		
		if (null == id)
		{
			warning("ID不能为空");
			ajaxable = true;
			return;
		}
		
		$.ajax({ 
			url: "/delivery/submitDelivery", 
			type: "post",
			dataType: "json",
			data: {"id": id},
			success: function(data)
			{
	        	if (data.code == 0)
	        	{
	        		warning("确认成功");
	        		window.location.reload();
	        	}
	        	else
	        	{
	        		warning(data.message);
	        	}
	        	ajaxable = true;
	  		}
		});
	}
}

var returnCount = 0;

// 拒签退货
function submitReturn(id)
{
	returnCount++;
	
	if (null == id)
	{
		warning("ID不能为空");
		returnCount = 0;
		return;
	}
	
	if (1 === Number(returnCount)) {
		$.ajax({ 
			url: "/delivery/submitReturn", 
			type: "post",
			dataType: "json",
			data: {"id": id},
			success: function(data)
			{
	        	if (data.code == 0)
	        	{
	        		warning("退货成功");
	        		window.location.reload();
	        	}
	        	else
	        	{
	        		returnCount = 0;
	        		warning(data.message);
	        	}
	  		}
		});
	}
}

var ajaxable = true;

function submitOwnMoney()
{
	$("#submit_check").removeAttr("onclick");
	var payed = document.getElementById("payed").value;
	var owned = document.getElementById("owned").value;
	var money = document.getElementById("money").value;
	var pos = document.getElementById("pos").value;
	
	<#-- 首先判断输入的值是不是一个数字 -->
	if(isNaN(money)){
		$("#submit_check").attr("onclick", "submitOwnMoney();");
		warning("亲，请输入一个正确的数字");
		return;
	}
	
	<#-- 再次判断输入的值是否大于0 -->
	if(money<0){
		$("#submit_check").attr("onclick", "submitOwnMoney();");
		warning("亲，不能输入负数");
		return;
	}
	
	<#-- 首先判断输入的值是不是一个数字 -->
	if(isNaN(pos)){
		$("#submit_check").attr("onclick", "submitOwnMoney();");
		warning("亲，请输入一个正确的数字");
		return;
	}
	
	<#-- 再次判断输入的值是否大于0 -->
	if(pos<0){
		$("#submit_check").attr("onclick", "submitOwnMoney();");
		warning("亲，不能输入负数");
		return;
	}
	
	<#-- 首先判断输入的值是不是一个数字 -->
	if(isNaN(payed)){
		$("#submit_check").attr("onclick", "submitOwnMoney();");
		warning("亲，请输入一个正确的数字");
		return;
	}
	
	<#-- 再次判断输入的值是否大于0 -->
	if(payed<0){
		$("#submit_check").attr("onclick", "submitOwnMoney();");
		warning("亲，不能输入负数");
		return;
	}
	
	<#-- 再次判断输入的值是否大于需代收金额 -->
	if(owned < 0){
		$("#submit_check").attr("onclick", "submitOwnMoney();");
		warning("亲，您输入的值大于需代收金额");
		return;
	}
	
	if (null == payed || null == owned || "" == payed || "" == owned)
	{
		$("#submit_check").attr("onclick", "submitOwnMoney();");
		warning("请输入正确的金额");
		return;
	}
	
	$.ajax({ 
		url: "/delivery/submitOwnMoney/<#if td_order??>${td_order.id?c}<#else>0</#if>",
		type: "post",
		dataType: "json",
		data: {"payed": payed, "owned": owned,"money":money,"pos":pos},
		success: function(data)
		{
        	if (data.code == 0)
        	{
        		if(data.owned==0){
        			warning("提交成功");
        		}else{
        			warning("申请成功");
        		}
        		window.location.reload();
        	}
        	else
        	{
        		warning(data.message);
        	}
    		$("#submit_check").attr("onclick", "submitOwnMoney();");
  		}
	});
}
//图片点击放大缩小
var isopen = false; 
function imgChange(){
	if (!isopen){ 
    	isopen = true; 
    	$("#signPhoto").width("230px"); 
    	$("#signPhoto").height("320px");
	} 
	else{ 
    	isopen = false; 
    	$("#signPhoto").width("100px");
    	$("#signPhoto").height("100px");
	} 
}
function payedChange(){
	$('#payed').val((parseFloat($('#money').val())+parseFloat($('#pos').val())).toFixed(2) );
	$('#owned').val(($('#agencyFund').html()-$('#payed').val()).toFixed(2) );
}
</script>
</head>
<body class="bgc-f3f4f6">
<div id='container'></div>
<div id="tip"></div>
<#include "/client/common_warn.ftl" />
<#include "/client/common_confirm.ftl">
  <!--弹窗-->
  <div id="bg"></div>
  <div id="arreabox" style="top:50%">
    <form>
      <div class="title" id="titleName">填写代收金额</div>   
      <div class="text1">现金<input type="number" id="money" value="0" oninput="payedChange()">元</div>
      <div class="text1">pos<input type="number" id="pos" value="0" oninput="payedChange()">元</div>
      <div class="text1">已交款<input type="text" id="payed" value="0" oninput="payedChange()" readonly="readonly">元</div>
      <div class="text1">欠款&nbsp;&nbsp;<input type="text" id="owned" value="<#if allMoney??>${allMoney?c}<#else>0</#if>" readonly="readonly">元</div>
      <div class="button-group">
        <a class="sure" href="#" onclick="pupclose()">关闭</a>
        <a class="cancle" href="#" id="submit_check" onclick="submitOwnMoney()">提交</a>
      </div> 
    </form>
  </div>
  <script type="text/javascript">
    // $("#bg").height($(window).height());
    function pupopen(type){
      if(type==1){
    	  $('#titleName').html("申请欠款");
      }
      document.getElementById("bg").style.display="block";
      document.getElementById("arreabox").style.display="block" ;
    }
    function pupclose(){
      document.getElementById("bg").style.display="none";
      document.getElementById("arreabox").style.display="none" ;
    }
  </script>
  <!--弹窗 END-->

  <!-- 头部 -->
    <#-- 引入警告提示样式 -->
    <#include "/client/common_warn.ftl">
    <#-- 引入等待提示样式 -->
    <#include "/client/common_wait.ftl">   
      <header>
        <a class="back" href="/delivery/order"></a>
        <p>详情查看</p>
      </header>
      <!-- 头部 END -->

<#if td_order??>
  <!-- 详情查看 -->
  <article class="look-details">
    <!-- 配送详情 -->
    <section>
      <div class="title">配送详情</div>
      <div class="content">
      	<#if td_order.statusId==4 || td_order.statusId==3>
      		<div class="mesg">预计送达时间：${td_order.deliveryDate!''}</div>
      	<#elseif td_order.statusId==5 || td_order.statusId==6>
      		<div class="mesg">送达时间：${td_order.deliveryTime!''}</div>
      	</#if>
      	<div class="mesg">导购姓名：${td_order.sellerRealName!''}</div>
        <div class="mesg">导购电话：${td_order.sellerUsername!''}</div>
        <div class="mesg">收货人姓名：${td_order.shippingName!''}</div>
        <div class="mesg">手机号码：${td_order.shippingPhone!''}</div>
        <div class="mesg">收货地址：${td_order.shippingAddress!''}</div>
        <div class="mesg">备注信息：${td_order.remark!''}</div>
      </div>
    </section>
    <section>
      <div class="title">门店信息</div>
      <div class="content">
        <div class="mesg">门店名称：${td_order.diySiteName!'0'}</div>
        <div class="mesg">门店电话：${td_order.diySitePhone!'0'}</div>
      </div>
    </section>
    <!-- 订单详情 -->
    <#if sub_order_list??>
    	<#list sub_order_list as sub_order>
		    <section>
		      <div class="title">分单详情</div>
		      <div class="content">
		        <div class="mesg">分单号：${sub_order.orderNumber!''}</div>
		        <#if sub_order.orderGoodsList??>
		        	<#list sub_order.orderGoodsList as item>
		        		<div class="mesg">产品：${item.goodsTitle!''} * ${item.quantity!'0'}  编号:${item.sku!''}</div>
		        	</#list>
		        </#if>
		        <#if sub_order.presentedList??>
		        	<#list sub_order.presentedList as item>
		        		<div class="mesg">活动赠品：${item.goodsTitle!''} * ${item.quantity!'0'}</div>
		        	</#list>
		        </#if>
		        <#if sub_order.giftGoodsList??>
		        	<#list sub_order.giftGoodsList as item>
		        		<div class="mesg">赠品：${item.goodsTitle!''} * ${item.quantity!'0'}</div>
		        	</#list>
		        </#if>
		        <div class="mesg">支付方式：${sub_order.payTypeTitle!''}</div>
		        <div class="mesg">上楼费总额：${sub_order.upstairsFee?string('0.00')}</div>
		        <div class="mesg">剩余上楼费：${sub_order.upstairsLeftFee?string('0.00')}</div>
		        <#--
		        <div class="mesg">已交款：${sub_order.actualPay!'0'}元</div>
        		<div class="mesg">欠款：<#if sub_order.totalPrice?? && sub_order.actualPay??>${sub_order.totalPrice-sub_order.actualPay}<#else>0</#if>元</div>
  			  	-->
  			  </div>
		    </section>
	    </#list>
    </#if>
    <!-- 申请欠款 -->
    <section>
      <div class="title">收款信息</div>
      <div class="content">
      	<#--
      	<div class="mesg">订单总金额：<#if td_order.allTotalPay??>${td_order.allTotalPay?c}<#else>0.00</#if>元</div>
        <div class="mesg">需代收金额：<#if ownrecord?? && ownrecord.isEnable?? && ownrecord.isEnable == true && ownrecord.ispassed == true><#if ownrecord.owned??>${ownrecord.owned?c}<#else>0.00</#if><#else><#if td_order.allTotalPay?? && td_order.allActualPay??>${td_order.allTotalPay-td_order.allActualPay}<#else>0</#if></#if>元</div>
        <div class="mesg">实代收金额：<#if ownrecord?? && ownrecord.isEnable?? && ownrecord.isEnable == true && ownrecord.ispassed == true><#if ownrecord.payed??>${ownrecord.payed?c}<#else>0.00</#if><#else><#if td_order.allActualPay??>${td_order.allActualPay?c}<#else>0.00</#if></#if>元</div>
        -->
        <div class="mesg">订单总金额：<#if allMoney??>${allMoney?string('0.00')}<#else>0.00</#if>元</div>
        <div class="mesg">需代收金额：<span id="agencyFund"><#if allMoney?? && isOnlinePay?? && isOnlinePay==false>${allMoney?c}<#else>0</#if></span>元</div>
        <div class="mesg">实代收金额：<#if (ownrecord?? && ownrecord.isEnable?? && ownrecord.isEnable == true && ownrecord.ispassed == true) || (ownrecord?? && ownrecord.isOwn?? && ownrecord.isOwn==false)><#if ownrecord.payed??>${ownrecord.payed?c}<#else>	0.00</#if><#else>0.00</#if>元</div>
      </div>
    </section>
    <!-- 签收图片 -->
    <#if td_order.photo??>
    <section>
      <div class="title">签收图片</div>
      <div class="content">
      	<div class="mesg" onclick="imgChange()"><img id='signPhoto' width="100px" height="100px" src="${td_order.photo!''}"></div>
      </div>
    </section>
    </#if>
    <#if td_order.statusId == 4>
    	<#--暂时隐藏拍照上传 <#if td_order.photo??> -->
    	   	<!-- <a class="btn-submit-save bgc-ff8e08" href="javascript:;" onclick="javascript:win_yes('是否已收回代收款<#if ownrecord?? && ownrecord.isEnable?? && ownrecord.isEnable == true && ownrecord.ispassed == true><#if ownrecord.owned??>${ownrecord.owned?c}<#else>0.00</#if><#else><#if td_order.allTotalPay??>${td_order.allTotalPay?c}<#else>0</#if></#if>元？','submitDelivery(${td_order.id?c});')">确认送达</a> -->
    		<!-- <a class="btn-submit-save bgc-ff8e08" <#if ownrecord??>href="javascript:;" style="background:#999"</#if> href="javascript:;" <#if !ownrecord??>onclick="pupopen()"</#if>><#if ownrecord?? && ownrecord.isEnable??><#if ownrecord.isEnable == true><#if ownrecord.ispassed == true>审核通过<#else>未通过审核</#if><#else>等待审核</#if><#else>填写代收金额</#if></a> -->
    		<#if ownrecord?? >
    			<#if ownrecord.isEnable?? && ownrecord.isEnable == true>
    				<#if ownrecord.ispassed?? && ownrecord.ispassed == true>
    					<a class="btn-submit-save bgc-ff8e08" href="javascript:;" style="background:#999">审核通过</a>
    					<a class="btn-submit-save bgc-ff8e08" href="javascript:;" onclick="javascript:win_yes('是否确认送达？','submitDelivery(${td_order.id?c});')">确认送达</a>
    				<#else>
    					<a class="btn-submit-save bgc-ff8e08" href="javascript:;" style="background:#999">未通过审核</a>
    					<a class="btn-submit-save bgc-ff8e08" onclick="pupopen(1)">申请欠款</a>
    					<a class="btn-submit-save bgc-ff8e08" id="submitReturn" href="javascript:;" onclick="javascript:win_yes('是否确定拒签退货？','submitReturn(${td_order.id?c});')">拒签退货</a>
    				</#if>
    			<#else>
    				<#if ownrecord.isOwn?? && ownrecord.isOwn==false>
    				    <a class="btn-submit-save bgc-ff8e08" href="javascript:;" onclick="javascript:win_yes('是否确认送达？','submitDelivery(${td_order.id?c});')">确认送达</a>
    				<#else>	
    					<a class="btn-submit-save bgc-ff8e08" href="javascript:;" style="background:#999">等待审核</a>
    				</#if>
    				
    			</#if>
    		<#else>
    			<#if td_order.allTotalPay?? && td_order.allTotalPay!=0 && isOnlinePay?? && isOnlinePay==false>
    			<a class="btn-submit-save bgc-ff8e08" onclick="pupopen()">填写代收金额</a>
    			<a class="btn-submit-save bgc-ff8e08" href="javascript:;" id="submitReturn" onclick="javascript:win_yes('是否确定拒签退货？','submitReturn(${td_order.id?c});')">拒签退货</a>
    			<#else>
    			<a class="btn-submit-save bgc-ff8e08" href="javascript:;" onclick="javascript:win_yes('是否确认送达？','submitDelivery(${td_order.id?c});')">确认送达</a>
    			<a class="btn-submit-save bgc-ff8e08" href="javascript:;" id="submitReturn" onclick="javascript:win_yes('是否确定拒签退货？','submitReturn(${td_order.id?c});')">拒签退货</a>
    			</#if>
    			
    		</#if>
    	<#--暂时隐藏拍照上传 <#else>
    	    
    		<a class="btn-submit-save bgc-ff8e08" href="javascript:photo();" >拍照上传</a>
    	</#if> -->

    <!-- <a class="btn-submit-save bgc-ff8e08" href="javascript:;" onclick="submitDelivery(${td_order.id?c})">确认送达</a> -->
    </#if>
    <div style="display:block;filter:alpha(opacity=0);-moz-opacity:0;opacity:0;float: left;height: 0px;width: 0px;position: relative;">
        <form id="imgUpload" action="/delivery/img" enctype="multipart/form-data" method="post" style="hight:0px;width: 0px;">
            <input type="file" onchange="upload()" name="Filedata" id="clickFile">
            <input type="text" name="orderNumber"  value="${td_order.orderNumber!''}">
            <input type="text" name="id" value="<#if id??>${id?c}<#else>0</#if>">
        </form>
    </div>
    <div style="float: left;position: relative;height: 60px;width: 90%;"></div>
    <script>
        $(function(){
            var msg = "${msg!''}";
            if("" != msg){
                if(0 != msg){
                    warning("上传失败");
                }else if(0 == msg){
                    warning("上传成功")
                }
            }
        });
    
        function photo(){
            var ele = document.getElementById("clickFile");
            return ele.click();
        }
        
        function upload(){
            var form = document.getElementById("imgUpload");
            wait();
            form.submit();
        }
    </script>
  </article>
  <!-- 详情查看 END -->
</#if>

  <div class="clear h66"></div>

</body>
</html>