/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
});
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
    return "/modelController/modelQueryGridData.do";
}

// 获取查询条件
function getRequestParams(){
    var name = $("#name").textbox("getValue");
    /*参数组装*/
    var params = {};
    params['name'] = name;
    return params;
}
//获取列信息
function createColumns(){
    //创建列表信息
    var headCols = [];
    headCols.push({field:"ck",checkbox:true,halign:'left'});
    headCols.push({field:"id",title:"模型ID",halign:'left',width:100});
    headCols.push({field:"deploymentId",title:"部署ID",halign:'left',width:100});
    headCols.push({field:"name",title:"模型名称",halign:'left',width:100});
    headCols.push({field:"key",title:"关键字",halign:'left',width:100});
    headCols.push({field:"category",title:"模型类型",halign:'left',width:100});
    // headCols.push({field:"metaInfo",title:"描述",halign:'left',width:150});
    headCols.push({field:"version",title:"版本",halign:'left',width:100});
    headCols.push({field:"lastUpdateTime",title:"修改时间",halign:'left',width:100,formatter:fmtDateAction});
    headCols.push({field:"createTime",title:"创建时间",halign:'left',width:100,formatter:fmtDateAction});
    // headCols.push({field:"option",title:"操作",halign:'center',width:250,formatter:fmtAction});
    return [headCols]
}
function fmtAction(value,row,index){
    var insert= [];
    insert.push('<a class="easyui-linkbutton " title="修改" data-options="plain:true,iconCls:\'icon-edit\'" onclick="singleUpdateRow()">修改</a> ');
    insert.push('<a class="easyui-linkbutton " title="发布" data-options="plain:true,iconCls:\'icon-settting\'" onclick="singleDeployRow()">发布</a> ');
    insert.push('<a class="easyui-linkbutton " title="启动" data-options="plain:true,iconCls:\'icon-ok\'" onclick="singleStartRow()">启动</a> ');
    insert.push('<a class="easyui-linkbutton " title="删除" data-options="plain:true,iconCls:\'icon-cancel\'" onclick="singleDeleteRow()">删除</a> ');
    return insert.join('');
}
//详情方法
function singleQueryRow(gridId){
    //详情弹出框
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
        title: '<span style="color:blue">'+singleRecordData[0]['name']+':'+singleRecordData[0]['key']+':'+singleRecordData[0]['id']+'模型</span>'+"》详情",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['704px', '600px'],
        content: contextPath+"/modelController/singleQueryModel.do?modelId="+singleRecordData[0]['id'],
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });
}
function fmtDateAction(value,row,index){
    if (value != null) {
        var date = new Date(value);
        var year = date.getFullYear().toString();
        var month = (date.getMonth() + 1);
        var day = date.getDate().toString();
        var hour = date.getHours().toString();
        var minutes = date.getMinutes().toString();
        var seconds = date.getSeconds().toString();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
        //return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'+ date.getDate() +' '+date.getHours()+':'+date.getMinutes()+':'+date.getMilliseconds();
    }
}
//新增相应的模型
function singleCreateRow(){
    /**
     * 添加
     */
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">流程</span>'+"》新增",
        shadeClose: false,
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['504px', '500px'],
        content: contextPath+"/modelController/singleCreateModel.do",
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
//导出xml
function singleExportRow(gridId){
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    $("#frameId").attr("src",contextPath+"/modelController/export/modelId.do");
    //alert(contextPath+"/modelController/export/"+singleRecordData[0]['id']+"/ctr.do")
    //window.open(contextPath+"/modelController/export/"+singleRecordData[0]['id']+"/ctr.do");

}
//设计方法
function singleDesignRow(gridId){
    //修改弹出框 进行设计
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    console.log(singleRecordData[0]);
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">'+singleRecordData[0]['name']+':'+singleRecordData[0]['key']+':'+singleRecordData[0]['id']+'流程</span>'+"》设计",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['1324px', '700px'],
        content: contextPath+"/modelController/modeler.do?modelId="+singleRecordData[0]['id'],
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });
}
//发布方法
function singleDeployRow(gridId){
    //修改弹出框 进行发布
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    top.layer.confirm('你确定要发布['+singleRecordData[0]['name']+']流程吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/modelController/deploy/"+singleRecordData[0]['id']+"/ctr.do",
            //提交的数据
            //data:{
            //},
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){

            },
            //成功返回之后调用的函数
            success:function(data){
                //提示删除成功
                //提示框
                top.layer.alert(data.msg, {
                    icon: 1,
                    skin: 'layer-ext-moon' //3秒关闭（如果不配置，默认是3秒）
                });
                $("#gridId").datagrid("reload");
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
//开始方法
function singleStartRow(){
    //修改弹出框 进行发布
    $.ajax({
        //提交数据的类型 POST GET
        type:"POST",
        //提交的网址
        url:contextPath+"/modelController/test.do",
        //提交的数据
        //data:{
        //},
        //返回数据的格式
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        //在请求之前调用的函数
        beforeSend:function(){
        },
        //成功返回之后调用的函数
        success:function(data){
            //提示删除成功
            //提示框
        },
        //调用执行后调用的函数
        complete: function(XMLHttpRequest, textStatus){
        },
        //调用出错执行的函数
        error: function(){
            //请求出错处理
        }
    });


}
//删除方法
function singleDeleteRow(){
    //修改弹出框 进行发布
    alert("ajax   删除");
}