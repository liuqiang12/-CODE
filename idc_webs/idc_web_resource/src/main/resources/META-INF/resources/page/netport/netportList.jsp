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
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="table" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,pagination:true,
                   onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                   fitColumns:false,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/netport/list.do?deviceId=${deviceId}',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'PORTID',hidden:'true'"></th>
                    <th data-options="field:'PORTNAME',width:150">端口名称</th>
                    <th data-options="field:'ALIAS',width:200,formatter:aliasDetail">端口别名</th>
                    <th data-options="field:'PORTTYPE',width:100">端口类型</th>
                    <%--<th data-options="field:'DNAME',width:300,formatter:deviceDealis">设备</th>--%>
                    <th data-options="field:'STATUS',width:100,formatter:statusDetail">资源占用状态</th>
                    <th data-options="field:'CUSTOMERNAME',width:200,formatter:customerInfo">客户名称</th>
                    <th data-options="field:'PORTACTIVE',width:100,formatter:formmatenabled">端口操作状态</th>
                    <%--<th data-options="field:'SIDEDEVICE',width:100">对端设备</th>--%>
                    <%--<th data-options="field:'IP',width:100">端口对应IP</th>--%>
                    <%--<th data-options="field:'MAC',width:100">Mac地址</th>--%>
                    <%--<th data-options="field:'NETMASK',width:100">子网掩码</th>--%>
                    <th data-options="field:'BANDWIDTH',width:100">端口带宽(Mbps)</th>
                    <th data-options="field:'ASSIGNATION',width:100">分派带宽(Mbps)</th>
                    <%--<th data-options="field:'PORTPLTYPE',width:100,formatter:portpltypeDetails">端口物理逻辑类型</th>--%>
                    <%--<th data-options="field:'MEDIATYPE',width:100,formatter:mediatypeDetails">物理端口类别</th>--%>
                    <%--<th data-options="field:'ADMINSTATUS',width:100,formatter:adminstatusDetails">端口管理状态</th>--%>
                    <th data-options="field:'TICKETID',width:100">工单编号</th>
                    <th data-options="field:'DESCR',width:100">端口描述</th>
                    <th data-options="field:'NOTE',width:100">备注</th>
                </tr>
                </thead>
            </table>
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
        $('#table').datagrid({
            onDblClickRow: function (index, row) {
                var url = contextPath + "/netport/netportDetails.do?portid=" + row.PORTID + "&buttonType=view";
                openDialogView('端口信息', url,'800px','530px');
            }
        });
    });
    function aliasDetail(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" style="text-decoration:none" title="' + value + '">' + value + '</a>';
        } else {
            return "";
        }
    }
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
    //查看客户信息
    function customerInfo(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="showCustomerInfo(\'' + row.CUSTOMERID + '\',\'' + value + '\')">' + value + '</a>';
        } else {
            return '';
        }
    }
    //查看客户信息
    function showCustomerInfo(customerId, customerName) {
        var url = contextPath + "/customerController/linkQueryWin.do?viewQuery=1&id=" + customerId;
        openDialogView(customerName, url, '784px', '600px');
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
    function deviceDealis(value,row,index){
        if(value != null && value != ''){
            return '<a href="javascript:void(0)" onclick="getDeviceDealis('+row.DEVICEID+')">' + value + '</a>';
        }else{
            return '';
        }
    }
    function getDeviceDealis(value){
        openDialogView('设备信息', contextPath + "/device/deviceDetails.do?id=" + value + "&buttonType=view&deviceclass=1", '800px', '530px');
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight');
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