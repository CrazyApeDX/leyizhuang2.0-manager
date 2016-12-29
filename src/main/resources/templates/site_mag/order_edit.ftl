<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>查看订单信息</title>
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
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
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
            $("#btnConfirm").click(function () { OrderConfirm(); });   //确认订单
            $("#btnPayment").click(function () { OrderPayment(); });   //确认付款

            $("#btnHDFKPayment").click(function () { OrderHDFKPayment(); });   //确认货到付款已付款
            $("#btnOrderExpress").click(function () { OrderExpress(); });   //确认发货
            $("#btnOrderComplete").click(function () { OrderComplete(); }); //完成订单
            $("#btnCancel").click(function () { OrderCancel(); });     //取消订单
            // $("#btnInvalid").click(function () { OrderInvalid(); });   //作废订单
            $("#btnPrint").click(function () { OrderPrint(); });       //打印订单
            $("#btnOrderReceive").click(function () { OrderReceive(); }); //确认收货
            $("#btnHDFKReceive").click(function () { OrderHDFKReceive(); }); //货到付款确认收货
            $("#btnEditAcceptInfo").click(function () { EditAcceptInfo(); }); //修改收货信息
            $("#btnEditRemark").click(function () { EditOrderRemark(); });    //修改订单备注
            $("#btnEditRealAmount").click(function () { EditRealAmount(); }); //修改商品总金额
            $("#btnEditExpressFee").click(function () { EditExpressFee(); }); //修改配送费用
            $("#btnEditPaymentFee").click(function () { EditPaymentFee(); }); //修改支付手续费
            $("#signPhoto").click(function () { imgChange(); }); //修改签收图片大小
            $("#backMoney").click(function () { showDialog(); }); //显示还款窗口
        });

        //确认收货
        var orderReceiveCount = 0;
        function OrderReceive() {
            var dialog = $.dialog.confirm('该步骤将确认收货，确认要继续吗？', function () {
            	orderReceiveCount++;
                var orderNumber = $.trim($("#spanOrderNumber").text());
                var postData = { "orderNumber": orderNumber, "type": "orderReceive" };
                //发送AJAX请求
                if (orderReceiveCount === 1) {
                	sendAjaxUrl(dialog, postData, "/Verwalter/order/param/edit");
                }
                return false;
            });

        }
        //货到付款 确认收货
        function OrderHDFKReceive() {
            var dialog = $.dialog.confirm('该步骤将确认收货，确认要继续吗？', function () {
                var orderNumber = $.trim($("#spanOrderNumber").text());
                var postData = { "orderNumber": orderNumber, "type": "orderReceive" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/order/param/edit");
                return false;
            });

        }

        //确认订单
        function OrderConfirm() {
            var dialog = $.dialog.confirm('确认订单后将无法修改金额，确认要继续吗？', function () {
                var orderNumber = $.trim($("#spanOrderNumber").text());
                var postData = { "orderNumber": orderNumber, "type": "orderConfirm" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/order/param/edit");
                return false;
            });
        }
        
        //确认付款
        function OrderPayment() {
            var dialog = $.dialog.confirm('操作提示信息：<br />1、该订单使用在线支付方式，付款成功后自动确认；<br />2、如客户确实已打款而没有自动确认可使用该功能；<br />3、确认付款后无法修改金额，确认要继续吗？', function () {
                var orderNumber = $.trim($("#spanOrderNumber").text());
                var postData = { "orderNumber": orderNumber, "type": "orderPay" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/order/param/edit");
                return false;
            });
        }

        //确认货到付款已付款
        function OrderHDFKPayment() {
            var dialog = $.dialog.confirm('操作提示信息：<br />1、该订单使用货到付款方式，确定后，将完成订单；<br />确认要继续吗？', function () {
                var orderNumber = $.trim($("#spanOrderNumber").text());
                var postData = { "orderNumber": orderNumber, "type": "orderPayOffline" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/order/param/edit");
                return false;
            });
        }
        //确认发货
        function OrderExpress() {
            var orderNumber = $.trim($("#spanOrderNumber").text());
            var dialog = $.dialog({
                title: '确认发货',
                content: 'url:/Verwalter/order/dialog/delivery?orderNumber=' + orderNumber,
                min: false,
                max: false,
                lock: true,
                width: 450,
                height:350
            });
        }
        
        //确认完成
        function OrderComplete() {
             var dialog = $.dialog.confirm('确认该订单已完成？', function () {
                var orderNumber = $.trim($("#spanOrderNumber").text());
                var postData = { "orderNumber": orderNumber, "type": "orderFinish" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/order/param/edit");
                return false;
            });
        }

        //取消订单
        function OrderCancel() {
            var dialog = $.dialog({
                title: '取消订单',
                content: '操作提示信息：<br />1、该订单为线上支付订单，请先确定购买者是否真的不需要继续支付；<br />2、取消的订单，将不在购买流程中显示，您可以到取消的订单中查阅；<br />3、请单击相应按钮继续下一步操作！',
                min: false,
                max: false,
                lock: true,
                icon: 'confirm.gif',
                button: [{
                    name: '直接取消',
                    callback: function () {
                        var orderNumber = $.trim($("#spanOrderNumber").text());
                        var postData = { "orderNumber": orderNumber, "type": "orderCancel" };
                        //发送AJAX请求
                        sendAjaxUrl(dialog, postData, "/Verwalter/order/param/edit");
                        return false;
                    },
                    focus: true
                }, {
                    name: '关闭'
                }]
            });

        }
        //作废订单
        function OrderInvalid() {
            var dialog = $.dialog({
                title: '取消订单',
                content: '操作提示信息：<br />1、设为作废订单，将不可逆转；<br />2、会员用户，自动检测退还金额或积分到账户；<br />3、请单击相应按钮继续下一步操作！',
                min: false,
                max: false,
                lock: true,
                icon: 'confirm.gif',
                button: [{
                    name: '检测退还',
                    callback: function () {
                        var order_no = $.trim($("#spanOrderNumber").text());
                        var postData = { "order_no": order_no, "edit_type": "order_invalid", "check_revert": 1 };
                        //发送AJAX请求
                        sendAjaxUrl(dialog, postData, "../../tools/Verwalter_ajax.ashx?action=edit_order_status");
                        return false;
                    },
                    focus: true
                }, {
                    name: '直接作废',
                    callback: function () {
                        var order_no = $.trim($("#spanOrderNumber").text());
                        var postData = { "order_no": order_no, "edit_type": "order_invalid", "check_revert": 0 };
                        //发送AJAX请求
                        sendAjaxUrl(dialog, postData, "../../tools/Verwalter_ajax.ashx?action=edit_order_status");
                        return false;
                    }
                }, {
                    name: '关闭'
                }]
            });
        }
        //打印订单
        function OrderPrint() {
            var dialog = $.dialog({
                title: '打印订单',
                content: 'url:/Verwalter/order/dialog/print?orderNumber=' + $.trim($("#spanOrderNumber").text()),
                min: false,
                max: false,
                lock: true,
                width: 850//,
                // height: 500
            });
        }
        //修改收货信息
        function EditAcceptInfo() {
            var dialog = $.dialog({
                title: '修改收货信息',
                content: 'url:/Verwalter/order/dialog/contact',
                min: false,
                max: false,
                lock: true,
                width: 550,
                height: 320
            });
        }
        //修改订单备注
        function EditOrderRemark() {
            var dialog = $.dialog({
                title: '订单备注',
                content: '<textarea id="orderRemark" name="txtOrderRemark" rows="2" cols="20" class="input">${order.remarkInfo!''}</textarea>',
                min: false,
                max: false,
                lock: true,
                ok: function () {
                    var remark = $("#orderRemark", parent.document).val();
                    if (remark == "") {
                        $.dialog.alert('对不起，请输入订单备注内容！', function () { }, dialog);
                        return false;
                    }
                    var orderNumber = $.trim($("#spanOrderNumber").text());
                    var postData = { "orderNumber": orderNumber, "type": "editMark", "data": remark };
                    //发送AJAX请求
                    sendAjaxUrl(dialog, postData, "/Verwalter/order/param/edit");
                    return false;
                },
                cancel: true
            });
        }
        //修改商品总金额
        function EditRealAmount() {
            var pop = $.dialog.prompt('请修改商品总金额',
            function (val) {
                if (!checkIsMoney(val)) {
                    $.dialog.alert('对不起，请输入正确的配送金额！', function () { }, pop);
                    return false;
                }
                var orderNumber = $.trim($("#spanOrderNumber").text());
                var postData = { "orderNumber": orderNumber, "type": "editTotalGoodsPrice", "data": val };
                //发送AJAX请求
                sendAjaxUrl(pop, postData, "/Verwalter/order/param/edit");
                return false;
            },
            $.trim($("#spanRealAmountValue").text())
        );
        }
        //修改配送费用
        function EditExpressFee() {
            var pop = $.dialog.prompt('请修改配送费用',
            function (val) {
                if (!checkIsMoney(val)) {
                    $.dialog.alert('对不起，请输入正确的配送金额！', function () { }, pop);
                    return false;
                }
                var orderNumber = $.trim($("#spanOrderNumber").text());
                var postData = { "orderNumber": orderNumber, "type": "editDeliveryPrice", "data": val };
                //发送AJAX请求
                sendAjaxUrl(pop, postData, "/Verwalter/order/param/edit");
                return false;
            },
            $.trim($("#spanExpressFeeValue").text())
        );
        }
        //修改手续费用
        function EditPaymentFee() {
            var pop = $.dialog.prompt('请修改支付手续费用',
            function (val) {
                if (!checkIsMoney(val)) {
                    $.dialog.alert('对不起，请输入正确的手续费用！', function () { }, pop);
                    return false;
                }
                var orderNumber = $.trim($("#spanOrderNumber").text());
                var postData = { "orderNumber": orderNumber, "type": "editPayPrice", "data": val };
                //发送AJAX请求
                sendAjaxUrl(pop, postData, "/Verwalter/order/param/edit");
                return false;
            },
            $.trim($("#spanPaymentFeeValue").text())
        );
        }

        //=================================工具类的JS函数====================================
        //检查是否货币格式
        function checkIsMoney(val) {
            var regtxt = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
            if (!regtxt.test(val)) {
                return false;
            }
            return true;
        }
        //发送AJAX请求
        function sendAjaxUrl(winObj, postData, sendUrl) {
            $.ajax({
                type: "post",
                url: sendUrl,
                data: postData,
                dataType: "json",
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert('尝试发送失败，错误信息：' + errorThrown, function () { }, winObj);
                },
                success: function (data) {
                    if (data.code == 0) {
                        winObj.close();
                        $.dialog.tips(data.msg, 2, '32X32/succ.png', function () { location.reload(); }); //刷新页面
                    } else {
                        $.dialog.alert('错误提示：' + data.message, function () { }, winObj);
                    }
                }
            });
        }
        //图片点击放大缩小
        var isopen = false; 
        function imgChange(){
        	if (!isopen){ 
	        	isopen = true; 
	        	$("#signPhoto").width("230px"); 
	        	$("#signPhoto").height("320px");
        	} 
        	else{ 
	        	isopen = false; 
	        	$("#signPhoto").width("100px");
	        	$("#signPhoto").height("100px");
        	} 
        }
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
        	var money=$('#money').val();
        	var pos=$('#pos').val();
        	var other = $('#other').val();
        	var realPayTime = $('#realPayTime').val();
        	
        	if (!money) {
        		money = 0.0;
        	}
        	
        	if (!pos) {
        		pos = 0.0;
        	}
        	
        	if (!other) {
        		other = 0.0;
        	}
        	
        	if (!realPayTime) {
        		alert("请填写真实收款时间");
        		return;
        	}
        	
        	if (Number(pos) < 0) {
        		alert("POS收款不能为负数");
        		return;
        	}
        	
        	if (Number(other) < 0) {
        		alert("其他收款不能为负数");
        		return;
        	}
        	
        	console.log(realPayTime);
        	
        	if(parseFloat(money)+parseFloat(pos)+parseFloat(other)==owned){
        		 $.ajax({
        	            type: "post",
        	            url: "/Verwalter/order/backMoney",
        	            data: {"id":id,"money":money,"pos":pos,"other":other,"realPayTime":realPayTime},
        	            dataType: "json",
        	            error: function (XMLHttpRequest, textStatus, errorThrown) {
        	            	 $.dialog.alert('错误提示：' + '网络连接失败', function () { });
        	            },
        	            success: function (data) {
        	                 if (data.code == -1) {
        	                	 $.dialog.alert('错误提示：' + data.message, function () { });
        	                } else if(data.code == 0){
        	                	$.dialog.tips(data.message, 2, '32X32/succ.png', function () { location.reload(); }); //刷新页面
        	                }
        	             }
        	        });
        	}else{
        		alert("必须一次性交清不允许欠款！");
        	}
        	
        }
       
    </script>
</head>
<body class="mainbody"><div style="position: absolute; left: -9999em; top: 236px; visibility: visible; width: auto; z-index: 1976;"><table class="ui_border ui_state_visible ui_state_focus"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;">视窗 </div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"><div class="ui_loading"><span>loading...</span></div></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/order/save" id="form1">
    <!--导航栏-->
    <div class="location" style="position: fixed; top: 0px;">
        <a href="/Verwalter/order/list/${statusId!"0"}" class="back"><i></i><span>返回列表页</span></a>
        <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
        <i class="arrow"></i>
        <a href="/Verwalter/order/list/${statusId!"0"}"><span>订单管理</span></a>
        <i class="arrow"></i><span>订单详细</span>
    </div>
    <div class="line10">
    </div>
    <!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab" style="position: static; top: 52px;">
            <div class="content-tab-ul-wrap">
                <ul>
                    <li>
                        <a href="javascript:;" onclick="tabs(this);" class="selected">订单详细信息</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="tab-content">
        <dl>
            <dd style="margin-left: 50px; text-align: center;">
                <div class="order-flow" style="width: 850px">
                    <#if order.statusId == 1>
                        <div class="order-flow-left">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">订单已生成</p>
                                <p>${order.orderTime!""}</p>
                            </span>
                        </div>
                        <div class="order-flow-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待确认</p>
                            </span>
                        </div>
                        <div class="order-flow-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待付款</p>
                            </span>
                        </div>
                        <div class="order-flow-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待评价</p>
                            </span>
                        </div>
                        <div class="order-flow-right-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">未完成</p>
                                <p></p>
                            </span>
                        </div>
                    <#elseif order.statusId == 2>
                            <div class="order-flow-left">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">订单已生成</p>
                                    <p>${order.orderTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已确认</p>
                                    <p>${order.checkTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-wait">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">待付款</p>
                                </span>
                            </div>
                            <div class="order-flow-wait">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">待评价</p>
                                </span>
                            </div>
                            <div class="order-flow-right-wait">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">未完成</p>
                                    <p></p>
                                </span>
                            </div>
                    <#elseif order.statusId == 3>
                        <div class="order-flow-left">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">订单已生成</p>
                                <p>${order.orderTime!""}</p>
                            </span>
                        </div>
                        <div class="order-flow-arrive">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">已付款</p>
                                <p>${order.payTime!""}</p>
                            </span>
                        </div>
                        <div class="order-flow-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待发货</p>
                            </span>
                        </div>
                        <div class="order-flow-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待收货</p>
                            </span>
                        </div>
                        <div class="order-flow-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待评价</p>
                            </span>
                        </div>
                        <div class="order-flow-right-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">未完成</p>
                                <p></p>
                            </span>
                        </div>
                    <#elseif order.statusId == 4>
                        <div class="order-flow-left">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">订单已生成</p>
                                <p>${order.orderTime!""}</p>
                            </span>
                        </div>
                        <div class="order-flow-arrive">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">已付款</p>
                                <p>${order.payTime!""}</p>
                            </span>
                        </div>
                        <div class="order-flow-arrive">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">已发货</p>
                                <p>${order.sendTime!''}</p>
                            </span>
                        </div>
                        <div class="order-flow-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待收货</p>
                            </span>
                        </div>
                        <div class="order-flow-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待评价</p>
                            </span>
                        </div>
                        <div class="order-flow-right-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">未完成</p>
                                <p></p>
                            </span>
                        </div>
                    <#elseif order.statusId == 5>
                    <#--
                        <#if order.isOnlinePay>
                            <div class="order-flow-left">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">订单已生成</p>
                                    <p>${order.orderTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已付款</p>
                                    <p>${order.payTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已发货</p>
                                    <p>${order.sendTime!''}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已收货</p>
                                    <p>${order.receiveTime!''}</p>
                                </span>
                            </div>
                            <div class="order-flow-wait">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">待评价</p>
                                </span>
                            </div>
                            <div class="order-flow-right-wait">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">未完成</p>
                                </span>
                            </div>
                        <#else>
                            -->
                            <div class="order-flow-left">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">订单已生成</p>
                                    <p>${order.orderTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已确认</p>
                                    <p>${order.checkTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已付款</p>
                                    <p>${order.payTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">待评价</p>
                                </span>
                            </div>
                            <div class="order-flow-right-wait">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">未完成</p>
                                    <p></p>
                                </span>
                            </div>
                    <#elseif order.statusId == 6>
                            <div class="order-flow-left">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">订单已生成</p>
                                    <p>${order.orderTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已确认</p>
                                    <p>${order.checkTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已付款</p>
                                    <p>${order.payTime!""}</p>
                                </span>
                            </div>
                            <div class="order-flow-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已评价</p>
                                    <p>${order.finishTime!''}</p>
                                </span>
                            </div>
                            <div class="order-flow-right-arrive">
                                <a class="order-flow-input"></a>
                                <span>
                                    <p class="name">已完成</p>
                                    <p>${order.finishTime!''}</p>
                                </span>
                            </div>
                    <#elseif order.statusId == 7>
                        <div class="order-flow-left">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">订单已生成</p>
                                <p>${order.orderTime!""}</p>
                            </span>
                        </div>
                        <div class="order-flow-right-arrive">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">已取消</p>
                                <p>${order.cancelTime!''}</p>
                            </span>
                        </div>
                    </#if>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>订单号</dt>
            <dd>
                <span id="spanOrderNumber">${order.orderNumber!""}<#if order.isCoupon??&&order.isCoupon>（券订单）</#if></span>
            </dd>
        </dl>
        <#if order.photo??>
            <dl>
                <dt>签收照片</dt>
                <dd>
                    <img id="signPhoto" width="100px" height="100px" src="${order.photo!''}">
                </dd>
            </dl>
        </#if>
        <dl>
            <dt>商品列表</dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <thead>
                        <tr>
                            <th width="12%">
                                商品ID
                            </th>
                            <th>
                                商品名称
                            </th>
                            <th width="12%">
                                成交价
                            </th>
                            <th width="10%">
                                数量
                            </th>
                            <th width="12%">
                                金额合计
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        
                        <#if order.orderGoodsList??>
                            <#list order.orderGoodsList as goods>
                                <tr class="td_c">
                                    <td>${goods.goodsId?c!""}</td>
                                    <td style="text-align: left; white-space: normal;">
                                        ${goods.goodsTitle!""} 
                                        ${goods.sku!""}
                                        ${goods.goodsColor!""}
                                        ${goods.goodsCapacity!""}
                                        ${goods.goodsVersion!""}
                                        ${goods.goodsSaleType!""}
                                        <#if goods.ownerGoodsSku??>
                                        	调色:${goods.ownerGoodsSku!""}
                                        </#if>
                                        
                                    </td>
                                    <td>${goods.price?string("0.00")}</td>
                                    <td>${goods.quantity!""}</td>
                                    <td>${(goods.price*goods.quantity)?string("0.00")}</td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody> 
                </table> 
            </dd> 
        </dl>
        <#if order.presentedList??>
             <dl>
                <dt>赠品列表</dt>
                <dd>
                    <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                        <thead>
                            <tr>
                                <th width="12%">
                                    商品ID
                                </th>
                                <th>
                                    商品名称
                                </th>
                                <th width="12%">
                                    价格
                                </th>
                                <th width="10%">
                                    数量
                                </th>
                                <th width="12%">
                                    金额合计
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                                <#list order.presentedList as goods>
                                    <tr class="td_c">
                                        <td>${goods.goodsId?c!""}</td>
                                        <td style="text-align: left; white-space: normal;">
                                            ${goods.goodsTitle!""} 
                                            ${goods.goodsColor!""}
                                            ${goods.goodsCapacity!""}
                                            ${goods.goodsVersion!""}
                                            ${goods.goodsSaleType!""}
                                        </td>
                                        <td>${goods.price?string("0.00")}</td>
                                        <td>${goods.quantity!""}</td>
                                        <td>${(goods.price*goods.quantity)?string("0.00")}</td>
                                    </tr>
                                </#list>
                        </tbody> 
                    </table> 
                </dd> 
            </dl>
        </#if>
        <#if order.giftGoodsList?? && order.giftGoodsList?size gt 0>
             <dl>
                <dt>小辅料列表</dt>
                <dd>
                    <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                        <thead>
                            <tr>
                                <th width="12%">
                                    商品ID
                                </th>
                                <th>
                                    商品名称
                                </th>
                                <th width="12%">
                                    价格
                                </th>
                                <th width="10%">
                                    数量
                                </th>
                                <th width="12%">
                                    金额合计
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                                <#list order.giftGoodsList as goods>
                                    <tr class="td_c">
                                        <td>${goods.goodsId?c!""}</td>
                                        <td style="text-align: left; white-space: normal;">
                                            ${goods.goodsTitle!""} 
                                            ${goods.goodsColor!""}
                                            ${goods.goodsCapacity!""}
                                            ${goods.goodsVersion!""}
                                            ${goods.goodsSaleType!""}
                                        </td>
                                        <td>${goods.price?string("0.00")}</td>
                                        <td>${goods.quantity!""}</td>
                                        <td>${(goods.price*goods.quantity)?string("0.00")}</td>
                                    </tr>
                                </#list>
                        </tbody> 
                    </table> 
                </dd> 
            </dl>
        </#if>
        
        <dl>
            <dt>订单统计</dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <tbody>
                    <tr>
                        <th>
                       门店名称
                        </th>
                        <td>
                            <div class="position">
                            <span><#if order.diySiteName??>${order.diySiteName!""}</#if> </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>
                        导购名字
                        </th>
                        <td>
                            <div class="position">
                            <span><#if order.sellerRealName??>${order.sellerRealName!""}</#if> </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th width="20%">
                            商品总金额
                        </th>
                        <td>
                            <div class="position">
                                <span id="spanRealAmountValue">
                                    <#if order.totalGoodsPrice??>${order.totalGoodsPrice?string("0.00")}</#if>
                                </span> 元
                            </div>
                        </td>
                    </tr>
                    <#if order.deliverTypeTitle??&&order.deliverTypeTitle="送货上门">
	                    <tr>
	                        <th width="20%">
	                            运费
	                        </th>
	                        <td>
	                            <div class="position">
	                                <span id="spanRealAmountValue">
	                                    <#if order.deliverFee??>${order.deliverFee?string("0.00")}</#if>
	                                </span> 元
	                                <#--
	                                <#if order.orderNumber?contains("XN")>
	                                	<input name="btnEditDeliveryFee" onclick="changeDeliveryFee();" type="button" class="ibtn" value="修改" style="margin-top: 2px;">
	                                </#if>
	                                -->
	                            </div>
	                            <script type="text/javascript">
	                            	var changeDeliveryFee = function() {
	                            		$.dialog.prompt('修改运费为：', function(fee) {
	                            			if (isNaN(fee)) {
	                            				$.dialog.alert('请输入一个正确的数字');
	                            			} else {
												$.ajax({
													url: '/Verwalter/order/delivery/fee/change',
													method: 'POST',
													data: {
														fee: fee,
														id: ${order.id?c}
													},
													success: function(result) {
														if (0 === result.status) {
															window.location.href = "/Verwalter/order/edit?id=${order.id?c}&statusId=0";
														}
													}
												});	                            			
	                            			}
	                            		})
	                            	}
	                            </script>
	                        </td>
	                    </tr>
                    </#if>
                    <#--
                    <tr>
                        <th>
                            配送费用
                        </th>
                        <td>
                            <div class="position">
                                <span id="spanExpressFeeValue"><#if order.deliverTypeFee??>${order.deliverTypeFee?string("0.00")}</#if></span> 元
                                <#if order.statusId==1 || order.statusId==2 && order.isOnlinePay>
                                <input type="button" id="btnEditExpressFee" class="ibtn" value="调价">
                                </#if>
                            </div>
                        </td>
                    </tr>
                   
                    <tr>
                        <th>
                            支付手续费
                        </th>
                        <td>
                            <div class="position">
                                <span id="spanPaymentFeeValue"><#if order.payTypeFee??>${order.payTypeFee?string("0.00")}</#if></span> 元
                                <#if order.statusId==1 || order.statusId==2 && order.isOnlinePay>
                                <input type="button" id="btnEditPaymentFee" class="ibtn" value="调价">
                                </#if>
                            </div>
                        </td>
                    </tr>
                     -->
                    <tr>
                        <th>
                            使用现金券、产品券
                        </th>
                        <td>
                            <div class="position">
                                使用现金卷金额${order.cashCoupon!'0'}元 | 使用产品劵情况 ：${order.productCoupon!''}
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            订单使用预存款
                        </th>
                        <td>
                         可提现预存款：<#if order.cashBalanceUsed??> ${order.cashBalanceUsed?string("0.00")}<#else>0.00</#if>元 | 
                        不可提现预存款：<#if order.unCashBalanceUsed??>${order.unCashBalanceUsed?string("0.00")}<#else>0.00</#if>元
                            </td>
                    </tr>
                    <tr>
                        <th>
                    订单在线支付金额                   
                        </th>
                        <td>
                         <#if order.otherPay??> ${order.otherPay?string("0.00")}<#else>0.00</#if>元
                        </td>
                    </tr>
                    <#-- <tr>
                        <th>
                    欠款金额                
                        </th>
                        <td>
                        
                         ${(order.totalPrice-order.actualPay!'0'?number-order.otherPay!'0'?number)?string("0.00")}元
                        </td>
                    </tr>
                    -->
                    <tr>
                        <th>
                            订单总金额
                        </th>
                        <td>
                            ${order.totalPrice?string("0.00")}元
                        </td>
                    </tr>
                    <tr>
                        <th>送货上楼方式</th>
                        <td>${order.upstairsType!'不上楼'}</td>
                    </tr>
                    <tr>
                        <th>楼层</th>
                        <td>${order.floor!'1'}</td>
                    </tr>
                    <tr>
                        <th>上楼费总额</th>
                        <td><#if order.upstairsFee??>${(order.upstairsFee)?string("0.00")}<#else>0.00</#if>元</td>
                    </tr>
                    <tr>
                        <th>预存款支付上楼费</th>
                        <td><#if order.upstairsBalancePayed??>${(order.upstairsBalancePayed)?string("0.00")}<#else>0.00</#if>元</td>
                    </tr>
                    <tr>
                        <th>第三方支付上楼费</th>
                        <td><#if order.upstairsOtherPayed??>${(order.upstairsOtherPayed)?string("0.00")}<#else>0.00</#if>元</td>
                    </tr>
                    <tr>
                        <th>剩余上楼费</th>
                        <td><#if order.upstairsLeftFee??>${(order.upstairsLeftFee)?string("0.00")}<#else>0.00</#if>元</td>
                    </tr>
                </tbody>
                </table>
            </dd>
        </dl>
        
        <#if order.deliverTypeTitle?? && order.deliverTypeTitle!='门店自提'>
        <dl>
            <dt>收货信息</dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <tbody><tr>
                        <th width="20%">
                            收件人
                        </th>
                        <td>
                            <div class="position">
                                <span id="spanAcceptName">${order.shippingName!""}</span>
                                <#if order.statusId lt 4>
                                <input name="btnEditAcceptInfo" type="button" id="btnEditAcceptInfo" class="ibtn" value="修改">
                                </#if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            发货地址
                        </th>
                        <td>
                            <span id="spanArea"></span> 
                            <span id="spanAddress">${order.shippingAddress!""}</span>
                        </td>
                    </tr>
                    <#--<tr>
                        <th>
                            邮政编码
                        </th>
                        <td><span id="spanPostCode">${order.postalCode!""}</span></td>
                    </tr>-->
                    <tr>
                        <th>
                            电话
                        </th>
                        <td><span id="spanMobile">${order.shippingPhone!""}</span></td>
                    </tr>
                    <#--<tr>
                        <th>
                            是否索要发票
                        </th>
                        
                        <td>
                            <span id="spanInvoice"><#if order.isNeedInvoice?? && order.isNeedInvoice>是<#else>否</#if></span>
                        </td>
                        </tr>
                        <tr>
                        <th>发票抬头</th>
                        <td>
                            <span id="spanInvoice">${order.invoiceTitle!""}</span>
                        </td>
                        
                    </tr>
                    -->
                </tbody></table>
            </dd>
        </dl>
        </#if>
        
        <dl>
            <dt>支付配送</dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <tbody><tr>
                        <th width="20%">
                            支付方式
                        </th>
                        <td>${order.payTypeTitle!""}</td>
                    </tr>
                    <tr>
                        <th>
                            配送方式
                        </th>
                        <td>${order.deliverTypeTitle!""}</td>
                    </tr>
                    <tr>
                        <th>
                            配送仓库
                        </th>
                        <td><#if tdWareHouse??>${tdWareHouse.whName!''}</#if></td>
                    </tr>
                    <tr>
                        <th>
                            配送员姓名
                        </th>
                        <td><#if deliveryUser??>${deliveryUser.realName!''}</#if></td>
                    </tr>
                    <tr>
                        <th>
                            配送员联系电话
                        </th>
                        <td><#if deliveryUser??>${deliveryUser.username!''}</#if></td>
                    </tr>
                    <tr>
                        <th>
                            用户留言
                        </th>
                        <td>${order.remark!""}</td>
                    </tr>
                    <tr>
                        <th valign="top">
                            订单备注
                        </th>
                        <td>
                            <div class="position">
                                <div>${order.remarkInfo!""}</div>
                                <input name="btnEditRemark" type="button" id="btnEditRemark" class="ibtn" value="修改" style="margin-top: 2px;">
                            </div>
                        </td>
                    </tr>
                </tbody></table>
            </dd>
        </dl>
        
    </div>
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
            <#if order.statusId==1>
                <input type="button" id="btnConfirm" value="确认订单" class="btn">
                <input type="button" id="btnCancel" value="取消订单" class="btn green">
            <#elseif order.statusId==2>
                    <#--后台确认付款没有拆单 <input type="button" id="btnPayment" value="确认付款" class="btn"> -->
                    <input type="button" id="btnCancel" value="取消订单" class="btn green">
            <#elseif order.statusId==3>
                <#if order.deliverTypeTitle=='门店自提'>
                <#if isown==true>
                <input type="button" id="btnOrderExpress" value="确认发货" class="btn">
                <#else>
                 <input type="button" id="backMoney" value="填写收款" class="btn">
                </#if>
                
                </#if>
                <#--<input type="button" id="btnCancel" value="取消订单" class="btn green">-->
            <#elseif order.statusId==4>
                <#if order.deliverTypeTitle=='门店自提'>
                <input type="button" id="btnOrderReceive" value="确认收货" class="btn green">
                </#if>
            <#elseif order.statusId==5>
                <input type="button" id="btnOrderComplete" value="确认完成" class="btn">
            </#if>
            <#if order.statusId != 7>
                <input type="button" id= "btnPrint" value="打印订单" class="btn violet">
            </#if>
            <input type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div> 
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
	<div class="dialog">
		<div class="dialog_title">还款 (<#if ownPrice??>${ownPrice?c}<#else>0</#if>)</div>
		<div class="dialog_row"><input placeholder="现金" id="money" style="height:30px;width:175px;" type="number" /> </div>
		<div class="dialog_row"><input placeholder="POS" id="pos" style="height:30px;width:175px;" type="number" /> </div>
		<div class="dialog_row"><input placeholder="其他" id="other" style="height:30px;width:175px;" type="number" /> </div>
		<div class="dialog_row">
			<div class="input-date">
	            <input id="realPayTime" name="beginDate" placeholder="收款时间" id="beginDate" type="text" value="<#if activity??>${activity.beginDate?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
	            <i>日期</i>
	        </div>
		</div>
		<div class="dialog_row"><input onclick="sumbitBackMoney(<#if ownPrice??>${ownPrice?c}<#else>0</#if>,${order.id?c })" class="dialog_btn btn" type="button" value="确定" /><input onclick="hiddenDialog()" class="dialog_btn btn" type="button" value="取消" /> </div>
	</div>

</body></html>