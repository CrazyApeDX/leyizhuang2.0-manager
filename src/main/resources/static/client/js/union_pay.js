


/*通用数据
     

jd
420122196812097928
978906469
1129000003





//UAT数据－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
/*
  
310107197111101248

var MERCHANTID ='105290045112144';              //南航支付 
var POSID='100000188';                 //分行代码
var BRANCHID='310000000'; 
310227750502041

var MERCHANTID ='105110052111126';              //anti
var POSID='744133191';                 //referer
var BRANCHID='110000000'; 

var MERCHANTID ='105110052111126';              //anti
var POSID='805994782';                 //pos
var BRANCHID='110000000';               

var MERCHANTID ='105110052111126';              //anti
var POSID='272020778';                 
var BRANCHID='110000000';    

var MERCHANTID ='105290063000001';              //验证环境
var POSID='100000031';                 //anti
var BRANCHID='310000000'; 

var MERCHANTID ='105290045112144';              //南航支付 
var POSID='100000188';                 //分行代码
var BRANCHID='310000000'; 
aa1a50aef9f92d310d55497f020111

var MERCHANTID ='105110052111126';              //sit
var POSID='744133191';                 //分行代码
var BRANCHID='110000000'; 
var PUB32='48060ab8d0a827b9adba32d9020111';


var MERCHANTID ='105290063000001';              //验证环境
var POSID='100000031';                 //anti
var BRANCHID='310000000'; 

var INTER_FLAG='0';						//接口类型，若为'1'则表示新接口，新接口必需有商户公钥的前30位PUB32
var PUB32='48060ab8d0a827b9adba32d9020111';

**********************************************/


var MERCHANTID ='105290045112144';              //南航支付 
var POSID='100000037';                 //分行代码
var BRANCHID='310000000'; 

var INTER_FLAG='0';						//接口类型，若为'1'则表示新接口，新接口必需有商户公钥的前30位PUB32
var PUB32='9325a01ffe84834f2713709b020111';

/*
var MERCHANTID ='105110052111126';              //南航支付 -sit
var POSID='744133191';                 //分行代码
var BRANCHID='110000000'; 
var INTER_FLAG='0';
var PUB32TR2='48060ab8d0a827b9adba32d9020111';    
*/
           
var ORDERID=Math.round(Math.random()*100000)+1;//'001';

var PAYMENT='0.01';
var CURCODE='01';
var TXCODE='520100';
var OPERID='140828008-999';
var AUTHID='P0123456';
var PASSWORD='111111';
var REQUESTSN='sss';
var REMARK1='';
var REMARK2='';


var bankURL='http://128.128.96.175:8001/app/ccbMain'; //sit
//var bankURL='http://128.128.96.2:8001/app/ccbMain';  //ua
//var bankURL='https://ibsbjstar.ccb.com.cn/app/ccbMain';  //生产


//以下内容不要轻易改动
var strMD5;
var URL;
var URL0;//网上银行支付
var URL1;//E付卡支付

function setValue()
{
	
	var objMERCHANTID=document.getElementById("MERCHANTID");
	objMERCHANTID.value=MERCHANTID;
	
	
	var objPOSID=document.getElementById("POSID");
	objPOSID.value=POSID;	
	
	
	var objBRANCHID=document.getElementById("BRANCHID");
	objBRANCHID.value=BRANCHID;
	
	
	var objORDERID=document.getElementById("ORDERID");
	objORDERID.value=Math.round(Math.random()*100000)+1;

	
	
	
	var objPAYMENT=document.getElementById("PAYMENT");
	objPAYMENT.value=PAYMENT;
	
	
	
	var objCURCODE=document.getElementById("CURCODE");
	objCURCODE.value=CURCODE;
	
	
	var objTXCODE=document.getElementById("TXCODE");
	objTXCODE.value=TXCODE;
	
	
	var objREMARK1=document.getElementById("REMARK1");
	objREMARK1.value=REMARK1;
	
	
	var objREMARK2=document.getElementById("REMARK2");
	objREMARK2.value=REMARK2;
	
	
	var objbankURL=document.getElementById("bankURL");
	objbankURL.value=bankURL;

	
	document.getElementById("PUB32").value=PUB32;;
	document.getElementById("INTER_FLAG").value=INTER_FLAG;;
	//封闭提交按钮
	//window.MD5form.sendB.disabled = true;
	var objsendB=document.getElementById("sendB");
	objsendB.disabled=true;

}

