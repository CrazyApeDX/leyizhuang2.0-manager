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
	    	/*
		if (inventory == quantity) {
			warning("亲，库存只有这么多啦");
			return;
		}*/
		// 正常增加数量
		quantity = parseInt(quantity) + 1;
	}
	/*
	if(parseInt(quantity)>parseInt(inventory)){
		// 设置为最大库存
		$(quantityElementId).val(inventory);
		warning("亲，库存只有这么多啦");
		return;
	}
	*/

	// 把更新后的商品信息和已选数量显示到页面上
	$(quantityElementId).val(quantity);
}

// 选择调色包的方法
function changeColor(goodsId) {

	// 开启等待响应的图标
	wait();

	// ajax获取指定商品的所有调色包
	$.ajax({
		url : "/goods/get/color",
		type : "post",
		timeout : 10000,
		data : {
			"goodsId" : goodsId,
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待响应的图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			$("#color_package_select").html(res);
			// 关闭等待响应的图标
			close(1000);

			$('.colo_choice').height($(window).height())
			$('.colo_box').height($(window).height() - 150)
			$('.colo_box li').height($('.colo_box li').width() * 0.16)
			$('.colo_sec').height($(window).height() - 50)
			var hei = $(window).height() - 100
			$('.colo_sec dl').css({
				maxHeight : hei
			});
			$('.colo_choice').animate({
				left : '0px'
			});
			$('.colo_back').click(function() {
				$('.colo_choice').css({
					left : '100%'
				});
			});

			$('.colo_test').click(function() {
				$('.colo_choice').css({
					left : '100%'
				});
			});

			$('.colo_title a').click(function() {
				$('.colo_sec').slideDown()
			});
			$('.colo_clo').click(function() {
				$('.colo_sec').slideUp()
			});
		}
	});
}

// 选择指定调色包的方法
function getColor(colorNum, colorId,obj) {
	$("#color_name").html(colorNum);
	// 拼接代表价格的标签的id
	var priceId = "#colorPackagePrice" + colorId;
	// 获取指定调色包的价格
	var colorPackagePrice = $(priceId).val();
	// 将指定调色包的价格显示在价格栏位上
	$("#color_price").html("￥" + colorPackagePrice);
	// 将指定调色包的价格存储到单价标签中
	$("#unit_price").val(colorPackagePrice);
	// 拼接代表库存的标签的id
	var inventoryId = "#colorPackageInventory" + colorId;
	// 获取指定调色包的库存量
	var colorPackageInventory = $(inventoryId).val();
	// 将指定调色包的库存量存储到库存标签中
	$("#color_package_inventory").val(colorPackageInventory);
	$('.colo_box li').css("border","none");
	$(obj).css("border","1px solid #cccccc");
}

// 改变选中调色包的数量的方法
function changeColorNum(operation) {
	// 获取目前选中的调色包的单价
	var unit_price = $("#unit_price").val();
	// 获取当前调色包的数量
	var nowNum = $("#select_color_quantity").val();
	// 获取当前选择调色包的库存
	var inventory = $("#color_package_inventory").val();

	// 增加数量的情况
	if ("add" == operation) {
		// 如果是数量已经等于库存了，则不做任何操作
		if (nowNum == inventory) {
			return;
		}
		$("#select_color_quantity").val(parseInt(nowNum) + 1);
		var colorPrice = unit_price * (parseInt(nowNum) + 1);
		$("#color_price").html("￥" + colorPrice);
	}
	// 减少数量的情况
	if ("delete" == operation) {
		if (0 == nowNum) {
			return;
		}
		$("#select_color_quantity").val(parseInt(nowNum) - 1);
		var colorPrice = unit_price * (parseInt(nowNum) - 1);
		$("#color_price").html("￥" + colorPrice);
	}
}

// 确定调色的方法
//增加商品id参数 zp
function addColor(goodsId) {
	// 获取当前数量
	var nowNum = $("#select_color_quantity").val();
	// 如果数量为0，不进行任何操作
	if (0 == nowNum) {
		return;
	}
	// 获取当前的颜色编号
	var colorName = $("#color_name").html();
	

	// 开启等待响应的图标
	wait();

	// 进行异步请求，存储数据
	$.ajax({
		url : "/goods/color/add",
		timeout : 10000,
		type : "post",
		data : {
			"colorName" : colorName,
			"quantity" : nowNum,
			"goodsId":goodsId
		},
		success : function(res) {
			$("#select_colors_by_dx").html(res);

			// 关闭等待响应的图标
			close();

			// 获取当前所有已选商品的数量
			selected_number = $("#selected_number").val();
			// 将已选数量正确显示到指定的位置
			$("#select_num").text(selected_number);

			var the_new_quantity = 0;
			$("#select_color_quantity").val(0);
			var unit_price = $("#unit_price").val();
			var colorPrice = unit_price;
			$("#color_price").html("￥" + colorPrice);

			$('.colo_choice').height($(window).height())
			$('.colo_box').height($(window).height() - 150)
			$('.colo_box li').height($('.colo_box li').width() * 0.16)
			$('.colo_sec').height($(window).height() - 50)
			var hei = $(window).height() - 100
			$('.colo_sec dl').css({
				maxHeight : hei
			});
			$('.colo_choice').animate({
				left : '0px'
			});
			$('.colo_back').click(function() {
				$('.colo_choice').css({
					left : '100%'
				});
			});

			$('.colo_test').click(function() {
				$('.colo_choice').css({
					left : '100%'
				});
			});

			$('.colo_title a').click(function() {
				$('.colo_sec').slideDown()
			});
			$('.colo_clo').click(function() {
				$('.colo_sec').slideUp()
			});

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待响应的图标
			close(1);
			warning("亲，您的网速不给力啊");
		}
	});
}

