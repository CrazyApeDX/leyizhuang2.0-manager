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

	var id = $("#_order_dev").val();
	
	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		url : "/fit/order/remark",
//		timeout : 10000,
		type : "post",
		data : {
			remark : remark,
			id: id
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待响应的图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待图标
			close(100);
			if ("SUCCESS" == res.actionCode) {
				warning("已保存");
				$("#remark")
						.attr("onblur", "userRemark('" + res.content + "');")
			} else {
				if (res.content) {
					warning(res.content);
				} else {
					warning("亲，您的网速不给力啊");
				}
			}
		}
	});
}	
