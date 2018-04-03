<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/physicshost/option.js"></script>
    <title>资源池</title>
    <style>
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="cc" class="easyui-layout" fit="true">
        <%--<div class="content_suspend">--%>
            <%--<div class="conter">--%>
                <%--<ul id="rtree" class="ztree" style="width:300px; overflow:auto;"></ul>--%>
            <%--</div>--%>
            <%--<div class="hoverbtn">--%>
                <%--<span>资</span><span>源</span><span>信</span><span>息</span>--%>

                <%--<div class="hoverimg" height="9" width="13"></div>--%>
            <%--</div>--%>
        <%--</div>--%>
        <div data-options="region:'center'" style="background:#eee;">
            <table id="dg" fit="true" class="easyui-datagrid"></table>
        </div>
    </div>
    <div id="toolbar" class="paramContent">
        <%--<div class="param-fieldset">--%>
            <%--<input type="input" id="buiName" class="param-input" placeholder="${searchStr}"/>--%>
        <%--</div>--%>
        <%--<div class="btn-cls-search" onClick="searchModels();"></div>--%>
        <%--<div class="btn-cls-reset" onClick="javascript:$('#buiName').val('')">--%>

        <%--</div>--%>

        <div class="param-actionset">
            <div class="btn-cls-common" id="add">新 增</div>
            <div class="btn-cls-common" id="edit">修 改</div>
            <div class="btn-cls-common" id="del">删 除</div>
            <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_physicshost_add','ROLE_admin')">--%>
            <%--<div class="btn-cls-common" id="add">新 增</div>--%>
            <%--</sec:authorize>--%>
            <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_physicshost_edit','ROLE_admin')">--%>
            <%--<div class="btn-cls-common" id="edit">修 改</div>--%>
            <%--</sec:authorize>--%>
            <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_physicshost_del','ROLE_admin')">--%>
            <%--<div class="btn-cls-common" id="del">删 除</div>--%>
            <%--</sec:authorize>--%>
        </div>
    </div>
</div>
<script type="text/javascript">

</script>
</body>
</html>