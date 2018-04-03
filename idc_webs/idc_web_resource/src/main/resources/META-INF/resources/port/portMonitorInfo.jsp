<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <title>资源占用流程</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',title:'资源树',width:230,border:true,headerCls:'panel-background'"
         style="padding:2px;">
        <div id="treeMenu_device" class="easyui-panel" data-options="fit:true,border:false"
             style="overflow: auto;"></div>
    </div>
    <div data-options="region:'center',border:false" style="padding-left:2px;overflow:hidden;">
        <div class="easyui-panel" border="false" fit="true" id="showAccessUserListTabel">111</div>
    </div>
</div>
</body>
</html>