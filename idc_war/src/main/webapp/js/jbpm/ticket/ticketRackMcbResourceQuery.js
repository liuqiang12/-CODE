/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
        if(rack && rack == "true"){
            var params = getRequestRackmcbParams();
            loadRackmcbGrid("ticket_rackmcb_gridId",params);
        }

});
function getRequestRackmcbParams(){
    var rackmcbName_params = $("#rackmcbName_params").textbox("getValue");
    var isProcessEnd_param = $("#isProcessEnd_param").val();
    var isRubbish_param = $("#isRubbish_param").val();
    var is_author_apply_show = $("#is_author_apply_show").val();

    /*参数组装*/
    var params = {};
    params['resourcename'] = rackmcbName_params;
    params['isProcessEnd'] = isProcessEnd_param;
    params['is_author_apply_show'] = is_author_apply_show;
    params['isRubbish'] = isRubbish_param;
    return params;
}

function loadRackmcbGrid(gridId,params){
    if(!params){
        params = getRequestRackmcbParams();
    }
    $('#'+gridId).datagrid({
        url:contextPath+'/actJBPMController/ticketRackmcbResourceQueryData/'+$("#ticketInstId_param").val()+'/700',
        title: '',
        iconCls:'',
        queryParams: params,
        toolbar: '#requestParamSettins_ticket_rackmcb_gridId',
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
                /*{field: 'mcbNo', title: 'MCB名称',halign:'left',width:80},*/
                {field: 'resourcename', title: 'MCB名称 ',halign:'left',width:130,formatter:targetNameFmt },
                {field: 'belongName', title: '所属PDF ',halign:'left',width:130,formatter:belongNameFmt },
                {field: 'resourceStatus', title: '状态 ',halign:'left',width:80},
                {field: 'pduPowertype', title: '电源类型 ',halign:'left',width:80},
                {field: 'mcbBelongName', title: '客户机架 ',halign:'left',width:130},
                {field: 'ratedelectricenergy', title: '额定电流 ',halign:'left',width:80,formatter: function(value,row,index){return value?value+" A":"0 A"}},/*,/!*KWH*!/
                {field: 'operate', title: '操作',width:100,halign:'center',formatter:fmtOperateRackMCBAction }*/
                {field: 'operate',hidden : $("#isShowGridColumnHandlerFlag").val() == "true"?false:true, title: '操作',width:80,halign:'center',formatter:fmtOperateMCBAction }
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
//这个是删除mcb开关的方法
function  fmtOperateMCBAction(value,row,index) {
    var insert= [];
    insert.push('<a class="easyui-linkbutton " title="删除"   data-options="plain:true,iconCls:\'icon-scqd-resource\'" onclick="singleMCBDeleteRow('+row.resourceid+')"><span class="icon-scqd-resource-over-d">删除</span></a> ');
    return insert.join('');
}

function singleMCBDeleteRow(mcbId) {
    /*ajax*/
    top.layer.confirm('你确定要移除所选MCB吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/actJBPMController/ticketServerDelectMCBServer/"+mcbId,
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
                    time: 3000 // 如果不配置，默认是3秒）
                }, function(){
                    //do something
                });
                top.layer.close(index);
                $("#ticket_rack_gridId").datagrid("reload");
                $("#ticket_rackmcb_gridId").datagrid("reload");
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