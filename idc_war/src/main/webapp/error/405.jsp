<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>405 - 资源被禁止</title>
</head>

<body>
<div><h1>资源被禁止.</h1></div>
<div><a href="<c:url value="/"/>">返回首页</a></div>
</body>
</html>
