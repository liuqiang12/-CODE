<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <!-- 引用 -->
    <script type="text/javascript"  src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <title></title>
    <!-- 左右布局 -->
    <script type="text/javascript">
        $(function () {
            var dom = $("#rtree");
            if (dom != null) {
                dom.rtree({
                    r_type: 2,
                    onClick: function (iteam,treeId,treeNode) {
                        var id = treeNode.id;
                        var params = getRequestServerParams();
                        params['nodeParam'] = id;
                        //调用查询方法
                        loadServerGrid("server_gridId",params);
                    }
                })
            }
        })
    </script>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="serverLayout" class="easyui-layout" fit="true">
        <div data-options="region:'west',iconCls:'icon-reload',split:true" style="width:300px;">
            <ul id="rtree" class="ztree" style="width:300px; overflow:auto;"></ul>
        </div>
        <div data-options="region:'center'" style="padding:5px;background:#eee;">
            <div style="padding: 5px;" id="requestParamSettins_gridId">
                主机名称: <input class="easyui-textbox"  id="serverDeviceName_params" style="width:100px;text-align: left;" data-options="">
                <a href="javascript:void(0);" onclick="loadServerGrid('server_gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
            <table class="easyui-datagrid" id="server_gridId"></table>
        </div>
    </div>
</div>
</body>
<script src="<%=request.getContextPath() %>/js/jbpm/win/openServerResourceQuery.js"></script>
</html>