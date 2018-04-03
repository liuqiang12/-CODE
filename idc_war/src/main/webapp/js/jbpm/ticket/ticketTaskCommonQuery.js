
//初始化按钮事件
function myTaskLoadsuccess(data){
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
/**       流程操作的方法都在一下几个     start      **/
function delJbpmTicketFun(index, layero){
    /*  删除之前要查看此工单是否存在父工单  */
    var ticketInstId=this.ticketInstId;
    var prodInstId=this.prodInstId;
    $.ajax({
        url:contextPath+"/actJBPMController/deleteTicketQuerySon/"+ticketInstId+"/"+prodInstId,
        type:"post",
        /*data:{username:username},*/
        success:function(data){

            if(data.success == true){
                //删除方法
                var laySum = top.layer.open({
                    type: 2,
                    title: '<span style="color:black">删除属于危险操作！</span>'+"",
                    shadeClose: false,
                    shade: 0.8,
                    btn: ['确定删除','取消'],
                    maxmin : true,
                    area: ['600px', '400px'],
                    content: contextPath+"/actJBPMController/rubbishTicketDeletePage/"+ticketInstId+"/"+prodInstId,
                    /*弹出框*/
                    yes:function(index, layero){
                        var childIframeid = layero.find('iframe')[0]['name'];
                        var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                        var refrashGrid = $("#gridId");
                        if($('#jbpm_tabs').length > 0){
                            var tabsActive = $('#jbpm_tabs').tabs("getSelected").panel('options').title;
                            if(tabsActive != '待办任务'){
                                refrashGrid = $("#pre_gridId");
                            }
                        }
                        //保存form信息,提示是否关闭窗口
                        var sbmtFlag  =  chidlwin.form_sbmt_delete(refrashGrid);
                    }
                });
            }else{
                top.layer.msg(data.msg, {
                    icon: 2,
                    time: 3000 //1.5秒关闭（如果不配置，默认是3秒）
                });
                return false;
            }
        },
        error:function(e){
            alert("错误！！");
            return false;
        }
    });


    return false;
}
function rejectJbpmTicketFun(index, layero){
    var childIframeid = layero.find('iframe')[0]['name'];
    var chidlwin = top.document.getElementById(childIframeid).contentWindow;
    var refrashGrid = $("#gridId");
    if($('#jbpm_tabs').length > 0){
        var tabsActive = $('#jbpm_tabs').tabs("getSelected").panel('options').title;
        if(tabsActive != '待办任务'){
            refrashGrid = $("#pre_gridId");
        }
    }

    //把idcRunProcCment_status_stand改成1，1代表同意审批，0代表驳回
    chidlwin.$("#idcRunProcCment_status_stand").val(0);

    //返回相应的数据
    //保存form信息,提示是否关闭窗口
    var sbmtFlag  =  chidlwin.form_sbmt(refrashGrid,'rejectJbpm');

    return false;
}
function yesJbpmFun(index, layero){
    var refrashGrid = $("#gridId");
    if($('#jbpm_tabs').length > 0){
        var tabsActive = $('#jbpm_tabs').tabs("getSelected").panel('options').title;
        if(tabsActive != '待办任务'){
            refrashGrid = $("#pre_gridId");
        }
    }

    //提交申请或者评价.调用子界面的方法。
    var childWin = getJbpmChlidWin(layero);
    childWin.form_sbmt(refrashGrid,'jbpm_next');
    //重新加载列表
    /*loadGrid("gridId");
     loadPreGrid("pre_gridId");*/
    return false;
}
function rubbishJbpmTicketFun(index, layero){
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:black">作废属于危险操作！</span>' + "",
        shadeClose: false,
        shade: 0.8,
        btn: ['确定作废', '取消'],
        maxmin: true,
        area: ['600px', '400px'],
        content: contextPath + "/actJBPMController/rubbishTicketPage/" + this.ticketInstId + "/" + this.prodInstId,
        /*弹出框*/
        yes: function (index, layero) {
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            var refrashGrid = $("#gridId");
            if($('#jbpm_tabs').length > 0){
                var tabsActive = $('#jbpm_tabs').tabs("getSelected").panel('options').title;
                if(tabsActive != '待办任务'){
                    refrashGrid = $("#pre_gridId");
                }
            }

            //保存form信息,提示是否关闭窗口
            var sbmtFlag = chidlwin.form_sbmt_rubbish(refrashGrid);
        }
    });
    return false;
}

