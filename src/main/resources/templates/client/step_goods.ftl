<div class="fen_goodbox">                   
    <#if level_one_categories??>
        <#list level_one_categories as level_one>
            <#if ("level_two_categories"+level_one_index)?eval??>
                <#list ("level_two_categories"+level_one_index)?eval as level_two>
                    <div class="ctrlGoods" id="goods${level_one.id?c}_${level_two.id?c}" <#if !(level_one_index==0&&level_two_index==0)>style="display:none;"</#if>>
                    </div>
                </#list>
            </#if>
        </#list>
    </#if>
</div>