/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    $("#add").click(function () {
        MyResource.open("rack", 0)
    })
    function getOptions() {
        var option = {
            frozenColumns: [[
                {field: 'id', hidden: true, width: 100},
                {field: 'name', title: '名称', width: 200}
            ]],
            columns: [[
                {field: 'location', title: '数据中心', width: 200},
                {field: 'roomareaname', title: '所属机房', width: 100},
                {field: 'rackmodelname', title: '机架型号', width: 100},
                {
                    field: 'usefor', title: '用途', width: 100, formatter: function (value, row, index) {
                    if (value == 1) {
                        return '自用'
                    } else {
                        return '客用'
                    }
                }
                },
                {
                    field: 'renttype', title: '出租类型', width: 100, formatter: function (value, row, index) {
                    if (value == 2) {
                        return '整架出租'
                    } else {
                        return '按机位出租'
                    }
                }
                },
                {
                    field: 'arrangement', title: '机位顺序', width: 100, formatter: function (value, row, index) {
                    if (value == 2) {
                        return '从上而下'
                    } else {
                        return '从下而上'
                    }
                }
                },
                {
                    field: 'status', title: '业务状态', width: 100, formatter: function (value, row, index) {
                    if (value == 110) {
                        return '不可用'
                    } else if (value == 20) {
                        return '可用'
                    } else if (value == 30) {
                        return '预留'
                    } else if (value == 40) {
                        return '空闲'
                    } else if (value == 50) {
                        return '预占'
                    } else if (value == 55) {
                        return '已停机'
                    }
                    else if (value == 60) {
                        return '在服'
                    }
                }
                },
                {field: 'topportproperty', title: '上联端口', width: 100},
                {
                    field: 'businesstypeId', title: '机架类型', width: 100, formatter: function (value, row, index) {
                    if (value == 'equipment') {
                        return '客户机架'
                    } else if (value == 'df') {
                        return 'ODF/DDF'
                    } else if (value == 'cabinet') {
                        return '网络头柜'
                    } else if (value == 'pdu') {
                        return 'PDU'
                    }
                }
                },
                {field: 'height', title: '机架U数', width: 100},
                {field: 'x', title: '机房平面图中的X坐标', width: 100},
                {field: 'y', title: '机房平面图中的Y坐标', width: 100},
                {field: 'pduPowertype', title: '电源类型', width: 100},
                {field: 'pduLocation', title: '所在位置', width: 100},
                {field: 'israckinstalled', title: '机架是否已安装', width: 100}
            ]],
            type: 'rack',
            title: '机架信息',
            toolbar: '#toolbar',
            pageSize: 20,
            rtree: {
                r_type: 3,
                onClick: function (iteam, treeId, treeNode) {
                    console.log(treeId);
                    console.log(treeNode);
                }
            },
            url: contextPath + "/rack/list",
        }
        return option;
    }
});

