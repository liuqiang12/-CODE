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
    return "/modelController/processDefinitionQueryGridData.do";
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
    headCols.push({field:"id",title:"部署ID",halign:'left'});
    headCols.push({field:"name",title:"部署名称",halign:'left'});
    headCols.push({field:"key",title:"关键字",halign:'left'});
    headCols.push({field:"category",title:"类型",halign:'left'});
    headCols.push({field:"description",title:"描述",halign:'left',width:150});
    headCols.push({field:"resourceName",title:"资源名称",halign:'left'});
    headCols.push({field:"diagramResourceName",title:"图形名称",halign:'left'});
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
//开始方法
function singleStartRow(gridId){
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
    var key = singleRecordData[0]['key'];
    top.layer.confirm('你确定要启动['+singleRecordData[0]['name']+']流程吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/modelController/startProcessByKey/"+key,
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
                var obj = jQuery.parseJSON(data);
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
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
function singleViewRow(gridId){
    //弹出框显示图片信息
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
        title: '<span style="color:blue">'+singleRecordData[0]['key']+'流程</span>'+"》图片",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['1324px', '700px'],
        //45001
        content: contextPath+"/modelController/processDefinitionImage/"+singleRecordData[0]['id'],
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
//删除方法
function singleDeleteRow(){
    //修改弹出框 进行发布
    alert("ajax   删除");
}