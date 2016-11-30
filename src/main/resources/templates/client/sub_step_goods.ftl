<#if some_goods??>
<#list some_goods as goods>
    <#if goods??>
        <dl>
            <dt>
                <a>
                    <#if goods??&&goods.coverImageUri??>
                        <#-- 商品的图片，点击可跳转到详情页 -->
                        <img onclick="window.location.href='/goods/detail/${goods.id?c}'" src="${goods.coverImageUri!''}" />
                    </#if>
                </a>
            </dt>
            <dd>
                <#-- 用户存储指定商品的库存 -->
                <#--<input type="hidden" id="inventory${goods.id?c}" value="<#if goods.leftNumber??>${goods.leftNumber?c}<#else>0</#if>">-->
                <input type="hidden" id="inventory${goods.id?c}" value="<#if ("goodInventory"+goods_index)?eval??>${("goodInventory"+goods_index)?eval?c}<#else>0</#if>">
                <#-- 商品的标题，点击可跳转到详情页 -->
                <p onclick="window.location.href='/goods/detail/${goods.id?c}'">${goods.title!''}</p>
                <label>${goods.code!''}</label>
                <div class="fen_div01">
                    <#-- 判断指定商品在该地区是否参与促销 -->
                    <#if ("priceListItem"+goods_index)?eval??>
                        <#if ("priceListItem"+goods_index)?eval.isPromotion??>
                            <#if ("priceListItem"+goods_index)?eval.isPromotion>
                                <span>促销</span>
                            </#if>
                        </#if>
                    </#if>
                    <a href="javascript:changeQuantity(${goods.id?c},'add');">+</a>
                    <input min="0" class="goodsSelectedQuantity" type="number" id="quantity${goods.id?c}" value="0" onkeyup="keyup(this)" onafterpaste="afterpaste(this)" onchange="changeQuantity(${goods.id?c})">
                    <a href="javascript:changeQuantity(${goods.id?c},'delete');">-</a>
                </div>
                <div class="fen_div02" value="${goods_index} ">
                    <#-- 显示指定商品在该地区的价格 -->
                    <#if ("priceListItem"+goods_index)?eval??>
                        <#if ("priceListItem"+goods_index)?eval.salePrice??>
                            <a>￥${("priceListItem"+goods_index)?eval.salePrice?string("0.00")}</a>
                        </#if>
                    </#if>
                    <#-- 判断是否属于调色产品 -->
                    <#if goods.isColorful??&&goods.isColorful>
                        <span onclick="changeColor(${goods.id?c});">调色</span>
                    </#if>
                </div>
            </dd>
        </dl>   
        <div class="index_test_box"></div>
    </#if>
    
</#list>
</#if>
