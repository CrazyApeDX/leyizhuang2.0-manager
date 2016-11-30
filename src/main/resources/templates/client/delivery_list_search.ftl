    <#if order_list??>
    	<#list order_list as item>
    	   <#if item.mainOrderNumber?? && item.mainOrderNumber != username>   
    		<section>
		      <a href="/delivery/detail/${item.id?c}">
		      	<#if item.statusId==3 || item.statusId==4>
		        	<div class="time">【预计 ${item.deliveryDate!''} <span>${item.deliveryDetailId!'0'}:30</span> 送达】</div>
	        	<#elseif item.statusId==5 || item.statusId==6>
	        		<div class="time">【<#if item.deliveryTime??>${item.deliveryTime?string("yyyy-MM-dd")}</#if> <span><#if item.deliveryTime??>${item.deliveryTime?string("HH:mm")}</#if></span> 送达】</div>
		        </#if>
		        <div class="address">主单号：${item.mainOrderNumber!''}</div>
		        <div class="address">收货人：${item.shippingName!''}</div>
		        <div class="address">电话：${item.shippingPhone!''}</div>
		        <div class="address">收货地址：${item.shippingAddress!''}</div>
		      </a>
		    </section>
		     <#if item.mainOrderNumber??><#assign username=item.mainOrderNumber /></#if>
		 </#if>  
    	</#list>
    </#if>
