<%@page import="org.apache.velocity.runtime.directive.Include"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="renderer" content="webkit">
    <title>登录</title>  
	<script type="text/javascript" src="<%=request.getContextPath() %>/framework/login/ui6/js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/framework/login/ui6/js/jquery.cookie.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/login/ui6/css/common.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/login/ui6/css/esmpMain.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/login/ui6/css/style.css">
	<style type="text/css">
#createcode{
border:1px solid #eee;
width:50px;
height:30px;
margin-left:8px;
text-align: left;
font-weight: 600;
font-style: italic;
color:highlight;
color: #606060;
}
.passcodeImg{
	display: inline-block;
	bottom: 50px;
    cursor: pointer;
    height: 30px;
    left: 143px;
    position: absolute;
    width: 77px
}
</style>

</head>
<body id="bg">
	<form action="<%=request.getContextPath() %>/login.do" method="post">
		<div class="login">
		  <div class="login-box">
		    <div class="logo-area"></div>
		    <div class="login-area">
		    	<ul>
		    		<li><label>帐&nbsp;&nbsp;&nbsp;&nbsp;号：</label><input type="text" class="longinput" id="username" name="username" ></li>
		    		<li><label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label><input type="password" class="longinput" id="password" name="password" ></li>
		    		<li><label>验证码：</label><input type="text" class="shortinput" id="validateCode" name="validateCode">
		    			<img src="<%=request.getContextPath() %>/captcha.do" alt="" width="150" height="32" class="passcode passcodeImg" style="height:30px;cursor:pointer;" onclick="this.src='<%=request.getContextPath() %>/captcha.do?t='+new Date()*1">  
		    		<li style="text-align:center;"><input type="submit" class="login-btn" value="登录"></li>
		    	</ul>
		    </div>
		  </div>
		</div>
	</form>
</body>
<body>
<script type="text/javascript" src="<%=request.getContextPath() %>/framework/login/ui1/js/jquery.js"></script><!-- 自定义菜单 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/framework/login/ui1/js/pintuer.js"></script><!-- 菜单所用 -->
</body>
</html>