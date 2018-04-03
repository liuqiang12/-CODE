/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //top.hideLeft();
    //新增
    $("#add").click(function () {
        MyResource.open("device", 0);
    });
    //修改
    $("#edit").click(function(){
        var rows = $('#dg').datagrid('getChecked');
        var flag = '';
        if(rows.length<1){
            layer.msg("没有选择设备");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个设备");
            return;
        }
        $.post(contextPath+"/device/getDeviceIsUpRack.do?deviceid="+rows[0].DEVICEID,function(result){
            flag = result.msg;
            var  url = contextPath+"/device/deviceDetails.do?id="+rows[0].DEVICEID+"&buttonType=update&deviceclass=1&flag="+flag;
            var btn=[];
            //视图上下架
            var btnValue = document.getElementsByName('downOrUp');
            var btnStr = "";
            if (btnValue != null && btnValue.length > 0) {
                for (var i = 0; i < btnValue.length; i++) {
                    btnStr += btnValue[i].value + ',';
                }
            }
            //快速上下架
            var quickBtnValue = document.getElementsByName('quickDownOrUp');
            var quickBtnStr = "";
            if (quickBtnValue != null && quickBtnValue.length > 0) {
                for (var i = 0; i < quickBtnValue.length; i++) {
                    quickBtnStr += quickBtnValue[i].value + ',';
                }
            }
            if(flag=='dwon'){
                if (btnStr.indexOf('2') > -1 && quickBtnStr.indexOf('2') > -1) {
                    btn = ['快速下架', '视图下架', '确定', '关闭'];
                    top.layer.open({
                        type: 2,
                        area: ['800px', '530px'],
                        fixed: false,
                        maxmin: true,
                        content: url,
                        btn: btn,
                        success: function (layero, index) {
                            var name = layero.find('iframe')[0].name;
                            top.winref[name] = window.name;
                        },
                        btn1: function (index, layero) {//快速下架
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.quickUpOrDwonRack());
                            return false;
                        },
                        btn2: function (index, layero) {//视图下架
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.upOrDwonRack());
                            return false;
                        },
                        btn3: function (index, layero) {//确定
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.doSubmit());
                            return false;
                        },
                        btn4: function (index, layero) {//关闭
                            top.layer.close(index);
                        }
                    })
                } else if (btnStr.indexOf('2') > -1 && quickBtnStr.indexOf('2') == -1) {
                    btn = ['视图下架', '确定', '关闭'];
                    top.layer.open({
                        type: 2,
                        area: ['800px', '530px'],
                        fixed: false,
                        maxmin: true,
                        content: url,
                        btn: btn,
                        success: function (layero, index) {
                            var name = layero.find('iframe')[0].name;
                            top.winref[name] = window.name;
                        },
                        btn1: function (index, layero) {//视图下架
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.upOrDwonRack());
                            return false;
                        },
                        btn2: function (index, layero) {//确定
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.doSubmit());
                            return false;
                        },
                        btn3: function (index, layero) {//关闭
                            top.layer.close(index);
                        }
                    })
                } else if (btnStr.indexOf('2') == -1 && quickBtnStr.indexOf('2') > -1) {
                    btn = ['快速下架', '确定', '关闭'];
                    top.layer.open({
                        type: 2,
                        area: ['800px', '530px'],
                        fixed: false,
                        maxmin: true,
                        content: url,
                        btn: btn,
                        success: function (layero, index) {
                            var name = layero.find('iframe')[0].name;
                            top.winref[name] = window.name;
                        },
                        btn1: function (index, layero) {//快速下架
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.quickUpOrDwonRack());
                            return false;
                        },
                        btn2: function (index, layero) {//确定
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.doSubmit());
                            return false;
                        },
                        btn3: function (index, layero) {//关闭
                            top.layer.close(index);
                        }
                    })
                } else {
                    btn = ['确定', '关闭'];
                    top.layer.open({
                        type: 2,
                        area: ['800px', '530px'],
                        fixed: false,
                        maxmin: true,
                        content: url,
                        btn: btn,
                        success: function (layero, index) {
                            var name = layero.find('iframe')[0].name;
                            top.winref[name] = window.name;
                        },
                        btn1: function (index, layero) {//确定
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.doSubmit());
                            return false;
                        },
                        btn2: function (index, layero) {//关闭
                            top.layer.close(index);
                        }
                    })
                }
            } else {
                if (btnStr.indexOf('1') > -1 && quickBtnStr.indexOf('1') > -1) {
                    btn = ['快速上架', '视图上架', '确定', '关闭'];
                    top.layer.open({
                        type: 2,
                        area: ['800px', '530px'],
                        fixed: false,
                        maxmin: true,
                        content: url,
                        btn: btn,
                        success: function (layero, index) {
                            var name = layero.find('iframe')[0].name;
                            top.winref[name] = window.name;
                        },
                        btn1: function (index, layero) {//快速上架
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.quickUpOrDwonRack());
                            return false;
                        },
                        btn2: function (index, layero) {//视图上架
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.upOrDwonRack());
                            return false;
                        },
                        btn3: function (index, layero) {//确定
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.doSubmit());
                            return false;
                        },
                        btn4: function (index, layero) {//关闭
                            top.layer.close(index);
                        }
                    })
                } else if (btnStr.indexOf('1') > -1 && quickBtnStr.indexOf('1') == -1) {
                    btn = ['视图上架', '确定', '关闭'];
                    top.layer.open({
                        type: 2,
                        area: ['800px', '530px'],
                        fixed: false,
                        maxmin: true,
                        content: url,
                        btn: btn,
                        success: function (layero, index) {
                            var name = layero.find('iframe')[0].name;
                            top.winref[name] = window.name;
                        },
                        btn1: function (index, layero) {//视图上架
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.upOrDwonRack());
                            return false;
                        },
                        btn2: function (index, layero) {//确定
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.doSubmit());
                            return false;
                        },
                        btn3: function (index, layero) {//关闭
                            top.layer.close(index);
                        }
                    })
                } else if (btnStr.indexOf('1') == -1 && quickBtnStr.indexOf('1') > -1) {
                    btn = ['快速上架', '确定', '关闭'];
                    top.layer.open({
                        type: 2,
                        area: ['800px', '530px'],
                        fixed: false,
                        maxmin: true,
                        content: url,
                        btn: btn,
                        success: function (layero, index) {
                            var name = layero.find('iframe')[0].name;
                            top.winref[name] = window.name;
                        },
                        btn1: function (index, layero) {//快速上架
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.quickUpOrDwonRack());
                            return false;
                        },
                        btn2: function (index, layero) {//确定
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.doSubmit());
                            return false;
                        },
                        btn3: function (index, layero) {//关闭
                            top.layer.close(index);
                        }
                    })
                } else {
                    btn = ['确定', '关闭'];
                    top.layer.open({
                        type: 2,
                        area: ['800px', '530px'],
                        fixed: false,
                        maxmin: true,
                        content: url,
                        btn: btn,
                        success: function (layero, index) {
                            var name = layero.find('iframe')[0].name;
                            top.winref[name] = window.name;
                        },
                        btn1: function (index, layero) {//确定
                            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                            console.log(iframeWin.contentWindow.doSubmit());
                            return false;
                        },
                        btn2: function (index, layero) {//关闭
                            top.layer.close(index);
                        }
                    })
                }
            }
        });
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
            rowArr.push(rows[i].DEVICEID);
        }
        //先判断选择的设备是否存在已经上架的设备  若存在不能删除
        $.post(contextPath + '/device/queryDeviceNumByDeviceId', {ids: rowArr.join(",")}, function (result) {
            if (result.msg != null && result.msg != '') {
                alert("设备" + result.msg + "已上架，请先下架再删除");
                return;
            } else {
                layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
                    $.post(contextPath + "/device/deleteIdcDeviceByIds.do?deviceclass=1", {ids: rowArr.join(',')}, function (result) {
                        layer.msg(result.msg);
                        //刷新列表
                        $("#dg").datagrid('reload');
                    });
                    layer.close(index);
                });
            }
        });
    })
    //查看端口信息
    $("#netProt").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择设备");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个设备");
            return;
        }
        var  url = contextPath+"/netport/netportList.do?deviceId="+rows[0].DEVICEID;
        openDialogView('端口信息', url, '800px', '530px');
    });
    //上下架设备
    $("#upordownDevice").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        if (rows.length != 1) {
            layer.msg("有且只能选择1个设备");
            return;
        }
        var uheight = rows[0].UHEIGHT;
        if (uheight == null || uheight == 0) {
            layer.msg("设备没有高度，不能上架");
            return;
        }
        var rackId = rows[0].RACKID;
        if (rackId == null || rackId == '') {
            var inx = top.layer.open({
                type: 2,
                title: '机架列表',
                shadeClose: false,
                shade: 0.8,
                btn: ['确定', '关闭'],
                maxmin: true,
                area: ['800px', '530px'],
                content: contextPath + '/idcRack/preUpordownForDeviceRackList',
                /*弹出框*/
                cancel: function (index, layero) {
                    top.layer.close(index);
                }, yes: function (index, layero) {
                    var childIframeid = layero.find('iframe')[0]['name'];
                    var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                    rackId = chidlwin.doSubmit();
                    if (rackId == null) {
                        layer.msg("没有选择上架机架");
                        return;
                    }
                    top.layer.close(index);
                    window.open(contextPath + '/racklayout/' + rackId + '?deviceId=' + rows[0].DEVICEID);
                }
            })
        } else {
            window.open(contextPath + '/racklayout/' + rackId + '?deviceId=' + rows[0].DEVICEID);
        }

    });
    //快速上下架设备
    $("#quickUpordownDevice").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        if (rows.length != 1) {
            layer.msg("有且只能选择1个设备");
            return;
        }
        var rackId = rows[0].RACKID;
        var uheight = rows[0].UHEIGHT;
        var uinstall = rows[0].UINSTALL;
        if (rackId == null || rackId == 0 || uheight == null || uheight == 0 || uinstall == null || uinstall == 0) {
            layer.msg("设备数据不完整，不能上架");
            return;
        }
        layer.confirm('快速上下架设备', {btn: ['确定', '取消']}, function (index) {
            $.post(contextPath + "/device/upOrDwonRack.do", {
                deviceid: rows[0].DEVICEID,
                flag: 'up',
                rackId: rackId,
                uheight: uheight,
                uinstall: uinstall
            }, function (result) {
                alert(result.msg);
            });
            layer.close(index);
        });
    });
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
        var searchStr = "";
        if (typeof CurrNode!='undefined' && CurrNode!= null) {
            searchStr = CurrNode.id;
        }
        window.open(contextPath + '/device/exportDeviceData?searchStr='+searchStr);
    });
    function getOptions() {
        var option = {
            frozenColumns: [[
                {field: 'DEVICEID',hidden:true, width: 100},
                {field: 'NAME', title: '设备名称', width: 270}
                // {field: 'deviceclass', title: '设备类别',width: 70,formatter:function(value,row,index){
                //     if(value == '1'){
                //         return '网络';
                //     }else if(value == '2'){
                //         return '主机';
                //     }else if(value == '3'){
                //         return 'MCB';
                //     }
                // }}
            ]],
            columns: [[
                //{field: 'businesstypeId', title: '设备类型', width: 100},
                {field: 'RNAME', title: '归属机架', width: 250,formatter:function(value,row,index){
                    if(value != null && value != ''){
                        return '<a href="javascript:void(0)" onclick="getRackDealis('+row.RACKID+')">' + value + '</a>';
                    }else{
                        return '';
                    }
                }},
                {field: 'IPADDRESS', title: 'IP', width: 120},
                {field: 'VENDOR', title: '厂商', width: 100,formatter:function(value,row,index){
                    if(value == 0){
                        return '华为';
                    }else if(value == 1){
                        return '思科';
                    }else if(value == 2){
                        return '阿尔卡特';
                    }else if(value == 3){
                        return '3Com';
                    }else if(value == 4){
                        return 'HP';
                    }else if(value == 5){
                        return 'Linux';
                    }else if(value == 6){
                        return 'Microsoft';
                    }else if(value == 17){
                        return 'D-Link';
                    }else if(value == 54){
                        return 'Juniper';
                    } else if (value == 61) {
                        return 'H3C';
                    }else{
                        return '';
                    }
                }},
                {field: 'MODEL', title: '型号', width: 100},
                {field: 'OWNERTYPE', title: '产权性质', width: 100,formatter:function(value,row,index){
                    if(value == 1){
                        return '自建';
                    }else if(value == 2){
                        return '租用';
                    }else if(value == 3){
                        return '客户';
                    }else if(value == 4){
                        return '自有业务';
                    }else{
                        return '';
                    }
                }},
                // {field: 'POWER', title: '额定功耗', width: 100},
                // {field: 'PWRPOWERTYPE', title: '电源类别', width: 100},
                {field: 'STATUS', title: '设备状态', width: 100,formatter:function(value,row,index){
                    if(value == 20){
                        return '可用';
                    }else if(value == 40){
                        return '空闲';
                    }else if(value == 50){
                        return '预占';
                    }else if(value == 55){
                        return '已停机';
                    }else if(value == 60){
                        return '在服';
                    }else if(value == 110){
                        return '不可用';
                    }else{
                        return '';
                    }
                }},
                {field: 'UINSTALL', title: '安装位置', width: 100},
                {field: 'UHEIGHT', title: '设备高度', width: 100},
                // {field: 'OWNER', title: '联系人', width: 100},
                // {field: 'PHONE', title: '联系人电话', width: 100},
                // {field: 'UPLINEDATE', title: '上架日期', width: 140,formatter:fmtDateAction},
                // {field: 'TICKETID', title: '工单编号', width: 100},
                {field: 'INSURANCEDATE', title: '出保时间', width: 140, formatter: fmtDateAction},
                {field: 'DEVICEVERSION', title: '设备版本号', width: 100},
                {field: 'DESCRIPTION', title: '设备描述', width: 100}
            ]],
            type: 'device',
            title: '设备信息',
            toolbar: '#toolbar',
            pageSize:20,
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            onClickRow: fun,
            url: contextPath + "/device/list.do?deviceclass=1",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/device/deviceDetails.do?id=" + row.DEVICEID + "&buttonType=view&deviceclass=1";
                openDialogView('设备信息', url,'800px','530px');
            },
            rtree: {
                r_type: 3,
                isShowRack:'N',
                onClick: function (iteam, treeId, treeNode) {
                    if (treeNode.id.indexOf("location_") > -1||treeNode.id.indexOf("building_") > -1||treeNode.id.indexOf("idcroom_") > -1||treeNode.id.indexOf("idcrack_") > -1) {
                        CurrNode = treeNode;
                        searchModels();
                    }
                }
            }
        }
        return option;
    }
});
//查看机架信息
function getRackDealis(rackId){
    var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + rackId + "&businesstype=other&buttonType=view";
    openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
}
//查询
function searchModels() {
    var name = $("#buiName").val();
    var netType = $("#netType").val();
    var searchType="",searchId="";
    if (typeof CurrNode!='undefined' && CurrNode!= null) {
        var id = CurrNode.id;
        var datas = id.split("_");
        searchType = datas[0];
        searchId = datas[1];
    }
    $('#dg').datagrid({
        //url:contextPath + "/device/list.do?name="+name+"&deviceclass=1&roomId="+roomId+"&rackId="+rackId,
        url:contextPath + "/device/list.do",
        queryParams: {name: name, deviceclass: 1,netType: netType,searchType:searchType,searchId:searchId}
    });
}
//导入
function importFileClick(){
    // $.ajaxFileUpload({
    //     type: "POST",
    //     url: contextPath + "/device/importDeviceData",
    //     data:{},//要传到后台的参数，没有可以不写
    //     secureuri : false,//是否启用安全提交，默认为false
    //     fileElementId:'uploadFileid',//文件选择框的id属性
    //     dataType: 'json',//服务器返回的格式
    //     async : false,
    //     success: function(data){
    //         console.log(2);
    //         console.log(data);
    //         if(data.state){
    //             alert(data.msg);
    //             closeDialog();
    //             top.window['frmright'].window.$('#dg').datagrid("reload");
    //         }
    //     },
    //     error: function (data, status, e){
    //         console.log(1);
    //         console.log(data);
    //     }
    // });
    $('#fm_pic').form('submit',{
        url: contextPath + "/device/importDeviceData",
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
function refreshTable() {
    $("#dg").datagrid('reload');
}
