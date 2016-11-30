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
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">修改密码</a></li>
      </ul>
    </div>
  </div>
</div>

<!--安全设置-->
<div class="tab-content" style="display:block;">
  <dl>
    <dt>原密码</dt>
    <dd><input id="old" type="password" value="" class="input normal" datatype="*6-20" ignore="ignore" nullmsg="请设置密码" errormsg="密码范围在6-20位之间" sucmsg=" " value=""> <span class="Validform_checktip">*新密码将覆盖原密码，至少6位</span></dd>
  </dl>
  <dl>
    <dt>设置新密码</dt>
    <dd><input id="new" type="password" name="theNewPassword" value="" class="input normal" datatype="*6-20" ignore="ignore" nullmsg="请设置密码" errormsg="密码范围在6-20位之间" sucmsg=" " value=""> <span class="Validform_checktip">*新密码将覆盖原密码，至少6位</span></dd>
  </dl>
  <dl>
    <dt>确认密码</dt>
    <dd><input id="re" type="password" value="" class="input normal" datatype="*" ignore="ignore" recheck="theNewPassword" nullmsg="请再输入一次密码" errormsg="两次输入的密码不一致" sucmsg=" " value=""> <span class="Validform_checktip">*再次输入密码</span></dd>
  </dl>
  <#--<#if user??>
  	<input name="oldPassword" type="hidden" value="${user.password!''}" />
  </#if>-->
</div>
<!--/安全设置-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-list">
    <input type="button" onclick="getInfo();" name="btnSubmit" value="提交保存" id="formBtn" class="btn">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
  <div class="clear"></div>
</div>
<!--/工具栏-->

</form>
<script>
	var getInfo = function() {
		$('#formBtn').removeAttr('onclick');
		var old = $('#old').val();
		var theNew = $('#new').val();
		var re = $('#re').val();
		
		if (theNew.length < 6 || theNew.length > 20) {
			alert("密码的长度为6-20位");
			$('#formBtn').attr('onclick','getInfo()');
			return;
		}
		
		if (re !== theNew) {
			alert("两次输入的密码不一致");
			$('#formBtn').attr('onclick','getInfo()');
			return;
		}
		
		$.ajax({
			url : '/Verwalter/manager/password/edit',
			method : 'POST',
			data : {
				old : old,
				theNew : theNew
			},
			success : function(result) {
				if (0 === result.status) {
					alert('修改成功！');
					window.location.href = '/Verwalter/center';
				} else {
					$('#formBtn').attr('onclick','getInfo()');
					alert(result.message);
				}
			}
		});
	}
</script>

</body></html>