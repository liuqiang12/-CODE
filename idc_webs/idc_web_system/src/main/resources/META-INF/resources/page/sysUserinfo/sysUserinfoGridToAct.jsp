<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>用户角色信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
    <div style="padding: 5px;" id="requestParamSettins">
        用户名称:<input class="easyui-textbox"  id="username" style="width:150px;text-align: left;" data-options="">
        <a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </div>
    <table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: false,striped: true,rownumbers:true,pagination:true,fitColumns:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
    <!-- 引用界面的控制类 -->
    <script src="<%=request.getContextPath() %>/js/user/sysUserGridToAct.js"></script>
</body>
</html>