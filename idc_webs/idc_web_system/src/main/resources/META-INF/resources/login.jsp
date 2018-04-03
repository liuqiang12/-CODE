<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<title>中国（西部）云计算中心</title>
<%--<script type="text/javascript"--%>
        <%--src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/jquery.min.js"></script>--%>
<%--<link href="<%=request.getContextPath() %>/framework/themes/css/import_login_basic.css" rel="stylesheet"--%>
      <%--type="text/css"/>--%>
<%--<script type="text/javascript" src="<%=request.getContextPath() %>/framework/layer/layer.js"></script>--%>
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/layer/skin/layer.css">--%>
<link href="<%=request.getContextPath() %>/framework/themes/skins/blue/import_login_skin.css" id="skin"
      themeColor="blue" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" media="screen and (max-width:960px)" href="<%=request.getContextPath() %>/framework/themes/skins/blue/home.css">
<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
<script type="text/javascript">
    document.onkeydown=enter_fun;
    if(top.window!=window){
        top.location = location
    }
    function enter_fun(e){
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            toPageMain();
            return false;
        }
        return true;
    }
    function toPageMain() {
        var username = $("#username").val();
        var pwd = $("#password").val();
        var validateCode = $("#validateCode").val();
        if (username == "" || pwd == "") {
            top.layer.msg("请输入用户名或密码")
//            alert("请输入用户名或密码");
            return;
        }
        if(validateCode==""){
            top.layer.msg("请输入验证码")
            return ;
        }
        //直接请求后台
        $("#loginForm").submit();
    }
    function formReset() {
        document.getElementById("loginForm").reset()
    }

    function processLowerIENavigate()
    {
        var isIE = document.all ? 1 : 0;
        if (isIE == 1)
        {
            if(navigator.userAgent.indexOf("MSIE7.0") > 0 || navigator.userAgent.indexOf("MSIE 8.0") > 0)
            {
                //  var doc=document;
                var link=document_createElement_x_x_x("link");
                link.setAttribute("rel", "stylesheet");
                link.setAttribute("type", "text/css");
                link.setAttribute("id", "size-stylesheet");
                link.setAttribute("href", "");

                var heads = document.getElementsByTagName_r("head");
                if(heads.length)
                    heads[0].a(link);
                else
                    document.documentElement.a(link);
            }
        }
    }
    var lowerIE8 = processLowerIENavigate();

    function adjustStyle(width) {
        width = parseInt(width);
        if (width < 902) {
            //alert("<900");
            //alert(width);
            $("#size-stylesheet").attr("href", "navigateLowerIE8.css");
        } else {
            // alert(">900");
            //alert(width);
            $("#size-stylesheet").attr("href", "");
        }
    }

    $(function() {
        adjustStyle($(this).width());
        $(window).resize(function() {
            adjustStyle($(this).width());
        });
        var showCheckCode = "${showCheckCode}";
        var mssge = "${SECURITY_LOGIN_EXCEPTION}";
        if(showCheckCode!=null && showCheckCode == '1'){
            layer.msg(mssge,{icon:2,anim:6});
        }
    });
</script>
<style>
    .bottom_cls{
        text-align: center;
        font-size: 15px;
        color: mediumvioletred;
        /*position: absolute;*/
        width: 100%;
        bottom: 94px;
    }
    @media screen and (min-width: 1280px) {
    }
</style>
</head>

