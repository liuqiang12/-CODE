<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <%@ include file="/globalstatic/include.jsp %>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta charset="UTF-8" />
        <title>通用后台管理系统</title>
        <link rel="stylesheet" href="<%=request.getContextPath() %>/framework/bootstrap/css/ace.onpage-help.css" />
        <script src="<%=request.getContextPath() %>/framework/jqueryui/cookie/jquery.cookie.js"></script>
		<script type="text/javascript">
// 			$(function(){
// 			 	if("${message}"=="loginError"){
// 			 		top.layer.alert("<span style='color:red'>用户名或密码错误！</span>"); 
// 			 	}	
			 	
// 			 	if("${message}"=="userError"){
// 			 		top.layer.alert("<span style='color:red'>该用户已被停用！</span>"); 
// 			 	}	
			 	
// 				$.validationEngine.addMethod({
// 		        	 "userNameRequired":{
// 		        		 "func":function(e,data){
// 		        			 var caller=$(data.caller);
// 		        			 if(caller.val()==""){
// 								 data.back(true);
// 							 }
// 			        	  },"alertText":"请输入用户名！"
// 		        	 },
// 		        	 "passwordRequired":{
// 		        		 "func":function(e,data){
// 		        			 var caller=$(data.caller);
// 		        			 if(caller.val()==""){
// 								 data.back(true);
// 							 }			        		 
// 			        	  },"alertText":"请输入用户密码！"
// 		        	 }
// 		         });	
// 			});
		
		
			jQuery(function($) {
				 $(document).on('click', '.toolbar a[data-target]', function(e) {
					e.preventDefault();
					var target = $(this).data('target');
					$('.widget-box.visible').removeClass('visible');//hide others
					$(target).addClass('visible');//show target
				 });
			});
			
			
			//用于改变背景
			jQuery(function($) {
				 $('#btn-login-dark').on('click', function(e) {
					 $.cookie('backgroundId', '#btn-login-dark',{ expires: 7 });
					 changeBackground(1,e);
				 });
				 $('#btn-login-light').on('click', function(e) {
					 $.cookie('backgroundId', '#btn-login-light',{ expires: 7 });
					 changeBackground(2,e);
				 });
				 $('#btn-login-blur').on('click', function(e) {
					 $.cookie('backgroundId', '#btn-login-blur',{ expires: 7 });
					 changeBackground(3,e);
				 });
			});
			
			
			function changeBackground(num,e){
				if(num==1){
					$('body').attr('class', 'login-layout');
					$('#id-text2').attr('class', 'white');
					$('#id-company-text').attr('class', 'blue');
				}else if(num==2){
					$('body').attr('class', 'login-layout light-login');
					$('#id-text2').attr('class', 'grey');
					$('#id-company-text').attr('class', 'blue');
				}else{
					$('body').attr('class', 'login-layout blur-login');
					$('#id-text2').attr('class', 'white');
					$('#id-company-text').attr('class', 'light-blue');
				}
				e.preventDefault();					
			}
			
		    $(function(){//不要删除name=sessionInvalid的form；用于session失效时跳转到top
				 if(window!=top){
					 document.sessionInvalid.submit();
				 }
		         var backgroundId=$.cookie('backgroundId');
		         if(backgroundId){
				 	$(backgroundId).click();
		         }
		         
		         $(".fa-lock").on({"mousedown":function(){
		        	 $("#userPassword").attr("type","text");
		        	 $(this).addClass("fa-unlock");
		        	 $(this).removeClass("fa-lock");
		         },"mouseup":function(){
		        	 $("#userPassword").attr("type","password");
		        	 $(this).addClass("fa-lock");
		        	 $(this).removeClass("fa-unlock");
		         }})
		    });	
		    
		    //登陆
		    function login() {
	        	var access=true;
        		access=$("#inputForm").validationEngine({returnIsValid:true});//false:验证失败，true:验证成功
        		if(access!=true) {return false;}				
	        	document.inputForm.submit();
	        }
	        
	        //点击Enter键登陆
	        $(document).keydown(function(event){
	            if (event.keyCode == 13){
	            	top.layer.closeAll();
	            	setTimeout(login,100);
	            }
	        });
		</script>        
    </head>
    <body class="login-layout">
        <form  name="sessionInvalid" action="<%=request.getContextPath() %>/login.jsp"  target="_top"></form>
		<div class="main-container">
			<div class="main-content">
				<div class="row" style="margin-top: 100px">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<img height="55px"  src="<%=request.getContextPath() %>/assets/images/sysframe/logo2.png">
									<span style="margin-left: 3px;color:#5D9CD0;">基础权限应用2222</span>
								</h1>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												请输入您的信息
											</h4>

											<div class="space-6"></div>

											<form action="<%=request.getContextPath() %>/user/login.do" method="post"  id="inputForm" name="inputForm">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="userName" name="userName" type="text" class="validate[required,userNameRequired,length[1,150]] col-xs-12 col-sm-12" placeholder="用户名" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input id="userPassword" name="userPassword" type="password" class="validate[passwordRequired,length[1,150]] col-xs-12 col-sm-12" placeholder="用户密码" />
															<i class="ace-icon fa fa-lock cursor-pointer" ></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl"> 记住密码</span>
														</label>

														<button type="button" class="width-35 pull-right btn btn-sm btn-primary" onclick="login();">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="#" data-target="#forgot-box" class="forgot-password-link">
													<i class="ace-icon fa fa-arrow-left"></i>
													忘记密码
												</a>
											</div>

											<div>
												<a href="#" data-target="#signup-box" class="user-signup-link">
													注册
													<i class="ace-icon fa fa-arrow-right"></i>
												</a>
											</div>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="ace-icon fa fa-key"></i>
												Retrieve Password
											</h4>

											<div class="space-6"></div>
											<p>
												Enter your email and to receive instructions
											</p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="ace-icon fa fa-lightbulb-o"></i>
															<span class="bigger-110">Send Me!</span>
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												Back to login
												<i class="ace-icon fa fa-arrow-right"></i>
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.forgot-box -->

								<div id="signup-box" class="signup-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="ace-icon fa fa-users blue"></i>
												New User Registration
											</h4>

											<div class="space-6"></div>
											<p> Enter your details to begin: </p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Repeat password" />
															<i class="ace-icon fa fa-retweet"></i>
														</span>
													</label>

													<label class="block">
														<input type="checkbox" class="ace" />
														<span class="lbl">
															I accept the
															<a href="#">User Agreement</a>
														</span>
													</label>

													<div class="space-24"></div>

													<div class="clearfix">
														<button type="reset" class="width-30 pull-left btn btn-sm">
															<i class="ace-icon fa fa-refresh"></i>
															<span class="bigger-110">Reset</span>
														</button>

														<button type="button" class="width-65 pull-right btn btn-sm btn-success">
															<span class="bigger-110">Register</span>

															<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												<i class="ace-icon fa fa-arrow-left"></i>
												Back to login
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.signup-box -->
							</div><!-- /.position-relative -->

							<div class="navbar-fixed-top align-right">
								<br />
								&nbsp;
								<a id="btn-login-dark" href="#">暗黑</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-blur" href="#">渐变</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-light" href="#">浅色</a>
								&nbsp; &nbsp; &nbsp;
							</div>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->
		<%@ include file="/globalstatic/includeBottom.jsp"%>
	</body>
</html>