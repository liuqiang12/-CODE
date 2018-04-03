<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑数据中心</title>
</head>
<script type="text/javascript">
    $(function () {
        //若为查看详情则只能看不能改
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
        <form id="locationForm" method="post">
        <table class="kv-table">
            <tbody>
            <tr>
                <td class="kv-label">名称</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="required:true,width:200" name="name" id="name" value="${idcLocation.name}"/>
                </td>
                <td class="kv-label">编码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="required:true,width:200" name="code" id="code" value="${idcLocation.code}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">地址</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="required:true,width:200" name="address" id="address" value="${idcLocation.address}"/>
                </td>
                <td class="kv-label">省份</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="province" id="province" value="${idcLocation.province}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">地市</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="city" id="city" value="${idcLocation.city}"/>
                </td>
                <td class="kv-label">区县</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="district" id="district" value="${idcLocation.district}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">邮编</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['zipCode'],width:200" name="zipcode"
                           id="zipcode" value="${idcLocation.zipcode}"/>
                </td>
                <td class="kv-label">联系人</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="contactperson" id="contactperson" value="${idcLocation.contactperson}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">电话</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['mobileTelephone'],width:200"
                           name="contacttel" id="contacttel" value="${idcLocation.contacttel}"/>
                </td>
                <td class="kv-label">传真</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['faxno'],width:200" name="contactfax"
                           id="contactfax" value="${idcLocation.contactfax}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">额定电量(KWH)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['number'],width:200" name="ratedcapacity"
                           id="ratedcapacity" value="${idcLocation.ratedcapacity}"/>
                </td>
                <td class="kv-label">互联网出入口带宽(G)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['number'],width:200" name="totalbandwidth"
                           id="totalbandwidth" value="${idcLocation.totalbandwidth}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">网关IP</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="gatewayip" id="gatewayip" value="${idcLocation.gatewayip}"/>
                </td>
                <td class="kv-label">备注</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="remark" id="remark" value="${idcLocation.remark}"/>
                </td>
            </tr>
            </tbody>
        </table>
        </form>
    </div>
</div>
</div>
<script type="text/javascript">
    var locationId = "${id}";
    function doSubmit() {
        $('#locationForm').form('submit', {
            url: "<%=request.getContextPath() %>/idcLocation/add.do?id="+locationId,
            onSubmit: function () {
                return $("#locationForm").form('validate');
            },
            success: function (data) {
                var obj = eval('(' + data + ')');
                alert(obj.msg);
                if (obj.state) {
                    var parentWin = top.winref[window.name];
                    top[parentWin].$('#dg').datagrid("reload");
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                }
            }
        });
    }
</script>
</body>
</html>