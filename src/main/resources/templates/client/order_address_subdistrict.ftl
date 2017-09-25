<select id="subdistrictId" >
    <#if subdistrict_list??>
        <#list subdistrict_list as item>
            <option value="${item.id?c}">${item.name!''}</option>
        </#list>
    </#if>
</select>