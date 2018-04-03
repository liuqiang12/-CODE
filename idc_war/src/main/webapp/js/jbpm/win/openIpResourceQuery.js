/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    var params = getRequestIpParams();
    loadIpGrid("ip_gridId",params);
});
function getRequestIpParams(){
    var ipName_params = $("#ipName_params").textbox("getValue");
    /*参数组装*/
    var params = {};
    params['ipIpaddress'] = ipName_params;
    return params;
}
function getSelectionIpDataForceAjax(gridId,ticketInstId,category){
    //获取所有的被选中的节点信息
    var records = $("#"+gridId).datagrid("getChecked");
    if(records && records.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    var sets = [];
    for(var i = 0 ; i < records.length ; i++){
        var singleRecordData = records[i];
        var srd = {};
        srd['ticketInstId'] = ticketInstId;
        srd['resourceid'] = singleRecordData['serverDeviceId'];
        srd['status'] = 1;/*新增*/
        srd['manual']= 1;//手工添加方式
        srd['category']= category;//手工添加方式
        srd['ticketCategory']='100';//预勘/预占
        sets.push(srd);
    }
    $.ajax({
        type:"POST",
        url:contextPath+"/actJBPMController/createTicketServer.do",
        data:{winServerVoListStr:JSON.stringify(sets)},
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        beforeSend:function(){
        },
        success:function(data){
            top.layer.msg(data.msg, {
                icon: 1,
                time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
            });
            var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
            parent.layer.close(parentIndex);  // 关闭layer
        },
        complete: function(XMLHttpRequest, textStatus){
        },
        error: function(){
        }
    });
    return true;
}
function forceReload(gridId){
    $("#"+gridId).datagrid("reload");
}
function loadIpGrid(gridId,params){
    if(!params){
        params = getRequestIpParams();
    }
    $('#'+gridId).datagrid({
        url:contextPath+'/winOpenController/ipGridPanelData.do',
        title: 'IP列表...',
        iconCls:'',
        queryParams: params,
        toolbar: '#requestParamSettins_gridId',
        border: false,
        singleSelect: false,
        striped : true,
        fit:true,
        pagination: true,
        pageSize: 10,
        pageList:[10,15,20,25,30],
        rownumbers: true,
        fitColumns: true,
        autoRowHeight:true,
        columns: [
            [
                {field: 'checkbox', checkbox:true,halign:'left'},
                {field: 'ipIpaddress', title: '地址',halign:'left'},
                {field: 'ipSubnetip', title: '子网',halign:'left'},
                {field: 'ipMaskstr', title: '掩码 ',halign:'left'},
                {field: 'ipStatusName', title: '使用状态 ',halign:'left'}
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