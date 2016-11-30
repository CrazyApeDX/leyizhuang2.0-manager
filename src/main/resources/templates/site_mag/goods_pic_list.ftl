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
        <#if left_goods??>
        showLeftGoods(${left_goods?c});
        </#if>
        $(window).resize(function () {
            imgLayout();
        });
        
        //显示为绑定的类别商品的数量
        
        
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
    
    function showLeftGoods(number) {
    alert("还有"+ number + "没有绑定类别");
    }
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
    
    //创建改价窗口
    function showDialogChangePrice(goodsId) {
        $.dialog({
            id: 'giftDialogId',
            lock: true,
            max: false,
            min: false,
            title: "改价",
            content: 'url:/Verwalter/goods/price?goodsId=' + goodsId,
            width: 600,
            height: 200
        });
    }
    
    // 改价记录
    function showDialogPriceLog(goodsId) {
        $.dialog({
            id: 'giftDialogId',
            lock: true,
            max: false,
            min: false,
            title: "改价记录",
            content: 'url:/Verwalter/goods/price/log?goodsId=' + goodsId,
            width: 800,
            height: 500
        });
    }
</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/goods/list" id="form1">
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
   
function confirmCopy(id)
{
    $.dialog.confirm("确定复制该商品吗？", function () {
        window.location.href = "/Verwalter/goods/copy?id=" + id;
    });
}
</script>

<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i
  <span>内容列表</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/goods/edit?__VIEWSTATE=${__VIEWSTATE!""}"><i></i><span>导入</span></a></li>
        <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a class="all" href="/Verwalter/goods/refresh" ><i></i><span>刷新关联分类</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
      </ul>
      <div class="menu-list">
        <div class="rule-single-select">
            <select name="categoryId" onchange="javascript:setTimeout(__doPostBack('categoryId', ''), 0)">
                <option <#if categoryId??><#else>selected="selected"</#if> value="">所有类别</option>
                <Option value="-1" <#if categoryId?? && categoryId==-1>selected="selected"</#if> >未绑定类别</option>
                <#if category_list??>
                    <#list category_list as c>
                        <option value="${c.id!""}" <#if categoryId?? && c.id==categoryId>selected="selected"</#if> ><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                    </#list>
                </#if>
            </select>
        </div>
        <div class="rule-single-select">
            <select name="brandId" onchange="javascript:setTimeout(__doPostBack('btnBrand',''), 0)">
                <option value="" <#if !brandId??>selected="selected"</#if> >所有品牌</option>
                <#if brand_list??>
                    <#list brand_list as brand>
                    <option value="${brand.id?c}" <#if brandId?? && brandId==brand.id>selected="selected"</#if> >${brand.title!''}</option>
                    </#list>
                </#if>
            </select>
        </div>
         <div class="rule-single-select">
            <select name="property" onchange="javascript:setTimeout(__doPostBack('property',''), 0)">
                <option value="">所有属性</option>
                <option value="isOnSale" <#if property?? && property=="isOnSale">selected="selected"</#if>>已上架</option>
                <option value="isNotOnSale" <#if property?? && property=="isNotOnSale">selected="selected"</#if>>已下架</option>
            </select>
        </div>
       <#--
       <div class="rule-single-select">
            <select name="saleType" onchange="javascript:setTimeout(__doPostBack('saleType',''), 0)">
                <option value="">所有</option>
                <option value="flashSale" <#if saleType?? && saleType=="flashSale">selected="selected"</#if>>抢拍</option>
                <option value="groupSale" <#if saleType?? && saleType=="groupSale">selected="selected"</#if>>团购</option>
            </select>
        </div> -->
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

<!--/文字列表-->

<!--图片列表-->

<div class="imglist">
<ul style="width: 1189px;">

    <#if content_page?? && content_page.content??>
    <#list content_page.content as content>
    <li style="margin-right: 15.75px;">
        <div class="details <#if !content.coverImageUri??>nopic</#if>">
            <div class="check">
                <span class="checkall">
                    <input type="checkbox" name="listChkId" value="${content_index}">
                </span>
                <input type="hidden" name="listId" id="listId" value="${content.id?c}">
            </div>
            <#if content.coverImageUri??>
            <div class="pic">
                <img src="${content.coverImageUri!""}" data-original="" style="display: inline; width: 247.5px; height: 165px;">
            </div>
            <i class="absbg"></i>
            </#if>
            <h1><span><a href="/Verwalter/goods/edit?cid=${cid!""}&mid=${mid!""}&id=<#if content.id??>${content.id?c}</#if>&__VIEWSTATE=${__VIEWSTATE!""}">${content.title!""}${content.code!""}</a></span></h1>
            <div class="remark">${content.subTitle!""}</div>
            <div class="tools">
                
                <#--<a title="上架/下架" class="hot <#if content.isOnSale?? && content.isOnSale>selected</#if>" href="javascript:__doPostBack('btnOnSale','${content.id?c!''}')"></a>-->
                <#--><a title="改价" class="change" href="javascript:showDialogChangePrice('${content.id!""}')"></a>
                <a title="改价记录" class="record" href="javascript:showDialogPriceLog('${content.id!""}')"></a> -->
                <input name="listSortId" type="text" value="${content.sortId!""}" class="sort" onkeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)));">
              
              <#--
              <a title="设置评论" class="msg selected" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsMsg','')"></a>
              <a title="设置置顶" class="top" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsTop','')"></a>
              <a title="设置推荐" class="red" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsRed','')"></a>
              <a title="设置热门" class="hot" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsHot','')"></a>
              <a title="设置幻灯片" class="pic" href="javascript:__doPostBack('rptList2$ctl01$lbtnIsSlide','')"></a>
              -->
                
            </div>
            <div class="foot">
              <p class="time"><#if content.onSaleTime??>${content.onSaleTime?string("yyyy-MM-dd HH:mm:ss")}</#if></p>
              <a href="/Verwalter/goods/edit?cid=${cid!""}&mid=${mid!""}&id=<#if content.id??>${content.id?c}</#if>&__VIEWSTATE=${__VIEWSTATE!""}" title="编辑" class="edit">编辑</a>
              <a href="javascript:confirmCopy(${content.id?c});" title="复制商品" class="show">复制</a>
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