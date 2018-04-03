<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
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
<form method="post" id="singleForm" action="<%=request.getContextPath() %>/resourceApplyController/productFormSaveOrUpdate.do">
    <fieldset class="fieldsetCls fieldsetHeadCls">
        <legend>&diams;客户需求</legend>
        <table class="kv-table">
            <tr>
                <td class="kv-label" style="width: 200px;">订单名称</td>
                <td class="kv-content">
                    <input type="hidden" value="${idcReProduct.id}" name="id"/>
                    <input class="easyui-textbox" data-options="required:true" readonly="readonly" value="${idcReProduct.prodName}" name="prodName" id="prodName" data-options="validType:'length[0,255]',width:150"/>
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
                    <input id="customerName"readonly="readonly"   value="${idcReProduct.customerName}" class="easyui-textbox" data-options="editable:false,required:true,iconAlign:'left',buttonText:'选择' ">
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
                    <input class="easyui-datebox" readonly="readonly" value="${idcReProduct.createTime}" name="createTime" id="createTime"/>
                </td>
            </tr>
            <tr>
                <td colspan="8">
                    <div style="position:relative;margin: 17px 42px 3px 207px">
                        <div style="float:left">
                            <span class="checkbackgrd" id="rack_check">
                                <input type="checkbox" name="serviceApplyImgStatus" value="100" class="opacity_default_0">
                            </span> 机架机位
                        </div>
                        <div style="float:left">
                             <span class="checkbackgrd" id="port_check">
                                <input type="checkbox" name="serviceApplyImgStatus" value="200"  class="opacity_default_0">
                            </span>端口带宽
                        </div>
                        <div style="float:left">
                             <span class="checkbackgrd" id="ip_check">
                                <input type="checkbox" disabled name="serviceApplyImgStatus" value="300" class="opacity_default_0">
                            </span>IP租用
                        </div>
                        <div style="float:left">
                             <span class="checkbackgrd" id="server_check">
                                <input type="checkbox" disabled name="serviceApplyImgStatus" value="400"  class="opacity_default_0">
                            </span>主机租赁
                        </div>
                        <div style="float:left">
                             <span class="checkbackgrd" id="add_check">
                                <input type="checkbox" disabled name="serviceApplyImgStatus" value="500" class="opacity_default_0">
                            </span> 增值业务
                        </div>
                    </div>
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
                        <input class="easyui-combobox"  data-options="valueField: 'value',textField: 'label',editable:false,required:true,data: [{label: '开放式机架',value: '1'},{label: '-------',value: '0'}]" name="idcReRackModel.spec" id="idcReRackModel_spec" readonly="readonly"  value="${idcReRackModel.SPEC}" />
                    </td>
                    <td class="kv-label">机架/机位数(个)</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" readonly="readonly"  name="idcReRackModel.rackNum" value="${idcReRackModel.RACKNUM}"
                               id="idcReRackModel_rackNum" data-options="precision:0,min:0,validType:'length[0,11]'"/>
                    </td>
                    <td class="kv-label" style="width: 200px;">额定功率(千瓦)</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" readonly="readonly"  name="idcReRackModel.ratedPower" value="${idcReRackModel.RATEDPOWER}"
                               id="idcReRackModel_ratedPower" data-options="precision:0,min:0,validType:'length[0,12]'"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label" style="width: 200px;">描述</td>
                    <td class="kv-content" colspan="5">
                        <input class="easyui-textbox" readonly="readonly"  value="${idcReRackModel.DESC}"
                               name="idcReRackModel.desc" id="idcReRackModel_desc"
                               data-options="multiline:true,height:28"/>
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
                        <input class="easyui-combobox"  readonly="readonly"  data-options="
                                   valueField: 'value',
                                       textField: 'label',
                                       editable:false,
                                       required:true,

                                       data: [{
                                       label: 'B类独享',
                                       value: '1'
                                   },{
                                       label: '-------',
                                       value: '0'
                                   }]" name="idcRePortModel.portMode" id="idcRePortModel_portMode" value="${idcRePortModel.PORTMODE}" />
                    </td>
                    <td class="kv-label" style="width: 200px;">带宽大小(兆)</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" readonly="readonly" data-options="precision:0,width:150,min:0,validType:'length[0,11]'" name="idcRePortModel.bandwidth" id="idcRePortModel_bandwidth" value="${idcRePortModel.bandwidth}" />
                    </td>
                    <td class="kv-label">数量(个)</td>
                    <td class="kv-content">
                        <input readonly="readonly"  class="easyui-numberbox"
                               name="idcRePortModel.num"
                               value="${idcRePortModel.NUM}"
                               id="idcReRackModel_num"
                               data-options="precision:0,min:0,validType:'length[0,11]'"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label" style="width: 200px;">描述</td>
                    <td class="kv-content" colspan="5">
                        <input readonly="readonly"  class="easyui-textbox" value="${idcRePortModel.DESC}"
                               name="idcRePortModel.desc" id="idcRePortModel_desc"
                               data-options="multiline:true,height:28"/>
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
                        <input readonly="readonly"  class="easyui-textbox" value="${idcReNewlyModel.NAME}"
                               name="idcReNewlyModel.name" id="idcReNewlyModel_name"
                        />
                    </td>
                    <td class="kv-label">业务分类</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" readonly="readonly"  data-options="
                                       valueField: 'value',
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
                               data-options="multiline:true,height:28"/>
                    </td>
                </tr>

            </table>
        </fieldset>
    </c:if>
</form>
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

    /**
     * 类别
     * @param category
     */
    function serviceApplyImgStatusOnclick(category){
        alert("设置300被选中");
        $("#serviceApplyImgStatus300")
    }
</script>
</html>