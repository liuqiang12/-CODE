<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <title></title>
    <style>
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <table class="kv-table">
        <tbody>
        <tr>
            <td class="kv-label">分组名称</td>
            <td class="kv-content">
                <input type="hidden" id="id" value="${id}"/>
                <input class="easyui-textbox" data-options="width:250,required:true" id="groupname"
                       value="${name}"/>
            </td>

        </tr>
        <tr>
            <td class="kv-label">上级分组</td>
            <td class="kv-content">
                <input class="easyui-combotree" id="parentid" style="width:250px" data-options="
                          panelHeight:150,
                          url: '${contextPath}/busiPort/group/list',
                          onShowPanel:function(){
                            var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            top.layer.style(index, {
                                                height: '300px'
                                                });
                          },
                          onHidePanel:function(){
                             var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                              top.layer.style(index, {
                                                height: '200px'
                                                });
                          },
                          valueField: 'parentId',
                          textField: 'name'
                        ">
            </td>
        </tr>
        </tbody>
    </table>
    <%--<div id="cc" class="easyui-layout" fit="true">--%>
    <%--<div data-options="region:'center'">--%>
    <%----%>
    <%--</div>--%>
    <%--</div>--%>
</div>
<script type="text/javascript">
    function doSubmit() {
        var id = $("#id").val();
        var groupname =$("#groupname").textbox("getValue");
        var parentid = $("#parentid").combobox("getValue");
        $.post(contextPath+"/busiPort/group/save",{
            id:id
            ,name:groupname
            ,parentId:parentid
        },function(data){

        })
    }
</script>
</body>
</html>