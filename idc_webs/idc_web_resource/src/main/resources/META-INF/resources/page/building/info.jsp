<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑机楼中心</title>
    <script type="text/javascript">
        $(function () {
            //绑定数据中心
            var locationid = "${idcBuilding.locationid}";
            $("#locationid").combobox({
                onChange: function (newVal, oldVal) {
                    changeIdcLocation(newVal, oldVal);
                }
            });
            $("#locationid").combobox("setValue", locationid);
            //若为查看详情则只能看不能改
            var buttonType = "${buttonType}";
            if (buttonType != null && buttonType == 'view') {
                $('input').attr("readonly", true);
                $('select').combobox('readonly', true);
            }
        })
        function changeIdcLocation(newValue, oldValue) {
            var data = '数据中心';
            if (newValue != null && newValue != '') {
                data = '<a href="javascript:void(0)" onclick="showLocation()">数据中心</a>';
            }
            $("#isShowLocationLink").html(data);
        }
        function showLocation() {
            var locationid = $("#locationid").combobox("getValue");
            var url = contextPath + "/idcLocation/getIdcLocationInfo.do?id=" + locationid+"&buttonType=view";
            openDialogView('数据中心信息', url, '800px', '530px');
        }
    </script>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="buildingForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">机楼名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="name"
                               value="${idcBuilding.name}"/>
                    </td>
                    <td class="kv-label">机楼编码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="code"
                               value="${idcBuilding.code}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label" id="isShowLocationLink">数据中心</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="locationid" id="locationid" data-options="
                             required:true,
                             panelHeight:100,
                            editable:false,
                            width:200,
                            valueField:'id',
                            textField:'name',
                            url:'<%=request.getContextPath() %>/idcBuilding/ajaxLocation'
                            ">
                        </select>
                    </td>
                    <td class="kv-label">电话</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['mobileTelephone'],width:200"
                               value="${idcBuilding.contacttel}" name="contacttel"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">传真</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['faxno'],width:200"
                               value="${idcBuilding.contactfax}" name="contactfax"/>
                    </td>
                    <td class="kv-label">楼层数</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['number','length[0,2]'],width:200"
                               value="${idcBuilding.floornumber}" name="floornumber"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">额定电量(KWH)</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['number'],width:200"
                               value="${idcBuilding.ratedcapacity}"
                               name="ratedcapacity"/>
                    </td>
                    <td class="kv-label">抗震等级</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['intOrFloat'],width:200"
                               value="${idcBuilding.seismicgrade}" name="seismicgrade"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">网关IP地址</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200"
                               value="${idcBuilding.gatewayip}" name="gatewayip"/>
                    </td>
                    <td class="kv-label">互联网出入口带宽(M)</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['number'],width:200"
                               value="${idcBuilding.totalbandwidth}"
                               name="totalbandwidth"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">备注</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="remark"
                               value="${idcBuilding.remark}"/>
                    </td>
                    <td class="kv-label"></td>
                    <td class="kv-content"></td>
                </tr>
                <c:if test="${buttonType eq 'update' or buttonType eq 'view'}">
                    <tr>
                        <td class="kv-content" colspan="4" style="padding: 12px 1px 0px 16px;">机楼统计信息</td>
                    </tr>
                    <tr>
                        <td class="kv-label">空闲机架数</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" disabled data-options="width:200"
                                   value="${idcBuilding.rackspare}"/>
                        </td>
                        <td class="kv-label">机架总数</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" disabled data-options="width:200"
                                   value="${idcBuilding.rackcount}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="kv-label">机架使用率(%)</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" disabled data-options="width:200"
                                   value="${idcBuilding.rackusage}"/>
                        </td>
                        <td class="kv-label"></td>
                        <td class="kv-content"></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </div>
</div>
</div>
<script type="text/javascript">
    var id = "${id}";
    //新增或修改机楼信息
    function doSubmit() {
        $('#buildingForm').form('submit', {
            url: "<%=request.getContextPath() %>/idcBuilding/addBuildingInfo?id=" + id,
            onSubmit: function () {
                return $("#buildingForm").form('validate');
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