<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>内容列表</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
    $(function () {
    });
</script>
</head>

<body class="mainbody">
<div style="left: 0px; top: 0px; visibility: hidden; position: absolute;" class=""><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/activity/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
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
   document.onkeydown = function(event){
        if((event.keyCode || event.which) == 13){
            __doPostBack('btnSearch','')
        }
   }
</script>

<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>内容列表</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/activity/add"><i></i><span>添加</span></a></li>
        <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
      </ul>
      <div class="menu-list">
        <div class="rule-single-select">
            <select name="categoryId" onchange="javascript:setTimeout(__doPostBack('categoryId', ''), 0)">
                <option <#if categoryId??><#else>selected="selected"</#if> value="">所有类别</option>
                <#if category_list??>
                    <#list category_list as c>
                        <option value="${c.id!""}" <#if categoryId?? && c.id==categoryId>selected="selected"</#if> ><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                    </#list>
                </#if>
            </select>
        </div>
        <div class="rule-single-select">
            <select name="property" onchange="javascript:setTimeout(__doPostBack('property',''), 0)">
                <option value="">所有属性</option>
                <option value="isOnSale" <#if property?? && property=="isOnSale">selected="selected"</#if>>已上架</option>
            </select>
        </div>
      </div>
    </div>
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${keywords!''}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('lbtnSearch','')">查询</a>
      <a id="lbtnViewImg" title="图像列表视图" class="img-view" href="javascript:__doPostBack('lbtnViewImg','')"></a>
      <a id="lbtnViewTxt" title="文字列表视图" class="txt-view" href="javascript:__doPostBack('lbtnViewTxt','')"></a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--文字列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
<tbody>
    <tr class="odd_bg">
        <th width="5%">选择</th>
        <th align="left" width="5%">序号</th>
        <th align="left" width="20%">活动名</th>
        <th align="left" width="10%">城市</th>
        <th align="left" width="40%">门店</th>
        <th align="left" width="10%">排序</th>
        <#--<th align="left" width="12%">所属类别</th>
        <th align="left" width="110">属性</th>-->
        <th width="8%">操作</th>
    </tr>
    
    <#if activity_page?? && activity_page.content??>
    <#list activity_page.content as activity>
        <tr>
            <td align="center">
                <span class="checkall" style="vertical-align:middle;">
                    <input id="listChkId" type="checkbox" name="listChkId" value="${activity_index}" >
                </span>
                <input type="hidden" name="listId" id="listId" value="${activity.id?c}">
            </td>
            <td>${activity.id?c}</td>
            <td><a href="/Verwalter/activity/edit?id=${activity.id?c}">${activity.name!"无"}</a></td>
            <td>${activity.cityName!"无"}</td>
            <#--<td>
                <#if category_list?? && content.categoryId??>
                    <#list category_list as cat>
                        <#if cat.id == content.categoryId>
                            ${cat.title!""}
                            <#break>
                        </#if>
                    </#list>
                </#if>
            </td>-->
            <td><#if activity.siteName??>${activity.siteName!''}</#if></td>
            <td>
                <input name="listSortId" type="text" disabled="" value="${activity.sortId!""}" id="listSortId" class="sort" onkeydown="return checkNumber(event);">
            </td>
            <#--<td>
              <div class="btn-tools">
                <a title="上架/下架" class="hot <#if activity.isOnSale?? && activity.isOnSale>selected</#if>" href="javascript:__doPostBack('btnOnSale','${activity.id?c!''}')"></a>
                <a title="改价" class="change" href="javascript:showDialogChangePrice('${activity.id!""}')"></a>
                <a title="改价记录" class="record" href="javascript:showDialogPriceLog('${activity.id!""}')"></a>
                
                <a id="rptList1_ctl01_lbtnIsTop" title="设置置顶" class="top" href="javascript:__doPostBack('rptList1$ctl01$lbtnIsTop','')"></a>
                <a id="rptList1_ctl01_lbtnIsRed" title="设置推荐" class="red" href="javascript:__doPostBack('rptList1$ctl01$lbtnIsRed','')"></a>
                <a id="rptList1_ctl01_lbtnIsHot" title="设置热门" class="hot" href="javascript:__doPostBack('rptList1$ctl01$lbtnIsHot','')"></a>
                <a id="rptList1_ctl01_lbtnIsSlide" title="设置幻灯片" class="pic" href="javascript:__doPostBack('rptList1$ctl01$lbtnIsSlide','')"></a>
                
              </div>
            </td>-->
            <td align="center">
                <a href="/Verwalter/activity/edit?id=${activity.id?c}">修改</a>
            </td>
        </tr>
    </#list>
    </#if>
</tbody>
</table>

<!--/文字列表-->

<!--图片列表-->

<!--/图片列表-->

<!--内容底部-->
<#assign PAGE_DATA=activity_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>
</body>
</html>