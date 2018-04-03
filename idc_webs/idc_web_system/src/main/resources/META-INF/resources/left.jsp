<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery-2.0.3.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/diytheme/css/style.css">
    <script type="text/javascript">

        /***
         *
         */
        function clickli(id,url) {

            var $obj = $("#div_" + id);
            if ($obj.length > 0) {
                $(".topdiv").hide();
            }
            $obj.show();
            var $first = $obj.find("ul li:first")
            if(url=='#')
            $first.click();
//            var url = $first.attr('url');
        }
        function getUrl(obj){
            var url = obj.find('a').attr('url');
            if(url!='#'){
                return url;
            }else{
                return getUrl(obj.next())
            }
        }
        $(function () {
            //导航切换
            $(".menuson li").click(function () {
                $(".menuson li.active").removeClass("active")
                $(this).addClass("active");
                parent.opensuburl( getUrl($(this)));
            });
            $("#div_7").show();
            $('.title').click(function () {
                var $ul = $(this).next('ul');
                $('dd').find('ul').slideUp();
                if ($ul.is(':visible')) {
                    $(this).next('ul').slideUp();
                } else {
                    $(this).next('ul').slideDown();
                }
            });
        })
    </script>
</head>
<body style="background:#f0f9fd;">
<c:if test="${not empty menus}">
    <c:forEach var="menutmp" items="${menus}">

        <div class="topdiv" style="display: none" id="div_${menutmp.id}">
            <dl class="leftmenu">
                <dd>
                    <div class="title">
                        <%--<span><img--%>
                                <%--src="<%=request.getContextPath() %>/framework/diytheme/images/leftico01.png"/></span>${menutmp.name}--%>
                    </div>
                    <ul class="menuson">
                        <c:if test="${not empty menutmp.children}">
                            <c:forEach var="menutmp1" items="${menutmp.children}">
                                <c:set var="menu1" value="${menutmp1.self}" scope="request"/>
                                <li><img src="<%=request.getContextPath() %>/framework/diytheme/images/menuicon/<c:if test="${empty menutmp.self.icon}">report.png</c:if><c:if test="${not empty menutmp.self.icon}">${menutmp.self.icon}</c:if>"   style="width: 16px;height: 16px;float: left;margin-left:32px;margin-top:7px;"/><a href="javascript:void(0)"
                                                    url="${menutmp1.self.url}">${menutmp1.name}</a><i></i></li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${ empty menutmp.children}">
                            <c:set var="menu1" value="${menutmp.self}" scope="request"/>
                            <li>
                                <img src="<%=request.getContextPath() %>/framework/diytheme/images/menuicon/<c:if test="${empty menutmp.self.icon}">report.png</c:if><c:if test="${not empty menutmp.self.icon}">${menutmp.self.icon}</c:if>"   style="width: 16px;height: 16px;float: left;margin-left:32px;margin-top:7px;"/>
                                <a href="javascript:void(0)"  url="${menutmp.self.url}">${menutmp.name}</a><i></i></li>
                        </c:if>
                    </ul>
                </dd>
            </dl>
        </div>

        </div>
    </c:forEach>
</c:if>
</body>
</html>
