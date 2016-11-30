function changeTime() {
	var time = $("#theTime").val();
	$("#date").val(time);
}

function submit() {
	// 获取所有的隐藏标签的值
	var type = $("#type").val();
	var date = $("#date").val();
	var detailTime = $("#detailTime").val();
	var diySite = $("#diySite").val();
	var limitDay = $("#limitDay").val();
	var limitId = $("#limitId").val();
	var limitTime = $("#limitTime").val();

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

	console.debug(time == limit);
	if ((time - limit) == 0) {
		if ((detailTime * 1 - limitId * 1) < 0) {
			warning("您能选择的最早时间为<br>" + limitTime);
			return;
		}
	}

	window.location.href = "/order/delivery/save?type=" + type + "&date="
			+ date + "&detailTime=" + detailTime + "&diySite=" + diySite;
}
