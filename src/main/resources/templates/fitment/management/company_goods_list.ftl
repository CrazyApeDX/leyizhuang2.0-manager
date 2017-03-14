<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>装饰公司可售商品</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody">
	<form name="form1" method="post" action="/Verwalter/fitment/goods/list/${companyId?c}" id="form1">
		<div>
			<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
			<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
			<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
		</div>
		<script type="text/javascript">
			var theForm = document.forms['form1'];
		    if (!theForm) {
		        theForm = document.form1;
		    }
		    function __doPostBack(eventTarget, eventArgument) {
		        if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
		            theForm.__EVENTTARGET.value = eventTarget;
		            theForm.__EVENTARGUMENT.value = eventArgument;
		            theForm.submit();
		        }
		    }
		</script>
		<!--导航栏-->
		<div class="location" style="position: static; top: 0px;">
			<a href="javascript:history.back(-1);" class="back">
				<i></i>
				<span>返回上一页</span>
			</a>
			<a href="/Verwalter/center" class="home">
				<i></i>
				<span>首页</span>
			</a>
			<i class="arrow"></i>
			<span>对外合作</span>
			<i class="arrow"></i>
			<span>装饰公司可售商品</span>  
		</div>
		<!--/导航栏-->

		<!--工具栏-->
		<div class="toolbar-wrap">
			<div id="floatHead" class="toolbar" style="position: static; top: 42px;">
				<div class="l-list">
					<ul class="icon-list">
						<li>
							<a class="add" href="javascript:addGoods(${companyId?c});">
								<i></i>
								<span>添加</span>
							</a>
						</li>
						<script type="text/javascript">
							var addGoods = function(companyId) {
								$.dialog.prompt("请输入商品的SKU", function(text) {
									$.ajax({
										method: 'POST',
										url: '/Verwalter/fitment/goods/add',
										data: {
											sku: text,
											companyId: companyId
										},
										success: function(res) {
											if (0 === res.status) {
												window.location.href = "/Verwalter/fitment/goods/list/" + companyId;
											} else {
												$.dialog.alert(res.message);
											}
										}
									});
								});
							}
						</script>
						<li>
							<a class="all" href="javascript:initGoods(${companyId?c})">
								<i></i>
								<span>商品初始化</span>
							</a>
						</li>
						<li>
							<a class="all" href="javascript:initInventory(${companyId?c})">
								<i></i>
								<span>库存初始化</span>
							</a>
						</li>
						<script type="text/javascript">
							var initGoods = function(companyId) {
								$.dialog.confirm("是否确认初始化", function() {
									window.location.href = "/Verwalter/fitment/goods/init/" + companyId;
									return true;
								});
							}
							var initInventory = function(companyId) {
								$.dialog.confirm("是否确认初始化", function() {
									window.location.href = "/Verwalter/fitment/inventory/init/" + companyId;
									return true;
								});
							}
						</script>
						<#--
						<li>
							<a class="all" href="javascript:;" onclick="checkAll(this);">
								<i></i>
								<span>全选</span>
							</a>
						</li>
						-->
						<#--
						<li>
							<a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')">
								<i></i>
								<span>删除</span>
							</a>
						</li>
						-->
					</ul>
				</div>
			</div>
		</div>
		<!--/工具栏-->

		<!--列表-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  			<tbody>
				<tr class="odd_bg">
					<th  width="10%">选择</th>
					<th align="left" width="25%">商品ID</th>
					<th align="left" width="25%">商品名称</th>
					<th align="left" width="25%">商品SKU</th>
					<th align="center" width="15%">操作</th>
				</tr>

    			<#if goodsPage??>
        			<#list goodsPage.content as item>
			            <tr>
			                <td align="center">
			                    <span class="checkall" style="vertical-align:middle;">
			                        <input id="listChkId" type="checkbox" name="listChkId" value="${item_index}" >
			                    </span>
			                    <input type="hidden" name="listId" id="listId" value="${item.id?c}">
			                </td>
			                <td align="left">${item.goodsId?c}</td>
			                <td align="left">${item.goodsTitle!""}</td>
			                <td align="left">${item.goodsSku!""}</td>
			                <td align="center">
			                	<a href="javascript:confirmDelete(${item.id?c});">删除</a>
			                	<script type="text/javascript">
			                		var confirmDelete = function(id) {
			                			$.dialog.prompt("请输入DELETE以确定删除", function(text) {
				                			if ("DELETE" === text) {
				                				window.location.href = "/Verwalter/fitment/goods/delete/" + id + "/${companyId?c}";
				                			}
			                			});
			                		}
			                	</script>
			                </td>
			            </tr>
        			</#list>
    			</#if>
			</tbody>
		</table>
		<!--/列表-->

		<!--内容底部-->
		<#if goodsPage??>
			<#assign PAGE_DATA=goodsPage />
			<#include "/fitment/management/list_footer.ftl" />
		</#if>
		<!--/内容底部-->
	</form>
</body>
</html>