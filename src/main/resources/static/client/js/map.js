//地图初始化
var map = new AMap.Map("mapContainer", {
	resizeEnable : true,
	// 二维地图显示视口
	// 地图中心点
	center : [ 116.397428, 39.90923 ],
	// 地图显示的缩放级别
	zoom : 13
});
// 获取用户所在城市信息
function showCityInfo() {
	// 调用等待方法
	wait();

	// 加载城市查询插件
	AMap.service([ "AMap.CitySearch" ], function() {
		// 实例化城市查询类
		var citysearch = new AMap.CitySearch();
		// 自动获取用户IP，返回当前城市
		citysearch.getLocalCity(function(status, result) {
			if (status === 'complete' && result.info === 'OK') {
				if (result && result.city && result.bounds) {
					var cityinfo = result.city;
					$("#my_box").html(cityinfo);
					var city_arry = $("#my_city option");
					var isHave = false;
					city_arry.each(function(i) {
						var option = city_arry.eq(i);
						var option_city_info = option.html();
						option.attr("selected", "");
						if (cityinfo == option_city_info) {
							isHave = true;
							city_arry.eq(0).removeAttr("selected");
							option.attr("selected", "selected");
						}
					});
					if (!isHave) {
						$("#my_city").append(
								"<option selected='selected' value=" + cityinfo
										+ ">" + cityinfo + "</option>");
					}
					// 等待方法结束
					close(1);
				}
			} else {
				close(1);
				warning("定位失败");
			}
		});
	});
}