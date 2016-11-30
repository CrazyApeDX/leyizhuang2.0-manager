function scroll_news() {

	var timer = setInterval(function() {
		$('.scroll_newsbox').animate(
				{
					top : '-40px'
				},
				2000,
				function() {
					$('.scroll_newsbox a:first').insertAfter(
							$('.scroll_newsbox a:last'));
					$('.scroll_newsbox').css({
						top : '0'
					});
				});
	}, 5000);
};

function my_hei() {

	function turn_hei(obj, my_math) {
		obj.height((obj.width()) * my_math)

	}
	;

	// //////////////////////////////////////////////////////////首页 00
	turn_hei($('.index_banner'), 0.5)// 首页
	turn_hei($('.index_nav li a'), 1)// nav导航
	turn_hei($('.index_goods01 dl dd'), 0.4)// 活动促销
	turn_hei($('.index_goods02'), 1)// nav
	turn_hei($('.index_goods03 ul li a'), 1.6)// nav
	// //////////////////////////////////////////////////////////////06分类

	// ///////////////////////////////////////////////////////////////

};

function you_draf(obj, child, see) {

	var go = window.screen.width;
	var timer = null;
	var iNow = 0; // 记录 索引
	var iScroll = 0; // 滑动的距离 每次滑动的距离 相加 储存在myX里面
	var straX = 0; // 最开始的坐标位子
	var myX = 0;// 用来储存滑动的距离
	var box_num = child.size();
	var len = -child.outerWidth(true) * box_num + see.width();
	// console.log(parseInt(child.css('marginLeft')))
	// 移除 冒泡 和 默认
	/*
	 * $(document).bind('touchmove',function(){ return false; })
	 */

	obj.bind('touchstart', function() {
		straX = event.changedTouches[0].pageX;
		myX = iScroll;
	});
	obj.bind('touchmove', function() {

		var disX = event.changedTouches[0].pageX - straX;
		iScroll = myX + disX;
		obj.animate({
			left : '' + iScroll + 'px'
		}, 0)
	});
	obj.bind('touchend', function() {

		var disX = event.changedTouches[0].pageX - straX;
		iScroll = myX + disX;
		if (iScroll > 0) {
			iScroll = 0;
		} else if (iScroll < len) {
			iScroll = len;
		}
		obj.animate({
			left : '' + iScroll + 'px'
		}, 400)
		// console.log(len)
	});

};

function win_out() {
	$('.win_cla').height($(window).height())
	$('.win_cla').css({
		left : '100%'
	});

	$('.win_cla dt span').click(function() {
		$('.win_cla').css({
			left : '100%'
		});
		$('.win_cla dd a').css({
			WebkitTransform : 'translateX(100%)'
		});
		$('.win_cla dd a').each(function(i) {
			$(this).css({
				WebkitTransition : '1s'
			});
		});
	});

	var hei = ($(window).height() - $('.win_cla dt span').height()) / 2
	// console.log(hei)
	$('.win_cla dt span').css({
		marginTop : '' + hei + 'px'
	})
	$('.win_cla dt span').height($('.win_cla dt span').width())

	// $('#go_out').click(function(){
	console.debug(123);
	$('.win_cla').css({
		left : '0px'
	});
	$('.win_cla dd a').css({
		WebkitTransform : 'translateX(0px)'
	});
	$('.win_cla dd a').each(function(i) {
		$(this).css({
			WebkitTransition : '1s ' + i * 200 + 'ms'
		});
	});
	// });
	// });
	// console.log(0)
};

function footer() {
	var le00 = ($(window).width() - $('.footer_act').width()) / 2;
	$('.footer_act').css({
		left : le00
	});
};

function win_colo_temp() {
	$('.colo_choice').height($(window).height())
	$('.colo_box').height($(window).height() - 150)
	$('.colo_box li').height($('.colo_box li').width() * 1)
	$('.colo_sec').height($(window).height() - 50)
	var hei = $(window).height() - 100
	$('.colo_sec dl').css({
		maxHeight : hei
	})

	$('.lei_box01 dl dt a').click(function() {
		// $('.colo_choice').css({
		// left : '0px'
		// })
	})
	$('.colo_back').click(function() {
		$('.colo_choice').css({
			left : '100%'
		})
	})

	$('.colo_test').click(function() {
		$('.colo_choice').css({
			left : '100%'
		})
	})

	$('.colo_title a').click(function() {
		$('.colo_sec').slideDown()
	})
	$('.colo_clo').click(function() {
		$('.colo_sec').slideUp()
	})
}

function win_cla() {
	$('.win_cla').height($(window).height())

	$('.win_cla dt span').click(function() {
		$('.win_cla').css({
			left : '100%'
		});
		$('.win_cla dd a').css({
			WebkitTransform : 'translateX(100%)'
		});
		$('.win_cla dd a').each(function(i) {
			$(this).css({
				WebkitTransition : '1s'
			});
		});
	});

	var hei = ($(window).height() - $('.win_cla dt span').height()) / 2
	// console.log(hei)
	$('.win_cla dt span').css({
		marginTop : '' + hei + 'px'
	})
	$('.win_cla dt span').height($('.win_cla dt span').width())
};

function fen_scroll() {
	$('.fen_testleft').height($(window).height()-160); //160是 顶部 50  底部 50+60 得出来的 
	var che = $('.fen_goodbox dl dt .my_checkbox');
	var dlb = $('.fen_goodbox dl dt')
	var hei = (dlb.height() - che.height()) / 2
	che.css({
		marginTop : hei
	})
	$('.fen_goodbox').height($(window).height() - 200) //200是 顶部 50+40  底部 50+60 得出来的 
	// ///////////
	var all_li = 0;
	var all_ul = $('.fen_testtop ul');
	// ////循环ul
	all_ul.each(function(i) {
		var len = all_ul.eq(i).children().size() * 82;
		all_ul.eq(i).width(len);
		/* my_draf($('.fen_testtop ul').eq(i),$('.fen_testtop'),'left') */
	});
	all_ul.css({
		display : 'none'
	});
	all_ul.eq(0).css({
		display : 'block'
	});
	$('.fen_testleft ul li a').eq(0).css({
		background : '#cc1421',
		color : 'white'
	})
	$('.fen_testleft ul li').each(function(i) {
		$('.fen_testleft ul li').eq(i).click(function() {
			all_ul.css({
				display : 'none'
			});
			all_ul.eq(i).css({
				display : 'block'
			});
			$('.fen_testleft ul li a').css({
				background : '#f8f8f8',
				color : '#333333'
			})
			$('.fen_testleft ul li a').eq(i).css({
				background : '#cc1421',
				color : 'white'
			})
		});
	});
};

function turn_hei(obj,my_math){
	obj.height((obj.width())*my_math);
};

