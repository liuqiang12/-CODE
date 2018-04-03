/**
 * Created by mylove on 2017/5/8.
 */

//var pathArr = ['<a href="javascript:void(0)" onclick="nav(0)">IP网段信息</a>'];
var pathNode = [];
var isSbunet = true;//子网界面
var currRow = null;
$(function () {
    $('#tt').tree({
        url: contextPath + '/idcLocation/list.do',
        onClick:function(node){
            localid = node.id
            navto(0);
        },
        loadFilter: function (data) {
            var trees = [];
            $.each(data.rows,function(i,iteam){
                trees.push(
                    {id:iteam.id,text:iteam.name}
                )
            })
            return [{id:'',text:'数据中心',children:trees}]
        }
    });
    var isLoadIp = false;
    showMenu(0);
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    $("#add").click(function () {
        openDialog('添加子网信息', contextPath + '/resource/ipsubnet/0', '800px', '330px')
        //openDialog('编辑子网信息', contextPath + '/idcRack/add/equipment/0','800px','530px')
    })
    $("#edit").click(function () {
        var row = $('#dg').datagrid('getSelected');
        if (row == null) {
            alert("请选择要修改的内容");
        } else {
            openDialog('编辑子网信息', contextPath + '/resource/ipsubnet/' + row.id, '800px', '330px')
        }
    })
    $("#splitsubnet").click(function () {
        var row = $('#dg').datagrid('getSelected');
        if (row == null) {
            top.layer.msg('请选择一条数据')
            return;
        }
        if (row.parent == true) {
            top.layer.msg('该子网不能拆分')
            return;
        }
        openDialog('子网切分', contextPath + '/resource/ipsubnet/splitsubnet/' + row.id, '800px', '530px')
    });
    //合并子网
    $("#mergesubnet").click(function () {
        var row = $('#dg').datagrid('getSelected');
        if (row == null) {
            top.layer.msg('请选择一条数据')
            return;
        }
        if (row.parent == true) {
            top.layer.msg('该子网不能再次合并')
            return;
        }
        openDialog('子网合并', contextPath + '/resource/ipsubnet/mergesubnet/' + row.id, '800px', '530px')
    });
    //分配IP
    $("#allocation").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row == null) {
            top.layer.msg('请分配的iP')
            return;
        }
        if (row.status!=null &&row.status!= 0) {
            top.layer.msg('该IP已经被分配')
            return;
        }
        layer.open({
            type: 1
            ,title: '分配IP' //不显示标题栏
            ,closeBtn: false
            ,area: ['400px;']
            ,shade: 0.8
            ,resize: false
            ,btnAlign: 'c'
            ,moveType: 1 //拖拽模式，0或者1
            ,btn:['确定','取消']
            ,yes:function(index, layero){
                //按钮【按钮一】的回调
                var usetype = $("#all_usetype").val();
                var all_remark = $("#all_remark").val();
                save(usetype,all_remark,index);
            }
            //,btn: ['业务地址', '互联地址', '管理地址','广播管理地址','网络地址','取消']
            ,content: '<table class="kv-table"><tbody> <tr><td class="kv-label">IP用途</td>' +
            '<td class="kv-content">' +
            '<select id="all_usetype">' +
            '<option value="1">业务地址</option>' +
            '<option value="2">互联地址</option>' +
            '<option value="3">管理地址</option>' +
            '<option value="4">广播管理地址</option>' +
            '<option value="5">网络地址</option>' +
            '</td></tr><tr><td class="kv-label">备注</td><td class="kv-content">' +
            '<textarea id="all_remark" style="rows:5" ></textarea>' +
            '</td></tr></tbody></table>'
        });
        //var indexd = layer.msg('选择地址用途', {
        //    time: 0, //20s后自动关闭
        //    area:['700px'],
        //    btnAlign:'c',
        //    btn: ['业务地址', '互联地址', '管理地址','广播管理地址','网络地址','取消']
        //}, function(index, layero){
        //    //按钮【按钮一】的回调
        //}, function(index){
        //    //按钮【按钮二】的回调
        //}, function(index){
        //    //按钮【按钮二】的回调
        //}, function(index){
        //    //按钮【按钮二】的回调
        //}, function(index){
        //    //按钮【按钮二】的回调
        //});
        //layer.prompt({title: 'IP用途:将IP'+row.ipaddress+'用作：', formType: 2}, function(text, index){
        //        $.post(contextPath+"/resource/ipinfo/allocation",{remark:text,id:row.id},function(){
        //            layer.msg('分配完成...');
        //            layer.close(index);
        //            $("#dg").datagrid("reload")
        //        });
        //
        //    });
        function save(usetype,all_remark,index){
                    $.post(contextPath+"/resource/ipinfo/allocation",{usetype:usetype,remark:all_remark,id:row.id},function(){
                        layer.msg('分配完成...');
                        layer.close(index);
                        $("#dg").datagrid("reload")
                    });
        }
    });

    $("#recovery").click(function(){
        var row = $('#dg').datagrid('getSelected');
        if (row == null) {
            top.layer.msg('请回收的IP')
            return;
        }
        if (row.status==null ||row.status== 0) {
            top.layer.msg('该IP尚未分配')
            return;
        }
        layer.prompt({title: '回收IP'+row.ipaddress+'：', formType: 2}, function(text, index){
            $.post(contextPath+"/resource/ipinfo/recovery",{remark:text,id:row.id},function(){
                layer.msg('回收完成...');
                layer.close(index);
                $("#dg").datagrid("reload")
            });

        });
    });
    $("#exportData").click(function () {
        window.open(contextPath + '/idcRack/export');
    });
    $("#importData").click(function () {
        $('#dlg_pic').dialog({
            title: '导入',
            closed: false,
        });
    })

    $("#del").click(function () {
        debugger
        var row = $('#dg').datagrid('getSelections');
        var list = new Array();
        for (var i = 0; i < row.length; i++) {
            list[i] = row[i].id;
        }
        if (row == null) {
            alert("请选择要删除的内容");
        } else {
            top.layer.confirm('确认删除该子网?', {
                btn: ['确认', '取消'] //按钮
            }, function (index, layero) {
                top.layer.close(index);
                $.post(contextPath + '/resource/ipsubnet/del', {ids: list.join(",")}, function (result) {
                    if (result.state) {
                        $('#dg').datagrid("reload");
                    } else {
                        top.layer.msg(result.msg)
                    }

                });
            });
        }
    })


});

