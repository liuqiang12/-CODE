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

    <script src="<%=request.getContextPath() %>/framework/themes/js/echart4/echarts.js"></script>
    <%--<script src="<%=request.getContextPath() %>/framework/themes/js/echart/custom.theme.js"></script>
    <script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.common.min.js"></script>
    <script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.simple.min.js"></script>--%>

    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <title>满意度报表</title>
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

<div id="jbpm_tabs" class="easyui-tabs" style="width:100%;height:100%;" fit="true">
        <div title="列表展示" style="padding:10px">
            <div style="padding: 5px;" id="requestParamSettins_taskQuery">
                开始时间: 	<input class="easyui-datetimebox" value="2017-10-01 00:00:00" id="createTime" style="width:150px" >
                结束时间: 	<input class="easyui-datetimebox" value="2019-1-01 00:00:00" id="endTime" style="width:150px" >
                <a href="javascript:void(0);" onclick="loadTableGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
            <table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: true,striped: true,fitColumns:true"> </table>
        </div>
        <div title="图形展示" style="padding:10px">
            <div id="main" style="width:1200px;height:600px"></div>
        </div>
</div>
</body>
<script src="<%=request.getContextPath() %>/js/jbpm/report/ticketSatisfactionReport.js"></script>
</html>