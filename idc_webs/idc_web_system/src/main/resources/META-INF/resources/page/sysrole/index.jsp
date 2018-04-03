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
    <title>用户角色组</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="table" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,pagination:true,singleSelect:true,onClickRow:fun,
                           onLoadSuccess:loadsuccess,pageSize:20,pageList:[10,15,20,50],striped:true,
                           fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/sysrole/list.do',  ">
                <thead>
                <tr>
                    <th data-options="field:'id',hidden:true"></th>
                    <th data-options="field:'name',width:$(this).width()*0.1">角色名</th>
                    <th data-options="field:'role_key',width:$(this).width()*0.1">角色关键字</th>
                    <th data-options="field:'type',width:$(this).width()*0.1">角色类型</th>
                    <th data-options="field:'enabled',width:100,formatter:formmatenabled">状态</th>
                    <th data-options="field:'description',width:$(this).width()*0.1,align:''">描述</th>
                    <th data-options="field:'createTime',width:$(this).width()*0.1,align:''">创建时间</th>
                    <%--<th data-options="field:'abc',width:$(this).width()*0.2,formatter:formmataction">操作</th>--%>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <div id="toolbar" class="paramContent">
        <div class="param-fieldset">
            <select class="easyui-combobox" name="type"
                    data-options="panelHeight:'auto',editable:false,width:150,height:22">
                <option value="">角色类型</option>
                <option value="user">USER</option>
                <option value="dba">DBA</option>
                <option value="admin">ADMIN</option>
                <option value="root">ROOT</option>
            </select>
        </div>
        <div class="btn-cls-search" id="ok"></div>
        <div class="param-actionset">
            <sec:authorize access="hasAnyRole('ROLE_sys_role_add','ROLE_admin')">
                <%--<a  href="javascript:void(0)" class="easyui-linkbutton sys_role_add" data-options="iconCls:'icon-add'">新 增</a>--%>
                <%--<button onclick="editRow(0)">新 增</button>--%>
                <div class="btn-cls-common" onclick="editRow(0)">新 增</div>
            </sec:authorize>

            <sec:authorize access="hasAnyRole('ROLE_sys_role_edit','ROLE_admin')">
                <%--<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-edit\'" onclick="editRow('+row.id+')">修 改</a>--%>
                <%--<button onclick="editRow(1)">修 改</button>--%>
                <div class="btn-cls-common" onclick="editRow(1)">修 改</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_sys_role_del','ROLE_admin')">
                <%--<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-no\'" onclick="deleteRow('+row.id+')">删 除</a>--%>
                <%--<button onclick="deleteRow()">删 除</button>--%>
                <div class="btn-cls-common" onclick="deleteRow()">删 除</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_sys_role_bind_perm','ROLE_admin')">
                <%--<a  href="javascript:void(0)" class="easyui-linkbutton sys_role_bind_perm" data-options="iconCls:'icon-reload'">权限分配</a>--%>
                <%--<button onclick="bindPerm()">权限分配</button>--%>
                <div class="btn-cls-common" onclick="bindPerm()">权限分配</div>
            </sec:authorize>

            <%--<select name="type" class="easyui-combobox" style="width: 200px">--%>
            <%--<option value="">角色类型</option>--%>
            <%--<option value="user">USER</option>--%>
            <%--<option value="dba">DBA</option>--%>
            <%--<option value="admin">ADMIN</option>--%>
            <%--<option value="root">ROOT</option>--%>
            <%--</select>--%>
            <%--<a id="ok" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>--%>
            <%--<button id="ok">查 询</button>--%>
        </div>
    </div>
</div>
<%--<div id="grouptree" style="width: 490px;height: 280px;overflow: hidden" >--%>
    <%--<div id="tt" class="easyui-tabs" fit="true">--%>
        <%--<div title="菜单资源" data-options="url:'<%=request.getContextPath() %>/bindlink.do?type=r_p'">--%>
            <%--&lt;%&ndash;<ul id="ugt"class="easyui-tree" data-options="url:'<%=request.getContextPath() %>/usergroup/tree.do',checkbox:true">&ndash;%&gt;--%>
            <%--&lt;%&ndash;</ul>&ndash;%&gt;--%>
        <%--</div>--%>
        <%--<div title="按钮资源"></div>--%>
    <%--</div>--%>

<%--</div>--%>
<%--<script type="text/javascript" src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>--%>
<%--<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree.all-3.5.min.js"></script>--%>
<link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">
<%--<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/global/plugins/tree.js"></script>--%>

