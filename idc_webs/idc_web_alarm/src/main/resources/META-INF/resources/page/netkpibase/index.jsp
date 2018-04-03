<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--&lt;%&ndash;<%@ page isErrorPage="true" %>&ndash;%&gt;--%>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%--%>
    <%--String contextPath = request.getContextPath();--%>
<%--%>--%>
<%--<!DOCTYPE html>--%>
<%--<html lang="zh-CN">--%>
<%--<head>--%>
    <%--<title>告警信息</title>--%>
    <%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8">--%>
    <%--<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">--%>
    <%--<meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <%--<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>--%>
    <%--<script>--%>
    <%--</script>--%>
    <%--<style>--%>
        <%--.btn {--%>
            <%--padding: 2px 5px;--%>
            <%--margin: 2px;--%>
        <%--}--%>
    <%--</style>--%>
<%--</head>--%>
<%--<body style="margin:0;padding:0;overflow:hidden;">--%>
<%--<div class="easyui-layout" fit="true">--%>
    <%--&lt;%&ndash;<div data-options="region:'east',collapsible:true,collapsed:true,width:350,border:true" title="告警阀值定义">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<table id="kpitable" class="easyui-datagrid" data-options="fit:true,pagination:false,singleSelect:true,&ndash;%&gt;--%>
    <%--&lt;%&ndash;toolbar:'#toolbarkpi',&ndash;%&gt;--%>
    <%--&lt;%&ndash;onLoadSuccess:loadsuccess,striped:true,url:'<%=request.getContextPath() %>/alarm/alarmkpilist.do'">&ndash;%&gt;--%>
    <%--&lt;%&ndash;<thead>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<th data-options="field:'alarmType',width:150,formatter:function(value,row,index){&ndash;%&gt;--%>
    <%--&lt;%&ndash;if(value==0){&ndash;%&gt;--%>
    <%--&lt;%&ndash;return '采集器告警'&ndash;%&gt;--%>
    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;if(value==1){&ndash;%&gt;--%>
    <%--&lt;%&ndash;return '机房用能突变告警'&ndash;%&gt;--%>
    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;if(value==2){&ndash;%&gt;--%>
    <%--&lt;%&ndash;return '机房PUE告警'&ndash;%&gt;--%>
    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;if(value==3){&ndash;%&gt;--%>
    <%--&lt;%&ndash;return '机楼用能突变告警'&ndash;%&gt;--%>
    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;}">告警类别&ndash;%&gt;--%>
    <%--&lt;%&ndash;</th>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<th data-options="field:'alarmLevel',width:50,formatter:function(value,row,index){&ndash;%&gt;--%>
    <%--&lt;%&ndash;if(value==0){&ndash;%&gt;--%>
    <%--&lt;%&ndash;return '一般'&ndash;%&gt;--%>
    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;if(value==1){&ndash;%&gt;--%>
    <%--&lt;%&ndash;return '重要 '&ndash;%&gt;--%>
    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;if(value==2){&ndash;%&gt;--%>
    <%--&lt;%&ndash;return '严重'&ndash;%&gt;--%>
    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;}">告警等级&ndash;%&gt;--%>
    <%--&lt;%&ndash;</th>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<th data-options="field:'alarmLimit',width:$(this).width()*0.1">阀值门限</th>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<th data-options="field:'description',width:$(this).width()*0.1">说明</th>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</thead>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</table>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--<div data-options="region:'center',border:false" style="padding-left: 2px;">--%>
        <%--<div class="easyui-layout" fit="true">--%>
            <%--&lt;%&ndash;<div data-options="region:'north',height:32,border:true">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<div style="float: left;padding-left: 10px" id="toolbar"><span>查询条件：</span>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<input type="hidden" id="regionid" name="regionid"/>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<input name="keyword" class="easyui-textbox" type="text" prompt="告警信息关键字"/>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<span>告警时间从：</span><input name="starttime" class="easyui-datetimebox" type="text" prompt="告警时间">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<span>至</span>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<input name="endtime" class="easyui-datetimebox" type="text" prompt="告警时间">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<a id="ok" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>&ndash;%&gt;--%>
            <%--&lt;%&ndash;&lt;%&ndash;<a id="kpi" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">告警阀值定义</a>&ndash;%&gt;&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--<div data-options="region:'center',border:false" style="padding-left: 2px;">--%>
                <%--<div id="table_kpi"></div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<div id="toolbarkpi" style="height: 28px">--%>
    <%--<div style="float: left">--%>
        <%--<select class="easyui-combobox" name="alarmType" data-options="onChange:function(newValue, oldValue){--%>
                 <%--var paramstmp = {type:newValue};--%>
                    <%--$('#kpitable').datagrid('options').queryParams=paramstmp;--%>
                       <%--$('#kpitable').datagrid('reload'); }">--%>
            <%--<option value="">所有</option>--%>
            <%--<option value="0">采集器告警</option>--%>
            <%--<option value="1">机房用能突变告警</option>--%>
            <%--<option value="2">机房PUE告警</option>--%>
            <%--<option value="3">机楼用能突变告警</option>--%>
        <%--</select>--%>
        <%--<a id="addkpi" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>--%>
        <%--&lt;%&ndash;<a id="editkpi" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<a id="delkpi" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>&ndash;%&gt;--%>
    <%--</div>--%>
<%--</div>--%>
<%--&lt;%&ndash;<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree.all-3.5.min.js"></script>&ndash;%&gt;--%>
<%--&lt;%&ndash;<link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">&ndash;%&gt;--%>
<%--&lt;%&ndash;<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/global/plugins/tree.js"></script>&ndash;%&gt;--%>
<%--<script type="text/javascript" language="JavaScript">--%>
    <%--function loadsuccess(data) {--%>
        <%--$(".datagrid-cell").find('.easyui-linkbutton').each(function () {--%>
            <%--$(this).linkbutton();--%>
        <%--});--%>
        <%--$(".datagrid-cell").find('.easyui-tooltip').each(function () {--%>
            <%--$(this).tooltip();--%>
        <%--});--%>
        <%--$('#table').datagrid('fixRowHeight')--%>
    <%--}--%>
    <%--var $table_pue = $('#table_alarm_curr'), $table_device = $('#table_device'),--%>
            <%--$table_his_pue = $('#table_his_pue'), $table_his_device = $('#table_his_device'),--%>
            <%--$ok = $('#ok');--%>
    <%--function queryByPid(event, treeId, treeNode) {--%>
        <%--if (treeNode.children && treeNode.children.length > 0) {--%>
            <%--$("input[name='groupname']").val('');--%>
            <%--$("input[name='groupPid']").val(treeNode.id);--%>
            <%--refreshTable();--%>
        <%--}--%>
    <%--}--%>
    <%--function refreshTable() {--%>
        <%--var paramstmp = {};--%>
        <%--$('#toolbar').find('input[name]').each(function () {--%>
            <%--paramstmp[$(this).attr('name')] = $(this).val();--%>
        <%--});--%>
        <%--$table_pue.datagrid('options').queryParams = paramstmp;--%>
        <%--$table_pue.datagrid('reload');--%>
        <%--$table_device.datagrid('options').queryParams = paramstmp;--%>
        <%--$table_device.datagrid('reload');--%>

        <%--$table_his_pue.datagrid('options').queryParams = paramstmp;--%>
        <%--$table_his_pue.datagrid('reload');--%>

        <%--$table_his_device.datagrid('options').queryParams = paramstmp;--%>
        <%--$table_his_device.datagrid('reload');--%>

    <%--}--%>
    <%--function refreshKpiTable() {--%>
        <%--$("#kpitable").datagrid('reload');--%>
    <%--}--%>
    <%--$(function () {--%>
<%--//        $ok.on('click', function () {--%>
<%--//            refreshTable();--%>
<%--//        });--%>
<%--//        $(".sys_usergroup_add").on('click', function () {--%>
<%--//            editRow(0, 0);--%>
<%--//        });--%>
<%--//--%>
<%--//        $("#addkpi").on("click", function () {--%>
<%--//            editkpi(0);--%>
<%--//        })--%>
<%--//        $("#editkpi").on("click", function () {--%>
<%--//            var row = $("#kpitable").datagrid("getSelected");--%>
<%--//            if (!row) {--%>
<%--//                top.layer.msg("没有选择要修改的指标");--%>
<%--//                return;--%>
<%--//            }--%>
<%--//            editkpi(row.id);--%>
<%--//        });--%>
        <%--$("#table_kpi").datagrid({--%>
            <%--fit: true,--%>
            <%--rownumbers: true,--%>
            <%--pagination: true,--%>
            <%--singleSelect: true,--%>
            <%--onLoadSuccess: loadsuccess,--%>
            <%--pageSize: 15,--%>
            <%--pageList: [10, 15, 20, 50],--%>
            <%--striped: true,--%>
            <%--url: '<%=request.getContextPath() %>/netkpibase/list',--%>
            <%--frozenColumns: [[--%>
                <%--{--%>
                    <%--title: '指标名称',--%>
                    <%--field: 'kpiname',--%>
                    <%--width: 100--%>
                <%--},--%>
                <%--{--%>
                    <%--title: '指标关联对象类别',--%>
                    <%--field: 'kpikey',--%>
                    <%--width: 80,--%>
                    <%--formatter: function (value, row, index) {--%>
                        <%--if (value == 'device') {--%>
                            <%--return '设备'--%>
                        <%--}--%>
                        <%--if (value == 'link') {--%>
                            <%--return '链路'--%>
                        <%--}--%>
                    <%--}--%>
                <%--},--%>
                <%--{--%>
                    <%--title: '指标单位',--%>
                    <%--field: 'kpiunit',--%>
                    <%--width: 100--%>
                <%--},--%>
                <%--{--%>
                    <%--title: '告警对象',--%>
                    <%--field: 'objName',--%>
                    <%--width: 200--%>
                <%--}--%>
            <%--]],--%>
            <%--columns: [[--%>
                <%--{--%>
                    <%--title: '告警信息',--%>
                    <%--field: 'alarminfo',--%>
                    <%--width: 200,--%>
                    <%--formatter: function (value, row, index) {--%>
                        <%--var newvalue = new Array();--%>
                        <%--var vs = value.replace(/(.)(?=[^$])/g, '$1,').split(',');--%>
                        <%--$.each(vs, function (i, iteam) {--%>
                            <%--if (i % 15 == 0) {--%>
                                <%--newvalue.push('</br>')--%>
                            <%--}--%>
                            <%--newvalue.push(iteam)--%>
                        <%--})--%>
                        <%--return '<span tyle=\'width:100px\' trackMouse=\'true\' class=\'easyui-tooltip\'title=\'' + newvalue.join('') + '\'>' + value + '</span>'--%>
                    <%--}--%>
                <%--},--%>
                <%--{--%>
                    <%--title: '告警时间', field: 'alarmtime', width: 200--%>
                <%--},--%>
                <%--{--%>
                    <%--title: '告警状态',--%>
                    <%--field: 'alarmsend',--%>
                    <%--width: $(this).width() * 0.05,--%>
                    <%--formatter: function (value, row, index) {--%>
                        <%--if (value == 0) {--%>
                            <%--return '未发送'--%>
                        <%--}--%>
                        <%--if (value == 1) {--%>
                            <%--return '已经发送'--%>
                        <%--}--%>
                        <%--if (value == 2) {--%>
                            <%--return '发送失败'--%>
                        <%--}--%>
                    <%--}--%>
                <%--}, {--%>
                    <%--title: '发送时间', field: 'alarmsendtime', width: 200--%>
                <%--}--%>
            <%--]]--%>
        <%--});--%>
        <%--$("#table_alarm_his").datagrid({--%>
            <%--fit: true,--%>
            <%--rownumbers: true,--%>
            <%--pagination: true,--%>
            <%--singleSelect: true,--%>
            <%--onLoadSuccess: loadsuccess,--%>
            <%--pageSize: 15,--%>
            <%--pageList: [10, 15, 20, 50],--%>
            <%--striped: true,--%>
            <%--url: '<%=request.getContextPath() %>/alarm/list.do?type=1',--%>
            <%--frozenColumns: [[--%>
                <%--{--%>
                    <%--title: '告警等级',--%>
                    <%--field: 'alarmlevel',--%>
                    <%--width: 80,--%>
                    <%--formatter: function (value, row, index) {--%>
                        <%--if (value == 0) {--%>
                            <%--return '<img title=\'一般\' style=\'height:20px;width:25px\' src=\'<%=request.getContextPath() %>/assets/images/alarmicon/small.png\' />'--%>
                        <%--}--%>
                        <%--if (value == 1) {--%>
                            <%--return '<img title=\'重要\' style=\'height:20px;width:25px\' src=\'<%=request.getContextPath() %>/assets/images/alarmicon/level1.png\' />'--%>
                        <%--}--%>
                        <%--if (value == 2) {--%>
                            <%--return '<img title=\'严重\' style=\'height:20px;width:25px\' src=\'<%=request.getContextPath() %>/assets/images/alarmicon/level2.png\' />'--%>
                        <%--}--%>
                    <%--}--%>
                <%--},--%>
                <%--{--%>
                    <%--title: '告警类别',--%>
                    <%--field: 'netKpibase',--%>
                    <%--width: 100,--%>
                    <%--formatter: function (value, row, index) {--%>
                        <%--return row.netKpibase.kpiname;--%>
                    <%--}--%>
                <%--},--%>
                <%--{--%>
                    <%--title: '告警对象',--%>
                    <%--field: 'objName',--%>
                    <%--width: 200--%>
                <%--}--%>
            <%--]],--%>
            <%--columns: [[--%>
                <%--{--%>
                    <%--title: '告警信息',--%>
                    <%--field: 'alarminfo',--%>
                    <%--width: 200,--%>
                    <%--formatter: function (value, row, index) {--%>
                        <%--var newvalue = new Array();--%>
                        <%--var vs = value.replace(/(.)(?=[^$])/g, '$1,').split(',');--%>
                        <%--$.each(vs, function (i, iteam) {--%>
                            <%--if (i % 15 == 0) {--%>
                                <%--newvalue.push('</br>')--%>
                            <%--}--%>
                            <%--newvalue.push(iteam)--%>
                        <%--})--%>
                        <%--return '<span tyle=\'width:100px\' trackMouse=\'true\' class=\'easyui-tooltip\'title=\'' + newvalue.join('') + '\'>' + value + '</span>'--%>
                    <%--}--%>
                <%--},--%>
                <%--{--%>
                    <%--title: '告警时间', field: 'alarmtime', width: 200--%>
                <%--},--%>
                <%--{--%>
                    <%--title: '告警状态',--%>
                    <%--field: 'alarmsend',--%>
                    <%--width: $(this).width() * 0.05,--%>
                    <%--formatter: function (value, row, index) {--%>
                        <%--if (value == 0) {--%>
                            <%--return '未发送'--%>
                        <%--}--%>
                        <%--if (value == 1) {--%>
                            <%--return '已经发送'--%>
                        <%--}--%>
                        <%--if (value == 2) {--%>
                            <%--return '发送失败'--%>
                        <%--}--%>
                    <%--}--%>
                <%--}, {--%>
                    <%--title: '发送时间', field: 'alarmsendtime', width: 200--%>
                <%--}, {--%>
                    <%--title: '告警恢复时间', field: 'resumetime', width: 200--%>
                <%--}, {--%>
                    <%--title: '告警恢复人', field: 'resumeuser', width: 200--%>
                <%--}--%>
            <%--]]--%>
        <%--})--%>
    <%--});--%>
    <%--function editkpi(id) {--%>
        <%--var url = contextPath + "/alarm/editkpi.do?id=" + id;--%>
        <%--top.layer.open({--%>
            <%--type: 2,--%>
            <%--title: '指标信息编辑',--%>
            <%--maxmin: true,--%>
            <%--id: 'kpiwin', //设定一个id，防止重复弹出--%>
            <%--closeBtn: 1,--%>
            <%--area: ['400px', '250px'],--%>
            <%--content: url--%>
        <%--});--%>
    <%--}--%>
    <%--function editRow(groupid, pid) {--%>
        <%--var url = contextPath + "/usergroup/form.do";--%>
        <%--if (groupid != 0) {//修改--%>
            <%--url += "?groupid=" + groupid;--%>

        <%--} else {//添加--%>
            <%--if (pid != 0)--%>
                <%--url += "?pid=" + pid;--%>
        <%--}--%>
        <%--top.layer.open({--%>
            <%--type: 2,--%>
            <%--title: '用户组信息',--%>
            <%--maxmin: true,--%>
            <%--id: 'groupwin', //设定一个id，防止重复弹出--%>
            <%--closeBtn: 1,--%>
            <%--area: ['500px', '300px'],--%>
            <%--content: url--%>
        <%--});--%>
    <%--}--%>
    <%--function refTree() {--%>
        <%--var treeObj = $.fn.zTree.getZTreeObj("treegroup");--%>
        <%--treeObj.reAsyncChildNodes(null, "refresh");--%>
    <%--}--%>
    <%--function deleteRow(id) {--%>
        <%--top.layer.msg('确定告警？', {--%>
            <%--time: 0, //不自动关闭--%>
            <%--btn: ['确认', '取消'],--%>
            <%--yes: function (index) {--%>
                <%--$.getJSON(contextPath + "/alarm/delete.do?id=" + id, function (data) {--%>
                    <%--if (data.state) {--%>
                        <%--top.layer.msg('确认成功');--%>
                        <%--refreshTable();--%>
                    <%--} else {--%>
                        <%--top.layer.msg(data.msg);--%>
                    <%--}--%>
                <%--});--%>
            <%--},--%>
            <%--no: function (index) {--%>
                <%--layer.close(index);--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>

    <%--/***--%>
     <%--*角色绑定--%>
     <%--* @param id--%>
     <%--*/--%>
    <%--function bindRole(id) {--%>
        <%--top.layer.open({--%>
            <%--type: 2,--%>
            <%--title: '组-角色分配',--%>
            <%--maxmin: true,--%>
            <%--id: 'bindwin', //设定一个id，防止重复弹出--%>
            <%--closeBtn: 1,--%>
            <%--area: ['600px', '450px'],--%>
            <%--content: contextPath + "/bindlink.do?type=g_r&id=" + id--%>
        <%--})--%>
    <%--}--%>
    <%--function formmataction(value, row, index) {--%>
        <%--var insert = new Array();--%>
        <%--<sec:authorize access="hasAnyRole('ROLE_sys_alarm_del','ROLE_admin')">--%>
        <%--insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-edit\'" onclick="deleteRow(' + row.id + ')">确认告警</a> ');--%>
        <%--</sec:authorize>--%>
        <%--return insert.join('');--%>
    <%--}--%>
<%--</script>--%>
<%--</body>--%>

<%--</html>--%>