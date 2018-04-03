/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid_rack("ticket_rack_gridId");
});
function loadGrid_rack(gridId){
    //创建表信息
    var url = getRackRequestUrl();
    var columns = createRackColumns();
    $("#"+gridId).datagrid({
        url : contextPath + url,
        columns : columns,
        onLoadSuccess:myRackLoadsuccess
    });
}
//初始化按钮事件
function myRackLoadsuccess(data){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
}
function getRackRequestUrl(){
    return '/actJBPMController/ticketRackResourceQueryData/'+$("#ticketInstId_param").val()+'/100';
}
//获取列信息
function createRackColumns(){
    //创建列表信息
    var headCols = [];
    //分页最多显示5条数据
    headCols.push({field:"ticketIdcRackVo.rackName",width:230,title:"机架名称",halign:'left',formatter: function(value,row,index){return row.ticketIdcRackVo?row.ticketIdcRackVo.rackName:null}});
    headCols.push({field:"ticketIdcRackVo.rackMode",width:120,title:"机架型号",halign:'left',formatter: function(value,row,index){return row.ticketIdcRackVo?row.ticketIdcRackVo.rackMode:null}});
    headCols.push({field:"ticketIdcRackVo.machineroomName",width:230,title:"所属机房",halign:'left',formatter: function(value,row,index){return row.ticketIdcRackVo?row.ticketIdcRackVo.machineroomName:null}});
    headCols.push({field:"ticketIdcRackVo.directionName",width:80,title:"机架方向",halign:'left',formatter: function(value,row,index){return row.ticketIdcRackVo?row.ticketIdcRackVo.directionName:null}});
    headCols.push({field:"ticketIdcRackVo.rackSize",width:100,title:"机架尺寸",halign:'left',formatter: function(value,row,index){return row.ticketIdcRackVo?row.ticketIdcRackVo.rackSize:null}});
    headCols.push({field:"ticketIdcRackVo.manufactureId",width:100,title:"品牌",halign:'left',formatter: function(value,row,index){return row.ticketIdcRackVo?row.ticketIdcRackVo.manufactureId:null}});
    /*操作按钮*/
    return [headCols]
}