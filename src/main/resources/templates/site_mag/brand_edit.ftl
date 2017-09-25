<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑品牌</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
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

    $("#txtImgUrl").blur(function () {
        var txtPic = $("#txtImgUrl").val();
        if (txtPic == "" || txtPic == null) {
            $(".thumb_ImgUrl_show").hide();
        }
        else {
            $(".thumb_ImgUrl_show").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
            $(".thumb_ImgUrl_show").show();
        }
    });
    
    //设置封面图片的样式
    $(".photo-list ul li .img-box img").each(function () {
        if ($(this).attr("src") == $("#hidFocusPhoto").val()) {
            $(this).parent().addClass("selected");
        }
    });
    
    // 选择类型后修改ajaxurl
    $("#proCatId").change(function(){
        var url = "/Verwalter/brand/check?catId=" + $(this).val() + "<#if brand??>&id=${brand.id?c}</#if>";
        $("#idBrandTitle").attr("ajaxurl", url);
    });
});
</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/brand/save" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input name="menuId" type="text" value='${mid!""}' style="display:none;">
<input name="channelId" type="text" value='${cid!""}' style="display:none">
<input name="id" type="text" value='<#if brand??>${brand.id?c!""}</#if>' style="display:none">
    <!--导航栏-->
    <div class="location">
        <a href="/Verwalter/brand/list" class="back"><i></i><span>
            返回列表页</span></a> 
        <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
        <i class="arrow"></i>
        <a href="/Verwalter/brand/list"><span>
            品牌管理</span></a> <i class="arrow"></i><span>编辑品牌</span>
    </div>
    <div class="line10">
    </div>
    <!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap">
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">基本信息</a></li>
                    <#--<li><a href="javascript:;" onclick="tabs(this);" class="">SEO选项</a></li>-->
                </ul>
            </div>
        </div>
    </div>
    <div class="tab-content" style="display: block;">
        <#--
        <dl>
            <dt>所属商品类别</dt>
            <dd>
                <div class="rule-single-select">
                    <select id="proCatId" name="productCategoryId" datatype="*" sucmsg=" " nullmsg="请选择！">
                    	<#if !brand?? || !brand.productCategoryId??>
                    	<option value="">请选择类别...</option>
                    	</#if>
                        <#if category_list??>
                            <#list category_list as c>
                                <option value="${c.id?c!""}" <#if brand?? && brand.productCategoryId?? && brand.productCategoryId==c.id>selected="selected"</#if>><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>显示状态</dt>
            <dd>
                <div class="rule-multi-radio">
                    <span>
                        <input type="radio" datatype="n" name="statusId" value="0" <#if brand?? && brand.statusId==0>checked="checked"</#if>><label>不显示</label>
                        <input type="radio" datatype="n" name="statusId" value="1" <#if brand?? && brand.statusId==1>checked="checked"</#if>><label>显示</label>
                    </span>
                </div>
            </dd>
        </dl>
        -->
        <dl>
            <dt>品牌名称</dt>
            <dd>
                <input id="idBrandTitle" name="title" type="text" datatype="*" value="<#if brand??>${brand.title!''}</#if>" ajaxurl="/Verwalter/brand/check/0<#if brand??>?id=${brand.id?c}</#if>" class="input normal" sucmsg=" " />
                <span class="Validform_checktip">*标题最多100个字符</span>
            </dd>
        </dl>
        <#--
        <dl>
            <dt>品牌图片</dt>
            <dd>
                <input name="logoUri" type="text" id="txtImgUrl" value="<#if brand??>${brand.logoUri!""}</#if>" class="input normal upload-path">
                <div class="upload-box upload-img"></div>
                <div class="photo-list thumb_ImgUrl_show" style="display: none;">
                    <ul>
                        <li>
                            <div class="img-box1"></div>
                        </li>
                    </ul>
                </div>
            </dd>
        </dl>
        -->
        <dl>
            <dt>排序数字</dt>
            <dd>
                <input name="sortId" type="text" value="<#if brand??>${brand.sortId!""}<#else>99</#if>" class="input txt100" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" ">
                <span class="Validform_checktip">*数字，越小越向前</span>
            </dd>
        </dl>
        <#--
        <dl>
            <dt>浏览次数</dt>
            <dd>
                <input name="totalViews" type="text" value="0" value="<#if brand??>${brand.totalViews!""}</#if>" class="input txt100" datatype="n" sucmsg=" ">
                <span class="Validform_checktip">点击浏览该信息自动+1</span>
            </dd>
        </dl>
        <dl>
            <dt>发布时间</dt>
            <dd>
                <div class="input-date">
                    <input name="createTime" type="text" value="<#if brand??>${brand.createTime!""}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip">不选择默认当前发布时间</span>
            </dd>
        </dl>
        -->
        <dl>
            <dt>别名</dt>
            <dd>
                <input name="shortName" type="text" value="<#if brand??>${brand.shortName!""}</#if>" class="input normal" datatype="*" ajaxurl="/Verwalter/brand/check/1<#if brand??>?id=${brand.id?c}</#if>" sucmsg=" ">
                <span class="Validform_checktip">*别名访问，非必填，不可重复</span>
            </dd>
        </dl>
        <#--
        <dl>
            <dt>URL链接</dt>
            <dd>
                <input name="linkUri" type="text" value="<#if brand??>${brand.linkUri!""}</#if>" maxlength="255" class="input normal">
                <span class="Validform_checktip">填写后直接跳转到该网址</span>
            </dd>
        </dl>
    </div>
    <div class="tab-content" style="display: none;">
        <dl>
            <dt>SEO标题</dt>
            <dd>
                <input name="seoTitle" type="text" maxlength="255" value="<#if brand??>${brand.seoTitle!""}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
                <span class="Validform_checktip">255个字符以内</span>
            </dd>
        </dl>
        <dl>
            <dt>SEO关健字</dt>
            <dd>
                <textarea name="seoKeywords" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if brand??>${brand.seoKeywords!""}</#if></textarea>
                <span class="Validform_checktip">以“,”逗号区分开，255个字符以内</span>
            </dd>
        </dl>
        <dl>
            <dt>SEO描述</dt>
            <dd>
                <textarea name="seoDescription" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if brand??>${brand.seoDescription!""}</#if></textarea>
                <span class="Validform_checktip">255个字符以内</span>
            </dd>
        </dl>
        -->
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


</body></html>