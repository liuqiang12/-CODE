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
    <title>所有工单列表</title>
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
	</style>
</head>
<body fit="true">
		<div id="requestParamSettins_taskQuery" style="padding: 5px;" >
			<input id="loginUserId" value="${loginUserId}" type="hidden"/>
			工单编号: <input class="easyui-textbox"  id="serialNumber" style="width:200px;text-align: left;" data-options="">
			工单状态:
			<input class="easyui-combobox"  style="width:200px;text-align: left;" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								value:'', 　
								data: [{
									label: '所有',
									value: ''
								},{
									label: '业务开通',
									value: '200'
								},{
									label: '业务变更',
									value: '900'
								},{
									label: '变更开通',
									value: '700'
								},{
									label: '停机',
									value: '400'
								},{
									label: '复通',
									value: '500'
								}]"  id="ticketCategory"/>
			业务类型:
			<input class="easyui-combobox"  style="width:200px;text-align: left;" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								value:'', 　
								data: [{
									label: '所有',
									value: ''
								},{
									label: '政企业务',
									value: '1'
								},{
									label: '自有业务',
									value: '0'
								}]"  id="prodCategory"/>
			<a href="javascript:void(0);" onclick="loadGrid()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<!-- 操作按钮 -->
			<%--<a href="javascript:void(0)" class="easyui-linkbutton marginLeft50" data-options="iconCls:'icon-ywbg',plain:true,disabled:true" id="zq_ywbg_buttom_id" onclick="singleStartRow('pre_gridId','900')">业务变更</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-bgkt',plain:true,disabled:true" id="zq_bgkt_buttom_id"  onclick="singleStartRow('zq_gridId','700')">变更开通</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-tj',plain:true,disabled:true" id="zq_tj_buttom_id"  onclick="singleStartRow('zq_gridId','400')">停机</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ft',plain:true,disabled:true" id="zq_ft_buttom_id"  onclick="singleStartRow('zq_gridId','500')">复通</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-xx',plain:true,disabled:true" id="zq_ywxx_buttom_id"  onclick="singleStartRow('zq_gridId','600')">业务下线</a>--%>
		</div>
		<table class="easyui-datagrid" id="serverGridId" data-options="singleSelect:true,nowrap: false,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},fitColumns:true"></table>
</body>
<jsp:include page="/globalstatic/easyui/publicButton.jsp"></jsp:include>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketTaskCommonQuery.js"></script>
<script type="text/javascript">

    $(function(){
        loadGrid();

    });

    function loadGrid(){
        var params = getPreRequestParams();
        $('#serverGridId').datagrid({
            url:contextPath + "/actJBPMController/ticketOpenLastTaskGridQueryData.do",
            queryParams: params,
            toolbar:"#requestParamSettins_taskQuery",
            columns:[[
                {field:"BUSNAME",title:"业务名称",halign:'left',width:50,formatter:formatterBusNameAA},
                {field:"SERIAL_NUMBER",title:"工单号",halign:'left',width:50},
                {field:"CUSTOMER_NAME",title:"客户名称",halign:'left',width:90},
                {field:"TICKET_TYPE",title:"工单状态",halign:'left',width:60},
                {field:"APPLY_NAME",title:"申请人",halign:'left',width:40},
                {field:"CREATE_TIME",title:"创建时间",halign:'left',width:70}
            ]]
        });
    }

    // 获取查询条件
    function getPreRequestParams(){
        try {
            var serialNumber = $("#serialNumber").textbox("getValue");
        } catch(error) {
        } finally {
        }
        var prodCategory = $("#prodCategory").val();
        var ticketCategory = $("#ticketCategory").val();
        var busName = "busName";

		/*参数组装*/
        var params = {};
        params['serialNumber'] = serialNumber;
        params['prodCategory'] = prodCategory;
        params['ticketCategory'] = ticketCategory;
        return params;
    }


	function formatterBusNameAA(value,row,index){
		var loginUserId=$("#loginUserId").val();    //当前登陆用户的id
		var params = {};
		params['prodInstId'] = row['PROC_INST_ID'];
		params['ticketInstId'] = row['TICKET_INST_ID'];
		params['serialNumber'] = row['SERIAL_NUMBER'];
		params['taskName'] = row['TASK_NAME'];
		params['formKey'] = row['FORM_KEY_'];
		params['formUrl'] = row['FORM_URL'];
		params['ticketStatus'] = row['TICKET_STATUS'];
		//查询都是相同的工单类别
		params['ticketCategory'] = row['TICKET_CATEGORY'];
		params['canPermission'] = row['CANPERMISSION'];
		params['applyId'] = row['APPLY_ID'];   //APPLY_ID申请人id
		params['loginUserId'] = loginUserId;   //APPLY_ID申请人id

		if(row['BUSNAME'] == null || row['BUSNAME'] == undefined || row['BUSNAME'] == ""){
			return "未命名";
		}

		return '<a href="javascript:void(0);" onclick="jbpm_apply(\''+params['ticketInstId']+'\'' +
			'                                                       ,\''+params['prodInstId']+'\'' +
			'                                                       ,\''+params['serialNumber']+'\'' +
			'                                                       ,\''+params['taskName']+'\'' +
			'                                                       ,\''+params['formKey']+'\'' +
			'                                                       ,\''+params['ticketStatus']+'\'' +
			'                                                       ,\''+params['ticketCategory']+'\'' +
			'                                                       ,\''+params['canPermission']+'\'' +
			'                                                       ,\''+params['applyId']+'\'' +
			'                                                       ,\''+params['loginUserId']+'\'' +
			'                                                   )">'+value+'</a>';
	}




</script></html>