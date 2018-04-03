/*
 * 有关流程模型的所有js都在这里
 */
// $(function() {
//     var params = getRequestRackParams();
//     loadRackGrid("rack_gridId",params);
//     //初始化   点击事件
//     /*$('#rackApply_tabs').tabs({
//         border:false,
//         onSelect:function(title){
//             if(title == '列表数据'){
//                 /!*直接隐藏所有发起工单按钮*!/
//                 $("#rack_gridId").datagrid("reload");
//             }else{
//                 $("#rack_gridId").datagrid("reload");
//             }
//         }
//     });*/
// });
function getRequestRackParams(){
    var rackName_params = $("#rackName_params").textbox("getValue");
    var nodeId = $("#nodeId").val();
    /*参数组装*/
    var params = {};
    params['rackName'] = rackName_params;
    params['nodeParam'] = nodeId;
    params['rackOrracunit'] = rackOrracunit;
    params['locationId'] = locationId;
    return params;
}
/**
 * 获取所有的相关数据
 * @returns {Array}
 */
function getSelectionData(){
    //获取被选中的所有资源信息
    var rack_space = $("#rackApply_tabs").tabs('getSelected');
    var rack_index = $('#rackApply_tabs').tabs('getTabIndex',rack_space);
    if(rack_index == 1){
        try {
            var roomlayoutIframewin = document.getElementById("roomlayoutIframeId").contentWindow || document.getElementById("roomlayoutIframeId").contentDocument.parentWindow;
            var records = roomlayoutIframewin.doSubmit();
            return records;
        } catch(error) {
            return [];
        } finally {
            // 此处是出口语句
        }
    }else{
        var records = $("#rack_gridId").datagrid("getChecked");
        var records_tmps = [];
        for(var i = 0 ; i < records.length ; i++){
            var singleRecordData = records[i];
            records_tmps[i] =singleRecordData['rackId'];
        }
        return records_tmps;
    }
}
//该方法没有再使用了
function getSelectionDataForceAjax(gridId,ticketInstId,category){
    /*判断被选中的是哪个tab*/
    var rack_space = $("#rackApply_tabs").tabs('getSelected');
    var rack_index = $('#rackApply_tabs').tabs('getTabIndex',rack_space);
    if(rack_index == 1){
        //获取相应的方法
        var roomlayoutIframewin = document.getElementById("roomlayoutIframeId").contentWindow || document.getElementById("roomlayoutIframeId").contentDocument.parentWindow;
        var records = roomlayoutIframewin.doSubmit();
        //alert(records);
        //consle.debug(records);
        if(records && records.length == 0){
            /*弹出提示框*/
            layer.msg('没有选择记录', {
                icon: 2,
                time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
            }, function(){
                //do something
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
                top.layer.close(index);
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
                layer.msg('添加成功!', {
                    icon: 1,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                });
                //选择端口后关闭当前窗口
                top.layer.close(index);
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
        url:contextPath+'/winOpenController/rackGridPanelData.do',
        title: '机架列表...',
        iconCls:'',
        queryParams: params,
        toolbar: '#requestParamSettins_gridId',
        border: false,
        singleSelect: false,
        striped : true,
        fit:true,
        pagination: true,
        pageSize: 15,
        pageList:[10,15,20,25,30],
        rownumbers: true,
        fitColumns: true,
        autoRowHeight:true,
        singleSelect:true,
        onClickRow: fun,
        columns: [
            [
                {field: 'checkbox', hidden: true, halign: 'left'},
                {field: 'rackName', title: '名称', halign: 'left', width: 250},
                {field: 'rackMode', title: '机架型号', halign: 'left', width: 150},
                //{field: 'machineroomName', title: '所属机房', halign: 'left', width: 200},
                // {field: 'renttype', title: '出租类型', halign: 'left', width: 100,formatter:function(value,row,index) {
                //     if(value ==1){
                //         return "按机位出租"
                //     }else{
                //         return "整架出租"
                //     }
                // }},
                {field: 'rackSize', title: '机架尺寸',halign:'left',width:100},
                {field: 'rackStatusName', title: '状态', halign: 'left', width: 100},
                {field: 'ratedelectricenergy', title: '额定电量', halign: 'left', width: 100},
                {field: 'pduPowertype', title: '用电类型', halign: 'left', width: 100}
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