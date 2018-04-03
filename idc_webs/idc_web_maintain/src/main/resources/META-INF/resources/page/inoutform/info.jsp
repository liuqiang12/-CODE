<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑人员进出临时申请单信息</title>
</head>
<script type="text/javascript">
    //用于增加或删除一个用户信息
    var rowIndex = null;
    $(function () {
        //若为查看，则只能看 不能对信息进行操作   并显示创建人信息
        var buttonType = "${buttonType}";
        if (buttonType != null && buttonType == 'add') {
            $("#rmNum").textbox('setValue', 1);
            rowIndex = 1;
        } else if (buttonType != null && buttonType == 'view') {
            $('input').attr("readonly", true);
            $('select').combobox('readonly', true);
            $(".easyui-datetimebox").datetimebox('readonly', true);
            $('.easyui-linkbutton').linkbutton('disable');
            rowIndex = "${rmTmpInOutForm.rmNum}";
        } else {
            rowIndex = "${rmTmpInOutForm.rmNum}";
        }
        rowIndex = parseInt(rowIndex);
    })
</script>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="deviceForm" method="post">
            <%---人员进出临时申请单基本信息 --%>
            <fieldset>
                <legend>人员进出临时申请单基本信息</legend>
                <div>
                    <table class="kv-table">
                        <tr>
                            <td class="kv-label">机房名称</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="required:true,width:200" name="rmRoomName"
                                       value="${rmTmpInOutForm.rmRoomName}"/>
                            </td>
                            <td class="kv-label">申请人</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="width:200" name="rmApplicant"
                                       value="${rmTmpInOutForm.rmApplicant}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">开始时间</td>
                            <td class="kv-content">
                                <input class="easyui-datetimebox" name="rmStartTime" data-options="width:200"
                                       value="${rmTmpInOutForm.rmStartTimeStr}"/>
                            </td>
                            <td class="kv-label">结束时间</td>
                            <td class="kv-content">
                                <input class="easyui-datetimebox" name="rmEndTime" data-options="width:200"
                                       value="${rmTmpInOutForm.rmEndTimeStr}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">项目负责人</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="width:200" name="rmLead"
                                       value="${rmTmpInOutForm.rmLead}"/>
                            </td>
                            <td class="kv-label">来人单位</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="required:true,width:200" name="rmCompany"
                                       value="${rmTmpInOutForm.rmCompany}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">出入事由</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="required:true,width:200" name="rmContent"
                                       value="${rmTmpInOutForm.rmContent}"/>
                            </td>
                            <td class="kv-label">人数</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="validType:['number'],width:200" readonly
                                       name="rmNum" id="rmNum"
                                       value="${rmTmpInOutForm.rmNum}"/>
                            </td>
                        </tr>
                        <c:if test="${not empty buttonType and buttonType eq 'view'}">
                            <tr>
                                <td class="kv-label">创建时间</td>
                                <td class="kv-content">
                                    <input class="easyui-datetimebox" data-options="width:200"
                                           value="${rmTmpInOutForm.rmCreateTimeStr}"/>
                                </td>
                                <td class="kv-label">创建人</td>
                                <td class="kv-content">
                                    <input class="easyui-textbox" data-options="width:200"
                                           value="${rmTmpInOutForm.rmCreateUser}"/>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </div>
            </fieldset>
            <%---  进出人员详细信息 --%>
            <fieldset>
                <legend>人员进出临时申请单基本信息</legend>
                <div style="position:relative;">
                    <div>
                        <table class="kv-table" id="infoT">
                            <c:choose>
                                <c:when test="${not empty buttonType and buttonType == 'add'}">
                                    <tr>
                                        <td class="kv-content" colspan="4">
                                            <table class="kv-table">
                                                <tr>
                                                    <td class="kv-label">姓名</td>
                                                    <td class="kv-content">
                                                        <input class="easyui-textbox"
                                                               data-options="required:true,width:200"
                                                               name="rmTmpRegisters[0].rmTmpRegisterName"/>
                                                    </td>
                                                    <td class="kv-label">证件号</td>
                                                    <td class="kv-content">
                                                        <input class="easyui-textbox"
                                                               data-options="required:true,width:200"
                                                               name="rmTmpRegisters[0].rmTmpRegisterCard"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="kv-label">联系电话</td>
                                                    <td class="kv-content">
                                                        <input class="easyui-textbox"
                                                               data-options="validType:['mobileTelephone'],required:true,width:200"
                                                               name="rmTmpRegisters[0].rmTmpRegisterPhone"/>
                                                    </td>
                                                    <td class="kv-label">是否录指纹</td>
                                                    <td class="kv-content">
                                                        <%--<select name="rmTmpRegisters[0].rmIsFingerPrint"--%>
                                                                <%--class="easyui-combobox"--%>
                                                                <%--data-options="editable:false,width:200">--%>
                                                            <%--<option value="2">未录指纹</option>--%>
                                                            <%--<option value="1">已录指纹</option>--%>
                                                        <%--</select>--%>
                                                        <input name="rmTmpRegisters[0].rmIsFingerPrint"  class="easyui-combobox" data-options="
                                                            width:200,
                                                            valueField: 'value',
                                                            textField: 'label',
                                                            editable:false,
                                                            data: [{
                                                                label: '未录指纹',
                                                                value: '2'
                                                            },{
                                                                label: '已录指纹',
                                                                value: '1'
                                                            }]"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${not empty rmTmpInOutForm.rmTmpRegisters}">
                                        <c:forEach items="${rmTmpInOutForm.rmTmpRegisters}" var="rmTmpRegister"
                                                   varStatus="index">
                                            <tr>
                                                <td class="kv-content" colspan="4">
                                                    <table class="kv-table">
                                                        <tr>
                                                            <td class="kv-label">姓名</td>
                                                            <td class="kv-content">
                                                                <input class="easyui-textbox"
                                                                       data-options="required:true,width:200"
                                                                       name="rmTmpRegisters[${index.index}].rmTmpRegisterName"
                                                                       value="${rmTmpRegister.rmTmpRegisterName}"/>
                                                            </td>
                                                            <td class="kv-label">证件号</td>
                                                            <td class="kv-content">
                                                                <input class="easyui-textbox"
                                                                       data-options="required:true,width:200"
                                                                       name="rmTmpRegisters[${index.index}].rmTmpRegisterCard"
                                                                       value="${rmTmpRegister.rmTmpRegisterCard}"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="kv-label">联系电话</td>
                                                            <td class="kv-content">
                                                                <input class="easyui-textbox"
                                                                       data-options="validType:['mobileTelephone'],required:true,width:200"
                                                                       name="rmTmpRegisters[${index.index}].rmTmpRegisterPhone"
                                                                       value="${rmTmpRegister.rmTmpRegisterPhone}"/>
                                                            </td>
                                                            <td class="kv-label">是否录指纹</td>
                                                            <td class="kv-content">
                                                                <%--<select name="rmTmpRegisters[${index.index}].rmIsFingerPrint"--%>
                                                                        <%--class="easyui-combobox"--%>
                                                                        <%--data-options="editable:false,width:200">--%>
                                                                    <%--<option value="2"--%>
                                                                            <%--<c:if test="${rmTmpRegister.rmIsFingerPrint eq '2'}">selected="selected"</c:if>>--%>
                                                                        <%--未录指纹--%>
                                                                    <%--</option>--%>
                                                                    <%--<option value="1"--%>
                                                                            <%--<c:if test="${rmTmpRegister.rmIsFingerPrint eq '1'}">selected="selected"</c:if>>--%>
                                                                        <%--已录指纹--%>
                                                                    <%--</option>--%>
                                                                <%--</select>--%>
                                                                    <input name="rmTmpRegisters[${index.index}].rmIsFingerPrint"  class="easyui-combobox" data-options="
                                                                        width:200,
                                                                        valueField: 'value',
                                                                        textField: 'label',
                                                                        editable:false,
                                                                        data: [{
                                                                            label: '未录指纹',
                                                                            value: '2',
                                                                            <c:if test='${rmTmpRegister.rmIsFingerPrint eq "2"}'>selected:true</c:if>
                                                                        },{
                                                                            label: '已录指纹',
                                                                            value: '1',
                                                                            <c:if test='${rmTmpRegister.rmIsFingerPrint eq "1"}'>selected:true</c:if>
                                                                        }]"/>
                                                                <c:if test="${not empty buttonType and buttonType eq 'update'}">
                                                                    <a href="javascript:void(0)"
                                                                       style="margin-left: 5px;"
                                                                       class="easyui-linkbutton"
                                                                       data-options="iconCls:'icon-clear'"
                                                                       onclick=deleteRowT(this)></a>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </table>
                    </div>
                    <c:if test="${not empty buttonType and buttonType != 'view'}">
                        <div style="position: absolute;right:30px;bottom:38px;">
                            <a href="javascript:void(0)" class="easyui-linkbutton"
                               data-options="iconCls:'icon-add-ext',onClick:addRow"></a>
                        </div>
                    </c:if>
                </div>
            </fieldset>
        </form>
    </div>
