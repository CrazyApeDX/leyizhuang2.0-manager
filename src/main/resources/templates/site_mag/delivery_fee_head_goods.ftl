<div class="tab-content" >
	<dl>
		<dt>商品信息</dt>
		<dd>
			<table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
				<thead>
					<tr>
						<th width="5%">选择</th>
						<th width="8%">商品编号</th>
						<th>商品标题</th>
						<th width="10%">编号</th>
						<th width="12%">类别</th>
					</tr>
				</thead>
				<tbody>              
					<#if goodsList??>
						<#list goodsList as g>
							<tr class="td_c">
								<td><input type="radio" value="${g.id?c}" name="goodsId"></td>
								<td>${g.id?c}</td>
								<td>${g.title!''}</td>
								<td>${g.code!''}</td>
								<td>${g.categoryTitle!''}</td>
							</tr>
						</#list>
					</#if>
				</tbody> 
			</table> 
		</dd> 
	</dl>
</div>
