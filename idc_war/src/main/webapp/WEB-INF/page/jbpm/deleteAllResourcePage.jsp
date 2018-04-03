<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/start/start.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>合同信息列表</title>
</head>
<style type="text/css">
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0 !important;
    }
</style>
<body>

<div >
    <form method="post" id="singleForm" action="<%=request.getContextPath() %>/actJBPMController/procCmentFormSaveOrUpdate.do">
        <div id="container" style="width:500px;">
            <input name="ticketInstId" id="ticketInstId" value="${ticketInstId}" type="hidden"/>
            <input name="prodInstId" id="prodInstId" value="${prodInstId}" type="hidden"/>
            <input name="operationSign" id="operationSign" value="isDelete" type="hidden"/>

            <div>
                <br />
                <p align="center"><span style="color: red;font-size: 26px">警告：您正在一键删除资源！</span></p>

                <br />
                    <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <span style="color: #007bb9;font-size: 16px"> 此操作将直接把资源的状态改为空闲状态！</span>
                    </p>
                <br />
            </div>
        </div>
</body>
<script type="text/javascript">

    /*提交表单情况*/
    function form_sbmt_delete(grid,parentId){
        //
        $("#singleForm").form('submit', {
            url:contextPath+"/actJBPMController/rubbishOrDeleteTicket.do",
            onSubmit: function(){
                //提交前执行的函数
                top.onclickProcessBar();  //打开进度条
            },
            success:function(data){
                top.closeProgressbar();    //关闭进度条
                //然后修改下一个form
                var obj = jQuery.parseJSON(data);
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //（默认是3秒）
                }, function(){
                    //do something
                });
                var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer*/
                if(parentId){
                    var topIndex = parent.layer.getFrameIndex(parentId);//获取了父窗口的索引
                    top.layer.close(topIndex);
                    grid.datagrid("reload");
                }else{
                    var topIndex = parent.layer.getFrameIndex("jbpmApplyWinId");//获取了父窗口的索引
                    top.layer.close(topIndex);
                    grid.datagrid("reload");
                }

            }
        });
    }
</script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/star.js"></script>
</html>