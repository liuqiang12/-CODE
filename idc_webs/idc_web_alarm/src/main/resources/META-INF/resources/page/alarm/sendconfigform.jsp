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
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
</head>
<body>
<div class="easyui-panel" title=""  border="false" style="width:100%;padding:0px 0px;">
    <form method="post" id="signupForm">
        <table class="kv-table">
            <tbody>
            <tr>
                <td class="kv-label">告警类别</td>
                <td class="kv-content">
                    <input type="hidden" name="id" value="${alarmconfigsend.id}"/>
                    <select class="easyui-combobox" name="kpiid" style="width: 200px" value="${alarmconfigsend.kpiid}"
                            data-options="panelHeight:150">
                        <c:forEach items="${kpis}" var="kpi">
                            <option value="${kpi.kpiid}" <c:if test="${alarmconfigsend.kpiid==kpi.kpiid}">selected</c:if>>${kpi.kpiname}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">告警等级</td>
                <td class="kv-content">
                    <select class="easyui-combobox" name="alarmlevel" data-options="panelHeight:100">
                        <option value="0" <c:if test="${alarmconfigsend.alarmlevel eq 0}">selected</c:if>>轻微</option>
                        <option value="1" <c:if test="${alarmconfigsend.alarmlevel eq 1}">selected</c:if>>一般</option>
                        <option value="2" <c:if test="${alarmconfigsend.alarmlevel eq 2}">selected</c:if>>严重</option>
                        <option value="3" <c:if test="${alarmconfigsend.alarmlevel eq 3}">selected</c:if>>关键</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">提醒方式</td>
                <td class="kv-content">
                    <select class="easyui-combobox" name="alarmmode" data-options="panelHeight:100">
                        <option value="0" <c:if test="${alarmconfigsend.alarmmode eq 0}">selected</c:if>>不做任何处理</option>
                        <option value="1" <c:if test="${alarmconfigsend.alarmmode eq 1}">selected</c:if>>声音</option>
                        <option value="2" <c:if test="${alarmconfigsend.alarmmode eq 2}">selected</c:if>>短信</option>
                        <option value="3" <c:if test="${alarmconfigsend.alarmmode eq 3}">selected</c:if>>邮件</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">发送地址</td>
                <td class="kv-content">
                    <input class="easyui-textbox" value="${alarmconfigsend.alarmaddress}" name="alarmaddress"  />
                </td>
            </tr>

            </tbody>
        </table>
        <%--<div style="text-align:center;padding:0px 0">--%>
            <%--&lt;%&ndash;<input class='submit' type='submit'class="easyui-linkbutton" iconCls="icon-save" value='提交'>&ndash;%&gt;--%>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton" id="submit" iconCls="icon-save"  style="width:80px">保存</a>--%>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()" iconCls="icon-cancel"  style="width:80px">关闭</a>--%>
        <%--</div>--%>
    </form>
</div>
<%--<script src="<%=request.getContextPath() %>/framework/jeasyui/plugins/jquery.form.js"></script>--%>
<%--<script src="<%=request.getContextPath() %>/framework/jeasyui/validationPlus.js"></script>--%>
<%--<script type="text/javascript"  src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>--%>
<script>
//    function closeWin(){
//        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//         parent.layer.close(index); //再执行关闭
//    }
    function doSubmit(fn){
        var flag = $("#signupForm").form('validate');
        if(flag){
            $.post(contextPath+"/alarm/sendconfigsave.do",$("#signupForm").serialize(),function(data){
                if(data.state){
                    layer.msg('保存成功');
                    if(fn&&typeof(fn)=="function"){
                        fn();
                    }
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                }else{
                    layer.msg(data.msg);
                }
            });
        }
    }
//    $(document).ready(function () {
//        $("#submit").on('click',function(){
//            $("#signupForm").form('submit', {
//                onSubmit: function () {
//                    var flag = $(this).form('validate');
//                    if(flag){
//                        $.post(contextPath+"/alarm/sendconfigsave.do",$(this).serialize(),function(data){
//                            if(data.state){
//                                layer.msg('保存成功');
//                                parent.document.getElementById("maincontent").contentWindow.refreshalarmsendtable();
//                                closeWin();
//                            }else{
//                                layer.msg(data.msg);
//                            }
//                        });
//                    }
//                    return false;
//                }
//            });
//        });
//    });
</script>
</body>
</html>