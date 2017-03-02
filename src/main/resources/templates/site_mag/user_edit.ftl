<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑会员信息</title>
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
    
    $("#btnEditRemark").click(function () { EditOrderRemark(); });    //修改积分备注 
    
    $("#cityId").change(function(){
    var cid = $("#cityId").val();
        $.ajax({
		url: "/Verwalter/user/change_city", 
		type: "post",
		dataType: "json",
		data: {"cid": cid},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
                },
		success: function(data)
		{	
			 $("#upperDiySiteId").empty();
			 $("#upperDiySiteId").append("<option value=''>请选择门店</option>");
        	 $.each(data.site_list, function(i,val){
        	 $("#upperDiySiteId").append("<option value='"+val.id+"'>"+val.title+"</option>");      
             });
        	 
  		}
	});
        $("#diySiteId").css("display","block");
    });
    $("#diySiteId").change(function(){
        var diy = $("#upperDiySiteId").val();
            $.ajax({
    		url: "/Verwalter/user/change_diy", 
    		type: "post",
    		dataType: "json",
    		data: {"did": diy},
    		error: function (XMLHttpRequest, textStatus, errorThrown) {
                    },
    		success: function(data)
    		{	
    			 $("#sellerId").empty();
    			 $("#sellerId").append("<option value=''>请选择导购</option>");
            	 $.each(data.user_list, function(i,val){
            	 $("#sellerId").append("<option value='"+val.id+"'>"+val.realName+"</option>");      
                 });
            	 
      		}
    	});
            $("#sellerdl").css("display","block");
        });
	    $("input[name='userType']").click(function(){
	    	var userTypeChecked= $(this).val();
	    	if(userTypeChecked==5){
	    		$("input[name='opUser']").attr("datatype","*");
	    		$("#opUserDl").show();
	    	}else{
	    		$("input[name='opUser']").attr("datatype","");
	    		$("input[name='opUser']").attr("value","");
	    		$("input[name='opUser']").val("");
	    		$("#opUserDl").hide();
	    	}
        });
});   

 //修改粮草备注
        function EditOrderRemark() {
            var dialog = $.dialog({
                title: '修改积分备注',
                content: '<input type="checkbox" name="showtype" id="showtype" checked="checked"/><label> 仅后台显示</label> </br><textarea id="pointRemark" name="txtPointRemark" rows="2" cols="20" class="input"></textarea>',
                min: false,
                max: false,
                lock: true,
                ok: function () {
                    var showtype = $("#showtype", parent.document).is(':checked');                    
                    var remark = $("#pointRemark", parent.document).val();                   
                    if (remark == "") {
                        $.dialog.alert('对不起，请输入备注内容！', function () { }, dialog);
                        return false;
                    }
                    var userId = eval(document.getElementById("userId")).value;
                    var point = eval(document.getElementById("totalPoints")).value;
                    var postData = { "userId": userId, "totalPoints": point, "data": remark, "type":"editPoint", "isBackgroundShow": showtype};
                    //发送AJAX请求
                    sendAjaxUrl(dialog, postData, "/Verwalter/user/param/edit");
                    return false;
                },
                cancel: true
            });
        }
    //发送AJAX请求
        function sendAjaxUrl(winObj, postData, sendUrl) {
            $.ajax({
                type: "post",
                url: sendUrl,
                data: postData,
                dataType: "json",
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert('尝试发送失败，错误信息：' + errorThrown, function () { }, winObj);
                },
                success: function (data) {
                    if (data.code == 0) {
                        winObj.close();
                        $.dialog.tips(data.msg, 2, '32X32/succ.png', function () { location.reload(); }); //刷新页面
                    } else {
                        $.dialog.alert('错误提示：' + data.message, function () { }, winObj);
                    }
                }
            });
        }   
    
        function changeBalance(){
        	var ocashBalance=$('#ocashBalance').val();
        	var ounCashBalance=$('#ounCashBalance').val();
        	if(isNaN(ocashBalance)){
        		//alert('可提现预存款必须是数字!');
        		return;
        	}
			if(isNaN(ounCashBalance)){
				//alert('不可提现预存款必须是数字!');
				return;
        	}
			$('#obalance').val(parseFloat(parseFloat(ocashBalance)+parseFloat(ounCashBalance)).toFixed(2));
        }

</script>
</head>

