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
    <style>
        .resourceDelete{
            text-decoration:line-through ;
            color: red;
        }

    </style>
</head>
<!---   --->
<body style="background-color: #e9f6fa">
<!-- 浮动提示框:tip -->
<%--<jbpm:jbpmTag title="工单滚动提示" module="FLOAT_TIP_MSG" width="70" height="23" lineHeight="24" actName="${idcTicket.taskName}" serialNumber="${idcTicket.serialNumber}" comment="请填写审批意见"></jbpm:jbpmTag>--%>
<!--  tabs布局  -->
<div id="jbpmApply_tabs" class="easyui-tabs" fit="true">

    <input type="hidden" id="idcTicket_ticketInstId"  name="idcRunTicket.ticketInstId" value="${idcTicket.ticketInstId}">
    <input type="hidden" id="idcTicket_prodInstId"  name="idcRunTicket.prodInstId" value="${idcTicket.prodInstId}">

    <jbpm:jbpmTag title="客户信息" module="TICKET_RECUSTOMER" item="${idcReCustomer}" ticketInstId="${idcTicket.id}"></jbpm:jbpmTag>
    <!--- 【驳回的情况处理】 --->
   <jbpm:jbpmTag title="客户首次需求" ticketItem="${idcTicket}" module="TICKET_PRODUCET_MODULE" prodInstId="${idcTicket.prodInstId}" ></jbpm:jbpmTag>

    <c:choose>
        <c:when test="${processdefinitonkey=='idc_ticket_temporary'}">
            <div title="测试需求" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        </c:when>
        <c:otherwise>
            <div title="客户首次需求" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        </c:otherwise>
    </c:choose>
                <form method="post" id="productReWriteForm" >
                    <input type="hidden" id="ticketCategoryFrom"  name="idcRunTicket.ticketCategoryFrom" value="${idcTicket.ticketCategoryFrom}">
                    <input type="hidden" id="ticketCategoryTo"  name="idcRunTicket.ticketCategoryTo" value="${idcTicket.ticketCategory}">
                    <input type="hidden" id="ticketInstId" name="idcRunTicket.ticketInstId" value="${idcTicket.ticketInstId}">
                    <input type="hidden" id="prodCategory" name="idcRunTicket.prodCategory" value="${idcTicket.prodCategory}">
                    <input type="hidden" id="prodInstId" name="idcRunTicket.prodInstId" value="${idcTicket.prodInstId}">
                    <input type="hidden" id="parentId" name="idcRunTicket.parentId" value="${idcTicket.parentId}">

                    <!-- 预受理信息表 -->
                    <fieldset class="fieldsetCls fieldsetHeadCls" id="first_demand">
                        <legend>&diams;<span style="color: #dd1144">预受理信息表</span></legend>

                            <table class="kv-table">
                                <tr>
                                    <td class="kv-label" style="width: 120px;">业务名称</td>
                                    <td class="kv-content">
                                        <input class="easyui-textbox"
                                               data-options=" width:160 " readonly="readonly"
                                                id="first_rxxack_spec" name="busName" value="${idcReProduct.busName}"/>
                                    </td>
                                    <td class="kv-label" style="width: 120px;">意向IDC</td>
                                    <td class="kv-content">
                                        <span style="position:relative;" class="tipCls" ></span>
                                        <input class="easyui-combobox" data-options="width:120" value="${idcReProduct.idcName}"
                                               id="first_racxxk_rackNum" name="idcName" data-optionsstyle="width:150px;"  readonly="readonly"></input>
                                        <span style="color: red" id="first_xxrack_rackNum_x"></span>
                                    </td>
                                    <td class="kv-label" style="width: 120px;">预占有效天数</td>
                                    <td class="kv-content">
                                        <input class="easyui-textbox"
                                               data-options="width:160 " name="validity" readonly="readonly"
                                               id="first_rack_xxsupplyType"
                                               value="${idcReProduct.validity}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="kv-label" style="width: 120px;">申请人</td>
                                    <td class="kv-content">
                                        <input class="easyui-textbox"
                                               data-options=" width:160" readonly="readonly"
                                                id="first_rxxacxxk_spec" name="applyerName" value="${idcReProduct.applyerName}"/>
                                    </td>
                                    <td class="kv-label" style="width: 120px;">申请人角色</td>
                                    <td class="kv-content">
                                        <span style="position:relative;" class="tipCls" ></span>
                                        <input class="easyui-textbox" data-options="width:160" value="${idcReProduct.applyerRoles}"
                                               readonly="readonly" id="first_racxxxxxxk_rackNum" name="applyerRoles" data-optionsstyle="width:150px;"></input>
                                    </td>
                                    <td class="kv-label" style="width: 120px;">联系电话</td>
                                    <td class="kv-content">
                                        <input class="easyui-textbox" readonly="readonly"
                                               data-options=" editable:false,width:160 "
                                               id="first_rack_xxxxsupplyType" name="applyerPhone"
                                               value="${idcReProduct.applyerPhone}" />
                                    </td>
                                </tr>
                                <c:choose>
                                    <c:when test="${processdefinitonkey=='idc_ticket_temporary'}">
                                        <tr>
                                            <td class="kv-label" style="width: 120px;">申请人证件类型</td>
                                            <td class="kv-content">
                                                <input class="easyui-combobox" readonly="readonly" data-options="
                                                    valueField: 'value',
                                                    textField: 'label',
                                                    editable:false,
                                                    width:160,
                                                    data: [{
                                                        label: '工商营业执照',
                                                        value: '1'
                                                    },{
                                                        label: '身份证',
                                                        value: '2'
                                                    },{
                                                        label: '组织机构代码证书',
                                                        value: '3'
                                                    },{
                                                        label: '事业法人证书',
                                                        value: '4'
                                                    },{
                                                        label: '军队代号',
                                                        value: '5'
                                                    },{
                                                        label: '社团法人证书',
                                                        value: '6'
                                                    },{
                                                        label: '护照',
                                                        value: '7'
                                                    },{
                                                        label: '军官证',
                                                        value: '8'
                                                    },{
                                                        label: '台胞证',
                                                        value: '9'
                                                    },{
                                                        label: '其他',
                                                        value: '999'
                                                    }]" name="zjlx" id="zjlx" value="${idcReCustomer.idcardtype}" />
                                            </td>
                                            <td class="kv-label" style="width: 120px;">申请人证件号</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox"
                                                       data-options=" width:160" readonly="readonly"
                                                       id="dfdfdf" name="applyerName" value="${idcReCustomer.idcardno}"/>
                                            </td>
                                            <td class="kv-label" style="width: 160px;">测试开始时间</td>
                                            <td class="kv-content">
                                                <input readonly="readonly" value="${idcReProduct.lscsStartTimeStr}" class="easyui-datetimebox" data-options="required:true,width:160,min:0">

                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label" style="width: 160px;">测试结束时间</td>
                                            <td class="kv-content">
                                                <input readonly="readonly" value="${idcReProduct.lscsEntTimeStr}" class="easyui-datetimebox" data-options="required:true,width:160,min:0">

                                            </td>
                                        </tr>
                                    </c:when>
                                </c:choose>
                            </table>
                        </fieldset>


                    <c:choose>
                        <c:when test="${processdefinitonkey=='idc_ticket_temporary'}">















                        </c:when>
                    </c:choose>













                    <c:choose>
                        <c:when test="${processdefinitonkey=='idc_ticket_temporary'}">
                            <div id="first_port_fieldset_content_Id"></div>
                            <!-- 机架机位信息 -->
                            <fieldset class="fieldsetCls fieldsetHeadCls" id="first_port_fieldsetId">
                                <legend>&diams;<span style="color: #00B4E1">服务项一：端口带宽</span></legend>
                                <table class="kv-table">
                                    <tr>
                                        <td class="kv-label" style="width: 120px;">端口带宽占用方式</td>
                                        <td class="kv-content">
                                            <input class="easyui-combobox" data-options="
                                                      valueField: 'value',onChange:firstCheckField,
                                                       textField: 'label',
                                                       editable:false,width:160,
                                                        url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/300'"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   name="idcRePortModel.portMode"
                                                   id="first_port_portMode" value="${idcRePortModel.portMode}">
                                        </td>
                                        <td class="kv-label" style="width: 120px;">带宽总需求(兆)</td>
                                        <td class="kv-content">
                                            <span style="position:relative;" class="tipCls" ></span>
                                            <input class="easyui-numberspinner first_numedit" data-options="width:120"
                                                   value="${idcRePortModel.bandwidth}"
                                                   name="idcRePortModel.bandwidth"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   id="first_port_bandwidth" data-optionsstyle="width:150px;"></input>
                                            <span style="color: red" id="first_rack_bandwidth_x"></span>
                                        </td>
                                        <td class="kv-label" style="width: 120px;">端口数量(个)</td>
                                        <td class="kv-content">
                                            <span style="position:relative;" class="tipCls" ></span>
                                            <input class="easyui-numberspinner first_numedit"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   id="first_port_num"
                                                   name="idcRePortModel.num"
                                                   value="${idcRePortModel.num}" style="width:120px;" ></input>
                                            <span style="color: red" id="first_port_num_x"></span>
                                    </tr>
                                    <tr>
                                        <td class="kv-label" style="width: 120px;">描述</td>
                                        <td class="kv-content" colspan="5">
                                            <input class="easyui-textbox" id="first_port_desc" value="${idcRePortModel.desc}"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   name="idcRePortModel.desc"
                                                   data-options="onChange:firstCheckField,multiline:true,height:70,width:525"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <!-- IP租用 -->
                            <div id="first_ip_fieldset_content_Id"></div>
                            <!-- 机架机位信息 -->
                            <fieldset class="fieldsetCls fieldsetHeadCls"  id="first_ip_fieldsetId">
                                <legend>&diams;<span style="color: #00B4E1">服务项二：IP租用</span></legend>
                                <table class="kv-table">
                                    <tr>
                                        <td class="kv-label" style="width: 85px;">IP性质</td>
                                        <td class="kv-content" >
                                            <input class="easyui-combobox" data-options="
                                               valueField: 'value',
                                                   textField: 'label',
                                                   editable:false,onChange:firstCheckField,
                                                   width:160,
                                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/400'
                                                   " id="first_ipip_portMode"  name="idcReIpModel.portMode"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   value="${idcReIpModel.portMode}" />
                                        </td>
                                        <td class="kv-label" style="width: 120px;">IP数量(个)</td>
                                        <td class="kv-content" colspan="8">
                                            <span style="position:relative;" class="tipCls" ></span>
                                            <input class="easyui-numberspinner first_numedit"
                                                   name="idcReIpModel.num"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   id="first_ipip_num" value="${idcReIpModel.num}" style="width:120px;" ></input>
                                            <span style="color: red" id="first_ipip_num_x"></span>
                                        </td>

                                    </tr>
                                    <tr>
                                        <td class="kv-label" style="width: 85px">描述</td>
                                        <td class="kv-content" colspan="4">
                                            <input  class="easyui-textbox"
                                                    value="${idcReIpModel.desc}" id="first_ipip_desc"
                                                    name="idcReIpModel.desc"
                                                    <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                    data-options="onChange:firstCheckField,multiline:true,height:70,width:525"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <!-- 主机租赁 -->
                            <div id="first_server_fieldset_content_Id"></div>
                            <!-- 机架机位信息 -->
                            <fieldset class="fieldsetCls fieldsetHeadCls"  id="first_server_fieldsetId">
                                <legend>&diams;<span style="color: #00B4E1">服务项三：测试主机</span></legend>
                                <table class="kv-table">
                                        <tr>
                                            <td class="kv-label" style="width: 120px;">测试机来源</td>
                                            <td class="kv-content">
                                                <input class="easyui-combobox" data-options="
                                                   valueField: 'value',
                                                       textField: 'label',
                                                       editable:false,
                                                       width:150,
                                                       data: [{
                                                       label: '客户自带主机',
                                                       value: '1'
                                                   },{
                                                       label: 'IDC提供主机',
                                                       value: '2'
                                                   }]"
                                                           name="idcReServerModel.source"
                                            readonly="readonly"
                                           value="${idcReServerModel.source}"
                                           id="idcReServerModel_source" />
                                            </td>
                                            <td class="kv-label" style="width: 120px;">CPU</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="onChange:updateTestJbpmDemand,width:150" placeholder="请输入CPU"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                       name="idcReServerModel.CPU"
                                                       value="${idcReServerModel.CPU}"
                                                       id="idcReServerModel_CPU"/>

                                            </td>
                                            <td class="kv-label" style="width: 120px;">MEM</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox"
                                                       <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                       name="idcReServerModel.MEM"
                                                       value="${idcReServerModel.MEM}"
                                                       id="idcReServerModel_MEM"
                                                       data-options="onChange:updateTestJbpmDemand,precision:0,width:150,min:0,validType:'length[0,300]'"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label" style="width: 120px;">存储信息</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="onChange:updateTestJbpmDemand,width:150" placeholder="存储信息"
                                                       <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                       value="${idcReServerModel.memory}"
                                                       name="idcReServerModel.memory" id="idcReServerModel_memory"/>

                                            </td>
                                            <td class="kv-label" style="width: 120px;">操作系统</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="onChange:updateTestJbpmDemand,width:150" placeholder="操作系统"
                                                       <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                       value="${idcReServerModel.OS}"
                                                       name="idcReServerModel.OS" id="idcReServerModel_OS"/>

                                            </td>
                                            <td class="kv-label" style="width: 120px;">资源类别</td>
                                            <td class="kv-content">
                                                <input class="easyui-combobox" data-options="
                                                    onChange:updateTestJbpmDemand,
                                                       valueField: 'value',
                                                           textField: 'label',
                                                           editable:false,
                                                           width:150,
                                                           data: [{
                                                           label: '虚拟机',
                                                           value: '1'
                                                       },{
                                                           label: '物理机',
                                                           value: '2'
                                                       },{
                                                           label: '其他',
                                                           value: '3'
                                                       }]" name="idcReServerModel.typeMode"
                                                       <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                       value="${idcReServerModel.typeMode}"
                                                       id="idcReServerModel_typeMode" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label" style="width: 120px;">需要U位数量（U）</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="onChange:updateTestJbpmDemand,width:150" placeholder="需要U位数量"
                                                       <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                       value="${idcReServerModel.rackUnit}"
                                                       name="idcReServerModel.rackUnit" id="idcReServerModel_rackUnit"/>
                                            </td>
                                            <td class="kv-label" style="width: 120px;">主机数量</td>
                                            <td class="kv-content">
                                                <input class="easyui-numberspinner" data-options="onChange:updateTestJbpmDemand,width:150" placeholder="主机数量"
                                                       <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                       value="${idcReServerModel.specNumber}"
                                                       name="idcReServerModel.specNumber" id="idcReServerModel_specNumber"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label" style="width: 120px;">描述</td>
                                            <td class="kv-content" colspan="5">
                                                <input class="easyui-textbox"
                                                       name="idcReServerModel.desc" id="idcReServerModel_desc"
                                                       <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                       value="${idcReServerModel.desc}"
                                                       data-options="onChange:updateTestJbpmDemand,multiline:true,height:70,width:525"/>
                                            </td>
                                        </tr>
                                </table>
                            </fieldset>
                        </c:when>
                        <c:otherwise>
                            <fieldset class="fieldsetCls fieldsetHeadCls" id="first_rack_fieldsetId">
                                <legend>&diams;<span style="color: #00B4E1">服务项一：机架机位</span></legend>
                                <table class="kv-table">
                                    <tr>
                                        <td class="kv-label" style="width: 120px;">规格</td>
                                        <td class="kv-content">
                                            <input class="easyui-combobox"
                                                   name="idcReRackModel.spec"
                                                   data-options="valueField: 'value',
                                                   textField: 'label',width:160,onChange:firstCheckField,
                                                   editable:false,
                                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/100' "
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   id="first_rack_spec" value="${idcReRackModel.spec}"/>
                                        </td>
                                        <td class="kv-label" style="width: 120px;">机架(个)</td>
                                        <td class="kv-content">
                                            <span style="position:relative;" class="tipCls" ></span>
                                            <input class="easyui-numberspinner first_numedit" data-options="width:120" value="${idcReRackModel.rackNum}"
                                                   name="idcReRackModel.rackNum"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   id="first_rack_rackNum" data-optionsstyle="width:150px;"></input>
                                            <span style="color: red" id="first_rack_rackNum_x"></span>
                                        </td>
                                        <td class="kv-label" style="width: 120px;">机位数(U)</td>
                                        <td class="kv-content">
                                            <span style="position:relative;" class="tipCls" ></span>
                                            <input class="easyui-numberspinner first_numedit" data-options="width:120" value="${idcReRackModel.seatNum}"
                                                   name="idcReRackModel.seatNum"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   id="first_rack_seatNum" data-optionsstyle="width:150px;"></input>
                                            <span style="color: red" id="first_rack_seatNum_x"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="kv-label" style="width: 120px;">供电类型</td>
                                        <td class="kv-content">
                                            <input class="easyui-combobox"
                                                   data-options="valueField: 'value', textField: 'label', editable:false,width:160,onChange:firstCheckField,
                                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/200' "
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   name="idcReRackModel.supplyType"  id="first_rack_supplyType"
                                                   value="${idcReRackModel.supplyType}" />
                                        </td>
                                        <td class="kv-label" style="width: 120px;">描述</td>
                                        <td class="kv-content" colspan="5">
                                            <input class="easyui-textbox" value="${idcReRackModel.desc}" id="first_rack_desc"
                                                   name="idcReRackModel.desc"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   data-options="onChange:firstCheckField,multiline:true,height:70,width:525" />
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <div id="first_port_fieldset_content_Id"></div>
                            <!-- 机架机位信息 -->
                            <fieldset class="fieldsetCls fieldsetHeadCls" id="first_port_fieldsetId">
                                <legend>&diams;<span style="color: #00B4E1">服务项二：端口带宽</span></legend>
                                <table class="kv-table">
                                    <tr>
                                        <td class="kv-label" style="width: 120px;">端口带宽占用方式</td>
                                        <td class="kv-content">
                                            <input class="easyui-combobox" data-options="
                                                      valueField: 'value',onChange:firstCheckField,
                                                       textField: 'label',
                                                       editable:false,width:160,
                                                        url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/300'"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   name="idcRePortModel.portMode"
                                                   id="first_port_portMode" value="${idcRePortModel.portMode}">
                                        </td>
                                        <td class="kv-label" style="width: 120px;">带宽总需求(兆)</td>
                                        <td class="kv-content">
                                            <span style="position:relative;" class="tipCls" ></span>
                                            <input class="easyui-numberspinner first_numedit" data-options="width:120"
                                                   value="${idcRePortModel.bandwidth}"
                                                   name="idcRePortModel.bandwidth"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   id="first_port_bandwidth" data-optionsstyle="width:150px;"></input>
                                            <span style="color: red" id="first_rack_bandwidth_x"></span>
                                        </td>
                                        <td class="kv-label" style="width: 120px;">端口数量(个)</td>
                                        <td class="kv-content">
                                            <span style="position:relative;" class="tipCls" ></span>
                                            <input class="easyui-numberspinner first_numedit"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   id="first_port_num"
                                                   name="idcRePortModel.num"
                                                   value="${idcRePortModel.num}" style="width:120px;" ></input>
                                            <span style="color: red" id="first_port_num_x"></span>
                                    </tr>
                                    <tr>
                                        <td class="kv-label" style="width: 120px;">描述</td>
                                        <td class="kv-content" colspan="5">
                                            <input class="easyui-textbox" id="first_port_desc" value="${idcRePortModel.desc}"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   name="idcRePortModel.desc"
                                                   data-options="onChange:firstCheckField,multiline:true,height:70,width:525"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <!-- IP租用 -->
                            <div id="first_ip_fieldset_content_Id"></div>
                            <!-- 机架机位信息 -->
                            <fieldset class="fieldsetCls fieldsetHeadCls"  id="first_ip_fieldsetId">
                                <legend>&diams;<span style="color: #00B4E1">服务项三：IP租用</span></legend>
                                <table class="kv-table">
                                    <tr>
                                        <td class="kv-label" style="width: 85px;">IP性质</td>
                                        <td class="kv-content" >
                                            <input class="easyui-combobox" data-options="
                                               valueField: 'value',
                                                   textField: 'label',
                                                   editable:false,onChange:firstCheckField,
                                                   width:160,
                                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/400'
                                                   " id="first_ipip_portMode"  name="idcReIpModel.portMode"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   value="${idcReIpModel.portMode}" />
                                        </td>
                                        <td class="kv-label" style="width: 120px;">IP数量(个)</td>
                                        <td class="kv-content" colspan="8">
                                            <span style="position:relative;" class="tipCls" ></span>
                                            <input class="easyui-numberspinner first_numedit"
                                                   name="idcReIpModel.num"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   id="first_ipip_num" value="${idcReIpModel.num}" style="width:120px;" ></input>
                                            <span style="color: red" id="first_ipip_num_x"></span>
                                        </td>

                                    </tr>
                                    <tr>
                                        <td class="kv-label" style="width: 85px">描述</td>
                                        <td class="kv-content" colspan="4">
                                            <input  class="easyui-textbox"
                                                    value="${idcReIpModel.desc}" id="first_ipip_desc"
                                                    name="idcReIpModel.desc"
                                                    <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                    data-options="onChange:firstCheckField,multiline:true,height:70,width:525"/>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <!-- 主机租赁 -->
                            <div id="first_server_fieldset_content_Id"></div>
                            <!-- 机架机位信息 -->
                            <fieldset class="fieldsetCls fieldsetHeadCls"  id="first_server_fieldsetId">
                                <legend>&diams;<span style="color: #00B4E1">服务项四：主机租赁</span></legend>
                                <table class="kv-table">
                                    <tr>
                                        <td class="kv-label" style="width: 120px;">资源类型</td>
                                        <td class="kv-content">
                                            <input class="easyui-combobox" data-options="
                                                   valueField: 'value',
                                                       textField: 'label',
                                                       editable:false,onChange:firstCheckField,
                                                       width:160,
                                                       data: [{
                                                                   label: '没有数据1',
                                                                   value: '1'
                                                               },{
                                                                   label: '没有数据2',
                                                                   value: '2'
                                                               }]"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   name="idcReServerModel.typeMode"  id="first_serv_typeMode" value="${idcReServerModel.typeMode}" />
                                        </td>
                                        <td class="kv-label" style="width: 120px;">设备型号</td>
                                        <td class="kv-content">
                                            <input class="easyui-textbox" data-options="onChange:firstCheckField,width:150"
                                                   placeholder="请输入设备型号"  name="idcReServerModel.specNumber"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   value="${idcReServerModel.specNumber}"  id="first_serv_specNumber"/>

                                        </td>
                                        <td class="kv-label" style="width: 120px;">主机数量(个)</td>
                                        <td class="kv-content">
                                            <span style="position:relative;" class="tipCls" ></span>
                                            <input class="easyui-numberspinner first_numedit"
                                                   name="idcReServerModel.num"  id="first_serv_num" value="${idcReServerModel.num}"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   data-options="onChange:firstCheckField,width:150" ></input>
                                            <span style="color: red" id="first_serv_num_x"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="kv-label" style="width: 120px;">描述</td>
                                        <td class="kv-content" colspan="5">
                                            <input class="easyui-textbox" value="${idcReServerModel.desc}"
                                                   name="idcReServerModel.desc" id="first_serv_desc"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   data-options="onChange:firstCheckField,multiline:true,height:70,width:525"/>
                                        </td>

                                    </tr>
                                </table>
                            </fieldset>
                            <div id="first_add_fieldset_content_Id"></div>
                            <!-- 机架机位信息 -->
                            <fieldset class="fieldsetCls fieldsetHeadCls" id="first_add_fieldsetId">
                                <legend>&diams;<span style="color: #00B4E1">服务项五：业务增值</span></legend>
                                <table class="kv-table">
                                    <tr>
                                        <td class="kv-label" style="width: 50px;">名称</td>
                                        <td class="kv-content">
                                            <input class="easyui-textbox" data-options="onChange:firstCheckField,width:150"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   name="idcReNewlyModel.name"  id="first_newl_name"  value="${idcReNewlyModel.name}" />
                                        </td>
                                        <td class="kv-label" style="width: 87px;">业务分类</td>
                                        <td class="kv-content" colspan="4">
                                            <input class="easyui-combobox" data-options="
                                                       valueField: 'value',onChange:firstCheckField,
                                                           textField: 'label',width:160,
                                                           editable:false,
                                                           url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/800'"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   name="idcReNewlyModel.category"  id="first_newl_category" value="${idcReNewlyModel.category}" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="kv-label" style="width: 87px;">描述</td>
                                        <td class="kv-content" colspan="4">
                                            <input class="easyui-textbox" value="${idcReNewlyModel.desc}" id="first_newl_desc"
                                                   <c:if test="${not empty firstDemand}">readonly="readonly"</c:if>
                                                   name="idcReNewlyModel.desc"  data-options="onChange:firstCheckField,multiline:true,height:70,width:525"/>
                                        </td>
                                    </tr>

                                </table>
                            </fieldset>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>

            <c:choose>
                <c:when test="${processdefinitonkey=='idc_ticket_temporary'}">
                    <div title="测试服务信息" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
                        <c:choose>
                            <c:when test="${idcNetServiceinfo.domainStatus== 1}">
                                <fieldset class="fieldsetCls fieldsetHeadCls">
                                    <legend>&diams;服务信息</legend>
                                    <table class="kv-table">
                                        <tr>
                                            <td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span></td>
                                            <td class="kv-content">
                                                <%--<input type="hidden" name="idcNetServiceinfo.id" value="${idcNetServiceinfo.id}"/>--%>
                                                <input type="hidden" name="idcNetServiceinfo.domainStatus" value="${idcNetServiceinfo.domainStatus}" id="idcNetServiceinfo.domainStatus"/>
                                                <input name="ins_ticketInstId" type="hidden" value="${ticketInstId}">
                                                <input class="easyui-textbox" name="idcNetServiceinfo.icpsrvname" value="${idcNetServiceinfo.icpsrvname}"  id="idcNetServiceinfo.icpsrvname" data-options="required:true,validType:'length[0,64]',width:200"/>
                                            </td>
                                            <td class="kv-label">所属用户</td>
                                            <td class="kv-content">
                                                <input name="ins_customerId" value="${idcReCustomer.id}" type="hidden" />
                                                <input class="easyui-textbox" readonly="readonly" name="ins_customerName" value="${idcReCustomer.name}"  id="ins_customerName" data-options="required:true,validType:'length[0,128]',width:200"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label">服务内容<span style="color:red">&nbsp;*</span></td>
                                            <td class="kv-content">
                                                <%--<div>  <ul id="ttFWNR" checkbox="true" data-options="lines:true,width:200" readonly="readonly" value="${idcNetServiceinfo.icpsrvcontentcode}"  name="idcNetServiceinfo.icpsrvcontentcode" id="idcNetServiceinfo.icpsrvcontentcode"></ul>  </div>--%>


                                                <input class="easyui-combotree" readonly="readonly"
                                                       data-options=" valueField: 'value',textField: 'label',editable:false,width:200,url:'<%=request.getContextPath() %>/assetBaseinfo/checkboxOpenMSG/9999'"
                                                       name="idcNetServiceinfo.icpsrvcontentcode"  value="${idcNetServiceinfo.icpsrvcontentcode}" />

                                            </td>
                                            <td class="kv-label">备案类型<span style="color:red">&nbsp;*</span></td>
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
                                                            }]" value="${idcNetServiceinfo.icprecordtype}"  name="idcNetServiceinfo.icprecordtype" id="idcNetServiceinfo.icprecordtype"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label">备案号[许可证号]<span style="color:red">&nbsp;*</span></td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" readonly="readonly" name="idcNetServiceinfo.icprecordno" value="${idcNetServiceinfo.icprecordno}"  id="idcNetServiceinfo.icprecordno" data-options="required:true,validType:'length[0,64]',width:200"/>
                                            </td>
                                            <td class="kv-label">接入方式<span style="color:red">&nbsp;*</span></td>
                                            <td class="kv-content">
                                                <input class="easyui-combobox" readonly="readonly"  data-options="
                                                            valueField: 'value',
                                                            textField: 'label',
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
                                                            }]" value="${idcNetServiceinfo.icpaccesstype}"  name="idcNetServiceinfo.icpaccesstype" id="idcNetServiceinfo.icpaccesstype"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label">域名信息<span style="color:red">&nbsp;*</span></td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" readonly="readonly" name="idcNetServiceinfo.icpdomain" value="${idcNetServiceinfo.icpdomain}"  id="idcNetServiceinfo.icpdomain" data-options="required:true,validType:'length[0,4000]',width:200"/>
                                            </td>
                                            <td class="kv-label">业务类型<span style="color:red">&nbsp;*</span></td>
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
                                                            }]" value="${idcNetServiceinfo.icpbusinesstype}"  name="idcNetServiceinfo.icpbusinesstype" id="idcNetServiceinfo.icpbusinesstype"/>
                                            </td>
                                        </tr>
                                    </table>
                                </fieldset>

                                <c:if test="${not empty idcNetServiceinfo.vmName}">
                                    <fieldset class="fieldsetCls fieldsetHeadCls">
                                        <legend>&diams;虚拟机信息</legend>
                                        <table class="kv-table">
                                            <tr>
                                                <td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span>
                                                </td>
                                                <td class="kv-content">
                                                    <input class="easyui-textbox" readonly="readonly" name="idcNetServiceinfo.vmName"
                                                           value="${idcNetServiceinfo.vmName}"
                                                           id="idcNetServiceinfo.vmName"
                                                           data-options="required:true,validType:'length[0,4000]',width:200"/>
                                                </td>
                                                <td class="kv-label">管理地址<span style="color:red">&nbsp;*</span></td>
                                                <td class="kv-content">
                                                    <input class="easyui-textbox" readonly="readonly"  name="idcNetServiceinfo.vmManagerAddr"
                                                           value="${idcNetServiceinfo.vmManagerAddr}"
                                                           id="idcNetServiceinfo.vmManagerAddr"
                                                           data-options="required:true,validType:'length[0,4000]',width:200"/>
                                                </td>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="kv-label">类型<span style="color:red">&nbsp;*</span></td>
                                                <td class="kv-content">
                                                    <input class="easyui-combobox" readonly="readonly"  data-options="
                                                          valueField: 'value',
                                                              textField: 'label',
                                                              width:200,
                                                              required:true,
                                                              editable:false,
                                                              data: [{
                                                              label:'共享式',
                                                               value: '1'
                                                           },{
                                                               label: '专用式',
                                                               value: '2'
                                                           },{
                                                              label: '云虚拟',
                                                               value: '3'
                                                          }]" value="${idcNetServiceinfo.vmCategory}" name="idcNetServiceinfo.vmCategory"
                                                       id="idcNetServiceinfo.vmCategory"/>

                                                </td>
                                                <td class="kv-label">网络地址<span style="color:red">&nbsp;*</span></td>
                                                <td class="kv-content">
                                                    <!-- idcNetServiceinfo.vmNetAddr -->
                                                    <input class="easyui-textbox" readonly="readonly"  name="idcNetServiceinfo.vmNetAddr"
                                                           value="${idcNetServiceinfo.vmNetAddr}"
                                                           id="idcNetServiceinfo.vmNetAddr"
                                                           data-options="required:true,validType:'length[0,4000]',width:200"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </fieldset>

                                </c:if>
                            </c:when>
                            <c:when test="${idcNetServiceinfo.domainStatus== 0}">
                                <fieldset class="fieldsetCls fieldsetHeadCls">
                                    <legend>&diams;服务信息</legend>
                                    <table class="kv-table">
                                        <tr>
                                            <td class="kv-label" style="width:215px">服务开通时间</td>
                                            <td class="kv-content" colspan="3">
                                                <input class="easyui-datetimebox" readonly="readonly"
                                                       name="idcNetServiceinfo.openTime"
                                                       value="${idcNetServiceinfo.openTimeStr}"
                                                       data-options="required:true,editable:false,showSeconds:true,width:200">
                                            </td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </c:when>
                        </c:choose>
                    </div>

            </c:when>
        </c:choose>



    <c:if test="${ticketCategoryTo > 200}">
        <div title="客户变更需求" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
            <div  class="easyui-layout" fit="true">
                <div data-options="region:'north',title:'历史变更信息...'" style="height:200px;">
                    <table class="easyui-datagrid" id="history_demand" data-options="
                                url:'<%=request.getContextPath() %>/actJBPMController/historyDemand.do',
                                queryParams:{'ticketInstId':${ticketInstId},'prodInstId':${prodInstId}},
                                iconCls:'',border:false,autoRowHeight:true,title:'',singleSelect:true,
                                striped:true,rownumbers:true,fitColumns:true" fit="false" style="height:170px;width: 100%">
                        <thead>
                            <tr>
                                <th data-options="field:'SERIAL_NUMBER',width:120,halign:'left' ">工单号</th>
                                <th data-options="field:'TICKET_INST_ID',width:48,halign:'left' ">工单ID</th>
                                <%--<th data-options="field:'RACK_SPEC',width:80,halign:'left' ">机架规格</th>--%>
                                <th data-options="field:'OTHER_MSG',width:88,halign:'left' ">机位变更个数（U）</th>
                                <th data-options="field:'RACK_NUM',width:88,halign:'left' ">机架变更个数(个)</th>
                                <th data-options="field:'RACK_SUPPLYTYPE',width:65,halign:'left' ">供电类型</th>
                                <%--<th data-options="field:'RACK_DESC',width:100,halign:'left' ">机架描述</th>--%>
                                <%--<th data-options="field:'PORT_PORTMODE',width:100,halign:'left' ">端口带宽占用方式</th>
                                <th data-options="field:'PORT_BANDWIDTH',width:100,halign:'left' ">端口带宽总需求</th>--%>
                                <th data-options="field:'PORT_NUM',width:88,halign:'left' ">端口带宽变更个数（个）</th>
                                <%--<th data-options="field:'PORT_DESC',width:100,halign:'left' ">端口描述</th>--%>
                                <th data-options="field:'IP_PORTMODE',width:65,halign:'left' ">IP性质</th>
                                <th data-options="field:'IP_NUM',width:88,halign:'left' ">IP变更个数（个）</th>
                                <%--<th data-options="field:'IP_DESC',width:65,halign:'left' ">IP描述</th>
                                <th data-options="field:'SERVER_TYPEMODE',width:65,halign:'left' ">主机资源类型</th>
                                <th data-options="field:'SERVER_SPECNUMBER',width:65,halign:'left' ">主机设备型号</th>--%>
                                <th data-options="field:'SERVER_NUM',width:88,halign:'left' ">主机变更个数（个)</th>
                                <%--<th data-options="field:'SERVER_DESC',width:100,halign:'left' ">主机描述</th>
                                <th data-options="field:'NEWLY_NAME',width:100,halign:'left' ">业务增值名称</th>
                                <th data-options="field:'NEWLY_CATEGORY',width:100,halign:'left' ">业务增值业务分类</th>
                                <th data-options="field:'NEWLY_DESC',width:100,halign:'left' ">业务增值描述</th>--%>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div data-options="region:'center',title:''" style="padding:5px;">
                    <!-- 机架机位信息 -->
                    <fieldset class="fieldsetCls fieldsetHeadCls" id="rack_fieldsetId">
                        <legend>&diams;<span style="color: #dd1144">变更服务项一：机架机位</span></legend>
                        <table class="kv-table">
                            <tr>
                                <td class="kv-label" style="width: 120px;">规格</td>
                                <td class="kv-content">
                                    <input class="easyui-combobox"
                                           data-options="valueField: 'value',
                                       textField: 'label',width:160,onChange:checkField,
                                       editable:false,
                                       url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/100' "
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if> id="rack_Spec" value="${idcTicketDemand.rack_Spec}"/>
                                </td>
                                <td class="kv-label" style="width: 120px;">机架(个)</td>
                                <td class="kv-content">
                                    <span style="position:relative;" class="tipCls" ></span>
                                    <input class="easyui-numberspinner numedit" data-options="width:120" value="${idcTicketDemand.rack_Num}"
                                           id="rack_Num" <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if> data-optionsstyle="width:150px;"></input>
                                    <span style="color: red" id="rack_Num_x"></span>
                                </td>
                                <td class="kv-label" style="width: 120px;">机位数(U)</td>
                                <td class="kv-content">
                                    <span style="position:relative;" class="tipCls" ></span>
                                    <input class="easyui-numberspinner numedit" data-options="width:120" value="${idcTicketDemand.other_Msg}"
                                           id="rack_Other" <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if> data-optionsstyle="width:150px;"></input>
                                    <span style="color: red" id="rack_Other_x"></span>
                                </td>
                            </tr>
                            <tr>
                                <td class="kv-label" style="width: 120px;">供电类型</td>
                                <td class="kv-content">
                                    <input class="easyui-combobox"
                                           data-options="valueField: 'value', textField: 'label', editable:false,width:160,onChange:checkField,
                                       url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/200' "
                                           id="rack_supplyType" <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           value="${idcTicketDemand.rack_supplyType}" />
                                </td>
                                <td class="kv-label" style="width: 120px;">描述</td>
                                <td class="kv-content" colspan="5">
                                    <input class="easyui-textbox" value="${idcTicketDemand.rack_desc}" id="rack_desc"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if> data-options="onChange:checkField,multiline:true,height:70,width:525" />
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <div id="port_fieldset_content_Id"></div>
                    <!-- 机架机位信息 -->
                    <fieldset class="fieldsetCls fieldsetHeadCls" id="port_fieldsetId">
                        <legend>&diams;<span style="color: #dd1144">变更服务项二：端口带宽</span></legend>
                        <table class="kv-table">
                            <tr>
                                <td class="kv-label" style="width: 120px;">端口带宽占用方式</td>
                                <td class="kv-content">
                                    <input class="easyui-combobox" data-options="
                                          valueField: 'value',onChange:checkField,
                                           textField: 'label',
                                           editable:false,width:160,
                                            url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/300'"
                                           id="port_portMode" value="${idcTicketDemand.port_portMode}" <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if> />
                                </td>
                                <td class="kv-label" style="width: 120px;">带宽变更大小(兆)</td>
                                <td class="kv-content">
                                    <span style="position:relative;" class="tipCls" ></span>
                                    <input class="easyui-numberspinner numedit" data-options="width:120"
                                           value="${idcTicketDemand.port_bandwidth}"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           id="port_bandwidth" data-optionsstyle="width:150px;"></input>
                                    <span style="color: red" id="port_bandwidth_x"></span>
                                </td>
                                <td class="kv-label" style="width: 120px;">端口数量(个)</td>
                                <td class="kv-content">
                                    <span style="position:relative;" class="tipCls" ></span>
                                    <input class="easyui-numberspinner numedit"
                                           id="port_num"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           value="${idcTicketDemand.port_num}" style="width:120px;" ></input>
                                    <span style="color: red" id="port_num_x"></span>
                            </tr>
                            <tr>
                                <td class="kv-label" style="width: 120px;">描述</td>
                                <td class="kv-content" colspan="5">
                                    <input class="easyui-textbox" id="port_desc" value="${idcTicketDemand.port_desc}"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           data-options="onChange:checkField,multiline:true,height:70,width:525"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <!-- IP租用 -->
                    <div id="ip_fieldset_content_Id"></div>
                    <!-- 机架机位信息 -->
                    <fieldset class="fieldsetCls fieldsetHeadCls"  id="ip_fieldsetId">
                        <legend>&diams;<span style="color: #dd1144">变更服务项三：IP租用</span></legend>
                        <table class="kv-table">
                            <tr>
                                <td class="kv-label" style="width: 85px;">IP性质</td>
                                <td class="kv-content" >
                                    <input class="easyui-combobox" data-options="
                                   valueField: 'value',
                                       textField: 'label',
                                       editable:false,onChange:checkField,
                                       width:160,
                                       url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/400'
                                       " id="ip_portMode"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           value="${idcTicketDemand.ip_portMode}" />
                                </td>
                                <td class="kv-label" style="width: 120px;">IP数量(个)</td>
                                <td class="kv-content" colspan="8">
                                    <span style="position:relative;" class="tipCls" ></span>
                                    <input class="easyui-numberspinner numedit"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           id="ip_num" value="${idcTicketDemand.ip_num}" style="width:120px;" ></input>
                                    <span style="color: red" id="ip_num_x"></span>
                                </td>

                            </tr>
                            <tr>
                                <td class="kv-label" style="width: 85px">描述</td>
                                <td class="kv-content" colspan="4">
                                    <input  class="easyui-textbox"
                                            <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                            value="${idcTicketDemand.ip_desc}" id="ip_desc"
                                            data-options="onChange:checkField,multiline:true,height:70,width:525"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <!-- 主机租赁 -->
                    <div id="server_fieldset_content_Id"></div>
                    <!-- 机架机位信息 -->
                    <fieldset class="fieldsetCls fieldsetHeadCls"  id="server_fieldsetId">
                        <legend>&diams;<span style="color: #dd1144">变更服务项四：主机租赁</span></legend>
                        <table class="kv-table">
                            <tr>
                                <td class="kv-label" style="width: 120px;">资源类型</td>
                                <td class="kv-content">
                                    <input class="easyui-combobox" data-options="
                                       valueField: 'value',
                                           textField: 'label',
                                           editable:false,onChange:checkField,
                                           width:160,
                                           data: [{
                                                   label: '没有数据1',
                                                   value: '1'
                                               },{
                                                   label: '没有数据2',
                                                   value: '2'
                                               }]"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           id="server_typeMode" value="${idcTicketDemand.server_typeMode}" />
                                </td>
                                <td class="kv-label" style="width: 120px;">设备型号</td>
                                <td class="kv-content">
                                    <input class="easyui-textbox" data-options="onChange:checkField,width:150"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           placeholder="请输入设备型号"
                                           value="${idcTicketDemand.server_specNumber}"  id="server_specNumber"/>

                                </td>
                                <td class="kv-label" style="width: 120px;">主机数量(个)</td>
                                <td class="kv-content">
                                    <span style="position:relative;" class="tipCls" ></span>
                                    <input class="easyui-numberspinner numedit"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           id="server_num" value="${idcTicketDemand.server_num}"
                                           data-options="onChange:checkField,width:150" ></input>
                                    <span style="color: red" id="server_num_x"></span>
                                </td>
                            </tr>
                            <tr>
                                <td class="kv-label" style="width: 120px;">描述</td>
                                <td class="kv-content" colspan="5">
                                    <input class="easyui-textbox" value="${idcTicketDemand.server_desc}"
                                           name="idcReServerModel.desc" id="server_desc"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           data-options="onChange:checkField,multiline:true,height:70,width:525"/>
                                </td>

                            </tr>
                        </table>
                    </fieldset>
                    <div id="add_fieldset_content_Id"></div>
                    <!-- 机架机位信息 -->
                    <fieldset class="fieldsetCls fieldsetHeadCls" id="add_fieldsetId">
                        <legend>&diams;<span style="color: #dd1144">变更服务项五：业务增值</span></legend>
                        <table class="kv-table">
                            <tr>
                                <td class="kv-label" style="width: 50px;">名称</td>
                                <td class="kv-content">
                                    <input class="easyui-textbox" data-options="onChange:checkField,width:150"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           id="newly_name"  value="${idcTicketDemand.newly_name}" />
                                </td>
                                <td class="kv-label" style="width: 87px;">业务分类</td>
                                <td class="kv-content" colspan="4">
                                    <input class="easyui-combobox" data-options="
                                           valueField: 'value',onChange:checkField,
                                               textField: 'label',width:160,
                                               editable:false,
                                               url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/800'"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           id="newly_category" value="${idcTicketDemand.newly_category}" />
                                </td>
                            </tr>
                            <tr>
                                <td class="kv-label" style="width: 87px;">描述</td>
                                <td class="kv-content" colspan="4">
                                    <input class="easyui-textbox" value="${idcTicketDemand.newly_desc}" id="newly_desc"
                                           <c:if test="${not empty demandReadOnly}">readonly="readonly"</c:if>
                                           data-options="onChange:checkField,multiline:true,height:70,width:525"/>
                                </td>
                            </tr>

                        </table>
                    </fieldset>
                    <div id="add_fieldset_content_Idxx"></div>
                    <!-- 机架机位信息 -->
                </div>
            </div>
        </div>

    </c:if>

    <jbpm:jbpmTag title="流程图" module="ACTIVITI_DIAGRAM" processInstanceId="${idcTicket.procInstId}" processDefinitionId="${idcTicket.procDefId}" processDefinitionKey="${idcTicket.processdefinitonkey}" ></jbpm:jbpmTag>

    <!-- 资源分配情况。formKey -->
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.resourceAllocationStatus}">
        <div title="资源分配" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
            <!-- 机架机位 start -->
            <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${productCategory.rack}">
                <div style="padding: 5px;" id="requestParamSettins_ticket_rack_gridId">
                    <%--机架名称: --%><input  type="hidden" class="easyui-textbox"  id="rackName_params" >
                    <!-- 选择机架 -->
                    <%--<a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('rack')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>--%>
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.ticketResourceHandStatus and !pageQueryDataStatus}">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('100','','rack','')">选择机架</a>
                        <span>||</span>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeAllResource('100','${idcTicket.ticketInstId}')">一键删除机架、机位、MCB</a>

                        <c:if test="${ticketCategoryFrom != 900}">  <%--如果当前流程不是业务变更流程--%>
                            <span style="color:red">申请机架/机位个数为>> ${idcReRackModelNum} 个</span>
                        </c:if>
                        <c:if test="${ticketCategoryFrom == 900}">  <%--如果当前流程不是业务变更流程--%>
                            <span style="color:red">变更个数为>> ${changeRackNum}  </span>
                        </c:if>

                    </jbpmSecurity:security>
                </div>
                <table class="easyui-datagrid" id="ticket_rack_gridId"></table>

                <div style="padding: 5px;" id="requestParamSettins_ticket_rackmcb_gridId">
                    <%--PDU:--%> <input type="hidden" class="easyui-textbox"  id="rackmcbName_params" >
                    <%--<a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('mcb')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>--%>
                </div>
                <table class="easyui-datagrid" id="ticket_rackmcb_gridId"></table>
            </jbpmSecurity:security>
            <!-- 机架机位 end -->
            <!-- 端口带宽 start -->
            <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${productCategory.port}">
                <div style="padding: 5px;" id="requestParamSettins_ticket_port_gridId">
                    <%--名称: --%><input  type="hidden" class="easyui-textbox"  id="portName_params" >
                    <%--<a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('port')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>--%>
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.ticketResourceHandStatus and !pageQueryDataStatus}">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('200','','port','')">选择带宽端口</a>
                        <span>||</span>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeAllResource('200','${idcTicket.ticketInstId}')">一键删除全部端口</a>
                        <c:if test="${ticketCategoryFrom != 900}">  <%--如果当前流程不是业务变更流程--%>
                            <span style="color:red">申请带宽端口个数为>> ${idcRePortModelNum} 个</span>
                        </c:if>
                        <c:if test="${ticketCategoryFrom == 900}">  <%--如果当前流程不是业务变更流程--%>
                            <span style="color:red">变更带宽端口个数为>> ${changePortNum} 个</span>
                        </c:if>
                    </jbpmSecurity:security>
                </div>
                <table class="easyui-datagrid" id="ticket_port_gridId"></table>
            </jbpmSecurity:security>
            <!-- 端口带宽 end -->
            <!-- ip租用 start -->
            <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${productCategory.ip}">
                <div style="padding: 5px;" id="requestParamSettins_ticket_ip_gridId">
                    <%--名称: --%><input type="hidden" class="easyui-textbox"  id="ipName_params" >
                    <%--<a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('ip')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>--%>
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.ticketResourceHandStatus and !pageQueryDataStatus}">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('300','','ip','')">选择IP</a>
                        <span>||</span>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeAllResource('300','${idcTicket.ticketInstId}')">一键删除全部IP</a>
                        <c:if test="${ticketCategoryFrom != 900}">  <%--如果当前流程不是业务变更流程--%>
                            <span style="color:red">申请IP个数为>> <c:out value="${idcReIpModelNum}"/>个</span>
                        </c:if>
                        <c:if test="${ticketCategoryFrom == 900}">  <%--如果当前流程不是业务变更流程--%>
                            <span style="color:red">变更IP个数为>> ${changeIPNum} 个</span>
                        </c:if>
                    </jbpmSecurity:security>
                </div>
                <table class="easyui-datagrid" id="ticket_ip_gridId"></table>
            </jbpmSecurity:security>
            <!-- ip租用 end -->
            <!-- 主机租赁 start -->
            <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${productCategory.server}">
                <div style="padding: 5px;" id="requestParamSettins_ticket_server_gridId">
                    <%--主机名称: --%><input type="hidden" class="easyui-textbox"  id="serverName_params" >
                    <%--<a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('server')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>--%>
                    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${idcTicket.ticketResourceHandStatus and !pageQueryDataStatus}">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('400','','server','')">选择主机</a>
                        <span>||</span>
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeAllResource('400','${idcTicket.ticketInstId}')">一键删除全部主机</a>
                        <c:if test="${ticketCategoryFrom != 900}">  <%--如果当前流程不是业务变更流程--%>
                            <span style="color:red">申请主机数为>> ${idcReServerModelNum} 台</span>
                        </c:if>
                        <c:if test="${ticketCategoryFrom == 900}">  <%--如果当前流程不是业务变更流程--%>
                            <span style="color:red">变更主机数为>> ${changeServerNum} 个</span>
                        </c:if>
                    </jbpmSecurity:security>
                </div>
                <table class="easyui-datagrid" id="ticket_server_gridId"></table>
            </jbpmSecurity:security>
            <!-- 主机租赁 end -->
        </div>
    </jbpmSecurity:security>

        <div title="附件" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
            <iframe id="downloadFile" src="" style="display: none"></iframe>
            <!-- 【附件列表信息:可以操纵的都具有附件情况ticketStatus是否参看】 -->
            <jbpm:jbpmTag ticketItem="${idcTicket}" module="TICKET_ATTACHMENT" gridId="ticketAttachmentGridId" title="工单附件列表" toolbar="ticketAttachmentBbar"></jbpm:jbpmTag>
        </div>

    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${isOpenTicket}">
        <jbpm:jbpmTag title="相关工单" hasOtherTicket="2" gridId="his_ticket_gridId" module="OTHER_LINKED_TICKET" toolbar="requestParamSettins_hisShowTaskQuery" ticketItem="${idcTicket}" prodInstId="${idcTicket.prodInstId}" ticketInstId="${idcTicket.id}"></jbpm:jbpmTag>

        <jbpm:jbpmTag title="开通信息" module="TICKET_CONCTRACT" idcReCustomer="${idcReCustomer}" ticketItem="${idcTicket}" isHasOpenEdit="${isHasOpenEdit}" ticketInstId="${idcTicket.id}"></jbpm:jbpmTag>
    </jbpmSecurity:security>

    <%--如果是第一次申请这一步，就不需要显示评价里面的信息--%>
    <c:if test="${notShowEvaluate}">
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
                                        <input class="easyui-datetimebox" <c:if test="${!isPauseTicketEdit}">readonly="readonly"</c:if>  name="idcRunTicketPause.reservestart" value="${idcTicketPause.reservestartStr}" data-options="required:false,editable:false,showSeconds:true,width:150">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="kv-label"style="width:100px" >停机结束时间<span style="color:red">&nbsp;*</span></td>
                                    <td class="kv-content">
                                        <input class="easyui-datetimebox" <c:if test="${!isPauseTicketEdit}">readonly="readonly"</c:if>  name="idcRunTicketPause.reserveend" value="${idcTicketPause.reserveendStr}" data-options="required:false,editable:false,showSeconds:true,width:150">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="kv-label"style="width:100px" >停机原因<span style="color:red">&nbsp;*</span></td>
                                    <td class="kv-content">
                                        <input class="easyui-textbox"  <c:if test="${!isPauseTicketEdit}">readonly="readonly"</c:if> data-options="required:true,width:783,height:100,multiline:true" name="idcRunTicketPause.constructComment" value="${idcTicketPause.constructComment}"  data-options="validType:'length[0,255]'"/>
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
                        <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${canPermission}">
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
                        </jbpmSecurity:security>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </c:if>

    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${addEasyuiTab and isHaltTicket}">
        <div title="下线原因" style="padding:10px">
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
        </div>
    </jbpmSecurity:security>
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${isAddRecoverEasyuiTab and isRecoverTicket}">
    <div title="复通" style="padding:10px">
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

                            <input class="easyui-datetimebox"  name="idcRunTicketPause.reservestart" value="${idcTicketPause.reservestartStr}" data-options="required:false,editable:false,showSeconds:true,width:150">
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
                            <input class="easyui-textbox" data-options="required:true,width:783,height:100,multiline:true" name="idcRunTicketPause.constructComment" value="${idcTicketPause.constructComment}"  data-options="validType:'length[0,255]'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </jbpmSecurity:security>
</div>
</body>
<script type="text/javascript">
    var ticketResourceAllocation;
    $(document).ready(function(){
        ticketResourceAllocation = getTicketResourceAllocation();
    });

    function getTicketResourceAllocation(){
    var obj = {};

    obj['ticketResourceHandStatus'] = "${idcTicket.ticketResourceHandStatus}";//是否可以操作的方法

    obj['resourceAllocationStatus'] = "${idcTicket.resourceAllocationStatus}";//是否显示了资源分配项
    obj['pageQueryDataStatus'] = '${pageQueryDataStatus}';
    return obj;
    }
</script>
<jsp:include page="/globalstatic/easyui/jbpmUtils.jsp"></jsp:include>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketRackResourceGrid.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/hisTicketGridQuery.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/runCommon.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/changeDemand.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/attachment/resourceAttachment.js"></script>
</html>