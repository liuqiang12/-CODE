<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',iconCls:'icon-ok'" style="width: 50%" title="源端端口">
        <table id="tablea" class="easyui-datagrid"
               data-options="fit:true,rownumbers:true,pagination:false,
                   pageSize:15,pageList:[10,15,20,50],striped:true,singleSelect:true,
                   fitColumns:true,toolbar:'#toolbara',url:'<%=request.getContextPath() %>/topoview/ports/${nodea.objfid}'">
            <thead>
            <tr>
                <th data-options="field:'PORTID',hidden:'true'"></th>
                <th data-options="field:'PORTNAME',width:200">端口名称</th>
                <th data-options="field:'ALIAS',width:300">别名</th>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center',iconCls:'icon-ok'"title="目的端口">
        <table id="tablez" class="easyui-datagrid"
               data-options="fit:true,rownumbers:true,pagination:false,singleSelect:true,
                   pageSize:15,pageList:[10,15,20,50],striped:true,
                   fitColumns:true,toolbar:'#toolbarz',url:'<%=request.getContextPath() %>/topoview/ports/${nodez.objfid}'">
            <thead>
            <tr>
                <th data-options="field:'PORTID',hidden:'true'"></th>
                <th data-options="field:'PORTNAME',width:200">端口名称</th>
                <th data-options="field:'ALIAS',width:300">别名</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="toolbara" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiNamea" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('a');"></div>
</div>
<div id="toolbarz" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiNamez" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('z');"></div>
</div>
<script type="text/javascript">
    function searchModels(type){
        var name = $("#buiName"+type).val();
        $('#table'+type).datagrid({
            queryParams:{name:name}
        });
    }
    $(function(){
//
//        $("#add").click(function(){
//            $("#table").datagrid('appendRow', {
//                src:'',
//                des:''
//            });
////            $("#table").datagrid('beginEdit', index);
////            var ed = $("#table").datagrid('getEditor', {index:index,field:field});
////            $(ed.target).focus();
//        })

    })
//    function enableEditor(index,field,value){
//        $("#table").datagrid('beginEdit', index);
//        var ed = $("#table").datagrid('getEditor', {index:index,field:field});
//        $(ed.target).focus();
//
//    }
    function doSubmit(fn) {
        var selectA = $("#tablea").datagrid("getSelected");
        var selectZ = $("#tablez").datagrid("getSelected");
        if(selectA==null||selectZ==null){
            top.layer.msg("两端必须选择");
            return ;
        }
        var sendData = {
            viewid:'${viewId}',
            srcdeviceid:'${nodea.objfid}',
            desdeviceid:'${nodez.objfid}',
            srcid:${nodea.objpid},
            desid:${nodez.objpid},
            srcportname:selectA.PORTNAME,
            desportname:selectZ.PORTNAME,
        }
        $.post(contextPath+"/topoview/addLink",sendData,function(data){
            if (data.state) {
                top.layer.msg("添加链接成功")
                fn(data.msg);
                var parentWin = top.winref[window.name];
                var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(index); //再执行关闭
            } else {
                top.layer.msg(data.msg)
            }
        })
        <%--var objtype = $("#objtype").combobox("getValue");--%>
        <%--var objfid = $("#objfid").combogrid("getValue");--%>
        <%--var objfidText = $("#objfid").combogrid("getText");--%>
        <%--$('#viewForm').form('submit', {--%>
            <%--url: "<%=request.getContextPath() %>/topoview/addObj",--%>
            <%--success: function (data) {--%>
                <%--if (data.state) {--%>
                    <%--top.layer.msg("添加链接成功")--%>
                    <%--fn(data.msg);--%>
                    <%--var parentWin = top.winref[window.name];--%>
                    <%--var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引--%>
                    <%--top.layer.close(index); //再执行关闭--%>
                <%--} else {--%>
                    <%--top.layer.msg(data.msg)--%>
                <%--}--%>
            <%--}--%>
        <%--});--%>
    }
</script>
</body>
</html>