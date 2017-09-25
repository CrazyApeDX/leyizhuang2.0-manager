function submitTheSuggestion() {
	var categoryId = $("#suggestion_category  option:selected").val();
	var suggestion = $("#suggestion").val();
	var phone = $("#phone").val();
	if ("" == suggestion) {
		warning("亲，我们还存在哪些不足");
		return;
	}
	if ("" == phone) {
		warning("亲，请留下您的联系电话");
		return;
	}
	if (!/^1\d{10}$/.test(phone)) {
		warning("亲，您的联系电话不正确啊");
		return;
	}

	// 开启等待图标
	wait();
	// 为了避免重复提交，禁用提交按钮
	$("#button").attr("onclick", "");

	// 发送异步请求
	$.ajax({
		url : "/user/suggestion/save",
		type : "post",
//		timeout : 10000,
		data : {
			categoryId : categoryId,
			suggestion : suggestion,
			phone : phone
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
			// 恢复提交功能
			$("#button").attr("onclick", "submitTheSuggestion();");
		},
		success : function(res) {
			// 未登陆的情况下直接跳转到登陆页面
			if (-2 == res.status) {
				window.location.href = "/login";
				return;
			}
			// 关闭等待图标
			close(1);
			warning("您的建议已经提交");
			setTimeout(function() {
				window.location.reload();
			}, 1000);
		}
	});
}