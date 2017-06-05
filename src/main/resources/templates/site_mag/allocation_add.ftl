<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑信息</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();
    
    // 添加产品
    $("#addGoods").click(function(){
        showDialogCombination();
    });
    
    //创建商品组合窗口
    function showDialogCombination(obj) {
        var objNum = arguments.length;
        
        var combinationDialog = $.dialog({
            id: 'combinationDialogId',
            lock: true,
            max: false,
            min: false,
            title: "选择产品",
            content: 'url:/Verwalter/goods/list/dialog/allocation?total=' + $("#var_box_comb").children("tr").length+'&cityId='+$('#cityId').val(),
            width: 800,
            height: 550
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            combinationDialog.data = obj;
        }
    }
    
    //删除商品组合节点
    function delCombinationNode(obj) {
        $(obj).parent().parent().remove();
    }
});

//创建商品组合窗口
function show_goods_comb_dialog(obj) {
    var objNum = arguments.length;
    var zengpinDialog = $.dialog({
        id: 'zengpinhDialogId',
        lock: true,
        max: false,
        min: false,
        title: "活动赠品",
        content: 'url:/Verwalter/goods/list/dialog/allocation',
        width: 800,
        height: 550
    });
    //如果是修改状态，将对象传进去
    if (objNum == 1) {
        zengpinDialog.data = obj;
    }
}
    
//删除促销商品节点
function del_goods_gift(obj) {
    $(obj).parent().parent().remove();
    $("#totalGift").val(parseInt($("#totalGift").val())-1);
}

function checkDetail() {
	var validateFlag = true;
	var trs = $("#var_box_comb").find("tr");
	if(trs.length==0) {
		alert('亲，必须选择调拨产品');
		return false;
	}
	
	var re = /^[0-9]+.?[0-9]*$/;
	trs.each(function(i,n){
		var num = $(n).find(".number").val();
		if(num=='' || num==0 || !re.test(num)) {
			validateFlag = false;
		}
	});
	if(!validateFlag) {
		alert('亲，数量必须为数字且不能为0');
	}
	return validateFlag;
}

//删除商品组合节点
function del_goods_comb(obj) {
    $(obj).parent().parent().remove();
    $("#totalComb").val(parseInt($("#totalComb").val())-1);
}

  <#if fns??>
  	$(document).ready(function(){
  		alert("保存成功");
  		location.href='/Verwalter/allocation/list';
  	});
  </#if>
</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/allocation/save" id="form1" onsubmit="return checkDetail();">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input id="cityId" name="cityId" type="text" value='${cityId!""}' style="display:none">
<input name="cityName" type="text" value='${cityName!""}' style="display:none">
<!--导航栏-->
<div class="location">
    <a href="/Verwalter/center" class="home">
    <i></i><span>首页</span></a>
    <i class="arrow"></i>
    <span>新建调拨单</span>
</div>
<div class="line10">
</div>
<!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap" >
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">基本信息</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
      	<dl>
            <dt>调出门店</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="allocationFrom" datatype="*" sucmsg=" ">
						<option value="">请选择</option>
                        <#list diySites as c>
                            <option value="${c.id?c}" >${c.title!''}</option>
                        </#list>
                    </select>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>备注</dt>
            <dd>
                <input name="comment" type="text" value="" class="input normal" sucmsg=" ">
            </dd>
        </dl>
        
        <dl>
            <dt>调拨明细</dt>
            <dd>
                <a id="addGoods" class="icon-btn add"><i></i><span>添加产品</span></a>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt></dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <thead>
                        <tr>
                            <th width="20%">
                               产品编码
                            </th>
                            <th width="60%">
                                产品名称
                            </th>
                            <th width="10%">
                                数量
                            </th>
                            <th width="10%">
                                操作
                            </th>
                        </tr>
                    </thead>
                    <tbody id="var_box_comb">
                        
                    </tbody>
                </table>
            </dd>
        </dl>
    </div>
    
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
            <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" >
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
</body>
</html>