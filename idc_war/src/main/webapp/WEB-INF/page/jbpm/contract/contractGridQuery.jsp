<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
	<script type="application/javascript" src="<%=request.getContextPath() %>/js/jbpm/ticket/datagrid-detailview.js" ></script>
    <title>合同列表</title>
    <style>
		.datagrid-row {
			height: 27px;
		}
	</style>
</head>
<body>
	<div style="padding: 5px;" id="requestParamSettins">
		名称:<input class="easyui-textbox"  id="contractname" style="width:150px;text-align: left;" data-options="">
		编码:<input class="easyui-textbox"  id="contractno" style="width:150px;text-align: left;" data-options="">
		<a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="singleCreateOrUpdate('gridId')">新增</a>--%>
		<%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="singleUpdateRow('gridId')">查看合同</a>--%>
		<%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="singleDeleteRow('gridId')">删除</a>--%>
	</div>
	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: true,striped: true,rownumbers:true,pagination:true,fitColumns:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
 	<script src="<%=request.getContextPath() %>/js/jbpm/contract/contractGridQuery.js"></script>

<%--<div style="padding: 5px;" id="requestParamSettins">
	名称:<input class="easyui-textbox"  id="contractname" style="width:150px;text-align: left;" data-options="">
	编码:<input class="easyui-textbox"  id="contractno" style="width:150px;text-align: left;" data-options="">
	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: true,striped: true,rownumbers:true,pagination:true,fitColumns:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
</div>--%>

</body>
<script type="application/javascript">

    /*$(function(){
        loadGrid();
	})

	function loadGrid() {
        $('#gridId').datagrid({
            title:'合同列表',
            singleSelect: true,
            rownumbers: true,
            resizable: false,
            fixed: true,
            nowrap: true,
            striped: true, //条纹
            width: "100%",
            height: "100%",
            pagination: true, //分页控件
            showFooter:true,
            pageNumber: 1,
            pageSize: 15,
            pageList: [ 15, 30, 50],

            url:contextPath + '/contractController/contractQueryData.do',
            columns:[[
                {field:'CONTRACTNAME',title:'合同名称',width:80},
                {field:'SERIAL_NUMBER',title:'工单序号',width:100,sortable:true},
                {field:'CONTRACTNO',title:'合同编码',width:80,align:'right',sortable:true},
                {field:'CONTRACTPRICE',title:'价格',width:80,align:'right',sortable:true},
                {field:'CONTRACTSIGNDATE',title:'签订时间',width:150,sortable:true},
                {field:'CONTRACTSTART',title:'开始时间',width:150,sortable:true},
                {field:'CONTRACTPERIOD',title:'期限',width:150,sortable:true},
                {field:'CONTRACTEXPIRREMINDER',title:'到期提醒方案',width:150,sortable:true},
                {field:'CUSTOMERNAME',title:'所属客户',width:60,align:'center'}
            ]],
            view: detailview,
            detailFormatter: function(rowIndex, rowData){
                return '<div style="padding:2px"><table id="ddv-' + 'xvcsadf' + '"></table></div>';
            }
        });
    }*/
</script>
</html>