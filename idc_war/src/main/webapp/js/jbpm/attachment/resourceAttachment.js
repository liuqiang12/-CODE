/*-------------start--------合同附件----------------------------------------------------------*/
function addAttachContract(){
    //添加附件信息
    var fileListLength = $("input[name^='contractFileList']").length;
    var addAttachHtml = '<div id="fileDiv'+fileListLength+'"><input class="easyui-filebox" name="contractFileList'+fileListLength+'" data-options="prompt:\'选择文件\',buttonText:\'选择\'" style="width:508px"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="removeBotton(this,'+fileListLength+')" style="width:38px">删除</a></div>';

    var targetObj = $("#muiltFilesDiv").append(addAttachHtml);
    $.parser.parse($("#fileDiv"+fileListLength));
}
function removeBotton(obj,indx){
    $("#fileDiv"+indx).remove();
}




/*-------------end--------合同附件----------------------------------------------------------*/






/*-------------start--------资源附件----------------------------------------------------------*/

function ticketAttachFmt(value,row,index){
    var id = row.id,ftpAttachName=row.ftpAttachName,ftpInfo=row.ftpInfo;
    return '<a href="javascript:void(0);" title="'+value+'" onclick="downTicketAttachRow(\''+id+'\')">'+value+'</a> ';
}
function downTicketAttachRow(id){
    //下载文件
    $("#downloadFile").attr("src",contextPath+"/assetAttachmentinfoController/downLoadFile/"+id);
}
function ticketAttachLoadsuccess(){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
}

function queryAttachmentData(prodInstId,ticketInstId){
    var attachmentType=$("#attachmentType").val();

    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
}


$(function() {
    var idcTicket_ticketInstId=$("idcTicket_ticketInstId").val();   //资源类型
    var idcTicket_prodInstId=$("#idcTicket_prodInstId").val();   //资源类型
    //初始化时就加载数据:构造列表信息
    loadAttachmentGrid(idcTicket_prodInstId,idcTicket_ticketInstId);
});

function loadAttachmentGrid(prodInstId,ticketInstId){
    var attachmentType=$("#attachmentType").val();   //资源类型

    console.log("idcTicket_ticketInstId:"+idcTicket_ticketInstId+"    idcTicket_prodInstId:"+idcTicket_prodInstId);
    /*参数组装*/
    var params = {};
    params['attachmentType'] = attachmentType;
    params['prodInstId'] = prodInstId;
    params['ticketInstId'] = ticketInstId;
    console.log(params);
    var columns = createColumnsA();
    //创建表信息
    $("#attachmentTable").datagrid({
        url : contextPath +'/assetAttachmentinfoController/loadAttachmetTable.do',
        queryParams: params,
        fitColumns:true,
        columns : columns,
        /*onLoadSuccess:myTaskLoadsuccess,*/
        toolbar:"#attachmentToolbar"
    })
}

function createColumnsA(){
    //创建列表信息
    var headCols = [];
    headCols.push({field:"attachName",title:"附件名称",halign:'center',width:350,formatter:ticketAttachFmt});
    headCols.push({field:"attachmentType",title:"附件类型",halign:'center',width:150});
    headCols.push({field:"author",title:"上传人",halign:'center',width:150});
    headCols.push({field:"createTimeStr",title:"上传时间",halign:'center',width:150});
    headCols.push({field:"operate",title:"操作",halign:'center',width:150,formatter:deleteTicketAttachfmt});
    /*操作按钮*/
    return [headCols]
}

function singleTicketAttachDeleteRow(id,index) {
    /**
     * ajax删除附件信息
     */
    //删除文件:
    top.layer.confirm('你确定删除吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            type:"POST",
            url:contextPath+"/assetAttachmentinfoController/removeAttach/"+id,

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
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                $('#attachmentTable').datagrid("reload");
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
function deleteTicketAttachfmt(value,row,index){
    var id = row.id;
   return '<a class="easyui-linkbutton" title="删除"data-options="plain:true,iconCls:\'icon-cancel\'" onclick="singleTicketAttachDeleteRow(\''+id+'\',\''+index+'\')">删除</a> ';
}
function openBaiduPluginAttachment(prodInstId,ticketInstId,attachmentType){
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">上传附件</span>'+"",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['600px', '40%'],
        content: contextPath+"/assetAttachmentinfoController/assetAttachmentinfoPage/"+prodInstId+"/"+ticketInstId+"/"+attachmentType,
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
            //刷新一下附件信息
            $('#attachmentTable').datagrid('reload');
        },btn1:function(index, layero){
            top.layer.close(index);
            $('#attachmentTable').datagrid('reload');
        }
    });
}
/*-------------end--------资源附件----------------------------------------------------------*/
