<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑物流配送</title>

<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script src="/client/js/jquery.cityselect.js"></script>


<link href="/mag/style/style.css" rel="stylesheet" type="text/css"> 
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
    
    $(".upload-show360").each(function () {
        $(this).InitSWFUpload_show360({ 
            btntext: "批量上传", 
            btnwidth: 66, 
            single: false, 
            water: true, 
            thumbnail: true, 
            filesize: "5120", 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf", 
            filetypes: "*.jpg;*.jpge;*.png;*.gif;" 
        });
    });
    
     $("#address").citySelect({
        nodata:"none",
        <#if diy_site?? && diy_site.cityId??>prov: "${diy_site.cityId?c}",</#if>
        <#if diy_site?? && diy_site.disctrictId??>city: "${diy_site.disctrictId?c}",</#if>
        <#if diy_site?? && diy_site.subDisctrictId??>dist: "${diy_site.subDisctrictId?c}",</#if>
        required:false
    });
 });
   
    
</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/diySiteAccount/setting/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/diySiteAccount/diySite/list"><span>门店账号</span></a>
  <i class="arrow"></i>
  <span>门店账号编辑</span>
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
    <dt>门店名称:</dt>
    <dd>
        ${diySiteName!''}
         <input name="diySiteTitle" type="text" value="${diySiteName!''}" style="display:none">
         <input name="diySiteId" type="text" value="${id?c}" style="display:none" >
    </dd>
  	<dt>选择门店管理账号:</dt>
    <dd>
        <div class="rule-single-select">
        <select name="user" datatype="*" sucmsg=" ">
            <option value="" >请选择</option>
            <#if userList??>
            	<#list userList as item>
            		<option value="<#if item??>${item.id?c},${item.username!''}</#if>" <#if diySiteAccount??&&diySiteAccount.userId==item.id>selected</#if> >${item.realName!''}[${item.username!''}]</option>
            	</#list>
            </#if>	
        </select>
        </div>
    </dd>


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