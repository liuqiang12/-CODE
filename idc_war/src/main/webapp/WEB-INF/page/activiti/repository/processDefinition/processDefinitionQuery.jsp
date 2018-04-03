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
    <title>流程模型列表信息</title>
    <style>
		.datagrid-row {
			height: 27px;
		}
	</style>
</head>
<body>
	<div style="padding: 5px;" id="requestParamSettins">
		名称:<input class="easyui-textbox"  id="name" style="width:150px;text-align: left;" data-options="">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="singleStartRow('gridId')">启动</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="singleViewRow('gridId')">查看图片</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="singleDeleteRow()">删除</a>
	</div>
	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: false,striped: true,rownumbers:true,pagination:true,fitColumns:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
	<script src="<%=request.getContextPath() %>/activiti-moduler/js/repository/processDefinition/processDefinitionQuery.js"></script>
</body>
</html>