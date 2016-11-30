<!-- 订单列表 -->
<#if all_return_list??>
    <div id="all_orders" class="some_orders" style="background: #f3f4f6">
        <#list all_return_list as item>
            <ol class="order-list" <#if item_index==0>style="margin-top:0%;"</#if>>
                <li class="li1">
                    <label>退货单号：<span>${item.returnNumber!''}</span></label>
                    <div class="species">
                        ${item.statusName }
                    </div>
                </li>
                <li class="li1">
                    <label>原订单号：<span>${item.orderNumber!''}</span></label>
                </li>
                <#if item.returnGoodsList??>
                    <#list item.returnGoodsList as goods>
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
                    <div>退货单总额：<font style="color:red;">￥<#if item.turnPrice??>${item.turnPrice?string("0.00")}</#if></font></div>
                    <div>退货单时间：<#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
                </div>
                <div class="li3">
                     <a href="/user/return/detail/${item.id?c}">退货单详情</a>
                </div>
            </ol>
        </#list>
    </div>
</#if>