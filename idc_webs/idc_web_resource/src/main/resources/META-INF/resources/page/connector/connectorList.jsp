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
    <title>connector信息</title>
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
                   fitColumns:false,toolbar:'#toolbar',url:'<%=request.getContextPath() %>/idcConnector/list.do?rackId=${rackId}',
                  ">
                <thead>
                <tr>
                    <th data-options="field:'ID',hidden:'true'"></th>
                    <th data-options="field:'NAME',width:250">端子名称</th>
                    <th data-options="field:'BNAME',width:200,formatter:rackDealisLink">所属机架</th>
                    <th data-options="field:'CONNECTORTYPE',width:100,formatter:connectorTypeDealis">连接类型</th>
                    <th data-options="field:'TYPE',width:100,formatter:typeDealis">端口类型</th>
                    <th data-options="field:'PORT_MODE',width:100,formatter:portModeDealis">光口模式</th>
                    <th data-options="field:'STATUS',width:100,formatter:statusDealis">业务状态</th>
                    <th data-options="field:'TICKET_ID',width:200">工单编号</th>
                    <th data-options="field:'CREATEDATE',width:200,formatter:fmtDateAction">创建时间</th>
                    <th data-options="field:'UPDATEDATE',width:200,formatter:fmtDateAction">更新时间</th>
                    <th data-options="field:'MEMO',width:200">备注</th>
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

    <%--<c:if test="${empty operateType}">--%>
    <%--<div class="param-actionset">--%>
    <%--<sec:authorize access="hasAnyRole('ROLE_sys_userinfo_add','ROLE_admin')">--%>
    <%--<div class="btn-cls-add" id="add"></div>--%>
    <%--</sec:authorize>--%>
    <%--<sec:authorize access="hasAnyRole('ROLE_sys_userinfo_edit','ROLE_admin')">--%>
    <%--<div class="btn-cls-edit" id="edit"></div>--%>
    <%--</sec:authorize>--%>
    <%--<sec:authorize access="hasAnyRole('ROLE_sys_userinfo_del','ROLE_admin')">--%>
    <%--<div class="btn-cls-delete" id="del"></div>--%>
    <%--</sec:authorize>--%>
    <%--<div class="btn-cls-common" id="importData">导 入</div>--%>
    <%--<div class="btn-cls-common" id="exportData">导 出</div>--%>
    <%--</div>--%>
    <%--</c:if>--%>
