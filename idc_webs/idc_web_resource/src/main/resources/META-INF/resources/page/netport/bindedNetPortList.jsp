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
    <title>端口信息</title>
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
                   pageSize:15,pageList:[10,15,20,50],striped:true,singleSelect:true,
                   fitColumns:false,toolbar:'#toolbar',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'portid',hidden:'true'"></th>
                    <th data-options="field:'portname',width:150">端口名称</th>
                    <th data-options="field:'alias',width:200,formatter:aliasDetail">端口别名</th>
                    <th data-options="field:'porttype',width:100">端口类型</th>
                    <th data-options="field:'STATUS',width:100,formatter:statusDetail">资源占用状态</th>
                    <th data-options="field:'CUSTOMERNAME',width:200,formatter:customerInfo">客户名称</th>
                    <th data-options="field:'portactive',width:100,formatter:formmatenabled">端口操作状态</th>
                    <%--<th data-options="field:'sidedevice',width:100">对端设备</th>--%>
                    <%--<th data-options="field:'ip',width:100">端口对应IP</th>--%>
                    <%--<th data-options="field:'mac',width:100">Mac地址</th>--%>
                    <%--<th data-options="field:'netmask',width:100">子网掩码</th>--%>
                    <th data-options="field:'bandwidth',width:100">端口带宽(Mbps)</th>
                    <th data-options="field:'assignation',width:100">分派带宽(Mbps)</th>
                    <%--<th data-options="field:'portpltype',width:100,formatter:portpltypeDetails">端口物理逻辑类型</th>--%>
                    <%--<th data-options="field:'mediatype',width:100,formatter:mediatypeDetails">物理端口类别</th>--%>
                    <%--<th data-options="field:'adminstatus',width:100,formatter:adminstatusDetails">端口管理状态</th>--%>
                    <th data-options="field:'ticketId',width:100">工单编号</th>
                    <th data-options="field:'descr',width:100">端口描述</th>
                    <th data-options="field:'note',width:100">备注</th>
                </tr>
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
</div>
<script type="text/javascript" language="JavaScript">
    var portIds = "${portIds}";
    $(function () {
        //加载数据
        $('#table').datagrid({
            url: contextPath + "/netport/queryBindedPortList",
            queryParams: {portIds: portIds},
            onDblClickRow: function (index, row) {
                var url = contextPath + "/netport/netportDetails.do?portid=" + row.PORTID + "&buttonType=view";
                openDialogView('端口信息', url, '800px', '530px');
            }
        });
    });
    function aliasDetail(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" style="text-decoration:none" title="' + value + '">' + value + '</a>';
        } else {
            return "";
        }
    }
    //资源占用状态
    function statusDetail(value, row, index) {
        if (value == 40) {
            return '空闲';
        } else if (value == 50) {
            return '预占';
        } else if (value == 60) {
            return '在服';
        } else if (value == 110) {
            return '不可用';
        } else {
            return '';
        }
    }
    function formmatenabled(value, row, index) {
        if (value == 1) {
            return "在用";
        } else if (value == 2) {
            return "空闲";
        } else if (value == 3) {
            return "测试";
        } else if (value == 4) {
            return "未知";
        } else if (value == 5) {
            return "休眠";
        } else if (value == 6) {
            return "模块不在";
        } else if (value == 7) {
            return "下层关闭";
        } else {
            return "其他";
        }
    }
    //查看客户信息
    function customerInfo(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="showCustomerInfo(\'' + row.CUSTOMERID + '\',\'' + value + '\')">' + value + '</a>';
        } else {
            return '';
        }
    }
    //查看客户信息
    function showCustomerInfo(customerId, customerName) {
        var url = contextPath + "/customerController/linkQueryWin.do?viewQuery=1&id=" + customerId;
        openDialogView(customerName, url, '784px', '600px');
    }
    function portpltypeDetails(value, row, index) {
        if (value == 0) {
            return "物理端口";
        } else if (value == 1) {
            return "逻辑端口";
        }
    }
    function mediatypeDetails(value, row, index) {
        if (value == 'fiber') {
            return "光口";
        } else if (value == 'cable') {
            return "电口";
        }
    }
    function adminstatusDetails(value, row, index) {
        if (value == 1) {
            return "UP";
        } else if (value == 2) {
            return "down";
        }
    }
    //查询
    function searchModels() {
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/netport/queryBindedPortList",
            queryParams: {name: name, portIds: portIds},
        });
    }
    //确认 资源分配（设备-端口）
    function doSubmit() {
        //进行资源绑定
        var rows = $('#table').datagrid('getChecked');
        if (rows != null && rows.length > 0) {
            return rows[0].portid;
        } else {
            layer.msg("请选择A端信息");
            return;
        }
    }
</script>
</body>
</html>