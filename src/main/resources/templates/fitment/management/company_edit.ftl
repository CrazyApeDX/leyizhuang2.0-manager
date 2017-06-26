<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑装饰公司</title>
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
	});
</script>
</head>

<body class="mainbody">
	<form name="form1" method="post" action="/Verwalter/fitment/company/save" id="form1">
		<div>
			<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
			<input name="id" type="text" value="<#if company??>${company.id?c}</#if>" style="display:none">
		</div>

		<!--导航栏-->
		<div class="location">
			<a href="/Verwalter/fitment/company/list" class="back"><i></i><span>返回列表页</span></a>
  			<a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  			<i class="arrow"></i>
			<a href="/Verwalter/fitment/company/list"><span>编辑装饰公司</span></a>
		</div>
		<div class="line10"></div>
		<!--/导航栏-->

		<!--内容-->
		<div class="content-tab-wrap">
  			<div id="floatHead" class="content-tab">
    			<div class="content-tab-ul-wrap">
      				<ul>
        				<li>
        					<a href="javascript:;" onclick="tabs(this);" class="selected">编辑信息</a>
    					</li>
      				</ul>
    			</div>
  			</div>
		</div>

		<div class="tab-content">
			<dl>
    			<dt>名称</dt>
    			<dd>
        			<input name="name" type="text" value="<#if company??>${company.name!""}</#if>" class="input normal" 
        				datatype="*1-15" sucmsg=" "
        				ajaxurl="/Verwalter/fitment/company/name/validate/<#if company??>${company.id?c}<#else>0</#if>"> 
        			<span class="Validform_checktip">*装饰公司名称</span>
    			</dd>
  			</dl>
  			<dl>
    			<dt>编码</dt>
    			<dd>
        			<input name="code" type="text" value="<#if company??>${company.code!""}</#if>" class="input normal" 
        				datatype="*1-15" sucmsg=" "
        				ajaxurl="/Verwalter/fitment/company/code/validate/<#if company??>${company.id?c}<#else>0</#if>"> 
        			<span class="Validform_checktip">*装饰公司编码</span>
    			</dd>
  			</dl>
  			
  			<dl>
    			<dt>信用额度</dt>
    			<dd>
        			<input name="credit" type="text" value="<#if company??>${(company.credit!"0.00")?string("0.00")}</#if>" class="input normal" 
        				datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" "> 
        			<span class="Validform_checktip">*当前信用额度（此处修改不会进行账务处理）</span>
    			</dd>
  			</dl>
  			
  			<dl>
    			<dt>赞助金</dt>
    			<dd>
        			<input name="promotionMoney" type="text" value="<#if company??>${(company.promotionMoney!"0.00")?string("0.00")}</#if>" class="input normal" 
        				datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" "> 
        			<span class="Validform_checktip">*当前赞助额度（此处修改不会进行账务处理）</span>
    			</dd>
  			</dl>
  			
			<dl>
				<dt>是否冻结</dt>
				<dd>
					<div class="rule-multi-radio">
					<span id="rblStatus">
						<input type="radio" name="frozen" value="true" <#if company?? && company.frozen?? && company.frozen==true>checked="checked"</#if>>
						<label>是</label>
						<input type="radio" name="frozen" value="false" <#if !company?? || company.frozen?? && company.frozen==false>checked="checked"</#if>>
						<label>否</label>
					</span>
					</div>
					<span class="Validform_checktip">*被冻结后无法下单</span>
				</dd>
			</dl>
			<dl>
        		<dt>所属城市</dt>
        		<dd>
            		<div class="rule-single-select">
                		<select name="sobId" datatype="*" sucmsg=" ">
                			<#--
                    		<#if (!company?? || (company?? && company.sobId = 0))>
                    			<option value="0">请选择...</option>
                    		</#if>
                    		-->
                    		<#if cityList??>
                        		<#list cityList as c>
                            		<option value="${(c.sobIdCity!"0")?c}" <#if company?? && company.sobId==c.sobIdCity>selected="selected"</#if>>${c.cityName!""}</option>
                        		</#list>
                    		</#if>
                		</select>
            		</div>
        		</dd>
    		</dl>
    		<dl>
        		<dt>华润价目表</dt>
        		<dd>
            		<div class="rule-single-select">
                		<select name="lsPriceHeaderId" datatype="*" sucmsg=" ">
                    		<#if (!company?? || (company?? && company.lsPriceHeaderId = 0))>
                    			<option value="0">请选择...</option>
                    		</#if>
                    		<#if lsPriceHeaderList??>
                        		<#list lsPriceHeaderList as h>
                            		<option value="${(h.id!"0")?c}" <#if company?? && company.lsPriceHeaderId==h.id>selected="selected"</#if>>${h.title!""}</option>
                        		</#list>
                    		</#if>
                		</select>
            		</div>
        		</dd>
    		</dl>
			<dl>
        		<dt>乐易装价目表</dt>
        		<dd>
            		<div class="rule-single-select">
                		<select name="lyzPriceHeaderId" datatype="*" sucmsg=" ">
                    		<#if (!company?? || (company?? && company.lyzPriceHeaderId = 0))>
                    			<option value="0">请选择...</option>
                    		</#if>
                    		<#if lyzPriceHeaderList??>
                        		<#list lyzPriceHeaderList as h>
                            		<option value="${(h.id!"0")?c}" <#if company?? && company.lyzPriceHeaderId==h.id>selected="selected"</#if>>${h.title!""}</option>
                        		</#list>
                    		</#if>
                		</select>
            		</div>
        		</dd>
    		</dl>
    		<dl>
        		<dt>莹润价目表</dt>
        		<dd>
            		<div class="rule-single-select">
                		<select name="yrPriceHeaderId" datatype="*" sucmsg=" ">
                    		<#if (!company?? || (company?? && company.yrPriceHeaderId = 0))>
                    			<option value="0">请选择...</option>
                    		</#if>
                    		<#if yrPriceHeaderList??>
                        		<#list yrPriceHeaderList as h>
                            		<option value="${(h.id!"0")?c}" <#if company?? && company.yrPriceHeaderId==h.id>selected="selected"</#if>>${h.title!""}</option>
                        		</#list>
                    		</#if>
                		</select>
            		</div>
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