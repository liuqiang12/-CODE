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
    <link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">

    <%--<script type="text/javascript" language="JavaScript">--%>
        <%--parent.window.onresize=function(){--%>
            <%--ThisiframeAuto();--%>
        <%--}--%>
        <%--function ThisiframeAuto(){--%>
            <%--var h=($(top).height()||0);--%>
            <%--var w=($(top).width()||0);--%>
<%--//            $(window).width(w*0.7);--%>
<%--//            $(window).height(h*0.7)--%>
            <%--$(document.body).height(h*0.7);--%>
            <%--$(document.body).width(w*0.7);--%>
            <%--var index = parent.layer.getFrameIndex(window.name);--%>
            <%--layer.iframeAuto(index);// - 指定iframe层自适应--%>
        <%--}--%>

    <%--</script>--%>
    <style>
        .ztree li {
            background-color: white;
        }
        .dv-table td {
            padding: 5px 0px 5px 0px ;
        }
    </style>
</head>
<body>
<div class="easyui-panel"  border="false" style="width:100%;padding:10px 30px;">
    <form method="post" id="signupForm">
        <table class="dv-table" style="width:100%;padding:5px;margin-top:1px;">
            <tr>
                <td>原密码:</td>
                <td>
                    <input name="oldpassword" type="password" class="easyui-validatebox easyui-textbox" required="true" placeholder="请输入原始密码"/>
                </td>
            </tr>
            <tr>
                <td>密码:</td>
                <td>
                    <input name="password" id="password" type="password" class="easyui-validatebox easyui-textbox" required="true" placeholder="请输入新密码"/>
                </td>
            </tr>
            <tr>
                <td>确认密码:</td>
                <td><input type="password" class="easyui-validatebox easyui-textbox" required="true"
                           validType="equalTo['#password']"
                           invalidMessage="两次输入密码不匹配"/></td>
            </tr>
            <tr>
                <td colspan="4"></td>
            </tr>
        </table>
        <div style="text-align:center;padding:0px 0">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()" iconCls="icon-save"  style="width:80px">保存</a>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()" iconCls="icon-cancel"  style="width:80px">关闭</a>--%>
        </div>
    </form>
</div>
<script>
    $.extend($.fn.validatebox.defaults.rules, {
        /*必须和某个字段相等*/
        equalTo: {
            validator: function (value, param) {
                return $(param[0]).val() == value;
            },
            message: '字段不匹配'
        }
    });
    function submitForm(){
        $('#signupForm').form('submit', {
            url: contextPath+"/userinfo/updatePassword",
            onSubmit: function () {
                return $("#signupForm").form('validate');
            },
            success: function (data) {
                var obj = eval('(' + data + ')');
                alert(obj.msg);
                if (obj.state) {
                    top.location.href = "<%=request.getContextPath() %>/logout.do";
                }
            }
        });
    }
    // function closeWin(){
    //     var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    //     parent.layer.close(index); //再执行关闭
    // }
</script>
</body>
</html>