<!DOCTYPE html>
<html lang="zh-CN" class="bgc-f3f4f6">
    <head>
        <meta charset="UTF-8">
        <meta name="keywords" content="">
        <meta name="copyright" content="" />
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <title>乐易装</title>
        <!-- css -->
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_my_wealth.css"/>
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/index.js" type="text/javascript"></script>
        <script type="text/javascript">
            window.onload = function(){
                footer();
            }
        </script>
    </head>
    <body class="bgc-f3f4f6">
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>余额提现</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 提现 -->
        <article class="tixian">
            <form>
                <div class="inp_text">
                    <label>姓名</label>
                    <input class="text" type="text" placeholder="收款人姓名">
                </div>
                <div class="inp_text">
                    <label>卡号</label>
                    <input class="text" type="text" placeholder="收款人储蓄卡号">
                </div>
                <div class="inp_text">
                    <label>银行</label>
                    <select class="text select">
                        <option>中国建设银行</option>
                        <option>中国工商银行</option>
                        <option>中国农业银行</option>
                    </select>
                </div>
                <div class="inp_text money">
                <label>金额</label>
                    <input class="text" type="text" placeholder="提现金额">
                </div>
                <input class="btn-submit-save" style="background-color:#cc1421;" type="submit" value="下一步">
                <div class="tips">可提现余额：<span>250</span>元</div>
            </form>
        </article>
    <!-- 提现 END -->
    </body>
</html>