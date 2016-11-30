<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑信息</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/zh_CN.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();
    
    // 添加赠品
    $("#addItem").click(function(){
        showDialogItem();
    });
       
    //创建促销赠品窗口
    function showDialogItem(obj) {
        var objNum = arguments.length;
        
        var giftDialog = $.dialog({
            id: 'giftDialogId',
            lock: true,
            max: false,
            min: false,
            title: "商品",
            content: 'url:/Verwalter/goods/list/dialog/price?total=' + $("#var_box_gift").children("tr").length<#if pricelist??> + '&priceId='+${pricelist.id?c}</#if>,
            width: 900,
            height: 600
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            giftDialog.data = obj;
        }
    }
    
    //删除促销赠品节点
    function delGiftNode(obj) {
        $(obj).parent().parent().remove();
    }
    
});

//创建促销赠品窗口
function show_goods_gift_dialog(obj) {
    var objNum = arguments.length;
    var zengpinDialog = $.dialog({
        id: 'zengpinhDialogId',
        lock: true,
        max: false,
        min: false,
        title: "促销赠品",
        content: 'url:/Verwalter/goods/list/dialog/price'<#if pricelist??>+ '?priceId='+${pricelist.id?c}</#if>,
        width: 900,
        height: 600
    });
    //如果是修改状态，将对象传进去
    if (objNum == 1) {
        zengpinDialog.data = obj;
    }
}
    
//删除促销商品节点
function del_goods_gift(obj) {
    $(obj).parent().parent().remove();
    $("#totalGift").val(parseInt($("#totalGift").val())-1);
}

//选择价目表 zhangji 
function pricelistSelect(id) {
	location.href="/Verwalter/pricelist/itemEdit?id="+id <#if pricelist??>+ '&priceId='+${pricelist.id?c}</#if>;
}

</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/pricelist/itemSave" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input name="menuId" type="text" value='${mid!""}' style="display:none;">
<input name="channelId" type="text" value='${cid!""}' style="display:none">
<!--导航栏-->
<div class="location">
    <a href="/Verwalter/pricelist/list" class="back"><i></i><span>
        返回列表页</span></a> 
    <a href="/Verwalter/center" class="home">
    <i></i><span>首页</span></a>
    <i class="arrow"></i>
    <span>编辑信息</span>
