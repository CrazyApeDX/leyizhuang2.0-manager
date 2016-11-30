<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>乐易装-接口数据管理</title>
    <link type="text/css" rel="stylesheet" href="/inter/bootstrap/dist/css/bootstrap.min.css" />
</head>
<body>
    <div class="text-center panel panel-primary">
        <div class="panel-heading">
        	<div class="panel-title">EBS接口数据管理</div>
        </div>
        <div class="panel-body">
        	<div class="col-lg-12 col-sm-12 col-xs-12">
		        <section class="col-lg-9 col-sm-9 col-xs-9">
		            <div id="content" class="col-lg-12 col-sm-12 col-xs-12">
		            	
		            </div>
		        </section>
		        <section class="col-lg-2 col-sm-2 col-xs-2">
					<div class="dropdown">
						<button class="btn btn-block btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
							菜单
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
							<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:getInfo(types._ORDER_INFO);">订单接口数据管理</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:getInfo(types._ORDER_DETAIL);">订单明细数据管理</a></li>
							<li role="presentation"><a role="menuitem" tabindex="-1" href="#">收款接口数据管理</a></li>
						</ul>
					</div>
					<br>
	        		<div class="group-list">
		                <a href="javascript:allSelect();" class="list-group-item">全选</a>
		                <a id="reSend" href="javascript:reSend();" class="list-group-item">重发</a>
	                </div>
		        </section>
		    </div>
        </div>
    </div>
    <div class="text-center panel panel-primary">
    	<div class="panel-heading">
    		<div class="panel-title">控制台</div>
    	</div>
		<div class="panel-body">
			<div class="col-lg-12 col-sm-12 col-xs-12">
	    		<textarea id="console" class="form-control" rows="5" style="resize:none;" readonly></textarea>
		    </div>
		</div>
    </div>

<script type="text/javascript" src="/inter/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="/inter/bootstrap/dist/js/bootstrap.min.js"></script>
<script type="text/javascript">
    var selectedMenu = {};

    var types = {
        _ORDER_INFO: '_ORDER_INFO',
        _ORDER_DETAIL: '_ORDER_DETAIL'
    };
    var getInfo = function(type) {
        (function($, window, type){

            <!-- 设置全局被选中对象 -->
            selectedMenu = type;

			$.ajax({
				url: '/inter/content',
				method: 'POST',
				data: {
					alias: selectedMenu
				},
				success: function(res) {
					$('#content').html(res);
				}
			});
        })($, window, type);
    }
    var allSelect = function() {
    	(function(){
    		$('[name = data]:checkbox').attr("checked", true);
    	})();
    }
    var reSend = function() {
    	(function() {
    		var values = [];
    		$('[name = data]:checkbox').each(function() {
    			if ($(this).is(':checked')) {
    				values.push($(this).attr('value'));
    			}
    		});
    		
    		if (values && values.length > 0) {
    			$('#reSend').removeAttr('href');
    			$('#console').val('');
    			
    			$.ajax({
    				url: '/inter/send',
    				method: 'POST',
    				traditional: true,
    				data: {
						values: values,
						alias: selectedMenu    				
    				},
    				success: function(res) {
    					if (res.message) {
    						$('#console').val(res.message);
    					}
    					$('#reSend').attr('href', 'javascript:reSend();');
    				}
    			});
    		}
    	})();
	}
</script>
</body>
</html>