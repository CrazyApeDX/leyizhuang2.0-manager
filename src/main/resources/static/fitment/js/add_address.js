window.onload = function() {
	var oBox = document.getElementById('win_box');
	var oBtn = document.getElementById('add_btn');
	var oBack = document.getElementById('back');
	oBox.children[1].style.height = window.screen.height - 50 + 'px';
	oBox.children[1].style.background = 'rgba(0,0,0,0.5)';
	oBox.style.webkitTransition = '0';
}

/**
 * 获取用户城市下所有的行政区划的方法
 * 
 * @author dengxiao
 */
function getDistrict() {
	var oBox = document.getElementById('win_box');
	var oBtn = document.getElementById('add_btn');
	var oBack = document.getElementById('back');

	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		// url : "/user/address/get",
		url : "/fit/order/address/district",
		type : "post",
		timeout : 10000,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待图标
			close(100);
			$("#win_box").html(res);
			oBox.children[1].style.height = window.screen.height - 50 + 'px';
			oBox.children[1].style.background = 'rgba(0,0,0,0.5)';
			oBox.style.webkitTransition = '1s';
			oBox.style.webkitTransform = 'translateX(0)';
		}
	});
}

/**
 * 选择行政区划或行政街道的方法
 * 
 * @author dengxiao
 */
function getRegion(id, name, status) {
	var oBox = document.getElementById('win_box');
	var oBtn = document.getElementById('add_btn');
	var oBack = document.getElementById('back');
	var city = $("#user_city").val();
	// 开启等待图标
	wait();
	if (1 == status) {
		$.ajax({
			// url : "/user/address/get",
			url : "/fit/order/address/subdistrict",
			type : "post",
			timeout : 10000,
			data : {
				type : 1,
				id : id
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// 关闭等待图标
				close(1);
				warning("亲，您的网速不给力啊");
			},
			success : function(res) {
				// 关闭等待图标
				close(100);
				$("#win_box").html(res);
				oBox.children[1].style.height = window.screen.height - 50
						+ 'px';
				oBox.children[1].style.background = 'rgba(0,0,0,0.5)';
				oBox.style.webkitTransition = '1s';
				oBox.style.webkitTransform = 'translateX(0)';
				$("#add_btn").html(city + name);
			}
		});
	} else if (2 == status) {
		// $.ajax({
		// url : "/user/address/get",
		// type : "post",
		// timeout : 10000,
		// data : {
		// type : 2,
		// id : id
		// },
		// error : function(XMLHttpRequest, textStatus, errorThrown) {
		// // 关闭等待图标
		// close(1);
		// warning("亲，您的网速不给力啊");
		// },
		// success : function(res) {
		var info = $("#add_btn").html();
		$("#add_btn").html(info + name);
		// 关闭等待图标
		close(100);
		windowHide();
		// }
		// });
	}
}

/**
 * 关闭弹窗的方法
 * 
 * @author dengxiao
 */
function windowHide() {
	var oBox = document.getElementById('win_box');
	var oBtn = document.getElementById('add_btn');
	var oBack = document.getElementById('back');
	oBox.style.webkitTransition = '1s';
	oBox.style.webkitTransform = 'translateX(100%)';
}

/**
 * 提交新的收货地址的方法
 * 
 * @author dengxiao
 */
function saveAddress() {
	var receiverName = $("#receiverName").val();
	var receiverMobile = $("#receiverMobile").val();
	var detailAddress = $("#detailAddress").val();
	var baseAddress = $("#add_btn").html();

	var limitDay = $("#early_date").val();
	var limitId = $("#early_time").val();
	var date = $("#theTime").val();
	var detailTime = $("#detailTime").val();
	var limitTime = limitDay + " "
			+ (limitId + ":30-" + (parseInt(limitId) + 1) + ":30");

	if (!/^1\d{10}$/.test(receiverMobile)) {
		warning("请输入正确的手机号码");
		return;
	}

	if (baseAddress.length <= 3) {
		warning("请点击\"" + baseAddress + "\"选择详细的行政区划和行政街道");
		return;
	}

	if (!isAllLegal(receiverName)) {
		warning("收货人信息不能输入除-()#,外的特殊字符");
		return;
	}

	if (!isAllLegal(detailAddress)) {
		warning("详细地址不能输入除-()#,外的特殊字符");
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

	// 开启等待图标
	wait();

	// 发送异步请求
	$.ajax({
		// url : "/user/address/add/save",
		url : "/fit/order/init",
		type : "post",
		timeout : 10000,
		data : {
			receiver : receiverName,
			receiverMobile : receiverMobile,
			baseAddress : baseAddress,
			detailAddress : detailAddress,
			selectedDate: date,
			selectedTime: detailTime
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			if (res.actionCode === "SUCCESS") {
				if (res.content) {
					window.location.href = "/fit/pay/" + res.content;
				} else {
					window.location.href = "/fit/audit/order";
				}
			} else {
				close(1);
				warning(res.content);
			}
		}
	});
}

function isLegal(str) {
	if (str >= '0' && str <= '9')
		return true;
	if (str >= 'a' && str <= 'z')
		return true;
	if (str >= 'A' && str <= 'Z')
		return true;
	var regEx = new RegExp("[-()#, ，（）]");
	if (regEx.test(str))
		return true;
	var reg = /^[\u4e00-\u9fa5]+$/i;
	if (reg.test(str))
		return true;
	return false;
}
function isAllLegal(str1) {
	if (str1 == "" || str1 == undefined)
		return false;
	for (i = 0; i < str1.length; i++) {
		if (!isLegal(str1.charAt(i))) {
			return false;
		}
	}
	return true;
}
