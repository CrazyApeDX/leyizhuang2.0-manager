<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑品牌</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/zh_CN.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/delivery/fee/line/edit/${(headId!0)?c}/${(lineId!0)?c}" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input name="headId" id="headId" type="text" value='${(headId!0)?c}' style="display:none;">
<input name="lineId" id="lineId" type="text" value='${(lineId!0)?c}' style="display:none">
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
	<a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
	<a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
	<i class="arrow"></i>
	<span>城市列表</span>
	<i class="arrow"></i>
	<span>运费管理</span> 
</div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
    <div id="floatHead" class="content-tab">
        <div class="content-tab-ul-wrap">
            <ul>
                <li><a href="javascript:;" onclick="tabs(this);" class="selected">基本信息</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="tab-content" style="display: block;">
    <dl>
        <dt>最小购买量</dt>
        <dd>
            <input id="minNumber" name="minNumber" type="number" datatype="*" value="<#if deliveryFeeLine??>${deliveryFeeLine.minNumber!''}</#if>" class="input normal" sucmsg=" " />
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>最大购买量</dt>
        <dd>
            <input id="maxNumber" name="maxNumber" type="number" datatype="*" value="<#if deliveryFeeLine??>${deliveryFeeLine.maxNumber!''}</#if>" class="input normal" sucmsg=" " />
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>运费单价</dt>
        <dd>
            <input id="unit" name="unit" type="text"  datatype="/^\d+(?:\.\d{1,2})?$/" value="<#if deliveryFeeLine??>${(deliveryFeeLine.unit!0.00)?string("0.00")}</#if>" class="input normal" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>一口价</dt>
        <dd>
            <input id="fixedPrice" name="fixedPrice" type="text" datatype="/^\d+(?:\.\d{1,2})?$/" value="<#if deliveryFeeLine?? && deliveryFeeLine.fixedPrice??>${deliveryFeeLine.fixedPrice?string("0.00")}</#if>" class="input normal" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
    <div class="btn-list">
        <input type="button" name="btnSubmit" value="提交保存" class="btn" onclick="save();">
        <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
    </div>
    <div class="clear">
    </div>
</div>
<!--/工具栏-->
</form>
</body>
<script type="text/javascript">
	var count = 0;
	var save = function() {
		count++;
		
		var minNumber = $('#minNumber').val();
		var maxNumber = $('#maxNumber').val();
		var unit = $('#unit').val();
		var fixedPrice = $('#fixedPrice').val();
		var headId = $('#headId').val();
		var lineId = $('#lineId').val();
		
		$.ajax({
			url: '',
			method: 'POST',
			data: {
				minNumber: minNumber,
				maxNumber: maxNumber,
				unit: unit,
				fixedPrice: fixedPrice,
				headId: headId,
				lineId: lineId
			},
			success: function(result) {
				if (0 === Number(result.status)) {
					window.location.href = '/Verwalter/delivery/fee/line/${(sobId!0)?c}/${(headId!0)?c}';
				} else {
					$.dialog.alert(result.message);
				}
			}
		});
	}

</script>
</html>