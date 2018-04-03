<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/v1.3.2/include.jsp"></jsp:include>
    <link href="<%=request.getContextPath() %>/framework/themes/css/select.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/framework/hplusV4.1.0/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="<%=request.getContextPath() %>/framework/hplusV4.1.0/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <%--<link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">--%>
    <script type="text/javascript" language="JavaScript">
        var baseURL = "<%=request.getContextPath() %>";
        function getBasePath(){
            return baseURL;
        }
        parent.window.onresize=function(){
            ThisiframeAuto();
        }
        function ThisiframeAuto(){
            var h=($(top).height()||0);
            var w=($(top).width()||0);
//            $(window).width(w*0.7);
//            $(window).height(h*0.7)
            $(document.body).height(h*0.7);
            $(document.body).width(w*0.7);
            var index = parent.layer.getFrameIndex(window.name);
            layer.iframeAuto(index);// - 指定iframe层自适应
        }

    </script>
    <style>
        .ztree li {
            background-color: white;
        }
    </style>
</head>

<body class="gray-bg page-content" style="overflow-y: auto">
<div class="wrapper wrapper-content animated fadeInRight ">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>用户信息</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="signupForm">
                        <!--隐藏字段-->
                        <%--<c:if test="${ not empty user}">${user.username}</c:if>--%>
                        <%--<input type="hidden" name="accountNonLocked" value="<c:if test="${!empty user.accountNonLocked}">${user.accountNonLocked}</c:if>">--%>
                        <%--<input type="hidden" name="accountNonLocked" value="<c:if test="${!empty user.enabled}">${user.enabled}</c:if>">--%>
                        <%--<input type="hidden" name="credentialsNonExpired" value="${(empty user.credentialsNonExpired)? '': user.credentialsNonExpired}  ">--%>
                        <%--<input type="hidden" name="accountNonExpired" value="${user.accountNonExpired}">--%>
                        <%--<input type="hidden" name="isDepartLeading" value="${(empty user.isDepartLeading)? 'false': user.isDepartLeading}">--%>
                        <%--<input type="hidden" name="enabled" value="${(empty user.enabled)? 'false': user.enabled}">--%>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户名：</label>
                            <div class="col-sm-8">
                                <input id="username" name="username" class="form-control" type="text" aria-required="true" aria-invalid="true" class="error" value="${user.username}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">用户昵称：</label>
                            <div class="col-sm-8">
                                <input id="nick" name="nick" class="form-control" type="text" aria-required="true" aria-invalid="false" class="valid" value="${user.nick}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-3 control-label">密码：</label>
                            <div class="col-sm-8">
                                <input id="password" name="password" class="form-control" type="password" value="">
                            </div>
                        </div>
                        <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label">确认密码：</label>--%>
                        <%--<div class="col-sm-8">--%>
                        <%--<input id="confirm_password" name="confirm_password" class="form-control" type="password">--%>
                        <%--<span class="help-block m-b-none">请再次输入您的密码</span>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">区域：</label>
                            <div class="col-sm-8">
                                <input id="region" class="form-control" type="" readonly value="${region.name}">
                                <input id="regionval" class="form-control"  name="region" type="hidden" value="${user.region}" >
                                <span class="help-block m-b-none"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">性别：</label>
                            <div class="col-sm-8">
                                <div class="radio i-checks">
                                    <label>  <input type="radio" <c:if test="${user.sex ne '女'}">checked=""</c:if> value="男" name="sex"> <i></i> 男</label>
                                    <label>  <input type="radio" <c:if test="${user.sex eq '女'}">checked=""</c:if> value="女" name="sex"> <i></i> 女</label>
                                </div>
                            </div>
                        </div>
                        <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label">启用：</label>--%>
                        <%--<div class="col-sm-8">--%>
                        <%--<div class="radio i-checks">--%>
                        <%--<label>  <input type="radio" checked="" value="1" name="enabled"> <i></i> 激活</label>--%>
                        <%--<label>  <input type="radio"  value="0" name="enabled"> <i></i> 未激活</label>--%>
                        <%--<label>  <input type="radio"  value="2" name="enabled"> <i></i> 锁定</label>--%>

                        <%--<input type="radio"  value="2" name="enabled">--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">年龄：</label>
                            <div class="col-sm-8">
                                <input id="age" name="age" class="form-control" type="number" value="${user.age}">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-3">
                                <button id="submit" class="btn btn-primary" type="submit">提交</button>
                                <a class="btn btn-primary" onclick="closeWin()">取消</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
