/*
 * 有关流程模型的所有js都在这里
 */

$(function() {

    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
    $('#jbpm_tabs').tabs({
        border:false,
        onSelect:function(title){
            if(title == '待办任务'){

                /*直接隐藏所有发起工单按钮*/
                //$("div.jbpm_process_back_float_right").css("display","none");
                $("div.jbpm_process_back_float_right").css("display","block");
                loadGrid("gridId");
            }else{
                $("div.jbpm_process_back_float_right").css("display","block");
                //刷新
                loadPreGrid("pre_gridId");
            }
        }
    });
});

function loadGrid(gridId){
    //创建表信息
    var url = getRequestUrl();
    var params = getRequestParams();
    var columns = createColumns();
    $("#"+gridId).datagrid({
        url : contextPath + url,
        queryParams: params,
        columns : columns,
        onLoadSuccess:myTaskLoadsuccess,
        toolbar:"#requestParamSettins_taskQuery"
    })
}　
function getRequestUrl(){
    return "/actJBPMController/runTicketTaskData.do";
}

// 获取查询条件
function getRequestParams(){
    var serialNumber = $("#serialNumber").textbox("getValue");
    var processDefinitonKey = $("#processDefinitonKey").val();
    var busName = $("#busName").val();
    var prodCategory = $("#prodCategory").val();
    var locationCode = $("#locationCode").val();

    /*参数组装*/
    var params = {};
    params['serialNumber'] = serialNumber;
    params['processDefinitonKey'] = processDefinitonKey;
    params['prodCategory'] = prodCategory;
    params['busName'] = busName;
    params['locationCode'] = locationCode;
    return params;
}
//获取列信息
function createColumns(){
    //创建列表信息
    var headCols = [];

    /*headCols.push({field:"ck",checkbox:true,halign:'left'});   //这个是复选框*/
    headCols.push({field:"busname",title:"业务名称",halign:'left',width:55,formatter:formatterBusNameAB});
    headCols.push({field:"idcName",title:"意向IDC",halign:'left',width:40});
    headCols.push({field:"serialNumber",title:"工单编号",halign:'left',width:40});
    headCols.push({field:"customerName",title:"客户名称",halign:'left',width:50});
    headCols.push({field:"taskName",title:"状态",halign:'left',width:45});
    headCols.push({field:"applyName",title:"申请人",halign:'left',width:20});
    headCols.push({field:"candidate",title:"审批人",halign:'left',width:40});
    headCols.push({field:"createTimeStr",title:"创建时间",halign:'left',width:30});
    /*操作按钮*/
    return [headCols]
}