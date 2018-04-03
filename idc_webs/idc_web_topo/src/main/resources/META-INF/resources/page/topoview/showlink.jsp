<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/themes/css/form.css"/>
    <title>性能监视</title>

</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',border:true" style="padding-left:2px;overflow:hidden;">
        <div class="easyui-tabs easyui-tabs1" style="width:100%;height:100%;">
            <div title="常规信息" data-options="closable:false,border:true" style="padding:5px">
                <div class="easyui-layout" fit="true">
                    <div data-options="region:'center',border:true">
                        <table id="dg" class="easyui-datagrid" style="width:100%" fit="true"
                               title="端口流量"
                               data-options="
                            rownumbers:true,
                            singleSelect:false,
                            autoRowHeight:false,
                            fitColumns:true,
                            striped:true,
                            checkOnSelect:false,
                            selectOnCheck:false,
                            remoteSort:false
                            ">
                            <thead>
                            <tr>
                                <th field="srcportName" width="300">本端端口</th>
                                <th field="desportName" width="300">对端端口</th>
                                <th field="inflow" width="110">上行流量</th>
                                <th field="outflow" width="112">下行流量</th>
                                <th field="active" width="70">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="link" items="${links}">
                                <tr>
                                    <td>${link.srcportname}</td>
                                    <td>${link.desportname}</td>

                                    <td>
                                        <fmt:formatNumber value="${link.inflowMbps}" pattern="#0.0000"/>Mbps
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${link.inflow>1024*1024}">--%>
                                                <%--<fmt:formatNumber value="${link.inflowMbps}" pattern="#0.0000"/>MB--%>
                                            <%--</c:when>--%>
                                            <%--<c:when test="${link.inflow>1024}">--%>
                                                <%--<fmt:formatNumber value="${link.inflowMbps}" pattern="#0.00"/>--%>
                                                <%--KB--%>
                                            <%--</c:when>--%>
                                            <%--<c:when test="${link.inflow<1024}">--%>
                                                <%--<fmt:formatNumber value="${link.inflow}" pattern="#0.00"/>--%>
                                                <%--Byte--%>
                                            <%--</c:when>--%>
                                        <%--</c:choose>--%>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${link.outflowMbps}" pattern="#0.0000"/>Mbps
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${link.outflow>1024*1024}">--%>
                                                <%--<fmt:formatNumber value="${link.outflow/1024/1024}" pattern="#0.00"/>MB--%>
                                            <%--</c:when>--%>
                                            <%--<c:when test="${link.outflow>1024}">--%>
                                                <%--<fmt:formatNumber value="${link.outflow/1024}" pattern="#0.00"/>--%>
                                                <%--KB--%>
                                            <%--</c:when>--%>
                                            <%--<c:when test="${link.outflow<1024}">--%>
                                                <%--<fmt:formatNumber value="${link.outflow}" pattern="#0.00"/>--%>
                                                <%--Byte--%>
                                            <%--</c:when>--%>
                                        <%--</c:choose>--%>
                                    </td>
                                    <td>
                                        <a href="javasscript:void(0)" onclick="deleteRow('${link.topoLinkID}','${link.desportname}')">删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function deleteRow(srcportname,desportname){
        top.layer.msg('确定删除这条链路关系？', {
            time: 0 //不自动关闭
            ,btn: ['删除', '取消']
            ,yes: function(index){
                $.post(contextPath+"/topoview/deletelink",{srcportname:srcportname,desportname:desportname},function(data){
                    top.layer.close(index)
                    if(data.state){
                        var parentWin = top.winref[window.name];
                        top[parentWin].deleteLink(data.msg);
//                        var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//                        top.layer.close(index); //再执行关闭
                       location=location;
                    }else{
                        top.layer.msg(data.msg)
                    }
                })
            }
        });
    }
</script>
</body>
</html>