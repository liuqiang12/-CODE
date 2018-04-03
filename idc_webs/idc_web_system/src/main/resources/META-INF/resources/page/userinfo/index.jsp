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
    <title>用户信息</title>
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
                   data-options="fit:true,rownumbers:false,pagination:true,singleSelect:true,onClickRow:fun,
                   onLoadSuccess:loadsuccess,pageSize:20,pageList:[10,15,20,50],striped:true,
                   fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/userinfo/list.do',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'ID',hidden:true"></th>
                    <th data-options="field:'USERNAME',width:100">用户名称</th>
                    <th data-options="field:'NICK',width:100">用户昵称</th>
                    <%--<th data-options="field:'SEX',width:100">性别</th>--%>
                    <%--<th data-options="field:'AGE',width:100">年龄</th>--%>
                    <th data-options="field:'LOCATIONNAME',width:200,formatter:locationNameLink">归属数据中心</th>
                    <th data-options="field:'GNAME',width:200,formatter:gnameLink">归属组</th>
                    <th data-options="field:'RNAME',width:100">归属区域</th>
                    <th data-options="field:'DNAME',width:200,formatter:gnameLink">归属部门</th>
                    <th data-options="field:'PHONE',width:100">联系电话</th>
                    <th data-options="field:'EMAIL',width:200">邮箱</th>
                    <th data-options="field:'ENABLED',width:100,formatter:formmatenabled">状态</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <div id="toolbar" class="paramContent">
        <div class="param-fieldset">
            <input name="username" id="username" class="param-input" type="input" placeholder="用户名" style="margin-right: 5px">
            <input name="nick" id="nick" class="param-input" type="input" placeholder="昵称">
        </div>
        <div class="btn-cls-search" onClick="searchModels();"></div>
        <div class="param-actionset">
            <sec:authorize access="hasAnyRole('ROLE_sys_userinfo_add','ROLE_admin')">
                <div class="btn-cls-common" onclick="editRow()">新 增</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_sys_userinfo_edit')">
                <div class="btn-cls-common" onclick="updateUserInfo('tabledata')">修 改</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_sys_userinfo_del')">
                <div class="btn-cls-common" onclick="deleteUserInfo('tabledata')">删 除</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_sys_userinfo_bind_group','ROLE_admin')">
                <div class="btn-cls-common" onclick="bindUserAndGroup()">绑定组</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_sys_userinfo_bind_role','ROLE_admin')">
            </sec:authorize>
        </div>
    </div>
</div>

<script type="text/javascript" language="JavaScript">
    var $table = $('#table');
    var tableId = "table";
    function formmatenabled(value,row,index){
        if(value){
            return '<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-ok\'">启用</a> ';
        }else{
            return '<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-remove\'">禁用</a> ';
        }
    }
    function locationNameLink(value, row, index){
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="showLocationInfo('+row.LOCATIONID+')">' + value + '</a>';
        } else {
            return "";
        }
    }
    function showLocationInfo(LOCATIONID){
        var url = contextPath + "/idcLocation/getIdcLocationInfo.do?id=" + LOCATIONID+"&buttonType=view";
        openDialogView('数据中心信息', url, '800px', '530px');
    }
    function gnameLink(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" title="' + value + '" style="text-decoration-line: none">' + value + '</a>';
        } else {
            return "";
        }
    }
    function editRow(userid){
        var  url = contextPath + "/userinfo/form.do";
        if(typeof(userid)!='undefined'){
            url+="?userid="+userid;
        }
        top.layer.open({
            type : 2,
            title : '用户信息',
            fix : false,
            closeBtn:1,
            maxmin : true,
            area : [ '60%', '350px' ],
            content: url,
            success: function (layero, index) {
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
            }
        });
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight')
    }
    /***
     *修改
     */
    function updateUserInfo(userid){
        var userarr = new Array();
        if('tabledata'==userid){
            var rows = $('#table').datagrid('getChecked');
            if(rows.length>1){
                layer.msg('有且只能选择一个用户');
                return;
            }
            for(var i=0;i<rows.length;i++){
                userarr.push(rows[i].ID);
            }
        }else{
            userarr.push(userid);
        }
        if (userarr.length == 0) {
            layer.msg('没有选择用户');
            return;
        }
        editRow(userarr[0]);
    }
    /***
     *删除
     */
    function deleteUserInfo(userid){
        var userarr = new Array();
        if('tabledata'==userid){
            var rows = $('#table').datagrid('getChecked');
            for(var i=0;i<rows.length;i++){
                userarr.push(rows[i].ID);
            }
        }else{
            userarr.push(userid);
        }
        if (userarr.length == 0) {
            layer.msg('没有选择用户');
            return;
        }
        deleteRow(userarr.join(','));
    }
    function bindUserAndGroup() {
        top.layer.open({
            type: 2,
            area: ['600px', '400px'],
            fixed: false,
            maxmin: true,
            content: contextPath + '/userinfo/bindUserAndGroup'
        })
    }
    function deleteRow(id){
        layer.msg('确定删除么？', {
            time: 0, //不自动关闭
            btn: ['删除', '取消'],
            yes: function(index){
                $.getJSON(contextPath + "/userinfo/delete.do?ids="+id,function(data){
                    if(data.state){
                        layer.msg('删除成功');
                        $table.datagrid('reload');
                    }else{
                        layer.msg(data.msg);
                    }
                });
            },
            no:function(index){
                layer.close(index);
            }
        });
    }
    //查询
    function searchModels(){
        var username = $("#username").val();
        var nick = $("#nick").val();
        $('#table').datagrid({
            url:contextPath + "/userinfo/list.do",
            queryParams:{username:username,nick:nick}
        });
    }
</script>
</body>
</html>