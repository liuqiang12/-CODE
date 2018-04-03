/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    var params = getRequestRackParams();
    loadRackGrid("rack_gridId",params);
});
function getRequestRackParams(){
    var rackName_params = $("#rackName_params").textbox("getValue");
    /*参数组装*/
    var params = {};
    params['rackName'] = rackName_params;
    return params;
}
function getSelectionDataForceAjax_Temporary(gridId,ticketInstId,category){
    /*判断被选中的是哪个tab*/
    var rack_space = $("#rackApply_tabs").tabs('getSelected');
    var rack_index = $('#rackApply_tabs').tabs('getTabIndex',rack_space);
    if(rack_index == 1){
        //获取相应的方法
        var roomlayoutIframewin = document.getElementById("roomlayoutIframeId").contentWindow || document.getElementById("roomlayoutIframeId").contentDocument.parentWindow;
        var records = roomlayoutIframewin.doSubmit();
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
            srd['resourceid'] = singleRecordData;
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
                var obj = jQuery.parseJSON(data);
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
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
    }else{
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
            srd['resourceid'] = singleRecordData['rackId'];
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
                var obj = jQuery.parseJSON(data);
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
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
}
function forceReload(gridId){
    $("#"+gridId).datagrid("reload");
}
function loadRackGrid(gridId,params){
    if(!params){
        params = getRequestRackParams();
    }
    $('#'+gridId).datagrid({
        /*url:contextPath+'/winOpenController/rackGridPanelData.do',*/
        url:contextPath+'/idcRackunit/queryRackunitList?rackIds=3307',
        title: '机架列表...',
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
                //{field: 'ID', checkbox:true,halign:'left'},
                //{field: 'UNO', title: '机位号',halign:'left',width:350},
                {field: 'RNAME', title: '机架',halign:'left',width:100},
                //{field: 'DNAME', title: '设备',halign:'left',width:300},
                //{field: 'SITENAME', title: '机房',halign:'left',width:100},
                //{field: 'ORDERNO', title: '顺序',halign:'left',width:100},
                //  {field: 'IDCNO', title: 'IDC编号',halign:'left',width:100},
                {field: 'STATUS', title: '状态',halign:'left',width:100},
               //{field: 'USEFOR', title: '用途',halign:'left',width:100}

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