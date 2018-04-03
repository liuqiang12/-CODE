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
    <!-- js动态加载js以及样式 -->
    <script type="application/javascript" src="<%=request.getContextPath() %>/framework/workFlowRoot.js" ></script>
    <!-- 引用 -->
    <title>审批面板</title>
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
        overflow: auto;
    }
    .tip_float_right_info{
        position: absolute;
        right: 10px;
        top:1px;
        z-index: 999;
    }
</style>
<body style="background-color: #e9f6fa">
<jbpm:jbpmTag title="工单滚动提示" module="FLOAT_TIP_MSG" width="525" height="23" lineHeight="24" actName="${actName}" serialNumber="${serialNumber}" comment="请填写审批意见"></jbpm:jbpmTag>
<!--  tabs布局  -->
<div id="jbpmApply_tabs" class="easyui-tabs" fit="true">
    <div title="客户信息" style="padding:10px;display:none;">
        <jbpm:jbpmTag module="TICKET_RECUSTOMER" item="${idcReCustomer}" ticketInstId="${ticketInstId}"></jbpm:jbpmTag>
    </div>
    <div title="客户需求" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <jbpm:jbpmTag  module="TICKET_PRODUCET_INFO" item="${idcReProduct}" ticketItem="${idcRunTicket}"  isFormEdit="true" category="${category}"></jbpm:jbpmTag>

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
                        <input readonly="readonly" id="idcReProductidcName" name="idcReProductidcName" value="${idcReProduct.idcName}" class="easyui-textbox" data-options="required:false,width:150">
                    </td>
                    <td class="kv-label">预占有效天数(天)</td>
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
                        <input readonly="readonly" name="applyerRoles" value="${idcReProduct.applyerRoles}" class="easyui-textbox" data-options="width:150">

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
                                           url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/800'" name="idcReNewlyModel.category" readonly="readonly"  id="idcReNewlyModel_category" value="${idcReNewlyModel.category}" />
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
                                <c:forEach items="${attachmentinfoList}" var="item" varStatus="index">
                                    <div class="attachHasedCls">
                                        <input value="${item.attachName}" class="easyui-textbox" data-options="editable:false,width:550" />
                                        <%--<a href="javascript:void(0)" class="easyui-linkbutton" onclick="removeAttach(this,${item.id})" style="width:38px">删除</a>--%>
                                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="downLoadAttach(${item.id})" style="width:38px">下载</a>
                                    </div>
                                </c:forEach>
                            </div>
                        </td>
                    </tr>

                </table>
            </fieldset>
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
            <!-- 虚拟机信息 -->
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
                            <td class="kv-content" colspan="3">
                                <input class="easyui-textbox" data-options="required:true,width:720,height:100,multiline:true" name="comment" value="${idcRunProcCment.comment}"  data-options="validType:'length[0,255]'"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
    <iframe id="downloadFile" src="" style="display: none"></iframe>
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

    function downLoadAttach(id){
        $("#downloadFile").attr("src",contextPath+"/assetAttachmentinfoController/downLoadFile/"+id);
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
                '       <input class="easyui-textbox" readonly="readonly"　name="ins_vmName" value="${idcNetServiceinfo.vmName}"  id="ins_vmName" data-options="required:true,validType:\'length[0,128]\',width:150"/>'+
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
                '   }]" value="${idcNetServiceinfo.vmStatus}"  name="ins_vmStatus" id="ins_vmStatus"/>'+
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
                '   }]" value="${idcNetServiceinfo.vmCategory}"  name="ins_vmCategory" id="ins_vmCategory"/>'+

                '   </td>'+
                '   <td class="kv-label">网络地址</td>'+
                '       <td class="kv-content">'+
                '       <!-- ins_vmNetAddr -->'+
                '       <input class="easyui-textbox" readonly="readonly"　name="ins_vmNetAddr" value="${idcNetServiceinfo.vmNetAddr}"  id="ins_vmNetAddr" data-options="required:true,validType:\'length[0,128]\',width:150"/>'+
                '       </td>'+
                '       </tr>'+
                '       <tr>'+
                '       <td class="kv-label">管理地址</td>'+
                '       <td class="kv-content">'+
                '       <input class="easyui-textbox" readonly="readonly"　name="ins_vmManagerAddr" value="${idcNetServiceinfo.vmManagerAddr}"  id="ins_vmManagerAddr" data-options="required:true,validType:\'length[0,128]\',width:150"/>'+
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
                /*如果服务都没有  则不需要提交流程 */
                if(!(rack && rack=='true' ||
                    port && port == 'true'||
                    ip && ip == 'true'||
                    server && server == 'true')){
                    top.layer.msg('至少需要一个服务项', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
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