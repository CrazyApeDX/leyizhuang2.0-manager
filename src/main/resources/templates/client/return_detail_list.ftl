    <script src="js/jquery-1.11.0.js" type="text/javascript"></script>
    <style>
        li {
            list-style: none;
            height : 25px;
        }

        ul,li {
            margin: 0;
            padding: 0;
        }
        .win_yn {
            overflow: hidden;
            position: fixed;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.3);
            /*display: block;*/
        }

        .win_yn .my_left {
            overflow: hidden;
            width: 80%;
            padding: 14px 5% 90px;
            height: 230px;
            margin: 64px auto auto;
            border-radius: 8px;
            background: white;
            position: relative;
        }

        .win_yn .my_left .close {
            width: 100%;
            position: absolute;
            bottom: 16px;
            left: 0;
            text-align: center;
        }

        .win_yn .my_left .sub {
            border: none;
            outline: none;
            color: white;
            width: 60px;
            height: 26px;
            border-radius: 4px;
            background: #cc1421;
            margin: auto;
        }

        .win_yn .my_left .btn_no {
            border: none;
            outline: none;
            color: white;
            width: 60px;
            height: 26px;
            border-radius: 4px;
            background: #cc1421;
            margin : 0px 10px;
        }

        .add_title {
            width: 100%;
            text-align: left;
            line-height: 30px;
            font-size: 1.2rem;
        }

        #txtbanknum {
            width: 100%;
            line-height: 24px;
            font-size: 14px;
            border: 1px solid #d2d2d2;
            border-radius: 3px;
            margin: 10px 0;
        }

        /*添加的样式  设置的时候设置好高度  上面的样式只要你样式名没有改  基本上没得撒子问题 */
        .overlisthide {
            width: 100%;
            height: 230px;
            overflow-y: auto;
        }

    </style>
	<script>
		/*根据id控制弹窗关闭*/
		function windowoc(id, closeoropen) { //弹窗id  关闭false 打开true
	        if (closeoropen) {
	            $('#' + id).animate({height: '100%'});
	            $('#' + id).css('display','block');
	        } else {
	            $('#' + id).animate({height: 0});
	        }
	    }
	</script>



<!--弹窗内容-->
<div class="win_yn" id="win_yn_return" style="display:none;z-index:250;">
    <div class="my_left">
        <div class="close">
        	<input class="btn_no" type="button" value="联系客服" style="background:#ffaa00;" onclick="window.location.href='tel://<#if telphone??>${telphone!''}</#if>'"/>
            <input class="btn_no" type="button" value="关闭" onclick="windowoc('win_yn_return',false)"/>
        </div>

        <div class="add_title" style="border-bottom:1px solid gray;float:left;">
        	<img src="/client/images/return_icon.png" height="30px" width="30px" style="float:left;margin-bottom:10px;">
        	<b  style="text-align:left;float:left;margin-bottom:10px;">退款明细</b>
    	</div>

        <!--就这里要加个样式名而已  我给你改了个样式名 应该不得重复了-->
        <div class="overlisthide">
            <ul id="info_detail" style="height:230px;margin-top:20px;">
            
            </ul>
        </div>


    </div>
</div>



