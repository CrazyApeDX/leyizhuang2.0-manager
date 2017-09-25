<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑信息</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/zh_CN.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
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
            sendurl: "/Verwalter/uploadPic", 
            params : {"source": "ui"},
            flashurl: "/mag/js/swfupload.swf"
        });
    });
    
    //（缩略图）
    var txtPic = $("#txtImgUrl").val();
    if (txtPic == "" || txtPic == null) {
        $("#thumb_ImgUrl_show1").hide();
    }
    else {
        $("#thumb_ImgUrl_show1").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
        $("#thumb_ImgUrl_show1").show();
    }

    $("#txtImgUrl").blur(function () {
        var txtPic = $("#txtImgUrl").val();
        if (txtPic == "" || txtPic == null) {
            $("#thumb_ImgUrl_show1").hide();
        }
        else {
            $("#thumb_ImgUrl_show1").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
            $("#thumb_ImgUrl_show1").show();
        }
    });
    
  
});





//判断活动结束时间不能小于开始时间
function checkDate(){
	var beginDate= $('#beginDate').val();
	var finishDate=$('#finishDate').val();
	
	var useBeginDate= $('#useBeginDate').val();
	var useFinishDate=$('#useFinishDate').val();
	
	if(finishDate<=beginDate){
		alert("亲,活动结束时间不能小于开始时间");
		return false;
	}
	
	if(useFinishDate<=useBeginDate){
		alert("亲,使用结束时间不能小于开始时间");
		return false;
	}
	
}
  <#if fns??>
  	$(document).ready(function(){
  		alert("保存成功");
  		location.href='/Verwalter/redPacket/list';
  	});
  <#else>
  	console.log("aaa");	
  </#if>
</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/redPacket/save" id="form1" onsubmit="return checkDate();">
<div>

</div>
<input name="menuId" type="text" value='${mid!""}' style="display:none;">
<input name="channelId" type="text" value='${cid!""}' style="display:none">
<input name="id" type="text" value='<#if activity??>${activity.id?c}</#if>' style="display:none">
<!--导航栏-->
<div class="location">
    <#--<a href="/Verwalter/goods/list" class="back"><i></i><span>
        返回列表页</span></a> -->
    <a href="/Verwalter/center" class="home">
    <i></i><span>首页</span></a>
    <i class="arrow"></i>
    <span>活动管理</span>
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
            <dt>红包名称</dt>
            <dd>
                <input name="name" type="text" value="<#if activity??>${activity.name!""}</#if>" class="input normal" datatype="*" <#if !activity??> ajaxurl="/Verwalter/activity/check/name"</#if> sucmsg=" ">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
         
        <dl>
            <dt>城市</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="cityId" id="cityId"  datatype="*" sucmsg=" ">
                        <#if !activity??>
                        <option value="">请选择城市...</option>
                        </#if>
                        <#if city_list??> 
                            <#list city_list as c>
                                <option value="${c.id?c}" <#if activity?? && activity.cityId==c.id>selected="selected"</#if>>${c.cityName!''}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>活动图片</dt>
            <dd>
                <input id="txtImgUrl" name="redPacketImg" type="text" value="<#if activity??>${activity.redPacketImg!""}</#if>" class="input normal upload-path">
                <div class="upload-box upload-img"></div>
                <div id="thumb_ImgUrl_show1" class="photo-list thumb_ImgUrl_show">
                </div>
            </dd>
        </dl>
        <dl>
            <dt>排序号</dt>
            <dd>
                <input name="sortId" type="text" value="<#if activity??>${activity.sortId}<#else>99</#if>" class="input normal" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>活动开始时间</dt>
            <dd>
                <div class="input-date">
                    <input name="beginDate" id="beginDate" type="text" value="<#if activity??>${activity.beginDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>结束到期时间</dt>
            <dd>
                <div class="input-date">
                    <input name="finishDate" id="finishDate" type="text" value="<#if activity??>${activity.finishDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>红包使用开始时间</dt>
            <dd>
                <div class="input-date">
                    <input name="useBeginDate" id="useBeginDate" type="text" value="<#if activity??>${activity.useBeginDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>红包使用到期时间</dt>
            <dd>
                <div class="input-date">
                    <input name="useFinishDate" id="useFinishDate" type="text" value="<#if activity??>${activity.finishDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>使用红包最低金额</dt>
            <dd>
                <input name="totalPrice" type="text" value="<#if activity??&&activity.totalPrice??>${activity.totalPrice?string("0.00")}<#else>0.00</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " errormsg="">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>红包金额</dt>
            <dd>
                <input name="price" type="text" value="<#if activity??&&activity.price??>${activity.price?string("0.00")}<#else>0.00</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " errormsg="">
                <span class="Validform_checktip"></span>
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