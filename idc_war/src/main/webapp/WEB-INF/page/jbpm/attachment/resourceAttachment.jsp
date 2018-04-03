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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <!-- 上传控件 -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/webuploader-0.1.5/uploadDemo/css/webuploader.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/webuploader-0.1.5/uploadDemo/css/style.css" />

    <title>合同信息列表</title>
</head>
<style type="text/css">
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0 !important;
    }
</style>
<body>

<form id="attachmentAdjunctFrom" method="post"  enctype="multipart/form-data" >
    <!-- Uploadify:附件上传和下载插件 -->
    <input id="prodInstId" type="hidden" value="${prodInstId}" />
    <input id="ticketInstId" type="hidden" value="${ticketInstId}" />
    <input id="attachmentType" type="hidden" value="${attachmentType}" />
    <div class="fileupload_wrapper">
        <div class="fileupload_statusBar" style="display:none;">
            <div class="progress">
                <span class="text">0%</span>
                <span class="percentage"></span>
            </div>
            <div class="info"></div>
            <div class="btns">
                <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
            </div>
        </div>
        <div class=".fileupload_container">
            <!--头部，相册选择和格式选择-->
            <div id="uploader">
                <div class="queueList">
                    <div id="dndArea" class="placeholder">
                        <div id="filePicker"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
<script type="application/javascript" src="<%=request.getContextPath() %>/framework/webuploader-0.1.5/uploadDemo/js/webuploader.js" ></script>
<script type="application/javascript" src="<%=request.getContextPath() %>/framework/webuploader-0.1.5/uploadDemo/js/upload.js" ></script>
</html>