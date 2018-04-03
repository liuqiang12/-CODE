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
    <title>机架占用报表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">
    <style>
        .datagrid-ftable > tbody > tr {
            background-color: #b087b9;
        }

        .datagrid-ftable > tbody > tr > td {
            font-weight: 900;
            color: #090404;
        }
    </style>
</head>
<body style="margin:0;padding:2px;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="table" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,singleSelect:true,striped:true,
                   onLoadSuccess:loadsuccess,fitColumns:true,toolbar: '#toolbar',showFooter:true,
                   url:'<%=request.getContextPath() %>/resourceReport/rackResourceReport',rowStyler:showFooterStyle
                  ">
                <thead>
                <th data-options="field:'ROOMID',hidden:'true'"></th>
                <th data-options="field:'SITENAME',width:200">机房名称</th>
                <th data-options="field:'RACKCOUNT',width:100">机架总数</th>
                <th data-options="field:'USEDRACKCOUNT',width:100">机架占用数</th>
                <th data-options="field:'RACKUSAGE',width:100">机架占用率(%)</th>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        数据中心:
        <input id="idcLocationIds" class="easyui-combotree" name="idcLocationIds" style="width: 200px;height:24px;"
               data-options="url:'<%=request.getContextPath() %>/idcBuilding/ajaxLocation',
              checkbox:true,multiple:true,
              onChange:changeLocation,
              loadFilter:function(data){
                   for(var i=0;i<data.length;i++){
                        data[i].text=data[i].name;
                   }
                   return data;
               }"/>
        机楼:
        <input id="idcBuildingIds" class="easyui-combotree" name="idcBuildingIds" style="width: 200px;height:24px;"
               data-options="url:'<%=request.getContextPath() %>/zbMachineroom/ajaxBuilding',
              checkbox:true,multiple:true,
              onChange:changeBuilding,
              loadFilter:function(data){
                   for(var i=0;i<data.length;i++){
                        data[i].text=data[i].name;
                   }
                   return data;
               }"/>
        机房:
        <input id="roomIds" class="easyui-combotree" name="roomIds" style="width: 200px;height:24px;"
               data-options="url:'<%=request.getContextPath() %>/idcRack/ajaxZbMachineroom',
              checkbox:true,multiple:true,
              loadFilter:function(data){
                   for(var i=0;i<data.length;i++){
                        data[i].text=data[i].sitename;
                   }
                   return data;
               }"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_usedrack_report_export','ROLE_admin')">
            <div class="btn-cls-common" onclick="exportData()">导 出</div>
        </sec:authorize>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    //当数据中心变更时 更新机楼内容
    function changeLocation() {
        var idcLocationIds = $("#idcLocationIds").val();
        $("#idcBuildingIds").combotree({
            url: contextPath + "/zbMachineroom/ajaxBuilding",
            queryParams: {idcLocationIds: idcLocationIds}
        })
    }
    //当机楼变更时 更新机房内容
    function changeBuilding() {
        var idcBuildingIds = $("#idcBuildingIds").val();
        $("#roomIds").combotree({
            url: contextPath + "/idcRack/ajaxZbMachineroom",
            queryParams: {idcBuildingIds: idcBuildingIds}
        })
    }
    function loadsuccess(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight');
    }
    function showFooterStyle(index, row) {
        if (row.SITENAME == '总计') {
            return 'background-color:#b087b9;color:#090404;font-weight:bold;';
        }
    }
    //查询
    function searchModels() {
        var idcLocationIds = $("#idcLocationIds").val();
        var idcBuildingIds = $("#idcBuildingIds").val();
        var roomIds = $("#roomIds").val();
        $('#table').datagrid({
            url: contextPath + "/resourceReport/rackResourceReport",
            queryParams: {idcLocationIds: idcLocationIds, idcBuildingIds: idcBuildingIds, roomIds: roomIds}
        });
    }
    //导出
    function exportData() {
        window.open(contextPath + '/resourceReport/exportUsedRackReportData');
    }
</script>
</body>
</html>