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
    <title>组-用户</title>
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
            <table id="usertable" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,pagination:true,
                   pageSize:15,pageList:[10,15,20,50],striped:true,
                   fitColumns:true,toolbar:'#toolbar',onClickRow: fun
                  ">
                <thead>
                <tr>
                    <th data-options="field:'ID',hidden:true"></th>
                    <th data-options="field:'USERNAME',width:100">用户名称</th>
                    <th data-options="field:'NICK',width:200">用户昵称</th>
                    <%--<th data-options="field:'SEX',width:100">性别</th>--%>
                    <%--<th data-options="field:'AGE',width:100">年龄</th>--%>
                    <th data-options="field:'RNAME',width:100">归属区域</th>
                    <th data-options="field:'DNAME',width:200,formatter:gnameLink">归属部门</th>
                    <th data-options="field:'PHONE',width:100">联系电话</th>
                    <th data-options="field:'EMAIL',width:200">邮箱</th>
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
    var tableId = "usertable";
    var groupId = "${groupId}";
    var isbind = "${isbind}";
    $(function () {
        $('#usertable').datagrid({
            url: contextPath + "/userinfo/queryUserListByGroupId",
            queryParams: {groupId: groupId, isbind: isbind}
        });
    })
    //展示部门/组提示
    function gnameLink(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" title="' + value + '" style="text-decoration-line: none">' + value + '</a>';
        } else {
            return "";
        }
    }
    //查询
    function searchModels() {
        var username = $("#buiName").val();
        $('#usertable').datagrid({
            url: contextPath + "/userinfo/queryUserListByGroupId",
            queryParams: {username: username, groupId: groupId, isbind: isbind}
        });
    }
    //进行用户与组的绑定
    function doSubmit() {
        var rowArr = [];
        var rows = $("#usertable").datagrid('getChecked');
        if (rows.length > 0) {
            for (var i = 0; i < rows.length; i++) {
                rowArr.push(rows[i].ID);
            }
        } else {
            layer.msg("请选择要添加的成员");
            return;
        }
        var url = contextPath + '/usergroup/bindUserToGroup';
        $.post(url, {ids: rowArr.join(','), groupId: groupId}, function (result) {
            alert(result.msg);
            if (result.state) {
                var parentWin = top.winref[window.name];
                top[parentWin].$("#userTable").datagrid("reload");
                var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(index); //再执行关闭
            }
        });
    }
</script>
</body>
</html>