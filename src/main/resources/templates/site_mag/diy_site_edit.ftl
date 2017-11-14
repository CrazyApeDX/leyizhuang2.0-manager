<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑物流配送</title>

<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script src="/client/js/jquery.cityselect.js"></script>


<link href="/mag/style/style.css" rel="stylesheet" type="text/css"> 
<script type="text/javascript">
    $(function () {
        //初始化表单验证
        $("#form1").initValidform();
        
         //初始化上传控件
    $(".upload-img").each(function () {
        $(this).InitSWFUpload({ 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf"
        });
    });
    
    //（缩略图）
    var txtPic = $("#txtImgUrl").val();
    if (txtPic == "" || txtPic == null) {
        $(".thumb_ImgUrl_show").hide();
    }
    else {
        $(".thumb_ImgUrl_show").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
        $(".thumb_ImgUrl_show").show();
    }
    
    $(".upload-show360").each(function () {
        $(this).InitSWFUpload_show360({ 
            btntext: "批量上传", 
            btnwidth: 66, 
            single: false, 
            water: true, 
            thumbnail: true, 
            filesize: "5120", 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf", 
            filetypes: "*.jpg;*.jpge;*.png;*.gif;" 
        });
    });
    
     $("#address").citySelect({
        nodata:"none",
        <#if diy_site?? && diy_site.cityId??>prov: "${diy_site.cityId?c}",</#if>
        <#if diy_site?? && diy_site.disctrictId??>city: "${diy_site.disctrictId?c}",</#if>
        <#if diy_site?? && diy_site.subDisctrictId??>dist: "${diy_site.subDisctrictId?c}",</#if>
        required:false
    });
 });
   
</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/order/setting/diysite/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="diySiteId" type="text" value='<#if diy_site??>${diy_site.id?c!''}</#if>' style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/order/setting/diysite/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/order/setting/diysite/list"><span>门店</span></a>
  <i class="arrow"></i>
  <span>门店编辑</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dt>门店名称</dt>
    <dd>
        <input name="title" type="text" value="<#if diy_site??>${diy_site.title!''}</#if>" class="input normal" datatype="*2-100" sucmsg=" "> 
        <span class="Validform_checktip">*门店名称</span>
    </dd>
  </dl>
      <dl>
        <dt>所属价目表</dt>
        <dd>
            <div class="rule-single-select">
                    <#if !diy_site??>
                        <select name="priceListId">
                        <option value="">请选择类别...</option>
                        <#if price_list??>
                            <#list price_list as c>
                                <option <#if diy_site??&&diy_site.priceListId??&&diy_site.priceListId==c.id>selected="selected"</#if> value="${c.id?c!''}" >${c.name!'无    '}</option>
                            </#list>
                        </#if>
                    </#if>
                    <#if diy_site??>
                        <select>
                        <option selected="selected">${diy_site.priceListName!'暂无'}</option>
                        <#if choose_price_list??>
                        <#list choose_price_list as item>
                        <option selected="selected">${item.description!'暂无'}</option>
                        </#list>
                        </#if>
                    </#if>
                </select>
            </div>
        </dd>
    </dl>
  <dl>
    <dt>是否启用</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span id="rblStatus" style="display: none;">
            <input type="radio" name="isEnable" value="1" <#if diy_site?? && diy_site.isEnable?? && diy_site.isEnable>checked="checked"</#if>>
            <label>是</label>
            <input type="radio" name="isEnable" value="0" <#if !diy_site?? || !diy_site.isEnable?? || !diy_site.isEnable>checked="checked"</#if>>
            <label>否</label>
      </div>
      <span class="Validform_checktip">*不启用则不显示</span>
    </dd>
  </dl>
  <dl>
  	 <dt>是否允许送货上门</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span id="rblStatus" style="display: none;">
            <input type="radio" name="isHomeDelivery" value="1" <#if diy_site?? && diy_site.isHomeDelivery?? && diy_site.isHomeDelivery>checked="checked"</#if>>
            <label>是</label>
            <input type="radio" name="isHomeDelivery" value="0" <#if !diy_site?? || !diy_site.isHomeDelivery?? || !diy_site.isHomeDelivery>checked="checked"</#if>>
            <label>否</label>
      </div>
      <span class="Validform_checktip">*不启用则不显示</span>
    </dd>
  </dl>
