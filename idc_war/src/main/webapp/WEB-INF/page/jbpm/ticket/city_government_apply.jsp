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
    <title></title>
</head>
<!---   --->
<body style="background-color: #e9f6fa">
<!-- 浮动提示框:tip -->
<jbpm:jbpmTag title="工单滚动提示" module="FLOAT_TIP_MSG" width="500" height="23" lineHeight="24" actName="${idcTicket.taskName}" serialNumber="${idcTicket.serialNumber}" comment="请填写审批意见"></jbpm:jbpmTag>
<!--  tabs布局  -->
<div id="jbpmApply_tabs" class="easyui-tabs" fit="true">
    <jbpm:jbpmTag title="客户信息" module="TICKET_RECUSTOMER" item="${idcReCustomer}" ticketInstId="${idcTicket.id}"></jbpm:jbpmTag>
    <!--- 【驳回的情况处理】 --->
    <jbpm:jbpmTag title="客户需求" ticketItem="${idcTicket}" module="TICKET_PRODUCET_MODULE" prodInstId="${idcTicket.prodInstId}" ></jbpm:jbpmTag>
    <jbpm:jbpmTag title="流程图" module="ACTIVITI_DIAGRAM" processInstanceId="${idcTicket.procInstId}" processDefinitionId="${idcTicket.procDefId}" processDefinitionKey="${idcTicket.processdefinitonkey}" ></jbpm:jbpmTag>

    <!-- 资源分配情况。formKey -->
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.resourceAllocationStatus}">
        <div title="资源分配" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
            <iframe id="downloadFile" src="" style="display: none"></iframe>
            <!-- 【附件列表信息:可以操纵的都具有附件情况ticketStatus是否参看】 -->
            <jbpm:jbpmTag ticketItem="${idcTicket}" module="TICKET_ATTACHMENT" gridId="ticketAttachmentGridId" title="工单附件列表"  maxHeight="150" toolbar="ticketAttachmentBbar"></jbpm:jbpmTag>
            <!-- 机架机位 start -->
            <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${productCategory.rack}">
                <div style="padding: 5px;" id="requestParamSettins_ticket_rack_gridId">
                    机架名称: <input class="easyui-textbox"  id="rackName_params" style="width:100px;text-align: left;" data-options="">
                    <!-- 选择机架 -->
                    <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('rack')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.ticketResourceHandStatus and !pageQueryDataStatus}">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('100','','rack','')">选择机架</a>
                        <span style="color:red">申请机架/机位个数为: ${idcReRackModelNum} 个</span>
                    </jbpmSecurity:security>
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
            <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${productCategory.port}">
                <div style="padding: 5px;" id="requestParamSettins_ticket_port_gridId">
                    名称: <input class="easyui-textbox"  id="portName_params" style="width:100px;text-align: left;" data-options="">
                    <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('port')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.ticketResourceHandStatus and !pageQueryDataStatus}">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('200','','port','')">选择带宽端口</a>
                        <span style="color:red">申请带宽端口个数为: ${idcRePortModelNum} 个</span>
                    </jbpmSecurity:security>
                </div>
                <table class="easyui-datagrid" id="ticket_port_gridId"></table>
            </jbpmSecurity:security>
            <!-- 端口带宽 end -->
            <!-- ip租用 start -->
            <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${productCategory.ip}">
                <div style="padding: 5px;" id="requestParamSettins_ticket_ip_gridId">
                    名称: <input class="easyui-textbox"  id="ipName_params" style="width:100px;text-align: left;" data-options="">
                    <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('ip')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.ticketResourceHandStatus and !pageQueryDataStatus}">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('300','','ip','')">选择IP</a>
                        <span style="color:red">申请IP个数为: <c:out value="${idcReIpModelNum}"/>个</span>
                    </jbpmSecurity:security>
                </div>
                <table class="easyui-datagrid" id="ticket_ip_gridId"></table>
            </jbpmSecurity:security>
            <!-- ip租用 end -->
            <!-- 主机租赁 start -->
            <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${productCategory.server}">
                <div style="padding: 5px;" id="requestParamSettins_ticket_server_gridId">
                    主机名称: <input class="easyui-textbox"  id="serverName_params" style="width:100px;text-align: left;" data-options="">
                    <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('server')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.ticketResourceHandStatus and !pageQueryDataStatus}">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('400','','server','')">选择主机</a>
                        <span style="color:red">申请主机数为: ${idcReServerModelNum} 台</span>
                    </jbpmSecurity:security>
                </div>
                <table class="easyui-datagrid" id="ticket_server_gridId"></table>
            </jbpmSecurity:security>
            <!-- 主机租赁 end -->
        </div>
    </jbpmSecurity:security>


    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${isOpenTicket}">

        <jbpm:jbpmTag title="相关工单" hasOtherTicket="2" gridId="his_ticket_gridId" module="OTHER_LINKED_TICKET" toolbar="requestParamSettins_hisShowTaskQuery" prodInstId="${idcTicket.prodInstId}" ticketInstId="${idcTicket.id}"></jbpm:jbpmTag>

        <jbpm:jbpmTag title="开通信息" module="TICKET_CONCTRACT" idcReCustomer="${idcReCustomer}" ticketItem="${idcTicket}" isHasOpenEdit="${isHasOpenEdit}" ticketInstId="${idcTicket.id}"></jbpm:jbpmTag>
    </jbpmSecurity:security>
    <!--  审批的表单属于单独:其中显示的title自定义描述 -->
    <div title="${tabsApplyTitle}" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <div id="apply_info_layout" class="easyui-layout"  fit="true">
            <div data-options="region:'north',title:'历史审批...'" style="height:200px;">
                <jbpm:jbpmTag module="HIS_TICKET_COMMENT" ticketStatus="${idcTicket.ticketStatus}" ticketInstId="${idcTicket.id}" ></jbpm:jbpmTag>
            </div>
            <div data-options="region:'center'" style="padding:2px;">
                <form method="post" id="singleForm" action="<%=request.getContextPath() %>/actJBPMController/procCmentFormSaveOrUpdate.do">
                    <table class="kv-table">
                    <input name="status" id="idcRunProcCment_status_stand" value="<c:if test="${empty idcRunProcCment.status}">1</c:if><c:if test="${not empty idcRunProcCment.status}">${idcRunProcCment.status}</c:if>" type="hidden"/>
                    <!-- 设置空单ID作为参数 -->
                    <input name="ticketInstId" type="hidden" value="${idcTicket.id}">
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${!addEasyuiTab and isHaltTicket}">
                        <tr>
                            <td class="kv-label"style="width:100px" >下线原因 </td>
                            <td class="kv-content" colspan="3">
                                <input class="easyui-textbox" <c:if test="${!isHaltTicketEdit}">readonly="readonly"</c:if>  data-options="required:true,width:783,height:100,multiline:true" name="idcRunTicketHalt.haltreason" value="${idcTicketHalt.haltreason}"  data-options="validType:'length[0,255]'"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label"style="width:100px" >下线备注 </td>
                            <td class="kv-content">
                                <input class="easyui-textbox" <c:if test="${!isHaltTicketEdit}">readonly="readonly"</c:if> data-options="required:true,width:783,height:100,multiline:true" name="idcRunTicketHalt.applydesc" value="${idcTicketHalt.applydesc}"  data-options="validType:'length[0,255]'"/>
                            </td>
                        </tr>
                    </jbpmSecurity:security>
                        <!--- 停机  -->
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${!isAddPasueEasyuiTab and isPauseTicket}">
                        <table class="kv-table">
                            <tr>
                                <td class="kv-label"style="width:100px" >停机开始时间<span style="color:red">&nbsp;*</span></td>
                                <td class="kv-content"colspan="3">
                                    <input class="easyui-datetimebox" <c:if test="${!isPauseTicketEdit}">readonly="readonly"</c:if>  name="idcRunTicketPause.reservestart" value="${idcTicketPause.reservestart}" data-options="required:false,editable:false,showSeconds:true,width:150">
                                </td>
                            </tr>
                            <tr>
                                <td class="kv-label"style="width:100px" >停机结束时间<span style="color:red">&nbsp;*</span></td>
                                <td class="kv-content">
                                    <input class="easyui-datetimebox" <c:if test="${!isPauseTicketEdit}">readonly="readonly"</c:if>  name="idcRunTicketPause.reserveend" value="${idcTicketPause.reserveend}" data-options="required:false,editable:false,showSeconds:true,width:150">
                                </td>
                            </tr>
                            <tr>
                                <td class="kv-label"style="width:100px" >停机原因<span style="color:red">&nbsp;*</span></td>
                                <td class="kv-content">
                                    <input class="easyui-textbox"  <c:if test="${!isPauseTicketEdit}">readonly="readonly"</c:if> data-options="required:false,width:783,height:100,multiline:true" name="idcRunTicketPause.constructComment" value="${idcTicketPause.constructComment}"  data-options="validType:'length[0,255]'"/>
                                </td>
                            </tr>
                    </jbpmSecurity:security>
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${!isAddRecoverEasyuiTab and isRecoverTicket}">
                        <tr>
                            <td class="kv-label"style="width:100px" >复通时间<span style="color:red">&nbsp;*</span></td>
                            <td class="kv-content"colspan="3">
                                <input class="easyui-datetimebox" <c:if test="${!isRecoverTicketEdit}">readonly="readonly"</c:if> name="idcRunTicketRecover.recovertime" value="${idcTicketRecover.recovertimeStr}" data-options="required:false,editable:false,showSeconds:true,width:150">
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label"style="width:100px" >备注<span style="color:red">&nbsp;*</span></td>
                            <td class="kv-content">
                                <input class="easyui-textbox" <c:if test="${!isRecoverTicketEdit}">readonly="readonly"</c:if>  data-options="required:true,width:783,height:100,multiline:true" name="idcRunTicketRecover.constructComment" value="${idcTicketRecover.constructComment}"  data-options="validType:'length[0,255]'"/>
                            </td>
                        </tr>
                    </jbpmSecurity:security>
                    <!-- 这里需要控制，评价的时候，属于另外的表单:设置满意度问题 -->
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${isLastStepTicket}">
                        <tr>
                            <td class="kv-label" style="width:100px">满意度<span style="color:red">&nbsp;*</span></td>
                            <td class="kv-content" colspan="3">
                                <input name="starNum" id="StarNum" value="${idcTicketCommnet.satisfaction}" type="hidden"/>
                                <div class="quiz">
                                    <div class="quiz_content">
                                        <div class="goods-comm">
                                            <div class="goods-comm-stars">
                                                <span class="star_l"></span>
                                                <div id="rate-comm-1" class="rate-comm"></div>
                                            </div>
                                        </div>
                                    </div><!--quiz_content end-->
                                </div><!--quiz end-->
                            </td>
                        </tr>
                    </jbpmSecurity:security>
                        <tr>
                            <td class="kv-label"style="width:100px" >${commentFieldName}<span style="color:red">&nbsp;*</span></td>
                            <td class="kv-content"colspan="3">
                            <!-- 是否readOnly -->
                                <c:choose>
                                    <c:when test="${pageQueryDataStatus}">
                                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:780,height:100,multiline:true" name="comment" value="${idcTicketCommnet.feedback}"  data-options="validType:'length[0,255]'"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input class="easyui-textbox" data-options="required:true,width:780,height:100,multiline:true" name="comment" value="${idcTicketCommnet.feedback}"  data-options="validType:'length[0,255]'"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${addEasyuiTab and isHaltTicket}">
        <div title="下线原因" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
            <form method="post" id="singleHaltForm">
                <!-- form元素 -->
                <table class="kv-table">
                    <tr>
                        <td class="kv-label"style="width:100px" >下线原因<span style="color:red">&nbsp;*</span></td>
                        <td class="kv-content"colspan="3">
                            <!---->
                            <input type="hidden" name="idcRunTicket.ticketCategoryFrom" value="${idcTicket.ticketCategoryFrom}">
                            <input type="hidden" name="idcRunTicket.ticketCategoryTo" value="${idcTicket.ticketCategory}">
                            <input type="hidden" name="idcRunTicket.ticketInstId" value="${idcTicket.ticketInstId}">
                            <input type="hidden" name="idcRunTicket.prodCategory" value="${idcTicket.prodCategory}">
                            <input type="hidden" name="idcRunTicket.prodInstId" value="${idcTicket.prodInstId}">

                            <input name="idcRunTicketHalt.id" type="hidden" value="${idcTicketHalt.id}">
                            <input class="easyui-textbox" data-options="required:true,width:783,height:100,multiline:true" name="idcRunTicketHalt.haltreason" value="${idcTicketHalt.haltreason}"  data-options="validType:'length[0,255]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label"style="width:100px" >备注<span style="color:red">&nbsp;*</span></td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="required:true,width:783,height:100,multiline:true" name="idcRunTicketHalt.applydesc" value="${idcTicketHalt.applydesc}"  data-options="validType:'length[0,255]'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </jbpmSecurity:security>
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${isAddRecoverEasyuiTab and isRecoverTicket}">
        <div title="复通" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
            <form method="post" id="singleRecoverForm">
                <!-- form元素 -->
                <table class="kv-table">
                    <tr>
                        <td class="kv-label"style="width:100px" >复通时间<span style="color:red">&nbsp;*</span></td>
                        <td class="kv-content"colspan="3">
                            <!---->
                            <input type="hidden" name="idcRunTicket.ticketCategoryFrom" value="${idcTicket.ticketCategoryFrom}">
                            <input type="hidden" name="idcRunTicket.ticketCategoryTo" value="${idcTicket.ticketCategory}">
                            <input type="hidden" name="idcRunTicket.ticketInstId" value="${idcTicket.ticketInstId}">
                            <input type="hidden" name="idcRunTicket.prodCategory" value="${idcTicket.prodCategory}">
                            <input type="hidden" name="idcRunTicket.prodInstId" value="${idcTicket.prodInstId}">
                            <input name="idcRunTicketRecover.id" type="hidden" value="${idcRunTicketRecover.id}">
                            <input class="easyui-datetimebox"  name="idcRunTicketRecover.recovertime" value="${idcRunTicketRecover.recovertime}" data-options="required:false,editable:false,showSeconds:true,width:150">
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label"style="width:100px" >备注<span style="color:red">&nbsp;*</span></td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="required:true,width:783,height:100,multiline:true" name="idcRunTicketRecover.constructComment" value="${idcRunTicketRecover.constructComment}"  data-options="validType:'length[0,255]'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </jbpmSecurity:security>
    <!---sssss-->
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${isAddPasueEasyuiTab and isPauseTicket}">
        <div title="停机" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
            <form method="post" id="singlePauseForm">
                <!-- form元素 -->
                <table class="kv-table">
                    <tr>
                        <td class="kv-label"style="width:100px" >停机开始时间<span style="color:red">&nbsp;*</span></td>
                        <td class="kv-content"colspan="3">
                            <!---->
                            <input type="hidden" name="idcRunTicket.ticketCategoryFrom" value="${idcTicket.ticketCategoryFrom}">
                            <input type="hidden" name="idcRunTicket.ticketCategoryTo" value="${idcTicket.ticketCategory}">
                            <input type="hidden" name="idcRunTicket.ticketInstId" value="${idcTicket.ticketInstId}">
                            <input type="hidden" name="idcRunTicket.prodCategory" value="${idcTicket.prodCategory}">
                            <input type="hidden" name="idcRunTicket.prodInstId" value="${idcTicket.prodInstId}">
                            <input name="idcRunTicketPause.id" type="hidden" value="${idcRunTicketPause.id}">

                            <input class="easyui-datetimebox"  name="idcRunTicketPause.reservestart" value="${idcTicketPause.reserveendStr}" data-options="required:false,editable:false,showSeconds:true,width:150">
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label"style="width:100px" >停机结束时间<span style="color:red">&nbsp;*</span></td>
                        <td class="kv-content">
                            <input class="easyui-datetimebox"  name="idcRunTicketPause.reserveend" value="${idcTicketPause.reserveendStr}" data-options="required:false,editable:false,showSeconds:true,width:150">
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label"style="width:100px" >停机原因<span style="color:red">&nbsp;*</span></td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="required:false,width:783,height:100,multiline:true" name="idcRunTicketPause.constructComment" value="${idcTicketPause.constructComment}"  data-options="validType:'length[0,255]'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </jbpmSecurity:security>


    <div title="服务申请" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">

        <form method="post" id="selfSingleForm" >

            <input type="hidden" name="idcRunTicket.ticketCategoryTo" value="${ticketCategoryTo}"/>
            <input type="hidden" name="idcRunTicket.ticketCategoryFrom" value="${ticketCategoryFrom}"/>
            <input type="hidden" name="idcRunTicket.ticketInstId" value="${ticketInstId}"/>
            <input type="hidden" name="idcRunTicket.prodCategory" value="${prodCategory}"/>
            <input type="hidden" name="idcRunTicket.prodInstId" value="${prodInstId}"/>

        </form>
    </div>


</div>
</body>
<jsp:include page="/globalstatic/easyui/jbpmUtils.jsp"></jsp:include>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketRackResourceGrid.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/hisTicketGridQuery.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/runCommon.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/attachment/resourceAttachment.js"></script>
</html>