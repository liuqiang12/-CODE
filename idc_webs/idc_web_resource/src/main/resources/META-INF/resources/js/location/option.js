/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //新增
    $("#add").click(function () {
        MyResource.open("location", 0)
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
        if(row.length<1){
            alert("请选择要删除的内容");
            return;
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
                { field: 'id',hidden:true,width:30},
                {field: 'name', title: '名称', width: 200},
                {field: 'address', title: '地址', width: 200},
            ]],
            columns: [[
                {field: 'province', title: '省份', width: 100},
                {field: 'city', title: '地市', width: 100},
                {field: 'district', title: '地区', width: 100},
                {field: 'zipcode', title: '邮编', width: 100},
                {field: 'contactperson', title: '联系人', width: 100},
                {field: 'contacttel', title: '电话', width: 100},
                {field: 'contactfax', title: '传真', width: 100},
                {field: 'totalbandwidth', title: '互联网出入口带宽', width: 100},
                {field: 'gatewayip', title: '网关IP地址', width: 100},
                {field: 'ratedcapacity', title: '额定电量(KWH)', width: 100},
                {field: 'remark', title: '备注', width: 200}
            ]],
            type: 'location',
            title: '数据中心',
            toolbar: '#toolbar',
            pageSize: 20,
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            onClickRow: fun,
            url: contextPath + "/idcLocation/list.do",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcLocation/getIdcLocationInfo.do?id=" + row.id + "&buttonType=view";
                openDialogView('数据中心信息', url, '800px', '530px');
            }
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