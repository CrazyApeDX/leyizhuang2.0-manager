<#if !goods??>
<script type="text/javascript" src="/mag/js/layout.js"></script>
</#if>
<script type="text/javascript">
$(function () {
    // 根据选择的产品载入筛选项
    $(".productIdRadio").click(function(){
        $.ajax({
            url : '/Verwalter/product/parameter/'+$(this).val() <#if goods??>+"?goodsId=${goods.id?c}"</#if>,
            type : 'GET',
            success : function(res) {
                $("#productSelectDiv").html(res);
            }
        });
    });
});
</script>
<#if product_list?? && product_list?size gt 0>
<dl>
    <dt>所属产品</dt>
    <dd>
        <div class="rule-multi-radio">
            <span>
                <#list product_list as product>
                    <input type="radio" class="productIdRadio" name="productId" value="${product.id!""}" datatype="*0-255" <#if goods?? && goods.productId?? && goods.productId==product.id>checked="checked"</#if>>
                    <label>${product.title!""}</label>
                </#list>
            </span>
        </div>
    </dd>
</dl>
<div id="productSelectDiv">
<#include "/site_mag/product_param_list.ftl" >
</div>
</#if>
<#if brand_list?? && brand_list?size gt 0>
<dl>
    <dt>品牌</dt>
    <dd>
        <div class="rule-multi-radio">
            <span id="field_control_brand">
                <#list brand_list as brand>
                    <input type="radio" name="brandId" value="${brand.id!""}" datatype="*" <#if goods?? && goods.brandId?? && goods.brandId==brand.id>checked="checked"</#if>>
                    <label>${brand.title!""}</label>
                </#list>
            </span>
        </div>
    </dd>
</dl>
</#if>
<#if param_list??>
    <#list param_list as param>
        <#if param.inputType?? && param.inputType==1>
            <#if param.isMultiple?? && param.isMultiple==true>
                <dl>
                    <dt>${param.title!""}</dt>
                    <dd>
                        <div class="rule-multi-checkbox">
                            <span>
                                <#if param.valueList??>
                                    <input type="hidden" name="paramList[${param_index}].paramId" value="${param.id}" />
                                    <input type="hidden" name="paramList[${param_index}].paramName" value="${param.title}" />
                                    <input type="hidden" name="paramList[${param_index}].paramCategory" value="${param.categoryTitle!''}" />
                                    <#list param.valueList?split(",") as item>
                                        <#if item!="">
                                        <input type="checkbox" name="paramList[${param_index}].value" value="${item?trim}" datatype="*0-255" <#if goods?? && goods.paramList?? && goods.paramList[param_index]?? && goods.paramList[param_index].value?? && goods.paramList[param_index].value?contains(item?trim)>checked="checked"</#if>> 
                                        <label>${item?trim}</label>
                                        </#if>
                                    </#list>
                                </#if>
                            </span>
                        </div>
                    </dd>
                </dl>
            <#else>
                <dl>
                    <dt>${param.title!""}</dt>
                    <dd>
                        <div class="rule-multi-radio">
                            <span>
                                <#if param.valueList??>
                                    <input type="hidden" name="paramList[${param_index}].paramId" value="${param.id}" />
                                    <input type="hidden" name="paramList[${param_index}].paramName" value="${param.title}" />
                                    <input type="hidden" name="paramList[${param_index}].paramCategory" value="${param.categoryTitle!''}" />
                                    <#list param.valueList?split(",") as item>
                                        <#if item!="">
                                        <input type="radio" name="paramList[${param_index}].value" datatype="*0-255" value="${item?trim}" <#if goods?? && goods.paramList?? && goods.paramList[param_index]?? && goods.paramList[param_index].value?? && goods.paramList[param_index].value?trim==item?trim>checked="checked"</#if>> 
                                        <label>${item?trim}</label>
                                        </#if>
                                    </#list>
                                </#if>
                            </span>
                        </div>
                    </dd>
                </dl>
            </#if>
        <#else>
            <dl>
                <dt>${param.title!""}</dt>
                <dd>
                    <input type="hidden" name="paramList[${param_index}].paramId" value="${param.id}" />
                    <input type="hidden" name="paramList[${param_index}].paramName" value="${param.title}" />
                    <input type="hidden" name="paramList[${param_index}].paramCategory" value="${param.categoryTitle!''}" />
                    <input name="paramList[${param_index}].value" type="text" value="<#if goods?? && goods.paramList?? && goods.paramList[param_index]?? && goods.paramList[param_index].value??>${goods.paramList[param_index].value?trim}</#if>" id="field_control_camera" datatype="*0-255" class="input txt100" >
                </dd>
            </dl>
        </#if>
    </#list>
</#if>
