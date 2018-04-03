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
    <title>用户组</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/locale/easyui-lang-zh_CN_least.js"></script>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',border:false" title="用户组" style="padding-left: 2px;width: 370px;">
        <table id="table" class="easyui-datagrid"
               data-options="fit:true,rownumbers:false,pagination:true,singleSelect:true,
                       pageSize:20,pageList:[10,15,20,50],striped:true,
                       fitColumns:true,toolbar:'#toolbar' ">
            <thead>
            <tr>
                <th data-options="field:'id',hidden:true"></th>
                <th data-options="field:'name',width:$(this).width()*0.1">组名字</th>
                <%--<th data-options="field:'description',width:$(this).width()*0.1">描述</th>--%>
                <%--<th data-options="field:'grpCode',width:$(this).width()*0.1,align:''">编码</th>--%>
                <%--<th data-options="field:'createTime',width:$(this).width()*0.1,align:''">创建时间</th>--%>
                <%--<th data-options="field:'abc',width:$(this).width()*0.2,formatter:formmataction">操作</th>--%>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center',border:false" style="padding-left: 2px;">
        <div class="easyui-tabs" fit="true">
            <div title="用户信息" style="padding:10px;">
                <table class="easyui-datagrid" id="userTable" data-options="fit:true,rownumbers:false,
                        pagination:true,singleSelect:true,
                       pageSize:20,pageList:[10,15,20,50],striped:true,
                       fitColumns:true,toolbar:'#userToolbar' ">
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
            <div title="角色信息" style="padding:10px;">
                <table class="easyui-datagrid" id="roleTable" data-options="fit:true,rownumbers:false,
                        pagination:true,singleSelect:true,
                       pageSize:20,pageList:[10,15,20,50],striped:true,
                       fitColumns:true,toolbar:'#roleToolbar' ">
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
</div>
<div id="toolbar" class="paramContent">
    <div class="param-fieldset">
        <input name="groupname" id="groupname" class="param-input" type="input" placeholder="用户组名"
               style="margin-right: 5px">
    </div>
    <div class="btn-cls-search" onclick="searchModel('group')"></div>
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_add','ROLE_admin')">
            <div class="btn-cls-common" onclick="preEditRow('add')">新 增</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_edit','ROLE_admin')">
            <div class="btn-cls-common" onclick="preEditRow('update')">修 改</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_del','ROLE_admin')">
            <div class="btn-cls-common" onclick="preDeleteRow()">删 除</div>
        </sec:authorize>
    </div>
</div>
<div id="userToolbar" class="paramContent">
    <div class="param-fieldset">
        <input id="username" class="param-input" type="input" placeholder="用户名" style="margin-right: 5px">
    </div>
    <div class="btn-cls-search" onclick="searchModel('user')"></div>
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_bind_user','ROLE_admin')">
            <div class="btn-cls-common" onclick="bindUserOrRoleToGroup('user')">添加用户</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_unbind_user','ROLE_admin')">
            <div class="btn-cls-common" onclick="unbindUserOrRoleToGroup('user')">移除用户</div>
        </sec:authorize>
    </div>