<jsp:include page="/globalstatic/v1.3.2/includeBottom.jsp"></jsp:include>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/framework/themes/js/select-ui.min.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath() %>/framework/hplusV4.1.0/js/plugins/suggest/bootstrap-suggest.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/hplusV4.1.0/js/plugins/validate/jquery.validate.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/hplusV4.1.0/js/plugins/validate/messages_zh.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/hplusV4.1.0/js/plugins/iCheck/icheck.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree.all-3.5.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree-combotree.js"></script>
<script src="<%=request.getContextPath() %>/framework/jeasyui/plugins/jquery.form.js"></script>
<%--<script src="<%=request.getContextPath() %>/framework/hplusV4.1.0/js/demo/form-validate-demo.min.js"></script>--%>
<%--<script src="js/plugins/validate/jquery.validate.min.js"></script>--%>
<%--<script src="js/plugins/validate/messages_zh.min.js"></script>--%>
<%--<script src="js/demo/form-validate-demo.min.js"></script>--%>
<!--  <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script> -->
<script>

    function initComboTree(){
//        var znoe =[
//            { name:"全国", open:true,id:"1",
//                children: [
//                    { name:"四川", open:true,id:"2",
//                        children: [
//                            { name:"成都", open:true,id:"3",
//                                children: [
//                                    { name:"武侯区", open:true ,id:"4"},
//                                    { name:"金牛区", open:true ,id:"5"}
//                                ]
//                            },
//                            { name:"绵阳" ,id:"6"},
//                            { name:"自贡",id:"7",},
//                            { name:"攀枝花",id:"8",}
//                        ]}
//                ]
//            }
//        ];
        var setting = {
            value_targer: $("#regionval"),
            data: {
                simpleData: {
                    enable: true,
                    pIdKey:'parentId'
                }
            },
            async: {
                enable: true,
                url: getBasePath() + "/sysregion/tree.do"
            }
        }
        new $.fn.zTree.ComboTree().initTreeCombo($("#region"),setting,[]);
    }
    $(document).ready(function () {
        initComboTree();

        $("#submit").click(function(){
            var s = $("#signupForm").submit();
        });
        $(".i-checks").iCheck({checkboxClass: "icheckbox_square-green", radioClass: "iradio_square-green"});
        $.validator.setDefaults({
            highlight: function (e) {
                $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
            }, success: function (e) {
                e.closest(".form-group").removeClass("has-error").addClass("has-success")
            }, errorElement: "span", errorPlacement: function (e, r) {
                e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
            }, errorClass: "help-block m-b-none", validClass: "help-block m-b-none"
        })})
    function closeWin(){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    }
    $(document).ready(function () {
        var e = "<i class='fa fa-times-circle'></i> ";
        var options={
            dataType: "json",
            success: function (data) {
            },
            error : function(){
                alert('保存错误！');
            }
        }
        $("#signupForm").validate({
            submitHandler: function (form) {
                //return true;
                //$(form).submit();
//                $(form).ajaxSubmit(options);
                $.post(baseURL+"/userinfo/save.do",$(form).serialize(),function(data){
                    if(data.status){
                        layer.msg('保存成功');
                        parent.document.getElementById("maincontent").contentWindow.refreshTable()
                        closeWin();
                    }else{
                        layer.msg(data.msg);
                    }

                });
            },
            rules: {
                nick: {required: !0, minlength: 2},
                username: {required: !0, minlength: 2},
                age: {required: !0, number: true,max:110,min:1},
                password: {required: !0, minlength: 5}

            },
            messages: {
                nick: e + "请输入您的昵称",
                age: {required:e + "请输入您的年龄",nubmer:e+'输入数字'},
                username: {required: e + "请输入您的用户名", minlength: e + "用户名必须两个字符以上"},
                password: {required: e + "请输入您的密码", minlength: e + "密码必须5个字符以上"}
                // confirm_password: {required: e + "请再次输入密码", minlength: e + "密码必须5个字符以上", equalTo: e + "两次输入的密码不一致"}
            }
        })

    });
</script>
</body>
</html>