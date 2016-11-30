<div class="swiper-container">
  <div class="swiper-wrapper">
<#if user_list??>
    <#list user_list as item>
        <div class="swiper-slide" onclick="seller.selectInfo(${item.id?c});">
            <div class="box">
                <p>
                	<#if item??&&item.realName??&&item.realName!="">${item.realName!''}<#else>暂无姓名</#if>
                </p>
            </div>
        </div>
    </#list>
    <div class="swiper-slide">
            <div class="box">
                <p>
                </p>
            </div>
        </div>
</#if>
  </div>
</div>
<script src="/client/js/swiper.min.js"></script>
<script type="text/javascript">
  var mySwiper = new Swiper('.swiper-container',{
	  direction: 'vertical',
	  loop: false,
	  freeMode : true,
	  slidesPerView: 6
  });  
</script>
<#-- 
<#if user_list??>
    <#list user_list as item>
        <div class="swiper-slide" onclick="seller.selectInfo(${item.id?c});">
            <div class="box">
                <p>
                	<#if item??&&item.realName??&&item.realName!="">${item.realName!''}<#else>暂无姓名</#if>
                </p>
            </div>
        </div>
    </#list>
</#if>
<script src="/client/js/swiper.min.js"></script>
<script>
    var swiper = new Swiper('#user_swiper', {
        pagination: '#user_swiper .swiper-pagination',
        paginationClickable: true,
        direction: 'vertical',
        slidesPerView: 6
    });
</script> 
-->

