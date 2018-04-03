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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>合同信息列表</title>
</head>
<style type="text/css">
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0 !important;
    }
</style>
<body>
<div>
    <input id="prodInstId" value="${prodInstId}" type="hidden"/>
    <input id="ticketInstId" value="${ticketInstId}" type="hidden"/>
    <input id="contractId" value="${contractId}" type="hidden"/>
    <table class="easyui-datagrid" id="contract_gridId" data-options="singleSelect:true,fitColumns:true"></table>

</div>
</body>
<jsp:include page="/globalstatic/easyui/publicButton.jsp"></jsp:include>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketTaskCommonQuery.js"></script>
<script type="application/javascript">

    $(function() {
        //初始化时就加载数据:构造列表信息
        loadGrid("contract_gridId");
    });

    function loadGrid(gridId){
        var prodInstId = $("#prodInstId").val();
        var ticketInstId = $("#ticketInstId").val();
        var contractId = $("#contractId").val();

        /*参数组装*/
        var params = {};
        params['prodInstId'] = prodInstId;
        params['ticketInstId'] = ticketInstId;
        params['contractId'] = contractId;

        //创建表信息
        var params = params;
        var columns = createColumns();
        $("#"+gridId).datagrid({
            url : contextPath + "/contractController/getContractListPageData.do",
            queryParams: params,
            columns : columns/*,
             onLoadSuccess:myTaskLoadsuccess,
             toolbar:"#requestParamSettins_taskQuery"*/
        })
    }

    //获取列信息
    function createColumns(){
        //创建列表信息
        var headCols = [];
        /*headCols.push({field:"ck",checkbox:true,halign:'left'});   //这个是复选框*/
        headCols.push({field:"CONTRACTNAME",title:"合同名称",halign:'left',width:80,formatter:fmtGetContractList});
        headCols.push({field:"BUSNAME",title:"业务名称",halign:'left',width:80,formatter:formatterBusNameCD});
        headCols.push({field:"SERIAL_NUMBER",title:"工单号",halign:'left',width:70});
        headCols.push({field:"TASK_NAME_FY",title:"流程状态",halign:'left',width:70});
        headCols.push({field:"CONTRACTNO",title:"合同编码",halign:'left',width:80});
        headCols.push({field:"CONTRACTPRICE",title:"合同价格",halign:'left',width:50,formatter:fmtPriceAction});
        headCols.push({field:"CONTRACTSIGNDATE",title:"签订时间",halign:'left',width:80});
        headCols.push({field:"CUSTOMER_NAME",title:"所属客户",halign:'left',width:80});
        /*操作按钮*/
        return [headCols]
    }
    /*合同名称*/
    function fmtGetContractList(value,row,index){
        var contractId=(row.ID != undefined && row.ID != "" ) ? row.ID : null;
        var ticketInstId=(row.TICKET_INST_ID != undefined && row.TICKET_INST_ID != "" ) ? row.TICKET_INST_ID : null;
        var prodInstId=(row.PROD_INST_ID != undefined && row.PROD_INST_ID != "" ) ? row.PROD_INST_ID : 0;
        var contractName=row.NAME;

        return '<a href="javascript:void(0);" onclick="linkQueryWin(\''+row.CONTRACTID+'\',\''+row.CONTRACTNAME+'\')">'+value+'</a>';
    }

    function fmtPriceAction(value,row,index){
        var res_value = value || 0;
        return res_value +"元";
    }

    //查看详情界面
    function linkQueryWin(id,name){
        var laySum = top.layer.open({
            type: 2,
            title: '<span style="color:blue">'+name+'</span>'+"》详情",
            shadeClose: false,
            shade: 0.8,
            btn: ['关闭'],
            maxmin : true,
            area: ['900px', '600px'],
            content: contextPath+"/contractController/linkQueryWin.do?viewQuery=1&id="+id,
            cancel: function(index, layero){
                top.layer.close(index);
            },no: function(index, layero){
                top.layer.close(index)
            }
        });
    }


    function formatterBusNameCD(value,row,index){
        var loginUserId=null;    //当前登陆用户的id
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

        var canperPermission=false;  //当前查看合同页面不需要进行审批，所以不需要
        params['canPermission'] = canperPermission;
        params['applyId'] = row['APPLY_ID'];
        params['loginUserId'] = loginUserId;

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


</script>
</html>