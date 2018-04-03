<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<c:set var="skin" scope="request" value="gray"></c:set><!-- blue|gray -->
<c:set var="iconPath" scope="request" value="/framework/themes/icons/${skin}/nav/"></c:set>
<script>
    /**
     * 添加内置方法
     * @param element
     * @returns {boolean}
     */
    Array.prototype.inArray = function (element) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == element) {
                return true;
            }
        } return false;
    };
    var contextPath = "${pageContext.request.contextPath}";
    var user_login_roles = "<sec:authentication property='principal.roles_user' htmlEscape="false"/>";//登录用户角色
    //当前登录人名称，当前登录id
    var user_login_nick = "<sec:authentication property='principal.nick' htmlEscape="false"/>";//登录人nick
    var user_login_id = "<sec:authentication property='principal.id' htmlEscape="false"/>";//登录人id
    var user_login_name = "<sec:authentication property='principal.username' htmlEscape="false"/>";//登录人username



    <%--<sec:authorize access="hasRole('ROLE_sys_userinfo_del')">
    &lt;%&ndash;<button class="" onclick="deleteUserInfo('tabledata')">刪 除</button>&ndash;%&gt;
    <div class="btn-cls-common" onclick="deleteUserInfo('tabledata')">删 除</div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_sys_userinfo_bind_group','ROLE_admin')">
    &lt;%&ndash;<button class="" onclick="bindGroup('tabledata')">绑定组</button>&ndash;%&gt;
    <div class="btn-cls-common" onclick="bindGroup('tabledata')">绑定组</div>
    </sec:authorize>--%>





    //是否是系统管理员
    var isROOTManager = false;
    if(user_login_roles != null){
        if(user_login_roles.split(",").inArray("系统管理人员")){
            isROOTManager = true;
        }
    }
    function getJbpmChlidWin(layero){
        var childIframeid = layero.find('iframe')[0]['name'];
        var chidlwin = top.document.getElementById(childIframeid).contentWindow;
        return chidlwin;
    }
</script>
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/default/easyui.css">--%>
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/icon.css">
<%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/demo/demo.css">--%>
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/IconExtension.css">
<script type="text/javascript"  src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
<!-- 弹出界面控制 -->

<!-- 
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/layer/skin/layer.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/framework/layer/layer.js"></script>
 -->
<script id="layerJS" src="<%=request.getContextPath() %>/framework/layer/layer_edit.js" layerSkinName="${skin}"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/cus_${skin}/easyui.css"/>

<script type="text/javascript" src="<%=request.getContextPath() %>/framework/ztree/js/jquery.ztree.all.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/themes/skins/blue/control.css"/>
<!-- form样式 -->
<link href="<%=request.getContextPath() %>/framework/themes/css/framework/form.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="<%=request.getContextPath() %>/framework/themes/js/framework.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/keyevent/keyupordown.js"></script>

<script type="text/javascript">
    Date.prototype.add = function (part, value) {
        value *= 1;
        if (isNaN(value)) {
            value = 0;
        }
        switch (part) {
            case "y":
                this.setFullYear(this.getFullYear() + value);
                break;
            case "m":
                this.setMonth(this.getMonth() + value);
                break;
            case "d":
                this.setDate(this.getDate() + value);
                break;
            case "h":
                this.setHours(this.getHours() + value);
                break;
            case "n":
                this.setMinutes(this.getMinutes() + value);
                break;
            case "s":
                this.setSeconds(this.getSeconds() + value);
                break;
            default:

        }
        return this;
    }
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
    var winref = {};
    jQuery.pindexStorage = function (index, isadd) {
        if (top.pindexlist == 'undefined' || top.pindexlist == '' || top.pindexlist == null) {
            var arrtmp = [];
            top.pindexlist = JSON.stringify(arrtmp);
        }
        var arr = JSON.parse(top.pindexlist);

        if (!isadd) {
            //关闭窗口拿掉最后一个窗口的index
            arr.pop();
        }
        else {
            // index 重复不放进去队列,保持队列的index 唯一, 同时也避免反复弹窗关闭的bug
            if (arr.indexOf(index) < 0) {
                arr.push(index);
            }
        }
        var arrStr = JSON.stringify(arr);
        top.pindexlist = arrStr;
        return arr[arr.length - 1];
    };
    jQuery.openwindow = function (obj) {
        var heighttmp = '450px';
        var widthtmp = '650px';
        //iframe窗
        //var cindex = -1;
        //var layerotmp;
        var options = $.extend({},{
            type: 2,
            shade: 0.3,
            maxmin: true, //开启最大化最小化按钮
            area: [widthtmp, heighttmp]
        },obj) ;
        var endfn = obj.end;
        options.end = function (index) {
            // 关闭窗口移除自身窗口的index
            $.pindexStorage(index, false);
            //关闭窗口的执行方法, 可为null
            if (typeof endfn == 'function') {
                endfn();
            }

        };
        var pindex = top.layer.open(options);
        //关键代码, 将自身窗口产生的index 加入全局存储中.
        $.pindexStorage(pindex, true);
    };
    jQuery.getpwindow = function () {
        var arr = JSON.parse(top.pindexlist);
        //倒数第二个的值就是父对象的index
        var pindex = arr[arr.length - 2];
        return top.window['layui-layer-iframe' + pindex].window;
    };

    mylayer = $.extend({},{},layer) ;
//    try {
//        mylayer = $.extend({},{},parent.mylayer) ;
//    } catch (e) {
//        mylayer = $.extend({},{},layer) ;
//    }
//    var myopen = mylayer.open
    mylayer.open = function(obj){
        $.openwindow(obj)
//        top.addWin(window);
//        open(obj);
    };
    //防止按钮自主提交  add by lilj
    $(function() {
        $("button").click(function(event){
            event.preventDefault();
        });
    })
</script>

<style type="text/css">
    /*wcg ：以下是流程审批进度条专用的style*/
    .clearProgressBar{
        width:0%;
        height:0%;
        top: 0%;/*将进度条固定在页面大概中间的位置*/
        left: 0%;
        z-index: 0 ;
        opacity:0 ;
    }

    .addProgressbarX{
        width:50%;
        height:50%;
        color:red;
        position:fixed; /*//设定进度条跟随屏幕滚动*/
        top: 50%;/*将进度条固定在页面大概中间的位置*/
        left: 30%;
        z-index:9999999999; /*//提高进度条的优先层级，避免被其他层遮挡*/
        opacity: 1}    /*//opacity 透明度　　　　　值为0-1之间的小数*/

    .addProgressbarMyPanel{
        background:#000;
        width:100%;
        height:100%;
        position:fixed; /*//设定进度条跟随屏幕滚动*/
        top:0; /*//将进度条固定在页面顶部*/
        left: 0%;
        filter:blur(1px);  /*模糊效果*/
        z-index:9999999998; /*//提高进度条的优先层级，避免被其他层遮挡*/
        opacity: 0.4}
</style>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Expires", "0");

%>
