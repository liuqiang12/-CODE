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
    <title>物资出入申请单信息</title>
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
                   fitColumns:false,toolbar:'#toolbar',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'RMMATERIALDETAILID',hidden:'true'"></th>
                    <th data-options="field:'RMMATERIALTYPENAME',width:200">物资名称</th>
                    <th data-options="field:'RMMATERIALCUSTOMER',width:100">客户名称</th>
                    <th data-options="field:'RMMATERIALNUM',width:100">物资数量</th>
                    <th data-options="field:'RMMATERIALINOUTTIME',width:140,formatter:fmtDateAction">出入时间</th>
                    <th data-options="field:'RMMATERIALINOUTTYPE',width:100,formatter:inoutTypeDetails">出入类型</th>
                    <th data-options="field:'RMMATERIALCODE',width:200">运单号</th>
                    <th data-options="field:'RMMATERIALDISPOSEMAN',width:100">处理人</th>
                    <th data-options="field:'RMCREATETIME',width:140,formatter:fmtDateAction">创建时间</th>
                    <th data-options="field:'RMCREATEUSER',width:100">创建人</th>
                    <th data-options="field:'NOTE',width:200">备注</th>
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

    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_rm_material_add','ROLE_admin')">
            <div class="btn-cls-common" onclick="saveRmMaterial('add')">新 增</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_rm_material_edit','ROLE_admin')">
            <div class="btn-cls-common" onclick="saveRmMaterial('update')">修 改</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_rm_material_del','ROLE_admin')">
            <div class="btn-cls-common" onclick="deleteRmMaterial()">删 除</div>
        </sec:authorize>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    $(function () {
        $('#table').datagrid({
            url: contextPath + "/rmMaterialDatail/list",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/rmMaterialDatail/getRmMaterialDatailInfo/view/" + row.RMMATERIALDETAILID;
                openDialogView('物资出入申请单信息', url, '800px', '400px');
            }
        });
    })
    //新增/修改
    function saveRmMaterial(type) {
        var url = contextPath + "/rmMaterialDatail/getRmMaterialDatailInfo";
        if (type == 'add') {//新增
            url += "/add/0";
        } else {//修改
            var rows = $('#table').datagrid('getChecked');
            if (rows.length < 1) {
                layer.msg("没有选择物资出入申请单");
                return;
            } else if (rows.length > 1) {
                layer.msg("只能选择一个物资出入申请单");
                return;
            }
            url += "/update/" + rows[0].RMMATERIALDETAILID;
        }
        openDialog('物资出入申请单信息', url, '800px', '400px');
    }
    //删除
    function deleteRmMaterial() {
        var rows = $('#table').datagrid('getChecked');
        var rowArr = [];
        if (rows.length < 1) {
            layer.msg("没有选择物资出入申请单");
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].RMMATERIALDETAILID);
        }
        var url = contextPath + "/rmMaterialDatail/deleteRmMaterialDatailInfo";
        layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
            $.post(url, {ids: rowArr.join(',')}, function (result) {
                layer.msg(result.msg);
                //刷新列表
                $("#table").datagrid('reload');
            });
            layer.close(index);
        });

    }
    //查询
    function searchModels() {
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/rmMaterialDatail/list",
            queryParams: {name: name}
        });
    }
    //出入类型
    function inoutTypeDetails(value, row, index) {
        if (value == '1') {
            return '入库';
        } else if (value == '2') {
            return '出库';
        } else {
            return '';
        }
    }
</script>
</body>
</html>