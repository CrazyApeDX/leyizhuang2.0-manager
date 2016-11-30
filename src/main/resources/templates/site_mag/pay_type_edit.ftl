<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑支付方式</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
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
    });
</script>
</head>
<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/order/setting/pay/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="payTypeId" type="text" value='<#if pay_type??>${pay_type.id}</#if>' style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/order/setting/pay/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/order/setting/pay/list"><span>支付方式</span></a>
  <i class="arrow"></i>
  <span>编辑支付方式</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑支付方式</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dt>支付名称</dt>
    <dd><input name="title" type="text" value="<#if pay_type??>${pay_type.title!""}</#if>" class="input normal" datatype="*2-100" sucmsg=" "> 
        <span class="Validform_checktip">*</span>
    </dd>
  </dl>
  <dl>
    <dt>支付类型</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span style="display: none;">
            <input type="radio" name="isOnlinePay" value="1" <#if pay_type?? && pay_type.isOnlinePay?? && pay_type.isOnlinePay>checked="checked"</#if>>
            <label>线上付款</label>
            <input type="radio" name="isOnlinePay" value="0" <#if !pay_type?? || !pay_type.isOnlinePay?? || !pay_type.isOnlinePay>checked="checked"</#if>>
            <label>线下付款</label>
        </span>
      </div>
      <span class="Validform_checktip">*线上付款自动验证付款状态</span>
    </dd>
  </dl>
  <dl>
    <dt>是否启用</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span id="rblStatus" style="display: none;">
            <input type="radio" name="isEnable" value="1" <#if pay_type?? && pay_type.isEnable?? && pay_type.isEnable>checked="checked"</#if>>
            <label>是</label>
            <input type="radio" name="isEnable" value="0" <#if !pay_type?? || !pay_type.isEnable?? || !pay_type.isEnable>checked="checked"</#if>>
            <label>否</label>
      </div>
      <span class="Validform_checktip">*不启用则不显示该支付方式</span>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if pay_type??>${pay_type.sortId!""}</#if>" class="input small" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  <dl>
    <dt>手续费类型</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
          <span style="display: none;">
            <input type="radio" name="isFeeCountByPecentage" value="1" <#if pay_type?? && pay_type.isFeeCountByPecentage?? && pay_type.isFeeCountByPecentage>checked="checked"</#if>>
            <label>百分比</label>
            <input type="radio" name="isFeeCountByPecentage" value="0" <#if !pay_type?? || !pay_type.isFeeCountByPecentage?? || !pay_type.isFeeCountByPecentage>checked="checked"</#if>>
            <label>固定金额</label>
          </span>
      </div>
      <span class="Validform_checktip">*百分比的计算公式：商品总金额+(商品总金额*百分比)+配送费用=订单总金额</span>
    </dd>
  </dl>
  <dl>
    <dt>支付手续费</dt>
    <dd>
      <input name="fee" type="text" value="<#if pay_type??>${pay_type.fee!""}</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
      <span class="Validform_checktip">*注意：百分比取值范围：0-100，固定金额单位为“元”</span>
    </dd>
  </dl>
  
  <dl>
    <dt>显示图标</dt>
    <dd>
      <input name="coverImageUri" type="text" value="<#if pay_type??>${pay_type.coverImageUri!""}</#if>" class="input normal upload-path">
      <div class="upload-box upload-img">
      </div>
    </dd>
  </dl>
  <dl>
    <dt>描述说明</dt>
    <dd>
      <textarea name="info" rows="2" cols="20" class="input normal"><#if pay_type??>${pay_type.info}</#if></textarea>
      <span class="Validform_checktip">支付方式的描述说明，支持HTML代码</span>
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