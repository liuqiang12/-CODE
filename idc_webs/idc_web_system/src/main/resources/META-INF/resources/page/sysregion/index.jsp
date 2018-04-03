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
    <title>区域管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <%--
    <div data-options="region:'west',width:240,border:true" title="导航信息">
        <ul id="treeregoin" class="ztree" style="margin-top:0; width:240px;"></ul>
    </div>
    --%>
    <div class="easyui-layout" fit="true">
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="table" class="easyui-treegrid"
                   data-options="fit:true,rownumbers:true,singleSelect:true,
                           idField:'id', treeField: 'name',
                           lines: true,loadFilter:loadFilter,onLoadSuccess:loadsuccess,
                           <%--pagination:false,pageSize:15,pageList:[10,15,20,50],striped:true,--%>
                           fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/sysregion/regionTree',  ">
                <thead>
                <tr>
                    <th data-options="field:'name',width:$(this).width()*0.1">名称</th>
                    <th data-options="field:'self.code',width:$(this).width()*0.1,align:''">编码</th>
                    <th data-options="field:'self.remark',width:$(this).width()*0.3,align:''">备注</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
    <div id="toolbar" class="paramContent">
        <%--<div class="param-fieldset">--%>
        <%--<input name="regionName" id="regionName" class="param-input" type="input" placeholder="名称"--%>
        <%--style="margin-right: 5px">--%>
        <%--<input name="region_pid" class="form-control" type="hidden" placeholder="">--%>
        <%--</div>--%>
        <%--<div class="btn-cls-search" id="ok"></div>--%>
        <%--<div style="float: left">--%>
        <%--<input name="regionName" id="regionName" class="easyui-textbox" type="text"  type="text" prompt="名称">--%>
        <%--<input name="region_pid" class="form-control" type="hidden" placeholder="">--%>
        <%--&lt;%&ndash;<a id="ok" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&ndash;%&gt;--%>
        <%--<button id="ok">查 询</button>--%>
        <%--</div>--%>
    </div>
</div>
<%--<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree.all-3.5.min.js"></script>--%>
<%--<link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">--%>
<%--<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/global/plugins/tree.js"></script>--%>

<script type="text/javascript" language="JavaScript">
    //    $(document).ready(function () {
    //        initTree()
    //
    //    });
    //    function initTree(){
    //        $("#treeregoin").regionTree({
    //            isShowOrg:true,
    //            callback:{
    //                onAsyncSuccess:function(event, treeId, treeNode, msg){
    //
    //                }
    //            }
    //        },queryByPid);
    //    }
    var $table = $('#table'),
        $ok = $('#ok');

    function queryByPid(event, treeId, treeNode){
        if(treeNode.children&&treeNode.children.length>0){

        }
        console.log(treeNode)
        $('#regionName').textbox("clear");

        if(treeNode.parentId==null){
            $('#toolbar').find("input[name='region_pid']").val("");
        }else{
            $('#toolbar').find("input[name='region_pid']").val(treeNode.id);
        }
        refreshTable();
    }
    function refreshTable(){

//        var paramstmp = {};
//        $('#toolbar').find('input[name]').each(function () {
//            paramstmp[$(this).attr('name')] = $(this).val();
//        });
//        $table.datagrid('options').queryParams=paramstmp;
        $table.treegrid('reload');
    }
    $(function () {
        $ok.on('click',function () {
            var regionName = $("#regionName").val();
            alert("查询==" + regionName);
            <%--$("#table").treegrid({--%>
            <%--url:"<%=request.getContextPath() %>/sysregion/regionTree",--%>
            <%--queryParams:{name:regionName}--%>
            <%--});--%>
        });

    });
    function queryParams(params) {
        console.log(params)
        var paramstmp = {};
        $('#toolbar').find('input[name]').each(function () {
            paramstmp[$(this).attr('name')] = $(this).val();
        });
        $.extend(true, params,paramstmp)
        return params;
    }
    function formmatenabled(value,row,index){
        if(value){
            return '<button  class="btn btn-info">启用</button>'
        }else{
            return '<button  class="btn btn-danger">已禁用</button>'
        }
    }
//    function editRow(userid){
//        var  url = baseURL + "/userinfo/form.do";
//        if(typeof(userid)!='undefined'){
//            url+="?userid="+userid;
//        }
//        top.layer.open({
//            type : 2,
//            title : '用户信息',
//            fix : false,
//            closeBtn:1,
//            maxmin : true,
//            area : [ '1000px', '600px' ],
//            content :url
//        });
//    }

    /***
     * 查询功能的实现直接本地过滤数据
     * @param data
     */
    function loadFilter(data){
//        var regionName = $("#regionName").textbox("getValue");
//        var region_pid = $("input[name='region_pid']").val();
//        var datas = new Array();
//        $.each(data.rows,function(i,iteam){
//            if(region_pid==''||iteam._parentId==region_pid){
//                if(regionName==''||iteam.name.indexOf(regionName)>-1){
//                    if(region_pid!=''||regionName!=''){
//                        iteam._parentId=undefined
//                    }
//                    datas.push(iteam);
//                }
//            }
//        });
//        data.rows = datas;
        return data;
    }
    function loadsuccess(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight')
    }
</script>
</body>
</html>