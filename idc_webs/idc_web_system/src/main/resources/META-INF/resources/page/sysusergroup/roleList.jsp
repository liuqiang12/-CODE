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
    <title>组-角色</title>
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
            <table id="roletable" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,pagination:true,
                   pageSize:15,pageList:[10,15,20,50],striped:true,
                   fitColumns:true,toolbar:'#toolbar',onClickRow: fun
                  ">
                <thead>
                <tr>
                    <th data-options="field:'id',hidden:true"></th>
                    <th data-options="field:'name',width:$(this).width()*0.1">角色名</th>
                    <th data-options="field:'role_key',width:$(this).width()*0.1">角色关键字</th>
                    <th data-options="field:'type',width:$(this).width()*0.1">角色类型</th>
                    <th data-options="field:'description',width:$(this).width()*0.1,align:''">描述</th>
                    <th data-options="field:'createTime',width:$(this).width()*0.1,align:''">创建时间</th>
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
    var tableId = "roletable";
    var groupId = "${groupId}";
    var isbind = "${isbind}";
    $(function () {
        $('#roletable').datagrid({
            url: contextPath + "/sysrole/queryRoleListByGroupId",
            queryParams: {groupId: groupId, isbind: isbind}
        });
    })
    //查询
    function searchModels() {
        var name = $("#buiName").val();
        $('#roletable').datagrid({
            url: contextPath + "/sysrole/queryRoleListByGroupId",
            queryParams: {name: name, groupId: groupId, isbind: isbind}
        });
    }
    //进行用户与组的绑定
    function doSubmit() {
        var rowArr = [];
        var rows = $("#roletable").datagrid('getChecked');
        if (rows.length > 0) {
            for (var i = 0; i < rows.length; i++) {
                rowArr.push(rows[i].id);
            }
        } else {
            layer.msg("请选择要添加的角色");
            return;
        }
        var url = contextPath + '/usergroup/bindRoleToGroup';
        $.post(url, {ids: rowArr.join(','), groupId: groupId}, function (result) {
            alert(result.msg);
            if (result.state) {
                var parentWin = top.winref[window.name];
                top[parentWin].$("#roleTable").datagrid("reload");
                var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(index); //再执行关闭
            }
        });
    }
</script>
</body>
</html>