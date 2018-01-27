<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<dl>
	<dt>销售经理</dt>
	<dd>
		<div class="rule-single-select">
    		<select name="salesManagerId" id="salesManagerId" datatype="*" sucmsg=" ">
        		<option value="0">请选择...</option>
        		
        		<#if fitSalesManagers?? && fitSalesManagers?size gt 0>
            		<#list fitSalesManagers as c>
                		<option value="${(c.id!"0")?c}" <#if company?? && company.salesManagerId?? && company.salesManagerId?c==c.id?c>selected="selected"</#if>>${c.name!""}</option>
            		</#list>
        		</#if>
        		
    		</select>
		</div>
	</dd>
</dl>