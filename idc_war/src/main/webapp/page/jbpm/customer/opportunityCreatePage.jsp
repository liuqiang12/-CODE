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
    <title></title>
    <style>
		.datagrid-row {
			height: 27px;
		}
	</style>
</head>
<body>

<!-- 开通信息form界面 start -->
<form id="contractFrom" method="post"  enctype="multipart/form-data" >
        <table class="kv-table">
            <tr>
                <td class="kv-label" style="width: 200px;">业务名称<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input class="easyui-textbox" name="contractname" value="${idcContract.contractname}"  id="contractname" data-options="required:true,validType:'length[0,128]',width:250"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">客户名称<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input name="idcContract_customerName" id="idcContract_customerName" value="${idcReCustomer.name}" class="easyui-textbox" data-options="editable:false,required:true,width:250">
                </td>
            </tr>
            <tr>
                <td class="kv-label">订单预计据时间<span style="color:red">&nbsp;*</span></td>
                <td class="kv-content">
                    <input class="easyui-numberbox" name="contractperiod" value="${idcContract.contractperiod}" id="contractperiod" data-options="required:true,precision:0,min:0,validType:'length[0,3]',width:250"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">项目说明</td>
                <td class="kv-content" colspan="4">
                    <input class="easyui-textbox" multiline="true"  name="contractremark"  value="${idcContract.contractremark}" style="width:88%;height:100px" data-options="validType:'length[0,255]'">
                </td>
            </tr>
        </table>
</form>
<!-- 开通信息form界面 end-->
</div>
</body>
</html>