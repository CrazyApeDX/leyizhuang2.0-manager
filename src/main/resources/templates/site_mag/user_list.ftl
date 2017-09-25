<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>用户管理</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/user/list" id="form1">
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

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
      <ul class="icon-list">
      	<!-- <li>
      		<a class="" href="javascript:;" onclick="clickfile()"><i></i><span>导入</span></a>
      	</li> -->
        <li><a class="add" href="/Verwalter/user/edit"><i></i><span>新增</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
      </ul>
      <div class="menu-list">      
        <div class="rule-single-select single-select">
        <select name="userType" onchange="javascript:setTimeout(__doPostBack('userType',''), 0)" style="display: none;">
            <option <#if !userType??>selected="selected"</#if> value="">用户类型</option>
                    <option <#if userType?? && userType==0>selected="selected"</#if> value="0">普通会员</option>
                    <option <#if userType?? && userType==1>selected="selected"</#if> value="1">销售顾问</option>
                    <option <#if userType?? && userType==2>selected="selected"</#if> value="2">店长</option>
                    <option <#if userType?? && userType==3>selected="selected"</#if> value="3">店主</option>
                    <option <#if userType?? && userType==4>selected="selected"</#if> value="4">区域经理</option>
                    <option <#if userType?? && userType==5>selected="selected"</#if> value="5">配送员</option>
        </select>
        </div>
      </div>
      	<#if cityList?? && cityList?size gt 0 >
          	<div class="menu-list">
              	<div class="rule-single-select">
                       <select name="cityCode" id="cityCode" onchange="javascript:setTimeout(__doPostBack('changeCity',''), 0)">
                       <option value="" >选择城市</option>      
                       <#list cityList as city>
                       <option value="${city.sobIdCity?c }" <#if cityCode?? && cityCode==city.sobIdCity>selected</#if> >${city.cityName }</option>
                       </#list>
                       </select>
           		</div>
           	</div>
           	</#if>
            <#if diySiteList?? && diySiteList?size gt 0 >
            <div class="menu-list" >
                <div class="rule-single-select">
                       <select name="diyCode" id="diyCode" onchange="javascript:setTimeout(__doPostBack('changeDiy',''), 0)">
                       <option value="" >选择门店</option>      
                       <#list diySiteList as diySite>
                       	<option value="${diySite.id?c }" <#if diyCode?? && diyCode==diySite.id>selected</#if> >${diySite.title }</option>
                       </#list>
                       </select>
           		</div>
           	</div>
           	</#if>     
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
    <th width="8%">选择</th>
    <th align="left" colspan="2">用户名</th>
    <th align="left" width="6%">用户等级</th>
    <th align="left" width="6%">用户角色</th>
    <th align="left" width="6%">归属门店</th>
    <th align="center" width="12%">邮箱</th>
    <th width="8%">最近登录</th>
    <th width="8%">积分</th>
    <th width="6%">状态</th>
    <th width="10%">操作</th>
  </tr>

    <#if user_page??>
        <#list user_page.content as user>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${user_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${user.id?c}">
                </td>
                <td width="64">
                  <a href="/Verwalter/user/edit?id=${user.id?c}">
                    <img width="64" height="64" src="${user.headImageUri!"/mag/style/user_avatar.png"}">
                  </a>
                </td>
                <td>
                  <div class="user-box">
                    <h4><b>${user.username!""}</b> (姓名：${user.realName!""})</h4>
                    <i>注册时间：${user.registerTime!""}</i>
                    <span>
                      <#--><a class="amount" href="/Verwalter/user/point/list?userId=${user.id?c}" title="积分">积分</a>-->
                      <a class="point" href="/Verwalter/user/collect/list?userId=${user.id?c}" title="关注商品">关注商品</a>
                      <a class="msg" href="/Verwalter/user/recent/list?userId=${user.id?c}" title="浏览历史">浏览历史</a>                                          
                    </span>
                  </div>
                </td>
                <td align="left">${user.userLevelTitle!""}</td>
                <td align="left">
                		<#switch user.userType>
                			<#case 0>普通会员<#break>
                			<#case 1>销售顾问<#break>
                			<#case 2>店长<#break>
                			<#case 3>店主<#break>
                			<#case 4>区域经理<#break>
                			<#case 5>配送员<#break>
                			<#default>会员
                		</#switch>
                </td>
                <#--
                <input type="radio" name="userType" value="0" datatype="n" <#if user?? && user.userType?? && user.userType==0>checked="checked"</#if>><label>普通会员</label>
                -->
                <td align="left">${user.diyName!""}</td>
                <td align="center">${user.email!""}</td>
                <td align="center">${user.lastLoginTime!""}</td>
                <td align="center"></td>
                <td align="center"><#if user.statusId??><#if user.statusId==0>待审核<#elseif user.statusId==1>正常</#if></#if></td>
                <td align="center">
                	<#if user.userType==1 || user.userType==2>
                    <a href="/Verwalter/user/setSeller?id=${user.id?c}&roleId=${roleId!""}">离职</a> |
                    </#if>
                    <a href="/Verwalter/user/edit?id=${user.id?c}&roleId=${roleId!""}">修改</a> | 
                    <a href="/Verwalter/user/edit?id=${user.id?c}&roleId=${roleId!""}&action=view">查看</a> |
                    <a href="/Verwalter/user/setUsername?id=${user.id?c}&roleId=${roleId!""}">修改用户名</a></td>
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