<body class="mainbody">
<form name="form_user" method="post" action="/Verwalter/user/save" id="form_user">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
<input type="hidden" id="userId" name="userId" value="<#if user??>${user.id?c!""}</#if>" >
<input type="hidden" id="userDesc" name="userDesc" value="<#if userDesc??>${userDesc!""}</#if>" >
</div>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="/Verwalter/user/list?roleId=${roleId!""}"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>会员管理</span>
  <i class="arrow"></i>
  <span>编辑会员信息</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab" style="position: static; top: 52px;">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">基本资料</a></li>
        <li><a href="javascript:;" onclick="tabs(this);">安全设置</a></li>
        
        <li><a href="javascript:;" onclick="tabs(this);">账户信息</a></li>
        
      </ul>
    </div>
  </div>
</div>

<!--基本资料-->
<div class="tab-content">
  <dl>
    <dt>用户类型</dt>
    <dd>
      <div class="rule-multi-radio multi-radio" >
        <span>
            <input type="radio" name="userType" value="0" datatype="n" <#if user?? && user.userType?? && user.userType==0>checked="checked"</#if>><label>普通会员</label>
            <input type="radio" name="userType" value="1" datatype="n" <#if user?? && user.userType?? && user.userType==1>checked="checked"</#if>><label>销售顾问</label>
            <input type="radio" name="userType" value="2" datatype="n" <#if user?? && user.userType?? && user.userType==2>checked="checked"</#if>><label>店长</label>
            <input type="radio" name="userType" value="3" datatype="n" <#if user?? && user.userType?? && user.userType==3>checked="checked"</#if>><label>店主</label>
            <input type="radio" name="userType" value="4" datatype="n" <#if user?? && user.userType?? && user.userType==4>checked="checked"</#if>><label>区域经理</label>
        	<input type="radio" name="userType" value="5" datatype="n" <#if user?? && user.userType?? && user.userType==5>checked="checked"</#if>><label>配送员</label>
        </span>
      </div>
      <span class="Validform_checktip"></span>
    </dd>
  </dl> 
  <dl>
    <dt>用户状态</dt>
    <dd>
      <div class="rule-multi-radio">
        <span id="rblStatus">
            <input type="radio" name="isEnable" value="1" datatype="n" <#if user?? && user.isEnable?? && user.isEnable == true>checked="checked"</#if>>
            <label>正常</label>
            <input type="radio" name="isEnable" value="0" datatype="n" <#if user?? && user.isEnable?? && user.isEnable == false>checked="checked"</#if>>
            <label>禁用</label>
        </span>
      </div>
      <span class="Validform_checktip">*禁用账户无法登录</span>
    </dd>
  </dl>
  <dl>
    <dt>是否允许货到付款</dt>
    <dd>
      <div class="rule-multi-radio">
        <span id="rblStatus">
            <input type="radio" name="isCashOnDelivery" value="1" datatype="n" <#if !(user?? && user.isCashOnDelivery?? && user.isCashOnDelivery == false)>checked="checked"</#if>>
            <label>允许</label>
            <input type="radio" name="isCashOnDelivery" value="0" datatype="n" <#if user?? && user.isCashOnDelivery?? && user.isCashOnDelivery == false>checked="checked"</#if>>
            <label>不允许</label>
        </span>
      </div>
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <dl>
    <dt>身份</dt>
    <dd>
      <div class="rule-multi-radio">
        <span id="rblStatus">
            <input type="radio" name="identityType" value="0" datatype="*" <#if user??&&user.identityType??&&user.identityType==0>checked="checked"</#if>>
            <label>会员</label>
            <input type="radio" name="identityType" value="1" datatype="*" <#if user??&&user.identityType??&&user.identityType==1>checked="checked"</#if>>
            <label>非会员</label>
        </span>
      </div>
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
    <dl>
        <dt>用户名：</dt>
        <dd>
            <#if user??>
                <span>${user.username!""}</span>
            <#else>
                <input name="username" type="text" maxlength="200" class="input normal" datatype="m|/^(0|86|17951)?(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}$/"ajaxurl="/Verwalter/user/check<#if user??>?id=${user.id?c}</#if>" sucmsg=" " minlength="2">
            </#if>
       <dd>
    </dl>
  <dl>
    <dt>真实姓名</dt>
    <dd><input name="realName" type="text" value="<#if user??>${user.realName!""}</#if>" class="input normal"></dd>
  </dl>
  <dl id="opUserDl" <#if user?? && user.userType?? && user.userType==5>style="display:block;"<#else>style="display:none;"</#if>>
    <dt>用户编码</dt>
    <dd>
    	<input name="opUser" type="text" value="<#if user??>${user.opUser!""}</#if>" class="input normal" ajaxurl="/Verwalter/user/check<#if user??>?id=${user.id?c}</#if>" >
    	<span class="Validform_checktip">仅配送员填写</span>
    </dd>
  </dl>
  <#if user??>
  <#--<dl>
    <dt>所属城市</dt>
    <dd>
        <select name="cityId" id="cityId" datatype="n">
            <#if city_list??>
                <#list city_list as item>
                    <option value="${item.sobIdCity?c}" <#if user.cityId==item.sobIdCity>selected="selected"</#if> >${item.cityName!''}</option>
                </#list>
            </#if>
        </select>
    </dd>
  </dl>-->
  <dl id="diySiteId" style="display:block;">
    <dt>归属门店</dt>
    <dd>
        <select name="upperDiySiteId" id="upperDiySiteId" datatype="n">
            <#if !user??>
                <option value="">请选择门店</option>
            </#if>
            <#if site_list??>
                <#list site_list as item>
                    <option <#if user??&&user.upperDiySiteId?? && item.id==user.upperDiySiteId>selected="selected"</#if> value="${item.id?c}">${item.title!''}</option>
                </#list>
            </#if>
        </select>
    </dd>
  </dl>
  <dl id="sellerdl" style="display:block;">
    <dt>归属导购</dt>
    <dd>
        <select name="sellerId" id="sellerId" >
                <option value="">请选择导购</option>
            <#if user_list??>
                <#list user_list as item>
                    <option <#if user??&&user.sellerId?? && item.id==user.sellerId>selected="selected"</#if> value="${item.id?c}" >${item.realName!''}</option>
                </#list>
            </#if>
        </select>
    </dd>
  </dl>
  <#else>
  <dl>
    <dt>所属城市</dt>
    <dd>
        <select name="cityId" id="cityId" datatype="n">
            <#if !user??>
                <option value="">请选择城市</option>
            </#if>
            <#if city_list??>
                <#list city_list as item>
                    <option value="${item.sobIdCity?c}">${item.cityName!''}</option>
                </#list>
            </#if>
        </select>
    </dd>
  </dl>
  <dl id="diySiteId" style="display:none;">
    <dt>归属门店</dt>
    <dd>
        <select name="upperDiySiteId" id="upperDiySiteId" datatype="n">
            <#if !user??>
                <option value="">请选择门店</option>
            </#if>
            <#if site_list??>
                <#list site_list as item>
                    <option value="${item.id?c}">${item.title!''}</option>
                </#list>
            </#if>
        </select>
    </dd>
  </dl>
  <dl id="sellerdl" style="display:none;">
    <dt>归属导购</dt>
    <dd>
        <select name="sellerId" id="sellerId" >
            <#if !user??>
                <option value="">请选择导购</option>
            </#if>
            <#if user_list??>
                <#list user_list as item>
                    <option value="${item.id?c}">${item.realName!''}</option>
                </#list>
            </#if>
        </select>
    </dd>
  </dl>
  </#if>
  <dl>
    <dt>上传头像</dt>
    <dd>
        <input id="txtImgUrl" name="headImageUri" type="text" value="<#if user??>${user.headImageUri!"/client/images/per_titleimg01.png"}<#else>/client/images/per_titleimg01.png</#if>" class="input normal upload-path">
        <div class="upload-box upload-img"></div>
        <div class="photo-list thumb_ImgUrl_show">
            <ul>
                <li>
                    <div class="img-box1"></div>
                </li>
            </ul>
        </div>
    </dd>
  </dl>
  <dl>
    <dt>用户性别</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span style="display: none;">
        <input type="radio" name="sex" value="保密" <#if !user?? || !user.sex?? || user.sex=="保密">checked="checked"</#if>>
        <label>保密</label>
        <input type="radio" name="sex" value="男" <#if user?? && user.sex?? && user.sex=="男">checked="checked"</#if>>
        <label>男</label>
        <input type="radio" name="rblSex" value="女" <#if user?? && user.sex?? && user.sex=="女">checked="checked"</#if>>
        <label>女</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>出生日期</dt>
    <dd>
      <div class="input-date">
        <input name="birthdate" type="text" value="<#if user?? && user.birthday??>${user.birthday?string("yyyy-MM-dd")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:&#39;yyyy-MM-dd&#39;})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
        <i>日期</i>
      </div>
    </dd>
  </dl> 
  <dl>
    <dt>基础信用额度</dt>
    <dd>
        <input name="creditLimit" type="text" value="<#if user?? && user.creditLimit??>${user.creditLimit?string("0.00")}</#if>" class="input date" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" errormsg="请输入正确的数字" sucmsg=" ">
        <i>信用额度</i>
    </dd>
  </dl>  
  <dl>
    <dt>现有信用额度</dt>
    <dd>
        <input name="credit" type="text" value="<#if user?? && user.credit??>${user.credit?string("0.00")}</#if>" class="input date" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" errormsg="请输入正确的数字" sucmsg=" ">
        <i>信用额度</i>
    </dd>
  </dl>  
