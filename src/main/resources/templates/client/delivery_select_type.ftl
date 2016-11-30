<!DOCTYPE html>
<html>
	<head>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="copyright" content="" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta charset="utf-8">
		<title>查看物流</title>
		
		<link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
		
		<script src="js/jquery-1.11.0.js" type="text/javascript"></script>
		<script src="js/rich_lee.js" type="text/javascript"></script>
	</head>
	<style type="text/css">
		.add_1_21{
			width: 90%;
			padding: 0 5%;
		}
		.add_1_21 button{
			border-radius: 8px;
			border: none;
			color: white;
		}
		.add_1_21 .btn01{
			width: 90%;
			height: 100px;
			margin: 0 5%;
			margin-top: 20px;
			background: ;
			font-size: 2.4em;
			background: #007d9a;
		}
		.add_1_21 .btn02{
			width: 90%;
			height: 100px;
			margin: 0 5%;
			margin-top: 20px;
			font-size: 2.4em;
			background: #d45500;
		}
		.add_1_21 .out{
			width: 90%;
			height: 30px;
			margin: 0 5%;
			position: fixed;
			bottom: 20px;
			left: 0px;
			font-size: 1.4em;
			background: #cc1421;
			color: white;
			border: none;
		}
	</style>
	<script type="text/javascript">
		html_hi();
        function logout(){
		  window.location.href = "/login/out";
		}
		
		function orderDeli() {
			window.location.href = "/delivery/order";
		}
		
		function returnDeli() {
			window.location.href = "/delivery/return";
		}
	</script>
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
	<body style="height: 100%; background: #f3f4f6;">
		<div>
			<div class="sec_header">
				<a style="width: 18%;height:100%;display: block;float: left"></a>
				<p>选择模式</p>
			</div>
			<section class="add_1_21">
				<button class="btn02" onclick="orderDeli();">配送单</button>
				<button class="btn01" onclick="returnDeli();">退货单</button>
				<button class="out" onclick="logout();">退出</button>
			</section>
		</div>		
	</body>
</html>
