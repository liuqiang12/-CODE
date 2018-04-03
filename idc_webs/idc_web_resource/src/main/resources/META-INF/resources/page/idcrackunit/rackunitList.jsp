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
    <title>机位信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/locale/easyui-lang-zh_CN_least.js"></script>
    <%--<script type="text/javascript"--%>
    <%--src="<%=request.getContextPath() %>/js/keyevent/keyupordown.js"></script>--%>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
    <script type="text/javascript">
        var tableId = "rackunitTable";
        $(function () {
            //加载机位视图
            $("#racklayoutIframeId").attr("src", contextPath + "/racklayout/" + rackId + "?type=view");
            //设置某些列不可被选中
            $("#rackunitTable").datagrid({
                rowStyler: function (index, row) {
                    if (row.STATUS != '20') {
                        return 'background-color:#dddddd;color: #9d9d9d';
                    }
                }
            })
            //是否显示提示
            $("#rackunittabsId").tabs({
                onSelect:function(title,index){
                    if(index==0){
                        $("#flot_tip").show();
                    }else{
                        $("#flot_tip").hide();
                    }
                }
            })
        })
    </script>
</head>
<body style="margin:0;padding:2px;overflow:hidden;">
<!-- 提示信息 -->
<div id="flot_tip" class="tip_float_right_info"
     style="height:50px;position: absolute;z-index: 2;top:-12px;right: 0px;color: #7a983c;font-size: 10px;">
    <marquee id="affiche" align="right" behavior="scroll" direction="left" height="25" width="500" hspace="20"
             vspace="20" loop="-1" scrollamount="10" scrolldelay="300" onMouseOut="this.start()"
             onMouseOver="this.stop()">
        按住shift/ctrl键，可进行多选
    </marquee>
</div>
<!-- 机位列表 -->
<div class="easyui-panel" fit="true">
    <div class="easyui-tabs" fit="true" id="rackunittabsId">
        <div title="机位信息" data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="rackunitTable" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,striped:true,singleSelect:true,
                   onLoadSuccess:loadsuccess,onClickRow:fun,
                   <%--pageSize:15,pageList:[10,15,20,50],pagination:true,--%>
                   fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/idcRackunit/queryRackunitList?rackId=${rackId}',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'ID',hidden:'true'"></th>
                    <th data-options="field:'CODE',width:200">机位编码</th>
                    <th data-options="field:'UNO',width:100">机位号</th>
                    <th data-options="field:'CUSTOMERNAME',width:100">客户名称</th>
                    <th data-options="field:'DNAME',width:250,formatter:deviceDealis">设备</th>
                    <%--<th data-options="field:'SITENAME',width:200,formatter:roomDealis">机房</th>--%>
                    <%--<th data-options="field:'ORDERNO',width:100">顺序</th>--%>
                    <%--<th data-options="field:'IDCNO',width:100">IDC编号</th>--%>
                    <th data-options="field:'STATUS',width:100,formatter:statusDealis">状态</th>
                    <%--<th data-options="field:'USEFOR',width:100">用途</th>--%>
                </tr>
                </thead>
            </table>
        </div>
        <div title="U位占用视图">
            <iframe id="racklayoutIframeId" style="width: 1018px;height:680px;"></iframe>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="rackunitBuiName" class="param-input" placeholder="编码"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
    <%-- 快速选择U位--%>
    <div class="param-fieldset">
        <input type="text" id="startU" class="param-input" placeholder="起始U"/>
        <label>U-</label>
        <input type="text" id="endU" class="param-input" placeholder="结束U"/>
        <label>U</label>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    var rackId = "${rackId}";
    //只是需要获取ID的情况
    function doSubmit() {
        var startU = $("#startU").val();
        var endU = $("#endU").val();
        if (isNaN(startU) || isNaN(endU)) {
            layer.msg("请输入数字");
            return;
        }
        if (startU != null && startU != '' && endU != null && endU != '') {
            var arr = [];
            var rows = $('#rackunitTable').datagrid('getRows');
            for (var i = startU - 1; i < endU; i++) {
                if (rows[i].STATUS != '20') {
                    layer.msg("机位" + i + 1 + "已占用,请从新输入");
                    return;
                }
                arr.push(rows[i]);
            }
            console.log(arr);
            return arr;
        } else {
            var rows = $('#rackunitTable').datagrid('getChecked');
            if (rows == null || rows.length < 1) {
                alert("请选择机位");
                return;
            }
            console.log(rows);
            return rows;
        }
    }
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
        if (type == 1) {
            url = contextPath + "/device/deviceDetails.do?id=" + value + "&buttonType=view&deviceclass=1";
        } else {
            url = contextPath + "/device/deviceDetails.do?id=" + value + "&buttonType=view&deviceclass=2";
        }
        openDialogView('设备信息', url, '800px', '530px');
    }
    function statusDealis(value, row, index) {
        if (value == 20) {
            return "可用";
        } else if (value == 50) {
            return "预占";
        } else if (value == 55) {
            return "占用";
        } else if (value == 60) {
            return "在服";
        } else if (value == 110) {
            return "不可用";
        } else {
            return '';
        }
    }
    //机房
    function roomDealis(value, row, index) {
        if (value != null && value != "") {
            return '<a href="javascript:void(0)" onclick="showRoomDealis(' + row.ROOMID + ')">' + value + '</a>';
        } else {
            return "";
        }
    }
    function showRoomDealis(value) {
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + value + "&buttonType=view";
        openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
    }
    function loadsuccess(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#rackunitTable').datagrid('fixRowHeight');
    }
    //查询机位
    function searchModels() {
        var code = $("#rackunitBuiName").val();
        $('#rackunitTable').datagrid({
            url: contextPath + "/idcRackunit/queryRackunitList",
            queryParams: {code: code, rackId: rackId}
        });
    }
</script>
</body>
</html>