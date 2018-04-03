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
    /*$(function () {
        //若为查看，则只能看 不能对信息进行操作   并显示创建人信息
        var buttonType = "${buttonType}";
        if (buttonType != null && buttonType == 'view') {
            $('input').attr("readonly", true);
            $('select').combobox('readonly', true);
            $(".easyui-datetimebox").datetimebox('readonly', true);
        }
    })*/
</script>
<body>
<div class="easyui-layout" fit="true">
    <fieldset class="fieldsetCls fieldsetHeadCls">
        <legend>&diams;值班信息</legend>
        <table class="kv-table">
            <tr>
                <td class="kv-label" style="width: 200px;">值班人<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input class="easyui-textbox" name="contractname" value="张某"  id="contractname" data-options="required:true,validType:'length[0,128]',width:250"/>
                </td>
                <td class="kv-label">值班领导<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input class="easyui-textbox" name="contractno" value="李某"  id="contractno" data-options="required:true,validType:'length[0,128]',width:250"/>
                </td>
            </tr>　
        </table>
    </fieldset>
    <fieldset class="fieldsetCls fieldsetHeadCls">
        <legend>&diams;登记信息</legend>
        <table class="kv-table">
            <tr>
                <td class="kv-label" style="width: 200px;">登记人<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input class="easyui-textbox" name="contractname" value=""  id="contractname" data-options="required:true,validType:'length[0,128]',width:250"/>
                </td>
                <td class="kv-label">登记证件<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input class="easyui-textbox" name="contractno" value=""  id="contractno" data-options="required:true,validType:'length[0,128]',width:250"/>
                </td>
            </tr>　
            <tr>
                <td class="kv-label" style="width: 200px;">进入时间<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input class="easyui-datetimebox" name="contractname" value=""  id="contractname" data-options="required:true,validType:'length[0,128]',width:250"/>
                </td>
                <td class="kv-label">出去时间<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input class="easyui-datetimebox" name="contractno" value=""  id="contractno" data-options="required:true,validType:'length[0,128]',width:250"/>
                </td>
            </tr>　
            <tr>
                <td class="kv-label">事由<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content" colspan="3">
                    <input class="easyui-textbox" data-options="width:200" name="rmContent"
                           value="${rmTmpInOutForm.rmContent}"/>
                </td>
            </tr>　
        </table>
    </fieldset>　
</div>
<script type="text/javascript">
    //申请单ID
    var id = "${id}";
    //新增或修改设备
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
</script>
</body>
</html>