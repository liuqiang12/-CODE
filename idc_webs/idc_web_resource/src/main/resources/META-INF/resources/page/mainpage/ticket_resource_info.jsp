<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath() ;
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>connector信息</title>
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
                   data-options="fit:true,rownumbers:true,pagination:true,singleSelect:true,
                   onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                   fitColumns:false,toolbar:'#toolbar',
                  ">
                <thead>
                <c:choose>
                    <c:when test="${type=='rack'}">
                        <tr>
                            <th data-options="field:'RACKID',hidden:'true'"></th>
                            <th data-options="field:'SERIALNUMBER',width:150">工单号</th>
                            <th data-options="field:'RACKNAME',width:200,formatter:rackDealisLink">所属机架</th>
                        </tr>
                    </c:when>
                    <c:when test="${type=='rackNum'}">
                        <tr>
                            <th data-options="field:'RACKID',hidden:'true'"></th>
                            <th data-options="field:'SERIALNUMBER',width:150">工单号</th>
                            <th data-options="field:'RACKNAME',width:200">机架名称</th>
                            <th data-options="field:'SITENAME',width:200,formatter:rackRoomDealisLink">所属机房</th>
                            <th data-options="field:'RACKMODELNAME',width:100">机架型号</th>
                            <th data-options="field:'USEFOR',width:100">用途</th>
                            <th data-options="field:'RENTTYPE',width:100">出租类型</th>
                            <th data-options="field:'STATUS',width:100">状态</th>
                            <th data-options="field:'POWERTYPE',width:100">用电类型</th>
                            <th data-options="field:'RATEDELECTRICENERGY',width:100">额定电量</th>
                        </tr>
                    </c:when>
                    <c:when test="${type=='rackunitNum'}">
                        <tr>
                            <th data-options="field:'ID',hidden:'true'"></th>
                            <th data-options="field:'SERIALNUMBER',width:150">工单号</th>
                            <th data-options="field:'RNAME',width:200,formatter:rackDealisLink">所属机架</th>
                            <th data-options="field:'CODE',width:200">机位编码</th>
                            <th data-options="field:'UNO',width:100">机位号</th>
                            <th data-options="field:'DNAME',width:250,formatter:deviceDealis">设备</th>
                                <%--<th data-options="field:'SITENAME',width:200,formatter:roomDealis">机房</th>--%>
                                <%--<th data-options="field:'ORDERNO',width:100">顺序</th>--%>
                                <%--<th data-options="field:'IDCNO',width:100">IDC编号</th>--%>
                            <th data-options="field:'STATUS',width:100,formatter:unitStatusDealis">状态</th>
                                <%--<th data-options="field:'USEFOR',width:100">用途</th>--%>
                        </tr>
                    </c:when>
                    <c:when test="${type=='ip'}">
                        <tr>
                            <th data-options="field:'ID',hidden:'true'"></th>
                            <th data-options="field:'SERIALNUMBER',width:150">工单号</th>
                            <th data-options="field:'BEGINIP',width:100">IP地址</th>
                            <th data-options="field:'ENDIP',width:100">IP地址止</th>
                        </tr>
                    </c:when>
                    <c:when test="${type=='ipNum'}">
                        <tr>
                            <th data-options="field:'ID',hidden:'true'"></th>
                            <th data-options="field:'SERIALNUMBER',width:150">工单号</th>
                            <th data-options="field:'IPADDRESS',width:100">IP</th>
                            <th data-options="field:'MASKSTR',width:100">掩码</th>
                            <th data-options="field:'SUBNETIP',width:100">子网IP</th>
                            <th data-options="field:'MAC',width:100">MAC</th>
                            <th data-options="field:'STATUS',width:100,formatter:ipStatus">状态</th>
                            <th data-options="field:'REMARK',width:100">备注</th>
                        </tr>
                    </c:when>
                    <c:when test="${type=='port'}">
                        <tr>
                            <th data-options="field:'PORTID',hidden:'true'"></th>
                            <th data-options="field:'SERIALNUMBER',width:150">工单号</th>
                            <th data-options="field:'PORTNAME',width:150">端口名称</th>
                            <th data-options="field:'ALIAS',width:200,formatter:aliasDetail">端口别名</th>
                            <th data-options="field:'DNAME',width:250,formatter:deviceDealis">设备名称</th>
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
                    </c:when>
                    <c:when test="${type=='ticketNum'}">
                        <tr>
                            <th data-options="field:'TICKETID',hidden:'true'"></th>
                            <th data-options="field:'SERIALNUMBER',width:200">工单号</th>
                            <th data-options="field:'BUSNAME',width:200,formatter:tipParamInfo">业务名称</th>
                            <th data-options="field:'RACKSTR',width:200,formatter:tipParamInfo">分配机架</th>
                            <th data-options="field:'RACKCOUNT',width:100">机架数</th>
                            <th data-options="field:'IPSTR',width:200,formatter:tipParamInfo">分配IP</th>
                            <th data-options="field:'DISTIME',width:200">分配时间</th>
                        </tr>
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="名称/编码/IP"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
</div>
<script type="text/javascript" language="JavaScript">
    var customerId = "${customerId}";
    var type = "${type}";
    var flag =true;
    $(function () {
        if (type == 'rackNum' || type == 'rackunitNum' || type == 'port') {
            flag = false;
        }
        $('#table').datagrid({
            fitColumns: flag,
            emptyMsg: '<span style="color: red;font-size: 16px;">无记录</span>',
            loadMsg: '正在加载中，请稍等... ',
            url: contextPath + "/resource/queryResourceInfos",
            queryParams: {type: type, customerId: customerId}
        });
    })
    //加载列表
    function loadsuccess(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight');
    }
    //查询
    function searchModels() {
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/resource/queryResourceInfos",
            queryParams: {type: type, name: name, customerId: customerId}
        });
    }

    //机架列表============start==================
    //机架链接
    function rackDealisLink(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="getRackDealis(' + row.RACKID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    //查看机架信息
    function getRackDealis(value) {
        openDialogShowView2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + value + '&businesstype=other&buttonType=view', '800px', '530px', '查看机架视图');
    }
    //机房链接
    function rackRoomDealisLink(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="showRoom(' + row.ROOMID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    //查看机房信息
    function showRoom(id) {
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + id + "&buttonType=view";
        openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
    }
    //机架列表======================end=================

    //机位======================start=================
    //设备
    function deviceDealis(value, row, index) {
        if (value != null && value != "") {
            return '<a href="javascript:void(0)" onclick="showDeviceDealis(' + row.DEVICEID + ',' + row.DEVICECLASS + ')">' + value + '</a>';
        } else {
            return "";
        }
    }
    function showDeviceDealis(value, type) {
        var url = "";
        if (type == 2) {//主机
            url = contextPath + "/device/deviceDetails.do?id=" + value + "&buttonType=view&deviceclass=2";
        } else {//网络
            url = contextPath + "/device/deviceDetails.do?id=" + value + "&buttonType=view&deviceclass=1";
        }
        openDialogView('设备信息', url, '800px', '530px');
    }
    function unitStatusDealis(value, row, index) {
        if (value == 20) {
            return "可用";
        } else {
            return "在服";
        }
    }
    //机位======================end=================

    //IP======================start=================
    //IP状态
    function ipStatus(value, row, index) {
        if (value == "1") {
            return '<font color="red">已用</font>';
        } else if (value == "0") {
            return '<font color="grey">空闲</font>';
        } else if (value == "2") {
            return '<font color="black">分配占用</font>';
        } else if (value == "3") {
            return '<font color="yellow">等待回收</font>';
        } else {
            return '<font color="grey">空闲</font>';
        }
    }
    //IP======================end=================

    //端口======================start=================
    function portpltypeDetails(value, row, index) {
        if (value == 0) {
            return "物理端口";
        } else if (value == 1) {
            return "逻辑端口";
        }
    }
    function mediatypeDetails(value, row, index) {
        if (value == 'fiber') {
            return "光口";
        } else if (value == 'cable') {
            return "电口";
        }
    }
    function adminstatusDetails(value, row, index) {
        if (value == 1) {
            return "UP";
        } else if (value == 2) {
            return "down";
        }
    }
    function aliasDetail(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" style="text-decoration:none" title="' + value + '">' + value + '</a>';
        } else {
            return "";
        }
    }
    //端口======================end=================
    //工单
    function tipParamInfo(value, row, index){
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" title="' + value + '">' + value + '</a>';
        } else {
            return "";
        }
    }
</script>
</body>
</html>