function closeJbpmTicketFun(index, layero){
    try {
        top.layer.close(index);  // 关闭layer
    } catch (e) {
    }
    return true;
}
/**       流程操作的方法都在一下几个  end         **/
//获取子界面window

function isEmptyObject(obj) {
    for (var key in obj){
        return false;//返回false，不为空对象
    }
    return true;//返回true，为空对象
}
/**
 * 流程申请方法整改
 * @param ticketInstId
 * @param prodInstId
 * @param serialNumber
 * @param procticketStatus
 */
function jbpm_apply(ticketInstId,prodInstId,serialNumber,taskName,formKey,ticketStatus,ticketCategory,canPermission,applyId,loginUserId){
    if(ticketStatus && ticketStatus != undefined && ticketStatus!='null'){
        var btns = applyBtns(taskName,formKey,ticketStatus,canPermission,applyId,loginUserId);
        //拼接对应的方法集合
        var btnsFun = applyBtnFun(taskName,formKey,ticketStatus,canPermission,applyId,loginUserId);
        console.log(btnsFun);
        var url = "/actJBPMController/jbpm_apply/"+ticketInstId+"/"+prodInstId+"/"+ticketStatus+"/"+ticketCategory+"/"+ticketCategory;
        var layerOpenOptions = {
            type: 2,
            id:'jbpmApplyWinId',
            title: '<span style="color:blue">['+serialNumber+']流程工单</span>'+"",
            shadeClose: false,
            ticketInstId : ticketInstId,
            prodInstId : prodInstId,
            shade: 0.8,
            btn: btns,//提交申请[评价]  作废  删除 关闭
            maxmin : true,
            area: ['1024px', '95%'],
            content: contextPath+url,
            cancel: function(index, layero){
                top.layer.close(index);
            }
        };
        layerOpenOptions = $.extend({},layerOpenOptions,btnsFun);
        var laySum = top.layer.open(layerOpenOptions);
    }
}
function formatterBusNameAB(value,row,index){
    var loginUserId=$("#loginUserId").val();    //当前登陆用户的id
    var params = {};
    params['prodInstId'] = row['prodInstId'];
    params['ticketInstId'] = row['ticketInstId'];
    params['serialNumber'] = row['serialNumber'];
    params['taskName'] = row['taskName'];
    params['formKey'] = row['formKey'];
    params['formUrl'] = row['formUrl'];
    params['ticketStatus'] = row['ticketStatus'];
    //查询都是相同的工单类别
    params['ticketCategory'] = row['ticketCategory'];
    params['canPermission'] = row['canPermission'];
    params['applyId'] = row['applyId'];   //APPLY_ID申请人id
    params['loginUserId'] = loginUserId;   //APPLY_ID申请人id

    if(row['busname'] == null || row['busname'] == undefined || row['busname'] == ""){
        return "未命名";
    }

    return '<a href="javascript:void(0);" onclick="jbpm_apply(\''+params['ticketInstId']+'\'' +
        '                                                       ,\''+params['prodInstId']+'\'' +
        '                                                       ,\''+params['serialNumber']+'\'' +
        '                                                       ,\''+params['taskName']+'\'' +
        '                                                       ,\''+params['formKey']+'\'' +
        '                                                       ,\''+params['ticketStatus']+'\'' +
        '                                                       ,\''+params['ticketCategory']+'\'' +
        '                                                       ,\''+params['canPermission']+'\'' +
        '                                                       ,\''+params['applyId']+'\'' +
        '                                                       ,\''+params['loginUserId']+'\'' +
        '                                                   )">'+value+'</a>';
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
        area: ['1014px', '678px'],
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
//创建开通、下线、停机、复通、业务变更、开通等等所有工单的页面，都是走这个方法打开页面的
function singleStartRow(gridId,ticketCategory){
    /* 创建工单 */
    /*获取的参数有：
     * 1：PRODINSTID             订单实例ID
     * 2：CATEGORY               流程类别
     * 3：PRODCATEGORY           订单类型
     * 4:PRODNAME                订单名称
     * */
    /*获取被选中的行数据*/
    var singleRecordData = $("#"+gridId).datagrid("getChecked");

    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        layer.msg('请选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    var prodInstId = singleRecordData[0]?singleRecordData[0]['prodInstId']:null;
    var prodCategory = singleRecordData[0]?singleRecordData[0]['prodCategory']:null;
    var serialNumber = singleRecordData[0]?singleRecordData[0]['serialNumber']:null;
    var ticketInstId = singleRecordData[0]?singleRecordData[0]['ticketInstId']:null;
    var ticketCategorySelected = singleRecordData[0]?singleRecordData[0]['ticketCategory']:null;//被选中的工单类型
    var ticketCategoryName = ticketCategory=='200'?'开通':
        ticketCategory=='400'?'停机':
            ticketCategory=='900'?'业务变更':
                ticketCategory=='600'?'业务下线':
                    ticketCategory=='500'?'复通':
                        ticketCategory=='700'?'变更开通':'';

    //判断是否存在子流程，如果存在则不能再次开通
    $.ajax({
        url: contextPath + "/actJBPMController/decideExistsOtherTicket.do",
        async: false,
        type: "post",
        dataType: "json",
        data: {
            ticketInstId:ticketInstId
        },
        success: function (data) {
            if(data.iExistsOtherTicket){
                top.layer.msg('该工单已经发起了['+data.existsOhterTicket+']流程,不能重复发起流程!', {
                    icon: 2,
                    time: 3000 //1.5秒关闭（如果不配置，默认是3秒）
                });
                return false;
            }else{
                //获取工单信息
                if(!ticketInstId || !prodInstId || !(prodCategory == 0 || prodCategory == 1)){
                    //该工单数据有问题，不能提交工单
                    top.layer.msg('该工单ID或产品ID或工单类型为空,不能提交工单', {
                        icon: 2,
                        time: 3000 //1.5秒关闭（如果不配置，默认是3秒）
                    });
                    return false;
                }
                //此时需要拼接url
                var url = "/actJBPMController/jbpm_apply/"+ticketInstId+"/"+prodInstId+"/2/"+ticketCategorySelected+"/"+ticketCategory;
                var laySum = top.layer.open({
                    type: 2,
                    title: '<span style="color:blue">由工单号['+serialNumber+']创建工单</span>'+"",
                    shadeClose: false,
                    id:'createJbpmTicketId',//开通工单的ID
                    shade: 0.8,
                    btn: ['确定','关闭'],
                    maxmin : true,
                    area: ['1024px', '95%'],
                    content: contextPath+url,
                    /*弹出框*/
                    cancel: function(index, layero){
                        top.layer.close(index);
                    },yes: function(index, layero){
                        var childIframeid = layero.find('iframe')[0]['name'];
                        var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                        /**
                         * 修改成功能方法
                         */
                        chidlwin.formSbmitForCreateTicket($("#"+gridId),"createJbpmTicketId");
                        //刷新列表信息

                    },no: function(index, layero){
                        top.layer.close(index)
                    },end:function(){
                        $("#"+gridId).datagrid("reload");
                    }
                });
            }
        },
        error: function (e) {

        }
    });
}