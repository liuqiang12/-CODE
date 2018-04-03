<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/include/include.jsp" %>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>用户管理</title>
		<meta name="description" content="Static &amp; Dynamic Tables" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<script type="text/javascript">
			$(function(){
				initPage(document.queryForm);
				//全选
	        	$("#checkedAll").click(function(){
	        		$(":checkbox[name='ids']").attr("checked",this.checked);
	        	});
	        	$(":checkbox[name='ids']").click(function(){
	        		$("#checkedAll").attr("checked",$(":checkbox[name='ids']").length == $(":checkbox[name='ids']:checked").length);
	        	});
			});	
            function load(){
        		if('${message}'=='save'){
					top.layer.msg("保存成功！");
				}
      	   		if('${message}'=='delete'){
					top.layer.msg("删除成功！");
				}
            }
           
           //新增
           function insertRow(form, action, subtarget) {
	        	$("input[type='checkbox']").each(function(){
	        		if($(this).attr("checked")){
	        			$(this).attr("checked",false);
	        		};
	        	});
	        	var frm = form;
	        	frm.action = action;
	        	if (subtarget != null) 
	        		frm.target=subtarget;
	        	frm.submit();
	        }
           //修改
	        function updateRow(form, action, target, subtarget) {
	        	var frm = form;
	        	if(checkNum(target) < 1) {
	        		top.layer.msg("必须选择一行！");
	        		return;
	        	} else if (checkNum(target) > 1) {
	        		top.layer.msg("只能选择一行, 不允许多选！");
	        		return;
	        	} 
	        	frm.action = action;
	        	if (subtarget != null) 
	        		frm.target=subtarget;
	        	frm.submit();    
	        }
	        //获取选中目标个数
	        function checkNum(target){
	        	var cknum = 0;
	        	var cks = document.getElementsByName(target);
	        	for(var i=0;i<cks.length;i++){
	        		if(cks[i].checked==true){
	        			cknum++;
	        		}
	        	} 
	        	return cknum;
	        }
	        //删除
	        function deleteRow(form, action, target, subtarget){
	        	var frm = form;
	        	if(checkNum(target)<1) {
	        		top.layer.msg("必须选择一行！");
	        		return false;
	        	}
	        	var index=top.layer.confirm("是否确定删除指定记录？",function(){
	        		frm.action = action;
	        		if (subtarget != null) 
	        			frm.target=subtarget;
	        		frm.submit();
	        		top.layer.close(index);
	        		return true;
	        	});
	        	return false;
	        }
	        //条件查询
	        function onSelect(form, action) {
	        	var frm = form;
	        	frm.submit();
	        }
	        // 重置
	        function onReset(){
	        	$("#employeeRealname").val('');
	        	$("#employeeCard").val('');
	        }
        </script>
	</head>
	<body class="no-skin" onload="load();">
		<div class="main-container" id="main-container">
			<div class="main-content">
				<div class="main-content-inner">
				<form action="<%=request.getContextPath() %>/employee/query.do" method="post" target="self" id="queryForm" name="queryForm">
					<div class="page-content">
						<div class="ace-settings-container" id="ace-settings-container">
						</div>
						<div class="row">
							<div class="col-xs-12">
								<div class="queryTitleDiv widget-box">
									<div class="widget-header">
										<h5 class="widget-title">条件查询</h5>
										<div class="widget-toolbar">
											<a href="#" data-action="collapse">
												<i class="ace-icon fa fa-chevron-up"></i>
											</a>
										</div>
									</div>
									<div class="widget-body">
										<div class="widget-main">
												<table style="width:100%">
													<tr>
													  <td width="13.3%" style="text-align: right;">真实姓名：</td>
													  <td width="20%"><input type="text" id="employeeRealname" name="employeeRealname" value="${employee.employeeRealname }" class="col-xs-10 col-sm-10" /></td>
														  <td width="13.3%" style="text-align: right;">身份证号：</td>
													  <td width="20%"><input type="text" id="employeeCard" name="employeeCard" value="${employee.employeeCard }" class="col-xs-10 col-sm-10" /></td>															  
													</tr>
													<tr>
													  <td colspan="6" align="center">
														   <div class="queryBtnDiv">
																<button class="btn btn-sm btn-info" onclick="onSelect(document.queryForm,'<%=request.getContextPath() %>/employee/query.do');">
																	<i class="fa fa-search align-top bigger-125"></i>
																	查询
																</button>												
																<button class="btn btn-sm btn-info" onclick="onReset();">
																	<i class="fa fa-refresh align-top bigger-125"></i>
																	重置
																</button>	
															</div>														  
													  </td>																
													</tr>																														
												</table>													
										</div>
									</div>
								</div>								    
						     </div>
							 <div class="col-xs-12">
										<div class="centerDiv">
											<div class="btnDiv">
												<sec:authorize access="hasRole('ROLE_sys_gnzy_jcqxgl_rygl_xz')">
													<button class="btn btn-sm btn-info" onclick="insertRow(document.queryForm,'<%=request.getContextPath() %>/employee/input.do','self')">
														<i class="fa fa-plus align-top bigger-125"></i>
														新增
													</button>	
												</sec:authorize>
												<sec:authorize access="hasRole('ROLE_sys_gnzy_jcqxgl_rygl_xg')">										
													<button class="btn btn-sm btn-info" onclick="updateRow(document.queryForm,'<%=request.getContextPath() %>/employee/input.do','ids','self')">
														<i class="fa fa-edit  align-top bigger-125"></i>
														修改
													</button>
												</sec:authorize>	
												<sec:authorize access="hasRole('ROLE_sys_gnzy_jcqxgl_rygl_sc')">											
													<button class="btn btn-sm btn-info" onclick="deleteRow(document.queryForm,'<%=request.getContextPath() %>/employee/delete.do','ids','self')">
														<i class="fa fa-trash-o align-top bigger-125"></i>
														删除
													</button>
												</sec:authorize>
											</div>
										</div>								    
								    </div>
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									<div class="col-xs-12">
										<div class="table-responsive">
										<table id="sample-table-1" class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th class="center">
														<label class="position-relative">
															<input id="checkedAll" type="checkbox" class="ace"/>
															<span class="lbl"></span>
														</label>
													</th>
													<th>真实姓名</th>
													<th>昵称</th>
													<th>身份证号</th>
													<th>家庭住址</th>
												</tr>
											</thead>
											<tbody>
											<c:forEach items="${employeeList}" var="employee">
												<tr>
													<td class="center">
														<label class="position-relative">
															<input id="ids" name="ids" value="${employee.employeeId }" type="checkbox" class="ace" />
															<span class="lbl"></span>
														</label>
													</td>
													<td>${employee.employeeRealname }</td>
													<td>${employee.employeeNickname }</td>
													<td>${employee.employeeCard }</td>
													<td>${employee.employeeAddress }</td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
										</div>
									</div><!-- /.span -->
									<div>
										<jsp:include page="/jsp/frame/page/pagePlug.jsp"/>
									</div>
								</div><!-- /.row -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
				</form>
				</div>
			</div><!-- /.main-content -->
			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div>
	<%@ include file="/include/includeBottom.jsp"%>	
	</body>
</html>
