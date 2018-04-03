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
    <title>配动力MCB</title>
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
    <div class="easyui-layout" fit="true">
        <!-- PDF机架-->
        <div data-options="region:'west',border:false" style="padding-left: 2px;width: 330px;">
            <table id="pdfTable" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,pagination:true,singleSelect:true,
               onLoadSuccess:loadsuccessRack,pageSize:15,pageList:[10,15,20,50],striped:true,
               fitColumns:true,toolbar:'#toolbar1',url:'<%=contextPath %>/idcRack/distributionPdfRackList?roomId=${roomId}',
              ">
                <thead>
                <tr>
                    <th data-options="field:'id',hidden:true,width:100"></th>
                    <th data-options="field:'name',width:250">机架名称</th>
                </tr>
                </thead>
            </table>
        </div>
        <!-- MCB-->
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="mcbTable" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:true,striped:true,
                   <%--pageSize:15,pageList:[10,15,20,50],pagination:true,--%>
                   fitColumns:false,toolbar:'#toolbar',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'ID',hidden:'true'"></th>
                    <th data-options="field:'NAME',width:200">PDU名称</th>
                    <th data-options="field:'PWRPWRSTATUS',width:100,formatter:pwrPwrstatus">PDU使用状态</th>
                    <th data-options="field:'INSTALLNAME',width:210,formatter:installFormatter">PDF机架</th>
                    <th data-options="field:'SERVICENAME',width:210,formatter:serviceFormatter">服务机架</th>
                    <th data-options="field:'SYSDESCR',width:100">PDU描述</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar1" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="pdfBuiName" class="param-input" placeholder="名称/编码"/>
    </div>
    <div class="btn-cls-search" onClick="searchRackModels();"></div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="mcbBuiName" class="param-input" placeholder="编码"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
</div>
<script type="text/javascript" language="JavaScript">
    var roomId = "${roomId}";
    //当前单击的机架ID
    var pdfRackId = 0;
    //当前单击的机架ID
    var serviceRackId = ${rackId};
    //用于防止单双击事件冲突问题
    var timeFn = null;
    $(function () {
        $('#pdfTable').datagrid({
            onDblClickRow: function (index, row) {
                clearTimeout(timeFn);
                var url = contextPath + '/idcRack/idcRackDetails.do?rackId=' + row.id + '&businesstype=pdu&buttonType=view';
                openDialogShowView2d('机架信息', url, '800px', '400px', '查看机架视图');
            },
            onClickRow: function (index, row) {
                clearTimeout(timeFn);
                timeFn = setTimeout(function () {
                    pdfRackId = row.id;
                    $("#mcbBuiName").val('');
                    $('#mcbTable').datagrid({
                        url: contextPath + '/idcmcb/distributionIdcMcbList',
                        queryParams: {name: '', rackId: pdfRackId}
                    });
                }, 200);
            }
        });
    });
    function doSubmit() {
        var result = {};
        var rowArr = [];
        var rows = $('#mcbTable').datagrid('getChecked');
        if (rows == null || rows.length < 1) {
            alert("请选择MCB");
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].ID);
        }
        result['ajaxBindRackMCBResourceParam'] = {
            ids:rowArr.join(","),
            serviceRackId:serviceRackId
        };
        result['rows'] =  rows;
        return result;
    }
    function installFormatter(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="getRackDealis(' + row.PWRINSTALLEDRACKID + ',1)">' + value + '</a>';
        } else {
            return '';
        }
    }
    function serviceFormatter(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" onclick="getRackDealis(' + row.PWRSERVICERACKID + ',2)">' + value + '</a>';
        } else {
            return '';
        }
    }
    function getRackDealis(value, type) {
        if (type == 1) {
            openDialogShowView2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + value + '&businesstype=pdu&buttonType=view', '800px', '400px', '查看机架视图');
        } else {
            var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + value + "&businesstype=other&buttonType=view";
            openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
        }
    }
    function pwrPwrstatus(value, row, index) {
        if (value == 20) {
            return '可用';
        } else if (value == 50) {
            return '预占';
        } else if (value == 55) {
            return '预释';
        } else if (value == 60) {
            return '在服';
        } else if (value == 110) {
            return '不可用';
        } else {
            return '';
        }
    }
    function loadsuccessRack(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#pdfTable').datagrid('fixRowHeight');
    }
    //查询MCB
    function searchModels() {
        var name = $("#mcbBuiName").val();
        $('#mcbTable').datagrid({
            url: contextPath + "/idcmcb/distributionIdcMcbList",
            queryParams: {name: name, rackId: pdfRackId}
        });
    }
    //机架
    function searchRackModels() {
        var name = $("#pdfBuiName").val();
        $('#pdfTable').datagrid({
            url: contextPath + "/idcRack/distributionPdfRackList",
            queryParams: {name: name, roomId: roomId}
        });
    }
</script>
</body>
</html>