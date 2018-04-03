<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<%response.setStatus(200);%>
<head>
	<title>404 - 页面不存在</title>
</head>

<body>
<div>
	<div><h1>页面不存在.</h1></div>
	<div><a href="<c:url value="/"/>">返回首页</a></div>
</div>
</body>
</html>