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
    <title>机构信息</title>
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
    <%--
    <div data-options="region:'west',width:240,border:true" title="机构">
        <ul id="treeregoin" class="ztree" style="margin-top:0; width:240px;"></ul>
    </div>
    --%>
    <div data-options="region:'east',width:400,border:true" title="部门">
        <table id="table1" class="easyui-treegrid"
               data-options="fit:true,rownumbers:true,pagination:false,singleSelect:true,
                         idField:'id',
                       treeField: 'name', lines: true,
                       onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                       fitColumns:true,toolbar:'#toolbar1',url:'<%=request.getContextPath() %>/sysdepartment/list.do',  ">
            <thead>
            <tr>
                <th data-options="field:'name',width:$(this).width()*0.1">部门名字</th>
                <th data-options="field:'orgName',width:$(this).width()*0.1">所属机构</th>
                <th data-options="field:'description',width:$(this).width()*0.1">描述</th>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center',border:false" title=">>机构信息" style="padding-left: 2px;">
        <table id="table" class="easyui-treegrid"
               data-options="fit:true,rownumbers:true,pagination:false,singleSelect:true,
                         idField:'id',onClickRow:clickrow,
                       treeField: 'name', lines: true,loadFilter:loadFilter,
                       onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                       fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/organization/list.do',  ">
            <thead>
            <tr>
                <th data-options="field:'name',width:$(this).width()*0.1">机构名字</th>
                <th data-options="field:'regionName',width:$(this).width()*0.1">所属区域</th>
                <th data-options="field:'description',width:$(this).width()*0.1">描述</th>
                <th data-options="field:'abc',width:$(this).width()*0.2,formatter:formmataction">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="toolbar" class="paramContent">
    <div class="param-fieldset">
        <input name="regionName" id="regionName" class="param-input" type="input" placeholder="名称"
               style="margin-right: 5px">
        <input name="region_pid" class="form-control" type="hidden" placeholder="">
    </div>
    <div class="btn-cls-search" id="ok"></div>
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_admin')">
            <%--<a  href="javascript:void(0)" class="easyui-linkbutton " onclick="editRow(0,0,0)" data-options="iconCls:'icon-add'">新 增</a>--%>
            <%--<button onclick="editRow(0,0,0)">新 增</button>--%>
            <div class="btn-cls-common" onclick="editRow(0,0,0)">新 增</div>
        </sec:authorize>
        <%--<input name="regionName" id="regionName" class="easyui-textbox" type="text"  type="text" prompt="名称">--%>
        <%--<input name="region_pid" class="form-control" type="hidden" placeholder="">--%>
        <%--&lt;%&ndash;<a id="ok" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查 询</a>&ndash;%&gt;--%>
        <%--<button id="ok">查 询</button>--%>
    </div>
</div>
<div id="toolbar1" class="paramContent">
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_admin')">
            <%--
            <a  href="javascript:void(0)" class="easyui-linkbutton dpt_add" onclick="dpt_add(0)" data-options="iconCls:'icon-add'">新 增</a>
            <a  href="javascript:void(0)" class="easyui-linkbutton dpt_edit" onclick="dpt_edit()" data-options="iconCls:'icon-edit'">编 辑</a>
            <a  href="javascript:void(0)" class="easyui-linkbutton dpt_del" onclick="dpt_del()" data-options="iconCls:'icon-remove'">删 除</a>
            --%>
            <div class="btn-cls-common" onclick="dpt_add(0)">新 增</div>
            <div class="btn-cls-common" onclick="dpt_edit()">编 辑</div>
            <div class="btn-cls-common" onclick="dpt_del()">删 除</div>
            <%--<button onclick="dpt_add(0)">新 增</button>--%>
            <%--<button onclick="dpt_edit()">编 辑</button>--%>
            <%--<button onclick="dpt_del()">删 除</button>--%>
        </sec:authorize>
    </div>
</div>
<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree.all-3.5.min.js"></script>
<link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/global/plugins/tree.js"></script>
<script type="text/javascript" language="JavaScript">
    $(document).ready(function () {
        initTree()
    });
    function initTree(){
        $("#treeregoin").regionTree({
            isShowOrg:true,
            callback:{
                onAsyncSuccess:function(event, treeId, treeNode, msg){

                }
            }
        },queryByPid);
    }
    function queryByPid(event, treeId, treeNode){
        clickrow();
        if(treeNode.children&&treeNode.children.length>0){

        }
        $('#regionName').textbox("clear");
        if(treeNode.parentId==null){
            $('#toolbar').find("input[name='region_pid']").val("");
        }else{
            var childNodes  =new Array() ;
            childNodes = getAllChildrenNodes(treeNode,childNodes);
            var childNodeIds  =new Array() ;
            $.each(childNodes,function(i,iteam){
                childNodeIds.push(iteam.id);
            })
            childNodeIds.push(treeNode.id)
            $('#toolbar').find("input[name='region_pid']").val(childNodeIds.join(","));
        }
        refreshTable();

    }
    function getAllChildrenNodes(treeNode,result){
        if (treeNode.isParent) {
            var childrenNodes = treeNode.children;
            if (childrenNodes) {
                for (var i = 0; i < childrenNodes.length; i++) {
                    result.push(childrenNodes[i]);
                    result = getAllChildrenNodes(childrenNodes[i], result);
                }
            }
        }
        return result;
    }
    /***
     * 点击组织显示部门
     * */
    function clickrow(row){
            if(row){
                $("#table1").treegrid('options').queryParams={orgid:row.id};

            }else{
                $("#table1").treegrid('options').queryParams={orgid:''};
            }
                 $("#table1").treegrid('reload');
    }
    function treecall(event, treeId, treeNode){
//        if(treeNode.parentId==null){
//            $('#toolbar').find("input[name='regionId']").val("");//查询所有
//        }else{
//            $('#toolbar').find("input[name='regionId']").val(treeNode.id);
//        }
        refreshTable();
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').treegrid('fixRowHeight')
    }
    var $table = $('#table'),
            $ok = $('#ok');
