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
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer.css" rel="stylesheet"
      type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/swipers.css" rel="stylesheet"
      type="text/css"/>

<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/normalize.css"
      rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/main.css"
      rel="stylesheet" type="text/css"/>

<link rel="stylesheet"
      href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/bootstrap-switch.css"/>

<!--
<script src="<%=request.getContextPath() %>/framework/themes/js/jquery-1.4.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/plugins/jquery/jquery-1.10.2.min.js"></script>

-->
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/jquery.jqDock.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/fisheye-iutil.min.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/js/echart/custom.theme.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/js/swipers.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/jquery.autocompleter.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/main.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/bootstrap-switch.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/highlight.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/main.js"></script>
<script src="<%=request.getContextPath() %>/framework/layui/layui.js"></script>
<link rel="stylesheet"
      href="<%=request.getContextPath() %>/framework/layui/css/layui.css"/>
<style>
    body {
        background-color: #fff;
    }

    /*fieldset{*/
    /*border-color: blue;*/
    /*}*/
    /*legend{*/
    /*color:blue*/
    /*}*/
    .layui-table {
        margin-top: 0;
        border-left: 5px solid #e2e2e2;
    }
    .layui-elem-quote {
        line-height: 26px;
        position: relative;
        border-left: 5px solid rgb(0, 167, 238);
        background-color: rgb(0, 167, 238);
    }
    .layui-elem-quote.title {
        padding: 10px 15px;
        margin-bottom: 0;
        font-size: 20px;
        font-weight: bold;
        color: #fff;
    }
    .layui-table{
        background-color: rgb(211,213,14);
    }
    tr td:last-child{
        border-right: 5px solid rgb(0, 167, 238);
    }
    td{
        word-wrap: break-word;
        color: #fff;
    }

    .layui-table tbody tr:hover, .layui-table thead tr, .layui-table-click, .layui-table-header, .layui-table-hover, .layui-table-mend, .layui-table-patch, .layui-table-tool, .layui-table[lay-even] tr:nth-child(even) {
        background-color: rgb(211,213,14);
    }
</style>
</head>
<body>


<div class="easyui-panel" fit="true" style="overflow-x: hidden">
    <div class="layui-row layui-col-space5">
        <div class="layui-col-xs12 layui-col-sm6 layui-col-md4" style="margin-top:5px; ">
            <blockquote class="layui-elem-quote title">学校</blockquote>
            <table class="layui-table" lay-skin="row" lay-size="sm" style="border: 1px solid rgb(0,167,238);">
                <tbody>
                <tr>
                    <td>硬盘：</td>
                    <td>CPU：</td>
                    <td>内存：</td>
                    <td>虚拟机：</td>
                </tr>
                <tr>
                    <td>总量:2000GB</td>
                    <td>核数:32</td>
                    <td>总量:256GB</td>
                    <td>10个</td>
                </tr>
                <tr>
                    <td>占用:200GB</td>
                    <td>使用率:32%</td>
                    <td>占用:32GB</td>
                    <td></td>
                </tr>
                <tr>
                    <td>空闲率:90%</td>
                    <td></td>
                    <td>空闲率:87.5%</td>
                    <td></td>
                </tr>
                </tbody>
            </table>

        </div>

    </div>
</div>
<script>
    //一般直接写在一个js文件中
    layui.use(['layer', 'element'], function () {
        var element = layui.element;
    });
</script>
</body>
</html>