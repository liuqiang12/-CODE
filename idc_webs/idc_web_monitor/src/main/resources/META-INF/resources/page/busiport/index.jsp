<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/busiport/option.js"></script>
    <title>资源占用流程</title>
    <style type="text/css">
        .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="cc" class="easyui-layout" fit="true">
        <div data-options="region:'west'" style="width:200px;background:#eee;">
            <ul class="ztree" id="treeDemo"></ul>
        </div>
        <div data-options="region:'center'" style="background:#eee;">
            <table id="dg" fit="true" class="easyui-datagrid"></table>
        </div>
    </div>
    <div id="toolbar" class="paramContent">
        <div class="param-fieldset">
            <div id="groupbox" style="width: 200px"> </div>
            <%--<select id="groupbox" class="easyui-combobox" style="width: 200px" data-options="formatter: formatItem">--%>
                <%--<option value="1">分组1</option>--%>
                <%--<option value="2">分组2</option>--%>
                <%--<option value="3">分组1</option>--%>
            <%--</select>--%>
        </div>
        <div class="param-fieldset">
            <input type="input" id="buiName" class="param-input" placeholder="关键字"/>
        </div>
        <div class="btn-cls-search" onClick="searchModels();"></div>
        <div class="param-actionset">
            <div class="btn-cls-common" id="add">新 增</div>
            <div class="btn-cls-common" id="edit">修 改</div>
            <div class="btn-cls-common" id="del">删 除</div>
            <%--<div class="btn-cls-common" id="addgroup">添加分组</div>--%>
                <%--<div class="btn-cls-add" id="add"></div>--%>
                <%--<div class="btn-cls-edit" id="edit"></div>--%>
                <%--<div class="btn-cls-delete" id="del"></div>--%>
        </div>
        <%--<div class="btn-cls-reset" onClick="javascript:$('#buiName').val('')"></div>--%>
    </div>
</div>
<div id="mm" class="easyui-menu" data-options="onClick:menuHandler" style="width:120px;">
    <div data-options="name:'new'">添加</div>
    <div data-options="name:'edit'">修改</div>
    <div data-options="name:'del'">删除</div>
</div>
<script type="text/javascript">

</script>
<script type="text/javascript">
//    function searchModels() {
////        var name = $("#buiName").val();
////        var rackType = $('#rackType').combobox('getValue')
////        var queryParams = {SearchKey: name,businesstypeId:rackType};
////        if (typeof CurrNode!='undefined' && CurrNode!= null) {
////            var id = CurrNode.id;
////            var datas = id.split("_");
////            queryParams.roomid = datas[1]
////        }
////        $('#dg').datagrid({
////            url:contextPath + "/idcRack/list.do",
////            queryParams: queryParams
////        });
//    }
</script>
</body>
</html>