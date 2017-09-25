<#if user_list??>
    <#list user_list as item>
        <div class="swiper-slide" onclick="service.selectSeller(${item.id?c});">
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
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        paginationClickable: true,
        direction: 'vertical',
        slidesPerView: 6
    });
</script>
