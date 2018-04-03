<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/globalstatic/easyui/head.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!--框架必需start-->
    <!-- 基本的样式 -->
    <link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <!-- 蓝色格调的样式 -->
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/import_skin.css" rel="stylesheet"
          type="text/css" id="skin" themeColor="blue"/>
    <!-- 滚动条start -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/zTreeStyle_${skin}.css"/>
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/framework/themes/js/custom-scrollbar/jquery.mCustomScrollbar.css"/>

    <%--<script src="<%=request.getContextPath() %>/framework/themes/js/custom-scrollbar/js/minified/jquery-1.11.0.min.js"></script>--%>
    <script src="<%=request.getContextPath() %>/framework/themes/js/custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="<%=request.getContextPath() %>/framework/themes/js/custom-scrollbar/framework.mCustomScrollbar.js"></script>
    <!-- 判断浏览器的版本 -->
    <script src="<%=request.getContextPath() %>/framework/themes/js/jquery-browser.js"></script>
    <!-- 基本的适配器 -->
    <script src="<%=request.getContextPath() %>/framework/themes/js/mainFrame/baseAdapt.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/themes/js/nav/accordion.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/themes/js/menu.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/ztree/js/jquery.ztree.exhide.min.js"></script>
    <!-- 基本的弹出框  xxxccc-->
    <%--<script src="<%=request.getContextPath() %>/framework/themes/js/layer/layer.js"></script>--%>
    <script>
        layer.config({
            extend: 'blue/style.css', //加载您的扩展样式
            skin: 'layui-ext-skin'
        });
        if(top.window!=window){
            top.location = location
        }
        var wins = [];
        function addWin(obj) {
            if (wins.length > 3) {
                wins.pop();
            }
            wins.unshift(obj);
        }
        function getLastWin() {
            return wins[0];
        }

        //进度条的百分比
        var progressbarTime;
        //调用进度条的函数
        function onclickProcessBar() {
            $("#progressbarPanelID").addClass("addProgressbarMyPanel");   //给进度条背景添加一个面板
            $("#progressbarXID").addClass("addProgressbarX");           //给把进度条移动到页面的中央

            //把progressbarXID设置成一个进度条
            $('#progressbarXID').progressbar({
                value: 2
            });
            progressbarTime = window.setInterval(showalert, 10);  //定时器，定时加载进度条
        }

        //养老进度条的函数
        function closeProgressbar(){
            $('#progressbarXID').progressbar('setValue', 100);   //重新设置进度条的百分比
        }
        //操作进度条
        function showalert(){
            var value = $('#progressbarXID').progressbar('getValue');   //得到进度条的进度百分比
            if(value > 90){
                window.clearTimeout(progressbarTime);  //成功后清除定时服务
                //去掉页面的进度条、进度条面板它们两个div的class
                $("#progressbarPanelID").removeClass("addProgressbarMyPanel");
                $("#progressbarXID").removeClass("addProgressbarX");

                $("#progressbarXID").addClass("clearProgressBar");
                $("#progressbarPanelID").addClass("clearProgressBar");
            }

            if(value < 80){
                $('#progressbarXID').progressbar('setValue', value+1);   //重新设置进度条的百分比
            }
        }


    </script>
</head>
<body>
<!--头部与导航start1-->
<div id="mainHbox">
    <div id="bs_bannercenter">
        <div id="bs_bannerleft">
            <div id="bs_bannerright2">
                <div class="bs_banner_logo"></div>
                <div class="bs_banner_title"></div>
                <!-- 模块菜单 start -->
                <div class="nav_icon_h">
                    <c:forEach items="${menus}" var="menu">
                    	<c:choose>
                    		<c:when test="${empty menu.name}">
                    			<div class="nav_icon_s_item" title="${menu.self.description}">
		                            <a href="javascript:" class="topNode" text="${menu.self.description}" nodeid="${menu.id}" url="${menu.self.url}">
		                                <div class="nav_icon_h_item_img"><img
		                                        src="<%=request.getContextPath() %>${iconPath}${menu.self.icon}"/>
		                                </div>
		                            </a>
		                        </div>
                    		</c:when>
                    		<c:otherwise>
	                    		<div class="nav_icon_h_item">
		                            <a href="javascript:" class="topNode" text="${menu.name}" nodeid="${menu.id}" url="${menu.self.url}">
		                                <div class="nav_icon_h_item_img"><img
		                                        src="<%=request.getContextPath() %>${iconPath}${menu.self.icon}"/>
		                                </div>
		                                <div class="nav_icon_h_item_text">${menu.name}</div>
		                            </a>
		                        </div>

                    		</c:otherwise>

                    	</c:choose>
                    </c:forEach>
                    <div class="nav_icon_s_item" title="帮助">
                        <a href="javascript:" ">
                            <div class="nav_icon_h_item_img"><img
                                    src="<%=request.getContextPath() %>${iconPath}top_menu_help.png"/>
                            </div>
                        </a>
                    </div>
                    <div class="nav_icon_s_item" title="注销">
                        <a href="javascript:" onclick="logout()">
                            <div class="nav_icon_h_item_img"><img
                                    src="<%=request.getContextPath() %>${iconPath}top_menu_logout.png"/>
                            </div>
                        </a>
                    </div>
                    <!-- 系统按钮 end -->
                    <div class="clear"></div>
                </div>
                <!-- 模块菜单 end -->
            </div>
        </div>
    </div>
