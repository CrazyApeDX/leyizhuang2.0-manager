<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑优惠券</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
    //  发放优惠券
    function grantCoupon(userId) {
        var dialog = $.dialog.confirm('确定给改会员发放优惠券？', function () {
            var number = $.trim($("#quan"+userId).val());
            var leftNumber = $.trim($("#leftNumber").html());
            var couponId = $.trim($("#couponId").val());
            var sellerId = $.trim($("#sellerId" + userId).val());
            var re = /^[0-9]*[1-9][0-9]*$/;
            
            if(undefined == number || "" ==number || !re.test(number))
            {
                alert("输入正确的发放数量！");
                return ;
            }
            
            var postData = { "number": number, "userId": userId, "couponId":couponId, "sellerId":sellerId};
            //发送AJAX请求
            sendAjaxUrl(dialog, postData, "/Verwalter/coupon/grantOne");
            return false;
        });

    }
   //发送AJAX请求
    function sendAjaxUrl(winObj, postData, sendUrl) {
        $.ajax({
            type: "post",
            url: sendUrl,
            data: postData,
            dataType: "json",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.dialog.alert('尝试发送失败，错误信息：' + errorThrown, function () { }, winObj);
            },
            success: function (data) {
                if (data.code == 0) {
                    winObj.close();
                    $.dialog.tips(data.msg, 2, '32X32/succ.png', function () { location.reload(); }); //刷新页面
                } else {
                    $.dialog.alert('错误提示：' + data.message, function () { }, winObj);
                }
            }
        });
    }

</script>
<body class="mainbody">

<div>
<input name="couponId" type="text" value='<#if coupon??>${coupon.id?c}</#if>' style="display:none" id="couponId">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/coupon/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/coupon/list"><span>优惠券</span></a>
  <i class="arrow"></i>
  <span>发放优惠券</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">发放优惠券</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dt>优惠券类型：</dt>
    <dd>
        <span>
            <#switch coupon.typeCategoryId>
                <#case 1>
                    通用现金券
                    <#break>    
                <#case 2>
                    指定商品现金券
                    <#break>
                <#case 3>
                    产品券
                    <#break>    
                <#default>
                    无类别 
                </#switch>  
        </span>&emsp;&emsp;&emsp;
        现金券名称：
          <span >${coupon.typeTitle!''}</span>&emsp;&emsp;&emsp;
        现金券金额：
          <span>${coupon.price!'0'?number?string('0.00')}</span>&emsp;&emsp;&emsp;
        剩余数量：
          <span id="leftNumber">${coupon.leftNumber!'0'}</span>
      </dd>
   </dl>
  <#if cou_goods??>
        <dl>
            <dt>指定产品</dt>
            <dd>
              <input name="goodsId" type="hidden" value="${cou_goods.id!'0'}" >
              <input  type="text" value="${cou_goods.title!''}" class="input text">
              <span class="Validform_checktip">*仅相对于指定商品券和产品券</span>
            </dd>
         </dl>
  </#if>
</div>
<form action="/Verwalter/coupon/grant/${couponId?c}" method="POST" id="form1">
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<#if cityName??><input type="hidden" name="cityName" id="cityName" value="${cityName}"></#if>
<script type="text/javascript">
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}

function checkAllNumber(chkobj) {
    if ($(chkobj).text() == "全选") {
        $(chkobj).children("span").text("取消");
        $("#quantityId input:enabled").val($("#setQuantity").val());
    } else {
        $(chkobj).children("span").text("全选");
        $("#quantityId input:enabled").val($("#setQuantity").val());
    }
}

</script>

<div class="tab-content">
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${keywords!''}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
      <a id="" class="" href="javascript:__doPostBack('grantMore','')">一键派发</a>
      <a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a>
      <a class="all" href="javascript:;" onclick="checkAllNumber(this);"><i></i><span>设置数量</span></a>
      <input type="text" id="setQuantity">
      
    </div>
      <table width="60%" border="0" cellspacing="0" cellpadding="0" class="ltable">
          <tbody>
          <tr class="odd_bg">
            <th width="8%">选择</th>
            <th align="left" >用户名</th>
            <th align="left" >归属门店</th>
            <th align="left" >归属导购</th>
            <th align="left" >发券数量</th>
            <th width="10%">操作</th>
          </tr>
        
            <#if user_page??>
                <#list user_page.content as user>
                    <tr>
                        <td align="center">
                            <span class="checkall" style="vertical-align:middle;">
                                <input id="listChkId" type="checkbox" name="listChkId" value="${user_index?c}" >
                            </span>
                            <input type="hidden" name="listId" id="listId" value="${user.id?c}">
                        </td>
                        <td>
                          <div class="user-box">
                            <h4><b>${user.username!""}</b> (姓名：${user.realName!""})</h4>
                          </div>
                        </td>
                        <td align="left">${user.diyName!""}</td>
                        <td align="left">
	                        <select name="sellerId" id="sellerId${user.id?c}"  datatype="*" sucmsg=" " style="width:100px;">
		                        <option value="">无归属导购</option>
		                        <#if sellers??> 
		                            <#list sellers as seller>
		                            	<#if coupon?? && coupon.diySiteCode?? && coupon.diySiteCode==seller.diyCode>
		                                	<option value="${seller.id?c}">${seller.realName!''}</option>
	                                	</#if>
		                            </#list>
		                        </#if>
		                        
	                    	</select>
                        </td>
                        <td id="quantityId"><input type="number" name="quantity" id="quan${user.id?c}"></td>
                        <td align="center">
                            <a href="javascript:grantCoupon(${user.id?c});">发券</a> 
                      </tr>
                </#list>
            </#if>
        </tbody>
      </table>
</div>
<#assign PAGE_DATA=user_page />
<#include "/site_mag/list_footer.ftl" />
</form>
<!--/内容-->

<!--工具栏-->
<!--/工具栏-->



</body></html>
