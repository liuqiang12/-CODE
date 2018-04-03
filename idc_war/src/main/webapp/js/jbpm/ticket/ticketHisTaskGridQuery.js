/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    var processDefinitonKey = $("#processDefinitonKey").val();
    if(processDefinitonKey != 'idc_ticket_pre_accept' && processDefinitonKey != 'idc_ticket_self_pre_accept') {
        loadHisGrid("his_gridId");
    }
});
function loadHisGrid(gridId){
    //创建表信息
    var url = getHisRequestUrl();
    var params = getHisRequestParams();
    var columns = createHisColumns();
    $("#"+gridId).datagrid({
        url : contextPath + url,
        queryParams: params,
        columns : columns,
        onLoadSuccess:myHisLoadsuccess,
        toolbar:"#requestParamSettins_taskhisQuery"
    })
}
//初始化按钮事件
function myHisLoadsuccess(data){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
}
function getHisRequestUrl(){
    return "/actJBPMController/hisTicketTaskData.do";
}

// 获取查询条件
function getHisRequestParams(){
    var his_serialNumber = $("#his_serialNumber").textbox("getValue");
    /*var his_customerName = $("#his_customerName").textbox("getValue");*/
    var processDefinitonKey = $("#processDefinitonKey").val();
    var prodCategory = $("#prodCategory").val();

    /*参数组装*/
    var params = {};
    params['serialNumber'] = his_serialNumber;/*
    params['customerName'] = his_customerName;*/
    params['processDefinitonKey'] = processDefinitonKey;
    params['prodCategory'] = prodCategory;
    return params;
}
//获取列信息customerId
function createHisColumns(){
    //创建列表信息
    var headCols = [];
    /*headCols.push({field:"ck",checkbox:true,halign:'left'});*/
    headCols.push({field:"SERIALNUMBER",title:"工单号",halign:'left',width:90});
    headCols.push({field:"CUSTOMERNAME",title:"客户名称",halign:'left',formatter:fmtCustomerLinkAction,width:90});
    headCols.push({field:"CUSTOMERATTR",title:"客户属性",halign:'left',width:90});
    headCols.push({field:"CONTACTMOBILE",title:"客户联系方式",halign:'left',width:90});
    headCols.push({field:"CATEGORYLINKED",title:"服务信息",halign:'left',width:90});
    headCols.push({field:"ACTNAME",title:"状态",halign:'left',width:90});
    headCols.push({field:"APPLY_NAME",title:"申请人",halign:'left',width:90});
    headCols.push({field:"USERS_",title:"审批人",halign:'left',width:90});
    headCols.push({field:"CREATETIME",title:"创建时间",halign:'left',width:90});

     
    return [headCols]
}
function linkCategoryWin(category,prodInstId){
    
}
function fmtHisCustomerLinkAction(value,row,index){

    //弹出用户信息
    // function fmtAction(value,row,index){
    return '<a href="javascript:void(0);" onclick="openWinCustomerIdFun(\''+row.customerId+'\',\''+row.customerName+'\')">'+value+'</a>';

} 