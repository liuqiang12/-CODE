<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<title>IDC机房_用户登录</title>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/jquery.min.js"></script>
<link href="<%=request.getContextPath() %>/framework/themes/css/import_login_basic.css" rel="stylesheet"
      type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/framework/layer/layer.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/layer/skin/layer.css">
<link href="<%=request.getContextPath() %>/framework/themes/skins/gray/import_login_skin.css" id="skin"
      themeColor="blue" rel="stylesheet" type="text/css"/>
<style type="text/css">

    body {
        margin-left: 0px;
        margin-top: 0px;
        margin-right: 0px;
        margin-bottom: 0px;
        overflow: hidden;
        top: 0;
        bottom: 0;
        position: absolute;
        right: 0;
        left: 0;
        overflow: hidden;
    }

    .STYLE1 {
        font-size: 12px;
        color: #a1c8c6;
    }

    .STYLE4 {
        color: #FFFFFF;
        font-size: 12px;
    }

</style>
<script type="text/javascript">
    document.onkeydown=enter_fun;
    function enter_fun(e){
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            toPageMain();
            return false;
        }
        return true;
    }
    function toPageMain() {
        var username = $("#username").val();
        var pwd = $("#password").val();
        var validateCode = $("#validateCode").val();
        if (username == "" || pwd == "") {
            top.layer.msg("请输入用户名或密码")
//            alert("请输入用户名或密码");
            return;
        }
        if(validateCode==""){
            top.layer.msg("请输入验证码")
            return ;
        }
        //直接请求后台
        $("#loginForm").submit();
    }
    function formReset() {
        document.getElementById("loginForm").reset()
    }
</script>
</head>

<body>
<div class="login-content">
    <form action="<%=request.getContextPath() %>/login.do" method="post" id="loginForm">
        <div class="login-logo"></div>
        <!-- 用户操作 start -->
        <div class="login-title"></div>
        <div class="login-main">
            <!-- 输入框情况 start -->
            <div class="login-inputs">
                <div class="login-username-cls">
                    <input type="text" class="login-font input_fix" name="username" id="username"/>
                </div>
                <div class="login-userpwd-cls">
                    <input type="password" name="password" id="password" class="login-font input_fix"/>
                </div>
                <div class="login-validate-cls">
                    <input type="text" class="login-font input_fix" name="validateCode" id="validateCode"/> <span class="validationReps">
                    <img src="<%=request.getContextPath() %>/captcha.do" width="70px" height="24px"
                         onclick="this.src='<%=request.getContextPath() %>/captcha.do?t='+new Date()*1"/>
            </span></div>
                <div class="handler-cls">
                    <div class="handler-cls-login" onclick="toPageMain()"></div>
                    <div class="handler-cls-reset" onclick="formReset()"></div>
                </div>
            </div>
            <!-- 输入框情况 end-->
            <div class="copyright-cls">
                <span class="copyright-text">2017    四川众力佳华信息技术有限公司 版权所有</span>
            </div>
        </div>
    </form>
    <!-- 用户操作 end -->
</div>
<%--<div class="login-content">--%>
<%--<form action="<%=request.getContextPath() %>/login.do" method="post" id="loginForm">--%>
<%--<div class="login-logo"></div>--%>
<%--<!-- 用户操作 start -->--%>
<%--<div class="login-main">--%>
<%--<!-- 输入框情况 start -->--%>
<%--<div class="login-inputs">--%>
<%--<div class="login-username-cls">--%>
<%--<input type="text" name="username" id="username" class="login-font"/>--%>
<%--</div>--%>
<%--<div class="login-userpwd-cls">--%>
<%--<input type="password" name="password" id="password" class="login-font"/>--%>
<%--</div>--%>
<%--<div class="login-validate-cls">--%>
<%--<input type="text" class="login-font" name="validateCode"/> <span class="validationReps">--%>
<%--<img src="<%=request.getContextPath() %>/captcha.do" width="70px" height="24px"--%>
<%--onclick="this.src='<%=request.getContextPath() %>/captcha.do?t='+new Date()*1"/>--%>
<%--</span>--%>
<%--</div>--%>
<%--<div class="handler-cls">--%>
<%--<div class="handler-cls-login" onclick="toPageMain()"></div>--%>
<%--<div class="handler-cls-reset" onclick="formReset()"></div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<!-- 输入框情况 end-->--%>
<%--<div class="copyright-cls">--%>
<%--<span class="copyright-text">2017    四川众力佳华信息技术有限公司 版权所有</span>--%>
<%--</div>--%>
<%--</div>--%>
<%--</form>--%>
<%--<!-- 用户操作 end -->--%>
<%--</div>--%>

</body>
</html>
