//对收货地址进行删除/设为默认操作的方法
function operation(id, type) {
	// type的值代表操作的类型：1. 删除；2. 设为默认

	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		url : "/user/address/operate",
		type : "post",
		timeout : 10000,
		data : {
			id : id,
			type : type
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待图标
			close(1);
			warning("操作成功");
			setTimeout(function() {
				window.location.reload();
			}, 1000);
		}
	});
}