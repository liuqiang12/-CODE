/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //top.hideLeft();//隐藏菜单
    //新增
    $("#add").click(function () {
        openDialog('编辑机架信息', contextPath + '/idcRack/add/equipment/0','800px','530px');
    });
    //修改
    $("#edit").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        if (rows.length < 1) {
            layer.msg("请选择要修改的机架");
            return;
        } else if (rows.length > 1) {
            layer.msg("只能选择一个机架");
            return;
        }
        var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + rows[0].ID + "&businesstype=other&buttonType=update";
        openDialog('编辑机架信息', url, '800px', '530px');
        //openDialog('编辑机架信息', contextPath + '/idcRack/edit/equipment/'+row.ID,'800px','530px');
    });
    //查看机架视图
    $("#viewrack2d").click(function () {
        var row = $('#dg').datagrid('getSelected');
        if(row==null){
            layer.msg('请选择一条数据');
            return ;
        }
        if (row.BUSINESSTYPE_ID == 'df' && row.DFTYPE != 'wiring') {
            layer.msg('配线柜没有没有视图信息');
            return;
        }
        window.open(contextPath+'/racklayout/'+row.ID);
    });
    /*上报所有的机房数据*/
    $("#isp_rack_upload").click(function(){
        top.layer.confirm('你确定要上报ISP吗？', {
            btn: ['确定','取消'] //按钮
        }, function(index, layero){
            /*ajax*/
            $.ajax({
                type:"POST",
                url:contextPath+"/idcISPController/ispResourceUpload.do",
                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                //在请求之前调用的函数
                beforeSend:function(){
                },
                //成功返回之后调用的函数
                success:function(data){
                    //提示删除成功
                    //提示框
                    top.layer.msg(data.msg, {
                        icon: 1,
                        time: 3000 //（默认是3秒）
                    });
                },
                //调用执行后调用的函数
                complete: function(XMLHttpRequest, textStatus){
                },
                //调用出错执行的函数
                error: function(){
                    //请求出错处理
                }
            });
        }, function(index, layero){
            top.layer.close(index)
        });
    });
    $("#isp_rack_delete").click(function(){
        top.layer.confirm('你确定要上报删除机房数据吗？', {
            btn: ['确定','取消'] //按钮
        }, function(index, layero){
            /*ajax*/
            $.ajax({
                type:"POST",
                url:contextPath+"/idcISPController/ispResourceDelete.do",
                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                //在请求之前调用的函数
                beforeSend:function(){
                },
                //成功返回之后调用的函数
                success:function(data){
                    //提示删除成功
                    //提示框
                    top.layer.msg(data.msg, {
                        icon: 1,
                        time: 3000 //（默认是3秒）
                    });
                },
                //调用执行后调用的函数
                complete: function(XMLHttpRequest, textStatus){
                },
                //调用出错执行的函数
                error: function(){
                    //请求出错处理
                }
            });
        }, function(index, layero){
            top.layer.close(index)
        });
    });


    //导出
    $("#exportData").click(function () {
        var searchStr = "";
        if (typeof CurrNode!='undefined' && CurrNode!= null) {
            searchStr = CurrNode.id;
        }
        window.open(contextPath + '/idcRack/exportIdcRackData?searchStr='+searchStr);
    });
    //导入
    $("#importData").click(function(){
        hideJdt();
        $('#dlg_pic').dialog({
            title: '导入',
            closed: false,
        });
    });
    //建立链路
    $("#bingConnecter").click(function(){
        var rows = $('#dg').datagrid('getChecked');
        if (rows.length < 1) {
            layer.msg("请选择要建立链路机架");
            return;
        }
        var roomId = rows[0].BID;
        var rackIds = [];
        for(var i=0;i<rows.length;i++){
            if(rows[i].BUSINESSTYPE_ID == 'df' && rows[i].DFTYPE == 'wiring'){
                alert("配线柜："+rows[i].NAME+"不能建立链路信息");
                return;
            }else if(rows[i].BID!=roomId){
                alert("只能选择同一机房机架建立链路信息");
                return;
            }else{
                rackIds.push(rows[i].ID);
            }
        }
        var url = contextPath + "/idclink/rack";
        var data = {roomId:roomId,ids:rackIds.join('-')};
        top.layer.open({
            type: 2,
            id:"linkrack",
            area: ["90%", "90%"],
            title: "跳纤机架",
            maxmin: true, //开启最大化最小化按钮
            content: 'blankpage',
            scrollbar: false,
            btn: ['确定', '取消'],
            success: function (layero, index) {
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
                var chidlwin = layero.find('iframe')[0].contentWindow;
                if (chidlwin.location.href.indexOf('blankpage') > -1) {
                    var html = buildFormByParam(url, data);
                    chidlwin.document.body.innerHTML = "";//清空iframe内容，达到重新请求
                    chidlwin.document.write(html);
                    chidlwin.document.getElementById('postData_form').submit();
                }
            },
            btn1: function (index, layero) {
                var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                iframeWin.contentWindow.doSubmit();
                return false;
            },
            btn2:function(index, layero){
                var iframeWin = layero.find('iframe')[0];
                iframeWin.contentWindow.cancelWin();
            },
            cancel: function (index, layero) {
                var iframeWin = layero.find('iframe')[0];
                iframeWin.contentWindow.cancelWin();
            }
        })
    });
    //删除
    $("#del").click(function () {
        var row = $('#dg').datagrid('getSelections');
        var list=[];
        for(var i = 0;i<row.length;i++){
            list[i] = row[i].ID;
        }
        if(row.length<1){
            alert("请选择要删除的内容");
            return;
        }else{
            layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
                $.post(contextPath + '/idcRack/del', {list: list.toString()}, function (result) {
                    alert(result.msg);
                    $('#dg').datagrid({
                        url: contextPath + "/idcRack/list.do",
                    });
               });
                layer.close(index);
            });
        }
    });
    function getOptions() {
        var option = {
            frozenColumns: [[
                {field: 'ID',hidden:true,width:30},
                {field: 'NAME', title: '机架名称', width: 200},
                {field: 'SITENAME', title: '所属机房', width: 200,formatter:function (value,row,index){
                    if(value!=null&&value!=''){
                        return '<a href="javascript:void(0)" onclick="showRoom('+row.BID+')">' + value + '</a>';
                    }else{
                        return '';
                    }
                }},
                // {field: 'ROOMAREAID', title: '所属VIP机房', width: 100},
            ]],
            columns: [[
                {field: 'BUSINESSTYPE_ID', title: '机架类型', width: 100,formatter:function (value,row,index){
                        if(value=='equipment'){
                            return '客户机柜';
                        }else if(value=='df'&&row.DFTYPE=='ddf'){
                            return 'DDF架';
                        }else if(value=='df'&&row.DFTYPE=='odf'){
                            return 'ODF架';
                        }else if(value=='cabinet'){
                            return '网络柜';
                        }else if(value=='df'&&row.DFTYPE=='wiring'){
                            return '配线柜';
                        }else{
                            return '';
                        }
                }},
                {field: 'MNAME', title: '机架型号', width: 150
                    // formatter: function (value, row, index) {
                    //     if (value != null && value != '') {
                    //         return '<a href="javascript:void(0)" onclick="showRackModel(' + row.RACKMODELID + ')">' + value + '</a>';
                    //     } else {
                    //         return '';
                    //     }
                    // }
                },
                {field: 'MCODE', title: '机架尺寸', width: 150},
                // {field: 'DIRECTION', title: '机架方向', width: 100,formatter:function (value,row,index){
                //     if(value==1){
                //         return 'Right';
                //     }else if(value==2){
                //         return 'Up';
                //     }else if(value==3){
                //         return 'Down';
                //     }else if(value==4){
                //         return 'left';
                //     }else{
                //         return '';
                //     }
                // }},
                // {field: 'USEFOR', title: '用途', width: 100,formatter:function (value,row,index){
                //     if(value==1){
                //         return '自用';
                //     }else if(value==2){
                //         return '客用';
                //     }else{
                //         return '';
                //     }
                // }},
                // {
                //     field: 'RENTTYPE', title: '出租类型', width: 100, formatter: function (value, row, index) {
                //     if (value == 0) {
                //         return '整架出租';
                //     } else if (value == 1) {
                //         return '按机位出租';
                //     }else{
                //         return '';
                //     }
                // }},
                // {field: 'ARRANGEMENT', title: '机位顺序', width: 100,formatter:function (value,row,index){
                //     if(value==1){
                //         return '从下至上';
                //     }else if(value==2){
                //         return '至上而下';
                //     }else{
                //         return '';
                //     }
                // }},
                {field: 'STATUS', title: '业务状态', width: 100,formatter:function (value,row,index){
                    if(value == 20){
                        return '可用';
                    }else if(value == 30){
                        return '预留';
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
                {field: 'PDU_POWERTYPE', title: '电源类型', width: 100},
                {field: 'RATEDELECTRICENERGY', title: '额定电量(KWH)', width: 100},
                // {field: 'ELECTRICTYPE', title: '用电类型', width: 100},
                // {field: 'ELECTRICENERGY', title: '电量(KWH)', width: 100},
                // {field: 'APPARENETPOWER', title: '电量(KVA)', width: 100},
                // {field: 'ISRACKINSTALLED', title: '机架是否已安装', width: 100,formatter:function(value,row,index){
                //         if(value==0){
                //             return '未安装'
                //         }else if(value==1){
                //             return '已安装'
                //         }else return ''
                // }},
                // {field: 'HEIGHT', title: '机架U数', width: 100},
                {field: 'ELECTRICNUM', title: '昨日用电量(KWH)', width: 100},
                {
                    field: 'ACTUALCUSTOMER', title: '客户名称', width: 200, formatter: function (value, row, index) {
                    if (value != null && value != '') {
                        return '<a href="javascript:void(0)" onclick="showCustomerInfo(\'' + row.ACTUALCUSTOMERID + '\',\'' + value + '\')">' + value + '</a>';
                    } else {
                        return '';
                    }
                }}
            ]],
            type: 'idcrack',
            title: '机架信息',
            toolbar: '#toolbar',
            singleSelect: true,
            pageSize:20,
            selectOnCheck: true,
            checkOnSelect: true,
            onClickRow: fun,
            url: contextPath + "/idcRack/list.do",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + row.ID + "&businesstype=other&buttonType=view";
                if (row.BUSINESSTYPE_ID == 'df' && row.DFTYPE != 'wiring') {
                    openDialogView('机架信息', url, '800px', '530px');
                } else {
                    openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
                }
            },
            rtree: {
                r_type: 2,
                onClick: function (iteam, treeId, treeNode) {
                    if (treeNode.id.indexOf("idcroom_") > -1 || treeNode.id.indexOf("building_") > -1 || treeNode.id.indexOf("location_") > -1) {
                        CurrNode = treeNode;
                        searchModels();
                    }
                }
            },
        };
        return option;
    }
});
function searchModels() {
    var name = $("#buiName").val();
    var rackType = $('#rackType').combobox('getValue');
    var queryParams = {SearchKey: name,businesstypeId:rackType};
    if (typeof CurrNode!='undefined' && CurrNode!= null) {
        var id = CurrNode.id;
        var datas = id.split("_");
        queryParams.SearchType = datas[0];
        queryParams.SearchId = datas[1];
    }
    $('#dg').datagrid({
        url:contextPath + "/idcRack/list.do",
        queryParams: queryParams
    });
}
// //查看机架型号信息
// function showRackModel(value) {
//     alert("查看机架信息");
// }
//查看机房信息
function  showRoom(id) {
    var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + id + "&buttonType=view";
    openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
}
//查看客户信息
function showCustomerInfo(customerId, customerName) {
    var url = contextPath + "/customerController/linkQueryWin.do?viewQuery=1&id=" + customerId;
    openDialogView(customerName, url, '784px', '600px');
}
//导入文件
function importFileClick (){
    $('#fm_pic').form('submit', {
        url: contextPath + "/idcRack/importIdcRackData?type=other",
        onSubmit: function () {
            return $("#fm_pic").form('validate');
        },
        success: function (data) {
            var obj = eval('(' + data + ')');
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



