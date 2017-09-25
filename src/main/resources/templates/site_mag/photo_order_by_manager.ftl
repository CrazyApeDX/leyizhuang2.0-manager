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
/* 照片浮动小窗口  */
.float_right{
    position: fixed;
    z-index: 9999;
    top: 10px;
    right: 50px;
    width: 100px;
    height: 25%;
}
.float_right img {
	width: 100%;
    height: 100%;
   
}
</style>
<script type="text/javascript">
$(function () {
   
    // 添加组合
    $("#addGoods").click(function(){
        showDialogCombination();
    });
    
   
    //创建商品组合窗口
    function showDialogCombination(obj) {
        var objNum = arguments.length;
        var username=$("#username").val();
        console.log(hidden_username);
        
        var combinationDialog = $.dialog({
            id: 'combinationDialogId',
            lock: true,
            max: false,
            min: false,
            title: "商品组合",
            content: 'url:/Verwalter/photo/order/dialog?username=' + username,
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
  
});


//删除商品组合节点
function del_goods_comb(obj) {
    $(obj).parent().parent().remove();
    $("#totalComb").val(parseInt($("#totalComb").val())-1);
}


//放大展示照片
function showPhotoOrderImg(node){
	var width = $(node).css("width");
	
	if(width == "100px"){
		$(node).css("width","400px");
		$(node).css("height","95%");
	}else{
		$(node).css("width","100px");
		$(node).css("height","25%");
	}
}


</script>
</head>
<body class="mainbody">
<form method="post" action="" id="form1" onsubmit="return checkDate();">
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
    <span>拍照下单</span>
</div>
<div class="line10">
</div>
<!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap" >
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">订单信息</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
    	<input type="hidden" id="photoOrderId" value="<#if photoOrder??>${photoOrder.id!'' }</#if>"/>
        <table>
        	<#if photoOrder?? && photoOrder.orderNumber??>
        		<tr>
        			<td>
        				<dl id="order_number">
				            <dt>订单号:</dt>
				             <dd>
				                <input id="orderNumber" type="text" class="input normal" value="<#if photoOrder??>${photoOrder.orderNumber!'' }</#if>" readonly="readonly">
				            </dd>
				        </dl>
        			</td>
        			<td></td>
        		</tr>
        	</#if>
        	
        	<tr>
        		<td>
        			<dl id="user_name">
			            <dt>用户姓名:</dt>
			             <dd>
			                <input id="userRealName" type="text" class="input normal" value="<#if photoOrder??>${photoOrder.userRealName!'' }</#if>" readonly="readonly">
			            </dd>
			        </dl>
			        <dl id="user_phone">
			            <dt>用户手机号码:</dt>
			            <dd>
			                <input id="username" type="text" class="input normal" value="<#if photoOrder??>${photoOrder.username!'' }</#if>" readonly="readonly">
			            </dd>
			        </dl>
			        
			         
			        <dl id="create_time">
			            <dt>客户下单时间:</dt>
			            <dd>
			                <input id="createTime" type="text" class="input normal" value="<#if photoOrder??>${photoOrder.createTime!'' }</#if>" readonly="readonly">
			            </dd>
			        </dl>
			        
			       <dl >
			            <dt>备注:</dt>
			            <dd>
			         		<textarea id="remark" name="remark" row="2" cols="20" class="input" readonly="readonly"><#if photoOrder??>${photoOrder.remark!'' }</#if></textarea>
			            </dd>
			        </dl>
        		</td>
        		<td>
        			<dl id="shipping_user_name">
			            <dt>收货人姓名:</dt>
			             <dd>
			                <input id="shippingUserRealName" type="text" class="input normal" value="<#if photoOrder??>${photoOrder.receiverName!'' }</#if>" readonly="readonly">
			            </dd>
			        </dl>
			        <dl id="shipping_user_phone">
			            <dt>收货人手机号码:</dt>
			            <dd>
			                <input id="shippingUserName" type="text" class="input normal" value="<#if photoOrder??>${photoOrder.receiverMobile!'' }</#if>" readonly="readonly">
			            </dd>
			        </dl>
			        
			         
			        <dl >
			            <dt>收货地址:</dt>
			            <dd>
			                <input id="address" type="text" class="input normal" value="<#if photoOrder??>${photoOrder.city!'' }${photoOrder.disctrict!'' }${photoOrder.subdistrict!'' }</#if>" readonly="readonly">
			            </dd>
			        </dl>
			        
			       <dl >
			            <dt>详细地址:</dt>
			            <dd>
			         		<textarea id="addressDetail" name="remark" row="2" cols="20" class="input" readonly="readonly"><#if photoOrder??>${photoOrder.detailAddress!'' }</#if></textarea>
			            </dd>
			        </dl>
        		</td>
        	</tr>
        	<#if seller??>
        		<tr>
	        		<td>
	        		 	<dl >
				            <dt>导购姓名:</dt>
				            <dd>
				                <input id="sellerName" type="text" class="input normal" value="<#if seller??>${seller.realName!'' }</#if>" readonly="readonly">
				            </dd>
				        </dl>
	        		</td>
	        		<td>
	        		 	<dl >
				            <dt>导购电话:</dt>
				            <dd>
				                <input id="sellerPhone" type="text" class="input normal" value="<#if seller??>${seller.username!'' }</#if>" readonly="readonly">
				            </dd>
				        </dl>
	        		</td>
	        	</tr>
        	</#if>
        	
        </table>
        <#if handleType?? && handleType == 0>
			<dl>
	            <dt>购买商品</dt>
	            <dd>
	                <a id="addGoods" class="icon-btn add"><i></i><span>添加商品</span></a>
	                <span class="Validform_checktip"></span>
	                
	            </dd>
	        </dl>
        </#if>
        
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
                        <#if photoOrderGoods??>
                            <#list photoOrderGoods as comb>
                                <tr class="td_c">
                                    <td>
                                        <input type="text" name="combList[${comb_index}].sortId" class="td-input" value="${comb_index+1}" style="width:90%;">
                                    </td>
                                    <td><input type="text" id="id" name="combList[${comb_index}].goodsId" class="td-input" value="<#if comb.goodsId??> ${comb.goodsId?c}</#if>" style="width:90%;"></td>
                                    <td>
                                        <input type="text" id="title" name="combList[${comb_index}].goodsName" class="td-input" value="${comb.goodsName!''}" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="number" name="combList[${comb_index}].quantity" class="td-input" value="<#if comb.quantity??>${comb.quantity!''}</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="price" name="combList[${comb_index}].salePrice" class="td-input" value="<#if comb.salePrice??>${comb.salePrice!''}</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <i class="icon"></i>
                                        <a title="删除" class="img-btn del operator" onclick="del_goods_comb(this);">删除</a>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </dd>
        </dl>
    </div>
    
    <!-- 浮动窗口 -->
    <div class="float_right" onclick="showPhotoOrderImg(this)">
    		<img  src="<#if photoOrder??>${photoOrder.photoUri!'' }</#if>"></img>
    </div>
    
    
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
        	<#if handleType?? && handleType == 0>
        		<input type="button" onclick="createOrder()" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" >
        	</#if>
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
	<script>
		
	
	// 提交订单
	function createOrder(){
		
		$("#btnSubmit").removeAttr("onclick");
		
		var username = $("#username").val();
		var photoOrderId = $("#photoOrderId").val();
		
		var ids = [];
		var numbers = [];
		var idArray = $("input[name='combList[${comb_index!'0'}].goodsId']");
		if (!idArray || idArray.length == 0) {
			$("#btnSubmit").attr("onclick", "createOrder();");
			alert("请选择商品！");
			return;
		}
		var numberArray = $("input[name='combList[${comb_index!'0'}].number']");
		for(var i = 0; i < idArray.length; i++) {
			if(isNaN(numberArray[i].value)){
				$("#btnSubmit").attr("onclick", "createOrder();");
				alert("数量含非法字符！");
				return
			}
			if(numberArray[i].value < 1){
				$("#btnSubmit").attr("onclick", "createOrder();");
				alert("数量不能小于1！");
				return
			}
			ids.push(idArray[i].value);
			numbers.push(numberArray[i].value);
		}
		
		if(checkRepeat(ids)){
			$("#btnSubmit").attr("onclick", "createOrder();");
			alert("存在重复商品！");
			return
		}
		
		var dialog = $.dialog({
            title: '提交订单',
            content: '操作提示信息：<br />请确认订单商品数目规格与照片一致，点击“确认”提交；<br />',
            min: false,
            max: false,
            lock: true,
            icon: 'confirm.gif',
            button: [{
                name: '确认',
                callback: function () {
                	
	                	$.ajax({
	            			url : '/Verwalter/photo/order/create/order',
	            			method : 'POST',
	            			traditional : true,
	            			data : {
	            				username : username,
	            				ids : ids,
	            				numbers : numbers,
	            				photoOrderId : photoOrderId
	            			},
	            			success : function(res) {
	            				if (0 === res.status) {
	            					alert("提交订单成功！");
	            					window.location.href = "/Verwalter/photo/order/list";
	            				}else if(-1 == res.status){
	            					alert(res.message);
	            					
	            				} else if( -2 == res.status) {
	            					alert(res.message);
	            					window.location.href = "/Verwalter/login";
	            				}
	            			}
	            		});
                },
                focus: true
            }, {
                name: '关闭'
            }]
        });
		
	}
    // 检查数组中是否存在重复元素    	
	function checkRepeat(a)
	{
	   return /(\x0f[^\x0f]+)\x0f[\s\S]*\1/.test("\x0f"+a.join("\x0f\x0f") +"\x0f");
	}    			
			
					
			
	</script>
</body>
</html>