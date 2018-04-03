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
        <table id="roomTable" class="easyui-datagrid"
               data-options="fit:true,rownumbers:false,singleSelect:true,
               striped:true,
               fitColumns:true,toolbar:'#toolbar'
              ">
            <thead>
            <tr>
                <th data-options="field:'id',hidden:true,width:100"></th>
                <th data-options="field:'sitename',width:230">机房名称</th>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center',border:false" style="padding-left:2px;">
        <table id="rackTable" class="easyui-datagrid"
               data-options="fit:true,rownumbers:false,striped:true,
               fitColumns:false,toolbar:'#toolbarRack'
              ">
            <thead>
            <tr>
                <th data-options="field:'id',checkbox:'true'"></th>
                <th data-options="field:'name',width:250">机架名称</th>
                <th data-options="field:'businesstypeId',width:100,formatter:businesstypeDetails">机架类型</th>
                <th data-options="field:'height',width:100">机架U数</th>
                <th data-options="field:'status',width:100,formatter:statusDetails">业务状态</th>
                <th data-options="field:'usefor',width:100,formatter:userforDetails">用途</th>
                <th data-options="field:'renttype',width:100,formatter:renttypeDetails">出租类型</th>
                <th data-options="field:'pduPowertype',width:100">电源类型</th>
                <th data-options="field:'ratedelectricenergy',width:100">额定电量(KWH)</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="toolbar" style="height: 28px;" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="roomName" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchRoomModels();"></div>
</div>
<div id="toolbarRack" style="height: 28px;" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="rackName" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchRackModels();"></div>
</div>
<script type="text/javascript" language="JavaScript">
    var roomIds = "${roomIds}";
    var rackIds = "${rackIds}";
    //当前选择的机房
    var selectedRoomId = null;
    $(function(){
        //加载机房列表
        $("#roomTable").datagrid({
            url:contextPath+"/zbMachineroom/queryZbMachineroomListByIds",
            queryParams:{roomIds:roomIds},
            onClickRow: function (index, row) {
                selectedRoomId = row.id;
                $("#rackName").val('');
                $('#rackTable').datagrid({
                    url: contextPath + '/idcRack/queryRackListByIds',
                    queryParams: {roomId: row.id,rackIds:rackIds},
                    onLoadSuccess:function(data){//当表格成功加载时执行
                        var rowData = data.rows;
                        $.each(rowData,function(idx,val){//遍历JSON
                            $("#rackTable").datagrid("selectRow", idx);//如果后台传过来的数据表示要选中，则此表示一记载就选中
                        });
                    }
                });
            }
        })
    })
    //进入跳纤页面进行跳纤
    function doSubmit(){
        var rowArr = [];
        var rows = $('#rackTable').datagrid('getChecked');
        if(rows.length<1){
            alert("请选择要跳纤的机架");
            return;
        }
        for(var i=0;i<rows.length;i++){
            rowArr.push(rows[i].id);
        }
        //跳纤
        var url = contextPath + "/idclink/rack";
        var data = {roomId:selectedRoomId,ids:rowArr.join('-')};
        // openDialogUsePostView("跳纤机架", url, "90%", "90%", data);
        //window.open(contextPath+"/idclink/rack?roomId="+selectedRoomId+"&ids="+rowArr.join('-'));
        top.layer.open({
            type: 2,
            id:"linkrack",
            area: ["90%", "90%"],
            title: "跳纤机架",
            maxmin: true, //开启最大化最小化按钮
            content: 'blankpage',
            scrollbar: false,
            btn: ['确定', '取消'],
            success: function (layero, index) {
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
                var chidlwin = layero.find('iframe')[0].contentWindow;
                if (chidlwin.location.href.indexOf('blankpage') > -1) {
                    var html = buildFormByParam(url, data);
                    chidlwin.document.body.innerHTML = "";//清空iframe内容，达到重新请求
                    chidlwin.document.write(html);
                    chidlwin.document.getElementById('postData_form').submit();
                }
            },
            btn1: function (index, layero) {
                var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                iframeWin.contentWindow.doSubmit();
            },
            btn2: function (index, layero) {
                var iframeWin = layero.find('iframe')[0];
                iframeWin.contentWindow.cancelWin();
                return false;
            }
        })
    }
    //查询  机房
    function searchRoomModels(){
        var roomName = $("#roomName").val();
        $('#roomTable').datagrid({
            url: contextPath + '/zbMachineroom/queryZbMachineroomListByIds',
            queryParams: {roomName:roomName,roomIds: roomIds}
        });
    }
    //查询  机架
    function searchRackModels(){
        var rackName = $("#rackName").val();
        $('#rackTable').datagrid({
            url: contextPath + '/idcRack/queryRackListByIds',
            queryParams: {rackName:rackName,roomId: selectedRoomId,rackIds:rackIds}
        });
    }
    function statusDetails(value,row,index){
        if(value == 20){
            return '可用';
        }else if(value == 30){
            return '预留';
        }else if(value == 40){
            return '空闲';
        }else if(value == 50){
            return '预占';
        }else if(value == 55){
            return '已停机';
        }else if(value == 60){
            return '在服';
        }else if(value == 110){
            return '不可用';
        }else{
            return '';
        }
    }
    function renttypeDetails(value,row,index){
        if (value == 0) {
            return '整架出租';
        } else if (value == 1) {
            return '按机位出租';
        }else{
            return '';
        }
    }
    function userforDetails(value,row,index){
        if(value==1){
            return '自用';
        }else if(value==2){
            return '客用';
        }else{
            return '';
        }
    }
    function businesstypeDetails(value,row,index){
        if(value=='equipment'){
            return '客户机柜';
        }else if(value=='df'&&row.DFTYPE=='ddf'){
            return 'DDF架';
        }else if(value=='df'&&row.DFTYPE=='odf'){
            return 'ODF架';
        }else if(value=='cabinet'){
            return '网络柜';
        }else if(value=='df'&&row.DFTYPE=='wiring'){
            return '配线柜';
        }else{
            return '';
        }
    }
</script>
</body>
</html>