<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
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
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/easyui/themes/icon.css">
    <%--<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/layout/ui7/css/common.css">--%>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/framework/layout/ui7/css/menu.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/layout/ui7/css/style.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/skin/layer.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/IconExtension.css">
    <title>云南移动能耗管理平台</title>
    <script type="text/javascript" language="JavaScript">
        $(document).ready(function(){
            $(".nav li a").click(function(){
//                $(".nav li a").removeClass("on");
//                $(this).addClass("on");
                var uri = $(this).attr("url");
                if(uri=='#'){
                    return ;
                }
                if(uri.indexOf("?")>0){
                    uri += "&_" + new Date();
                }else{
                    uri += "?_=" + new Date();
                }
                if(uri.indexOf("/")==0){
                    // uri = "<%=request.getContextPath() %>"+uri;
                    uri = "<%=request.getContextPath() %>/redirect.do?fromindex=fromindex&url="+encodeURI(uri);

                }else{
                    uri="http://"+uri;
                    $("#maincontent").attr("src",uri);
                }
                $("#maincontent").attr("src",uri);

            })
        });
        function topfun(){
            if( window.top != window.self ){
                window.top.location.href=window.self.location.href;
            }
        }
    </script>
    <style>

    </style>
</head>
<body onload="topfun()">
<%--<div >--%>
<div class="nav" style="width: 100%;;z-index: 1110003;position: absolute;top:78px">
    <nav>
        <ul class="clearfix">
            <jsp:include page="menu.jsp"></jsp:include>
        </ul>
    </nav>
