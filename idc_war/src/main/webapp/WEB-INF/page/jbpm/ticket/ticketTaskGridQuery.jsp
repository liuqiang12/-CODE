<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
	<jsp:include page="/globalstatic/easyui/publicButton.jsp"></jsp:include>


	<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/layui/css/layui.css" media="all">
	<script src="<%=request.getContextPath() %>/framework/layui/layui.all.js"></script>


	<title>预占预勘察管理</title>
    <style>
		.datagrid-row {
			height: 27px;
		}
		.textbox .textbox-button, .textbox .textbox-button:hover{
			right:0;
		}
		.datagrid-td-rownumber{
			width: 19px;
		}
		.jbpm_toolbars_cls{
			position: absolute;
			top: 5px;
			right: 10px;
			z-index: 9999;
			display: none;
		}
		.marginLeft50{
			margin-left: 50px;
		}
		.jbpm_process_back_float_right{
			position: absolute;
			top:1px;
			right: 12px;
			z-index:999;
		}
		a {
			color:blue;
			text-decoration:underline ;
		}

		a:hover {
			color: inherit !important;}
	</style>
</head>
<body fit="true">
	<!-- 浮动显示按钮信息 -->
	<div class="jbpm_process_back_float_right">
		<%--下面是政企业务的权限控制--%>
		<c:if test="${prodCategory == '1'}">

			<sec:authorize access="hasAnyRole('ROLE_idc_customer_manager_handler','ROLE_admin')">
				<%--ticketCategory:工单类型100:预勘 200:开通 400:停机 500:复通 600:下线 700:变更开通 800:临时测试,900:业务变更--%>
				<!-- 预占预勘  100 可以  -->
				<c:if test="${ticketCategory == '100'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'start_ing',plain:true" onclick="singleCreateOrUpdate_('pre_gridId','100','no')">发起预堪</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-bgkt',plain:true,disabled:true" id="ktlc_buttom_id"  onclick="singleStartRow('pre_gridId','200')">开通</a>
				</c:if>
				<!-- 开通流程 200 可  -->
				<c:if test="${ticketCategory == '200'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tj',plain:true,disabled:true" id="zq_tj_buttom_id"  onclick="singleStartRow('pre_gridId','400')">停机</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ywbg',plain:true,disabled:true" id="zq_ywbg_buttom_id" onclick="singleStartRow('pre_gridId','900')">业务变更</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('pre_gridId','600')">业务下线</a>
				</c:if>

				<!-- 停机流程 400  -->
				<c:if test="${ticketCategory == '400'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ft',plain:true,disabled:true" id="zq_ft_buttom_id"  onclick="singleStartRow('pre_gridId','500')">复通</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('pre_gridId','600')">业务下线</a>

				</c:if>
				<!-- 复通流程 500  -->
				<c:if test="${ticketCategory == '500'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ywbg',plain:true,disabled:true" id="zq_ywbg_buttom_id" onclick="singleStartRow('pre_gridId','900')">业务变更</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('pre_gridId','600')">业务下线</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tj',plain:true,disabled:true" id="zq_tj_buttom_id"  onclick="singleStartRow('pre_gridId','400')">停机</a>

				</c:if>

				<!-- 变更开通 700  -->
				<c:if test="${ticketCategory == '700'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tj',plain:true,disabled:true" id="zq_tj_buttom_id"  onclick="singleStartRow('pre_gridId','400')">停机</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('pre_gridId','600')">业务下线</a>
					<a href="javascript:void(0)" class="easyui-linkbutton " data-options="iconCls:'icon-ywbg',plain:true,disabled:true" id="zq_ywbg_buttom_id" onclick="singleStartRow('pre_gridId','900')">业务变更</a>
				</c:if>

				<!-- 临时测试 800  -->
				<c:if test="${ticketCategory == '800'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'start_ing',plain:true" onclick="singleCreateOrUpdate_test('pre_gridId','800','no')">申请临时测试</a>
				</c:if>

				<!-- 业务变更 900  -->
				<c:if test="${ticketCategory == '900'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-bgkt',plain:true,disabled:true" id="zq_bgkt_buttom_id"  onclick="singleStartRow('pre_gridId','700')">变更开通</a>
				</c:if>
			</sec:authorize>
		</c:if>

		<%--下面是自有业务的权限控制--%>
		<c:if test="${prodCategory == '0'}">
			<sec:authorize access="hasAnyRole('ROLE_self_idc_customer_manager_handler','ROLE_admin')">
				<!-- 预占预勘  100 可以  -->
				<c:if test="${ticketCategory == '100'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'start_ing',plain:true" onclick="singleCreateOrUpdate_('pre_gridId','100','no')">发起预堪</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-bgkt',plain:true,disabled:true" id="ktlc_buttom_id"  onclick="singleStartRow('pre_gridId','200')">开通</a>
				</c:if>
				<!-- 开通流程 200 可  -->
				<c:if test="${ticketCategory == '200'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tj',plain:true,disabled:true" id="zq_tj_buttom_id"  onclick="singleStartRow('pre_gridId','400')">停机</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ywbg',plain:true,disabled:true" id="zq_ywbg_buttom_id" onclick="singleStartRow('pre_gridId','900')">业务变更</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('pre_gridId','600')">业务下线</a>
				</c:if>

				<!-- 停机流程 400  -->
				<c:if test="${ticketCategory == '400'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ft',plain:true,disabled:true" id="zq_ft_buttom_id"  onclick="singleStartRow('pre_gridId','500')">复通</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('pre_gridId','600')">业务下线</a>

				</c:if>
				<!-- 复通流程 500  -->
				<c:if test="${ticketCategory == '500'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ywbg',plain:true,disabled:true" id="zq_ywbg_buttom_id" onclick="singleStartRow('pre_gridId','900')">业务变更</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('pre_gridId','600')">业务下线</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tj',plain:true,disabled:true" id="zq_tj_buttom_id"  onclick="singleStartRow('pre_gridId','400')">停机</a>

				</c:if>

				<!-- 变更开通 700  -->
				<c:if test="${ticketCategory == '700'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tj',plain:true,disabled:true" id="zq_tj_buttom_id"  onclick="singleStartRow('pre_gridId','400')">停机</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('pre_gridId','600')">业务下线</a>
					<a href="javascript:void(0)" class="easyui-linkbutton " data-options="iconCls:'icon-ywbg',plain:true,disabled:true" id="zq_ywbg_buttom_id" onclick="singleStartRow('pre_gridId','900')">业务变更</a>
				</c:if>

				<!-- 临时测试 800  -->
				<c:if test="${ticketCategory == '800'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'start_ing',plain:true" onclick="singleCreateOrUpdate_test('pre_gridId','800','no')">申请临时测试</a>
				</c:if>

				<!-- 业务变更 900  -->
				<c:if test="${ticketCategory == '900'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-bgkt',plain:true,disabled:true" id="zq_bgkt_buttom_id"  onclick="singleStartRow('pre_gridId','700')">变更开通</a>
				</c:if>

			</sec:authorize>
		</c:if>
	</div>
	 <!-- 待办/归档 -->
	 <div id="jbpm_tabs" class="easyui-tabs" fit="true">
		 <div title="待办任务" style="padding:10px;display:none;">
			 <div style="padding: 5px;" id="requestParamSettins_taskQuery">
				 <%--订单名称: <input class="easyui-textbox"  id="prodName" style="width:100px;text-align: left;" data-options="">--%>
				 <input id="processDefinitonKey" value="${processDefinitonKey}" type="hidden"/>
				 <input id="prodCategory" value="${prodCategory}" type="hidden"/>
				 <input id="is_self_process" value="${is_self_process}" type="hidden"/>
				 <input id="ticket_ctl" value="${ticket_ctl}" type="hidden"/>
				 <input id="category" value="${category}" type="hidden"/>
				 <input id="idcName" value="${idcName}" type="hidden"/>
				 <input id="isAdministrator" value="${isAdministrator}" type="hidden"/>
				 <input id="loginUserId" value="${loginUserId}" type="hidden"/>
				 业务名称: <input class="easyui-textbox"  id="busName" style="width:290px;text-align: left;" data-options="">
				 工单编号: <input class="easyui-textbox"  id="serialNumber" style="width:120px;text-align: left;" data-options="">
				 区域:<input class="easyui-combobox"  data-options="
							valueField: 'value',
							textField: 'label',
							editable:false,
							width:165,
							data: [{
								label: '所有区域',
								value: ''
							},{
								label: '中国（西部）云计算中心',
								value: 'DC-280-04'
							},{
								label: '四川移动东区数据中心',
								value: 'DC-280-02'
							},{
								label: '四川移动西区枢纽',
								value: 'DC-280-03'
							}]" id="locationCode"/>

					 <a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
				 <a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'mydefined_reset'">刷新</a>
					 <!-- 审批:申请人和当前人不一致才可以审批 -->
				 <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'apply_ffcc00',plain:true" id="apply_buttom_id" onclick="author_apply_item('gridId')">审批</a>--%>
			 </div>
			 <table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: true,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},fitColumns:true"></table>
		 </div>
		 <div title="${tabsTitle}" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
			 <div style="padding: 5px;" id="requestParamSettins_pre_taskQuery">
				 业务名称: <input class="easyui-textbox"  id="pre_busName" style="width:290px;text-align: left;" data-options="">
				 工单编号: <input class="easyui-textbox"  id="pre_serialNumber" style="width:120px;text-align: left;" data-options="">
				 区域:<input class="easyui-combobox"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:165,
								data: [{
									label: '所有区域',
									value: ''
								},{
									label: '中国（西部）云计算中心',
									value: 'DC-280-04'
								},{
									label: '四川移动东区数据中心',
									value: 'DC-280-02'
								},{
									label: '四川移动西区枢纽',
									value: 'DC-280-03'
								}]" id="pre_locationCode"/>

				 <a href="javascript:void(0);" onclick="loadPreGrid('pre_gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
				 <a href="javascript:void(0);" onclick="resetForm('pre_gridId')" class="easyui-linkbutton" data-options="iconCls:'mydefined_reset'">刷新</a>

			 </div>
			 <table class="easyui-datagrid" id="pre_gridId" data-options="singleSelect:true,nowrap: true,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},fitColumns:true"></table>
  		 </div>
	 </div>

</body>

<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketTaskGridQuery.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketTaskCommonQuery.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketPreTaskGridQuery.js"></script>
</html>