<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <style>
		.datagrid-row {
			height: 27px;
		}
	</style>
</head>
<body>
     <div style="padding: 5px;" id="requestParamSettins">
    	客户名称:<input class="easyui-textbox"  id="btsname" style="width:150px;text-align: left;" data-options="">
   			<input id="resourceType" value="" type="hidden">
    	客户类型:<input   class="easyui-combobox" data-options="
					valueField: 'value',
					textField: 'label',
					editable:false,
					data: [{
						label: 'VIP',
						value: '1'
					},{
						label: '普通',
						value: '2'
					},{
						label: '告警',
						value: '3'
					}]
					"style="width:150px;text-align: left;" > 
			<a href="javascript:void(0);" onclick="loadGrid('gridId')"  class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<!-- 其实就是发布流程 -->
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="publish()">新建预受理清单</a>
   </div>
     	<!-- 写到一张界面中的table -->
     	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,rownumbers:true,pagination:true,fitColumns:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
     	<div id="mm1"class="easyui-menu" style="width:75px;">  
			    <div iconCls="icon-search" id="menuSearch">查看</div>
		</div>
     	<div id="mm2"class="easyui-menu" style="width:75px;">  
<!-- 			    <div  iconCls="icon-edit"  id="menuEdit">编辑</div>   -->
<!-- 			    <div  iconCls="icon-save"  id="menuSave">提交</div>   -->
			    <div iconCls="icon-ok" id="menuOk">开通业务</div>
			    <div iconCls="icon-search" id="menuSearch">查看</div>
			</div>
     	<div id="mm3"class="easyui-menu" style="width:75px;">  
			    <div  iconCls="icon-edit"  id="menuEdit">编辑</div>  
			    <div  iconCls="icon-save"  id="menuSave">提交</div>  
<!-- 			    <div iconCls="icon-ok" id="menuOk">开通业务</div> -->
			    <div iconCls="icon-cancel" id="menuCancel">删除</div>
			    <div iconCls="icon-search" id="menuSearch">查看</div>
<!-- 			    <div iconCls="icon-settting" id="menuSettting">归档</div> -->
			</div>
     	<div id="mm4"class="easyui-menu" style="width:75px;">  
			    <div  iconCls="icon-edit"  id="menuEdit">编辑</div>  
			    <div  iconCls="icon-save"  id="menuSave">提交</div>  
<!-- 			    <div iconCls="icon-ok" id="menuOk">开通业务</div> -->
			    <div iconCls="icon-cancel" id="menuCancel">删除</div>
			    <div iconCls="icon-search" id="menuSearch">查看</div>
<!-- 			    <div iconCls="icon-settting" id="menuSettting">归档</div> -->
			</div>
 	<script src="<%=request.getContextPath() %>/assets/bussness/publishLayout.js"></script>   
</body>
</html>