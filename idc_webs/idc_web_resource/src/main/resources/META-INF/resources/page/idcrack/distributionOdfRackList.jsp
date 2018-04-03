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
    <title>ODF机架信息</title>
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
               pageSize:20,pageList:[10,15,20,50],striped:true,
               fitColumns:true,toolbar:'#toolbar'
              ">
            <thead>
            <tr>
                <th data-options="field:'ID',hidden:true,width:100"></th>
                <th data-options="field:'NAME',width:230">机架名称</th>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center',border:false" style="padding-left:2px;">
        <table id="connectorTable" class="easyui-datagrid"
               data-options="fit:true,rownumbers:true,striped:true,singleSelect:true,
               <%--pagination:true,pageSize:15,pageList:[15,30,50,100],--%>
               fitColumns:true,toolbar:'#toolbarConn'
              ">
            <thead>
            <tr>
                <th data-options="field:'ID',hidden:'true'"></th>
                <th data-options="field:'NAME',width:250">端子名称</th>
                <th data-options="field:'CONNECTORTYPE',width:100">连接类型</th>
                <th data-options="field:'TYPE',width:100">端子类型</th>
                <th data-options="field:'PORT_MODE',width:100">光口模式</th>
                <%--<th data-options="field:'STATUS',width:100,formatter:statusDealis">业务状态</th>--%>
                <%--<th data-options="field:'TICKET_ID',width:200">工单编号</th>--%>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="toolbar" style="height: 28px;" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="名称/编码"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>
</div>
<div id="toolbarConn" style="height: 28px;" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="connectorName" class="param-input" placeholder="名称/编码"/>
    </div>
    <div class="btn-cls-search" onClick="searchConnectorModels();"></div>
</div>
<script type="text/javascript" language="JavaScript">
    //A端机架ID
    var rackIdAs = "${rackIds}";
    //A端端口ID
    var portIdA = "${portIdA}";
    //机架所在的机房ID
    var roomId = "${roomId}";
    //当前单击的机架ID
    var rackIdForConn = 0;
    //用于防止单双击事件冲突问题
    var timeFn = null;
    $(function(){
        //绑定双击事件
        $('#table').datagrid({
            url:contextPath+"/idcRack/distributionOdfRackList.do",
            queryParams:{roomId:roomId},
            onDblClickRow: function (index, row) {
                clearTimeout(timeFn);
                var url = contextPath + '/idcRack/idcRackDetails.do?rackId=' + row.ID + '&businesstype=df&buttonType=view';
                openDialogShowView2d('机架信息', url, '800px', '400px', '查看机架视图');
            },
            onClickRow: function (index, row) {
                clearTimeout(timeFn);
                timeFn = setTimeout(function () {
                    rackIdForConn = row.ID;
                    $("#connectorName").val('');
                    $('#connectorTable').datagrid({
                        url: contextPath + '/idcConnector/distributionList.do',
                        queryParams: {name: '', rackId: row.ID}
                    });
                }, 200);
            }
        });
    })
    //业务状态
    function statusDealis(value,row,index){
        if(value == 20){
            return '可用';
        }else if(value == 30){
            return '预留';
        }else if(value == 50){
            return '预占';
        }else if(value == 55){
            return '已停机';
        }else if(value == 60){
            return '在服';
        }else{
            return '';
        }
    }
    //查询
    function searchModels(){
        var name = $("#buiName").val();
        $('#table').datagrid({
            url:contextPath + "/idcRack/distributionOdfRackList.do",
            queryParams:{name:name,roomId:roomId}
        });
    }
    function searchConnectorModels() {
        var name = $("#connectorName").val();
        $('#connectorTable').datagrid({
            url: contextPath + '/idcConnector/distributionList.do',
            queryParams: {name: name, rackId: rackIdForConn}
        });
    }
    //建立连接关系 topo
    function doSubmit() {
        var rowArr = new Array();
        var rows = $('#connectorTable').datagrid('getChecked');
        if (rows.length < 1) {
            layer.msg("没有选择端子");
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].ID);
        }
        $.post(contextPath + "/idcConnector/bindConnectionToIdcLink.do", {
            ids: rowArr.join(','),
            roomId: roomId,
            odfRackId: rackIdForConn,
            portIdA: portIdA,
            rackIds: rackIdAs
        }, function (result) {
            if (result.state) {
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