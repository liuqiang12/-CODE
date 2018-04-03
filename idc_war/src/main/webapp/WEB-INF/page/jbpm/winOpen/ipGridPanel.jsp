<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title></title>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div style="padding: 5px;" id="requestParamSettins_gridId">
        名称: <input class="easyui-textbox"  id="ipName_params" style="width:100px;text-align: left;" data-options="">
        <a href="javascript:void(0);" onclick="loadServerGrid('ip_gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </div>
    <table class="easyui-datagrid" id="ip_gridId"></table>
</div>
</body>
<script src="<%=request.getContextPath() %>/js/jbpm/win/openIpResourceQuery.js"></script>
</html>