<body>
<div class="header" > <img src="<%=request.getContextPath() %>/framework/themes/skins/blue/images/bg1.png" width="100%"/> </div>
<div class="main">

    <!--logo和登录框-->

    <div class="login wid800">
        <div class="pos">
            <div class="logo fl m-t-50"><img src="<%=request.getContextPath() %>/framework/themes/skins/blue/images/logo.png"/></div>
            <form action="<%=request.getContextPath() %>/login.do" method="post" id="loginForm">
                <div class="kuang fr" style=" ">
                    <h3>登录</h3>
                    <div class="m-t-20 login_k">
                        <div class="name"><span class="fl">用户名</span>
                            <input type="text" name="username" id="username"/>
                        </div>
                        <div class="mima"><span class="fl">密码</span>
                            <input type="password" name="password" id="password"/>
                        </div>
                        <div class="yzm"> <span class="fl">验证码</span>
                            <input class="fl " type="text" name="validateCode" id="validateCode"/>
                            <img class="fl m-t-15"  src="<%=request.getContextPath() %>/framework/themes/skins/blue/images/btn.jpg"/>
                            <a href="javascript:void(0)"><img width="70px" height="36px" src="<%=request.getContextPath() %>/captcha.do" onclick="this.src='<%=request.getContextPath() %>/captcha.do?t='+new Date()*1" class="fr m-t-10 m-r-5"/></a> </div>
                    </div>
                    <div style="clear: both;"></div>
                    <div class="memory">
                        <input class="fl" type="checkbox"/>
                        <span class="f-z-12 fl">记住我的登陆状态</span> <a class="f-z-12 fr" href="#">忘记密码?</a> </div>
                    <div style="clear: both;"></div>
                    <div class="sub">
                        <input type="submit" value="立即登陆"/>
                    </div>
                    <div><img src="<%=request.getContextPath() %>/framework/themes/skins/blue/images/yy.png"/></div>
                </div>
            </form>
        </div>
    </div>
    <div style="clear: both; height:20px;"></div>
    <div class="login_t"><b>·</b>&nbsp;&nbsp;中国西部云计算中心</div>
    <div class="login_list m-t-10"> <a href="#" class="last">西部最强性能云计算资源池</a> <a href="#" class="last">西部旗舰承载的托管数据中心</a> <a href="#" class="last">西部最大规模综合信息服务中心</a> <a href="#" class="last">中国创新最多的省级政务云中心</a> <a href="#" >中国最佳备选的行业灾备中心</a> </div>
    <%--<div class="bottom_cls">--%>
          <%--有问题或建议，请联系：18349212446 18908199811--%>
        <%--问题处理QQ群：628063747--%>
    <%--</div>--%>
    </div>
</div>
<div style="clear: both;text-align: center"></div>
<div class="footer">
    有问题或建议，请联系：18349212446 18908199811 问题处理QQ群：628063747
    <br>支持Chrome60+,Firefox55+,IE9+
    <%--<div style="clear: both;text-align: center">支持IE9+,Chrome60+,Firefox55+</div>--%>
</div>

<%--<div class="header">

</div>

<div class="main">

    <!--logo和登录框-->

    <div class="login wid800">

        <div class="pos">
            <div class="logo fl m-t-50"><img src="<%=request.getContextPath() %>/framework/themes/skins/blue/images/logo.png"/></div>

            <form action="<%=request.getContextPath() %>/login.do" method="post" id="loginForm">
                <div class="kuang fr" style=" ">
                    <h3>登录</h3>
                    <div class="m-t-20 login_k">
                        <div class="name"><span class="fl">用户名</span><input type="text" name="username" id="username"/></div>
                        <div class="mima"><span class="fl">密码</span><input type="password" name="password" id="password"/></div>
                        <div class="yzm">
                            <span class="fl">验证码</span><input class="fl " type="text" name="validateCode" id="validateCode"/>
                            <img class="fl m-t-15" src="<%=request.getContextPath() %>/framework/themes/skins/blue/images/btn.jpg"/><a href="javascript:void(0)" >
                            <img src="<%=request.getContextPath() %>/captcha.do" width="70px" height="36px" class="fr m-t-10 m-r-5" onclick="this.src='<%=request.getContextPath() %>/captcha.do?t='+new Date()*1"/></a>
                        </div>
                    </div>

                    <div style="clear: both;"></div>

                    <div class="memory">
                        <input class="fl" type="checkbox"/><span class="f-z-12 fl">记住我的登陆状态</span>
                        <a class="f-z-12 fr" href="#">忘记密码?</a>
                    </div>

                    <div style="clear: both;"></div>

                    <div class="sub"><input type="submit" value="立即登陆"/></div>

                </div>
            </form>

        </div>
    </div>

    <div style="clear: both; height:50px;"></div>

    <div class="login_t">·&nbsp;&nbsp;中国西部和计算机</div>

    <div class="login_list m-t-30">
        <a href="#" class="last">西部最强性能云计算资源池</a>
        <a href="#" class="last">西部旗舰承载的托管数据中心</a>
        <a href="#" class="last">西部最大规模综合信息服务中心</a>
        <a href="#" class="last">中国创新最多的省级政务云中心</a>
        <a href="#" >中国最佳备选的行业灾备中心</a>
    </div>


</div>
<div style="clear: both;"></div>

<div class="footer">版权所以xxx公司</div>--%>
</body>
</html>
