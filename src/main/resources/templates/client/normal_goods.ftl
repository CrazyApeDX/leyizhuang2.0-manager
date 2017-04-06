<#if clientGoodsList??>
    <#-- 遍历二级分类下的所有商品 -->
    <#list clientGoodsList as clientGoods>
        <#if clientGoods??>
            <dl>
                <dt>
                    <#-- 用户存储指定商品的库存 -->
                    <input type="hidden" id="inventory${clientGoods.goodsId?c}" value="${clientGoods.inventory!'0'}">
					<#-- 商品的标题，点击可跳转到详情页 -->
                    <h3 onclick="window.location.href='/goods/detail/${clientGoods.goodsId?c}'">${clientGoods.goodsTitle!''}</h3>
                    <label>${clientGoods.goodsSku!''}</label>
                    <#-- 判断该商品是不是属于调色商品 -->
                    <#if clientGoods.isColorful??&&clientGoods.isColorful>
                        <a id="color${clientGoods.goodsId?c}" href="javascript:changeColor(${clientGoods.goodsId?c});">调色</a>
                    </#if>
                </dt>
                <dd>
                    <p>￥${(clientGoods.salePrice!'0')?string("0.00")}</p>
                
                    <#if clientGoods.isPromotion??&&clientGoods.isPromotion>
                        <a style="margin-right:3%;">促销</a>
                    </#if>
                    <div>
                        <span onclick="changeQuantity(${clientGoods.goodsId?c},'delete')">-</span>
                        <input class="goodsSelectedQuantity" min="0" type="number" id="quantity${clientGoods.goodsId?c}" value="0" onchange="quantityChange(${clientGoods.goodsId?c})" onkeyup="keyup(this)" onafterpaste="afterpaste(this)" onfocus="clearQuantity(this)" onblur="setQuantity(this)">
                        <span onclick="changeQuantity(${clientGoods.goodsId?c},'add')">+</span>
                    </div>
                </dd>
            </dl>
        </#if>
    </#list>
</#if>