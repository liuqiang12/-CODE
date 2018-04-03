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
        openDialog('编辑信息',contextPath + '/resource/pdf/0','800px','400px');
    })
    //修改
    $("#edit").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择PDF");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个PDF");
            return;
        }
        var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + rows[0].ID + "&businesstype=pdu&buttonType=update";
        openDialog('编辑设备信息', url,'800px','400px');

    })
    //查看PDF架视图
    $("#viwePdf2d").click(function () {
        var row = $('#dg').datagrid('getSelected');
        if(row==null){
            layer.msg('请选择一条数据')
            return ;
        }
        //window.open(contextPath+'/idcRack/viewpdf/'+row.ID);
        openDialogView('机架视图', contextPath+'/idcRack/viewpdf/'+row.ID,'900px','530px');
    })
    //导出
    $("#exportData").click(function () {
        var searchStr = "";
        if (typeof CurrNode!='undefined' && CurrNode!= null) {
            searchStr = CurrNode.id;
        }
        window.open(contextPath + '/idcRack/exportIdcRackData?businesstype=pdu&searchStr='+searchStr);
    });
    //导入
    $("#importData").click(function(){
        hideJdt();
        $('#dlg_pic').dialog({
            title: '导入',
            closed: false,
        });
    })
    //删除
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
            layer.confirm('是否确认删除', {btn: ['确定', '取消']}, function (index) {
                $.post(contextPath + '/idcRack/del', {list: list.toString()}, function (result) {
                    alert(result.msg);
                    $('#dg').datagrid({
                        url: contextPath + "/idcRack/pdfList.do?businesstype=pdu",
                    });
                });
                layer.close(index);
            });
        }
    })
    //mcb列表
    $("#mcbList").click(function(){
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择PDF架");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个PDF架");
            return;
        }
        var  url = contextPath+"/idcmcb/getMcbList.do?rackId="+rows[0].ID;
        top.layer.open({
            type: 2,
            title:'MCB列表',
            area: ['800px', '530px'],
            fixed: false, //不固定
            maxmin: true,
            content: url,
            btn:['关闭']
        })
    })
    function getOptions() {
        var option = {
            frozenColumns: [[
                { field: 'ID',hidden:true,width:30},
                {field: 'NAME', title: '机架名称', width: 300},
                {field: 'SITENAME', title: '所属机房', width: 300,formatter:function (value,row,index){
                    if(value!=null && value != ''){
                        return '<a href="javascript:void(0)" onclick="showRoom('+row.BID+')">' + value + '</a>';
                    }else{
                        return '';
                    }
                }}
            ]],
            columns: [[
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
                {field: 'PDU_POWERTYPE', title: '电源类型', width: 150},
                {field: 'PDU_LOCATION', title: '所在位置', width: 300}
                // {field: 'MNAME', title: '机架型号', width: 200}
            ]],
            type: 'idcrack',
            title: '机架信息',
            toolbar: '#toolbar',
            singleSelect: true,
            pageSize:20,
            selectOnCheck: true,
            checkOnSelect: true,
            fitColumns:true,
            onClickRow: fun,
            url: contextPath + "/idcRack/pdfList.do?businesstype=pdu",
            onDblClickRow: function (index, row) {
                openDialogShowView2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + row.ID + '&businesstype=pdu&buttonType=view', '800px', '400px', '查看机架视图');
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
        }
        return option;
    }
});
//查询
function searchModels() {
    var name = $("#buiName").val();
    var queryParams = {name: name, businesstype: 'pdu'};
    if (typeof CurrNode!='undefined' && CurrNode!= null) {
        var id = CurrNode.id;
        var datas = id.split("_");
        queryParams.searchType = datas[0];
        queryParams.searchId = datas[1];
    }
    $('#dg').datagrid({
        url: contextPath + "/idcRack/pdfList.do",
        queryParams: queryParams
    });
}
//查看机房信息
function  showRoom(id) {
    var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + id + "&buttonType=view";
    openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
}
//导入文件
function importFileClick (){
    $('#fm_pic').form('submit',{
        url: contextPath + "/idcRack/importIdcRackData?type=pdu",
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



