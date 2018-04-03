/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    var params = getRequestServerParams();
    loadServerGrid("server_gridId",params);
});
function getRequestServerParams(){
    var serverDeviceName_params = $("#serverDeviceName_params").textbox("getValue");
    /*参数组装*/
    var params = {};
    params['serverDeviceName'] = serverDeviceName_params;
    return params;
}
function getSelectionData_(){
    var records = $("#server_gridId").datagrid("getChecked");
    return records;
}
function getSelectionDataForceAjax(gridId,ticketInstId,category){
    //获取所有的被选中的节点信息
    var records = $("#"+gridId).datagrid("getChecked");
    if(records && records.length == 0){
        /*弹出提示框*/
        layer.msg('请选择数据', {
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
            layer.msg(data.msg, {
                icon: 1,
                time: 3000 //1.5秒关闭（如果不配置，默认是3秒）
            }, function(){
                //do something
            });
            //选择端口后关闭当前窗口
            top.layer.close(index);
            var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
            parent.layer.close(parentIndex);  // 关闭layer
        },
        complete: function(XMLHttpRequest, textStatus){
            return true;
        },
        error: function(){
            return false
        }
    });

}
function forceReload(gridId){
    $("#"+gridId).datagrid("reload");
}
function loadServerGrid(gridId,params){
    if(!params){
        params = getRequestServerParams();
    }
    $('#'+gridId).datagrid({
        url:contextPath+'/winOpenController/serverGridData.do',
        title: '主机列表...',
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
        fitColumns: false,
        autoRowHeight:true,
        columns: [
            [
                {field: 'checkbox', checkbox: true},
                {field: 'serverDeviceName', title: '设备名称', width: 200},
                {field: 'serverOs', title: '操作系统', width: 100},
                {field: 'serverDeviceModel', title: '设备规格 ', width: 100},
                {field: 'serverCpusize', title: '主频大小 ', width: 100},
                {field: 'serverMemsize', title: '内存大小 ', width: 100},
                {field: 'serverDisksize', title: '硬盘大小 ', width: 100},
                {field: 'serverRackName', title: '所属机架名称 ', width: 200},
                {field: 'serverDeviceStatusName', title: '使用状态 ', width: 100},
                {field: 'serverDeviceVendorName', title: '厂商 ', width: 100},
                {field: 'serverDeviceOwner', title: '联系人 ', width: 100},
                {field: 'serverOwnertypeName', title: '产权性质 ', width: 100},
                {field: 'operate', title: '操作', width: 100, formatter: fmtOperateServerAction}

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
//操作删除按钮
function  fmtOperateServerAction(value,row,index) {
    var insert= [];
    //订单ID
    var id = row.id;/*工单资源中间表ID*/
    var ipId = row.ipId;/*IP  ID*/
    var ipType = row.ipType;
    /*其实就只需要中间表id和id和设备id*/
    insert.push('<a class="easyui-linkbutton " title="删除" data-options="plain:true,iconCls:\'icon-cancel\'" onclick="singleServerDeleteRow('+id+',\''+ipId+'\',\''+ipType+'\')">删除</a> ');
    return insert.join('');
}
//操作删除的函数
function singleServerDeleteRow(id,ipId,ipType){
    /*ajax*/
    top.layer.confirm('你确定要移除所选主机吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/actJBPMController/ticketServerIpRecoverInitServer/"+id+"/"+ipId+"/"+ipType+"/20/300",
            //提交的数据
            //data:{
            //},
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
            },
            //成功返回之后调用的函数
            success:function(data){
                //提示删除成功
                //提示框
                layer.msg(data.msg, {
                    icon: 1,
                    time: 3000 //1.5秒关闭（如果不配置，默认是3秒）
                }, function(){
                    //do something
                });
                //选择端口后关闭当前窗口
                top.layer.close(index);
                $("#ticket_ip_gridId").datagrid("reload");
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
            },
            //调用出错执行的函数
            error: function(){
                //请求出错处理
            }
        });
    }, function(index, layero){
        top.layer.close(index)
    });
}