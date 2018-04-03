<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>设备</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <style>
        .col-xs-12 >table,td,th {  border: 1px solid #8DB9DB; padding:5px; border-collapse: collapse; font-size:16px; }
    </style>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-tabs" style="height: 100%;width: 100%" data-options="fit:true">
    <div title="网络设备" style="height: 100%;width: 100%" data-options="fit:true">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'center',border:false" style="padding-left: 2px;">
                <%--<table id="table1" class="easyui-datagrid" title="网络设备"--%>
                <%--data-options="fit:true,rownumbers:false,pagination:true,--%>
                        <%--onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,--%>
                        <%--fitColumns:false,toolbar:'#toolbar1',url:'<%=request.getContextPath() %>/device/list.do?deviceclass=1',--%>
                        <%--onDblClickRow:function(row){alert(row.deviceid)},">--%>
                    <%--<thead>--%>
                        <%--<tr>--%>
                            <%--<th data-options="field:'deviceid',checkbox:true"></th>--%>
                            <%--<th data-options="field:'name',width:$(this).width()*0.1,align:'center'">设备名称</th>--%>
                            <%--<th data-options="field:'deviceclass',width:$(this).width()*0.1,align:'center'">设备类别</th>--%>
                            <%--<th data-options="field:'businesstypeId',width:$(this).width()*0.1,align:'center'">设备类型</th>--%>
                            <%--<th data-options="field:'code',width:$(this).width()*0.1,align:'center'">设备编码</th>--%>
                            <%--<th data-options="field:'rackId',width:$(this).width()*0.1,align:'center'">归属机架</th>--%>
                            <%--<th data-options="field:'uplinedate',width:$(this).width()*0.1,align:'center'">上架日期</th>--%>
                            <%--<th data-options="field:'description',width:$(this).width()*0.1,align:'center'">设备描述</th>--%>
                            <%--<th data-options="field:'uinstall',width:$(this).width()*0.1,align:'center'">安装位置</th>--%>
                            <%--<th data-options="field:'uheight',width:$(this).width()*0.1,align:'center'">设备高度</th>--%>
                            <%--<th data-options="field:'power',width:$(this).width()*0.1,align:'center'">额定功耗</th>--%>
                            <%--<th data-options="field:'pwrPowertype',width:$(this).width()*0.1,align:'center'">电源类别</th>--%>
                            <%--<th data-options="field:'serverIpaddress',width:$(this).width()*0.15,align:'center'">管理IP地址</th>--%>
                            <%--<th data-options="field:'owner',width:$(this).width()*0.1,align:'center'">联系人</th>--%>
                            <%--<th data-options="field:'vendor',width:$(this).width()*0.1,align:'center'">厂商</th>--%>
                            <%--<th data-options="field:'model',width:$(this).width()*0.1,align:'center'">型号</th>--%>
                            <%--<th data-options="field:'phone',width:$(this).width()*0.15,align:'center'">联系人电话</th>--%>
                            <%--<th data-options="field:'ownertype',width:$(this).width()*0.1,align:'center'">产权性质</th>--%>
                            <%--<th data-options="field:'status',width:$(this).width()*0.1,align:'center'">设备状态</th>--%>
                            <%--<th data-options="field:'ticketId',width:$(this).width()*0.1,align:'center'">工单编号</th>--%>
                        <%--</tr>--%>
                    <%--</thead>--%>
                <%--</table>--%>
            </div>
        </div>
    </div>
    <div title="主机设备" style="padding-left: 2px;" data-options="fit:true,border:false,url:'<%=request.getContextPath()%>/resource/idchost'" >
        <%--<table id="table2" class="easyui-datagrid" title="主机设备"--%>
               <%--data-options="fit:true,rownumbers:true,pagination:true,--%>
                <%--onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,--%>
                <%--fitColumns:true,toolbar:'#toolbar2',url:'<%=request.getContextPath() %>/device/list.do?deviceclass=2',--%>
                <%--onDblClickRow:function(row){alert(row.deviceid)},">--%>
            <%--<thead>--%>
                <%--<tr>--%>
                    <%--<th data-options="field:'deviceid',checkbox:true"></th>--%>
                    <%--<th data-options="field:'name',width:$(this).width()*0.1,align:'center'">设备名称</th>--%>
                    <%--<th data-options="field:'deviceclass',width:$(this).width()*0.1,align:'center'">设备类别</th>--%>
                    <%--<th data-options="field:'businesstypeId',width:$(this).width()*0.1,align:'center'">设备类型</th>--%>
                    <%--<th data-options="field:'code',width:$(this).width()*0.1,align:'center'">设备编码</th>--%>
                    <%--<th data-options="field:'rackId',width:$(this).width()*0.1,align:'center'">归属机架</th>--%>
                    <%--<th data-options="field:'uplinedate',width:$(this).width()*0.1,align:'center'">上架日期</th>--%>
                    <%--<th data-options="field:'description',width:$(this).width()*0.1,align:'center'">设备描述</th>--%>
                    <%--<th data-options="field:'uinstall',width:$(this).width()*0.1,align:'center'">安装位置</th>--%>
                    <%--<th data-options="field:'uheight',width:$(this).width()*0.1,align:'center'">设备高度</th>--%>
                    <%--<th data-options="field:'power',width:$(this).width()*0.1,align:'center'">额定功耗</th>--%>
                    <%--<th data-options="field:'pwrPowertype',width:$(this).width()*0.1,align:'center'">电源类别</th>--%>
                    <%--<th data-options="field:'serverIpaddress',width:$(this).width()*0.15,align:'center'">管理IP地址</th>--%>
                    <%--<th data-options="field:'owner',width:$(this).width()*0.1,align:'center'">联系人</th>--%>
                    <%--<th data-options="field:'vendor',width:$(this).width()*0.1,align:'center'">厂商</th>--%>
                    <%--<th data-options="field:'model',width:$(this).width()*0.1,align:'center'">型号</th>--%>
                    <%--<th data-options="field:'phone',width:$(this).width()*0.15,align:'center'">联系人电话</th>--%>
                    <%--<th data-options="field:'ownertype',width:$(this).width()*0.1,align:'center'">产权性质</th>--%>
                    <%--<th data-options="field:'status',width:$(this).width()*0.1,align:'center'">设备状态</th>--%>
                    <%--<th data-options="field:'ticketId',width:$(this).width()*0.1,align:'center'">工单编号</th>--%>
                <%--</tr>--%>
            <%--</thead>--%>
        <%--</table>--%>
    </div>
</div>
<div id="toolbar1" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <label>名称:</label>
        <input type="input" id="buiName1" class="param-input"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels(1);"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName1').val('')"></div>
    <div class="param-actionset">
        <div class="btn-cls-common" onclick="addDevice('device', 0);">新 增</div>
        <div class="btn-cls-common" onclick="updateDevice(1);">修 改</div>
        <div class="btn-cls-common" onclick="delDevice(1);">删 除</div>
        <div class="btn-cls-common" id="importData1">导 入</div>
        <div class="btn-cls-common" id="exportData1">导 出</div>
        <div class="btn-cls-common" id="netProt1">端口信息</div>
    </div>
</div>
<div id="toolbar2" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <label>名称:</label>
        <input type="input" id="buiName2" class="param-input"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels(2);"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName2').val('')"></div>
    <div class="param-actionset">
        <div class="btn-cls-common" onclick="addDevice('idchost', 0);">新 增</div>
        <div class="btn-cls-common" onclick="updateDevice(2);">修 改</div>
        <div class="btn-cls-common" onclick="delDevice(2);">删 除</div>
        <div class="btn-cls-common" id="importData2">导 入</div>
        <div class="btn-cls-common" id="exportData2">导 出</div>
    </div>
</div>
<div id="toolbar3" style="height: 28px" class="paramContent">
    <div class="param-fieldset">
        <label>名称:</label>
        <input type="input" id="buiName3" class="param-input"/>
    </div>
    <div class="btn-cls-search" onClick="searchModels(3);"></div>
    <div class="btn-cls-reset" onClick="javascript:$('#buiName3').val('')"></div>
    <div class="param-actionset">
        <div class="btn-cls-common" onclick="addDevice('idcmcb', 0);">新 增</div>
        <div class="btn-cls-common" onclick="updateDevice(3);">修 改</div>
        <div class="btn-cls-common" onclick="delDevice(3);">删 除</div>
        <div class="btn-cls-common" id="importData3">导 入</div>
        <div class="btn-cls-common" id="exportData3">导 出</div>
    </div>
</div>
<!--导入弹出框-->
<div id="dlg_pic" class="easyui-dialog" style="width:400px;height:290px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">导入</div>
    <form id="fm_pic" method="post" enctype="multipart/form-data" novalidate>
        <div class="fitem">
            <label>excel文件:</label>
            <input type="file" name="uploadFile" id="uploadFileid" name="file"/>
            <%--	<input class="easyui-filebox" name="uploadFile" id="uploadFileid" data-options="prompt:'上传文件'">--%>
        </div><br/>
        <div class="fitem" align="center">
            <input type="button" onclick="importFileClick()" value="导入">&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" onclick="closeDialog()" value="取消">
        </div>
    </form>
</div>
<script src="<%=request.getContextPath() %>/framework/treetable/jquery.treeTable.js"></script>
<link  href="<%=request.getContextPath() %>/framework/treetable/vsStyle/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="JavaScript">
    function loadFilter(data){
        return data;
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#table2').datagrid('fixRowHeight')
    }
    //刷新列表
//    function refreshTable(){
//        $('#table1').datagrid('reload');
//        $("#table2").datagrid('reload');
//        $("#table3").datagrid('reload');
//    }
    //新增设备
    function addDevice(type,id){
        var  url = contextPath + "/resource/"+type+"/"+id;
        top.layer.open({
            type : 2,
            title : type=='device'?'网络设备信息':type=='idchost'?'主机设备信息':'MCB信息',
            maxmin : true,
            id: 'idcDevice', //设定一个id，防止重复弹出
            closeBtn:1,
            area : [ '800px', '530px' ],
            content :url
        });
    }
    //修改设备
    function updateDevice(deviceClass){
        var rows = $('#table1').datagrid('getChecked');
        if(deviceClass == 2){
            rows = $('#table2').datagrid('getChecked');
        }else if(deviceClass == 3){
            rows = $('#table3').datagrid('getChecked');
        }
        if(rows.length<1){
            layer.msg("没有选择设备");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个设备");
            return;
        }
        var  url = contextPath+"/device/deviceDetails.do?id="+rows[0].deviceid+"&buttonType=update&deviceclass="+deviceClass;
        top.layer.open({
            type: 2,
            area: ['800px', '530px'],
            fixed: false, //不固定
            maxmin: true,
            content: url
        })
    }
    //删除设备
    function delDevice(deviceClass) {
        var rowArr = new Array();
        var rows = $('#table1').datagrid('getChecked');
        if (deviceClass == 2) {
            rows = $('#table2').datagrid('getChecked');
        } else if (deviceClass == 3) {
            rows = $('#table3').datagrid('getChecked');
        }
        if (rows.length < 1) {
            layer.msg("没有选择设备");
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].deviceid);
        }
        $.post(contextPath + "/device/deleteIdcDeviceByIds.do?deviceclass=" + deviceClass, {ids: rowArr.join(',')}, function (result) {
            layer.msg(result.msg);
            //刷新列表
            refreshTable();
        });
    }
    //查询
    function searchModels(deviceClass) {
        if(deviceClass == 1){
            var name = $("#buiName1").val();
            $('#table1').datagrid({
                url:contextPath + "/device/list.do?name="+name+"&deviceclass="+deviceClass,
            });
        }else if(deviceClass == 2){
            var name = $("#buiName2").val();
            $('#table2').datagrid({
                url:contextPath + "/device/list.do?name="+name+"&deviceclass="+deviceClass,
            });
        }else if(deviceClass == 3){
            var name = $("#buiName3").val();
            $('#table3').datagrid({
                url:contextPath + "/device/list.do?name="+name+"&deviceclass="+deviceClass,
            });
        }

    }
    //关闭导出框
    function closeDialog(){
        $("#dlg_pic").dialog('close');
    }
</script>
</body>

</html>