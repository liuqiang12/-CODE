/**
 * Created by mylove on 2017/5/8.
 */
var url = contextPath + "/virtualdevice/list";
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //$("#add").click(function () {
    //    openDialog('编辑信息', contextPath + '/physicshost/form/0', '800px', '330px')
    //});
    //修改
    //$("#edit").click(function () {
    //    var rows = $('#dg').datagrid('getChecked');
    //    var flag = '';
    //    if (rows.length < 1) {
    //        layer.msg("没有选择设备");
    //        return;
    //    } else if (rows.length > 1) {
    //        layer.msg("只能选择一个设备");
    //        return;
    //    }
    //    openDialog('编辑信息', contextPath + '/physicshost/form/'+rows[0].idcPhysicsHostsId, '800px', '330px')
    //});
    ////删除
    //$("#del").click(function () {
    //    var rowArr = new Array();
    //    var rows = $('#dg').datagrid('getChecked');
    //    if (rows.length < 1) {
    //        layer.msg("没有选择设备");
    //        return;
    //    }
    //    for (var i = 0; i < rows.length; i++) {
    //        rowArr.push(rows[i].DEVICEID);
    //    }
    //    layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
    //        $.post(contextPath + "/device/deleteIdcDeviceByIds.do?deviceclass=2", {ids: rowArr.join(',')}, function (result) {
    //            layer.msg(result.msg);
    //            //刷新列表
    //            $("#dg").datagrid('reload');
    //        });
    //        layer.close(index);
    //    });
    //})
    //    //上下架设备
    //;

    function getOptions() {
        var option = {
            frozenColumns: [[
                {field: 'hostid', hidden: true, width: 30},
                {field: 'hostName', title: '虚拟机名称', width: 200},
                {
                    field: 'idcResourcesPoolname', title: '所属资源池', width: 200, formatter: function (value, row, index) {
                    if (typeof row.idcResourcesPool != 'undefined') {
                        return row.idcResourcesPool.idcResourcesPoolName;
                    } else {
                        return '';
                    }
                }
                },
            ]],
            columns: [[
                {field: 'menSize', title: '内存大小', width: 100},
                {field: 'diskSize', title: '硬盘大小', width: 100},
                {field: 'cpuNum', title: 'CPU核数', width: 100},
                {field: 'ticketId', title: '工单ID', width: 100},
                {field: 'ticketUser', title: '用户', width: 100}
            ]],
            title: '虚拟设备',
            toolbar: '#toolbar',
            pageSize: 20,
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            url: url,
            onDblClickRow: function (index, row) {

            },
            //rtree: {
            //    r_type: 3,
            //    onClick: function (iteam, treeId, treeNode) {
            //        console.log(treeNode);
            //        if (treeNode.id.indexOf("location_") > -1) {
            //            CurrNode = null;
            //            searchModels();
            //        }
            //        if (treeNode.id.indexOf("idcroom_") > -1) {
            //            CurrNode = null;
            //            searchModels();
            //        }
            //        if (treeNode.id.indexOf("idcrack_") > -1) {
            //            CurrNode = treeNode;
            //            searchModels();
            //        }
            //    }
            //}
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
    $('#dg').datagrid({
        url: url,
        queryParams: {idcVmHostsName: name}
    });
}
//导入
//刷新table
function reloadGrid() {
    $("#dg").datagrid('reload');
}

