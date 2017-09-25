<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        imgLayout();
        $(window).resize(function () {
            imgLayout();
        });
        //图片延迟加载
        $(".pic img").lazyload({ load: AutoResizeImage, effect: "fadeIn" });
        //点击图片链接
        $(".pic img").click(function () {
            //$.dialog({ lock: true, title: "查看大图", content: "<img src=\"" + $(this).attr("src") + "\" />", padding: 0 });
            var linkUrl = $(this).parent().parent().find(".foot a").attr("href");
            if (linkUrl != "") {
                location.href = linkUrl; //跳转到修改页面
            }
        });
    });
    //排列图文列表
    function imgLayout() {
        var imgWidth = $(".imglist").width();
        var lineCount = Math.floor(imgWidth / 222);
        var lineNum = imgWidth % 222 / (lineCount - 1);
        $(".imglist ul").width(imgWidth + Math.ceil(lineNum));
        $(".imglist ul li").css("margin-right", parseFloat(lineNum));
    }
    //等比例缩放图片大小
    function AutoResizeImage(e, s) {
        var img = new Image();
        img.src = $(this).attr("src")
        var w = img.width;
        var h = img.height;
        var wRatio = w / h;
        if ((220 / wRatio) >= 165) {
            $(this).width(220); $(this).height(220 / wRatio);
        } else {
            $(this).width(165 * wRatio); $(this).height(165);
        }
    }
</script>
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/content/list?cid=${cid!""}&mid=${mid!""}" id="form1">
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
<#include "/site_mag/content_list_navi_bar.ftl" />
<!--导航栏-->

<!--工具栏-->
<#include "/site_mag/content_list_tool_bar.ftl" />
<!--/工具栏-->

<!--文字列表-->

<!--/文字列表-->

<!--图片列表-->

<div class="imglist">
<ul style="width: 1189px;">

    <#if content_page?? && content_page.content??>
    <#list content_page.content as content>
    <li style="margin-right: 15.75px;">
        <div class="details <#if !content.imgUrl??>nopic</#if>">
            <div class="check">
                <span class="checkall">
                    <input type="checkbox" name="listChkId" value="${content_index}">
                </span>
                <input type="hidden" name="listId" id="listId" value="${content.id?c}">
            </div>
            <#if content.imgUrl??>
            <div class="pic">
                <img src="${content.imgUrl!""}" data-original="" style="display: inline; width: 247.5px; height: 165px;">
            </div>
            <i class="absbg"></i>
            </#if>
            <h1><span><a href="/Verwalter/article/edit?cid=${cid!""}&mid=${mid!""}&id=${content.id!""}&__VIEWSTATE=${__VIEWSTATE!""}">${content.title!""}</a></span></h1>
            <div class="remark">${content.brief!""}</div>
            <div class="tools">
                <#--
              <a id="rptList2_ctl01_lbtnIsMsg" title="设置评论" class="msg" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsMsg','')"></a>
              <a id="rptList2_ctl01_lbtnIsTop" title="设置置顶" class="top" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsTop','')"></a>
              <a id="rptList2_ctl01_lbtnIsRed" title="设置推荐" class="red" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsRed','')"></a>
              <a id="rptList2_ctl01_lbtnIsHot" title="设置热门" class="hot" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsHot','')"></a>
              <a id="rptList2_ctl01_lbtnIsSlide" title="设置幻灯片" class="pic" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsSlide','')"></a>
                -->
              <input name="listSortId" type="text" disabled="" value="${content.sortId!""}" id="idListSortId" class="sort" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)));">
            </div>
            <div class="foot">
              <p class="time">${content.createTime!""}</p>
              <a href="/Verwalter/article/edit?cid=${cid!""}&mid=${mid!""}&id=${content.id!""}&__VIEWSTATE=${__VIEWSTATE!""}" title="编辑" class="edit">编辑</a>
            </div>
        </div>
    </li>
    </#list>
    </#if>    
</ul>
</div>

<!--/图片列表-->

<!--内容底部-->
<#assign PAGE_DATA=content_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>