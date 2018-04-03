<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <%--<link rel="stylesheet" type="text/css"--%>
    <%--href="<%=request.getContextPath() %>/framework/easyui/themes/default/easyui.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/easyui/themes/icon.css">--%>
    <%--&lt;%&ndash;<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/layout/ui7/css/common.css">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/layout/ui7/css/menu.css">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/layout/ui7/css/style.css">&ndash;%&gt;--%>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery-2.0.3.min.js"></script>
    <%--<script type="text/javascript"--%>
    <%--src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.easyui.min.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css"--%>
    <%--href="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/skin/layer.css">--%>
    <%--<script type="text/javascript"--%>
    <%--src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css"--%>
    <%--href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/IconExtension.css">--%>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/diytheme/css/style.css">

    <title>云南移动能耗管理平台</title>
    <script type="text/javascript" language="JavaScript">
        $(document).ready(function () {
            $(".nav li a").click(function () {
                $(".nav li a.selected").removeClass("selected")
                $(this).addClass("selected");
            })
            $(".menuson li").click(function(){
                $(".menuson li.active").removeClass("active")
                $(this).addClass("active");
            });

            $('.title').click(function(){
                var $ul = $(this).next('ul');
                $('dd').find('ul').slideUp();
                if($ul.is(':visible')){
                    $(this).next('ul').slideUp();
                }else{
                    $(this).next('ul').slideDown();
                }
            });
            <%--$(".nav li a").click(function () {--%>
            <%--//                $(".nav li a").removeClass("on");--%>
            <%--//                $(this).addClass("on");--%>
            <%--var uri = $(this).attr("url");--%>
            <%--if (uri == '#') {--%>
            <%--return;--%>
            <%--}--%>
            <%--if (uri.indexOf("?") > 0) {--%>
            <%--uri += "&_" + new Date();--%>
            <%--} else {--%>
            <%--uri += "?_=" + new Date();--%>
            <%--}--%>
            <%--if (uri.indexOf("/") == 0) {--%>
            <%--// uri = "<%=request.getContextPath() %>"+uri;--%>
            <%--uri = "<%=request.getContextPath() %>/redirect.do?fromindex=fromindex&url=" + encodeURI(uri);--%>

            <%--} else {--%>
            <%--uri = "http://" + uri;--%>
            <%--$("#maincontent").attr("src", uri);--%>
            <%--}--%>
            <%--$("#maincontent").attr("src", uri);--%>

            <%--})--%>
        });
        //        function topfun() {
        //            if (window.top != window.self) {
        //                window.top.location.href = window.self.location.href;
        //            }
        //        }
    </script>
    <style>

    </style>
</head>

<body>
<div style="overflow:hidden;height:auto;width: 100%;;background:url('<%=request.getContextPath() %>/framework/diytheme/images/topbg.gif') repeat-x;">
    <div class="topleft">
        <a href="#" target="_parent"><img src="<%=request.getContextPath() %>/framework/diytheme/images/logo.png"
                                          title="系统首页"/></a>
    </div>

    <ul class="nav">
        <li><a href="default.html" target="rightFrame" class="selected"><img
                src="<%=request.getContextPath() %>/framework/diytheme/images/icon01.png" title="工作台"/>

            <h2>工作台</h2></a></li>
        <li><a href="imgtable.html" target="rightFrame"><img
                src="<%=request.getContextPath() %>/framework/diytheme/images/icon02.png" title="模型管理"/>

            <h2>模型管理</h2></a></li>
        <li><a href="imglist.html" target="rightFrame"><img
                src="<%=request.getContextPath() %>/framework/diytheme/images/icon03.png" title="模块设计"/>

            <h2>模块设计</h2></a></li>
        <li><a href="tools.html" target="rightFrame"><img
                src="<%=request.getContextPath() %>/framework/diytheme/images/icon04.png" title="常用工具"/>

            <h2>常用工具</h2></a></li>
        <li><a href="computer.html" target="rightFrame"><img
                src="<%=request.getContextPath() %>/framework/diytheme/images/icon05.png" title="文件管理"/>

            <h2>文件管理</h2></a></li>
        <li><a href="tab.html" target="rightFrame"><img
                src="<%=request.getContextPath() %>/framework/diytheme/images/icon06.png" title="系统设置"/>

            <h2>系统设置</h2></a></li>
    </ul>

    <div class="topright">
        <ul>
            <li><span><img src="<%=request.getContextPath() %>/framework/diytheme/images/help.png" title="帮助"
                           class="helpimg"/></span><a href="#">帮助</a></li>
            <li><a href="#">关于</a></li>
            <li><a href="login.html" target="_parent">退出</a></li>
        </ul>

        <div class="user">
            <span>admin</span>
            <i>消息</i>
            <b>5</b>
        </div>
    </div>
