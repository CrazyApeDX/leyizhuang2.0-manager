$(function(){
	my_select();//模拟select              //注册页  01
	my_hei();//百分比高设置				
	banner_scroll();//首页banner滑动		//index  00	
	scroll_news();//滚动新闻				//index  00	
	you_draf($('.index_scroll_goods'),$('.index_scroll_goods a'),$(window));
})

function turn_hei(obj,my_math){
		obj.height((obj.width())*my_math)
		
	};
function my_hei(){
	
	function turn_hei(obj,my_math){
		obj.height((obj.width())*my_math)
		
	};
	
	////////////////////////////////////////////////////////////首页 00
		turn_hei($('.index_banner'),0.5)//首页
		turn_hei($('.index_nav li a'),1)//nav导航		
		turn_hei($('.index_goods01 dl dd'),0.4)//活动促销
		turn_hei($('.index_goods02'),1)//nav		
		turn_hei($('.index_goods03 ul li a'),1.6)//nav
	////////////////////////////////////////////////////////////////06分类
		
	
	
	/////////////////////////////////////////////////////////////////
	
	
};
	
	
function my_select(){

				$('.my_sele').text($('.reg_content dt select option').eq(0).text());
		$('.reg_content dt select').click(function(){
			var optionvalue=$(this).find('option:selected').text();
			$('.my_sele').text(optionvalue);

		});
}		
	
	function win_out(){
		//$('.index_head div').click(function(){
			//console.log(0)
			$('.win_cla').css({left:'0px'});			
			$('.win_cla dd a').css({WebkitTransform:'translateX(0px)'});
			$('.win_cla dd a').each(function(i){
				$(this).css({WebkitTransition:'1s ' + i*200 +'ms'});				
			});												
		//});
		//console.log(0)
	};
	
	
	
	function win_cla(){
		$('.win_cla').height($(window).height())
		

		$('.win_cla dt span').click(function(){
			$('.win_cla').css({left:'100%'});
			$('.win_cla dd a').css({WebkitTransform:'translateX(100%)'});
			$('.win_cla dd a').each(function(i){
				$(this).css({WebkitTransition:'1s'});				
			});				
		});	
		
		var hei = ($(window).height() - $('.win_cla dt span').height())/2
		//console.log(hei)
		$('.win_cla dt span').css({marginTop:''+hei+'px'})
		$('.win_cla dt span').height($('.win_cla dt span').width())
	};
	
	
	
	function banner_scroll(){
		
			var go = window.screen.width;
			var timer = null;
			var iNow = 0 ;    //记录 索引
			var iScroll = -2*go;  //滑动的距离      每次滑动的距离 相加  储存在myX里面
			var straX = 0;  //最开始的坐标位子
			var myX = 0;//用来储存滑动的距离
			//移除  冒泡 和 默认
			/*$(document).bind('touchmove',function(){
				return false;
			})*/
			
			$('.scroll_box').bind('touchstart',function(){
				$('.scroll_box').css({WebkitTransition:'0'})
				straX = event.changedTouches[0].pageX;				
				myX	= iScroll;
			});
			
			$('.scroll_box').bind('touchmove',function(){
				
				var disX =event.changedTouches[0].pageX - straX;				
					iScroll= myX + disX;
										
					//$('.box').css({WebkitTransform:'translateX('+iScroll+'px)'})
					$('.scroll_box').animate({left:''+iScroll+'px'},0)
			});
			
			$('.scroll_box').bind('touchend',function(){
				
				var disX =event.changedTouches[0].pageX - straX;				
					//iScroll = myX + disX;
				iScroll= myX + disX;
				iNow = iScroll/go;
				iNow = Math.round(iNow)
				iScroll =iNow*go;
				
				if(!$('.scroll_box').is(':animated')){
						$('.scroll_box').animate({left:''+iScroll+'px'},400,function(){
							if(iScroll <= -3*go){
								$('.scroll_box img:first').insertAfter($('.scroll_box img:last'))
							$('.scroll_box').css({left:'-200%'})
							myX = iScroll = -2*go;
								
							}else if(iScroll >= -go){
								$('.scroll_box img:last').insertBefore($('.scroll_box img:first'))
								$('.scroll_box').css({left:'-200%'})
								myX = iScroll = -2*go;						
							};	
							console.log(iNow)
						})
				};
			});
		
			//自动走
			

	};
	
	
	function scroll_news(){
		
		var timer = setInterval(function(){
			$('.scroll_newsbox').animate({top:'-40px'},2000,function(){
				$('.scroll_newsbox a:first').insertAfter($('.scroll_newsbox a:last'));
				$('.scroll_newsbox').css({top:'0'});
			});
		},5000);											
	};
	
	function my_check(){
		//var my_num = [];
		$('.my_black').hide();
		$('.my_checkbox').each(function(i){
			$(this).click(function(){
				if($('.my_black').eq(i).css('display') == 'none'){
					$('.my_black').eq(i).show();
				}else{
					$('.my_black').eq(i).hide();
				};
				/*if($('.my_black').css('display') == 'block'){
					$('.go_buy').animate({height:'50px',bottom:'60px'})

				}else if($('.my_black').css('display') == 'none'){
					$('.go_buy').animate({height:'0px',bottom:'0px'})
				}*/
				/////////////娱乐  统计数据
				/*if($('.my_black').eq(i).css('display') == 'block'){
					my_num.push($('.my_black').eq(i));
				}else if($('.my_black').css('display') == 'none'){
					my_num.shift();
				}
				$('.go_buy span').text(my_num.length);
				//console.log(my_num.length)*/
			})	
		})
		
		
	};
	
	
	function win_colo(obj){
		$('.colo_choice').height($(window).height())
		$('.colo_box').height($(window).height()-150)
		$('.colo_box li').height($('.colo_box li').width()*1)
		$('.colo_sec').height($(window).height()-50)
		
		
		
		var hei = $(window).height()-100
		$('.colo_sec dl').css({maxHeight:hei})
						
		
		obj.click(function(){
			
			//console.log(0)
			$('.colo_choice').css({left:'0px'})	
		})
		$('.colo_back').click(function(){
			$('.colo_choice').css({left:'100%'})	
		})
		
		$('.colo_test').click(function(){
			$('.colo_choice').css({left:'100%'})	
		})
		
		$('.colo_title a').click(function(){
			if($('.colo_sec').css('display') == 'none'){
				$('.colo_sec').slideDown()	
			}else if($('.colo_sec').css('display') == 'block'){
				$('.colo_sec').slideUp()
			};			
		})
		$('.colo_clo').click(function(){
			$('.colo_sec').slideUp()	
		})
	
	};
	
	
	function fen_scroll(){
		$('.fen_testleft').height($(window).height());
		var che = $('.fen_goodbox dl dt .my_checkbox');
		var dlb = $('.fen_goodbox dl dt')
		var hei = (dlb.height() - che.height())/2
		che.css({marginTop:hei})
		$('.fen_goodbox').height($(window).height()-200)
		/////////////
		var all_li = 0;
		var all_ul = $('.fen_testtop ul');



		//////循环ul
		all_ul.each(function(i){
			var len = all_ul.eq(i).children().size()*80;
			all_ul.eq(i).width(len);
			/*my_draf($('.fen_testtop ul').eq(i),$('.fen_testtop'),'left')*/
		});

		all_ul.css({display:'none'});
		all_ul.eq(0).css({display:'block'});
		//////////////////////////////////
		////点击左变切换
		$('.fen_testleft ul li').each(function(i){
			$('.fen_testleft ul li').eq(i).click(function(){
				all_ul.css({display:'none'});
				all_ul.eq(i).css({display:'block'});
			});
			
			
		});
	
		

	};
