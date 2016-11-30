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
</head>
<body class="bgc-f3f4f6">
<#include "/client/common_warn.ftl" />
<div id='container'></div>
<div id="tip"></div>
  <!--弹窗-->
  <div id="bg"></div>
  <div id="popbox">
    <div class="time-select">
      <div>开始时间：<input type="date" id="start" min="2015-12-01" value="<#if startDate??>${startDate?string("yyyy-MM-dd")}<#else>${.now?string("yyyy-MM-dd")}</#if>"></div>
      <div>结束时间：<input type="date" id="end" min="2015-12-01"  value="<#if endDate??>${endDate?string("yyyy-MM-dd")}</#if>"></div>
      <a class="btn-sure-time" href="javascript:;" onclick="pupclose()">确定</a>
    </div>    
  </div>
  <script type="text/javascript">
    $("#bg").height($(window).height());
    function pupopen(){
      document.getElementById("bg").style.display="block";
      document.getElementById("popbox").style.display="block" ;
    }
    function pupclose(){
      document.getElementById("bg").style.display="none";
      document.getElementById("popbox").style.display="none" ;
      window.location.href="/delivery/order?start=" + document.getElementById("start").value + "&end=" + document.getElementById("end").value;
    }
    <#-- 模糊查询配送单的方法 -->
    function searchDelivery(){
        <#-- 获取查询关键词 -->
        var keywords = $("#keywords").val();
        if("" === keywords){
            return;
        }
        <#-- 开启等待图标 -->
        wait();
        $.ajax({
            url : "/delivery/order/search",
            type : "post",
            timeout : 20000,
            data:{
                keyword : keywords,
                type : 1
            },
            error:function(){
                close(1);
                warning("亲，您的网速不给力啊");
            },
            success:function(res){
                $(".look-details-list").html(res);
                close(1);
            }
        });
    }
  </script>
  <!--弹窗 END-->
	<#-- 引入等待提示样式 -->
	<#include "/client/common_wait.ftl">

	<!-- 头部 -->
  <header>
    <a class="back" href="/delivery"></a>
    <div class="date-group">
      <a <#if days?? && days!=7>class="active"</#if> href="/delivery/order?days=3">今天</a>
      <a <#if days?? && days==7>class="active"</#if> href="/delivery/order?days=7">一周内</a>
      <a <#if startDate?? || endDate??>class="active btn-filter"</#if> href="javascript:;" onclick="pupopen()">筛选</a>
    </div>
  </header>
  <!-- 头部 END -->
  <!-- 搜索栏  -->
	<input id="typeId" type="hidden" value="${typeId!'0'}">
	<div class="searchbox bgc-f3f4f6 bdt">
		<input type="text" id="keywords" placeholder="地址/收货人信息"> <a
			href="javascript:searchDelivery();"></a>
	</div>
  <!-- 搜索栏  END -->
  <!-- 详情列表 -->
  <article class="look-details-list">
    <ul>
      <li class="<#if type?? && type==2>active</#if> delivery"><a href="/delivery/order?type=2<#if days??>&days=${days}</#if><#if startDate??>&start=${startDate?string("yyyy-MM-dd")}</#if><#if endDate??>&end=${endDate?string("yyyy-MM-dd")}</#if>">配送中（${count_type_2!'0'}）</a></li>
      <li class="<#if type?? && type==1>active</#if> delivery"><a href="/delivery/order?type=1<#if days??>&days=${days}</#if><#if startDate??>&start=${startDate?string("yyyy-MM-dd")}</#if><#if endDate??>&end=${endDate?string("yyyy-MM-dd")}</#if>">已配送（${count_type_1!'0'}）</a></li>
      <#--
      <li <#if type?? && type==3>class="active"</#if>><a href="/delivery/order?type=3<#if days??>&days=${days}</#if><#if startDate??>&start=${startDate?string("yyyy-MM-dd")}</#if><#if endDate??>&end=${endDate?string("yyyy-MM-dd")}</#if>">待配送（${count_type_3!'0'}）</a></li>
      -->
    </ul>
    <!-- 详情列表 -->
    
    <#if order_list??>
    	<#list order_list as item>
    	   <#if item.mainOrderNumber?? && item.mainOrderNumber != username>   
    		<section>
		      <a href="/delivery/detail/${item.id?c}">
		      	<#if item.statusId==3 || item.statusId==4>
		        	<div class="time">【预计 ${item.deliveryDate!''} <span>${item.deliveryDetailId!'0'}:30</span> 送达】</div>
	        	<#elseif item.statusId==5 || item.statusId==6>
	        		<div class="time">【<#if item.deliveryTime??>${item.deliveryTime?string("yyyy-MM-dd")}</#if> <span><#if item.deliveryTime??>${item.deliveryTime?string("HH:mm")}</#if></span> 送达】</div>
		        </#if>
		        <div class="address">主单号：${item.mainOrderNumber!''}</div>
		        <div class="address">收货人：${item.shippingName!''}</div>
		        <div class="address">电话：${item.shippingPhone!''}</div>
		        <div class="address">收货地址：${item.shippingAddress!''}</div>
		      </a>
		    </section>
		     <#if item.mainOrderNumber??><#assign username=item.mainOrderNumber /></#if>
		 </#if>  
    	</#list>
    </#if>
  </article>
  <!-- 详情列表 END -->

  <div class="clear h66"></div>


</body>
</html>