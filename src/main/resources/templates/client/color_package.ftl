<div class="colo_choice">
    <div class="colo_test"></div>
        <div class="win_colo">
            <!--描述：定位颜色选择第二层-->
            <div id="select_colors_by_dx">
                <#include "/client/selected_color_package.ftl">
            </div>
                
            <div class="colo_title">
                <div class="colo_back"><img src="/client/images/colo_back.png"></div>
                <p>调色选择</p>
                <a><img src="/client/images/colo_choic.png"/></a>
            </div>
            <ul class="colo_box">
                <#if color_package_list??>
                    <#list color_package_list as item>
                        <li onclick="getColor('${item.code!''}',${item.id?c},this)" style="width: 90%;float: none;"><img src="${item.coverImageUri!''}" style="width: 16%;"> ${item.title!''}</li>
                        <#-- 指定调色包的库存 -->
                        <#if item.leftNumber??>
                            <input type="hidden" id="colorPackageInventory${item.id?c}" value="${item.leftNumber?c}">
                        <#else>
                            <input type="hidden" id="colorPackageInventory${item.id?c}" value="0">
                        </#if>
                        <#-- 指定调色包的价格 -->
                        <#if ("colorPackagePriceListItem"+item_index)?eval??&&("colorPackagePriceListItem"+item_index)?eval.salePrice??>
                            <input type="hidden" id="colorPackagePrice${item.id?c}" value="${("colorPackagePriceListItem"+item_index)?eval.salePrice?string("0.00")}">
                        <#else>
                            <input type="hidden" id="colorPackagePrice${item.id?c}" value="0.00">
                        </#if>
                    </#list>
                </#if>
            </ul>
            <div class="colo_num">
                <p id="color_name">
                    <#if color_package_list??>
                        <#list color_package_list as item>
                            <#if item_index==0>
                                ${item.code!''}
                            </#if>
                        </#list>
                    </#if>
                </p>
                <div class="colo_add">
                    <span <#if color_package_list??&&color_package_list?size gt 0>onclick="changeColorNum('delete');"</#if>>-</span>
                    <input type="text" readonly="true" style="text-align:center;" id="select_color_quantity" value="0" />
                    <span <#if color_package_list??&&color_package_list?size gt 0>onclick="changeColorNum('add');"</#if>>+</span>
                </div>
                <#if unit_price??>
                    <p id="color_price">￥${unit_price?string("0.00")}</p>
                <#else>
                    <p id="color_price">￥0.00</p>
                </#if>
            </div>
            <div class="down_buy" <#if color_package_list??&&color_package_list?size gt 0>onclick="addColor(${goodsId?c })"</#if>>确定</div>
        </div>
    </div>