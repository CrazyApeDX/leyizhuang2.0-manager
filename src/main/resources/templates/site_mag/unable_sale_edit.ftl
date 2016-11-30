<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑门店限购</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#moduleForm").initValidform();
    
    //初始化上传控件
    $(".upload-img").each(function () {
        $(this).InitSWFUpload({ 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf"
        });
    });
    
    //（缩略图）
    var txtPic = $("#txtImgUrl").val();
    if (txtPic == "" || txtPic == null) {
        $(".thumb_ImgUrl_show").hide();
    }
    else {
        $(".thumb_ImgUrl_show").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
        $(".thumb_ImgUrl_show").show();
    }
    
    $("#addGoods").click(function(){
        showDialogCombination();
    });
    
    function showDialogCombination(obj) {
        var objNum = arguments.length;
        
        var combinationDialog = $.dialog({
            id: 'combinationDialogId',
            lock: true,
            max: false,
            min: false,
            title: "商品组合",
            content: 'url:/Verwalter/goods/list/dialog/module?total=' + $("#var_box_comb").children("tr").length,
            width: 800,
            height: 550
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            combinationDialog.data = obj;
        }
    }

});
</script>
</head>

<body class="mainbody">
<form name="moduleForm" method="post" action="/Verwalter/unable/sale/save" id="moduleForm">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="id" type="text" value='<#if item??>${item.id?c}</#if>' id="moduleId" style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/coupon/type/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/coupon/type/list"><span>门店限购列表</span></a>
  <i class="arrow"></i>
  <span>编辑门店限购</span>
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
        <dt>门店</dt>
        <dd>
            <div class="rule-single-select">
            <select name="diySiteCode" id="diySiteCode" datatype="*" sucmsg=" ">
            	<#if diySiteList??>
            		<#list diySiteList as diy>
                		<option value="${diy.storeCode!''}" <#if item?? && item.diySiteCode == diy.storeCode>selected="selected"</#if>>${diy.title!''}</option>
                	</#list>
                </#if>
            </select>
            </div>
        </dd>
    </dl>
<dl>	
    <dt>产品关键字</dt>
    <dd>
      <input  type="text" value="<#if item??>${item.goodsSku!''}</#if>" id="keywords" class="input text"  onblur="javascript:searchGoods();">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
<div id="goodsDiv" >
	<#include "/site_mag/unable_sale_goods.ftl">
</div>
  
<!--/内容-->


<!--工具栏-->
<div class="page-footer">
  <div class="btn-list">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
  <div class="clear"></div>
</div>
<!--/工具栏-->
</form>


</body>
<script type="text/javascript">
function searchGoods()
{
    var keywords = $("#keywords").val();
    if (!keywords) {
    	return;
    }
    $.ajax({
        type : "post",
        url : "/Verwalter/unable/sale/search",
        data : {"keywords":keywords},
        success:function(res)
        {
            $("#goodsDiv").html(res);
        }　
    })
}

function moduleCheck(){
	var cityId = $("#cityId option:selected").val();
	var type = $("#type option:selected").val();
	var goodsId = $("input[name='goodsId']:checked").val()
	var moduleId = $("#moduleId").val();
	<#-- 判断是否选中 -->
	if(!cityId || "" === cityId){
		$.dialog.alert("请选择城市");
		return;
	}
	
	if(!goodsId || "" === goodsId){
		$.dialog.alert("请选择商品");
		return;
	}
	
	if(!type || "" === type){
		$.dialog.alert("请选择模板类型");
		return;
	}
	
	if(!moduleId){
		moduleId = null;
	}
	
	<#-- 发送异步请求 -->
	$.ajax({
		url : "/Verwalter/coupon/module/check",
		type : "post",
		timeout : 20000,
		data : {
			cityId : cityId,
			goodsId : goodsId,
			type : type,
			moduleId : moduleId
		},
		error : function(){
			$.dialog.alert("请选择商品");
		},
		success : function(res){
			if(0 === res.status){
				$("#moduleForm").submit();
			}else{
				$.dialog.alert(res.message);
			}
		}
	});
}
</script>
</html>