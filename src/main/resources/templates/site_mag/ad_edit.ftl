<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑广告</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
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
<form method="post" action="/Verwalter/ad/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
<input type="hidden" name="id" value="<#if ad??>${ad.id?c!""}</#if>" >
</div>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="/Verwalter/ad/list"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>广告管理</span>
  <i class="arrow"></i>
  <span>编辑广告</span>
</div>
<div class="line10"></div>
<!--导航栏-->
<!--内容-->
<div class="content-tab-wrap">
    <div id="floatHead" class="content-tab">
        <div class="content-tab-ul-wrap" >
            <ul>
                <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑广告</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="tab-content" style="display: block;">
    <dl>
        <dt>所属广告位</dt>
        <dd>
            <div class="rule-single-select single-select">
            <select name="typeId" datatype="*" sucmsg=" ">
                <option value="" <#if !ad??>selected="selected"</#if>>请选择</option>
                <#if ad_type_list??>
                    <#list ad_type_list as type>
                        <option value="${type.id}" <#if ad?? && ad.typeId == type.id>selected="selected"</#if>>${type.title}</option>
                    </#list>
                </#if>
            </select>
            </div>
        </dd>
    </dl>
    <dl>
        <dt>状态</dt>
        <dd>
            <div class="rule-multi-radio multi-radio">
                <span>
                    <input type="radio" name="isEnable" value="1" <#if ad?? && ad.isEnable>checked="checked"</#if>>
                    <label>正常</label>
                    <input type="radio" name="isEnable" value="0" <#if !ad?? || !ad.isEnable>checked="checked"</#if>>
                    <label>暂停</label>
                </span>
            </div>
        </dd>
    </dl>
    <dl>
        <dt>广告名称</dt>
        <dd>
            <input name="title" type="text" value="<#if ad??>${ad.title!""}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>广告副标题</dt>
        <dd>
            <input name="subtitle" type="text" value="<#if ad??>${ad.subtitle!""}</#if>" class="input normal" >
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>广告图片</dt>
        <dd>
            <input id="txtImgUrl" name="fileUri" type="text" datatype="*" value="<#if ad??>${ad.fileUri!""}</#if>" class="input normal upload-path">
            <div class="upload-box upload-img"></div>
            <div class="photo-list thumb_ImgUrl_show">
                <ul>
                    <li>
                        <div class="img-box1"></div>
                    </li>
                </ul>
            </div>
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>链接地址</dt>
        <dd>
            <input name="linkUri" type="text" value="<#if ad??>${ad.linkUri!""}</#if>" class="input normal" datatype="*1-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>开始时间</dt>
        <dd>
            <div class="input-date">
                <input name="startTime" type="text" value="<#if ad??>${ad.startTime!""}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                <i>日期</i>
            </div>
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>到期时间</dt>
        <dd>
            <div class="input-date">
                <input name="endTime" type="text" value="<#if ad??>${ad.endTime!""}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                <i>日期</i>
            </div>
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>排序数字</dt>
        <dd>
            <input name="sortId" type="text" value="<#if ad??>${ad.sortId!""}<#else>99</#if>" class="input txt100" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
            <span class="Validform_checktip">*数字，越小越向前</span>
        </dd>
    </dl>
    <dl>
        <dt>备注</dt>
        <dd>
            <textarea name="mark" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if ad??>${ad.mark!""}</#if></textarea>
            <span class="Validform_checktip">255个字符以内</span>
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
    <div class="clear">
    </div>
</div>
<!--/工具栏-->
</form>
</body>
</html>