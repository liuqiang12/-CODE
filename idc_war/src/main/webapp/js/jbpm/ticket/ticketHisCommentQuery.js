
/*下载附件信息*/
function downLoadAttach(id){
    $("#downloadFile").attr("src",contextPath+"/assetAttachmentinfoController/downLoadFile/"+id);
}

/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    loadHisCommentGrid("ticket_hisComment_gridId");
});
function getHisCommentGridParams(){
    var is_author_apply_show = $("#is_author_apply_show").val();
    var params = {};
    params['is_author_apply_show'] = is_author_apply_show;
    return params;
}
function loadHisCommentGrid(gridId){
    var params = getHisCommentGridParams();
    $('#'+gridId).datagrid({
        url:contextPath+'/actJBPMController/runProcCommentQueryData/'+$("#prodInstId_param").val()+'/'+$("#ticketInstId_param").val(),
        border: false,
        title: '',
        queryParams: params,
        iconCls:'',
        singleSelect: true,
        striped : true,
        rownumbers: true,
        fitColumns: true,
        autoRowHeight:true,
        columns: [
            [ {field: 'actName', title: '名称',halign:'left',width:100},
                {field: 'author', title: '处理人',halign:'left',width:80},
                {field: 'comment', title: '处理意见',halign:'left',width:150},
                {field: 'status', title: '状态 ',halign:'left',width:130,formatter:fmtStatusAction},
                {field: 'createTime', title: '时间 ',halign:'left',width:200,formatter:fmtDateAction,width:200}
            ]
        ],
        onLoadSuccess:function(){
            $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
                $(this).linkbutton();
            });
        }
    });
}
function fmtStatusAction(value,row,index){
    return value == 1?"通过":"不通过";
}