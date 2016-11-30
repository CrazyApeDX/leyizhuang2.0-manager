<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>后台管理中心</title>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/jquery.nicescroll.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript">
    //页面加载完成时
    $(function () {
        //检测IE
        if ('undefined' == typeof(document.body.style.maxHeight)){
            // 升级IE浏览器
            window.location.href = 'ie6update.html';
        }
        
        $("#sidebar-nav").niceScroll({ touchbehavior: false, cursorcolor: "#7C7C7C", cursoropacitymax: 0.6, cursorwidth: 5 });       
    });

    //页面尺寸改变时
    $(window).resize(function () {
        navresize();
    });

    //导航菜单显示和隐藏
    function navresize() {
        var docWidth = $(document).width();
        if (docWidth < 1004) {
            $(".nav li span").hide();
        } else {
            $(".nav li span").show();
        }
    }

    //点击收缩展开左边栏代码
    $(document).ready(function () {
        $("#switchPoint").click(function () {
            var status = $("#switchPoint").attr("status");
            if (status == "1") {
                $("#switchPoint").attr("style", "left: 2px;background:url(mag/style/left.gif) no-repeat center center;cursor:pointer;");
                $("#switchPoint").attr("title", "展开左边导航");
                $("#main_left").hide();
                $("#switchPoint").attr("status", "0");
        
                $("#main_right").attr("style", "left: 0px;");
        
            }
            else {
                $("#switchPoint").attr("style", "left: 184px;background:url(mag/style/right.gif) no-repeat center center;cursor:pointer;");
                $("#switchPoint").attr("title", "收起左边导航");
                $("#main_left").show();
                $("#switchPoint").attr("status", "1");
        
                $("#main_right").attr("style", "left: 182px;");
            }
        });

        // 点击顶部导航按钮
        $("#nav li").click(function () {
            $("#switchPoint").attr("style", "left: 184px;background:url(mag/style/right.gif) no-repeat center center;cursor:pointer;");
            $("#switchPoint").attr("title", "收起左边导航");
            $("#main_left").show();
            $("#switchPoint").attr("status", "1");

            $("#main_right").attr("style", "left: 182px;");  
            
            // 顶部按钮效果切换
            $(this).addClass("selected").siblings().removeClass("selected");
            $(".list-group").hide();
            $(".list-group").eq($(this).index()).show();
        });
        
         
        // 左侧导航栏子菜单展开与收拢
        $(".list-group li").click(function(){
            var childUl = $(this).children("ul");
            var siblingsChildUl = $(this).siblings().children("ul");
            var childA = $(this).children("a");
            var siblingsChildA = $(this).siblings().children("a");
            // 文件夹
            if (childUl.length > 0 && childUl.children("li").length > 0)
            {
                // 文件夹关闭状态
                if (childA.children(".expandable").hasClass("close"))
                {
                    childA.children(".expandable").removeClass("close").addClass("open");
                    childUl.show();
                    siblingsChildUl.hide();
                    siblingsChildA.children(".expandable.open").removeClass("open").addClass("close");
                }
                else // 打开状态
                {
                    //childA.children(".expandable").removeClass("open").addClass("close");
                    //childUl.hide();
                    if(childA.children(".expandable").hasClass("toclose")){
                    	childA.children(".expandable").removeClass("open").addClass("close");
                    	childUl.hide();
                    }
                }
            }
        });
        $(".list-group li .expandable").click(function(){
            var childA = $(this).parent("a");
                // 文件夹关闭状态
                if (childA.children(".expandable").hasClass("close"))
                {
                    childA.children(".expandable").removeClass("toclose").addClass("toopen");
                }
                else // 打开状态
                {
                    childA.children(".expandable").removeClass("toopen").addClass("toclose");
                }
        });
   	
    });

  var ordernumberconfirmed = 0; //待确认订单
  var ordernumberbuy = 0;       //立即购买订单
  var ordernumberpay = 0;       //付款订单
  var consultsnumber = 0;       //咨询
  var commentsnumber = 0;       //评论
  var returnsnumber = 0;        //退换货
  var complainsnumber = 0;      //投诉
  $(document).ready(function () {
        //setInterval("remind()",30000);
  });
  //下单自动提醒
    function remind(){    
         $.ajax({
                type: "post",
                url: "/Verwalter/automaticRemind",
                data: {},
                dataType: "json",
                success: function (data) {   
                     if (data.code == 0) {                       
                        if(data.ordernumberconfirmed>ordernumberconfirmed && ordernumberconfirmed !=0){
                            alert("有新待确认订单");
                        }
                        ordernumberconfirmed = data.ordernumberconfirmed;
                        
                        if(data.ordernumberbuy>ordernumberbuy && ordernumberbuy !=0){
                            alert("有新待支付订单");
                        }
                        ordernumberbuy = data.ordernumberbuy;
                        
                        if(data.ordernumberpay>ordernumberpay && ordernumberpay !=0){
                            alert("有新已支付订单！");
                        }
                        ordernumberpay = data.ordernumberpay;
                        
                        if(data.consults>consultsnumber && consultsnumber !=0){
                            alert("有新咨询！");
                        }
                        consultsnumber = data.consults;
                        
                        if(data.comments>commentsnumber && commentsnumber !=0){
                            alert("有新的评论！");
                        }
                        commentsnumber = data.comments;
                        
                        if(data.complains>complainsnumber && complainsnumber !=0){
                            alert("有新的投诉！");
                        }
                        complainsnumber = data.complains;
                        
                        if(data.Returns>returnsnumber && returnsnumber !=0){
                            alert("有新退换货申请！");
                        }
                        returnsnumber = data.Returns;
                    } else {
                       // alert(data.msg);
                    }
                }
            }); 
    }
