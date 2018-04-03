/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
        console.log("js的ip："+ip);
        if(ip && ip == "true"){
            var params = getRequestIpParams();
            loadIpGrid("ticket_ip_gridId",params);
        }

});
function getRequestIpParams(){
    /*var ipName_params = $("#ipName_params").textbox("getValue");*/
    var ipName_params = $("#ipName_params").val();

    /*控制一下*/
    var isProcessEnd_param = $("#isProcessEnd_param").val();
    var isRubbish_param = $("#isRubbish_param").val();
    var is_author_apply_show = $("#is_author_apply_show").val();


    /*参数组装*/
    var params = {};
    params['resourcename'] = ipName_params;
    params['isProcessEnd'] = isProcessEnd_param;
    params['is_author_apply_show'] = is_author_apply_show;
    params['isRubbish'] = isRubbish_param;
    return params;
}
//选择IP界面:作废
function preIpChoiceOperate(gridId){
    //弹出框
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:red">IP</span>'+"》选择",
        shadeClose: false,
        shade: 0.8,
        btn: ['确定','关闭'],
        maxmin : true,
        area: ['1084px', '650px'],
        /*content: contextPath+"/winOpenController/ipGridPanel.do",*/
        content: contextPath+"/resource/ipsubnet/flowallc",
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            //直接调用ajax保存信息
            var records = chidlwin.doSubmit();
            if(records.rows.length  == 0 ){
                top.layer.msg('请选择数据', {
                    icon: 2,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                });
                return false;
            }
            var sets = [];
            console.log("records>>>"+records);
            for(var i = 0 ; i < records.rows.length ; i++){
                var singleRecordData = records.rows[i];
                var srd = {};
                srd['ticketInstId'] = $("#ticketInstId_param").val();
                srd['resourceid'] = singleRecordData;
                srd['status'] = 1;/*新增*/
                srd['manual']= 1;//手工添加方式
                srd['category']= 300;//手工添加方式
                srd['ipType']= records.type;//手工添加方式
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
                    "resourceCategory":300
                },
                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                beforeSend:function(){
                },
                success:function(data){
                    top.layer.msg(data.msg, {
                        icon: 1,
                        time: 3000 // （如果不配置，默认是3秒）
                    });
                    /*删除选择的win*/
                    top.layer.close(index);
                    $("#"+gridId).datagrid("reload");

                },
                complete: function(XMLHttpRequest, textStatus){

                },
                error: function(){

                }
            });
            //刷新列表信息
        }
    });
}

function loadIpGrid(gridId,params){
    if(!params){
        params = getRequestIpParams();
    }
    $('#'+gridId).datagrid({

        url:contextPath+'/actJBPMController/ticketIpResourceQueryData/'+$("#ticketInstId_param").val()+'/300',
        title: 'IP租用订单...',
        iconCls:'',
        queryParams: params,
        toolbar: '#requestParamSettins_ticket_ip_gridId',
        border: false,
        singleSelect: true,
        striped : true,
        pagination: true,
        pageSize: 7,
        pageList:[7,10],
        rownumbers: true,
        fitColumns: true,
        autoRowHeight:true,
        columns: [
            [
                {field: 'ipName', title: '起始地址',halign:'left',width:130},
                {field: 'endip', title: '结束地址',halign:'left',width:130},
                {field: 'resourceStatus', title: '状态',halign:'left',width:130},
                {field: 'operate',hidden : $("#isShowGridColumnHandlerFlag").val() == "true"?false:true, title: '操作',width:100,halign:'center',formatter:fmtOperateIPAction }
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

/**
 * 操作
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function  fmtOperateIPAction(value,row,index) {
    var insert= [];
    //订单ID
    var id = row.id;/*工单资源中间表ID*/
    var ipId = row.ipId;/*IP  ID*/
    var ipType = row.ipType;
    /*其实就只需要中间表id和id和设备id*/
    insert.push('<a class="easyui-linkbutton " title="删除" data-options="plain:true,iconCls:\'icon-scqd-resource\'" onclick="singleIpDeleteRow('+id+',\''+ipId+'\',\''+ipType+'\')"><span class="icon-scqd-resource-over-d">删除</span></a> ');
    return insert.join('');
}
function fmtOperateIpTypeAction(value,row,index){

    if(value && value == 'ipsubnet'){
        return "网段";
    }else if(value && value == 'ipaddress'){
        return "IP地址";
    }
}
function singleIpDeleteRow(id,ipId,ipType){
    var ticketInstId=$("#ticketInstId_param").val();

    /*ajax*/
    top.layer.confirm('你确定要移除所选IP吗？', {
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
                'primaryId' : id,
                'ipType' : ipType
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
                top.layer.msg(data.msg, {
                    icon: 1,
                    time: 3000 // （如果不配置，默认是3秒）
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