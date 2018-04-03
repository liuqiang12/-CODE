<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <script type="text/javascript">
        $(function () {
            //若为查看详情则只能看不能改
            var buttonType = "${buttonType}";
            if (buttonType != null && buttonType == 'view') {
                $('input').attr("readonly", true);
                $('select').combobox('readonly', true);
                $('.easyui-linkbutton').linkbutton('disable');
                $(".easyui-datetimebox").datetimebox('readonly', true);
                $(".easyui-filebox").filebox('disable');
            } else if (buttonType != null && buttonType == 'update') {
                $('.easyui-linkbutton').linkbutton('disable');
            }
        })
        //下载附件
        function downloadAttachmentn() {
            console.log("下载附件");
            window.open(contextPath + '/idcLinkOutport/downLoadFileByTransmissionId?idcTransmissionId=' + idcTransmissionId);
        }
    </script>
    <title>编辑链路</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="deviceForm" method="post" enctype="multipart/form-data">
            <%---附件 --%>
            <fieldset>
                <legend>附件</legend>
                <div>
                    <div>
                        <table class="kv-table">
                            <tr>
                                <td class="kv-content" colspan="4">
                                    <table class="kv-table">
                                        <tr>
                                            <td class="kv-label">调单号</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="required:true,width:200"
                                                       name="idcTransmissionCode"
                                                       value="${idcTransmissionForm.idcTransmissionCode}"/>
                                            </td>
                                            <td class="kv-label">备注</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcTransmissionNote" value="${idcTransmissionForm.note}"/>
                                            </td>
                                        </tr>
                                        <c:choose>
                                            <c:when test="${not empty buttonType and buttonType == 'add'}">
                                                <td class="kv-label">调单文件</td>
                                                <td class="kv-content" colspan="3">
                                                    <input name="file" id="upFile" class="easyui-filebox"
                                                           style="width:70%"
                                                           data-options="prompt:'请选择要上传的文件',buttonText:'&nbsp;浏&nbsp;&nbsp;览&nbsp;'">
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td class="kv-label">
                                                        <c:choose>
                                                            <c:when test="${attachname!=null and attachname!='' and attachname!='undefined' }">
                                                                <a href="javascript:void(0)"
                                                                   onclick="downloadAttachmentn()"><span
                                                                        style="color: blue;">调单文件</span></a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                调单文件
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="kv-content" colspan="3">
                                                        <input name="file" id="upFile" class="easyui-filebox"
                                                               style="width:70%" value="${attachname}"
                                                               data-options="prompt:'请选择要上传的文件',buttonText:'&nbsp;浏&nbsp;&nbsp;览&nbsp;'">
                                                    </td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </fieldset>
            <%---基本信息 --%>
            <fieldset>
                <legend>基本信息</legend>
                <div>
                    <div>
                        <table class="kv-table">
                            <tr>
                                <td class="kv-content" colspan="4">
                                    <table class="kv-table">
                                        <tr>
                                            <td class="kv-label">IDC名称</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="required:true,width:200"
                                                       name="idcLinkFromArea" value="${linkOutport.idcLinkFromArea}"/>
                                            </td>
                                            <td class="kv-label">IDC机房名称</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkRoomName" value="${linkOutport.idcLinkRoomName}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label">设备机柜位置</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkCabinetPosition"
                                                       value="${linkOutport.idcLinkCabinetPosition}"/>
                                            </td>
                                            <td class="kv-label">出局路由器/其他出局数通设备</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkLocalRouteName"
                                                       value="${linkOutport.idcLinkLocalRouteName}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label">本端设备型号</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkDeviceModel"
                                                       value="${linkOutport.idcLinkDeviceModel}"/>
                                            </td>
                                            <td class="kv-label">本端链路带宽</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox"
                                                       data-options="validType:['number'],width:200"
                                                       name="idcLinkTapeWidth" value="${linkOutport.idcLinkTapeWidth}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label">省核心机房名称</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkTargetRoomName"
                                                       value="${linkOutport.idcLinkTargetRoomName}"/>
                                            </td>
                                            <td class="kv-label">上联省核心路由器名称</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkTargetRouteName"
                                                       value="${linkOutport.idcLinkTargetRouteName}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label">对端设备型号</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkTargetDeviceModel"
                                                       value="${linkOutport.idcLinkTargetDeviceModel}"/>
                                            </td>
                                            <td class="kv-label">备注</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200" name="note"
                                                       value="${linkOutport.note}"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </fieldset>
            <%---端口信息 --%>
            <fieldset>
                <legend>端口信息</legend>
                <div style="position:relative;">
                    <div>
                        <table class="kv-table" id="infoT">
                            <tr>
                                <td class="kv-content" colspan="4">
                                    <table class="kv-table">
                                        <tr>
                                            <td class="kv-label">本端ODF端口</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkLocalOdfPort"
                                                       value="${linkOutport.idcLinkLocalOdfPort}"/>
                                            </td>
                                            <td class="kv-label">本端设备端口</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkLocalDevicePort"
                                                       value="${linkOutport.idcLinkLocalDevicePort}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="kv-label">对端ODF端口</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkTargetOdfPort"
                                                       value="${linkOutport.idcLinkTargetOdfPort}"/>
                                            </td>
                                            <td class="kv-label">对端设备端口</td>
                                            <td class="kv-content">
                                                <input class="easyui-textbox" data-options="width:200"
                                                       name="idcLinkTargetDevicePort"
                                                       value="${linkOutport.idcLinkTargetDevicePort}"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="position: absolute;right:30px;bottom:38px;">
                        <a href="javascript:void(0)" class="easyui-linkbutton"
                           data-options="iconCls:'icon-add-ext',onClick:addRow"></a>
                    </div>
                </div>

            </fieldset>
        </form>
    </div>
