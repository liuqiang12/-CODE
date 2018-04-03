/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    $("#add").click(function () {
        MyResource.open("connector", 0);
    })

    $("#edit").click(function () {
        var row = $('#dg').datagrid('getSelected');
        if(row==null){
            alert("请选择要修改的内容");
        }else{
            MyResource.open("connector", row.ID);
        }
    })

    $("#exportData").click(function () {
        $.post(contextPath +'/idcConnector/export',{},function(result){
            alert(result.msg);
        });
    });
    $("#importData").click(function(){
        hideJdt();
        $('#dlg_pic').dialog({
            title: '导入',
            closed: false,
        });
    })

    $("#del").click(function () {
        var row = $('#dg').datagrid('getSelections');
        var list=new Array();
        for(var i = 0;i<row.length;i++){
            list[i] = row[i].ID;
        }
        if(row.length<1){
            alert("请选择要删除的内容");
            return;
        }else{
            layer.confirm('是否确认删除？', {btn: ['确定', '取消']}, function (index) {
                $.post(contextPath + '/idcConnector/del', {list: list.toString()}, function (result) {
                    alert(result.msg);
                    $('#dg').datagrid({
                        url: contextPath + "/idcConnector/list.do",
                    });
                });
                layer.close(index);
            });
        }
    })
    function getOptions() {
        var option = {
            frozenColumns: [[
                {field: 'ID', hidden: true, width: 30},
                {field: 'NAME', title: '名称', width: 100},
                {field: 'MEMO', title: '备注', width: 100},
                {field: 'RACK_ID', title: '所属机架', width: 200,formatter:function (value,row,index){
                    return '<a href="javascript:void(0)" onclick="showRack('+row.BID+')">' + value + '</a>';
                }},
                {field: 'CREATEDATE', title: '创建时间', width: 200,sortable: true,
                    formatter:function(value,row,index){
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }},
                {field: 'UPDATEDATE', title: '更新时间', width: 200,sortable: true,
                    formatter:function(value,row,index){
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }},
                {field: 'CONNECTORTYPE', title: '连接类型', width: 100,formatter:function (value,row,index){
                    if(value==1){
                        return 'RJ45';
                    }else if(value==2){
                        return 'FC';
                    }else if(value==3){
                        return 'LC';
                    }else if(value==4){
                        return 'SC';
                    }
                }},
                {field: 'TYPE', title: '端子类型', width: 100},
                {field: 'PORT_MODE', title: '光口模式', width: 100},
                {field: 'STATUS', title: '业务状态', width: 100,formatter:function (value,row,index){
                    if(value==20){
                        return '可用';
                    }else if(value==30){
                        return '预留';
                    }else if(value==50){
                        return '预占';
                    }else if(value==55){
                        return '已停机';
                    }else if(value==60){
                        return '在服';
                    }
                }},
                {field: 'TICKET_ID', title: '工单编号', width: 100},
            ]],
            type: 'connector',
            title: '端子信息',
            toolbar: '#toolbar',
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            onClickRow: fun,
            url: contextPath + "/idcConnector/list.do",
            onDblClickRow: function (index, row) {
                MyResource.open("connector", row.ID);
            }
        }
        return option;
    }

});
function searchModels() {
    var name = $("#buiName").val();
    var rackType = $('#rackType').combobox('getValue')
    $('#dg').datagrid({
        url: contextPath + "/idcRack/list.do",
        queryParams: {name: name, businesstypeId: rackType}
    });
}
function  showRack(id) {
    mylayer.open({
        type: 2,
        area: ['800px', '530px'],
        fixed: false, //不固定
        maxmin: true,
        content:  contextPath +'/resource/' + 'idcrack' + "/" + id
    })
}

//文件上传
//导入事件，显示导入弹出窗口
function importClick()
{
    $('#dlg_pic').dialog({
        title: '导入',
        closed: false,
    });

}

//导入文件
function importFileClick (){
    $.ajaxFileUpload({
        type: "POST",
        url: contextPath + "/idcRack/inport",
        data:{},//要传到后台的参数，没有可以不写
        secureuri : false,//是否启用安全提交，默认为false
        fileElementId:'uploadFileid',//文件选择框的id属性
        dataType: 'json',//服务器返回的格式
        async : false,
        success: function(data){
            if(data.state==true){
                alert(data.msg);
            }else{
                //coding
            }
        },
        error: function (data, status, e){

        }
    });
}