</script>
</head>

<body class="indexbody">
    <div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;">
        <table class="ui_border">
            <tbody>
                <tr>
                    <td class="ui_lt"></td>
                    <td class="ui_t"></td>
                    <td class="ui_rt"></td>
                </tr>
                <tr>
                    <td class="ui_l"></td>
                    <td class="ui_c">
                        <div class="ui_inner">
                            <table class="ui_dialog">
                                <tbody>
                                    <tr>
                                        <td colspan="2"><div class="ui_title_bar">
                                            <div class="ui_title" unselectable="on" style="cursor: move;"></div>
                                            <div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
    <form name="form1" method="post" action="" id="form1">
        <div>
            <input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
            <input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
            <input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
        </div>
    
        <script type="text/javascript">
            var theForm = document.forms['form1'];
            if (!theForm) {
                theForm = document.form1;
            }
            function __doPostBack(eventTarget, eventArgument) {
                if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
                    theForm.__EVENTTARGET.value = eventTarget;
                    theForm.__EVENTARGUMENT.value = eventArgument;
                    theForm.submit();
                }
            }
        </script>
    
    
        <div>
        	<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION" value="">
        </div>
        <!--全局菜单-->
    
        <!--/全局菜单-->
        
        <div class="header">
          <div class="header-box">
            <h1 class="logo"></h1>
            <ul id="nav" class="nav">
                <#if root_menu_list??>
                    <#list root_menu_list as menu>
                        <#if menu_index == 0>
                            <li class="selected"><i class="icon-${menu.iconId}"></i><span style="display: inline;">${menu.title}</span></li>
                        <#else>
                            <li class=""><i class="icon-${menu.iconId}"></i><span style="display: inline;">${menu.title}</span></li>
                        </#if>
                    </#list>
                </#if>
            </ul>
            <div class="nav-right">
              <div class="icon-info">
                <span>
                  您好，${manager!''}<br>
                 ${manager_title!"" }
                </span>
              </div>
              <div class="icon-option">
              	<i></i>
                <div class="drop-box">
                  <div class="arrow"></div>
                  <ul class="drop-item">
                    <li><a target="_blank" href="/">预览网站</a></li>
                    <li><a href="/Verwalter/manager/password" target="mainframe">修改密码</a></li>
                    <#--
                    <li><a onclick="linkMenuTree(false, &#39;&#39;);" href="http://localhost:2100/site_mag/manager/manager_pwd.aspx" target="mainframe">修改密码</a></li>
                    -->
                    <li><a id="lbtnExit" href="/Verwalter/logout">注销登录</a></li>            
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="main-sidebar" id="main_left">
            <div id="sidebar-nav" class="sidebar-nav" tabindex="5000" style="overflow: hidden; outline: none;">
                <div class="list-box">
                    <#if root_menu_list??>
                        <#list root_menu_list as root_menu>
                            <div class="list-group" name="${root_menu.title}" <#if root_menu_index==0>style="display: block;"<#else>style="display: none;"</#if> >
                                <h2>${root_menu.title}<i></i></h2>
                                <ul style="display: block;">
                                    <#if ("level_"+root_menu_index+"_menu_list")?eval?? >
                                        <#list ("level_"+root_menu_index+"_menu_list")?eval as lOneMenu>
                                            <li>
                                                <a navid="${lOneMenu.name}" class="item pack">
                                                    <div class="arrow"></div>
                                                    <div class="expandable <#if ("level_"+root_menu_index+lOneMenu_index+"_menu_list")?eval??><#if lOneMenu_index==0>open<#else>close</#if></#if>"></div>
                                                    <div class="folder <#if ("level_"+root_menu_index+lOneMenu_index+"_menu_list")?eval??>close<#else>open</#if>"></div>
                                                    <span>${lOneMenu.title}</span>
                                                </a>
                                                <ul <#if lOneMenu_index==0>style="display: block;"<#else>style="display: none;"</#if>>
                                                    <#if ("level_"+root_menu_index+lOneMenu_index+"_menu_list")?eval??>
                                                        <#list ("level_"+root_menu_index+lOneMenu_index+"_menu_list")?eval as lSecondMenu>
                                                            <li>
                                                                <a navid="${lSecondMenu.name}" href="/Verwalter${lSecondMenu.linkUrl}?cid=${lSecondMenu.channelId}&mid=${lSecondMenu.parentId}" target="mainframe" class="item">
                                                                <div class="arrow"></div>
                                                                <div class="expandable"></div>
                                                                <div class="folder open"></div>
                                                                <span>${lSecondMenu.title}</span>
                                                                </a>
                                                            </li>
                                                        </#list>
                                                    </#if>
                                                </ul>
                                            </li>
                                        </#list>
                                    </#if>
                                </ul>
                            </div>
                        </#list>
                    </#if>
                </div>
            </div>
        </div>
    
        <div class="picBox" id="switchPoint" status="1" title="收起左边导航" style="left: 184px;background:url(mag/style/right.gif) no-repeat center center;cursor:pointer;"></div>
    
        <div class="main-container" id="main_right" style="left: 182px;">
          <iframe id="mainframe" name="mainframe" frameborder="0" src="/Verwalter/center"></iframe>
        </div>
    </form>

    <div id="ascrail2000" class="nicescroll-rails" style="width: 7px; z-index: auto; cursor: default; position: absolute; top: 49px; left: 176px; height: 393px; display: block; opacity: 0;">
        <div style="position: relative; top: 0px; float: right; width: 5px; height: 209px; border: 1px solid rgb(255, 255, 255); border-radius: 8px; background-color: rgb(124, 124, 124); background-clip: padding-box;"></div>
    </div>

    <div id="ascrail2000-hr" class="nicescroll-rails" style="height: 7px; z-index: auto; top: 435px; left: 0px; position: absolute; cursor: default; display: none; opacity: 0; width: 176px;">
        <div style="position: relative; top: 0px; height: 5px; width: 183px; border: 1px solid rgb(255, 255, 255); border-radius: 8px; left: 0px; background-color: rgb(124, 124, 124); background-clip: padding-box;"></div>
    </div>
</body>
</html>