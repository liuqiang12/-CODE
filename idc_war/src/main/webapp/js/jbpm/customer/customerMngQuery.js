/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
});
/**
 * 客户信息上报
 */
function ispUploadCustomer(){
    top.layer.confirm('你确定要上报客户数据吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            type:"POST",
            url:contextPath+"/idcISPController/ispCustomerUpload.do",
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
}
/**
 * 客户信息 删除
 */
function ispDeleteCustomer(){
    top.layer.confirm('你确定要上报删除用户数据吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            type:"POST",
            url:contextPath+"/idcISPController/ispCustomerDelete.do",
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
}

function loadGrid(gridId){
    //创建表信息
    var url = getRequestUrl();
    var params = getRequestParams();
    var columns = createColumns();
    $("#"+gridId).datagrid({
        url : contextPath + url,
        queryParams: params,
        columns : columns,
        onLoadSuccess:myLoadsuccess,
        toolbar:"#requestParamSettins"
    })
}
//初始化按钮事件
function myLoadsuccess(data){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
}
function getRequestUrl(){
    return "/customerController/customerMngQueryData.do";
}

// 获取查询条件
function getRequestParams(){
    var name = $("#name").textbox("getValue");
    var customerlevel = $("#customerlevel").combobox("getValue");
    var tracestate = $("#tracestate").combobox("getValue");
    var sla = $("#sla").combobox("getValue");
    var category = $("#category").combobox("getValue");

    /*参数组装*/
    var params = {};
    params['name'] = name;
    params['customerlevel'] = customerlevel;
    params['tracestate'] = tracestate;
    params['category'] = category;
    params['sla'] = sla;
    return params;
}
//获取列信息
function createColumns(){
    var headTr1Cols = [];
    //修改单元格字体:颜色
    /*headTr1Cols.push({field:"ck",checkbox:true,halign:'left'});*/
    //第二个
    headTr1Cols.push({field:'name',title:'客户名称',halign:'center',width:200,formatter:fmtLinkAction});
    headTr1Cols.push({field:'subordinateManagerName',title:'创建人',halign:'center',width:80});
    /*headTr1Cols.push({field:'loginUserRegion',title:'创建人区域',halign:'center',width:70});*/
    headTr1Cols.push({field:'attribute',title:'属性',halign:'center',width:70,formatter:fmtAttributeAction});
    headTr1Cols.push({field:'idcardtype',title:'证件类型',halign:'center',width:100,formatter:fmtCardtypeAction});
    headTr1Cols.push({field:'idcardno',title:'证件号',width:170,halign:'center'});
    headTr1Cols.push({field:'zipcode',title:'邮政编码',halign:'center'});
    headTr1Cols.push({field:'addr',title:'单位地址',width:200,halign:'center'});
    headTr1Cols.push({field:'createTime',title:'注册时间',width:150,halign:'center',formatter:fmtDateAction});
    // 客户级别
    headTr1Cols.push({field:'customerlevel',title:'客户级别',halign:'center',width:100,formatter:fmtCustomerlevelAction});
    headTr1Cols.push({field:'tracestate',title:'跟踪状态',halign:'center',formatter:fmtTracestateAction});
    headTr1Cols.push({field:'sla',title:'服务等级',halign:'center',formatter:fmtSlaAction});
    headTr1Cols.push({field:'category',title:'客户类别',halign:'center',formatter:fmtCategoryAction});
    // 用户联系人
    headTr1Cols.push({field:'contactname',title:'联系人名称',halign:'center',formatter:columnCSS});
    headTr1Cols.push({field:'contactIdcardtype',title:'证件类型',halign:'center',formatter:columnCSS});
    headTr1Cols.push({field:'contactIdcardno',title:'证件号码',halign:'center',formatter:columnCSS});
    headTr1Cols.push({field:'contactPhone',title:'固定号码',halign:'center',formatter:columnCSS});
    headTr1Cols.push({field:'contactMobile',title:'移动号码',halign:'center',formatter:columnCSS});
    headTr1Cols.push({field:'contactEmail',title:'邮箱',halign:'center',formatter:columnCSS});
    return [headTr1Cols];
}
function fmtAttributeAction(value,row,index){
    //分类说明
    var label = value == '1'?"军队":
                    value == '2'?"政府机关":
                        value == '3'?"事业单位":
                            value == '4'?"企业":
                                value == '5'?"个人":
                                    value == '6'?"社会团体":
                                        value == '999'?"其他":
                                            null;
    return label;
}
function fmtLinkAction(value,row,index){
    return '<a href="javascript:void(0);" onclick="linkQueryWin(\''+row.id+'\',\''+row.name+'\')">'+value+'</a>';
}
//查看详情界面
function linkQueryWin(id,name){
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">'+name+'</span>'+"》详情",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['784px', '600px'],
        content: contextPath+"/customerController/linkQueryWin.do?viewQuery=1&id="+id,
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        }
    });
}
//删除
function singleDeleteRow(gridId){
    //至少选中一行
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    top.layer.confirm('你确定要删除['+singleRecordData[0]['name']+']客户吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/customerController/singleDelete/"+singleRecordData[0]['id'],
            //提交的数据
            data:{
                name: singleRecordData[0]['name']
            },
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
            },
            //成功返回之后调用的函数
            success:function(data){
                //提示删除成功
                top.layer.msg(data.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                $("#"+gridId).datagrid("reload");
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
}
function columnCSS(value,row,index){
    var css = "#036513";
    return '<span style="color:'+css+'">'+value+'</span>';//改变表格中内容字体的大小
}
//证件名称
function fmtCardtypeAction(value,row,index){
    //分类说明
    var label = value == '1'?"工商营业执照":
        value == '2'?"身份证":
            value == '3'?"组织机构代码证书":
                value == '4'?"事业法人证书":
                    value == '5'?"军队代号":
                        value == '6'?"社团法人证书":
                            value == '7'?"护照":
                                value == '8'?"军官证":
                                    value == '9'?"台胞证":
                                        value == '999'?"其他":
                                                null;
    return label;
}
//客户级别
function fmtCustomerlevelAction(value,row,index){
    var label = value == '10'?"A类个人客户":
                    value == '20'?"B类个人客户":
                        value == '30'?"C类个人客户":
                            value == '40'?"A类集团":
                                value == '50'?"A1类集团":
                                    value == '60'?"A2类集团":
                                        value == '70'?"B类集团":
                                            value == '80'?"B1类集团":
                                                value == '90'?"B2类集团":
                                                    value == '100'?"C类集团":
                                                        value == '110'?"D类集团":
                                                            value == '120'?"Z类集团":
                                                                value == '999'?"其他":
                                                                        null;
    return label;
}
//跟踪状态
function fmtTracestateAction(value,row,index){
    //分类说明
    var label = value == '10'?"跟踪状态":
                    value == '20'?"有意向":
                        value == '30'?"继续跟踪":
                            value == '40'?"无意向":
                                null;
    return label;
}
// 服务级别
function fmtSlaAction(value,row,index){
    //分类说明
    var label = value == '10'?"金牌":
        value == '20'?"银牌":
            value == '30'?"铜牌":
                value == '40'?"铁牌":
                    value == '999'?"标准":
                    null;
    return label;
}
// 客户类别
function fmtCategoryAction(value,row,index){
    //分类说明
    var label = value == '10'?"局方":
        value == '20'?"第三方":
            value == '30'?"测试":
                value == '40'?"退场客户":
                    value == '50'?"国有":
                        value == '60'?"集体":
                            value == '70'?"私营":
                                value == '80'?"股份":
                                    value == '90'?"外商投资":
                                        value == '100'?"港澳台投资":
                                            value == '110'?"客户":
                                                value == '120'?"自由合作":
                                                    value == '130'?"内容引入":
                                                        value == '999'?"其他":
                                                        null;
    return label;
}

//获取被选中的节点信息
function gridSelectedRecord(gridId){
    //获取被选中的节点信息
    console.log(gridId);
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    return singleRecordData[0];
}
/* 操作界面信息 */
//新增相应的模型
function singleCreateOrUpdate(gridId){
    /**
     * 添加
     */
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">客户管理</span>'+"》新增",
        shadeClose: false,
        id:'updateCustomerMSG',
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['784px', '600px'],
        content: contextPath+"/customerController/singleCreateOrUpdate.do",
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            chidlwin.form_sbmt("gridId");
            //刷新列表信息
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });
}
function singleUpdateRow(gridId){
    //至少选中一行
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">客户管理</span>'+"》修改",
        shadeClose: false,
        id:"updateCustomerMSG",
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['784px', '600px'],
        content: contextPath+"/customerController/singleCreateOrUpdate.do?id="+singleRecordData[0]['id'],
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            chidlwin.form_sbmt("gridId");
            //刷新列表信息
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });
}