<#-->  <dl>
    <dt>是否旗舰店</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span  style="display: none;">
            <input type="radio" name="isFlagShip" value="1" <#if diy_site?? && diy_site.isFlagShip?? && diy_site.isFlagShip>checked="checked"</#if>>
            <label>是</label>
            <input type="radio" name="isFlagShip" value="0" <#if !diy_site?? || !diy_site.isFlagShip?? || !diy_site.isFlagShip>checked="checked"</#if>>
            <label>否</label>
      </div>
      <span class="Validform_checktip">*如果是旗舰店则地图显示不同</span>
    </dd>
  </dl> 
  <dl>
    <dt>服务电话</dt>
    <dd>
        <input name="mobile" type="text" value="<#if diy_site??>${diy_site.serviceTele!""}</#if>" class="input normal" > 
        <span class="Validform_checktip">*用于会员联系门店</span>
    </dd>
  </dl>
  -->
  <#--
  <dl>
    <dt>客服QQ</dt>
    <dd>
        <input name="qq" type="text" value="<#if diy_site??>${diy_site.qq!""}</#if>" class="input normal" > 
        <span class="Validform_checktip"></span>
    </dd>
  </dl>
  -->
  <dl>
       <dt>地区</dt>
       <dd>
             <div id="address">
             <select id="prov" name="cityId" class="prov" style="width: 100px;"></select>
             <select id="city" name="disctrictId" class="city" style="width: 100px;"></select>
             <select id="dist" name="subDisctrictId" class="dist" style="width: 100px;"></select>
             </div>
       </dd>
  </dl>
  <dl>
       <dt>门店类型</dt>
       <dd>
             <div id="address">
                 <select id="status" name="status" class="dist" style="width: 100px;">
                    <option value="0" <#if diy_site??&&diy_site.status??&&diy_site.status==0>selected="selected"</#if>>直营</option>
                    <option value="1" <#if diy_site??&&diy_site.status??&&diy_site.status==1>selected="selected"</#if>>加盟门店</option>
                    <option value="2" <#if diy_site??&&diy_site.status??&&diy_site.status==2>selected="selected"</#if>>虚拟门店</option>
                    <option value="3" <#if diy_site??&&diy_site.status??&&diy_site.status==3>selected="selected"</#if>>第三方</option>
                 </select>
             </div>
       </dd>
  </dl>
  <dl>
       <dt>门店类别</dt>
       <dd>
         <div id="address">
             <select id="info" name="info" class="dist" style="width: 100px;">
                <option value="ZY" <#if diy_site??&&diy_site.info??&&diy_site.info=='ZY'>selected="selected"</#if>>直营门店</option>
                <option value="JM" <#if diy_site??&&diy_site.info??&&diy_site.info=='JM'>selected="selected"</#if>>加盟门店</option>
                <option value="FX" <#if diy_site??&&diy_site.info??&&diy_site.info=='JX'>selected="selected"</#if>>经销门店</option>
             </select>
         </div>
       </dd>
  </dl>
  <dl>
    <dt>门店位置</dt>
    <dd>
      <input name="address" type="text" value="<#if diy_site??>${diy_site.address!""}</#if>" class="input normal" datatype="*" errormsg="" sucmsg=" ">
      <span class="Validform_checktip">该信息可以帮助用户选择最合适的自提点</span>
    </dd>
  </dl>
  <#--
  <dl>
    <dt>经度</dt>
    <dd>
      <input name="longitude" type="text" value="<#if diy_site?? && diy_site.longitude??>${diy_site.longitude?string("#.######")}</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,6})?$/" errormsg="" sucmsg=" ">
      <a href="http://api.map.baidu.com/lbsapi/getpoint/" target="_blank">坐标拾取(点击获取坐标)</a>
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <dl>
    <dt>纬度</dt>
    <dd>
      <input name="latitude" type="text" value="<#if diy_site?? && diy_site.latitude??>${diy_site.latitude?string("#.######")}</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,6})?$/" errormsg="" sucmsg=" ">
      <a href="http://api.map.baidu.com/lbsapi/getpoint/" target="_blank">坐标拾取(点击获取坐标)</a>
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <dl>
    <dt>店面图片</dt>
    <dd>
        <input id="txtImgUrl" name="imageUri" type="text" datatype="*" value="<#if diy_site?? && diy_site.imageUri??>${diy_site.imageUri!""}</#if>" class="input normal upload-path">
        <div class="upload-box upload-img"></div>
        <div class="photo-list thumb_ImgUrl_show">
            <ul>
                <li>
                    <div class="img-box1"></div>
                </li>
            </ul>
        </div>
        <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <dl id="div_show360_container">
        <dt>展示图片</dt>
        <dd>
            <div class="upload-box upload-show360"></div>
            <div class="photo-list_show360">
                <ul>
                    <#if diy_site?? && diy_site.showPictures??>
                        <#list diy_site.showPictures?split(",") as uri>
                            <#if uri != "">
                            <li>
                                <input type="hidden" name="hid_photo_name_show360" value="0|${uri!""}|${uri!""}">
                                <div class="img-box">
                                    <img src="${uri!""}" bigsrc="${uri!""}">
                                </div>
                                <a href="javascript:;" onclick="delImg(this);">删除</a>
                            </li>
                            </#if>
                        </#list>
                    </#if>
                </ul>
            </div>
        </dd>
  </dl>
  -->
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if diy_site??>${diy_site.sortId!"99"}</#if>" class="input small" datatype="/^(([1-9]\d{0,1})|0)((\.\d{2})|(\.\d{1}))?$/" sucmsg=" " errormsg="请输入不超过100的2位小数">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  <#--
  <dl>
    <dt>营业时间</dt>
    <dd>
      <input name="openTimeSpan" type="text" value="<#if diy_site??>${diy_site.openTimeSpan!""}</#if>" class="input normal" datatype="*" errormsg="" sucmsg=" ">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  -->
  <dl>
    <dt>客服电话</dt>
    <dd>
      <input name="serviceTele" type="text" value="<#if diy_site??>${diy_site.serviceTele!""}</#if>" class="input normal" datatype="*" errormsg="" sucmsg=" ">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <#--
   <dl>
    <dt>投诉电话</dt>
    <dd>
      <input name="complainTele" type="text" value="<#if diy_site??>${diy_site.complainTele!""}</#if>" class="input normal" datatype="*" errormsg="" sucmsg=" ">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <dl>
    <dt>描述说明</dt>
    <dd>
      <textarea name="info" rows="2" cols="20" class="input normal"><#if diy_site??>${diy_site.info!""}</#if></textarea>
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
    -->
</div>
<!--/内容-->


<!--工具栏-->
<div class="page-footer">
  <div class="btn-list">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
  <div class="clear"></div>
</div>
<!--/工具栏-->
</form>


</body></html>