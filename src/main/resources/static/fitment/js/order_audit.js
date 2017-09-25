//$(function() {
//	$(".some_orders").css("display", "none");
//	$(".order-nav li").click(function() {
//		$(this).addClass("current").siblings().removeClass("current");
//		var li_id = $(this).attr("id");
//
//		if ("all" == li_id) {
//			$(".some_orders").css("display", "none");
//			$("#all_orders").css("display", "block");
//		}
//	});
//
//	var init_id = $("#typeId").val();
//	if (0 == init_id) {
//		$("#all").click();
//	} else if (1 == init_id) {
//		$("#unpayed").click();
//	} else if (2 == init_id) {
//		$("#unsignin").click();
//	} else if (3 == init_id) {
//		$("#uncommend").click();
//	}
//});

var page = 0;

var loadMore = function() {
	wait();
	$.ajax({
		url : "/fit/order/load",
		method : "POST",
		data : {
			page : page
		},
		success : function(res) {
			close(1);
			if (res) {
				page += 1;
				$("#all_orders").append(res);
			} else {
				warning("没有更多的数据了");
			}
		}
	})
}

var agree = function(id) {
	wait();
	$
			.ajax({
				url : "/fit/order/audit/",
				method : "POST",
				data : {
					action : "AGREE",
					id : id
				},
				success : function(res) {
					close(1);
					if ("SUCCESS" === res.actionCode) {
						$("#status" + id).html("审核通过");
						$("#action" + id)
								.html(
										"<a href='/fit/pay/"
												+ id
												+ "' style='border:#cc1421 1px solid;color:#cc1421;'>去支付</a>")
					}
				}
			});
}

var reject = function(id) {
	wait();
	$.ajax({
		url : "/fit/order/audit/",
		method : "POST",
		data : {
			action : "REJECT",
			id : id
		},
		success : function(res) {
			close(1);
			if ("SUCCESS" === res.actionCode) {
				$("#status" + id).html("审核不通过");
				$("#action" + id).html(
						"<a href='javascript:remove(" + id + ");'>删除</a>")
			}
		}
	});
}

var remove = function(id) {
	wait();
	$.ajax({
		url : "/fit/order/audit/",
		method : "POST",
		data : {
			action : "DELETE",
			id : id
		},
		success : function(res) {
			close(1);
			if ("SUCCESS" === res.actionCode) {
				$("#order" + id).remove();
			}
		}
	});
}