</div>
<div id="roleToolbar" class="paramContent">
    <div class="param-fieldset">
        <input id="rolename" class="param-input" type="input" placeholder="角色名" style="margin-right: 5px">
    </div>
    <div class="btn-cls-search" onclick="searchModel('role')"></div>
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_bind_role','ROLE_admin')">
            <div class="btn-cls-common" onclick="bindUserOrRoleToGroup('role')">添加角色</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_unbind_role','ROLE_admin')">
            <div class="btn-cls-common" onclick="unbindUserOrRoleToGroup('role')">移除角色</div>
        </sec:authorize>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    var groupId = null;
    $(function () {
        $('#table').datagrid({
            url: contextPath + '/usergroup/list.do',
        });
        $('#userTable').datagrid({
            url: contextPath + '/userinfo/queryUserListByGroupId',
        });
        $('#roleTable').datagrid({
            url: contextPath + '/sysrole/queryRoleListByGroupId',
        });
        $('#table').datagrid({
            //绑定单击事件
            onClickRow: function (index, row) {
                groupId = row.id;
                $("#username").val('');
                $("#rolename").val('');
                $('#userTable').datagrid({
                    url: contextPath + '/userinfo/queryUserListByGroupId',
                    queryParams: {username: '', nick: '', groupId: row.id}
                });
                $('#roleTable').datagrid({
                    url: contextPath + '/sysrole/queryRoleListByGroupId',
                    queryParams: {name: '', groupId: row.id}
                });
            }
        });
    });
    //查询    组/用户/角色
    function searchModel(type) {
        if (type == 'user') {//查询用户
            var username = $("#username").val();
            $('#userTable').datagrid({
                url: contextPath + '/userinfo/queryUserListByGroupId',
                queryParams: {username: username, nick: '', groupId: groupId}
            });
        } else if (type == 'role') {//角色查询
            var rolename = $("#rolename").val();
            $('#roleTable').datagrid({
                url: contextPath + '/sysrole/queryRoleListByGroupId',
                queryParams: {name: rolename, groupId: groupId}
            });
        } else {//组查询
            var groupname = $("#groupname").val();
            $('#table').datagrid({
                url: contextPath + '/usergroup/list.do',
                queryParams: {groupname: groupname}
            });
        }
    }
    //展示部门/组提示
    function gnameLink(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" title="' + value + '" style="text-decoration-line: none">' + value + '</a>';
        } else {
            return "";
        }
    }
    //准备添加修改
    function preEditRow(value){
        var rows = $('#table').datagrid('getChecked');
        if(value=='add'){
            if(rows.length == 0){//新增
                editRow(0,0);
            }else if(rows.length > 1){
                layer.msg("只能选择一个组");
                return;
            }else{//添加
                editRow(0,rows[0].id);
            }
        }else{//修改
            if(rows.length == 0){
                layer.msg("没有选择组");
                return;
            }else if(rows.length > 1){
                layer.msg("只能选择一个组");
                return;
            }else{
                editRow(rows[0].id,0);
            }
        }
    }
    //添加or修改
    function editRow(groupid,pid){
        var  url = contextPath + "/usergroup/form.do";
        if(groupid!=0){//修改
            url+="?groupid="+groupid;
        }else{//添加
            if(pid!=0)
                url+="?pid="+pid;
        }
        top.layer.open({
            type : 2,
            title : '用户组信息',
            maxmin : true,
            id: 'groupwin', //设定一个id，防止重复弹出
            closeBtn:1,
            area: ['600px', '350px'],
            content: url,
            success: function (layero, index) {
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
            }
        });
    }
    //准备删除
    function preDeleteRow(){
        var rows = $('#table').datagrid('getChecked');
        var groparr = new Array();
        if(rows.length < 1){
            layer.msg('没有选择组');
            return;
        }else{
            for(var i=0;i<rows.length;i++){
                groparr.push(rows[i].id);
            }
        }
        deleteRow(groparr.join(','));
    }
    //删除
    function deleteRow(ids){
        top.layer.msg('确定删除么？删除会将已经绑定好的组绑定关系清除', {
            time: 0, //不自动关闭
            btn: ['删除', '取消'],
            yes: function (index) {
                $.getJSON(contextPath + "/usergroup/delete.do?ids=" + ids, function (data) {
                    if (data.state) {
                        top.layer.msg('删除成功');
                        $('#table').datagrid({
                            url: contextPath + '/usergroup/list.do'
                        });
                    } else {
                        top.layer.msg(data.msg);
                    }
                });
            },
            no:function(index){
                layer.close(index);
            }
        });
    }
    /*添加用户或者角色*/
    function bindUserOrRoleToGroup(type) {
        if (groupId == null || groupId == '') {
            alert("没有指定用户组，请指定");
            return;
        }
        if (type == 'user') {//添加用户
            var url = contextPath + '/usergroup/preUnbindUserList?groupId=' + groupId + '&isbind=unbind';
            openDialog("添加成员", url, '900px', '530px');
        } else {//添加角色
            var url = contextPath + '/usergroup/preUnbindRoleList?groupId=' + groupId + '&isbind=unbind';
            openDialog("添加角色", url, '900px', '530px');
        }
    }
    /*移除用户或者角色*/
    function unbindUserOrRoleToGroup(type) {
        if (groupId == null || groupId == '') {
            alert("没有指定用户组，请指定");
            return;
        }
        if (type == 'user') {//移除用户
            unbindUserToGroup();
        } else {//移除角色
            unbindRoleToGroup();
        }
    }
    //解除用户与组的绑定
    function unbindUserToGroup() {
        var rowArr = [];
        var rows = $("#userTable").datagrid('getChecked');
        if (rows.length > 0) {
            for (var i = 0; i < rows.length; i++) {
                rowArr.push(rows[i].ID);
            }
        } else {
            layer.msg("请选择要删除的成员");
            return;
        }
        var url = contextPath + '/usergroup/unbindUserToGroup';
        layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
            $.post(url, {ids: rowArr.join(','), groupId: groupId}, function (result) {
                alert(result.msg);
                //刷新列表
                if (result.state) {
                    $('#userTable').datagrid({
                        url: contextPath + '/userinfo/queryUserListByGroupId',
                        queryParams: {username: '', nick: '', groupId: groupId}
                    });
                }
            });
            layer.close(index);
        });
    }
    //解除角色与组的绑定
    function unbindRoleToGroup() {
        var rowArr = [];
        var rows = $("#roleTable").datagrid('getChecked');
        if (rows.length > 0) {
            for (var i = 0; i < rows.length; i++) {
                rowArr.push(rows[i].id);
            }
        } else {
            layer.msg("请选择要移除的角色");
            return;
        }
        var url = contextPath + '/usergroup/unbindRoleToGroup';
        layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
            $.post(url, {ids: rowArr.join(','), groupId: groupId}, function (result) {
                alert(result.msg);
                //刷新列表
                if (result.state) {
                    $('#roleTable').datagrid({
                        url: contextPath + '/sysrole/queryRoleListByGroupId',
                        queryParams: {name: '', groupId: groupId}
                    });
                }
            });
            layer.close(index);
        });
    }
</script>
</body>

</html>