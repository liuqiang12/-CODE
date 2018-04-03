<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
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

                <td class="kv-label">注册时间</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:150" name="createTimeStr" value="${idcReCustomer.createTimeStr}"  id="createTimeStr" data-options="validType:'length[0,128]'"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">单位地址</td>
                <td class="kv-content" colspan="3">
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true,width:592" name="addr" value="${idcReCustomer.addr}"  id="addr" data-options="validType:'length[0,128]'"/>
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
