<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
	<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>用户管理</title>
	<meta name="description" content="Static &amp; Dynamic Tables" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	
    <link href="<%=request.getContextPath() %>/framework/jqueryui/frame/JqueryPagination/jquery.pagination.css" rel="stylesheet" />
    <script src="<%=request.getContextPath() %>/framework/jqueryui/frame/JqueryPagination/jquery-1.11.2.min.js"></script>
    <!--  [整改]使“当前页”可以以参数形式从后台传入、同时支持form表单的submit方法 -->
    <script src="<%=request.getContextPath() %>/framework/jqueryui/frame/JqueryPagination/pageEngine.js"></script>
    <script src="<%=request.getContextPath() %>/framework/jqueryui/frame/JqueryPagination/jquery.pagination-1.2.7.js"></script>
    <style>
        body {
            font-family: 'Microsoft YaHei';
        }
    </style>
    <script type="text/javascript">
    	$(function(){
    		$("#demo").pageEngine ({
    			total : 1000,//总数
    			currentPageIndex : 5,//配置当前页参数
    			pageSize:11
    		});
    	})
    </script>
</head>
    <div style="width:1000px; margin:0 auto;">
        <header>
            <h1> 分页测试插件 </h1>
        </header>
        <form action="" id="defined-paginationCtlForm">
        	<div id="demo"></div>
        </form>
    </div>
</body>
</html>
