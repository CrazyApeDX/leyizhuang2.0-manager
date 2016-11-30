<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑优惠券类型</title>
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
<form name="moduleForm" method="post" action="/Verwalter/coupon/module/save" id="moduleForm">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="id" type="text" value='<#if module??>${module.id?c}</#if>' id="moduleId" style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/coupon/type/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/coupon/type/list"><span>模板列表</span></a>
  <i class="arrow"></i>
  <span>编辑优惠券模板</span>
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
    <dt>名称</dt>
    <dd>
        <input name="title" type="text" value="<#if module??>${module.title!""}</#if>" class="input normal" datatype="*2-100" sucmsg=" "> 
        <span class="Validform_checktip">*名称</span>
    </dd>
  </dl>
    <dl>
        <dt>城市</dt>
        <dd>
            <div class="rule-single-select">
            <select name="cityId" id="cityId" datatype="*" sucmsg=" " onchange="javascript:searchGoods();">
            	<#if city_list??>
            		<#list city_list as item>
                		<option value="${item.sobIdCity?c}" <#if module?? && module.cityId == item.sobIdCity>selected="selected"</#if>>${item.cityName!''}</option>
                	</#list>
                </#if>
            </select>
            </div>
        </dd>
    </dl>
     <dl>
            <dt>模板类型</dt>
            <dd>
                <div class="rule-single-select">
                    <select id="type" name="type" datatype="*" sucmsg=" ">
                        <#if !module??>
                        <option value="">请选择类别...</option>
                        </#if>
                        <option value="0" <#if module?? && module.type?? && module.type == 0>selected="selected"</#if> >商品</option>
                        <option value="1" <#if module?? && module.type?? && module.type == 1>selected="selected"</#if> >产品卷</option>
                    </select>
                </div>
            </dd>
        </dl>
    <dl>
        <dt>价值</dt>
        <dd>
          <input name="price" type="text" value="<#if module?? && module.price?? >${module.price?string("0.00")}<#else>0</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
          <span class="Validform_checktip">优惠券的价值</span>
        </dd>
    </dl>
	  <dl>
	    <dt>排序数字</dt>
	    <dd>
	      <input name="sortId" type="text" value="<#if module??>${module.sortId!""}<#else>99</#if>" class="input small" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
	      <span class="Validform_checktip">*数字，越小越向前</span>
	    </dd>
	  </dl>
    <dl id="search">
    <dt>产品关键字</dt>
    <dd>
      <input  type="text" value="<#if module??>${module.sku!''}</#if>" id="keywords" class="input text"  onblur="javascript:searchGoods();">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
<div id="goodsDiv" >
	<#include "/site_mag/coupon_goods.ftl">
</div>
  
<!--/内容-->


<!--工具栏-->
<div class="page-footer">
  <div class="btn-list">
    <input type="button" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" onclick="moduleCheck();">
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
    var cityId = $("#cityId").val();
    $.ajax({
        type : "post",
        url : "/Verwalter/coupon/search",
        data : {"keywords":keywords,"cityId":cityId,"searchType":1},
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