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
                    </td>
                </tr>
            </table>
        </fieldset>
        <!-- 客户需求form界面 -->
        <fieldset class="fieldsetCls fieldsetHeadCls">
            <legend>&diams;客户需求信息</legend>
            <table class="kv-table">
                <tr>
                    <td class="kv-label" style="width: 200px;">所属客户</td>
                    <td class="kv-content">
                        <input type="hidden" value="${idcReProduct.id}" name="id"/>
                        <input type="hidden" value="${idcReProduct.prodName}" name="prodName"/>
                        <input type="hidden" value="${idcReProduct.prodCategory}" name="prodCategory"/>
                        <input type="hidden" value="${idcReProduct.isActive}" name="isActive"/>
                        <input type="hidden" value="${idcReProduct.prodStatus}" name="prodStatus"/>
                        <input type="hidden" value="${idcReProduct.createTime}" name="createTime"/>
                        <input type="hidden" name="customerId" value="${idcReProduct.customerId}" id="customerId"/>
                        <input id="customerName"readonly="readonly"   value="${idcReProduct.customerName}" class="easyui-textbox" data-options="editable:false,width:150,required:true,iconAlign:'left',buttonText:'选择' ">
                    </td>
                    <td class="kv-label">客户级别</td>
                    <td class="kv-content">
                        <input readonly="readonly" value="${idcReCustomer.customerlevel}" class="easyui-textbox" data-options="width:150">

                    </td>
                    <td class="kv-label">联系人</td>
                    <td class="kv-content">
                        <!-- 客户选择框 -->
                        <input readonly="readonly" value="${idcReCustomer.contactname}" class="easyui-textbox" data-options="width:150">
                    </td>
                </tr>
                <tr>
                    <td colspan="8">
                        <div style="position:relative;margin: 17px 42px 3px 207px">                            <div style="float:left">                            <span class="checkbackgrd" id="rack_check">                                <input type="checkbox" name="serviceApplyImgStatus" value="100" class="opacity_default_0">机架机位                            </span>                            </div>                            <div style="float:left">                             <span class="checkbackgrd" id="port_check">                                <input type="checkbox" name="serviceApplyImgStatus" value="200"  class="opacity_default_0">端口带宽                            </span>                            </div>                            <div style="float:left">                             <span class="checkbackgrd" id="ip_check">                                <input type="checkbox" disabled name="serviceApplyImgStatus" value="300" class="opacity_default_0">IP租用                            </span>                            </div>                            <div style="float:left">                             <span class="checkbackgrd" id="server_check">                                <input type="checkbox" disabled name="serviceApplyImgStatus" value="400"  class="opacity_default_0">主机租赁                            </span>                            </div>                            <div style="float:left">                             <span class="checkbackgrd" id="add_check">                                <input type="checkbox" disabled name="serviceApplyImgStatus" value="500" class="opacity_default_0">增值业务                            </span>                            </div>                        </div>
                    </td>
                </tr>
            </table>
        </fieldset>
        <!-- 订单情况 -->
        <c:if test="${serviceApplyImgStatus.rack == 'true'}">
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls">
                <legend>&diams;机架机位</legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 200px;">规格</td>
                        <td class="kv-content">
                            <input class="easyui-combobox"
                                   readonly="readonly"
                                   data-options="valueField: 'value',
                                   textField: 'label',
                                   editable:false,
                                   width:150,
                                   required:true,
                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/100' "
                                   name="idcReRackModel.spec"
                                   id="idcReRackModel_spec"
                                   value="${idcReRackModel.spec}" />
                        </td>
                        <td class="kv-label" style="width: 200px;">分配方式</td>
                        <td class="kv-content">
                            <input class="easyui-combobox"
                                   readonly="readonly"
                                   data-options="valueField: 'value',
                                   textField: 'label',
                                   editable:false,
                                   width:150,
                                   required:true,
                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/66' "
                                   name="idcReRackModel.rackOrracunit"
                                   id="idcReRackModel_rackOrracunit"
                                   value="${idcReRackModel.rackOrracunit}" />
                        </td>
                        <td class="kv-label">机架/机位数(个)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox" readonly="readonly"  name="idcReRackModel.rackNum" value="${idcReRackModel.rackNum}"
                                   id="idcReRackModel_rackNum" data-options="precision:0,width:150,min:0,validType:'length[0,11]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">供电类型</td>
                        <td class="kv-content">
                            <input class="easyui-combobox"
                                   readonly="readonly"
                                   data-options="valueField: 'value',
                                   textField: 'label',
                                   editable:false,
                                   width:150,
                                   required:true,
                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/200'
                                   "
                                   name="idcReRackModel.supplyType"
                                   id="idcReRackModel_supplyType"
                                   value="${idcReRackModel.supplyType}" />
                        </td>

                        <td class="kv-label" style="width: 200px;">描述</td>
                        <td class="kv-content" colspan="3">
                            <input class="easyui-textbox" readonly="readonly"  value="${idcReRackModel.desc}"
                                   name="idcReRackModel.desc" id="idcReRackModel_desc"
                                   data-options="multiline:true,width:150,height:28"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </c:if>
        <!-- 端口带宽 -->
        <c:if test="${serviceApplyImgStatus.port == 'true'}">
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls">
                <legend>&diams;端口带宽</legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 200px;">端口带宽</td>
                        <td class="kv-content">
                            <input class="easyui-combobox"
                                   readonly="readonly"
                                   data-options="
                                      valueField: 'value',
                                       textField: 'label',
                                       editable:false,
                                       required:true,
                                       width:150,
                                        url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/300'
                                " name="idcRePortModel.portMode" id="idcRePortModel_portMode" value="${idcRePortModel.portMode}" />
                        </td>
                        <td class="kv-label" style="width: 200px;">带宽大小(兆)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox" readonly="readonly" data-options="precision:0,width:150,min:0,validType:'length[0,11]'" name="idcRePortModel.bandwidth" id="idcRePortModel_bandwidth" value="${idcRePortModel.bandwidth}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">数量(个)</td>
                        <td class="kv-content">
                            <input readonly="readonly"  class="easyui-numberbox"
                                   name="idcRePortModel.num"
                                   value="${idcRePortModel.num}"
                                   id="idcReRackModel_num"
                                   data-options="precision:0,width:150,min:0,validType:'length[0,11]'"/>
                        </td>
                        <td class="kv-label" style="width: 200px;">描述</td>
                        <td class="kv-content" colspan="5">
                            <input readonly="readonly"  class="easyui-textbox" value="${idcRePortModel.desc}"
                                   name="idcRePortModel.desc" id="idcRePortModel_desc"
                                   data-options="multiline:true,width:150,height:28"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </c:if>
        <!-- IP租用 -->
        <c:if test="${serviceApplyImgStatus.ip == 'true'}">
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls">
                <legend>&diams;IP租用</legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 200px;">IP租用</td>
                        <td class="kv-content">
                            <input class="easyui-combobox"
                                   readonly="readonly"
                                   data-options="
                               valueField: 'value',
                                   textField: 'label',
                                   editable:false,
                                   required:true,
                                   width:150,
                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/400'
                                   " name="idcReIpModel.portMode" id="idcReIpModel_portMode" value="${idcReIpModel.portMode}" />

                        </td>
                        <td class="kv-label">数量(个)</td>
                        <td class="kv-content">
                            <input readonly="readonly"  class="easyui-numberbox"
                                   name="idcReIpModel.num"
                                   value="${idcReIpModel.num}"
                                   id="idcReIpModel_num"
                                   data-options="precision:0,width:150,min:0,validType:'length[0,5]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 200px;">描述</td>
                        <td class="kv-content" colspan="5">
                            <input readonly="readonly"  class="easyui-textbox" value="${idcReIpModel.desc}"
                                   name="idcReIpModel.desc" id="idcReIpModel_desc"
                                   data-options="multiline:true,width:150,height:28"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </c:if>
        <!-- IP租用 -->
        <c:if test="${serviceApplyImgStatus.server == 'true'}">
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls">
                <legend>&diams;主机租赁</legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 200px;">资源类型</td>
                        <td class="kv-content">
                            <input readonly="readonly"  class="easyui-combobox" data-options="
                                   valueField: 'value',
                                       textField: 'label',
                                       editable:false,
                                       required:true,width:150,

                                       data: [{
                                       label: '----',
                                       value: '1'
                                   },{
                                       label: 'XXXX',
                                       value: '2'
                                   }]" name="idcReServerModel.typeMode" id="idcReServerModel_typeMode" value="${idcReServerModel.typeMode}" />
                        </td>
                        <td class="kv-label">设备型号</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="width:150" readonly="readonly"  value="${idcReServerModel.specNumber}"
                                   name="idcReServerModel.specNumber" id="idcReServerModel_specNumber"
                            />

                        </td>
                        <td class="kv-label" style="width: 200px;">数量(个)</td>
                        <td class="kv-content">
                            <input readonly="readonly"  class="easyui-numberbox"
                                   name="idcReServerModel.num"
                                   value="${idcReServerModel.num}"
                                   id="idcReServerModel_num"
                                   data-options="precision:0,width:150,min:0,validType:'length[0,11]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 200px;">描述</td>
                        <td class="kv-content" colspan="4">
                            <input readonly="readonly"  class="easyui-textbox" value="${idcReServerModel.desc}"
                                   name="idcReServerModel.desc" id="idcReServerModel_desc"
                                   data-options="multiline:true,width:150,height:28"/>
                        </td>

                    </tr>
                </table>
            </fieldset>
        </c:if>
        <c:if test="${serviceApplyImgStatus.add == 'true'}">
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls">
                <legend>&diams;业务增值</legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 200px;">名称</td>
                        <td class="kv-content">
                            <input readonly="readonly"  class="easyui-textbox" data-options="width:150" value="${idcReNewlyModel.name}"
                                   name="idcReNewlyModel.name" id="idcReNewlyModel_name"
                            />
                        </td>
                        <td class="kv-label">业务分类</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" readonly="readonly"  data-options="
                                       valueField: 'value',
                                       width:150,
                                           textField: 'label',
                                           editable:false,

                                           data: [{
                                           label: '基础应用',
                                           value: '1'
                                       },{
                                           label: '----',
                                           value: '2'
                                       }]" name="idcReNewlyModel.category" readonly="readonly"  id="idcReNewlyModel_category" value="${idcReNewlyModel.category}" />
                        </td>
                        <td class="kv-label">描述</td>
                        <td class="kv-content">
                            <input readonly="readonly"  class="easyui-textbox" value="${idcReNewlyModel.desc}"
                                   name="idcReNewlyModel.desc" id="idcReNewlyModel_desc"
                                   data-options="multiline:true,height:28,width:150"/>
                        </td>
                    </tr>

                </table>
            </fieldset>
        </c:if>


    </div>
    <div title="流程图" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <!-- 加载流程图 -->
        <jbpm:jbpmTag module="ACTIVITI_DIAGRAM" processInstanceId="${processInstanceId}" processDefinitionId="${processDefinitionId}" ></jbpm:jbpmTag>

    </div>
    <div title="服务申请" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
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
                        <input id="ticketInstId_param" type="hidden" value="${ticketInstId}">

                        <!-- 订单实例 -->
                        <input name="prodInstId" type="hidden" value="${prodInstId}">
                        <!-- 流程实例 -->
                        <input name="procInstId" type="hidden" value="${processInstanceId}">
                        <!-- 流程实例定义的KEY -->
                        <input name="processDefinitionKey" type="hidden" value="${processDefinitionKey}" id="processDefinitionKey">

                        <!-- formKey -->
                        <input name="formKey" type="hidden" value="${formKey}">
                        <!-- 执行实例 -->
                        <input name="executionid" type="hidden" value="${processInstanceId}">
                        <!-- 任务ID -->
                        <input name="taskId" type="hidden" value="${taskid}">
                        <!-- 申请人ID -->
                        <input name="authorId" type="hidden" value="${authorId}">
                        <input name="isRejectTicket" type="hidden" value="${idcRunTicket.isRejectTicket}">
                </tr>
                <!--  rejectName  -->
                <!-- 如果是驳回情况 -->
                <c:if test="${idcRunTicket.isRejectTicket}">
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

                </c:if>

                <tr>
                    <td class="kv-label"style="width:100px" >备注<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:620,height:100,multiline:true" name="comment"  data-options="validType:'length[0,255]'"/>
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
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                var parentIndex = parent.layer.getFrameIndex("createJbpmTicketId");//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
                grid.datagrid("reload");
            }
        });
    }
</script>
</html>