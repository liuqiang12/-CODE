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
    return "/modelController/queryTaskQueryData.do";
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
    headCols.push({field:"id",title:"任务ID",halign:'left'});
    headCols.push({field:"name",title:"任务名称",halign:'left'});
    headCols.push({field:"category",title:"任务类别",halign:'left'});
    headCols.push({field:"description",title:"描述",halign:'left',width:150});
    headCols.push({field:"createTime",title:"创建时间",halign:'center',width:250,formatter:fmtDateAction});
    return [headCols]
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
function singleViewRow(gridId){
    //弹出框显示图片信息
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
       /* top.layer.alert("至少选择一条记录", {
            skin: 'layui-layer-lan'
            ,closeBtn: 0
            ,anim: 4 //动画类型
        });
        return false;*/
    }
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">'+'sdsdsd'+'流程</span>'+"》图片",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['1324px', '700px'],
        //45001
        content: contextPath+"/modelController/getActivitiProccessImageByprocessInstanceId/45001",
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
//完成任务
function singleCompaleteRow(gridId){
    //直接查看历史流程图
    var laySum = top.layer.open({
        type: 2,
        title: "图片",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['1324px', '700px'],
        //45001
        url:contextPath+"/modelController/getActivitiProccessImageByprocessInstanceId/"+'45001',
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });




    /*var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /!*弹出提示框*!/
        top.layer.alert("至少选择一条记录", {
            skin: 'layui-layer-lan'
            ,closeBtn: 0
            ,anim: 4 //动画类型
        });
        return false;
    }
    top.layer.confirm('你确定要完成['+singleRecordData[0]['name']+']任务吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){

        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/modelController/completeByTaskId/"+singleRecordData[0]['id'],
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
                top.layer.alert(obj.msg, {
                    skin: 'layui-layer-lan'
                    ,closeBtn: 0
                    ,anim: 4 //动画类型
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
    });*/

}