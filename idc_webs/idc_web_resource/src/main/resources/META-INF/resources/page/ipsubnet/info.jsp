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
    <style>
        table.yes-not.kv-table td.kv-label {
            width: 130px
        }

        table.kv-table {
            margin-bottom: 20px
        }

        table.kv-table td.kv-label {
            height: 26px;
            font-size: 12px
        }

        table.kv-table td.kv-content {
            height: 26px;
            font-size: 12px
        }

        table.kv-table td.kv-content a {
            color: #1da02b;
            text-decoration: none
        }

        table.kv-table td.kv-content a:hover {
            text-decoration: underline
        }

        .kv-table {
            border-right: 1px solid #cacaca \9;
            *border-right: 1px solid #cacaca
        }

        .kv-table .kv-table-row {
            border-bottom: 1px solid #cacaca
        }

        .kv-table .kv-table-row .kv-item {
            padding-left: 134px
        }

        .kv-table .kv-table-row .kv-item .kv-label {
            float: left;
            padding: 0 10px;
            margin-left: -134px;
            width: 112px;
            background: #f5f5f5;
            border: 1px solid #cacaca;
            border-bottom: none;
            border-top: none
        }

        .kv-table .kv-table-row .kv-item .kv-content-wrap {
            float: left;
            width: 100%
        }

        .kv-table .kv-table-row .kv-item .kv-content {
            padding: 10px
        }

        .kv-table .kv-table-row.col-3 .kv-item-wrap {
            float: left;
            width: 33.33%
        }

        .kv-table .kv-table-row.col-2 .kv-item-wrap {
            float: left;
            width: 33.33%
        }

        table.kv-table {
            width: 100%
        }

        table.kv-table .kv-label {
            padding: 0 10px;
            width: 114px;
            background: #f5f5f5;
            border: 1px solid #cacaca;
            border-top: none
        }

        table.kv-table td.kv-content,
        table.kv-table td.kv-label {
            height: 29px;
            padding: 5px 0;
            border-bottom: 1px solid #cacaca;
            font-size: 14px;
            padding-left: 20px;
        }

        table.kv-table tr:first-child td.kv-content,
        table.kv-table tr:first-child td.kv-label {
            border-top: 1px solid #cacaca;
        }

        table.kv-table tr td.kv-content:last-child {
            border-right: 1px solid #cacaca
        }

        table.kv-table tr .button {
            text-align: center;
            border-radius: 0;
            text-indent: 0;
            height: 32px
        }

        table.kv-table .kv-content {
            width: 260px;
            padding: 5px 10px
        }

        table.kv-table .textarea-wrap textarea {
            width: 98%
        }

        .tabs-header {
            background-color: #fff;
            border-width: 0
        }

        .tabs li {
            border-top: 1px solid #bfbfbf;
            border-radius: 3px 3px 0 0
        }

        .tabs li.tabs-selected {
            border-top: 2px solid #1da02b
        }

        .tabs li.tabs-selected a.tabs-inner {
            color: #000;
            background-color: #fff
        }

        .tabs li a.tabs-inner {
            color: #000;
            background-color: #e3e3e3
        }

        .tabs li a.tabs-inner .tabs-title {
            font-size: 14px
        }

    </style>
    <title>编辑</title>

</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="locationForm" method="post" action="#">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">子网地址</td>
                    <td class="kv-content">
                        <input type="hidden" name="id" value="${id}" id="id"/>
                        <input class="easyui-textbox" data-options="required:true,onChange:change" <c:if test="${id>0}">readonly</c:if> name="subnetip"
                               id="subnetip"
                               value="${idcIpSubnet.subnetip}"/>
                    </td>
                    <td class="kv-label">掩码</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" data-options="required:true,max:32,min:1,onChange:change" <c:if test="${id>0}">readonly</c:if>
                               name="mask" id="mask"
                               value="${idcIpSubnet.mask}"/>/位
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">开始地址</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true" readonly id="begip" name="begip"
                               value="${idcIpSubnet.begip}"/>
                        <%--<input type="hidden" name="begip" value="${idcIpSubnet.begip}" id=""/>--%>
                    </td>
                    <td class="kv-label">结束地址</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true" readonly id="endip" name="endip"
                               value="${idcIpSubnet.endip}"/>
                        <%--<input type="hidden" name="endip" value="${idcIpSubnet.endip}"/>--%>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">编码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="" name="code" id="code"
                               value="${idcIpSubnet.code}"/>
                    </td>
                    <td class="kv-label">用途</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="" name="usefor" id="usefor"
                               value="${idcIpSubnet.usefor}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">说明</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" name="remark" id="remark" value="${idcIpSubnet.remark}"></input>
                    </td>
                    <td class="kv-label">数据中心</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" name="localid" id="localid" value="${idcIpSubnet.localid}" data-options="
                          url:'<%=request.getContextPath() %>/idcLocation/list.do',
                          valueField: 'id',
                          textField: 'name',
                          loadFilter: function (data) {
                                return data.rows
                           }
                        "></input>
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
    var cansum = true;
    function doSubmit() {
        if(cansum==true){
            $('#locationForm').form('submit', {
                url: "<%=request.getContextPath() %>/resource/ipsubnet/save",
                onSubmit: function () {
                    var flag=  $("#locationForm").form('validate')
                    if(flag==true){
                        cansum=false;
                    }
                    return flag;//$("#locationForm").form('validate');
                },
                success: function (data) {
                    cansum=true;
                    var data = eval('(' + data + ')');
                    if(data.state){
                        top.layer.msg('保存成功');
                        var parentWin = top.winref[window.name];
                        top[parentWin].$('#dg').datagrid("reload");
                        var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        top.layer.close(index); //再执行关闭
                    }else{
                        top.layer.msg('保存失败');
                    }

                }
            });
        }else{
            top.layer.msg("不要重复提交")
        }

    }
</script>
</html>