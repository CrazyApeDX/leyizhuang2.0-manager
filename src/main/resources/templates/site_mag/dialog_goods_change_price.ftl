<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>改价</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js?skin=idialog"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
    //初始化表单验证
    $("#form1").initValidform();
    
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
        var price = $.trim($('#outFactoryPrice').val());
        var originPrice = $.trim($('#originPrice').val());
    
        if (isNaN(price) || price=="") {
            W.$.dialog.alert('请设置正确的价格！', function () { $("#outFactoryPrice").focus(); }, api);
            return false;
        }
        
        var p1 = parseFloat(price);
        var p2 = parseFloat(originPrice);
        
        if (p1 == p2)
        {
            W.$.dialog.alert('请设置不同的价格！', function () { $("#outFactoryPrice").focus(); }, api);
            return false;
        }
        
        $.ajax({
            type:"post",
            url:"/Verwalter/goods/price/set?goodsId=${goods.id?c}&outPrice=" + price,
            success:function(res){
                // 成功
                if (res.code==0)
                {
                    W.$.dialog.tips('成功修改价格', 1, "confirm.gif",function () { api.close(); });
                }
                else
                {
                    W.$.dialog.alert(res.message, function () { }, api);
                }
            }
        });
        
    }
</script>
</head>

<body>
<form id="form1">
<div class="div-content">
    <input type="hidden" class="input normal">
    <dl>
        <dt>原出厂价</dt>
        <dd>
            <input id="originPrice" disabled="disabled" type="text" value="<#if goods?? && goods.outFactoryPrice??>${goods.outFactoryPrice?string("0.00")}<#else>0</#if>" class="input normal" style="background: #EEEEEE;">
            <span class="Validform_checktip">商品原出厂价</span>
        </dd>
    </dl>
    <dl>
        <dt>调整后出厂价</dt>
        <dd>
            <input id="outFactoryPrice" name="outFactoryPrice" type="text" value="" class="input normal">
            <span class="Validform_checktip">*调整后出厂价</span>
        </dd>
    </dl>
</div>
</form>
</body>
</html>