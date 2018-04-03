/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    var processDefinitonKey = $("#processDefinitonKey").val();
    if(processDefinitonKey == 'idc_ticket_pre_accept' || processDefinitonKey == 'idc_ticket_self_pre_accept'){

    }　
});
function loadPreGrid(gridId){
    //创建表信息
    $("#ktlc_buttom_id")?$("#ktlc_buttom_id").linkbutton('disable'):null;
    $("#zq_ywbg_buttom_id")?$("#zq_ywbg_buttom_id").linkbutton('disable'):null;
    $("#zq_bgkt_buttom_id")?$("#zq_bgkt_buttom_id").linkbutton('disable'):null;
    $("#zq_tj_buttom_id")?$("#zq_tj_buttom_id").linkbutton('disable'):null;
    $("#zq_ywxx_buttom_id")?$("#zq_ywxx_buttom_id").linkbutton('disable'):null;
    $("#zq_ft_buttom_id")?$("#zq_ft_buttom_id").linkbutton('disable'):null;
    var url = getPreRequestUrl();
    var params = getPreRequestParams();
    var columns = createPreColumns();
    $("#"+gridId).datagrid({
        url : contextPath + url,
        queryParams: params,
        columns : columns,
        onLoadSuccess:myTaskLoadsuccess,
        toolbar:"#requestParamSettins_pre_taskQuery",
        rowStyler: function(index,row){
        },
    //ticketCategory:工单类型100:预勘 200:开通 400:停机 500:复通 600:下线 700:变更开通 800:临时测试,900:业务变更
        onClickRow:function(index,row){
            /************ 工单状态。结束了的工单 ****************/

            if(row.ticketStatus == 2 ){
                //禁用信息
                if(row.ticketCategory == 100){
                    //预勘
                    if(row.ticketStatus == 2){
                        //预勘结束
                        $("#ktlc_buttom_id").linkbutton('enable');
                    }else{
                        $("#ktlc_buttom_id").linkbutton('disable');
                    }
                }else if(row.ticketCategory == 200 || row.ticketCategory == 900 || row.ticketCategory == 700 || row.ticketCategory == 500){
                    //开通流程 则需要
                    if(row.ticketStatus == 2){
                        //预勘结束
                        $("#zq_ywbg_buttom_id").linkbutton('enable');
                        $("#zq_bgkt_buttom_id").linkbutton('enable');
                        $("#zq_tj_buttom_id").linkbutton('enable');
                        $("#zq_ywxx_buttom_id").linkbutton('enable');
                    }else{
                        $("#zq_ywbg_buttom_id").linkbutton('disable');
                        $("#zq_bgkt_buttom_id").linkbutton('disable');
                        $("#zq_tj_buttom_id").linkbutton('disable');
                        $("#zq_ywxx_buttom_id").linkbutton('disable');
                    }
                }else if(row.ticketCategory == 400){
                    if(row.ticketStatus == 2){
                        $("#zq_ywxx_buttom_id").linkbutton('enable');
                        $("#zq_ft_buttom_id").linkbutton('enable');
                    }else{
                        $("#zq_ywxx_buttom_id").linkbutton('disable');
                        $("#zq_ft_buttom_id").linkbutton('disable');
                    }
                }
            }else{
                $("#ktlc_buttom_id")?$("#ktlc_buttom_id").linkbutton('disable'):null;
                $("#zq_ywbg_buttom_id")?$("#zq_ywbg_buttom_id").linkbutton('disable'):null;
                $("#zq_bgkt_buttom_id")?$("#zq_bgkt_buttom_id").linkbutton('disable'):null;
                $("#zq_tj_buttom_id")?$("#zq_tj_buttom_id").linkbutton('disable'):null;
                $("#zq_ywxx_buttom_id")?$("#zq_ywxx_buttom_id").linkbutton('disable'):null;
            }
        }
    })
}　
function getPreRequestUrl(){
    //申请人可以查看
    return "/actJBPMController/hisTicketTaskData.do?apply_force_look=1";
}

