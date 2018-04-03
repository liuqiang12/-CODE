/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");
})
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
    return "/userinfo/sysUserinfoGridDataToAct.do";
}

// 获取查询条件
function getRequestParams(){
    var username = $("#username").textbox("getValue");
    /*参数组装*/
    var params = {};
    params['username'] = username;
    return params;
}
//获取列信息
function createColumns(){
    //创建列表信息
    var headCols = [];
    headCols.push({field:"ck",halign:'left',checkbox:true});
    headCols.push({field:"username",title:"名称",halign:'left',width:150});
    headCols.push({field:"nick",title:"昵称",halign:'left',width:150});
    headCols.push({field:"sex",title:"性别",halign:'left',width:150});
    headCols.push({field:"age",title:"年龄",halign:'left',width:150});
    headCols.push({field:"enabled",title:"状态",halign:'left',width:150,formatter:fmtAction});
    headCols.push({field:"createTime",title:"创建时间",halign:'left',width:150});
    return [headCols]
}
/**
 * 被选中的节点的记录
 */
function getWinCheckedRecord(gridId){
    var checkedRecord = $("#"+gridId).datagrid("getChecked");
    return checkedRecord;
}
function fmtAction(value,row,index){
    if( value != null ){
        return "启用";
    }else {
        return "禁用";
    }
}