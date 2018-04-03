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
                                           url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/900'" name="idcReProductidcName"  />
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
    <div title="开通信息" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <!-- 合同信息 -->
        <!-- 开通信息form界面 start -->
        <form id="contractFrom" method="post"  enctype="multipart/form-data" >

            <!-- 服务信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls">
                <legend>&diams;服务信息</legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 200px;">名称</td>
                        <td class="kv-content">
                            <input name="prodInstId" type="hidden" value="${prodInstId}">
                            <input name="category" type="hidden" value="${category}">
                            <input type="hidden" value="${idcReProduct.prodCategory}" name="prodCategory"/>

                            <!-- 合同的工单id -->
                            <input name="isRejectTicket" type="hidden" value="${idcRunTicket.isRejectTicket}">
                            <input name="idcContract_ticketInstId" type="hidden" value="${ticketInstId}">

                            <input type="hidden" name="idcNetServiceinfo.id" value="${idcNetServiceinfo.id}" id="idcNetServiceinfo.id"/>
                            <input name="idcNetServiceinfo.ticketInstId" type="hidden" value="${ticketInstId}">
                            <input class="easyui-textbox" name="idcNetServiceinfo.icpsrvname" value="${idcNetServiceinfo.icpsrvname}"  id="icpsrvname" data-options="required:true,validType:'length[0,64]',width:200"/>
                        </td>
                        <td class="kv-label">所属用户</td>
                        <td class="kv-content">
                            <input name="idcNetServiceinfo.customerId" value="${idcReCustomer.id}" type="hidden" />
                            <input class="easyui-textbox" readonly="readonly" name="idcNetServiceinfo.customerName" value="${idcReCustomer.name}"  id="idcNetServiceinfo.customerName" data-options="required:true,validType:'length[0,128]',width:200"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">服务内容</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" data-options="
                                    valueField: 'value',
                                    textField: 'label',
                                    required:true,
                                    editable:false,
                                    width:200,
                                    url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/1000'" value="${idcNetServiceinfo.icpsrvcontentcode}"  name="idcNetServiceinfo.icpsrvcontentcode" id="idcNetServiceinfo.icpsrvcontentcode"/>

                        </td>
                        <td class="kv-label">备案类型</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" data-options="
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
                        <td class="kv-label">备案号[许可证号]</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" name="idcNetServiceinfo.icprecordno" value="${idcNetServiceinfo.icprecordno}"  id="icprecordno" data-options="required:true,validType:'length[0,64]',width:200"/>
                        </td>
                        <td class="kv-label">接入方式</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" data-options="
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
                                    }]" value="${idcNetServiceinfo.icpaccesstype}"  name="idcNetServiceinfo.icpaccesstype" id="icpaccesstype"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">域名信息</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" name="idcNetServiceinfo.icpdomain" value="${idcNetServiceinfo.icpdomain}"  id="idcNetServiceinfo.icpdomain" data-options="required:true,validType:'length[0,128]',width:200"/>
                        </td>
                        <td class="kv-label">业务类型</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" data-options="
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
                    <!-- 服务信息 -->
                    <tr>
                        <td class="kv-label">服务开通时间</td>
                        <td class="kv-content" colspan="3">
                            <input class="easyui-datetimebox"  name="idcNetServiceinfo.openTime" value="${idcNetServiceinfo.openTime}" data-options="required:true,editable:false,showSeconds:true,width:200">
                        </td>

                    </tr>
                </table>
            </fieldset>
            <!-- 虚拟机信息 -->
            <div id="vm_content">
                <!-- 默认如果是虚拟主机  则需要显示 -->
                <c:if test="${not empty idcNetServiceinfo and idcNetServiceinfo.icpaccesstype == 1}">
                    <fieldset class="fieldsetCls fieldsetHeadCls">
                        <legend>&diams;虚拟机信息</legend>
                        <table class="kv-table">
                            <tr>
                                <td class="kv-label" style="width: 200px;">名称</td>
                                <td class="kv-content">
                                    <input class="easyui-textbox" name="idcNetServiceinfo.vmName" value="${idcNetServiceinfo.vmName}"  id="idcNetServiceinfo.vmName" data-options="required:true,validType:'length[0,128]',width:200"/>
                                </td>
                                <td class="kv-label">状态</td>
                                <td class="kv-content">
                                    <input class="easyui-combobox" data-options="
                                               valueField: 'value',
                                                   textField: 'label',
                                                   required:true,
                                                   editable:false,
                                                   width:200,
                                                   data: [{
                                                   label: '可用',
                                                   value: '1'
                                               },{
                                                   label: '不可用',
                                                   value: '-1'
                                               }]" value="${idcNetServiceinfo.vmStatus}"  name="idcNetServiceinfo.vmStatus" id="idcNetServiceinfo.vmStatus"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="kv-label">类型</td>
                                <td class="kv-content">
                                    <input class="easyui-combobox" data-options="
                                                   valueField: 'value',
                                                       textField: 'label',
                                                       width:200,
                                                      required:true,
                                                       editable:false,
                                                       data: [{
                                                       label: '共享式',
                                                       value: '1'
                                                   },{
                                                       label: '专用式',
                                                       value: '2'
                                                   },{
                                                       label: '云虚拟',
                                                       value: '3'
                                                   }]" value="${idcNetServiceinfo.vmCategory}"  name="idcNetServiceinfo.vmCategory" id="idcNetServiceinfo.vmCategory"/>
                                </td>
                                <td class="kv-label">网络地址</td>
                                <td class="kv-content">
                                    <!-- idcNetServiceinfo.vmNetAddr -->
                                    <input class="easyui-textbox" name="idcNetServiceinfo.vmNetAddr" value="${idcNetServiceinfo.vmNetAddr}"  id="idcNetServiceinfo.vmNetAddr" data-options="required:true,validType:'length[0,128]',width:200"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="kv-label">管理地址</td>
                                <td class="kv-content" colspan="3">
                                    <input class="easyui-textbox" name="idcNetServiceinfo.vmManagerAddr" value="${idcNetServiceinfo.vmManagerAddr}"  id="idcNetServiceinfo.vmManagerAddr" data-options="required:true,validType:'length[0,128]',width:200"/>
                                </td>

                            </tr>
                        </table>
                    </fieldset>
                </c:if>
            </div>
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
            <jbpm:jbpmTag module="TICKET_ATTACHMENT" count="${ticketAttachCount}" gridId="ticketAttachmentGridId"  ticketInstId="${ticketInstId}" prodInstId="${prodInstId}" title="工单附件列表"  maxHeight="150" toolbar="ticketAttachmentBbar" isShowGridColumnHandlerFlag="${isShowGridColumnHandlerFlag}"></jbpm:jbpmTag>

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
            <jbpmSecurity:security module="SIMPLE_MODULE" comment="主机" hasSecurity="${serviceApplyImgStatus.server}">
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
                        </td>
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
                '       <input class="easyui-textbox" name="idcNetServiceinfo.vmName" value="${idcNetServiceinfo.vmName}"  id="idcNetServiceinfo.vmName" data-options="required:true,validType:\'length[0,128]\',width:200"/>'+
                '       </td>'+
                '       <td class="kv-label">状态</td>'+
                '       <td class="kv-content">'+
                '       <input class="easyui-combobox" data-options="'+
                '   valueField: \'value\','+
                '       textField: \'label\','+
                '       required:true,'+
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
                '   <td class="kv-label">类型</td>'+
                '       <td class="kv-content">'+
                '       <input class="easyui-combobox" data-options="'+
                '   valueField: \'value\','+
                '       textField: \'label\','+
                '       width:200,'+
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
                '       <input class="easyui-textbox" name="idcNetServiceinfo.vmNetAddr" value="${idcNetServiceinfo.vmNetAddr}"  id="idcNetServiceinfo.vmNetAddr" data-options="required:true,validType:\'length[0,128]\',width:200"/>'+
                '       </td>'+
                '       </tr>'+
                '       <tr>'+
                '       <td class="kv-label">管理地址</td>'+
                '       <td class="kv-content">'+
                '       <input class="easyui-textbox" name="idcNetServiceinfo.vmManagerAddr" value="${idcNetServiceinfo.vmManagerAddr}"  id="idcNetServiceinfo.vmManagerAddr" data-options="required:true,validType:\'length[0,128]\',width:200"/>'+
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
        var isRejectTicket = '${idcRunTicket.isRejectTicket}';

        $("#contractFrom").form('submit', {
            url:contextPath+"/actJBPMController/contractFormSaveOrUpdate.do",
            onSubmit: function(){
                if(!$("#contractFrom").form("validate")){
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    //直接定位到:[审批界面]
                    $("#jbpmForm_tabs").tabs("select","开通信息");
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
                        time: 3000 //默认也是3秒关闭
                    });
                    var parentIndex = parent.layer.getFrameIndex("jbpmApplyWinId");//获取了父窗口的索引
                    parent.layer.close(parentIndex);  // 关闭layer
                    grid.datagrid("reload");
                }
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