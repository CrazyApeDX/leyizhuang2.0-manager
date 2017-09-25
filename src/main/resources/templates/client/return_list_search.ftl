    <#if return_list??>
    	<#list return_list as item>
    		<section>
		      <a href="/delivery/return/detail/${item.id?c}">
		      	
		        	<div class="time">【退货时间 ${item.orderTime!''}】</div>
	        	
		        <div class="address">退货单号：${item.returnNumber!''}</div>
		        <div class="address">定单号：${item.orderNumber!''}</div>
		        <div class="address">门店名称：${item.diySiteTitle!''}</div>
		        <div class="address">门店地址：${item.diySiteAddress!''}</div>
		        <div class="address">门店电话：${item.diySiteTel!''}</div>
		      </a>
		    </section>
    	</#list>
    </#if>
