<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
<link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/import_skin.css" rel="stylesheet"
      type="text/css" id="skin" themeColor="${skin}"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_page.css" rel="stylesheet"
      type="text/css"/>
<script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/js/echart/custom.theme.js"></script>

<script src="<%=request.getContextPath() %>/framework/layui/layui.js"></script>
<link rel="stylesheet"
      href="<%=request.getContextPath() %>/framework/layui/css/layui.css"/>
<style>
    body {
        background-color: #fff;
    }
</style>
</head>
<body>
<div class="easyui-panel" fit="true" style="overflow-x: hidden">
    <table class="easyui-datagrid" data-options="fit:true,fitColumns:true">
        <thead>
        <tr>
            <th data-options="field:'code',width:200">存储序列</th>
            <th data-options="field:'name',width:200">大小(GB)</th>
            <th data-options="field:'used',width:200">占用(GB)</th>
            <th data-options="field:'free',width:200">空闲(GB)</th>
            <th data-options="field:'freeur',width:200">空闲率(%)</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>001</td>
            <td>1000</td>
            <td>200</td>
            <td>800</td>
            <td>80</td>
        </tr>
        <tr>
            <td>002</td>
            <td>3000</td>
            <td>1000</td>
            <td>2000</td>
            <td>66</td>
        </tr>
        <tr>
            <td>003</td>
            <td>2000</td>
            <td>400</td>
            <td>1600</td>
            <td>75</td>
        </tr>
        </tbody>
    </table>
</div>
<script>
    //一般直接写在一个js文件中
    layui.use(['layer', 'element'], function () {
        var element = layui.element;
    });
</script>
</body>
</html>