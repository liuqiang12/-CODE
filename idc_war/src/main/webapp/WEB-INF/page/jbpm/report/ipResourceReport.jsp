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
    <title>IP地址使用统计报表</title>
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
    业务名称: <input class="easyui-textbox"  id="busName" style="width:200px;text-align: left;" data-options="">
    <a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
</div>
<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: true,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},fitColumns:true"> </table>
</body>
<script src="<%=request.getContextPath() %>/js/jbpm/report/ipResourceReport.js"></script>
<script type="text/javascript">


</script>
</html>