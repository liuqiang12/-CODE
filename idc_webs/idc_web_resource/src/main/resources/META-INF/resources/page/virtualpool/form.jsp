<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑资源池</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="idcRackForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">资源池名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="idcResourcesPoolName"
                               value="${info.idcResourcesPoolName}"/>
                    </td>
                    <td class="kv-label">资源池编码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="idcResourcesPoolCode"
                               value="${info.idcResourcesPoolCode}"/>
                    </td>
                </tr>
                <%--<tr>--%>
                    <%--&lt;%&ndash;<td class="kv-label">最大物理机数</td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<td class="kv-content">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input class="easyui-numberbox" data-options="required:true,width:200" id="idcMaxNum" name="idcMaxNum"&ndash;%&gt;--%>
                               <%--&lt;%&ndash;value="${info.idcMaxNum}"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
                    <%--<td class="kv-label">硬盘大小(GB)</td>--%>
                    <%--<td class="kv-content" colspan="3">--%>
                        <%--<input class="easyui-textbox" data-options="required:true,width:200" id="idcResourcesDiskSize" readonly="readonly"--%>
                               <%--value="${info.idcResourcesDiskSize}"/>--%>
                    <%--</td>--%>


                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td class="kv-label">内存大小(GB)</td>--%>
                    <%--<td class="kv-content">--%>
                        <%--<input class="easyui-textbox" data-options="required:true,width:200" id="idcResourcesMemSize" readonly="readonly"--%>
                               <%--value="${info.idcResourcesMemSize}"/>--%>
                    <%--</td>--%>
                    <%--<td class="kv-label">CPU数</td>--%>
                    <%--<td class="kv-content">--%>
                        <%--<input class="easyui-textbox" data-options="required:true,width:200" id="idcResourcesCpuNum" value="${info.idcResourcesCpuNum}" readonly="readonly"--%>
                               <%--value=""/>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <tr>
                    <td class="kv-content" colspan="4" style="padding: 12px 1px 0px 16px;">主机列表</td>
                </tr>
                <tr>
                    <td class="kv-content" colspan="4" style="padding-left: 0px; height: 300px;width: 790px">
                        <table class="easyui-datagrid" id="dg"
                               data-options="fit:true,toolbar:'#toolbar',border:false">
                            <thead>
                            <tr>
                                <th data-options="field:'idcPhysicsHostsId',hidden:true"></th>
                                <th data-options="field:'idcPhysicsHostsName',width:150">物理机名称</th>
                                <th data-options="field:'idcPhysicsHostsCpuCore',width:130">物理机CPU核数</th>
                                <th data-options="field:'idcPhysicsHostsMemSize',width:130">物理机内存大小(GB)</th>
                                <th data-options="field:'idcPhysicsHostDiskSize',width:130">物理机硬盘大小(GB)</th>
                                <th data-options="field:'idcDiskAvailableSize',width:100">可用硬盘(GB)</th>
                                <th data-options="field:'idcMemAvailableSize',width:100">可用内存(GB)</th>
                                <%--<th data-options="field:'abc',width:60,formatter(v,r,i){--%>
                                      <%--return '<a href=\'javascript:void(0)\' onclick=\'delRow('+i+')\'>删除</a>'--%>
                                        <%--//$(this).datagrid('deleteRow',i);--%>
                                <%--}">操作</th>--%>
                            </tr>
                            </thead>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<div id="toolbar" class="paramContent">
    <div class="param-actionset">
        <div class="btn-cls-common" id="add">添加</div>
        <div class="btn-cls-common" id="del">删除</div>
    </div>
