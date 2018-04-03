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
            //动态绑定PDF架
            var pwrInstalledrackId = "${idcMcb.pwrInstalledrackId}";
            changeInstallRack(pwrInstalledrackId, 0);
            //动态绑定客户架
            var pwrServicerackId = "${idcMcb.pwrServicerackId}";
            changeServiceRack(pwrServicerackId, 0);
            //动态绑定状态
            var pwrPwrstatus = "${idcMcb.pwrPwrstatus}";
            $("#pwrPwrstatus").combobox("setValue",pwrPwrstatus);

            //若为查看详情则只能看不能改
            var buttonType = "${buttonType}";
            if (buttonType != null && buttonType == 'view') {
                $('input').attr("readonly", true);
                $('select').combobox('readonly', true);
                $('#openPdfRack').linkbutton('disable');
                $('#openServerRack').linkbutton('disable');
            }
        })
        //是否显示pdf架超链接
        function changeInstallRack(newValue,oldValue){
            var data = 'PDF机架';
            if(newValue != null && newValue != ''){
                data = '<a href="javascript:void(0)" onclick="getRackDealis(1)">PDF机架</a>';
            }
            $("#isShowInstallLink").html(data);
        }
        //是否显示服务架链接
        function changeServiceRack(newValue,oldValue){
            var data = '服务机架';
            if(newValue != null && newValue != ''){
                data = '<a href="javascript:void(0)" onclick="getRackDealis(2)">服务机架</a>';
            }
            $("#isShowServiceLink").html(data);
        }
        //打开PDF架列表
        function openPdfRackListWin() {
            openRackListWin("pdf");
        }
        //打开客户架列表
        function openServerRackListWin() {
            openRackListWin("server");
        }
        //打开机架列表   客户架和网络架
        function openRackListWin(rackType) {
            var url = contextPath + '/idcRack/preUpordownForDeviceRackList';
            if (rackType == 'pdf') {
                url += '?type=mcb';
            } else {
                url += '?type=device';
            }
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
                    if (rackType == 'pdf') {
                        $("#rackPdfName").textbox('setValue', rackInfo.NAME);
                        $("#pwrInstalledrackId").val(rackInfo.ID);
                        changeInstallRack(rackInfo.ID, 0);
                    } else {
                        $("#rackServerName").textbox('setValue', rackInfo.NAME);
                        $("#pwrServicerackId").val(rackInfo.ID);
                        changeServiceRack(rackInfo.ID, 0);
                    }
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
                <td class="kv-label">PDU名称</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="required:true,width:200" name="name" value="${idcMcb.name}"/>
                </td>
                <td class="kv-label">PDU编号</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="pwrMcbno" value="${idcMcb.pwrMcbno}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label" id="isShowInstallLink">PDF机架</td>
                <td class="kv-content">
                    <input class="easyui-textbox" name="rackPdfName" id="rackPdfName" disabled data-options="width:200"
                           value="${pdfRack.name}"/>
                    <%--<a href="javascript:void(0)" id="openPdfRack" class="easyui-linkbutton"--%>
                    <%--data-options="iconCls:'icon-add-ext',onClick:openPdfRackListWin"></a>--%>
                    <input type="hidden" name="pwrInstalledrackId" id="pwrInstalledrackId"
                           value="${pdfRackId}"/>
                </td>
                <td class="kv-label">PDU描述</td>
                <td class="kv-content">
                    <input class="easyui-textbox" data-options="width:200" name="sysdescr" value="${idcMcb.sysdescr}"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label" id="isShowServiceLink">服务机架</td>
                <td class="kv-content">
                    <input class="easyui-textbox" name="rackServerName" id="rackServerName" disabled
                           data-options="width:172"
                           value="${serverRack.name}"/>
                    <a href="javascript:void(0)" id="openServerRack" class="easyui-linkbutton"
                       data-options="iconCls:'icon-add-ext',onClick:openServerRackListWin"></a>
                    <input type="hidden" name="pwrServicerackId" id="pwrServicerackId"
                           value="${idcMcb.pwrServicerackId}"/>
                </td>
                <td class="kv-label">PDU使用状态</td>
                <td class="kv-content">
                    <select name="pwrPwrstatus" id="pwrPwrstatus" class="easyui-combobox" data-options="editable:false,width:200">
                        <option value="20" selected>可用</option>
                        <option value="50">预占</option>
                        <option value="55">预释</option>
                        <option value="60">在服</option>
                        <option value="110">不可用</option>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>
        </form>  
    </div>
</div>
<script type="text/javascript">
    var id = "${id}";
    var pdfRackId ="${pdfRackId}";
    function doSubmit() {
        addMcb();
    }
    //新增或修改设备
    function addMcb(){
        var linkUrl = "<%=request.getContextPath() %>/idcmcb/addIdcmcbInfo.do?mcbId="+id+"&pdfRackId="+pdfRackId;
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
                    var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top[parentWin].$('#table').datagrid("reload");
                    top.layer.close(indexT); //再执行关闭
                }
            }
        });
    }
    function getRackDealis(type){
        var rackId = "";
        if(type == 1){
            rackId = $("#pwrInstalledrackId").val();
            openDialogShowView2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + rackId + '&businesstype=pdu&buttonType=view', '800px', '400px', '查看机架视图');
        }else if(type == 2){
            rackId = $("#pwrServicerackId").val();
            var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + rackId + "&businesstype=other&buttonType=view";
            openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
        }
    }
</script>
</body>
</html>