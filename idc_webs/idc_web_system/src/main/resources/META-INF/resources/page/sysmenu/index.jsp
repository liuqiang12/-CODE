<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>--%>
    <%--<script type="text/javascript"--%>
    <%--src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>--%>
    <%--<script type="text/javascript"--%>
    <%--src="<%=request.getContextPath() %>/js/base/rtree.js"></script>--%>
    <style>
        .col-xs-12 >table,td,th {  border: 1px solid #8DB9DB; padding:5px; border-collapse: collapse; font-size:16px; }
    </style>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-tabs" style="height: 100%;width: 100%" data-options="fit:true">
    <div title="菜单资源" style="height: 100%;width: 100%" data-options="fit:true">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'center',border:false" style="padding-left: 2px;">
                <div class="easyui-layout" fit="true">
                    <div data-options="region:'center',border:false" style="padding-left: 2px;">
                        <table id="table1" class="easyui-treegrid" title="菜单资源"
                               data-options="fit:true,rownumbers:true,idField:'id',singleSelect:true,
                               striped:true,
                       treeField: 'name', lines: true,border:false,
                       loadFilter:loadFilter,onLoadSuccess:loadsuccess,
                       fitColumns:true,toolbar:'#toolbar1',url:'<%=request.getContextPath() %>/sysmenu/tree.do',  ">
                            <thead>
                            <tr>
                                <th data-options="field:'name',width:$(this).width()*0.15">菜单名称</th>
                                <th data-options="field:'url',width:$(this).width()*0.1,formatter:function(value,row,index){return row.self.url}">连接地址(url)</th>
                                <th data-options="field:'enabled',width:$(this).width()*0.1,align:'',formatter:formmatenabled">是否禁用</th>
                                <th data-options="field:'description',width:$(this).width()*0.1,formatter:function(value,row,index){return row.self.description}">备注</th>
                                <%--<th data-options="field:'abc',width:$(this).width()*0.2,formatter:formmataction">操作</th>--%>
                            </tr>
                            </thead>
                        </table>
                        <%--<div class="">--%>
                        <%--<div class="col-xs-12">--%>
                        <%--<table  class="" id="treeTable1" style="width:100%">--%>
                        <%--<tr class="thhead">--%>
                        <%--<td>菜单名称</td>--%>
                        <%--<td>连接地址(url)</td>--%>
                        <%--<td>是否禁用</td>--%>
                        <%--<td>备注</td>--%>
                        <%--<td>操作</td>--%>
                        <%--</tr>--%>

                        <%--<c:forEach var="item" items="${list}">--%>
                        <%--<tr id="${item.id}" pId="${item.parentId}">--%>
                        <%--<td><span ><c:if test="${fn:length(pageHelper.list)>0}">controller="true"</c:if>${item.name}</span></td>--%>
                        <%--<td>${item.url}</td>--%>
                        <%--<td>--%>
                        <%--<c:if test="${item.enabled eq 1}">--%>
                        <%--&lt;%&ndash;<input class="btn btn-success " type="button"  value="启 用" >&ndash;%&gt;--%>
                        <%--<a class="easyui-linkbutton " data-options="plain:true,iconCls:'icon-ok'">启用</a>--%>
                        <%--</c:if>--%>
                        <%--<c:if test="${item.enabled ne 1}">--%>
                        <%--&lt;%&ndash;<input class="btn btn-danger " type="button"  value="禁 用" >&ndash;%&gt;--%>
                        <%--<a class="easyui-linkbutton " data-options="plain:true,iconCls:'icon-no'">禁用</a>--%>
                        <%--</c:if>--%>
                        <%--</td>--%>
                        <%--<td>${item.description}</td>--%>
                        <%--<td>--%>
                        <%--<sec:authorize access="hasAnyRole('ROLE_sys_menu_enabled','ROLE_admin')">--%>
                        <%--<a  href="javascript:void(0)" class="easyui-linkbutton sys_menu_enabled" menuid="${item.id}" data-options="iconCls:'icon-reload'">切换状态</a>--%>
                        <%--&lt;%&ndash;<input  class="btn btn-primary  sys_menu_enabled" menuid="${item.id}" type="button" value="切换状态">&ndash;%&gt;--%>
                        <%--</sec:authorize>--%>
                        <%--</td>--%>
                        <%--</tr>--%>
                        <%--</c:forEach>--%>
                        <%--</table>--%>
                        <%--</div><!-- /.row -->--%>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div title="按钮资源" data-options="fit:true,border:false" style="padding-left: 2px;">
        <table id="table" class="easyui-treegrid" title="操作资源"
               data-options="fit:true,rownumbers:true,idField:'id',singleSelect:true,
                       treeField: 'name', lines: true,border:false,
                       loadFilter:loadFilter,onLoadSuccess:loadsuccess,striped:true,
                       <%--onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,--%>
                       fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/sysoperate/tree.do',  ">
            <thead>
            <tr>
                <th data-options="field:'name',width:$(this).width()*0.15">模块/资源</th>
                <th data-options="field:'self.optName',width:$(this).width()*0.1,formatter:function(value,row,index){return row.self.optName}">操作名称</th>
                <th data-options="field:'self.optUrl',width:$(this).width()*0.1,formatter:function(value,row,index){return row.self.optUrl}">URL</th>
                <th data-options="field:'page_path',width:$(this).width()*0.1,formatter:function(value,row,index){return row.self.pagePath}">路径</th>
                <th data-options="field:'enabled',width:$(this).width()*0.1,align:'',formatter:formmatenabled1">是否禁用
                </th>
                <th data-options="field:'opt_status',width:$(this).width()*0.1,align:'',formatter:function(value,row,index){
                     if(row.self.optStatus==0){
                       return '隐藏'
                     }
                       else if(row.self.optStatus==1){
                       return '显示'
                     }
                       else if(row.self.optStatus==-1){
                        return '禁用'
                     }else{
                         return ''
                     }

                }">操作状态
                </th>
                <th data-options="field:'description',width:$(this).width()*0.1,formatter:function(value,row,index){return row.self.description}">
                    描述
                </th>
                <%--<th data-options="field:'abc',width:$(this).width()*0.2,formatter:formmataction">操作</th>--%>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div id="toolbar" class="paramContent">
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_operate_add','ROLE_admin')">
            <%--<a  href="javascript:void(0)" class="easyui-linkbutton" onclick="addperm(1,0)" data-options="iconCls:'icon-add'">新 增</a>--%>
            <%--<button onclick="addperm(1,0)">新 增</button>--%>
            <div class="btn-cls-common" onclick="addperm(1,0)">新 增</div>
        </sec:authorize>
        <%--<input name="groupname" class="easyui-textbox" type="text" prompt="用户组名">--%>
        <%--<input name="groupPid" class="form-control" type="hidden" placeholder="">--%>
        <%--<a id="ok" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>--%>
    </div>
