<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 自定义标签 -->
<%@ taglib prefix="jbpm" uri="http://jbpm.idc.tag.cn/" %>
<%@ taglib prefix="jbpmSecurity" uri="http://jbpmSecurity.idc.tag.cn/" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">

    <script type="application/javascript" src="<%=request.getContextPath() %>/framework/workFlowRoot.js" ></script>
    <!-- 引用 -->
    <title>订单服务管理[地市IDC政企管理员]</title>
</head>
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
<body style="background-color: #e9f6fa">
<div class="tip_float_right_info" style="height:23px;line-height:24px;width: 497px;">
    <marquee direction="left"onmouseover="this.stop();" onmouseout="this.start();" onstart="this.firstChild.innerHTML+=this.firstChild.innerHTML;" scrollamount="20" scrolldelay="500" behavior="SCROLL" hspace="0" vspace="0" loop="INFINITE">
        <span style="color: #006107">当前活动节点:<span style="color: blue;">${actName}</span>;工单号:<span style="color: blue;">${serialNumber}</span>;请填写审批意见....</span>
    </marquee>
</div>

<!--  tabs布局
    1:jsp:include
    2:自定义标签
    3:restlet接口返回html
 -->
<div id="jbpmApply_tabs" class="easyui-tabs" fit="true">
    <!-- 判断:自定标签  c标签  spec标签  restlet接口返回html   ajax:动态加载   -->
    <div title="客户信息" style="padding:10px;display:none;">
        <jbpm:jbpmTag module="TICKET_RECUSTOMER" item="${idcReCustomer}" ticketInstId="${ticketInstId}"></jbpm:jbpmTag>
    </div>
    <div title="客户需求" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <jbpm:jbpmTag  module="TICKET_PRODUCET_INFO" item="${idcReProduct}" ticketItem="${idcRunTicket}"   category="${category}"></jbpm:jbpmTag>
        <jbpm:jbpmTag module="TICKET_PRODUCET_MODULE" item="${serviceApplyImgStatus}"></jbpm:jbpmTag>
    </div>

    <div title="流程图" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <!-- 加载流程图 -->
        <jbpm:jbpmTag module="ACTIVITI_DIAGRAM" processInstanceId="${processInstanceId}" processDefinitionId="${processDefinitionId}" ></jbpm:jbpmTag>

    </div>
    <div title="开通信息" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">

        <!-- 合同信息 -->
        <!-- 开通信息form界面 start -->
        <form id="contractFrom" method="post"  enctype="multipart/form-data" >
            <fieldset class="fieldsetCls fieldsetHeadCls">
                <legend>&diams;合同信息</legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 200px;">名称</td>
                        <td class="kv-content">
                            <input type="hidden" name="id" value="${idcContract.id}" id="id"/>
                            <!-- 合同的工单id -->
                            <input name="idcContract_ticketInstId" type="hidden" value="${ticketInstId}">
                            <input class="easyui-textbox" readonly="readonly" name="contractname" value="${idcContract.contractname}"  id="contractname" data-options="required:true,validType:'length[0,128]',width:250"/>
                        </td>
                        <td class="kv-label">编码</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" readonly="readonly" name="contractno" value="${idcContract.contractno}"  id="contractno" data-options="required:true,validType:'length[0,128]',width:250"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">价格(元)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox" readonly="readonly" name="contractprice" value="${idcContract.contractprice}" id="contractprice" data-options="required:true,width:250,min:0,validType:'length[0,12]'"/>
                        </td>
                        <td class="kv-label">客户信息</td>
                        <td class="kv-content">
                            <!--  -->
                            <input type="hidden" name="idcContract_customerId" value="${idcReCustomer.id}" id="idcContract_customerId"/>
                            <input id="idcContract_customerName" readonly="readonly" value="${idcReCustomer.name}" class="easyui-textbox" data-options="editable:false,required:true,width:250">
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">开始时间</td>
                        <td class="kv-content">
                            <input class="easyui-datetimebox"  readonly="readonly" name="contractstart" value="${idcContract.contractstart}" data-options="required:true,editable:false,showSeconds:true,width:250">
                        </td>

                        <td class="kv-label">合同期限(月)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox"  readonly="readonly"name="contractperiod" value="${idcContract.contractperiod}" id="contractperiod" data-options="required:true,precision:0,min:0,validType:'length[0,10]',width:250"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">签订日期</td>
                        <td class="kv-content">
                            <input class="easyui-datetimebox"  readonly="readonly" name="contractsigndate" value="${idcContract.contractsigndate}" data-options="required:true,editable:false,showSeconds:true,width:250">
                        </td>

                        <td class="kv-label">到期提醒方案</td>
                        <td class="kv-content">
                            <input class="easyui-combobox"  readonly="readonly" data-options="
                                    valueField: 'value',
                                    textField: 'label',
                                    required:true,
                                    editable:false,
                                    width:250,
                                    data: [{
                                        label: '联系客户经理',
                                        value: '0'
                                    },{
                                        label: '短信',
                                        value: '1'
                                    },{
                                        label: '邮件',
                                        value: '2'
                                    }]" value="${idcContract.contractexpirreminder}"  name="contractexpirreminder" id="contractexpirreminder"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">合同备注</td>
                        <td class="kv-content" colspan="3">
                            <input class="easyui-textbox"  readonly="readonly" multiline="true"  name="contractremark"  value="${idcContract.contractremark}" style="width:83%;height:50px" data-options="validType:'length[0,255]'">
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">合同附件</td>
                        <td class="kv-content" colspan="3">
                            <div id="muiltFilesDiv">
                                <!-- 罗列出已经添加了的附件信息 -->
                                <jbpm:jbpmTag contractId="${idcContract.id}" module="IDC_CONTRACT_ATTACH" width="550"></jbpm:jbpmTag>
                            </div>
                        </td>
                    </tr>

                </table>
            </fieldset>
            <!-- 虚拟机信息 -->
            <div id="contract_content">
                <%--这里面是提供域名用户填写的信息--%>
                <jbpm:jbpmTag module="TICKET_NET_SERVER" idcRunTicket="${idcRunTicket}" item="${idcNetServiceinfo}" idcReProduct="${idcReProduct}" idcReCustomer="${idcReCustomer}" ticketInstId="${ticketInstId}" prodInstId="${prodInstId}" category="${category}"></jbpm:jbpmTag>
            </div>
        </form>
        <!-- 开通信息form界面 end-->
    </div>
    <!-- 资源分配 start -->
    <jbpmSecurity:security module="TICKET_RESOURCE_MODULE" moreThan="1" item="${serviceApplyImgStatus}">
        <!-- 只有有资源申请就可以分配 -->
    <div title="资源分配" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <iframe id="downloadFile" src="" style="display: none"></iframe>
        <!-- 【附件列表信息】 -->
        <jbpm:jbpmTag module="TICKET_ATTACHMENT" count="${ticketAttachCount}" gridId="ticketAttachmentGridId" prodInstId="${prodInstId}" title="工单附件列表"  maxHeight="150" toolbar="ticketAttachmentBbar" ticketInstId="${ticketInstId}" isShowGridColumnHandlerFlag="${isShowGridColumnHandlerFlag}"></jbpm:jbpmTag>
        <!-- 机架机位 start -->
        <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${serviceApplyImgStatus.rack}">
            <div style="padding: 5px;" id="requestParamSettins_ticket_rack_gridId">
                机架名称: <input class="easyui-textbox"  id="rackName_params" style="width:100px;text-align: left;" data-options="">
                <!-- 选择机架 -->
                <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('rack')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('100','','rack','')">选择</a>--%>
            </div>
            <table class="easyui-datagrid" id="ticket_rack_gridId"></table>

            <div style="padding: 5px;" id="requestParamSettins_ticket_rackmcb_gridId">
                MCB: <input class="easyui-textbox"  id="rackmcbName_params" style="width:100px;text-align: left;" data-options="">
                <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('mcb')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </div>
            <table class="easyui-datagrid" id="ticket_rackmcb_gridId"></table>
        </jbpmSecurity:security>
        <!-- 机架机位 end -->
        <!-- 端口带宽 start -->
        <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${serviceApplyImgStatus.port}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_port_gridId">
            名称: <input class="easyui-textbox"  id="portName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('port')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </div>
        <table class="easyui-datagrid" id="ticket_port_gridId"></table>
        </jbpmSecurity:security>
        <!-- 端口带宽 end -->
        <!-- ip租用 start -->
        <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${serviceApplyImgStatus.ip}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_ip_gridId">
            名称: <input class="easyui-textbox"  id="ipName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('ip')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('300','','ip','')">选择</a>--%>
        </div>
        <table class="easyui-datagrid" id="ticket_ip_gridId"></table>
        </jbpmSecurity:security>
        <!-- ip租用 end -->
        <!-- 主机租赁 start -->
        <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${serviceApplyImgStatus.server}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_server_gridId">
            主机名称: <input class="easyui-textbox"  id="serverName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('server')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="preServerChoiceOperate('ticket_server_gridId')">选择</a>--%>
        </div>
        <table class="easyui-datagrid" id="ticket_server_gridId"></table>
        </jbpmSecurity:security>
    </div>
    </jbpmSecurity:security>
    <jbpm:jbpmTag title="相关工单" hasOtherTicket="${hasOtherTicket}" gridId="his_ticket_gridId" module="OTHER_LINKED_TICKET" toolbar="requestParamSettins_hisShowTaskQuery" prodInstId="${prodInstId}" ticketInstId="${ticketInstId}"></jbpm:jbpmTag>
    <!-- 资源分配 end -->
    <div title="审批" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <div id="apply_info_layout" class="easyui-layout"  fit="true">
            <div data-options="region:'north',title:'历史审批...'" style="height:200px;">
                <jbpm:jbpmTag module="HIS_TICKET_COMMENT" is_author_apply_show="true" prodInstId="${prodInstId}" ticketInstId="${ticketInstId}" ></jbpm:jbpmTag>
            </div>
            <div data-options="region:'center'" style="padding:2px;">
                <form method="post" id="singleForm" action="<%=request.getContextPath() %>/actJBPMController/procCmentFormSaveOrUpdate.do">
                    <table class="kv-table">
                                <!-- 隐藏的属性 -->
                                <input name="id" id="idcRunProcCment_id" type="hidden" value="${idcRunProcCment.id}">
                                <!-- 工单实例 -->
                                <input name="ticketInstId" id="ticketInstId_param" type="hidden" value="${ticketInstId}"><input name="is_author_apply_show" id="is_author_apply_show" type="hidden" value="${is_author_apply_show}">

                                <!-- 订单实例 -->
                                <input name="prodInstId" id="prodInstId_param" type="hidden" value="${prodInstId}"><input  id="category_param" type="hidden" value="${category}">
                                <!-- formKey -->
                                <input name="formKey" type="hidden" value="${formKey}">
                                <!-- 流程实例 -->
                                <input name="procInstId" type="hidden" value="${processInstanceId}">
                                <!-- 流程实例定义的KEY -->
                                <input name="processDefinitionKey" type="hidden" value="${processDefinitionKey}" id="processDefinitionKey">
                                <!-- 执行实例 -->
                                <input name="executionid" type="hidden" value="${processInstanceId}">
                                <!-- 任务ID -->
                                <input name="taskId" type="hidden" value="${taskid}">
                                <!-- 申请人ID -->
                                <input name="authorId" type="hidden" value="${authorId}">
                                <!-- 控制操作列的显示与否 -->
                                <input id="isShowGridColumnHandlerFlag" type="hidden" value="${isShowGridColumnHandlerFlag}">
                            <input name="status" id="idcRunProcCment_status_stand" value="<c:if test="${empty idcRunProcCment.status}">1</c:if><c:if test="${not empty idcRunProcCment.status}">${idcRunProcCment.status}</c:if>" type="hidden"/>

                        <tr>
                            <td class="kv-label"style="width:100px" >审批意见<span style="color:red">&nbsp;*</span></td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="required:true,width:420,height:100,multiline:true" name="comment" value="${idcRunProcCment.comment}"  data-options="validType:'length[0,255]'"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>

