<#if diySite_list?? && diySite_list?size gt 0>
        <dl>
            <dt>门店</dt>
            <dd>
                <div class="rule-single-select" id="diySiteIdd">
                    <select name="diySiteId" id="diySiteId" datatype="*" sucmsg=" ">
                        <#if !city??>
                        <option value="">请选择门店...</option>
                        </#if>
                        <#if diySite_list??> 
                            <#list diySite_list as c>
                                <option value="${c.id?c}">${c.title!''}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </dd>
        </dl>
</#if>