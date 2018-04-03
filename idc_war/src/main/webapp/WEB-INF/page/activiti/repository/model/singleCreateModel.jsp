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
    <title>流程模型新增情况</title> 
</head>
<body>
	<form method="post" id="singleForm" action="<%=request.getContextPath() %>/modelController/create.do">
		<table class="dv-table" style="width:100%;padding:5px;margin-top:1px;">
			<tr>
				<td>名称:</td>
				<td>
					<input type="hidden"  name="id"  value="${id}" />
					<input type="text" class="easyui-textbox" name="name" style="width: 250px;" placeholder="输入模型名称" data-options="required:true" value="${name}" />
				</td>
			</tr>
			<tr>
				<td>关键字:</td>
				<td>
					<input type="text" class="easyui-textbox" name="key" style="width: 250px;" placeholder="输入模型关键字" data-options="required:true" value="${key}" />
				</td>
			</tr>
			<tr>
				<td>类别:</td>
				<td>
					<input class="easyui-combobox" value="${category}" data-options="
                                            valueField: 'value',
                                            textField: 'label',
                                            editable:false,
                                            data: [{
                                                label: '所有与业务流程无关的流程',
                                                value: 'process_self_defined'
                                            },{
                                                label: '预勘',
                                                value: 'idc_ticket_pre_accept'
                                            },{
                                                label: '开通',
                                                value: 'idc_ticket_open'
                                            },{
                                                label: '暂停',
                                                value: 'idc_ticket_pause'
                                            },{
                                                label: '复通',
                                                value: 'idc_ticket_recover'
                                            },{
                                                label: '下线',
                                                value: 'idc_ticket_halt'
                                            },{
                                                label: '临时测试',
                                                value: 'idc_ticket_temporary'
                                            },{
                                                label: '开通变更',
                                                value: 'idc_ticket_open_change'
                                            },{
                                                label: '业务变更',
                                                value: 'idc_ticket_business_change'
                                            },{
                                                label: '自有业务_预占预勘察流程',
                                                value: 'idc_ticket_self_pre_accept'
                                            },{
                                                label: '自有业务_开通流程',
                                                value: 'idc_ticket_self_open'
                                            },{
                                                label: '自有业务_变更开通',
                                                value: 'idc_ticket_self_open_change'
                                            },{
                                                label: '自有业务_停机',
                                                value: 'idc_ticket_self_pause'
                                            },{
                                                label: '自有业务_复通',
                                                value: 'idc_ticket_self_recover'
                                            },{
                                                label: '自有业务_业务变更',
                                                value: 'idc_ticket_self_business_change'
                                            },{
                                                label: '自有业务_业务下线',
                                                value: 'idc_ticket_self_halt'
                                            }]
                                            "style="width:250px;text-align: left;" placeholder="输入模型类别"  name="category" >
				</td>
			</tr>
		</table>
	</form>
</body>
<script type="application/javascript">
    function form_sbmt(grid,jbpmFunFlag){
        if(jbpmFunFlag && jbpmFunFlag == 'rejectJbpm'){
            $("#idcRunProcCment_status_stand").val(0);//驳回情况
        }
        $("#singleForm").form('submit', {
            url:contextPath+"/modelController/create.do",
            onSubmit: function(){
                if(!$("#singleForm").form("validate")){
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    return false;
                }
            },
            success:function(data){
                //然后修改下一个form
                var obj = jQuery.parseJSON(data);
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                var parentIndex = parent.layer.getFrameIndex("jbpmApplyWinId");//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
                grid.datagrid("reload");
            }
        });
    }
</script>
</html>