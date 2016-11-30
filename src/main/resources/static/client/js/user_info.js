//修改性别的方法
function changeSex() {
	var selectElementId = $("#sex_hidden").val();
	// 获取当前选择的性别
	var sex = $("#user_sex  option:selected").val();
	// 开启等待图标
	wait();
	// 发送异步请求
	$.ajax({
		url : "/user/change",
		type : "post",
		timeout : 10000,
		data : {
			type : 0,
			param : sex
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
			// 让option还原之前被选中的状态
			$("#" + selectElementId).attr("selected", "selected");
		},
		success : function(res) {
			// 未登陆的情况下直接跳转到登陆页面
			if (-2 == res.status) {
				window.location.href = "/login";
				return;
			}

			// 关闭等待图标
			close(100);
			if (0 == res.status) {
				// 修改界面的显示值
				$("#sex_view").html(sex);
			} else {
				warning("亲，您的网速不给力啊");
				// 让option还原之前被选中的状态
				selectElement.attr("selected", "selected");
			}
		}
	});
}
