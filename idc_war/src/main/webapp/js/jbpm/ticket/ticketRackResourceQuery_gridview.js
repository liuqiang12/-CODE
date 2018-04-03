/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    loadRackGrid("ticket_rack_gridId");
});

function loadRackGrid(gridId){
    $('#'+gridId).datagrid({
        url:contextPath+'/actJBPMController/ticketRackResourceQueryData/'+$("#ticketInstId_param").val()+'/100',
        title: '机架机位订单,选择可用的客户机架的同时体现MCB资源的信息...',
        iconCls:'',
        toolbar: '#requestParamSettins_ticket_rack_gridId',
        border: false,
        fit: true,
        singleSelect: true,
        striped : true,
        pagination: true,
        pageSize: 15,
        pageList:[15,20,25,35,40],
        rownumbers: true,
        fitColumns: false,
        autoRowHeight:true,
        columns: [
            [
                {field: 'rackName', title: '机架名称',halign:'left'},
                {field: 'rackMode', title: '机架型号',halign:'left'},
                {field: 'machineroomName', title: '所属机房',halign:'left'},
                {field: 'directionName', title: '机架方向',halign:'left'},
                {field: 'rackSize', title: '机架尺寸',halign:'left'},
                {field: 'manufactureId', title: '品牌',halign:'left'},
                {field: 'operate', title: '操作',
                    formatter: function(value,row,index){
                        var html = '<a onclick="alert(1)" href="javascript:void(0);">【xxxx】</a>';
                        html = html + '<a onclick="alert(1)" href="javascript:void(0);">【xxxx】</a>';
                        return html;
                    }
                }
            ]
        ],
        view:detailview,
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
        },
        detailFormatter:function(index,row){//注意2
            return '<div style="padding:2px"><table id="ddv-' + index + '"></table></div>';
        } ,
        onExpandRow: function(index,row){
            $('#ddv-'+index).datagrid({
                    url:contextPath+'/actJBPMController/ticketRackmcbResourceQueryData/'+(row.rackId),
                fitColumns:true,
                singleSelect:true,
                height:'auto',
                columns:[[
                    {field:'taskId',title:'xxxxxxxx'}
                ]],
                onResize:function(){
                    $('#'+gridId).datagrid('fixDetailRowHeight',index);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $('#'+gridId).datagrid('fixDetailRowHeight',index);
                    },0);
                }
            });
            $('#'+gridId).datagrid('fixDetailRowHeight',index);
        }
    });
}