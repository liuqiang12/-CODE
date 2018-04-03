/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
        console.log("js的port："+port);
        if(port && port == "true"){
            var params = getRequestPortParams();
            loadportGrid("ticket_port_gridId",params);
        }
});
function getRequestPortParams(){
    var portName_params = $("#portName_params").val();
    /*控制一下*/
    var isProcessEnd_param = $("#isProcessEnd_param").val();
    var isRubbish_param = $("#isRubbish_param").val();
    var is_author_apply_show = $("#is_author_apply_show").val();

    /*参数组装*/
    var params = {};
    params['portName'] = portName_params;
    params['isProcessEnd'] = isProcessEnd_param;
    params['is_author_apply_show'] = is_author_apply_show;
    params['isRubbish'] = isRubbish_param;
    return params;
}

function loadportGrid(gridId,params){
    if(!params){
        params = getRequestPortParams();
    }
    $('#'+gridId).datagrid({
        url:contextPath+'/actJBPMController/ticketPortResourceQueryData/'+$("#ticketInstId_param").val()+'/200',
        title: '带宽端口订单...',
        iconCls:'',
        queryParams: params,
        toolbar: '#requestParamSettins_ticket_port_gridId',
        border: false,
        singleSelect: true,
        striped : true,
        pagination: true,
        pageSize: 7,
        pageList:[7,10],
        rownumbers: true,
        fitColumns:true,
        autoRowHeight:true,
        columns: [
            [
                {field: 'resourcename', title: '端口名称',halign:'left',width:100,formatter:targetNameFmt },
                {field: 'belongName', title: '所属设备 ',halign:'left',width:280,formatter:belongNameFmt },
                /*{field: 'portId', title: '端口id ',halign:'left',width:50},*/
                {field: 'resourceStatus', title: '状态 ',halign:'left',width:100},
                {field: 'ipName', title: '地址',halign:'left',width:100},
                {field: 'portbandwidth', title: '端口带宽 ',halign:'left',width:100},
                {field: 'portassigation', title: '分派带宽 ',halign:'left',width:100},
                {field: 'operate',hidden : $("#isShowGridColumnHandlerFlag").val() == "true"?false:true, title: '操作',width:100,halign:'center',formatter:fmtPortRackAction }
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
            $(".targetName_tip").tooltip({
                onShow: function(){
                    $(this).tooltip('tip').css({
                        width:'250',
                        height:'50',
                        boxShadow: '1px 1px 3px #292929'
                    });
                }}
            );
        }
    });
}

//这个是操作是否删除的方法
function  fmtPortRackAction(value,row,index) {

    var insert= [];
    var ID= row.id;   //对应的id
    var resourceid = row.resourceid;/*资源id*/
    /*其实就只需要中间表id和id和设备id*/
    /* 目前只有申请的时候才能修改和删除，即：form是pre_accept_apply_form */
    /*insert.push('<a class="easyui-linkbutton " title="配置" data-options="plain:true,iconCls:\'icon-settting\'" onclick="singleMCBSettingsRow('+id+',\''+rackId+'\',\''+roomId+'\')">配置</a> ');*/
    insert.push('<a class="easyui-linkbutton " title="删除" data-options="plain:true,iconCls:\'icon-scqd-resource\'" onclick="singlePortDeleteRow('+ID+','+resourceid+')"><span class="icon-scqd-resource-over-d">删除</span></a> ');
    return insert.join('');
}
//下面两个是删除的方法
function singlePortDeleteRow(id,resourceid) {
    var ticketInstId=$("#ticketInstId_param").val();
    /*ajax*/
    top.layer.confirm('你确定要移除所选端口带宽吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){

        /*ajax*/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
                url:contextPath+"/actJBPMController/deleteTicketResource.do",
            //提交的数据
            //"resourceCategory"代表分配方式，100机架;200端口带宽;300ip租用;400主机租赁;500增值业务;600机位（u位）
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
                //提示删除成功
                //提示框
                layer.msg(data.msg, {
                    icon: 1,
                    time: 3000 // （如果不配置，默认是3秒）
                }, function(){
                    //do something
                });
                //选择端口后关闭当前窗口
                top.layer.close(index);
                $("#ticket_port_gridId").datagrid("reload");
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

//这个是端口带宽的方法
function prePortChoiceOperate(gridId){
    //这里ajax 获取相应的机房ids
    $.ajax({
        type:"POST",
        url:contextPath+"/actJBPMController/loadRoomsWithTicket.do",
        data:{ticketInstId:$("#ticketInstId_param").val()},
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        beforeSend:function(){
        },
        success:function(data){
            //获取所有的机房ids
            var roomidstr = data.result;
            var laySum = top.layer.open({
                type: 2,
                title: '<span style="color:red">端口带宽</span>'+"》选择",
                shadeClose: false,
                shade: 0.8,
                btn: ['确定','关闭'],
                maxmin : true,
                area: ['1084px', '650px'],
                //得到机架的id，把id拼成地址传过去得到端口信息
                content: contextPath+"/netport/distributionNetPort?roomIds="+roomidstr,
                /*弹出框*/
                cancel: function(index, layero){
                    top.layer.close(index);
                },yes: function(index, layero){
                    var childIframeid = layero.find('iframe')[0]['name'];
                    var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                    var ports = chidlwin.doSubmit();
                    //拿到ports得到的数据后，就往后台数据库中保存
                    if(ports.length==0){
                        top.layer.msg('没有选择端口带宽', {
                            icon: 2,
                            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                        });
                        return false;
                    }
                    var sets = [];
                    for(var i = 0 ; i < ports.length ; i++){
                        var singleRecordData = ports[i];
                        var srd = {};
                        srd['ticketInstId'] = $("#ticketInstId_param").val();
                        srd['resourceid'] = singleRecordData.PORTID;
                        srd['status'] = 1;/!*新增*!/;
                        srd['isIpPort'] = true;/!*端口带宽标志*!/;
                        srd['manual']= 1;//手工添加方式
                        srd['category']= 200;//手工添加方式
                        srd['ticketCategory']=$("#category_param").val();//预勘/预占
                        srd['deviceid']=singleRecordData.DEVICEID;//设备标识
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
                            "resourceCategory":200
                        },
                        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                        beforeSend:function(){
                        },
                        success:function(data){
                            layer.msg('成功', {
                                icon: 1,
                                time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                            }, function(){
                                //do something
                            });
                            //选择端口后关闭当前窗口
                            top.layer.close(index);
                            $("#"+gridId).datagrid("reload");
                        },
                        complete: function(XMLHttpRequest, textStatus){
                            return true;
                        },
                        error: function(){
                            return false;
                        }
                    });
                }
            });
        },
        complete: function(XMLHttpRequest, textStatus){
        },
        error: function(){
        }
    });
}
