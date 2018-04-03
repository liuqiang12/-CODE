<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
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
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <title>能耗分析</title>

</head>
<body>
<div class="easyui-layout" fit="true" id="cc">
    <%--<div data-options="region:'west'" style="width:200px">--%>
    <%--<div class="content_suspend">--%>
    <%--<div class="conter">--%>
    <%--<ul id="rtree" class="ztree" style="width:200px; overflow:auto;"></ul>--%>
    <%--</div>--%>
    <%--<div class="hoverbtn">--%>
    <%--<span>资</span><span>源</span><span>信</span><span>息</span>--%>

    <%--<div class="hoverimg" height="9" width="13"></div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <div data-options="region:'center'">
        <table id="dg" fit="true" class="easyui-datagrid"></table>
        <%--<thead>--%>
        <%--<tr>--%>
        <%--<th data-options="field:'roomname',width:100">机房名字</th>--%>
        <%--<th data-options="field:'pe',width:100">昨日耗电量</th>--%>
        <%--</tr>--%>
        <%--</thead>--%>
        <%--<tbody>--%>
        <%--<c:forEach items="${datas}" var="row" varStatus="status">--%>
        <%--<tr>--%>
        <%--<td><a href="javascript:void(0)" onclick="showroom(${row.roomid})">${row.roomName}</a></td>--%>
        <%--<td>${row.pue}</td>--%>
        <%--</tr>--%>
        <%--</c:forEach>--%>
        <%--</tbody>--%>
    </div>
</div>
<script type="text/javascript">
    function showroom(id) {
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + id + "&buttonType=view";
        openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
    }
    /***
     *历史用电情况
     * @param roomid
     */
    function showhispuebyroom(roomid,roomname) {
        var url = contextPath + "/pe/hispe/${group}/"+roomid;
        openDialogView(roomname+'历史用电情况', url, '800px', '530px');
    }
    var CurrNode = null;
    function searchModels() {
        var queryParams = {};
        if (typeof CurrNode != 'undefined' && CurrNode != null) {
            var id = CurrNode.id;
            var datas = id.split("_");
            queryParams.buildid = datas[1]
        }
        $('#dg').datagrid({
            url: contextPath + "/pe/list",
            queryParams: queryParams
        });
    }
    function getOptions() {
        var option = {
            columns: [[
                {
                    field: 'roomName', title: ' <c:if test="${group==null || group!='build'}">机房名字</c:if><c:if test="${group!=null && group=='build'}">机楼名字</c:if>', width: 100, formatter: function (value, row, index) {
                    return '<a href="javascript:void(0)" onclick="showroom(' + row.roomid + ')">' + row.roomName + '</a>'
                }
                },

                {
                    field: 'electric', title: '昨日耗电量(KW/h)', width: 100, formatter: function (value, row, index) {
                    return '<a href="javascript:void(0)" onclick="showhispuebyroom('+ row.roomid + ',\''+row.roomName+'\')">' + value + '</a>'
                }
                },
                {
                    field: 'pue', title: 'PUE',width: 100,formatter:function(){
                    return ((Math.random()*(13-8)+8)/10).toFixed(2);
                }
                }

            ]],
            title: '昨日耗电量',
            pagination: false,
            singleSelect: true,
            fitColumns: true,
            pageSize: 20,
            selectOnCheck: false,
            checkOnSelect: false,
            url: contextPath + "/pe/list/${group}",
            <c:if test="${group==null || group!='build'}">
            rtree: {
                r_type: 1,
                onClick: function (iteam, treeId, treeNode) {
                    if (treeNode.id.indexOf("building_") > -1) {
                        CurrNode = treeNode;
                        searchModels();
                    } else {
                        CurrNode = null;
                        searchModels();
                    }
                }
            }
            </c:if>
        }
        return option;
    }
    $(function () {
        $("#dg").MyResource(
                getOptions()
        );
//        top.hideLeft();
    })
</script>
</body>
</html>