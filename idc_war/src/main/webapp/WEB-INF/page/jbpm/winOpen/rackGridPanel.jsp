<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <!-- 引用 -->
    <script type="text/javascript"  src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <title></title>
    <!-- 左右布局 -->
    <script type="text/javascript">
        var rackOrracunit = "${rackOrracunit}";
        var locationId = "${locationId}";
        var tableId = "rack_gridId";
        $(function () {
            var dom = $("#rtree");
            if (dom != null) {
                dom.rtree({
                    r_type: 2,
                    locationId:locationId,
                    onClick: function (iteam,treeId,treeNode) {
                        var id = treeNode.id;
                        $("#rackName_params").textbox('setValue','');
                        var params = getRequestRackParams();
                        //查询机房信息,点击的是否是机房
                        if(id != null && id.indexOf("idcroom_") > -1){
                            /*idcroom_1332*/
                            var idparams = id.split("idcroom_");
                            //调用查询方法
                            $("#nodeId").val(idparams[1]);
                            params['nodeParam'] = idparams[1];
                            loadRackGrid("rack_gridId",params);
                            $("#roomlayoutIframeId").attr("src", contextPath + "/roomlayout/select/" + idparams[1] + "/" + rackOrracunit);
                        }else{
                            $("#roomlayoutIframeId").removeAttr("src");
                        }
                    }
                })
            }
        })
    </script>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="serverLayout" class="easyui-layout" fit="true">
        <div data-options="region:'west',iconCls:'icon-reload',split:true" style="width:250px;height: 100%;overflow-x: scroll;overflow-y: scroll;">
            <ul id="rtree" class="ztree"></ul>
        </div>
        <div data-options="region:'center'" style="padding:5px;background:#eee;">
            <div id="rackApply_tabs" class="easyui-tabs" fit="true">
                <div title="列表数据" style="padding:10px;display:none;">
                    <div style="padding: 5px;" id="requestParamSettins_gridId">
                        名称: <input class="easyui-textbox"  id="rackName_params" style="width:200px;text-align: left;" data-options="">
                        <input id="nodeId" value="" type="hidden">

                        <a href="javascript:void(0);" onclick="loadRackGrid('rack_gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                    </div>
                    <table class="easyui-datagrid" id="rack_gridId"></table>
                </div>
                <div title="空间布局" style="padding:10px;display:none;">
                    <!-- roomlayoutIframeId -->
                    <iframe id="roomlayoutIframeId" style="width: 1800px;height:2050px;"></iframe>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
<script src="<%=request.getContextPath() %>/js/jbpm/win/openRackResourceQuery.js"></script>
</html>