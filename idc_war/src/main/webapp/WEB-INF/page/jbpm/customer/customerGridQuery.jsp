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
    <title>合同列表</title>
    <style>
		.datagrid-row {
			height: 27px;
		}
	</style>
</head>
<body>
	<div style="padding: 5px;" id="requestParamSettins">
		名称:<input class="easyui-textbox"  id="name" style="width:150px;text-align: left;" data-options="">
		客户级别:<input class="easyui-combobox"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								data: [{
									label: 'A类个人客户',
									value: '10'
								},{
									label: 'B类个人客户',
									value: '20'
								},{
									label: 'C类个人客户',
									value: '30'
								},{
									label: 'A类集团',
									value: '40'
								},{
									label: 'A1类集团',
									value: '50'
								},{
									label: 'A2类集团',
									value: '60'
								},{
									label: 'B类集团',
									value: '70'
								},{
									label: 'B1类集团',
									value: '80'
								},{
									label: 'B2类集团',
									value: '90'
								},{
									label: 'C类集团',
									value: '100'
								},{
									label: 'D类集团',
									value: '110'
								},{
									label: 'Z类集团',
									value: '120'
								},{
									label: '其他',
									value: '999'
								}]
								"   id="customerlevel" name="customerlevel"  >
		<a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: true,striped: true,rownumbers:true,pagination:true,fitColumns:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
 	<script src="<%=request.getContextPath() %>/js/jbpm/customer/customerGridQuery.js"></script>
</body>
</html>