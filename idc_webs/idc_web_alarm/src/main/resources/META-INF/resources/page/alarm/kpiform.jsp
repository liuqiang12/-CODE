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
<div class="easyui-panel" title="" border="false" style="width:100%;padding:0px 10px;">
    <form method="post" id="signupForm" action="${contextPath}/alarm/alarmkpisave.do">
        <table class="kv-table">
            <tbody>
            <tr>
                <td class="kv-label">告警类别</td>
                <td class="kv-content">
                    <input type="hidden" name="id" value="${alarmconfiglevel.id}"/>
                    <select class="easyui-combobox" name="kpiid" style="width: 200px" value="${alarmconfiglevel.kpiid}"
                            data-options="panelHeight:150">
                        <c:forEach items="${kpis}" var="kpi">
                            <option value="${kpi.kpiid}">${kpi.kpiname}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">告警等级</td>
                <td class="kv-content">
                    <select class="easyui-combobox" name="alarmlevel" style="width: 200px" value="${alarmconfiglevel.alarmlevel}"
                            data-options="panelHeight:100">
                        <option value="0">轻微</option>
                        <option value="1">一般</option>
                        <option value="2">严重</option>
                        <option value="3">关键</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">阀值下限</td>
                <td class="kv-content">
                    <input class="easyui-numberbox" value="${alarmconfiglevel.thresholdlowlimit}" style="width: 200px"
                           name="thresholdlowlimit"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">阀值上限</td>
                <td class="kv-content">
                    <input class="easyui-numberbox" value="${alarmconfiglevel.thresholdlimit}" style="width: 200px"
                           name="thresholdlimit"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">说明</td>
                <td class="kv-content">
                    <input class="easyui-textbox" value="${alarmconfiglevel.description}" style="width: 200px"
                           name="description"/>
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
<script>
    function closeWin() {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    }
    function doSubmit(fn){
        var flag = $("#signupForm").form('validate');
        if (flag) {
            $.post(contextPath + "/alarm/alarmkpisave.do", $("#signupForm").serialize(), function (data) {
                if (data.state) {
                    layer.msg('保存成功');
                    if(fn&&typeof(fn)=="function"){
                        fn();
                    }
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                } else {
                    layer.msg(data.msg);
                }
            });
        }
//        $("#signupForm").form('submit', {
//            onSubmit: function () {
//                var flag = $(this).form('validate');
//                if (flag) {
//                    $.post(contextPath + "/alarm/alarmkpisave.do", $(this).serialize(), function (data) {
//                        if (data.state) {
//                            layer.msg('保存成功');
//                            if(fn&&typeof(fn)=="function"){
//                                fn();
//                            }
//                            var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//                            top.layer.close(index); //再执行关闭
//                        } else {
//                            layer.msg(data.msg);
//                        }
//                    });
//                }
//                return false;
//            }
//        });
    }
//    $(document).ready(function () {
//        $("#submit").on('click', function () {
//
//        });
//    });
</script>
</body>
</html>