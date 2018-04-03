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
    <title>流程模型详情情况</title>
</head>
<body>
	<form method="post" id="singleForm" action="<%=request.getContextPath() %>/modelController/create22222.do">
		<table class="dv-table" style="width:100%;padding:5px;margin-top:1px;">
			<tr>
				<td>名称:</td>
				<td>
					<input type="text" class="easyui-textbox" name="name" readonly="readonly" style="width: 250px;" placeholder="输入模型名称" data-options="required:true" value="${actModel.name}" />
				</td>

				<td>关键字:</td>
				<td>
					<input type="text" class="easyui-textbox" name="key" readonly="readonly" style="width: 250px;" placeholder="输入模型关键字" data-options="required:true" value="${actModel.key}" />
				</td>
			</tr>
			<tr>
				<td>类别:</td>
				<td>
					<input class="easyui-combobox" value="${actModel.category}" readonly="readonly" style="width: 250px;" data-options="
                                            valueField: 'value',
                                            textField: 'label',
                                            editable:false,
                                            data: [{
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
                                            }]
                                            "style="width:250px;text-align: left;" placeholder="输入模型类别"  name="category" >
				</td>

				<td>创建时间:</td>
				<td>
					<input type="text" class="easyui-datebox"style="width: 250px;"  readonly="readonly"  value="${actModel.createTime}" formatter="function(date){
							var y = date.getFullYear();
							var m = date.getMonth()+1;
							var d = date.getDate();
							return m+'/'+d+'/'+y;
						}" />
				</td>
			</tr>
			<tr>
				<td>修改时间:</td>
				<td>
					<input type="text" class="easyui-datebox" style="width: 250px;" readonly="readonly"  value="${actModel.lastUpdateTime}" formatter="function(date){
							var y = date.getFullYear();
							var m = date.getMonth()+1;
							var d = date.getDate();
							return m+'/'+d+'/'+y;
						}" />
				</td>

			</tr>
			<tr>
				<td>元数据:</td>
				<td colspan="3">
					<c:out value="${actModel.metaInfo}"></c:out>
				</td>
			</tr>
			<tr>
				<td>部署ID:</td>
				<td>
					<input type="text" class="easyui-textbox" style="width: 250px;"  readonly="readonly" value="${actModel.deploymentId}" />
				</td>
				<td>模型ID:</td>
				<td>
					<input type="text" class="easyui-textbox" style="width: 250px;" name="id"  readonly="readonly" value="${actModel.id}" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>