</div>
<div class="line10">
</div>
<!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap" >
                <ul>
                    <li><a class="selected">价目表信息</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
    <dl>
        <dt>所属价目表</dt>
        <dd>
            <div class="rule-single-select">
            <select name="id" datatype="*" sucmsg=" " onchange="javascript:pricelistSelect(this.value);">
                <option value="" selected="selected">请选择</option>
                <#if pricelist_list??>
                	<#list pricelist_list as item>
                		<option value="<#if item??>${item.id?c}</#if>" <#if pricelist??&&pricelist.id == item.id>selected="selected"</#if>>${item.name!''}</option>
                	</#list>
                </#if>	
            </select>
            </div>
        </dd>
    </dl>

    <#if pricelist??>
    <#--<input type="hidden" name="id" value="<#if pricelist.id??>${pricelist.id?c!''}</#if>" />-->
    <input type="hidden" id="totalGift" name="totalItem" value="${pricelist.totalItem!'0'}" />
    <input type="hidden" name="priceListNumber" value="${pricelist.priceListNumber!''}" />
    <input type="hidden" name="cityName" value="${pricelist.cityName!''}" />
    <input type="hidden" name="sortId" value="${pricelist.sortId!''}" />
    <input type="hidden" name="name" value="${pricelist.name!''}" />
    <input type="hidden" name="cityId" value="<#if pricelist.cityId??>${pricelist.cityId?c}</#if>" />
    <input type="hidden" name="username" value="${pricelist.username!''}" />
    <input type="hidden" name="username" value="${pricelist.companyName!''}" />
        <dl>
            <dt>类型描述</dt>
            <dd>
                ${pricelist.priceTypeDesc!''}
            </dd>
        </dl>
        <dl>
            <dt>选择商品</dt>
            <dd>
                <a id="addItem" class="icon-btn add"><i></i><span>添加商品</span></a>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt></dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <thead>
                        <tr>
                            <th width="6%">
                                排序
                            </th>
                            <th width="10%">
                                商品ID
                            </th>
                            <th width="38%">
                                商品标题
                            </th>
                            <th width="10%">
                                虚拟销售价
                            </th>
                            <th width="10%">
                                实际销售价
                            </th>
                            <th width="10%">
                                虚拟进货价
                            </th>
                            <th width="10%">
                                实际进货价
                            </th>
                            <th width="6%">
                                操作
                            </th>
                        </tr>
                    </thead>
                    <tbody id="var_box_gift">
                        <#if pricelist.priceItemList??>
                            <#list pricelist.priceItemList as gift>
                                <tr class="td_c">
                                	<input type="hidden" name="priceItemList[${gift_index}].priceListId" class="td-input" value="<#if gift.priceListId??>${gift.priceListId?c!''}</#if>" >
	                                <input type="hidden" name="priceItemList[${gift_index}].priceListNumber" class="td-input" value="${gift.priceListNumber!''}" >
		                            <input type="hidden" name="priceItemList[${gift_index}].priceListName" class="td-input" value="${gift.priceListName!''}" >
		                            <input type="hidden" name="priceItemList[${gift_index}].dispatch" id="dispatch" class="td-input" value="${gift.dispatch!''}" >
		                            <input type="hidden" name="priceItemList[${gift_index}].cityName" class="td-input" value="${gift.cityName!''}" >
		                            <input type="hidden" name="priceItemList[${gift_index}].companyName" id="companyName" class="td-input" value="${gift.companyName!''}" >
		                            <input type="hidden" name="priceItemList[${gift_index}].isCommendIndex"  id="isCommendIndex" class="td-input" value="<#if gift.isCommendIndex??>${gift.isCommendIndex?c}</#if>" >
                                    <td>
                                        <input type="hidden" name="priceItemList[${gift_index}].id"  value="${gift.id?c!''}">
                                        <input type="text" name="priceItemList[${gift_index}].sortId" class="td-input" value="${gift.sortId!''}" style="width:90%;">
                                    </td>
                                    <td><input type="text" id="id" name="priceItemList[${gift_index}].goodsId" class="td-input" value="${gift.goodsId!''}" style="width:90%;"></td>
                                    <td>
                                        <input type="text" id="title" name="priceItemList[${gift_index}].goodsTitle" class="td-input" value="${gift.goodsTitle!''}" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="salePrice" name="priceItemList[${gift_index}].salePrice" class="td-input" value="<#if gift.salePrice??>${gift.salePrice?string("0.00")}<#else>0.00</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="realSalePrice" name="priceItemList[${gift_index}].realSalePrice" class="td-input" value="<#if gift.realSalePrice??>${gift.realSalePrice?string("0.00")}<#else>0.00</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="stockPrice" name="priceItemList[${gift_index}].stockPrice" class="td-input" value="<#if gift.stockPrice??>${gift.stockPrice?string("0.00")}<#else>0.00</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="realStockPrice" name="priceItemList[${gift_index}].realStockPrice" class="td-input" value="<#if gift.realStockPrice??>${gift.realStockPrice?string("0.00")}<#else>0.00</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <i class="icon"></i>
                                        <a title="编辑" class="img-btn edit operator" onclick="show_goods_gift_dialog(this);">编辑</a>
                                        <a title="删除" class="img-btn del operator" onclick="del_goods_gift(this);">删除</a>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </dd>
        </dl>
    </#if>    
    </div>
    <div class="tab-content" style="display: none;">
       
    </div>
    
    
    
    
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
            <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
</body>
</html>