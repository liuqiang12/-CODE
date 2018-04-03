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
    <title>端口信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
</head>
<body style="margin:0;padding:2px;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'west',border:false" style="width: 200px">

        </div>
        <div data-options="region:'center',border:false" style="padding-left: 2px;">

        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
</div>
<script type="text/javascript" language="JavaScript">
    var deviceId = "${deviceId}";
    var roomId = "${roomId}";
    var rackId = "${rackId}";
    $(function(){
        var tree = dom.rtree({
            r_type: 3,
            onClick: function (iteam, treeId, treeNode) {
                if (treeNode.id.indexOf("location_") > -1) {
                    CurrNode = null;
                    searchModels();
                }
                if (treeNode.id.indexOf("idcroom_") > -1) {
                    CurrNode = treeNode;
                    searchModels();
                }
                if (treeNode.id.indexOf("idcrack_") > -1) {
                    CurrNode = treeNode;
                    searchModels();
                }
            }
        })

    });

    //资源占用状态
    function statusDetail(value, row, index) {
        if (value == 40) {
            return '空闲';
        } else if (value == 50) {
            return '预占';
        } else if (value == 60) {
            return '在服';
        } else if (value == 110) {
            return '不可用';
        } else {
            return '';
        }
    }
    function formmatenabled(value,row,index){
        if(value == 1){
            return "在用";
        }else if(value == 2){
            return "空闲";
        }else if(value == 3){
            return "测试";
        }else if(value == 4){
            return "未知";
        }else if(value == 5){
            return "休眠";
        }else if(value == 6){
            return "模块不在";
        }else if(value == 7){
            return "下层关闭";
        }else{
            return "其他";
        }
    }

    function portpltypeDetails(value,row,index){
        if(value == 0){
            return "物理端口";
        }else if(value == 1){
            return "逻辑端口";
        }
    }
    function mediatypeDetails(value,row,index){
        if(value == 'fiber'){
            return "光口";
        }else if(value == 'cable'){
            return "电口";
        }
    }
    function adminstatusDetails(value,row,index){
        if(value == 1){
            return "UP";
        }else if(value == 2){
            return "down";
        }
    }

    //查询
    function searchModels(){
        var name = $("#buiName").val();
        $('#table').datagrid({
            url:contextPath + "/netport/list.do",
            queryParams:{name:name,deviceId:${deviceId}}
        });
    }
    //确认 资源分配（设备-端口）
    function doSubmit(){
        //进行资源绑定
        var rowArr = [];
        var rows = $('#table').datagrid('getChecked');
        for(var i=0;i<rows.length;i++){
            rowArr.push(rows[i].PORTID);
        }
        $.post(contextPath+"/netport/bindConnectionToIdcLink.do",{portIds:rowArr.join(','),deviceId:deviceId,roomId:roomId,rackId:rackId},function(result){
            alert(result.msg);
            if(result.state){
                var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(indexT); //再执行关闭
            }
        });
    }
</script>
</body>
</html>