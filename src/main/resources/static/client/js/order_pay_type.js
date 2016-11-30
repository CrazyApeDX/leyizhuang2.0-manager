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
 * 确认支付方式选择的方法
 * 
 * @author dengxiao
 */
function confirm() {
	// 获取当前的选择
	var select = $("#selectedType").val();
	window.location.href = "/order/confirm/paytype?id=" + select;
}
