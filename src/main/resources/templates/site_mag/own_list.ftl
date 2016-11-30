<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>审核欠款</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/order/own/list" id="form1">
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
   
</script>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>订单管理</span>
  <i class="arrow"></i>
  <span>审核欠款</span>  
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
      <ul class="icon-list">
        <li><a onclick="return ExePostBack('btnEnable','审核将允许欠款，是否继续？');" class="save" href="javascript:__doPostBack('btnVerify','')"><i></i><span>通过</span></a></li>
        <li><a onclick="return ExePostBack('btnNotEnable','不通过审核将不允许欠款，是否继续？');" class="save" href="javascript:__doPostBack('btnVerify','')"><i></i><span>不通过</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <#if tdManagerRole?? && tdManagerRole.isSys>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        </#if>
      </ul>
      <div class="menu-list">
        <div class="rule-single-select single-select">
        <select name="isEnable" onchange="javascript:setTimeout(__doPostBack('isEnable',''), 0)" style="display: none;">
            <option <#if !statusId??>selected="selected"</#if> value="">审批状态</option>
            <option <#if statusId?? && statusId==0>selected="selected"</#if> value="0">待审批</option>
            <option <#if statusId?? && statusId==1>selected="selected"</#if> value="1">已审批</option>
        </select>
        </div>
      </div>
      <div class="menu-list">
        <div class="rule-single-select single-select">
        <select name=ispassed onchange="javascript:setTimeout(__doPostBack('ispassed',''), 0)" style="display: none;">
            <option <#if !ispassed??>selected="selected"</#if> value="">是否通过</option>
            <option <#if ispassed?? && ispassed==0>selected="selected"</#if> value="0">未通过</option>
            <option <#if ispassed?? && ispassed==1>selected="selected"</#if> value="1">已通过</option>
        </select>
        </div>
      </div>
      <div class="menu-list">
        <div class="rule-single-select single-select">
        <select name=isPayed onchange="javascript:setTimeout(__doPostBack('isPayed',''), 0)" style="display: none;">
            <option <#if !payLeft??>selected="selected"</#if> value="">还款状态</option>
            <option <#if payLeft?? && payLeft==0>selected="selected"</#if> value="0">未还清</option>
            <option <#if payLeft?? && payLeft==1>selected="selected"</#if> value="1">已还清</option>
        </select>
        </div>
      </div>
      <#if cityList?? && cityList?size gt 0 >
          	<div class="menu-list">
              	<div class="rule-single-select">
                       <select name="city" id="cityCode" onchange="javascript:setTimeout(__doPostBack('changeCity',''), 0)">
                       <option value="" >选择城市</option>      
                       <#list cityList as city>
                       	<option value="${city.cityName }" <#if cityName?? && cityName==city.cityName>selected</#if> >${city.cityName }</option>
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
                       	<option value="${diySite.storeCode }" <#if diyCode?? && diyCode==diySite.storeCode>selected</#if> >${diySite.title }</option>
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

    <#if own_page??>
        <#list own_page.content as consult>
            <tr>
                <td class="comment">
                  <div class="title">
                    <span class="note">
								<#if diySiteList?? && diySiteList?size gt 0 >
									<#list diySiteList as diySite>
										<#if diySite.storeCode==consult.diyCode>
										<i>门店名称：${diySite.title!"" }</i>
										</#if>
									</#list>
								</#if>
						<i>会员名称：<#if name_map??>${name_map[consult.username]!''}</#if></i>
                        <i>联系电话：${consult.username!""}</i>
                        <i>时间：${consult.createTime!""}</i>
                        <#--<i class="reply">
                            <a href="/Verwalter/user/consult/edit?id=${consult.id?c}&statusId=${statusId!""}">回复</a>
                        </i>-->
                    </span>
                    <b>
                        <span class="checkall" style="vertical-align:middle;">
                            <input id="listChkId" type="checkbox" name="listChkId" value="${consult.id?c}" >
                        </span>
                        <input type="hidden" name="listId" id="listId" value="${consult.id?c}">
                    </b>
                    订单号 ：<a href="/Verwalter/order/own/edit?id=${consult.id?c}">${consult.orderNumber!""}</a> 
                    <#-- 订单号 ：${consult.orderNumber!""} -->
                  </div>
                  <div class="ask">
                    <#if !consult.isEnable?? || consult.isEnable==false>
                        <b class="answer" title="待审批">待审批</b>
                    </#if>
                    <#if !consult.isEnable?? || consult.isEnable==true>
                        <#if !consult.ispassed?? ||consult.ispassed == true>
                        <b class="answer" title="已审批">审批通过</b>
                        </#if>
                        <#if !consult.ispassed?? ||consult.ispassed == false>
                        <b class="answer" title="已审批">审批未通过</b>
                        </#if>
                    </#if>
                    <#if !consult.isPayed?? || consult.isPayed==false>
                        <b class="answer" title="未还清">未还清</b>
                    </#if>
                    <#if !consult.isPayed?? || consult.isPayed==true>
                        <b class="answer" title="已还清">已还清</b>
                    </#if>
                    收款：<#if consult.payed??>${consult.payed?c}<#else>0</#if><b></b>   |<b></b> 欠款：<#if consult.owned??>${consult.owned?c}<#else>0</#if>
                  </div>
                </td>
            </tr>
        </#list>
    </#if>
     
</tbody>
</table>

<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=own_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>