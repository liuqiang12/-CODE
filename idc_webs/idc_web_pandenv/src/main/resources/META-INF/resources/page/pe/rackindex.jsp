<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/themes/css/form.css"/>
    <title>性能监视</title>

</head>
<body>
<div class="easyui-layout" fit="true">
      <div class="content_suspend">
            <div class="conter">
               <ul id="rtree" class="ztree"></ul>
            </div>
            <div class="hoverbtn">
                <span>资</span><span>源</span><span>导</span><span>航</span>
                <div class="hoverimg" height="9" width="13"></div>
            </div>
        </div>
    <div data-options="region:'center',border:true" style="padding-left:2px;overflow:hidden;">
        <iframe scrolling="yes"  width="100%" height="100%" frameBorder=0 id="devicecap"  name="devicecap" allowTransparency="true"  src="<%=request.getContextPath() %>/pe/roomlayout/0"></iframe>
    </div>
</div>
<script type="text/javascript">
    $(function () {
    try{ top.hideLeft();}catch(e){}

        var setting = {
            view: {
            },
            data: {
                key:{
                    name:'text'
                }
            }
        };
        var dom = $("#rtree");
        if (dom != null) {
            var tree = dom.rtree({
                r_type: 2,
                onClick: function (iteam, treeId, treeNode) {
                    if (treeNode.id.indexOf("room_") > -1) {
                        CurrNode = treeNode;
                        var deviceid = treeNode.id.split("room_")[1];
                        $("#devicecap").attr("src",contextPath+"/pe/roomlayout/"+deviceid);

                    }
                }})
        }
    });
</script>
</body>
</html>