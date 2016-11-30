<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>城市维护</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
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
    
    //（缩略图）
    var txtPic = $("#txtImgUrl").val();
    if (txtPic == "" || txtPic == null) {
        $(".thumb_ImgUrl_show").hide();
    }
    else {
        $(".thumb_ImgUrl_show").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
        $(".thumb_ImgUrl_show").show();
    }
});
</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/setting/city/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="id" type="text" value='<#if city??>${city.id?c}</#if>' style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/setting/city/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/setting/city/list"><span>城市编辑</span></a>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑信息</a></li>
        <li><a href="javascript:;" onclick="tabs(this);" class="">分类限购</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
    <dl>
        <dt>所属子公司</dt>
        <dd>
            <div class="rule-single-select">
                <select name="companyId" datatype="*" sucmsg=" ">
                    <#if !city??>
                    <option value="">请选择子公司...</option>
                    </#if>
                    <#if company_list??>
                        <#list company_list as c>
                            <option value="${c.id!""}" <#if city?? && city.companyId==c.id>selected="selected"</#if>>${c.name!""}</option>
                        </#list>
                    </#if>
                </select>
            </div>
        </dd>
    </dl>
  <dl>
    <dt>城市名</dt>
    <dd>
        <input name="cityName" type="text" value="<#if city??>${city.cityName!"0"}</#if>" class="input normal" datatype="*1-255" sucmsg=" "> 
        <span class="Validform_checktip">*城市名称</span>
    </dd>
  </dl>
  <dl>
        <dt>短信账号</dt>
        <dd>
            <div class="rule-single-select">
                <select name="smsAccountId" datatype="*" sucmsg=" ">
                    <#if !city??>
                    <option value="">请选择类别...</option>
                    </#if>
                    <#if SMSAccount_list??>
                        <#list SMSAccount_list as c>
                         <option value="${c.id?c}" <#if city??&& city.smsAccountId?? && city.smsAccountId == c.id>selected="selected"</#if>>${c.accountTitle!""}</option>
                        </#list>
                    </#if>
                </select>
            </div>
        </dd>
    </dl>
    <dl>
        <dt>是否开通配送服务</dt>
        <dd>
            <div class="rule-multi-radio multi-radio">
                <span>
                    <input type="radio" name="citySend" value="1" <#if city??==false || city.citySend==true>checked="checked"</#if>>
                    <label>开通</label>
                    <input type="radio" name="citySend" value="0" <#if city?? && city.citySend?? && city.citySend==false>checked="checked"</#if>>
                    <label>关闭</label>
                </span>
            </div>
        </dd>
    </dl>
    <dl>
        <dt>配送起始小时</dt>
        <dd>
            <input name="beginHour" type="text" value="<#if city?? && city.beginHour??>${city.beginHour?c}</#if>" class="input normal" datatype="n" sucmsg=" ">
            <span class="Validform_checktip">配送起始时间（小时）</span>
        </dd>
    </dl>
    <dl>
        <dt>配送起始分钟</dt>
        <dd>
            <input name="beginMinute" type="text" value="<#if city?? && city.beginMinute??>${city.beginMinute?c}</#if>" class="input normal" datatype="n" sucmsg=" ">
            <span class="Validform_checktip">配送起始时间（分钟）</span>
        </dd>
    </dl>
    <dl>
        <dt>配送结束小时</dt>
        <dd>
            <input name="finishHour" type="text" value="<#if city?? && city.finishHour??>${city.finishHour?c}</#if>" class="input normal" datatype="n" sucmsg=" ">
            <span class="Validform_checktip">配送结束时间（小时）</span>
        </dd>
    </dl>
    <dl>
        <dt>配送结束分钟</dt>
        <dd>
            <input name="finishMinute" type="text" value="<#if city?? && city.finishMinute??>${city.finishMinute?c}</#if>" class="input normal" datatype="n" sucmsg=" ">
            <span class="Validform_checktip">配送结束时间（分钟）</span>
        </dd>
    </dl>
    <dl>
        <dt>预约配送延迟时间</dt>
        <dd>
            <input name="delayHour" type="text" value="<#if city?? && city.delayHour??>${city.delayHour?c}</#if>" class="input normal" datatype="n" sucmsg=" ">
            <span class="Validform_checktip">预约配送延迟时间（小时）</span>
        </dd>
    </dl>
    <dl>
        <dt>排序数字</dt>
        <dd>
            <input name="sortId" type="text" value="<#if city??>${city.sortId!"0"}<#else>99</#if>" class="input small" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
            <span class="Validform_checktip">*数字，越小越向前</span>
        </dd>
    </dl>