/////////////////////////////////////////////////////////////////////////////////左右滑动封装
	function my_draf(obj,obj_child,guide,page){
			
			var go = window.screen.width;
			var timer = null;
			var iNow = 0 ;    //记录 索引
			var iScroll = 0;  //滑动的距离      每次滑动的距离 相加  储存在myX里面
			var straX = 0;  //最开始的坐标位子
			var myX = 0;//用来储存滑动的距离
			var len = -obj.width()+obj_child.width()
			//console.log(obj.width())
			//移除  冒泡 和 默认
			/*$(document).bind('touchmove',function(){
				return false;
			})*/
			
			obj.bind('touchstart',function(){
				straX = event.changedTouches[0].pageX;				
					myX	= iScroll;
			});
			obj.bind('touchmove',function(){
				
				var disX =event.changedTouches[0].pageX - straX;				
					 iScroll= myX + disX;										
					obj.animate({[guide]:''+iScroll+'px'},0)
			});
			obj.bind('touchend',function(){
				
				var disX =event.changedTouches[0].pageX - straX;				
				iScroll= myX + disX;
				if(iScroll>0){
					iScroll=0;
				}else if(iScroll<len){
					iScroll = len;
				}
				obj.animate({[guide]:''+iScroll+'px'},400)
				console.log(len)
			});
		
			};
/////////////////////////////////////////////////////////////////////////////////	
	
	
//////////////////////////////////////////////////////////////////////////////////拖动封装
		function you_draf(obj,child,see){
			
			var go = window.screen.width;
			var timer = null;
			var iNow = 0 ;    //记录 索引
			var iScroll = 0;  //滑动的距离      每次滑动的距离 相加  储存在myX里面
			var straX = 0;  //最开始的坐标位子
			var myX = 0;//用来储存滑动的距离
			var box_num =child.size();
			var len = -child.outerWidth(true) * box_num + see.width();
			//console.log(parseInt(child.css('marginLeft')))
			//移除  冒泡 和 默认
			/*$(document).bind('touchmove',function(){
				return false;
			})*/
			
			obj.bind('touchstart',function(){
				straX = event.changedTouches[0].pageX;				
					myX	= iScroll;
			});
			obj.bind('touchmove',function(){
				
				var disX =event.changedTouches[0].pageX - straX;				
					 iScroll= myX + disX;										
					obj.animate({left:''+iScroll+'px'},0)
			});
			obj.bind('touchend',function(){
				
				var disX =event.changedTouches[0].pageX - straX;				
				iScroll= myX + disX;
				if(iScroll>0){
					iScroll=0;
				}else if(iScroll<len){
					iScroll = len;
				}
				obj.animate({left:''+iScroll+'px'},400)
				//console.log(len)
			});
		
			};
