<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>机架型号信息</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="dcimRackModelForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">机架型号</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="name" id="name" value="${dcimRackModel.name}"/>
                    </td>
                    <td class="kv-label">型号编码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="code" id="code" value="${idcConnector.code}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">工单编号</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" data-options="width:200" name="ticketId" id="ticketId" value="${idcConnector.ticketId}"/>
                    </td>
                    <td class="kv-label" id="isShowLink">所属机架</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" name="rackName" id="rackName" disabled data-options="width:172"
                               value="${idcrack.name}"/>
                        <a href="javascript:void(0)" id="openRack" class="easyui-linkbutton"
                           data-options="iconCls:'icon-add-ext',onClick:openRackListWin"></a>
                        <input type="hidden" name="rackId" id="rackId" value="${idcConnector.rackId}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">连接类型</td>
                    <td class="kv-content">
                        <select id="connectortype" class="easyui-combobox" name="connectortype"
                                data-options="editable:false,width:200" >
                            <option value="1">RJ45</option>
                            <option value="2">FC</option>
                            <option value="3">LC</option>
                            <option value="4">SC</option>
                        </select>
                    </td>
                    <td class="kv-label">端子类型</td>
                    <td class="kv-content">
                        <select id="type" class="easyui-combobox" name="type"
                                data-options="editable:false,width:200" >
                            <option value="odf">ODF</option>
                            <option value="ddf">DDF</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">光口模式</td>
                    <td class="kv-content">
                        <select id="portMode" class="easyui-combobox" name="portMode"
                                data-options="editable:false,width:200" >
                            <option value="1">单模</option>
                            <option value="2">多模</option>
                        </select>
                    </td>
                    <td class="kv-label">业务状态</td>
                    <td class="kv-content">
                        <select id="status" class="easyui-combobox" name="status"
                                data-options="editable:false,width:200" >
                            <option value="20">可用</option>
                            <option value="30">预留</option>
                            <option value="50">预占</option>
                            <option value="55">已停机</option>
                            <option value="60">在服</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">备注</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:200" name="memo" id="memo" value="${idcConnector.memo}"/>
                    </td>
                    <td class="kv-label"></td>
                    <td class="kv-content"></td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
</div>
<script type="text/javascript">
    var id = "${id}";
    var odfRackId = "${odfRackId}";
    function doSubmit(){
        addConnector();
    }
    //新增或修改端子
    function addConnector(){
        var linkUrl = "<%=request.getContextPath() %>/idcConnector/addIdcConnector.do?id="+id;
        $('#idcConnectForm').form('submit', {
            url: linkUrl,
            onSubmit: function () {
                return $("#idcConnectForm").form('validate');
            },
            success: function (data) {
                var obj = eval('(' + data + ')');
                alert(obj.msg);
                if(obj.state){
                    var parentWin = top.winref[window.name];
                    //刷新ODF架列表
                    top.window['frmright'].window.$('#dg').datagrid("reload");
                    //刷新端子列表
                    top[parentWin].$('#table').datagrid("reload");
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                }
            }
        });
    }
    function getRackDealis(){
        var rackId = $("#rackId").val();
        openDialogViewRack2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + rackId + '&businesstype=df&buttonType=view', '800px', '400px');
    }
</script>
</body>
</html>