// 改变商品数量的方法
function changeQuantity(goodsId, operation) {
	// 获取指定商品显示数量的输入框的id
	var quantityElementId = "#quantity" + goodsId;
	// 获取当前指定商品选择的数量
	var quantity = $(quantityElementId).val();
	// 获取商品的库存量的标签的id
	var inventoryId = "#inventory" + goodsId;
	// 获取当前商品的库存量
	var inventory = $(inventoryId).val();

	// 如果是减少当前商品的数量
	if ("delete" == operation) {
		// 如果当前商品的数量已经是0了就不做任何处理
		if (0 == quantity) {
			warning("亲，不能再少啦");
			return;
		}
		// 正常减少数量
		quantity = parseInt(quantity) - 1;
	}

	// 如果是增加商品数量的情况
	if ("add" == operation) {
		// 如果商品数量已经等于库存量之后就不做任何处理
		if (inventory == quantity) {
			warning("亲，库存只有这么多啦");
			return;
		}
		// 正常增加数量
		quantity = parseInt(quantity) + 1;
	}

	// 把更新后的商品信息和已选数量显示到页面上
	$(quantityElementId).val(quantity);
}

// 判断是否为代下单的方法
function checkSeller() {
	var params = "";
	// 获取所有value值大于0的input标签（即获得了所有数量要大于0的商品）
	$('.goodsSelectedQuantity').each(
			// 获取标签之后拼接参数变量
			function(i) {
				var my_value = $('.goodsSelectedQuantity').eq(i).val();
				if (!isNaN(my_value) && my_value > 0) {
					params = params
							+ $('.goodsSelectedQuantity').eq(i).attr("id")
									.replace("quantity", "") + "_";
					params = params + my_value + "-";
				}
			});
	if ("" == params) {
		warning("亲，请先选择商品的数量");
		return;
	}
	// 开启等待方法
	wait();
	$.ajax({
		url : "/coupon/check",
		timeout : 20000,
		type : "post",
		error : function() {
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			if (-1 === res.status) {
				seller.getInfo();
			}
			if (0 === res.status) {
				window.location.href = "/coupon/order/before?params=" + params;
			}
		}
	});
}

// 立即购买券的方法
function addCart() {
	var params = "";
	// 获取所有value值大于0的input标签（即获得了所有数量要大于0的商品）
	$('.goodsSelectedQuantity').each(
			// 获取标签之后拼接参数变量
			function(i) {
				var my_value = $('.goodsSelectedQuantity').eq(i).val();
				if (!isNaN(my_value) && my_value > 0) {
					params = params
							+ $('.goodsSelectedQuantity').eq(i).attr("id")
									.replace("quantity", "") + "_";
					params = params + my_value + "-";
				}
			});
	if ("" == params) {
		warning("亲，请先选择商品的数量");
		return;
	}
	// 开启等待图标
	wait();

	$.ajax({
		url : "/coupon/add/cart",
		type : "post",
		timeout : 10000,
		data : {
			params : params,
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待图标
			close(100);
		}
	});
}
