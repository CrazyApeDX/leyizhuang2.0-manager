<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta charset="gb2312">
		<title>确认支付</title>
		
		<link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
		
		<script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
		<script src="/client/js/rich_lee.js" type="text/javascript"></script>
		<script src="/client/js/union_pay.js" type="text/javascript"></script>
		<script src="/client/js/union_md5.js" type="text/javascript"></script>
	</head>
	<style type="text/css">
		.add316{
			width: 100%;
		}
		.add316 li{
			width: 90%;
			padding: 0 5%;
			line-height: 46px;
			margin-top: 10px;
			background: white;
		}
		.add316 li label{
			float: left;
			width: 60px;
			text-align: right;
		}
		.add316 li:nth-last-of-type(1){
			margin-bottom: 10px;
		}
		.sub{
			position: absolute;
			left: 0px;
			bottom: 20px;
			width: 80%;
			height: 40px;
			background: #cc1421;
			border: none;
			font-size: 1.2em;
			margin-left: 10%;
			border-radius: 6px;
			color: white;
		}
	</style>
	<script type="text/javascript">
		html_height();
		function html_height(){
			var oHtml = document.getElementsByTagName('html')[0];
			var win_hi =document.documentElement.clientHeight;
			var doc_hi =document.documentElement.offsetHeight;
			if(doc_hi>=win_hi){
				oHtml.style.height = doc_hi + 'px';
			}else{
				oHtml.style.height = win_hi + 'px';
			};
		}
		$(function(){
			DOTYPE_onclick();
		});
	</script>
	<body style="height: 100%; background: #f3f4f6;">
		<div>
			<div class="sec_header">
				<a class="back" href="/"></a>
				<p>确认支付</p>
			</div>
			<article class="product-volume">
  		</div>
  		<ul class="add316">
  			<li>
  				<label>订单号：</label><span>${ORDERID!''}</span>
  			</li>
  			<li>
  				<label>金额：</label><span>￥${PAYMENT!''}</span>
  			</li>
  		</ul>
  		<form action="" method="post" name="MD5form" id="MD5form">
			<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
				<tr style="display:none">
					<td height="20">商户接口类型</td>
					<td height="20">
						<select name="INTER_FLAG" id="INTER_FLAG">
							<option value='3' selected="selected">防钩鱼</option>
						</select>
					</td>
				</tr>
				<tr style="display:none"> 
					<td width="26%" height="20" nowrap>商户代码【MERCHANTID】</td>
					<td width="74%" height="20"><input name="MERCHANTID" type="text" class="text1" id="MERCHANTID" value="${MERCHANTID!''}"></td>
				</tr>
				<tr style="display:none"> 
					<td height="20">商户柜台代码【POSID】</td>
					<td height="20"><input name="POSID" type="text" class="text1" id="POSID" value="${POSID!''}"></td>
				</tr>
				<tr style="display:none"> 
					<td height="20">分行代码【BRANCHID】</td>
					<td height="20"><input name="BRANCHID" type="text" class="text1" id="BRANCHID" value="${BRANCHID!''}"></td>
				</tr>
				<tr style="display:none"> 
					<td height="20">定单号【ORDERID】</td>
					<td height="20"><input name="ORDERID" type="text" class="text1" id="ORDERID" value="${ORDERID!''}"></td>
				</tr>
				<tr style="display:none"> 
					<td height="20">付款金额【PAYMENT】</td>
					<td height="20"><input name="PAYMENT" type="text" class="text1" id="PAYMENT" value=${PAYMENT!''}></td>
				</tr>
				<tr style="display:none"> 
					<td height="20">币种【CURCODE】</td>
					<td height="20"><input name="CURCODE" type="text" class="text1" id="CURCODE" value="${CURCODE!''}"></td>
				</tr>
				<tr style="display:none"> 
					<td height="20">交易码【TXCODE】</td>
					<td height="20"><input name="TXCODE" type="text" class="text1" id="TXCODE" value="${TXCODE!''}"></td>
				</tr>
				<tr style="display:none"> 
                  <td height="20">备注1【REMARK1】</td>
                  <td height="20"><input name="REMARK1" type="text" class="text1" id="REMARK1"></td>
                </tr>
                <tr style="display:none">
                  <td height="20">备注2【REMARK2】</td>
                  <td height="20"><input name="REMARK2" type="text" class="text1" id="REMARK2"></td>
                </tr>
				<tr id="PUB32TR" style="display:none"> 
					<td height="20">公钥前30位【PUB32】</td>
					<td height="20"><input name="PUB32" type="text" class="text1" id="PUB32" value=""></td>
				</tr>
				<tr id="PUB32TR1" style="display:none"> 
					<td height="20">公钥后30位【PUB】</td>
					<td height="20"><input name="PUB32TR2" type="text" class="text1" id="PUB32TR2" value="${PUB32TR2!''}"></td>
				</tr>
				<tr id='GATEWAYTR' style='display:none'> 
					<td height="20">网关类型【GATEWAY】</td>
					<td height="20"><input name="GATEWAY" type="text" class="text1" id="GATEWAY" value="UnionPay"></td>
				</tr>
				<tr id='CLIENTIPTR' style='display:none'> 
					<td height="20">客户端IP【CLIENTIP】</td>
					<td height="20"><input name="CLIENTIP" type="text" class="text1" id="CLIENTIP" value=""></td>
				</tr>
				<tr id='REGINFOTR' style='display:none'> 
					<td height="20">注册信息【REGINFO】</td>
					<td height="20"><input name="REGINFO" type="text" class="text1" id="REGINFO" value=""></td>
				</tr>
				<tr id='PROINFOTR' style='display:none'> 
					<td height="20">商品信息【PROINFO】</td>
					<td height="20"><input name="PROINFO" type="text" class="text1" id="PROINFO" value=""></td>
				</tr>
				<tr id='MER_REFERERTR' style='display:none'> 
					<td height="20">商户域名【REFERER】</td>
					<td height="20"><input name="MER_REFERER" type="text" class="text1" id="MER_REFERER" value=""></td>
				</tr>
				<tr style='display:none'> 
					<td height="20">银行网址【URL】</td>
					<td height="20">
					<select name="bankURL" id="bankURL">
						<option value='https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain'>生产:https://ibsbjstar.ccb.com.cn/CCBIS/ccbMain</option>
					</select>
					<br>
					<span class="smallfont">说明：要从http://开始添加，到？号以前，？号不要添加</span></td>
				</tr>
				<tr style='display:none'> 
					<td height="20" colspan="2">
						<div align="center"> 
							<input name="makeMd5" type="button" id="makeMd5" value="生成MD5串" onClick="make()">
							<input type="button" name="Submit2" value="重置输入数据项" onClick="newRest()">
						</div>
					</td>
				</tr>
				<tr style='display:none'> 
					<td height="20" colspan="2">
						<div align="center"> 
							<textarea name="result" cols="80" rows="10" wrap="VIRTUAL" class="text" id="result"></textarea>
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" name="MAC" id="MAC" value="">
			<input type="hidden" name="URL01" id="URL01" value="">
			<input type="hidden" name="REM1" id="REM1" value="">
			<input type="hidden" name="REM2" id="REM2" value="">
		</form>
		<input class="sub" name="sendB" type="button" id="sendB" onClick="formPay();" value="请确认支付" />
	</body>
	<script>
		function formPay(){
			make();
			go(window.MD5form.result.value);
		}
	</script>
</html>