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
                   onLoadSuccess:loadsuccess,
                   pageSize:15,pageList:[10,15,20,50],pagination:true,
                   fitColumns:false,toolbar:'#toolbar',url:'<%=contextPath %>/idcRack/getRackListByRackIds?rackIds=${rackIds}',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'ID',hidden:true"></th>
                    <th data-options="field:'NAME',width:200">机架名称</th>
                    <th data-options="field:'SITENAME',width:200,formatter:sitenameDealis">所属机房</th>
                    <th data-options="field:'ROOMAREAID',width:100">所属VIP机房</th>
                    <th data-options="field:'DIRECTION',width:100,formatter:directionDealis">机架方向</th>
                    <th data-options="field:'USEFOR',width:100,formatter:useforDealis">用途</th>
                    <th data-options="field:'RENTTYPE',width:100">出租类型</th>
                    <th data-options="field:'ARRANGEMENT',width:100,formatter:arrangementDealis">机位顺序</th>
                    <th data-options="field:'STATUS',width:100,formatter:statusDealis">业务状态</th>
                    <th data-options="field:'IPRESOURCE',width:100">IP资源</th>
                    <th data-options="field:'TOPPORTPROPERTY',width:100">上联端口</th>
                    <th data-options="field:'BUSINESSTYPE_ID',width:100,formatter:businesstypeDealis">机架类型</th>
                    <th data-options="field:'PDU_POWERTYPE',width:100">电源类型</th>
                    <th data-options="field:'RATEDELECTRICENERGY',width:100">额定电量KWH</th>
                    <th data-options="field:'ELECTRICTYPE',width:100">用电类型</th>
                    <th data-options="field:'ELECTRICENERGY',width:100">电量KWH</th>
                    <th data-options="field:'APPARENETPOWER',width:100">电量KVA</th>
                    <th data-options="field:'ISRACKINSTALLED',width:100,formatter:israckInstalledDealis">机架是否已安装</th>
                    <th data-options="field:'HEIGHT',width:100">机架U数</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="名称/编码"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
</div>
<script type="text/javascript" language="JavaScript">
    var rackIds = "${rackIds}";
    $(function () {
        $('#table').datagrid({
            //绑定双击事件
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + row.ID + "&businesstype=other&buttonType=view";
                openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
            }
        });
    })
    //机架是否安装
    function israckInstalledDealis(value, row, index) {
        if (value == 0) {
            return '未安装';
        } else if (value == 1) {
            return '已安装';
        } else {
            return '';
        }
    }
    //机架类型
    function businesstypeDealis(value, row, index) {
        if (value == 'equipment') {
            return '客户机柜';
        } else if (value == 'df' && row.DFTYPE == 'ddf') {
            return 'DDF架';
        } else if (value == 'df' && row.DFTYPE == 'odf') {
            return 'ODF架';
        } else if (value == 'cabinet') {
            return '网络柜';
        } else if (value == 'df' && row.DFTYPE == 'wiring') {
            return '配线柜';
        } else {
            return '';
        }
    }
    //业务状态
    function statusDealis(value, row, index) {
        if (value == 20) {
            return '可用';
        } else if (value == 30) {
            return '预留';
        } else if (value == 40) {
            return '空闲';
        } else if (value == 50) {
            return '预占';
        } else if (value == 55) {
            return '已停机';
        } else if (value == 60) {
            return '在服';
        } else if (value == 110) {
            return '不可用';
        } else {
            return '';
        }
    }
    //机位顺序
    function arrangementDealis(value, row, index) {
        if (value == 1) {
            return '从下至上';
        } else if (value == 2) {
            return '至上而下';
        } else {
            return '';
        }
    }
    //用途
    function useforDealis(value, row, index) {
        if (value == 1) {
            return '自用';
        } else if (value == 2) {
            return '客用';
        } else {
            return '';
        }
    }
    //机架方向
    function directionDealis(value, row, index) {
        if (value == 1) {
            return 'Right';
        } else if (value == 2) {
            return 'Up';
        } else if (value == 3) {
            return 'Down';
        } else if (value == 4) {
            return 'left';
        } else {
            return '';
        }
    }
    //所属机房
    function sitenameDealis(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="showRoom(' + row.BID + ')">' + value + '</a>';
        } else {
            return '';
        }
    }
    function showRoom(value) {
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + value + "&buttonType=view";
        openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
    }
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
            url: contextPath + "/idcRack/getRackListByRackIds",
            queryParams: {name: name, rackIds: rackIds}
        });
    }
</script>
</body>
</html>