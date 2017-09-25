<!DOCTYPE html>
    <html lang="zh-CN" class="bgc-f3f4f6">
    <head>
        <meta charset="UTF-8">
        <meta name="keywords" content="">
        <meta name="copyright" content="" />
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <title>乐易装</title>
        <!-- css -->
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_my_wealth.css"/>
        <!-- js -->
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script src="/client/js/rich_lee.js" type="text/javascript"></script>
        <style type="text/css">
        	.tab-content li section div.div2{
        		left:212px;
        		top:34px;
        		font-size: small;
        	}
        </style>
        <script type="text/javascript">
            $(function(){
                win_cla();
                footer();
            });
        </script>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入公共购物方式选择滑动窗口 -->
        <#include "/client/common_shopping_type.ftl">
    
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>我的产品劵</p>
            <a class="save" href="/user/coupon/goods/guide">使用说明</a>
        </header>
        <!-- 头部 END -->
        <!-- 我的产品劵 -->
        <article class="product-volume">
            <div class="tab-view">
                <ul class="title-1">
                    <li class="active"><a>未使用(<span><#if product_unused_list??>${product_unused_list?size}<#else>0</#if></span>)</a></li>
                    <li><a>已过期(<span><#if product_out_date_list??>${product_out_date_list?size}<#else>0</#if></span>)</a></li>
                    <li><a>已使用(<span><#if product_used_list??>${product_used_list?size}<#else>0</#if></span>)</a></li>
                </ul>
                <ul class="tab-content" style="display:block;">
                    <!-- 未使用 -->
                    <#if product_unused_list??>
                        <li class="li1">
                            <#list product_unused_list as item>
                                <section>
                                    <a>
                                        <!-- 图片原始尺寸 992*386 -->
                                        <img src="/client/images/bg2_product_volume.png" alt="产品劵">
                                        <div class="div1">有效期：<span><#if item.getTime??>${item.getTime?string("yyyy-MM-dd")}</#if></span> - <span><#if item.expireTime??>${item.expireTime?string("yyyy-MM-dd")}</#if></span></div>
                                        <div class="div2">${item.sku!''}</div>
                                        <div class="div3"><#if item.customerId??>(CRM)</#if>${item.goodsName!''}</div>
                                        <img class="product-pic" src="${item.picUri!''}" alt="产品图片">
                                    </a>
                                </section>
                            </#list>
                        </li>
                    </#if>
                <!-- 未使用 END -->
                
                <!-- 已过期 -->
                <#if product_out_date_list??>
                    <li class="li2">
                        <#list product_out_date_list as item>
                            <section>
                                <a>
                                    <!-- 图片原始尺寸 992*386 -->
                                    <img src="/client/images/bg1_product_volume.png" alt="产品劵">
                                    <div class="div1">有效期：<span><#if item.getTime??>${item.getTime?string("yyyy-MM-dd")}</#if></span> - <span><#if item.expireTime??>${item.expireTime?string("yyyy-MM-dd")}</#if></span></div>
                                    <div class="div2">${item.sku!''}</div>
                                    <div class="div3">${item.goodsName!''}</div>
                                    <img class="product-pic" src="${item.picUri!''}" alt="产品图片">
                                </a>
                            </section>
                        </#list>
                    </li>
                </#if>
                <!-- 已过期 END -->
                
                <!-- 已使用 -->
                <#if product_used_list??>
                    <li class="li3">
                        <#list product_used_list as item>
                            <section>
                                <a>
                                    <!-- 图片原始尺寸 992*386 -->
                                    <img src="/client/images/bg1_product_volume.png" alt="产品劵">
                                    <div class="div1">有效期：<span><#if item.getTime??>${item.getTime?string("yyyy-MM-dd")}</#if></span> - <span><#if item.expireTime??>${item.expireTime?string("yyyy-MM-dd")}</#if></span></div>
                                    <div class="div2">${item.sku!''}</div>
                                    <div class="div3">${item.goodsName!''}</div>
                                    <img class="product-pic" src="${item.picUri!''}" alt="产品图片">
                                </a>
                            </section>
                        </#list>
                    </li>
                </#if>
                <!-- 已使用 END -->  
                </ul>
            </div>
        </article>
        <script type="text/javascript">
            $('.tab-view ul').on('click','a',function(){
                var $self = $(this);//当前a标签
                var $active = $self.closest('li');//当前点击li
                var index = $active.prevAll('li').length;//当前索引
                $active.addClass('active').siblings('li').removeClass('active');
                $('.tab-content').find('>li')[index==-1?'show':'hide']().eq(index).show();
            });
        </script>
        <!-- 我的产品劵 END -->
        
        <div class="clear h66"></div>
        
        <!-- 底部 -->
        <#include "/client/common_footer.ftl">
        <!-- 底部 END -->
    </body>
</html>