</div>
<!--  顶部的情况  -->
<!--头部与导航end-->
<!-- 中间区域start -->
<table width="100%" cellpadding="0" cellspacing="0" class="fixcenter">
    <tr>
        <!--左侧区域start-->
        <td id="hideCon" class="ver-top ali-left" style="display:none">
            <div id="lbox">
                <div id="lbox_middlecenter">
                    <div id="lbox_middleleft">
                        <div id="lbox_middleright">
                            <div id="bs_left">
                                <ul id="tree" class="ztree" style="width:200px; overflow:auto;"></ul>
                            </div>

                        </div>
                        <!--更改左侧栏的宽度需要修改id="bs_left"的样式-->
                    </div>
                </div>
            </div>
            </div>
        </td>
        <!--左侧区域end-->

        <!--中间栏区域start-->
        <td id="main_shutiao" class="main_shutiao" style="display:none">
            <div class="bs_leftArr" id="bs_center" title="收缩面板" ></div>
        </td>
        <!--中间栏区域end-->

        <!--右侧区域start-->
        <td class="ver-top ali-left">
            <!-- 右侧的iframe配置 -->
            <!-- 中间区域的外部控制 -->
            <div id="rbox">
                <div id="rbox_topcenter">
                    <div style="position:relative">
                        <div class="rbox_left_navcontent">
                            <!-- 导航 -->
                            <ul class="topNavItems">
                                <li class="topNav-item"><a href="javascript:void(0);" onclick="mainPage()">首页</a></li>
                                <li class="topNav-item"></li>
                            </ul>
                        </div>
                        <div class="rbox_right_loginInfo">
                            <div>
                                您好!<a href="javascript:void(0)" onclick="showUserinfo()"><span style="color: #f6e7a2;font-size: 15px;"><sec:authentication property='principal.nick' /></span></a>
                            </div>
                            <div>
                                当前时间:<span id="time"></span>
                            </div>
                            <div>
                                欢迎使用管理平台！
                            </div>
                            <%--<div>--%>
                                <%--<a href="javascript:void(0)" class="easyui-linkbutton"--%>
                                   <%--data-options="iconCls:'icon-lock',plain:true" onclick="updatePassword()"><span style="color: #f1cb06">修改密码</span></a>--%>
                            <%--</div>--%>
                        </div>
                    </div>
                </div>
                <div id="rbox_middlecenter">
                    <div id="rbox_middleleft">
                        <div id="rbox_middleright">
                            <div id="bs_right">
                                <c:if test="${not empty viewtype}">
                                    <iframe scrolling="no"  width="100%" height="100%" frameBorder=0 id="frmright"  name="frmright" allowTransparency="true"  src="${contextPath}/${viewtype}" ></iframe>
                                </c:if>
                                <c:if test="${ empty viewtype}">
                                    <iframe scrolling="no"  width="100%" height="100%" frameBorder=0 id="frmright"  name="frmright" allowTransparency="true"  src="" ></iframe>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </td>
        <!--右侧区域end-->
    </tr>
