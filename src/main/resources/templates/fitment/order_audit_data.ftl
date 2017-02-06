<#if orderPage??&&orderPage.content?size gt 0>
    <#list orderPage.content as item>
        <ol class="order-list" style="border-bottom:2px solid #cc1421;margin-top:0px;" id="order${item.id?c}">
            <li class="li1">
                <label>订单号：<span>${item.orderNumber!''}</span></label>
                <div class="species" id="status${item.id?c}">
                    <#if item.status??>
                        <#switch item.status>
                            <#case "WAIT_AUDIT">待审核<#break>
                            <#case "AUDIT_SUCCESS">审核通过<#break>
                            <#case "AUDIT_FAILURE">审核未通过<#break>
                        </#switch>
                    </#if>
                </div>
            </li>
            <#if item.orderGoodsList??>
                <#list item.orderGoodsList as goods>
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
            	<li class="li1">
                    <label>下单人：<span>${item.employeeName!''}</span></label>
                    <label>审核人：<span>${item.auditorName!''}</span></label>
                </li>
                <li class="li1">
                	<label>
                        <div>订单总额：
                        	<font style="color:red;">
                        		<#if item.allTotalGoodsPrice??>
                        			${item.allTotalGoodsPrice?string("0.00")}
                        		<#else>
                        			0.00
                        		</#if>
                        	</font>
                    	</div>
                	</label>
                    <#if employee.isMain>
                        <label>
                        	<div>订单应付：
                            	<font style="color:red;">
                            		<#if item.allTotalPrice??>
                            			${item.allTotalPrice?string("0.00")}
                            		<#else>
                            			0.00
                            		</#if>
                            	</font>
                        	</div>
                    	</label>
                	</#if>
                </li>
            	<div>下单时间：<#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
                <div>审核时间：<#if item.auditTime??>${item.auditTime?string("yyyy-MM-dd HH:mm:ss")}<#else>还未审核</#if></div>
            </div>
            <#if employee.isMain>
                <div class="li3"  id="action${item.id?c}">
                    <#if item.status??>
                        <#switch item.status>
                            <#case "WAIT_AUDIT">
                            	<a href="javascript:agree(${item.id?c});">通过</a>
                            	<a href="javascript:reject(${item.id?c});">不通过</a>
                            <#break>
                            <#case "AUDIT_SUCCESS">
                            	<a href="/fit/pay/${item.id?c}" style="border: #cc1421 1px solid; color: #cc1421;">去支付</a>
                            <#break>
                            <#case "AUDIT_FAILURE">
                            	<a href="javascript:remove(${item.id?c});">删除</a>
                            <#break>
                        </#switch>
                    </#if>
                </#if>
            </div>
        </ol>
    </#list>
</#if>
                