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
    <title>合同信息列表</title>
</head>
<style type="text/css">
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0 !important;
    }
    table.kv-table{
        margin-bottom: 0 !important;
    }
    .fieldsetCls{
        border-color: #eee !important;
    }

    .fieldsetCls > *{
        /*增加border变色*/
        color: #565757 !important;
    }
</style>
<body>
<form method="post" id="singleForm" action="<%=request.getContextPath() %>/customerController/customerFormSaveOrUpdate.do">
    <fieldset style="border-color: #c0e7fb;" class="fieldsetCls">
        <legend>&diams;用户信息</legend>
        <table class="kv-table">
            <tr>
                <td class="kv-label" style="width: 200px;">名称</td>
                <td class="kv-content">
                    <input type="hidden" name="id" value="${idcReCustomer.id}" id="id"/>
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="name" value="${idcReCustomer.name}"  id="name" data-options="validType:'length[0,64]'"/>
                </td>
                <td class="kv-label">单位属性</td>
                <td class="kv-content">
                    <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								required:true,
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
								required:true,
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
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="idcardno" value="${idcReCustomer.idcardno}"  id="idcardno" data-options="validType:'length[0,64]'"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">邮政编码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="zipcode" value="${idcReCustomer.zipcode}"  id="zipcode" data-options="validType:'length[0,6]'"/>
                </td>

                <td class="kv-label">单位地址</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="addr" value="${idcReCustomer.addr}"  id="addr" data-options="validType:'length[0,128]'"/>
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
								editable:false,
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
								textField: 'label',
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
								textField: 'label',
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
								textField: 'label',
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
    <fieldset style="border-color: #c0e7fb;" class="fieldsetCls">
        <legend>&diams;用户联系人</legend>
        <table class="kv-table">
            <tr>
                <td class="kv-label" style="width: 200px;">名称</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contactname" value="${idcReCustomer.contactname}"  id="contactname" data-options="validType:'length[0,64]'"/>
                </td>
                <td class="kv-label">证件类型</td>
                <td class="kv-content">
                    <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								required:true,
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
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contactIdcardno" value="${idcReCustomer.contactIdcardno}"  id="contactIdcardno" data-options="validType:'length[0,64]'"/>
                </td>

                <td class="kv-label">固定电话</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contactPhone" value="${idcReCustomer.contactPhone}"  id="contactPhone" data-options="validType:'length[0,15]'"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">移动号码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contactMobile" value="${idcReCustomer.contactMobile}"  id="contactMobile" data-options="validType:'length[0,15]'"/>
                </td>

                <td class="kv-label">邮箱</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contactEmail" value="${idcReCustomer.contactEmail}"  id="contactEmail" data-options="validType:'length[0,64]'"/>
                </td>
            </tr>
        </table>
    </fieldset>




    <c:if test="${isOpportunity == '1'}">
        <table class="kv-table">
                <tr>
                    <td class="kv-label" style="width: 200px;"><span style="color: #00bbee">2017年8月预计费用</span></td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contactname" value="${cost8}" data-options="validType:'length[0,64]'"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label"><span style="color: #00bbee">2017年9月预计费用</span></td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contactIdcardno" value="${cost9}" data-options="validType:'length[0,64]'"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label"><span style="color: #00bbee">2017年10月预计费用</span></td>
                    <td class="kv-content">
                        <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contactMobile" value="${cost10}" data-options="validType:'length[0,15]'"/>
                    </td>
                </tr>
            </table>
    </c:if>

</form>
</body>
<script type="application/javascript">
    function form_sbmt(gridId){
        $("#singleForm").form('submit', {
            url:contextPath+"/customerController/customerFormSaveOrUpdate.do",
            onSubmit: function(){
                if(!$("#singleForm").form("validate")){
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    return false;
                }
            },
            success:function(data){
                //然后修改下一个form
                var obj = jQuery.parseJSON(data);
                console.log(obj);
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
</html>