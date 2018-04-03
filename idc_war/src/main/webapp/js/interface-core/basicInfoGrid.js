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
        onLoadSuccess:myTaskLoadsuccess,
        toolbar:"#requestParamSettins_query"
    })
}
//初始化按钮事件
function myTaskLoadsuccess(data){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });
}
function getRequestUrl(){
    return "/idcISPController/basicInfoGridData.do";
}

// 获取查询条件
function getRequestParams(){
    /*参数组装*/
    var params = {};
    params['provId'] = 280;
    return params;
}
//获取列信息
function createColumns(){
    //创建列表信息
    var headCols = [];
    //IDC_ISP_BASIC_INFO
    headCols.push({field:"provId",title:"省份编号",halign:'left',width:50});
    /*//IDC_ISP_INTERFACE_INFO
    headCols.push({field:"idcId",title:"IDC/ISP经营者ID",halign:'left',width:50});
    headCols.push({field:"idcName",title:"经营者名称",halign:'left',width:90});
    headCols.push({field:"idcAdd",title:"经营者通信地址",halign:'left',width:60});
    headCols.push({field:"idcZip",title:"对应经营者通信地址的邮编",halign:'left',width:40});
    headCols.push({field:"corp",title:"企业法人",halign:'left',width:90});
    /!*网络安全负责人*!/
    //IDC_ISP_COFFICER_INFO通过IDC_ISP_INTERFACE_INFO中FK_COFFICER_INFO_ID关联
    headCols.push({field:"fkCofficerInfoId",title:"网络安全负责人ID",halign:'left',width:50});
    //IDC_ISP_COFFICER_INFO通过IDC_ISP_INTERFACE_INFO中FK_EMERGENCY_CONTACT关联
    headCols.push({field:"fkEmergencyContact",title:"应急联系人信息ID",halign:'left',width:50});
    //IDC_ISP_HOUSE_INFO通过FK_NEW_INFO_ID关联表IDC_ISP_INTERFACE_INFO
    headCols.push({field:"fkNewInfoId",title:"数据中心ID",halign:'left',width:90});*/
    /*操作按钮*/
    return [headCols]
}