<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <title>合同列表</title>
    <style>
		.datagrid-row {
			height: 27px;
		}
	</style>
</head>
<body>
<div style="padding: 5px;" id="requestParamSettins">
	客户名称:<input class="easyui-textbox"  id="name" style="width:300px;text-align: left;" data-options="">
	客户类别:<input class="easyui-combobox" style="width:300px;text-align: left;"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								data: [{
									label: '局方',
									value: '10'
								},{
									label: '第三方',
									value: '20'
								},{
									label: '测试',
									value: '30'
								},{
									label: '退场客户',
									value: '40'
								},{
									label: '国有',
									value: '50'
								},{
									label: '集体',
									value: '60'
								},{
									label: '私营',
									value: '70'
								},{
									label: '股份',
									value: '80'
								},{
									label: '外商投资',
									value: '90'
								},{
									label: '港澳台投资',
									value: '100'
								},{
									label: '客户',
									value: '110'
								},{
									label: '自由合作',
									value: '120'
								},{
									label: '内容引入',
									value: '130'
								},{
									label: '其他',
									value: '999'
								}]"  id="category"/>
	<a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="singleCreateOrUpdate('gridId')">新增</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="singleDeleteRow('gridId')">删除</a>
</div>

<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,nowrap: false,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
<script>
    $(function() {
        //初始化时就加载数据:构造列表信息
        loadGrid("gridId");
    });
    function loadGrid(gridId){
        //创建表信息
        $("#"+gridId).datagrid({
            url : contextPath + "/customerController/opportunityData.do",
            /*queryParams: params,*/
            columns : [[
            {field:'busName',title:'业务名称',width:280},
            {field:'name',title:'客户',width:400,formatter:fmtLinkAction},
            {field:'time',title:'预计成单时间',width:300,align:'right'},
            {field:'yesOrNo',title:'是否成单',width:300,align:'right'}
			           ]] ,
			/*onLoadSuccess:myLoadsuccess,*/
            toolbar:"#requestParamSettins"
        })
    }


    function fmtLinkAction(value,row,index){
        return '<a href="javascript:void(0);" onclick="linkQueryWin(\''+row.id+'\',\''+row.name+'\')">'+value+'</a>';
    }
    //查看详情界面
    function linkQueryWin(id,name){
        var laySum = top.layer.open({
            type: 2,
            title: '<span style="color:blue">'+name+'</span>'+"》详情",
            shadeClose: false,
            shade: 0.8,
            btn: ['关闭'],
            maxmin : true,
            area: ['784px', '600px'],
            content: contextPath+"/customerController/linkQueryWin.do?isOpportunity=123&id="+id,
            cancel: function(index, layero){
                top.layer.close(index);
            },no: function(index, layero){
                top.layer.close(index)
            }
        });
    }


    //新增相应的模型
    function singleCreateOrUpdate(gridId){
        var laySum = top.layer.open({
            type: 2,
            title: '<span style="color:blue">商机</span>'+"》新增",
            shadeClose: false,
            shade: 0.8,
            btn: ['保存','关闭'],
            maxmin : true,
            area: ['800px', '400px'],
            content: contextPath+"/customerController/opportunityCreate.do",
			/*弹出框*/
            cancel: function(index, layero){
                top.layer.close(index);
            },yes: function(index, layero){
                var childIframeid = layero.find('iframe')[0]['name'];
                var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                chidlwin.form_sbmt("gridId");
                //刷新列表信息
            },no: function(index, layero){
                top.layer.close(index)
            }
        });

    }

</script>
</body>
</html>