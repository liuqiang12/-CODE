/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    var params = getRequestAddParams();
    loadAddGrid("ticket_add_gridId",params);
});
function getRequestAddParams(){
    var addName_params = $("#addName_params").textbox("getValue");
    /*参数组装*/
    var params = {};
    params['addName'] = addName_params;
    return params;
}

function loadAddGrid(gridId,params){
    $('#'+gridId).datagrid({
        url:contextPath+'/actJBPMController/ticketRackmcbResourceQueryData/'+$("#ticketInstId_param").val()+'/100',
        title: '增值业务订单...',
        iconCls:'',
        queryParams: params,
        toolbar: '#requestParamSettins_ticket_add_gridId',
        border: false,
        singleSelect: true,
        striped : true,
        pagination: true,
        pageSize: 7,
        pageList:[7,10],
        rownumbers: true,
        fitColumns: true,
        autoRowHeight:true,
        columns: [
            [
                {field: 'mcbNo', title: 'MCB名称',halign:'left',width:80},
                {field: 'pdfName', title: '所属PDF ',halign:'left',width:130},
                {field: 'powerStatusName', title: '业务状态 ',halign:'left',width:80},
                {field: 'customerRackName', title: '客户机架 ',halign:'left',width:130},
                {field: 'pdfPowerTypeName', title: '电源类型 ',halign:'left',width:80},
                {field: 'ratedelectricenergy', title: '额定电流 ',halign:'left',width:80,formatter: function(value,row,index){return value+" A"}}/*KWH*/
            ]
        ],
        onBeforeLoad:function(param){
            param['pageNo'] = param['page'];
            param['pageSize'] = param['rows'];
            return true;
        },
        loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},
        onLoadSuccess:function(){
            $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
                $(this).linkbutton();
            });
        }
    });
}