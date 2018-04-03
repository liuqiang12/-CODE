<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${not empty menus}">
    <c:forEach var="menutmp" items="${menus}">
        <c:set var="menu" value="${menutmp.self}" scope="request"/>
        <li level=${menutmp.level}
                    <c:if test="${not empty menutmp.children&&fn:length(menutmp.children)>0}">class="children"</c:if>>
            <a href="javascript:void(0)" url="${menu.url}" <c:if test="${menutmp.parentId<0}"> </c:if>>
                <span class="${menu.icon}" style="display: inline-block;width: 16px;height: 16px;line-height: 16px;">
                    &nbsp;
                </span>
                <span>${menutmp.name}</span>
                <%--<span style="float: left"><img src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/IconsExtension/group.png" style="width: 16px;height: 16px;"/></span>${menutmp.name}--%>
            </a>
        <c:choose>
            <c:when test="${not empty menutmp.children&&fn:length(menutmp.children)>0}">
                    <ul>
                        <c:set var="menus" value="${menutmp.children}" scope="request"/>
                        <jsp:include page="menu.jsp"/>
                    </ul>
            </c:when>
        </c:choose>
        </li>
    </c:forEach>
</c:if>