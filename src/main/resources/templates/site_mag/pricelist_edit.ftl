<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑价目表</title>
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
    $("#form1").initValidform();
});
</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/pricelist/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="id" type="text" value='<#if priceList??>${priceList.id?c}</#if>' style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/pricelist/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/pricelist/list"><span>价目表</span></a>
  <i class="arrow"></i>
  <span>编辑价目表</span>
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
        <input name="name" type="text" value="<#if priceList??>${priceList.name!""}</#if>" class="input normal" datatype="*1-100" sucmsg=" "> 
        <span class="Validform_checktip">*名称</span>
    </dd>
  </dl>
    <dl>
        <dt>所属城市</dt>
        <dd>
            <div class="rule-single-select">
            <select name="cityId" datatype="*" sucmsg=" ">
                <option value="" <#if !priceList?? || !priceList.cityId??>selected="selected"</#if>>请选择</option>
                <#if city_list??>
                	<#list city_list as item>
                		<option value="<#if item??>${item.id?c}</#if>" <#if priceList?? && priceList.cityId ==item.id>selected="selected"</#if>><#if item.province??>${item.province}-</#if>${item.cityName!''}</option>
                	</#list>
                </#if>	
            </select>
            </div>
        </dd>
    </dl>

  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if priceList??>${priceList.sortId!""}<#else>99</#if>" class="input small" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  
  <dl>
    <dt>创建者名称</dt>
    <dd>
      <textarea name="username" rows="2" cols="20" class="input normal"><#if priceList??>${priceList.username!""}</#if></textarea>
      <span class="Validform_checktip"></span>
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