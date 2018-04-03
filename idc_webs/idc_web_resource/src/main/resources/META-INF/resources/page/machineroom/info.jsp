<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑机房</title>
    <script type="text/javascript">
        $(function () {
            //绑定机楼
            var buildingid = "${zbMachineroom.buildingid}";
            $("#buildingid").combobox({
                onChange: function (newVal, oldVal) {
                    changeIdcBuilding(newVal, oldVal);
                }
            });
            $("#buildingid").combobox("setValue", buildingid);
            //绑定机房类别
            var roomcategory = "${zbMachineroom.roomcategory}";
            $("#roomcategory").combobox("setValue", roomcategory);
            //绑定机房类型
            var roomtype = "${zbMachineroom.roomtype}";
            $("#roomtype").combobox("setValue", roomtype);
            //绑定业务业务类型
            var usefor = "${zbMachineroom.usefor}";
            $("#usefor").combobox("setValue", usefor);
            //若为查看详情则只能看不能改
            var buttonType = "${buttonType}";
            if (buttonType != null && buttonType == 'view') {
                $('input').attr("readonly", true);
                $('select').combobox('readonly', true);
            }
        })
        //是否显示机架超链接
        function changeIdcBuilding(newValue, oldValue) {
            var data = '机楼';
            if (newValue != null && newValue != '') {
                data = '<a href="javascript:void(0)" onclick="showBuilding()">机楼</a>';
            }
            $("#isShowLink").html(data);
        }
    </script>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="zbMachineroomForm" method="post">
        <table class="kv-table">
            <tbody>
            <tr>
                <td class="kv-label">机房名称</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['zbMachineroomReg'],required:true,width:200" name="sitename"
                           value="${zbMachineroom.sitename}"/>
                </td>
                <td class="kv-label">机房编码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="required:true,width:200" name="sitenamesn"
                           value="${zbMachineroom.sitenamesn}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label" id="isShowLink">机楼</td>
                <td class="kv-content">
                    <select class="easyui-combobox" name="buildingid" id="buildingid" data-options="
                             required:true,
                             panelHeight:100,
                            editable:false,
                            width:200,
                            valueField:'id',
                            textField:'name',
                            url:'<%=request.getContextPath() %>/zbMachineroom/ajaxBuilding'
                            ">
                    </select>
                </td>
                <td class="kv-label">机房类别</td>
                <td class="kv-content">
                    <select name="roomcategory" id="roomcategory" class="easyui-combobox"
                            data-options="editable:false,width:200">
                        <option value="0">模块化机房</option>
                        <option value="1">普通机房</option>
                        <option value="2">热管背板机房</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">机房等级</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['number','length[0,2]'],width:200"
                           name="grade" value="${zbMachineroom.grade}"/>
                </td>
                <td class="kv-label">机房类型</td>
                <td class="kv-content">
                    <select name="roomtype" id="roomtype" class="easyui-combobox"
                            data-options="editable:false,width:200">
                        <option value="1">IDC机房</option>
                        <option value="2">传输机房</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">最大容量(机架数)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['number','length[0,4]'],width:200"
                           name="maxracks"
                           value="${zbMachineroom.maxracks}"/>
                </td>
                <td class="kv-label">总设计电量(KVA)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="designelectricity"
                           value="${zbMachineroom.designelectricity}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">面积(m&sup2;)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['intOrFloat'],width:200" name="area"
                           value="${zbMachineroom.area}"/>
                </td>
                <td class="kv-label">楼层高度(m)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="floorheight"
                           value="${zbMachineroom.floorheight}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">负责人</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="responsibleuserid"
                           value="${zbMachineroom.responsibleuserid}"/>
                </td>
                <td class="kv-label">空调备用</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="airconditioningreserve"
                           value="${zbMachineroom.airconditioningreserve}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">机房出入口带宽(M)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['number'],width:200" name="totalbandwidth"
                           value="${zbMachineroom.totalbandwidth}"/>
                </td>
                <td class="kv-label">宽度(m)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['intOrFloat'],width:200" name="widthM"
                           value="${zbMachineroom.widthM}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">长度(m)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['intOrFloat'],width:200" name="heightM"
                           value="${zbMachineroom.heightM}"/>
                </td>
                <td class="kv-label">业务类型</td>
                <td class="kv-content">
                    <select name="usefor" id="usefor" class="easyui-combobox" data-options="editable:false,width:200">
                        <option value="1">自有业务</option>
                        <option value="2">网络核心机房</option>
                        <option value="3">政企业务</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="kv-label">备注</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="remark"
                           value="${zbMachineroom.remark}"/>
                </td>
                <td class="kv-label"></td>
                <td class="kv-content"></td>
            </tr>
            <c:if test="${buttonType eq 'update' or buttonType eq 'view'}">
                <tr>
                    <td class="kv-content" colspan="4" style="padding: 12px 1px 0px 16px;">机房统计信息</td>
                </tr>
                <tr>
                    <td class="kv-label">接入端口总数</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" disabled data-options="width:200"
                               value="${zbMachineroom.accessporttotal}"/>
                    </td>
                    <td class="kv-label">接入端口使用率(%)</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" disabled data-options="width:200"
                               value="${zbMachineroom.accessportusage}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">空闲机架数</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" disabled data-options="width:200"
                               value="${zbMachineroom.rackspare}"/>
                    </td>
                    <td class="kv-label">机架总数</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" disabled data-options="width:200"
                               value="${zbMachineroom.rackcount}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">机架使用率(%)</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" disabled data-options="width:200"
                               value="${zbMachineroom.rackusage}"/>
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
    function doSubmit() {
        $('#zbMachineroomForm').form('submit', {
            url: "<%=request.getContextPath() %>/zbMachineroom/addZbMachineroomInfo.do?id=" + id,
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
    function showBuilding() {
        var buildingId = $("#buildingid").combobox("getValue");
        var url = contextPath + "/idcBuilding/getIdcBuildingInfo.do?id=" + buildingId + "&buttonType=view";
        openDialogView('机楼信息', url, '800px', '530px');
    }
    function showView2d() {
        window.open(contextPath + '/roomlayout/' + id);
    }
</script>
</body>
</html>