</div>
<!--/基本资料-->

<!--安全设置-->
<div class="tab-content" style="display:none;">
  <dl>
    <dt>设置新密码</dt>
    <dd><input name="oldPassword" type="password" value="" class="input normal" datatype="*6-20" ignore="ignore" nullmsg="请设置密码" errormsg="密码范围在6-20位之间" sucmsg=" " value=""> <span class="Validform_checktip">*新密码将覆盖原密码，至少6位</span></dd>
  </dl>
  <dl>
    <dt>确认密码</dt>
    <dd><input name="rePassword" type="password" value="" class="input normal" datatype="*" ignore="ignore" recheck="oldPassword" nullmsg="请再输入一次密码" errormsg="两次输入的密码不一致" sucmsg=" " value=""> <span class="Validform_checktip">*再次输入密码</span></dd>
  </dl>
  <#--<#if user??>
  	<input name="oldPassword" type="hidden" value="${user.password!''}" />
  </#if>-->
</div>
<!--/安全设置-->

<!--账户信息-->
<div class="tab-content" style="display:none;">
<#if !user?? || (user?? && user.userType?? && user.userType == 0 )>
  <dl>
    <dt>总金额</dt>
    <#-- 不可修改
    <dd><input name="obalance" id="obalance" type="text" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " value="<#if user?? && user.balance??>${user.balance?string('0.00')}<#else>0</#if>" readonly="readonly"> <span class="Validform_checktip"></span></dd>
  	-->
    <dd><div><#if user?? && user.balance??>${user.balance?string('0.00')}<#else>0</#if></div> <span class="Validform_checktip"></span></dd>
  </dl>
  <dl>
    <dt>可提现余额</dt>
    <#-- 不可修改
    <dd><input readonly="readonly" name="ocashBalance" id="ocashBalance" onkeyup="changeBalance()" type="text" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " value="<#if user?? && user.cashBalance??>${user.cashBalance?string('0.00')}<#else>0</#if>"> <span class="Validform_checktip"></span></dd>
  	-->
  	<dd><div><#if user?? && user.cashBalance??>${user.cashBalance?string('0.00')}<#else>0</#if></div> <span class="Validform_checktip"></span></dd>
  </dl>
  <dl>
    <dt>不可提现余额</dt>
    <#-- 不可修改
    <dd><input readonly="readonly" name="ounCashBalance" id="ounCashBalance" onkeyup="changeBalance()" type="text" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " value="<#if user?? && user.unCashBalance??>${user.unCashBalance?string('0.00')}<#else>0</#if>"> <span class="Validform_checktip"></span></dd>
  	-->
  	<dd><div><#if user?? && user.unCashBalance??>${user.unCashBalance?string('0.00')}<#else>0</#if></div> <span class="Validform_checktip"></span></dd>
  </dl>
  </#if>
  <#-- 没有这些字段
  <#if !user?? || user?? && user.userType?? && user.userType == 100>
  <dl>
    <dt>下级用户总数</dt>
    <dd><input name="totalLowerUsers" type="text" id="txtPay_Password" class="input normal" sucmsg=" " value="<#if user?? && user.totalLowerUsers??>${user.totalLowerUsers?c}</#if>"> <span class="Validform_checktip"></span></dd>
  </dl>
  <dl>
    <dt>用户返现金额</dt>
    <dd><input name="totalCashRewards" type="text" id="txtPay_Password" class="input normal"sucmsg=" " value="<#if user?? && user.totalCashRewards??>${user.totalCashRewards?c}</#if>"> <span class="Validform_checktip"></span></dd>
  </dl>
  <dl>
    <dt>提现冻结金额</dt>
    <dd><input name="cashRewardsFrozen" type="text" id="txtPay_Password" class="input normal"sucmsg=" " value="<#if user?? && user.cashRewardsFrozen??>${user.cashRewardsFrozen?c}</#if>"> <span class="Validform_checktip"></span></dd>
  </dl>
  </#if>
  -->
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