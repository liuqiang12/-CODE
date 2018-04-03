<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jbpm" uri="http://jbpm.idc.tag.cn/" %>
<%@ taglib prefix="jbpmSecurity" uri="http://jbpmSecurity.idc.tag.cn/" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/custom/uimaker/css/basic_info.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/checkbox/skin/style/css/checkbox.css" />
    <script type="application/javascript" src="<%=request.getContextPath() %>/framework/checkbox/js/checkbox.js" ></script>

    <!-- 引用 -->
    <title>订单服务管理</title>
</head>
<style type="text/css">

    .fieldsetCls{
        border-color: #eee !important;
    }

    .fieldsetCls > *{
        /*增加border变色
         safdasf

         */
        color: #1e1e1e !important;
    }
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0;
    }
</style>
<body style="background-color: #e9f6fa">
<!--  tabs布局  -->
<div id="jbpmForm_tabs" class="easyui-tabs" fit="true">
    <div title="客户信息" style="padding:10px;display:none;">
        <jbpm:jbpmTag module="TICKET_RECUSTOMER" item="${idcReCustomer}" ticketInstId="${ticketInstId}"></jbpm:jbpmTag>
    </div>
    <div title="客户需求" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <fieldset class="fieldsetCls fieldsetHeadCls">
            <legend>&diams;预受理信息表</legend>
            <table class="kv-table">
                <tr>
                    <td class="kv-label" style="width: 200px;">业务名称</td>
                    <td class="kv-content">
                        <input readonly="readonly" id="idcReProductbusName" name="idcReProductbusName" value="${idcReProduct.busName}" class="easyui-textbox" data-options="required:false,width:150">
                    </td>
                    <td class="kv-label">意向IDC</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly" data-options="
                                       valueField: 'value',
                                           textField: 'label',
                                           editable:false,
                                           width:200,
                                           url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/900'" name="idcReProductidcName"  value="${idcReProduct.idcName}"  />
                    </td>
                    <td class="kv-label">预占有效天数</td>
                    <td class="kv-content">
                        <input readonly="readonly" id="idcReProductvalidity" name="idcReProductvalidity" value="${idcReProduct.validity}" class="easyui-numberbox" data-options="required:false,validType:'length[0,4]',width:150">
                    </td>
                </tr>
                <tr>
                    <td class="kv-label" style="width: 200px;">申请人</td>
                    <td class="kv-content">
                        <input readonly="readonly" name="applyerName" value="${idcReProduct.applyerName}" class="easyui-textbox" data-options="width:150">
                    </td>
                    <td class="kv-label">申请人角色</td>
                    <td class="kv-content">
                        <input readonly="readonly" name="applyerRoles" value="${idcReProduct.applyerRoles}" class="easyui-textbox" data-options="width:200">

                    </td>
                    <td class="kv-label">联系电话</td>
                    <td class="kv-content">
                        <input readonly="readonly" name="applyerPhone" value="${idcReProduct.applyerPhone}" class="easyui-textbox" data-options="width:150">
                        <input type="hidden" value="${idcReProduct.id}" name="id"/>
                        <input type="hidden" value="${idcReProduct.prodName}" name="prodName"/>
                        <input type="hidden" value="${idcReProduct.prodCategory}" name="prodCategory"/>
                        <input type="hidden" value="${idcReProduct.isActive}" name="isActive"/>
                        <input type="hidden" value="${idcReProduct.prodStatus}" name="prodStatus"/>
                        <input type="hidden" value="${idcReProduct.createTime}" name="createTime"/>
                        <input type="hidden" name="customerId" value="${idcReProduct.customerId}" id="customerId"/>
                    </td>
                </tr>
            </table>
        </fieldset>
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
            <!-- 服务信息 -->
            <c:if test="${not empty idcNetServiceinfo.icpsrvname}">
                <fieldset class="fieldsetCls fieldsetHeadCls">
                    <legend>&diams;服务信息</legend>
                    <table class="kv-table">
                        <tr>
                            <td class="kv-label" style="width: 200px;">名称</td>
                            <td class="kv-content">
                                <input type="hidden" name="ins_id" value="${idcNetServiceinfo.id}" id="ins_id"/>
                                <input name="ins_ticketInstId" type="hidden" value="${ticketInstId}">
                                <input class="easyui-textbox" readonly="readonly"  name="ins_icpsrvname" value="${idcNetServiceinfo.icpsrvname}"  id="icpsrvname" data-options="required:true,validType:'length[0,64]',width:200"/>
                                <input name="prodInstId" type="hidden" value="${prodInstId}">
                                <input name="category" type="hidden" value="${category}">
                                <input type="hidden" value="${idcReProduct.prodCategory}" name="prodCategory"/>

                                <!-- 合同的工单id -->
                                <input name="isRejectTicket" type="hidden" value="${idcRunTicket.isRejectTicket}">
                                <input name="idcContract_ticketInstId" type="hidden" value="${ticketInstId}">

                                <input type="hidden" name="ins_id" value="${idcNetServiceinfo.id}" id="ins_id"/>
                                <input name="ins_ticketInstId" type="hidden" value="${ticketInstId}">
                            </td>
                            <td class="kv-label">所属用户</td>
                            <td class="kv-content">
                                <input name="ins_customerId" value="${idcReCustomer.id}" type="hidden" />
                                <input class="easyui-textbox" readonly="readonly"  readonly="readonly" name="ins_customerName" value="${idcReCustomer.name}"  id="ins_customerName" data-options="required:true,validType:'length[0,128]',width:200"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">服务内容</td>
                            <td class="kv-content">
                                <input class="easyui-combobox" readonly="readonly" data-options="
                                            valueField: 'value',
                                            textField: 'label',
                                            required:true,
                                            editable:false,
                                            width:200,
                                            url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/1000'" value="${idcNetServiceinfo.icpsrvcontentcode}"  name="ins_icpsrvcontentcode" id="ins_icpsrvcontentcode"/>

                            </td>
                            <td class="kv-label">备案类型</td>
                            <td class="kv-content">
                                <input class="easyui-combobox" readonly="readonly" data-options="
                                            valueField: 'value',
                                            textField: 'label',
                                            required:true,
                                            editable:false,
                                            width:200,
                                            data: [{
                                                label: '无',
                                                value: '0'
                                            },{
                                                label: '经营性网站',
                                                value: '1'
                                            },{
                                                label: '非经营性网站',
                                                value: '2'
                                            },{
                                                label: 'SP',
                                                value: '3'
                                            },{
                                                label: 'BBS',
                                                value: '4'
                                            },{
                                                label: '其它',
                                                value: '999'
                                            }]" value="${idcNetServiceinfo.icprecordtype}"  name="ins_icprecordtype" id="ins_icprecordtype"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">备案号[许可证号]</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" readonly="readonly" name="ins_icprecordno" value="${idcNetServiceinfo.icprecordno}"  id="icprecordno" data-options="required:true,validType:'length[0,64]',width:200"/>
                            </td>
                            <td class="kv-label">接入方式</td>
                            <td class="kv-content">
                                <input class="easyui-combobox" readonly="readonly" data-options="
                                            valueField: 'value',
                                            textField: 'label',
                                            required:true,
                                            width:200,
                                            editable:false,
                                            onChange:icpaccesstypeOnChange,
                                            data: [{
                                                label: '专线',
                                                value: '0'
                                            },{
                                                label: '虚拟主机',
                                                value: '1'
                                            },{
                                                label: '主机托管',
                                                value: '2'
                                            },{
                                                label: '整机租用',
                                                value: '3'
                                            },{
                                                label: '其它',
                                                value: '999'
                                            }]" value="${idcNetServiceinfo.icpaccesstype}"  name="ins_icpaccesstype" id="icpaccesstype"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">域名信息</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" readonly="readonly" name="ins_icpdomain" value="${idcNetServiceinfo.icpdomain}"  id="ins_icpdomain" data-options="required:true,validType:'length[0,128]',width:200"/>
                            </td>
                            <td class="kv-label">业务类型</td>
                            <td class="kv-content">
                                <input class="easyui-combobox" readonly="readonly" data-options="
                                            valueField: 'value',
                                            textField: 'label',
                                            required:true,
                                            width:200,
                                            editable:false,
                                            data: [{
                                                label: 'IDC业务',
                                                value: '1'
                                            },{
                                                label: 'ISP业务',
                                                value: '2'
                                            }]" value="${idcNetServiceinfo.icpbusinesstype}"  name="ins_icpbusinesstype" id="ins_icpbusinesstype"/>
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </c:if>
            <jbpm:jbpmTag module="TICKET_NET_VIRTUAL_BTN_SERVER" item="${idcNetServiceinfo}" title="服务按钮"></jbpm:jbpmTag>
            <!-- 虚拟机信息 -->
            <jbpm:jbpmTag module="TICKET_NET_VIRTUAL_SERVER" item="${idcNetServiceinfo}" title="虚拟机信息"></jbpm:jbpmTag>
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

                </div>
                <table class="easyui-datagrid" id="ticket_ip_gridId"></table>
            </jbpmSecurity:security>
            <!-- ip租用 end -->
            <!-- 主机租赁 start -->
            <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${serviceApplyImgStatus.server}">
                <div style="padding: 5px;" id="requestParamSettins_ticket_server_gridId">
                    主机名称: <input class="easyui-textbox"  id="serverName_params" style="width:100px;text-align: left;" data-options="">
                    <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('server')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>

                </div>
                <table class="easyui-datagrid" id="ticket_server_gridId"></table>
            </jbpmSecurity:security>
            <!-- 主机租赁 end -->

        </div>
    </jbpmSecurity:security>
    <!-- 提交开通意见 -->
    <div title="服务申请原因" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <!--
            工单号
            显示申请人
            申请理由
            申请状态
        -->
        <form method="post" id="singleForm" action="<%=request.getContextPath() %>/actJBPMController/procCmentFormSaveOrUpdate.do">

            <table class="kv-table">
                <tr>
                        <!-- 隐藏的属性 -->
                        <input name="id" id="idcRunProcCment_id" type="hidden" value="${idcRunProcCment.id}">
                        <!-- 工单实例 -->
                        <input name="ticketInstId" type="hidden" value="${ticketInstId}">

                        <!-- formKey -->
                        <input name="formKey" type="hidden" value="${formKey}">
                        <!-- 流程实例 -->
                        <input name="procInstId" type="hidden" value="${processInstanceId}">
                        <!-- 执行实例 -->
                        <input name="executionid" type="hidden" value="${processInstanceId}">
                        <!-- 流程实例定义的KEY -->
                        <input name="processDefinitionKey" type="hidden" value="${processDefinitionKey}" id="processDefinitionKey">
                        <!-- 订单实例 -->
                        <input name="prodInstId" type="hidden" value="${prodInstId}">
                        <input name="category" type="hidden" value="${category}">
                        <input type="hidden" value="${idcReProduct.prodCategory}" name="prodCategory"/>
                        <!-- 任务ID -->
                        <input name="taskId" type="hidden" value="${taskid}">
                        <!-- 申请人ID -->
                        <input name="authorId" type="hidden" value="${authorId}">
                        <!-- 控制操作列的显示与否 -->
                        <input id="isShowGridColumnHandlerFlag" type="hidden" value="${isShowGridColumnHandlerFlag}">
                </tr>
                <tr>
                    <td class="kv-label"style="width:100px">申请人名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:420" readonly="readonly" value="${author}" name="author"/>
                        <input name="status" id="idcRunProcCment_status_stand" value="<c:if test="${empty idcRunProcCment.status}">1</c:if><c:if test="${not empty idcRunProcCment.status}">${idcRunProcCment.status}</c:if>" type="hidden"/>

                    </td>
                </tr>
                <%--<tr>
                    <td class="kv-label" style="width:100px">提交状态<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content">
                        <!-- 提交状态：同意 不同意 -->
                        <div style="position:relative;margin: 0px;" >
                            <div style="float:left">
                                <c:if test="${empty idcRunProcCment.status}">
                                <span class="checkbackgrd on_check" id="status_check">
                                    </c:if>
                                        <c:if test="${not empty idcRunProcCment.status}">
                                    <span class="checkbackgrd" id="status_check">
                                    </c:if>
                                        <input type="checkbox" name="idcRunProcCment_status" value="1" class="opacity_default_0">同意
                                    </span>
                            </div>
                            <div style="float:left">
                                     <span class="checkbackgrd" id="status_not_check">
                                        <input type="checkbox" name="idcRunProcCment_status" value="0"  class="opacity_default_0">不同意
                                    </span>
                            </div>
                        </div>
                    </td>
                </tr>--%>

                <tr>
                    <td class="kv-label"style="width:100px" >下线原因<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:420,height:100,multiline:true" name="comment" value="${idcRunProcCment.comment}"  data-options="validType:'length[0,255]'"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label"style="width:100px" >备注<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:420,height:100,multiline:true" name="applydesc" value="${idcRunProcCment.applydesc}"  data-options="validType:'length[0,255]'"/>
                    </td>
                </tr>
            </table>
        </form>
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
                    //提示框
                    top.layer.msg('成功', {
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
        var addAttachHtml = '<div id="fileDiv'+fileListLength+'"><input class="easyui-filebox" name="contractFileList'+fileListLength+'" data-options="prompt:\'选择文件\',buttonText:\'选择\'" style="width:508px"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="removeBotton(this,'+fileListLength+')" style="width:38px">删除</a></div>';

        var targetObj = $("#muiltFilesDiv").append(addAttachHtml);
        $.parser.parse($("#fileDiv"+fileListLength));
    }
    function removeBotton(obj,indx){
        $("#fileDiv"+indx).remove();
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
        $("#vm_content").empty();
        if(newValue != oldValue && newValue == '1'){
            //虚拟主机
            var fieldsetHTML = '<fieldset class="fieldsetCls fieldsetHeadCls">'+
                '<legend>&diams;虚拟机信息</legend>'+
                '    <table class="kv-table">'+
                '       <tr>'+
                '       <td class="kv-label" style="width: 200px;">名称</td>'+
                '       <td class="kv-content">'+
                '       <input class="easyui-textbox" name="ins_vmName" value="${idcNetServiceinfo.vmName}"  id="ins_vmName" data-options="required:true,validType:\'length[0,128]\',width:150"/>'+
                '       </td>'+
                '       <td class="kv-label">状态</td>'+
                '       <td class="kv-content">'+
                '       <input class="easyui-combobox" data-options="'+
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
                '   }]" value="${idcNetServiceinfo.vmStatus}"  name="ins_vmStatus" id="ins_vmStatus"/>'+
                '   </td>'+
                '   </tr>'+
                '   <tr>'+
                '   <td class="kv-label">类型</td>'+
                '       <td class="kv-content">'+
                '       <input class="easyui-combobox" data-options="'+
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
                '   }]" value="${idcNetServiceinfo.vmCategory}"  name="ins_vmCategory" id="ins_vmCategory"/>'+

                '   </td>'+
                '   <td class="kv-label">网络地址</td>'+
                '       <td class="kv-content">'+
                '       <!-- ins_vmNetAddr -->'+
                '       <input class="easyui-textbox" name="ins_vmNetAddr" value="${idcNetServiceinfo.vmNetAddr}"  id="ins_vmNetAddr" data-options="required:true,validType:\'length[0,128]\',width:150"/>'+
                '       </td>'+
                '       </tr>'+
                '       <tr>'+
                '       <td class="kv-label">管理地址</td>'+
                '       <td class="kv-content">'+
                '       <input class="easyui-textbox" name="ins_vmManagerAddr" value="${idcNetServiceinfo.vmManagerAddr}"  id="ins_vmManagerAddr" data-options="required:true,validType:\'length[0,128]\',width:150"/>'+
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
            /*url:contextPath+"/actJBPMController/xxFormSaveOrUpdate.do",*/
            url:contextPath+"/actJBPMController/procCmentFormSaveOrUpdate.do",
            onSubmit: function(){
                if(!$("#singleForm").form("validate")){
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    $("#jbpmForm_tabs").tabs("select","服务申请");
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
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/runCommon.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/attachment/resourceAttachment.js"></script>
</html>