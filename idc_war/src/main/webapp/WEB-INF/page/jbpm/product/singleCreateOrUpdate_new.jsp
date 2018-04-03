<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/checkbox/skin/style/css/checkbox.css" />

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
    .kv-label{
        width:200px;
    }
</style>
<body>
<form method="post" id="singleForm" action="<%=request.getContextPath() %>/resourceApplyController/productFormSaveOrUpdate.do">
    <fieldset class="fieldsetCls fieldsetHeadCls">
        <legend>&diams;预受理信息表</legend>
        <table class="kv-table">
            <tr>
                <td class="kv-label" style="width: 120px;">申请人</td>
                <td class="kv-content">
                    <input readonly="readonly" name="sysUserinfoApplyero_username" value="<sec:authentication property='principal.nick' htmlEscape="false"/>" class="easyui-textbox" data-options="width:200">
                </td>
                <td class="kv-label" style="width: 120px;">申请人角色</td>
                <td class="kv-content">
                    <input readonly="readonly" id="sysUserinfoApplyer_sysRoleinfoTypes" name="sysUserinfo_roles_user" value="<sec:authentication property='principal.roles_user' htmlEscape="false"/>" class="easyui-textbox" data-options="width:200" title="${sysUserinfo.roles_user}">

                </td>
                <td class="kv-label" style="width: 120px;">联系电话</td>
                <td class="kv-content">
                    <input readonly="readonly" name="sysUserinfoApplyer_phone" value="<sec:authentication property='principal.phone' htmlEscape="false"/>" class="easyui-textbox" data-options="width:200">
                </td>
            </tr>
            <tr>
                <td class="kv-label" style="width: 120px;">业务名称<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input name="idcReProductbusName" class="easyui-textbox" data-options="required:true,width:200">
                </td>
                <td class="kv-label" style="width: 120px;">意向IDC<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input class="easyui-combobox" data-options="required:true,
                                       valueField: 'value',
                                           textField: 'label',
                                           editable:false,
                                           width:200,
                    url:'<%=request.getContextPath()%>/assetBaseinfo/getIntentionIdcCode.do'" name="idcReProductidcName"  value="${idcReProductidcName}" />

                </td>
                <td class="kv-label" style="width: 120px;">预勘天数(天)<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input name="idcReProductvalidity"  id="idcReProductvalidity" value="30" class="easyui-numberbox" data-options="required:true,width:200,min:0,validType:'length[0,4]'">
                </td>
            </tr>
            <c:if test="${category == 800}">
                <tr>
                    <td class="kv-label" style="width: 120px;">测试开始时间<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content">
                        <input name="lscsStartTime" id="lscsStartTime" class="easyui-datetimebox" data-options="required:true,width:200,min:0">
                    </td>
                    <td class="kv-label" style="width: 120px;">测试结束时间<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content">
                        <input name="lscsEntTime" id="lscsEntTime" class="easyui-datetimebox" data-options="required:true,width:200,min:0">
                    </td>
                </tr>
            </c:if>
        </table>
    </fieldset>

    <fieldset class="fieldsetCls fieldsetHeadCls">
        <legend>&diams;客户信息</legend>
        <table class="kv-table">
            <tr>
                <input type="hidden" value="${idcReProduct.id}" name="id"/>
                <input type="hidden" value="1" name="isActive"/>
                <input type="hidden" value="10" name="prodStatus"/>

                <!-- 额外的参数 -->
                <c:if test="${category == 800}">
                    <input type="hidden" name="idcRunTicket.ticketCategoryTo" value="800"/>
                    <input type="hidden" name="idcRunTicket.ticketCategoryFrom" value="800"/>
                </c:if>

                <c:if test="${category != 800}">
                    <input type="hidden" name="idcRunTicket.ticketCategoryTo" value="100"/>
                    <input type="hidden" name="idcRunTicket.ticketCategoryFrom" value="100"/>
                </c:if>

                <input type="hidden" value="${idcReProduct.catalog}" name="catalog"/>

                <input type="hidden" value="${idcReProduct.prodCategory}" name="prodCategory"/>
                <input type="hidden" name="customerId" value="${idcReCustomer.id}" id="customerId"/>
                <input type="hidden" value="0" name="isRejectTicket"/>

                <c:choose>
                    <%--<c:when test="${idcReProduct.getCatalog() == 800}">
                        <td class="kv-label" style="width:120px;">选择客户</td>
                        <td class="kv-content">
                            <!-- 客户选择框 -->
                            <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.name}" data-options="width:200" name="customerName"  id="customerName_name" />
                        </td>
                    </c:when>--%>
                    <c:when test="${idcReProduct.getProdCategory() == 1}">
                        <td class="kv-label" style="width:120px;">选择客户</td>
                        <td class="kv-content">
                            <!-- 客户选择框 -->
                            <input id="customerName" name="customerName" value="${idcReProduct.customerName}" class="easyui-textbox" data-options="editable:false,required:true,iconAlign:'left',buttonText:'选择',width:200,onClickButton:openWinCustomerNameFun">
                        </td>
                    </c:when>
                    <c:when test="${idcReProduct.getProdCategory() == 0}">
                        <td class="kv-label" style="width:120px;">选择客户</td>
                        <td class="kv-content">
                            <!-- 客户选择框 -->
                            <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.name}" data-options="width:200" name="customerName"  id="customerName_name" />
                        </td>
                    </c:when>
                </c:choose>

                <td class="kv-label" style="width:120px;">客户级别</td>
                <td class="kv-content">
                    <%--<input class="easyui-textbox" readonly="readonly" data-options="width:150" value="${idcReCustomer.customerlevel}"  name="khjb"  id="khjb" />--%>
                    <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,width:200,
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
								}]" value="${idcReCustomer.customerlevel}"  name="khjb"  id="khjb" />
                </td>
                </td>
                <td class="kv-label" style="width:120px;">服务等级</td>
                <td class="kv-content">
                    <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:200,
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
								}]" name="fwdj" id="fwdj" value="${idcReCustomer.sla}"/>

                </td>
            </tr>
            <tr>
                <td class="kv-label" style="width:120px;">单位属性</td>
                <td class="kv-content">
                    <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:200,
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
									value: '6'
								}]" name="dwsx" id="dwsx" value="${idcReCustomer.attribute}"/>
                </td>

                <td class="kv-label" style="width:120px;">证件类型</td>
                <td class="kv-content">
                            <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								width:200,
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

                <td class="kv-label" style="width:120px;">证件号码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.idcardno}" data-options="width:200" name="zjhm"  id="zjhm" />
                </td>
            </tr>
            <tr>
                <td class="kv-label" style="width:120px;">客户地址</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.addr}" data-options="width:200" name="khdz"  id="khdz" />
                </td>

                <td class="kv-label" style="width:120px;">邮政编码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.zipcode}" data-options="width:200" name="yzbm"  id="yzbm" />
                </td>

                <td class="kv-label" style="width:120px;">注册时间</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.createTimeStr}" data-options="width:200" name="cjsj"  id="cjsj" />
                </td>
            </tr>

            <tr>
                <td class="kv-label" style="width:120px;">联系人姓名</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.contactname}" data-options="width:200" name="lxrxm"  id="lxrxm" />
                </td>

                <td class="kv-label" style="width:120px;">联系人证件类型</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.contactIdcardtype}" data-options="width:200" name="lxrzjlx"  id="lxrzjlx" />
                </td>

                <td class="kv-label" style="width:120px;">联系人证件号码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.contactIdcardno}" data-options="width:200" name="lxrzjhm"  id="lxrzjhm" />
                </td>
            </tr>
            <tr>
                <td class="kv-label" style="width:120px;">联系人固定电话</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.contactPhone}" data-options="width:200" name="lxrgdhm"  id="lxrgdhm" />
                </td>

                <td class="kv-label" style="width:120px;">联系人移动电话</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.contactMobile}" data-options="width:200" name="lxrydhm"  id="lxrydhm" />
                </td>

                <td class="kv-label" style="width:120px;">联系人EMAIL</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" value="${idcReCustomer.contactEmail}" data-options="width:200 " name="lxremail"  id="lxremail" />
                </td>
            </tr>
        </table>
    </fieldset>



    <c:if test="${category == 800}">

        <!-- 服务信息 -->
        <fieldset class="fieldsetCls fieldsetHeadCls">
            <legend>&diams;选择域名信息</legend>
            <table class="kv-table">
                <tr>
                    <td class="kv-label" style="width:215px">域名信息<span style="color:red">&nbsp;*</span></td>
                    <td class="kv-content" colspan="3">
                        <div style="position:relative;margin: 0px;" class="checkboxGrpCls" >
                            <div style="float:left">
                                    <span class="checkbackgrd on_check" id="other_check">
                                        <input type="checkbox"  name="domainStatus" id="qtNetServiceId" value="0" class="opacity_default_0 ">其他用户
                                    </span>
                            </div>
                            <div style="float:left">
                                     <span class="checkbackgrd" id="dns_check">
                                        <input type="checkbox"  name="domainStatus"  id="fuwuNetServiceId" value="1" class="opacity_default_0 ">提供域名信息用户
                                     </span>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </fieldset>



        <div id="contract_content">


        </div>

        <div id="vm_content">
        </div>
    </c:if>



    <!-- 订单情况 -->
    <div id="rack_fieldset_content_Id"></div>
    <c:choose>
        <c:when test="${idcReProduct.getCatalog() == 800}">
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls" id="port_fieldsetId">
                <legend>&diams;<span style="color: #dd1144">服务项一：端口带宽</span></legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 120px;">端口带宽占用方式</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" data-options="
                                      valueField: 'value',
                                       textField: 'label',
                                       editable:false,
                                        url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/300'
                                            " name="idcRePortModel.portMode" id="idcRePortModel_portMode" value="300001" />
                        </td>
                        <td class="kv-label" style="width: 120px;">带宽总需求(兆)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox" data-options="precision:0,width:150,min:0,validType:'length[0,11]'" name="idcRePortModel.bandwidth" id="idcRePortModel_bandwidth" value="0" />
                        </td>
                        <td class="kv-label" style="width: 120px;">端口数量(个)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox"
                                   name="idcRePortModel.num"
                                   value="0"
                                   id="idcReRackModel_num"
                                   data-options="precision:0,min:0,validType:'length[0,11]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 120px;">描述</td>
                        <td class="kv-content" colspan="5">
                            <input class="easyui-textbox" value="  "
                                   name="idcRePortModel.desc" id="idcRePortModel_desc"
                                   data-options="multiline:true,height:70,width:525"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <!-- IP租用 -->
            <div id="ip_fieldset_content_Id"></div>
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls"  id="ip_fieldsetId">
                <legend>&diams;<span style="color: #dd1144">服务项二：IP租用</span></legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 85px;">IP性质</td>
                        <td class="kv-content" >
                            <input class="easyui-combobox" data-options="
                               valueField: 'value',
                                   textField: 'label',
                                   editable:false,
                                   width:160,
                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/400'
                                   " name="idcReIpModel.portMode" id="idcReIpModel_portMode" value="400001" />
                        </td>
                        <td class="kv-label" style="width: 120px;">数量(个)</td>
                        <td class="kv-content" colspan="8">
                            <input class="easyui-numberbox"  name="idcReIpModel.num"
                                   value="0"  id="idcReIpModel_num" data-options="precision:0,width:150,min:0,validType:'length[0,5]'">
                        </td>

                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 85px">描述</td>
                        <td class="kv-content" colspan="4">
                            <input  class="easyui-textbox" value="  "
                                    name="idcReIpModel.desc" id="idcReIpModel_desc"
                                    data-options="multiline:true,height:70,width:525"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <!-- 主机租赁 -->
            <div id="server_fieldset_content_Id"></div>
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls"  id="server_fieldsetId">
                <legend>&diams;<span style="color: #dd1144">服务项三：测试主机</span></legend>
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
                                   }],onChange:endChoice" name="idcReServerModel.source" id="idcReServerModel_source" value="请选择" />
                        </td>
                        <td class="kv-label" style="width: 120px;">CPU</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="width:150" placeholder="请输入CPU" value=" "
                                   name="idcReServerModel.CPU" id="idcReServerModel_CPU"/>

                        </td>
                        <td class="kv-label" style="width: 120px;">MEM</td>
                        <td class="kv-content">
                            <input class="easyui-textbox"
                                   name="idcReServerModel.MEM"
                                   value="0"
                                   id="idcReServerModel_MEM"
                                   data-options="precision:0,width:150,min:0,validType:'length[0,300]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 120px;">存储信息</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="width:150" placeholder="存储信息" value=" "
                                   name="idcReServerModel.memory" id="idcReServerModel_memory"/>

                        </td>
                        <td class="kv-label" style="width: 120px;">操作系统</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="width:150" placeholder="操作系统" value=" "
                                   name="idcReServerModel.OS" id="idcReServerModel_OS"/>

                        </td>
                        <td class="kv-label" style="width: 120px;">资源类别</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" data-options="
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
                                   }]" name="idcReServerModel.typeMode" id="idcReServerModel_typeMode" value="请选择" />
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 120px;">需要U位数量（U）</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="width:150" placeholder="需要U位数量" value=" "
                                   name="idcReServerModel.rackUnit" id="idcReServerModel_rackUnit"/>
                        </td>
                        <td class="kv-label" style="width: 120px;">主机数量</td>
                        <td class="kv-content">
                            <input class="easyui-numberspinner" data-options="width:150" placeholder="主机数量" value=" "
                                   name="idcReServerModel.specNumber" id="idcReServerModel_specNumber"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 120px;">描述</td>
                        <td class="kv-content" colspan="5">
                            <input class="easyui-textbox" value="  "
                                   name="idcReServerModel.desc" id="idcReServerModel_desc"
                                   data-options="multiline:true,height:70,width:525"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <div id="add_fieldset_content_Id"></div>
            </fieldset>
        </c:when>
        <c:otherwise>

            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls" id="rack_fieldsetId">
                <legend>&diams;<span style="color: #dd1144">服务项一：机架机位</span></legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 120px;">规格</td>
                        <td class="kv-content">
                            <input class="easyui-combobox"
                                   data-options="valueField: 'value',
                                   textField: 'label',
                                   editable:false,
                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/100' "
                                   name="idcReRackModel.spec"
                                   id="idcReRackModel_spec"
                                   value="100001" />
                        </td>
                        <td class="kv-label" style="width: 120px;">机架(个)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox"  name="idcReRackModel.rackNum" value="0"
                                   id="idcReRackModel_rackNum" data-options="precision:0,min:0,validType:'length[0,5]',width:145"/>
                        </td>
                        <td class="kv-label" style="width: 120px;">机位数(U)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox"  name="idcReRackModel.seatNum" value="0"
                                   id="idcReRackModel_seatNum" data-options="precision:0,min:0,validType:'length[0,5]',width:145"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 120px;">供电类型</td>
                        <td class="kv-content">
                            <input class="easyui-combobox"
                                   data-options="valueField: 'value',
                                   textField: 'label',
                                   editable:false,
                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/200' "
                                   name="idcReRackModel.supplyType"
                                   id="idcReRackModel_supplyType"
                                   value="200001" />
                        </td>
                        <td class="kv-label" style="width: 120px;">描述</td>
                        <td class="kv-content" colspan="5">
                            <input class="easyui-textbox" value="  "
                                   name="idcReRackModel.desc" id="idcReRackModel_desc"
                                   data-options="multiline:true,height:70,width:525"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <div id="port_fieldset_content_Id"></div>
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls" id="port_fieldsetId">
                <legend>&diams;<span style="color: #dd1144">服务项二：端口带宽</span></legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 120px;">端口带宽占用方式</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" data-options="
                                      valueField: 'value',
                                       textField: 'label',
                                       editable:false,
                                        url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/300'
                                            " name="idcRePortModel.portMode" id="idcRePortModel_portMode" value="300001" />
                        </td>
                        <td class="kv-label" style="width: 120px;">带宽总需求(兆)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox" data-options="precision:0,width:150,min:0,validType:'length[0,11]'" name="idcRePortModel.bandwidth" id="idcRePortModel_bandwidth" value="0" />
                        </td>
                        <td class="kv-label" style="width: 120px;">端口数量(个)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox"
                                   name="idcRePortModel.num"
                                   value="0"
                                   id="idcReRackModel_num"
                                   data-options="precision:0,min:0,validType:'length[0,11]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 120px;">描述</td>
                        <td class="kv-content" colspan="5">
                            <input class="easyui-textbox" value="  "
                                   name="idcRePortModel.desc" id="idcRePortModel_desc"
                                   data-options="multiline:true,height:70,width:525"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <!-- IP租用 -->
            <div id="ip_fieldset_content_Id"></div>
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls"  id="ip_fieldsetId">
                <legend>&diams;<span style="color: #dd1144">服务项三：IP租用</span></legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 85px;">IP性质</td>
                        <td class="kv-content" >
                            <input class="easyui-combobox" data-options="
                               valueField: 'value',
                                   textField: 'label',
                                   editable:false,
                                   width:160,
                                   url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/400'
                                   " name="idcReIpModel.portMode" id="idcReIpModel_portMode" value="400001" />
                        </td>
                        <td class="kv-label" style="width: 120px;">数量(个)</td>
                        <td class="kv-content" colspan="8">
                            <input class="easyui-numberbox"  name="idcReIpModel.num"
                                   value="0"  id="idcReIpModel_num" data-options="precision:0,width:150,min:0,validType:'length[0,5]'">
                        </td>

                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 85px">描述</td>
                        <td class="kv-content" colspan="4">
                            <input  class="easyui-textbox" value="  "
                                    name="idcReIpModel.desc" id="idcReIpModel_desc"
                                    data-options="multiline:true,height:70,width:525"/>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <!-- 主机租赁 -->
            <div id="server_fieldset_content_Id"></div>
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls"  id="server_fieldsetId">
                <legend>&diams;<span style="color: #dd1144">服务项四：主机租赁</span></legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 120px;">资源类型</td>
                        <td class="kv-content">
                            <input class="easyui-combobox" data-options="
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
                                   }]" name="idcReServerModel.typeMode" id="idcReServerModel_typeMode" value="请选择" />
                        </td>
                        <td class="kv-label" style="width: 120px;">主机规格</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="width:150" placeholder="请输入主机规格" value=" "
                                   name="idcReServerModel.specNumber" id="idcReServerModel_specNumber"/>

                        </td>
                        <td class="kv-label" style="width: 120px;">数量(个)</td>
                        <td class="kv-content">
                            <input class="easyui-numberbox"
                                   name="idcReServerModel.num"
                                   value="0"
                                   id="idcReServerModel_num"
                                   data-options="precision:0,width:150,min:0,validType:'length[0,11]'"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 120px;">描述</td>
                        <td class="kv-content" colspan="5">
                            <input class="easyui-textbox" value="  "
                                   name="idcReServerModel.desc" id="idcReServerModel_desc"
                                   data-options="multiline:true,height:70,width:525"/>
                        </td>

                    </tr>
                </table>
            </fieldset>
            <div id="add_fieldset_content_Id"></div>
            <!-- 机架机位信息 -->
            <fieldset class="fieldsetCls fieldsetHeadCls" id="add_fieldsetId">
                <legend>&diams;<span style="color: #dd1144">服务项五：业务增值</span></legend>
                <table class="kv-table">
                    <tr>
                        <td class="kv-label" style="width: 50px;">名称</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" value=" "
                                   name="idcReNewlyModel.name" id="idcReNewlyModel_name"
                            />
                        </td>
                        <td class="kv-label" style="width: 87px;">业务分类</td>
                        <td class="kv-content" colspan="4">
                            <input class="easyui-combobox" data-options="
                                       valueField: 'value',
                                           textField: 'label',
                                           editable:false,
                                           url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/800'" name="idcReNewlyModel.category"   id="idcReNewlyModel_category" value="800001" />
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label" style="width: 87px;">描述</td>
                        <td class="kv-content" colspan="4">
                            <input class="easyui-textbox" value="  "
                                   name="idcReNewlyModel.desc" id="idcReNewlyModel_desc"
                                   data-options="multiline:true,height:70,width:525"/>
                        </td>
                    </tr>

                </table>
            </fieldset>
        </c:otherwise>
    </c:choose>

