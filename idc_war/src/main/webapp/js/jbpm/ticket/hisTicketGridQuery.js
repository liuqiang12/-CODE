// 获取查询条件
function getLinkedHisRequestParams(){
    /*参数组装*/
    var params = {};
    try{
        var serialNumber = $("#linkedHis_serialNumber").textbox("getValue");
        params['serialNumber'] = serialNumber;
    }catch (e){

    }
    return params;
}
function reloadHisTicketGrid(gridId){
    /**/
    loadHisTicketGrid(gridId);
}
function loadHisTicketGrid(gridId){
    var params = getLinkedHisRequestParams();
    $('#'+gridId).datagrid({queryParams:params});
}

function loadSingleTicketInfo_w(value,row,index){
    var css = "#036513";
    var params = {};
    params['prodInstId'] = row['PRODINSTID'];
    params['ticketInstId'] = row['TICKETINSTID'];
    params['serialNumber'] = row['SERIALNUMBER'];
    /*是否是驳回的标志*/
    params['rejectComment'] = row['REJECT_COMMENT'];
    params['ticketCategoryTo'] = row['TICKETCATEGORYTO'];
    params['ticketCategoryFrom'] = row['TICKETCATEGORYFROM'];
    params['ticketStatus'] = row['TICKETSTATUS'];
    /*return '<a href="javascript:void(0);" onclick="author_apply_show(\''+params['ticketInstId']+'\',\''+params['prodInstId']+'\',\''+params['serialNumber']+'\')">'+value+'</a>';*/
    return '<a href="javascript:void(0);" onclick="author_apply_show_w(\''+params['ticketInstId']+'\',\''+params['prodInstId']+'\',\''+params['ticketStatus']+'\',\''+params['ticketCategoryFrom']+'\',\''+params['ticketCategoryTo']+'\')">'+value+'</a>';
}

/*只是查看;*/
function author_apply_show_w(ticketInstId,prodInstId,ticketStatus,ticketCategoryTo,ticketCategoryFrom){
    console.log("author_apply_show_w的usl的值:");
    console.log("ticketInstId:"+ticketInstId+"===prodInstId:"+prodInstId+"===ticketStatus:"+ticketStatus+"==ticketCategoryTo:"+ticketCategoryTo+"===ticketCategoryFrom:"+ticketCategoryFrom);

    var url = "/actJBPMController/jbpm_apply/"+ticketInstId+"/"+prodInstId+"/"+ticketStatus+"/"+ticketCategoryFrom+"/"+ticketCategoryTo;
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">['+']审批信息</span>'+"",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['1024px', '85%'],
        content: contextPath+url,
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });
}

function columnCSS(value,row,index){
    var css = "#036513";
    /*如果可以开通的颜色改变*/
    if(row.IS_PROCESS_END == '1' && row.IS_RUBBISH == '0' ){
        //禁用信息
        if(row.CATEGORY == $("#category").val()){
            if(row.PROCTICKET_STATUS == 21){
                return '<span style="color:'+css+'" title="可以开通的工单">'+value+'</span>';//改变表格中内容字体的大小
            }else{
                return '<span style="color:darkslategray">'+value+'</span>';//改变表格中内容字体的大小
            }
        }else{
            return value;
        }
    }else if(row.IS_RUBBISH == '1'){
        return '<span style="color:dimgrey">'+value+'</span>';//改变表格中内容字体的大小
    }else{
        return value;
    }

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