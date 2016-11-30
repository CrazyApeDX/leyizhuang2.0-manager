<!DOCTYPE html>
<html lang="zh-CN" class="bgc-f3f4f6">
    <head>
    <style>
        #suggestion_category{width:100%;height:30px;outline:none;border-radius:4px;};
    </style>
        <meta charset="UTF-8">
        <meta name="keywords" content="">
        <meta name="copyright" content="" />
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
        <title>乐易装</title>
        <!-- css -->
        <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_common.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/x_gu_sales.css"/>
        <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
        
        <script type="text/javascript" src="/client/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/client/js/user_suggestion.js"></script>
        <script src="/client/js/index.js" type="text/javascript"></script>
        <script type="text/javascript">
            window.onload = function(){
                footer();
            }
        </script>
        <style type="text/css">
            input{
                -webkit-appearance:none;            
            }
            select{
                -webkit-appearance:none;
            }
        </style>
    </head>
    <body class="bgc-f3f4f6">
        <#-- 引入警告提示样式 -->
        <#include "/client/common_warn.ftl">
        <#-- 引入等待提示样式 -->
        <#include "/client/common_wait.ftl">  
        <#-- 引入下单方式选择滑动窗 -->
        <#include "/client/common_shopping_type.ftl">
        <!-- 头部 -->
        <header>
            <a class="back" href="javascript:history.go(-1);"></a>
            <p>投诉建议</p>
        </header>
        <!-- 头部 END -->
        
        <!-- 投诉建议 -->
        <article class="suggest">
            <div class="title">送货、退换货及咨询请联系<a href="tel://<#if telphone??>${telphone!''}</#if>">电话客服</a></div>
            <#if category_list??&&category_list?size gt 0>
                <div class="title" style="background:none;">
                    <select name="categoryId" id="suggestion_category">
                        <#list category_list as item>
                            <option value="${item.id?c}">${item.name!''}</option>
                        </#list>
                    </select>
                </div>
            </#if>
            <div class="textarea">
                <div class="headline">我们存在哪些不足</div>
                <textarea id="suggestion" name="suggestion"></textarea>
                <div class="headline">您的联系电话</div>
                <input class="phone-num-textarea" id="phone" type="text" name="phone" value="${username!''}">
            </div>
            <input class="btn-submit-save" id="button" type="button" onclick="submitTheSuggestion();" value="提交" style="background-color: #cc0000;">
        </article>
        <!-- 投诉建议 END -->
        
        <div class="clear h66"></div>
        
        <!-- 底部 -->
        <#include "/client/common_footer.ftl">
        <!-- 底部 END -->
    </body>
</html>