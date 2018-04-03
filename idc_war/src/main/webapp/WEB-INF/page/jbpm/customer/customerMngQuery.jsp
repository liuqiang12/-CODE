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
		客户级别:<input class="easyui-combobox"  style="width:100px;text-align: left;" data-options="
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
								"   id="customerlevel" >
		跟踪状态:<input class="easyui-combobox" style="width:100px;text-align: left;" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								data: [{
									label: '跟踪状态',
									value: '10'
								},{
									label: '有意向',
									value: '20'
								},{
									label: '继续跟踪',
									value: '30'
								},{
									label: '无意向',
									value: '40'
								}]
								"   id="tracestate"   >
		服务等级:<input class="easyui-combobox"  style="width:100px;text-align: left;" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								data: [{
									label: '金牌',
									value: '10'
								},{
									label: '银牌',
									value: '20'
								},{
									label: '铜牌',
									value: '30'
								},{
									label: '铁牌',
									value: '40'
								},{
									label: '标准',
									value: '999'
								}]"  id="sla"/>
		客户类别:<input class="easyui-combobox" style="width:100px;text-align: left;"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								data: [{
									label: '局方',
									value: '10'
								},{
									label: '第三方',
									value: '20'
								},{
									label: '测试',
									value: '30'
								},{
									label: '退场客户',
									value: '40'
								},{
									label: '国有',
									value: '50'
								},{
									label: '集体',
									value: '60'
								},{
									label: '私营',
									value: '70'
								},{
									label: '股份',
									value: '80'
								},{
									label: '外商投资',
									value: '90'
								},{
									label: '港澳台投资',
									value: '100'
								},{
									label: '客户',
									value: '110'
								},{
									label: '自由合作',
									value: '120'
								},{
									label: '内容引入',
									value: '130'
								},{
									label: '其他',
									value: '999'
								}]"  id="category"/>
		<a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="singleCreateOrUpdate('gridId')">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="singleUpdateRow('gridId')">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="singleDeleteRow('gridId')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="ispUploadCustomer()">上报ISP</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="ispDeleteCustomer()">删除ISP</a>
	</div>
	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: false,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
 	<script src="<%=request.getContextPath() %>/js/jbpm/customer/customerMngQuery.js"></script>
</body>
</html>