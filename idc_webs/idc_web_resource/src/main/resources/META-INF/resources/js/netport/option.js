/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //新增
    $("#add").click(function () {
        MyResource.open("netport", 0);
    });
    //修改
    $("#edit").click(function(){
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择设备");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个设备");
            return;
        }
        //MyResource.open("device", rows[0].id);
        var  url = contextPath+"/netport/netportDetails.do?id="+rows[0].PORTID+"&buttonType=update";
        top.layer.open({
            type: 2,
            area: ['800px', '530px'],
            fixed: false, //不固定
            maxmin: true,
            content: url
        })
    });
    //删除
    $("#del").click(function(){
        var rowArr = new Array();
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择设备");
            return;
        }
        for(var i=0;i<rows.length;i++){
            rowArr.push(rows[i].PORTID);
        }
        layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
            $.post(contextPath + "/netport/deleteNetProtByIds.do", {ids: rowArr.join(',')}, function (result) {
                layer.msg(result.msg);
                //刷新列表
                reloadGrid;
            });
            layer.close(index);
        });
    })
    function getOptions() {
        var option = {
            frozenColumns: [[
                {field: 'PORTID',hidden:true, width: 100},
                {field: 'PORTNAME', title: '端口名称', width: 100}
            ]],
            columns: [[
                {field: 'DNAME', title: '设备', width: 100},
                {field: 'PORTTYPE', title: '端口类型', width: 300},
                {field: 'PORTACTIVE', title: '端口操作状态', width: 100,formatter:function(value,row,index){
                    if(value == 1){
                        return '在用';
                    }else if(value == 2){
                        return '空闲';
                    }else if(value == 3){
                        return '测试';
                    }else if(value == 4){
                        return '未知';
                    }else if(value == 5){
                        return '休眠';
                    }else if(value == 6){
                        return '模块不在';
                    }else if(value == 7){
                        return '下层关闭';
                    }else{
                        return '其他';
                    }
                }},
                {field: 'SIDEDEVICE', title: '对端设备', width: 100},
                {field: 'NOTE', title: '备注', width: 100},
                {field: 'IP', title: '端口对应IP', width: 100},
                {field: 'MAC', title: 'Mac地址', width: 100},
                {field: 'NETMASK', title: '子网掩码', width: 100},
                {field: 'ALIAS', title: '端口别名', width: 100},
                {field: 'DESCR', title: '端口描述', width: 100},
                {field: 'BANDWIDTH', title: '端口带宽', width: 100},
                {field: 'ASSIGNATION', title: '分派带宽', width: 100},
                {field: 'PORTPLTYPE', title: '端口逻辑类型', width: 100,formatter:function(value,row,index){
                    if(value == 0){
                        return '物理端口';
                    }else if(value == 1){
                        return '逻辑端口';
                    }else{
                        return '';
                    }
                }},
                {field: 'MEDIATYPE', title: '物理端口类别', width: 100,formatter:function(value,row,index){
                    if(value == 'fiber'){
                        return '光口';
                    }else if(value == 'cable'){
                        return '电口';
                    }else{
                        return '';
                    }
                }},
                {field: 'ADMINSTATUS', title: '端口管理状态', width: 100,formatter:function(value,row,index){
                    if(value == 1){
                        return 'UP';
                    }else if(value == 2){
                        return 'down';
                    }else{
                        return '';
                    }
                }},
                {field: 'TICKETID', title: '工单编号', width: 100}
            ]],
            type: 'netport',
            title: '端口信息',
            toolbar: '#toolbar',
            pageSize:20,
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            onClickRow: fun,
            url: contextPath + "/netport/list.do",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/netport/netportDetails.do?id=" + row.PORTID + "&buttonType=view";
                top.layer.open({
                    type: 2,
                    area: ['800px', '530px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: url
                })
            }
        }
        return option;
    }
});
//查询
function searchBuilding() {
    var name = $("#buiName").val();
    $('#dg').datagrid({
        url: contextPath + "/netport/list.do",
        queryParams: {name: name}
    });
}
//刷新table
function reloadGrid() {
    $("#dg").datagrid('reload');
}
