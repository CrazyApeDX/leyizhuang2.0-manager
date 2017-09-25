<!-- js -->
<script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="/client/js/touch-0.2.14.min.js"></script>
<#if cartList??&&cartList?size gt 0>
    <article class="my-selected">
        <!-- 已选列表 -->
        <#list cartList as goods>
            <div class="selected-list" id="${goods.goodsId?c}">
                <div class="swipe"></div>
                <section class="sec1">
                    <div class="img">
                        <img src="${goods.imageUri!''}" alt="产品图片">
                    </div>
                    <div class="product-info">
                        <div class="descript">${goods.goodsTitle!''} </div>
                        <label>${goods.goodsSku!''}</label>
                        <div class="choose-num">
                            <#if ("goods"+goods_index)?eval??>
                                <input type="hidden" id="goods${goods.goodsId?c}quantity" value="<#if ("goods"+goods_index)?eval??>${("goods"+goods_index)?eval?c}<#else>0</#if>"> 
                            </#if>
                            <!-- 数量选择 -->
                            <div class="numbers">
                                <a class="less" href="javascript:operate('del',0,${goods.goodsId?c});">-</a>
                                <!-- 可以手动修改数量 -->
                                <input type="text" onkeyup="keyup(this)" onafterpaste="afterpaste(this)" onchange="javascript:operate('key',0,${goods.goodsId?c});" id="goods${goods.goodsId?c}" value="${goods.quantity?c}" >
                                <a class="add" href="javascript:operate('add',0,${goods.goodsId?c});">+</a>
                            </div>
                            <div class="price" id="goods${goods.goodsId?c}price">￥
                                <span>
                                    <#if goods.price??&&goods.quantity??>
                                        ${((goods.price)*(goods.quantity))?string("0.00")}
                                    </#if>
                                </span>
                            </div>
                        </div>
                    </div>
                </section>
                <a class="btn-backspace"></a>
            </div>
        </#list>
    </article>
</#if>
<div class="select-total-money">总额：<strong id="all_price">￥<#if totalPrice??>${totalPrice?string("0.00")}<#else>0.00</#if><strong></div>
<#-- 创建一个隐藏标签用于存储当前已选有多少商品（整合后） -->
<span id="select_num" style="display:none"><#if cartList??>${cartList?size}<#else>0</#if></span>
<script type="text/javascript">
$(function touch(){
   
    <#-- 点击删除 -->
    $(".btn-backspace").click(function(){
        <#--$(this).parent(".selected-list").remove();-->
        var goodsId = $(this).parent(".selected-list").attr("id");
        <#-- 开启等待图标 -->
        wait();
        <#-- 发送异步请求 -->
        $.ajax({
            url:"/fit/cart/delete/" + goodsId,
            type:"post",
            timeout:10000,
            error:function(XMLHttpRequest, textStatus, errorThrown){
                <#-- 关闭等待图标 -->
                close(1);
                warning("亲，您的网速不给力啊");
            },
            success:function(res){
                <#-- 关闭等待图标 -->
                close(100);
                if("SUCCESS" == res.actionCode){
                    $("#"+goodsId).remove();
                }else{
                    warning("操作失败");
                }
                var number = $("#select_num").html();
                $("#select_num").html(number - 1);               
                $("#all_price").html("￥"+res.content);   
            }
        })
    });
});
//限制输入 只能输入数字
function keyup(obj){
	if(obj.value.length==1){obj.value=obj.value.replace(/[^1-9]/g,'')}else{obj.value=obj.value.replace(/\D/g,'')};
}
//限制输入 只能输入数字
function afterpaste(obj){
	if(obj.value.length==1){obj.value=obj.value.replace(/[^1-9]/g,'')}else{obj.value=obj.value.replace(/\D/g,'')};
}
</script>

<script type="text/javascript">
    $(function(){
        var $bEdit = $("header .btn-edit");
        var $move = $(".my-selected .selected-list .sec1");
        var $bSpace = $(".my-selected .selected-list .btn-backspace");
        var onOff = true;
        
        $bEdit.click(function(){
            if(onOff){
            	$("#info").html("完成");
                $move.addClass("selected");
                $bSpace.css("right","0px");       
            }else{
            	$("#info").html("编辑");
                $move.removeClass("selected");
                $bSpace.css("right","-80px");
            }
            onOff = !onOff;
      });
    });
</script>



