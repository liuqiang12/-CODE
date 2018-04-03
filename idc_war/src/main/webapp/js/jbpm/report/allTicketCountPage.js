$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
});

function loadGrid(gridId){
    //alert(jQuery.fn.jquery);
    var columns = createColumns();
    $("#"+gridId).datagrid({
        url : contextPath + '/actJBPMController/allTicketCountPageData.do',
        queryParams: {'createTime': $('#createTime').val(),
                      'endTime': $("#endTime").val()},
        /*onLoadSuccess:myLoadsuccess,*/
        columns:columns,
        toolbar:"#requestParamSettins_taskQuery"
    });
}

function createColumns(){
    var headCols = [];
    headCols.push({field:"PRODTYPE",title:"业务类型",align:'center',width:40});
    headCols.push({field:"TICKETTYPE",title:"流程类型",align:'center',width:40});
    headCols.push({field:"TICKETCOUNT",title:"工单合计（个）",align:'center',width:40});
    return [headCols];
}

