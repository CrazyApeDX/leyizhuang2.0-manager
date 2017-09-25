<#if clientGoodsList??>
<#list clientGoodsList as clientGoods>
    <#if clientGoods??>
        <dl>
            <dt>
                <a>
                    <#-- 商品的图片，点击可跳转到详情页 -->
                    <img onclick="window.location.href='/goods/detail/${clientGoods.goodsId?c}'" src="${clientGoods.goodsCoverImageUri!''}" />
                </a>
            </dt>
            <dd>
                <#-- 用户存储指定商品的库存 -->
                <input type="hidden" id="inventory${clientGoods.goodsId?c}" value="${clientGoods.inventory!'0'}">
                <#-- 商品的标题，点击可跳转到详情页 -->
                <p onclick="window.location.href='/goods/detail/${clientGoods.goodsId?c}'">${clientGoods.goodsTitle!''}</p>
                <label>${clientGoods.goodsSku!''}</label>
                <div class="fen_div01">
                	<#if clientGoods.isPromotion??&&clientGoods.isPromotion>
                    	<span>促销</span>	
                    </#if>
                    <a href="javascript:changeQuantity(${clientGoods.goodsId?c},'add');">+</a>
                    <input min="0" class="goodsSelectedQuantity" type="number" id="quantity${clientGoods.goodsId?c}" value="0" onkeyup="keyup(this)" onafterpaste="afterpaste(this)" onchange="changeQuantity(${clientGoods.goodsId?c})" onfocus="clearQuantity(this)" onblur="setQuantity(this)">
                    <a href="javascript:changeQuantity(${clientGoods.goodsId?c},'delete');">-</a>
                </div>
                <div class="fen_div02" value="${clientGoods_index} ">
                    <#-- 显示指定商品在该地区的价格 -->
                    <a>￥${(clientGoods.salePrice!'0')?string("0.00")}</a>
                    <#-- 判断是否属于调色产品 -->
                    <#if clientGoods.isColorful??&&clientGoods.isColorful>
                        <span onclick="changeColor(${clientGoods.goodsId?c});">调色</span>
                    </#if>
                </div>
            </dd>
        </dl>   
        <div class="index_test_box"></div>
    </#if>
    
</#list>
</#if>
