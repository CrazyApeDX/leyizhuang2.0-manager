<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑优惠券</title>
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

function changeType(e)
{
    if(e.value==3 || e.value==2)
    {
        $("#search").css("display","block");
    }else{
         $("#search").css("display","none");
    }
}

</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/coupon/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="couponId" type="text" value='<#if coupon??>${coupon.id?c}</#if>' style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/coupon/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/coupon/list"><span>优惠券</span></a>
  <i class="arrow"></i>
  <span>编辑优惠券</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑优惠券</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dt>优惠券类型</dt>
    <dd>
        <div class="rule-single-select">
            <select id="type" name="typeCategoryId" datatype="n" sucmsg=" " onchange="changeType(this)"  <#if coupon??>readOnly="true"</#if>>
                <#if !coupon??>
                	<option value="">请选择类型...</option>
                </#if> 
            	<option value="1" <#if coupon?? && coupon.typeCategoryId==1>selected="selected"</#if>>通用现金券</option>
            	<option value="2" <#if coupon?? && coupon.typeCategoryId==2>selected="selected"</#if>>指定商品现金券</option>
            	<option value="3" <#if coupon?? && coupon.typeCategoryId==3>selected="selected"</#if>>产品券</option>
            </select> 
        </div>
    </dd>
  </dl>
  <dl>
    <dt>发放方式</dt>
    <dd>
    	<div class="rule-multi-radio multi-radio">
            <span>
                <input type="radio" name="typeId" value="1" <#if !coupon?? || coupon.typeId==1>checked="checked"</#if> <#if coupon??>readOnly="true"</#if>>
                <label>手动发放</label>
                <input type="radio" name="typeId" value="2" <#if coupon?? && coupon.typeId==2>checked="checked"</#if> <#if coupon??>readOnly="true"</#if>>
                <label>用户抢券</label>
            </span>
        </div>
    </dd>
  </dl>
  <dl>
            <dt>城市</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="cityId" id="cityId"  datatype="*" sucmsg=" " <#if !coupon??>onchange="javascript:searchGoods();"</#if> <#if coupon??>readOnly="true"</#if>>
                        <#if !activity_gift??>
                        <option value="">请选择城市...</option>
                        </#if>
                        <#if city_list??> 
                            <#list city_list as c>
                                <option value="${c.id?c}" <#if coupon?? && coupon.cityId?? && coupon.cityId==c.id>selected="selected"</#if>>${c.cityName!''}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </dd>
        </dl>
  <dl>
  
    <dt>所属公司</dt>
    <dd>
        <div class="rule-single-select">
            <select name="brandId" id="brandId" <#if !coupon??>onchange="javascript:searchGoods();"</#if> <#if coupon??>readOnly="true"</#if>>
            	<option value="">请选择...</option>
                <#if brand_list??>
                    <#list brand_list as item>
                        <option value="${item.id?c!""}" <#if coupon?? && coupon.brandId?? && coupon.brandId==item.id>selected="selected"</#if>>${item.title!""}</option>
                    </#list>
                </#if>
            </select>
        </div>
        <span class="Validform_checktip">仅对产品券有效</span>
    </dd>
  </dl>
  <dl>
  
    <dt>所属门店</dt>
    <dd>
        <div class="rule-single-select">
            <select name="diyId" id="diySiteId" <#if coupon??>readOnly="true"</#if>>
            	<option value="">请选择...</option>
            	<#if siteList??>
	            	<#list siteList as item>
	                	<option value="${item.id?c!""}" <#if coupon?? && coupon.diySiteTital?? && coupon.diySiteTital==item.title>selected="selected"</#if>>${item.title!""}</option>
	                </#list>
				</#if>
            </select>
        </div>
    </dd>
  </dl>
  <dl>
    <dt>现金券名称</dt>
    <dd>
      <input name="typeTitle" type="text" value="<#if coupon??&&coupon.typeTitle??>${coupon.typeTitle!''}</#if>" class="input txt100" datatype="*2-255" sucmsg=" " <#if coupon??>readOnly="true"</#if>>
      <span class="Validform_checktip">*</span>
    </dd>
  </dl>
  <dl>
    <dt>现金券金额</dt>
    <dd>
      <input name="price" type="text" value="<#if coupon??&&coupon.price??>${coupon.price?string("0.00")}<#else>0.00</#if>" class="input txt100" datatype="/^\d{0,8}\.{0,1}(\d{1,2})?$/" sucmsg=" " <#if coupon??>readOnly="true"</#if>>
      <span class="Validform_checktip">*仅现金券设置</span>
    </dd>
  </dl>
  <dl>
        <dt>到期时间</dt>
        <dd>
            <div class="input-date">
                <input name="expireTime" type="text" value="<#if coupon??>${coupon.expireTime!""}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" " <#if coupon??>readOnly="true"</#if>>
                <i>日期</i>
            </div>
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
  <dl>
    <dt>剩余数量</dt>
    <dd>
      <input name="leftNumber" type="text" value="<#if coupon??&&coupon.leftNumber??>${coupon.leftNumber?c!""}<#else>1</#if>" class="input small" datatype="n" sucmsg=" " <#if coupon??>readOnly="true"</#if>>
      <span class="Validform_checktip">*可用数量</span>
    </dd>
  </dl>
  <#if cou_goods??>
        <dl>
            <dt>指定产品</dt>
            <dd>
              <input name="goodsId" type="hidden" value="${cou_goods.id?c}"  <#if coupon??>readOnly="true"</#if>>
              <input  type="text" value="${cou_goods.title!''}" name="goodsName" class="input text" <#if coupon??>readOnly="true"</#if>>
              <span class="Validform_checktip">*仅相对于指定商品券和产品券</span>
            </dd>
         </dl>
  </#if>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if coupon??>${coupon.sortId!""}<#else>99</#if>" class="input small" datatype="n" sucmsg=" " errormsg="请输入不超过100的2位小数" <#if coupon??>readOnly="true"</#if>>
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  
  <dl id="search" style="display:none;">
    <dt>产品关键字</dt>
    <dd>
      <input  type="text" value="" id="keywords" class="input text"  onblur="javascript:searchGoods();" <#if coupon??>readOnly="true"</#if>>
      <span class="Validform_checktip">*仅相对于指定商品券和产品券</span>
    </dd>
  </dl>
<div id="goodsDiv" >
    
</div>
</div>
<!--/内容-->
<script type="text/javascript">
function searchGoods()
{
	//查询条件增加公司id 城市id
    var keywords = $("#keywords").val();
    var brandId = $("#brandId").val();
    var cityId= $("#cityId").val();
    $.ajax({
        type : "post",
        url : "/Verwalter/coupon/search",
        data : {"keywords":keywords,"brandId":brandId,"cityId":cityId},
        success:function(res)
        {
            $("#goodsDiv").html(res);
        }　
    })
}

</script>

<!--工具栏-->
<div class="page-footer">
  <div class="btn-list">
  	<#if !coupon??>
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    </#if>
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
  <div class="clear"></div>
</div>
<!--/工具栏-->
</form>


</body></html>
