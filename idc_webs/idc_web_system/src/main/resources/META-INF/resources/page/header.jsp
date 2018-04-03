<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--<%@ page isErrorPage="true" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%--%>
    <%--String contextPath = request.getContextPath();--%>
<%--%>--%>
<%--<link href="<%=request.getContextPath() %>/framework/hplusV4.1.0/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">--%>

<%--<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/layout/ui7/css/common.css">--%>
<%--<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/layout/ui7/css/menu.css">--%>
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/layout/ui7/css/style.css">--%>
<%--&lt;%&ndash;<!-- jqgrid-->&ndash;%&gt;--%>
<%--<link href="<%=request.getContextPath() %>/framework/hplusV4.1.0/css/plugins/jqgrid/ui.jqgridffe4.css?0820"--%>
      <%--rel="stylesheet">--%>
<%--<link href="<%=request.getContextPath() %>/framework/bootstrap1.3.2/assets/css/jquery-ui.custom.css"--%>
      <%--rel="stylesheet">--%>
<%--<link rel="stylesheet" type="text/css"--%>
      <%--href="<%=request.getContextPath() %>/framework/jeasyui/themes/bootstrap/easyui.css">--%>
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/themes/icon.css">--%>
<%--<link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">--%>
<%--<script type="text/javascript"--%>
        <%--src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery-2.0.3.min.js"></script>--%>
<%--<script type="text/javascript"--%>
        <%--src="<%=request.getContextPath() %>/framework/bootstrap/js/bootstrap.min.js"></script>--%>
<%--<script type="text/javascript"--%>
        <%--src="<%=request.getContextPath() %>/framework/jeasyui/jquery.easyui.min.js"></script>--%>
<%--<script type="text/javascript"--%>
        <%--src="<%=request.getContextPath() %>/framework/jeasyui/locale/easyui-lang-zh_CN.js"></script>--%>
<%--<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree.all-3.5.min.js"></script>--%>

<%--<style>--%>
    <%--.form-table{--%>
        <%--border:0;--%>
        <%--border-collapse:separate;--%>
        <%--border-spacing:1px;--%>
        <%--background: #5593E7;--%>
    <%--}--%>
    <%--.table-label{--%>
        <%--background:#E0ECFF;--%>
        <%--text-align:center;--%>
        <%--font-family:寰蒋闆呴粦;--%>
        <%--font-size:13px;--%>
        <%--line-height:25px;--%>
        <%--font-weight:bold;--%>
    <%--}--%>
    <%--.table-input{--%>
        <%--margin:0;--%>
        <%--padding:0px 0px 0px 0px;--%>
        <%--overflow:hidden;--%>
        <%--background-color:white;--%>
        <%--font-size:12px;--%>
    <%--}--%>
    <%--/*.table_label {*/--%>
    <%--/*text-align: center;*/--%>
    <%--/*background-color: #cbddf3;*/--%>
    <%--/*}*/--%>
<%--</style>--%>
<%--<title>main</title>--%>
<%--<script type="text/javascript" language="JavaScript">--%>
    <%--var openWindow=null;--%>
    <%--/**--%>
     <%--*--%>
     <%--* @param url 璇锋眰鐨勯摼鎺�-%>
     <%--* @param width  寮瑰嚭妗嗙殑瀹藉害--%>
     <%--* @param heigth 寮瑰嚭妗嗙殑楂樺害--%>
     <%--* @param title  寮瑰嚭娆炬鐨勬爣棰�-%>
     <%--* @param islock 寮瑰嚭妗嗘槸鍚﹂攣灞�-%>
     <%--* @param parent 寮瑰嚭妗嗙殑鐖堕〉闈�-%>
     <%--*/--%>
    <%--function openWindowIfram(url,width,heigth,title,islock,parent){--%>
        <%--openWindow=$.dialog({--%>
            <%--content : 'url:'+rootPath + url,--%>
            <%--title : title,--%>
            <%--width : width,--%>
            <%--height : heigth,--%>
            <%--lock : islock==undefined?true:islock,--%>
            <%--parent:parent==undefined?null:parent--%>
        <%--});--%>
    <%--}--%>
    <%--function closeWindow(){--%>
        <%--try{--%>
            <%--if(openWindow!=null){--%>
                <%--openWindow.close();--%>
                <%--openWindow=null;--%>
            <%--}else{--%>
                <%--frameElement.api.close();--%>
                <%--openWindow=null;--%>
            <%--}--%>
        <%--}catch(e){--%>
            <%--alert("鍏抽棴寮瑰嚭绐楀紓甯�..");--%>
        <%--}--%>
    <%--}--%>
    <%--function refWinUrl(divId,url){--%>
        <%--$("#"+divId).attr("src",url);--%>
    <%--}--%>
<%--</script>--%>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<%--<link href="<%=request.getContextPath() %>/framework/bootstrap/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">--%>
<%--<link rel="stylesheet" type="text/css"--%>
      <%--href="<%=request.getContextPath() %>/framework/jeasyui/themes/default/easyui.css">--%>
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/themes/icon.css">--%>
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/layout/ui7/css/style.css">--%>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery-2.0.3.min.js"></script>
<%--<script type="text/javascript"--%>
        <%--src="<%=request.getContextPath() %>/framework/bootstrap/js/bootstrap.min.js"></script>--%>
<%--<script type="text/javascript"--%>
        <%--src="<%=request.getContextPath() %>/framework/jeasyui/jquery.easyui.min.js"></script>--%>
<%--<script type="text/javascript"--%>
        <%--src="<%=request.getContextPath() %>/framework/jeasyui/locale/easyui-lang-zh_CN.js"></script>--%>
<%--<script type="text/javascript" src="<%=request.getContextPath() %>/framework/jqueryui/frame/layui/layer.js"></script>--%>

