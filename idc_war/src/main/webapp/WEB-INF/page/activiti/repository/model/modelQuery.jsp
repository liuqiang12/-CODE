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
		<a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="singleCreateRow()">创建模型</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-design',plain:true" onclick="singleDesignRow('gridId')">设计流程</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="singleQueryRow('gridId')">模型详情</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-settting',plain:true" onclick="singleDeployRow('gridId')">发布</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="singleStartRow()">启动</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="singleExportRow('gridId')">导出XML</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="singleDeleteRow()">删除</a>
	</div>
	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: false,striped: true,rownumbers:true,pagination:true,fitColumns:true,pageSize:50,pageList:[50,60,70,100],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
	<a href="javascript:void(0)" id="gridId_menu" class="easyui-menubutton" data-options="menu:'#gridId_menu_data',iconCls:'icon-edit'">更多</a>
	<div id="gridId_menu_data" style="width:150px;">
		<div data-options="iconCls:'icon-undo'">修改</div>
		<div data-options="iconCls:'icon-redo'">发布</div>
		<div data-options="iconCls:'icon-remove'">删除</div>
	</div>
	<iframe  id="frameId"></iframe>
 	<script src="<%=request.getContextPath() %>/activiti-moduler/js/repository/modeler/modelQuery.js"></script>
</body>
</html>