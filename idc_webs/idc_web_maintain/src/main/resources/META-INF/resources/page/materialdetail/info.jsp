<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑物资出入申请单信息</title>
</head>
<script type="text/javascript">
    $(function () {
        //绑定物资类别
        var rmMaterialTypeId = "${rmMaterialDetail.rmMaterialTypeId}";
        $("#rmMaterialTypeId").combobox("setValue", rmMaterialTypeId);
        //绑定出入类型
        var rmMaterialInOutType = "${rmMaterialDetail.rmMaterialInOutType}";
        $("#rmMaterialInOutType").combobox("setValue", rmMaterialInOutType);

        //若为查看，则只能看 不能对信息进行操作   并显示创建人信息
        var buttonType = "${buttonType}";
        if (buttonType != null && buttonType == 'view') {
            $('input').attr("readonly", true);
            $('select').combobox('readonly', true);
            $(".easyui-datetimebox").datetimebox('readonly', true);
        }
    })
</script>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="deviceForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">物资类别</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="rmMaterialTypeId" id="rmMaterialTypeId"
                                data-options="
                            required:true,
                            panelHeight:100,
                            editable:false,
                            width:200,
                            valueField:'ID',
                            textField:'NAME',
                            url:'<%=request.getContextPath() %>/rmMaterialType/queryAllMaterialTypeModel'
                          ">
                        </select>
                    </td>
                    <td class="kv-label">客户名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="rmMaterialCustomer"
                               value="${rmMaterialDetail.rmMaterialCustomer}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">出入类型</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="rmMaterialInOutType" id="rmMaterialInOutType"
                                data-options="editable:false,width:200">
                            <option value="1">入库</option>
                            <option value="2">出库</option>
                        </select>
                    </td>
                    <td class="kv-label">出入时间</td>
                    <td class="kv-content">
                        <input class="easyui-datetimebox" name="rmMaterialInOutTime" data-options="width:200"
                               value="${rmMaterialDetail.rmMaterialInOutTimeStr}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">物资数量</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" name="rmMaterialNum" data-options="validType:['number'],width:200"
                               value="${rmMaterialDetail.rmMaterialNum}"/>
                    </td>
                    <td class="kv-label">运单号</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="rmMaterialCode"
                               value="${rmMaterialDetail.rmMaterialCode}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">处理人</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="rmMaterialDisposeMan"
                               value="${rmMaterialDetail.rmMaterialDisposeMan}"/>
                    </td>
                    <td class="kv-label">备注</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="note"
                               value="${rmMaterialDetail.note}"/>
                    </td>
                </tr>
                <c:if test="${not empty buttonType and buttonType eq 'view'}">
                    <tr>
                        <td class="kv-label">创建时间</td>
                        <td class="kv-content">
                            <input class="easyui-datetimebox" data-options="width:200"
                                   value="${rmMaterialDetail.rmCreateTimeStr}"/>
                        </td>
                        <td class="kv-label">创建人</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="width:200"
                                   value="${rmMaterialDetail.rmCreateUser}"/>
                        </td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    //物资出入申请单ID
    var id = "${id}";
    //新增或修改物资出入申请单
    function doSubmit() {
        var linkUrl = "<%=request.getContextPath() %>/rmMaterialDatail/saveOrUpdateRmMaterialDatailInfo";
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
</script>
</body>
</html>