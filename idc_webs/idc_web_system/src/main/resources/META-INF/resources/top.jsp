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
                parent.openurl($(this).attr('url'),$(this).attr('id'));
            })
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

<body style="background:url(<%=request.getContextPath() %>/framework/diytheme/images/topbg.gif) repeat-x;">

<div class="topleft">
    <a href="<%=request.getContextPath() %>/index.do" target="_parent"><img src="<%=request.getContextPath() %>/framework/diytheme/images/logo.png" title="系统首页" /></a>
</div>

<ul class="nav" style="background:url(<%=request.getContextPath() %>/framework/diytheme/images/topbg.gif) repeat-x;">
    <c:if test="${not empty menus}">
        <c:forEach var="menutmp" items="${menus}">
            <c:set var="menu" value="${menutmp.self}" scope="request"/>
            <li><a href="javascript:void(0)" id="${menu.id}" url="${menu.url}" ><img src="<%=request.getContextPath() %>/framework/diytheme/images/menuicon/${menu.icon}" title="${menutmp.name}"  style="width: 32px;height: 32px"/><h2>${menutmp.name}</h2></a></li>
        </c:forEach>
    </c:if>
</ul>

<div class="topright">
    <ul>
        <%--<li><span><img src="<%=request.getContextPath() %>/framework/diytheme/images/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>--%>
        <li><a href="#">欢迎您：
            <sec:authentication property='principal.username' /></a></li>
            <li><a href="javascript:void (0)" onclick="parent.changepwd()">修改密码</a></li>
        <li><a href="<%=request.getContextPath() %>/logout.do" target="_parent">退出</a></li>
    </ul>

    <%--<div class="user">--%>
        <%--<span><sec:authentication property='principal.username' /></span>--%>
        <%--&lt;%&ndash;<i>消息</i>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<b>5</b>&ndash;%&gt;--%>
    <%--</div>--%>

</div>

</body>
</html>