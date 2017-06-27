function addActivity(activityId) {
	// 开启等待图标
	wait();
	// 发送异步请求
	$.ajax({
		url : "/promotion/add",
		type : "post",
//		timeout : 10000,
		data : {
			activityId : activityId
		},
		error : function() {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			//关闭等待图标
			close(100);
			if(0 == res.status){
				setTimeout(warning("已添加"),1000);
			}else{
				setTimeout(warning("添加失败"),1000);
			}
		}
	});
}