var localid = '';
function getIpOptions(srcoption) {
    if (typeof (srcoption) == 'undefined') {
        srcoption = {};
    }
    var  url= contextPath + "/resource/ipinfo/list";
    if(actiontype=="flowallc"){
        url+="?actionType=flowallc"
    }
    var option = {
        columns: [[
            {field: 'id', hidden: true, width: 30},
            {field: 'ipaddress', title: 'IP', width: 150},
            {
                field: 'mask', title: '掩码', width: 150, formatter: function (value, row, index) {
                return row.maskstr + '/' + value
            }
            },
            {
                field: 'subnetip', title: '子网IP', width: 150, formatter: function (value, row, index) {
                    return '<a href="javascript:void(0)" onclick="navto(' + pathNode.length + ')">'+pathNode[pathNode.length-1].subnetip+'</a>'
                //return pathArr[pathArr.length - 1];
            }
            },
            {field: 'useTypeStr', title: '地址类别', width: 200},
            {field: 'mac', title: 'MAC', width: 200},
            {field: 'ticket', title: '工单信息', width: 200,formatter:function(value, row, index){
                    if(row.status>0&&value&&value!=''){
                        return '<a href="javascript:void(0)" onclick="showticket('+value+')">'+row.userId+'</a>'
                    }
            }},
            {
                field: 'status', title: '状态', width: 150,
                formatter: function (value, row) {
                    if (value == "1") {
                        return '<font color="red">已用</font>';
                    } else if (value == "0") {
                        return '<font color="grey">空闲</font>';
                    } else if (value == "2") {
                        return '<font color="black">分配占用</font>';
                    } else if (value == "3") {
                        return '<font color="yellow">等待回收</font>';
                    } else {
                        return '<font color="grey">空闲</font>';
                    }
                }
            },
            {field: 'remark', title: '备注', width: 200}
        ]],
        singleSelect: false,
        fitColumns: true,
        pageSize: 20,
        selectOnCheck: true,
        checkOnSelect: true,
        url:url,
        onBeforeCheck:function(index, row){
            if(actiontype=="flowallc"){//分配状态如果空闲不等于所有 禁止分配
                if(row.status>0){
                    return false;
                }
            }
            return true
        },
        onLoadError:function(){
            $("#dg").datagrid("loadData",{})
        },
        onDblClickRow: function (index, row) {
        }
    };
    var settings = $.extend({}, option, srcoption);//将一个空对象做为第一个参数
    return settings;
}
function showticket(ticket){
    if(ticket){
        openDialog('查看工单信息', contextPath + '/actJBPMController/author_apply_show/'+ticket+"/0", '1024px', '90%')
    }else{
        alert("测试数据,没有关联工单信息,请重新走工单流程!");
    }

}
function getOptions(srcoption) {
    if (typeof (srcoption) == 'undefined') {
        srcoption = {};
    }
    var url =  contextPath + "/resource/ipsubnet/list";
    if(actiontype=="flowallc"){
        url+="?actionType=flowallc"
    }

    var option = {
        columns: [[
            {field: 'id', hidden: true, width: 30},
            {field: 'subnetip', title: '子网IP', width: 150},
            {
                field: 'mask', title: '掩码', width: 200, formatter: function (value, row, index) {
                return row.maskstr + '/' + value
            }
            },
            {field: 'begip', title: '地址段开始', width: 150},
            {field: 'endip', title: '地址段结束', width: 150},
            {field: 'allip', title: '总IP数', width: 60},
            {field: 'free', title: '空闲IP数', width: 70},
            {
                field: 'ipfreed', title: '空闲率', width: 150, formatter: function (value, row, index) {
                var value = 0;
                if (row.allip > 0) {
                    value = ((row.free) / row.allip * 100).toFixed(2);
                    //value = (100-value).toFixed(2);
                }
                if (value < 20) {
                    return '<div style="width:100%;background:#53FF53;">' +
                        '<div style="margin-left: 0px;float:left; background-color: #53FF53;width:' + value + '%;height:100%">' + value + '%</div>' +
                        '</div>';
                } else {
                    return '<div style="width:100%;background:#F0F0F0;">' +
                        '<div style="margin-left: 0px;float:left; background-color: #53FF53;width:' + value + '%;height:100%">' + value + '%</div>' +
                        '</div>';
                }
            }
            },
            {
                field: 'parent', title: '下级网段', width: 50, formatter: function (value, row, index) {
                if (value == true) {
                    return '<span style="color: red">有</span>'
                } else {
                    return '无'
                }
            }
            },
            {field: 'usefor', title: '用途', width: 150},
            {field: 'remark', title: '说明', width: 150}
        ]],
        type: 'ipsubnet',
        toolbar: '#toolbar',
        singleSelect: false,
        fitColumns: true,
        pageSize: 20,
        selectOnCheck: true,
        checkOnSelect: true,
        url:url,
        onBeforeCheck:function(index, row){
            if(actiontype=="flowallc"){//分配状态如果空闲不等于所有 禁止分配
                if(row.allip!=row.free){
                    return false;
                }
            }
            return true
        },
        onLoadSuccess:function(){
            initnva();
        },
        onDblClickRow: function (index, row) {
            currRow = row.id;
            pathNode.push(row);
            navto(pathNode.length)
            if (row.parent) {
                showMenu(0);
            } else {
                showMenu(1);
            }
        }
    };
    var settings = $.extend({}, option, srcoption);//将一个空对象做为第一个参数
    if( typeof (settings.queryParams)=='undefined'){
        settings.queryParams={};
    }
    settings.queryParams.localid=localid;
    return settings;
}
function initnva(){
    var patharray = [];
    patharray.push('<a href="javascript:void(0)" onclick="navto(' + 0 + ')">IP网段信息</a>');
    for(var i=1;i<pathNode.length+1;i++){
        patharray.push('<a href="javascript:void(0)" onclick="navto(' + i + ')">'+pathNode[i-1].subnetip+'</a>');
    }
    $("#dgtitle").panel({
        title: patharray.join(">>")
    })
}
function showMenu(index) {
    $("#buiName").val("");
    if (index == 1) {
        isSbunet = false;
        $("#lableName").text("IP地址");
        $("#add").hide();
        $("#del").hide();
        $("#edit").hide()
        $("#splitsubnet").hide();
        $("#mergesubnet").hide();
        $("#allocation").show();
        $("#recovery").show();

    } else {
        isSbunet = true;
        $("#lableName").text("子网地址")
        $("#add").show();
        $("#del").show();
        $("#edit").show()
        $("#splitsubnet").show();
        $("#mergesubnet").show();
        $("#allocation").hide();
        $("#recovery").hide();
    }
}
function navto(index){
    if(index==0){
        pathNode =[];
        currRow = null;
        showMenu(0);
        $("#dg").datagrid(getOptions({
            queryParams: {}
        }))
    }else{
        var node = pathNode[index-1];
        currRow = node.id;
        if(node.parent){
            pathNode.splice(index);
            showMenu(0);
            $("#dg").datagrid(getOptions({
                queryParams: {pid: currRow}
            }))
        }else{
            showMenu(1);
            $("#dg").datagrid(getIpOptions({
                queryParams: {subnetid: currRow}
            }));
        }
    }
    initnva()
}
//function nav(order, id) {
//    pathArr.splice(order + 1);
//    var title = pathArr.join(">>");
//    showMenu(0);
//    if (typeof (id) == 'undefined') {
//        currRow = null;
//        $("#dg").datagrid(getOptions({
//            queryParams: {},
//            onLoadSuccess: function (data) {
//                $("#dgtitle").panel({
//                    title: title
//                })
//            }
//        }))
//        //$("#dg").datagrid({
//        //    queryParams: {},
//        //    //title:title,
//        //    onLoadSuccess: function (data) {
//        //
//        //        $("#dgtitle").panel({
//        //            title: title
//        //        })
//        //        $("#ipdgtitle").panel({
//        //            title: title
//        //        })
//        //    }
//        //});
//    } else {
//        currRow = id;
//        $("#dg").datagrid(getOptions({
//            queryParams: {pid: id},
//            onLoadSuccess: function (data) {
//                $("#dgtitle").panel({
//                    title: title
//                })
//            }
//        }))
//        //$("#dg").datagrid({
//        //    queryParams: {pid: id},
//        //    onLoadSuccess: function (data) {
//        //        $("#dgtitle").panel({
//        //            title: title,
//        //        })
//        //        $("#ipdgtitle").panel({
//        //            title: title,
//        //        })
//        //    }
//        //});
//    }
//}
//function addNav(order, nav, id) {
//    var title = '<a href="javascript:void(0)" onclick="nav(' + order + ',' + id + ')">' + nav + '</a>';
//    return title;
//}
function searchModels() {
    var name = $("#buiName").val();
    var queryParams = {SearchKey: name};

    if (isSbunet) {
        if (typeof currRow != 'undefined' && currRow != null) {
            queryParams.pid = currRow
        }
        $('#dg').datagrid({
            queryParams: queryParams
        });
    } else {
        if (typeof currRow != 'undefined' && currRow != null) {
            queryParams.subnetid = currRow
        }
        $('#dg').datagrid({
            //url: contextPath + "/resource/ipinfo/list",
            queryParams: queryParams
        });
    }

}
//文件上传
//导入事件，显示导入弹出窗口
function importClick() {
    $('#dlg_pic').dialog({
        title: '导入',
        closed: false,
    });

}

//导入文件
function importFileClick() {
    $.ajaxFileUpload({
        type: "POST",
        url: contextPath + "/idcRack/inport",
        data: {},//要传到后台的参数，没有可以不写
        secureuri: false,//是否启用安全提交，默认为false
        fileElementId: 'uploadFileid',//文件选择框的id属性
        dataType: 'json',//服务器返回的格式
        async: false,
        success: function (data) {
            if (data.state == true) {
                alert(data.msg);
            } else {
                //coding
            }
        },
        error: function (data, status, e) {

        }
    });
}



