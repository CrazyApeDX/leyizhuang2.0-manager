<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑仓库</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();
    
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
});
</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/setting/warehouse/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="id" type="text" value='<#if warehouse??>${warehouse.id?c}</#if>' style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/setting/warehouse/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/setting/warehouse/list"><span>仓库列表</span></a>
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
    <dt>仓库编号</dt>
    <dd>
        <input name="whNumber" type="text" value="<#if warehouse??>${warehouse.whNumber!"1"}</#if>" class="input normal" datatype="*1-255" sucmsg=" "> 
        <span class="Validform_checktip">*仓库编号</span>
    </dd>
  </dl>
    <dl>
        <dt>仓库名称</dt>
        <dd>
            <input name="whName" type="text" value="<#if warehouse?? && warehouse.whName??>${warehouse.whName!""}</#if>" class="input normal" datatype="*1-255" sucmsg=" ">
            <span class="Validform_checktip">仓库名称</span>
        </dd>
    </dl>
    <dl>
        <dt>城市编码（sob_id）</dt>
        <dd>
            <input name="sobId" type="text" value="<#if warehouse?? && warehouse.sobId??>${warehouse.sobId?c}</#if>" class="input normal" datatype="*" sucmsg=" ">
            <span class="Validform_checktip">城市编码，由EBS提供</span>
        </dd>
    </dl>
  <dl style="display: none;">
    <dt>创建时间</dt>
    <dd>
        <input name="creatTime" type="text" readonly="readonly" value="<#if warehouse??>${warehouse.creatTime!"1"}</#if>"  class="input normal" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " /> 
        <span class="Validform_checktip">创建时间</span>
    </dd>
  </dl>
  <dl style="display: none;">
    <dt>修改时间</dt>
    <dd>
        <input name="updateTime" type="text" readonly="readonly" value="<#if warehouse??>${warehouse.updateTime!"1"}</#if>"  class="input normal" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " /> 
        <span class="Validform_checktip">修改时间</span>
    </dd>
  </dl> 
  <dl>  
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if warehouse??>${warehouse.sortId!"0"}<#else>99</#if>" class="input small" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
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


</body></html>