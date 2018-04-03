<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <script type="text/javascript">
        $(function(){
            //动态绑定端口操作状态
            var portactive = "${netPort.portactive}";
            $("#portactive").combobox("setValue",portactive);
            //动态绑定端口物理逻辑类型
            var portpltype = "${netPort.portpltype}";
            $("#portpltype").combobox("setValue",portpltype);
            //动态绑定物理端口类别
            var mediatype = "${netPort.mediatype}";
            $("#mediatype").combobox("setValue",mediatype);
            //动态绑定端口管理状态
            var adminstatus = "${netPort.adminstatus}";
            $("#adminstatus").combobox("setValue",adminstatus);
            //动态绑定设备
            var deviceid = "${netPort.deviceid}";
            $("#deviceid").combobox({
                onChange: function (newVal, oldVal) {
                    changeIdcDevice(newVal,oldVal);
                }
            });
            $("#deviceid").combobox("setValue",deviceid);

            //若为查看详情则只能看不能改
            var buttonType = "${buttonType}";
            if (buttonType != null && buttonType == 'view') {
                $('input').attr("readonly", true);
                $('select').combobox('readonly', true);
            }
        })
        //是否显示设备超链接
        function changeIdcDevice(newValue,oldValue){
            var data = '设备';
            if(newValue != null && newValue != ''){
                data = '<a href="javascript:void(0)" onclick="getDeviceDealis()">设备</a>';
            }
            $("#isShowLink").html(data);
        }
        function getDeviceDealis(){
            var deviceId = $("#deviceid").combobox('getValue');
            var url = contextPath + "/device/deviceDetails.do?id=" + deviceId + "&buttonType=view&deviceclass=1";
            openDialogView('设备信息', url,'800px','530px');
        }
    </script>
    <title>编辑端口</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
    <form id="deviceForm" method="post">
        <table class="kv-table">
            <tbody>
            <tr>
                <td class="kv-label">端口名称</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="portname" value="${netPort.portname}">
                </td>
                <td class="kv-label">端口别名</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="alias" value="${netPort.alias}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">端口类型</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="porttype" value="${netPort.porttype}"/>
                </td>
                <td class="kv-label" id="isShowLink">设备</td>
                <td class="kv-content">
                    <select class="easyui-combobox" name="deviceid" id="deviceid"
                            data-options="
                            panelHeight:100,
                            width:200,
                            editable:false,
                            valueField:'ID',
                            textField:'NAME',
                            url:'<%=request.getContextPath() %>/netport/getNetDeviceModel.do'
                          ">
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">对端设备</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="sidedevice"
                           value="${netPort.sidedevice}"/>
                </td>
                <td class="kv-label">端口操作状态</td>
                <td class="kv-content">
                    <select name="portactive" id="portactive" class="easyui-combobox" data-options="width:200">
                        <option value="1">在用</option>
                        <option value="2" selected="selected">空闲</option>
                        <option value="3">测试</option>
                        <option value="4">未知</option>
                        <option value="5">休眠</option>
                        <option value="6">模块不在</option>
                        <option value="7">下层关闭</option>
                        <option value="8">其他</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">端口对应IP</td>
                <td class="kv-content">
                    <input class="easyui-textbox" name="ip" value="${netPort.ip}" data-options="width:200"/>
                </td>
                <td class="kv-label">Mac地址</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="mac" value="${netPort.mac}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">子网掩码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="netmask" value="${netPort.netmask}"/>
                </td>
                <td class="kv-label">对端设备</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="sidedevice"
                           value="${netPort.sidedevice}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">端口带宽(Mbps)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="bandwidth"
                           value="${netPort.bandwidth}"/>
                </td>
                <td class="kv-label">分派带宽(Mbps)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="assignation"
                           value="${netPort.assignation}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">端口物理逻辑类型</td>
                <td class="kv-content">
                    <select name="portpltype" id="portpltype" class="easyui-combobox" data-options="width:200" >
                        <option value="0" selected="selected">物理端口</option>
                        <option value="1">逻辑端口</option>
                    </select>
                </td>
                <td class="kv-label">物理端口类别</td>
                <td class="kv-content">
                    <select name="mediatype" id="mediatype" class="easyui-combobox" data-options="width:200" >
                        <option value="fiber" selected="selected">光口</option>
                        <option value="cable">电口</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">端口管理状态</td>
                <td class="kv-content">
                    <select name="adminstatus" id="adminstatus" class="easyui-combobox" data-options="width:200" >
                        <option value="1" selected="selected">UP</option>
                        <option value="2">down</option>
                    </select>
                </td>
                <td class="kv-label">工单编号</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="ticketId" value="${netPort.ticketId}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">备注</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="note" value="${netPort.note}"/>
                </td>
                <td class="kv-label"></td>
                <td class="kv-content"></td>
            </tr>
            </tbody>
        </table>
        </form>  
    </div>
</div>
</body>
</html>