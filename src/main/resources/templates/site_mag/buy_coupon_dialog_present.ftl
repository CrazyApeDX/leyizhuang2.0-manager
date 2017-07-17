<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商品挑选</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js?skin=idialog"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
  $(function(){
  
  	<%  
  		String errMsg = (String)request.getParameter("errMsg");
	%>
	var error = '<%=errMsg%>';
	
  	if(null != error){
  		alert(error);
  	}
  } 
  );
</script>
</head>

<body>

<form name="form1" method="post" action="" id="form1">
<!--文字列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tbody>
        <tr class="odd_bg">
            <th align="center" width="10%">赠品ID</th>
            <th align="center" width="30%">赠品名称</th>
            <th align="center" width="20%">赠品单价</th>
            <th align="center" width="20%">赠品数量</th>
            <th align="center" width="20%">金额合计</th>
        </tr>
        
        <#if presentList??>
            <#list presentList as present>
            	<tr>
	                <td width="10%" align="center">${present.goodsId!""}</td>
	                <td width="30%" align="center">${present.goodsTitle!""}</td>
	                <td width="20%" align="center">${present.price!""}</td>
	                <td width="20%" align="center">${present.quantity!""}</td>
	                <td width="20%" align="center">${present.price!0 * present.quantity!0}</td> 
	            </tr>
            </#list>
        </#if>
</tbody>
</table>

</form>

</body>
</html>