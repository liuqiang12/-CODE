$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
});

function loadGrid(gridId){
    //创建表信息
    $("#"+gridId).datagrid({
        url : contextPath + "/ticketJbpmReportController/ipResourceReportData.do",
        queryParams: {"busName":$("#busName").val()},
        /*onLoadSuccess:myLoadsuccess,*/
        columns:[[
            {field:'TICKET_INST_ID',title:'工单ID',rowspan:2,width:'5%' },
            {field:'BUSNAME',title:'业务名称',rowspan:2,width:'10%' },
            {title:'对应地址段',colspan:3},
            {field:'BANDWIDTH',title:'占用带宽',rowspan:2,width:'8%' },
            {field:'LINK_NAME',title:'使用链路条数',rowspan:2,width:'8%' },
            {field:'RACK_NAME',title:'所在机架',rowspan:2,width:'10%' },
            {field:'CONTACTNAME',title:'联系人',rowspan:2,width:'8%' },
            {field:'CONTACT_MOBILE',title:'电话',rowspan:2,width:'8%' },
            {field:'CONTACT_EMAIL',title:'邮箱',rowspan:2,width:'8%' },
            {field:'REMARK',title:'变动以及备注',rowspan:2,width:'8%' },
        ],[
            {field:'BEGIP',title:'起始IP',width:'9%',align:'right' },
            {field:'ENDIP',title:'结束IP',width:'9%',align:'right' },
            {field:'MASKSTR',title:'子网掩码',width:'9%',align:'right' }
        ]],
        toolbar:"#requestParamSettins_taskQuery",
        /*控制按钮配置*/
        onLoadSuccess:function(index,row){
            console.log(row);
        }
    })
}

