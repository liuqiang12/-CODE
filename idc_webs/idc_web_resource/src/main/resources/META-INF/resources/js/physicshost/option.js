/**
 * Created by mylove on 2017/5/8.
 */
var url = contextPath + "/physicshost/list";
if(typeof actionType != 'undefined'&&actionType=="select"){
    url+="?type=select"
}
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //top.hideLeft();
    //新增
    $("#add").click(function () {
        openDialog('编辑信息', contextPath + '/physicshost/form/0', '800px', '330px')
    });
    //修改
    $("#edit").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        var flag = '';
        if (rows.length < 1) {
            layer.msg("没有选择设备");
            return;
        } else if (rows.length > 1) {
            layer.msg("只能选择一个设备");
            return;
        }
        openDialog('编辑信息', contextPath + '/physicshost/form/'+rows[0].idcPhysicsHostsId, '800px', '330px')
    });
    //删除
    $("#del").click(function () {
        var rowArr = new Array();
        var rows = $('#dg').datagrid('getChecked');
        if (rows.length < 1) {
            layer.msg("没有选择设备");
            return;
        }
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].DEVICEID);
        }
        layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
            $.post(contextPath + "/device/deleteIdcDeviceByIds.do?deviceclass=2", {ids: rowArr.join(',')}, function (result) {
                layer.msg(result.msg);
                //刷新列表
                $("#dg").datagrid('reload');
            });
            layer.close(index);
        });
    })
        //上下架设备
    ;

    function getOptions() {
        var option = {
            frozenColumns: [[
                {field: 'idcPhysicsHostsId', hidden: true, width: 30},
                {field: 'idcPhysicsHostsName', title: '物理机名称', width: 200},
                //{field: 'idcPhysicsHostsIp', title: '物理机IP', width: 200},
            ]],
            columns: [[
                {field: 'idcPhysicsHostsCpuCore', title: '物理机CPU核数', width: 100},
                {field: 'idcPhysicsHostsMemSize', title: '物理机内存大小(GB)', width: 100},
                {field: 'idcPhysicsHostDiskSize', title: '物理机硬盘大小(GB)', width: 100},
                {field: 'idcReserveCpuCore', title: '预留CPU核数', width: 100},
                {field: 'idcReserveMemSize', title: '预留内存(GB)', width: 100},
                {field: 'idcReserveDiskSize', title: '预留硬盘(GB)', width: 100},
                {field: 'idcDiskAvailableSize', title: '可用硬盘(GB)', width: 100},
                {field: 'idcMemAvailableSize', title: '可用内存(GB)', width: 100},
                {field: 'idcPhysicsHostsStutasStr', title: '状态', width: 100},
            ]],
            type: 'location',
            title: '资源设备',
            toolbar: '#toolbar',
            pageSize: 20,
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            url:url,
            onDblClickRow: function (index, row) {},
            rtree: {
                r_type: 3,
                onClick: function (iteam, treeId, treeNode) {
                    console.log(treeNode);
                    if (treeNode.id.indexOf("location_") > -1) {
                        CurrNode = null;
                        searchModels();
                    }
                    if (treeNode.id.indexOf("idcroom_") > -1) {
                        CurrNode = null;
                        searchModels();
                    }
                    if (treeNode.id.indexOf("idcrack_") > -1) {
                        CurrNode = treeNode;
                        searchModels();
                    }
                }
            }
        }
        return option;
    }
});
function getRackDealis(rackId) {
    var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + rackId + "&businesstype=other&buttonType=view";
    openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
}
//查询
function searchModels() {
    var name = $("#buiName").val();
    var roomId = "", rackId = "";
    if (typeof CurrNode != 'undefined' && CurrNode != null) {
        var id = CurrNode.id;
        var datas = id.split("_");
        rackId = datas[1];
    }
    $('#dg').datagrid({
        url: url,
        queryParams: {name: name, deviceclass: 2, roomId: roomId, rackId: rackId}
    });
}
//导入
//刷新table
function reloadGrid() {
    $("#dg").datagrid('reload');
}