</form>
</body>
<script type="text/javascript">

    /*
    * 选择测试主机：
    *IDC提供测试主机：
        *资源类别（物理机/虚拟机）、* CPU 、* MEM、 *存储信息、*操作系统、*数量、备注
        *
    客户自带测试主机：
        	*主机数量
        	*需要U位（U）

    * */
    function endChoice(newValue,oldValue){

        if(newValue == '1'){
            $("#idcReServerModel_CPU").textbox({disabled:true});
            $("#idcReServerModel_MEM").textbox({disabled:true});
            $("#idcReServerModel_memory").textbox({disabled:true});
            $("#idcReServerModel_OS").textbox({disabled:true});
            $("#idcReServerModel_rackUnit").textbox({disabled:false});
            $("#idcReServerModel_typeMode").textbox({disabled:true});

        }else if(newValue == '2'){
            $("#idcReServerModel_CPU").textbox({disabled:false});
            $("#idcReServerModel_MEM").textbox({disabled:false});
            $("#idcReServerModel_memory").textbox({disabled:false});
            $("#idcReServerModel_OS").textbox({disabled:false});
            $("#idcReServerModel_rackUnit").textbox({disabled:true});
            $("#idcReServerModel_typeMode").textbox({disabled:false})
        }
    }

    function openWinCustomerNameFun(){
        //选择弹出框
        var laySum = top.layer.open({
            type: 2,
            title: '<span style="color:blue">客户人员</span>'+"》选择",
            shadeClose: false,
            shade: 0.8,
            btn: ['确定','关闭'],
            maxmin : true,
            area: ['984px', '600px'],
            content: contextPath+"/customerController/customerQuery.do",
            /*弹出框*/
            cancel: function(index, layero){
                top.layer.close(index);
            },yes: function(index, layero){
                var childIframeid = layero.find('iframe')[0]['name'];
                var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                //返回相应的数据
                var selectedRecord = chidlwin.gridSelectedRecord("gridId");
                var customerName = selectedRecord?selectedRecord['name']:null;
                $("#customerId").val(selectedRecord?selectedRecord['id']:null);
                $("#customerName").textbox('setValue', customerName);
                var value = selectedRecord?selectedRecord['customerlevel']:null;
                var label = value == '10'?"A类个人客户":
                    value == '20'?"B类个人客户":
                        value == '30'?"C类个人客户":
                            value == '40'?"A类集团":
                                value == '50'?"A1类集团":
                                    value == '60'?"A2类集团":
                                        value == '70'?"B类集团":
                                            value == '80'?"B1类集团":
                                                value == '90'?"B2类集团":
                                                    value == '100'?"C类集团":
                                                        value == '110'?"D类集团":
                                                            value == '120'?"Z类集团":
                                                                value == '999'?"其他":
                                                                    null;

                $("#khjb").combobox('setValue', selectedRecord?selectedRecord['customerlevel']:null);
                $("#fwdj").combobox('setValue', selectedRecord?selectedRecord['sla']:null);
                /*单位属性*/
                $("#dwsx").combobox('setValue', selectedRecord?selectedRecord['attribute']:null);
                /*证件类型*/
                $("#zjlx").combobox('setValue', selectedRecord?selectedRecord['idcardtype']:null);

                /*证件号码*/
                $("#zjhm").textbox('setValue', selectedRecord?selectedRecord['idcardno']:null);
                /*客户地址*/
                $("#khdz").textbox('setValue', selectedRecord?selectedRecord['addr']:null);
                /*邮政编码*/
                $("#yzbm").textbox('setValue', selectedRecord?selectedRecord['zipcode']:null);
                /*创建时间*/
                $("#cjsj").textbox('setValue', selectedRecord?selectedRecord['createTimeStr']:null);
                /*联系人姓名*/
                $("#lxrxm").textbox('setValue', selectedRecord?selectedRecord['contactname']:null);
                /*联系人证件类型*/
                $("#lxrzjlx").textbox('setValue', selectedRecord?selectedRecord['contactIdcardtype']:null);
                /*联系人证件号码*/
                $("#lxrzjhm").textbox('setValue', selectedRecord?selectedRecord['contactIdcardno']:null);
                /*联系人固定电话*/
                $("#lxrgdhm").textbox('setValue', selectedRecord?selectedRecord['contactPhone']:null);
                /*联系人移动电话*/
                $("#lxrydhm").textbox('setValue', selectedRecord?selectedRecord['contactMobile']:null);
                /*联系人EMAIL*/
                $("#lxremail").textbox('setValue', selectedRecord?selectedRecord['contactEmail']:null);

                //同时：如果是临时设置，还是设置提供域名信息的所属用户


                top.layer.close(index)
                //刷新列表信息
            },no: function(index, layero){
                top.layer.close(index)
            },end:function(){
                //top.layer.close(index);
            }
        });
    }
    /**
     * 类别
     * @param category
     */
    function serviceApplyImgStatusOnclick(category){
        alert("设置300被选中");
        $("#serviceApplyImgStatus300")
    }
    function form_sbmt(preGrid,grid,winId){
        $("#singleForm").form('submit', {
            url:contextPath+"/actJBPMController/createJbpmEntrance.do",
            onSubmit: function(){

            },
            success:function(data){
                //然后修改下一个form
                var obj = jQuery.parseJSON(data);
                if(obj.success){
                    top.layer.msg(obj.msg, {
                        icon: 1,
                        time: 3000 //（默认是3秒）
                    });
                    var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
                    parent.layer.close(parentIndex);  // 关闭layer
                    if(preGrid){
                        preGrid.datagrid("reload");
                    }
                    if(grid){
                        grid.datagrid("reload");
                    }
                }else{
                    top.layer.msg(obj.msg, {
                        icon: 1,
                        time: 3000 //（默认是3秒）
                    });
                }
            }
        });
    }
</script>
<script src="<%=request.getContextPath() %>/js/jbpm/temporaryJbpm/temporaryJbpm.js"></script>
</html>