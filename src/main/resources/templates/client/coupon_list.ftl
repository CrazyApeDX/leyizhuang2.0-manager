<!DOCTYPE html>
<html>
	<head>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta name="copyright" content="" />
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta charset="utf-8">
		<title>领券</title>
		
    	<link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
    	<link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
    	<link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
    	
    	<script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
    	
    </head>
    <style type="text/css">
    	.add_1_17{
    		position: absolute;
    		right: 4%;
    		top: 24%;
    		z-index: 999;
    	}
    </style>
    <script type="text/javascript">
    	html_hi();
    	$(function(){
    	  changest('.tab-view .title-1 li a','.tab-content','red');
    	})
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
    </script>
    <body style="height: 100%; background: #f3f4f6;">
    <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
    	<div>
    		<div class="sec_header">
    			<a class="back" href="javascript:history.go(-1);"></a>
    			<p>领券中心</p>
    		</div>
    		<article class="product-volume">
    <div class="tab-view">
      <ul class="title-1">
        <li style="width: 49%;"><a href="javascript:;">现金券</a></li>
        <li style="width: 49%;"><a href="javascript:;">产品券</a></li>
      </ul>
    <!--  <div style="width: 100%;height: 40px;line-height: 40px;text-align: center;background: white;margin-top: 10px;">剩余优惠券：<font style="color: red;">3213</font>张</div>-->
      <ul class="tab-content" style="clear: both;">
        <!-- 未使用 -->
            <li class="li1">
                <#if couponList??>
                 <#list couponList as coup>
                  <section><a href="javascript:win_yes('是否确定领取？','grant(${coup.id?c});');">
                    <!-- 图片原始尺寸 992*386 -->
                  	<img src="/client/images/bg2_cash_volume.png" alt="产品劵">
                  	<div class="div1">有效期：<span><#if coup.addTime??>${coup.addTime?string('yyyy-MM-dd')}</#if></span> - 
                                             <span><#if coup.expireTime??>${coup.expireTime?string('yyyy-MM-dd')}</#if></span></div>
                  	<div class="rich">立即领取</div>
                    <div class="div2">￥<span><#if coup.price??>${coup.price?string('0.00')}</#if></span></div>
                    <div class="add_1_17">剩余${coup.leftNumber!'0'}张</div>
                    </a>
                  </section>
                  </#list>
              </#if>
            </li>
        <!-- 未使用 END -->
      </ul>
      <#-- 引入公共confirm窗口 -->
        <#include "/client/common_confirm.ftl">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
<script type="text/javascript">
function grant(id)
{
  $.ajax({
      url : "/coupon/grant",
      type : "post",
      data : {"id":id},
      success:function(data){
    	  warning(data.msg);
    	  var timer = setTimeout(function(){
    		  if(data.code==1)
              {
                  location.replace("/coupon/list");
              }
          },1000);
         
      }
  });
}

</script>      
      <ul class="tab-content" style="clear: both;">
        
        <!-- 未使用 -->
        <li class="li1">
          <#if coupon_list??>
          <#list coupon_list as cp>
          <section>
                <a href="javascript:win_yes('是否确定领取？','grant(${cp.id?c});');">
                    <!-- 图片原始尺寸 992*386 -->
                  	<img src="/client/images/bg2_product_volume.png" alt="${cp.typeTitle!''}">
                  	<div class="div1">有效期：<span>${cp.addTime?string('yyyy-MM-dd')}</span> - 
                  	                         <span><#if cp.expireTime??>${cp.expireTime?string('yyyy-MM-dd')}</#if></span></div>
                  	<div class="rich">立即领取</div>
                    <div class="rich02"><img src="${cp.picUri!''}"/></div>
                    <div class="div3">${cp.goodsName!''}</div>
                    <div class="add_1_17">剩余${cp.leftNumber!'0'}张</div>
                </a>
          </section>
          </#list>
          </#if>
        </li>
        <!-- 未使用 END -->
      </ul>
    </div>
  </article>
		</div>		
	</body>
</html>
