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
    <title>临时出入申请单信息</title>
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
                    <th data-options="field:'RMTMPINOUTID',hidden:'true'"></th>
                    <th data-options="field:'RMROOMNAME',width:200">机房名称</th>
                    <th data-options="field:'RMSTARTTIME',width:140,formatter:fmtDateAction">开始时间</th>
                    <th data-options="field:'RMENDTIME',width:140,formatter:fmtDateAction">结束时间</th>
                    <th data-options="field:'RMAPPLICANT',width:100">申请人</th>
                    <th data-options="field:'RMLEAD',width:100">项目负责人</th>
                    <th data-options="field:'RMCOMPANY',width:200">来人单位</th>
                    <th data-options="field:'RMNUM',width:100">人数</th>
                    <th data-options="field:'RMCONTENT',width:100">工作内容</th>
                    <th data-options="field:'RMCREATETIME',width:140,formatter:fmtDateAction">创建时间</th>
                    <th data-options="field:'RMCREATEUSER',width:100">创建人</th>
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
        <sec:authorize access="hasAnyRole('ROLE_rm_inoutform_add','ROLE_admin')">
            <div class="btn-cls-common" onclick="saveRmInOutForm('add')">新 增</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_rm_inoutform_edit','ROLE_admin')">
            <div class="btn-cls-common" onclick="saveRmInOutForm('update')">修 改</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_rm_inoutform_del','ROLE_admin')">
            <div class="btn-cls-common" onclick="deleteRmInOutForm()">删 除</div>
        </sec:authorize>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    $(function () {
        $('#table').datagrid({
            url: contextPath + "/rmTmpInOutForm/list",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/rmTmpInOutForm/getRmInOutFormInfo/view/" + row.RMTMPINOUTID;
                openDialogView('人员进出临时申请单信息', url, '1000px', '530px');
            }
        });
    })
    //新增/修改
    function saveRmInOutForm(type) {
        var url = contextPath + "/rmTmpInOutForm/getRmInOutFormInfo";
        if (type == 'add') {//新增
            url += "/add/0";
        } else {//修改
            var rows = $('#table').datagrid('getChecked');
            if (rows.length < 1) {
                layer.msg("没有选择申请单");
                return;
            } else if (rows.length > 1) {
                layer.msg("只能选择一个申请单");
                return;
            }
            url += "/update/" + rows[0].RMTMPINOUTID;
        }
        openDialog('人员进出临时申请单信息', url, '1000px', '530px');
    }
    //删除
    function deleteRmInOutForm() {
        var rows = $('#table').datagrid('getChecked');
        var rowArr = [];
        if (rows.length < 1) {
            layer.msg("没有选择申请单");
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].RMTMPINOUTID);
        }
        var url = contextPath + "/rmTmpInOutForm/deleteRmInOutFormInfo";
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
            url: contextPath + "/rmTmpInOutForm/list",
            queryParams: {name: name}
        });
    }
</script>
</body>
</html>