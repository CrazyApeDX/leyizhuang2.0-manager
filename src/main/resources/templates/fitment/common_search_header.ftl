<header class="index_head">
    <span style="width:35%;"><#if employee??>${employee.companyTitle!''}</#if></span>
    <form <#--action="/fitment/search" method="get"-->>
        <section style="width:60%;" class="search_box">                    
            <input style="width:70%;" placeholder="快速搜索商品" type="text" name="keywords" value="${keywords!''}"/>
            <input type="submit" value=""/>
        </section>
    </form>
    <#--
    <div class="menu" onClick="win_out();"></div>
    -->
</header>