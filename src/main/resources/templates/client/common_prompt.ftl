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
        <!-- 1-7新增文字描述页面样式 本段样式放在x_my_wealth.css末即可-->
        <style>
        .msg{
        	width:80%;
        	height:13em;
        	margin:5em auto;
        	border:1px solid #cccccc;
        	text-align: center;
        }
        .conten{
        	position: absolute;
        	width:100%;
        	top: 35%;
        	color: #ff2d2d;
        }
        .conten p{
        	color: #666;
        }
        .btn{
        	position: absolute;
        	bottom: 1em;
        	right:1em;
        	display: block;
		    width: 40px;
		    height: 27px;
		    line-height: 27px;
		    background-color:#ff2d2d;
		    color: #fff;
		    text-align: center;
		    border: none;
		    border-radius: 3px;
		    cursor: pointer;
        }
        </style>
        <script type="text/javascript"> 
			onload=function(){ 
				setInterval(go, 1000); 
			}; 
			var x=3; //利用了全局变量来执行 
			function go(){ 
				x--; 
				if(x>0){ 
					document.getElementById("showTime").innerHTML=x; //每次设置的x的值都不一样了。 
				}else{ 
					location.href='javascript:history.go(-1)'; 
				} 
			} 
		</script> 	
    </head>
    <body class="bgc-f3f4f6">
    
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>友情提示</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 文字描述 -->
        <div class="msg">
        <div class="conten">${msg!'' }<p><span id="showTime">3</span>秒后自动返回</p></div>
        <div class="btn" onclick="javascript:history.go(-1)">返回</div>
        </div>
        <!-- 文字描述 END -->
    
    </body>
</html>