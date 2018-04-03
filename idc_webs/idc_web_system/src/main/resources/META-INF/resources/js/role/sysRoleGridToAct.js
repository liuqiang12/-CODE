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
        toolbar:"#requestParamSettins"
    })
}
function getRequestUrl(){
    return "/sysrole/sysRoleGridDataToAct.do";
}

// 获取查询条件
function getRequestParams(){
    var name = $("#name").textbox("getValue");
    var type = $("#type").combobox("getValue");
    /*参数组装*/
    var params = {};
    params['name'] = name;
    params['type'] = type;
    return params;
}
//获取列信息
function createColumns(){
    //创建列表信息
    var headCols = [];
    headCols.push({field:"ck",halign:'left',checkbox:true});
    headCols.push({field:"name",title:"角色名",halign:'left'});
    headCols.push({field:"type",title:"角色类型",halign:'left'});
    headCols.push({field:"role_key",title:"关键字",halign:'left'});
    headCols.push({field:"enabled",title:"状态",halign:'left'});
    headCols.push({field:"description",title:"描述",halign:'left'});
    headCols.push({field:"createTime",title:"创建时间",halign:'left'});
    return [headCols]
}
/**
 * 被选中的节点的记录
 */
function getWinCheckedRecord(gridId){
    var checkedRecord = $("#"+gridId).datagrid("getChecked");
    return checkedRecord;
}