</div>
<div id="toolbar1" class="paramContent">
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_menu_add','ROLE_admin')">
            <%--<a  href="javascript:void(0)" class="easyui-linkbutton" onclick="addperm(0,0)" data-options="iconCls:'icon-add'">新 增</a>--%>
            <%--<button onclick="addperm(0,0)">新 增</button>--%>
            <div class="btn-cls-common" onclick="addperm(0,0)">新 增</div>
        </sec:authorize>
        <%--<input name="groupname" class="easyui-textbox" type="text" prompt="用户组名">--%>
        <%--<input name="groupPid" class="form-control" type="hidden" placeholder="">--%>
        <%--<a id="ok" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>--%>
    </div>
</div>
<%--<script src="<%=request.getContextPath() %>/framework/treetable/jquery.treeTable.js"></script>--%>
<%--<link  href="<%=request.getContextPath() %>/framework/treetable/vsStyle/jquery.treeTable.css" rel="stylesheet" type="text/css" />--%>
<script type="text/javascript" language="JavaScript">
    $(function(){
        var option = {
            theme:'vsStyle',
            expandLevel : 2,
            beforeExpand : function($treeTable, id) {
                //判断id是否已经有了孩子节点，如果有了就不再加载，这样就可以起到缓存的作用
                if ($('.' + id, $treeTable).length) { return; }
                //这里的html可以是ajax请求
            },
            onSelect : function($treeTable, id) {
                //window.console && console.log('onSelect:' + id);

            }
        };
        //$('#treeTable1').treeTable(option);
    });
    var $table = $('#table'),
        $ok = $('#ok');
    function queryByPid(event, treeId, treeNode){
        if(treeNode.children&&treeNode.children.length>0){
            $("input[name='groupname']").val('');
            $("input[name='groupPid']").val(treeNode.id);
            refreshTable();
        }
    }
    function loadFilter(data){
        return data;
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight')
    }
    function refreshTable(){
        $table.treegrid('reload');
        $("#table1").treegrid('reload');
    }
    function addperm(type,id){
        var  url = contextPath + "/permissioninfo/form.do/"+type+"/"+id;
        top.layer.open({
            type : 2,
            title : '资源信息',
            maxmin : true,
            id: 'groupwin', //设定一个id，防止重复弹出
            closeBtn:1,
            area : [ '600px', '450px' ],
            content: url,
            success: function (layero, index) {
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
            }
        });

    }
    $(function () {
//        initTree()
        $ok.on('click',function () {
            refreshTable();
        });
        $(".sys_menu_enabled").on('click',function () {
            var id = $(this).attr("menuid");
            switchstatus(id);
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
        var  url = basePath + "/sysperm/form.do";
        if(id!=0){//修改
            url+="?id="+id;
        }
        top.layer.open({
            type : 2,
            title : '用户角色信息',
            maxmin : true,
            id: 'groupwin', //设定一个id，防止重复弹出
            closeBtn:1,
            area : [ '600px', '450px' ],
            content :url
        });
    }
    //    function refTree(){
    //        var treeObj = $.fn.zTree.getZTreeObj("treegroup");
    //        treeObj.reAsyncChildNodes(null, "refresh");
    //    }
    function deleteRow(type,id){
        layer.msg('确定删除么？', {
            time: 0, //不自动关闭
            btn: ['删除', '取消'],
            yes: function(index){
                $.getJSON(basePath + "/permissioninfo/delete.do?type="+type+"&id="+id,function(data){
                    if(data.state){
                        layer.msg('删除成功');
                        refreshTable();
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
    function switchstatus(id) {
        parent.layer.msg('确定切换么？', {
            time: 0, //不自动关闭
            btn: ['确定', '取消'],
            yes: function (index) {
                $.getJSON(contextPath + "/sysmenu/switch.do?id=" + id, function (data) {
                    if (data.state) {
                        parent.layer.msg('切换成功');
                        window.location.reload();//刷新当前页面.
                    } else {
                        parent.layer.msg('切换失败');
                    }
                });
            },
            no: function (index) {
                layer.close(index);
            }
        });
    }
    /***
     *角色绑定
     * @param id
     */
    function dindRole(id){

    }
    function formmatRoleType(value,row,index){
        return  value.toUpperCase();
    }
    function formmatenabled(value,row,index){
        if(row.self.url){
            if (row.self.enabled == 1){
                return   '<a class="easyui-linkbutton " onclick="switchstatus('+row.id+')" data-options="plain:true,iconCls:\'icon-ok\'">启用</a>';
            }else{
                return  '<a class="easyui-linkbutton " onclick="switchstatus('+row.id+')" data-options="plain:true,iconCls:\'icon-no\'">禁用</a>';
            }
        }else{
            if (row.self.enabled == 1){
                return   '<a class="easyui-linkbutton "  data-options="plain:true,iconCls:\'icon-ok\'">启用</a>';
            }else{
                return  '<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-no\'">禁用</a>';
            }
        }

    }
    function formmatenabled1(value, row, index) {
        if (row.self.enabled == 1) {
            return '<a class="easyui-linkbutton " onclick="switchstatus1(' + row.id + ')" data-options="plain:true,iconCls:\'icon-ok\'">启用</a>';
        }else{
            return '<a class="easyui-linkbutton " onclick="switchstatus1(' + row.id + ')" data-options="plain:true,iconCls:\'icon-no\'">禁用</a>';
        }
    }
    function switchstatus1(id) {
        parent.layer.msg('确定切换么？', {
            time: 0, //不自动关闭
            btn: ['确定', '取消'],
            yes: function (index) {
                $.getJSON(contextPath + "/sysoperate/switch.do?id=" + id, function (data) {
                    if (data.state) {
                        parent.layer.msg('切换成功');
                        window.location.reload();//刷新当前页面.
                    } else {
                        parent.layer.msg('切换失败');
                    }
                });
            },
            no: function (index) {
                layer.close(index);
            }
        });
    }
    <%--function formmataction(value,row,index){--%>
    <%--var insert= new Array();--%>
    <%--if(typeof (row.self.optName)=='undefined'){//菜单--%>
    <%--&lt;%&ndash;<sec:authorize access="hasAnyRole('ROLE_sys_menu_edit','ROLE_admin')">&ndash;%&gt;--%>
    <%--&lt;%&ndash;insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-edit\'" onclick="editRow('+row.id+')">修 改</a> ');&ndash;%&gt;--%>
    <%--&lt;%&ndash;</sec:authorize>&ndash;%&gt;--%>
    <%--<sec:authorize access="hasAnyRole('ROLE_sys_menu_del','ROLE_admin')">--%>
    <%--insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-no\'" onclick="deleteRow(0,'+row.id+')">删除</a> ');--%>
    <%--//            insert.push( '<input  class="easyui-linkbutton" data-options="plain:true,iconCls:\'icon-no\'" onclick="deleteRow('+row.id+')" value="刪 除" >');--%>
    <%--</sec:authorize>--%>

    <%--}else{--%>
    <%--&lt;%&ndash;<sec:authorize access="hasAnyRole('ROLE_sys_operate_edit','ROLE_admin')">&ndash;%&gt;--%>
    <%--&lt;%&ndash;insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-edit\'" onclick="editRow('+row.id+')">修 改</a> ');&ndash;%&gt;--%>
    <%--&lt;%&ndash;</sec:authorize>&ndash;%&gt;--%>
    <%--<sec:authorize access="hasAnyRole('ROLE_sys_operate_del','ROLE_admin')">--%>
    <%--insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-no\'" onclick="deleteRow(1,'+row.id+')">删除</a> ');--%>
    <%--</sec:authorize>--%>

    <%--}--%>
    <%--return insert.join('');--%>
    <%--}--%>

</script>
</body>

</html>