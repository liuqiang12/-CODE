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
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
        .sherchInput{
            display: block;
            padding: 2px 6px;
            margin: 6px 7px 0px;
            width: 86%;
            font-size: 12px;
            line-height: 1.42857143;
            color: #555;
            border-radius: 3px;
            background-color: #fff;
            background-image: none;
            border: 1px solid #CCC;
            /*-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);*/
            box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
            /*-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;*/
            /*-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;*/
            /*transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;*/
        }
    </style>
    <script type="text/javascript">
        var CurrNode = null;
        $(function () {
            var dom = $("#rtree");
            if (dom != null) {
                dom.rtree({
                    r_type: 4,
                    locationId:locationId,
                    roomIds:roomIds,
                    isShowRack:'N',
                    deviceclass:1,
                    onClick: function (iteam,treeId,treeNode) {
                        if (treeNode.id.indexOf("device_") > -1) {
                            CurrNode = treeNode;
                            $("#portName").val('');
                            searchPortModels();
                        }
                    },
                    view: { showLine: false, fontCss: getFontCss }
                })
            }
        })
        var nodeList = [];
        function callNumber(value){
            var zTree = $.fn.zTree.getZTreeObj("rtree");
            updateNodes(false);
            if (value === ""){
                return;
            }
            nodeList = zTree.getNodesByParamFuzzy("name", value); //调用ztree的模糊查询功能，得到符合条件的节点
            updateNodes(true); //更新节点
        }
        function updateNodes(flag) {
            var zTree = $.fn.zTree.getZTreeObj("rtree");
            for( var i=0, l=nodeList.length; i<l; i++) {
                nodeList[i].highlight = flag;
                zTree.expandNode(nodeList[i].getParentNode(), true, false, false); //将搜索到的节点的父节点展开
                zTree.updateNode(nodeList[i]); //更新节点数据，主要用于该节点显示属性的更新
            }
        }
        function getFontCss(treeId, treeNode) {
            return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
        }
    </script>
</head>
<body style="margin:0;padding:2px;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',border:false" style="padding-left: 2px;width: 250px;height: 100%;overflow-x: scroll;overflow-y: scroll;">
        <div style="">
            <input type="text" class="sherchInput" id="sherchKey" onkeyup="callNumber(this.value)" placeholder="Search...">
        </div>
        <div>
            <ul id="rtree" class="ztree"></ul>
        </div>
    </div>
    <div data-options="region:'center',border:false" style="padding-left: 2px;">
        <table id="netPortTable" class="easyui-datagrid"
               data-options="fit:true,rownumbers:false,striped:true,singleSelect:true,
                   pagination:true,pageSize:20,pageList:[20,50,100,200,300,500],
                   onLoadSuccess:loadsuccessPort,onClickRow:fun,
                   fitColumns:false,toolbar:'#toolbarPort'
                  ">
            <thead>
            <tr>
                <th data-options="field:'PORTID',hidden:'true'"></th>
                <th data-options="field:'PORTNAME',width:200">端口名称</th>
                <th data-options="field:'ALIAS',width:200,formatter:aliasDetail">端口别名</th>
                <th data-options="field:'PORTTYPE',width:100">端口类型</th>
                <th data-options="field:'STATUS',width:100,formatter:statusDetail">资源占用状态</th>
                <%--<th data-options="field:'CUSTOMERNAME',width:200,formatter:customerInfo">客户名称</th>--%>
                <%--<th data-options="field:'SIDEDEVICE',width:100">对端设备</th>--%>
                <%--<th data-options="field:'IP',width:100">端口对应IP</th>--%>
                <%--<th data-options="field:'MAC',width:100">Mac地址</th>--%>
                <%--<th data-options="field:'NETMASK',width:100">子网掩码</th>--%>
                <th data-options="field:'BANDWIDTH',width:100">端口带宽(Mbps)</th>
                <%--<th data-options="field:'ASSIGNATION',width:100">分派带宽(Mbps)</th>--%>
                <%--<th data-options="field:'TICKETID',width:100">工单编号</th>--%>
                <th data-options="field:'DESCR',width:100">端口描述</th>
                <th data-options="field:'NOTE',width:100">备注</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="toolbarPort" style="height: 28px;" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="portName" class="param-input" placeholder="名称/编码" style="width: 200px;"/>
    </div>
    <div class="btn-cls-search" onClick="searchPortModels();"></div>
    <%--<c:if test="${not empty rackIds}">--%>
        <%--<div class="param-actionset">--%>
            <%--<div class="btn-cls-common" onClick="bingConnecter();">跳 纤</div>--%>
        <%--</div>--%>
    <%--</c:if>--%>
</div>
<script type="text/javascript" language="JavaScript">
    var tableId = "netPortTable";
    var rackIds = "${rackIds}";
    var roomIds = "${roomIds}";
    var locationId = "${locationId}";
    var status = "${status}";
    var rowObj = [];
    function doSubmit() {
        var rows = $('#netPortTable').datagrid('getChecked');
        if(rows==null){
            rows =[];
        }
        if(rowObj.length>0){
            for(var i=0;i<rowObj.length;i++){
                for(var j=0;j<rowObj[i].length;j++){
                    rows.push(rowObj[i][j]);
                }
            }
        }
        console.log(rows);
        return rows;
    }
    //跳纤 先选择要跳纤的机架在跳纤
    function bingConnecter(){
        var url = contextPath+'/idcRack/preDsitributionJumpFiber';
        var data = {rackIds:rackIds,roomIds:roomIds};
        openDialogUsePost("跳纤机架", url, "70%", "70%", data,'jumpFiber');

    }
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
    //加载端口列表
    function loadsuccessPort(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#netPortTable').datagrid('fixRowHeight');
    }
    //查询
    function searchPortModels() {
        var name = $("#portName").val();
        var queryParams = {name:name,status:status};
        if (typeof CurrNode!='undefined' && CurrNode!= null) {
            var id = CurrNode.id;
            var datas = id.split("_");
            queryParams.deviceId = datas[1];
        }else{
            alert("请先选择设备！");
            $("#portName").val('');
            return;
        }
        $('#netPortTable').datagrid({
            url: contextPath + '/netport/list.do',
            queryParams: queryParams
        });
    }
</script>
</body>
</html>