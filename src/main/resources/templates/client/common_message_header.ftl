<script type="text/javascript">
    $(function(){
        turn_hei($('.det_banner'),0.8) 
        $('.det_banner img').width($(window).width())
        sub_nav();
        function sub_nav(){
            var btn =true;
            $('#sub').click(function(){
                if(btn){
                    $('.rsub_nav').slideDown(400);  
                }else{
                    $('.rsub_nav').slideUp(400);    
                };
                btn = !btn;
            });
        };
    })
</script>
<div class="sec_header">
    <a class="back" href="javascript:history.go(-1);"></a>
    <p>消息中心</p>
    <span id="sub"></span>
    <dl class="rsub_nav">
        <dt></dt>
        <dd class="dd1">
            <a href="/message">消息</a>              
        </dd>
        <dd class="dd2">
            <a href="/">首页 </a>             
        </dd>
    </dl>
</div>