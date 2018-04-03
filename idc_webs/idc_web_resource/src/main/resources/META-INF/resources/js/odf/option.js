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
        openDialog('编辑信息',contextPath + '/resource/odf/0','800px','400px');
    })
    //修改
    $("#edit").click(function () {
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择ODF");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个ODF");
            return;
        }
        var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + rows[0].ID + "&businesstype=odf&buttonType=update";
        openDialog('编辑机架信息', url,'800px','400px');

    })
    //查看机架视图
    $("#viweOdf2d").click(function () {
        var row = $('#dg').datagrid('getSelected');
        if(row==null){
            layer.msg('请选择一条数据')
            return ;
        }
        openDialogView('机架视图', contextPath + '/idcRack/viewodf/' + row.ID, '1200px', '530px');
    })
    //导出
    $("#exportData").click(function () {
        var searchStr = "";
        if (typeof CurrNode!='undefined' && CurrNode!= null) {
            searchStr = CurrNode.id;
        }
        window.open(contextPath + '/idcRack/exportIdcRackData?businesstype=df&searchStr='+searchStr);
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
           layer.confirm('是否确认删除',{ btn : [ '确定', '取消' ]},function(index){
               $.post(contextPath +'/idcRack/del',{list:list.toString()},function(result){
                   alert(result.msg);
                   $('#dg').datagrid({
                       url:contextPath + "/idcRack/odfList.do?businesstype=df",
                   });
               });
               layer.close(index);
           });
        }
    })
    //端口列表
    $("#connectorList").click(function(){
        var rows = $('#dg').datagrid('getChecked');
        if (rows.length < 1) {
            layer.msg("没有选择ODF架");
            return;
        } else if (rows.length > 1) {
            layer.msg("只能选择一个ODF架");
            return;
        }
        var url = contextPath + "/idcConnector/getIdcConnectorList.do?rackId=" + rows[0].ID;
        top.layer.open({
            type: 2,
            title:'端子列表',
            area: ['800px', '530px'],
            fixed: false, //不固定
            maxmin: true,
            content: url,
            btn: ['关闭']
        })
    });

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
                {field: 'BUSINESSTYPE_ID', title: '机架类型', width: 150,formatter:function(value,row,index){
                    if(value == 'equipment'){
                        return '设备机架';
                    }else if(value == 'cabinet'){
                        return '网络头柜';
                    }else if(value == 'df'){
                        if(row.DFTYPE == 'odf'){
                            return 'ODF';
                        }else if(row.DFTYPE == 'ddf'){
                            return 'DDF';
                        }else{
                            return '配线柜';
                        }
                    }else if(value == 'pdu'){
                        return 'PDU';
                    }else{
                        return '';
                    }
                }},
                {field: 'STATUS', title: '业务状态', width: 250,formatter:function (value,row,index){
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
                // {field: 'MNAME', title: '机架型号', width: 150},
                // {field: 'MCODE', title: '机架尺寸', width: 150},
                {field: 'CONNECTORNUM', title: 'ODF端口数', width: 150}
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
            url: contextPath + "/idcRack/odfList.do?businesstype=df",
            onDblClickRow: function (index, row) {
                openDialogShowView2d('机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + row.ID + '&businesstype=odf&buttonType=view', '800px', '400px', '查看机架视图');
            },
            rtree: {
                r_type: 2,
                onClick: function (iteam, treeId, treeNode) {
                    if (treeNode.id.indexOf("idcroom_") > -1 || treeNode.id.indexOf("building_") > -1 || treeNode.id.indexOf("location_") > -1 ) {
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
    var queryParams = {name: name, businesstype: 'df'};
    if (typeof CurrNode!='undefined' && CurrNode!= null) {
        var id = CurrNode.id;
        var datas = id.split("_");
        queryParams.searchType = datas[0];
        queryParams.searchId = datas[1];
    }
    $('#dg').datagrid({
        url: contextPath + "/idcRack/odfList.do",
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
    $('#fm_pic').form('submit', {
        url: contextPath + "/idcRack/importIdcRackData?type=df",
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



