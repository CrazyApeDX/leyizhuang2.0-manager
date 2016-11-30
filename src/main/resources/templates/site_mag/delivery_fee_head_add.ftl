<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>城市运费管理</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody">
<form name="moduleForm" method="post" action="/Verwalter/delivery/fee/head/save" id="moduleForm">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
</div>

<!--导航栏-->
<div class="location">
	<a href="/Verwalter/delivery/fee/head/${(sobId!0)?c}" class="back">
		<i></i>
		<span>返回列表页</span>
	</a>
	<a href="/Verwalter/center" class="home">
		<i></i>
		<span>首页</span>
	</a>
	<i class="arrow"></i>
	<span>城市列表</span>
	<i class="arrow"></i>
	<span>运费管理</span>
	<i class="arrow"></i>
	<span>新增</span>
	</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
	<div id="floatHead" class="content-tab">
		<div class="content-tab-ul-wrap">
			<ul>
				<li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑信息</a></li>
			</ul>
		</div>
	</div>
</div>

<div class="tab-content">
	<dl>
		<dt>名称：</dt>
		<dd>
			<span>${cityName!''}</span> 
			<input type="hidden" id="sobId" value="${(sobId!'0')?c}">
		</dd>
	</dl>
	<dl id="search">
		<dt>商品关键字：</dt>
		<dd>
			<input type="text" id="keywords" class="input text" onblur="searchGoods();" placeholder="商品SKU">
			<span class="Validform_checktip"></span>
		</dd>
	</dl>
	<div id="container" >
		<#include "/site_mag/delivery_fee_head_goods.ftl">
	</div>
  	<script type="text/javascript">
  		var searchGoods = function() {
  			var keywords = $('#keywords').val();
  			if (keywords) {
  				$.ajax({
  					url: '/Verwalter/delivery/fee/head/goods',
  					method: 'post',
  					data: {
  						keywords: keywords
  					},
  					success: function(result) {
  						$('#container').html(result);
  					}
  				});
  			}
  		}
  		
  		var addHead = function() {
  			var sobId = $('#sobId').val();
  			var goodsId = $("input[name='goodsId']:checked").val();

			if (sobId && goodsId) {
				$.ajax({
					url: '/Verwalter/delivery/fee/head/save',
					method: 'POST',
					data: {
						sobId: sobId,
						goodsId: goodsId
					},
					success: function(result) {
						if (0 === Number(result.status)) {
							window.location.href = '/Verwalter/delivery/fee/head/${(sobId!0)?c}';
						} else {
							showAlert(result.message);
						}	
					}
				});
			}
  		}
  		
  		var showAlert = function(text) {
            var dialog = $.dialog.alert(text);

        }
  	</script>
<!--/内容-->


<!--工具栏-->
<div class="page-footer">
	<div class="btn-list">
		<input type="button" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" onclick="addHead();">
		<input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
	</div>
	<div class="clear"></div>
</div>
<!--/工具栏-->
</form>
</body>
</html>