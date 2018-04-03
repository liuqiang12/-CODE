/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
        console.log("js的server："+server);
        if(server && server == "true"){
            var params = getRequestServerParams();
            loadServerGrid("ticket_server_gridId",params);
        }

});
function getRequestServerParams(){
    try {
        var serverName_params = $("#serverName_params").textbox("getValue");
    } catch(error) {}
    /*控制一下*/
    var isProcessEnd_param = $("#isProcessEnd_param").val();
    var isRubbish_param = $("#isRubbish_param").val();
    var is_author_apply_show = $("#is_author_apply_show").val();


    /*参数组装*/
    var params = {};
    params['serverDeviceName'] = serverName_params;
    params['isProcessEnd'] = isProcessEnd_param;
    params['is_author_apply_show'] = is_author_apply_show;
    params['isRubbish'] = isRubbish_param;
    return params;
}
function preServerChoiceOperate(gridId){
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:red">主机</span>'+"》选择",
        shadeClose: false,
        shade: 0.8,
        btn: ['确定','关闭'],
        maxmin : true,
        area: ['984px', '650px'],
        content: contextPath+"/winOpenController/serverLayout.do",
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            //直接调用ajax保存信息
            /*获取相应的值*/
            var records = chidlwin.getSelectionData_();
            if(records.length == 0){
                top.layer.msg('请选择数据', {
                    icon: 2,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                });
                return false;
            }
            var sets = [];
            for(var i = 0 ; i < records.length ; i++){
                var singleRecordData = records[i];
                var srd = {};
                srd['ticketInstId'] = $("#ticketInstId_param").val();
                srd['resourceid'] = singleRecordData['serverDeviceId'];
                srd['status'] = 1;/*新增*/
                srd['manual']= 1;//手工添加方式
                srd['category']= 400;//手工添加方式
                srd['ticketCategory']=$("#category_param").val();//预勘/预占
                sets.push(srd);
            }
            var customerId=$("#customerId").val();
            var prodInstId=$("#prodInstId_param").val();
            var customerName=$("#customerName").val();
            var ticketInstId=$("#ticketInstId_param").val();
            var ticketCategory=$("#category_param").val();
            //"resourceCategory"代表分配方式，100机架;200端口带宽;300ip租用;400主机租赁;500增值业务;600机位（u位）
            $.ajax({
                type:"POST",
                url:contextPath+"/actJBPMController/createTicketServer.do",
                data:{winServerVoListStr:JSON.stringify(sets),
                    "customerId":customerId,
                    "prodInstId":prodInstId,
                    "customerName":customerName,
                    "ticketInstId":ticketInstId,
                    "ticketCategory":ticketCategory,
                    "resourceCategory":400
                },
                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                beforeSend:function(){
                },
                success:function(data){
                    var obj = jQuery.parseJSON(data);
                    top.layer.msg(obj.msg, {
                        icon: 1,
                        time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                    });
                    //选择端口后关闭当前窗口
                    top.layer.close(index);
                    $("#"+gridId).datagrid("reload");
                },
                complete: function(XMLHttpRequest, textStatus){
                },
                error: function(){
                }
            });
        }
    });
}
function loadServerGrid(gridId,params){
    if(!params){
        params = getRequestServerParams();
    }
    $('#'+gridId).datagrid({
        url:contextPath+'/actJBPMController/ticketServerResourceQueryData/'+$("#ticketInstId_param").val()+'/400',
        title: '主机租赁订单...',
        iconCls:'',
        queryParams: params,
        toolbar: '#requestParamSettins_ticket_server_gridId',
        border: false,
        singleSelect: true,
        striped : true,
        pagination: true,
        pageSize: 7,
        pageList:[7,10],
        rownumbers: true,
        fitColumns: true,
        autoRowHeight:true,
        nowrap: true,
        columns: [
            [
                {field: 'serverDeviceName', title: '设备的名称',halign:'left',width:140},
                {field: 'serverOs', title: '操作系统',halign:'left',width:80},
                /*{field: 'serverDeviceModel', title: '设备规格 ',halign:'left',width:80},
                {field: 'serverCpusize', title: '主频大小 ',halign:'left',width:80},*/
                /*{field: 'serverMemsize', title: '内存大小 ',halign:'left',width:60},
                {field: 'serverDisksize', title: '硬盘大小 ',halign:'left',width:60},*/
                {field: 'serverDeviceStatusName', title: '使用状态 ',halign:'left',width:80},
                {field: 'serverDeviceVendorName', title: '厂商 ',halign:'left',width:80},
                /*{field: 'serverDeviceOwner', title: '联系人 ',halign:'left',width:80},*/
                {field: 'serverOwnertypeName', title: '产权性质 ',halign:'left',width:80},
                {field: 'operate',hidden : $("#isShowGridColumnHandlerFlag").val() == "true"?false:true, title: '操作',width:100,halign:'center',formatter:fmtOperateServerAction }
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
function  fmtOperateServerAction(value,row,index) {
    var insert= [];
    //订单ID
    var id = row.id;/*工单资源中间表ID*/
    var serverDeviceId = row.serverDeviceId;/*设备ID*/
    /*其实就只需要中间表id和id和设备id*/
    insert.push('<a class="easyui-linkbutton " title="删除" data-options="plain:true,iconCls:\'icon-cancel\'" onclick="singleServerDeleteRow('+id+',\''+serverDeviceId+'\')">删除</a> ');
    return insert.join('');
}
function singleServerDeleteRow(id,deviceId) {
    var ticketInstId=$("#ticketInstId_param").val();
    /*ajax*/
    top.layer.confirm('你确定要移除所选主机吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/actJBPMController/deleteTicketResource.do",
            //提交的数据
            data:{
                'ticketInstId' : ticketInstId,
                'primaryId' : id
            },
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
            },
            //成功返回之后调用的函数
            success:function(data){
                var obj = jQuery.parseJSON(data);
                //提示删除成功
                //提示框
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                }, function(){
                    //do something
                });
                //选择端口后关闭当前窗口
                top.layer.close(index);
                $("#ticket_server_gridId").datagrid("reload");
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