<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/idcrack/optionViwe.js"></script>
    <title>机架列表</title>
    <style>
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="cc" class="easyui-layout" fit="true">
        <div data-options="region:'center'" style="background:#eee;">
            <table id="dg" fit="true" class="easyui-datagrid" data-options="fitColumns:true"></table>
        </div>
    </div>
    <div id="toolbar" class="paramContent">
        <div class="param-fieldset">
            <input type="input" id="buiName" class="param-input" placeholder="名称/编码"/>
        </div>
        <div class="btn-cls-search" onClick="searchModels();"></div>
    </div>
</div>

</body>
</html>