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
        //若为查看，则只能看 不能对信息进行操作   并显示创建人信息
        var buttonType = "${buttonType}";
        if (buttonType != null && buttonType == 'view') {
            $('input').attr("readonly", true);
            $('select').combobox('readonly', true);
        }
    })
</script>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="deviceForm" method="post">
            <table class="kv-table">
                <tr>
                    <td class="kv-label">物资类别名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="rmMaterialTypeName"
                               value="${rmMaterialType.rmMaterialTypeName}"/>
                    </td>
                    <td class="kv-label">物资类别编码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="rmMaterialTypeCode"
                               value="${rmMaterialType.rmMaterialTypeCode}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">备注</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="note"
                               value="${rmMaterialType.note}"/>
                    </td>
                    <td class="kv-label"></td>
                    <td class="kv-content"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    //物资类别ID
    var id = "${id}";
    //新增或修改物资类别
    function doSubmit() {
        var linkUrl = "<%=request.getContextPath() %>/rmMaterialType/saveOrUpdateRmMaterialTypeInfo";
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