</div>
<%--</div>--%>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north',height:108,border:false" >
        <div class="header-bg" id="top">
            <div class="main-logo"><img src="<%=request.getContextPath() %>/framework/layout/ui7/img/main-logo.png" width="443" height="64" alt=""></div>
            <div class="topnav">
                <div class="topnav-l"></div>
                <div class="topnav-c">当前登陆用户:<sec:authentication property='principal.username' />|<a href="<%=request.getContextPath() %>/logout.do">退出系统</a></div>
                <div class="topnav-r"></div>
            </div>
            <div class="highlight"></div>
        </div>
        <%--<div class ="nav">--%>
        <%--<nav>--%>
        <%--<ul>--%>
        <%--<li>--%>
        <%--<a id="1_menu"--%>
        <%--class="easyui-menubutton" menu="#1_submenu" data-options="hideOnUnhover:false"--%>
        <%--href="#">--%>
        <%--绯荤粺绠＄悊--%>
        <%--</a>--%>
        <%--</li>--%>
        <%--<div id="1_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="464#1#/sysuser.do"--%>

        <%--menu="#464_submenu">鐢ㄦ埛绠＄悊--%>

        <%--</div>--%>


        <%--<div id="465#1#/sysgroup.do"--%>

        <%--menu="#465_submenu">鐢ㄦ埛鍒嗙粍绠＄悊--%>

        <%--</div>--%>


        <%--<div id="463#1#/sysrole.do"--%>

        <%--menu="#463_submenu">绠＄悊瑙掕壊瀹氫箟--%>

        <%--</div>--%>


        <%--<div id="457#1##"--%>

        <%--menu="#457_submenu">鍖哄煙绠＄悊--%>

        <%--<div id="457_submenu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="462#457#/netdevicerealm.do"--%>

        <%--menu="#462_submenu">璁惧鍩熺鐞�--%>

        <%--</div>--%>


        <%--<div id="461#457#/areaEdit.do"--%>

        <%--menu="#461_submenu">琛屾斂鍖哄煙绠＄悊--%>

        <%--</div>--%>


        <%--</div>--%>

        <%--</div>--%>


        <%--<div id="2#1##"--%>

        <%--menu="#2_submenu">绯荤粺璁剧疆--%>

        <%--<div id="2_submenu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="583#2##"--%>

        <%--menu="#583_submenu">涓氬姟妯″瀷--%>

        <%--</div>--%>


        <%--<div id="582#2##"--%>

        <%--menu="#582_submenu">绯荤粺鍙傛暟--%>

        <%--</div>--%>


        <%--<div id="3#2#/sysmenu.do"--%>

        <%--menu="#3_submenu">鑿滃崟淇℃伅閰嶇疆--%>

        <%--</div>--%>


        <%--</div>--%>

        <%--</div>--%>


        <%--<div id="468#1#/taskManagerView.do?action=listTask&taskType=1"--%>

        <%--menu="#468_submenu">浠诲姟璋冨害--%>

        <%--</div>--%>


        <%--</div>--%>


        <%--<li >--%>
        <%--<a id="515_menu"--%>


        <%--class="easyui-menubutton" menu="#515_submenu"--%>


        <%--href="#">--%>

        <%--璧勬簮绠＄悊--%>
        <%--</a>--%>
        <%--</li>--%>


        <%--<div id="515_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="516#515##"--%>

        <%--menu="#516_submenu">瀛橀噺绠＄悊--%>

        <%--<div id="516_submenu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="586#516#/Equipment.do?action=toEquipmentJsp"--%>

        <%--menu="#586_submenu">缃戠粶璁惧绠＄悊--%>

        <%--</div>--%>


        <%--<div id="480#516#/views/ICS/vpnNetLineInfo/netLineInfo.jsp"--%>

        <%--menu="#480_submenu">閾捐矾淇℃伅绠＄悊--%>

        <%--</div>--%>


        <%--<div id="588#516#/netbackupline.do"--%>

        <%--menu="#588_submenu">鍙屽钩闈俊鎭鐞�--%>

        <%--</div>--%>


        <%--<div id="591#516#/vpnmpls.do?action=getPortWithVpnBus"--%>

        <%--menu="#591_submenu">涓氬姟缁戝畾瑙勫垯--%>

        <%--</div>--%>


        <%--</div>--%>

        <%--</div>--%>


        <%--<div id="549#515##"--%>

        <%--menu="#549_submenu">鏁版嵁缁存姢--%>

        <%--<div id="549_submenu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="518#549#/group.do"--%>

        <%--menu="#518_submenu">璁惧鍒嗙粍--%>

        <%--</div>--%>


        <%--<div id="550#549#/NetTelnetflow.do"--%>

        <%--menu="#550_submenu">鐧诲綍娴佺▼淇℃伅--%>

        <%--</div>--%>


        <%--</div>--%>

        <%--</div>--%>


        <%--<div id="519#515##"--%>

        <%--menu="#519_submenu">璁惧閲囬泦--%>

        <%--<div id="519_submenu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="520#519#/DsyNetdsyplan_res.do?action=listDsyResdeviceinfo"--%>

        <%--menu="#520_submenu">璧勬簮閲囬泦--%>

        <%--</div>--%>


        <%--</div>--%>

        <%--</div>--%>


        <%--</div>--%>


        <%--<li >--%>
        <%--<a id="511_menu"--%>


        <%--class="easyui-menubutton" menu="#511_submenu"--%>


        <%--href="#">--%>

        <%--鍛婅绠＄悊--%>
        <%--</a>--%>
        <%--</li>--%>


        <%--<div id="511_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="512#511#/vpnalarm.do"--%>

        <%--menu="#512_submenu">鍛婅浜嬩欢--%>

        <%--</div>--%>


        <%--<div id="541#511#/vpnalarm.do?action=getalarmlevel"--%>

        <%--menu="#541_submenu">鍛婅闃��--%>

        <%--</div>--%>


        <%--</div>--%>


        <%--<li >--%>
        <%--<a id="553_menu"--%>


        <%--class="easyui-menubutton" menu="#553_submenu"--%>


        <%--href="#">--%>

        <%--涓氬姟瑙嗗浘--%>
        <%--</a>--%>
        <%--</li>--%>


        <%--<div id="553_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="555#553##"--%>

        <%--menu="#555_submenu">涓氬姟鎷撴墤--%>

        <%--<div id="555_submenu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="556#555#/views/flash/Topo.jsp"--%>

        <%--menu="#556_submenu">鎷撴墤绠＄悊--%>

        <%--</div>--%>


        <%--<div id="557#555#/views/flash/TopologyPhysicsShow.jsp"--%>

        <%--menu="#557_submenu">鎷撴墤鐩戣--%>

        <%--</div>--%>


        <%--</div>--%>

        <%--</div>--%>


        <%--<div id="554#553#/vpnmpls.do"--%>

        <%--menu="#554_submenu">涓氬姟绠＄悊--%>

        <%--</div>--%>


        <%--</div>--%>


        <%--<li >--%>
        <%--<a id="558_menu"--%>


        <%--class="easyui-menubutton" menu="#558_submenu"--%>


        <%--href="#">--%>

        <%--娴侀噺鍒嗘瀽--%>
        <%--</a>--%>
        <%--</li>--%>


        <%--<div id="558_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="559#558#/vpnController.do"--%>

        <%--menu="#559_submenu">閾捐矾娴侀噺鏄庣粏--%>

        <%--</div>--%>


        <%--<div id="589#558#/vpnController.do?action=getlineflowthred"--%>

        <%--menu="#589_submenu">閾捐矾娴侀噺瓒嬪娍--%>

        <%--</div>--%>


        <%--<div id="590#558#/vpnController.do?action=addrDeviceBusFlowRations"--%>

        <%--menu="#590_submenu">璁惧娴侀噺鍗犳瘮--%>

        <%--</div>--%>


        <%--<div id="561#558#/vpnController.do?action=addrBusFlowRations"--%>

        <%--menu="#561_submenu">鍦板競娴侀噺鍗犳瘮--%>

        <%--</div>--%>


        <%--<div id="562#558#/vpnController.do?action=busFlowTrendsAnalysis"--%>

        <%--menu="#562_submenu">涓氬姟娴侀噺瓒嬪娍--%>

        <%--</div>--%>


        <%--<div id="560#558#/vpnController.do?action=busFlowAnalysisByDevice"--%>

        <%--menu="#560_submenu">涓氬姟娴侀噺缁熻--%>

        <%--</div>--%>


        <%--</div>--%>


        <%--<li >--%>
        <%--<a id="563_menu"--%>


        <%--class="easyui-menubutton" menu="#563_submenu"--%>


        <%--href="#">--%>

        <%--鏅鸿兘璇婃柇宸ュ叿--%>
        <%--</a>--%>
        <%--</li>--%>


        <%--<div id="563_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="565#563##"--%>

        <%--menu="#565_submenu">璺敱鍙戝竷妫�祴--%>

        <%--<div id="565_submenu" data-options="onClick:clickSubMenu">--%>


        <%--<div id="567#565#/views/ICS/routcompare/NetRoutcomparResultList.jsp"--%>

        <%--menu="#567_submenu">璺敱瀵规瘮--%>

        <%--</div>--%>


        <%--</div>--%>

        <%--</div>--%>


        <%--<div id="566#563#/SmartTool.do?action=topackfilter"--%>

        <%--menu="#566_submenu">涓氬姟鎶ユ枃绛涙煡--%>

        <%--</div>--%>


        <%--</div>--%>


        <%--<li >--%>
        <%--<a id="522_menu"--%>


        <%--class="easyui-linkbutton" plain="true" target="maincontent"--%>


        <%--href="/vpnController.do?action=getVpnReportPages">--%>

        <%--鎶ヨ〃绠＄悊--%>
        <%--</a>--%>
        <%--</li>--%>


        <%--</ul>--%>
        <%--</nav>--%>
        <%--</div>--%>
    </div>
    <div data-options="region:'south'" style="height: 28px" class="footer">
        Copyright ©  云南移动. All Rights Reserved
    </div>
    <div id="content" data-options="region:'center',border:false,minHeight:500" style="margin:0;padding:1px 3px 1px 3px;text-align:center;line-height:100%;overflow:hidden;">
        <div id="iframe" style="width:100%;height:100%;"  class="ui-layout-center">
            <iframe id="maincontent" name="maincontent" width="100%" height="100%" frameborder="0" scrolling="yes" src="about:black"></iframe>
        </div>
    </div>
    <%--<div data-options="region:'center',fit:true">--%>
    <%--<iframe id="maincontent" name="maincontent" width="100%" height="100%" frameborder="0" onload="changeFrameHeight()" scrolling="no" src="/t/userinfo/index.do"></iframe>--%>
    <%--</div>--%>
</div>
</body>
</html>