</div>

<div class="tab-content" style="display: none;">
	<dl>
		<dt>城市名</dt>
		<dd>${city.cityName!''}</dd>
	</dl>
	
	<dl>
		<dt>分类限购</dt>
		<dd>
			<table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
				<thead>
					<tr>
						<th width="30%">一级分类</th>
						<th>二级分类</th>
					</tr>
				</thead>
				<tbody style="text-align:center;">
					<#if level_one??>
						<#list level_one as one>
							<tr>
								<td>
									<label>${one.title!''}</label>
									<input id="${one.id?c}" type="checkbox" <#if ("limit" + one.id?c)?eval??>checked="checked"</#if> name="limit" value="${one.id?c}" onclick="oneSelect(${one.id?c});">
								</td>
								<td>
									<#if ("level_two"+one_index)?eval??>
										<#list ("level_two"+one_index)?eval as two>
											<label>${two.title!''}</label>
											<input id="${two.id?c}" type="checkbox" <#if ("limit" + two.id?c)?eval??>checked="checked"</#if> name="limit" value="${two.id?c}" onclick="twoSelect(${two.id?c});">
										</#list>
									</#if>								
								</td>
							</tr>
						</#list>
					</#if>
				</tbody>
			</table>
		</dd>
	</dl>
	<script type="text/javascript">
		<#-- 生成分类树数据 -->
		var data = {
			<#if level_one??>
				<#list level_one as one>
					${one.id?c} :[ 
						<#if ("level_two"+one_index)?eval??>
							<#list ("level_two"+one_index)?eval as two>	
								${two.id?c}		
								<#if ("level_two"+one_index)?eval?size !=(two_index+1)>
									,
								</#if>
							</#list>
						</#if>
						]
						<#if level_one?size != (one_index+1)>
							,
						</#if>
				</#list>
			</#if>
		}
		
		<#-- 二级分类及其父类的关系数据 -->
		var two_level_data = {
			<#if level_one??>
				<#list level_one as one>
					<#if ("level_two"+one_index)?eval??>
						<#list ("level_two"+one_index)?eval as two>	
							${two.id?c} : ${one.id?c}
							<#if !((one_index+1)==level_one?size && (two_index+1) == ("level_two"+one_index)?eval?size)>
								,
							</#if>
						</#list>
					</#if>
				</#list>
			</#if>
		}
		
		<#-- 点击一级分类选中框的方法 -->
		function oneSelect(id){
			if(id){
				<#-- 获取指定一级分类下的所有二级分类 -->
				var two_levels = data[id];
				
				<#-- 判断指定一级分类的是否被选中 -->
				var one_level = document.getElementById(id);
				if(one_level && one_level.checked){
					<#-- 遍历其下所有的二级分类，使所有的二级分类被选中 -->
					for(var i = 0; i < two_levels.length; i++){
						var two = document.getElementById(two_levels[i]);
						if(two){
							two.checked = true;
						}
					}
				}
				
				if(one_level && !one_level.checked){
					<#-- 遍历其下所有的二级分类，使所有的二级分类被选中 -->
					for(var i = 0; i < two_levels.length; i++){
						var two = document.getElementById(two_levels[i]);
						if(two){
							two.checked = false;
						}
					}
				}
			}
		}
		
		<#-- 点击二级分类选中框的方法 -->
		function twoSelect(id){
			if(id){
				<#-- 获取指定二级分类的父类 -->
				var parentId = two_level_data[id];
				
				var child = document.getElementById(id);
				
				var parent = document.getElementById(parentId);
				
				<#-- 场景1：选中被点击的子类 -->
				if(child && child.checked){
					<#-- 子类被选中，父类也被选中 -->
					if(parent){
						parent.checked = true;
					}
				}
				
				<#-- 场景2：点击子类取消选中 -->
				if(child && !child.checked){
					<#-- 获取父类下所有的子类id -->
					var children = data[parentId];
					<#-- 创建一个bool类型变量用于判断是否所有的子类都取消选中了 -->
					var childChecked = false;
					
					for(var i = 0; i < children.length; i++){
						var childEle = document.getElementById(children[i]);
						if(childEle && childEle.checked){
							childChecked = true
						}
					}
					
					if(childChecked){
						if(parent){
							parent.checked = true;
						}
					}else{
						if(parent){
							parent.checked = false;
						}
					}
				}
			}
		}
	</script>		
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