</div>
<script type="text/javascript">
    //链路ID
    var id = "${id}";
    //掉单表ID
    var idcTransmissionId = "${idcTransmissionForm.idcTransmissionId}";
    //新增或修改链路
    function doSubmit() {
        saveIdcLinkOutport();
    }
    function saveIdcLinkOutport() {
        var linkUrl = "<%=request.getContextPath() %>/idcLinkOutport/save";
        $('#deviceForm').form('submit', {
            url: linkUrl,
            queryParams: {
                id: id,
                idcTransmissionId: idcTransmissionId
            },
            onSubmit: function () {
                return $("#deviceForm").form('validate');
            },
            success: function (data) {
                var obj = eval('(' + data + ')');
                alert(obj.msg);
                if (obj.state) {
                    var parentWin = top.winref[window.name];
                    var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top[parentWin].$('#table').datagrid("reload");
                    top.layer.close(indexT); //再执行关闭
                }
            }
        });
    }
    function addRow() {
        console.log("添加行");
        var str = '<tr>';
        str += '<td class="kv-content" colspan="4">';
        str += '<table class="kv-table">';
        str += '<tr>';
        str += '<td class="kv-label">本端ODF端口</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="width:200" name="idcLinkLocalOdfPort" value="${linkOutport.idcLinkLocalOdfPort}"/>';
        str += '</td>';
        str += '<td class="kv-label">本端设备端口</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="width:200" name="idcLinkLocalDevicePort" value="${linkOutport.idcLinkLocalDevicePort}"/>';
        str += '</td>';
        str += '</tr>';
        str += '<tr>';
        str += '<td class="kv-label">对端ODF端口</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="width:200" name="idcLinkTargetOdfPort" value="${linkOutport.idcLinkTargetOdfPort}"/>';
        str += '</td>';
        str += '<td class="kv-label">对端设备端口</td>';
        str += '<td class="kv-content">';
        str += '<input class="easyui-textbox" data-options="width:200" name="idcLinkTargetDevicePort" value="${linkOutport.idcLinkTargetDevicePort}"/>';
        str += '<a href="javascript:void(0)" style="margin-left: 5px;" class="easyui-linkbutton" data-options="iconCls:\'icon-clear\'" onclick=deleteRowT(this)></a>';
        str += '</td>';
        str += '</tr>';
        str += '</table>';
        str += '</td>';
        str += '</tr>';
        var targetObj = $("#infoT").append(str);
        //重新渲染form表单
        $.parser.parse(targetObj);
    }
    function deleteRowT(obj) {
        var index = obj.parentNode.parentNode.rowIndex;
        console.log("删除行=====" + index);
        $("#infoT")[0].deleteRow(index);
    }
</script>
</body>
</html>