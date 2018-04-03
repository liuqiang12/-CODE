$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
});

function loadGrid(gridId){
    //创建表信息
    $("#"+gridId).datagrid({
        url : contextPath + "/ticketJbpmReportController/recycleResourceReportData.do",
        queryParams: {"busName":$("#busName").val(),"customerName":$("#customerName").val()},
        fitColumns : false,  //允许水平滚动
        /*onLoadSuccess:myLoadsuccess,*/
        columns:[[
            {field:'a',title:'分配编号',rowspan:2,width:150 },
            {field:'b',title:'业务需求',rowspan:2,width:150 },
            {field:'BUSNAME',title:'业务名称',rowspan:2,width:150 },
            {field:'YWLX',title:'类别',rowspan:2,width:150 },
            {field:'d',title:'机房',rowspan:2,width:150 },
            {title:'机柜',colspan:2},
            {field:'e',title:'带宽',rowspan:2,width:150 },
            {title:'IP地址资源',colspan:4},
            {field:'g',title:'回收时间',rowspan:2,width:150 },
            {field:'CUSTOMER_PHONE',title:'客户联系方式（电话、邮箱等）',rowspan:2,width:150 },
            {field:'j',title:'备注',rowspan:2,width:150 },
        ],[
            {field:'k',title:'机柜号',width:150,align:'right' },
            {field:'l',title:'机柜数量',width:150,align:'right' },
            {field:'m',title:'具体IP地址',width:150,align:'right' },
            {field:'n',title:'IP数量',width:150,align:'right' },
            {field:'o',title:'互联地址',width:150,align:'right' },
            {field:'p',title:'互联地址数量',width:150,align:'right' }
        ]],
        toolbar:"#requestParamSettins_taskQuery",
        /*控制按钮配置*/
        onLoadSuccess:function(index,row){
            console.log(row);
        }
    })
}

