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
    
    //初始化上传控件
    $(".upload-img").each(function () {
        $(this).InitSWFUpload({ 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf"
        });
    });
    
    $(".upload-show360").each(function () {
        $(this).InitSWFUpload_show360({ 
            btntext: "批量上传", 
            btnwidth: 66, 
            single: false, 
            water: true, 
            thumbnail: true, 
            filesize: "5120", 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf", 
            filetypes: "*.jpg;*.jpge;*.png;*.gif;" 
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
    
    //（团购缩略图）
    var groupPic = $("#groupSaleImage").val();
    if (groupPic == "" || groupPic == null) {
        $("#thumb_ImgUrl_show2").hide();
    }
    else {
        $("#thumb_ImgUrl_show2").html("<ul><li><div class='img-box1'><img src='" + groupPic + "' bigsrc='" + groupPic + "' /></div></li></ul>");
        $("#thumb_ImgUrl_show2").show();
    }

    $("#groupSaleImage").blur(function () {
        var groupPic = $("#groupSaleImage").val();
        if (groupPic == "" || groupPic == null) {
            $("#thumb_ImgUrl_show2").hide();
        }
        else {
            $("#thumb_ImgUrl_show2").html("<ul><li><div class='img-box1'><img src='" + groupPic + "' bigsrc='" + groupPic + "' /></div></li></ul>");
            $("#thumb_ImgUrl_show2").show();
        }
    });
    
    //设置封面图片的样式
    $(".photo-list ul li .img-box img").each(function () {
        if ($(this).attr("src") == $("#hidFocusPhoto").val()) {
            $(this).parent().addClass("selected");
        }
    });
    
    // 根据类型载入参数
    $("#categoryId").change(function(){
        $.ajax({
            url : '/Verwalter/goods/edit/parameter/'+$(this).val(),
            type : 'POST',
            success : function(res) {
                $("#id-param-sec").html(res);
            }
        });
    });
    
    // 添加赠品
    $("#addGift").click(function(){
        showDialogGift();
    });
    
    // 添加组合
    $("#addCombination").click(function(){
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
            content: 'url:/Verwalter/goods/list/dialog/gift?total=' + $("#var_box_gift").children("tr").length,
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
            content: 'url:/Verwalter/goods/list/dialog/comb?total=' + $("#var_box_comb").children("tr").length,
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
    $("#outFactoryPrice").change(function(){
        var p1 = $.trim($('#outFactoryPrice').val());
        var p2 = $.trim($('#returnPrice').val())
        if (isNaN(p1) || p1=="") { p1 = 0 }
        if (isNaN(p2) || p2== "") { p2 = 0 }
        
        $("#idComputeSalePrice").val((parseFloat(p1) + parseFloat(p2)));
    });
    
    $("#returnPrice").change(function(){
        var p1 = $.trim($('#outFactoryPrice').val());
        var p2 = $.trim($('#returnPrice').val())
        if (isNaN(p1) || p1=="") { p1 = 0 }
        if (isNaN(p2) || p2== "") { p2 = 0 }
        
        $("#idComputeSalePrice").val((parseFloat(p1) + parseFloat(p2)));
    });
    
    // 判断积分购买限额不能大于最高返现额
    $("#pointLimited").change(function(){
        var point = $.trim($('#pointLimited').val());
        var price = $.trim($('#returnPrice').val())
        if (isNaN(point) || point=="") { p1 = 0 }
        if (isNaN(price) || price== "") { p2 = 0 }
        
        if (parseFloat(price) < parseFloat(point))
        {
            alert("购买积分限额不能大于最高返现额!");
            $(this).val("0");
        }
    });
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
        content: 'url:/Verwalter/goods/list/dialog/gift',
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
        title: "促销赠品",
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

</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/change/diy/save" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input name="menuId" type="text" value='${mid!""}' style="display:none;">
<input name="channelId" type="text" value='${cid!""}' style="display:none">
<input name="id" type="text" value='<#if goods??>${goods.id?c}</#if>' style="display:none">
<!--导航栏-->
<div class="location">
    <a href="/Verwalter/goods/list?__VIEWSTATE=${__VIEWSTATE!""}" class="back"><i></i><span>
        返回列表页</span></a> 
    <a href="/Verwalter/center" class="home">
    <i></i><span>首页</span></a>
    <i class="arrow"></i>
    <span>转移门店</span>
</div>
<div class="line10">
</div>
<!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap" >
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">转移门店</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
        <dl>
            <dt>起始门店</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="start" id="start" datatype="*" sucmsg=" ">
                        <#if !diy_site_list??>
                        <option value="">请选择门店...</option>
                        </#if>
                            <#if diy_site_list??>
                                <#list diy_site_list as c>
                                    <option value="${c.id?c}">${c.title!''}</option>
                                </#list>
                            </#if>
                    </select>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>目标门店</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="end" id="end" datatype="*" sucmsg=" ">
                        <#if !diy_site_list??>
                        <option value="">请选择门店...</option>
                        </#if>
                            <#if diy_site_list??>
                                <#list diy_site_list as c>
                                    <option value="${c.id?c}">${c.title!''}</option>
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
            <input type="button" onclick="change();" id="submitButton" name="btnSubmit" value="提交保存" class="btn">
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
    <script>
    	function change(){
    		var start = $("#start").val();
    		var end = $("#end").val();
    		if (start === end) {
    			alert("门店一致，不需要转移");
    		} else {
    		
    			$("#submitButton").removeAttr("onclick");
    			
    			$.ajax({
    				url : "/Verwalter/change/diy/save",
    				method : "POST",
    				data : {
    					start : start,
    					end : end
    				},
    				error : function(){
    					alert("未知原因，操作失败！");
    				},
    				success: function(res) {
						alert(res.message);
						$("#submitButton").attr("onclick","change()");
    				}
    			});
    		}
    	}
    </script>
</body>
</html>