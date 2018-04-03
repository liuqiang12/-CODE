<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/custom/uimaker/css/basic_info.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/checkbox/skin/style/css/checkbox.css" />
<script type="application/javascript" src="<%=request.getContextPath() %>/framework/checkbox/js/checkbox.js" ></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/start/start.css" />
<style type="text/css">
    .fieldsetCls{
        border-color: #eee !important;
    }
    .fieldsetCls > *{
        /*增加border变色*/
        color: #1e1e1e !important;
    }
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0;
    }
    .forcePanel_HeightCls{
        height: 182px;
        margin-bottom: 8px;
        border-bottom: 1px solid #ddd;
    }
    .tip_float_right_info{
        position: absolute;
        right: 10px;
        top:1px;
        z-index: 999;
    }
</style>
<script type="text/javascript">//界面加载的时候，就引用该js

function formSbmitForCreateTicket(grid,parentLayerId){
    /**
     * 开通业务是否可以编辑
     */
    console.log("已经进入开通的方法！！！");
    if("${ticketCategoryTo}" == '200' ){
        console.log("需要修改或者保存开通业务信息");
        updateOrCreateContract(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '900'){
        console.log("需要修改或者保存【业务变更】信息");
        updateOrCreateProduct(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '400'){
        console.log("需要修改或者保存【停机流程】信息");
        updateOrCreatePause(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '700'){
        console.log("需要修改或者保存【变更开通】信息");
        create_ChangeTicket_Apply(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '600'){
        console.log("需要修改或者保存【业务下线】信息");
        create_haltTicket_Apply(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '500'){
        console.log("需要修改或者保存【复通】信息");
        create_recoverTicket_Apply(grid,parentLayerId,{});
    }else{
        console.log("下面是自有业务的开通？？？？？");
        create_Ticket_Apply(grid,parentLayerId);
    }
}

/**
 * 业务变更申请、业务下线申请、变更开通申请、复通申请等。不包括开通流程申请
 */
function create_Ticket_Apply(grid,parentLayerId){
    console.log("父窗口的ID"+parentLayerId);

    $("#selfSingleForm").form('submit', {
        url:contextPath+"/actJBPMController/self_OpenSaveOrUpdate.do",
       // queryParams:params,
        onSubmit: function(){
            top.onclickProcessBar();  //打开进度条
        },
        success:function(data){
            top.closeProgressbar();    //关闭进度条
            var obj = jQuery.parseJSON(data);
            top.layer.msg(obj.msg, {
                icon: 1,
                time: 3000 //3秒关闭（如果不配置，默认是3秒）
            });
            var parentIndex = parent.layer.getFrameIndex(parentLayerId);//获取了父窗口的索引
            parent.layer.close(parentIndex);  // 关闭layer
            grid.datagrid("reload");　

        }
    });
}
/***
 * 业务变更
 */
function updateOrCreateProduct(grid,parentLayerId,params){
    /*修改的时候，需要将审批信息提交*/
    var  singleFormData = getFormJson($("#productReWriteForm"));
    /* var ticketCategoryFrom=$("#ticketCategoryFrom").val();
    var ticketCategoryTo=$("#ticketCategoryTo").val();
    var ticketInstId=$("#ticketInstId").val();
    var prodCategory=$("#prodCategory").val();
    var prodInstId=$("#prodInstId").val();*/

    $("#productReWriteForm").form('submit', {
        url:contextPath+"/actJBPMController/reWriteProductFormSaveOrUpdate.do",
        queryParams:singleFormData,
        onSubmit: function(){
           /* if(!$("#productReWriteForm").form("validate")){
                //验证未通过
                top.layer.msg('验证未通过', {
                    icon: 2,
                    time: 3000 //（默认是3秒）
                });
                //直接定位到:[审批界面]
                $("#jbpmApply_tabs").tabs("select", "客户需求");
                return false;
            }*/
            top.onclickProcessBar();  //打开进度条
        },
        success:function(data){
            top.closeProgressbar();    //关闭进度条
            var obj = jQuery.parseJSON(data);
            top.layer.msg(obj.msg, {
                icon: 1,
                time: 3000 //3秒关闭（如果不配置，默认是3秒）
            });
            var parentIndex = parent.layer.getFrameIndex(parentLayerId);//获取了父窗口的索引
            parent.layer.close(parentIndex);  // 关闭layer
            grid.datagrid("reload");
        }
    });
}
function create_recoverTicket_Apply(grid,parentLayerId,params){
    var  singleFormData = getFormJson($("#singleForm"));
    $("#singleRecoverForm").form('submit', {
        url:contextPath+"/actJBPMController/recoverFormSaveOrUpdateForCreateTicket.do",
        queryParams:singleFormData,
        onSubmit: function(){
            if(!$("#singleRecoverForm").form("validate")){
                //验证未通过
                top.layer.msg('验证未通过', {
                    icon: 2,
                    time: 3000 //（默认是3秒）
                });
                //直接定位到:[审批界面]
                $("#jbpmApply_tabs").tabs("select", "复通");
                return false;
            }
            top.onclickProcessBar();  //打开进度条
        },
        success:function(data){
            top.closeProgressbar();    //关闭进度条
            var obj = jQuery.parseJSON(data);
            top.layer.msg(obj.msg, {
                icon: 1,
                time: 3000 //3秒关闭（如果不配置，默认是3秒）
            });
            var parentIndex = parent.layer.getFrameIndex(parentLayerId);//获取了父窗口的索引
            parent.layer.close(parentIndex);  // 关闭layer
            grid.datagrid("reload");
        }
    });
}
function create_haltTicket_Apply(grid,parentLayerId,params){
    var  singleFormData = getFormJson($("#singleForm"));
    $("#singleHaltForm").form('submit', {
        url:contextPath+"/actJBPMController/haltFormSaveOrUpdate.do",
        queryParams:singleFormData,
        onSubmit: function(){
            if(!$("#singleHaltForm").form("validate")){
                //验证未通过
                top.layer.msg('验证未通过', {
                    icon: 2,
                    time: 3000 //（默认是3秒）
                });
                //直接定位到:[审批界面]
                $("#jbpmApply_tabs").tabs("select", "下线原因");
                return false;
            }
            top.onclickProcessBar();  //打开进度条
        },
        success:function(data){
            top.closeProgressbar();    //关闭进度条
            var obj = jQuery.parseJSON(data);
            top.layer.msg(obj.msg, {
                icon: 1,
                time: 3000 //3秒关闭（如果不配置，默认是3秒）
            });
            var parentIndex = parent.layer.getFrameIndex(parentLayerId);//获取了父窗口的索引
            parent.layer.close(parentIndex);  // 关闭layer
            grid.datagrid("reload");
        }
    });
}
function create_ChangeTicket_Apply(grid,parentLayerId,params){
    var  singleFormData = getFormJson($("#singleForm"));
    $("#contractFrom").form('submit', {
        url:contextPath+"/actJBPMController/contractFormSaveOrUpdate.do",
        queryParams:singleFormData,
        onSubmit: function(){
            if(!$("#contractFrom").form("validate")){
                //验证未通过
                top.layer.msg('验证未通过', {
                    icon: 2,
                    time: 3000 //（默认是3秒）
                });
                //直接定位到:[审批界面]
                $("#jbpmApply_tabs").tabs("select", "开通信息");
                return false;
            }
            top.onclickProcessBar();  //打开进度条
        },
        success:function(data){
            top.closeProgressbar();    //关闭进度条
            var obj = jQuery.parseJSON(data);
            top.layer.msg(obj.msg, {
                icon: 1,
                time: 3000 //3秒关闭（如果不配置，默认是3秒）
            });
            var parentIndex = parent.layer.getFrameIndex(parentLayerId);//获取了父窗口的索引
            parent.layer.close(parentIndex);  // 关闭layer
            grid.datagrid("reload");
        }
    });
}
/**
 * 合同基本信息的保存或者修改
 */
function updateOrCreateContract(grid,parentLayerId,params){
    /*修改的时候，需要将审批信息提交*/
    var prodCategory = "${idcTicket.prodCategory}";
    if(prodCategory == 0){
        var selfSingleFormData = getFormJson($("#selfSingleForm"));
        params =  $.extend({},params,selfSingleFormData);
    }

    var isRepeat=false;   //判断合同编码是否重复
    //验证合同编码重复
    $.ajax({
        type: "post",
        url:contextPath+"/actJBPMController/verifyContractRepeat.do",
        data:{"contractno":$("#contractno").val(),
              "idcRunTicket_ticketInstId":$("#idcRunTicket_ticketInstId").val(),
              "idcRunTicket_prodInstId":$("#idcRunTicket_prodInstId").val() },
        async:false,
        dataType: "JSON",
        success: function(result){
            if(result.success == false){
                top.layer.msg(result.msg, {
                    icon: 2,
                    time: 3000 //3秒关闭
                });
                isRepeat=true;
            }else{

            }
        }
    });

    if(isRepeat){
        alert('合同编码重复！！');
        console.log('合同编码重复！！')
    }
    if(!isRepeat){
        console.log('合同编码没有重复！！');
        //提交合同表单
        $("#contractFrom").form('submit', {
            url:contextPath+"/actJBPMController/contractFormSaveOrUpdate.do",
            queryParams:params,
            onSubmit: function(){
                if(!$("#contractFrom").form("validate")){
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    //直接定位到:[审批界面]
                    $("#jbpmApply_tabs").tabs("select", "开通信息");
                   return false;
                }
                if (!$("#singleForm").form("validate")) {
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                    });
                    var formKey = "${idcTicket.formKey}";
                    console.log(formKey );
                    var lastStepFormReg = /comment_form$/g;
                    var isLastStep = lastStepFormReg.test(formKey);
                    $("#jbpmApply_tabs").tabs("select", isLastStep?"评价":"审批");
                    return false;
                }
                top.onclickProcessBar();  //打开进度条
            },
            success:function(data){
                top.closeProgressbar();    //关闭进度条
                var obj = jQuery.parseJSON(data);
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                var parentIndex = parent.layer.getFrameIndex(parentLayerId);//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
                grid.datagrid("reload");
            }
        });
    }
}

/**
 * 停机申请信息的保存或者修改
 */
function updateOrCreatePause(grid,parentLayerId,params){
    /*修改的时候，需要将审批信息提交*/
    var  singleFormData = getFormJson($("#singleForm"));
    console.log(params);
    $("#singlePauseForm").form('submit', {
        url:contextPath+"/actJBPMController/pauseFormSaveOrUpdate.do",
        queryParams:singleFormData,
        onSubmit: function(){
            if(!$("#singlePauseForm").form("validate")){
                //验证未通过
                top.layer.msg('验证未通过', {
                    icon: 2,
                    time: 3000 //（默认是3秒）
                });
                //直接定位到:[审批界面]
                $("#jbpmApply_tabs").tabs("select", "开通信息");
               return false;
            }
            top.onclickProcessBar();  //打开进度条
        },
        success:function(data){
            top.closeProgressbar();    //关闭进度条
            var obj = jQuery.parseJSON(data);
            top.layer.msg(obj.msg, {
                icon: 1,
                time: 3000 //3秒关闭（如果不配置，默认是3秒）
            });
            var parentIndex = parent.layer.getFrameIndex(parentLayerId);//获取了父窗口的索引
            parent.layer.close(parentIndex);  // 关闭layer
            grid.datagrid("reload");
        }
    });
}

function form_sbmt(grid,jbpmFunFlag){

    $("#idcRunProcCment_status_stand").val(1);
    if(jbpmFunFlag && jbpmFunFlag == 'rejectJbpm'){
        $("#idcRunProcCment_status_stand").val(0);//驳回情况
    }else{
        /* 这里是存在资源分配的情况 */
        if("${idcTicket.resourceAllocationStatus}" && "${idcTicket.resourceAllocationStatus}" == "true"){

            var isHaveTicketResource = getTicketResourceCount('${idcTicket.id}');
            if(!isHaveTicketResource){
                layer.msg('至少选择一个资源 !', {
                    icon: 2,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                });
                $("#jbpmApply_tabs").tabs("select","资源分配");
                return false;
            }

            //如果分配了端口，端口必须要手动分配带宽
            var portResult = portHaveAssignation('${idcTicket.id}');
            if(!portResult){
                layer.msg('请给端口分配带宽 !', {
                    icon: 2,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                });
                $("#jbpmApply_tabs").tabs("select","资源分配");
                return false;
            }
        }
    }

    /*如果存在修改情况。则选执行驳回操作*/
    //首先判断是否是驳回情况，如果是。则需要验证
    var ticketStatus = "${idcTicket.ticketStatus}";
    var processdefinitonkey = "${idcTicket.processdefinitonkey}";

    if(parseInt(ticketStatus) == -1 && (processdefinitonkey == 'idc_ticket_open' || processdefinitonkey == 'idc_ticket_open_change')){
        /******** 如果是开通驳回，情况不一样。因为有附件 ********/
        console.log("开通被驳回后，流程需要的参数获取");
        var  singleFormData = getFormJson($("#singleForm"));
        updateOrCreateContract(grid,"jbpmApplyWinId",singleFormData);
    }else if("${ticketCategoryTo}" == '200' && "${ticketCategoryFrom}" != "${ticketCategoryTo}" && "${isHasOpenEdit}" == 'true'){
        console.log("需要修改或者保存开通业务信息");
        updateOrCreateContract(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '900' && "${ticketCategoryFrom}" != "${ticketCategoryTo}"){
        console.log("需要修改或者保存【业务变更】信息");
        updateOrCreateProduct(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '400'  && "${ticketCategoryFrom}" != "${ticketCategoryTo}"){
        console.log("需要修改或者保存【停机流程】信息");
        updateOrCreatePause(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '700'  && "${ticketCategoryFrom}" != "${ticketCategoryTo}"){
        console.log("需要修改或者保存【变更开通】信息");
        create_ChangeTicket_Apply(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '600' && "${ticketCategoryFrom}" != "${ticketCategoryTo}"){
        console.log("需要修改或者保存【业务下线】信息");
        create_haltTicket_Apply(grid,parentLayerId,{});
    }else if("${ticketCategoryTo}" == '500' && "${ticketCategoryFrom}" != "${ticketCategoryTo}"){
        console.log("需要修改或者保存【复通】信息");
        create_recoverTicket_Apply(grid,parentLayerId,{});
    } else{
        var formKey = "${idcTicket.formKey}";
        console.log("===========[这个方法似乎就是走流程的方法哦！！！]==============");
        var productReWriteForm = getProductReWriteForm(ticketStatus,formKey);

        $("#singleForm").form('submit', {
            url: contextPath + "/actJBPMController/handlerRunTikcetProcess.do",
            queryParams:productReWriteForm,
            onSubmit: function (param) {
                ///*状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束*/
                if(parseInt(ticketStatus) == -1 && formKey == 'pre_accept_apply_form'){
                    //预勘预占流程 且是驳回情况需要验证。。。。
                    if (!$("#productReWriteForm").form("validate")) {
                        //验证未通过
                        top.layer.msg('验证未通过', {
                            icon: 2,
                            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                        });
                        $("#jbpmApply_tabs").tabs("select", "客户需求");
                        return false;
                    }
                }
                /* 开通其他工单的时候，应该不需要再次验证 */
                if (!$("#singleForm").form("validate")) {
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                    });
                    var lastStepFormReg = /comment_form$/g;
                    var isLastStep = lastStepFormReg.test(formKey);
                    $("#jbpmApply_tabs").tabs("select", isLastStep?"评价":"审批");
                    return false;
                }
                if (!(rack && rack == 'true' ||
                    port && port == 'true' ||
                    ip && ip == 'true' ||
                    add && add == 'true' ||
                    server && server == 'true')) {
                    top.layer.msg('请选择服务', {
                        icon: 2,
                        time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                    });
                    $("#jbpmApply_tabs").tabs("select","资源分配");
                    return false;
                }
                /*请给满意度打分 */
                /*前提是最后一步*/
                if("${isLastStepTicket}" && "${isLastStepTicket}" == 'true'){
                    if($("#StarNum").val() == '' || $("#StarNum").val() == null){
                        layer.msg('请给满意度打分', {
                            icon: 2,
                            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                        });
                        return false;
                    }
                }
                top.onclickProcessBar();  //打开进度条
            },
            success: function (data) {
                top.closeProgressbar();    //关闭进度条
                //然后修改下一个form
                var obj = jQuery.parseJSON(data);
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                var parentIndex = parent.layer.getFrameIndex("jbpmApplyWinId");//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
                grid.datagrid("reload");
            },error : function(msg) {

            }
        });
    }
}

function loadScript(url, callback) {
    var script = document.createElement("script");
    script.type = "text/javascript";
    if (script.readyState) { //IE
        script.onreadystatechange = function () {
            if (script.readyState == "loaded" || script.readyState == "complete") {
                script.onreadystatechange = null;
                callback();
            }
        };
    } else { //Others
        script.onload = function () {
            callback();
        };
    }
    script.src = url;
    document.getElementsByTagName("head")[0].appendChild(script);
}
if(//预受理工单被驳回和业务变更工单申请界面
    ("${idcTicket.ticketStatus}" == '-1' && "${idcTicket.formKey}" == 'pre_accept_apply_form')//预受理工单驳回情况
    ||
    ("${idcTicket.formKey}" == 'business_change_accept_apply_form') //业务变更申请界面
    ||
    ("${idcTicket.ticketCategory}" == '900' && "${idcTicket.ticketCategoryFrom}" == '200' )
    ||
    ("${idcTicket.ticketCategory}" == '900' && "${idcTicket.ticketCategoryFrom}" == '700' )
){
    loadScript(contextPath+"/js/jbpm/ticket/ticketResourceCategory.js",function(){});
}
/*附件----start*/
function downLoadAttach(id){
    $("#downloadFile").attr("src",contextPath+"/assetAttachmentinfoController/downLoadFile/"+id);
}

function removeAttach(obj,id){
    //删除文件:
    top.layer.confirm('你确定删除吗？', {btn:['确定','取消']}, function(index, layero){
        $.ajax({
            type:"POST",
            url:contextPath+"/assetAttachmentinfoController/removeAttach/"+id,
            datatype: "json",
            success:function(data){
                //提示删除成功
                //提示框
                layer.msg('成功', {
                    icon: 1,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                });
                $(obj).parents("div.attachHasedCls").remove();
                top.layer.close(index);
            }
        });
    });
}

try {
    var rack = "${productCategory.rack}" || null;
    var port = "${productCategory.port}"||null;
    var ip = "${productCategory.ip}"||null;
    var server = "${productCategory.server}"||null;
    var add = "${productCategory.add}"||null;
    //updateCheckStauts();
}catch(err) {
    console.log(err)
}finally {}
function updateCheckStauts() {
    try {
        if (rack && rack == "true") {
            //被选中
            $("#rack_check").addClass("on_check_disabled");
        } else {
            $("#rack_check").addClass("disabled_not_check");
        }

        if (port && port == "true") {
            $("#port_check").addClass("on_check_disabled");
        } else {
            $("#port_check").addClass("disabled_not_check");
        }
        if (ip && ip == "true") {
            $("#ip_check").addClass("on_check_disabled");
        } else {
            $("#ip_check").addClass("disabled_not_check");
        }
        if (server && server == "true") {
            $("#server_check").addClass("on_check_disabled");
        } else {
            $("#server_check").addClass("disabled_not_check");
        }
        if (add && add == "true") {
            $("#add_check").addClass("on_check_disabled");
        } else {
            $("#add_check").addClass("disabled_not_check");
        }
    }catch(err) {
        console.log(err)
    }finally {}
}
function resWidthTip(count){
    if(count == 5){
        $("#FLOAT_TIP_MSG_ID").css({
            width:629
        });
    }else if(count == 4){
        $("#FLOAT_TIP_MSG_ID").css({
            width:694
        });
    }else if(count == 6){
        $("#FLOAT_TIP_MSG_ID").css({
            width:525
        });
    }else if(count == 7){
        $("#FLOAT_TIP_MSG_ID").css({
            width:492
        });
    }else if(count == 8){
        $("#FLOAT_TIP_MSG_ID").css({
            width:415
        });
    }
}

$(function(){
    var tabs = $('#jbpmApply_tabs').tabs("tabs");
    //改变顶部提示框的宽度
    resWidthTip(tabs.length);
    //修改最后一个title的显示
    //更改：服务项选择-服务项选则
    ticketResourceCategory = getTicketResourceCategory();
    /*状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束
    * */

    console.log("forKey:"+"${idcTicket.formKey}");
    if(//预受理工单被驳回和业务变更工单申请界面
        ("${idcTicket.ticketStatus}" == '-1' && "${idcTicket.formKey}" == 'pre_accept_apply_form')//预受理工单驳回情况
        ||
        ("${idcTicket.formKey}" == 'business_change_accept_apply_form') //业务变更申请界面
        ||
        ("${idcTicket.ticketCategory}" == '900' && "${idcTicket.ticketCategoryFrom}" == '200')
        ||
        ("${idcTicket.ticketCategory}" == '900' && "${idcTicket.ticketCategoryFrom}" == '700' )
    ){
        for(var ticketResKy in ticketResourceCategory){
            console.log(ticketResKy + "="+ticketResourceCategory[ticketResKy]);
            if(ticketResourceCategory[ticketResKy] == "true"){
                console.log("#"+ticketResKy+"_check");
                $("#"+ticketResKy+"_check").addClass("on_check");
            }
        }
    }
});

/**
 * 类别
 * @param category
 */
function serviceApplyImgStatusOnclick(category){
    alert("设置300被选中");
    $("#serviceApplyImgStatus300")
}
//得到：查询资源的条件。只有ticketInstId是必需，其他参数可有可无
function getRackORRacunitOrMCBSetting(){
    var obj = {};
    obj['ticketInstId'] = '${idcTicket.id}';
    obj['prodInstId'] = '${idcTicket.prodInstId}';
    obj['category'] = '${idcTicket.ticketCategory}';
    obj['ticketStatus'] = '${idcTicket.ticketStatus}';
    return obj;
}
/* 具有的有关资源类型组合 */
function getTicketResourceCategory(){
    var obj = {};
    obj['rack'] = "${productCategory.rack}";
    obj['port'] = "${productCategory.port}";
    obj['ip'] = "${productCategory.ip}";
    obj['server'] = "${productCategory.server}";
    obj['add'] = "${productCategory.add}";
    return obj;
}

function getFormJson(form) {
    var o = {};
    var a = $(form).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    console.log("getFormJson得到表单的数据");
    console.log(o);
    return o;
}

function getProductReWriteForm(ticketStatus,formKey){
    console.log("进入getProductReWriteForm方法");
    console.log("ticketStatus：  "+ticketStatus);
    console.log("formKey：  "+formKey);


    //工单重新提交方法
    if(parseInt(ticketStatus) == -1 && formKey == 'pre_accept_apply_form'){
        //驳回的单子，且是预勘预占流程
        return getFormJson($("#productReWriteForm"));
    }else if(parseInt(ticketStatus) == -1 && formKey == 'business_change_accept_apply_form'){
        return getFormJson($("#productReWriteForm"));
    }else if(parseInt(ticketStatus) == -1 && formKey == 'pause_accept_apply_form'){
        return getFormJson($("#pauseSingleForm"));
    }else{
        return {};
    }
}
function ticketCategoryAdd_(){
    var add_fieldset_contentHTML =
        '<fieldset class="fieldsetCls fieldsetHeadCls" id="add_fieldsetId">'+
        '        <legend>&diams;业务增值</legend>'+
        '   <table class="kv-table">'+
        '       <tr>'+
        '       <td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span></td>'+
        '       <td class="kv-content">'+
        '       <input class="easyui-textbox" value="${idcReNewlyModel.name}"'+
        '   name="idcReNewlyModel.name" id="idcReNewlyModel_name" '+
        '       />'+
        '       </td>'+
        '       <td class="kv-label" style="width: 200px;">业务分类<span style="color:red">&nbsp;*</span></td>'+
        '       <td class="kv-content">'+
        '       <input class="easyui-combobox" data-options="'+
        '   valueField: \'value\','+
        '       textField: \'label\','+
        '           editable:false,'+
        '           url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/800\'" name="idcReNewlyModel.category"  id="idcReNewlyModel_category" value="${idcReNewlyModel.category}" />'+
        '       </td>'+
        '       <td class="kv-label">描述</td>'+
        '           <td class="kv-content">'+
        '           <input class="easyui-textbox" value="${idcReNewlyModel.desc}"'+
        '       name="idcReNewlyModel.desc" id="idcReNewlyModel_desc"'+
        '       data-options="multiline:true,height:28"/>'+
        '           </td>'+
        '           </tr>'+
        '           </table>'+
        '           </fieldset>';
    var targetObj = $("#add_fieldset_content_Id").append(add_fieldset_contentHTML);
    $.parser.parse(targetObj);
}
function ticketCategoryServer(){
    $("#server_fieldset_content_Id").empty();
    var server_fieldset_contentHTML =
        '<fieldset class="fieldsetCls fieldsetHeadCls"  id="server_fieldsetId">'+
        '        <legend>&diams;主机租赁</legend>'+
        '    <table class="kv-table">'+
        '        <tr>'+
        '       <td class="kv-label" style="width: 200px;">资源类型<span style="color:red">&nbsp;*</span></td>'+
        '       <td class="kv-content">'+
        '       <input class="easyui-combobox" data-options="'+
        '   valueField: \'value\','+
        '       textField: \'label\','+
        '           editable:false,'+
        '           url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/500\'" name="idcReServerModel.typeMode" id="idcReServerModel_typeMode" value="${idcReServerModel.typeMode}" />'+
        '       </td>'+
        '       <td class="kv-label">设备型号<span style="color:red">&nbsp;*</span></td>'+
        '           <td class="kv-content">'+
        '       <input class="easyui-textbox" name="idcReServerModel.specNumber" id="idcReServerModel_specNumber" value="${idcReServerModel.specNumber}" />'+
        '           </td>'+
        '           <td class="kv-label" style="width: 200px;">数量(个)<span style="color:red">&nbsp;*</span></td>'+
        '           <td class="kv-content">'+
        '           <input   class="easyui-numberbox"'+
        '       name="idcReServerModel.num"'+
        '       value="${idcReServerModel.num}"'+
        '       id="idcReServerModel_num"'+
        '       data-options="precision:0,min:0,validType:\'length[0,5]\'"/>'+
        '           </td>'+
        '           </tr>'+
        '           <tr>'+
        '           <td class="kv-label" style="width: 200px;">描述</td>'+
        '           <td class="kv-content" colspan="5">'+
        '           <input class="easyui-textbox" value="${idcReServerModel.desc}"'+
        '       name="idcReServerModel.desc" id="idcReServerModel_desc"'+
        '       data-options="multiline:true,height:28"/>'+
        '           </td>'+
        '           </tr>'+
        '           </table>'+
        '           </fieldset>';
    var targetObj = $("#server_fieldset_content_Id").append(server_fieldset_contentHTML);
    $.parser.parse(targetObj);
}
function ticketCategoryIp(){
    $("#ip_fieldset_content_Id").empty();
    var ip_fieldset_contentHTML =
        '<fieldset class="fieldsetCls fieldsetHeadCls"  id="ip_fieldsetId">'+
        '        <legend>&diams;IP租用</legend>'+
        '   <table class="kv-table">'+
        '        <tr>'+
        '        <td class="kv-label" style="width: 200px;">IP租用<span style="color:red">&nbsp;*</span></td>'+
        '        <td class="kv-content">'+
        '        <input class="easyui-combobox" data-options="'+
        '   valueField: \'value\','+
        '       textField: \'label\','+
        '           editable:false,'+
        '           url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/400\'" name="idcReIpModel.portMode" id="idcReIpModel_portMode" value="${idcReIpModel.portMode}" />'+
        '           </td>'+
        '           <td class="kv-label" style="width: 200px;">数量(个)<span style="color:red">&nbsp;*</span></td>'+
        '               <td class="kv-content" colspan="2">'+
        '               <input  class="easyui-numberbox" value="${idcReIpModel.num}"'+
        '           name="idcReIpModel.num" id="idcReIpModel_num"'+
        '           data-options=" multiline:true,height:28,validType:\'length[0,5]\',width:150,"/>'+
        '               </td>'+

        '           <td class="kv-label" style="width: 200px;">描述</td>'+
        '               <td class="kv-content" colspan="2">'+
        '               <input  class="easyui-textbox" value="${idcReIpModel.desc}"'+
        '           name="idcReIpModel.desc" id="idcReIpModel_desc"'+
        '           data-options="multiline:true,height:28,width:150"/>'+
        '               </td>'+

        '               </tr>'+
        '               </table>'+
        '               </fieldset>';
    var targetObj = $("#ip_fieldset_content_Id").append(ip_fieldset_contentHTML);
    $.parser.parse(targetObj);
}
//端口动态配置情况
function ticketCategoryPort(){
    $("#port_fieldset_content_Id").empty();
    var port_fieldset_contentHTML =
        '<fieldset class="fieldsetCls fieldsetHeadCls" id="port_fieldsetId">'+
        '        <legend>&diams;端口带宽</legend>'+
        '   <table class="kv-table">'+
        '       <tr>'+
        '       <td class="kv-label" style="width: 200px;">端口带宽<span style="color:red">&nbsp;*</span></td>'+
        '       <td class="kv-content">'+
        '       <input class="easyui-combobox" data-options="'+
        '   valueField: \'value\','+
        '       textField: \'label\','+
        '           editable:false,'+
        '           url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/300\'" name="idcRePortModel.portMode" id="idcRePortModel_portMode" value="${idcRePortModel.portMode}" />'+
        '       </td>'+
        '       <td class="kv-label" style="width: 200px;">带宽大小(兆)<span style="color:red">&nbsp;*</span></td>'+
        '           <td class="kv-content">'+
        '           <input class="easyui-numberbox" data-options="precision:0,min:0,validType:\'length[0,10]\'" name="idcRePortModel.bandwidth" id="idcRePortModel_bandwidth" value="${idcRePortModel.bandwidth}" />'+
        '       </td>'+
        '       <td class="kv-label">数量(个)<span style="color:red">&nbsp;*</span></td>'+
        '           <td class="kv-content">'+
        '           <input class="easyui-numberbox"'+
        '       name="idcRePortModel.num"'+
        '       value="${idcRePortModel.num}"'+
        '       id="idcReRackModel_num"'+
        '       data-options="precision:0,min:0,validType:\'length[0,5]\'"/>'+
        '           </td>'+
        '           </tr>'+
        '           <tr>'+
        '           <td class="kv-label" style="width: 200px;">描述</td>'+
        '           <td class="kv-content" colspan="5">'+
        '           <input class="easyui-textbox" value="${idcRePortModel.desc}"'+
        '       name="idcRePortModel.desc" id="idcRePortModel_desc"'+
        '       data-options="multiline:true,height:28"/>'+
        '           </td>'+
        '           </tr>'+
        '           </table>'+
        '           </fieldset>';
    var targetObj = $("#port_fieldset_content_Id").append(port_fieldset_contentHTML);
    $.parser.parse(targetObj);
}
function ticketCategoryRack() {
    $("#rack_fieldset_content_Id").empty();
    var rack_fieldset_contentHTML =
        ' <fieldset class="fieldsetCls fieldsetHeadCls" id="rack_fieldsetId">'+
        '     <legend>&diams;机架机位</legend>'+
        '    <table class="kv-table">'+
        '        <tr>'+
        '        <td class="kv-label" style="width: 200px;">规格<span style="color:red">&nbsp;*</span></td>'+
        '        <td class="kv-content">'+
        '        <input class="easyui-combobox"  data-options="valueField: \'value\',textField: \'label\',editable:false,url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/100\'" name="idcReRackModel.spec" id="idcReRackModel_spec"  value="${idcReRackModel.spec}" />'+
        '        </td>'+
        '        <td class="kv-label" style="width: 200px;">规格<span style="color:red">&nbsp;*</span></td>'+
        '        <td class="kv-content">'+
        '        <input class="easyui-combobox"  data-options="valueField: \'value\',textField: \'label\',editable:false,url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/100\'" name="idcReRackModel.spec" id="idcReRackModel_spec"  value="${idcReRackModel.spec}" />'+
        '        </td>'+
        '       <td class="kv-label">机架/机位数(个)<span style="color:red">&nbsp;*</span></td>'+
        '       <td class="kv-content">'+
        '       <input class="easyui-numberbox"  name="idcReRackModel.rackNum" value="${idcReRackModel.rackNum}"'+
        '   id="idcReRackModel_rackNum" data-options="precision:0,min:0,validType:\'length[0,5]\'"/>'+
        '       </td>'+
        '       </tr>'+
        '       <tr>'+
        '       <td class="kv-label" style="width: 200px;">供电类型<span style="color:red">&nbsp;*</span></td>'+
        '       <td class="kv-content">'+
        '        <input class="easyui-combobox"  data-options="valueField: \'value\',textField: \'label\',editable:false,url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/200\'" name="idcReRackModel.supplyType" id="idcReRackModel_supplyType"  value="${idcReRackModel.supplyType}" />'+
        '       </td>'+
        '       <td class="kv-label" style="width: 200px;">描述</td>'+
        '       <td class="kv-content" colspan="3">'+
        '       <input class="easyui-textbox" value="${idcReRackModel.desc}"'+
        '   name="idcReRackModel.desc" id="idcReRackModel_desc"'+
        '   data-options="multiline:true,height:28"/>'+
        '       </td>'+
        '       </tr>'+
        '   </table>'+
        '</fieldset>';
    var targetObj = $("#rack_fieldset_content_Id").append(rack_fieldset_contentHTML);
    $.parser.parse(targetObj);
}


function icpaccesstypeOnChange(newValue,oldValue){
    $("#vm_content").empty();
    if(newValue != oldValue && newValue == '1'){
        //虚拟主机
        var fieldsetHTML = '<fieldset class="fieldsetCls fieldsetHeadCls">'+
            '<legend>&diams;虚拟机信息</legend>'+
            '    <table class="kv-table">'+
            '       <tr>'+
            '       <td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmName" value="${idcNetServiceinfo.vmName}"  id="idcNetServiceinfo.vmName" data-options="validType:\'length[0,128]\',width:150"/>'+
            '       </td>'+
            '       <td class="kv-label">状态<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       editable:false,'+
            '       width:150,'+
            '       data: [{'+
            '       label: \'可用\','+
            '       value: \'1\''+
            '   },{'+
            '       label: \'不可用\','+
            '       value: \'-1\''+
            '   }]" value="${idcNetServiceinfo.vmStatus}"  name="idcNetServiceinfo.vmStatus" id="idcNetServiceinfo.vmStatus"/>'+
            '   </td>'+
            '   </tr>'+
            '   <tr>'+
            '   <td class="kv-label">类型<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       width:150,'+
            '       editable:false,'+
            '       data: [{'+
            '       label: \'共享式\','+
            '       value: \'1\''+
            '   },{'+
            '       label: \'专用式\','+
            '       value: \'2\''+
            '   },{'+
            '       label: \'云虚拟\','+
            '       value: \'3\''+
            '   }]" value="${idcNetServiceinfo.vmCategory}"  name="idcNetServiceinfo.vmCategory" id="idcNetServiceinfo.vmCategory"/>'+

            '   </td>'+
            '   <td class="kv-label">网络地址<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <!-- idcNetServiceinfo.vmNetAddr -->'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmNetAddr" value="${idcNetServiceinfo.vmNetAddr}"  id="idcNetServiceinfo.vmNetAddr" data-options="validType:\'length[0,128]\',width:150"/>'+
            '       </td>'+
            '       </tr>'+
            '       <tr>'+
            '       <td class="kv-label">管理地址<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmManagerAddr" value="${idcNetServiceinfo.vmManagerAddr}"  id="idcNetServiceinfo.vmManagerAddr" data-options="validType:\'length[0,128]\',width:150"/>'+
            '       </td>'+
            '       <td class="kv-label"></td>'+
            '       <td class="kv-content">'+
            '       </td>'+
            '       </tr>'+
            '   </table>'+
            '</fieldset>';
        var targetObj = $("#vm_content").prepend(fieldsetHTML);
        $.parser.parse(targetObj);
    }else{
        $("#vm_content").empty();
    }
}

function dnsAdd(){

    $("#contract_content").empty();
    var fieldsetHTML = '<fieldset class="fieldsetCls fieldsetHeadCls">'+
        '<legend>&diams;服务信息</legend>'+
        '<table class="kv-table">'+
        '<tr>'+
        '<td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span></td>'+
        ' <td class="kv-content">'+
        /*' <input type="hidden" name="idcNetServiceinfo.id" value="${idcNetServiceinfo.id}" id="idcNetServiceinfo.id"/>'+*/
        ' <input name="idcNetServiceinfo.ticketInstId" type="hidden" value="${ticketInstId}">'+
        '  <input class="easyui-textbox" name="idcNetServiceinfo.icpsrvname" value="${idcNetServiceinfo.icpsrvname}"  id="idcNetServiceinfo.icpsrvname" data-options="validType:\'length[0,64]\',width:250"/>'+
        '  </td>'+
        '  <td class="kv-label">所属用户</td>'+
        '   <td class="kv-content">'+
        '   <input name="idcNetServiceinfo.customerId" value="${idcReCustomer.id}" type="hidden" />'+
        '  <input class="easyui-textbox" readonly="readonly" name="idcNetServiceinfo.customerName" value="${idcReCustomer.name}"  id="idcNetServiceinfo.customerName" data-options="validType:\'length[0,128]\',width:250"/>'+
        '   </td>'+
        '   </tr>'+

        '   <tr>'+
        '   <td class="kv-label">服务内容<span style="color:red">&nbsp;*</span></td>'+
        '<td class="kv-content">'+
        '<div>  <ul id="ttFWNR" checkbox="true" data-options="lines:true,width:250" value="${idcNetServiceinfo.icpsrvcontentcode}"  name="idcNetServiceinfo.icpsrvcontentcode" id="idcNetServiceinfo.icpsrvcontentcode"></ul>  </div> ' +

        '</td>'+
        '<td class="kv-label">备案类型<span style="color:red">&nbsp;*</span></td>'+
        '<td class="kv-content">'+
        '    <input class="easyui-combobox" data-options="'+
        'valueField: \'value\','+
        '    textField: \'label\','+
        '    editable:false,'+
        '   width:250,'+
        '   data: [{'+
        '   label: \'无\','+
        '   value: \'0\''+
        '},{'+
        '    label: \'经营性网站\','+
        '   value: \'1\''+
        '},{'+
        '    label: \'非经营性网站\','+
        '   value: \'2\''+
        '},{'+
        '    label: \'SP\','+
        '     value: \'3\''+
        ' },{'+
        '    label: \'BBS\','+
        '    value: \'4\''+
        ' },{'+
        '      label: \'其它\','+
        '      value: \'999\''+
        '  }]" value="${idcNetServiceinfo.icprecordtype}"  name="idcNetServiceinfo.icprecordtype" id="idcNetServiceinfo.icprecordtype"/>'+
        '  </td>'+
        '   </tr>'+
        '   <tr>'+
        '  <td class="kv-label">备案号[许可证号]<span style="color:red">&nbsp;*</span></td>'+
        ' <td class="kv-content">'+
        '   <input class="easyui-textbox" name="idcNetServiceinfo.icprecordno" value="${idcNetServiceinfo.icprecordno}"  id="idcNetServiceinfo.icprecordno" data-options="validType:\'length[0,4000]\',width:250"/>'+
        '   </td>'+
        '   <td class="kv-label">接入方式<span style="color:red">&nbsp;*</span></td>'+
        '<td class="kv-content">'+
        '   <input class="easyui-combobox" data-options="'+
        'valueField: \'value\','+
        '   textField: \'label\','+
        '   width:250,'+
        '   editable:false,'+
        '   onChange:icpaccesstypeOnChange,'+
        '   data: [{'+
        '   label: \'专线\','+
        '   value: \'0\''+
        '},{'+
        '   label: \'虚拟主机\','+
        '   value: \'1\''+
        '   },{'+
        '    label: \'主机托管\','+
        '      value: \'2\''+
        '     },{'+
        '      label: \'整机租用\','+
        '       value: \'3\''+
        '   },{'+
        '       label: \'其它\','+
        '      value: \'999\''+
        '  }]" value="${idcNetServiceinfo.icpaccesstype}"  name="idcNetServiceinfo.icpaccesstype" id="idcNetServiceinfo.icpaccesstype"/>'+
        '  </td>'+
        '   </tr>'+
        '   <tr>'+
        '   <td class="kv-label">域名信息<span style="color:red">&nbsp;*</span></td>'+
        '   <td class="kv-content">'+
        '     <input class="easyui-textbox" name="idcNetServiceinfo.icpdomain" value="${idcNetServiceinfo.icpdomain}"  id="idcNetServiceinfo.icpdomain" data-options="validType:\'length[0,4000]\',width:250"/>'+
        '     </td>'+
        '     <td class="kv-label">业务类型<span style="color:red">&nbsp;*</span></td>'+
        '  <td class="kv-content">'+
        '      <input class="easyui-combobox" data-options="'+
        '  valueField: \'value\','+
        '     textField: \'label\','+
        '     width:250,'+
        '    editable:false,'+
        '    data: [{'+
        '     label: \'IDC业务\','+
        '     value: \'1\''+
        '        },{'+
        '       label: \'ISP业务\','+
        '       value: \'2\''+
        '    }]" value="${idcNetServiceinfo.icpbusinesstype}"  name="idcNetServiceinfo.icpbusinesstype" id="idcNetServiceinfo.icpbusinesstype"/>'+
        '    </td>'+
        '   </tr>'+
        '    <div>\n' +
        '        <span style="color: deeppink;font-size: 15px">\n' +
        '            &emsp;域名信息规则是：1.不同域名通过英文分号 ; 隔开，例如：  10.101.10.1;10.101.10.2;www.baidu.com\n' +
        '            <br>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;\n' +
        '            2.IP段的情况，只能输入短横线-，例如：10.101.10.1-10.101.10.255\n' +
        '        </span>\n' +
        '    </div>\n'+
        ' </table>'+
        '</fieldset>';

    var targetObj = $("#contract_content").prepend(fieldsetHTML);
    $.parser.parse(targetObj);
    /*如果选择的是虚拟机*/
    if("${idcNetServiceinfo.icpaccesstype}" == 1){
        $("#vm_content").empty();
        var fieldsetHTML = '<fieldset class="fieldsetCls fieldsetHeadCls">'+
            '<legend>&diams;虚拟机信息</legend>'+
            '    <table class="kv-table">'+
            '       <tr>'+
            '       <td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmName" value="${idcNetServiceinfo.vmName}"  id="idcNetServiceinfo.vmName" data-options="validType:\'length[0,128]\',width:200"/>'+
            '       </td>'+
            '       <td class="kv-label">状态<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       editable:false,'+
            '       width:200,'+
            '       data: [{'+
            '       label: \'可用\','+
            '       value: \'1\''+
            '   },{'+
            '       label: \'不可用\','+
            '       value: \'-1\''+
            '   }]" value="${idcNetServiceinfo.vmStatus}"  name="idcNetServiceinfo.vmStatus" id="idcNetServiceinfo.vmStatus"/>'+
            '   </td>'+
            '   </tr>'+
            '   <tr>'+
            '   <td class="kv-label">类型<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       width:200,'+
            '       editable:false,'+
            '       data: [{'+
            '       label: \'共享式\','+
            '       value: \'1\''+
            '   },{'+
            '       label: \'专用式\','+
            '       value: \'2\''+
            '   },{'+
            '       label: \'云虚拟\','+
            '       value: \'3\''+
            '   }]" value="${idcNetServiceinfo.vmCategory}"  name="idcNetServiceinfo.vmCategory" id="idcNetServiceinfo.vmCategory"/>'+

            '   </td>'+
            '   <td class="kv-label">网络地址<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <!-- idcNetServiceinfo.vmNetAddr -->'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmNetAddr" value="${idcNetServiceinfo.vmNetAddr}"  id="idcNetServiceinfo.vmNetAddr" data-options="validType:\'length[0,128]\',width:200"/>'+
            '       </td>'+
            '       </tr>'+
            '       <tr>'+
            '       <td class="kv-label">管理地址<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmManagerAddr" value="${idcNetServiceinfo.vmManagerAddr}"  id="idcNetServiceinfo.vmManagerAddr" data-options="validType:\'length[0,128]\',width:200"/>'+
            '       </td>'+
            '       <td class="kv-label"></td>'+
            '       <td class="kv-content">'+
            '       </td>'+
            '       </tr>'+
            '   </table>'+
            '</fieldset>';
        var targetObj = $("#vm_content").prepend(fieldsetHTML);
        $.parser.parse(targetObj);
    }

    $('#ttFWNR').combotree({
        multiple:true,
        data:[],
        url:contextPath+"/service.json",
        method:'get',
        onClick:function(data){
            var t = $('#tt').combotree('tree');	// get the tree object
            var n = t.tree('getSelected');		// get selected node
        }
    });

}

function otherAdd(){
    $("#contract_content").empty();
    $("#vm_content").empty();
    var fieldsetHTML ='<fieldset class="fieldsetCls fieldsetHeadCls">'+
        '<legend>&diams;服务信息</legend>'+
        ' <table class="kv-table">'+
        '  <tr>'+
        '   <td class="kv-label" style="width:215px">服务开通时间<span style="color:red">&nbsp;*</span></td>'+
        ' <td class="kv-content" colspan="3">'+
        '   <input class="easyui-datetimebox"  name="idcNetServiceinfo.openTime" value="${idcNetServiceinfo.openTime}" data-options="editable:false,showSeconds:true,width:350">'+
        '    </td>'+
        '    </tr>'+
        '   </table>'+
        '    </fieldset>';
    var targetObj = $("#contract_content").prepend(fieldsetHTML);
    $.parser.parse(targetObj);
}

function ticketAttachLoadsuccess(){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
}

</script>