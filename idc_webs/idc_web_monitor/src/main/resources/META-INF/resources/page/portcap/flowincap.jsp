<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:north" style=""></div>
</div>
<script type="text/javascript">
    $(function () {
        var tabMap = {};
        $("#tt").tabs({
            onSelect: function (title, index) {
                var url = tabMap[title];
                if (typeof(url) == 'undefined') {
                    if (index == 1) {

                    }
                }
            }
        })
    })
</script>
</body>
</html>