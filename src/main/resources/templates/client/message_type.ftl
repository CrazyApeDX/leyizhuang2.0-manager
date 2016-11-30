<!DOCTYPE html>
<html>
    <head>
        <meta name="keywords" content="">
        <meta name="description" content="">
        <meta name="copyright" content="" />
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <meta charset="utf-8">
        <title>乐易装-消息中心</title>
        
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        
        <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
        <script src="/client/js/rich_lee.js" type="text/javascript"></script>
    </head>
    <body>
        <div>
            <#include "/client/common_message_header.ftl">
            <section class="new_list">
                <#if all_type??>
                    <#list all_type as item>
                        <a href="/message/list/${item.id?c}">
                            <dl>
                                <dt><img src="${item.imgUrl!''}"></dt>
                                <dd>
                                    <h3>${item.name!''}</h3>
                                    <#if ("content"+item_index)?eval??>
                                        <span>${("content"+item_index)?eval!''}</span>
                                    <#else>
                                        <span></span>
                                    </#if>
                                </dd>
                            </dl>
                        </a>
                    </#list>
                </#if>
                <#-- 永远存在的一组：用户咨询/回复 -->
                <a href="/message/list/0">
                    <dl>
                        <dt><img src="/client/images/index_nav_icon08.png"></dt>
                        <dd>
                            <h3>咨询与回复</h3>
                            <span>3</span>
                            <span></span>
                        </dd>
                    </dl>
                </a>
            </section>
        </div>		
    </body>
</html>
