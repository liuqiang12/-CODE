<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>MCB信息</title>
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
                   data-options="fit:true,rownumbers:false,pagination:true,singleSelect:true,
                   onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                   fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/device/queryDeviceList',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'DEVICEID',hidden:true"></th>
                    <%--<th data-options="field:'CODE',width:100">编码</th>--%>
                    <th data-options="field:'NAME',width:250">名称</th>
                    <%--<th data-options="field:'SERVERIPADDRESS',width:100">IP</th>--%>
                    <th data-options="field:'VENDOR',width:100,formatter:vendorDealis">厂商</th>
                    <th data-options="field:'MODEL',width:100">型号</th>
                    <th data-options="field:'UHEIGHT',width:100">高度</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="名称/IP"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
</div>
<script type="text/javascript" language="JavaScript">
    $(function () {
        //绑定双击事件
        $('#table').datagrid({
            onDblClickRow: function (index, row) {
                var url = contextPath + "/device/deviceDetails.do?id=" + row.DEVICEID + "&buttonType=view&deviceclass=" + row.DEVICECLASS;
                openDialogView('设备信息', url, '800px', '530px');
            }
        });
    })
    function doSubmit() {
        var row = $("#table").datagrid("getSelected");
        if (row == null) {
            mylayer.msg('没有选择设备');
            return;
        }
        if (row.DEVICEID && row.UHEIGHT) {
            top.add(row);
        } else {
            top.layer.msg('该设备数据不完整，无法上架')
        }
    }
    //厂商
    function vendorDealis(value, row, index) {
        if (value == 0) {
            return '华为';
        } else if (value == 1) {
            return '思科';
        } else if (value == 2) {
            return '阿尔卡特';
        } else if (value == 3) {
            return '3Com';
        } else if (value == 4) {
            return 'HP';
        } else if (value == 5) {
            return 'Linux';
        } else if (value == 6) {
            return 'Microsoft';
        } else if (value == 17) {
            return 'D-Link';
        } else if (value == 54) {
            return 'Juniper';
        } else if (value == 59) {
            return 'H3C';
        } else {
            return '';
        }
    }
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
            url: contextPath + "/device/queryDeviceList",
            queryParams: {name: name}
        });
    }
</script>
</body>
</html>