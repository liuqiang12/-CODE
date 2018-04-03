<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript">
        function add() {
            alert(add);
        }
        var vuex_store='dfdf'
        function opentt() {
            mylayer.open({
                type: 2,
                id:'adb',
                area: ['1200px', '630px'],
                fixed: false, //不固定
                maxmin: true,
                content: contextPath + '/racklayout/2024685'
            })
        }
    </script>
</head>
<body style="width:100%; margin:0px auto;">
<button onclick="opentt()">11111</button>
</body>
</html>