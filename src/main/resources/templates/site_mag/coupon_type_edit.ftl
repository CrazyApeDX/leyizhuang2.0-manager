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
<form name="form1" method="post" action="/Verwalter/coupon/type/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="couponTypeId" type="text" value='<#if coupon_type??>${coupon_type.id?c}</#if>' style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/coupon/type/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/coupon/type/list"><span>优惠券类型</span></a>
  <i class="arrow"></i>
  <span>编辑优惠券类型</span>
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
        <input name="title" type="text" value="<#if coupon_type??>${coupon_type.title!""}</#if>" class="input normal" datatype="*2-100" sucmsg=" "> 
        <span class="Validform_checktip">*名称</span>
    </dd>
  </dl>
    <dl>
        <dt>类型</dt>
        <dd>
            <div class="rule-single-select">
            <select name="categoryId" datatype="*" sucmsg=" ">
                <option value="" <#if !coupon_type?? || !coupon_type.categoryId??>selected="selected"</#if>>请选择</option>
                <option value="0" <#if coupon_type?? && coupon_type.categoryId == 0>selected="selected"</#if>>全场通用券 </option>
                <option value="1" <#if coupon_type?? && coupon_type.categoryId == 1>selected="selected"</#if>>分品类满减券</option>
                <option value="2" <#if coupon_type?? && coupon_type.categoryId == 2>selected="selected"</#if>>不分品类满减券</option>
            </select>
            </div>
        </dd>
    </dl>
    <dl>
        <dt>满减券使用额度</dt>
        <dd>
          <input name="canUsePrice" type="text" value="<#if coupon_type?? && coupon_type.canUsePrice?? >${coupon_type.canUsePrice?string("0.00")}<#else>0</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
          <span class="Validform_checktip">消费满该额度时可使用，仅对满减券有效</span>
        </dd>
    </dl>
    <dl>
        <dt>分品类满减券限制使用品类</dt>
        <dd>
            <div class="rule-single-select">
            <select name="productTypeId" datatype="n0-5">
                <option <#if coupon_type?? && coupon_type.productTypeId??><#else>selected="selected"</#if> value="">请选择</option>
                <#if category_list??>
                    <#list category_list as c>
                        <option value="${c.id?c}" <#if coupon_type?? && coupon_type.productTypeId?? && c.id==coupon_type.productTypeId>selected="selected"</#if> ><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                    </#list>
                </#if>
            </select>
        </div>
        </dd>
    </dl>
    
  <dl>
    <dt>优惠券抵用额度</dt>
    <dd>
      <input name="price" type="text" value="<#if coupon_type??>${coupon_type.price!""}<#else>0</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
      <span class="Validform_checktip">免费券抵用额度为0</span>
    </dd>
  </dl>
  <dl>
    <dt>有效期</dt>
    <dd>
      <input name="totalDays" type="text" value="<#if coupon_type??>${coupon_type.totalDays!""}<#else>1</#if>" class="input small" datatype="*" sucmsg=" ">
      <span class="Validform_checktip">天</span> <span class="Validform_checktip">从领用时间开始计算</span>
    </dd>
  </dl>
  
  <dl>
    <dt>优惠券图片</dt>
    <dd>
        <input id="txtImgUrl" name="picUri" type="text" datatype="*0-255" value="<#if coupon_type??>${coupon_type.picUri!""}</#if>" class="input normal upload-path">
        <div class="upload-box upload-img"></div>
        <div class="photo-list thumb_ImgUrl_show"></div>
        <span class="Validform_checktip"></span>
    </dd>
  </dl>

  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if coupon_type??>${coupon_type.sortId!""}<#else>99</#if>" class="input small" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  
  <dl>
    <dt>描述说明</dt>
    <dd>
      <textarea name="description" rows="2" cols="20" class="input normal"><#if coupon_type??>${coupon_type.description!""}</#if></textarea>
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