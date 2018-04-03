<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/globalstatic/easyui/head.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!--框架必需start-->
    <!-- 基本的样式 -->
    <link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <!-- 蓝色格调的样式 -->
    <link href="<%=request.getContextPath() %>/framework/themes/skins/blue/import_skin.css" rel="stylesheet" type="text/css" id="skin" themeColor="blue"/>
    <!-- 滚动条start -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/framework/themes/js/custom-scrollbar/jquery.mCustomScrollbar.css"/>

    <script src="<%=request.getContextPath() %>/framework/themes/js/custom-scrollbar/js/minified/jquery-1.11.0.min.js"></script>
    <script src="<%=request.getContextPath() %>/framework/themes/js/custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="<%=request.getContextPath() %>/framework/themes/js/custom-scrollbar/framework.mCustomScrollbar.js"></script>
    <!-- 判断浏览器的版本 -->
    <script src="<%=request.getContextPath() %>/framework/themes/js/jquery-browser.js"></script>
    <!-- 基本的适配器 -->
    <script src="<%=request.getContextPath() %>/framework/themes/js/mainFrame/baseAdapt.js"></script>
    <!-- 基本的弹出框 -->
    <%--<script src="<%=request.getContextPath() %>/framework/themes/js/layer/layer.js"></script>--%>
    <script>
        layer.config({
            extend: 'blue/style.css', //加载您的扩展样式
            skin: 'layui-ext-skin'
        });
    </script>
</head>
<body>
<!--头部与导航start-->
<div id="mainHbox">
    <div id="bs_bannercenter">
        <div id="bs_bannerleft">
            <div id="bs_bannerright2">
                <div class="bs_banner_logo"></div>
                <div class="bs_banner_title"></div>
                <!-- 模块菜单 start -->
                <div class="nav_icon_h">
                    <div class="nav_icon_h_item">
                        <a href="javascript:;" onclick="alert('加载不同的子菜单');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/nav/topMenuImage001.png"/></div>
                            <div class="nav_icon_h_item_text">用户管理2</div>
                        </a>
                    </div>
                    <div class="nav_icon_h_item">
                        <a href="javascript:;" onclick="alert('加载不同的子菜单');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/nav/topMenuImage002.png"/></div>
                            <div class="nav_icon_h_item_text">监控管理</div>
                        </a>
                    </div>
                    <div class="nav_icon_h_item">
                        <a href="javascript:;" onclick="alert('加载不同的子菜单');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/nav/topMenuImage003.png"/></div>
                            <div class="nav_icon_h_item_text">监控管理</div>
                        </a>
                    </div>
                    <div class="nav_icon_h_item">
                        <a href="javascript:;" onclick="alert('加载不同的子菜单');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/nav/topMenuImage004.png"/></div>
                            <div class="nav_icon_h_item_text">监控管理</div>
                        </a>
                    </div>
                    <div class="nav_icon_h_item">
                        <a href="javascript:;" onclick="alert('加载不同的子菜单');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/nav/topMenuImage005.png"/></div>
                            <div class="nav_icon_h_item_text">监控管理</div>
                        </a>
                    </div>
                    <div class="nav_icon_h_item">
                        <a href="javascript:;" onclick="alert('加载不同的子菜单');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/nav/topMenuImage006.png"/></div>
                            <div class="nav_icon_h_item_text">监控管理</div>
                        </a>
                    </div>
                    <div class="nav_icon_h_item">
                        <a href="javascript:;" onclick="alert('加载不同的子菜单');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/nav/topMenuImage006.png"/></div>
                            <div class="nav_icon_h_item_text">监控管理</div>
                        </a>
                    </div>

                    <!-- 系统按钮 start -->
                    <div class="nav_icon_s_item" style="margin-left:25px">
                        <a href="javascript:;" onclick="alert('自行处理');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/sysOperate/userinfo.png"/></div>
                        </a>
                    </div>
                    <div class="nav_icon_s_item">
                        <a href="javascript:;" onclick="alert('自行处理');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/sysOperate/lock.png"/></div>
                        </a>
                    </div>
                    <div class="nav_icon_s_item">
                        <a href="javascript:;" onclick="alert('自行处理');">
                            <div class="nav_icon_h_item_img"><img src="icons/blue/sysOperate/close.png"/></div>
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
<table width="100%" cellpadding="0" cellspacing="0">
    <tr>
        <!--左侧区域start-->
        <td id="hideCon" class="ver-top ali-left">
            <div id="lbox">
                <div id="lbox_middlecenter">
                    <div id="lbox_middleleft">
                        <div id="lbox_middleright">
                            <div id="bs_left">
                                <!-- Chrome浏览器的自适应问题  -->
                                <iframe scrolling="no" onload="javascript:setAutoHeightWithChrome(this)" width="100%"
                                        height="100%" frameBorder="0" id="frmleft" name="frmleft"
                                        src="templete/menu.html" allowTransparency="true"
                                        style="x-overflow:hidden"></iframe>
                            </div>
                            <!--更改左侧栏的宽度需要修改id="bs_left"的样式-->
                        </div>
                    </div>
                </div>
            </div>
        </td>
        <!--左侧区域end-->

        <!--中间栏区域start-->
        <td class="main_shutiao">
            <div class="bs_leftArr" id="bs_center" title="收缩面板"></div>
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
                                <li class="topNav-item">首页</li>
                                <li class="topNav-item">xxxx</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div id="rbox_middlecenter">
                    <div id="rbox_middleleft">
                        <div id="rbox_middleright">
                            <div id="bs_right">
                                <iframe scrolling="no" class="minHeight_655" width="100%" height="100%" frameBorder=0
                                        id="frmright" name="frmright"
                                        src="plugins/jquery-easyui-1.5.2/demo/datagrid/frozencolumns.html"
                                        allowTransparency="true"></iframe>
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
</body>
</html>
