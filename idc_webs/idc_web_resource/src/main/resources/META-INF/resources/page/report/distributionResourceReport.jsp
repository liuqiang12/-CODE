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
                    <th data-options="field:'XQNAME',width:150">需求名称</th>
                    <th data-options="field:'TICKETCATEGORY',width:100">业务类型</th>
                    <th data-options="field:'CUSTOMERNAME',width:200">客户名称</th>
                    <th data-options="field:'CONTACTPHONE',width:100">联系电话</th>
                    <th data-options="field:'ROOMSTR',width:200,formatter:roomStrInfo">占用机房</th>
                    <th data-options="field:'RACKNAMESTR',width:200,formatter:rackStrInfo">占用机架</th>
                    <th data-options="field:'RACKNUM',width:100">占用机架数</th>
                    <th data-options="field:'IPSTR',width:200,formatter:ipStrInfo">占用IP</th>
                    <th data-options="field:'IPNUM',width:100">占用IP数</th>
                    <th data-options="field:'BANDWIDTH',width:100">分配带宽</th>
                    <th data-options="field:'PROCTICKETSTATUS',width:100">业务状态</th>
                    <th data-options="field:'CREATETIME',width:150">开通时间</th>
                    <th data-options="field:'DISTRITIME',width:150">分配时间</th>
                    <%--<th data-options="field:'REMARK',width:100">备注</th>--%>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="名称"/>&nbsp;&nbsp;
        分配时间起:<input class="easyui-datetimebox" data-options="width:200" id="beginTime"/>&nbsp;&nbsp;
        分配时间止:<input class="easyui-datetimebox" data-options="width:200" id="endTime"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_usedresource_report_export','ROLE_admin')">
            <div class="btn-cls-common" onclick="exportData()">导 出</div>
        </sec:authorize>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    $(function () {
        $('#table').datagrid({
            url: contextPath + "/resourceReport/distributionResourceReport"
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
        var beginTime = $("#beginTime").datetimebox("getValue");
        var endTime = $("#endTime").datetimebox("getValue");
        $('#table').datagrid({
            url: contextPath + "/resourceReport/distributionResourceReport",
            queryParams: {name: name, beginTime: beginTime, endTime: endTime}
        });
    }
    //导出
    function exportData() {
        window.open(contextPath + '/resourceReport/exportUsedResourceReportData');
    }
</script>
</body>
</html>