/**
 * Created by mylove on 2017/5/8.
 */
var CurrNode = null;
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //top.hideLeft();
    //新增
    $("#add").click(function () {
        MyResource.open("machineroom", 0);
    })
    $("#closeBtn").click(function(){
        top.mylayer.closeAll();
    })
    //修改
    $("#edit").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        if (rows.length < 1) {
            layer.msg("请选择要修改的机房");
            return;
        } else if (rows.length > 1) {
            layer.msg("只能选择一个机房");
            return;
        }
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + rows[0].ID + "&buttonType=update";
        openDialog('编辑机房信息', url, '800px', '530px');
    })
    //查看机房平面视图
    $("#view2d").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if(row==null){
            top.layer.msg('请选择一条数据')
            return ;
        }
        window.open(contextPath+'/roomlayout/'+row.ID);
    })
    //导出
    $("#exportData").click(function () {
        var searchStr = "";
        if(typeof CurrNode!='undefined'&&CurrNode!=null){
            searchStr = CurrNode.id;
        }
        window.open(contextPath + '/zbMachineroom/exportRoomData?searchStr='+searchStr);
    })
    //导入
    $("#importData").click(function () {
        hideJdt();
        $('#dlg_pic').dialog({
            title: '导入',
            closed: false,
        });
    });
    //删除
    $("#del").click(function () {
        var rows = $('#dg').datagrid('getSelections');
        var rowArr = new Array();
        for (var i = 0; i < rows.length; i++) {
            rowArr.push(rows[i].ID);
        }
        if (rows.length<1) {
            alert("请选择要删除的内容");
            return;
        } else {
            $.post(contextPath + '/zbMachineroom/queryRackNumByRoomId', {ids: rowArr.join(",")}, function (result) {
                if (result.msg != null && result.msg != '') {
                    alert("机房" + result.msg + "下存在机架，不能删除");
                    return;
                } else {
                    layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
                        $.post(contextPath + '/zbMachineroom/del', {ids: rowArr.join(",")}, function (result) {
                            alert(result.msg);
                            $('#dg').datagrid({
                                url: contextPath + "/zbMachineroom/list.do",
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
                {field: 'ID', hidden: true, width: 30},
                {field: 'SITENAME', title: '机房名称', width: 200},
                {field: 'NAME', title: '机楼', width: 200, formatter: function (value, row, index) {
                    if(value!=null&&value!=''){
                        return '<a href="javascript:void(0)" onclick="showBuilding(' + row.BID + ')">' + value + '</a>';
                    }else{
                        return '';
                    }
                }}
            ]],
            columns: [[
                {
                    field: 'ROOMCATEGORY', title: '机房类别', width: 100, formatter: function (value, row, index) {
                    if (value == 0) {
                        return '模块化机房';
                    } else if (value == 1) {
                        return '普通机房';
                    } else if (value == 2) {
                        return '热管背板机房';
                    } else {
                        return '';
                    }
                }
                },
                // {field: 'GRADE', title: '机房等级', width: 100},
                // {field: 'MAXRACKS', title: '最大容量(机架数)', width: 100},
                // {field: 'DESIGNELECTRICITY', title: '总设计电量(KVA)', width: 130},
                // {field: 'AREA', title: '面积', width: 100},
                // {field: 'FLOORHEIGHT', title: '楼层高度(米)', width: 100},
                // {field: 'AIRCONDITIONINGRESERVE', title: '空调备用', width: 100},
                {
                    field: 'USEFOR', title: '业务类型', width: 100, formatter: function (value, row, index) {
                    if (value == 1) {
                        return '自有业务';
                    } else if (value == 2) {
                        return '网络核心机房';
                    } else if (value == 3) {
                        return '政企业务';
                    } else {
                        return '';
                    }
                }
                },
                // {field: 'MODIFYTIME', title: '修改时间', width: 200, sortable: true,formatter: fmtDateAction},
                // {field: 'ACCESSPORTTOTAL', title: '接入端口总数', width: 100},
                // {field: 'ACCESSPORTUSAGE', title: '接入端口使用率(%)', width: 200},
                {field: 'RACKSPARE', title: '空闲机架数', width: 100},
                {field: 'RACKCOUNT', title: '机架总数', width: 100},
                {field: 'RACKUSAGE', title: '机架使用率(%)', width: 100},
                {field: 'TOTALBANDWIDTH', title: '机房出口带宽', width: 100},
                {field: 'pue', title: '昨日耗电量(KWH)', width: 100},
                {field: 'RESPONSIBLEUSERID', title: '负责人', width: 100},
                {field: 'REMARK', title: '备注', width: 100}
            ]],
            type: 'machineroom',
            title: '机房信息',
            toolbar: '#toolbar',
            pageSize: 20,
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            fitColumns:true,
            onClickRow: fun,
            url: contextPath + "/zbMachineroom/list.do",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + row.ID + "&buttonType=view";
                openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
            },
            rtree: {
                r_type: 1,
                onClick: function (iteam, treeId, treeNode) {
                    if (treeNode.id.indexOf("building_") > -1 || treeNode.id.indexOf("location_") > -1) {
                        CurrNode = treeNode;
                        searchModels();
                    }
                }
            },
        }
        return option;
    }
});
function searchModels() {
    var name = $("#buiName").val();
    var queryParams = {SearchKey: name};
    if (typeof CurrNode!='undefined'&&CurrNode != null) {
        var id = CurrNode.id;
        var datas = id.split("_");
        queryParams.SearchType = datas[0];
        queryParams.SearchId = datas[1];
    }
    $('#dg').datagrid({
        url: contextPath + "/zbMachineroom/list.do",
        queryParams: queryParams
    });
}
function showBuilding(id) {
    var url = contextPath + "/idcBuilding/getIdcBuildingInfo.do?id=" + id + "&buttonType=view";
    openDialogView('机楼信息', url, '800px', '400px');
}
//导入文件
function importFileClick() {
    // $.ajaxFileUpload({
    //     type: "POST",
    //     url: contextPath + "/zbMachineroom/importRoomData",
    //     data: {},//要传到后台的参数，没有可以不写
    //     secureuri: false,//是否启用安全提交，默认为false
    //     fileElementId: 'uploadFileid',//文件选择框的id属性
    //     dataType: 'json',//服务器返回的格式
    //     async: false,
    //     success: function (data) {
    //         if (data.state == true) {
    //             alert(data.msg);
    //         } else {
    //             //coding
    //         }
    //     },
    //     error: function (data, status, e) {
    //
    //     }
    // });
    $('#fm_pic').form('submit', {
        url: contextPath + "/zbMachineroom/importRoomData",
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
