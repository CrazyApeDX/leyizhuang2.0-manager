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
<form name="form_user" method="post" action="/Verwalter/user/balance/change/save" id="form">
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
      	<li><a href="javascript:;" onclick="tabs(this);" class="selected">账户信息</a></li>
      </ul>
    </div>
  </div>
</div>

<!--账户信息-->
<div class="tab-content">
	
	<dl>
    <dt>变更类型</dt>
    <dd>
        <select name="changeType" id="changeType" datatype="n">
            <option value="">请选择变更类型</option>
            <option value="1">公司刷POS充值</option>
            <option value="2">网银转账充值</option>
            <option value="3">交现金充值</option>
            <option value="4">线上支付宝充值失败</option>
            <option value="5">线上微信充值失败</option>
            <option value="6">线上银联充值失败</option>
            <option value="7">取消订单退支付宝第三方支付</option>
            <option value="8">取消订单退微信第三方支付</option>
            <option value="9">取消订单退银联第三方支付</option>
            <option value="10">装饰公司信用额度充值</option>
        </select>
    </dd>
  </dl>
	
	<dl>
		<dt>用户名：</dt>
		<dd>
			<span>${username!''}（${realName!''}）</span>
			<input type="hidden" value="${username!''}" name="username">
		<dd>
	</dl>
<dl>
	<dt>可提现余额</dt>
	<dd><input name="cashBalance" id="cashBalance"  type="number" value="0.00" class="input normal"></dd>
</dl>
<dl>
	<dt>不可提现余额</dt>
	<dd><input name="unCashBalance" id="unCashBalance" type="number" value="0.00" class="input normal"></dd>
</dl>
<dl>
    <dt>商户订单号</dt>
    <dd><input name="userOrderNumber" type="text" value="${userOrderNumber!""}" class="input normal"></dd>
</dl>

<dl>
    <dt>到账时间</dt>
    <dd>
      <div class="input-date">
        <input name="transferTime" type="text" value="" class="input date" onfocus="WdatePicker({dateFmt:&#39;yyyy-MM-dd&#39;})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
        <i>日期</i>
      </div>
    </dd>
</dl>  
<dl>
	<dt>备注</dt>
	<dd>
		<textarea id="remark" style="resize:none;" name="remark" rows="2" cols="20" class="input"></textarea>
		<span class="Validform_checktip">255个字符以内</span>
	</dd>
</dl>
<dl>
	<dt>管理员密码</dt>
	<dd><input name="password" id="password" type="password" class="input normal"></dd>
</dl>
</div> 

<!--工具栏-->
<div class="page-footer">
  <div class="btn-list">
    <input type="button" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" onclick="javascript:validate();">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
  <div class="clear"></div>
</div>
<!--/工具栏-->

</form>

<script type="text/javascript">
	var validate = function() {
	
		var cashBalance = $("#cashBalance").val();
		var unCashBalance = $("#unCashBalance").val();
	
		if (Number(cashBalance) + Number(<#if cashBalance??>${cashBalance?string("0.00")}<#else>0.00</#if>) < 0) {
			alert("用户可提现预存款不足以扣减");
			return;
		}
		
		if (Number(unCashBalance) + Number(<#if unCashBalance??>${unCashBalance?string("0.00")}<#else>0.00</#if>) < 0) {
			alert("用户不可提现预存款不足以扣减");
			return;
		}
	
		$("#btnSubmit").removeAttr("onclick");
		var remark = $("#remark").val();
		var password = $('#password').val();
		var changeType = $('#changeType').val();
		if (!password) {
			$("#btnSubmit").attr("onclick","javascript:validate();");
			alert('请输入管理员密码');
			return;
		}else if (remark.length > 255) {
			$("#btnSubmit").attr("onclick","javascript:validate();");
			alert('备注的长度不能超过255个字符');
			return;
		}else if(!changeType){
			$("#btnSubmit").attr("onclick","javascript:validate();");
			alert('请选择变更类型');
			return;
		}else {
			$.ajax({
				url : '/Verwalter/user/balance/change/manager/check',
				type : 'POST',
				data : {
					'username' : ${username!''},
					'password' : password
				},
				error : function() {
					$("#btnSubmit").attr("onclick","javascript:validate();");
					alert('网络错误，请稍后重试');
				},
				success : function(res) {
					if (-1 === Number(res.status)) {
						$("#btnSubmit").attr("onclick","javascript:validate();");
						alert(res.message);
					} else {
						$('#form').submit();
					}
				}
			});
		}
	}
</script>
</body>

</html>