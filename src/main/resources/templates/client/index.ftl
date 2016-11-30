<!DOCTYPE html>
<html>
    <head>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="copyright" content="" />
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta charset="utf-8">
    <title>乐易装首页</title>
    <link rel="stylesheet" type="text/css" href="/client/css/my_base.css"/>
    <link rel="stylesheet" type="text/css" href="/client/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="/client/css/other.css"/>
    <link rel="stylesheet" type="text/css" href="/client/css/swiper.min.css"/>
    
    <script src="/client/js/jquery-1.11.0.js" type="text/javascript"></script>
    <script src="/client/js/index.js" type="text/javascript"></script>
    <script src="/client/js/swiper.min.js" type="text/javascript"></script>
    <script type="text/javascript">
    window.onload = function(){
            you_draf($('.index_scroll_goods'),$('.index_scroll_goods a'),$(window));
            my_hei();
            scroll_news();//滚动新闻                        
            footer();//底部居中
        };
    </script>
    </head>
    <body>
        <div>
            <#-- 引入公共购物方式选择滑动窗口 -->
            <#include "/client/common_shopping_type.ftl">
            <#-- 
                              引入有奖问答图标
            <#include "/client/common_award.ftl"> 
            -->
            
            <header class="index_head">
                <span><#if user??>${user.cityName!''}</#if></span>
                <form action="/goods/search" method="post">
                    <section class="search_box">                    
                        <input placeholder="快速搜索商品" type="text" name="keywords"/>
                        <input type="submit" name="" id="" value="" />
                    </section>
                </form>
                <div class="index_mes_contanier" onclick="window.location.href='/message'">
                    <div class="index_mes_box">
                        <img class="index_message" src="/client/images/index_mesicon01.png">
                        <p></p>
                    </div>
                </div>
            </header>
            
            <#if circle_ad_list??>
                <div class="index_banner">
                    <div class="swiper-container" style="width: 100%;height: 100%;">
                        <div class="swiper-wrapper">
                            <#list circle_ad_list as item>
                                <div class="swiper-slide orange-slide">
                                    <a href="${item.linkUri }"><img src="${item.fileUri!''}"/></a>
                                </div>
                            </#list>
                        </div>
                    <div class="swiper-pagination"></div>
                </div>
                <script type="text/javascript">
                    var mySwiper = new Swiper('.swiper-container',{
                        loop: true,
                        autoplay: 3000,
                        pagination : '.swiper-pagination'
                    });   
                </script>
                </div>
            </#if>
            <#if navi_bar_list??>
                <ul class="index_nav">
                    <li>
                        <#list navi_bar_list as item>
                            <#if item_index lt 4>
                                <a href="${item.linkUri!'#'}">
                                    <img src="${item.iconUri!''}" />
                                    <span>${item.title!''}</span>
                                </a>
                            </#if>
                        </#list>
                    </li>
                    <li>
                        <#list navi_bar_list as item>
                            <#if item_index gt 3&&item_index lt 8>
                                <a href="${item.linkUri!'#'}">
                                    <img src="${item.iconUri!''}" />
                                    <span>${item.title!''}</span>
                                </a>
                            </#if>
                        </#list>
                    </li>
                </ul>
            </#if>
            
            <section class="index_content">
                <#if headline_list??>
                    <dl class="index_news">
                        <dt><img src="/client/images/scroll_news.png" /></dt>
                        <dd>
                            <div class="scroll_newsbox">
                                <#list headline_list as item>
                                    <a href="/article/${item.id?c}">${item.title!''}</a>
                                </#list>
                            </div>
                        </dd>               
                    </dl>
                </#if>
                <div class="index_test_box"></div>
                <#if promotion_list??&&promotion_list?size gt 0>
                    <div class="index_goods01">
                        <dl>
                            <dt>
                                <p>活动促销<span>·优惠多多</span></p>                          
                                <a href="/promotion/list"><img src="/client/images/index_guide_right.png" /></a>
                            </dt>
                            <#-- 滑动促销商品 -->
                            <dd>
                                <div class="index_scroll_goods">
                                    <#-- 遍历所有促销集合 -->
                                    <#list promotion_list as item>
                                        <#-- 此位置只显示序号0——6的促销商品 -->
                                        <#if item_index lt 7>
                                            <#if item??>
                                                <#-- item的格式为【TdGoods:price】 -->
                                                <#list item?keys as key>
                                                    <#if key??>
                                                        <a href="/goods/detail/${key.id?c}">
                                                            <div>
                                                                <img src="${key.coverImageUri!''}"  />
                                                                <span>促销</span>
                                                                
                                                            </div>
                                                            <#-- 判断指定的key值是否存在 -->
                                                            <#if item.get(key)??>
                                                                <p>￥${item.get(key)?string("0.00")}</p>                                 
                                                            <#else>
                                                                <p>￥0.00</p>         
                                                            </#if>
                                                        </a>
                                                    </#if>
                                                </#list>
                                            </#if>
                                        </#if>
                                    </#list>
                                </div>
                            </dd>
                        </dl>
                    </div>
                
                    <div class="index_goods02">
                        <#-- 特殊的三个推荐（序号7——9的三个推荐的促销商品） -->    
                        <dl>
                            <#-- 遍历所有促销集合 -->
                            <#list promotion_list as item>
                                <#if item_index==7>
                                    <#list item?keys as key>
                                        <#if key??>
                                            <dt>
                                                <a class="my_indexgood" href="/goods/detail/${key.id?c}">
                                                    <h3>${key.title!''}</h3><div class="guide"></div>
                                                    <p>${key.subTitle!''}</p>
                                                    <#if item.get(key)??>
                                                        <span>￥${item.get(key)?string("0.00")}</span>
                                                    <#else>
                                                        <span>￥0.00</span>
                                                    </#if>
                                                    <div><img src="${key.coverImageUri!''}" /></div>
                                                </a>
                                            </dt>
                                        </#if>
                                    </#list>
                                </#if>
                                <#if item_index==8>
                                    <#list item?keys as key>
                                        <#if key??>
                                            <dd>
                                                <a class="my_indexgood02" href="/goods/detail/${key.id?c}">
                                                    <div class="rich_box">
                                                        <h3>${key.title!''}</h3><div class="guide"></div>
                                                        <p>${key.subTitle!''}</p>
                                                        <#if item.get(key)??>
                                                            <span>￥${item.get(key)?string("0.00")}</span>
                                                        <#else>
                                                            <span>￥0.00</span>
                                                        </#if>
                                                    </div>
                                                    <div><img src="${key.coverImageUri!''}" /></div>                              
                                                </a>
                                            </dd>
                                        </#if>
                                    </#list>
                                </#if>
                                <#if item_index==9>
                                    <#list item?keys as key>
                                        <#if key??>
                                            <dd>
                                                <a class="my_indexgood02" href="/goods/detail/${key.id?c}">
                                                    <div class="rich_box">
                                                        <h3>${key.title!''}</h3><div class="guide"></div>
                                                        <p>${key.subTitle!''}</p>
                                                        <#if item.get(key)??>
                                                            <span>￥${item.get(key)?string("0.00")}</span>
                                                        <#else>
                                                            <span>￥0.00</span>
                                                        </#if>
                                                    </div>
                                                    <div><img src="${key.coverImageUri!''}" /></div>                              
                                                </a>
                                            </dd>
                                        </#if>
                                    </#list>
                                </#if>
                            </#list>
                        </dl>
                        <#-- 最少的三个推荐 -->
                        <ul>
                            <#list promotion_list as item>
                                <#if item_index gt 9 && item_index lt 13>
                                    <li>
                                        <a class="my_indexgood03" href="/goods/detail/${key.id?c}">
                                            <h3>${key.title!''}</h3><div class="guide"></div>
                                            <p>${key.subTitle!''}</p>
                                            <#if item.get(key)??>
                                                <span>￥${item.get(key)?string("0.00")}</span>
                                            <#else>
                                                <span>￥0.00</span>
                                            </#if>
                                            <div><img src="${key.coverImageUri!''}" /></div>
                                        </a>
                                    </li>
                                </#if>
                            </#list>
                        </ul>
                    </div>
                </#if>
                <div class="index_test_box"></div>
                <div class="index_goods03">
                    <#if index_center??>
                        <div class="goods03_title">
                           <img onclick="window.location.href='${index_center.linkUri!'#'}'" src="${index_center.fileUri!''}"/>
                        </div>
                    </#if>
                    <#if commend_page??>
                        <ul>
                            <li>
                                <#list commend_page.content as item>
                                    <a class="good03_box" href="/goods/detail/${item.goodsId?c}">
                                        <div>
                                            <#if ("goods"+item_index)?eval??>
                                                <img src="${("goods"+item_index)?eval.getCoverImageUri()}" />
                                            <#else>
                                                <img src="" />
                                            </#if>
                                            <#if item.isPromotion??&&item.isPromotion==true>
                                                <span>促销</span>
                                            </#if>
                                        </div>
                                        <p>${item.goodsTitle!''}</p>
                                        <#if ("goods"+item_index)?eval??>
                                            <label>${("goods"+item_index)?eval.code}</label>
                                        </#if>
                                        <span class="box03_pri">￥<#if item.salePrice??>${item.salePrice?string("0.00")}<#else>0.00</#if></span>
                                    </a>
                                </#list>
                            </li>
                        </ul>
                    </#if>
                </div>
            </section>
            <div class="index_test_box02"></div>
            <#include "/client/common_footer.ftl">
            </div>      
        </div>
    </body>
</html>