//    function queryByPid(event, treeId, treeNode){
//        if(treeNode.children&&treeNode.children.length>0){
//            $("input[name='name']").val('');
//            $("input[name='pid']").val(treeNode.id);
//            refreshTable();
//        }
//    }
    function refreshTable(){

         $table.treegrid('reload');
    }
    function refreshTable1(){
        $("#table1").treegrid('reload');
    }
    $(function () {
        $ok.on('click',function () {
            refreshTable();
        });
        $(".sys_usergroup_add").on('click',function () {
            editRow(0,0,0);
        });
    });

    function editRow(type,id,pid){
        var  url ="";
        if(type==0){
              url = contextPath + "/organization/form.do";
            if(id!=0){//修改
                url+="?orgid="+id;

            }else{//添加
                if(pid!=0)
                    url+="?pid="+pid;
            }
        }
        if(type==1){
            url = contextPath + "/sysdepartment/form.do";
            if(id!=0){//修改
                url+="?dptid="+id;

            }else{//添加
                if(pid!=0)
                    url+="?pid="+pid;
            }
        }
        top.layer.open({
            type : 2,
            title : '机构部门信息编辑',
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
    function deleteRow(type,id){
        var url="";
        if(type==0){
            url=contextPath + "/organization/delete.do?id="+id;
        }
        if(type==1){
            url=contextPath + "/sysdepartment/delete.do?id="+id;
        }
        top.layer.msg('将会级联删除数据，确定删除么？', {
            time: 0, //不自动关闭
            btn: ['删除', '取消'],
            yes: function(index){
                $.getJSON(url,function(data){
                    if(data.state){
                        top.layer.msg('删除成功');
                        if(type==0){
                            refreshTable();
                        }else{
                            refreshTable1();
                        }
                    }else{
                        if(type==0){
                            top.layer.msg("删除失败，是否有下级部门？");
                        }else{
                            top.layer.msg(data.msg);
                        }

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
        <sec:authorize access="hasAnyRole('ROLE_admin')">
        insert.push('<a class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-add\'" onclick="editRow(0,0,'+row.id+')">添加</a> ');
        </sec:authorize>

        <sec:authorize access="hasAnyRole('ROLE_admin')">
        insert.push('<a class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-edit\'" onclick="editRow(0,'+row.id+',0)">修改</a> ');
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_admin')">
        insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-no\'" onclick="deleteRow(0,'+row.id+')">删除</a> ');
        </sec:authorize>
        <%--<sec:authorize access="hasAnyRole('ROLE_sys_usergroup_bind_role','ROLE_admin')">--%>
        <%--insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'myicon-group\'" onclick="bindRole('+row.id+')">绑定角色</a> ');--%>
        <%--</sec:authorize>--%>
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
    /***
     * 查询功能的实现直接本地过滤数据
     * @param data
     */
    function loadFilter(data){
        var regionName = $("#regionName").textbox("getValue");
        var region_pid = $("input[name='region_pid']").val();
        var regions = region_pid.split(",");
        var datas = new Array();
        $.each(data.rows,function(i,iteam){
            if(region_pid==''||regions.indexOf(iteam.region+"")>-1){
                if(regionName==''||iteam.name.indexOf(regionName)>-1){
                    if(region_pid!=''||regionName!=''){
                        iteam._parentId=undefined
                    }
                    datas.push(iteam);
                }
            }
        });
        data.rows = datas;
        return data;
    }

    function dpt_add(){
        editRow(1,0,0)
    }

    function dpt_edit(){
        var row = $("#table1").treegrid("getSelected");
        if(!row){
            top.layer.msg("没有选择编辑数据");
            return;
        }
        editRow(1,row.id,0);
    }
    function dpt_del(){
        var row = $("#table1").treegrid("getSelected");
        if(!row){
            top.layer.msg("没有选择要删除的数据");
            return;
        }
        deleteRow(1,row.id)
    }
</script>
</body>

</html>