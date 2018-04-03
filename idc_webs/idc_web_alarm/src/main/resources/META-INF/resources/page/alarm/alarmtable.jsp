<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>首屏</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
<style>
    /*.btn {*/
        /*padding: 2px 5px;*/
        /*margin: 2px;*/
    /*}*/
</style>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <table class="easyui-datagrid" title="地市资源信息" data-options="fit:true,rownumbers:true,pagination:false,striped:true">
        <thead data-options="frozen:true">
                <th data-options="field:'regionname',width:100">告警级别</th>
                <th data-options="field:'allbuild',width:100">告警类别</th>
                <th data-options="field:'alarmObj',width:150">告警对象</th>
        </thead>
        <thead>
        <tr>
            <th data-options="field:'alarminfo',width:300,align:''">告警内容</th>
            <th data-options="field:'alarmtime',width:100,align:''">告警时间</th>
            <th data-options="field:'alarmsendflag',width:100,align:''">发送状态</th>
            <th data-options="field:'alarmsendtime',width:100,align:''">发送时间</th>
        </tr>
        </thead>
       <tbody>
       <c:if test="${not empty alarms}">
           <c:forEach var="alarm" items="${alarms}">
                <tr>
                    <td>
                        <c:if test="${alarm.alarmlevel eq 0}">一般</c:if>
                        <c:if test="${alarm.alarmlevel eq 1}">重要</c:if>
                        <c:if test="${alarm.alarmlevel eq 2}">严重</c:if>
                    </td>
                    <td>
                        <c:if test="${alarm.alarmtype eq 0}">采集器告警</c:if>
                        <c:if test="${alarm.alarmtype eq 1}">机房用能突变告警</c:if>
                        <c:if test="${alarm.alarmtype eq 2}">PUE告警</c:if>
                        <c:if test="${alarm.alarmtype eq 3}">机楼用能突变告警</c:if>
                    </td>
                    <td>${alarm.alarmobjstr}</td>
                    <td>${alarm.alarminfo}</td>
                    <td> <fmt:formatDate value="${alarm.alarmtime}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>
                        <c:if test="${alarm.alarmsendflag eq 0}">未发送</c:if>
                        <c:if test="${alarm.alarmsendflag eq 1}">已经发送</c:if>
                        <c:if test="${alarm.alarmsendflag eq 2}">发送失败</c:if>
                    </td>
                    <td>
                        <c:if test="${alarm.alarmsendflag ne 0}">
                            <fmt:formatDate value="${alarm.alarmsendtime}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>
                        </c:if>
                </tr>
           </c:forEach>
       </c:if>
       <%--<tr>--%>
           <%--<td>轻微</td>--%>
           <%--<td>机房用能突变告警</td>--%>
           <%--<td>昆明五华区枢纽楼2层网管支撑机房</td>--%>
           <%--<td>昆明五华区枢纽楼2层网管支撑机房</td>--%>
           <%--<td>2016-11-12 00：00：00</td>--%>
           <%--<td>未发送</td>--%>
           <%--<td></td>--%>
       <%--</tr>--%>
       <%--<tr>--%>
           <%--<td>严重</td>--%>
           <%--<td>机房用能突变告警</td>--%>
           <%--<td>昆明五华区枢纽楼3层网管支撑机房</td>--%>
           <%--<td>昆明五华区枢纽楼3层网管支撑机房</td>--%>
           <%--<td>2016-11-13 00：00：00</td>--%>
           <%--<td>未发送</td>--%>
           <%--<td></td>--%>
       <%--</tr>--%>
       </tbody>
    </table>

</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/global/plugins/tree.js"></script>

<script type="text/javascript" language="JavaScript">

</script>
</body>
</html>