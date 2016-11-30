$(function() {
    $(".some_orders").css("display", "none");
    $(".order-nav li").click(function() {
	$(this).addClass("current").siblings().removeClass("current");
	var li_id = $(this).attr("id");

	if ("all" == li_id) {
	    $(".some_orders").css("display", "none");
	    $("#all_orders").css("display", "block");
	} else if ("unpayed" == li_id) {
	    $(".some_orders").css("display", "none");
	    $("#unpayed_orders").css("display", "block");
	} else if ("undeliver" == li_id) {
	    $(".some_orders").css("display", "none");
	    $("#undeliver_orders").css("display", "block");
	} else if ("unsignin" == li_id) {
	    $(".some_orders").css("display", "none");
	    $("#unsignin_orders").css("display", "block");
	} else if ("uncommend" == li_id) {
	    $(".some_orders").css("display", "none");
	    $("#uncomment_orders").css("display", "block");
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