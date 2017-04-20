$(function() {
	$(".some_orders").css("display", "none");
	$(".order-nav li").click(function() {
		$(this).addClass("current").siblings().removeClass("current");
		var li_id = $(this).attr("id");

		if ("all" == li_id) {
			$(".some_orders").css("display", "none");
			$("#all_orders").css("display", "block");
			userOrderData.currentOrderType = 0;
			if (!($("#all_orders").html().trim())) {
				loadData(0);
			}
		} else if ("unpayed" == li_id) {
			$(".some_orders").css("display", "none");
			$("#unpayed_orders").css("display", "block");
			userOrderData.currentOrderType = 1;
			if (!($("#unpayed_orders").html().trim())) {
				loadData(0);
			}
		} else if ("undeliver" == li_id) {
			$(".some_orders").css("display", "none");
			$("#undeliver_orders").css("display", "block");
			userOrderData.currentOrderType = 2;
			if (!($("#undeliver_orders").html().trim())) {
				loadData(0);
			}
		} else if ("unsignin" == li_id) {
			$(".some_orders").css("display", "none");
			$("#unsignin_orders").css("display", "block");
			userOrderData.currentOrderType = 3;
			if (!($("#unsignin_orders").html().trim())) {
				loadData(0);
			}
		} else if ("uncommend" == li_id) {
			$(".some_orders").css("display", "none");
			$("#uncomment_orders").css("display", "block");
			userOrderData.currentOrderType = 4;
			if (!($("#uncomment_orders").html().trim())) {
				loadData(0);
			}
		}
	});

	var init_id = $("#typeId").val();
	if (0 == init_id) {
		$("#all").click();
	} else if (1 == init_id) {
		$("#unpayed").click();
	} else if (2 == init_id) {
		$("#unsignin").click();
	} else if (3 == init_id) {
		$("#uncommend").click();
	}
});
function loadMore() {
	var page = 0;
	if (0 == userOrderData.currentOrderType) {
		page = userOrderData.pages.all + 1;
	} else if (1 == userOrderData.currentOrderType) {
		page = userOrderData.pages.unpayed + 1;
	} else if (2 == userOrderData.currentOrderType) {
		page = userOrderData.pages.undelivery + 1;
	} else if (3 == userOrderData.currentOrderType) {
		page = userOrderData.pages.unsigned + 1;
	} else if (4 == userOrderData.currentOrderType) {
		page = userOrderData.pages.uncommend + 1;
	}
	loadData(page);
}

function loadData(page) {
	wait();
	$.ajax({
		url : "/user/order/load/page",
		method : "POST",
		data : {
			"currentOrderType" : userOrderData.currentOrderType,
			"currentPageNumber" : page
		},
		error : function() {
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			if (!res.trim()) {
				close(1);
				warning("没有更多的订单了");
			} else {
				close(1);
				if (0 == userOrderData.currentOrderType) {
					var html = $("#all_orders").html();
					$("#all_orders").html(html + res);
					userOrderData.pages.all = page;
				} else if (1 == userOrderData.currentOrderType) {
					var html = $("#unpayed_orders").html();
					$("#unpayed_orders").html(html + res);
					userOrderData.pages.unpayed = page;
				} else if (2 == userOrderData.currentOrderType) {
					var html = $("#undeliver_orders").html();
					$("#undeliver_orders").html(html + res);
					userOrderData.pages.undelivery = page;
				} else if (3 == userOrderData.currentOrderType) {
					var html = $("#unsignin_orders").html();
					$("#unsignin_orders").html(html + res);
					userOrderData.pages.unsigned = page;
				} else if (4 == userOrderData.currentOrderType) {
					var html = $("#uncomment_orders").html();
					$("#uncomment_orders").html(html + res);
					userOrderData.pages.uncommend = page;
				}
			}
		}
	});
}

/**
 * 取消订单的方法
 * 
 * @author dengxiao
 */

var count = 0;
function cancel(id) {
	count++;
	// 关闭窗口
	win_no();

	// 开启等待图标
	wait();

	if (count === 1) {
		console.log(count);
		// 发送异步请求
		$.ajax({
			url : "/user/order/cancel",
			timeout : 10000,
			type : "post",
			data : {
				orderId : id
			},
			error : function() {
				close(1);
				setTimeout(warning("亲，您的网速不给力啊"), 100);
				count = 0;
			},
			success : function(res) {
				close(100);
				if (0 == res.status) {
					window.location.reload();
				} else {
					setTimeout(warning(res.message), 500);
					count = 0;
				}
			}
		});
	}
}

function deleteOrder(id) {
	// 关闭窗口
	win_no();

	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		url : "/user/order/delete",
		timeout : 10000,
		type : "post",
		data : {
			orderId : id
		},
		error : function() {
			close(1);
			setTimeout(warning("亲，您的网速不给力啊"), 100);
		},
		success : function(res) {
			close(100);
			if (0 == res.status) {
				window.location.reload();
			} else {
				setTimeout(warning("操作失败，请重新试试"), 500);
			}
		}
	});
}

/**
 * 用户确认收货的方法
 * 
 * @author dengxiao
 */
function confirmAccipt(id) {
	// 开启等待图标
	wait();

	$.ajax({
		url : "/user/confirm/accipt",
		type : "post",
		timeout : 10000,
		data : {
			id : id
		},
		error : function() {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			close(100);
			if (0 == res.status) {
				window.location.reload();
			} else {
				setTimeout(warning(res.message), 500);
			}
		}
	});
}