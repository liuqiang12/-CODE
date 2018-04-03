<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>错误页</title>
</head>

<body>
<div><h1>系统发生内部错误.</h1></div>
<div style="color: red">
    ${error}
</div>
</body>
</html>
