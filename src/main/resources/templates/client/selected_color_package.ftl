<div class="colo_sec">
    <#-- 以下几个input标签全部用来存储一些变量的值 -->
    <input type="hidden" id="unit_price" value="${unit_price!'0'}">
    <input type="hidden" id="goods_id" value="<#if goodsId??>${goodsId?c}</#if>">
    <input type="hidden" id="color_package_inventory" value="<#if inventory??>${inventory?c}<#else>0</#if>">
    <input type="hidden" id="selected_number" value="<#if selected_number??>${selected_number?c}<#else>0</#if>">
    <div class="my_close">
        <dl>
            <dt>
                <span>颜色</span>
                <span>数量</span>
                <span>价格</span>
                <span>编辑</span>
            </dt>
            <#if select_colors??>
                <#list select_colors as item>
                    <dd id="colorPackage${item.goodsId?c}">
                        <span>${item.sku!''}</span>
                        <span>${item.quantity!''}</span>
                        <span>
                            <#if item.totalPrice??>
                                ${item.totalPrice?string("0.00")}
                            </#if>
                        </span>
                        <span>
                            <a href="javascript:deleteSelectedColorPackage(${item.goodsId?c})">删除</a>
                        </span>
                    </dd>
                </#list>
            </#if>
        </dl>
        <a class="colo_clo">关闭</a>
    </div>
</div>