function make()
{
	
	var tmp;
	var tmp0;
	var tmp1;
    var temp_New1;
  MERCHANTID=document.getElementById("MERCHANTID").value;
  
	
	POSID=document.getElementById("POSID").value;
	
	
	BRANCHID=document.getElementById("BRANCHID").value;
	
	
	ORDERID=document.getElementById("ORDERID").value;
	
	
	
	PAYMENT=document.getElementById("PAYMENT").value;
	

	CURCODE=document.getElementById("CURCODE").value;
	
	
	TXCODE=document.getElementById("TXCODE").value;
	

	REMARK1=document.getElementById("REMARK1").value;
	
	
	REMARK2=document.getElementById("REMARK2").value;
	
	
	bankURL=document.getElementById("bankURL").value;
	

    
	tmp ='MERCHANTID='+MERCHANTID+'&POSID='+POSID+'&BRANCHID='+BRANCHID+'&ORDERID='+ORDERID+'&PAYMENT='+PAYMENT+'&CURCODE='+CURCODE+'&TXCODE='+TXCODE+'&REMARK1='+REMARK1+'&REMARK2='+REMARK2;
	tmp0='MERCHANTID='+MERCHANTID+'&POSID='+POSID+'&BRANCHID='+BRANCHID+'&ORDERID='+ORDERID+'&PAYMENT='+PAYMENT+'&CURCODE='+CURCODE+'&TXCODE=520100'+'&REMARK1='+REMARK1+'&REMARK2='+REMARK2;
	
	index=document.getElementById("INTER_FLAG").value;
	temp_New=tmp;
	//alert("接口类型为："+index+"  (1为新接口类型，其他为旧接口类型)");

	if(index=="1"){
		temp_New=tmp+'&PUB32='+document.getElementById("PUB32").value;
	}
	if(index=="3"){
		
		temp_New=tmp+'&TYPE=1&PUB='+document.getElementById("PUB32TR2").value+'&GATEWAY='+document.getElementById("GATEWAY").value+'&CLIENTIP='+document.getElementById("CLIENTIP").value+'&REGINFO='+document.getElementById("REGINFO").value+'&PROINFO='+document.getElementById("PROINFO").value+'&REFERER='+document.getElementById("MER_REFERER").value;
		temp_New1=tmp+'&TYPE=1&GATEWAY='+document.getElementById("GATEWAY").value+'&CLIENTIP='+document.getElementById("CLIENTIP").value+'&REGINFO='+document.getElementById("REGINFO").value+'&PROINFO='+document.getElementById("PROINFO").value+'&REFERER='+document.getElementById("MER_REFERER").value;
	}

	if(index=='2'){
		tmp ='MERCHANTID='+MERCHANTID+'&POSID='+POSID+'&BRANCHID='+BRANCHID+'&ORDERID='+ORDERID+'&AUTHID='+AUTHID+'&USERID='+OPERID+'&PASSWORD='+PASSWORD+'&PAYMENT='+PAYMENT+'&REQUESTSN='+REQUESTSN+'&CURCODE='+CURCODE+'&TXCODE='+TXCODE+'&REM1='+REMARK1+'&REM2='+REMARK2;
		temp_New=tmp+'&PUB32='+document.getElementById("PUB32").value;
		document.getElementById('REM1').value=document.getElementById('REMARK1').value;
		document.getElementById('REM2').value=document.getElementById('REMARK2').value;
	}
	strMD5=hex_md5(temp_New);

    URL = bankURL+'?'+tmp+'&MAC='+strMD5;
	URL0 = bankURL+'?'+tmp0+'&MAC='+hex_md5(tmp0);
	
	URL3 = bankURL+'?'+temp_New1+'&MAC='+hex_md5(temp_New);
    
    document.getElementById("result").value=URL;


	
	document.getElementById("MAC").value=strMD5;
	
	
	document.getElementById("URL01").value=URL0+'||'+URL1;

  //打开提交按钮
  if (index=="3")
{
 document.getElementById("result").value=URL3;
}
	
	document.getElementById("sendB").disabled=false;

  //封闭MD5生成按钮
    
    document.getElementById("makeMd5").disabled=true;
}

function go( sendUrl)
{

	
	var objMD5form=document.getElementById("MD5form");
	
	objMD5form.method="post";
    var index=document.getElementById("INTER_FLAG").value;
 	if(index==2)
		objMD5form.action=bankURL+'?CCB_IBSVersion=V5';
	   //objMD5form.action=bankURL;
 	else
		objMD5form.action=sendUrl;


	objMD5form.submit();

}

function newRest()
{
   window.MD5form.sendB.disabled =true ;
   window.MD5form.makeMd5.disabled = false;

   window.MD5form.MERCHANTID.value=MERCHANTID;
   window.MD5form.POSID.value=POSID;
   window.MD5form.BRANCHID.value=BRANCHID;
   window.MD5form.ORDERID.value=Math.round(Math.random()*100000)+1;
   window.MD5form.PAYMENT.value=PAYMENT;
   window.MD5form.CURCODE.value=CURCODE;
	window.MD5form.TXCODE.value=TXCODE;
	window.MD5form.REMARK1.value=REMARK1;
	window.MD5form.REMARK2.value=REMARK2;
	window.MD5form.bankURL.value=bankURL;
   //window.MD5form.reset();
}

function DOTYPE_onclick()
{
var index=window.MD5form.INTER_FLAG.selectedIndex;
var value=window.MD5form.INTER_FLAG.options[index].value;
if (value==0)
{

document.getElementById("PUB32TR1").style.display='none';
document.getElementById("GATEWAYTR").style.display='none';
document.getElementById("CLIENTIPTR").style.display='none';
document.getElementById("REGINFOTR").style.display='none';
document.getElementById("PROINFOTR").style.display='none';
document.getElementById("MER_REFERERTR").style.display='none';


}else if(value==1){

document.getElementById("PUB32TR").style.display='';
document.getElementById("PUB32TR1").style.display='none';
document.getElementById("GATEWAYTR").style.display='none';
document.getElementById("CLIENTIPTR").style.display='none';
document.getElementById("REGINFOTR").style.display='none';
document.getElementById("PROINFOTR").style.display='none';
document.getElementById("MER_REFERERTR").style.display='none';



}
else if(value==3){

document.getElementById("PUB32TR").style.display='none';
document.getElementById("PUB32TR1").style.display='none';
document.getElementById("GATEWAYTR").style.display='none';
document.getElementById("CLIENTIPTR").style.display='none';
document.getElementById("REGINFOTR").style.display='none';
document.getElementById("PROINFOTR").style.display='none';
document.getElementById("MER_REFERERTR").style.display='none';

}

}