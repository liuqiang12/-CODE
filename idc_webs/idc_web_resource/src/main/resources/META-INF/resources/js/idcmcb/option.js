/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //新增
    $("#add").click(function () {
        MyResource.open("idcmcb", 0);
    });
    //修改
    $("#edit").click(function(){
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择MCB");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个MCB");
            return;
        }
        var  url = contextPath+"/idcmcb/idcmcbDetails.do?id="+rows[0].ID+"&buttonType=update";
        openDialog('编辑MCB信息', url,'800px','530px');
    });
    //删除
    $("#del").click(function(){
        var rowArr = new Array();
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择MCB");
            return;
        }
        for(var i=0;i<rows.length;i++){
            rowArr.push(rows[i].ID);
        }
        layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
            $.post(contextPath + "/idcmcb/deleteIdcmcbByIds.do", {ids: rowArr.join(',')}, function (result) {
                layer.msg(result.msg);
                //刷新列表
                $("#dg").datagrid('reload');
            });
            layer.close(index);
        });
    })
    //导入
    $("#importData").click(function () {
        hideJdt();
        $('#dlg_pic').dialog({
            title: '导入',
            closed: false,
        });
    });
    //导出
    $("#exportData").click(function () {
        window.open(contextPath + '/idcmcb/exportDeviceData');
        // $.post(contextPath +'/idcmcb/exportDeviceData',{},function(result){
        //     alert(result.msg);
        // });
    });
    function getOptions() {
        var option = {
            frozenColumns: [[
                {field: 'ID',hidden:true, width: 100},
                {field: 'NAME',title: 'MCB名称', width: 300},
                {field: 'PWRPWRSTATUS', title: 'MCB使用状态', width: 100,formatter:function(value,row,index){
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
                }}
            ]],
            columns: [[
                {field: 'INSTALLNAME', title: 'PDF机架', width: 300,formatter:function(value,row,index){
                    if(value != null && value != ''){
                        return '<a href="javascript:void(0)" onclick="getRackDealis(' + row.PWRINSTALLEDRACKID, 1 + ')">' + value + '</a>';
                    }else{
                        return '';
                    }
                }},
                {field: 'SERVICENAME', title: '服务机架', width: 300,formatter:function(value,row,index) {
                    if (value != null && value != '') {
                        return '<a href="javascript:void(0)" onclick="getRackDealis(' + row.PWRSERVICERACKID, 2 + ')">' + value + '</a>';
                    } else {
                        return '';
                    }
                }},
                {field: 'SYSDESCR', title: 'MCB描述', width: 200}
            ]],
            type: 'idcmcb',
            title: 'MCB信息',
            toolbar: '#toolbar',
            pageSize:20,
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            fitColumns:true,
            onClickRow: fun,
            url: contextPath + "/idcmcb/list.do",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcmcb/idcmcbDetails.do?id=" + row.ID + "&buttonType=view";
                openDialogView('MCB信息', url,'800px','530px');
            }
        }
        return option;
    }
});
function getRackDealis(value, type) {
    if (type == 2) {
        openDialogShowView2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + value + '&businesstype=pdu&buttonType=view', '800px', '400px', '查看机架视图');
    } else if (type == 1) {
        var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + value + "&businesstype=other&buttonType=view";
        openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
    }
}
//查询
function searchModels() {
    var name = $("#buiName").val();
    $('#dg').datagrid({
        url: contextPath + "/idcmcb/list.do",
        queryParams: {name: name}
    });
}
//导入
function importFileClick(){
    // $.ajaxFileUpload({
    //     type: "POST",
    //     url: contextPath + "/idcmcb/importDeviceData",
    //     data:{},//要传到后台的参数，没有可以不写
    //     secureuri : false,//是否启用安全提交，默认为false
    //     fileElementId:'uploadFileid',//文件选择框的id属性
    //     dataType: 'json',//服务器返回的格式
    //     async : false,
    //     success: function(data){
    //         if(data.state==true){
    //             layer.mag(data.msg);
    //         }
    //     },
    //     error: function (data, status, e){
    //
    //     }
    // });
    $('#fm_pic').form('submit',{
        url: contextPath + "/idcmcb/importDeviceData",
        onSubmit: function () {
            return $("#fm_pic").form('validate');
        },
        success: function (data) {
            var obj = eval('(' + data + ')');
            //alert(obj.msg);
            if (obj.state) {
                //top.window['frmright'].window.$('#dg').datagrid("reload");
                setFlagValue('success');
                //closeDialog();
            } else {
                setFlagValue('error');
                //alert("导入失败")
            }
        }
    });
}
//刷新table
function reloadGrid() {
    $("#dg").datagrid('reload');
}
