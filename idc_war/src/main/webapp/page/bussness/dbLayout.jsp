<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <style>
		.datagrid-row {
			height: 27px;
		}
	</style>
</head>
<body>
     <div style="padding: 5px;" id="requestParamSettins">
    	工单编号:<input class="easyui-textbox"  id="btsname" style="width:150px;text-align: left;" data-options="">
			<a href="javascript:void(0);" onclick="loadGrid('gridId')"  class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<!-- 其实就是发布流程 -->
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="publish()">批量审批</a>
   </div>
	   <table id="waitingClaimTask" style="width: 700px; height: 300px">
		<thead>
			<tr>
				<th field="taskId" hidden="true">编号</th>
				<th field="name" width="50">任务名称</th>
				<th field="processDefinitionId" width="50">流程定义</th>
				<th field="gdcode" width="50">工单编号</th>
				<th field="lxr" width="50">联系人</th>
				<th field="usercode" width="50">客户编号</th>
				<th field="processInstanceId" width="50">流程实例</th>
				<th field="opt" width="50">操作</th>
			</tr>
		</thead>
	</table>
<script type="text/javascript">
		//查看流程历史
		function claimTask(taskId){
			//ajax 记性签收
			layer.confirm('确定要签收该流程吗?', function(){ 
			//ajax
			$.ajax({
		        //提交数据的类型 POST GET
		        type:"POST",
		        //提交的网址
		        url:contextPath+"/activiti/claimTask.do",
		        //提交的数据
		        data:{
		        	taskId : taskId
		        },
		        //返回数据的格式
		        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
		        //在请求之前调用的函数
		        beforeSend:function(){
		        },
		        //成功返回之后调用的函数            
		        success:function(data){
		        	//提示删除成功
		        	//提示框
                    top.layer.msg(data.msg, {
                        icon: 1,
                        time: 3000 //（默认是3秒）
                    });
		        	$("#gridId").datagrid("reload");
		        },
		        //调用执行后调用的函数
		        complete: function(XMLHttpRequest, textStatus){
		        },
		        //调用出错执行的函数
		        error: function(){
		            //请求出错处理
		        }        
		     });
		 
	});
			 
		}
	    // 编辑初始化数据
		function getData(data){
			var rows = [];			
			var total = data.total;
			for(var i=0; i<data.rows.length; i++){
				rows.push({
					taskId: data.rows[i].taskId,
					name: data.rows[i].name,
					processDefinitionId: data.rows[i].processDefinitionId,
					usercode: data.rows[i].usercode,
					gdcode: data.rows[i].gdcode,
					lxr: data.rows[i].lxr,
					id: data.rows[i].id,
					processInstanceId: data.rows[i].processInstanceId,
					opt: "[<a href=\"#\" onclick=\"claimTask('"+data.rows[i].taskId+"')\">签收</a>]|[<a href=\"#\" onclick=\"queryTask('"+data.rows[i].id+"')\">查看</a>]"
				});
			}
			var newData={"total":total,"rows":rows};
			return newData;
		}
		function queryTask(id) {
			//query
			layer.open({
				  type: 2,
				  title: '<span style="color:blue">》查看</span>',
				  shadeClose: false,
				  shade: 0.8,
				  btn: ['关闭'],
			      maxmin : true,
				  area: ['850px', '550px'],
				  content: contextPath+"/createPublishResource/menuEdit.do?id="+id,//iframe的url:  控制查看是否是编辑还是查看
				  /*弹出框*/
				  cancel: function(index, layero){ 
				    //右上角关闭回调
				    layer.close(index);
				  },yes: function(index, layero){
				    	//刷新列表信息
					  layer.close(index);
				  },no: function(index, layero){
					  //按钮【按钮一】的回调
					  layer.close(index);
				  },end:function(){
					  $("#gridId").datagrid("reload");
				  }
				});
			
		}
		
		// 刷新
	    function reloadTable(){
	    	$('#waitingClaimTask').datagrid('reload');
	    }
	    
		// 设置datagrid属性
		$('#waitingClaimTask').datagrid({
			title: '待领任务列表',
	        idField: 'id',
	        fit:true,
	        loadMsg: '数据加载中...',
	        pageSize: 10,
	        pagination:true,
	        sortOrder:'asc',
	        rownumbers:true,
	        singleSelect:true,
	        fitColumns:true,
	        showFooter:true,
	        url:contextPath+'/activiti/dblistPage.do',  
	        loadFilter: function(data){
	        	return getData(data);
	    	}
	        
	    }); 
	    //设置分页控件  
	    $('#waitingClaimTask').datagrid('getPager').pagination({  
	        pageSize: 10,  
	        pageList: [10,20,30],  
	        beforePageText: '',  
	        afterPageText: '/{pages}',
	        displayMsg: '{from}-{to}共{total}条',
	        showPageList:true,
	        showRefresh:true,
	        onBeforeRefresh:function(pageNumber, pageSize){
	            $(this).pagination('loading');
	            $(this).pagination('loaded');
	        }
	    });
	</script>
     	<!-- 写到一张界面中的table -->
<!--      	<table class="easyui-datagrid" id="gridId" data-options="singleSelect:true,rownumbers:true,pagination:true,fitColumns:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table> -->
<!--      	<div id="mm1"class="easyui-menu" style="width:75px;">   -->
<!-- 		    <div iconCls="icon-search" id="menuSearch">查看</div> -->
<!-- 		</div> -->
<!--      	<div id="mm2"class="easyui-menu" style="width:75px;">   -->
<!-- 		   <div iconCls="icon-ok" id="menuOk">审批</div> -->
<!-- 		    <div iconCls="icon-cancel" id="menuCancel">驳回</div> -->
<!-- 		    <div iconCls="icon-search" id="menuSearch">查看</div> -->
<!-- 		</div> -->
<%--  	<script src="<%=request.getContextPath() %>/assets/bussness/publishLayout.js"></script>    --%>
</body>
</html>