<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/globalstatic/easyui/head.jsp"%>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<table id="myprocessList" style="width: 700px; height: 300px">
	<thead>
		<tr>
			<th field="id" hidden="hidden">编号</th>
			<th field="name" width="50">流程名称</th>
			<th field="key" width="50">KEY</th>
			<th field="version" width="20">版本</th>
			<th field="image" width="50">流程图片</th>
			<th field="isSuspended" width="50">是否挂起</th>
			<th field="opt" width="50">操作</th>
		</tr>
	</thead>
</table>

<script type="text/javascript">
		//查看流程xml或流程图片
		function readProcessResouce(processDefinitionId,resourceType) {
			var url = "";
			var title = "";

			if(resourceType == "image"){
				title = "查看流程图片";
				url = contextPath+"/activiti/resourceRead.do?processDefinitionId="+processDefinitionId+"&resourceType=image&isIframe"
			}
			//弹出框形式
      	  layer.open({
				  type: 2,
				  title: title,
				  shadeClose: false,
				  shade: 0.8,
				  btn: ['关闭'],
                maxmin : true,
				  area: ['600px', '500px'],
				  content: url ,//iframe的url
				  /*弹出框*/
				  cancel: function(index, layero){ 
				    //右上角关闭回调
				    layer.close(index);
				  },yes: function(index, layero){
				    layer.close(index)
				  },no: function(index, layero){
					  //按钮【按钮一】的回调
					  layer.close(index)
				  }
				}); 
		}
		//ajax 进行发布流程
		function add(title,url,flag){
			//弹出框
			layer.open({
				  type: 2,
				  title: title,
				  shadeClose: false,
				  shade: 0.8,
				  btn: ['保存','关闭'],
              	maxmin : true,
				  area: ['600px', '500px'],
				  content: contextPath+""+url ,//iframe的url
				  /*弹出框*/
				  cancel: function(index, layero){ 
				    //右上角关闭回调
				    layer.close(index);
				  },yes: function(index, layero){
					  
					//ajax
						$.ajax({
					        //提交数据的类型 POST GET
					        type:"POST",
					        //提交的网址
					        url:contextPath+"/activiti/resouce_process.do",  
					        //提交的数据
					        //返回数据的格式
					        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
					        //在请求之前调用的函数
					        beforeSend:function(){
					        },
					        //成功返回之后调用的函数            
					        success:function(data){
					        	//提示删除成功
					        	//设置日月
					        	alert("发布成功！")
					        	 layer.close(index)
					        },
					        //调用执行后调用的函数
					        complete: function(XMLHttpRequest, textStatus){
					        },
					        //调用出错执行的函数
					        error: function(){
					            //请求出错处理
					        }        
					     });
				   
				  },no: function(index, layero){
					  //按钮【按钮一】的回调
					  layer.close(index)
				  }
				}); 
		}
		 
		
	    // 编辑初始化数据
		function getData(data){
			var rows = [];			
			var total = data.total;
			for(var i=0; i<data.rows.length; i++){
				rows.push({
					id: data.rows[i].id,
					processDefinitionId: data.rows[i].processDefinitionId,
					deploymentId: data.rows[i].deploymentId,
					name: data.rows[i].name,
					key: data.rows[i].key,
					version: data.rows[i].version,
					image: "[<a href=\"#\" onclick=\"readProcessResouce('"+data.rows[i].processDefinitionId+"','image')\">查看流程图片</a>]",
					isSuspended: data.rows[i].isSuspended,
					opt: "[<a href=\"#\" onclick=\"add('发起新流程','/activiti/startPageSelect.do','myprocessList')\">发起新流程</a>]"
				});
			}
			var newData={"total":total,"rows":rows};
			return newData;
		}
	    // 筛选
// 		function jeecgEasyUIListsearchbox(value,name){
//     		var queryParams=$('#myprocessList').datagrid('options').queryParams;
//     		queryParams[name]=value;
//     		queryParams.searchfield=name;
//     		$('#myprocessList').datagrid('load');
//     	}
	    // 刷新
	    function reloadTable(){
	    	$('#myprocessList').datagrid('reload');
	    }
	    
		// 设置datagrid属性
		$('#myprocessList').datagrid({
			title: '发起新流程',
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
	        url:contextPath+'/activiti/datagrid.do',  
	        loadFilter: function(data){
	        	return getData(data);
	    	}
	        
	    }); 
	    //设置分页控件  
	    $('#myprocessList').datagrid('getPager').pagination({  
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
	    // 设置筛选
//     	$('#jeecgEasyUIListsearchbox').searchbox({
//     		searcher:function(value,name){
//     			jeecgEasyUIListsearchbox(value,name);
//     		},
//     		menu:'#jeecgEasyUIListmm',
//     		prompt:'请输入查询关键字'
//     	});
	</script></div>
</div>