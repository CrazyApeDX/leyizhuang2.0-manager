<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑信息</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/zh_CN.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();

    //初始化编辑器
    var editor = KindEditor.create('.editor', {
        width: '98%',
        height: '350px',
        resizeType: 1,
        uploadJson: '/Verwalter/editor/upload?action=EditorFile',
        fileManagerJson: '/Verwalter/editor/upload?action=EditorFile',
        allowFileManager: true
    });
    
    // 添加赠品
    $("#addGift").click(function(){
        showDialogGift();
    });
 	//根据城市选择门店
    $("#cityId").change(function(){
        getDiySiteList(this);
    });
    
    //创建促销赠品窗口
    function showDialogGift(obj) {
        var objNum = arguments.length;
        
        var giftDialog = $.dialog({
            id: 'giftDialogId',
            lock: true,
            max: false,
            min: false,
            title: "赠品",
            content: 'url:/Verwalter/activity/gift/dialog?total=' + $("#var_box_gift").children("tr").length,
            width: 800,
            height: 350
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            giftDialog.data = obj;
        }
    }
    
    //删除促销赠品节点
    function delGiftNode(obj) {
        $(obj).parent().parent().remove();
    }
});

//创建促销赠品窗口
function show_goods_gift_dialog(obj) {
    var objNum = arguments.length;
    var zengpinDialog = $.dialog({
        id: 'zengpinhDialogId',
        lock: true,
        max: false,
        min: false,
        title: "促销赠品",
        content: 'url:/Verwalter/activity/gift/dialog',
        width: 800,
        height: 550
    });
    //如果是修改状态，将对象传进去
    if (objNum == 1) {
        zengpinDialog.data = obj;
    }
}
    
//删除促销商品节点
function del_goods_gift(obj) {
    $(obj).parent().parent().remove();
    $("#totalGift").val(parseInt($("#totalGift").val())-1);
}

function getDiySiteList(object)
{
    $.ajax({
            url : '/Verwalter/activity/diysite/list/show?regionId='+$(object).val(),
            type : 'post',
            success : function(res) 
            {
                $("#id-param-sec").html(res);
            },
            error: function(res)
            {
                alert("error code : -1 + " + res);
            }
    });
}

</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/activity/gift/save" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input name="menuId" type="text" value='${mid!""}' style="display:none;">
<input name="channelId" type="text" value='${cid!""}' style="display:none">
<input name="id" type="text" value='<#if activity_gift??>${activity_gift.id?c}</#if>' style="display:none">
<!--导航栏-->
<div class="location">
    <a href="/Verwalter/activity/gift/list" class="back"><i></i><span>
        返回列表页</span></a> 
    <a href="/Verwalter/center" class="home">
    <i></i><span>首页</span></a>
    <i class="arrow"></i>
    <span>编辑信息</span>
