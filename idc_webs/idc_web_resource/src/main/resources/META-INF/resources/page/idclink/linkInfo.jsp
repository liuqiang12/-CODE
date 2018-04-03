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
        <div data-options="region:'center',border:false" style="padding-left: 2px;width: 330px;">
            <table id="table" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,singleSelect:true,striped:true,
                   fitColumns:false,toolbar:'#toolbar',onClickRow:fun
                  ">
                <thead>
                <tr>
                    <th data-options="field:'ID',hidden:true"></th>
                    <%--<th data-options="field:'NAME',width:400">链路名称</th>--%>
                    <th data-options="field:'ARACKNAME',width:200,formatter:arackDealis">A端机架</th>
                    <th data-options="field:'ADEVICENAME',width:250,formatter:adeviceDealis">A端设备</th>
                    <th data-options="field:'APORTNAME',width:300,formatter:aportDealis">A端端口</th>
                    <th data-options="field:'ZRACKNAME',width:200,formatter:zrackDealis">Z端机架</th>
                    <th data-options="field:'ZDEVICENAME',width:250,formatter:zdeviceDealis">Z端设备</th>
                    <th data-options="field:'ZPORTNAME',width:300,formatter:zportDealis">Z端端口</th>
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
    var tableId = "table";
    var aKey = "${aKey}";
    var zKey = "${zKey}";
    var portStr = "${portStr}";
    var wlRackId = "${wlRackId}";
    $(function(){
        $("#table").datagrid({
            url:contextPath+"/idclink/getLinksByAZ",
            queryParams:{aKey:aKey,zKey:zKey,portStr:portStr,wlRackId:wlRackId}
        })
    })
    function arackDealis(value, row, index) {
        if (value != null && value != "") {
            return '<a href="javascript:void(0)" onclick="getRackDealis(' + row.AENDRACKID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    function zrackDealis(value, row, index) {
        if (value != null && value != "") {
            return '<a href="javascript:void(0)" onclick="getRackDealis(' + row.ZENDRACKID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    //查看机架信息
    function getRackDealis(value) {
        $.post(contextPath + "/idcRack/getIdcRackInfo", {id: value}, function (result) {
            console.log(result);
            var url = '';
            if (result.BUSINESSTYPE_ID != null && result.BUSINESSTYPE_ID == 'pdu') {
                url = contextPath + '/idcRack/idcRackDetails.do?rackId=' + value + '&businesstype=pdu&buttonType=view';
                openDialogShowView2d('PDF架信息', url, '800px', '400px', '查看机架视图');
            } else if (result.BUSINESSTYPE_ID != null && result.BUSINESSTYPE_ID == 'df' && result.DFTYPE != 'wiring') {
                url = contextPath + '/idcRack/idcRackDetails.do?rackId=' + value + '&businesstype=odf&buttonType=view';
                openDialogShowView2d('ODF架信息', url, '800px', '400px', '查看机架视图');
            } else {
                url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + value + "&businesstype=other&buttonType=view";
                openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
            }
        });
    }
    function adeviceDealis(value, row, index) {
        if (value != null && value != "") {
            return '<a href="javascript:void(0)" onclick="getDeviceDealis(' + row.AENDDEVICEID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    function zdeviceDealis(value, row, index) {
        if (value != null && value != "") {
            return '<a href="javascript:void(0)" onclick="getDeviceDealis(' + row.ZENDDEVICEID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    //查看设备信息
    function getDeviceDealis(value) {
        var url = contextPath + "/device/deviceDetails.do?id=" + value + "&buttonType=view&deviceclass=1";
        openDialogView('设备信息', url, '800px', '530px');
    }
    function aportDealis(value, row, index) {
        if (value != null && value != "") {
            return '<a href="javascript:void(0)" onclick="getPortDealis(' + row.AENDPORTID + ',' + row.AENDDEVICEID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    function zportDealis(value, row, index) {
        if (value != null && value != "") {
            return '<a href="javascript:void(0)" onclick="getPortDealis(' + row.ZENDPORTID + ',' + row.ZENDDEVICEID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    //查看端子或端口详细信息
    function getPortDealis(value, type) {
        if (type != null && type != '') {
            //端口
            var url = contextPath + "/netport/netportDetails.do?portid=" + value + "&buttonType=view";
            openDialogView('端口信息', url, '800px', '530px');
        } else {
            //端子
            var url = contextPath + "/idcConnector/getIdcConnectorInfo.do?id=" + value + "&buttonType=view";
            openDialogView('端子信息', url, '800px', '330px');
        }
    }
    //查询
    function searchModels() {
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/idclink/getLinksByAZ",
            queryParams: {name: name, aKey: aKey, zKey: zKey,portStr:portStr,wlRackId:wlRackId}
        });
    }
    //删除链路调用方法
    function doSubmit(){
        var rows = $('#table').datagrid('getSelections');
        var arr = [];
        for(var i = 0;i<rows.length;i++){
            arr.push(rows[i].ID);
        }
        if (rows.length<1) {
            alert("请选择要删除的链路");
            return;
        }else{
            layer.confirm('是否确认删除',{ btn : [ '确定', '取消' ]},function(index){
                $.post(contextPath +'/idclink/deleteLinkByParams',{ids:arr.join(",")},function(result){
                    alert(result.msg);
                    if (result.state) {
                        var parentWin = top.winref[window.name];
                        top[parentWin].reloadWin();
                        var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        top.layer.close(index); //再执行关闭
                    }
                });
                layer.close(index);
            });
        }
    }
</script>
</body>
</html>