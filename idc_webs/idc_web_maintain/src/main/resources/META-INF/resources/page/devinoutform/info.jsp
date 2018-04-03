<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑设备出入申请单信息</title>
</head>
<script type="text/javascript">
    //用于增加或删除一个设备信息
    var rowIndex = null;
    $(function () {
        //绑定申请事项
        var rmInOutItem = "${rmDevInOutForm.rmInOutItem}";
        $("#rmInOutItem").combobox("setValue", rmInOutItem);

        //若为查看，则只能看 不能对信息进行操作   并显示创建人信息
        var buttonType = "${buttonType}";
        if (buttonType != null && buttonType == 'view') {
            $('input').attr("readonly", true);
            $('select').combobox('readonly', true);
            $(".easyui-datetimebox").datetimebox('readonly', true);
            $('.easyui-linkbutton').linkbutton('disable');
            rowIndex = "${rmDevListSize}";
        } else if (buttonType != null && buttonType == 'update') {
            rowIndex = "${rmDevListSize}";
        } else {
            rowIndex = 1;
        }
        rowIndex = parseInt(rowIndex);
    })
</script>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="deviceForm" method="post">
            <%-- 设备出入申请单基本信息 --%>
            <fieldset>
                <legend>设备出入申请单基本信息</legend>
                <div>
                    <table class="kv-table">
                        <tr>
                            <td class="kv-label">标题</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="required:true,width:200"
                                       name="rmDevInOutTitle"
                                       value="${rmDevInOutForm.rmDevInOutTitle}"/>
                            </td>
                            <td class="kv-label">机房名称</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="required:true,width:200" name="rmRoomName"
                                       value="${rmDevInOutForm.rmRoomName}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">申请事项</td>
                            <td class="kv-content">
                                <select class="easyui-combobox" name="rmInOutItem" id="rmInOutItem"
                                        data-options="editable:false,width:200">
                                    <option value="1">入库</option>
                                    <option value="2">出库</option>
                                </select>
                            </td>
                            <td class="kv-label">负责人</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" name="rmLead" data-options="width:200"
                                       value="${rmDevInOutForm.rmLead}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">施工单位</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="width:200" name="rmBuildCompany"
                                       value="${rmDevInOutForm.rmBuildCompany}"/>
                            </td>
                            <td class="kv-label">施工人</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="width:200" name="rmBuildMan"
                                       value="${rmDevInOutForm.rmBuildMan}"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">联系人</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="width:200" name="rmContacts"
                                       value="${rmDevInOutForm.rmContacts}"/>
                            </td>
                            <td class="kv-label">出入事由</td>
                            <td class="kv-content">
                                <input class="easyui-textbox" data-options="width:200" name="rmReason"
                                       value="${rmDevInOutForm.rmReason}"/>
                            </td>
                        </tr>
                        <c:if test="${not empty buttonType and buttonType eq 'view'}">
                            <tr>
                                <td class="kv-label">创建时间</td>
                                <td class="kv-content">
                                    <input class="easyui-datetimebox" data-options="width:200"
                                           value="${rmDevInOutForm.rmCreateTimeStr}"/>
                                </td>
                                <td class="kv-label">创建人</td>
                                <td class="kv-content">
                                    <input class="easyui-textbox" data-options="width:200"
                                           value="${rmDevInOutForm.rmCreateUser}"/>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </div>
            </fieldset>
            <fieldset>
                <legend>出入设备信息</legend>
                <div style="position:relative;">
                    <div>
                        <table class="kv-table" id="infoT">
                            <c:choose>
                                <c:when test="${not empty buttonType and buttonType == 'add'}">
                                    <tr>
                                        <td class="kv-content" colspan="4">
                                            <table class="kv-table">
                                                <tr>
                                                    <td class="kv-label">设备名称</td>
                                                    <td class="kv-content">
                                                        <input class="easyui-textbox"
                                                               data-options="required:true,width:200"
                                                               name="rmDevLists[0].rmDevName"/>
                                                    </td>
                                                    <td class="kv-label">设备编码</td>
                                                    <td class="kv-content">
                                                        <input class="easyui-textbox"
                                                               data-options="required:true,width:200"
                                                               name="rmDevLists[0].rmDevCode"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="kv-label">设备数量</td>
                                                    <td class="kv-content">
                                                        <input class="easyui-textbox"
                                                               data-options="validType:['number'],required:true,width:200"
                                                               name="rmDevLists[0].rmDevNum"/>
                                                    </td>
                                                    <td class="kv-label">单位</td>
                                                    <td class="kv-content">
                                                        <input class="easyui-textbox"
                                                               data-options="required:true,width:200"
                                                               name="rmDevLists[0].rmDevUnit"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="kv-label">设备类别</td>
                                                    <td class="kv-content">
                                                        <input class="easyui-textbox"
                                                               data-options="required:true,width:200"
                                                               name="rmDevLists[0].rmDevType"/>
                                                    </td>
                                                    <td class="kv-label">备注</td>
                                                    <td class="kv-content">
                                                        <input class="easyui-textbox" data-options="width:200"
                                                               name="rmDevLists[0].note"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${not empty rmDevInOutForm.rmDevLists}">
                                        <c:forEach items="${rmDevInOutForm.rmDevLists}" var="rmDevList"
                                                   varStatus="index">
                                            <tr>
                                                <td class="kv-content" colspan="4">
                                                    <table class="kv-table">
                                                        <tr>
                                                            <td class="kv-label">设备名称</td>
                                                            <td class="kv-content">
                                                                <input class="easyui-textbox"
                                                                       data-options="required:true,width:200"
                                                                       name="rmDevLists[${index.index}].rmDevName"
                                                                       value="${rmDevList.rmDevName}"/>
                                                            </td>
                                                            <td class="kv-label">设备编码</td>
                                                            <td class="kv-content">
                                                                <input class="easyui-textbox"
                                                                       data-options="required:true,width:200"
                                                                       name="rmDevLists[${index.index}].rmDevCode"
                                                                       value="${rmDevList.rmDevCode}"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="kv-label">设备数量</td>
                                                            <td class="kv-content">
                                                                <input class="easyui-textbox"
                                                                       data-options="validType:['number'],required:true,width:200"
                                                                       name="rmDevLists[${index.index}].rmDevNum"
                                                                       value="${rmDevList.rmDevNum}"/>
                                                            </td>
                                                            <td class="kv-label">单位</td>
                                                            <td class="kv-content">
                                                                <input class="easyui-textbox"
                                                                       data-options="required:true,width:200"
                                                                       name="rmDevLists[${index.index}].rmDevUnit"
                                                                       value="${rmDevList.rmDevUnit}"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="kv-label">设备类别</td>
                                                            <td class="kv-content">
                                                                <input class="easyui-textbox"
                                                                       data-options="required:true,width:200"
                                                                       name="rmDevLists[${index.index}].rmDevType"
                                                                       value="${rmDevList.rmDevType}"/>
                                                            </td>
                                                            <td class="kv-label">备注</td>
                                                            <td class="kv-content">
                                                                <input class="easyui-textbox" data-options="width:200"
                                                                       name="rmDevLists[${index.index}].note"
                                                                       value="${rmDevList.note}"/>
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
    //设备出入申请单ID
    var id = "${id}";
    //新增或修改设备
    function doSubmit() {
        var linkUrl = "<%=request.getContextPath() %>/rmDevInOutForm/saveOrUpdateRmDevInOutFormInfo";
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
        str += '<td class="kv-label">设备名称</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="required:true,width:200" name="rmDevLists[' + rowIndex + '].rmDevName" />';
        str += '</td>';
        str += '<td class="kv-label">设备编码</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="required:true,width:200" name="rmDevLists[' + rowIndex + '].rmDevCode" />';
        str += '</td>';
        str += '</tr>';
        str += '<tr>';
        str += '<td class="kv-label">设备数量</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="validType:[\'number\'],required:true,width:200" name="rmDevLists[' + rowIndex + '].rmDevNum" />';
        str += '</td>';
        str += '<td class="kv-label">单位</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="required:true,width:200" name="rmDevLists[' + rowIndex + '].rmDevUnit"/>';
        str += '</td>';
        str += '</tr>';
        str += '<tr>';
        str += '<td class="kv-label">设备类别</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="required:true,width:200" name="rmDevLists[' + rowIndex + '].rmDevType" />';
        str += '</td>';
        str += '<td class="kv-label">备注</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="width:200" name="rmDevLists[' + rowIndex + '].note"/>';
        str += '<a href="javascript:void(0)" style="margin-left: 5px;" class="easyui-linkbutton" data-options="iconCls:\'icon-clear\'" onclick=deleteRowT(this)></a>';
        str += '</td>';
        str += '</tr>';
        str += '</table>';
        str += '</td>';
        str += '</tr>';
        var targetObj = $("#infoT").append(str);
        //重新渲染form表单
        $.parser.parse(targetObj);
        rowIndex += 1;
    }
    function deleteRowT(obj) {
        var index = obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.rowIndex;
        console.log("删除行=====" + index);
        $("#infoT")[0].deleteRow(index);
        rowIndex -= 1;
    }
</script>
</body>
</html>