////////////////////////////////////////////////////////////////////////////////////////	
//////////////////////////////////////////////////////////////////////////////缓冲
		function wait(obj){
			var wait = ($(window).height() - $('.wait img').height())/2;
			$('.wait').hide();
			$('.wait img').css({marginTop:wait})
			var timer =null;
			
			obj.click(function(){
				$('.wait').show();
			});
		};
		
		function close(){
			$('.wait').hide();
		};
//////////////////////////////////////////////////////////////////////////////		
		function footer(){
			var le = ($(window).width() - $('.footer_act').width())/2;
			$('.footer_act').css({left:le});
		};
//////////////////////////////////////////////////////////////////////////////				
	function warning(){
		var le = ($(window).width()-$('.warning').width())/2;
		var he = ($(window).height()-$('.warning').height())/2;
		
		$('.warning').css({left:le,top:he});
		$('.warning').show();
	};
	function warning_out(){
		$('.warning').hide();
	};
//////////////////////////////////////////////////////////////////////////////评价
function star_go(obj){
	var oBox = document.getElementById(obj);
	var aStar = oBox.children;
	var arr_star = ['url(img/star_bd.png)','url(img/star_bd02.png)'];	
	//aStar[0].style.backgroundImage = 'url(img/star_bd.png)';	
		for(var i=0;i<aStar.length;i++){
			aStar[i].index = i;
			aStar[i].onclick = function(){
				for(var i=0;i<aStar.length;i++){
					aStar[i].style.backgroundImage =arr_star[1];
				};				
					//console.log(this.index)	
				for(var i=0;i<aStar.length;i++){
					if(i ==this.index+1){				
						break;
					};
					aStar[i].style.backgroundImage =arr_star[0];
				};		
			};	
		};
};		
//////////////////////////////////////////////////////////////////////////////	匿名	
function change(){
	var oChange = document.getElementById('change');
	var oCimg = oChange.children[0];
	var arr_img = ['img/star_img03.png','img/star_img04.png'];
	var onOff = true;
	oChange.onclick = function(){
		if(onOff){			
			oCimg.src = arr_img[1];
		}else{
			oCimg.src = arr_img[0];
		};
		//oCimg.src = arr_img[1];
		onOff = !onOff;
	};
};
		
/////////////////////////////////////////////////////////////////新加优惠券		
////////////////计算heml 高
function html_hi(){
	var oHtml = document.getElementsByTagName('html')[0];
	var win_hi = window.screen.height;
	var doc_hi =document.documentElement.offsetHeight;
	if(doc_hi>=win_hi){
		oHtml.style.height = doc_hi + 'px';
	}else{
		oHtml.style.height = win_hi + 'px';
	};
	
};
////////////////////////////////////////		
function changest(obj,obj_show,sty){
	var ang = $(obj);
	var ang_img = $(obj_show);
	ang_img[0].style.display = 'block';
	ang[0].className = sty;
	for(var i=0;i<ang.length;i++){
		ang[i].index = i;
		ang[i].onclick = function(){
			for(var i=0;i<ang.length;i++){
				ang_img[i].style.display = 'none';
				ang[i].className = '';
			};
			this.className = sty;
			ang_img[this.index].style.display = 'block';
		};
	};
};		
		
////////////////////////////////////////////////		
function drag_adv(){
	 var oWar = $('.warard')[0];
			 var disX =0;
			 var disY =0;
			 //按下
			 oWar.addEventListener('touchstart',richStar,false);
			 function richStar(ev){
			 	//阻止默认
			 	ev.preventDefault();
			 	disX =event.changedTouches[0].pageX - oWar.offsetLeft;
			 	disY =event.changedTouches[0].pageY - oWar.offsetTop;
			 	oWar.style.webkitTransition = '';
			 };
			 //拖拽
			 oWar.addEventListener('touchmove',richMove,false);
			 function richMove(){
				oWar.style.left =event.changedTouches[0].pageX - disX +'px';
				oWar.style.top =event.changedTouches[0].pageY - disY +'px';
			 };	
			 oWar.addEventListener('touchend',richEnd,false);
			 function richEnd(){
			 	oWar.style.left =window.screen.width - oWar.offsetWidth + 'px';
				oWar.style.top ='40%';
				oWar.style.webkitTransition = '1s';
			 };
};
		
		
		
		