</div>
</body>
<script type="text/javascript">
    try {
        var rack = "${serviceApplyImgStatus.rack}" || null;
        var port = "${serviceApplyImgStatus.port}"||null;
        var ip = "${serviceApplyImgStatus.ip}"||null;
        var server = "${serviceApplyImgStatus.server}"||null;
        var add = "${serviceApplyImgStatus.add}"||null;

        if(rack && rack == "true"){
            //被选中
            $("#rack_check").addClass("on_check_disabled");
        }else{
            $("#rack_check").addClass("disabled_not_check");
        }

        if(port && port == "true"){
            $("#port_check").addClass("on_check_disabled");
        }else{
            $("#port_check").addClass("disabled_not_check");
        }
        if(ip && ip == "true"){
            $("#ip_check").addClass("on_check_disabled");
        }else{
            $("#ip_check").addClass("disabled_not_check");
        }
        if(server && server == "true"){
            $("#server_check").addClass("on_check_disabled");
        }else{
            $("#server_check").addClass("disabled_not_check");
        }
        if(add && add == "true"){
            $("#add_check").addClass("on_check_disabled");
        }else{
            $("#add_check").addClass("disabled_not_check");
        }
    }catch(err) {
        console.log(err)
    }finally {

    }
    $(function(){
        //针对于审批提交状态设置
        var status_res = '${idcRunProcCment.status}';
        $("input[name='idcRunProcCment_status']").each(function(){
            var val = this.value;
            if(val == status_res){
                $(this).parents(".checkbackgrd").removeClass("on_not_check").addClass("on_check");
            }
        })
    });
    /*下载附件信息*/
    function downLoadAttach(id){
        $("#downloadFile").attr("src",contextPath+"/assetAttachmentinfoController/downLoadFile/"+id);
    }
    function removeAttach(obj,id){
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
                    top.layer.msg(data.msg, {
                        icon: 1,
                        time: 3000 //（默认是3秒）
                    });
                    $(obj).parents("div.attachHasedCls").remove();
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
    function addAttachContract(){
        //添加附件信息
        var fileListLength = $("input[name^='contractFileList']").length;
       var addAttachHtml = '<div id="fileDiv'+fileListLength+'"><input class="easyui-filebox" name="contractFileList'+fileListLength+'" data-options="prompt:\'选择文件\',buttonText:\'选择\'" style="width:508px"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="removeBotton(this)" style="width:38px">删除</a></div>';

        var targetObj = $("#muiltFilesDiv").append(addAttachHtml);
         $.parser.parse($("#fileDiv"+fileListLength));
    }
    function removeBotton(obj){
        console.log(obj)
    }

    function openWinCustomerNameContractFun(){
        //选择弹出框
        var laySum = top.layer.open({
            type: 2,
            title: '<span style="color:blue">客户人员</span>'+"》选择",
            shadeClose: false,
            shade: 0.8,
            btn: ['确定','关闭'],
            maxmin : true,
            area: ['884px', '650px'],
            content: contextPath+"/customerController/customerQuery.do",
            /*弹出框*/
            cancel: function(index, layero){
                top.layer.close(index);
            },yes: function(index, layero){
                var childIframeid = layero.find('iframe')[0]['name'];
                var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                //返回相应的数据
                var selectedRecord = chidlwin.gridSelectedRecord("gridId");
                $("#idcContract_customerId").val(selectedRecord?selectedRecord['id']:null);
                $("#idcContract_customerName").textbox('setValue', selectedRecord?selectedRecord['name']:null);
                top.layer.close(index)
                //刷新列表信息
            },no: function(index, layero){
                top.layer.close(index)
            },end:function(){
                //top.layer.close(index);
            }
        });
    }
    function icpaccesstypeOnChange(newValue,oldValue){
        if(newValue != oldValue && newValue == '1'){
            //虚拟主机
           var fieldsetHTML = '<fieldset class="fieldsetCls fieldsetHeadCls">'+
            '<legend>&diams;虚拟机信息</legend>'+
            '    <table class="kv-table">'+
            '       <tr>'+
            '       <td class="kv-label" style="width: 200px;">名称</td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" readonly="readonly"　name="idcNetServiceinfo.vmName" value="${idcNetServiceinfo.vmName}"  id="idcNetServiceinfo.vmName" data-options="required:true,validType:\'length[0,128]\',width:150"/>'+
            '       </td>'+
            '       <td class="kv-label">状态</td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" readonly="readonly" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       required:true,'+
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
            '   <td class="kv-label">类型</td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" readonly="readonly" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       width:150,'+
            '       required:true,'+
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
            '   <td class="kv-label">网络地址</td>'+
            '       <td class="kv-content">'+
            '       <!-- idcNetServiceinfo.vmNetAddr -->'+
            '       <input class="easyui-textbox" readonly="readonly"　name="idcNetServiceinfo.vmNetAddr" value="${idcNetServiceinfo.vmNetAddr}"  id="idcNetServiceinfo.vmNetAddr" data-options="required:true,validType:\'length[0,128]\',width:150"/>'+
            '       </td>'+
            '       </tr>'+
            '       <tr>'+
            '       <td class="kv-label">管理地址</td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" readonly="readonly"　name="idcNetServiceinfo.vmManagerAddr" value="${idcNetServiceinfo.vmManagerAddr}"  id="idcNetServiceinfo.vmManagerAddr" data-options="required:true,validType:\'length[0,128]\',width:150"/>'+
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
    /**
     * 类别
     * @param category
     */
    function serviceApplyImgStatusOnclick(category){
        alert("设置300被选中");
        $("#serviceApplyImgStatus300")
    }
    /*提交表单情况*/
    function form_sbmt(grid,jbpmFunFlag){
        if(jbpmFunFlag && jbpmFunFlag == 'rejectJbpm'){
            $("#idcRunProcCment_status_stand").val(0);//驳回情况
        }
        $("#singleForm").form('submit', {
            url:contextPath+"/actJBPMController/procCmentFormSaveOrUpdate.do",
            onSubmit: function(){

                if(!$("#singleForm").form("validate")){
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    $("#jbpmApply_tabs").tabs("select","审批");
                    return false;
                }
            },
            success:function(data){
                //然后修改下一个form
                var obj = jQuery.parseJSON(data);
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                var parentIndex = parent.layer.getFrameIndex("jbpmApplyWinId");//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
                grid.datagrid("reload");
            }
        });
    }
    function getRackORRacunitOrMCBSetting(){
        var obj = {};
        obj['rackOrracunit'] = '${rackOrracunit}';
        obj['ticketInstId'] = '${ticketInstId}';
        obj['prodInstId'] = '${prodInstId}';
        obj['category'] = '${category}';
        return obj;
    }
    /* 具有的有关资源类型组合 */
    function getTicketResourceCategory(){
        var obj = {};
        obj['rack'] = rack;
        obj['port'] = port;
        obj['ip'] = ip;
        obj['server'] = server;
        obj['add'] = add;
        return obj;
    }
</script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketRackResourceGrid.js"></script>
<%--<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketHisCommentQuery.js"></script>--%>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/hisTicketGridQuery.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/runCommon.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/attachment/resourceAttachment.js"></script>
</html>