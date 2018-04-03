/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
});
function loadGrid(gridId){
    //创建表信息
    var url = getRequestUrl();
    var params = getRequestParams();
    var columns = createColumns();
    $("#"+gridId).datagrid({
        url : contextPath + url,
        queryParams: params,
        columns : columns,
        onLoadSuccess:myLoadsuccess,
        toolbar:"#requestParamSettins_taskQuery"
    })
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
    return "/actJBPMController/runTicketTaskData.do";
}

// 获取查询条件
function getRequestParams(){
    var serialNumber = $("#serialNumber").textbox("getValue");
    var processDefinitonKey = $("#processDefinitonKey").val();
    var prodCategory = $("#prodCategory").val();
    var isCustomerView = $("#isCustomerView").val();
    var ticketCategory = $("#ticketCategory").val();
    var busName = $("#busName").val();
    var locationCode = $("#locationCode").val();

    /*参数组装*/
    var params = {};
    params['serialNumber'] = serialNumber;
    params['processDefinitonKey'] = processDefinitonKey;
    params['prodCategory'] = prodCategory;
    params['isCustomerView'] = isCustomerView;
    params['busName'] = busName;
    params['ticketCategory'] = ticketCategory;
    params['locationCode'] = locationCode;
    return params;
}
//获取列信息
function createColumns(){
    var headCols = [];
    /*headCols.push({field:"ck",checkbox:true,halign:'left'});   //这个是复选框*/
    headCols.push({field:"busname",title:"业务名称",halign:'left',width:55,formatter:formatterBusNameAB});
    headCols.push({field:"idcName",title:"意向IDC",halign:'left',width:40});
    headCols.push({field:"serialNumber",title:"工单编号",halign:'left',width:40});
    headCols.push({field:"customerName",title:"客户名称",halign:'left',width:50});
    headCols.push({field:"taskName",title:"状态",halign:'left',width:45});
    headCols.push({field:"applyName",title:"申请人",halign:'left',width:20});
    headCols.push({field:"candidate",title:"审批人",halign:'left',width:40});
    headCols.push({field:"createTimeStr",title:"创建时间",halign:'left',width:30});
    return [headCols]
}

/**       流程操作的方法都在一下几个     start      **/
function delJbpmTicketFun(index, layero){
    //删除方法
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:black">非常危险！此操作将永久删除工单!</span>'+"",
        shadeClose: false,
        shade: 0.8,
        btn: ['确定删除','取消'],
        maxmin : true,
        area: ['600px', '400px'],
        content: contextPath+"/actJBPMController/rubbishTicketDeletePage/"+this.ticketInstId+"/"+this.prodInstId,
        /*弹出框*/
        yes:function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;

            //保存form信息,提示是否关闭窗口
            var sbmtFlag  =  chidlwin.form_sbmt_delete($("#gridId"));
        }
    });

    return false;
}
function rejectJbpmTicketFun(index, layero){
    var childIframeid = layero.find('iframe')[0]['name'];
    var chidlwin = top.document.getElementById(childIframeid).contentWindow;

    //把idcRunProcCment_status_stand改成1，1代表同意审批，0代表驳回
    chidlwin.$("#idcRunProcCment_status_stand").val(0);

    //返回相应的数据
    //保存form信息,提示是否关闭窗口
    var sbmtFlag  =  chidlwin.form_sbmt($("#gridId"),'rejectJbpm');

    return false;
}
function rubbishJbpmTicketFun(index, layero){
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:black">请谨慎操作！作废后不能再次提交！</span>' + "",
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

            //保存form信息,提示是否关闭窗口
            var sbmtFlag = chidlwin.form_sbmt_rubbish($("#gridId"));
        }
    });
    return false;
}

function closeJbpmTicketFun(index, layero){
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
function applySuccess(sbmtData,index){
    if(sbmtData && sbmtData.flag){
        //同时需要关闭窗口
        top.layer.close(index);
        //提示保存成功。
        top.layer.msg('保存成功!', {
            icon: 1,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        //刷新工单查询界面
        loadGrid("gridId");
        return true;
    }
    return false;
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
    /*params['isRejectTicket'] = row['IS_REJECT_TICKET'];*/
    params['ticketCategoryTo'] = row['TICKETCATEGORYTO'];
    params['ticketCategoryFrom'] = row['TICKETCATEGORYFROM'];
    params['ticketStatus'] = row['TICKETSTATUS'];
    console.log("params的值:");
    console.log(params);
    /*return '<a href="javascript:void(0);" onclick="author_apply_show(\''+params['ticketInstId']+'\',\''+params['prodInstId']+'\',\''+params['serialNumber']+'\')">'+value+'</a>';*/
    return '<a href="javascript:void(0);" onclick="author_apply_show_w(\''+params['ticketInstId']+'\',\''+params['prodInstId']+'\',\''+params['ticketStatus']+'\',\''+params['ticketCategoryFrom']+'\',\''+params['ticketCategoryTo']+'\')">'+value+'</a>';
}

/**
 *
 * @param taskid
 * @param procInstId
 * @param prodName
 * @param prodInstId
 * @param ticketInstId
 * @param customerId
 * @param procDefId
 * @param formKey
 * @param processDefinitonKey
 * @param serialNumber
 * @param actName
 * @param parentId
 * @param initId
 * @param prodcategory
 */

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