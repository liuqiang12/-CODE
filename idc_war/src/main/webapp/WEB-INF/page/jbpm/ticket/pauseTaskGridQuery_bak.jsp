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
    <title>停机页面</title>
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
	 <!-- 待办/归档 -->
	 <div id="jbpm_tabs" class="easyui-tabs" fit="true">
		 <div title="待办任务" style="padding:10px;display:none;">
			 <div style="padding: 5px;" id="requestParamSettins_taskQuery">
				 订单名称: <input class="easyui-textbox"  id="prodName" style="width:100px;text-align: left;" data-options="">
				 <input id="processDefinitonKey" value="${processDefinitonKey}" type="hidden"/>
				 客户名称: <input class="easyui-textbox"  id="customerName" style="width:100px;text-align: left;" data-options="">
				 单位属性:<input class="easyui-combobox"  style="width:100px;text-align: left;"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								data: [{
									label: '军队',
									value: '1'
								},{
									label: '政府机关',
									value: '2'
								},{
									label: '事业单位',
									value: '3'
								},{
									label: '企业',
									value: '4'
								},{
									label: '个人',
									value: '5'
								},{
									label: '社会团体',
									value: '6'
								},{
									label: '其他',
									value: '999'
								}]"  id="attribute"/>
				 工单编号: <input class="easyui-textbox"  id="serialNumber" style="width:100px;text-align: left;" data-options="">

				 <a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			 </div>
			 <table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: false,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},fitColumns:true"></table>
		 </div>
		 <div title="已办任务" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
			 <div style="padding: 5px;" id="requestParamSettins_taskhisQuery">
				 工单编号: <input class="easyui-textbox"  id="his_serialNumber" style="width:100px;text-align: left;" data-options="">
				 客户名称: <input class="easyui-textbox"  id="his_customerName" style="width:100px;text-align: left;" data-options="">
				 <a href="javascript:void(0);" onclick="loadHisGrid('his_gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			 </div>
			 <table class="easyui-datagrid" id="his_gridId" data-options="singleSelect:true,nowrap: false,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},fitColumns:true"></table>
		 </div>
	 </div>
</body>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketTaskGridQuery.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketHisTaskGridQuery.js"></script>

</html>