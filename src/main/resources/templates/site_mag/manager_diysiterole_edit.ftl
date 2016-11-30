<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="http://www.rcsjcs.com/scripts/lhgdialog/skins/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑管辖门店角色</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();
    
    //权限全选
    $("input[name='checkAll']").click(function () {
        if ($(this).prop("checked") == true) {
            $(this).parent().siblings("td").find("input[type='checkbox']").prop("checked", true);
        } else {
            $(this).parent().siblings("td").find("input[type='checkbox']").prop("checked", false);
        }
    });
});
function checkCity(obj){
	 if ($(obj).prop("checked") == true) {
		 $("input[class='city"+$(obj).val()+"']").prop("checked",true);
	 }else{
		 $("input[class='city"+$(obj).val()+"']").prop("checked",false);
	 }
	
}
</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/manager/diysiterole/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" name="diySiteRoleId" id="__VIEWSTATE" value="<#if tdDiySiteRole??>${tdDiySiteRole.id!''}</#if>">
</div>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="/Verwalter/manager/diysiterole/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>系统用户</span>
  <i class="arrow"></i>
  <span>编辑角色</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab" style="position: static; top: 52px;">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑角色信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dt>角色类型</dt>
    <dd>
      <div class="rule-single-select">
        <select name="isSys" datatype="*" errormsg="请选择角色类型！" sucmsg="">
        	<option value="">请选择类型...</option>
        	<option value="1" <#if tdDiySiteRole?? && tdDiySiteRole.isSys?? && tdDiySiteRole.isSys>selected="selected"</#if>>超级用户</option>
        	<option value="0" <#if tdDiySiteRole?? && tdDiySiteRole.isSys?? && !tdDiySiteRole.isSys>selected="selected"</#if>>非超级用户</option>
        </select>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>角色名称</dt>
    <dd>
    	<div class="rule-single-select">
		    <select name="title"  datatype="*" errormsg="请选择角色类型！" sucmsg="">
		    <option value="">请选择角色...</option>
		    <#if manager_role??>
	    		<#list manager_role as role>
	    			<option value="${role.title!'' }" <#if tdDiySiteRole?? && role.title==tdDiySiteRole.title >selected</#if>  >${role.title!'' }</option>
	    		</#list>
	    	</#if>
		    </select>
   		</div>
    </dd>
    <#--不能让用户输入改为下拉列表选择 <dd><input name="title" type="text" value="<#if tdDiySiteRole??>${tdDiySiteRole.title!''}</#if>" class="input normal" datatype="*" sucmsg=" "><span class="Validform_checktip">*角色中文名称，100字符内</span></dd> -->
  </dl>   
  <dl>
    <dt>管理权限</dt>
    <dd>
      <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
        <thead>
          <tr>
            <th width="30%">所属城市</th>
            <th width="30%">门店名称</th>
            <th>权限分配</th>
            <#--> <th width="10%">全选</th> </#-->
          </tr>
        </thead>
        <tbody>
        	<#assign total_index=0>
        	<#assign cityId=0>
        	<#if diysite_list??>
        		<#list diysite_list as item>
        		
        			<tr>
	        			<td style="white-space:nowrap;word-break:break-all;overflow:hidden;">
	        					<#if item.cityId?? && cityId!=item.cityId >
		        					<span class="folder-open"></span>
		                            ${item.city!'' }
		                            <input type="checkbox" name="cityChkIds" onclick="checkCity(this)" value="${item.cityId?c }" <#if tdDiySiteRole?? && tdDiySiteRole.cityTree?? && tdDiySiteRole.cityTree?contains("["+item.cityId?c+"]")>checked="checked"</#if> >
	        					</#if>
                        </td>
                        <td style="white-space:nowrap;word-break:break-all;overflow:hidden;">
                        
                            ${item.title!''}
                        </td>
                        <td>
                            <span class="cbllist">
                                <input type="checkbox" name="listChkId" class="city${item.cityId!'' }" <#if tdDiySiteRole?? && tdDiySiteRole.diySiteTree?? && tdDiySiteRole.diySiteTree?contains("["+item.id?c+"]")>checked="checked"</#if> value="${item.id?c}">
                                <label> 可控 </label>
                            </span>
                            <#assign total_index=total_index+1>
                        </td>
                        <#--> <td align="center"><input name="checkAll" type="checkbox"></td> </#-->
                    </tr>
                    <#assign cityId=item.cityId!''>
        		</#list>
    		</#if>
        </tbody>
      </table>
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
</body>
</html>