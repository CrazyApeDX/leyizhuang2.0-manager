/**
 * 选择行政区划后改变门店列表的方法
 * 
 * @author dengxiao
 */
function changeDistrict() {
	// 获取当前选择的行政区划id
	var districtId = $("#site_district  option:selected").val();

	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		url : "/user/diysite/get",
		type : "post",
		timeout : 10000,
		data : {
			districtId : districtId
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待图标
			close(100);
			// 更改门店列表
			$("#site_by_district").html(res);
		}
	});
}

/**
 * 选择门店的方法
 * 
 * @author dengxiao
 */
function selectDiySite(id) {

	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		url : "/user/select/diysite",
		type : "post",
		timeout : 10000,
		data : {
			id : id
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success:function(res){
			// 未登陆的情况下直接跳转到登陆页面
			if (-2 == res.status) {
				window.location.href = "/login";
				return;
			}
			
			// 关闭等待图标
			close(1);
			warning("归属门店更改成功");
			setTimeout(function() {
				window.location.reload();
			}, 1000);
		}
	});
}