</div>
<script type="text/javascript">
    var rackId = "${rackId}";
    function showRoom() {
        var roomid = $("#roomid").combobox("getValue");
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + roomid + "&buttonType=view";
        openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
    }
    function doSubmit() {
        addIdcRack();
    }
    function addIdcRack() {
        var rows = $("#dg").datagrid("getRows");
        var hostids = [];
        $.each(rows,function(i,item){
            hostids.push(item['idcPhysicsHostsId']);
        })
        $('#idcRackForm').form('submit', {
            url: "<%=request.getContextPath() %>/virtualpool/save",
            queryParams: {hostids: hostids.join(",")},
            onSubmit: function () {
                return $("#idcRackForm").form('validate');
            },
            success: function (data) {
                var re = eval("(" + data + ")");
                if(!re.state){
                    top.layer.msg("添加失败")
                    return ;
                }
                var parentWin = top.winref[window.name];
                top[parentWin].location.reload();
                var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(index); //再执行关闭
            }
        });
    }
    // 打开对话框(添加修改)
    function openDialog(title, url, width, height, callback) {
        if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端，就使用自适应大小弹窗
            width = 'auto';
            height = 'auto';
        } else {//如果是PC端，根据用户设置的width和height显示。

        }
        var index = top.layer.open({
            type: 2,
            area: [width, height],
            title: title,
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
                iframeWin.contentWindow.doSubmit(callback, index)
            },
            cancel: function (index) {
                //setTimeout(function () {
                //    window.location.reload(true);
                //}, 500);
            }
        });
        return index;
    }
    $(function () {
//        var width = $("#dg").parent().width();
//        $("#dg").width(width);
//        $("#dg").height($("body").height()-100);
//        $("#dg").datagrid();
//        $("#dg").datagrid(
//                {
//                    fit: true,
//                    frozenColumns: [[
//                        {field: 'idcPhysicsHostsId', hidden: true, width: 30},
//                        {field: 'idcPhysicsHostsName', title: '物理机名称', width: 150}
//                    ]],
//                    columns: [[
//                        {field: 'idcPhysicsHostsCpuCore', title: '物理机CPU核数', width: 150},
//                        {field: 'idcPhysicsHostsMemSize', title: '物理机内存大小(GB)', width: 150},
//                        {field: 'idcPhysicsHostDiskSize', title: '物理机硬盘大小(GB)', width: 150},
////                        {field: 'idcReserveCpuCore', title: '预留CPU核数', width: 100},
////                        {field: 'idcReserveMemSize', title: '预留内存(GB)', width: 100},
////                        {field: 'idcReserveDiskSize', title: '预留硬盘(GB)', width: 100},
//                        {field: 'idcDiskAvailableSize', title: '可用硬盘(GB)', width: 150},
//                        {field: 'idcMemAvailableSize', title: '可用内存(GB)', width: 150},
//                        {
//                            field: '操作', title: '操作', width: 150, formatter: function (value, row, index) {
//                            $(this).datagrid("deleteRow", index);
//                        }
//                        }
//                    ]]
//                });
        //添加设备
        $("#del").on("click",function(){
            var checkedRows = $("#dg").datagrid("getChecked");
            $.each(checkedRows,function(i,iteam){
                var index = $("#dg").datagrid("getRowIndex",iteam);
                $("#dg").datagrid("deleteRow", index);
            })
            getResSize();
        })
        $("#add").on("click", function () {
            var index1 = openDialog('选择设备', contextPath + '/physicshost/select', '600px', '430px', function (data, index) {
                var rows = $("#dg").datagrid("getRows");
//                var rowlength = rows.length;
//                var maxlength = $("#idcMaxNum").numberbox("getValue");
                var rowid = {};
                $.each(rows, function (i, iteam) {
                    rowid[iteam.idcPhysicsHostsId] = '';
                })
                $.each(data, function (i, iteam) {
                    if (typeof (rowid[iteam.idcPhysicsHostsId]) == 'undefined') {
                        $("#dg").datagrid("appendRow", iteam);
//                        if(rowlength+1<=maxlength){
//
//                            rowlength+=1;
//                        }

                    }
                })
                getResSize();
                top.layer.close(index1); //再执行关闭
            })
        })
    })
    function delRow(index){
        $("#dg").datagrid("deleteRow", index);
        getResSize();
    }
    function getResSize(){
        var rows = $("#dg").datagrid("getRows");
        var diskSzie = 0, cpu=0, men= 0;
        $.each(rows,function(i,iteam){
            var tmem = parseInt(iteam['idcMemAvailableSize']);
            var tdisk = parseInt(iteam['idcDiskAvailableSize']);
            var tcpu = parseInt(iteam['idcPhysicsHostsCpuCore']);
            men+=tmem>0?tmem:0;
            diskSzie+=tdisk>0?tdisk:0;
            cpu+=tcpu>0?tcpu:0;
        })
        $("#idcResourcesMemSize").textbox("setValue",men);
        $("#idcResourcesDiskSize").textbox("setValue",diskSzie);
        $("#idcResourcesCpuNum").textbox("setValue",cpu);
    }
</script>
</body>
</html>