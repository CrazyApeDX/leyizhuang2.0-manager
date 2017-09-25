<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑员工账号</title>
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
	});
</script>
</head>

<body class="mainbody">
	<form name="form_user" method="post" action="/Verwalter/fitment/employee/save" id="form_user">
		<div>
			<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
			<input type="hidden" id="userId" name="id" value="<#if employee??>${employee.id?c}</#if>" >
		</div>
		<!--导航栏-->
		<div class="location" style="position: static; top: 0px;">
  			<a href="/Verwalter/fitment/employee/list">
  				<i></i>
  				<span>返回列表页</span>
			</a>
  			<a href="/Verwalter/center" class="home">
  				<i></i>
  				<span>首页</span>
			</a>
  			<i class="arrow"></i>
  			<span>对外合作</span>
  			<i class="arrow"></i>
  			<span>编辑装饰公司</span>
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
    					<#--
    					<li><a href="javascript:;" onclick="tabs(this);">账户信息</a></li>
    					-->
  					</ul>
				</div>
			</div>
		</div>

		<!--基本资料-->
		<div class="tab-content">
			<dl id="diySiteId" style="display:block;">
    			<dt>所属装饰公司</dt>
    			<dd>
    				<div class="rule-single-select">
	        			<select name="companyId" id="companyId" datatype="n">
	            			<#if companyList??>
	                			<#list companyList as item>
	                    			<option <#if employee?? && employee.companyId?? && item.id == employee.companyId>selected="selected"</#if> value="${item.id?c}">${item.name!''}</option>
	                			</#list>
	            			</#if>
	        			</select>
        			</div>
				</dd>
			</dl>
			<dl>
    			<dt>手机号码</dt>
				<dd>
					<input name="mobile" type="text" value="<#if employee??>${employee.mobile!""}</#if>" class="input normal" 
						datatype="/^1\d{10}$/" sucmsg=" "
						ajaxurl="/Verwalter/fitment/employee/mobile/validate/<#if employee??>${employee.id?c}<#else>0</#if>">
				</dd>
  			</dl>
  			<dl>
    			<dt>姓名</dt>
				<dd>
					<input name="name" type="text" datatype="*1-10" value="<#if employee??>${employee.name!""}</#if>" class="input normal">
				</dd>
  			</dl>
  			<dl>
				<dt>是否冻结</dt>
				<dd>
					<div class="rule-multi-radio">
					<span id="rblStatus">
						<input type="radio" name="frozen" value="true" <#if employee?? && employee.frozen?? && employee.frozen==true>checked="checked"</#if>>
						<label>是</label>
						<input type="radio" name="frozen" value="false" <#if !employee?? || employee.frozen?? && employee.frozen==false>checked="checked"</#if>>
						<label>否</label>
					</span>
					</div>
					<span class="Validform_checktip">*被冻结后无法下单</span>
				</dd>
			</dl>
			<dl>
            	<dt>冻结结束时间</dt>
            	<dd>
                	<div class="input-date">
                    	<input name="frozenEndTime" id="frozenEndTime" type="text" value="<#if employee??>${employee.frozenEndTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    	<i>日期</i>
                	</div>
                	<span class="Validform_checktip"></span>
            	</dd>
        	</dl>
  			<dl>
    			<dt>身份</dt>
    			<dd>
      				<div class="rule-multi-radio">
        				<span id="rblStatus">
            				<input type="radio" name="isMain" value="true" datatype="*" <#if employee??&&employee.isMain??&&employee.isMain==true>checked="checked"</#if>>
				            <label>采购经理</label>
				            <input type="radio" name="isMain" value="false" datatype="*" <#if !employee??||employee.isMain??&&employee.isMain==false>checked="checked"</#if>>
				            <label>工长</label>
        				</span>
      				</div>
      				<span class="Validform_checktip"></span>
				</dd>
  			</dl>
		</div>
		<!--/基本资料-->

		<!--安全设置-->
		<div class="tab-content" style="display:none;">
			<dl>
				<dt>设置新密码</dt>
				<dd>
					<input name="oldPassword" type="password" value="" class="input normal" datatype="*6-20" ignore="ignore" nullmsg="请设置密码" errormsg="密码范围在6-20位之间" sucmsg=" " value=""> 
					<span class="Validform_checktip">*新密码将覆盖原密码，至少6位</span>
				</dd>
			</dl>
			<dl>
				<dt>确认密码</dt>
				<dd>
					<input name="rePassword" type="password" value="" class="input normal" datatype="*" ignore="ignore" recheck="oldPassword" nullmsg="请再输入一次密码" errormsg="两次输入的密码不一致" sucmsg=" " value=""> 
					<span class="Validform_checktip">*再次输入密码</span>
				</dd>
			</dl>
		</div>
		<!--/安全设置-->

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