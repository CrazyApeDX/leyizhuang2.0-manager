<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑信息</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/dialog/jquery.artDialog.js?skin=idialog"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();
    
    
});

function cancel() {
	var id = $("#__id").val();
	artDialog({
		id : 'Confirm',
		icon : 'error',
		fixed : true,
		lock : true,
		opacity : 0.1,
		content : "确认作废调拨单？",
		ok : function(here) {
			$.post("/Verwalter/allocation/cancel", {
				id : id
			}, function(result) {
				alert(result.msg);
				if(result.code==0) {
  					location.href='/Verwalter/allocation/list';
				} else if(result.code==1) {
  					location.href='/Verwalter/login';
				} else if(result.code==2) {
  					location.href='/Verwalter/allocation/edit?id=' + id;
				} else {
				
				}
			})
		},
		cancel : function(here) {

		}
	});
}

function send() {
	var id = $("#__id").val();
	var realNums = new Array();
	var validateFlag = true;
	var re = /^[0-9]+.?[0-9]*$/;
	$("#var_box_detail").find("tr").each(function(i,n){
		var realNum = $(n).find(".td-input").val();
		realNums.push(realNum);
		if(realNum=='' || realNum==0 || !re.test(realNum)) {
			validateFlag = false;
		}
	});
	
	if(validateFlag){
		artDialog({
			id : 'Confirm',
			icon : 'warning',
			fixed : true,
			lock : true,
			opacity : 0.1,
			content : "确认出库？",
			ok : function(here) {
				$.post("/Verwalter/allocation/send", {
					id : id,
					"realNums" : JSON.stringify(realNums)
				}, function(result) {
					alert(result.msg);
					if(result.code==0) {
	  					location.href='/Verwalter/allocation/list';
					} else if(result.code==1) {
	  					location.href='/Verwalter/login';
					} else if(result.code==2) {
	  					location.href='/Verwalter/allocation/edit?id=' + id;
					} else {
					
					}
				})
			},
			cancel : function(here) {
	
			}
		});
	} else {
		alert('亲，出库数量必须为数字且不能为0');
	}
}

function receive() {
	var id = $("#__id").val();
	artDialog({
		id : 'Confirm',
		icon : 'warning',
		fixed : true,
		lock : true,
		opacity : 0.1,
		content : "确认入库？",
		ok : function(here) {
			$.post("/Verwalter/allocation/receive", {
				id : id
			}, function(result) {
				alert(result.msg);
				if(result.code==0) {
  					location.href='/Verwalter/allocation/list';
				} else if(result.code==1) {
  					location.href='/Verwalter/login';
				} else if(result.code==2) {
  					location.href='/Verwalter/allocation/edit?id=' + id;
				} else {
				
				}
			})
		},
		cancel : function(here) {

		}
	});
}

</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/allocation/save" id="form1" onsubmit="return checkDetail();">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
<input type="hidden" id="__id" value="${allocation.id}" />
</div>
<input id="cityId" name="cityId" type="text" value='${cityId!""}' style="display:none">
<input name="cityName" type="text" value='${cityName!""}' style="display:none">
<!--导航栏-->
<div class="location">
    <a href="/Verwalter/center" class="home">
    <i></i><span>首页</span></a>
    <i class="arrow"></i>
    <span>调拨单详情</span>
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
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
      	<dl>
            <dt>单号</dt>
            <dd>${allocation.number}</dd>
        </dl>
      	<dl>
            <dt>调出门店</dt>
            <dd>${allocation.allocationFromName!''}</dd>
        </dl>
      	<dl>
            <dt>调入门店</dt>
            <dd>${allocation.allocationToName!''}</dd>
        </dl>
      	<dl>
            <dt>状态</dt>
            <dd>${allocation.statusDisplay}</dd>
        </dl>
        <dl>
            <dt>备注</dt>
            <dd>${allocation.comment!''}</dd>
        </dl>
        
        <dl>
            <dt>调拨明细</dt>
            
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <thead>
                        <tr>
                            <th width="10%">
                               产品编码
                            </th>
                            <th width="20%">
                               产品SKU
                            </th>
                            <th width="50%">
                                产品名称
                            </th>
                            <th width="10%">
                               申请数量
                            </th>
         					<#if allocation.status=1 && allocationFromFlag?? || allocation.status!=1>
                            	<th width="10%">
                               出库数量
                            	</th>
                            </#if>
                        </tr>
                    </thead>
                    <tbody id="var_box_detail">
                        <#if allocation?? && allocation.details??>
                            <#list allocation.details as detail>
                                <tr class="td_c">
                                    <td>${detail.goodId?c}</td>
                                    <td>${detail.goodSku!''}</td>
                                    <td>${detail.goodTitle!''}</td>
                                    <td>${detail.num!''}</td>
                                    <#if allocation.status=1 && allocationFromFlag??>
	                                    <td><input type="text" class="td-input" value="${detail.realNum!''}" style="width:90%;" /></td>
	                                </#if>
	                                <#if allocation.status!=1>
                                    	<td>${detail.realNum!''}</td>
                                    </#if>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </dd>
        </dl>
        
        <dl>
            <dt>轨迹</dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <thead>
                        <tr>
                            <th width="40%">
                               事件
                            </th>
                            <th width="20%">
                                操作人
                            </th>
                            <th width="40%">
                                操作时间
                            </th>
                        </tr>
                    </thead>
                    <tbody id="var_box_trail">
                    	<#if allocation?? && allocation.trails??>
                            <#list allocation.trails as trail>
                                <tr class="td_c">
                                    <td>${trail.operation!''}</td>
                                    <td>${trail.operatedBy!''}</td>
                                    <td>${trail.operatedTime!''}</td>
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
        	<#if allocation.status=1 && allocationToFlag??>
            	<input type="button" value="作废调拨单" class="btn" onclick="javascript:cancel();">
            </#if>
            <#if allocation.status=1 && allocationFromFlag??>
            	<input type="button" value="出库" class="btn" onclick="javascript:send();">
            </#if>
            <#if allocation.status=2 && allocationToFlag??>
            	<input type="button" value="入库" class="btn" onclick="javascript:receive();">
            </#if>
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
</body>
</html>