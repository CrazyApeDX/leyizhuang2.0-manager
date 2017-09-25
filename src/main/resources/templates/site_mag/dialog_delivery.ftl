<html xmlns="http://www.w3.org/1999/xhtml"><head>
<title>订单发货窗口</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js?skin=idialog"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
    //窗口API
    var api = frameElement.api, W = api.opener;
    api.button({
        name: '确定',
        focus: true,
        callback: function () {
            submitForm();
            return false;
        }
    }, {
        name: '取消'
    });

    //提交表单处理
    function submitForm() {
        //验证表单
        if ($("#ddlExpressId").val() == "") {
            W.$.dialog.alert('请选择配送方式！', function () { $("#ddlExpressId").focus(); }, api);
            return false;
        }
        var orderNumber = $.trim($("#spanOrderNumber", W.document).text());
        //组合参数
        var postData = {
            "orderNumber": orderNumber, "type": "orderDelivery",
            "deliverTypeId": $("#ddlExpressId").val(), "expressNumber": $("#expressNumber").val()
        };
        //判断是否需要输入物流单号
        if ($("#expressNumber").val() == "") {
            W.$.dialog.confirm('您确定不填写物流单号吗？',
            function () {
                //发送AJAX请求
                W.sendAjaxUrl(api, postData, "/Verwalter/order/param/edit");
            },
            function () {
                $("#expressNumber").focus();
            },
            api);
                return false;
            } else {
                //发送AJAX请求
                W.sendAjaxUrl(api, postData, "/Verwalter/order/param/edit");
            }
            return false;
    }
</script>
</head>
<body>
    <form name="form1" method="post" action="" id="form1">
    <div>
        <input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
        <input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="30FCF0EA">
        <input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="">
    </div>
    <div class="div-content">
        <#--<dl>
            <dt>更改配送方式</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="ddlExpressId" id="ddlExpressId">
                        <option value="">请选择配送方式</option>
                        <#if delivery_type_list??>
                            <#list delivery_type_list as item>
                                <option <#if order?? && order.deliverTypeId==item.id>selected="selected"</#if> value="${item.id?c}">${item.title!''}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>发货物流单号</dt>
            <dd>
                <input name="expressNumber" type="text" id="expressNumber" class="input txt">
            </dd>
        </dl>-->
    </div>
    </form>
</body>
</html>