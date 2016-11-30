<#if info_list??>
    <#list info_list as item>
        <div class="swiper-slide" onclick="selectInfo(${item.id?c});">
            <div class="box">
                <p>
                    <#if operation_type??&&operation_type==0>
                        ${item.title!''}
                    <#elseif operation_type??&&operation_type==1&&item.realName??&&""!=item.realName>
                        ${item.realName!''}
                    </#if>
                </p>
            </div>
        </div>
    </#list>
</#if>
<script>
function searchSomeInfo(){
    wait();
    var keywords = $("#info_keywords").val();
    var diySiteName= $("#diySite_info").html();
    $.ajax({
        url:"/order/search/info",
        type:"POST",
        timeout:10000,
        data:{
            keywords:keywords,
            type:${operation_type!'-1'},
            diySiteName:diySiteName
        },
        error:function(){
            close(1);
            warning("亲，您的网速不给力啊");
        },
        success:function(res){
            close(1);
            $("#changeInfo").html(res);
        }
    });
}

function selectInfo(infoId){
    wait(1);
    $.ajax({
        url:"/order/select/info",
        type:"post",
        timeout:10000,
        data:{
            id:infoId,
            type:${operation_type!'-1'}
        },
        error:function(){
            close(1);
            warning("亲，您的网速不给力啊"); 
        },
        success:function(res){
            close(1);
            win_no();
            $("#seller_info").html(res.sellerName);
            $("#diySite_info").html(res.diyTitle);
            $("#diySite").val(res.diyId);
        }
    });
}
</script>