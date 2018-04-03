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
		.textbox .textbox-button, .textbox .textbox-button:hover{
			right:0;
		}
	</style>
</head>
<body>
	<div style="padding: 5px;" id="requestParamSettins">
		资源状态:<input class="easyui-combobox"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								data: [{
									label: '全部',
									value: ''
								},{
									label: '在途',
									value: '10'
								},{
									label: '已竣工',
									value: '20'
								},{
									label: '已停机',
									value: '30'
								},{
									label: '已撤销',
									value: '40'
								}]
								"   id="prodStatus" name="prodStatus"  >
		订单类型:<input class="easyui-combobox"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								data: [{
									label: '全部',
									value: ''
								},{
									label: '普通业务',
									value: '1'
								},{
									label: '自有业务',
									value: '0'
								}]
								"   id="prodCategory" name="prodCategory"  >
		所属客户:<input type="hidden" name="customerId" value="" id="customerId"/>
		       <input type="hidden" name="ticket_ctl" value="${ticket_ctl}" id="ticket_ctl"/>
		<input id="customerName" class="easyui-textbox" data-options="editable:false,iconAlign:'left',buttonText:'选择'">
		<a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="singleCreateOrUpdate('gridId')">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="singleUpdateRow('gridId')">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="singleDeleteRow('gridId')">删除</a>
	</div>
	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: false,striped: true,rownumbers:true,pagination:true,fitColumns:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
 	<script src="<%=request.getContextPath() %>/js/jbpm/product/productGridQuery.js"></script>
</body>
</html>