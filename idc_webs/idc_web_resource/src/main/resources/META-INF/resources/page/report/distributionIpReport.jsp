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
    <title>IP占用报表</title>
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
                   onLoadSuccess:loadsuccess,loadFilter:loadFilterData,pageSize:20,pageList:[10,15,20,50],striped:true,
                   fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/resourceReport/distributionIpReport'
                  ">
                <thead>
                <tr>
                    <th data-options="field:'TICKETID',hidden:'true'"></th>
                    <th data-options="field:'BUSNAME',width:250">业务名称</th>
                    <th data-options="field:'BEGINIP',width:130">IP起</th>
                    <th data-options="field:'ENDIP',width:130">IP止</th>
                    <th data-options="field:'BANDWIDTH',width:100">占用带宽</th>
                    <th data-options="field:'LINKNUM',width:100">链路条数</th>
                    <th data-options="field:'RACKNAMESTR',width:200,formatter:rackStrInfo">占用机架</th>
                    <th data-options="field:'CONTACTNAME',width:100">联系人</th>
                    <th data-options="field:'CONTACTPHONE',width:100">电话</th>
                    <th data-options="field:'CONTACTEMAIL',width:200">邮箱</th>
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
    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_usedip_report_export','ROLE_admin')">
            <div class="btn-cls-common" onclick="exportData()">导 出</div>
        </sec:authorize>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    function loadFilterData(data) {
        if (data && data["rows"].length > 0) {
            var otherCos = ["BUSNAME", "BANDWIDTH", "LINKNUM", "RACKNAMESTR", "CONTACTNAME", "CONTACTPHONE", "CONTACTEMAIL"];
            sumDataColunmByKey('TICKETID', data, otherCos);
        }
        return data;
    }
    function sumDataColunmByKey(key, data, otherCos) {
        var preidx = 0;
        var rowspan = 0;
        var tempzgid = null;
        var rsdata = [];
        for (var i = 0; i < data["rows"].length; i++) {
            var item = data["rows"][i];
            if (tempzgid == null) {
                tempzgid = item[key];
                rowspan += 1;
            } else if (tempzgid != item[key]) {
                rsdata.push({preidx: preidx, rowspan: rowspan});
                tempzgid = item[key];
                preidx += rowspan;
                rowspan = 1;
            } else {
                rowspan += 1;
            }
            if ((data["rows"].length - 1) == i) {
                if (rsdata && (rsdata.length == 0 || (rsdata.length > 0 && rsdata[rsdata.length - 1]["preidx"] != preidx))) {
                    rsdata.push({preidx: preidx, rowspan: rowspan});
                }
            }
        }
        data["mergeCells"] = {cols: otherCos, rsdata: rsdata};
    }
    function loadsuccess(data) {
        if (data && data["mergeCells"]) {
            for (var i = 0; i < data["mergeCells"].rsdata.length; i++) {
                var rd = data["mergeCells"].rsdata[i];
                for (var j = 0; j < data["mergeCells"].cols.length; j++) {
                    var cd = data["mergeCells"].cols[j];
                    $("#table").datagrid('mergeCells', {
                        index: rd["preidx"],
                        field: cd,
                        rowspan: rd["rowspan"]
                    });
                }
            }
        }
    }
    //rackStr
    function rackStrInfo(value, row, index) {
        if (value != null && value != '') {
            return '<a href="javascript:void(0)" class="easyui-tooltip" title="' + value + '">' + value + '</a>';
        } else {
            return "";
        }
    }
    //查询
    function searchModels() {
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/resourceReport/distributionIpReport",
            queryParams: {name: name}
        });
    }
    //导出
    function exportData() {
        window.open(contextPath + '/resourceReport/exportUsedIpReportData');
    }
</script>
</body>
</html>