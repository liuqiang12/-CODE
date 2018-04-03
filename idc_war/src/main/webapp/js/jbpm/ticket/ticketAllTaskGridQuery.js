/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
});
function loadGrid(gridId){
    //创建表信息
    //创建表信息
    $("#ktlc_buttom_id")?$("#ktlc_buttom_id").linkbutton('disable'):null;
    $("#zq_ywbg_buttom_id")?$("#zq_ywbg_buttom_id").linkbutton('disable'):null;
    $("#zq_bgkt_buttom_id")?$("#zq_bgkt_buttom_id").linkbutton('disable'):null;
    $("#zq_tj_buttom_id")?$("#zq_tj_buttom_id").linkbutton('disable'):null;
    $("#zq_ywxx_buttom_id")?$("#zq_ywxx_buttom_id").linkbutton('disable'):null;
    $("#zq_ft_buttom_id")?$("#zq_ft_buttom_id").linkbutton('disable'):null;
    var url = getRequestUrl();
    var params = getRequestParams();
    var columns = createColumns();
    $("#"+gridId).datagrid({
        url : contextPath + url,
        queryParams: params,
        columns : columns,
        onLoadSuccess:myLoadsuccess,
        toolbar:"#requestParamSettins_taskQuery",
        /*控制按钮配置*/
        onClickRow:function(index,row){
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
/**
 * 审批按钮
 * @param gridId
 */
function author_apply_item(gridId){
    /*获取列表选中的行*/
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    /*调用apply方法*/
    //获取的所有的参数信息
    var params = {};
    var record = singleRecordData?singleRecordData[0]:null;
    params['prodInstId'] = record?record['PRODINSTID']:null;
    params['ticketInstId'] = record?record['TICKETINSTID']:null;
    params['serialNumber'] = record?record['SERIALNUMBER']:null;
    author_apply(params,gridId);
}
//初始化按钮事件
function myLoadsuccess(data){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
    $(".ticket_reject_tip").tooltip({
        onShow: function(){
            $(this).tooltip('tip').css({
                width:'300',
                boxShadow: '1px 1px 3px #292929'
            });
        }}
    );
}
function getRequestUrl(){
    return "/actJBPMController/hisTicketTaskData.do";
}

// 获取查询条件
function getRequestParams(){
    var prodCategory = $("#prodCategory").val();
    var serialNumber = $("#serialNumber").val();
    var isCustomerView = $("#isCustomerView").val();
    var ticketCategory = $("#ticketCategory").val();
    var locationCode = $("#locationCode").val();
    var busName = $("#busName").val();

    /*参数组装*/
    var params = {};
    params['serialNumber'] = serialNumber;
    params['prodCategory'] = prodCategory;
    params['ticketCategory'] = ticketCategory;
    params['isCustomerView'] = isCustomerView;
    params['busName'] = busName;
    params['locationCode'] = locationCode;
    return params;
}
//获取列信息
function createColumns(){
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

/*-----------此只能查看工单的信息  start --------------------*/
/*加载相应的界面信息*/
function loadSingleTicketInfo(value,row,index){
    var css = "#036513";
    var params = {};
    params['prodInstId'] = row['PRODINSTID'];
    params['ticketInstId'] = row['TICKETINSTID'];
    params['serialNumber'] = row['SERIALNUMBER'];
    /*是否是驳回的标志*/
    params['rejectComment'] = row['REJECT_COMMENT'];
    params['isRejectTicket'] = row['IS_REJECT_TICKET'];
    return '<a href="javascript:void(0);" onclick="author_apply_show(\''+params['ticketInstId']+'\',\''+params['prodInstId']+'\',\''+params['serialNumber']+'\')">'+value+'</a>';

}
/*只是查看;*/
/*function author_apply_show(ticketInstId,prodInstId,serialNumber){
    var url = "/actJBPMController/author_apply_show/"+ticketInstId+"/"+prodInstId;
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">['+serialNumber+']审批信息</span>'+"",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['1024px', '80%'],
        content: contextPath+url,
        /!*弹出框*!/
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index);
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });
}*/
/*-----------此只能查看工单的信息  end --------------------*/

/*function menubtn(value, index, options) {
    var id = value.id;
    //如果该订单中有正在运行的工单，则不能再次创建
    var isRunTiket = value.isRunTiket;
    var prodName = value.prodName;
    var procticketStatus = value.procticketStatus;//流程状态
    var prodCategory = value.prodCategory;//订单类型

    var opt = options || {};
    var len = opt.option.length;
    var div = $('<div></div>');
    if ($("div[id^='submenu']"))
        $("div[id^='submenu']").remove();
    for (var i = 0; i < len; i++) {
        var op = opt.option[i];
        var span = $('<a href="javascript:void(0)" class="easyui-menubutton" style="color:#166DCC" id="menubtn'
            + index
            + '"  plain="true" iconCls="'
            + op.icon
            + '" menu="#submenu' + index + '"></a>');

        span.append(op.text);
        span.appendTo(div);
        var items = op.items || {};
        var subdiv = $('<div id="submenu' + index + '"></div>');
        for (var j = 0; j < items.length; j++) {
            var item = items[j];
            var hide = '';
            if (item.hide)
                hide = 'style=display:none';
            var sub = $('<div  iconCls="' + item.icon + '" onclick='
                + item.onclick + '(' + id + ',\'' + prodName + '\',\'' + item.catalog + '\',' + prodCategory + ') ' + hide + '>'
                + item.text + '</div>');
            sub.appendTo(subdiv);
        }
        subdiv.appendTo(div);
    }
    return div.html();
}*/
function fmtProdNameLinkAction(value,row,index){
    //* 弹出客户需求 *//
    return '<a href="javascript:void(0);" onclick="linkProdNameQueryWin(\''+row.PRODINSTID+'\',\''+row.PRODNAME+'\')">'+value+'</a>';
}
function fmtCustomerLinkAction(value,row,index){
    //弹出用户信息
    return '<a href="javascript:void(0);" onclick="openWinCustomerIdFun(\''+row.CUSTOMERID+'\',\''+row.CUSTOMERNAME+'\')">'+value+'</a>';
}
/**
 * 弹出订单
 * @param prodInstId
 * @param prodName
 */
function linkProdNameQueryWin(prodInstId,prodName){
    //弹出框显示客户需求
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">订单服务['+prodName+']</span>'+"》详情",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['1014px', '670px'],
        content: contextPath+"/resourceApplyController/linkQueryWin_new.do?viewQuery=1&id="+prodInstId,
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        }
    });
}

//查看客户基本信息
function openWinCustomerIdFun(customerId,customerName){
    //弹出form表单查看客户基本信息
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">客户人员['+customerName+']</span>'+"》详情",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['784px', '600px'],
        content: contextPath+"/customerController/linkQueryWin.do?viewQuery=1&id="+customerId,
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        }
    });
}
/**
 *
 * @param catalog:类型
 * @param prodInstId：服务实例ID
 */
function linkCategoryWin(catalog,prodInstId){

}