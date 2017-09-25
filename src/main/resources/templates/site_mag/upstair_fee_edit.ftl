<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑上楼费</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/zh_CN.js"></script>
<link href="/mag/style/WdatePicker.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();
    
    //（缩略图）
    var txtPic = $("#txtImgUrl").val();
    if (txtPic == "" || txtPic == null) {
        $(".thumb_ImgUrl_show").hide();
    }
    else {
        $(".thumb_ImgUrl_show").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
        $(".thumb_ImgUrl_show").show();
    }
});



</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/upstair/fee/save" id="form1">
<!--导航栏-->
<div class="location">
  <a href="/Verwalter/setting/city/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/coupon/list"><span>城市列表</span></a>
  <i class="arrow"></i>
  <span>编辑上楼费</span>
</div>
<div class="line10"></div>
<!--/导航栏-->
<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑上楼费</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  	 	
  <dl>
    <dt>板材类商品SKU</dt>
    <dd>
      <input name="panelSkus" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.panelSkus??>${tdUpstairsSetting.panelSkus}</#if>" class="input normal" datatype="/^[A-Za-z0-9#,-.]{0,1000}$/" sucmsg="通过验证">
      <span class="Validform_checktip">*板材类商品SKU，多个以英文逗号隔开</span>
    </dd>
  </dl>
  <dl>
    <dt>木龙类商品SKU</dt>
    <dd>
      <input name="keelSkus" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.keelSkus??>${tdUpstairsSetting.keelSkus}</#if>" class="input normal" datatype="/^[A-Za-z0-9#,-.]{0,1000}$/" sucmsg="通过验证" >
      <span class="Validform_checktip">*木龙类商品SKU，多个以英文逗号隔开</span>
    </dd>
  </dl>
  <dl>
    <dt>轻钢类商品SKU</dt>
    <dd>
      <input name="metalSkus" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.metalSkus??>${tdUpstairsSetting.metalSkus}</#if>" class="input normal" datatype="/^[A-Za-z0-9#,-.]{0,1000}$/" sucmsg="通过验证" >
      <span class="Validform_checktip">*轻钢类商品SKU，多个以英文逗号隔开</span>
    </dd>
  </dl>
  <dl>
    <dt>板材上楼费单位价（步梯）</dt>
    <dd>
      <input name="panelStepUnit" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.panelStepUnit??>${tdUpstairsSetting.panelStepUnit?string("0.00")}<#else>0.00</#if>" class="input txt100" datatype="/^\d{0,8}\.{0,1}(\d{1,2})+$/" sucmsg=" " >
      <span class="Validform_checktip">*板材上楼费单位价（步梯）</span>
    </dd>
  </dl> 
  <dl>
    <dt>木龙上楼费单位价（步梯）</dt>
    <dd>
      <input name="keelStepUnit" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.keelStepUnit??>${tdUpstairsSetting.keelStepUnit?string("0.00")}<#else>0.00</#if>" class="input txt100" datatype="/^\d{0,8}\.{0,1}(\d{1,2})+$/" sucmsg=" " >
      <span class="Validform_checktip">*木龙上楼费单位价（步梯）</span>
    </dd>
  </dl>
  <dl>
    <dt>轻钢上楼费单位价（步梯）</dt>
    <dd>
      <input name="metalStepUnit" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.metalStepUnit??>${tdUpstairsSetting.metalStepUnit?string("0.00")}<#else>0.00</#if>" class="input txt100" datatype="/^\d{0,8}\.{0,1}(\d{1,2})+$/" sucmsg=" " >
      <span class="Validform_checktip">*轻钢上楼费单位价（步梯）</span>
    </dd>
  </dl>
  <dl>
    <dt>板材上楼费单位价（电梯）</dt>
    <dd>
      <input name="panelEleUnit" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.panelEleUnit??>${tdUpstairsSetting.panelEleUnit?string("0.00")}<#else>0.00</#if>" class="input txt100" datatype="/^\d{0,8}\.{0,1}(\d{1,2})+$/" sucmsg=" " >
      <span class="Validform_checktip">*板材上楼费单位价（电梯）</span>
    </dd>
  </dl>
  <dl>
    <dt>木龙上楼费单位价（电梯）</dt>
    <dd>
      <input name="keelEleUnit" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.keelEleUnit??>${tdUpstairsSetting.keelEleUnit?string("0.00")}<#else>0.00</#if>" class="input txt100" datatype="/^\d{0,8}\.{0,1}(\d{1,2})+$/" sucmsg=" " >
      <span class="Validform_checktip">*木龙上楼费单位价（电梯）</span>
    </dd>
  </dl>
  <dl>
    <dt>轻钢上楼费单位价（电梯）</dt>
    <dd>
      <input name="metalEleUnit" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.metalEleUnit??>${tdUpstairsSetting.metalEleUnit?string("0.00")}<#else>0.00</#if>" class="input txt100" datatype="/^\d{0,8}\.{0,1}(\d{1,2})+$/" sucmsg=" " >
      <span class="Validform_checktip">*轻钢上楼费单位价（电梯）</span>
    </dd>
  </dl>
  <dl>
    <dt>木龙单位数量</dt>
    <dd>
      <input name="keelUnitNumber" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.keelUnitNumber??>${tdUpstairsSetting.keelUnitNumber}</#if>" class="input small" datatype="n" sucmsg=" " >
      <span class="Validform_checktip">*木龙单位数量</span>
    </dd>
  </dl>
  <dl>
    <dt>轻钢单位数量</dt>
    <dd>
      <input name="metalUnitNumber" type="text" value="<#if tdUpstairsSetting??&&tdUpstairsSetting.metalUnitNumber??>${tdUpstairsSetting.metalUnitNumber}</#if>" class="input small" datatype="n" sucmsg=" " >
      <span class="Validform_checktip">*轻钢单位数量</span>
    </dd>
  </dl>
</div>
</div>
<div>
	<input type="hidden" name="sobIdCity" id="sobIdCity" value="${sobId?c}">
	<input type="hidden" name="id" id="id" value="<#if tdUpstairsSetting.id??>${tdUpstairsSetting.id?c}</#if>">
</div>
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


</body></html>
