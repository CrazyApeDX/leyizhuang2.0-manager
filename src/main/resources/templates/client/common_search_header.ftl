<header class="index_head">
    <span><#if user??>${user.cityName!''}</#if></span>
    <form action="/goods/search" method="post">
        <section class="search_box">                    
            <input placeholder="快速搜索商品" type="text" name="keywords" value="${keywords!''}"/>
            <input type="submit" value=""/>
        </section>
    </form>
    <div class="menu" onClick="win_out();"></div>
</header>