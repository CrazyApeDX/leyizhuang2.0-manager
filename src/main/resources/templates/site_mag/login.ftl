<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>管理员登录</title>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
    $(function () {
        //检测IE
        if ('undefined' == typeof (document.body.style.maxHeight)) {
            window.location.href = 'ie6update.html';
        }
    });
</script>
</head>

<body class="loginbody">
<form name="form1" method="post" action="/Verwalter/login" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
</div>

<div>

	<input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="527A148E">
	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="">
</div>
<div class="login-screen">
	<div class="login-icon">LOGO</div>
    <div class="login-form">
        <h1>系统管理登录</h1>
        <div class="control-group">
            <input name="username" type="text" value="" class="login-field" placeholder="用户名" title="用户名">
            <label class="login-field-icon user"></label>
        </div>
        <div class="control-group">
            <input name="password" type="password" class="login-field" placeholder="密码" title="密码">
            <label class="login-field-icon pwd"></label>
        </div>
        <div><input type="submit" name="btnSubmit" value="登 录" id="btnSubmit" class="btn-login"></div>
        <span class="login-tips"><i></i><b id="msgtip"><#if error??>${error!''}</#if></b></span>
    </div>    
</div>
</form>

<embed id="xunlei_com_thunder_helper_plugin_d462f475-c18e-46be-bd10-327458d045bd" type="application/thunder_download_plugin" height="0" width="0"></body></html>