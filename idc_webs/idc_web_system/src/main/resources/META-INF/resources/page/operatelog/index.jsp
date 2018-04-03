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
    <title>用户操作日志</title>
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
    <%--<div data-options="region:'west',width:250,border:true" title="用户组">--%>
        <%--<ul id="treegroup" class="ztree" style="margin-top:0; width:250px;"></ul>--%>
    <%--</div>--%>
    <div class="easyui-layout" fit="true">
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="table" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:true,pagination:true,singleSelect:true,
                           onLoadSuccess:loadsuccess,pageSize:20,pageList:[10,15,20,50],striped:true,
                           fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/log/list.do',  ">
                <thead>
                <tr>
                    <th data-options="field:'typestr',width:$(this).width()*0.1">操作类型</th>
                    <th data-options="field:'DESCRIPTION',width:$(this).width()*0.3,align:''">信息</th>
                    <th data-options="field:'USERNAME',width:$(this).width()*0.1">操作人</th>
                    <th data-options="field:'createtimestr',width:$(this).width()*0.1,align:''">创建时间</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" class="paramContent">
    <div class="param-fieldset">
        <input name="key" class="param-input" type="input" placeholder="关键字" style="margin-right: 5px">
    </div>
    <div class="btn-cls-search" id="ok"></div>
    <%--<div style="float: left">--%>
    <%--&lt;%&ndash;<sec:authorize access="hasAnyRole('ROLE_sys_usergroup_add','ROLE_admin')">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<a  href="javascript:void(0)" class="easyui-linkbutton sys_usergroup_add" data-options="iconCls:'icon-add'">新 增</a>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</sec:authorize>&ndash;%&gt;--%>
    <%--<input name="key" class="easyui-textbox" type="text" prompt="关键字">--%>
    <%--&lt;%&ndash;<input name="groupPid" class="form-control" type="hidden" placeholder="">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<a id="ok" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&ndash;%&gt;--%>
    <%--<button id="ok">查 询</button>--%>
    <%--</div>--%>
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
//        initTree()
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
    <%--function formmataction(value,row,index){--%>
        <%--var insert="";--%>

        <%--<sec:authorize access="hasAnyRole('ROLE_sys_usergroup_add','ROLE_admin')">--%>
         <%--insert+= '<input  class="n" type="button" onclick="editRow(0,'+row.id+')" value="添 加" >';--%>
          <%--&lt;%&ndash;insert+='<input class="btn btn-success" type="button" value="新增频道"  data-toggle="modal" data-target="#addChannelInfoModal" href="${contextPath}/usergroup/form.do"/>'&ndash;%&gt;--%>
        <%--</sec:authorize>--%>
        <%--<sec:authorize access="hasAnyRole('ROLE_sys_usergroup_edit','ROLE_admin')">--%>
        <%--insert+= '<input  class="btn btn-info  iptbtn" type="button" onclick="editRow('+row.id+',0)" value="修 改" >';--%>
        <%--</sec:authorize>--%>
        <%--<sec:authorize access="hasAnyRole('ROLE_sys_usergroup_del','ROLE_admin')">--%>
        <%--insert+='&nbsp;<input  class="btn btn-danger iptbtn" type="button" onclick="deleteRow('+row.id+')" value="刪 除" >'--%>
        <%--</sec:authorize>--%>
        <%--<sec:authorize access="hasAnyRole('ROLE_sys_usergroup_bind_role','ROLE_admin')">--%>
        <%--insert+='&nbsp;<input  class="btn btn-primary iptbtn" type="button" onclick="dindRole('+row.id+')" value="绑定角色" >'--%>
        <%--</sec:authorize>--%>

        <%--return insert;--%>
    <%--}--%>

</script>
</body>

</html>