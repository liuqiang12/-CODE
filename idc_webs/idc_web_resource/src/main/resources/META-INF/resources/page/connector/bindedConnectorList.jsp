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
    <title>connector信息</title>
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
                   fitColumns:false,toolbar:'#toolbar'
                  ">
                <thead>
                <tr>
                    <th data-options="field:'id',hidden:'true'"></th>
                    <th data-options="field:'name',width:250">端子名称</th>
                    <th data-options="field:'bname',width:200,formatter:rackDealisLink">所属机架</th>
                    <th data-options="field:'connectortype',width:100,formatter:connectorTypeDealis">连接类型</th>
                    <th data-options="field:'type',width:100,formatter:typeDealis">端口类型</th>
                    <th data-options="field:'port_mode',width:100,formatter:portModeDealis">光口模式</th>
                    <th data-options="field:'status',width:100,formatter:statusDealis">业务状态</th>
                    <th data-options="field:'ticket_id',width:200">工单编号</th>
                    <th data-options="field:'createdate',width:200,formatter:fmtDateAction">创建时间</th>
                    <th data-options="field:'updatedate',width:200,formatter:fmtDateAction">更新时间</th>
                    <th data-options="field:'memo',width:200">备注</th>
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
        //加载段子列表
        $('#table').datagrid({
            url: contextPath + "/idcConnector/queryBindedConnectorList",
            queryParams: {portIds: portIds},
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcConnector/getIdcConnectorInfo.do?id=" + row.ID + "&buttonType=view";
                openDialogView('端子信息', url, '800px', '330px');
            }
        });
    })
    //查询
    function searchModels() {
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/idcConnector/list.do",
            queryParams: {portIds: portIds, name: name}
        });
    }
    //归属机架链接
    function rackDealisLink(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="getRackDealis(' + row.RACK_ID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    //查看机架信息
    function getRackDealis(value) {
        openDialogShowView2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + value + '&businesstype=odf&buttonType=view', '800px', '400px', '查看机架视图');
    }
    //连接方式
    function connectorTypeDealis(value, row, index) {
        if (value == 1) {
            return 'RJ45';
        } else if (value == 2) {
            return 'FC';
        } else if (value == 3) {
            return 'LC';
        } else if (value == 4) {
            return 'SC';
        } else {
            return '';
        }
    }
    //端子类型
    function typeDealis(value, row, index) {
        if (value == 'odf') {
            return 'ODF';
        } else if (value == 'ddf') {
            return 'DDF';
        } else {
            return '';
        }
    }
    //光口模式
    function portModeDealis(value, row, index) {
        if (value == 1) {
            return '单模';
        } else if (value == 2) {
            return '多模';
        } else {
            return '';
        }
    }
    //业务状态
    function statusDealis(value, row, index) {
        if (value == 20) {
            return '可用';
        } else if (value == 30) {
            return '预留';
        } else if (value == 50) {
            return '预占';
        } else if (value == 55) {
            return '已停机';
        } else if (value == 60) {
            return '在服';
        } else {
            return '';
        }
    }
    //确认 资源分配（机架-端口）
    function doSubmit() {
        //进行资源绑定
        var rows = $('#table').datagrid('getChecked');
        if (rows != null && rows.length > 0) {
            return rows[0].id;
        } else {
            layer.msg("请选择A端信息");
            return;
        }
    }
</script>
</body>
</html>