// 删除已选调色包的方法
function deleteSelectedColorPackage(id) {
	// 获取节点元素的id
	var docElementId = "#colorPackage" + id;
	// 获取节点元素
	var ele = $(docElementId);

	// 开启等待图标
	wait();
	$.ajax({
		url : "/goods/delete/color",
		type : "post",
		data : {
			"colorPackageGoodsId" : id,
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			// 关闭等待响应的图标
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			// 关闭等待响应的图标
			close(1);
			console.debug(id);
			ele.remove();

			// 将已选数量正确显示到指定的位置
			$("#select_num").text(res.selected_number);
		}
	});
}

// 加入已选的方法  
//isGoHistory=1 返回上个页面 
function addCart(isGoHistory) {
	var params = "";
	// 获取所有value值大于0的input标签（即获得了所有数量要大于0的商品）
	$('.goodsSelectedQuantity').each(
			// 获取标签之后拼接参数变量
			function(i) {
				var my_value = $('.goodsSelectedQuantity').eq(i).val();
				if (!isNaN(my_value) && my_value > 0) {
					params = params
							+ $('.goodsSelectedQuantity').eq(i).attr("id")
									.replace("quantity", "") + "+";
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
		url : "/goods/add/cart",
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
			if (0 != res.selected_number) {
				$('.goodsSelectedQuantity').val('0');
				// 将已选商品的数量正确显示
				$("#select_num").text(res.selected_number);
				if(isGoHistory==1){
					window.location.href = "/user/selected";
				}
			}
		}
	});
}

// 添加或删除收藏的方法
function operateCollect(goodsId) {
	var element = $("#operate");
	// 开启等待图标
	wait();
	$.ajax({
		url : "/goods/operate/collection",
		type : "post",
		timeout : 10000,
		data : {
			goodsId : goodsId
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			close(1);
			warning("亲，您的网速不给力啊");
		},
		success : function(res) {
			close(100);
			// 取消收藏的情况
			if (element.hasClass("isCollectTrue")) {
				element.removeClass("isCollectTrue");
				element.addClass("isCollectFalse");
				warning("已取消");
			}
			// 添加收藏的情况下
			else if (element.hasClass("isCollectFalse")) {
				element.removeClass("isCollectFalse");
				element.addClass("isCollectTrue");
				warning("已添加");
			}
		}
	});
}

/**
 * 去结算的方法（首先判定当前是否已有选择的商品）
 * 
 * @author dengxiao
 */
function clearing() {
	var number = $("#select_num").html();
	if (0 == number) {
		warning("亲，请先选择商品");
		return;
	}

	if (number > 0) {
		window.location.href = "/order";
	}
}

/**
 * 在商品详情页点击立即购买的方法
 * 
 * @author dengxiao
 */
function buyNow(id) {
	var quantity = $("#quantity" + id).val();

	if (0 == quantity) {
		warning("亲，请选择商品的数量");
	}

	window.location.href = "/goods/buy/now?goodsId=" + id + "&quantity="
			+ quantity;
}
function quantityChange(goodsId){
	// 获取指定商品显示数量的输入框的id
	var quantityElementId = "#quantity" + goodsId;
	// 获取当前指定商品选择的数量
	var quantity = $(quantityElementId).val();
	// 获取商品的库存量的标签的id
	var inventoryId = "#inventory" + goodsId;
	// 获取当前商品的库存量
	var inventory = $(inventoryId).val();
	
//	if(parseInt(quantity)>parseInt(inventory)){
//		warning("亲，库存只有这么多啦");
//		$(quantityElementId).val(inventory);
//		return;
//	}
	
}
//限制输入 只能输入数字
function keyup(obj){
	if(obj.value.length==1){obj.value=obj.value.replace(/[^1-9]/g,'')}else{obj.value=obj.value.replace(/\D/g,'')};
}
//限制输入 只能输入数字
function afterpaste(obj){
	if(obj.value.length==1){obj.value=obj.value.replace(/[^1-9]/g,'')}else{obj.value=obj.value.replace(/\D/g,'')};
}

//商品数量输入框获取焦点时清空
function clearQuantity(obj){
	obj.value="";
}

//商品数量如果为空，则设为0
function setQuantity(obj){
	if(obj.value.length==0){
		obj.value=obj.min;
	}
}