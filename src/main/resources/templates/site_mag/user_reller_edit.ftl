<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑用户名</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/WdatePicker.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form_user").initValidform();
    
</script>
</head>

<body class="mainbody">
<form name="form_user" method="post" action="/Verwalter/user/moreSeller" id="form_user">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
<input type="hidden" id="userId" name="userId" value="<#if user??>${user.id?c!""}</#if>" >
</div>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="/Verwalter/user/list?roleId=<#if roleId??>${roleId?c}"<#else>0</#if><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>会员管理</span>
  <i class="arrow"></i>
  <span>导购离职</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab" style="position: static; top: 52px;">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">账户信息</a></li>
      </ul>
    </div>
  </div>
</div>

<!--账户信息-->
<div class="tab-content">
	<input type="hidden" name="id" value="<#if user??>${user.id?c}</#if>" >
    <dl>
        <dt>原始${userTypeName!'' }名：</dt>
       <dd>
            <#if user??>${user.username!''}</#if>
       </dd>
    </dl>
  


    <dl>
        <dt>新任${userTypeName!'' }名：</dt>
       <dd>
            <input name="newUsername" type="text" value="<#if user??>${user.referPhone!''}</#if>" maxlength="200" class="input normal" datatype="m|/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/" ajaxurl="/Verwalter/user/check<#if user??>?id=${user.id?c}</#if>" sucmsg=" " minlength="2">
       		<input name="oldUsername" type="hidden" value="<#if user??>${user.referPhone!''}</#if>" />
            <span class="Validform_checktip">*修改${userTypeName!'' }名后，原始${userTypeName!'' }名下的用户将同步为新${userTypeName!'' }名</span>
            <#if error??><span class="Validform_checktip">${error }</span></#if>
        </span></dd>
    </dl>
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