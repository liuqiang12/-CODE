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
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',width:250,border:true" title="用户组">
        <ul id="treegroup" class="ztree" style="margin-top:0; width:250px;"></ul>
    </div>
    <div data-options="region:'center',border:false" title=">>用户组信息" style="padding-left: 2px;">
        <table id="table" class="easyui-datagrid"
               data-options="fit:true,rownumbers:true,pagination:true,singleSelect:true,
                       onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                       fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/usergroup/list.do',  ">
            <thead>
            <tr>
                <th data-options="field:'name',width:$(this).width()*0.1">组名字</th>
                <th data-options="field:'description',width:$(this).width()*0.1">描述</th>
                <th data-options="field:'code',width:$(this).width()*0.1,align:''">编码</th>
                <th data-options="field:'createTime',width:$(this).width()*0.1,align:''">创建时间</th>
                <th data-options="field:'abc',width:$(this).width()*0.2,formatter:formmataction">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="toolbar" style="height: 28px">
    <div style="float: left">
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_add','ROLE_admin')">
            <a  href="javascript:void(0)" class="easyui-linkbutton sys_usergroup_add" data-options="iconCls:'icon-add'">新 增</a>
        </sec:authorize>
        <input name="groupname" class="easyui-textbox" type="text" prompt="用户组名">
        <input name="groupPid" class="form-control" type="hidden" placeholder="">
        <a id="ok" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    </div>
</div>
<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree.all-3.5.min.js"></script>
<link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/global/plugins/tree.js"></script>
<script type="text/javascript" language="JavaScript">
    function initTree(){
        var setting = {
            async:{
                enable: true,
                url: contextPath + "/usergroup/tree.do"
            },
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable:true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPId: ""
                }
            },
            callback: {
                onClick:queryByPid
            }
        };
        $.fn.zTree.init($("#treegroup"), setting);
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight')
    }
    var $table = $('#table'),
            $ok = $('#ok');
    function queryByPid(event, treeId, treeNode){
        if(treeNode.children&&treeNode.children.length>0){
            $("input[name='groupname']").val('');
            $("input[name='groupPid']").val(treeNode.id);
            refreshTable();
        }
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
        initTree()
        $ok.on('click',function () {
            refreshTable();
        });
        $(".sys_usergroup_add").on('click',function () {
            editRow(0,0);
        });
    });

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
            area : [ '500px', '300px' ],
            content :url
        });
    }
    function refTree(){
        var treeObj = $.fn.zTree.getZTreeObj("treegroup");
        treeObj.reAsyncChildNodes(null, "refresh");
    }
    function deleteRow(id){
        top.layer.msg('确定删除么？', {
            time: 0, //不自动关闭
            btn: ['删除', '取消'],
            yes: function(index){
                $.getJSON(contextPath + "/usergroup/delete.do?id="+id,function(data){
                    if(data.state){
                        top.layer.msg('删除成功');
                        refTree();
                        refreshTable();
                    }else{
                        top.layer.msg(data.msg);
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
    function bindRole(id){
        top.layer.open({
            type : 2,
            title : '组-角色分配',
            maxmin : true,
            id: 'bindwin', //设定一个id，防止重复弹出
            closeBtn:1,
            area : [ '600px','450px' ],
            content :contextPath + "/bindlink.do?type=g_r&id="+id
        })
    }
    function formmataction(value,row,index){
        var insert= new Array();
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_add','ROLE_admin')">
        insert.push('<a class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-add\'" onclick="editRow(0,'+row.id+')">添加</a> ');
        </sec:authorize>

        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_edit','ROLE_admin')">
        insert.push('<a class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editRow('+row.id+',0)">修改</a> ');
        </sec:authorize>

        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_del','ROLE_admin')">
        insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-no\'" onclick="deleteRow('+row.id+')">删除</a> ');
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_usergroup_bind_role','ROLE_admin')">
        insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'myicon-group\'" onclick="bindRole('+row.id+')">绑定角色</a> ');
        </sec:authorize>
        return insert.join('');
    }

</script>
</body>

</html>