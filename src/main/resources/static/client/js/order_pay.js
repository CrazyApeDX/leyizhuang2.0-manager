/**
 * 填写备注留言之后存储留言的方法（失去焦点事件）
 * 
 * @author dengxiao
 */
function userRemark(old_remark) {
	var remark = $("#remark").val();
	// 如果没有填写备注留言，则不需要存储其信息
	if ("" == remark.trim()) {
		return;
	}
	// 如果跟上一次一样，也不需要存储
	if (old_remark == remark.trim()) {
		return;
	}

	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		url : "/order/remark/save",
		timeout : 10000,
		type : "post",
		data : {
			remark : remark
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待响应的图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待图标
			close(100);
			if (0 == res.status) {
			    warning("已保存");
			    $("#remark").attr("onblur", "userRemark('" + res.remark + "');")
			} else {
			    if (res.message) {
				warning(res.message);
			    } else {
				warning("亲，您的网速不给力啊");
			    }
			}
		}
	});
}

/**
 * 获取本店所有会员信息的方法
 * 
 * @author DengXiao
 */
function getUserInfo() {
	wait();
	$.ajax({
		url : "/order/get/user/infomation",
		timeout : 20000,
		type : "post",
		error : function() {
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			close(1);
			$("#changeInfo").html(res);
			win_yes();
		}
	});
}

/**
 * 去支付的方法
 * 
 * @author DengXiao
 */
function pay() {

	wait();
	// 发起异步请求验证结算
	$.ajax({
		type : "post",
		timeout : 20000,
		url : "/order/check",
		error : function() {
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			if (-1 == res.status) {
				close(1);
				warning(res.message);
				return;
			}
			if (0 == res.status) {
				window.location.href = "/order/pay";
				return;
			}
			if (3 == res.status) {
				close(1);
				if ("支付宝" == res.title) {
					window.location.href = "/pay/alipay?number=" + res.order_number
							+ "&type=0";
				} else if ("微信支付" == res.title) {
					console.log(res);
//					document.location = "WXAppPay:WX:" + res.order_number;
				} else if ("银行卡" === res.title) {
					window.location.href = "/pay/union?number=" + res.order_number
							+ "&type=0";
				}
			}
		}
	});
}

function confirmPay() {
    
    // 第一次点击的时候锁定按钮
    $("#buyNow").removeAttr("href");
    
	wait();
	$.ajax({
		url : "/order/coupon/confirm",
		timeout : 20000,
		type : "post",
		error : function() {
		    	$("#buyNow").attr("href", "javascript:confirmPay();");
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
		    	$("#buyNow").attr("href", "javascript:confirmPay();");
			if (-1 === res.status) {
				close(-1);
				warning("该订单不能选择'货到付款'或'到店支付'");
			}
			else if (-2 === res.status) {
				close(-1);
				warning(res.message);
			}
			else if (-3 === res.status) {
				close(-1);
				warning("非华润产品请选择物流配送！");
			}

			else if (0 === res.status) {
				pay();
			}
		}
	});
}

/**
 * 填写其他收入之后存储其他收入的方法（失去焦点事件）
 * 
 * @author zp
 */
function sellerOtherIncome(old_income) {
	var income = $("#otherIncome").val();
	var totalPay=$("#order_total_price").html();
	// 如果没有填写其他收入，则修改为0
	if ("" == income) {
		income=0;
	}
	// 如果跟上一次一样，也不需要存储
	if (old_income == income) {
		$("#otherIncome").val("");
		return;
	}
	//判断是否是数字
	if(isNaN(income)){
		warning("亲，请输入数字");
		$("#otherIncome").val("");
		return;
	}
	//判断是否大于0
	if(income<0){
		warning("亲，请输入大于等于0的数字");
		$("#otherIncome").val("");
		return;
	}
	//判断是否大于支付金额
	if(income>totalPay){
		warning("亲，不能大于支付价格");
		$("#otherIncome").val("");
		return;
	}

	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		url : "/order/otherIncome/save",
		timeout : 10000,
		type : "post",
		data : {
			otherIncome : income
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待响应的图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待图标
			close(100);
			if (0 == res.status) {
				warning("已保存");
				$("#otherIncome").attr("onblur", "sellerOtherIncome('" + res.otherIncome + "');")
			} else {
				warning("亲，请输入正确的数字");
			}
		}
	});
}
