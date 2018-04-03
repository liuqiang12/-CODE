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
            //动态绑定厂商
            var vendor = "${idcDevice.vendor}";
            $("#vendor").combobox("setValue",vendor);
            //动态绑定机架
            var rackId = "${idcDevice.rackId}";
            changeIdcRack(rackId, 0);
            //动态绑定电源类型
            var pwrPowertype = "${idcDevice.pwrPowertype}";
            $("#pwrPowertype").combobox("setValue",pwrPowertype);
            //若为查看详情则只能看不能改
            var buttonType = "${buttonType}";
            if (buttonType != null && buttonType == 'view') {
                $('input').attr("readonly", true);
                $('select').combobox('readonly', true);
                $('#openRack').linkbutton('disable');
                $(".easyui-datetimebox").datetimebox('readonly', true);
            }
        })
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
            var url = contextPath + '/idcRack/preUpordownForDeviceRackList';
            //openDialog('机架列表',url,'800px','530px');
            var index = top.layer.open({
                type: 2,
                area: ['800px', '530px'],
                title: '机架列表',
                maxmin: true, //开启最大化最小化按钮
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
                    <input class="easyui-textbox" data-options="required:true,width:200" name="name" value="${idcDevice.name}"/>
                </td>
                <td class="kv-label">设备编码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="required:true,width:200" name="code"  value="${idcDevice.code}"/>
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
                    <select name="pwrPowertype" id="pwrPowertype" class="easyui-combobox" data-options="editable:false,width:200">
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
                    <select name="vendor" id="vendor" class="easyui-combobox" data-options="required:true,editable:false,width:200">
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
                    <input class="easyui-textbox" data-options="validType:['mobileTelephone'],width:200" name="phone"
                           value="${idcDevice.phone}"/>
                </td>
                <td class="kv-label">产权性质</td>
                <td class="kv-content">
                    <select name="ownertype" id="ownertype" class="easyui-combobox" data-options="editable:false,width:200">
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
                    <select name="status" id="status" class="easyui-combobox" data-options="editable:false,width:200">
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
            <!-- 主机设备信息 -->
            <tr>
                <td class="kv-label">主机设备描述</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="idcHost.sysdescr" value="${idcDevice.idcHost.sysdescr}"/>
                </td>
                <td class="kv-label">操作系统</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="idcHost.os" value="${idcDevice.idcHost.os}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">CPU大小(MHZ)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['number'],width:200" name="idcHost.cpusize"
                           value="${idcDevice.idcHost.cpusize}"/>
                </td>
                <td class="kv-label">内存大小(G)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['number'],width:200" name="idcHost.memsize"
                           value="${idcDevice.idcHost.memsize}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">硬盘大小(G)</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['number'],width:200" name="idcHost.disksize"
                           value="${idcDevice.idcHost.disksize}"/>
                </td>
                <td class="kv-label">操作用户</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="idcHost.userid" value="${idcDevice.idcHost.userid}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">管理IP地址</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="validType:['ip'],width:200"
                           name="idcHost.ipaddress"
                           value="${idcDevice.idcHost.ipaddress}"/>
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
    //新增或修改设备
    function addDevice(){
        var linkUrl = "<%=request.getContextPath() %>/device/addDeviceInfo.do?id="+id+"&deviceclass=2";
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
            layer.msg("设备没有高度，不能上架");
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
    function getRackDealis(){
        var rackId = $("#rackId").val();
        var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + rackId + "&businesstype=other&buttonType=view";
        openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
    }
</script>
</body>
</html>