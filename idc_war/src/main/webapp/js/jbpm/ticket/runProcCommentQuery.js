/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    loadCommnetGrid("ticket_comment_gridId");
});
function loadCommnetGrid(gridId){
    //创建表信息
    var url = getCommnetRequestUrl();
    var columns = createCommnetColumns();
    $("#"+gridId).datagrid({
        url : contextPath + url,
        columns : columns,
        onLoadSuccess:myCommnetLoadsuccess
    });
}
//初始化按钮事件
function myCommnetLoadsuccess(data){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
}
function getCommnetRequestUrl(){
    return '/actJBPMController/runProcCommentQueryData/'+$("#prodInstId_param").val()+'/'+$("#ticketInstId_param").val();
}
//获取列信息
function createCommnetColumns(){
    //创建列表信息
    var headCols = [];
    headCols.push({field:"actName",title:"任务名称",halign:'left',width:100});
    headCols.push({field:"author",title:"审批人",halign:'left',width:80});
    headCols.push({field:"comment",title:"描审批意见述",halign:'left',width:150});
    headCols.push({field:"status",title:"审批状态",halign:'left',formatter:fmtStatusAction,width:80});
    headCols.push({field:"createTime",title:"审批时间",halign:'left',formatter:fmtDateAction,width:200});
    /*操作按钮*/
    return [headCols]
}
function fmtStatusAction(value,row,index){
    return value == 1?"通过":"不通过";
}