</div>
<div style="overflow: hidden;height: auto;width: 100%">
    <div style="float: left; background:#f0f9fd;width:187px;height:100%;">
        <div class="lefttop">
            <span></span>通讯录
        </div>
        <dl class="leftmenu">
            <dd>
                <div class="title">
                    <span><img src="<%=request.getContextPath() %>/framework/diytheme/images/leftico01.png"/></span>管理信息
                </div>
                <ul class="menuson">
                    <li><cite></cite><a href="http://localhost:8081/alarm/index.do" target="rightFrame">首页模版</a><i></i></li>
                    <li class="active"><cite></cite><a href="right.html" target="rightFrame">数据列表</a><i></i></li>
                    <li><cite></cite><a href="imgtable.html" target="rightFrame">图片数据表</a><i></i></li>
                    <li><cite></cite><a href="form.html" target="rightFrame">添加编辑</a><i></i></li>
                    <li><cite></cite><a href="imglist.html" target="rightFrame">图片列表</a><i></i></li>
                    <li><cite></cite><a href="imglist1.html" target="rightFrame">自定义</a><i></i></li>
                    <li><cite></cite><a href="tools.html" target="rightFrame">常用工具</a><i></i></li>
                    <li><cite></cite><a href="filelist.html" target="rightFrame">信息管理</a><i></i></li>
                    <li><cite></cite><a href="tab.html" target="rightFrame">Tab页</a><i></i></li>
                    <li><cite></cite><a href="error.html" target="rightFrame">404页面</a><i></i></li>
                </ul>
            </dd>
            <dd>
                <div class="title">
                    <span><img src="<%=request.getContextPath() %>/framework/diytheme/images/leftico02.png"/></span>其他设置
                </div>
                <ul class="menuson">
                    <li><cite></cite><a href="#">编辑内容</a><i></i></li>
                    <li><cite></cite><a href="#">发布信息</a><i></i></li>
                    <li><cite></cite><a href="#">档案列表显示</a><i></i></li>
                </ul>
            </dd>


            <dd>
                <div class="title"><span><img
                        src="<%=request.getContextPath() %>/framework/diytheme/images/leftico03.png"/></span>编辑器
                </div>
                <ul class="menuson">
                    <li><cite></cite><a href="#">自定义</a><i></i></li>
                    <li><cite></cite><a href="#">常用资料</a><i></i></li>
                    <li><cite></cite><a href="#">信息列表</a><i></i></li>
                    <li><cite></cite><a href="#">其他</a><i></i></li>
                </ul>
            </dd>


            <dd>
                <div class="title"><span><img
                        src="<%=request.getContextPath() %>/framework/diytheme/images/leftico04.png"/></span>日期管理
                </div>
                <ul class="menuson">
                    <li><cite></cite><a href="#">自定义</a><i></i></li>
                    <li><cite></cite><a href="#">常用资料</a><i></i></li>
                    <li><cite></cite><a href="#">信息列表</a><i></i></li>
                    <li><cite></cite><a href="#">其他</a><i></i></li>
                </ul>

            </dd>

        </dl>
    </div>
    <div>3</div>
</div>
</body>
</html>