</div>
<!-- 导入EXCEL弹窗-->
<%--<div id="dlg_pic" class="easyui-dialog" style="width:400px;height:290px;padding:10px 20px"--%>
<%--closed="true" buttons="#dlg-buttons">--%>
<%--<div class="ftitle">导入</div>--%>
<%--<form id="fm_pic" method="post" enctype="multipart/form-data" novalidate>--%>
<%--<div class="fitem">--%>
<%--<label>excel文件:</label>--%>
<%--<input type="file" name="uploadFile" id="uploadFileid" name="file"/>--%>
<%--</div>--%>
<%--<div class="fitem">--%>
<%--<input type="button" onclick="importFileClick()" value="导入">--%>
<%--<input type="button" onclick="closeDialog()" value="取消">--%>
<%--</div>--%>
<%--</form>--%>
<%--</div>--%>
<script type="text/javascript" language="JavaScript">
    var odfRackId = "${rackId}";
    var rackIdA = "${rackIdA}";
    var roomId = "${roomId}";
    //资源分配 - 信息绑定
    function doSubmit(){
        //进行资源绑定
        var rowArr = new Array();
        var rows = $('#table').datagrid('getChecked');
        for(var i=0;i<rows.length;i++){
            rowArr.push(rows[i].ID);
        }
        $.post(contextPath+"/idcConnector/bindConnectionToIdcLink.do",{ids:rowArr.join(','),roomId:roomId,odfRackId:odfRackId,rackId:rackIdA},function(result){
            alert(result.msg);
            if(result.state){
                var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(indexT); //再执行关闭
            }
        });
    }
    $(function(){
        $('#table').datagrid({
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcConnector/getIdcConnectorInfo.do?id=" + row.ID + "&buttonType=view&odfRackId=" + odfRackId;
                openDialogView('端子信息', url,'800px','330px');
            }
        });
        //导入------------------------------------------------
//        $("#importData").click(function(){
//            $('#dlg_pic').dialog({
//                title: '导入',
//                closed: false,
//            });
//        });

        //导出--------------------------------------------------------------
//        $("#exportData").click(function(){
//            window.open(contextPath + '/idcConnector/exportDeviceData.do');
//        });
//        //新增
//        $("#add").click(function(){
//            openDialog('新增端子', contextPath +'/idcConnector/preAddIdcConnectorInfo.do?odfRackId='+odfRackId,'800px','530px');
//        });
//        //修改
//        $("#edit").click(function(){
//            var rows = $('#table').datagrid('getChecked');
//            if(rows.length<1){
//                layer.msg("没有选择端子");
//                return;
//            }else if(rows.length>1){
//                layer.msg("只能选择一个端子");
//                return;
//            }
//            var  url = contextPath+"/idcConnector/getIdcConnectorInfo.do?id="+rows[0].ID+"&buttonType=update&odfRackId="+odfRackId;
//            openDialog('编辑端子信息', url,'800px','330px');
//        });
//        //删除
//        $("#del").click(function(){
//            var rowArr = new Array();
//            var rows = $('#table').datagrid('getChecked');
//            if(rows.length<1){
//                layer.msg("请选择要删除的端子");
//                return;
//            }
//            for(var i=0;i<rows.length;i++){
//                rowArr.push(rows[i].ID);
//            };
//            $.post(contextPath+"/idcConnector/deleteConnector.do",{ids:rowArr.join(',')},function(result){
//                layer.msg(result.msg);
//                //刷新odf架列表
//                top.window['frmright'].window.$('#dg').datagrid("reload");
//                //刷新端子列表
//                $("#table").datagrid('reload');
//            });
//        })
    })
    //加载段子列表
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table').datagrid('fixRowHeight');
    }
    //查询
    function searchModels(){
        var name = $("#buiName").val();
        $('#table').datagrid({
            url:contextPath + "/idcConnector/list.do",
            queryParams:{name:name,rackId:odfRackId}
        });
    }
    //归属机架链接
    function rackDealisLink(value,row,index){
        if(value!=null&&value!=''){
            return '<a href="javascript:void(0)" onclick="getRackDealis('+row.RACK_ID+')">' + value + '</a>';
        }else{
            return '';
        }
    }
    //查看机架信息
    function getRackDealis(value){
        openDialogShowView2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + value + '&businesstype=odf&buttonType=view', '800px', '400px', '查看机架视图');
    }
    //连接方式
    function connectorTypeDealis(value,row,index){
        if(value == 1){
            return 'RJ45';
        }else if(value == 2){
            return 'FC';
        }else if(value == 3){
            return 'LC';
        }else if(value == 4){
            return 'SC';
        }else{
            return '';
        }
    }
    //端子类型
    function typeDealis(value,row,index){
        if(value == 'odf'){
            return 'ODF';
        }else if(value == 'ddf'){
            return 'DDF';
        }else{
            return '';
        }
    }
    //光口模式
    function portModeDealis(value,row,index){
        if(value == 1){
            return '单模';
        }else if(value == 2){
            return '多模';
        }else{
            return '';
        }
    }
    //业务状态
    function statusDealis(value,row,index){
        if(value == 20){
            return '可用';
        }else if(value == 30){
            return '预留';
        }else if(value == 50){
            return '预占';
        }else if(value == 55){
            return '已停机';
        }else if(value == 60){
            return '在服';
        }else{
            return '';
        }
    }

    //关闭导入窗口
    function closeDialog(){
        $('#dlg_pic').dialog('close');
    }
    //    function importFileClick(){
    //        alert("导入");
//        $('#fm_pic').form('submit',{
//            url: contextPath + "/idcmcb/importDeviceData",
//            onSubmit: function () {
//                return $("#fm_pic").form('validate');
//            },
//            success: function (data) {
//                var obj = eval('(' + data + ')');
//                alert(obj.msg);
//                if(obj.state){
//                    //top.window['frmright'].window.$('#dg').datagrid("reload");
//                    $("#table").datagrid('reload');
//                    closeDialog();
//                }
//            }
//        });
    //    }
</script>
</body>
</html>