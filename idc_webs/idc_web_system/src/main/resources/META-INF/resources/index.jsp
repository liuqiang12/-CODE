<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="/globalstatic/include.jsp"></jsp:include>
		<meta charset="utf-8" />
		<title>通用后台管理</title>
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/common.css" />
		<script type="text/javascript">
		//菜单滚动条
		function sidebar_fixed(){
			var n = document.getElementById("sidebar");
			ace.addClass(n, "sidebar-fixed");
// 			ace.settings.set("sidebar", "fixed");
			window.jQuery && jQuery(document).trigger("settings.ace", ["sidebar_fixed", true]);
		}
		$(function(){
			$('#menu').sidebarMenu({
				data: [{
			          id: '1',
			          text: '系统设置',
			          icon: 'icon-cog',
			          url: '',
			          menus: [{
			            id: '11',
			            text: '机构',
			            icon: 'icon-glass',
			            url: '/CodeType/Index'
				        },{
				            id: '12',
				            text: '部门',
				            icon: 'icon-glass',
				            url: '/CodeType/Index'
				        },{
				            id: '13',
				            text: '用户',
				            icon: 'icon-glass',
				            url: '/SysUserinfoAction_query.do'
				        },{
				            id: '131',
				            text: '分页1[default]',
				            icon: 'icon-glass',
				            url: '/SysUserinfoAction_querypage1.do'
				        },{
				            id: '14',
				            text: '角色',
				            icon: 'icon-glass',
				            url: '/CodeType/Index'
				        },{
				            id: '15',
				            text: '菜单',
				            icon: 'icon-glass',
				            url: '/CodeType/Index'
				        },{
				            id: '16',
				            text: '权限',
				            icon: 'icon-glass',
				            url: '/CodeType/Index'
				        }]
			      	},{
				          id: '2',
				          text: '系统设置',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '3',
				          text: '系统设置',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '4',
				          text: '系统设置',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '5',
				          text: '系统设置',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '6',
				          text: '系统设置',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '7',
				          text: '系统设置',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '8',
				          text: '系统设置',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '9',
				          text: '系统设置',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '11',
				          text: '系统设置',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '21',
				          text: '系统设置3',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '12',
				          text: '系统设置2',
				          icon: 'icon-cog',
				          url: '',
			      	},{
				          id: '13',
				          text: '系统设置1',
				          icon: 'icon-cog',
				          url: '',
			      	}]
				});
			$.fn.sidebarMenu.refresh=true;//是否刷新，默认为true
			$.sidebarMenu.expand('#menu>li:eq(0)');//默认展开第一个菜单
			sidebar_fixed();//给菜单加滚动条，必须在菜单生成完成后才能调用此方法；
		});
		</script>
		
	</head>
	
	<body class="no-skin">
		<jsp:include page="/head.jsp"></jsp:include>
		<div class="main-container" id="main-container">
<!-- 			<script type="text/javascript"> -->
<!-- 				try{ace.settings.check('main-container' , 'fixed')}catch(e){} -->
<!-- 			</script> -->

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar responsive" id="sidebar">
					<div class="sidebar-shortcuts" id="sidebar-shortcuts">
						<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
							<button class="btn btn-success">
								<i class="icon-signal"></i>
							</button>

							<button class="btn btn-info">
								<i class="icon-pencil"></i>
							</button>

							<button class="btn btn-warning">
								<i class="icon-group"></i>
							</button>

							<button class="btn btn-danger">
								<i class="icon-cogs"></i>
							</button>
						</div>

						<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
							<span class="btn btn-success"></span>

							<span class="btn btn-info"></span>

							<span class="btn btn-warning"></span>

							<span class="btn btn-danger"></span>
						</div>
					</div><!-- #sidebar-shortcuts -->
					<ul class="nav nav-list" id="menu" ></ul>
					<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>
					<script type="text/javascript">
						try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					</script>
				</div>

				<div class="main-content">
						<div class="page-content">
						<jsp:include page="/settingContainer.jsp"></jsp:include>
				         <div class="row">
				           <div class="col-xs-12" style="padding-left:1px;">
				             <ul class="nav nav-tabs tabContextMenuUl" role="tablist">
				               <li class="active" id="tab_tab_00000000"><a href="#Index"  role="tab" data-toggle="tab" >首页</a></li>
				             </ul>
				             <div class="tab-content" style="padding:0">
				               <div role="tabpanel" class="tab-pane active" id="Index">
									<iframe src="<%=request.getContextPath() %>/welcome.jsp" width="100%" height="500"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes" id="frmright" name="frmright" ></iframe>			               
				               </div>
				             </div>
				           </div>
				         </div>
				       </div><!-- /.page-content -->
				</div><!-- /.main-content -->
				<!-- 设置主题 -->
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
		<jsp:include page="/globalstatic/includeBottom.jsp"></jsp:include>
</body>
</html>

