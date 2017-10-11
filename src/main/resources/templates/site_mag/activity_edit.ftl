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
    
    //初始化编辑器
    var editor = KindEditor.create('.editor', {
        width: '98%',
        height: '350px',
        resizeType: 1,
        uploadJson: '/Verwalter/editor/upload?action=EditorFile',
        fileManagerJson: '/Verwalter/editor/upload?action=EditorFile',
        allowFileManager: true
    });
    
    //根据城市选择门店
    $("#cityId").change(function(){
        getDiySiteList(this);
        removeGoodsAndGifts();
    });
    
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
        $("#thumb_ImgUrl_show1").hide();
    }
    else {
        $("#thumb_ImgUrl_show1").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
        $("#thumb_ImgUrl_show1").show();
    }

    $("#txtImgUrl").blur(function () {
        var txtPic = $("#txtImgUrl").val();
        if (txtPic == "" || txtPic == null) {
            $("#thumb_ImgUrl_show1").hide();
        }
        else {
            $("#thumb_ImgUrl_show1").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
            $("#thumb_ImgUrl_show1").show();
        }
    });
    
    
    // 添加门店
    $("#addDiySite").click(function(){
        showDialogDiySite();
    });
    
    // 添加赠品
    $("#addGift").click(function(){
        showDialogGift();
    });
    
    // 添加组合
    $("#addGoods").click(function(){
        showDialogCombination();
    });
    
    //创建促销赠品窗口
    function showDialogGift(obj) {
        var objNum = arguments.length;
        var giftDialog = $.dialog({
            id: 'giftDialogId',
            lock: true,
            max: false,
            min: false,
            title: "赠品",
            content: 'url:/Verwalter/goods/list/dialog/gift?total=' + $("#var_box_gift").children("tr").length+'&cityId='+$('#cityId').val(),
            width: 800,
            height: 550
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            giftDialog.data = obj;
        }
    }
    //创建门店
    function showDialogDiySite(obj) {
        var objNum = arguments.length;
        
        var regionId = $("#cityId").val()
        
        if(regionId == "")
        {
            alert("请选择城市！");
            return;
        }
        
        var giftDialog = $.dialog({
            id: 'diySiteDialogId',
            lock: true,
            max: false,
            min: false,
            title: "门店",
            content: 'url:/Verwalter/activity/list/dialog?total=' + $("#var_box_DiySite").children("tr").length + "&regionId=" + $("#cityId").val(),
            width: 800,
            height: 350
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
    
    //创建商品组合窗口
    function showDialogCombination(obj) {
        var objNum = arguments.length;
        
        var combinationDialog = $.dialog({
            id: 'combinationDialogId',
            lock: true,
            max: false,
            min: false,
            title: "商品组合",
            content: 'url:/Verwalter/goods/list/dialog/comb?total=' + $("#var_box_comb").children("tr").length+'&cityId='+$('#cityId').val(),
            width: 800,
            height: 550
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            combinationDialog.data = obj;
        }
    }
    
    //删除商品组合节点
    function delCombinationNode(obj) {
        $(obj).parent().parent().remove();
    }
    
    // 自动计算销售价
    /*
    $("#outFactoryPrice").change(function(){
        var p1 = $.trim($('#outFactoryPrice').val());
        var p2 = $.trim($('#returnPrice').val())
        if (isNaN(p1) || p1=="") { p1 = 0 }
        if (isNaN(p2) || p2== "") { p2 = 0 }
        
        $("#idComputeSalePrice").val((parseFloat(p1) + parseFloat(p2)));
    });
    */     
    
    // 判断粮草购买限额不能大于最高返现额
    $("#pointLimited").change(function(){
        var point = $.trim($('#pointLimited').val());
        var price = $.trim($('#returnPrice').val())
        if (isNaN(point) || point=="") { p1 = 0 }
        if (isNaN(price) || price== "") { p2 = 0 }
        
        if (parseFloat(price) < parseFloat(point))
        {
            alert("购买粮草限额不能大于最高返现额!");
            $(this).val("0");
        }
    });
    function getDiySiteList(object)
    {
        $.ajax({
                url : '/Verwalter/activity/diysite/list/show?regionId='+$(object).val(),
                type : 'POST',
                success : function(res) 
                {
                    $("#id-param-sec").html(res);
                },
                error: function(res)
                {
                    alert("error code : -1 + " + res);
                }
        });
    }
    //清空已选商品好赠品
    function removeGoodsAndGifts(){
    	//删除商品及赠品
    	$('tr[class=td_c]').remove();
    	//修改 商品总算
    	$('#totalComb').val('0');
    	//修改赠品总算
    	$('#totalGift').val('0');
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
        title: "赠送商品",
        content: 'url:/Verwalter/goods/list/dialog/gift',
        width: 800,
        height: 550
    });
    //如果是修改状态，将对象传进去
    if (objNum == 1) {
        zengpinDialog.data = obj;
    }
}
//创建门店窗口
function show_diysite_dialog(obj) {
    var objNum = arguments.length;
    var zengpinDialog = $.dialog({
        id: 'zengpinhDialogId',
        lock: true,
        max: false,
        min: false,
        title: "门店选择",
        content: 'url:/Verwalter/activity/list/dialog',
        width: 800,
        height: 550
    });
    //如果是修改状态，将对象传进去
    if (objNum == 1) {
        zengpinDialog.data = obj;
    }
}

//创建商品组合窗口
function show_goods_comb_dialog(obj) {
    var objNum = arguments.length;
    var zengpinDialog = $.dialog({
        id: 'zengpinhDialogId',
        lock: true,
        max: false,
        min: false,
        title: "活动赠品",
        content: 'url:/Verwalter/goods/list/dialog/comb',
        width: 800,
        height: 550
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

//删除商品组合节点
function del_goods_comb(obj) {
    $(obj).parent().parent().remove();
    $("#totalComb").val(parseInt($("#totalComb").val())-1);
}
//判断活动结束时间不能小于开始时间
function checkDate(){
	var beginDate= $('#beginDate').val();
	var finishDate=$('#finishDate').val();
	if(finishDate<=beginDate){
		alert("亲,活动结束时间不能小于开始时间");
		return false;
	}
	
}
  <#if fns??>
  	$(document).ready(function(){
  		alert("保存成功");
  		location.href='/Verwalter/activity/list';
  	});
  <#else>
  	console.log("aaa");	
  </#if>
</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/activity/save" id="form1" onsubmit="return checkDate();">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input name="menuId" type="text" value='${mid!""}' style="display:none;">
<input name="channelId" type="text" value='${cid!""}' style="display:none">
<input name="id" type="text" value='<#if activity??>${activity.id?c}</#if>' style="display:none">
<!--导航栏-->
<div class="location">
    <#--<a href="/Verwalter/goods/list" class="back"><i></i><span>
        返回列表页</span></a> -->
    <a href="/Verwalter/center" class="home">
    <i></i><span>首页</span></a>
    <i class="arrow"></i>
    <span>活动管理</span>
</div>
<div class="line10">
</div>
<!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap" >
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">基本信息</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
        <dl>
            <dt>活动名称</dt>
            <dd>
                <input name="name" type="text" value="<#if activity??>${activity.name!""}</#if>" class="input normal" datatype="*" <#if !activity??> ajaxurl="/Verwalter/activity/check/name"</#if> sucmsg=" ">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
          <dl>
            <dt>赠送类型</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="giftType" datatype="*" sucmsg=" ">
                        <#if !activity??>
                        <option value="">请选择类别...</option>
                        </#if>
                        <option value="0" <#if activity?? && activity.giftType?? && activity.giftType == 0>selected="selected"</#if> >赠送商品</option>
                        <option value="1" <#if activity?? && activity.giftType?? && activity.giftType == 1>selected="selected"</#if> >赠送产品卷</option>
                    </select>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>城市</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="cityId" id="cityId"  datatype="*" sucmsg=" ">
                        <#if !activity??>
                        <option value="">请选择城市...</option>
                        </#if>
                        <#if city_list??> 
                            <#list city_list as c>
                                <option value="${c.id?c}" <#if activity?? && activity.cityId==c.id>selected="selected"</#if>>${c.cityName!''}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>活动图片</dt>
            <dd>
                <input id="txtImgUrl" name="activityImg" type="text" value="<#if activity??>${activity.activityImg!""}</#if>" class="input normal upload-path">
                <div class="upload-box upload-img"></div>
                <div id="thumb_ImgUrl_show1" class="photo-list thumb_ImgUrl_show">
                </div>
            </dd>
        </dl>
        <dl>
            <dt>排序号</dt>
            <dd>
                <input name="sortId" type="text" value="<#if activity??>${activity.sortId}<#else>99</#if>" class="input normal" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>活动开始时间</dt>
            <dd>
                <div class="input-date">
                    <input name="beginDate" id="beginDate" type="text" value="<#if activity??>${activity.beginDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>结束到期时间</dt>
            <dd>
                <div class="input-date">
                    <input name="finishDate" id="finishDate" type="text" value="<#if activity??>${activity.finishDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>促销类型</dt>
            <dd>
                <div class="rule-multi-radio multi-radio">
                    <span>
                        <input type="radio" name="isCombo" value="1" <#if !(activity?? && activity.isCombo?? && activity.isCombo==false) >checked="checked"</#if>>
                        <label>组合促销</label>
                        <input type="radio" name="isCombo" value="0" <#if activity?? && activity.isCombo?? && activity.isCombo==false>checked="checked"</#if>>
                        <label>阶梯促销</label>
                    </span>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>折扣条件</dt>
            <dd>
                <div class="rule-multi-radio multi-radio">
                    <span>
                        <input type="radio" name="isEnoughMoney" value="0" <#if activity??==false ||!activity.isEnoughMoney??||activity.isEnoughMoney==false>checked="checked"</#if>>
                        <label>数量</label>
                        <input type="radio" name="isEnoughMoney" value="1" <#if activity?? && activity.isEnoughMoney?? && activity.isEnoughMoney==true>checked="checked"</#if>>
                        <label>金额</label>
                    </span>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>最低购买金额</dt>
            <dd>
                <input name="totalPrice" type="text" value="<#if activity??&&activity.totalPrice??>${activity.totalPrice?string("0.00")}<#else>0.00</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " errormsg="">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>最低购买总量</dt>
            <dd>
                <input name="totalNumber" type="text" value="<#if activity??&&activity.totalNumber??>${activity.totalNumber!'1'}<#else>1</#if>" class="input normal" datatype="n" sucmsg=" " errormsg="请输入一个正确的整数">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <div id="id-param-sec">
            <#if diysite_list??>
                <#include "/site_mag/activity_diysite_list_detail.ftl" />
            </#if>
        </div>
        <dl>
            <dt>活动商品</dt>
            <dd>
                <a id="addGoods" class="icon-btn add"><i></i><span>添加活动商品</span></a>
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
                                活动商品
                            </th>
                            <th width="10%">
                               数量
                            </th>
                            <th width="10%">
                                价格
                            </th>
                            <th width="6%">
                                操作
                            </th>
                        </tr>
                    </thead>
                    <tbody id="var_box_comb">
                        <input type="hidden" id="totalComb" name="totalGoods" value="<#if activity??>${activity.totalGoods!'0'}<#else>0</#if>" />
                        <#if activity?? && activity.combList??>
                            <#list activity.combList as comb>
                                <tr class="td_c">
                                    <td>
                                        <input name="combList[${comb_index}].id?c" type="hidden" value="${comb.id?c}">
                                        <input name="combList[${comb_index}].coverImageUri" type="hidden" value="${comb.coverImageUri!''}">
                                        <input type="text" name="combList[${comb_index}].sortId" class="td-input" value="${comb.sortId!''}" style="width:90%;">
                                    </td>
                                    <td><input type="text" id="id" name="combList[${comb_index}].goodsId" class="td-input" value="<#if comb.goodsId??> ${comb.goodsId?c}</#if>" style="width:90%;"></td>
                                    <td>
                                        <input type="text" id="title" name="combList[${comb_index}].goodsTitle" class="td-input" value="${comb.goodsTitle!''}" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="number" name="combList[${comb_index}].number" class="td-input" value="<#if comb.number??>${comb.number?c}</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="price" name="combList[${comb_index}].goodsPrice" class="td-input" value="<#if comb.goodsPrice??>${comb.goodsPrice?string("0.00")}</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <i class="icon"></i>
                                        <a title="编辑" class="img-btn edit operator" onclick="show_goods_comb_dialog(this);">编辑</a>
                                        <a title="删除" class="img-btn del operator" onclick="del_goods_comb(this);">删除</a>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </dd>
        </dl>
        <dl>
            <dt>赠送商品</dt>
            <dd>
                <a id="addGift" class="icon-btn add"><i></i><span>添加赠送商品</span></a>
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
                                赠送商品
                            </th>
                            <th width="10%">
                                数量
                            </th>
                            <th width="10%">
                                价格
                            </th>
                            <th width="6%">
                                操作
                            </th>
                        </tr>
                    </thead>
                    <tbody id="var_box_gift">
                        <input type="hidden" id="totalGift" name="totalGift" value="<#if activity??>${activity.totalGift!'0'}<#else>0</#if>" />
                        <#if activity?? && activity.giftList??>
                            <#list activity.giftList as item>
                                <tr class="td_c">
                                    <td>
                                        <input name="giftList[${item_index}].id?c" type="hidden" value="${item.id?c}">
                                        <input name="giftList[${item_index}].coverImageUri" type="hidden" value="${item.coverImageUri!''}">
                                        <input type="text" name="giftList[${item_index}].sortId" class="td-input" value="${item.sortId!''}" style="width:90%;">
                                    </td>
                                    <td><input type="text" id="id" name="giftList[${item_index}].goodsId" class="td-input" value="<#if item.goodsId??>${item.goodsId?c}</#if>" style="width:90%;"></td>
                                    <td>
                                        <input type="text" id="title" name="giftList[${item_index}].goodsTitle" class="td-input" value="${item.goodsTitle!''}" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="number" name="giftList[${item_index}].number" class="td-input" value="<#if item.number??>${item.number?c}</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="price" name="giftList[${item_index}].goodsPrice" class="td-input" value="<#if item.goodsPrice??>${item.goodsPrice?string("0.00")}</#if>" style="width:90%;">
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
        <dl>
            <dt>立减金额</dt>
            <dd>
                <input name="subPrice" type="text" value="<#if activity??&&activity.subPrice??>${activity.subPrice?string("0.00")}<#else>0.00</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " errormsg="">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        
        <dl>
            <dt>促销对象</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="activityTarget" id="activityTarget"  datatype="*" sucmsg=" ">
                        <option value="0" <#if activity?? && activity.activityTarget==0>selected="selected"</#if>>全部</option>
                        <option value="1" <#if activity?? && activity.activityTarget==1>selected="selected"</#if>>导购</option>
                        <option value="2" <#if activity?? && activity.activityTarget==2>selected="selected"</#if>>会员</option>
                    </select>
                </div>
            </dd>
        </dl>
    </div>
    
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
            <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" >
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
</body>
</html>