</table>
<!-- 中间区域end -->
<script type="text/javascript">
    var nodes = [];
    var iconPath="<%=request.getContextPath() %>${iconPath}";
    <c:forEach items="${menus}" var="menu">
        nodes.push({
            id:'${menu.id}',
            pId:'${menu.parentId}'||'0',
            name:'${menu.name!=null?menu.name:menu.self.description}',
            open:true,
            openurl:'${menu.self.url}',
            icon:iconPath+'/${menu.self.icon}'
        });
            <c:forEach items="${menu.children}" var="child">
                nodes.push({
                    id:'${child.id}',
                    pId:'${menu.id}',
                    name:'${child.name!=null?child.name:child.self.description}',
                    openurl:'${child.self.url}',
                    icon:iconPath+'/${child.self.icon}'
                });


                 <c:forEach items="${child.children}" var="third">
	                 nodes.push({
	                     id:'${third.id}',
	                     pId:'${child.id}',
	                     name:'${third.name!=null?third.name:third.self.description}',
	                     openurl:'${third.self.url}',
	                     icon:iconPath+'/${third.self.icon}'
	                 });

	             </c:forEach>




            </c:forEach>
    </c:forEach>
    function showLeft() {
//        var className = $("#bs_center").attr("class");
//        if (className == "bs_rightArr") {
//            $("#bs_center").click();
//        }
        menuVisible(true);
    }
    function hideLeft() {
//        var className = $("#bs_center").attr("class");
//        if (className != "bs_rightArr") {
//            $("#bs_center").click();
//        }
        menuVisible(false);
    }
    $(function () {
        tipUpdateP();
        hideLeft();
        setTimeout(updatetimeText, 1);
        setInterval(updatetimeText, 1000 * 60);
        function updatetimeText() {
            var date = new Date();
            $("#time").text(date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes());
        }

        $(".topNode").click(function () {
            var topNodeId = $(this).attr("nodeid");
            var titleText = $(this).attr("text");
//            gridPage()
            var url = $(this).attr("url");
            if (url != '#') {
                loadUrl(url);
                <%--$("#frmright").attr("src", "<%=request.getContextPath() %>" + url);--%>
            }
            $(".topNavItems").empty();
            var nav = $('<li class="topNav-item">' + titleText + '</li>');
            $(".topNavItems").append(nav);
            var treeObj = $.fn.zTree.getZTreeObj("tree");
            var nodes = treeObj.getNodes();
            treeObj.hideNodes(nodes);
            var shownodes = [];
            $(nodes).each(function(i,iteam){
                if(iteam.id==topNodeId){
                    if(iteam.children&&iteam.children.length>0){
                        shownodes.push(iteam)
                    }
//                    treeObj.showNode(nodes);
                }
            });
            treeObj.showNodes(shownodes);
            //var childs = $("#li_" + topNodeId + " .child_0_indent32");
            if (shownodes.length > 0) {
//                var className = $("#bs_center").attr("class");
//                if (className == "bs_rightArr") {
//                    $("#bs_center").click();
//                }
//                $("li[id^='li_']").hide();
////                $("#li_*").hide();
//                $("#li_" + topNodeId).show();
                showLeft();
            } else {
                hideLeft();
            }
            $(".nav_icon_h_item a.current").removeClass("current");
			$(this).addClass("current");
        });
        <%--$(".menu-pre").click(function () {--%>
            <%--var url = $(this).attr("url");--%>
            <%--$(".topNavItems").children().eq(1).remove();--%>

            <%--var nav = $('<li class="topNav-item">' + $(this).find("span").text() + '</li>');--%>
            <%--$(".topNavItems").append(nav);--%>
            <%--$("#frmright").attr("src", "<%=request.getContextPath() %>" + url);--%>
        <%--});--%>

    });
    function logout() {
        location.href = "<%=request.getContextPath() %>/logout.do";
    }
    // function updatePassword(){
    //     top.layer.open({
    //         type: 2,
    //         title:'修改密码',
    //         area: ['600px', '400px'],
    //         fixed: false,
    //         maxmin: true,
    //         content: contextPath+'/userinfo/preUpdatePassword',
    //         success: function (layero, index) {
    //             var name = layero.find('iframe')[0].name;
    //             top.winref[name] = window.name;
    //         }
    //     })
    // }
    /*密码将失效   修改密码*/
    function tipUpdateP(){
        var showCheckCode = "${showCheckCode}";
        var mssge = "${SECURITY_LOGIN_EXCEPTION}";
        if(showCheckCode!=null && showCheckCode == '1'){
            layer.confirm(mssge, {
                btn: ['修改','取消'] //按钮
            }, function(){
                //layer.open('的确很重要', {icon: 1});
                top.layer.open({
                    type: 2,
                    area: ['600px', '300px'],
                    fixed: false,
                    maxmin: true,
                    content: contextPath + '/userinfo/preUpdatePassword'
                })
            });
        }
    }
    /*用户信息*/
    function showUserinfo(){
        var  url = contextPath + "/userinfo/getSysUserinfo.do?flag=show";
        top.layer.open({
            type : 2,
            title : '用户信息',
            fix : false,
            closeBtn:1,
            maxmin : true,
            area : [ '60%', '350px' ],
            content: url,
            success: function (layero, index) {
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
            }
        });
    }
</script>

<div id="progressbarPanelID" class="progressbarXPanel"></div>
<div>
    <div id="progressbarXID" class="progressbarX" style="width:700px;"></div>
</div>
</body>

</html>
