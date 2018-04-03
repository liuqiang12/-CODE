<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="jbpm" uri="http://jbpm.idc.tag.cn/" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/custom/uimaker/css/basic_info.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/checkbox/skin/style/css/checkbox.css" />
    <script type="application/javascript" src="<%=request.getContextPath() %>/framework/checkbox/js/checkbox.js" ></script>
    <script type="application/javascript" src="<%=request.getContextPath() %>/js/jbpm/ticket/datagrid-detailview.js" ></script>

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
        overflow: auto;
    }
</style>
<body style="background-color: #e9f6fa">
<!--  tabs布局  -->
<div id="jbpmApply_tabs" class="easyui-tabs" fit="true">
    <div title="客户信息" style="padding:10px;display:none;">
        <!-- 客户信息form界面 -->
        <fieldset style="border-color: #c0e7fb;" class="fieldsetCls">
            <legend>&diams;客户信息</legend>
            <table class="kv-table" style="width: 100%;height: 100%">
                <tr>
                    <td class="kv-label" style="width: 200px;">单位名称</td>
                    <td class="kv-content">
                        <input type="hidden" name="idcReCustomer.id" value="${idcReCustomer.id}" id="idcReCustomer_id"/>
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="name" value="${idcReCustomer.name}"  id="name" data-options="validType:'length[0,64]'"/>
                    </td>
                    <td class="kv-label">单位属性</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								required:true
								,width:150,
								data: [{
									label: '军队',
									value: '1'
								},{
									label: '政府机关',
									value: '2'
								},{
									label: '事业单位',
									value: '3'
								},{
									label: '企业',
									value: '4'
								},{
									label: '个人',
									value: '5'
								},{
									label: '社会团体',
									value: '6'
								},{
									label: '其他',
									value: '999'
								}]" value="${idcReCustomer.attribute}"  name="attribute" id="attribute"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">证件类型</td>
                    <td class="kv-content">
                        <!-- 下拉框 -->
                        <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								required:true,width:150,
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
								}]" value="${idcReCustomer.idcardtype}"  name="idcardtype" id="idcardtype"/>
                    </td>

                    <td class="kv-label">证件号</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="idcardno" value="${idcReCustomer.idcardno}"  id="idcardno" data-options="validType:'length[0,64]'"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">邮政编码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="zipcode" value="${idcReCustomer.zipcode}"  id="zipcode" data-options="validType:'length[0,6]'"/>
                    </td>

                    <td class="kv-label">单位地址</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="addr" value="${idcReCustomer.addr}"  id="addr" data-options="validType:'length[0,128]'"/>
                    </td>
                </tr>
            </table>
        </fieldset>　
        <fieldset style="border-color: #c0e7fb;" class="fieldsetCls">
            <legend>&diams;客户联系人</legend>
            <table class="kv-table">
                <tr>
                    <td class="kv-label" style="width: 200px;">联系人</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="contactname" value="${idcReCustomer.contactname}"  id="contactname" data-options="validType:'length[0,64]'"/>
                    </td>
                    <td class="kv-label">证件类型</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								required:true,width:150,
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
								}]" value="${idcReCustomer.contactIdcardtype}"  name="contactIdcardtype" id="contactIdcardtype"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">证件号</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="contactIdcardno" value="${idcReCustomer.contactIdcardno}"  id="contactIdcardno" data-options="validType:'length[0,64]'"/>
                    </td>

                    <td class="kv-label">固定电话</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="contactPhone" value="${idcReCustomer.contactPhone}"  id="contactPhone" data-options="validType:'length[0,15]'"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">移动号码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="contactMobile" value="${idcReCustomer.contactMobile}"  id="contactMobile" data-options="validType:'length[0,15]'"/>
                    </td>

                    <td class="kv-label">邮箱</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="contactEmail" value="${idcReCustomer.contactEmail}"  id="contactEmail" data-options="validType:'length[0,64]'"/>
                    </td>
                </tr>
            </table>
        </fieldset>
        <fieldset style="border-color: #c0e7fb;" class="fieldsetCls">
            <legend>&diams;附加类型</legend>
            <table class="kv-table">
                <tr>
                    <td class="kv-label" style="width: 200px;">客户级别</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,width:150,
								data: [{
									label: 'A类个人客户',
									value: '10'
								},{
									label: 'B类个人客户',
									value: '20'
								},{
									label: 'C类个人客户',
									value: '30'
								},{
									label: 'A类集团',
									value: '40'
								},{
									label: 'A1类集团',
									value: '50'
								},{
									label: 'A2类集团',
									value: '60'
								},{
									label: 'B类集团',
									value: '70'
								},{
									label: 'B1类集团',
									value: '80'
								},{
									label: 'B2类集团',
									value: '90'
								},{
									label: 'C类集团',
									value: '100'
								},{
									label: 'D类集团',
									value: '110'
								},{
									label: 'Z类集团',
									value: '120'
								},{
									label: '其他',
									value: '999'
								}]" value="${idcReCustomer.customerlevel}"  name="customerlevel" id="customerlevel"/>
                    </td>
                    <td class="kv-label">跟踪状态</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',width:150,
								editable:false,
								data: [{
									label: '跟踪状态',
									value: '10'
								},{
									label: '有意向',
									value: '20'
								},{
									label: '继续跟踪',
									value: '30'
								},{
									label: '无意向',
									value: '40'
								}]" value="${idcReCustomer.tracestate}"  name="tracestate" id="tracestate"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">服务等级</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',width:150,
								editable:false,
								data: [{
									label: '金牌',
									value: '10'
								},{
									label: '银牌',
									value: '20'
								},{
									label: '铜牌',
									value: '30'
								},{
									label: '铁牌',
									value: '40'
								},{
									label: '标准',
									value: '999'
								}]" value="${idcReCustomer.sla}"  name="sla" id="sla"/>
                    </td>
                    <td class="kv-label">客户类别</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',width:150,
								editable:false,
								data: [{
									label: '局方',
									value: '10'
								},{
									label: '第三方',
									value: '20'
								},{
									label: '测试',
									value: '30'
								},{
									label: '退场客户',
									value: '40'
								},{
									label: '国有',
									value: '50'
								},{
									label: '集体',
									value: '60'
								},{
									label: '私营',
									value: '70'
								},{
									label: '股份',
									value: '80'
								},{
									label: '外商投资',
									value: '90'
								},{
									label: '港澳台投资',
									value: '100'
								},{
									label: '客户',
									value: '110'
								},{
									label: '自由合作',
									value: '120'
								},{
									label: '内容引入',
									value: '130'
								},{
									label: '其他',
									value: '999'
								}]" value="${idcReCustomer.category}"  name="category" id="category"/>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <div title="客户需求" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <!-- 客户需求form界面 -->
        <fieldset class="fieldsetCls fieldsetHeadCls">
            <legend>&diams;客户需求</legend>
            <table class="kv-table">
                <tr>
                    <td class="kv-label" style="width: 200px;">订单名称</td>
                    <td class="kv-content">
                        <input type="hidden" value="${idcReProduct.id}" name="id"/>
                        <input class="easyui-textbox" data-options="required:true,width:150" readonly="readonly" value="${idcReProduct.prodName}" name="prodName" id="prodName" data-options="validType:'length[0,255]',width:150"/>
                    </td>
                    <td class="kv-label">订单类别</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								required:true,
								width:100,
								data: [{
									label: '政企业务',
									value: '1'
								},{
									label: '自有业务',
									value: '0'
								}]" name="prodCategory" id="prodCategory" value="${idcReProduct.prodCategory}" />
                    </td>
                    <td class="kv-label">所属客户</td>
                    <td class="kv-content">
                        <!-- 客户选择框 -->
                        <input type="hidden" name="customerId" value="${idcReProduct.customerId}" id="customerId"/>
                        <input id="customerName"readonly="readonly"   value="${idcReProduct.customerName}" class="easyui-textbox" data-options="editable:false,width:150,required:true,iconAlign:'left',buttonText:'选择' ">
                    </td>
                </tr>
                <tr>
                    <td class="kv-label" style="width: 200px;">激活状态</td>
                    <td class="kv-content">
                        <!-- isActive -->
                        <input class="easyui-combobox" readonly="readonly"  data-options="
                            valueField: 'value',
                            textField: 'label',
                            width:100,
                            data: [{
                                label: '激活',
                                value: '1'
                            },{
                                label: '禁用',
                                value: '0'
                            }]" name="isActive" id="isActive" value="${idcReProduct.isActive}" />
                    </td>
                    <td class="kv-label">订单状态</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:100,
								data: [{
									label: '在途',
									value: '10'
								},{
									label: '已竣工',
									value: '20'
								},{
									label: '已停机',
									value: '30'
								},{
									label: '已撤销',
									value: '40'
								}]" name="prodStatus" id="prodStatus" value="${idcReProduct.prodStatus}" />
                    </td>
                    <td class="kv-label">创建时间</td>
                    <td class="kv-content">
                        <input class="easyui-datebox" data-options="width:150" readonly="readonly" value="${idcReProduct.createTime}" name="createTime" id="createTime"/>
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
                            <input readonly="readonly"  class="easyui-textbox" data-options="width:150" value="${idcReNewlyModel.NAME}"
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
                                       }]" name="idcReNewlyModel.category" readonly="readonly"  id="idcReNewlyModel_category" value="${idcReNewlyModel.CATEGORY}" />
                        </td>
                        <td class="kv-label">描述</td>
                        <td class="kv-content">
                            <input readonly="readonly"  class="easyui-textbox" value="${idcReNewlyModel.DESC}"
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
                            <input type="hidden" readonly="readonly" name="id" value="${idcContract.id}" id="id"/>
                            <!-- 合同的工单id -->
                            <input name="idcContract_ticketInstId" readonly="readonly" type="hidden" value="${ticketInstId}">
                            <input class="easyui-textbox"readonly="readonly"  name="contractname" value="${idcContract.contractname}"  id="contractname" data-options="required:true,validType:'length[0,128]',width:150"/>
                        </td>
                        <td class="kv-label">编码</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" readonly="readonly" name="contractno" value="${idcContract.contractno}"  id="contractno" data-options="required:true,validType:'length[0,128]',width:150"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">价格(元)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox" readonly="readonly" name="contractprice" value="${idcContract.contractprice}" id="contractprice" data-options="required:true,width:150,min:0,validType:'length[0,12]'"/>
                        </td>
                        <td class="kv-label">客户信息</td>
                        <td class="kv-content">
                            <!--  -->
                            <input type="hidden" readonly="readonly" name="idcContract_customerId" value="${idcReCustomer.id}" id="idcContract_customerId"/>
                            <input id="idcContract_customerName"readonly="readonly"  value="${idcReCustomer.name}" class="easyui-textbox" data-options="editable:false,required:true,width:150">
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">开始时间</td>
                        <td class="kv-content">
                            <input class="easyui-datetimebox" readonly="readonly"  name="contractstart" value="${idcContract.contractstart}" data-options="required:true,editable:false,showSeconds:true,width:150">
                        </td>

                        <td class="kv-label">合同期限(月)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox"readonly="readonly"  name="contractperiod" value="${idcContract.contractperiod}" id="contractperiod" data-options="required:true,precision:0,min:0,validType:'length[0,10]',width:150"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">签订日期</td>
                        <td class="kv-content">
                            <input class="easyui-datetimebox" readonly="readonly"   name="contractsigndate" value="${idcContract.contractsigndate}" data-options="required:true,editable:false,showSeconds:true,width:150">
                        </td>

                        <td class="kv-label">到期提醒方案</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" readonly="readonly" data-options="
                                    valueField: 'value',
                                    textField: 'label',
                                    required:true,
                                    editable:false,
                                    width:150,
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
                            <input class="easyui-textbox" readonly="readonly" multiline="true"  name="contractremark"  value="${idcContract.contractremark}" style="width:83%;height:50px" data-options="validType:'length[0,255]'">
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">合同附件</td>
                        <td class="kv-content" colspan="3">
                            <div id="muiltFilesDiv">
                                <!-- 罗列出已经添加了的附件信息 -->
                                <c:forEach items="${attachmentinfoList}" var="item" varStatus="index">
                                    <div class="attachHasedCls">
                                        <input value="${item.attachName}" readonly="readonly" class="easyui-textbox" data-options="editable:false,width:550" />
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
            <c:if test="${not empty idcNetServiceinfo.openTime}">
                <fieldset class="fieldsetCls fieldsetHeadCls">
                    <legend>&diams;服务信息</legend>
                    <table class="kv-table">
                        <tr>
                            <td class="kv-label" style="width:215px">服务开通时间</td>
                            <td class="kv-content" colspan="3">
                                <input class="easyui-datetimebox" readonly="readonly"  name="idcNetServiceinfo.openTime" value="${idcNetServiceinfo.openTime}" data-options="required:true,editable:false,showSeconds:true,width:350">
                            </td>
                        </tr>
                    </table>
                </fieldset>
            </c:if>
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
                                    <input class="easyui-textbox" readonly="readonly" name="ins_vmName" value="${idcNetServiceinfo.vmName}"  id="ins_vmName" data-options="required:true,validType:'length[0,128]',width:150"/>
                                </td>
                                <td class="kv-label">状态</td>
                                <td class="kv-content">
                                    <input class="easyui-combobox" readonly="readonly" data-options="
                                               valueField: 'value',
                                                   textField: 'label',
                                                   required:true,
                                                   editable:false,
                                                   width:150,
                                                   data: [{
                                                   label: '可用',
                                                   value: '1'
                                               },{
                                                   label: '不可用',
                                                   value: '-1'
                                               }]" value="${idcNetServiceinfo.vmStatus}"  name="ins_vmStatus" id="ins_vmStatus"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="kv-label">类型</td>
                                <td class="kv-content">
                                    <input class="easyui-combobox" readonly="readonly" data-options="
                                                   valueField: 'value',
                                                       textField: 'label',
                                                       width:150,
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
                                                   }]" value="${idcNetServiceinfo.vmCategory}"  name="ins_vmCategory" id="ins_vmCategory"/>
                                </td>
                                <td class="kv-label">网络地址</td>
                                <td class="kv-content">
                                    <!-- ins_vmNetAddr -->
                                    <input class="easyui-textbox"  readonly="readonly" name="ins_vmNetAddr" value="${idcNetServiceinfo.vmNetAddr}"  id="ins_vmNetAddr" data-options="required:true,validType:'length[0,128]',width:150"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="kv-label">管理地址</td>
                                <td class="kv-content">
                                    <input class="easyui-textbox" readonly="readonly" name="ins_vmManagerAddr" value="${idcNetServiceinfo.vmManagerAddr}"  id="ins_vmManagerAddr" data-options="required:true,validType:'length[0,128]',width:150"/>
                                </td>
                                <td class="kv-label"></td>
                                <td class="kv-content">
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </c:if>
            </div>
        </form>
        <!-- 开通信息form界面 end-->
    </div>
    <!-- 资源分配 start -->
    <c:if test="${
            (not empty serviceApplyImgStatus.rack and serviceApplyImgStatus.rack == 'true') or
            (not empty serviceApplyImgStatus.port and serviceApplyImgStatus.port == 'true') or
            (not empty serviceApplyImgStatus.ip and serviceApplyImgStatus.ip == 'true') or
            (not empty serviceApplyImgStatus.server and serviceApplyImgStatus.server == 'true')
    }">
        <!-- 只有有资源申请就可以分配 -->
    <div title="资源分配" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <iframe id="downloadFile" src="" style="display: none"></iframe>
        <div>
            <fieldset style="border-color: #c0e7fb;" class="fieldsetCls">
                <legend>&diams;附件信息</legend>
                <table class="easyui-datagrid" id="addAttachmentResource"></table>
            </fieldset>
        </div>

        <!-- 机架机位 start -->
        <c:if test="${not empty serviceApplyImgStatus.rack and serviceApplyImgStatus.rack == 'true'}">
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
        </c:if>
        <!-- 机架机位 end -->
        <!-- 端口带宽 start -->
        <c:if test="${not empty serviceApplyImgStatus.port and serviceApplyImgStatus.port == 'true'}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_port_gridId">
            名称: <input class="easyui-textbox"  id="portName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('port')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </div>
        <table class="easyui-datagrid" id="ticket_port_gridId"></table>
        </c:if>
        <!-- 端口带宽 end -->
        <!-- ip租用 start -->
        <c:if test="${not empty serviceApplyImgStatus.ip and serviceApplyImgStatus.ip == 'true'}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_ip_gridId">
            名称: <input class="easyui-textbox"  id="ipName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('ip')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </div>
        <table class="easyui-datagrid" id="ticket_ip_gridId"></table>
        </c:if>
        <!-- ip租用 end -->
        <!-- 主机租赁 start -->
        <c:if test="${not empty serviceApplyImgStatus.server and serviceApplyImgStatus.server == 'true'}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_server_gridId">
            主机名称: <input class="easyui-textbox"  id="serverName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('server')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </div>
        <table class="easyui-datagrid" id="ticket_server_gridId"></table>
        </c:if>
        <!-- 主机租赁 end -->

    </div>
    </c:if>

    <!-- 资源分配 end -->
    <div title="审批" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
        <!-- 分上下布局： 历史审批情况 -->
        <!-- 温馨提示[请先按照申请要求配置相应的资源信息...] -->
        <div style="height:30px;line-height:30px;background-color: #d7f5fe">
            <marquee  direction="left" scrollamount="30" scrolldelay="500" behavior="SCROLL" hspace="0" vspace="0" loop="INFINITE">
                <span style="color: #006107">温馨提示[请先按照申请要求配置相应的资源信息...]</span>
            </marquee>
        </div>
        <table class="easyui-datagrid" style="margin-bottom: 30px;" id="ticket_hisComment_gridId"></table>
        <form method="post" id="singleForm" action="<%=request.getContextPath() %>/actJBPMController/procCmentFormSaveOrUpdate.do">
            <table class="kv-table">
                <tr>
                    <td class="kv-label">复通时间</td>
                    <td class="kv-content">
                        <input class="easyui-datetimebox"  readonly="readonly" name="reservestart" value="${categoryTicketMeta.RECOVERTIME}" data-options="required:false,editable:false,showSeconds:true,width:150" >
                    </td>
                </tr>
                <tr>
                    <td class="kv-label"style="width:100px" >备注<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:false,width:420,height:100,multiline:true"  value="${categoryTicketMeta.APPLYDESC}" />
                    </td>
                </tr>

                        <!-- 隐藏的属性 -->
                        <input name="id" id="idcRunProcCment_id" type="hidden" value="${idcRunProcCment.id}">
                        <!-- 工单实例 -->
                        <input name="ticketInstId" id="ticketInstId_param" type="hidden" value="${ticketInstId}"><input name="is_author_apply_show" id="is_author_apply_show" type="hidden" value="${is_author_apply_show}">
                        <!-- 订单实例 -->
                        <input name="prodInstId" id="prodInstId_param" type="hidden" value="${prodInstId}"><input  id="category_param" type="hidden" value="${category}">
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
                        <!-- 控制操作列的显示与否 -->
                        <input id="isShowGridColumnHandlerFlag" type="hidden" value="${isShowGridColumnHandlerFlag}">
                <tr>
                    <td class="kv-label"style="width:100px">申请人名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:420" readonly="readonly" value="${author}" name="author"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label" style="width:100px">提交状态<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content">
                        <!-- 提交状态：同意 不同意 -->
                        <input name="status" id="idcRunProcCment_status_stand" value="<c:if test="${empty idcRunProcCment.status}">1</c:if><c:if test="${not empty idcRunProcCment.status}">${idcRunProcCment.status}</c:if>" type="hidden"/>
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
                </tr>
                <tr>
                    <td class="kv-label"style="width:100px" >审批意见<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:420,height:100,multiline:true" name="comment" value="${idcRunProcCment.comment}"  data-options="validType:'length[0,255]'"/>
                    </td>
                </tr>
            </table>
        </form>
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
    /**
     * 类别
     * @param category
     */
    function serviceApplyImgStatusOnclick(category){
        alert("设置300被选中");
        $("#serviceApplyImgStatus300")
    }
    /*提交表单情况*/
    function form_sbmt(gridId){
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
                var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
            }
        });
    }
</script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketRackResourceQueryInit.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketRackResourceGrid.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/runCommon.js"></script>
<script src="<%=request.getContextPath() %>/js/jbpm/attachment/resourceAttachment.js"></script>
</html>