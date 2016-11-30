<style>
    .win_yn{
        overflow: hidden;
        position: fixed;
        left: 0px;
        top: 0px;
        width: 100%;
        height: 0;
        background: rgba(0,0,0,0.3);
        /*display: none;*/
    }
    .win_yn .my_left{
        overflow: hidden;
        width:80%;
        padding: 0 5%;
        padding-bottom: 40px;
        padding-top: 52px;
        height: 230px;
        margin: auto;
        border-radius: 8px;
        background:white;
        margin-top: 64px;
    }
    .win_yn .my_left .sub{
        position: absolute;
        bottom:10px;
        border: none;
        outline: none;
        color: white;
        width: 60px;
        height: 26px;
        border-radius: 4px;
        left: 30px;
        background: #cc1421;
    }
    .win_yn .my_left .btn_no{
    margin:auto;
        border: none;
        outline: none;
        color: white;
        width: 60px;
        height: 26px;
        border-radius: 4px;
        background: #cc1421;
    }
    .win_yn .swiper-slide{
        overflow: hidden;
        line-height: 20px;
        height: 60px;
        border-bottom: #EEEEEE 1px dashed;
    }
    .win_yn .swiper-slide input{
        float: left;
        width: 16px;
        height: 16px;
        margin-top:20px;
    }
    .win_yn .swiper-slide div{
        float: left;
        padding:0 2%;
        margin-top: 10px;
    }
    .my_left .swiper-container {
        width: 100%;
        height: 230px;
        overflow:hidden;
    }
    .searchBox{
        overflow: hidden;
        position: absolute;
        left: 0px;
        top: 10px;
        height: 40px;
        line-height: 40px;
        width: 90%;
        margin: 0 5%;
        border: #EEEEEE 1px solid;
        border-radius:6px;
    }
    .searchBox input{
        float: left;
        border: none;
        outline: none;
        background: none;
    }
    .searchBox input:nth-of-type(1){
        width: 66%;
        height: 40px;
        padding-left:4% ;
    }
    .searchBox input:nth-of-type(2){
        width: 29%;
        height: 40px;
        color: #888888;
        border-left: #eeeeee 1px solid;
    }
</style>
<script type="text/javascript">
    function win_yes(){
        $('.win_yn').animate({height:'100%'})
    };
    
    function win_no(){  
        $('.win_yn').animate({height:0})
    };
</script>
<div class="win_yn" style="z-index:99">
    <div class="my_left">
        <div class="searchBox">
            <input placeholder="请输入搜索内容" type="text" name="" id="info_keywords" value="" />
            <input type="button" name="" id="" value="搜索" onclick="javascript:searchSomeInfo();" />
        </div>
        <#--
            <input class="sub" type="submit" name="" id="" value="确定" onclick="win_no()" />
        -->
        <div style="position:absolute;bottom:8px;left:0px;width:100%;text-align:center;"> 
            <input class="btn_no" type="button" name="" id="" value="关闭" onclick="win_no()" />
        </div>
        <div class="swiper-container">
            <div class="swiper-wrapper" id="changeInfo">
                <#include "/client/seller_diy_info.ftl">
            </div>
        </div>
        <script src="/client/js/swiper.min.js"></script>
        <script>
            var swiper = new Swiper('.my_left .swiper-container', {
                pagination: '.my_left .swiper-pagination',
                paginationClickable: true,
                direction: 'vertical',
                slidesPerView: 6
            });
        </script>
    </div>
</div>