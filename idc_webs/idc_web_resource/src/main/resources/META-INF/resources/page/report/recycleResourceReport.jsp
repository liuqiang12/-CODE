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
    <title>资源分配报表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
</head>
<body style="margin:0;padding:2px;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="table" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,pagination:true,
                   pageSize:20,pageList:[10,15,20,50],striped:true,
                   fitColumns:false,toolbar:'#toolbar'
                  ">
                <thead>
                <tr>
                    <th data-options="field:'TICKETID',hidden:'true'"></th>
                    <th data-options="field:'BUSNAME',width:100">业务名称</th>
                    <th data-options="field:'BUSITYPE',width:150">需求名称</th>
                    <th data-options="field:'TICKETCATEGORY',width:100">业务类型</th>
                    <th data-options="field:'ROOMSTR',width:200,formatter:roomStrInfo">占用机房</th>
                    <th data-options="field:'RACKSTR',width:200,formatter:rackStrInfo">占用机架</th>
                    <th data-options="field:'RACKNUM',width:100">占用机架数</th>
                    <th data-options="field:'IPSTR',width:200,formatter:ipStrInfo">占用IP</th>
                    <th data-options="field:'IPNUM',width:100">占用IP数</th>
                    <th data-options="field:'BANDWIDTH',width:100">分配带宽</th>
                    <th data-options="field:'CYSTIME',width:150">回收时间</th>
                    <th data-options="field:'CONNECTSTR',width:300">联系方式</th>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_recycleresource_report_export','ROLE_admin')">
            <div class="btn-cls-common" onclick="exportData()">导 出</div>
        </sec:authorize>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    $(function () {
        $('#table').datagrid({
            url: contextPath + "/resourceReport/recycleResourceReport"
        });
    });
    //roomStr
    function roomStrInfo(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" title="' + value + '">' + value + '</a>';
        } else {
            return "";
        }
    }
    //rackStr
    function rackStrInfo(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" title="' + value + '">' + value + '</a>';
        } else {
            return "";
        }
    }
    //ipStr
    function ipStrInfo(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" title="' + value + '">' + value + '</a>';
        } else {
            return "";
        }
    }
    //查询
    function searchModels() {
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/resourceReport/recycleResourceReport",
            queryParams: {name: name}
        });
    }
    //导出
    function exportData() {
        window.open(contextPath + '/resourceReport/exportRecycleResourceReportData');
    }
</script>
</body>
</html>