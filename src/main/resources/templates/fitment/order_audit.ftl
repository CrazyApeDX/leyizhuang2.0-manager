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
        <title>订单审核</title>
        
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/xiuxiu.css"/>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/index.js" type="text/javascript"></script>
        <script src="/fitment/js/order_audit.js" type="text/javascript"></script>
        <script src="/client/js/Validform_v5.3.2_min.js" type="text/javascript"></script>

    </head>
    <script type="text/javascript">
    </script>
    <body style="background: #f3f4f6;">
        <#-- 引入公共confirm窗口 -->
        <#include "/client/common_confirm.ftl">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <#-- 引入公共购物方式选择滑动窗口 -->
        <#include "/client/common_shopping_type.ftl">
        <div id="content">
            <div class="sec_header">
                <a class="back" href="/fit/home"></a>
                <p>订单审核</p>             
            </div>
            
            <section id="order_container" class="my_order">
                <input id="typeId" type="hidden" value="${typeId!'0'}">
                <#--
                <div class="searchbox bgc-f3f4f6 bdt">
                    <input type="text" id="keywords" placeholder="">
                    <a href="javascript:searchOrder();"></a>
                </div>
                -->          
                
                <!-- 订单管理 -->
                <ul class="order-nav">
                    <li id="all"><a>全部</a></li>
                </ul>
                
                <!-- 订单分类 -->
                <article class="orders-species"> 
                    <#if orderPage??>
                        <div id="all_orders" class="some_orders">
                            <#list orderPage.content as item>
                                <ol class="order-list" style="border-bottom:2px solid #cc1421;margin-top:0px;" id="order${item.id?c}">
                                    <li class="li1">
	                                    <label>订单号：<span>${item.orderNumber!''}</span></label>
                                        <div class="species" id="status${item.id?c}">
                                            <#if item.status??>
                                                <#switch item.status>
                                                    <#case "WAIT_AUDIT">待审核<#break>
                                                    <#case "AUDIT_SUCCESS">审核通过<#break>
                                                    <#case "AUDIT_FAILURE">审核未通过<#break>
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
	                                	<li class="li1">
		                                    <label>下单人：<span>${item.employeeName!''}</span></label>
		                                    <label>审核人：<span>${item.auditorName!''}</span></label>
	                                    </li>
	                                    <li class="li1">
	                                    	<label>
			                                    <div>订单总额：
		                                        	<font style="color:red;">
		                                        		<#if item.allTotalGoodsPrice??>
		                                        			${item.allTotalGoodsPrice?string("0.00")}
		                                        		<#else>
		                                        			0.00
		                                        		</#if>
		                                        	</font>
		                                    	</div>
	                                    	</label>
		                                    <#if employee.isMain>
			                                    <label>
			                                    	<div>订单应付：
			                                        	<font style="color:red;">
			                                        		<#if item.allTotalPrice??>
			                                        			${item.allTotalPrice?string("0.00")}
			                                        		<#else>
			                                        			0.00
			                                        		</#if>
			                                        	</font>
			                                    	</div>
		                                    	</label>
	                                    	</#if>
	                                    </li>
                                    	<div>下单时间：<#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
                                        <div>审核时间：<#if item.auditTime??>${item.auditTime?string("yyyy-MM-dd HH:mm:ss")}<#else>还未审核</#if></div>
                                    </div>
                                    <#if employee.isMain>
	                                    <div class="li3" id="action${item.id?c}">
	                                        <#if item.status??>
	                                            <#switch item.status>
	                                                <#case "WAIT_AUDIT">
                                                    	<a href="javascript:agree(${item.id?c});">通过</a>
                                                    	<a href="javascript:reject(${item.id?c});">不通过</a>
	                                                <#break>
	                                                <#case "AUDIT_SUCCESS">
	                                                	<a href="/fit/pay/${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
	                                                <#break>
	                                                <#case "AUDIT_FAILURE">
	                                                	<a href="javascript:remove(${item.id?c});">删除</a>
	                                                <#break>
	                                            </#switch>
	                                        </#if>
                                        </#if>
                                    </div>
                                </ol>
                            </#list>
                        </div>
                    </#if>
                </article>
                <!-- 用户订单 END -->                           
            </section>

			<div style="height:10px;background: #f4f4f4">
			
			</div>
            
            <a style="
				display: block;
			    border-radius: 5px;
			    width: 96%;
			    height: 36px;
			    margin: 10px 2%;
			    background: #cc1421;
			    line-height: 36px;
			    color: #ffffff;
			    font-size: 1.2em;
			" href="javascript:loadMore();">加载更多</a>
			
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
