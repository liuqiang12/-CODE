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
        toolbar:"#requestParamSettins"
    })
}
//初始化按钮事件
function myLoadsuccess(data){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
}
function getRequestUrl(){
    return "/contractController/contractQueryData.do";
}

// 获取查询条件
function getRequestParams(){
    try {
        var contractname = $("#contractname").textbox("getValue");
        var contractno = $("#contractno").textbox("getValue");
    } catch(error) {
    } finally {
    }

    /*参数组装*/
    var params = {};
    params['contractname'] = contractname;
    params['contractno'] = contractno;
    return params;
}
//获取列信息
function createColumns(){
    //创建列表信息
    var headCols = [];
    /*headCols.push({field:"ck",checkbox:true,halign:'left'});*/
    headCols.push({field:"CONTRACTNAME",title:"合同名称",halign:'left',formatter:fmtGetContractList,width:150});
    headCols.push({field:"SERIAL_NUMBER",title:"工单序号",halign:'left',width:150});
    headCols.push({field:"CONTRACTNO",title:"合同编码",halign:'left',width:140});
    headCols.push({field:"CONTRACTPRICE",title:"价格",halign:'left',formatter:fmtPriceAction,width:70});
    headCols.push({field:"CONTRACTSIGNDATE",title:"签订时间",halign:'left',formatter:fmtDateAction,width:130});
    headCols.push({field:"CONTRACTSTART",title:"开始时间",halign:'left',formatter:fmtDateAction,width:130});
    headCols.push({field:"CONTRACTPERIOD",title:"期限",halign:'left',formatter:fmtContractperiodAction,width:70});
    headCols.push({field:"CONTRACTEXPIRREMINDER",title:"到期提醒方案",halign:'left',formatter:fmtContractexpirreminderAction,width:100});
    /*headCols.push({field:"contractpostpaid",title:"据实缴费",halign:'left',formatter:fmtContractpostpaidAction,width:100});
     headCols.push({field:"contractpaycycle",title:"缴费周期",halign:'left',formatter:fmtContractpaycycleAction,width:100});*/
        headCols.push({field:"CUSTOMERNAME",title:"所属客户",halign:'left',width:150});
    return [headCols]
}
function fmtPriceAction(value,row,index){
    var res_value = value || 0;
    return res_value +"元";
}
function fmtContractperiodAction(value,row,index){
    var res_value = value || 1;
    return res_value +"月";
}
function fmtContractpostpaidAction(value,row,index){
    var res_value = value || 0;
    return res_value?"是":"否";
}
function fmtContractpaycycleAction(value,row,index){
    var res_value = value || 1;
    return "每月【"+res_value +"】号";
}
function fmtContractexpirreminderAction(value,row,index){
    var res_value = value || 0;
    return res_value==0?"联系客户经理":res_value==1?"短信":res_value==2?"邮件":null;
}
//查看详情界面
function linkQueryWin(id,name){
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">'+name+'</span>'+"》详情",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['784px', '600px'],
        content: contextPath+"/contractController/linkQueryWin.do?viewQuery=1&id="+id,
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        }
    });
}




/*合同名称*/
function fmtGetContractList(value,row,index){
    var contractId=(row.ID != undefined && row.ID != "" ) ? row.ID : null;
    var ticketInstId=(row.TICKET_INST_ID != undefined && row.TICKET_INST_ID != "" ) ? row.TICKET_INST_ID : null;
    var prodInstId=(row.PROD_INST_ID != undefined && row.PROD_INST_ID != "" ) ? row.PROD_INST_ID : 0;
    var contractName=row.NAME;

    return '<a href="javascript:void(0);" onclick="getContractList(\''+contractId+'\',\''+contractName+'\',\''+ticketInstId+'\',\''+prodInstId+'\')">'+value+'</a>';
}

/*通过合同的id ticket_inst_id查询到关联的*/
function getContractList(contractId,contractName,ticketInstId,prodInstId){
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">'+name+'</span>'+"》详情",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['900px', '300px'],
        content: contextPath+"/contractController/getContractListPage.do?prodInstId="+prodInstId+"&ticketInstId="+ticketInstId+"&contractId="+contractId,
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        }
    });

}


/*
 function fmtLinkAction(value,row,index){
 return '<a href="javascript:void(0);" onclick="linkQueryWin(\''+row.ID+'\',\''+row.CONTRACTNAME+'\')">'+value+'</a>';

 }
 */

//删除
/*function singleDeleteRow(gridId){
    //至少选中一行
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /!*弹出提示框*!/
        top.layer.alert("至少选择一条记录", {
            skin: 'layui-layer-lan'
            ,closeBtn: 0
            ,anim: 4 //动画类型
        });
        return false;
    }
    top.layer.confirm('你确定要删除['+singleRecordData[0]['contractname']+']合同吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /!*ajax*!/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/contractController/singleDelete/"+singleRecordData[0]['id'],
            //提交的数据
            data:{
                contractname: singleRecordData[0]['contractname']
            },
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
            },
            //成功返回之后调用的函数
            success:function(data){
                //提示删除成功
                top.layer.alert(data.msg, {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                    ,anim: 4 //动画类型
                });
                $("#"+gridId).datagrid("reload");
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
}*/
/*function singleUpdateRow(gridId){
    //至少选中一行
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /!*弹出提示框*!/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">合同管理</span>'+"》修改",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['899px', '600px'],
        content: contextPath+"/contractController/singleCreateOrUpdate.do?id="+singleRecordData[0]['id'],
        /!*弹出框*!/
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });
}*/
//新增相应的模型
/*function singleCreateOrUpdate(gridId){
    /!**
     * 添加
     *!/
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">合同管理</span>'+"》新增",
        shadeClose: false,
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['784px', '600px'],
        content: contextPath+"/contractController/singleCreateOrUpdate.do",
        /!*弹出框*!/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            chidlwin.form_sbmt("gridId");
            //刷新列表信息
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });*/
