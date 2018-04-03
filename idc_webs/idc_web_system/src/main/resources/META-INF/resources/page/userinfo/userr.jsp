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
    <title>用户信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="../header.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/userinfo/userinfo.js"></script>
    <script type="text/javascript" language="JavaScript">
        var baseURL = "<%=request.getContextPath() %>";
        $().ready(function(){
            var znoe =[
                { name:"全国", open:true,
                    children: [
                        { name:"四川", open:true,
                            children: [
                                { name:"成都", open:true,
                                    children: [
                                        { name:"武侯区", open:true },
                                        { name:"金牛区", open:true }
                                    ]
                                },
                                { name:"绵阳"},
                                { name:"自贡"},
                                { name:"攀枝花"}
                            ]}
                    ]
                }
            ];
            $.fn.zTree.init($("#treeregoin"), {},znoe);
        });
    </script>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',width:200,border:true" title="导航信息">
        <ul id="treeregoin" class="ztree" style="margin-top:0; width:200px;"></ul>
    </div>
    <div data-options="region:'center',border:false" title=">>用户信息" style="padding-left: 2px;">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'north',height:30,border:true">
                <%--<span style="float: left">过滤条件：</span>--%>

                <form id="fmSearch">
                     <span style="float: left">过滤条件：
                    用户名<input type="text" name="username"/>
                    <%--年龄<input type="text" name="UAge"/>--%>
                    <a class="easyui-linkbutton" id="a_search">查询</a></span>
                </form>
            </div>
            <div data-options="region:'center',border:false" style="padding-left: 2px;">

                <table id="dg"></table>

            </div>
        </div>

    </div>
</div>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/framework/hplusV4.1.0/js/plugins/layer/layer.min.js"></script>
</body>
<div id="tb">
    <sec:authorize access="hasRole('ROLE_sys_userinfo_add')">
        <a href="#" class="easyui-linkbutton sys_userinfo_add" plain="true" icon="icon-add">新增</a>
        <a data-toggle="modal" href="${contextPath}/userinfo/form.do" data-target="#myModal5">Click me</a>
        <div class="datagrid-btn-separator"></div>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_sys_userinfo_edit')">
        <a href="#" class="easyui-linkbutton" plain="true" icon="icon-save">编辑</a>

        <div class="datagrid-btn-separator"></div>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_sys_userinfo_del')">
        <a href="#" class="easyui-linkbutton" plain="true"
           icon="icon-remove">删除</a>

        <div class="datagrid-btn-separator"></div>
    </sec:authorize>

</div>

<div id="edit_window" class="easyui-dialog" fit="true" closed="true" modal="true" title="" style="width:800px;height:400px;">
    <iframe scrolling="auto" id='edit_iframe' name="edit_iframe" frameborder="0" src="" style="width:100%;height:98%;">
    </iframe>
</div>
<div class="modal inmodal fade" id="myModal5" tabindex="-1" role="dialog"  aria-hidden="true"></div>
<div class="modal fade" id="NoPermissionModal">
    <div class="modal-dialog" >
        <div class="modal-content">
            <div class="modal-header">
                <%-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>--%>
                <button type="button" class="close" onclick="window.history.go(-1);">×</button>
                <h4 class="modal-title" id="NoPermissionModalLabel">用户编辑</h4>
            </div>
            <div class="modal-body">
                <iframe id="NoPermissioniframe" width="100%" height="100%" frameborder="0"></iframe>
            </div>
            <div class="modal-footer">
                <button class="btn btn-default"  type="button" onclick="window.history.go(-1);" >关闭</button>
            </div>
        </div>
    </div>
</div>

</html>