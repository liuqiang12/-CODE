<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>流程管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <style>
        .col-xs-12 >table,td,th {  border: 1px solid #8DB9DB; padding:5px; border-collapse: collapse; font-size:16px; }
    </style>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-tabs" style="height: 100%;width: 100%" data-options="fit:true">
    <div title="客户信息" style="padding-left: 2px;" data-options="fit:true,border:false">

    </div>
    <div title="业务信息" style="padding-left: 2px;" data-options="fit:true,border:false" >

    </div>
    <div title="客户需求表" style="padding-left: 2px;" data-options="fit:true,border:false" >

    </div>
    <div title="资源配置" style="padding-left: 2px;" data-options="fit:true,border:false" >
         <div class="easyui-tabs" data-options="fit:true">
             <div title="机架信息" style="padding-left: 2px;" data-options="fit:true,border:false" >
                 <table id="table2a" class="easyui-datagrid"
                 data-options="fit:true,rownumbers:true,pagination:true,
                 onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                 toolbar:'#toolbar2a',url:'<%=request.getContextPath() %>/idcRack/pdfList.do',
                 onDblClickRow:function(index,row){alert(row.ID)},">
                     <thead>
                         <tr>
                             <th data-options="field:'ID',hidden:true" style="width:10%;"></th>
                             <th data-options="field:'NAME'" style="width:20%;">机架名称</th>
                             <th data-options="field:'STATUS',formatter:statusDealisValue" style="width:10%;">业务状态</th>
                             <th data-options="field:'SITENAME',formatter:linkRoomDealis" style="width:20%;">所属机房</th>
                             <th data-options="field:'MNAME',formatter:linkRackModelDealis" style="width:10%;">机架型号</th>
                             <th data-options="field:'DIRECTION',formatter:directionDealis" style="width:10%;">机架方向</th>
                             <th data-options="field:'CODE'" style="width:10%;">机架尺寸</th>
                             <th data-options="field:'CODE'" style="width:10%;">机架品牌</th>
                         </tr>
                     </thead>
                 </table>
             </div>
             <div title="MCB" style="padding-left: 2px;" data-options="fit:true,border:false" >
                 <table id="table2b" class="easyui-datagrid"
                        data-options="fit:true,rownumbers:true,pagination:true,
                 onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                 toolbar:'#toolbar2b',url:'<%=request.getContextPath() %>/idcmcb/list.do',
                 onDblClickRow:function(index,row){alert(row.ID)},">
                     <thead>
                     <tr>
                         <th data-options="field:'ID',hidden:true" style="width:5%;"></th>
                         <th data-options="field:'SERVICENAME'" style="width:20%;">所属PDU</th>
                         <th data-options="field:'PWRMCBNO'" style="width:10%;">MCB名称</th>
                         <th data-options="field:'PWRPWRSTATUS'" style="width:5%;">业务状态</th>
                         <th data-options="field:'INSTALLNAME'" style="width:20%;">安装机架</th>
                         <th data-options="field:'PWRMCBNO'" style="width:10%;">电源类型</th>
                         <th data-options="field:'PWRMCBNO'" style="width:10%;">类型</th>
                         <th data-options="field:'PWRMCBNO'" style="width:10%;">额定电流</th>
                         <th data-options="field:'PWRMCBNO'" style="width:10%;">UPS编号</th>
                     </tr>
                     </thead>
                 </table>
             </div>
             <div title="端口" style="padding-left: 2px;" data-options="fit:true,border:false" >
                 <table id="table2c" class="easyui-datagrid"
                        data-options="fit:true,rownumbers:true,pagination:true,
                 onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                 toolbar:'#toolbar2c',url:'<%=request.getContextPath() %>/idcRack/pdfList.do',
                 onDblClickRow:function(index,row){alert(row.ID)},">
                     <thead>
                     <tr>
                         <th data-options="field:'ID',hidden:true"></th>
                         <th data-options="field:'NAME'" style="width:20%;">名称</th>
                         <th data-options="field:'STATUS'" style="width:20%;">所属设备</th>
                         <th data-options="field:'SITENAME'" style="width:20%;">业务状态</th>
                         <th data-options="field:'STATUS'" style="width:20%;">光电特性</th>
                         <th data-options="field:'DIRECTION'" style="width:20%;">端口速率</th>
                     </tr>
                     </thead>
                 </table>
             </div>
             <div title="IP" style="padding-left: 2px;" data-options="fit:true,border:false" >
                 <table id="table2d" class="easyui-datagrid"
                        data-options="fit:true,rownumbers:true,pagination:true,
                 onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                 toolbar:'#toolbar2d',url:'<%=request.getContextPath() %>/idcRack/pdfList.do',
                 onDblClickRow:function(index,row){alert(row.ID)},">
                     <thead>
                     <tr>
                         <th data-options="field:'ID',hidden:true"></th>
                         <th data-options="field:'NAME'" style="width:25%;">名称</th>
                         <th data-options="field:'STATUS'" style="width:25%;">IP段</th>
                         <th data-options="field:'SITENAME'" style="width:25%;">掩码</th>
                         <th data-options="field:'STATUS'" style="width:25%;">业务状态</th>
                     </tr>
                     </thead>
                 </table>
             </div>
             <div title="设备" style="padding-left: 2px;" data-options="fit:true,border:false" >
                 <table id="table2e" class="easyui-datagrid"
                        data-options="fit:true,rownumbers:true,pagination:true,
                 onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                 toolbar:'#toolbar2e',url:'<%=request.getContextPath() %>/device/list.do',
                 onDblClickRow:function(index,row){alert(row.DEVICEID)},">
                     <thead>
                     <tr>
                         <th data-options="field:'DEVICEID',hidden:true" style="width:5%;"></th>
                         <th data-options="field:'NAME'" style="width:10%;">设备名称</th>
                         <th data-options="field:'RNAME'" style="width:15%;">所属机架</th>
                         <th data-options="field:'CODE'" style="width:15%;">所在机房</th>
                         <th data-options="field:'STATUS',formatter:deviceStatusDealis" style="width:5%;">业务状态</th>
                         <th data-options="field:'CODE'" style="width:10%;">品牌</th>
                         <th data-options="field:'VENDOR',formatter:deviceVendorDealis" style="width:10%;">厂商</th>
                         <th data-options="field:'CODE'" style="width:10%;">SN</th>
                         <th data-options="field:'CODE'" style="width:10%;">资产编号</th>
                         <th data-options="field:'OWNERTYPE',deviceOwnertypeDealis" style="width:10%;">产权性质</th>
                     </tr>
                     </thead>
                 </table>
             </div>
             <%--<div title="IdcRackBlack" style="padding-left: 2px;" data-options="fit:true,border:false" >--%>
                    <%--<c:forEach items="${idcRackList}" var="params">--%>
                        <%--<div style="float: left;width:300px;height:300px;background-color: #5bc0de;border: 1px solid royalblue;margin: 5px;">--%>
                            <%--ID：<c:out value="${params.ID}"/><br/>--%>
                            <%--编码：<c:out value="${params.CODE}"/><br/>--%>
                            <%--机房名称：<a href="javascript:void(0)" onclick="alert('${params.ROOMID}')"><c:out value="${params.SITENAME}"/></a><br/>--%>
                        <%--</div>--%>
                    <%--</c:forEach>--%>
             <%--</div>--%>
         </div>
    </div>
    <div title="资源树" style="padding-left: 2px;" data-options="fit:true,border:false" >

    </div>
    <div title="开通信息" style="padding-left: 2px;" data-options="fit:true,border:false" >

    </div>
    <div title="流程图" style="padding-left: 2px;" data-options="fit:true,border:false" >

    </div>
    <div title="相关工单" style="padding-left: 2px;" data-options="fit:true,border:false" >

    </div>
    <div title="流程日志" style="padding-left: 2px;" data-options="fit:true,border:false" >

    </div>
</div>
<div id="toolbar1" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName1" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('buiName1');"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName1').val('')"></div>
</div>
<div id="toolbar2" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName2" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('buiName2');"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName2').val('')"></div>
</div>
<div id="toolbar3" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName3" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('buiName3');"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName3').val('')"></div>
</div>
<div id="toolbar2a" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName2a" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('buiName2a');"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName2a').val('')"></div>
</div>
<div id="toolbar2b" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName2b" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('buiName2b');"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName2b').val('')"></div>
</div>
<div id="toolbar2c" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName2c" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('buiName2c');"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName2c').val('')"></div>
</div>
<div id="toolbar2d" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName2d" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('buiName2d');"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName2d').val('')"></div>
</div>
<div id="toolbar2e" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName2e" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels('buiName2e');"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName2e').val('')"></div>
</div>
<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree.all-3.5.min.js"></script>
<link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">
<script type="text/javascript" language="JavaScript">
    function loadFilter(data){
        return data;
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table2').datagrid('fixRowHeight')
    }
    //查询
    function searchModels(value) {
        if(value == 'buiName1'){
            var name = $("#buiName1").val();
            $('#table1').datagrid({
                url:contextPath + "/device/list.do?name="+name,
            });
        }else if(value == 'buiName2'){
            var name = $("#buiName2").val();
            $('#table2').datagrid({
                url:contextPath + "/device/list.do?name="+name,
            });
        }else if(value == 'buiName3'){
            var name = $("#buiName3").val();
            $('#table3').datagrid({
                url:contextPath + "/device/list.do?name="+name,
            });
        }else if(value == 'buiName2a'){
            var name = $("#buiName2a").val();
            $('#table2a').datagrid({
                url:contextPath + "/device/list.do?name="+name,
            });
        }else if(value == 'buiName2b'){
            var name = $("#buiName2b").val();
            $('#table2b').datagrid({
                url:contextPath + "/device/list.do?name="+name,
            });
        }else if(value == 'buiName2c'){
            var name = $("#buiName2c").val();
            $('#table2c').datagrid({
                url:contextPath + "/device/list.do?name="+name,
            });
        }else if(value == 'buiName2d'){
            var name = $("#buiName2d").val();
            $('#table2d').datagrid({
                url:contextPath + "/device/list.do?name="+name,
            });
        }else if(value == 'buiName2e'){
            var name = $("#buiName2e").val();
            $('#table2e').datagrid({
                url:contextPath + "/device/list.do?name="+name,
            });
        }

    }
    //机架状态
    function statusDealisValue(value,row,index){
        if(value==20){
            return '空闲';
        }else if(value==30){
            return '预留';
        }else if(value==50){
            return '预占';
        }else if(value==60){
            return '实占';
        }else if(value==70){
            return '欠费停';
        }else if(value==100){
            return '不可用';
        }else{
            return value;
        }
    }
    //机架方向
    function directionDealis(value,row,index){
        if(value==1){
            return 'Right';
        }else if(value==2){
            return 'Up';
        }else if(value==3){
            return 'Down';
        }else if(value==4){
            return 'left';
        }else{
            return value;
        }
    }
    //所属机房
    function linkRoomDealis(value,row,index){
        if(value!=null && value != ''){
            return '<a href="javascript:void(0)" onclick="getRoomDealis('+row.ROOMID+')">' + value + '</a>';
        }else{
            return '';
        }
    }
    function getRoomDealis(value){
        openDialogView('机房信息',  contextPath +'/resource/' + 'machineroom' + "/" + value+"/view",'800px','530px');
    }
    //机架型号
    function linkRackModelDealis(value,row,index){
        if(value!=null && value != ''){
            return '<a href="javascript:void(0)" onclick="getRackModelDealis('+row.RACKMODELID+')">' + value + '</a>';
        }else{
            return '';
        }
    }
    function getRackModelDealis(value){
        //openDialogView('机架信息',  contextPath +'/resource/' + 'idcrack' + "/" + rackId+"/view",'800px','530px');
    }
    //设备状态
    function deviceStatusDealis(value,row,index){
        if(value==20){
            return '空闲';
        }else if(value==30){
            return '预留';
        }else if(value==50){
            return '预占';
        }else if(value==55){
            return '已停机';
        }else if(value==60){
            return '在服';
        }else if(value==110){
            return '不可用';
        }else{
            return value;
        }
    }
    //设备厂商
    function deviceVendorDealis(value,row,index){
        if(value == 0){
            return '华为';
        }else if(value == 1){
            return '思科';
        }else if(value == 2){
            return '阿尔卡特';
        }else if(value == 3){
            return '3Com';
        }else if(value == 4){
            return 'HP';
        }else if(value == 5){
            return 'Linux';
        }else if(value == 6){
            return 'Microsoft';
        }else if(value == 17){
            return 'D-Link';
        }else if(value == 54){
            return 'Juniper';
        }else if(value == 59){
            return 'H3C';
        }else{
            return value;
        }
    }
    //设备产权性质
    function deviceOwnertypeDealis(value,row,index){
        if(value == 1){
            return '自建';
        }else if(value == 2){
            return '租用';
        }else if(value == 3){
            return '客户';
        }else if(value == 4){
            return '自有业务';
        }else{
            return value;
        }
    }
</script>
</body>

</html>