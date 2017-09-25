function operate(operation, type, id) {
	if (0 == type) {
		var elementId = "#goods" + id;
		var inventoryId = "#goods" + id + "quantity";
		var priceId = "#goods" + id + "price";
	}

	var quantity = $(elementId).val();
	var inventory = $(inventoryId).val();
	var priceElement = $(priceId);

	if (0 == operation) {
		if (0 == quantity) {
			warning("亲，不能再少了");
			return;
		}
	}

	/*
	if (1 == operation) {
		if (inventory == quantity) {
			warning("亲，库存只有这么多啦");
			return;
		}
	}
	*/
	
	if(parseInt(quantity)<0){
		$(elementId).val(0);
		warning("亲，数量不能小于0");
		return;
	}
	
	/*
	if(parseInt(inventory)<0){
		$(elementId).val(0);
		warning("亲，库存不足");
		return;
	}
	*/
	
	//数字比较
	/*
	if(parseInt(quantity)>parseInt(inventory)){
		$(elementId).val(inventory);
		warning("亲，库存只有这么多啦");
	}
	*/
	
	
		
	//增加当前数量参数
	// 开启等待图标
	wait();
	// 发送异步请求
	$.ajax({
		url : "/user/selected/change/quantity",
		type : "post",
//		timeout : 10000,
		data : {
			operation : operation,
			type : type,
			id : id,
			quantity:quantity
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			close(100);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待图标
			close(100);
			$("#my_selected").html(res);
		}
	});
}

/**
 * 去结算的方法（首先判定当前是否存在已选商品）
 * 
 * @author dengxiao
 */
function clearing() {
	var number = $("#number").val();
	if (0 == number) {
		warning("亲，请先选择商品");
		setTimeout(function() {
			window.location.href = "/goods/normal/list";
		}, 1000);
		return;
	}

	if (number > 0) {
		window.location.href = "/order/clear";
	}
}
