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
    <title>设备信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/locale/easyui-lang-zh_CN_least.js"></script>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
</head>
<body style="margin:0;padding:2px;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',border:false" style="padding-left: 2px;width: 330px;">
        <table id="table" class="easyui-datagrid"
               data-options="fit:true,rownumbers:false,pagination:true,singleSelect:true,
               onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
               fitColumns:true,toolbar:'#toolbar',url:'<%=contextPath %>/device/distributionDeviceList/${roomId}',
              ">
            <thead>
            <tr>
                <th data-options="field:'DEVICEID',hidden:true,width:100"></th>
                <th data-options="field:'NAME',width:250">设备名称</th>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center',border:false" style="padding-left: 2px;">
        <table id="netPortTable" class="easyui-datagrid"
               data-options="fit:true,rownumbers:true,striped:true,
                   onLoadSuccess:loadsuccessPort,
                   pagination:true,pageSize:20,pageList:[20,50,100,200,300,500],
                   fitColumns:false,toolbar:'#toolbarPort'
                  ">
            <thead>
            <tr>
                <th data-options="field:'PORTID',hidden:'true'"></th>
                <th data-options="field:'PORTNAME',width:150">端口名称</th>
                <th data-options="field:'ALIAS',width:200,formatter:aliasDetail">端口别名</th>
                <th data-options="field:'PORTTYPE',width:100">端口类型</th>
                <th data-options="field:'STATUS',width:100,formatter:statusDetail">资源占用状态</th>
                <th data-options="field:'CUSTOMERNAME',width:200,formatter:customerInfo">客户名称</th>
                <%--<th data-options="field:'portactive',width:100,formatter:formmatenabled">端口操作状态</th>--%>
                <%--<th data-options="field:'SIDEDEVICE',width:100">对端设备</th>--%>
                <%--<th data-options="field:'IP',width:100">端口对应IP</th>--%>
                <%--<th data-options="field:'MAC',width:100">Mac地址</th>--%>
                <%--<th data-options="field:'NETMASK',width:100">子网掩码</th>--%>
                <th data-options="field:'BANDWIDTH',width:100">端口带宽(Mbps)</th>
                <th data-options="field:'ASSIGNATION',width:100">分派带宽(Mbps)</th>
                <th data-options="field:'TICKETID',width:100">工单编号</th>
                <th data-options="field:'DESCR',width:100">端口描述</th>
                <th data-options="field:'NOTE',width:100">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
</div>

<div id="toolbarPort" style="height: 28px;" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="portName" class="param-input" placeholder="名称/编码"/>
    </div>
    <div class="btn-cls-search" onClick="searchPortModels();"></div>
</div>
<script type="text/javascript" language="JavaScript">
    //A端机架ID
    var rackIds = "${rackIds}";
    //A端端口ID
    var portIdA = "${portIdA}";
    //机房ID
    var roomId = "${roomId}";
    //当前单击的设备ID
    var deviceIdForPort = null;
    //用于防止单双击事件冲突问题
    var timeFn = null;
    $(function(){
        $('#table').datagrid({
            //绑定双击事件
            onDblClickRow: function (index, row) {
                clearTimeout(timeFn);
                var url = contextPath + "/device/deviceDetails.do?id=" + row.DEVICEID + "&buttonType=view&deviceclass=1";
                openDialogView('设备信息', url,'800px','530px');
            },
            //绑定单击事件
            onClickRow: function (index, row) {
                clearTimeout(timeFn);
                timeFn = setTimeout(function () {
                    deviceIdForPort = row.DEVICEID;
                    $("#portName").val('');
                    $('#netPortTable').datagrid({
                        url: contextPath + '/netport/list.do',
                        queryParams: {name: '', deviceId: row.DEVICEID,status:40}
                    });
                }, 200);
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
    // function formmatenabled(value, row, index) {
    //     if (value == 1) {
    //         return "在用";
    //     } else if (value == 2) {
    //         return "空闲";
    //     } else if (value == 3) {
    //         return "测试";
    //     } else if (value == 4) {
    //         return "未知";
    //     } else if (value == 5) {
    //         return "休眠";
    //     } else if (value == 6) {
    //         return "模块不在";
    //     } else if (value == 7) {
    //         return "下层关闭";
    //     } else {
    //         return "其他";
    //     }
    // }
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
    //加载列表
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight');
    }
    function loadsuccessPort(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#netPortTable').datagrid('fixRowHeight');
    }
    //查询
    function searchModels(){
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/device/distributionDeviceList/" + roomId,
            queryParams:{name:name}
        });
    }
    function searchPortModels() {
        var name = $("#portName").val();
        $('#netPortTable').datagrid({
            url: contextPath + '/netport/list.do',
            queryParams: {name: name, deviceId: deviceIdForPort,status:40}
        });
    }
    //进行关系绑定  topo
    function doSubmit() {
        var rowArr = new Array();
        var rows = $('#netPortTable').datagrid('getChecked');
        if (rows.length < 1) {
            layer.msg("请选择端口");
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].PORTID);
        }
        $.post(contextPath + "/netport/bindConnectionToIdcLink.do", {
            portIds: rowArr.join(','),
            deviceId: deviceIdForPort,
            roomId: roomId,
            rackIds: rackIds,
            portIdA: portIdA
        }, function (result) {
            if (result.state) {
                // var topIndex = top.layer.getFrameIndex("jumpFiber")-1;
                // var topParentWin = top.document.getElementById("layui-layer-iframe"+topIndex).contentWindow;
                // topParentWin.rowObj.push(rows);
                //重新加载topo图
                var parentWin = top.winref[window.name];
                top[parentWin].reloadWin();
                var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(indexT); //再执行关闭
            }
        });
    }
</script>
</body>
</html>