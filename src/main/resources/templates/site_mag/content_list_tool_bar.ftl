<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/article/edit?cid=${cid!""}&mid=${mid!""}&__VIEWSTATE=${__VIEWSTATE!""}"><i></i><span>新增</span></a></li>
        <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
      </ul>
      <div class="menu-list">
        <div class="rule-single-select single-select">
            <select name="categoryId" onchange="javascript:setTimeout(__doPostBack('categoryId', ''), 0)" id="ddlCategoryId" style="display: none;">
                <option <#if categoryId??><#else>selected="selected"</#if> value="">所有类别</option>
                <#if category_list??>
                    <#list category_list as c>
                        <option value="${c.id!""}" <#if categoryId?? && c.id==categoryId>selected="selected"</#if> ><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                    </#list>
                </#if>
            </select>
        </div>
        <#--
        <#if cid?? && cid==1>
        <div class="rule-single-select single-select">
            <select name="property" onchange="javascript:setTimeout(__doPostBack('property',''), 0)" id="ddlProperty" style="display: none;">
                <option selected="selected" value="">所有属性</option>
                <option value="isMsg">允许评论</option>
                <option value="isTop">置顶</option>
                <option value="isRed">推荐</option>
                <option value="isHot">热门</option>
                <option value="isSlide">幻灯片</option>
            </select>
        </div>
        </#if>
        -->
      </div>
    </div>
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${keywords!''}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('lbtnSearch','')">查询</a>
      <a id="lbtnViewImg" title="图像列表视图" class="img-view" href="javascript:__doPostBack('lbtnViewImg','')"></a>
      <a id="lbtnViewTxt" title="文字列表视图" class="txt-view" href="javascript:__doPostBack('lbtnViewTxt','')"></a>
    </div>
  </div>
</div>
<!--/工具栏-->