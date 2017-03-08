<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
            html,body{width:100%;height: 100%;}

        </style>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="copyright" content="" />
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <meta charset="utf-8">
        <title>我的订单</title>
        
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/xiuxiu.css"/>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/index.js" type="text/javascript"></script>
        <#--<script src="/client/js/user_order.js" type="text/javascript"></script>-->
        <script src="/client/js/Validform_v5.3.2_min.js" type="text/javascript"></script>

    </head>
    <script type="text/javascript">
    <!--
    $(document).ready(function(){
            $("#form1").Validform({
                tiptype:4, 
                ajaxPost:true,
                callback:function(data){
                    warning(data.message);
                    if(data.code==0)
                    {
                        win_no();
                        window.location.reload();
                    }
                }
            });
     })
     -->
     
          function order_return(id){
            var he = ($(window).height() - $('.turn_div div').height())/2 - 50;
            $('.turn_div div').css({marginTop:he});   
            $('.turn_div').fadeIn(600);
            $("#orderId").attr("value",id);
        };
        
        function win_no_return(){  
            $('.turn_div').fadeOut(600);
        };
        
        <#--
	        function win_no_turn(){  
	            $('.turn_div').fadeOut(600);
	        };
        -->
        
        window.onload = function(){
            footer();
        }
        
        <#-- 模糊查询订单的方法 -->
        function searchOrder(){
            <#-- 获取查询关键词 -->
            var keywords = $("#keywords").val();
            if("" === keywords){
                return;
            }
            <#-- 开启等待图标 -->
            wait();
            $.ajax({
                url : "/user/order/search",
                type : "post",
                timeout : 20000,
                data:{
                    keywords : keywords
                },
                error:function(){
                    close(1);
                    warning("亲，您的网速不给力啊");
                },
                success:function(res){
                    $("#user_all_order").html(res);
                    $("#all").click();
                    close(1);
                }
            });
        }
        
        function checkReturn(id) {
        	if (id) {
        		wait();
        		
        		$.ajax({
        			url: '/user/can/return',
        			method: 'POST',
        			data: {
        				id: id
        			},
        			success: function(res) {
        				if (0 === res.status) {
        					window.location.href = '/user/order/return?orderId=' + id;
        				} else {
        					close(1);
        					warning('您还有欠款未还清，不能够退货');
        				}
        			},
        			error : function() {
        				warning("亲，您的网速不给力啊");
        				close(1);
        			}
        		});
        	}
        }
    </script>
    <!--  <style type="text/css"> 
    #slideDown{margin-top: 0;width: 100%;} 
         #slideDown1,#slideDown2{width: 100%;height: 70px;;background: #e9f4f7;display: none;} 
        #slideDown1{height: 20px;} 
        #slideDown1>p,#slideDown2>p{margin: 20px auto;text-align:center;font-size: 14px;color: #37bbf5;} 
    </style>  -->
    <body style="background: #f3f4f6;">
    <div class="turn_div">
        <form id="form1" action="/user/order/return" method="post">
          <input type="hidden" value="" id="orderId" name="id">
        <div>                   
            <p id="title">退货原因</p>
            <textarea name="remark"></textarea>
            <span>
              <#--
                    <input type="hidden" name="turnType" id="" value="1" />                   
                <section>
                    <input type="radio" name="turnType" id="" value="1" checked="checked"/>
                    <label>到店退货</label>
                </section>
                <section>
                    <input type="radio" name="turnType" id="" value="2" />                   
                    <label>物流取货</label>
                </section>
                <input onclick="win_no();" type="button" name="" id="" value="否" />
                -->
                <input type="submit" name="" id="" value="是" />
                <input onclick="javascript:win_no_return();" type="button" value="否" />
            </span>             
        </div>
        </form>
    </div>
        <#-- 引入公共confirm窗口 -->
        <#include "/client/common_confirm.ftl">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <div id="content">
            <div class="sec_header">
                <a class="back" href="javascript:history.go(-1);"></a>
                <p>我的订单</p>             
            </div>
            
            <section class="my_order">
                <!-- 订单管理 -->
                <ul class="order-nav">
                    <li <#if status==0>class="current"</#if>><a href="/fit/history/0">全部</a></li>
                    <li <#if status==2>class="current"</#if>><a href="/fit/history/2">待付款</a></li>
                    <li <#if status==3>class="current"</#if>><a href="/fit/history/3">待发货</a></li>
                    <li <#if status==4>class="current"</#if>><a href="/fit/history/4">待收货</a></li>
                    <li <#if status==6>class="current"</#if>><a href="/fit/history/6">已完成</a></li>
                </ul>
                
                <!-- 订单分类 -->
                <article class="orders-species"> 
                    <#if orderPage??&&orderPage.content?size gt 0>
                        <div class="some_orders">
                            <#list orderPage.content as item>
                                <ol class="order-list" id="container${item.id?c}">
                                    <li class="li1">
                                        <label>订单号：<span>${item.orderNumber!''}</span></label>
                                        <div class="species">
                                            <#if item.statusId??>
                                                <#switch item.statusId>
                                                    <#case 2>待付款<#break>
                                                    <#case 3>待发货<#break>
                                                    <#case 4>待签收<#break>
                                                    <#case 5>待评价<#break>
                                                    <#case 6>已完成<#break>
                                                    <#case 7>已取消<#break>
                                                    <#case 9>退货中<#break>
                                                    <#case 10>退货确认<#break>
                                                    <#case 11>退货取消<#break>
                                                    <#case 12>退货完成<#break>
                                                </#switch>
                                            </#if>
                                        </div>
                                    </li>
                                    <#if item.orderGoodsList??>
                                        <#list item.orderGoodsList as goods>
                                            <li class="li2">
                                                <div class="img"><img src="${goods.goodsCoverImageUri!''}" alt="产品图片"></div>
                                                <div class="product-info">
                                                    <div class="div1">${goods.goodsTitle!''}</div>
                                                    <div class="div2">￥<span><#if goods.price??>${goods.price?string("0.00")}<#else>0.00</#if></span>&nbsp;&nbsp;<label>数量：<span>${goods.quantity!'0'}</span></label></div>
                                                </div>
                                            </li>
                                        </#list>
                                    </#if>
                                    <div style="width:80%;margin-left:3%;line-height:30px">
                                        <div>订单总额：<font style="color:red;">￥<#if item.totalPrice??>${item.totalPrice?string("0.00")}</#if></font></div>
                                        <div>下单时间：<#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
                                    </div>
                                    <div class="li3">
                                        <#if item.statusId??>
                                            <#switch item.statusId>
                                                <#case 3>
                                                	<a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');">取消订单</a>
                                                    <script type="text/javascript">
                                                		var cancel = function(id) {
                                                			win_no();
                                                			wait();
                                                			$.ajax({
                                                				url: "/fit/order/cancel",
                                                				method: "POST",
                                                				data: {
                                                					id: id
                                                				},
                                                				success: function(res) {
                                                					close(1);
                                                					if ("SUCCESS" === res.actionCode) {
                                            							$("#container" + id).remove();
                                                					} else {
                                                						warning(res.content);
                                                					}
                                                				}
                                                			})
                                                		}
                                                    </script>
                                                    <a href="/fit/detail/${item.id?c}">订单详情</a>
                                                <#break>
                                                <#case 4>
                                                    <a href="/fit/detail/${item.id?c}">订单详情</a>
                                                <#break>
                                                <#case 5>
                                                    <a href="/fit/detail/${item.id?c}">订单详情</a>
                                                    <#if !(item.isRefund??&&item.isRefund==true)>
                                                    	<a href="javascript:checkRefund(${item.id?c})">申请退货</a>
                                                    </#if>
                                                    <script type="text/javascript">
                                                    	var checkRefund = function(id) {
                                                    		wait();
                                                    		$.ajax({
                                                    			url: "/fit/my/refund/check",
                                                    			method: "POST",
                                                    			data: {
                                                    				id: id
                                                    			},
                                                    			success: function(res) {
                                                    				if ("SUCCESS" == res.actionCode) {
                                                    					window.location.href = "/fit/refund/" + id
                                                    				} else {
                                                    					close(1);
                                                    					warning(res.content);
                                                    				}
                                                    			}
                                                    		})
                                                    	}
                                                    </script>
                                                <#break>
                                            </#switch>
                                        </#if>
                                    </div>
                                </ol>
                            </#list>
                        </div>
                    </#if>
                    
                </article>
                <!-- 用户订单 END -->                           
            </section>

            
            <div class="index_test_box02"></div>
            <#include "/fitment/common_footer.ftl">
        </div>  
        <script type="text/javascript"> 
        /*  //第一步：下拉过程 
         function slideDownStep1(dist){  // dist 下滑的距离，用以拉长背景模拟拉伸效果 
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown2.style.display = "none"; 
             slideDown1.style.display = "block"; 
             slideDown1.style.height = (parseInt("20px") - dist) + "px"; 
         } 
         //第二步：下拉，然后松开， 
         function slideDownStep2(){  
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown1.style.display = "none"; 
             slideDown1.style.height = "20px"; 
             slideDown2.style.display = "block"; 
             //刷新数据 
             //location.reload(); 
         } 
         //第三步：刷新完成，回归之前状态 
         function slideDownStep3(){  
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown1.style.display = "none"; 
             slideDown2.style.display = "none"; 
         }  */
        /*  //第一步：上划过程 
         function slideUpStep1(dist){  // dist 下滑的距离，用以拉长背景模拟拉伸效果 
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown2.style.display = "none"; 
             slideDown1.style.display = "block"; 
             //slideDown1.style.height = (parseInt("50px") - dist) + "px"; 
             slideDown1.style.height = 20 + "px";
         } 
         //第二步：上划，然后松开， 
         function slideUpStep2(){  
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown1.style.display = "n; 
             slideDown2.style.height = "20px"; 
             slideDown2.style.display = "block"; 
             //刷新数据 
             //location.reload(); 
         } 
         //第三步：刷新完成，回归之前状态 
         function slideUpStep3(){  
             var slideDown1 = document.getElementById("slideDown1"), 
                 slideDown2 = document.getElementById("slideDown2"); 
             slideDown1.style.display = "none"; 
             slideDown2.style.display = "none"; 
             console.log('ajax');
         } 
          
         //下滑刷新调用 
         k_touch("all_orders","y"); 
         //contentId表示对其进行事件绑定，way==>x表示水平方向的操作，y表示竖直方向的操作 
         function k_touch(contentId,way){  
             var _start = 0, 
                 _end = 0, 
                 _content = document.getElementById(contentId); 
             _content.addEventListener("touchstart",touchStart,false); 
             _content.addEventListener("touchmove",touchMove,false); 
             _content.addEventListener("touchend",touchEnd,false); 
             function touchStart(event){  
                 //var touch = event.touches[0]; //这种获取也可以，但已不推荐使用 
         
                 var touch = event.targetTouches[0]; 
                 if(way == "x"){  
                     _start = touch.pageX; 
                 }else{  
                     _start = touch.pageY; 
                 } 
             } 
             function touchMove(event){  
                 var touch = event.targetTouches[0]; 
                 if(way == "x"){  
                     _end = (_start - touch.pageX); 
                 }else{  
                     _end = (_start - touch.pageY); 
                     //下滑才执行操作 
                     if(_end < 0){ 
                        // slideDownStep1(_end); 
                     } else{
                        slideUpStep1(_end); 
                     }
                 } 
         
             } 
             function touchEnd(event){  
                 if(_end >0){  
                     //console.log("左滑或上滑  "+(parseInt(_start)+parseInt(_end)));
                     var totaliscoll=parseInt(_start)+parseInt(_end);
                    slideUpStep2();
                     //刷新成功则 
                     //模拟刷新成功进入第三步 
                     if(parseInt($("#"+contentId).height())<parseInt(totaliscoll)){
                         slideUpStep3(); 
                     }
                      /* setTimeout(function(){  
                         slideUpStep3(); 
                     },2500);   */
               //  }else{  
                     //console.log("右滑或下滑"+_end); 
                     /* slideDownStep2(); 
                     //刷新成功则 
                     //模拟刷新成功进入第三步 
                     setTimeout(function(){  
                         slideDownStep3(); 
                     },2500);  */
                // } 
            // } 
        // }  */
 </script>    
    </body>
</html>