</div>
<script type="text/javascript">
    //申请单ID
    var id = "${id}";
    //新增或修改
    function doSubmit() {
        var linkUrl = "<%=request.getContextPath() %>/rmTmpInOutForm/saveOrUpdateRmInOutFormInfo";
        $('#deviceForm').form('submit', {
            url: linkUrl,
            queryParams: {id: id},
            onSubmit: function () {
                return $("#deviceForm").form('validate');
            },
            success: function (data) {
                var obj = eval('(' + data + ')');
                alert(obj.msg);
                if (obj.state) {
                    var parentWin = top.winref[window.name];
                    var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top[parentWin].$('#table').datagrid("reload");
                    top.layer.close(indexT); //再执行关闭
                }
            }
        });
    }
    function addRow() {
        console.log("添加行====" + rowIndex);
        var str = '<tr>';
        str += '<td class="kv-content" colspan="4">';
        str += '<table class="kv-table">';
        str += '<tr>';
        str += '<td class="kv-label">姓名</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="required:true,width:200" name="rmTmpRegisters[' + rowIndex + '].rmTmpRegisterName" />';
        str += '</td>';
        str += '<td class="kv-label">证件号</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="required:true,width:200" name="rmTmpRegisters[' + rowIndex + '].rmTmpRegisterCard" />';
        str += '</td>';
        str += '</tr>';
        str += '<tr>';
        str += '<td class="kv-label">联系电话</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="validType:[\'mobileTelephone\'],required:true,width:200" name="rmTmpRegisters[' + rowIndex + '].rmTmpRegisterPhone" />';
        str += '</td>';
        str += '<td class="kv-label">是否录指纹</td>';
        str += '<td class="kv-content">';
        str += '<input name="rmTmpRegisters['+rowIndex+'].rmIsFingerPrint"  class="easyui-combobox" data-options="';
        str += 'width:200,valueField:\'value\',textField: \'label\',editable:false,';
        str += 'data: [{label: \'未录指纹\',value: \'2\'},{label: \'已录指纹\',value: \'1\'}]"/>';
        // str += '<select name="rmTmpRegisters[' + rowIndex + '].rmIsFingerPrint" class="easyui-combobox" ';
        // str += 'data-options="editable:false,width:200"> ';
        // str += '   <option value="2">未录指纹</option> ';
        // str += '    <option value="1">已录指纹</option> ';
        // str += '</select>';
        str += '<a href="javascript:void(0)" style="margin-left: 5px;" class="easyui-linkbutton" data-options="iconCls:\'icon-clear\'" onclick=deleteRowT(this)></a>';
        str += '</td>';
        str += '</tr>';
        str += '</table>';
        str += '</td>';
        str += '</tr>';
        var targetObj = $("#infoT").append(str);
        //重新渲染form表单
        $.parser.parse(targetObj);
        setRmNumValue("add");
    }
    function deleteRowT(obj) {
        var index = obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.rowIndex;
        console.log("删除行=====" + index);
        $("#infoT")[0].deleteRow(index);
        setRmNumValue("del");
    }
    function setRmNumValue(type) {
        var rmNum = $("#rmNum").textbox('getValue');
        if (rmNum == null || rmNum == '') {
            rmNum = 0;
        } else {
            rmNum = parseInt(rmNum);
        }
        if (type == 'add') {
            rmNum += 1;
            rowIndex += 1;
        } else {
            rmNum -= 1;
            rowIndex -= 1;
        }
        $("#rmNum").textbox('setValue', rmNum);
    }
</script>
</body>
</html>