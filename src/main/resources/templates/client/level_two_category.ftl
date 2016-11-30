<#if level_one_categories??>
    <div class="fen_testtop">
        <#list level_one_categories as level_one>
            <#if ("level_two_categories"+level_one_index)?eval??>
                <ul id="level_two${level_one.id?c}">
                    <#list ("level_two_categories"+level_one_index)?eval as level_two>
                        <li><a id="${level_one.id?c}_${level_two.id?c}" <#if level_one_index==0&&level_two_index==0>style="background:#ffaa00;color:white;padding:0px;"<#else>style="padding:0px;"</#if> href="javascript:clickLevelTwo('${level_one.id?c}_${level_two.id?c}',${level_two.categoryId?c});">${level_two.title!''}</a></li>
                    </#list>
                </ul>
            </#if>
        </#list>
    </div>
</#if>