function submit(id) {
	// 获取所有的隐藏标签的值
	var date = $("#theTime").val();
	var detailTime = $("#detailTime").val();
	var limitDay = $("#earlyDate").val();
	var limitId = $("#earlyTime").val();
	var limitTime = limitDay + " "
			+ (limitId + ":30-" + (parseInt(limitId) + 1) + ":30");
	var floor = $("#floor").val();

	if (!date) {
		warning("请输入预约日期");
		return;
	}

	// 选择的日期不能少于最小日期
	var time = new Date(date.replace("-", "/").replace("-", "/"));
	var limit = new Date(limitDay.replace("-", "/").replace("-", "/"));

	if ((time - limit) < 0) {
		warning("您能选择的最早时间为<br>" + limitTime);
		return;
	}

	if ((time - limit) == 0) {
		if ((detailTime * 1 - limitId * 1) < 0) {
			warning("您能选择的最早时间为<br>" + limitTime);
			return;
		}
	}

	var re = /^[0-9]+$/;
	if (!re.test(floor) || parseInt(floor) < 1 || parseInt(floor) > 99) {
		warning("请输入一个正确的楼层");
		return;
	}

	wait();
	$.ajax({
		url : "/fit/order/delivery",
		method : "POST",
		data : {
			deliveryDate : date,
			deliveryTime : detailTime,
			floor : floor,
			id : id
		},
		success : function(res) {
			if ("SUCCESS" === res.actionCode) {
				window.location.href = "/fit/pay/" + id
			} else {
				close(1);
				warning(res.content);
			}
		}
	})
}