</div>
<div class="line10">
</div>
<!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap" >
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">基本信息</a></li>
                    <#--<li><a href="javascript:;" onclick="tabs(this);" class="">扩展选项</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">详细描述</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">价格与库存</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">促销</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">赠品</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">组合商品</a></li> 
                    <li><a href="javascript:;" onclick="tabs(this);" class="">SEO选项</a></li>-->
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
        <dl>
            <dt>名称</dt>
            <dd>
                <input name="name" type="text" value="<#if activity_gift??>${activity_gift.name!""}</#if>" class="input normal" datatype="*2-100" sucmsg=" ">
                <span class="Validform_checktip">*标题最多100个字符</span>
            </dd>
        </dl>
        <dl>
            <dt>所属类别</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="categoryId" id="categoryId" datatype="*" sucmsg=" ">
                        <#if !activity_gift??>
                        <option value="">请选择类别...</option>
                        </#if>
                        <#if category_list??>
                            <#list category_list as c>
                                <option value="${c.id!""}" <#if activity_gift?? && activity_gift.categoryId==c.id>selected="selected"</#if>><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </dd>
        </dl>
         <dl>
            <dt>赠送类型</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="giftType" datatype="*" sucmsg=" ">
                        <#if !activity_gift??>
                        <option value="">请选择类别...</option>
                        </#if>
                        <option value="0" <#if activity_gift?? && activity_gift.giftType?? && activity_gift.giftType == 0>selected="selected"</#if> >赠送商品</option>
                        <option value="1" <#if activity_gift?? && activity_gift.giftType?? && activity_gift.giftType == 1>selected="selected"</#if> >赠送产品卷</option>
                    </select>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>可用</dt>
            <dd>
                <div class="rule-multi-radio multi-radio">
                    <span>
                        <input type="radio" name="isUseable" value="1" <#if activity_gift??==false || activity_gift.isUseable==true>checked="checked"</#if>>
                        <label>可用</label>
                        <input type="radio" name="isUseable" value="0" <#if activity_gift?? && activity_gift.isUseable?? && activity_gift.isUseable==false>checked="checked"</#if>>
                        <label>不可用</label>
                    </span>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>城市</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="cityId" id="cityId"  datatype="*" sucmsg=" ">
                        <#if !activity_gift??>
                        <option value="">请选择城市...</option>
                        </#if>
                        <#if city_list??> 
                            <#list city_list as c>
                                <option value="${c.id?c}" <#if activity_gift?? && activity_gift.cityId==c.id>selected="selected"</#if>>${c.cityName!''}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>活动开始时间</dt>
            <dd>
                <div class="input-date">
                    <input name="beginTime" type="text" value="<#if activity_gift??>${activity_gift.beginTime!""}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>结束到期时间</dt>
            <dd>
                <div class="input-date">
                    <input name="endTime" type="text" value="<#if activity_gift??>${activity_gift.endTime!""}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>最少购买数量</dt>
            <dd>
                <input name="buyNumber" type="text" value="<#if activity_gift??>${activity_gift.buyNumber!""}<#else>1</#if>" id="txtSortId" class="input txt100" datatype="n" sucmsg=" ">
                <span class="Validform_checktip">*满足这个购买数量才有可能赠送</span>
            </dd>
        </dl>
        <dl>
            <dt>排序数字</dt>
            <dd>
                <input name="sortId" type="text" value="<#if activity_gift??>${activity_gift.sortId!""}<#else>99</#if>" id="txtSortId" class="input txt100" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
                <span class="Validform_checktip">*数字，越小越向前</span>
            </dd>
        </dl>
        <div id="id-param-sec">
            <#if diysite_list??>
                <#include "/site_mag/activity_diysite_list_detail.ftl" />
            </#if>
        </div>
        <dl>
            <dt>赠品</dt>
            <dd>
                <a id="addGift" class="icon-btn add"><i></i><span>添加赠品</span></a>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt></dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <thead>
                        <tr>
                            <th width="6%">
                                排序
                            </th>
                            <th width="10%">
                                商品ID
                            </th>
                            <th width="38%">
                                赠品标题
                            </th>
                            <th width="10%">
                                数量
                            </th>
                            <th width="10%">
                                原价
                            </th>
                            <th width="6%">
                                操作
                            </th>
                        </tr>
                    </thead>
                    <tbody id="var_box_gift">
                        <input type="hidden" id="totalGift" name="totalGift" value="<#if activity_gift??>${activity_gift.totalGift!'0'}<#else>0</#if>" />
                        <#if activity_gift?? && activity_gift.giftList??>
                            <#list activity_gift.giftList as gift>
                                <tr class="td_c">
                                    <td>
                                        <input name="giftList[${gift_index}].id" type="hidden" value="${gift.id?c}">
                                        <input name="giftList[${gift_index}].coverImageUri" type="hidden" value="${gift.coverImageUri!''}">
                                        <input type="text" name="giftList[${gift_index}].sortId" class="td-input" value="${gift.sortId!''}" style="width:90%;">
                                    </td>
                                    <td><input type="text" id="id" name="giftList[${gift_index}].goodsId" class="td-input" value="${gift.goodsId?c}" style="width:90%;"></td>
                                    <td>
                                        <input type="text" id="title" name="giftList[${gift_index}].goodsTitle" class="td-input" value="${gift.goodsTitle!''}" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="number" name="giftList[${gift_index}].number" class="td-input" value="${gift.number!'0'}" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="price" name="giftList[${gift_index}].goodsPrice" class="td-input" value="${gift.goodsPrice?string("0.00")}" style="width:90%;">
                                    </td>
                                    <td>
                                        <i class="icon"></i>
                                        <a title="编辑" class="img-btn edit operator" onclick="show_goods_gift_dialog(this);">编辑</a>
                                        <a title="删除" class="img-btn del operator" onclick="del_goods_gift(this);">删除</a>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </dd>
        </dl>
    </div>
    
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
            <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
</body>
</html>