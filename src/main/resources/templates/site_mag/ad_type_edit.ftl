<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑广告位</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();
});
</script>
</head>
<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form method="post" action="/Verwalter/ad/type/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
<input type="hidden" name="id" value="<#if ad_type??>${ad_type.id!""}</#if>" >
</div>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="/Verwalter/ad/type/list"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>广告位管理</span>
  <i class="arrow"></i>
  <span>编辑广告位</span>
</div>
<div class="line10"></div>
<!--导航栏-->
<!--内容-->
<div class="content-tab-wrap">
    <div id="floatHead" class="content-tab">
        <div class="content-tab-ul-wrap" >
            <ul>
                <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑广告位</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="tab-content" style="display: block;">
    <dl>
        <dt>名称</dt>
        <dd>
            <input name="title" type="text" value="<#if ad_type??>${ad_type.title!""}</#if>" class="input normal" datatype="s" sucmsg=" ">
            <span class="Validform_checktip">*广告位名称</span>
        </dd>
    </dl>
    <dl>
        <dt>打开位置</dt>
        <dd>
            <div class="rule-multi-radio multi-radio">
                <span>
                    <input type="radio" name="isNewWindow" value="1" <#if ad_type?? && ad_type.isNewWindow>checked="checked"</#if>>
                    <label>新窗口</label>
                    <input type="radio" name="isNewWindow" value="0" <#if !ad_type?? || !ad_type.isNewWindow>checked="checked"</#if>>
                    <label>原窗口</label>
                </span>
            </div>
        </dd>
    </dl>
    <dl>
        <dt>显示数量</dt>
        <dd>
            <input name="totalShows" type="text" value="<#if ad_type??>${ad_type.totalShows!""}</#if>" class="input txt100" datatype="n1-10" sucmsg=" ">
            <span class="Validform_checktip">*显示的广告数量</span>
        </dd>
    </dl>
    <dl>
        <dt>价格</dt>
        <dd>
            <input name="price" type="text" value="<#if ad_type??>${ad_type.price!''}</#if>" class="input txt100" datatype="n0-100|." sucmsg=" ">
            <span class="Validform_checktip">元/月</span>
        </dd>
    </dl>
    <dl>
        <dt>宽度</dt>
        <dd>
            <input name="width" type="text" value="<#if ad_type??>${ad_type.width!''}</#if>" class="input txt100" datatype="n1-100" sucmsg=" ">
            <span class="Validform_checktip">*广告宽度</span>
        </dd>
    </dl>
    <dl>
        <dt>高度</dt>
        <dd>
            <input name="heigth" type="text" value="<#if ad_type??>${ad_type.heigth!''}</#if>" class="input txt100" datatype="n1-100" sucmsg=" ">
            <span class="Validform_checktip">*广告高度</span>
        </dd>
    </dl>
    <dl>
        <dt>排序数字</dt>
        <dd>
            <input name="sortId" type="text" value="<#if ad_type??>${ad_type.sortId!""}<#else>99</#if>" class="input txt100" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
            <span class="Validform_checktip">*数字，越小越向前</span>
        </dd>
    </dl>
    <dl>
        <dt>备注</dt>
        <dd>
            <textarea name="mark" rows="2" cols="20" class="input" datatype="*0-255" sucmsg=""><#if ad_type??>${ad_type.mark!""}</#if></textarea>
            <span class="Validform_checktip">255个字符以内</span>
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