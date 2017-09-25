<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0042)http://localhost:2105/site_mag/center.aspx -->
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理首页</title>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="" id="form1">
    <!--导航栏-->
    <div class="location">
      <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
      <a class="home"><i></i><span>首页</span></a>
      <i class="arrow"></i>
      <span>管理中心</span>
    </div>
    <!--/导航栏-->
    
    <!--内容-->
    <div class="line10"></div>
    <div class="nlist-1">
      <ul>
        <li>本次登录IP：${client_ip!("")}</li>
        <#if last_ip??>
        <li>上次登录IP：${last_ip!''}</li>
        </#if>
        <#if last_login_time??>
        <li>上次登录时间：${last_login_time?string("yyyy-MM-dd HH:mm:ss")}</li>
        </#if>
      </ul>
    </div>
    <div class="line10"></div>
    
    <div class="nlist-2">
      <h3><i></i>站点信息</h3>
      <ul>
        <li>站点名称：${site_name!''}</li>
        <li>公司名称：${company_name!''}</li>
        <li>网站域名：${server_name!("")}</li>
        <li>安装目录：/</li>
        <li>网站管理目录：site_mag</li>
        <li>附件上传目录：upload</li>
        <li>服务器名称：${server_name!("")}</li>
        <li>服务器IP：${server_ip!("")}</li>
        <li>Java版本：${java_version!("")}</li>
        <li>操作系统：${os_name!("sss")}</li>
        <li>服务器端口：${server_port?c!("")}</li>
        <li>目录物理路径：${java_home!("")}</li>
      </ul>
    </div>
    <div class="line20"></div>
    
    <div class="nlist-jszc">
      <h3><i></i>技术支持</h3>
      <ul>
        <li>系统开发公司：昆明天度网络信息技术有限公司</li>    
        <li>公司联系地址：云南省.昆明市环城南路668号（海埂路口）云纺国际商厦A座18层</li>
        <li>免费800服务热线：800-889-7711</li>
        <li>公司联系电话：0871-63157517、63157171、63157172、63139406、63168206、63167196</li>
        <li>在线咨询方式：QQ：563957</li>
        <li>电子邮件：info@ynyes.com</li>
        <li>公司网站：<a href="http://www.ynyes.com/" target="_blank">http://www.ynyes.com</a></li>    
      </ul>
    </div>
    
    <div class="line20"></div>
    
    

<!--/内容-->
</form>


</body>
</html>