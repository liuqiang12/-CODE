<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>PDU信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
</head>
<body style="margin:0;padding:2px;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="table" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,pagination:true,
                   onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                   fitColumns:true,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/idcmcb/list.do?rackId=${rackId}',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'ID',hidden:'true'"></th>
                    <th data-options="field:'NAME',width:200">PDU名称</th>
                    <th data-options="field:'PWRPWRSTATUS',width:100,formatter:pwrPwrstatus">PDU使用状态</th>
                    <th data-options="field:'INSTALLNAME',width:210,formatter:installFormatter">PDF机架</th>
                    <th data-options="field:'SERVICENAME',width:210,formatter:serviceFormatter">服务机架</th>
                    <th data-options="field:'SYSDESCR',width:100">PDU描述</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <input type="input" id="buiName" class="param-input" placeholder="名称"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels();"></div>

    <div class="param-actionset">
        <sec:authorize access="hasAnyRole('ROLE_sys_resource_mcb_add','ROLE_admin')">
            <div class="btn-cls-common" id="add">新 增</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_resource_mcb_edit','ROLE_admin')">
            <div class="btn-cls-common" id="edit">修 改</div>
        </sec:authorize>
        <sec:authorize access="hasAnyRole('ROLE_sys_resource_mcb_del','ROLE_admin')">
            <div class="btn-cls-common" id="del">删 除</div>
        </sec:authorize>
    </div>
    <%--<div class="btn-cls-common" id="importData">导 入</div>--%>
    <%--<div class="btn-cls-common" id="exportData">导 出</div>--%>
    <%--</div>--%>
</div>
<!-- 导入EXCEL弹窗-->
<%--<div id="dlg_pic" class="easyui-dialog" style="width:400px;height:290px;padding:10px 20px"--%>
<%--closed="true" buttons="#dlg-buttons">--%>
<%--<div class="ftitle">导入</div>--%>
<%--<form id="fm_pic" method="post" enctype="multipart/form-data" novalidate>--%>
<%--<div class="fitem">--%>
<%--<label>excel文件:</label>--%>
<%--<input type="file" name="uploadFile" id="uploadFileid" name="file"/>--%>
<%--&lt;%&ndash;	<input class="easyui-filebox" name="uploadFile" id="uploadFileid" data-options="prompt:'上传文件'">&ndash;%&gt;--%>
<%--</div>--%>
<%--<div class="fitem">--%>
<%--<input type="button" onclick="importFileClick()" value="导入">--%>
<%--<input type="button" onclick="closeDialog()" value="取消">--%>
<%--</div>--%>
<%--</form>--%>
<%--</div>--%>
<script type="text/javascript" language="JavaScript">
    var pdfRackId = "${rackId}";
    $(function(){
        $('#table').datagrid({
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcmcb/idcmcbDetails.do?id=" + row.ID + "&buttonType=view";
                openDialogView('PDU信息', url, '800px', '330px');
            }
        });
        //新增
        $("#add").click(function () {
            openDialog('新增PDU', contextPath + '/idcmcb/preAddIdcmcbInfo.do?pdfRackId=' + pdfRackId, '800px', '330px');
        });
        //修改
        $("#edit").click(function () {
            var rows = $('#table').datagrid('getChecked');
            if (rows.length < 1) {
                layer.msg("没有选择PDU");
                return;
            } else if (rows.length > 1) {
                layer.msg("只能选择一个PDU");
                return;
            }
            var url = contextPath + "/idcmcb/idcmcbDetails.do?id=" + rows[0].ID + "&buttonType=update";
            openDialog('编辑PDU信息', url, '800px', '330px');
        });
        //删除
        $("#del").click(function () {
            var rowArr = new Array();
            var rows = $('#table').datagrid('getChecked');
            if (rows.length < 1) {
                layer.msg("没有选择PDU");
                return;
            }
            for (var i = 0; i < rows.length; i++) {
                rowArr.push(rows[i].ID);
            }
            layer.confirm('是否确认删除', {btn: ['确定', '取消']}, function (index) {
                $.post(contextPath + "/idcmcb/deleteIdcmcbByIds.do", {ids: rowArr.join(',')}, function (result) {
                    layer.msg(result.msg);
                    //刷新列表
                    $("#table").datagrid('reload');
                });
                layer.close(index);
            });
        })
        //导入------------------------------------------------
//        $("#importData").click(function(){
//            $('#dlg_pic').dialog({
//                title: '导入',
//                closed: false,
//            });
//        });
//        function importFileClick(){
//            $('#fm_pic').form('submit',{
//                url: contextPath + "/idcmcb/importDeviceData",
//                onSubmit: function () {
//                    return $("#fm_pic").form('validate');
//                },
//                success: function (data) {
//                    var obj = eval('(' + data + ')');
//                    alert(obj.msg);
//                    if(obj.state){
//                        //top.window['frmright'].window.$('#dg').datagrid("reload");
//                        $("#table").datagrid('reload');
//                        closeDialog();
//                    }
//                }
//            });
//        }
        //导出--------------------------------------------------------------
//        $("#exportData").click(function(){
//            window.open(contextPath + '/idcmcb/exportDeviceData');
//        });
    })
    function installFormatter(value,row,index){
        if(value!=null&&value!=''){
            return '<a href="javascript:void(0)" onclick="getRackDealis('+row.PWRINSTALLEDRACKID+',1)">' + value + '</a>';
        }else{
            return '';
        }
    }
    function serviceFormatter(value,row,index){
        if(value!=null&&value!=''){
            return '<a href="javascript:void(0)" onclick="getRackDealis('+row.PWRSERVICERACKID+',2)">' + value + '</a>';
        }else{
            return '';
        }
    }
    function getRackDealis(value,type){
        if(type == 1){
            openDialogShowView2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + value + '&businesstype=pdu&buttonType=view', '800px', '400px', '查看机架视图');
        }else{
            var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + value + "&businesstype=other&buttonType=view";
            openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
        }
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight');
    }
    function pwrPwrstatus(value,row,index){
        if(value == 20){
            return '可用';
        }else if(value == 50){
            return '预占';
        }else if(value == 55){
            return '预释';
        }else if(value == 60){
            return '在服';
        }else if(value == 110){
            return '不可用';
        }else{
            return '';
        }
    }
    //查询
    function searchModels(){
        var name = $("#buiName").val();
        $('#table').datagrid({
            url: contextPath + "/idcmcb/list.do",
            queryParams: {name: name, rackId: pdfRackId}
        });
    }
    //关闭导入窗口
    function closeDialog(){
        $('#dlg_pic').dialog('close');
    }
</script>
</body>
</html>