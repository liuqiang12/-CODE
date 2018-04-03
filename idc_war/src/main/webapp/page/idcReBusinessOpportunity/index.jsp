<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>业务商机信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
</head>
<body style="margin:0;padding:2px;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'center',border:false" style="padding-left: 2px;">
            <table id="table" class="easyui-datagrid"
                   data-options="fit:true,rownumbers:false,pagination:true,
                   pageSize:15,pageList:[10,15,20,50],striped:true,singleSelect:true,
                   fitColumns:true,toolbar:'#toolbar',onClickRow:fun
                  ">
                <thead>
                <tr>
                    <th data-options="field:'id',hidden:'true'"></th>
                    <th data-options="field:'businessName',width:100">业务名称</th>
                    <th data-options="field:'customerName',width:100">客户名称</th>
                    <th data-options="field:'orderPredictTime',width:100,formatter:fmtDateAction">预计成单时间</th>
                    <th data-options="field:'isordered',width:100">是否成单</th>
                    <th data-options="field:'userName',width:100">创建人</th>
                    <th data-options="field:'createTime',width:100,formatter:fmtDateAction">创建时间</th>
                    <th data-options="field:'remark',width:100">备注</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<div id="toolbar" style="height: 28px" class="paramContent">
    业务名称:
    <input id="buiName" class="easyui-textbox" placeholder="名称"/>&nbsp;&nbsp;
    预计成单时间起:
    <input class="easyui-datetimebox" data-options="width:200" id="beginTime"/>&nbsp;&nbsp;
    预计成单时间止:
    <input class="easyui-datetimebox" data-options="width:200" id="endTime"/>&nbsp;&nbsp;
    <a href="javascript:void(0);" onclick="searchModels()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    <div class="param-actionset" style="padding-right: 15px;">
        <a href="javascript:void(0);" onclick="saveBusinessOpportunity('add')" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
        <a href="javascript:void(0);" onclick="saveBusinessOpportunity('update')" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
        <a href="javascript:void(0);" onclick="delBusinessOpportunity()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">删除</a>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
    var tableId = "table";
    $(function(){
        $('#table').datagrid({
            url:contextPath+"/idcReBusinessOpportunity/list",
            onDblClickRow: function (index, row) {
                var url = contextPath+"/idcReBusinessOpportunity/preSaveIdcReBusinessOpportunity";
                var data = {buttonType:"view",id:row.id};
                openDialogUsePostView("业务商机信息",url,"800px","300px",data,"idcReBusinessOpportunityInfo");
            }
        });
    });
    /*查询*/
    function searchModels(){
        $('#table').datagrid({
            url:contextPath+"/idcReBusinessOpportunity/list",
            queryParams:getQueryParams()
        });
    }
    /*新增、修改*/
    function saveBusinessOpportunity(type){
        var url = contextPath+"/idcReBusinessOpportunity/preSaveIdcReBusinessOpportunity";
        var title = "业务商机信息";
        var data = {buttonType:type};
        if(type == "add"){//新增
            title = "新增"+title;
        }else{//修改
            title = "修改"+title;
            var rows = $("#table").datagrid("getSelections");
            if(rows==null||rows.length<1){
                alert("请选择要修改的数据");
                return;
            }
            data.id=rows[0].id;
        }
        openDialogUsePost(title,url,"800px","400px",data,"idcReBusinessOpportunityInfo");
    }
    /*删除*/
    function delBusinessOpportunity(){
        var rowArr = [];
        var rows = $("#table").datagrid("getSelections");
        if(rows==null||rows.length<1){
            alert("请选择要删除的数据");
            return;
        }
        for(var i=0;i<rows.length;i++){
            rowArr.push(rows[i].id);
        }
        layer.confirm("是否确认删除？",{btn:["确认","取消"]},function(index){
            $.post(contextPath+"/idcReBusinessOpportunity/deleteBusinessOpportunityByIds",{ids:rowArr.join(",")},function(result){
                layer.alert(result.msg);
                if(result.state){
                    searchModels();
                }
            })
        })
    }
    function getQueryParams(){
        var name = $("#buiName").val();
        var beginTime = $("#beginTime").datetimebox("getValue");
        var endTime = $("#endTime").datetimebox("getValue");
        var queryParams = {businessName:name,beginOrderPredictTime:beginTime,endOrderPredictTime:endTime};
        return queryParams;
    }
</script>
</body>
</html>