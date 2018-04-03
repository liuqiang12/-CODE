/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //新增
    $("#add").click(function () {
        openDialog('编辑信息',contextPath + '/virtualpool/form/0','800px','530px')
    });
    //修改
    $("#edit").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("请选择要修改的数据中心");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个数据中心");
            return;
        }
        openDialog('编辑数据中心信息', contextPath + "/idcLocation/getIdcLocationInfo.do?id=" + rows[0].id + "&buttonType=update", '800px', '530px');
    })
    //删除
    $("#del").click(function () {
        var row = $('#dg').datagrid('getSelections');
        var list=new Array();
        for(var i = 0;i<row.length;i++){
            list[i] = row[i].id;
        }
        if(row==null){
            alert("请选择要删除的内容");
        }else{
            layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
                $.post(contextPath + '/idcLocation/del', {list: list.toString()}, function (result) {
                    alert(result.msg);
                    $('#dg').datagrid({
                        url: contextPath + "/idcLocation/list.do",
                    });
                });
                layer.close(index);
            });
        }
    })
    function getOptions() {
        var option = {
            frozenColumns: [[
                { field: 'idcResourcesPoolId',hidden:true,width:30},
                {field: 'idcResourcesPoolName', title: '资源池名称', width: 200},
                {field: 'idcResourcesPoolCode', title: '资源池编码', width: 200},
            ]],
            columns: [[
                {field: 'idcResourcesUseRate', title: '资源占用率', width: 100},
                {field: 'idcResourcesCpuNum', title: '总CPU数量', width: 100},
                {field: 'idcResourcesMemSize', title: '总内存大小', width: 100},
                {field: 'idcResourcesDiskSize', title: '总硬盘大小', width: 100},
                {field: 'idcResourcesMemUseRate', title: '内存占用率', width: 100},
                {field: 'idcResourcesCpuUseRate', title: 'CPU占用率', width: 100},
                {field: 'idcResourcesDiskUseRate', title: '硬盘占用率', width: 100},
                {field: 'idcResourcesPoolStatus', title: '状态', width: 100}
            ]],
            type: 'location',
            title: '资源池',
            toolbar: '#toolbar',
            pageSize: 20,
            singleSelect: false,
            selectOnCheck: true,
            checkOnSelect: true,
            onDblClickRow: function (index, row) {},
            url: contextPath + "/virtualpool/list"
            //rtree:function(dom){
            //    console.log(dom);
            //}
        }
        return option;
    }
});
function searchModels() {
    var name = $("#buiName").val();
    $('#dg').datagrid({
        url: contextPath + "/idcLocation/list.do",
        queryParams:{name:name}
    });
}