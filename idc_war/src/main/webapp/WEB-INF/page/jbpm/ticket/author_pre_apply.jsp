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
    <script type="application/javascript">
        $(function(){
            $(".checkbackgrd").bind("click",function(){
                if(!$(this).hasClass("disabled_check")){
                    $(this).hasClass("on_check")?$(this).removeClass("on_check"):$(this).addClass("on_check");
                    var catalog = $(this).find("input:checkbox").val();
                    if($(this).hasClass("on_check")){
                        /*自动添加界面*/
                        if(catalog == 100){
                            /* add [ ]*/
                            $("#rack_fieldset_content_Id").empty();

                            var rack_fieldset_contentHTML =
                                ' <fieldset class="fieldsetCls fieldsetHeadCls" id="rack_fieldsetId">'+
                                '     <legend>&diams;机架机位</legend>'+
                                '    <table class="kv-table">'+
                                '        <tr>'+
                                '        <td class="kv-label" style="width: 200px;">规格<span style="color:red">&nbsp;*</span></td>'+
                                '        <td class="kv-content">'+
                                '        <input class="easyui-combobox"  data-options="valueField: \'value\',textField: \'label\',editable:false,required:true,url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/100\'" name="idcReRackModel.spec" id="idcReRackModel_spec"  value="${idcReRackModel.spec}" />'+
                                '        </td>'+
                                '        <td class="kv-label" style="width: 200px;">分配方式<span style="color:red">&nbsp;*</span></td>'+
                                '        <td class="kv-content">'+
                                '        <input class="easyui-combobox"  data-options="required:true,valueField: \'value\',textField: \'label\',editable:false,required:true,url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/66\'" name="idcReRackModel.rackOrracunit" id="idcReRackModel_rackOrracunit"  value="${idcReRackModel.rackOrracunit}" />'+
                                '        </td>'+
                                '       <td class="kv-label">机架/机位数(个)<span style="color:red">&nbsp;*</span></td>'+
                                '       <td class="kv-content">'+
                                '       <input class="easyui-numberbox"  name="idcReRackModel.rackNum" value="${idcReRackModel.rackNum}"'+
                                '   id="idcReRackModel_rackNum" data-options="precision:0,min:0,required:true,validType:\'length[0,5]\'"/>'+
                                '       </td>'+
                                '       </tr>'+
                                '       <tr>'+
                                '       <td class="kv-label" style="width: 200px;">供电类型<span style="color:red">&nbsp;*</span></td>'+
                                '       <td class="kv-content">'+
                                '        <input class="easyui-combobox"  data-options="valueField: \'value\',required:true,textField: \'label\',editable:false,required:true,url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/200\'" name="idcReRackModel.supplyType" id="idcReRackModel_supplyType"  value="${idcReRackModel.supplyType}" />'+
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

                        }else if(catalog == 200){
                            /* add [ ]*/
                            $("#port_fieldset_content_Id").empty();
                            $("#port_fieldsetId").remove();
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
                                '           required:true,'+
                                '           url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/300\'" name="idcRePortModel.portMode" id="idcRePortModel_portMode" value="${idcRePortModel.portMode}" />'+
                                '       </td>'+
                                '       <td class="kv-label" style="width: 200px;">带宽大小(兆)<span style="color:red">&nbsp;*</span></td>'+
                                '           <td class="kv-content">'+
                                '           <input class="easyui-numberbox" data-options="required:true,precision:0,min:0,validType:\'length[0,10]\'" name="idcRePortModel.bandwidth" id="idcRePortModel_bandwidth" value="${idcRePortModel.bandwidth}" />'+
                                '       </td>'+
                                '       <td class="kv-label">数量(个)<span style="color:red">&nbsp;*</span></td>'+
                                '           <td class="kv-content">'+
                                '           <input class="easyui-numberbox"'+
                                '       name="idcRePortModel.num"'+
                                '       value="${idcRePortModel.num}"'+
                                '       id="idcReRackModel_num"'+
                                '       data-options="precision:0,required:true,min:0,validType:\'length[0,5]\'"/>'+
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
                        }else if(catalog == 300){
                            /* add [ ]*/
                            $("#ip_fieldset_content_Id").empty();
                            $("#ip_fieldsetId").remove();
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
                                '           required:true,'+
                                '           url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/400\'" name="idcReIpModel.portMode" id="idcReIpModel_portMode" value="${idcReIpModel.portMode}" />'+
                                '           </td>'+
                                '           <td class="kv-label">数量(个)<span style="color:red">&nbsp;*</span></td>'+
                                '          <td class="kv-content">'+
                                '           <input class="easyui-numberbox"'+
                                '           name="idcReIpModel.num"'+
                                '           value="${idcReIpModel.num}"'+
                                '           id="idcReIpModel_num"'+
                                '           data-options="precision:0,required:true,width:150,min:0,validType:\'length[0,5]\'"/>'+
                                '           </td>'+
                                '           <td class="kv-label">描述</td>'+
                                '               <td class="kv-content" colspan="2">'+
                                '               <input  class="easyui-textbox" value="${idcReIpModel.desc}"'+
                                '           name="idcReIpModel.desc" id="idcReIpModel_desc"'+
                                '           data-options="multiline:true,height:28"/>'+
                                '               </td>'+
                                '               </tr>'+
                                '               </table>'+
                                '               </fieldset>';
                            var targetObj = $("#ip_fieldset_content_Id").append(ip_fieldset_contentHTML);
                            $.parser.parse(targetObj);
                        }else if(catalog == 400){
                            /* add [ ]*/
                            $("#server_fieldset_content_Id").empty();
                            $("#server_fieldsetId").remove();
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
                                '           required:true,'+
                                '           url:\'<%=request.getContextPath()%>/assetBaseinfo/combobox/500\'" name="idcReServerModel.typeMode" id="idcReServerModel_typeMode" value="${idcReServerModel.typeMode}" />'+
                                '       </td>'+
                                '       <td class="kv-label">设备型号<span style="color:red">&nbsp;*</span></td>'+
                                '           <td class="kv-content">'+
                                '       <input class="easyui-textbox" data-options="required:true" name="idcReServerModel.specNumber" id="idcReServerModel_specNumber" value="${idcReServerModel.specNumber}" />'+
                                '           </td>'+
                                '           <td class="kv-label" style="width: 200px;">数量(个)<span style="color:red">&nbsp;*</span></td>'+
                                '           <td class="kv-content">'+
                                '           <input   class="easyui-numberbox"'+
                                '       name="idcReServerModel.num"'+
                                '       value="${idcReServerModel.num}"'+
                                '       id="idcReServerModel_num"'+
                                '       data-options="required:true,precision:0,min:0,validType:\'length[0,5]\'"/>'+
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
                        }else if(catalog == 500){
                            /* add [ ]*/
                            $("#add_fieldset_content_Id").empty();
                            $("#add_fieldsetId").remove();
                            /**/
                            var add_fieldset_contentHTML =
                                '<fieldset class="fieldsetCls fieldsetHeadCls" id="add_fieldsetId">'+
                                '        <legend>&diams;业务增值</legend>'+
                                '   <table class="kv-table">'+
                                '       <tr>'+
                                '       <td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span></td>'+
                                '       <td class="kv-content">'+
                                '       <input class="easyui-textbox" value="${idcReNewlyModel.name}"'+
                                '   name="idcReNewlyModel.name" data-options="required:true" id="idcReNewlyModel_name"'+
                                '       />'+
                                '       </td>'+
                                '       <td class="kv-label" style="width: 200px;">业务分类<span style="color:red">&nbsp;*</span></td>'+
                                '       <td class="kv-content">'+
                                '       <input class="easyui-combobox" data-options="required:true,'+
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
                    }else{
                        /*去掉情况*/
                        if(catalog == 100){
                            $("#rack_fieldset_content_Id").empty();
                        }else if(catalog == 200){
                            $("#port_fieldset_content_Id").empty();
                        }else if(catalog == 300){
                            $("#ip_fieldset_content_Id").empty();
                        }else if(catalog == 400){
                            $("#server_fieldset_content_Id").empty();
                        }else if(catalog == 500){
                            $("#add_fieldset_content_Id").empty();
                        }
                    }
                }
            });
        });
    </script>
    <!-- 引用 -->
    <title>订单服务管理</title>
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
</style>
<body style="background-color: #e9f6fa">
<!--  tabs布局  -->
<div id="jbpmForm_tabs" class="easyui-tabs" fit="true">
    <div title="客户信息" style="padding:10px;display:none;">
        <!-- 客户信息form界面 -->
        <jbpm:jbpmTag module="TICKET_RECUSTOMER" item="${idcReCustomer}" ticketInstId="${ticketInstId}"></jbpm:jbpmTag>
    </div>
    <div title="客户需求" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <!-- 客户需求form界面 -->
        <form id="productFrom" method="post">
            <jbpm:jbpmTag module="TICKET_PRODUCET_INFO"  item="${idcReProduct}" ticketItem="${idcRunTicket}" isFormEdit="true" category="${category}"></jbpm:jbpmTag>
            <fieldset class="fieldsetCls fieldsetHeadCls">
                <legend>&diams;客户需求信息</legend>
                <table class="kv-table">

                    <tr>
                        <td colspan="8">
                            <div style="position:relative;margin: 17px 42px 3px 207px">
                                <div style="float:left">
                                <span class="checkbackgrd" id="rack_check">
                                    <input type="checkbox" name="serviceApplyImgStatus" value="100" class="opacity_default_0">机架机位
                                </span>
                                </div>
                                <div style="float:left">
                                 <span class="checkbackgrd" id="port_check">
                                    <input type="checkbox" name="serviceApplyImgStatus" value="200"  class="opacity_default_0">端口带宽
                                </span>
                                </div>
                                <div style="float:left">
                                 <span class="checkbackgrd" id="ip_check">
                                    <input type="checkbox" disabled name="serviceApplyImgStatus" value="300" class="opacity_default_0">IP租用
                                </span>
                                </div>
                                <div style="float:left">
                                 <span class="checkbackgrd" id="server_check">
                                    <input type="checkbox" disabled name="serviceApplyImgStatus" value="400"  class="opacity_default_0">主机租赁
                                </span>
                                </div>
                                <div style="float:left">
                                 <span class="checkbackgrd" id="add_check">
                                    <input type="checkbox" disabled name="serviceApplyImgStatus" value="500" class="opacity_default_0">增值业务
                                </span>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <jbpm:jbpmTag module="TICKET_PRODUCET_MODULE" item="${serviceApplyImgStatus}" isFormEdit="true"></jbpm:jbpmTag>
        </form>
    </div>
    <div title="流程图" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <!-- 加载流程图 -->
        <jbpm:jbpmTag module="ACTIVITI_DIAGRAM" processInstanceId="${processInstanceId}" processDefinitionId="${processDefinitionId}" ></jbpm:jbpmTag>
    </div>
    <!-- 只有被驳回的情况才有相关的动作 -->
    <c:if test="${idcRunTicket.isRejectTicket}">
        <div title="服务申请" data-options="closable:false,showHeader:false" style="overflow:auto;padding:10px;display:none;">
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
                        <!-- 订单实例 -->
                        <input name="prodInstId" type="hidden" value="${prodInstId}">
                        <!-- formKey -->
                        <input name="formKey" type="hidden" value="${formKey}">
                        <!-- 流程实例 -->
                        <input name="procInstId" type="hidden" value="${processInstanceId}">
                        <!-- 执行实例 -->
                        <input name="executionid" type="hidden" value="${processInstanceId}">
                        <!-- 流程实例定义的KEY -->
                        <input name="processDefinitionKey" type="hidden" value="${processDefinitionKey}" id="processDefinitionKey">
                        <input name="isRejectTicket" type="hidden" value="${idcRunTicket.isRejectTicket}">
                        <!-- 任务ID -->
                        <input name="taskId" type="hidden" value="${taskid}">
                        <!-- 申请人ID -->
                        <input name="authorId" type="hidden" value="${authorId}">
                        <!-- 客户id -->
                        <input name="customerId" id="customerId" type="hidden" value="${idcReProduct.customerId}">
                        <input name="customerName" id="customerName" type="hidden" value="${idcReProduct.customerName}">

                    </tr>
                    <tr>
                        <td class="kv-label" style="width:100px">驳回处理人</td>
                        <td class="kv-content">
                            <!-- 提交状态：同意提交申请 暂不提交申请 -->
                            <input class="easyui-textbox" data-options="width:620" readonly="readonly" value="${idcRunTicket.applyName}"  />
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width:100px">驳回意见</td>
                        <td class="kv-content">
                            <!-- 提交状态：同意提交申请 暂不提交申请 -->
                            <input class="easyui-textbox" readonly="readonly" data-options="width:620,height:100,multiline:true"  value="${idcRunTicket.rejectComment}"  data-options="validType:'length[0,255]'"/>
                            <input name="status" id="idcRunProcCment_status_stand" value="<c:if test="${empty idcRunProcCment.status}">1</c:if><c:if test="${not empty idcRunProcCment.status}">${idcRunProcCment.status}</c:if>" type="hidden"/>

                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label"style="width:100px" >备注<span style="color:red">&nbsp;*</span></td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="required:true,width:620,height:100,multiline:true" name="comment" value="${idcRunProcCment.comment}"  data-options="validType:'length[0,255]'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </c:if>
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
            $("#rack_check").addClass("on_check");
        }else{
            $("#rack_check").addClass("on_not_check");
        }

        if(port && port == "true"){
            $("#port_check").addClass("on_check");
        }else{
            $("#port_check").addClass("on_not_check");
        }
        if(ip && ip == "true"){
            $("#ip_check").addClass("on_check");
        }else{
            $("#ip_check").addClass("on_not_check");
        }
        if(server && server == "true"){
            $("#server_check").addClass("on_check");
        }else{
            $("#server_check").addClass("on_not_check");
        }
        if(add && add == "true"){
            $("#add_check").addClass("on_check");
        }else{
            $("#add_check").addClass("on_not_check");
        }
    }catch(err) {
        console.log(err)
    }finally {

    }

    /*提交表单情况*/
    function form_sbmt(grid,jbpmFunFlag){
        if(jbpmFunFlag && jbpmFunFlag == 'rejectJbpm'){
            $("#idcRunProcCment_status_stand").val(0);//驳回情况
        }else{
            $("#idcRunProcCment_status_stand").val(1);//
        }
        var isRejectTicket = '${idcRunTicket.isRejectTicket}';
        $("#productFrom").form('submit', {
            url:contextPath+"/actJBPMController/productFormSaveOrUpdate.do",
            onSubmit: function(){
                //是否被选中
                var count_checked = 0;
                $("span[id$='_check']").each(function(){
                    if ($(this).hasClass("on_check")) {
                        count_checked++
                    }
                });
                if(count_checked == 0 ){
                    top.layer.msg('至少需要一个服务项', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    return false;
                }

                if(!$("#customerId") == null || !$("#customerName") == null){
                    //验证未通过
                    top.layer.msg('验证未通过,客户信息不能为空', {
                        icon: 2,
                        time: 2000 //（默认是3秒）
                    });
                }

                if(!$("#productFrom").form("validate")){
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    //直接定位到:[审批界面]
                    $("#jbpmForm_tabs").tabs("select","客户需求");
                    return false;
                }

                if(isRejectTicket && $("#singleForm") && !$("#singleForm").form("validate")){
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    //直接定位到:[审批界面]
                    $("#jbpmForm_tabs").tabs("select","服务申请");
                    return false;
                }
            },
            success:function(data){
                /* 提交流程情况：singleForm */
                /*判断 服务申请是否存在*/
                if(isRejectTicket && $("#singleForm") && $("#singleForm").form("validate")){
                     $("#singleForm").form('submit', {
                        url:contextPath+"/actJBPMController/procCmentFormSaveOrUpdate.do",
                        onSubmit: function(){
                            if(isRejectTicket && !$("#singleForm").form("validate")){
                                //验证未通过
                                $.messager.show({
                                    title : '提示',
                                    msg : "验证未通过，请核实数据项!"
                                });
                                //直接定位到:[审批界面]
                                $("#jbpmForm_tabs").tabs("select","服务申请");
                                return false;
                            }
                        },
                        success:function(data){
                            //然后修改下一个form
                            var obj = jQuery.parseJSON(data);
                            top.layer.msg(obj.msg, {
                                icon: 1,
                                time: 3000 //（默认是3秒）
                            });
                            var parentIndex = parent.layer.getFrameIndex("jbpmApplyWinId");//获取了父窗口的索引
                            parent.layer.close(parentIndex);  // 关闭layer
                            grid.datagrid("reload");
                        }
                    });
                }else{
                    //然后修改下一个form
                    var obj = jQuery.parseJSON(data);
                    top.layer.msg(obj.msg, {
                        icon: 1,
                        time: 3000 //（默认是3秒）
                    });
                    var parentIndex = parent.layer.getFrameIndex("createJbpmTicketId");//获取了父窗口的索引
                    parent.layer.close(parentIndex);  // 关闭layer
                    grid.datagrid("reload");
                }
            }
        });
    }
</script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketHisCommentQuery.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/runCommon.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/attachment/resourceAttachment.js"></script>
</html>