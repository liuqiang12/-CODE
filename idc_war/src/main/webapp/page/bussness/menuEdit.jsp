<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
<title>资源占用流程</title>
</head>
<body>
	<div class="easyui-tabs" fit="true"  >
	    <div title="客户信息" style="padding:10px;display:none;">
			<form id="formId" method="post">
				<table style="width: 95%;height: 100%;">
					<tr>
						<td style="width: 50%">
							<input class="easyui-textbox"  type="text"  value="${testResouces.gdcode }"   style="width:90%" data-options="label:'编号'">
						</td>
						<td>
							<input class="easyui-textbox"  type="text"  value="${testResouces.usercode }"  style="width:90%" data-options="label:'名称'">
						</td>
					</tr>
					<tr>
						<td style="width: 50%">
							<input class="easyui-textbox"  type="text"     style="width:90%" data-options="label:'地址'">
						</td>
						<td>
							<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'性质'">
						</td>
					</tr>
					<tr>
						<td style="width: 50%">
							<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'类别'">
						</td>
						<td>
							<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'级别'">
						</td>
					</tr>
					<tr>
						<td style="width: 50%">
							<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'等级'">
						</td>
						<td>
							<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'类型'">
						</td>
					</tr>
					<tr>
						<td>
							<input class="easyui-textbox"  style="width:90%" data-options="label:'合同开始日期'">
						</td>
						<td>
							<input class="easyui-textbox"  style="width:90%" data-options="label:'合同结束日期'">
						</td>
					</tr>
					
					<tr>
						<td>
							<input class="easyui-textbox"  style="width:90%" value="${testResouces.lxr }" data-options="label:'联系人姓名'">
						</td>
						<td>
							<input class="easyui-textbox"   style="width:90%" value="${testResouces.email }" data-options="label:'联系人EMILE'">
						</td>
					</tr>
				</table>
			</form>
	    </div>
	    <div title="预受理" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
	    	<form id="formId2" method="post">
			<table style="width: 95%;height: 100%;">
				<tr>
					<td style="width: 50%">
						<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'预受理编号'">
					</td>
					<td>
						<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'产品名称 *'">
					</td>
				</tr>
				<tr>
					<td style="width: 50%">
						<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'机房名称'">
					</td>
					<td>
						<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'机房信息'">
					</td>
				</tr>
				<tr>
					<td style="width: 50%">
						<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'楼层信息'">
					</td>
					<td>
						<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'签约起始日期'">
					</td>
				</tr>
				<tr>
					<td style="width: 50%">
						<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'签约截止日期'">
					</td>
					<td>
						<input class="easyui-textbox"  type="text"    style="width:90%" data-options="label:'审批意见'">
					</td>
				</tr>
			</table>
			</form>
	    </div>
	</div>
</body>
</html>