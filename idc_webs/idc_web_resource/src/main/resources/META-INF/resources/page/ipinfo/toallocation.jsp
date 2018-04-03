<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/themes\css\form.css"/>
    <title>编辑</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="locationForm" method="post" action="#">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">IP地址</td>
                    <td class="kv-content">
                        <input type="hidden" name="id" value="${id}" id="id"/>
                        <input class="easyui-textbox" data-options="required:true" name="subnetip" style="width: 300px"  id="subnetip" value=""/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">备注</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" style="width: 300px" data-options="required:true,multiline:true"  name="remark" id="remark" value=""/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ipsubnet/ipcal.js"></script>
<script type="text/javascript">
    function change() {
        var subnetip = $("#subnetip").textbox("getValue");
        var mask = $("#mask").textbox("getValue");
        $("#begip").textbox("setValue","");
        $("#endip").textbox("setValue","");
        console.log("______变更")
        if(subnetip!=""&&mask!="")
            //var ss = validateNetSeg(subnetip,mask);
        //console.log(ss);
        calNBFL(subnetip, mask)
    }
    /**
     * 根据输入的地址 和掩码 计算 地址网段
     * @param ipAddress	ip地址
     * @param netMask	掩码(IP形式)
     * @returns {String}网段地址
     */
    function validateNetSeg(ipAddress,netMask){
        netMask=netMask.toString();
        var tag=netMask.match(/^\d+$/);
        if(tag!=null){
            netMask=bitToMaskStr(netMask);
        }
        var ipAddressElements = ipAddress.split(".");
        var netMaskElements = netMask.split(".");
        try {
            var result ="";
            var size = ipAddressElements.length;
            for (var i = 0; i < size; i++) {
                var ipAddresInt = parseInt(ipAddressElements[i]);
                var maskInt = parseInt(netMaskElements[i]);
                if (i == 3)
                    result+=(((ipAddresInt & maskInt) | 0));
                else
                    result+=(ipAddresInt & maskInt);
                if (i < size - 1) {
                    result+=".";
                }
            }
            return result;
        } catch (e) {
            return null;
        }
    }
    /**
     * 将掩码位 转换为 IP 形式的掩码
     * @param maskBit		掩码位
     * @returns {String}	IP形式的掩码
     */
    function bitToMaskStr(maskBit){
        var bitNetMask = "";
        for (var i = 0; i < maskBit; i++)
            bitNetMask += "1";
        for (var i = 0; i < 32 - maskBit; i++)
            bitNetMask += "0";

        var netMask = "";
        for (var i = 0; i < 4; i++) {
            if (i != 0)
                netMask += ".";
            netMask += parseInt(bitNetMask.substring(8 * i, 8 * (i + 1)),2);
        }
        return netMask;
    }
    function doSubmit() {
        $('#locationForm').form('submit', {
            url: "<%=request.getContextPath() %>/resource/ipsubnet/save",
            onSubmit: function () {
                return $("#locationForm").form('validate');
            },
            success: function (data) {
                var data = eval('(' + data + ')');
                if(data.state){
                    top.layer.msg('保存成功');
                    var parentWin = top.winref[window.name];
                    top[parentWin].$('#dg').datagrid("reload");
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                }

            }
        });
    }
</script>
</html>