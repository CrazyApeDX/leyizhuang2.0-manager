<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<style>
	.turn_div{
	    position: fixed;
	    left: 0px;
	    top: 0px;
	    width: 100%;
	    height: 100%;
	    background: rgba(0,0,0,0.3);
	    z-index:9999;
	    display:none;
	}
	.turn_div div{
	    width:80%;
	    padding: 0 5%;
	    height: 200px;
	    margin: auto;
	    border-radius: 8px;
	    background:white;
	}
	.turn_div div p{
	    float: left;
	    width: 100%;
	    margin-top: 10px;
	    line-height: 40px;
	    font-size: 1.2em;
	    color: #333333;
	    text-align: center;
	}
	.turn_div div span{
	    float: left;
	    width: 72%;
	    padding: 0 14%;
	}
	.turn_div div span input{
	    float: left;
	    width: 60px;
	    height: 28px;
	    margin-top: 10px;
	    line-height: 28px;
	    text-align: center;
	    border-radius: 6px;
	    border: none;
	}
	.turn_div div span input:nth-of-type(1){
	    float: left;
	    color: white;
	    background: url(../images/btn01.png) no-repeat center;
	}
	.turn_div div span input:nth-of-type(2){
	    float: right;
	    background: url(../images/btn02.png) no-repeat center;
	}
	.turn_div textarea{
	    width: 100%;
	    height:100px;
	}
	
	.turn_div{
	     position: fixed;
	     left: 0px;
	     top: 0px;
	     width: 100%;
	     height: 100%;
	     background: rgba(0,0,0,0.3);
	 }
	 .turn_div div{
	     width:30%;
	     padding: 0 5%;
	     height: 230px;
	     margin: auto;
	     border-radius: 8px;
	     background:white;
	 }
	 .turn_div div p{
	     float: left;
	     width: 100%;
	     margin-top: 10px;
	     line-height: 40px;
	     font-size: 1.2em;
	     color: #333333;
	     text-align: center;
	 }
	 .turn_div div span{
	     float: left;
	     width: 72%;
	     padding: 0 14%;
	 }
	 .turn_div div span input{
	     float: left;
	     margin-top: 10px;
	     line-height: 28px;
	     text-align: center;
	     border-radius: 6px;
	     border: none;
	 }
	
	 .turn_div textarea{
	     width: 100%;
	     height:100px;
	 }
	 .turn_div section{
	     float: left;
	     width: 50%;
	     margin-top: 10px;
	 }
	 .turn_div section input[type='radio']{
	     width: 14px;
	     height: 14px;
	     margin-top: 2px;
	 }
	 .turn_div section *{
	     float: left;
	 }
</style>
<title>用户管理</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/user/balance/change/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
</div>

<script type="text/javascript">
var theForm = document.forms['form1'];
    if (!theForm) {
        theForm = document.form1;
    }
    function __doPostBack(eventTarget, eventArgument) {
        if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
            theForm.__EVENTTARGET.value = eventTarget;
            theForm.__EVENTARGUMENT.value = eventArgument;
            theForm.submit();
        }
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
 		/* function clickfile(){
 			$('#clickFile')[0].click()
 		}	
 		function upload(){
            var formData = new FormData($( "#upload" )[0]);  
            $.ajax({  
                 url: '/Verwalter/upload' ,  
                 type: 'POST',  
                 data: formData,  
                 async: false,  
                 cache: false,  
                 contentType: false,  
                 processData: false,  
                 success: function (res) {  
                     if(res.status==1){
                    	 
                     }else{
                    	 alert("上传失败，请检查excel格式");  
                     }
                 },  
                 error: function (res) {  
                     alert("上传失败，请检查excel格式");  
                 }  
            });  
        } */
 	function order_return(id){
        var he = ($(window).height() - $('.turn_div div').height())/2 - 50;
        $('.turn_div div').css({marginTop:he});
        $('.turn_div').fadeIn(600);
        $('#withdrawId').val(id);
    };
    function win_no_return(){  
        $('.turn_div').fadeOut(600);
    };
    function send(){
    	var remark = $('#remark').val();
    	var withdrawId = $('#withdrawId').val();
    	if (!remark) {
			close(1);
			alert('请输入退回原因');
			return;
		}
    	$.ajax({
			type:"post",
			url:"/Verwalter/user/balance/change/withdraw/invalid",
			data:{
				withdrawId : withdrawId,
				remarks : remark
			},
			error:function(){
				close(1);
				alert('亲，您的网速不给力啊！');
			},
			success:function(res){
				if(0 === res.status){
					window.location.href="/Verwalter/user/balance/change/withdraw/list";
				}
			}
		});
    
    }
</script>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>会员管理</span>
  <i class="arrow"></i>
  <span>会员列表</span>  
</div>
<!--/导航栏-->
	<div class="turn_div">
		<input type="hidden" value="" id="withdrawId" name="withdrawId">
        <div style="height:205px;margin-top:160px;">                   
            <p id="title">申请退回原因</p>
            <textarea name="remark" id="remark" style="background-color:#f3f4f6"></textarea>
            <span>
                <input type="button" style="background:#666;" name="" id="" value="是" onclick="send();" />
                <input onclick="javascript:win_no_return();" style="background:#ea5d0e;" type="button" value="否" />
            </span>             
        </div>
    </div>
<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
      <ul class="icon-list">
      	<!-- <li>
      		<a class="" href="javascript:;" onclick="clickfile()"><i></i><span>导入</span></a>
      	</li> 
        <li><a class="add" href="/Verwalter/user/edit"><i></i><span>新增</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>-->
      </ul>
    </div>
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${keywords!""}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th width="15%;">选择</th>
    <th width="25%" align="left" style="padding:8px 0;">&emsp;用户名</th>
    <th width="10%" align="left">提现金额</th>
    <th width="20%" align="left">申请时间</th>
    <th width="10%" align="left">状态</th>
    <th width="10%" align="center">操作</th>
  </tr>

    <#if user_page??>
        <#list user_page.content as cashWithdraw>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${cashWithdraw_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${cashWithdraw.id?c}">
                </td>
                <td>
                  <div class="user-box">
                    <h4><b>${cashWithdraw.username!""}</b> (姓名：${cashWithdraw.realName!""})</h4>
                  </div>
                </td>
                <td align="left">${cashWithdraw.amount!""}</td>
                <td align="left">${cashWithdraw.createTime!""}</td>
	            <td align="left">
	            	<#if cashWithdraw.status??>
                        <#switch cashWithdraw.status>
                            <#case "WAITING">待处理<#break>
                            <#case "FINISHING">已完成<#break>
                            <#case "INVALID">申请退回<#break>
                        </#switch>
                    </#if></td>
                <td align="center">
                	<#if cashWithdraw.status?? && cashWithdraw.status == 'WAITING'>
                    	<a href="/Verwalter/user/balance/change/withdraw/edit?id=${cashWithdraw.userId?c}&withdrawId=${cashWithdraw.id?c}&roleId=${roleId!""}">变更</a>
                    	&nbsp;&nbsp;<a href="javascript:order_return(${cashWithdraw.id?c})">退回申请</a>
              		</#if>
              </tr>
        </#list>
    </#if>
     
</tbody>
</table>

<!--列表-->

<!--内容底部-->
<#assign PAGE_DATA=user_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>
<!-- <form id="upload" action="/Verwalter/upload" enctype="multipart/form-data" method="post">
            <input type="file" onchange="upload()" name="Filedata" id="clickFile">
        	</form> -->

</body></html>