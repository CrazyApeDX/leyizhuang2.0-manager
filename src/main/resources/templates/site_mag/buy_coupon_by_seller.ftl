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
<style type="text/css">
.dialog{
	position: fixed;
	_position:absolute;
	z-index:1;
	top: 40%;
	left: 50%;
	margin: -141px 0 0 -201px;
	width: 400px;
	height:350px;
	line-height: 210px;
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
	margin-top: 10px;
	margin-bottom: 10px;
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
        var hidden_username=$("#hidden_username").val();
        console.log(hidden_username);
        
        var combinationDialog = $.dialog({
            id: 'combinationDialogId',
            lock: true,
            max: false,
            min: false,
            title: "商品组合",
            content: 'url:/Verwalter/buy/coupon/by/seller/dialog?username=' + hidden_username,
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

//选择买券用户
    function showUserSelection(obj) {
    	if(null != $("#realName")){
    		$("#realName").remove();
    	}
    	if(null != $("#username")){
    		$("#username").remove();
    	}
        var objNum = arguments.length;
        
        var combinationDialog = $.dialog({
            id: 'combinationDialogId',
            lock: true,
            max: false,
            min: false,
            title: "选择会员信息",
            content: 'url:/Verwalter/buy/coupon/by/seller/dialog/user?username=' + $("#username").val(),
            width: 800,
            height: 550
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            combinationDialog.data = obj;
        }
    }
    
//选择代买券导购
    function showSellerSelection(obj) {
    	if(null != $("#sellerRealName")){
    		$("#sellerRealName").remove();
    	}
    	if(null != $("#sellerUsername")){
    		$("#sellerUsername").remove();
    	}
        var objNum = arguments.length;
        
        var combinationDialog = $.dialog({
            id: 'combinationDialogId',
            lock: true,
            max: false,
            min: false,
            title: "选择导购信息",
            content: 'url:/Verwalter/buy/coupon/by/seller/dialog/seller?username='+$("#hidden_username").val(),
            width: 800,
            height: 550
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            combinationDialog.data = obj;
        }
    }

//查看赠品
    function showPresents() {
    	var ids = [];
	    var numbers = [];
	    var coupons = [];
	    var totalPrice = 0.00;
	    var pos = 0.00;
	    var cash = 0.00;		
	    var username = $("#hidden_username").val();
		var sellerUsername = $("#hidden_seller_username").val();			
		if (!username || !sellerUsername || username.length != 11 || sellerUsername.length != 11) {
		   $("#btnSubmit").attr("onclick", "getInfo();");
		   return; 			
		 }    		
		 ids = [];   		
		 numbers = [];   		
		 coupons = [];
		 var idArray = $("input[name='combList[${comb_index!'0'}].goodsId']");
		 if (!idArray || idArray.length == 0) {
		    $("#btnSubmit").attr("onclick", "getInfo();");
		    return;
		 }   	
		 var numberArray = $("input[name='combList[${comb_index!'0'}].number']");   		
		 var couponArray = $("input[name='combList[${comb_index!'0'}].couponNumber']");   		
		 for(var i = 0; i < idArray.length; i++) {
		    ids.push(idArray[i].value);
			numbers.push(numberArray[i].value);
			if (numberArray[i].value < couponArray[i].value) {
				coupons.push(numberArray[i].value);
				couponArray[i].value = numberArray[i].value;
			} else {
				coupons.push(couponArray[i].value);
			}			
		 }   		
		 var remark = $("#remark").val();    		
       	
       	 var combinationDialog = $.dialog({
            id: 'combinationDialogId',
            lock: true,
            max: false,
            min: false,
            title: "查看促销赠品",
            content: 'url:/Verwalter/buy/coupon/by/seller/get/present?sellerUsername='+sellerUsername+'&username='+username+'&ids='+ids+'&numbers='+numbers+'&coupons='+coupons,
            width: 800,
            height: 550
        });
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
</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/activity/save" id="form1" onsubmit="return checkDate();">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
<input type="hidden" id="hidden_username" name="hidden_username" />
<input type="hidden" id="hidden_seller_username" name="hidden_seller_username" />
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
    <span>购买产品券</span>
</div>
<div class="line10">
</div>
<!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap" >
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">填写信息</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
        <dl>
            <dt>搜索用户</dt>
            <dd>
                <input name="search_user" id="search_user" type="text" class="input normal" onfocus="showUserSelection(this.username)">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl id="user_name">
            <dt>用户姓名:</dt>
            <#--<dd id="realName"></dd>-->
        </dl>
        <dl id="user_phone">
            <dt>用户手机号码:</dt>
            <#--<dd id="realName"></dd>-->
        </dl>
        <script>
        	var getRealName = function(type) {
        		if (0 === type) {
	        		var username = $("#username").val();
        		} else if (1 === type) {
        			var username = $("#sellerUsername").val();
        		}
        		if (username && username.length === 11) {
        			$.ajax({
        				url : '/Verwalter/buy/coupon/by/seller/get/name',
        				type : 'POST',
        				data : {
        					type : type,
        					username : username
        				},
        				success : function(res) {
        					if (0 === res.status) {
        						if (0 === type) {
	        						$("#realName").html(res.message);
        						} else if (1 === type) {
        							$("#sellerName").html(res.message);
        						}
        					} else {
        						$("#realName").html("");
        						alert(res.message);
        					}
        				}
        			})
        		}
        	}
        </script>
         <dl>
            <dt>搜索导购</dt>
            <dd>
                <input name="search_seller" id="search_seller" type="text" class="input normal" onfocus="showSellerSelection(this.username)">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl id="seller_name">
            <dt>导购姓名:</dt>
            <#--<dd id="seller"></dd>-->
        </dl>
        <dl id="seller_phone">
            <dt>导购手机号码:</dt>
            <#--<dd id="realName"></dd>-->
        </dl>
       
        <dl>
            <dt>购买产品券</dt>
            <dd>
                <a id="addGoods" class="icon-btn add"><i></i><span>添加商品</span></a>
                <span class="Validform_checktip"></span>
                <a id="queryPresent" class="icon-btn i" onclick="showPresents();"><i></i><span>查看赠品</span></a>
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
                                商品
                            </th>
                            <th width="10%">
                               数量
                            </th>
                            <th>
                            	使用产品现金券	
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
            <dt>备注</dt>
            <dd>
                <input name="remark" id="remark" type="text" class="input normal">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
    </div>
    
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
            <input type="button" onclick="getInfo();" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" >
            <script>
            	var ids = [];
	    		var numbers = [];
	    		var coupons = [];
	    		var totalPrice = 0.00;
	    		var pos = 0.00;
	    		var cash = 0.00;
		    		
		    	var getInfo = function() {
		    	
	    			$("#btnSubmit").removeAttr("onclick");
	    			
		    		var username = $("#hidden_username").val();
		    		var sellerUsername = $("#hidden_seller_username").val();
		    		
		    		if (!username || !sellerUsername || username.length != 11 || sellerUsername.length != 11) {
		    			$("#btnSubmit").attr("onclick", "getInfo();");
		    			return;
		    		}
		    	
		    		ids = [];
		    		numbers = [];
		    		coupons = [];
		    		var idArray = $("input[name='combList[${comb_index!'0'}].goodsId']");
		    		if (!idArray || idArray.length == 0) {
		    			$("#btnSubmit").attr("onclick", "getInfo();");
		    			return;
		    		}
		    		var numberArray = $("input[name='combList[${comb_index!'0'}].number']");
		    		var couponArray = $("input[name='combList[${comb_index!'0'}].couponNumber']");
		    		for(var i = 0; i < idArray.length; i++) {
		    			ids.push(idArray[i].value);
		    			numbers.push(numberArray[i].value);
		    			if (numberArray[i].value < couponArray[i].value) {
	    					coupons.push(numberArray[i].value);
	    					couponArray[i].value = numberArray[i].value;
		    			} else {
		    				coupons.push(couponArray[i].value);
		    			}
		    		}
		    		
		    		var remark = $("#remark").val(); 
		    		
		    		$("#username").attr("readOnly", true);
		    		$("#sellerUsername").attr("readOnly", true);
		    		
		    		$.ajax({
		    			url : '/Verwalter/buy/coupon/by/seller/count',
		    			method : 'POST',
		    			traditional: true,
		    			data : {
		    				sellerUsername : sellerUsername,
		    				username : username,
		    				ids : ids,
		    				numbers : numbers,
		    				coupons : coupons,
		    				remark : remark
		    			},
		    			success : function(res) {
		    				if (0 === res.status) {
		    					total = res.total;
		    					$("#money").html(total);
		    					<#--
	    						$("#balance").attr("placeholder", "预存款（还剩余" + res.balance + "）");
		    					-->
		    					$("#myDialog").show();
		    				} else {
		    					alert(res.message);
		    					$("#username").removeAttr("readOnly");
		    					$("#sellerUsername").removeAttr("readOnly");
	    						$("#btnSubmit").attr("onclick", "getInfo();");
		    				}
		    			}
		    		});
		    	}
		    </script>
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
    <div class="dialog" id="myDialog">
		<div class="dialog_title">支付：<span id="money"></span></div>
		<div class="dialog_row"><input placeholder="现金" id="cash" style="height:30px;width:175px;" type="number" /> </div>
		<div class="dialog_row"><input placeholder="POS" id="pos" style="height:30px;width:175px;" type="number" /> </div>
		<div class="dialog_row"><input placeholder="其他" id="other" style="height:30px;width:175px;" type="number" /> </div>
		<#--
		<div class="dialog_row"><input placeholder="预存款" id="balance" style="height:30px;width:175px;" type="number" /> </div>
		-->
		<div class="dialog_row">
			<div class="input-date">
	            <input id="realPayTime" name="beginDate" placeholder="收款时间" id="beginDate" type="text" value="<#if activity??>${activity.beginDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
	            <i>日期</i>
	        </div>
		</div>
		<div class="dialog_row">
			<input onclick="buy();" class="dialog_btn btn" type="button" value="确定" />
			<input onclick="hiddenDialog()" class="dialog_btn btn" type="button" value="取消" /> 
		</div>
	</div>
	
	<script>
		var hiddenDialog = function() {
			$("#username").removeAttr("readOnly");
			$("#sellerUsername").removeAttr("readOnly");
			$("#btnSubmit").attr("onclick", "getInfo();");
			$("#myDialog").hide();
		}
		
		var count = 0;
		
		var buy = function() {
			$("#submitBuy").removeAttr("onclick");
			
			count += 1;
			
			var cash = $("#cash").val();
			var pos = $("#pos").val();
			var other = $("#other").val();
			
			var money = $("#money").html();
			
			var realPayTime = $('#realPayTime').val();
				
			if (!cash) {
	    		cash = 0.0;
	    	}
	    	if (!pos) {
	    		pos = 0.0;
	    	}
	    	if (!other) {
	    		other = 0.0;
	    	}
	    	if (!realPayTime) {
	    		alert("请填写真实收款时间");
	    		$("#submitBuy").attr("onclick", "buy();");
	    		count = 0;
	    		return;
	    	}
						
			if (Number(cash) + Number(pos) + Number(other) === Number(money)) {
			
				var username = $("#hidden_username").val();
		    	var sellerUsername = $("#hidden_seller_username").val();
			
				if (count === 1) {
					$.ajax({
						url : '/Verwalter/buy/coupon/by/seller/create/order',
						method : 'POST',
						traditional : true,
						data : {
							username : username,
		    				ids : ids,
		    				numbers : numbers,
		    				coupons : coupons,
		    				pos : pos,
		    				cash : cash,
		    				other : other,
		    				realPayTime: realPayTime
						},
						success : function(res) {
							if (0 === res.status) {
								alert("购买成功！");
								$("#myDialog").hide();
								window.location.href = "/Verwalter/center";
							} else {
								alert(res.message);
								$("#submitBuy").attr("onclick", "buy();");
								count = 0;
							}
						}
						
					});
				} else {
					console.log(count);
				}
			} else {
				$("#submitBuy").attr("onclick", "buy();");
				count = 0;
			}	
		}
	</script>
</body>
</html>