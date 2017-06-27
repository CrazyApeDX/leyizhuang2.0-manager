/**
 * 展示/隐藏货到付款支付方式的方法
 * 
 * @author dengxiao
 */
function cashOnDelivery() {
	// 获取包含货到付款支付方式组件的display属性
	var display = $("#cashOndelivery").css("display");
	// 当display的值为none时，此时应该将其显示
	if ("none" == display) {
		$("#cashOndelivery").css("display", "block");
	}
	// 当display的值为block时，此时应该将其隐藏
	if ("block" == display) {
		$("#cashOndelivery").css("display", "none");
	}
}

/**
 * 选择了支付方式进行支付的方法
 * 
 * @author dengxiao
 */
function payNow() {
	// 获取支付方式
	var type = $("#selectedType").val();
	// 获取订单id
	var orderId = $("#order").val();
	
	if(0 == type){
		warning("亲，请选择支付方式");
		return;
	}

	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		url : "/user/order/pay/now",
//		timeout : 10000,
		type : "post",
		data : {
			type : type,
			orderId : orderId
		},
		error : function() {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待图标
			close(100);
			$("#buyNow").attr("href", "javascript:void(0);")
			setTimeout(function() {
				warning(res.message);
			}, 500);
			if (0 == res.status) {
				setTimeout(function() {
					window.location.href = "/user/order/0";
				}, 1500);
			}
			if(-1 == res.status){
				$("#buyNow").attr("href", "javascript:payNow();");
			}
		}
	});
}