<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统设置</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/kindeditor-min.js"></script>
<link href="/mag/style/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
<script type="text/javascript">
$(function () {
    <#if status??>
        alert("修改系统配置成功");
    </#if>

    //初始化表单验证
    $("#form1").initValidform();
    
    //初始化编辑器
    var editor = KindEditor.create('.editor', {
        width: '98%',
        height: '350px',
        resizeType: 1,
        uploadJson: '/Verwalter/editor/upload?action=EditorFile',
        fileManagerJson: '/Verwalter/editor/upload?action=EditorFile',
        allowFileManager: true
    });
    
    //初始化上传控件
    $(".upload-img").each(function () {
        $(this).InitSWFUpload({ 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf"
        });
    });
    
    //初始化上传控件
    $(".upload-apk").each(function () {
        $(this).InitSWFUpload({ 
            sendurl: "/Verwalter/uploadAPk/upload", 
            flashurl: "/mag/js/swfupload.swf"
        });
    });
    //初始化上传控件
    $(".upload-qrCode").each(function () {
        $(this).InitSWFUpload({ 
            sendurl: "/Verwalter/upload/QRCode", 
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
    
    var wxPic = $("#wxImgUrl").val();
    if (wxPic == "" || wxPic == null) {
        $(".thumb_wxImgUrl_show").hide();
    }
    else {
        $(".thumb_wxImgUrl_show").html("<ul><li><div class='img-box1'><img src='" + wxPic + "' bigsrc='" + wxPic + "' /></div></li></ul>");
        $(".thumb_wxImgUrl_show").show();
    }
    
    var iOsPic = $("#iOsImgUrl").val();
    if (iOsPic == "" || iOsPic == null) {
        $(".thumb_iOsImgUrl_show").hide();
    }
    else {
        $(".thumb_iOsImgUrl_show").html("<ul><li><div class='img-box1'><img src='" + iOsPic + "' bigsrc='" + iOsPic + "' /></div></li></ul>");
        $(".thumb_iOsImgUrl_show").show();
    }
    
    var androidPic = $("#androidImgUrl").val();
    if (androidPic == "" || androidPic == null) {
        $(".thumb_androidImgUrl_show").hide();
    }
    else {
        $(".thumb_androidImgUrl_show").html("<ul><li><div class='img-box1'><img src='" + androidPic + "' bigsrc='" + androidPic + "' /></div></li></ul>");
        $(".thumb_androidImgUrl_show").show();
    }
});
</script>
</head>
<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form method="post" action="/Verwalter/setting/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
<input type="hidden" name="id" value="<#if setting??>${setting.id!""}</#if>" >
</div>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>系统管理</span>
  <i class="arrow"></i>
  <span>系统设置</span>
</div>
<div class="line10"></div>
<!--导航栏-->
<!--内容-->
<div class="content-tab-wrap">
    <div id="floatHead" class="content-tab">
        <div class="content-tab-ul-wrap" >
            <ul>
                <li><a href="javascript:;" onclick="tabs(this);" class="selected">网站基本信息</a></li>
                <li><a href="javascript:;" onclick="tabs(this);">功能权限设置</a></li>
                <li><a href="javascript:;" onclick="tabs(this);">网站奖励设置</a></li>
                <li><a href="javascript:;" onclick="tabs(this);">产品卷使用说明</a></li>
                <li><a href="javascript:;" onclick="tabs(this);">现金卷使用说明</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="tab-content" style="display: block;">
    <dl>
        <dt>网站名称</dt>
        <dd>
            <input name="title" type="text" value="<#if setting??>${setting.title!""}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>网站域名</dt>
        <dd>
            <input name="domainName" type="text" value="<#if setting??>${setting.domainName!""}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>网站LOGO</dt>
        <dd>
            <input id="txtImgUrl" name="logoUri" type="text" datatype="*0-255" value="<#if setting??>${setting.logoUri!""}</#if>" class="input normal upload-path">
            <div class="upload-box upload-img"></div>
            <span class="Validform_checktip"></span>
            <div class="photo-list thumb_ImgUrl_show">
            </div>
        </dd>
    </dl>
    <dl>
        <dt>微信二维码</dt>
        <dd>
            <input id="wxImgUrl" name="wxQrCode" type="text" datatype="*0-255" value="<#if setting??>${setting.wxQrCode!""}</#if>" class="input normal upload-path">
            <div class="upload-box upload-img"></div>
            <span class="Validform_checktip"></span>
            <div class="photo-list thumb_ImgUrl_show thumb_wxImgUrl_show"></div>
        </dd>
    </dl>
    <dl>
        <dt>苹果App二维码</dt>
        <dd>
            <input id="iOsImgUrl" name="iOsQrCode" type="text" datatype="*0-255" value="<#if setting??>${setting.iOsQrCode!""}</#if>" class="input normal upload-path">
            <div class="upload-box upload-img"></div>
            <span class="Validform_checktip"></span>
            <div class="photo-list thumb_ImgUrl_show thumb_iOsImgUrl_show"></div>
        </dd>
    </dl>
    <dl>
        <dt>安卓App二维码</dt>
        <dd>
            <input id="androidImgUrl" name="androidQrCode" type="text" datatype="*0-255" value="<#if setting??>${setting.androidQrCode!""}</#if>" class="input normal upload-path">
            <div class="upload-box upload-img"></div>
            <span class="Validform_checktip"></span>
            <div class="photo-list thumb_ImgUrl_show thumb_androidImgUrl_show"></div>
        </dd>
    </dl>
    <dl>
        <dt>公司名称</dt>
        <dd>
            <input name="company" type="text" value="<#if setting??>${setting.company!""}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>通讯地址</dt>
        <dd>
            <input name="address" type="text" value="<#if setting??>${setting.address!""}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>接口通知电话</dt>
        <dd>
            <input name="infPhone" type="text" value="<#if setting??>${setting.infPhone!""}</#if>" class="input normal" datatype="/^1\d{10}$/" sucmsg=" "</input>
            <span class="Validform_chektip">为空代表不发送</span>
        </dd>
    </dl>
    <dl>
        <dt>ebs接口传输开始时间</dt>
            <dd>
                <div class="input-date">
                    <input name="startDate" id="beginDate" type="text" value="<#if setting?? && setting.startDate??>${setting.startDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i></i>
                </div>
                <span class="Validform_checktip">为空代表可传输</span>
            </dd>
        </dl>
        <dl>
            <dt>ebs接口传输结束时间</dt>
            <dd>
                <div class="input-date">
                    <input name="endDate" id="finishDate" type="text" value="<#if setting?? && setting.endDate??>${setting.endDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>为空代表可传输</i>
                </div>
                <span class="Validform_checktip">为空代表可传输</span>
            </dd>
        </dl>
    <dl>
        <dt>客服电话</dt>
        <dd>
            <input name="telephone" type="text" value="<#if setting??>${setting.telephone!""}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>传真号码</dt>
        <dd>
            <input name="fax" type="text" value="<#if setting??>${setting.fax!""}</#if>" class="input normal" datatype="n0-100|-" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>在线客服QQ</dt>
        <dd>
            <input name="qq" type="text" value="<#if setting??>${setting.qq!""}</#if>" class="input normal" datatype="n0-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>管理员邮箱</dt>
        <dd>
            <input name="adminEmail" type="text" value="<#if setting??>${setting.adminEmail!""}</#if>" class="input normal" datatype="e" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>网站备案号</dt>
        <dd>
            <input name="icpNumber" type="text" value="<#if setting??>${setting.icpNumber!""}</#if>" class="input normal" datatype="n0-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>首页标题(SEO)</dt>
        <dd>
            <textarea name="titleSeo" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if setting??>${setting.titleSeo!""}</#if></textarea>
            <span class="Validform_checktip">*自定义的首页标题</span>
        </dd>
    </dl>
    <dl>
        <dt>页面关键词(SEO)</dt>
        <dd>
            <textarea name="seoKeywords" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if setting??>${setting.seoKeywords!""}</#if></textarea>
            <span class="Validform_checktip">*自定义的首页标题</span>
        </dd>
    </dl>
    <dl>
        <dt>页面描述(SEO)</dt>
        <dd>
            <textarea name="seoDescription" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if setting??>${setting.seoDescription!""}</#if></textarea>
            <span class="Validform_checktip">*自定义的首页标题</span>
        </dd>
    </dl>
    <dl>
        <dt>网站版权信息</dt>
        <dd>
            <textarea name="copyright" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=" "><#if setting??>${setting.copyright!""}</#if></textarea>
            <span class="Validform_checktip">支持HTML</span>
        </dd>
    </dl>
    <dl>
        <dt>订单失效时间</dt>
        <dd>
            <input name="cancelTime" rows="2" cols="6" class="input" datatype="*0-255" sucmsg=" " value="<#if setting??&&setting.cancelTime??>${setting.cancelTime?c}<#else>0</#if>" />&nbsp;&nbsp;分钟
        </dd>
    </dl>
    <dl>
        <dt>最大收货地址数量</dt>
        <dd>
            <input name="maxShipping" rows="2" cols="6" class="input" datatype="*0-255" sucmsg=" " value="<#if setting??&&setting.maxShipping??>${setting.maxShipping?c}<#else>0</#if>" />&nbsp;&nbsp;
        </dd>
    </dl>
    <dl>
        <dt>最小运费额</dt>
        <dd>
            <input name="minDeliveryFee" rows="2" cols="6" class="input" datatype="n" sucmsg=" " value="<#if setting??&&setting.minDeliveryFee??>${setting.minDeliveryFee?string("0.00")}<#else>0.00</#if>" />&nbsp;&nbsp;
        </dd>
    </dl>
    <dl>
        <dt>最大运费额</dt>
        <dd>
            <input name="maxDeliveryFee" rows="2" cols="6" class="input" datatype="n" sucmsg=" " value="<#if setting??&&setting.maxDeliveryFee??>${setting.maxDeliveryFee?string("0.00")}<#else>0.00</#if>" />&nbsp;&nbsp;
        </dd>
    </dl>
    <dl>
        <dt>上传APK</dt>
        <dd>
            <input name="apk" type="text" id="txtImgUrl" value="<#if setting??&&setting.apk??>${setting.apk!''}</#if>" style="width:50%;" class="input normal upload-path">
            <div class="upload-box upload-apk"></div>
        </dd>
    </dl>
    <dl>
        <dt>上传APK下载二维码</dt>
        <dd>
            <input name="apkQrCode" type="text" id="txtImgUrl" value="<#if setting??&&setting.apkQrCode??>${setting.apkQrCode!''}</#if>" style="width:50%;" class="input normal upload-path">
            <div class="upload-box upload-qrCode"></div>
           <#-- <span class="Validform_checktip"></span>
            <div class="photo-list thumb_ImgUrl_show thumb_androidImgUrl_show"></div>-->
            <div class="img"><img src="<#if setting??&&setting.apkQrCode??>${setting.apkQrCode!''}</#if>" alt="产品图片" style="width:100px;higth:100px;"></div>
        </dd>
    </dl>
</div>
<div class="tab-content" style="display: none;">
    <dl>
        <dt>开启触屏网站</dt>
        <dd>
            <div class="rule-multi-radio">
                <span>
                    <input type="radio" name="isTouchEnable" value="1" <#if setting?? && setting.isTouchEnable?? && setting.isTouchEnable==true>checked="checked"</#if> >
                    <label>是</label>
                    <input type="radio" name="isTouchEnable" value="0" <#if setting??==false || setting.isTouchEnable??==false || setting.isTouchEnable==false>checked="checked"</#if>>
                    <label>否</label>
                </span>
            </div>
        </dd>
    </dl>
    <dl>
        <dt>触屏网站域名</dt>
        <dd>
            <input name="touchUri" type="text" value="<#if setting??>${setting.touchUri!""}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>开启评论审核</dt>
        <dd>
            <div class="rule-multi-radio">
                <span>
                    <input type="radio" name="isCommentVerify" value="1" <#if setting?? && setting.isCommentVerify?? && setting.isCommentVerify>checked="checked"</#if> >
                    <label>是</label>
                    <input type="radio" name="isCommentVerify" value="0" <#if setting??==false || setting.isCommentVerify??==false || !setting.isCommentVerify>checked="checked"</#if>>
                    <label>否</label>
                </span>
            </div>
        </dd>
    </dl>
    <dl>
        <dt>开启管理员日志</dt>
        <dd>
            <div class="rule-multi-radio">
                <span>
                    <input type="radio" name="isEnableLog" value="1" <#if setting?? && setting.isEnableLog?? && setting.isEnableLog>checked="checked"</#if> >
                    <label>是</label>
                    <input type="radio" name="isEnableLog" value="0" <#if setting??==false || setting.isEnableLog??==false || !setting.isEnableLog>checked="checked"</#if>>
                    <label>否</label>
                </span>
            </div>
        </dd>
    </dl>
</div> 
<div class="tab-content" style="display: none;">
 <#-->   <dl>
        <dt>注册成功奖励积分</dt>
        <dd>
            <input name="registerSuccessPoints" type="text" value="<#if setting??>${setting.registerSuccessPoints!"50"}<#else>50</#if>" class="input normal" datatype="n" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>分享注册奖励积分</dt>
        <dd>
            <input name="registerSharePoints" type="text" value="<#if setting??>${setting.registerSharePoints!"20"}<#else>20</#if>" class="input normal" datatype="n" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>分享商品奖励积分</dt>
        <dd>
            <input name="goodsSharePoints" type="text" value="<#if setting??>${setting.goodsSharePoints!"10"}<#else>10</#if>" class="input normal" datatype="n" sucmsg=" ">
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
        <dt>商品分享每日积分限额</dt>
        <dd>
            <input name="goodsShareLimits" type="text" value="<#if setting??>${setting.goodsShareLimits!"50"}<#else>50</#if>" class="input normal" datatype="n" sucmsg=" ">
            <span class="Validform_checktip">同一天通过商品分享获得的积分超过该值将不再奖励积分</span>
        </dd>
    </dl>
    
    <dl>
            <dt>分销商返利比例</dt>
            <dd>
                <input name="returnRation" type="text" value="<#if setting??>${setting.returnRation!"0"}<#else>0</#if>" class="input normal"  sucmsg=" ">
                <span class="Validform_checktip">填写小数(返利计算为订单总金额*返利比例)</span>
            </dd>
        </dl> -->
</div>    
<div class="tab-content" style="display: none;">
    <dl>
        <dt>产品卷使用说明</dt>
        <dd>
            <textarea name="goodsCouponGuide" class="editor"><#if setting??>${setting.goodsCouponGuide!""}</#if></textarea>
        </dd>
    </dl>
</div>
<div class="tab-content" style="display: none;">
    <dl>
        <dt>现金卷使用说明</dt>
        <dd>
            <textarea name="cashCouponGuide" class="editor"><#if setting??>${setting.cashCouponGuide!""}</#if></textarea>
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