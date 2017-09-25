<#if some_goods??>
    <#-- 遍历二级分类下的所有商品 -->
    <#list some_goods as goods>
        <#if goods??>
            <dl>
                <dt>
                    <#-- 用户存储指定商品的库存 -->
                    <input type="hidden" id="inventory${goods.id?c}" value="<#if goods??&&goods.inventory??>${goods.inventory?c}<#else>0</#if>">
					<#-- 商品的标题，点击可跳转到详情页 -->
                    <h3>${goods.title!''}</h3>
                    <label>${goods.sku!''}</label>
                </dt>
                <dd>
                    <p>￥${goods.price?string("0.00")}</p>
                    <div>
                        <span onclick="changeQuantity(${goods.id?c},'delete')">-</span>
                        <input class="goodsSelectedQuantity" min="0" type="number" id="quantity${goods.id?c}" value="0" onchange="quantityChange(${goods.id?c})" onkeyup="keyup(this)" onafterpaste="afterpaste(this)">
                        <span onclick="changeQuantity(${goods.id?c},'add')">+</span>
                    </div>
                </dd>
            </dl>
        </#if>
    </#list>
</#if>