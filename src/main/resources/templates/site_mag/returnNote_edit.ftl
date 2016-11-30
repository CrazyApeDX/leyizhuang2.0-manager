<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>查看订单信息</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        $(function () {
            $("#btnConfirm").click(function () { OrderConfirm(); });   // 确认退货单
            $("#btnPayment").click(function () { OrderPayment(); });   //通知物流

            $("#btnOrderExpress").click(function () { OrderExpress(); });   // 验货确认
            $("#btnOrderReceive").click(function () { OrderReceive(); }); // 确认退款
            $("#btnEditRemark").click(function () { EditOrderRemark(); });    //修改退货单备注
        });

        // 确认退款
        function OrderReceive() {
            var dialog = $.dialog.confirm('该步骤将确认退款，确认要继续吗？', function () {
                var returnNumber = $.trim($("#returnNumber").text());
                var postData = { "returnNumber": returnNumber, "type": "refund" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/returnNote/param/edit");
                return false;
            });
        }
        
        var confirmCount = 0;
        // 确认取货
        function OrderConfirm() {
            var dialog = $.dialog.confirm('该步骤将确认退货单取货(如果非门店自提单子,该由配送员点击)，确认要继续吗？', function () {
            	confirmCount++;
                var returnNumber = $.trim($("#returnNumber").text());
                var postData = { "returnNumber": returnNumber, "type": "btnConfirm" };
                //发送AJAX请求
                if (confirmCount === 1) {
                	console.log(confirmCount);
                	sendAjaxUrl(dialog, postData, "/Verwalter/returnNote/param/edit");
                }
                return false;
            });
        }
        
        // 通知物流
        function OrderPayment() {
            var dialog = $.dialog.confirm('操作提示信息：<br />1、该退货单选择物流退货方式，确定通知物流取货？', function () {
                var returnNumber = $.trim($("#returnNumber").text());
                var postData = { "returnNumber": returnNumber, "type": "informDiy" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/returnNote/param/edit");
                return false;
            });
        }

        // 验货确认
        function OrderExpress() {
             var dialog = $.dialog.confirm('操作提示信息：<br />1、确认商品已验货，确认要继续吗？', function () {
                var returnNumber = $.trim($("#returnNumber").text());
                var postData = { "returnNumber": returnNumber, "type": "examineReturn" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/returnNote/param/edit");
                return false;
            });
        }
        

        //修改订单备注
        function EditOrderRemark() {
            var dialog = $.dialog({
                title: '退货单备注',
                content: '<textarea id="orderRemark" name="txtOrderRemark" rows="2" cols="20" class="input"></textarea>',
                min: false,
                max: false,
                lock: true,
                ok: function () {
                    var remark = $("#orderRemark", parent.document).val();
                    if (remark == "") {
                        $.dialog.alert('对不起，请输入备注内容！', function () { }, dialog);
                        return false;
                    }
                    var returnNumber = $.trim($("#returnNumber").text());
                    var postData = { "returnNumber": returnNumber, "type": "editMark", "data": remark };
                    //发送AJAX请求
                    sendAjaxUrl(dialog, postData, "/Verwalter/returnNote/param/edit");
                    return false;
                },
                cancel: true
            });
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
    </script>
</head>
<body class="mainbody"><div style="position: absolute; left: -9999em; top: 236px; visibility: visible; width: auto; z-index: 1976;"><table class="ui_border ui_state_visible ui_state_focus"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;">视窗 </div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"><div class="ui_loading"><span>loading...</span></div></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/returnNote/save" id="form1">
    <!--导航栏-->
    <div class="location" style="position: fixed; top: 0px;">
        <a href="/Verwalter/returnNote/returnNote/list" class="back"><i></i><span>返回列表页</span></a>
        <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
        <i class="arrow"></i>
        <a href="/Verwalter/returnNote/returnNote/list"><span>退货单管理</span></a>
        <i class="arrow"></i><span>退货单详细</span>
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
                        <a href="javascript:;" onclick="tabs(this);" class="selected">退货单详细信息</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="tab-content">
        <dl>
            <dd style="margin-left: 50px; text-align: center;">
                <div class="order-flow" style="width: 850px">
                </div>
            </dd>
        </dl>
        <dl>
            <dt>退货单号</dt>
            <dd>
                <span id="returnNumber">${returnNote.returnNumber!''}</span>
            </dd>
        </dl>
        
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
                        
                        <#if returnNote.returnGoodsList??>
                            <#list returnNote.returnGoodsList as goods>
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
                        </#if>
                    </tbody> 
                </table> 
            </dd> 
        </dl>
        
        <dl>
            <dt>退货单信息</dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <tbody>
                    <tr>
                        <th>
                            原订单号
                        </th>
                        <td>
                            <div class="position">
                                ${returnNote.orderNumber!''}
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            支付方式
                        </th>
                        <td>
                            <div class="position">
                                ${returnNote.payTypeTitle!''}
                            </div>
                        </td>
                    </tr>
                     <tr>
                        <th>
                            订单配送方式
                        </th>
                        <td>
                            <div class="position">
                                ${returnNote.deliverTypeTitle!''}
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            用户选择退货方式
                        </th>
                        <td>
                            <div class="position">
                                <#if returnNote.turnType?? && returnNote.turnType==1>
                                    到店退货
                                <#else>
                                    物流取货
                                </#if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            退货门店
                        </th>
                        <td>
                            <div class="position">
                                ${returnNote.diySiteTitle!''}
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            门店地址
                        </th>
                        <td>
                            ${returnNote.diySiteAddress!''}
                         </td>
                    </tr>
                    <th width="20%">
                            申请用户
                        </th>
                        <td>
                            <div class="position">
                                <span id="spanAcceptName">${returnNote.username!""}</span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                    <th width="20%">
                          用户姓名 	
                    </th>
                    <td>
                    	<div class="position">
                             <span id="spanAcceptName">${user.realName!""}</span>
                         </div>
                    </td>
                    </tr>
                    <tr>
                    <th width="20%">
                            当前状态
                        </th>
                        <td>
                        ${returnNote.statusName!'' }
                            <#-- <#if returnNote.statusId??>
                            <#switch returnNote.statusId>
                                  <#case 1>待通知物流<#break>
                                  <#case 2>待确认收货<#break>
                                  <#case 3>待退款<#break>
                                  <#case 4>还未定义的状态<#break>
                                  <#case 5>已完成<#break> 
                            </#switch>
                            </#if> -->
                        </td>
                    </tr>
                    <tr>
                        <th>
                            客户备注
                        </th>
                        <td>
                            <span id="spanArea"></span> 
                            <span id="spanAddress">${returnNote.remarkInfo!""}</span>
                        </td>
                    </tr>
                    <tr>
                        <th valign="top">
                            备注
                        </th>
                        <td>
                            <div class="position">
                                <div>${returnNote.managerRemarkInfo!""}</div>
                                <input name="btnEditRemark" type="button" id="btnEditRemark" class="ibtn" value="修改" style="margin-top: -3px;">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th valign="top">
                            退款明细
                        </th>
                        <td>
                            <div class="position">
                                <div>${returnNote.returnDetail!""}</div>
                            </div>
                        </td>
                    </tr>
                </tbody>
                </table>
            </dd>
        </dl>
    </div>
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
            <#if returnNote.statusId??>
                <#if returnNote.statusId==1>
                   <#-- <input type="button" id="btnConfirm" value="确认退货单" class="btn">-->
                        <input type="button" id="btnPayment" value="通知物流" class="btn">
                <#elseif returnNote.statusId==2>
                	<#if returnNote.turnType==1>
                    <input type="button" id="btnConfirm" value="确认收货" class="btn">
                    </#if>
                <#elseif returnNote.statusId==3>
                    <input type="button" id="btnOrderReceive" value="确认退款" class="btn green">
                <#elseif returnNote.statusId==4>
                	<input type="button" id="btnOrderReceive" value="确认退款" class="btn green">
                    <#-- <input type="button" id="btnOrderExpress" value="验货确认" class="btn"> -->
                <#elseif returnNote.statusId==5>
                     <#-- <input type="button" class="btn yellow" value="已完成" class="btn"> -->
                </#if>
            </#if>
            <#--
            <#if returnNote.statusId != 7>
                <input type="button" id= "btnPrint" value="打印订单" class="btn violet">
            </#if>
            -->
            <input type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>


</body></html>