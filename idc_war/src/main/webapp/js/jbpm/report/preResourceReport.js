$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
});

function loadGrid(gridId){
    //创建表信息
        $("#"+gridId).datagrid({
        url : contextPath + "/ticketJbpmReportController/preResourceReportData.do",
        queryParams: {"busName":$("#busName").val(),"customerName":$("#customerName").val()},
        /*onLoadSuccess:myLoadsuccess,*/
        fitColumns : false,  //允许水平滚动
        columns:[[
            {field:'CUSTOMER_NAME',title:'客户名称',rowspan:2,width:150 },
            {field:'RESOURCES_CATEGORY',title:'业务需求',rowspan:2,width:150,formatter: formatterResourcesCategory},
            {field:'BUSNAME',title:'业务名称',rowspan:2,width:150 },
            {field:'YWLX',title:'业务类型',rowspan:2,width:150 },
            {field:'SITENAME',title:'机房',rowspan:2,width:150 },
            {title:'机柜',colspan:2},
            {field:'BANDWIDTH',title:'带宽',rowspan:2,width:150,formatter: formatterBandwidth},
            {title:'IP地址资源',colspan:4},
            {field:'h',title:'分配时间',rowspan:2,width:150 },
            {field:'CREATE_TIME',title:'业务开通时间',rowspan:2,width:150 },
            {field:'YWZT',title:'业务状态',rowspan:2,width:150},
            {field:'CUSTOMER_PHONE',title:'客户联系方式（电话、邮箱等）',rowspan:2,width:150 },
            {field:'REMARK',title:'备注',rowspan:2,width:150 },
        ],[
            {field:'SITENAME',title:'机柜号',width:150,align:'right' },
            {field:'RACK_WM_CONCAT_COUNT',title:'机柜数量',width:150,align:'right' },
            {field:'IP_WM_CONCAT',title:'具体IP地址',width:150,align:'right' },
            {field:'IP_WM_CONCAT_COUNT',title:'IP数量',width:150,align:'right' },
            {field:'IPSUBNET_WM_CONCAT',title:'互联地址',width:150,align:'right',formatter:formatterIPsubnet},
            {field:'IPSUBNET_WM_CONCAT_COUNT',title:'互联地址数量',width:150,align:'right' }
        ]],
        toolbar:"#requestParamSettins_taskQuery",
        /*控制按钮配置*/
        onLoadSuccess:function(index,row){
            console.log(row);
        }
    })

    //翻译  业务需求 字段
    function formatterResourcesCategory(value,row,index){
        var res='';
        if(value.indexOf("100") != -1){
            if(!(res == '')){
                res += "+";
            }
            res="机柜";
        }
        if(value.indexOf("200") != -1){
            if(!(res == '')){
                res += "+";
            }
            res += "端口";
        }
        if(value.indexOf("300") != -1){
            if(!(res == '')){
                res += "+";
            }
            res += "IP";
        }
        if(value.indexOf("400") != -1){
            if(!(res == '')){
                res += "+";
            }
            res += "主机";
        }
        if(value.indexOf("500") != -1){
            if(!(res == '')){
                res += "+";
            }
            res += "增值业务";
        }
        return res;
    }

    //翻译  带宽 字段
    function formatterBandwidth(value,row,index){
        //带宽1:10M,2:100M,3:10/100BASET,4:100/1000BASET,5:10/100/1000BASET,6:1G,7:1.25G,8:2.5G,
        // 9:10G,10:40G,11:100G,12:250GE,......STM-256
        if(value !=undefined && value !=''){
            return value+'M';
        }
    }


    //翻译  IP安装网段分配的字段
    function formatterIPsubnet(value,row,index){

        var haveNumber = /[0-9]/;   //正则表达式，验证是否含有数字
        var res = haveNumber.test(value);//true,说明有数字
        //ip地址肯定有数字，所以，如果没有数字，就返回空字符串
        if(!res){
            return "";
        }else{
            return res;
        }
    }




}

