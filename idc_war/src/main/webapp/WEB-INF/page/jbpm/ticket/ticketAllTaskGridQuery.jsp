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
    <title>所有工单列表</title>
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
		.jbpm_toolbars_cls{
			position: absolute;
			top: 5px;
			right: 10px;
			z-index: 9999;
			display: none;
		}
		.marginLeft50{
			margin-left: 50px;
		}
	</style>
</head>
<body fit="true">
	<div style="padding: 5px;" id="requestParamSettins_taskQuery">
		<input id="prodCategory" value="${prodCategory}" type="hidden">
		<input id="isCustomerView" value="${isCustomerView}" type="hidden">
		<input id="loginUserId" value="${loginUserId}" type="hidden"/>
		业务名称: <input class="easyui-textbox"  id="busName" style="width:290px;text-align: left;" data-options="">
		工单编号: <input class="easyui-textbox"  id="serialNumber" style="width:120px;text-align: left;" data-options="">
		<!-- 工单类型:自定义增加 -->
		状态:<input class="easyui-combobox"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:110,
								data: [{
									label: '所有',
									value: ''
								},{
									label: '预占流程',
									value: '100'
								},{
									label: '开通流程',
									value: '200'
								},{
									label: '停机流程',
									value: '400'
								},{
									label: '复通流程',
									value: '600'
								},{
									label: '变更开通流程',
									value: '700'
								},{
									label: '临时测试流程',
									value: '800'
								},{
									label: '业务变更流程',
									value: '900'
								}]" id="ticketCategory"/>
		区域:<input class="easyui-combobox"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:165,
								data: [{
									label: '所有区域',
									value: ''
								},{
									label: '中国（西部）云计算中心',
									value: 'DC-280-04'
								},{
									label: '四川移动东区数据中心',
									value: 'DC-280-02'
								},{
									label: '四川移动西区枢纽',
									value: 'DC-280-03'
								}]" id="locationCode"/>
		<a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'mydefined_reset'">刷新</a>
		<!-- 所有的单子的审批情况 -->
		<%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'apply_ffcc00',plain:true" id="apply_buttom_id" onclick="author_apply_item('gridId')">审批</a>--%>
		<%--<sec:authorize access="hasAnyRole('ROLE_idc_customer_manager_handler','ROLE_admin')">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-bgkt',plain:true,disabled:true" id="ktlc_buttom_id"  onclick="singleStartRow('gridId','200')">开通</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ywbg',plain:true,disabled:true" id="zq_ywbg_buttom_id" onclick="singleStartRow('gridId','900')">业务变更</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-bgkt',plain:true,disabled:true" id="zq_bgkt_buttom_id"  onclick="singleStartRow('gridId','700')">变更开通</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tj',plain:true,disabled:true" id="zq_tj_buttom_id"  onclick="singleStartRow('gridId','400')">停机</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('gridId','600')">业务下线</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ft',plain:true,disabled:true" id="zq_ft_buttom_id"  onclick="singleStartRow('gridId','500')">复通</a>
		</sec:authorize>--%>

		<%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'apply_ffcc00',plain:true" id="apply_buttom_id" onclick="author_apply_item('gridId')">审批</a>--%>
		<sec:authorize access="hasAnyRole('ROLE_idc_customer_manager_handler','ROLE_admin')">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-bgkt',plain:true,disabled:true" id="ktlc_buttom_id"  onclick="singleStartRow('gridId','200')">开通</a>
		</sec:authorize>
	</div>
	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: true,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},fitColumns:true"></table>
</body>
<jsp:include page="/globalstatic/easyui/publicButton.jsp"></jsp:include>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketAllTaskGridQuery.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketTaskCommonQuery.js"></script>
</html>