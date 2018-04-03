$(function() {
	//初始化时就加载数据:构造列表信息
	loadGrid("gridId");
})


function loadGrid(gridId){
	//创建表信息
	
	var url = getRequestUrl();
	var columns = createColumns();
//	var frozenColumns = createFrozenColumns(resourceType);
	$("#"+gridId).datagrid({
		url : contextPath + url,
//		queryParams: params,
//		frozenColumns : frozenColumns,
		columns : columns,
		onLoadSuccess:myLoadsuccess,
		toolbar:"#requestParamSettins"
	})
}
//初始化按钮事件
function myLoadsuccess(data){
	for (i = 0; i < $('#gridId').datagrid('getRows').length; i++) {        
        $('#mb' + i).menubutton();
    }
}
function getRequestUrl(){
	return "/activiti/dblistPage.do";
}

　
//获取列信息
function createColumns(){
	//创建列表信息
	var headCols = [];
	headCols.push({field:"gdcode",title:"工单编号",width:150});
	headCols.push({field:"usercode",title:"客户编号",width:150});
	headCols.push({field:"status",title:"状态",width:150});
	headCols.push({field:"handuser",title:"当前处理人",width:300});
	headCols.push({field:"lxr",title:"联系人",width:80});
	headCols.push({field:"email",title:"联系人Email",width:150});
	headCols.push({field:"timeStr",title:"提交时间",width:150}); 
	headCols.push({field:"option",title:"操作",halign:'center',width:80,formatter:fmtAction});
	
	return [headCols];
}
function fmtAction(value,row,index){
    var insert= new Array();
    // 获取被选中的tabs是哪个
    // record  row.status == '审批中'
    if(row.status == '评价中'){
    	//不能进行任何操作
    	insert.push('<a class="easyui-menubutton " id="mb' + index + '" menu="#mm1"  data-options="plain:true,iconCls:\'icon-settting\'" onclick="ShowMenu('+index+',\''+row.status+'\')"></a> ');
    }else if(row.status == '审批中'){
    	//不能进行任何操作
    	insert.push('<a class="easyui-menubutton " id="mb' + index + '" menu="#mm1"  data-options="plain:true,iconCls:\'icon-settting\'" onclick="ShowMenu('+index+',\''+row.status+'\')"></a> ');
    }else if(row.status == '结束'){
    	insert.push('<a class="easyui-menubutton " id="mb' + index + '" menu="#mm2"  data-options="plain:true,iconCls:\'icon-settting\'" onClick="ShowMenu('+index+',\''+row.status+'\')"></a> ');
    }else if(row.status == '草稿'){
    	insert.push('<a class="easyui-menubutton " id="mb' + index + '" menu="#mm3"  data-options="plain:true,iconCls:\'icon-settting\'" onClick="ShowMenu('+index+',\''+row.status+'\')"></a> ');
    }else if(row.status == '驳回'){
    	insert.push('<a class="easyui-menubutton " id="mb' + index + '" menu="#mm4"  data-options="plain:true,iconCls:\'icon-settting\'" onClick="ShowMenu('+index+',\''+row.status+'\')"></a> ');
    }
    
    return insert.join('');
}

function ShowMenu(index,status){
	$('#mm3').menu({
		onClick: function (item) {
			if (item.id != undefined && eval(item.id) != undefined) {
				item.onclick = eval(item.id + "(" + index + ")");
			}
		} 
	});
	$('#mm1').menu({
		onClick: function (item) {
			if (item.id != undefined && eval(item.id) != undefined) {
				item.onclick = eval(item.id + "(" + index + ")");
			}
		} 
	});
	$('#mm2').menu({
		onClick: function (item) {
			if (item.id != undefined && eval(item.id) != undefined) {
				item.onclick = eval(item.id + "(" + index + ")");
			}
		} 
	});
	$('#mm4').menu({
		onClick: function (item) {
			if (item.id != undefined && eval(item.id) != undefined) {
				item.onclick = eval(item.id + "(" + index + ")");
			}
		} 
	});
}
//在此 所有的流程都走部署ID:20001
//编辑
function menuEdit(key) {
//    alert(key);
}
//提交流程：即流程创建
function menuSave(key) {
	var record = $('#gridId').datagrid("getRows")[key];
	console.log(record);
	
	//直接ajax进行提交
	layer.confirm('确定要提交改流程吗?', function(){ 
		//ajax
		$.ajax({
	        //提交数据的类型 POST GET
	        type:"POST",
	        //提交的网址
	        url:contextPath+"/activiti/applyStart.do",
	        //提交的数据
	        data:{
	        	id : record['id'],
	        	status : record['status'],
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
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
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
//开通业务
function menuOk(key) {
//    alert(key);
}
//删除工单
function menuCancel(key) {
//    alert(key);
}
//修改方法
/**
 * zgid:资管ID
 * index:索引
 * name：资源名称
 * :修改
 * 
 */
function singleUpdateRow(zgid,index,name,id){
	//修改弹出框 进行修改
	var laySum = top.layer.open({
		  type: 2,
		  title: '<span style="color:blue">'+name+'</span>'+"》修改",
		  shadeClose: false,
		  shade: 0.8,
		  btn: ['确定','关闭'],
	      maxmin : true,
		  area: ['800px', '600px'],
		  content: contextPath+"/resbts/baseinfoHandler.do?zgid="+zgid+"&id="+id,//iframe的url:  控制查看是否是编辑还是查看
		  /*弹出框*/
		  cancel: function(index, layero){ 
		    //右上角关闭回调
		    top.layer.close(index);
		  },yes: function(index, layero){
		    	var childIframeid = layero.find('iframe')[0]['name'];
		    	var chidlwin = top.document.getElementById(childIframeid).contentWindow;
		    	chidlwin.form_sbmt("gridId");
		    	//刷新列表信息
		  },no: function(index, layero){
			  //按钮【按钮一】的回调
			    top.layer.close(index)
		  },end:function(){
			  $("#gridId").datagrid("reload");
		  }
		});
}
　
function addResouce(){
	//修改弹出框 进行修改
	var laySum = top.layer.open({
		  type: 2,
		  title: "》新增",
		  shadeClose: false,
		  shade: 0.8,
		  btn: ['确定','关闭'],
	      maxmin : true,
		  area: ['800px', '600px'],
		  content: contextPath+"/resbts/baseinfoHandler_zgid.do",//iframe的url:  控制查看是否是编辑还是查看
		  /*弹出框*/
		  cancel: function(index, layero){ 
		    //右上角关闭回调
		    top.layer.close(index);
		  },yes: function(index, layero){
		    	var childIframeid = layero.find('iframe')[0]['name'];
		    	var chidlwin = top.document.getElementById(childIframeid).contentWindow;
		    	chidlwin.form_sbmt("gridId");
		    	//刷新列表信息
		  },no: function(index, layero){
			  //按钮【按钮一】的回调
			    top.layer.close(index)
		  },end:function(){
			  $("#gridId").datagrid("reload");
		  }
		});
}


/**
 * 删除资管信息
 * zgid:资管ID
 * index:索引
 * name：资源名称
 */
function singleDeleteRow(id,index,name){
	//确认删除的layer
	top.layer.confirm('确定删除'+name+'?', function(){ 
		//ajax
		$.ajax({
	        //提交数据的类型 POST GET
	        type:"POST",
	        //提交的网址
	        url:contextPath+"/resbts/baseinfo_delete.do",
	        //提交的数据
	        data:{
	        	id : id
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
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
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