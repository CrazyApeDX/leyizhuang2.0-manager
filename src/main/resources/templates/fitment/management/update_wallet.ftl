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
		</script>
		</head>
		
		<body class="mainbody">
		<form name="form_user" method="post" action="/Verwalter/fitment/wallet/save" id="form">
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
		      	<li><a href="javascript:;" onclick="tabs(this);" class="selected">钱包充值</a></li>
		      </ul>
		    </div>
		  </div>
		</div>
		
		<!--账户信息-->
		<div class="tab-content">
		
			<dl>
				<dt>装饰公司名称</dt>
				<dd>
					<#if company??>${company.name!""}</#if>
				</dd>
			</dl>
				
			<dl>
				<dt>装饰公司编码</dt>
				<dd>
					<#if company??>${company.code!""}</#if>
					<input name="code" type="hidden" value="<#if company??>${company.code!""}</#if>">
				</dd>
			</dl>
			
			<dl>
				<dt>当前钱包余额</dt>
				<dd>
					<#if company??>￥${company.walletMoney?string("0.00")}</#if>
				</dd>
			</dl>
				
			<dl>
			    <dt>充值类型</dt>
			    <dd>
			        <select name="remark" id="remark" datatype="n">
			            <option value="">请选择变更类型</option>
			            <#if tdCity.cityName??&&tdCity.cityName=="成都市">
			            <option value="装饰公司预存款充值1">装饰公司预存款充值1</option>
			            <option value="装饰公司预存款充值2">装饰公司预存款充值2</option>
			            <option value="装饰公司预存款充值3">装饰公司预存款充值3</option>
			            </#if>
			            <#if tdCity.cityName??&&tdCity.cityName=="郑州市">
			            <option value="装饰公司预存款充值4">装饰公司预存款充值4</option>
			            </#if>
			        </select>
			   	 </dd>
			</dl>
			<dl>
			    <dt>充值金额</dt>
			    <dd>
			     <dd><input name="money" id="money" type="number" value="" class="input normal"></dd>
			    </dd>
			</dl>
			
		  	<dl>
			    <dt>到账时间</dt>
			    <dd>
			      <div class="input-date">
			        <input name="arrivalTime" id="arrivalTime" type="text" value="" class="input date" onfocus="WdatePicker({dateFmt:&#39;yyyy-MM-dd&#39;})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
			        <i>日期</i>
			      </div>
			    </dd>
			</dl>  
			<dl>
				<dt>备注</dt>
				<dd>
					<textarea id="writtenRemarks" style="resize:none;" name="writtenRemarks" rows="2" cols="20" class="input"></textarea>
					<span class="Validform_checktip">255个字符以内</span>
				</dd>
			</dl>
			<dl>
				<dt>管理员密码</dt>
				<dd><input name="password" id="password" type="password" class="input normal"></dd>
			</dl>
			<input type="hidden" id="count" name="count" value="">
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
		var money = $("#money").val();
		var arrivalTime = $("#arrivalTime").val();
		var remark = $("#remark").val();
		var password = $('#password').val();
		var writtenRemarks = $('#writtenRemarks').val();
		var myDate = new Date();  
		var count = $("#count").val(); 
		
		
		if (isNaN(money)) {
			$("#btnSubmit").attr("onclick","javascript:validate();");
			$.dialog.alert("请输入一个正确的数字");
			return;
		}
		if(money.indexOf(".") != -1){;
 			money = money.substr(0,money.indexOf(".")+3);
 		}
		if (money == 0) {
			$("#btnSubmit").attr("onclick","javascript:validate();");
			$.dialog.alert("请输入一个有效的数字");
			return;
		}
		$("#money").val(money);
		if(arrivalTime == ''){;
 			$("#btnSubmit").attr("onclick","javascript:validate();");
			$.dialog.alert("请输入到账时间");
			return;
 		}
		
		if (!password) {
			$("#btnSubmit").attr("onclick","javascript:validate();");
			$.dialog.alert('请输入管理员密码');
			return;
		}else if (writtenRemarks.length > 255) {
			$("#btnSubmit").attr("onclick","javascript:validate();");
			$.dialog.alert('备注的长度不能超过255个字符');
			return;
		}else if(!remark){
			$("#btnSubmit").attr("onclick","javascript:validate();");
			$.dialog.alert('请选择变更类型');
			return;
		}else if(count == 1){
			$.dialog.alert('不能重复提交');
			return;
		}else {
			$("#count").val(1);
			$.ajax({
				url : '/Verwalter/fitment/wallet/manager/check',
				type : 'POST',
				data : {
					'id' : ${company.id},
					'password' : password
				},
				error : function() {
					$("#btnSubmit").attr("onclick","javascript:validate();");
					$("#count").val("");
					alert('网络错误，请稍后重试');
				},
				success : function(res) {
					if (-1 === Number(res.status)) {
						$("#btnSubmit").attr("onclick","javascript:validate();");
						$("#count").val("");
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