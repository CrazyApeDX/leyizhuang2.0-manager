<script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<script>
	function cancel(id){
	  		$(".win_yn").hide();
			$.ajax({	
				url:"/return/cancel/"+id,
				method:"post",
				success:function(result){
					if (0 === result.status) {
					$(".cancelhide").hide();
					$(".mas").empty();
					$(".mas").append("退货取消");
						// success
						} else {
						close(-1);
						warning(result.message);
						console.log(result.message);
					}
				}
				
			}); 
	}
</script>
<!-- 订单列表 -->
<#if all_return_list??>
    <div id="all_orders" class="some_orders" style="background: #f3f4f6">
        <#list all_return_list as item>
            <ol class="order-list" <#if item_index==0>style="margin-top:0%;"</#if>>
                <li class="li1">
                    <label>退货单号：<span>${item.returnNumber!''}</span></label>
                    <div class="species">
                       <span class="mas"> ${item.statusName }</span>
                    </div>
                </li>
                <li class="li1">
                    <label>原订单号：<span>${item.orderNumber!''}</span></label>
                </li>
                <#if item.returnGoodsList??>
                    <#list item.returnGoodsList as goods>
                        <li class="li2">
                            <div class="img"><img src="${goods.goodsCoverImageUri!''}" alt="产品图片"></div>
                            <div class="product-info">
                                <div class="div1">${goods.goodsTitle!''}</div>
                                <div class="div2">￥<span><#if goods.price??>${goods.price?string("0.00")}<#else>0.00</#if></span>&nbsp;&nbsp;<label>数量：<span>${goods.quantity!'0'}</span></label></div>
                            </div>
                        </li>
                    </#list>
                </#if>
                <div style="width:80%;margin-left:3%;line-height:30px">
                    <div>退货单总额：<font style="color:red;">￥<#if item.turnPrice??>${item.turnPrice?string("0.00")}</#if></font></div>
                    <div>退货单时间：<#if item.orderTime??>${item.orderTime?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
                </div>
                <div class="li3">
                
               		 <#if item.statusName != "退货取消" && item.statusName != "已完成" && item.statusName != "待退款">
                	     <a href="javascript:win_yes('是否确定取消？','cancel(${item.id?c});');" class="cancelhide">取消退货</a>
                     </#if>
                
                     <a href="/user/return/detail/${item.id?c}">退货单详情</a>
                </div>
            </ol>
        </#list>
    </div>
</#if>