<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/${type}/option.js"></script>

    <%--<link rel="stylesheet" type="text/css"--%>
    <%--href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/custom/uimaker/css/basic_info.css"/>--%>
    <title>资源占用流程</title>
    <style>
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <table id="dg" fit="true" class="easyui-datagrid"></table>
    <div id="toolbar">
        <div class="conditions">
            <span class="con-span">编码: </span><input class="easyui-textbox" type="text" name="code"
                                                     style="width:166px;height:35px;line-height:35px;"/>
            <span class="con-span">名称: </span><input class="easyui-textbox" type="text" name="name"
                                                     style="width:166px;height:35px;line-height:35px;"/>
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true">查询</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
            <a href="#" class="easyui-linkbutton more" iconCls="icon-more">更多</a>
        </div>
        <div class="opt-buttons">
            <a href="#" class="easyui-linkbutton" data-options="selected:true">新增</a>
            <a href="#" class="easyui-linkbutton">发布</a>
            <a href="#" class="easyui-linkbutton">取消发布</a>
            <a href="#" class="easyui-linkbutton">标记为执行完毕</a>
            <a href="#" class="easyui-linkbutton">导出</a>
        </div>
    </div>
</div>
<script type="text/javascript">

</script>
</body>
</html>