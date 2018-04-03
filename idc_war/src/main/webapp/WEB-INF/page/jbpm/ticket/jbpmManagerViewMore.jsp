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
    <title>经理视图查看更多发起的工单</title>
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
	</style>
</head>
<body fit="true">
			 <input id="loginUserId" value="${loginUserId}" type="hidden"/>
			 <div style="padding: 5px;" id="requestParamSettins_taskQuery">
				 工单编号：<input class="easyui-textbox"  id="serialNumber" style="width:200px;text-align: left;" data-options="">
				 <a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
				 <a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'mydefined_reset'">刷新</a>
			 </div>
			 <table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: true,striped: true,
			 rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,
			 fitColumns:true,toolbar:'#requestParamSettins_taskQuery',loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;} ">
			 </table>

</body>
<jsp:include page="/globalstatic/easyui/publicButton.jsp"></jsp:include>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketTaskCommonQuery.js"></script>
<script>
$(function() {
	//初始化时就加载数据:构造列表信息
	loadGrid("gridId");
});
function loadGrid(gridId){
	//创建表信息
	var columns = createColumns();
	$("#"+gridId).datagrid({
		url : contextPath + "/actJBPMController/jbpmManagerView.do",
		columns : columns,
	})
}
//初始化按钮事件
function myTaskLoadsuccess(data){
	$(".datagrid-cell").find('.easyui-linkbutton').each(function () {
		$(this).linkbutton();
	});
	$(".ticket_reject_tip").tooltip({
		onShow: function(){
			$(this).tooltip('tip').css({
				width:'300',
				boxShadow: '1px 1px 3px #292929'
			});
		}}
	);
}

//获取列信息
function createColumns(){
	//创建列表信息
	var headCols = [];
	headCols.push({field:"BUSNAME",title:"业务名称",halign:'left',width:50,formatter:formatterBusNameCD});
	headCols.push({field:"SERIAL_NUMBER",title:"工单号",halign:'left',width:50});
	headCols.push({field:"CUSTOMERNAME",title:"客户名称",halign:'left',width:90});
	/*headCols.push({field:"TITLE",title:"申请人名称",halign:'left',width:60});*/
	headCols.push({field:"TASK_NAME",title:"工单状态",halign:'left',width:40});
    headCols.push({field:"TICKETCATEGORY",title:"工单类型",halign:'left',width:40});
    headCols.push({field:"CREATE_TIME",title:"创建时间",halign:'left',width:70});
	return [headCols]
}

function formatterBusNameCD(value,row,index){
    var loginUserId=$("#loginUserId").val();    //当前登陆用户的id
    var params = {};
    params['prodInstId'] = row['PROD_INST_ID'];
    params['ticketInstId'] = row['TICKET_INST_ID'];
    params['serialNumber'] = row['SERIAL_NUMBER'];
    params['taskName'] = row['TASK_NAME'];
    params['formKey'] = row['FORM_KEY_'];
    params['formUrl'] = row['FORM_URL'];
    params['ticketStatus'] = row['TICKET_STATUS'];
    //查询都是相同的工单类别
    params['ticketCategory'] = row['TICKET_CATEGORY'];

    var canperPermission=false;  //是否有权限审批
    if(row['CANPERMISSION'] == 1){
        canperPermission=true;
	}
    params['canPermission'] = canperPermission;
    params['applyId'] = row['APPLY_ID'];
    params['loginUserId'] = loginUserId;

    if(row['BUSNAME'] == null || row['BUSNAME'] == undefined || row['BUSNAME'] == ""){
        return "未命名";
    }
    return '<a href="javascript:void(0);" onclick="jbpm_apply(\''+params['ticketInstId']+'\',\''+
																params['prodInstId']+'\',\''+
																params['serialNumber']+'\',\''+
																params['taskName']+'\',\''+
																params['formKey']+'\',\''+
																params['ticketStatus']+'\',\''+
																params['ticketCategory']+'\',\''+
																params['canPermission']+'\',\''+
																params['applyId']+'\',\''+
																params['loginUserId']+'\')">'+value+'</a>';
}



function fmtProdNameLinkAction(value,row,index){
	//* 弹出客户需求 *//
	return '<a href="javascript:void(0);" onclick="linkProdNameQueryWin(\''+row.PRODINSTID+'\',\''+row.PRODNAME+'\')">'+value+'</a>';
}
function fmtCustomerLinkAction(value,row,index){
	//弹出用户信息
	return '<a href="javascript:void(0);" onclick="openWinCustomerIdFun(\''+row.CUSTOMERID+'\',\''+row.CUSTOMERNAME+'\')">'+value+'</a>';
}

//查看客户基本信息
function openWinCustomerIdFun(customerId,customerName){
	//弹出form表单查看客户基本信息
	var laySum = top.layer.open({
		type: 2,
		title: '<span style="color:blue">客户人员['+customerName+']</span>'+"》详情",
		shadeClose: false,
		shade: 0.8,
		btn: ['关闭'],
		maxmin : true,
		area: ['784px', '500px'],
		content: contextPath+"/customerController/linkQueryWin.do?viewQuery=1&id="+customerId,
		cancel: function(index, layero){
			top.layer.close(index);
		},no: function(index, layero){
			top.layer.close(index)
		}
	});
}

</script>

</html>