<%--<script type="text/javascript"--%>
<%--src="<%=request.getContextPath() %>/js/userinfo/userinfo.js"></script>--%>


<script type="text/javascript" language="JavaScript">
    var tableId = "table";
    var $table = $('#table'),
            $ok = $('#ok');
    function queryByPid(event, treeId, treeNode){
        if(treeNode.children&&treeNode.children.length>0){
            $("input[name='groupname']").val('');
            $("input[name='groupPid']").val(treeNode.id);
            refreshTable();
        }
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight')
    }
    function refreshTable(){
        var paramstmp = {};
        $('#toolbar').find('input[name]').each(function () {
            paramstmp[$(this).attr('name')] = $(this).val();
        });
        $table.datagrid('options').queryParams=paramstmp;
        $table.datagrid('reload');
    }
    $(function () {
//        initTree()
        $ok.on('click',function () {
            refreshTable();
        });
        $(".sys_role_add").on('click',function () {
            editRow(0);
        });
        $(".sys_role_bind_perm").on('click',function () {
            var rows = $table.datagrid("getChecked");
            var ids = new Array();
            for(var i=0;i<rows.length;i++){
                ids.push(rows[i].id);
            }
            bindPerm(ids.join(","))
        });

    });

    function queryParams(params) {
        var paramstmp = {};
        $('#toolbar').find(':input[name]').each(function () {
            paramstmp[$(this).attr('name')] = $(this).val();
        });
        $.extend(true, params,paramstmp)
        return params;
    }
    function editRow(id){
        var rows = $table.datagrid('getChecked');
        var  url = contextPath + "/sysrole/form.do";
        if(id!=0){//修改
            if(rows.length < 1 ){
                layer.msg('没有选择角色');
                return;
            }else if(rows.length > 1){
                layer.msg('只能选择一个角色');
                return;
            }
            id=rows[0].id;
            url+="?id="+id;
        }
        top.layer.open({
            type : 2,
            title : '用户角色信息',
            maxmin : true,
            id: 'groupwin', //设定一个id，防止重复弹出
            closeBtn:1,
            area: ['600px', '400px'],
            content: url,
            success: function (layero, index) {
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
            }
        });
    }
//    function refTree(){
//        var treeObj = $.fn.zTree.getZTreeObj("treegroup");
//        treeObj.reAsyncChildNodes(null, "refresh");
//    }
    function deleteRow(){
        var rows = $table.datagrid('getChecked');
        var rolearr = new Array();
        if(rows.length < 1){
            layer.msg('没有角色');
            return;
        }else{
            for(var i=0;i<rows.length;i++){
                rolearr.push(rows[i].id);
            }
        }
        parent.layer.msg('确定删除么？删除会将已经绑定好的角色权限资源绑定关系清除', {
            time: 0, //不自动关闭
            btn: ['删除', '取消'],
            yes: function(index){
                $.getJSON(contextPath + "/sysrole/delete.do?ids="+rolearr.join(','),function(data){
                    if(data.state){
                        parent.layer.msg('删除成功');
                        refreshTable();
                    }else{
                        parent.layer.msg(data.msg);
                    }
                });
            },
            no:function(index){
                layer.close(index);
            }
        });
    }

    /***
     *角色绑定
     * @param id
     */
    function bindPerm(){
        var rows = $table.datagrid('getChecked');
        var url = contextPath + "/bindlink.do?type=r_p";
        if(rows.length>0){
            url+="&id="+rows[0].id;
        }
        top.layer.open({
            type : 2,
            title : '角色权限分配',
            maxmin : true,
            id: 'bindwin', //设定一个id，防止重复弹出
            closeBtn:1,
            area : [ '600px','450px' ],
            content :url
        })
    }
    function formmatenabled(value,row,index){
        if(value){
            return '<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-ok\'">启用</a> ';
        }else{
            return '<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-no\'">禁用</a> ';
        }
    }
    function formmatRoleType(value,row,index){
        return  value.toUpperCase();
    }
    function formmataction(value,row,index){
        var insert= new Array();
        <sec:authorize access="hasAnyRole('ROLE_sys_role_edit','ROLE_admin')">
        insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-edit\'" onclick="editRow('+row.id+')">修 改</a> ');
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_role_del','ROLE_admin')">
        insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-no\'" onclick="deleteRow('+row.id+')">删除</a> ');
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_role_bind_perm','ROLE_admin')">
        insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-reload\'" onclick="bindPerm('+row.id+')">权限分配</a> ');
        </sec:authorize>
        return insert.join('');
    }

</script>
</body>

</html>