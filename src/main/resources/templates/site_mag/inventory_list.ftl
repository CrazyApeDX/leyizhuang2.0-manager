<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>库存管理</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">

<style type="text/css">
.dialog{
	position: fixed;
	_position:absolute;
	z-index:1;
	top: 50%;
	left: 50%;
	margin: -141px 0 0 -201px;
	width: 400px;
	height:230px;
	line-height: 190px;
	text-align:center;
	font-size: 14px;
	background-color:#FFF;
	overflow:hidden;
	border: 1px solid #cccccc;
	display: none;
	box-shadow: 10px 10px 5px #888888;
	color: #333;
}	
.dialog_row{
	width: 100%;
	height: 30px;
	line-height: 30px;
}
.dialog_tip{
	width: 100%;
	height: 30px;
	color: red;
	line-height: 30px;
	text-align: center;
}
.dialog_title{
	width: 100%;
	height: 50px;
	line-height: 50px;
}
.dialog_btn{
	width:60px;
	height: 30px;
	line-height: 30px;
	margin-left: 20px;
    margin-top: 30px;
}
</style>
<script type="text/javascript">
//打开还款窗口
    function showDialog(){
    	$(".dialog").show();
    }
    //关闭还款窗口
    function hiddenDialog(){
    	$(".dialog").hide();
    }
    //提交还款
    function sumbitBackMoney(owned,id){
    	var defualtInventory=$('#defualtInventory').val();
    	
    	if(!isNaN(defualtInventory) && Number(defualtInventory)>=0){
    		$.dialog.confirm("确认运行,点击确定后请勿操作?",function(){
    			$.ajax({
     	            type: "post",
     	            url: "/Verwalter/goods/setting/goodsleft/numbers",
     	            data: {"defualtInventory":defualtInventory},
     	            dataType: "json",
     	            error: function (XMLHttpRequest, textStatus, errorThrown) {
     	            	 $.dialog.alert('错误提示：' + '网络连接失败', function () { });
     	            },
     	            success: function (data) {
     	            	$.dialog.alert(data.res, function () { });
     	            	hiddenDialog();
     	             }
     	        });
    		},function(){});
    		
    	}else{
    		alert("初始库存必须是大于等于0的数字");
    	}
    }
    function downloaddate(type)
    {
        var keywords = $("#keywords").val();
        var diyCode = $("#siteId").val();
        var city = $("#regionId").val();
        if(null==city){
        	city="";
        }
        
        window.open("/Verwalter/goods/diysiteDowndata?keywords="+ keywords + "&diyCode="+diyCode+"&cityCode="+city+"&type="+type);
    }
    </script>
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/goods/inventory/list" id="form1">
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
  <span>库存管理</span>
  <i class="arrow"></i>
  <span>库存列表</span>  
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
        <ul class="icon-list">
          <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
          <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnInventory','')"><i></i><span>保存</span></a></li>
        </ul>
        <div class="menu-list">
        <#if !is_diy_site_bool??>
            <div class="rule-single-select">
                <select id="regionId" name="regionId" onchange="javascript:setTimeout(__doPostBack('categoryId', ''), 0)">
                    <option <#if !regionId??>selected="selected"</#if> value="" >所有城市</option>
                    <#if city_list??>
                        <#list city_list as c>
                            <option value='${c.sobIdCity?c}' <#if regionId?? && c.sobIdCity==regionId>selected="selected"</#if> >${c.cityName!""}</option>
                        </#list>
                    </#if>
                </select>
            </div>
            </#if>
            <div class="rule-single-select">
                <select id="siteId" name="siteId" onchange="javascript:setTimeout(__doPostBack('categoryId', ''), 0)">
                <#if !is_diy_site_bool??>
                    <option <#if !siteId??>selected="selected"</#if> value="" >所有库存</option>
                    <option <#if siteId?? && siteId = -1>selected="selected"</#if> value="-1">仅城市库存</option>
                </#if>
                    <#if site_list??>
                        <#list site_list as site>
                            <option value='${site.id?c}' <#if siteId?? && site.id==siteId>selected="selected"</#if> >${site.title!""}</option>
                        </#list>
                    </#if>
                </select>
            </div>
        </div>
    </div>
    <div class="r-list">
      <input id="keywords" name="keywords" type="text" class="keyword" value="${keywords!''}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('lbtnSearch','')">查询</a>
      <a style="color:black;line-height: 30px;margin-left: 20px;" href="javascript:showDialog()">添加库存</a>
      <a style="color:black;line-height: 30px;margin-left: 20px;" href="javascript:downloaddate(1)">城市可用报表下载</a>
      <a style="color:black;line-height: 30px;margin-left: 20px;" href="javascript:downloaddate(2)">门店可用报表下载</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th width="6%">选择</th>
    <th align="left">商品名称</th>
    <th align="left" width="15%">商品编码</th>
    <th align="left" width="15%">所属门店</th>
    <th align="left" width="15%">所属城市</th>
    <th align="center" width="15%">剩余库存</th>
  </tr>

    <#if inventory_page??>
        <#list inventory_page.content as item>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${item_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${item.id?c}">
                </td>
                <td align="left">${item.goodsTitle!""}</td>
                <td align="left"><#if item.goodsCode??>${item.goodsCode!""}</#if></td>
                <td align="left">${item.diySiteName!"城市库存"}</td>
                <td align="left">${item.regionName!""}</td>
                <td align="center"><input name="listInventory" type="text" <#if is_diy_site_bool??>readonly="readonly"</#if> value="<#if item.inventory??>${item.inventory?c}<#else>0</#if>" id="listSortId" class="sort" onkeydown="return checkNumber(event);"></td>
            </tr>
        </#list>
    </#if>
</tbody>
</table>

<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=inventory_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>
<div class="dialog">
	<div class="dialog_title">添加库存</div>
	<div class="dialog_tip">此操作可能会运行几分钟.</div>
	<div class="dialog_tip">运行结果会在几分钟后提示请勿重复操作! </div>
	<div class="dialog_row">默认初始库存：<input id="defualtInventory" type="number" value="0" /> </div>
	<div class="dialog_row"><input onclick="sumbitBackMoney()" class="dialog_btn btn" type="button" value="确定" /><input onclick="hiddenDialog()" class="dialog_btn btn" type="button" value="取消" /> </div>
</div>
 </body></html>