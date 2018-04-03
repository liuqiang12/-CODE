/**
 * Created by mylove on 2017/5/8.
 */
$(function () {
    var MyResource = $("#dg").MyResource(
        getOptions()
    );
    //top.hideLeft();
    function getOptions() {
        var option = {
            frozenColumns: [[
                { field: 'ID',hidden:true,width:30},
                {field: 'NAME', title: '机架名称', width: 300},
                {field: 'SITENAME', title: '所属机房', width: 300,formatter:function (value,row,index){
                    if(value != null && value !='') {
                        return '<a href="javascript:void(0)" onclick="showRoom(' + row.BID + ')">' + value + '</a>';
                    }else{
                        return '';
                    }
                }},
            ]],
            columns: [[
                {field: 'MNAME', title: '机架型号', width: 200,formatter:function(value,row,index){
                    if(value != null && value !=''){
                        //return '<a href="javascript:void(0)" onclick="showRackModel('+row.RACKMODELID+')">' + value + '</a>';
                        return '';
                    }else{
                        return '';
                    }
                }},
                {field: 'MCODE', title: '机架尺寸', width: 200},
                {field: 'DIRECTION', title: '机架方向', width: 100,formatter:function (value,row,index){
                    if(value==1){
                        return 'Right';
                    }else if(value==2){
                        return 'Up';
                    }else if(value==3){
                        return 'Down';
                    }else if(value==4){
                        return 'left';
                    }else{
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
            fitColumns:true,
            onClickRow: fun,
            url: contextPath + "/idcRack/pdfList.do?businesstype=equipment&statu=20&roomId=${roomId}",
            onDblClickRow: function (index, row) {
                var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + row.ID + "&businesstype=other&buttonType=view";
                openDialogShowView2d('机架信息', url, '800px', '530px', '查看机架视图');
            },
            rtree: {
                r_type: 2,
                onClick: function (iteam, treeId, treeNode) {
                    if (treeNode.id.indexOf("location_") > -1) {
                        CurrNode = null;
                        searchModels();
                    }
                    if (treeNode.id.indexOf("idcroom_") > -1) {
                        CurrNode = treeNode;
                        searchModels();
                    }
                }
            },
        }
        return option;
    }
});
function searchModels() {
    var name = $("#buiName").val();
    var roomId = '';
    if (typeof CurrNode!='undefined' && CurrNode!= null) {
        var id = CurrNode.id;
        var datas = id.split("_");
        roomId = datas[1];
    }
    $('#dg').datagrid({
        url: contextPath + "/idcRack/pdfList.do",
        queryParams: {name: name, businesstype: equipment, statu: 20, roomId: roomId}
    });
}
function  showRoom(id) {
    var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + id + "&buttonType=view";
    openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
}




