<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/globalstatic/easyui/head.jsp"%>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <title>IDC管理平台</title>
    <%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/default/easyui.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/layer/skin/layer.css">--%>
    <%--<script type="text/javascript" src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/jquery.min.js"></script>--%>
    <%--<script type="text/javascript" src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/jquery.easyui.min.js"></script>--%>
    <%--<script type="text/javascript" src="<%=request.getContextPath() %>/framework/layer/layer.js"></script>--%>
    <script type="text/javascript" language="JavaScript">
        function opent(){
            top.layer.open({
                type: 2,
                area: ['400px', '130px'],
                fixed: false, //不固定
                maxmin: true,
                content: '/main/test2.do'
            });
        }
        function opent1(){
            $('#win').window('open');
        }
    </script>
</head>

<body style="width: 300px;height: 200px">
<div id="cc" class="easyui-layout" style="width: 300px;height: 200px">
    <div id="win" class="easyui-window" title="My Window" style="width:440px;height:100px"
         data-options="iconCls:'icon-save',modal:false,closed:true">
         <iframe src="/main/test2.do" style="width: 400px;height: 100px"></iframe>
    </div>
    <div data-options="region:'center',title:'center title'" fit="true" style="padding:5px;background:red;">
        <a href="#" onclick="opent()">111</a>
        <a href="#" onclick="opent1()">222</a>
      <div onclick="opent">dfdfdf</div>
    </div>
</div>
</body>

</html>
