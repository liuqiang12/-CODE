<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <title>接口信息列表</title>
    <style>
		.datagrid-row {
			height: 27px;
		}
		.textbox .textbox-button, .textbox .textbox-button:hover{
			right:0;
		}
		.datagrid-td-rownumber{
			width: 19px;
		}
	</style>
</head>
<body fit="true">
<div style="padding: 5px;" id="requestParamSettins_query">
	PROV_ID: <input class="easyui-textbox"  id="provId" style="width:100px;text-align: left;" data-options="">
	<a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
</div>
<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap:false,striped:true,rownumbers:true,fit:true,fitColumns:true"></table>
</body>
<script src="<%=request.getContextPath() %>/js/interface-core/basicInfoGrid.js"></script>

</html>