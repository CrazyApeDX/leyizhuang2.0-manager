function clickLevelTwo(elementId,categoryId) {
	var goods_div = $("#goods" + elementId);
	
	// 显示出正确的商品栏
	$(".ctrlGoods").css("display", "none");
	goods_div.css("display", "block");
	
	// 点击左变切换
	$('.fen_testtop ul li a').css({
		background : '#e8e8e8',
		color : '#333333'
	});
	// 是被点击的元素变颜色
	$("#" + elementId).css({
		background : '#ffaa00',
		color : 'white'
	});
	
	//开启等待图标
	wait();
	$.ajax({
		url:"/goods/step/get",
		type:"post",
//		timeout:10000,
		data:{
			categoryId:categoryId
		},
		error:function(){
			close(1),
			warning("亲，您的网速不给力啊");
		},
		success:function(res){
			close(1);
			goods_div.html(res);
		}
	});

}

function change(level_one_id) {
	var level_two = document.getElementById("level_two" + level_one_id);
	var li_arry = level_two.getElementsByTagName("li");
	var li = li_arry[0].getElementsByTagName("a")[0];
	li.click();
}
