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
    <title>工单统计页面</title>
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
    开始时间: 	<input class="easyui-datetimebox" value="2017-10-01 00:00:00" id="createTime" style="width:150px" >
    结束时间: 	<input class="easyui-datetimebox" value="2019-1-01 00:00:00" id="endTime" style="width:150px" >
    <%--业务类型:<input class="easyui-combobox"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:150,
								data: [{
									label: '全部',
									value: ''
								},{
									label: '政企业务',
									value: '1'
								},{
									label: '自有业务',
									value: '0'
								}]" id="prodCategory"/>

    工单类型:<input class="easyui-combobox"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:150,
								data: [{
									label: '全部',
									value: ''
								},{
									label: '预勘/预占流程',
									value: '100'
								},{
									label: '开通流程',
									value: '200'
								},{
									label: '停机流程',
									value: '400'
								},{
									label: '复通流程',
									value: '500'
								},{
									label: '下线流程',
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
								}]" id="ticketCtegory"/>

    工单状态:<input class="easyui-combobox"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:150,
								data: [{
									label: '全部',
									value: ''
								},{
									label: '流程审批中',
									value: '222'
								},{
									label: '流程审批结束',
									value: '333'
								},{
									label: '流程作废',
									value: '444'
								} ]" id="ticketActiviti"/>--%>

    <a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>

</div>
<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: true,striped: true,fitColumns:true"> </table>
</body>
<script src="<%=request.getContextPath() %>/js/jbpm/report/allTicketCountPage.js"></script>
</html>