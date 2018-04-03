/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //新增
    $("#add").click(function () {
        openDialog('编辑信息', contextPath + '/resource/building/0', '800px', '400px');
    });
    //修改
    $("#edit").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        if (rows.length < 1) {
            layer.msg("请选择要修改的机楼");
            return;
        } else if (rows.length > 1) {
            layer.msg("只能选择一个机楼");
            return;
        }
        var url = contextPath + "/idcBuilding/getIdcBuildingInfo.do?id=" + rows[0].ID + "&buttonType=update";
        openDialog('编辑机楼信息', url, '800px', '530px');
    })
    //删除
    $("#del").click(function () {
        var rows = $('#dg').datagrid('getSelections');
        var rowArr = [];
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].ID);
        }
        if (rows.length<1) {
            alert("请选择要删除的内容");
            return;
        }else{
            $.post(contextPath + '/idcBuilding/queryRoomNumByBuildingId', {ids: rowArr.join(",")}, function (result) {
                if (result.msg != null && result.msg != '') {
                    alert("机楼" + result.msg + "下存在机房，不能删除");
                    return;
                } else {
                    layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
                        $.post(contextPath + '/idcBuilding/del', {ids: rowArr.join(",")}, function (result) {
                            alert(result.msg);
                            $('#dg').datagrid({
                                url: contextPath + "/idcBuilding/list.do",
                            });
                        });
                        layer.close(index);
                    });
                }
            });
        }
    })
    function getOptions() {
        var option = {
            frozenColumns: [[
                { field: 'ID',hidden:true,width:30},
                {field: 'NAME', title: '机楼名称', width: 200},
                {
                    field: 'BNAME', title: '数据中心', width: 200, formatter: function (value, row, index) {
                    if(value!=null&&value!=''){
                        return '<a href="javascript:void(0)" onclick="showLocation('+row.BID+')">' + value + '</a>';
                    }else{
                        return '';
                    }
                }}
            ]],
            columns: [[
                // {field: 'CONTACTTEL', title: '电话', width: 100},
                // {field: 'CONTACTFAX', title: '传真', width: 100},
                {field: 'GATEWAYIP', title: '网关IP地址', width: 100},
                {field: 'TOTALBANDWIDTH', title: '互联网出入口带宽', width: 100},
                {field: 'RACKSPARE', title: '空闲机架数', width: 100},
                {field: 'RACKCOUNT', title: '机架总数', width: 100},
                {field: 'RACKUSAGE', title: '机架使用率(%)', width: 100},
                // {field: 'RATEDCAPACITY', title: '额定电量(KWH)', width: 100},
                {field: 'FLOORNUMBER', title: '楼层数', width: 100},
                {field: 'SEISMICGRADE', title: '抗震等级', width: 100},
                {field: 'pue', title: '昨日耗电量(KWH)', width: 100},
                {field: 'REMARK', title: '备注', width: 100}
            ]],
            type: 'building',
            title: '机楼信息',
            toolbar: '#toolbar',
            pageSize: 20,
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            fitColumns:true,
            onClickRow: fun,
            url: contextPath + "/idcBuilding/list.do",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcBuilding/getIdcBuildingInfo.do?id=" + row.ID + "&buttonType=view";
                openDialogView('机楼信息', url, '800px', '530px');
            }
        }
        return option;
    }
});
function searchModels() {
    var name = $("#buiName").val();
    $('#dg').datagrid({
        url: contextPath + "/idcBuilding/list.do",
        queryParams: {name: name}
    });
}

function  showLocation(id) {
    var url = contextPath + "/idcLocation/getIdcLocationInfo.do?id=" + id+"&buttonType=view";
    openDialogView('数据中心信息', url, '800px', '530px');
}