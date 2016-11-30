<script type="text/javascript">
    //等待开始方法
    function wait(){
        var wait = ($(window).height() - $('.wait img').height())/2;
        $('.wait img').css({marginTop:wait})            
        $('.wait').show();
    };
    
    //等待关闭方法
    function close(times){
        var timer = setTimeout(function(){
            $('.wait').hide()
            },times);
    };
</script>
<style>
    .wait{
        width: 100%;
        height: 100%;
        position:fixed;
        left: 0px;
        top: 0px;
        z-index: 99999999999999999;
        text-align: center;
        background: url(/client/images/colo_waitbd.png);
        display:none;
    }
</style>
<#-- 等待响应的旋转图标 -->
<div class="wait">  
    <img src="/client/images/colo_wait.gif" />
</div>