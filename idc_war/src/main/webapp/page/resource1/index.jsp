<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
	<script type="text/javascript"
			src="<%=request.getContextPath() %>/js/base/ajaxfileupload.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/${type}/option.js"></script>

    <%--<link rel="stylesheet" type="text/css"--%>
    <%--href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/custom/uimaker/css/basic_info.css"/>--%>
    <title>资源占用流程</title>
    <style>
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="cc" class="easyui-layout" fit="true">
        <div data-options="region:'west',iconCls:'icon-reload',split:true" style="width:300px;">
            <ul id="rtree" class="ztree" style="width:300px; overflow:auto;"></ul>
            <%--<div id="rtree" class="ztree" style="overflow: auto"></div>--%>
        </div>
        <div data-options="region:'center'" style="padding:5px;background:#eee;">
            <table id="dg" fit="true" class="easyui-datagrid"></table>
        </div>
    </div>

    <div id="toolbar" class="paramContent">
		<%--<div class="param-fieldset">--%>
			<%--<label>编码:</label>--%>
			<%--<input type="input"  class="param-input"/>--%>
		<%--</div>--%>
		<div class="param-fieldset">
			<label>名称:</label>
			<input type="input" id="buiName" class="param-input"/>
			<c:if test="${type eq 'idcrack'}">
				<label>机架类型:</label>
				<select id="rackType" class="easyui-combobox" data-options="panelHeight:'auto'" name="dept" style="width:100px;">
					<option value="1">客户机柜</option>
					<option value="2">DDF架</option>
					<option value="3">ODF架</option>
					<option value="4">网络柜</option>
					<option value="5">配线柜</option>
				</select>
			</c:if>
		</div>
			<%--
			<c:if test="${type eq 'machineroom'}">
				<div class="btn-cls-search" onClick="searchMachineroom();"></div>
			</c:if>
			<c:if test="${type eq 'building'}">
				<div class="btn-cls-search" onClick="searchBuilding();"></div>
			</c:if>
			<c:if test="${type eq 'idcrack'}">
				<div class="btn-cls-search" onClick="searchIdcrack();"></div>
			</c:if>
			<c:if test="${type eq 'location'}">
				<div class="btn-cls-search" onClick="searchIdclocation();"></div>
			</c:if>
			--%>
			<div class="btn-cls-search" onClick="searchModels();"></div>
		<div class="btn-cls-reset" onClick="$('#buiName').val('')"></div>
	
		<div class="param-actionset">
			<div class="btn-cls-add" id="add"></div>
			<div class="btn-cls-edit" id="edit"></div>
			<div class="btn-cls-delete" id="del"></div>
			<div class="btn-cls-common" id="importData">导 入</div>
			<div class="btn-cls-common" id="exportData">导 出</div>
			<!--
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"
			   onclick="importClick();">导入</a>
			<button id="export" class="easyui-linkbutton" data-options="iconCls:'icon-save'">导出</button>
			-->
			<c:if test="${type == 'device' }">
				<div class="btn-cls-common" id="netProt">端口信息</div>
			</c:if>
			<!-- <div class="btn-cls-delete" onClick="alert('删除')"></div> -->
		</div>
	</div>
</div>

<div id="dlg_pic" class="easyui-dialog" style="width:400px;height:290px;padding:10px 20px"
	 closed="true" buttons="#dlg-buttons">
	<div class="ftitle">导入</div>
	<form id="fm_pic" method="post" enctype="multipart/form-data" novalidate>
		<div class="fitem">
			<label>excel文件:</label>
			<input type="file" name="uploadFile" id="uploadFileid" name="file"/>
			<%--	<input class="easyui-filebox" name="uploadFile" id="uploadFileid" data-options="prompt:'上传文件'">--%>
		</div>
		<div class="fitem">
			<input type="button" onclick="importFileClick()" value="导入">
			<input type="button" onclick="" value="取消">
		</div>
	</form>
</div>
<script type="text/javascript">



</script>
</body>
</html>