<div class="panel panel-primary table-responsive">
	<#if title??>
		<div class="panel-heading">
			<div class="panel-title">${title!''}</div>
		</div>
	</#if>
	<table class="table table-bordered table-striped table-hover">
		<#if orderInfList??>
			<thead>
				<tr>
					<td>&emsp;</td>
					<td>门店名</td>
					<td>主单号</td>
					<td>分单号</td>
					<td>错误原因</td>
				</tr>
			</thead>
			<tbody>
				<#list orderInfList as item>
					<tr>
						<td><div class="checkbox"><label><input type="checkbox" name="data" value="${item.headerId?c}"></label></div></td>
						<td>${item.diySiteName!''}</td>
						<td>${item.mainOrderNumber!''}</td>
						<td>${item.orderNumber!''}</td>
						<td>${item.errorMsg}</td>
					</tr>
				</#list>
			</tbody>
		</#if>
		<#if orderGoodsInfList??>
			<thead>
				<tr>
					<td>&emsp;</td>
					<td>名称</td>
					<td>SKU</td>
					<td>数量</td>
					<td>错误原因</td>
				</tr>
			</thead>
			<tbody>
				<#list orderGoodsInfList as item>
					<tr>
						<td><div class="checkbox"><label><input type="checkbox" name="data" value="${item.orderLineId?c}"></label></div></td>
						<td>${item.goodsTitle!''}</td>
						<td>${item.sku!''}</td>
						<td>${item.quantity!'0'}</td>
						<td>${item.errorMsg!''}</td>
					</tr>
				</#list>
			</tbody>
		</#if>
	</table>
</div>