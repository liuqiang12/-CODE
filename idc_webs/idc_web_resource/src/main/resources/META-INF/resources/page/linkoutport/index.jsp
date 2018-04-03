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
    <title>链路信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
</head>
<body style="margin:0;padding:2px;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="table" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,pagination:true,singleSelect:true,onClickRow:fun,
                   onLoadSuccess:loadsuccess,pageSize:20,pageList:[10,15,20,50],striped:true,
                   fitColumns:false,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/idcLinkOutport/list'
                  ">
                <thead>
                <tr>
                    <th data-options="field:'IDCLINKINFOID',hidden:'true'"></th>
                    <th data-options="field:'IDCTRANSMISSIONCODE',width:150">调单号</th>
                    <th data-options="field:'ATTACHNAME',width:150,formatter:attachnameLink">附件名称</th>
                    <th data-options="field:'IDCLINKFROMAREA',width:150">IDC名称</th>
                    <th data-options="field:'IDCLINKROOMNAME',width:150">IDC机房名称</th>
                    <th data-options="field:'IDCLINKCABINETPOSITION',width:150">设备机柜位置</th>
                    <th data-options="field:'IDCLINKLOCALROUTENAME',width:200">出局路由器/其他出局数通设备</th>
                    <th data-options="field:'IDCLINKDEVICEMODEL',width:100">本端设备型号</th>
                    <th data-options="field:'IDCLINKTAPEWIDTH',width:100">本端链路带宽</th>
                    <th data-options="field:'IDCLINKLOCALODFPORT',width:100">本端ODF端口</th>
                    <th data-options="field:'IDCLINKLOCALDEVICEPORT',width:150">本端设备端口</th>
                    <th data-options="field:'IDCLINKTARGETROOMNAME',width:150">省核心机房名称</th>
                    <th data-options="field:'IDCLINKTARGETROUTENAME',width:200">上联省核心路由器名称</th>
                    <th data-options="field:'IDCLINKTARGETDEVICEMODEL',width:100">对端设备型号</th>
                    <th data-options="field:'IDCLINKTARGETODFPORT',width:100">对端ODF端口</th>
                    <th data-options="field:'IDCLINKTARGETDEVICEPORT',width:150">对端设备端口</th>
                    <th data-options="field:'NOTE',width:200">备注</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="IDC名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>

    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_resource_linkOutport_add','ROLE_admin')">
            <div class="btn-cls-common" onclick="saveLinkOutport('add')">新 增</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_resource_linkOutport_edit','ROLE_admin')">
            <div class="btn-cls-common" onclick="saveLinkOutport('update')">修 改</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_resource_linkOutport_del','ROLE_admin')">
            <div class="btn-cls-common" onclick="deleteLinkOutport()">删 除</div>
        </sec:authorize>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    var tableId = "table";
    $(function () {
        //绑定双击事件
        $("#table").datagrid({
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcLinkOutport/getIdcLinkOutportInfo?id=" + row.IDCLINKINFOID + "&buttonType=view";
                if (row.ATTACHNAME != undefined) {
                    url += "&attachname=" + encodeURI(encodeURI(row.ATTACHNAME));
                }
                openDialogView('链路信息', url, '1000px', '530px');
            }
        })
    });
    //加载链路列表
    function loadsuccess(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight');
    }
    //下载附件
    function attachnameLink(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="downloadAttachmentn(' + row.IDCTRANSMISSIONID + ')">' + value + '</a>';
        } else {
            return value;
        }
    }
    //下载附件
    function downloadAttachmentn(value) {
        console.log("下载附件");
        window.open(contextPath + '/idcLinkOutport/downLoadFileByTransmissionId?idcTransmissionId=' + value);
    }
    //查询
    function searchModels() {
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/idcLinkOutport/list",
            queryParams: {name: name}
        });
    }
    //保存链路信息
    function saveLinkOutport(type) {
        var url = contextPath + "/idcLinkOutport/getIdcLinkOutportInfo";
        if (type == 'update') {//修改链路信息
            var rows = $('#table').datagrid('getChecked');
            if (rows.length < 1) {
                layer.msg("没有选择链路");
                return;
            } else if (rows.length > 1) {
                layer.msg("只能选择一个链路");
                return;
            }
            url += "?id=" + rows[0].IDCLINKINFOID + "&buttonType=update";
            if (rows[0].ATTACHNAME != undefined) {
                url += "&attachname=" + encodeURI(encodeURI(rows[0].ATTACHNAME));
            }
        } else {
            url += "?buttonType=add";
        }
        openDialog("链路信息", url, "1000px", "530px");
    }
    //删除链路信息
    function deleteLinkOutport() {
        var arr = [];
        var trr = [];
        var rows = $('#table').datagrid('getChecked');
        if (rows.length < 1) {
            layer.msg("请选择要删除的选择链路");
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            arr.push(rows[i].IDCLINKINFOID);
            trr.push(rows[i].IDCTRANSMISSIONID);
        }
        layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
            $.post(contextPath + '/idcLinkOutport/deleteLinkOutport', {
                linkIds: arr.join(','),
                transIds: trr.join(',')
            }, function (result) {
                alert(result.msg);
                $('#table').datagrid({
                    url: contextPath + "/idcLinkOutport/list"
                });
            });
            layer.close(index);
        });
    }
</script>
</body>
</html>