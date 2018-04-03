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
            //动态绑定产权性质
            var ownertype = "${idcDevice.ownertype}";
            $("#ownertype").combobox("setValue",ownertype);
            //动态绑定设备状态
            var status = "${idcDevice.status}";
            if (status == null || status == "") {
                status = 40;
            }
            $("#status").combobox("setValue",status);
            //动态绑定网络设备类别
            var netDeviceDeviceclass = "${idcDevice.netDevice.deviceclass}";
            $("#netDeviceDeviceclass").combobox({
                onChange: function (newVal, oldVal) {
                    changeRoutType(newVal==null?1:newVal,oldVal);
                }
            });
            $("#netDeviceDeviceclass").combobox("setValue",netDeviceDeviceclass);
            //根据网络设备类别动态绑定网络设备类型
            var netDeviceRouttype = "${idcDevice.netDevice.routtype}";
            $("#netDeviceRouttype").combobox({
                valueField:'id',
                textField:'text'
            });
            changeRoutType(netDeviceDeviceclass,0);
            $("#netDeviceRouttype").combobox("setValue",netDeviceRouttype);
            //动态绑定厂商
            var vendor = "${idcDevice.vendor}";
            $("#vendor").combobox("setValue",vendor);
            //动态绑定机架
            var rackId = "${idcDevice.rackId}";
            changeIdcRack(rackId, 0);
            //动态绑定电源类型
            var pwrPowertype = "${idcDevice.pwrPowertype}";
            $("#pwrPowertype").combobox("setValue",pwrPowertype);
            //动态绑定网络层次
            var networklayer = "${idcDevice.netDevice.networklayer}";
            $("#networklayer").combobox("setValue", networklayer);
            //若为查看详情则只能看不能改
            var buttonType = "${buttonType}";
            if (buttonType != null && buttonType == 'view') {
                $('input').attr("readonly", true);
                $('select').combobox('readonly', true);
                $('#openRack').linkbutton('disable');
                $(".easyui-datetimebox").datetimebox('readonly', true);
            }
        })
        //根据网络设备类别改变网络设备类型
        function changeRoutType(newVal,oldVal){
            //先清空设备类型
            $("#netDeviceRouttype").combobox("setValue",'');
            //往设备类型中填入值
            var data=[];
            if(newVal == 1){
                data=[{"id":1,"text":"二层交换机"},{"id":2,"text":"三层交换机"},{"id":3,"text":"路由器"},{"id":4,"text":"核心路由器"},
                    {"id":5,"text":"核心交换机"},{"id":6,"text":"CMTS路由器"},{"id":7,"text":"无线设备"},{"id":8,"text":"光纤交换机"},
                    {"id": 9, "text": "BRAS设备"}, {"id": 91, "text": "其它网络设备"}];
            }else if(newVal == 2){
                data = [{"id": 10, "text": "防火墙"}, {"id": 11, "text": "安全应用网关"}, {"id": 92, "text": "其它安全设备"}];
            }else if(newVal == 3){
                data = [{"id": 12, "text": "访问接入设备"}, {"id": 13, "text": "信令通信设备"}, {
                    "id": 14,
                    "text": "光通信设备"
                }, {"id": 93, "text": "其它通信设备"}];
            }
            $("#netDeviceRouttype").combobox("loadData",data);
        }
        //是否显示机架超链接
        function changeIdcRack(newValue,oldValue){
            var data = '归属机架';
            if(newValue != null && newValue != ''){
                data = '<a href="javascript:void(0)" onclick="getRackDealis()">归属机架</a>';
            }
            $("#isShowLink").html(data);
        }
        //打开机架列表   客户架和网络架
        function openRackListWin() {
            var url = contextPath + '/idcRack/preUpordownForDeviceRackList?type=device';
            var index = top.layer.open({
                type: 2,
                area: ['800px', '530px'],
                title: '机架列表',
                maxmin: true,
                content: url,
                btn: ['确定', '关闭'],
                success: function (layero, index) {
                    var name = layero.find('iframe')[0].name;
                    top.winref[name] = window.name;
                },
                yes: function (index, layero) {
                    var body = top.layer.getChildFrame('body', index);
                    var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                    var rackInfo = iframeWin.contentWindow.returnRackInfo();
                    top.layer.close(index);
                    $("#rackName").textbox('setValue', rackInfo.NAME);
                    $("#rackId").val(rackInfo.ID);
                    changeIdcRack(rackInfo.ID, 0);
                },
                cancel: function (index) {
                }
            });
        }
    </script>
    <title>编辑设备</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="deviceForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">设备名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="name"
                               value="${idcDevice.name}"/>
                    </td>
                    <td class="kv-label">设备编码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="code"
                               value="${idcDevice.code}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label" id="isShowLink">归属机架</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" name="rackName" id="rackName" disabled data-options="width:172"
                               value="${idcrack.name}"/>
                        <a href="javascript:void(0)" id="openRack" class="easyui-linkbutton"
                           data-options="iconCls:'icon-add-ext',onClick:openRackListWin"></a>
                        <input type="hidden" name="rackId" id="rackId" value="${idcDevice.rackId}"/>
                    </td>
                    <td class="kv-label">安装位置</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['number'],width:200" name="uinstall"
                               value="${idcDevice.uinstall}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">设备高度</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['number'],width:200" name="uheight"
                               value="${idcDevice.uheight}"/>
                    </td>
                    <td class="kv-label">额定功耗</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['number'],width:200" name="power"
                               value="${idcDevice.power}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">电源类别</td>
                    <td class="kv-content">
                        <select name="pwrPowertype" id="pwrPowertype" class="easyui-combobox"
                                data-options="editable:false,width:200">
                            <option value="DC">DC</option>
                            <option value="AC">AC</option>
                        </select>
                    </td>
                    <td class="kv-label">联系人</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="owner" value="${idcDevice.owner}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">厂商</td>
                    <td class="kv-content">
                        <select name="vendor" id="vendor" class="easyui-combobox"
                                data-options="required:true,editable:false,width:200">
                            <option value="0">华为</option>
                            <option value="1">思科</option>
                            <option value="2">阿尔卡特</option>
                            <option value="3">3Com</option>
                            <option value="4">HP</option>
                            <option value="5">Linux</option>
                            <option value="6">Microsoft</option>
                            <option value="17">D-Link</option>
                            <option value="54">Juniper</option>
                            <option value="61">H3C</option>
                        </select>
                    </td>
                    <td class="kv-label">型号</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="model" value="${idcDevice.model}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">联系人电话</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['mobileTelephone'],width:200"
                               name="phone"
                               value="${idcDevice.phone}"/>
                    </td>
                    <td class="kv-label">产权性质</td>
                    <td class="kv-content">
                        <select name="ownertype" id="ownertype" class="easyui-combobox"
                                data-options="editable:false,width:200">
                            <option value="1">自建</option>
                            <option value="2">租用</option>
                            <option value="3">客户</option>
                            <option value="4">自有业务</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">设备状态</td>
                    <td class="kv-content">
                        <select name="status" id="status" class="easyui-combobox"
                                data-options="editable:false,width:200">
                            <option value="20">可用</option>
                            <option value="40">空闲</option>
                            <option value="50">预占</option>
                            <option value="55">已停机</option>
                            <option value="60">在服</option>
                            <option value="110">不可用</option>
                        </select>
                    </td>
                    <td class="kv-label">工单编号</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['number'],width:200" name="ticketId"
                               value="${idcDevice.ticketId}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">出保时间</td>
                    <td class="kv-content">
                        <input class="easyui-datetimebox" data-options="width:200" name="insurancedate"
                               value="${idcDevice.insurancedateStr}"/>
                    </td>
                    <td class="kv-label">设备版本号</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="deviceVersion"
                               value="${idcDevice.deviceVersion}"/>
                    </td>
                </tr>
                <!-- 网络设备信息 -->
                <tr>
                    <td class="kv-label">网络设备类别</td>
                    <td class="kv-content">
                        <select name="netDevice.deviceclass" id="netDeviceDeviceclass" class="easyui-combobox"
                                data-options="required:true,editable:false,width:200">
                            <option value="1">网络设备</option>
                            <option value="2">安全设备</option>
                            <option value="3">通信设备</option>
                        </select>
                    </td>
                    <td class="kv-label">网络设备类型</td>
                    <td class="kv-content">
                        <select name="netDevice.routtype" id="netDeviceRouttype" class="easyui-combobox"
                                data-options="required:true,editable:false,width:200">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">SNMP采集名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="netDevice.routname"
                               value="${idcDevice.netDevice.routname}"/>
                    </td>
                    <td class="kv-label">网络层次</td>
                    <td class="kv-content">
                        <select name="netDevice.networklayer" id="networklayer" class="easyui-combobox"
                                data-options="required:true,editable:false,width:200">
                            <option value="核心">核心</option>
                            <option value="接入">接入</option>
                            <option value="汇聚">汇聚</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">管理IP地址</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['ip'],width:200"
                               name="netDevice.ipaddress"
                               value="${idcDevice.netDevice.ipaddress}"/>
                    </td>
                    <td class="kv-label">设备描述</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="description"
                               value="${idcDevice.description}"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var id = "${id}";
    var flag = "${flag}";
    var rackId = "${idcDevice.rackId}";
    var uinstall = "${idcDevice.uinstall}";
    var uheight = "${idcDevice.uheight}";
    function doSubmit(){
        addDevice();
    }
    function upOrDwonRack(){
        upOrDwon();
    }
    function quickUpOrDwonRack() {
        quickUpOrDwon();
    }
    //新增或修改设备
    function addDevice(){
        var linkUrl = "<%=request.getContextPath() %>/device/addDeviceInfo.do?id="+id+"&deviceclass=1";
        $('#deviceForm').form('submit', {
            url: linkUrl,
            onSubmit: function () {
                return $("#deviceForm").form('validate');
            },
            success: function (data) {
                var obj = eval('(' + data + ')');
                alert(obj.msg);
                if(obj.state){
                    var parentWin = top.winref[window.name];
                    top[parentWin].$('#dg').datagrid("reload");
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                }
            }
        });
    }
    function upOrDwon(){
        if (uheight == null || uheight == 0) {
            layer.msg("设备没有高度，不能上下架");
            return;
        }
        if (rackId == null || rackId == '') {
            var inx = top.layer.open({
                type: 2,
                title: '机架列表',
                shadeClose: false,
                shade: 0.8,
                btn: ['确定', '关闭'],
                maxmin: true,
                area: ['800px', '530px'],
                content: contextPath + '/idcRack/preUpordownForDeviceRackList',
                /*弹出框*/
                cancel: function (index, layero) {
                    top.layer.close(index);
                }, yes: function (index, layero) {
                    var childIframeid = layero.find('iframe')[0]['name'];
                    var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                    var rackIdN = chidlwin.doSubmit();
                    if (rackIdN == null) {
                        layer.msg("没有选择上架机架");
                        return;
                    }
                    top.layer.close(index);
                    window.open(contextPath + '/racklayout/' + rackIdN + '?deviceId=' + id);
                }
            })
        } else {
            window.open(contextPath + '/racklayout/' + rackId + '?deviceId=' + id);
        }
    }
    //快速上下架
    function quickUpOrDwon() {
        if (uheight == null || uheight == 0 || uinstall == null || uinstall == 0) {
            layer.msg("设备数据不完整，不能快速上下架");
            return;
        }
        if (rackId == null || rackId == '') {
            var inx = top.layer.open({
                type: 2,
                title: '机架列表',
                shadeClose: false,
                shade: 0.8,
                btn: ['确定', '关闭'],
                maxmin: true,
                area: ['800px', '530px'],
                content: contextPath + '/idcRack/preUpordownForDeviceRackList',
                /*弹出框*/
                cancel: function (index, layero) {
                    top.layer.close(index);
                }, yes: function (index, layero) {
                    var childIframeid = layero.find('iframe')[0]['name'];
                    var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                    var rackIdN = chidlwin.doSubmit();
                    if (rackIdN == null) {
                        layer.msg("没有选择上架机架");
                        return;
                    }
                    top.layer.close(index);
                    var title = "";
                    if (flag == 'dwon') {
                        title = "是否确认快速下架？";
                    } else {
                        title = "是否确认快速上架？";
                    }
                    layer.confirm(title, {btn: ['确定', '取消']}, function (index) {
                        $.post(contextPath + "/device/upOrDwonRack.do", {
                            deviceid: id,
                            flag: flag,
                            rackId: rackId,
                            uheight: uheight,
                            uinstall: uinstall
                        }, function (result) {
                            alert(result.msg);
                            if (result.state) {
                                var parentWin = top.winref[window.name];
                                top[parentWin].$('#dg').datagrid("reload");
                                var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                top.layer.close(indexT); //再执行关闭
                            }
                        });
                        layer.close(index);
                    });
                }
            })
        } else {
            var title = "";
            if (flag == 'dwon') {
                title = "是否确认快速下架？";
            } else {
                title = "是否确认快速上架？";
            }
            layer.confirm(title, {btn: ['确定', '取消']}, function (index) {
                $.post(contextPath + "/device/upOrDwonRack.do", {
                    deviceid: id,
                    flag: flag,
                    rackId: rackId,
                    uheight: uheight,
                    uinstall: uinstall
                }, function (result) {
                    alert(result.msg);
                    if (result.state) {
                        var parentWin = top.winref[window.name];
                        top[parentWin].$('#dg').datagrid("reload");
                        var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        top.layer.close(indexT); //再执行关闭
                    }
                });
                layer.close(index);
            });
        }
    }
    function getRackDealis(){
        var rackId = $("#rackId").val();
        var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + rackId + "&businesstype=other&buttonType=view";
        openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
    }
</script>
</body>
</html>