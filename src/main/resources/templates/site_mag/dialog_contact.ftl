<html xmlns="http://www.w3.org/1999/xhtml"><head>
<title>修改收货信息窗口</title>
<script type="text/javascript" src="/client/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/client/js/lhgdialog.js?skin=idialog"></script>
<script type="text/javascript" src="/client/js/PCASClass.js"></script>
<script type="text/javascript" src="/client/js/layout.js"></script>
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

    //页面加载完成执行
    $(function () {
        var acceptName = $.trim($("#spanAcceptName", W.document).text());
        var address = $.trim($("#spanAddress", W.document).text());
        var postCode = $.trim($("#spanPostCode", W.document).text());
        var mobi = $.trim($("#spanMobile", W.document).text());
        $("#txtAcceptName").val(acceptName);
        $("#txtAddress").val(address);
        $("#txtPostCode").val(postCode);
        $("#txtMobile").val(mobi);
    });

    //提交表单处理
    function submitForm() {
        //验证表单
        if ($("#txtAcceptName").val() == "") {
            W.$.dialog.alert('请填写收货人姓名！', function () { $("#txtAcceptName").focus(); }, api);
            return false;
        }

        if ($("#txtAddress").val() == "") {
            W.$.dialog.alert('请填写详细的送货地址！', function () { $("#txtAddress").focus(); }, api);
            return false;
        }
        if ($("#txtMobile").val() == "") {
            W.$.dialog.alert('请填写电话！', function () { $("#txtMobile").focus(); }, api);
            return false;
        }
        //下一步，AJAX提交表单
        var orderNumber=$.trim( $("#spanOrderNumber", W.document).text());
        var postData = {
            "orderNumber": orderNumber, 
            "type": "editContact",
            "name": $("#txtAcceptName").val(), 
            "address": $("#txtAddress").val(),
            "postal": $("#txtPostCode").val(), 
            "mobile": $("#txtMobile").val()
        };
        //发送AJAX请求
        W.sendAjaxUrl(api, postData, "/Verwalter/order/param/edit");
        return false;
    }
</script>
</head>
<body>
    <div class="div-content">
        <dl>
            <dt>收件人姓名</dt>
            <dd>
                <input type="text" id="txtAcceptName" class="input txt">
                <span class="Validform_checktip">*</span></dd>
        </dl>
        <dl>
            <dt>详细地址</dt>
            <dd>
                <input type="text" id="txtAddress" class="input normal">
                <span class="Validform_checktip">*</span></dd>
        </dl>
        <dl>
            <dt>邮政编码</dt>
            <dd>
                <input type="text" id="txtPostCode" class="input txt"></dd>
        </dl>
        <dl>
            <dt>联系手机</dt>
            <dd>
                <input type="text" id="txtMobile" class="input txt">
                <span class="Validform_checktip">*</span></dd>
        </dl>
    </div>
</body>
</html>