// 获取查询条件
function getPreRequestParams(){
    try {
        var serialNumber = $("#serialNumber").textbox("getValue");
    } catch(error) {
    }

    var processDefinitonKey = $("#processDefinitonKey").val();
    var prodCategory = $("#prodCategory").val();
    var serialNumber = $("#pre_serialNumber").val();
    var busName = $("#pre_busName").val();
    var pre_locationCode = $("#pre_locationCode").val();

    /*参数组装*/
    var params = {};
    params['serialNumber'] = serialNumber;
    params['processDefinitonKey'] = processDefinitonKey;
    params['prodCategory'] = prodCategory;
    params['busName'] = busName;
    params['locationCode'] = pre_locationCode;
    return params;
}
//获取列信息customerId
function createPreColumns(){
    //创建列表信息
    var headCols = [];
    /*headCols.push({field:"ck",checkbox:true,halign:'left'});   //这个是复选框*/
    headCols.push({field:"busname",title:"业务名称",halign:'left',width:55,formatter:formatterBusNameAB});
    headCols.push({field:"idcName",title:"意向IDC",halign:'left',width:40});
    headCols.push({field:"serialNumber",title:"工单编号",halign:'left',width:40});
    headCols.push({field:"customerName",title:"客户名称",halign:'left',width:50});
    headCols.push({field:"taskName",title:"状态",halign:'left',width:45});
    headCols.push({field:"applyName",title:"申请人",halign:'left',width:20});
    headCols.push({field:"candidate",title:"候选审批人",halign:'left',width:40});
    headCols.push({field:"createTimeStr",title:"创建时间",halign:'left',width:30});

    return [headCols]
} 　  　
/*-----------此只可以审批和驳回工单  end --------------------*/
　 　
function singleCreateOrUpdate_test(gridId,catalog){
    /*此时需要重新写*/
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">客户需求</span>'+"》新增",
        shadeClose: false,
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['1154px', '700px'],
        content: contextPath+"/resourceApplyController/singleCreateOrUpdate_new.do?prodCategory="+$("#prodCategory").val()+"&catalog="+catalog,
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            chidlwin.form_sbmt("pre_gridId");
            //刷新列表信息

        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#"+gridId).datagrid("reload");
        }
    });
}
//新增相应的模型
function singleCreateOrUpdate_(gridId,catalog,isCustomerView){
    //isCustomerView意思是判断是否是客户经理视图，客户经理视图的发起预堪是发起政企业务
    /*此时需要重新写*/
    var laySum = top.layer.open({
        type: 2,
        id:'singleCreateOrUpdate_new_id',
        title: '<span style="color:blue">客户需求</span>'+"》新增",
        shadeClose: false,
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['1154px', '95%'],
        content: contextPath+"/resourceApplyController/singleCreateOrUpdate_new.do?prodCategory="+$("#prodCategory").val()+"&catalog="+catalog+"&isCustomerView="+isCustomerView,
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            chidlwin.form_sbmt($("#pre_gridId"),$("#gridId"),"singleCreateOrUpdate_new_id");
        }
    });
}
function singleCreateOrUpdateFromROOTMainView(catalog,isCustomerView){
    //isCustomerView意思是判断是否是客户经理视图，客户经理视图的发起预堪是发起政企业务
    /*此时需要重新写*/
    var laySum = top.layer.open({
        type: 2,
        id:'singleCreateOrUpdateFromROOTMainView_new_id',
        title: '<span style="color:blue">客户需求</span>'+"》新增",
        shadeClose: false,
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['1154px', '700px'],
        content: contextPath+"/resourceApplyController/singleCreateOrUpdate_new.do?prodCategory=1&catalog="+catalog+"&isCustomerView="+isCustomerView,
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            chidlwin.form_sbmt(null,null,"singleCreateOrUpdateFromROOTMainView_new_id");
        }
    });
}

//刷新
function resetForm(grid){
    /*$("#customerId").val('');
    $("#customerName").textbox("clear");
    $("#ktlc_buttom_id").linkbutton('disable');*/
    loadPreGrid(grid);
}

//订单状态
function fmtProdStatusAction(value,row,index){
    //订单状态
    var label = value == '10'?"在途":
        value == '20'?"已竣工":
            value == '30'?"已停机":
                value == '40'?"已撤销":
                    null;
    return label;
}
function fmtHisCustomerLinkAction(value,row,index){

    //弹出用户信息
    // function fmtAction(value,row,index){
    return '<a href="javascript:void(0);" onclick="openWinCustomerIdFun(\''+row.customerId+'\',\''+row.customerName+'\')">'+value+'</a>';

}