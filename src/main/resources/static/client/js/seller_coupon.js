/**
 * 判断当前登录用户是否是销顾，需不需要代下单的方法
 * 
 * @author DengXiao
 */

// 创建一个命名空间，用于定义关于销顾或店长代下单的方法
var seller = {
	// 用户搜索代下单用户的关键词
	keywords : "",
	// 公共方法，简化DOM
	getE : function(id) {
		return document.getElementById(id);
	},
	// 检查购物车是否有商品的方法
	checkCart : function() {
		var number = $("#select_num").html();
		if (0 == number) {
			warning("亲，请先选择商品");
			return;
		}
		if (number > 0) {
			this.checkUser();
		}
	},
	// 判断当前登录用户是否是会员或者销顾的方法
	checkUser : function() {
		wait();
		$.ajax({
			url : "/order/check/user/status",
//			timeout : 20000,
			type : "post",
			error : function() {
				close(1);
				warning("亲，您的网速不给力啊");
			},
			success : function(res) {
				console.log(res);
				// res中的check代表当前登录的是否为普通用户，其值为true代表是，其值为false代表不是
				if (true === res.check) {
					window.location.href = "/order";
					return;
				}
				if (false === res.check) {
					seller.getInfo();
				}
			}
		});
	},
	// 获取用户信息的方法
	getInfo : function() {
		wait();
		$.ajax({
			url : "/order/get/user/infomation",
//			timeout : 20000,
			type : "post",
			error : function() {
				close(1);
				warning("亲，您的网速不给力啊");
			},
			success : function(res) {
				$("#changeInfo").html(res);
				win_yes();
				close(1);
			}
		});
	},
	// 根据关键词查找用户的方法
	searchInfo : function() {
		this.keywords = this.getE("keywords").value;
		wait();
		$.ajax({
			url : "/order/change/user/info",
//			timeout : 20000,
			type : "post",
			data : {
				keywords : this.keywords
			},
			error : function() {
				close(1);
				warning("亲，您的网速不给力啊");
			},
			success : function(res) {
				close(1);
				seller.getE("changeInfo").innerHTML = res;
			}
		});
	},
	selectInfo : function(id) {
		if (id) {
			var params = "";
			// 获取所有value值大于0的input标签（即获得了所有数量要大于0的商品）
			$('.goodsSelectedQuantity')
					.each(
							// 获取标签之后拼接参数变量
							function(i) {
								var my_value = $('.goodsSelectedQuantity')
										.eq(i).val();
								if (!isNaN(my_value) && my_value > 0) {
									params = params
											+ $('.goodsSelectedQuantity').eq(i)
													.attr("id").replace(
															"quantity", "")
											+ "_";
									params = params + my_value + "-";
								}
							});
			window.location.href = "/coupon/order/before?realUserId=" + id
					+ "&params=" + params;
		}
	}
}
