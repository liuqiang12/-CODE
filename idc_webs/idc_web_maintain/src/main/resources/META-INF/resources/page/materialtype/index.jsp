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
    <title>物资列别信息</title>
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
                   fitColumns:true,toolbar:'#toolbar',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'RMMATERIALTYPEID',hidden:'true'"></th>
                    <th data-options="field:'RMMATERIALTYPENAME',width:100">物资类别名称</th>
                    <th data-options="field:'RMMATERIALTYPECODE',width:100">物资类别编码</th>
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
        <sec:authorize access="hasAnyRole('ROLE_rm_material_type_add','ROLE_admin')">
            <div class="btn-cls-common" onclick="saveRmMaterialType('add')">新 增</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_rm_material_type_edit','ROLE_admin')">
            <div class="btn-cls-common" onclick="saveRmMaterialType('update')">修 改</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_rm_material_type_del','ROLE_admin')">
            <div class="btn-cls-common" onclick="deleteRmMaterialType()">删 除</div>
        </sec:authorize>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    $(function () {
        $('#table').datagrid({
            url: contextPath + "/rmMaterialType/list",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/rmMaterialType/getRmMaterialTypeInfo/view/" + row.RMMATERIALTYPEID;
                openDialogView('物资类别信息', url, '800px', '200px');
            }
        });
    })
    //新增/修改
    function saveRmMaterialType(type) {
        var url = contextPath + "/rmMaterialType/getRmMaterialTypeInfo";
        if (type == 'add') {//新增
            url += "/add/0";
        } else {//修改
            var rows = $('#table').datagrid('getChecked');
            if (rows.length < 1) {
                layer.msg("没有选择物资类别");
                return;
            } else if (rows.length > 1) {
                layer.msg("只能选择一个物资类别");
                return;
            }
            url += "/update/" + rows[0].RMMATERIALTYPEID;
        }
        openDialog('物资类别信息', url, '800px', '200px');
    }
    //删除
    function deleteRmMaterialType() {
        var rows = $('#table').datagrid('getChecked');
        var rowArr = [];
        if (rows.length < 1) {
            layer.msg("没有选择物资类别");
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].RMMATERIALTYPEID);
        }
        var url = contextPath + "/rmMaterialType/deleteRmMaterialTypeInfo";
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
            url: contextPath + "/rmMaterialType/list",
            queryParams: {name: name}
        });
    }
</script>
</body>
</html>