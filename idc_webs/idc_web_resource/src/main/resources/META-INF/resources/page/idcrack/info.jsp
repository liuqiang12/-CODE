<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑数据中心</title>
    <script type="text/javascript">
        $(function () {
            //现将其隐藏
            $(".equipment").css("display", "none");
            //绑定机架类型
            var businesstypeId = "${idcRack.businesstypeId}";
            var dftype = "${idcRack.dftype}";
            if (businesstypeId != null && businesstypeId == 'equipment') {
                $(".equipment").css("display", "");
            } else if (businesstypeId != null && businesstypeId == 'cabinet') {
                $(".equipment").css("display", "none");
            }
            if (businesstypeId != null && businesstypeId == 'df' && dftype != null && dftype == 'wiring') {
                $("#businesstypeId").combobox("setValue", dftype);
            } else {
                $("#businesstypeId").combobox("setValue", businesstypeId);
            }
            //绑定机房
            var roomid = "${idcRack.roomid}";
            $("#roomid").combobox({
                onChange: function (newVal, oldVal) {
                    changeIdcRoom(newVal, oldVal);
                }
            });
            $("#roomid").combobox("setValue", roomid);
            //通过机房获取该机房所有模块
            if (roomid != null && roomid != '') {
                loadModule(roomid);
            }
            //绑定机架型号
            var rackmodelid = "${idcRack.rackmodelid}";
            $("#rackmodelid").combobox("setValue", rackmodelid);
            //equipment
            if (businesstypeId != null && businesstypeId == 'equipment') {
                //绑定用途
                var usefor = "${idcRack.usefor}";
                $("#usefor").combobox("setValue", usefor);
                //绑定出租类型
                var renttype = "${idcRack.renttype}";
                $("#renttype").combobox("setValue", renttype);
                //绑定业务状态
                var status = "${idcRack.status}";
                $("#status").combobox("setValue", status);
                //绑定是否安装
                var israckinstalled = "${idcRack.israckinstalled}";
                $("#israckinstalled").combobox("setValue", israckinstalled);
            }
            //cabinet
            <%--if (businesstypeId != null && businesstypeId == 'cabinet') {--%>
            <%--//绑定互联网出入口--%>
            <%--var internetexportId = "${idcRack.internetexportId}";--%>
            <%--$("#internetexportId").combogrid("setValue", internetexportId);--%>
            <%--}--%>
            //绑定电源类型
            var pduPowertype = "${idcRack.pduPowertype}";
            $("#pduPowertype").combobox("setValue", pduPowertype);
            //绑定模块
            var moduleid = "${idcRack.moduleid}";
            $("#moduleid").combobox("setValue", moduleid);

            //若为查看详情则只能看不能改
            var buttonType = "${buttonType}";
            if (buttonType != null && buttonType == 'view') {
                $('input').attr("readonly", true);
                $('select').combobox('readonly', true);
            }
        })
        //是否显示机房超链接
        function changeIdcRoom(newValue, oldValue) {
            var data = '所属机房';
            if (newValue != null && newValue != '') {
                data = '<a href="javascript:void(0)" onclick="showRoom()">所属机房</a>';
            }
            $("#isShowRoomLink").html(data);
            loadModule(newValue);
        }
        //通过机房获取该机房所有模块
        function loadModule(roomId) {
            $.post(contextPath + "/idcRack/queryModuleByRoomId", {roomId: roomId}, function (result) {
                console.log(result);
                var data = [];
                if (result != null && result.length > 0) {
                    for (var i = 0; i < result.length; i++) {
                        data.push({"moduleid": result[i].ID, "modulename": result[i].NAME});
                    }
                }
                $("#moduleid").combobox("loadData", data);
            });
        }
    </script>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="idcRackForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">机架名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="name"
                               value="${idcRack.name}"/>
                    </td>
                    <td class="kv-label">类型</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="businesstypeId" id="businesstypeId"
                                data-options="
                                required:true,
                                panelHeight:100,
                                width:200,
                                editable:false,
                                onChange:changeType">
                            <option value="equipment">客户机柜</option>
                            <option value="cabinet">网络头柜</option>
                            <option value="wiring">配线柜</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">标准名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['wlOrPxOrPtRackReg'],required:true,width:200" name="codeView"
                               value="${idcRack.codeView}"/>
                    </td>
                    <td class="kv-label">机架编码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="code"
                               value="${idcRack.code}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label" id="isShowRoomLink">所属机房</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="roomid" id="roomid"
                                data-options="
                            required:true,
                            panelHeight:100,
                            editable:false,
                            width:200,
                            valueField:'id',
                            textField:'sitename',
                            url:'<%=request.getContextPath() %>/idcRack/ajaxZbMachineroom'
                          ">
                        </select>
                    </td>
                    <td class="kv-label">所属vip机房</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="roomareaid"
                               value="${idcRack.roomareaid}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">机架型号</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="rackmodelid" id="rackmodelid"
                                data-options="
                            panelHeight:100,
                            editable:false,
                            width:200,
                            valueField:'ID',
                            textField:'NAME',
                            url:'<%=request.getContextPath() %>/idcRack/getRackModel'
                          ">
                        </select>
                    </td>
                    <td class="kv-label">归属模块</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="moduleid" id="moduleid"
                                data-options="
                            panelHeight:100,
                            editable:false,
                            width:200,
                            valueField:'moduleid',
                            textField:'modulename'
                          ">
                        </select>
                    </td>
                </tr>
                <tr class="equipment">
                    <td class="kv-label">用途</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="usefor" id="usefor"
                                data-options="
                                panelHeight:100,
                                editable:false,
                                width:200">
                            <option value="1">自用</option>
                            <option value="2">客用</option>
                        </select>
                    </td>
                    <td class="kv-label">出租类型</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="renttype" id="renttype"
                                data-options="
                                panelHeight:100,
                                editable:false,
                                width:200">
                            <option value="0">整架出租</option>
                            <option value="1">按机位出租</option>
                        </select>
                    </td>
                </tr>
                <tr class="equipment">
                    <td class="kv-label">业务状态</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="status" id="status"
                                data-options="
                                panelHeight:100,
                                editable:false,
                                width:200">
                            <option value="20">可用</option>
                            <option value="30">预留</option>
                            <option value="40" selected>空闲</option>
                            <option value="50">预占</option>
                            <option value="55">已停机</option>
                            <option value="60">在服</option>
                            <option value="110">不可用</option>
                        </select>
                    </td>
                    <td class="kv-label">是否安装</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" data-options="panelHeight:100,width:200,editable:false"
                                name="israckinstalled" id="israckinstalled">
                            <option value="0" selected>未安装</option>
                            <option value="1">已安装</option>
                        </select>
                    </td>
                </tr>
                <%--<tr class="equipment">--%>
                <%--<td class="kv-label">IP资源</td>--%>
                <%--<td class="kv-content">--%>
                <%--<input class="easyui-textbox" data-options="width:200" name="ipresource"--%>
                <%--value="${idcRack.ipresource}"/>--%>
                <%--</td>--%>
                <%--<td class="kv-label">上联端口</td>--%>
                <%--<td class="kv-content">--%>
                <%--<input class="easyui-textbox" data-options="width:200" name="topportproperty"--%>
                <%--value="${idcRack.topportproperty}"/>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr id="cabinet">--%>
                <%--<td class="kv-label">互联网出入口</td>--%>
                <%--<td class="kv-content">--%>
                <%--<select class="easyui-combogrid" name="internetexportId" id="internetexportId"--%>
                <%--data-options="--%>
                <%--panelHeight:100,--%>
                <%--editable:false,--%>
                <%--width:200,--%>
                <%--idField:'ID',--%>
                <%--required:true,--%>
                <%--textField:'NAME',--%>
                <%--columns:[[--%>
                <%--{field:'ID',title:'id',width:50},--%>
                <%--{field:'NAME',title:'名字',width:100},--%>
                <%--{field:'BANDWIDTH',title:'带宽',width:120},--%>
                <%--{field:'GATEWAYIP',title:'网关IP',width:120},--%>
                <%--{field:'IPTOTAL',title:'IP总数',width:120},--%>
                <%--{field:'IPFREE',title:'空闲IP',width:120}--%>
                <%--]],--%>
                <%--url:'<%=request.getContextPath() %>/idcRack/getInternetexport'--%>
                <%--">--%>
                <%--</select>--%>
                <%--</td>--%>
                <%--<td class="kv-label"></td>--%>
                <%--<td class="kv-content"></td>--%>
                <%--</tr>--%>
                <tr>
                    <td class="kv-content" colspan="4" style="padding: 12px 1px 0px 16px;">电力信息</td>
                </tr>
                <tr>
                    <td class="kv-label">电源类型</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" data-options="panelHeight:100,width:200,editable:false"
                                name="pduPowertype" id="pduPowertype">
                            <option value="AC">AC</option>
                            <option value="DC">DC</option>
                            <option value="AC/DC">AC/DC</option>
                        </select>
                    </td>
                    <td class="kv-label">额定电量(KWH)</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['intOrFloat'],width:200"
                               name="ratedelectricenergy" value="${idcRack.ratedelectricenergy}"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var rackId = "${rackId}";
    function showRoom(){
        var roomid = $("#roomid").combobox("getValue");
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + roomid + "&buttonType=view";
        openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
    }
    function doSubmit(){
        addIdcRack();
    }
    function addIdcRack (){
        var moduleName = $("#moduleid").combobox('getText');
        $('#idcRackForm').form('submit', {
            url: "<%=request.getContextPath() %>/idcRack/addIdcRackInfo.do?rackId=" + rackId,
            queryParams: {modulename: moduleName},
            onSubmit: function () {
                return $("#idcRackForm").form('validate');
            },
            success: function (data) {
                var re = eval("("+data+")");
                alert(re.msg);
                var parentWin = top.winref[window.name];
                top[parentWin].$('#dg').datagrid("reload");
                var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(index); //再执行关闭
            }
        });
    }
    function changeType(newValue,oldValue){
        if (newValue == 'equipment') {
            $(".equipment").css("display", "");
        } else if (newValue == 'cabinet' || newValue == 'wiring') {
            $(".equipment").css("display", "none");
        }
    }
    //查看机架视图
    function showView2d() {
        window.open(contextPath + '/racklayout/' + rackId);
    }
</script>
</body>
</html>