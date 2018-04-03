<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
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
    <link rel="stylesheet" href="<%=request.getContextPath() %>/framework/layout/ui7/css/common.css">

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/layout/ui7/css/style.css">
    <%--<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/layout/ui7/css/menu.css">--%>
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery-2.0.3.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.easyui.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/skin/layer.css">
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>
    <title>云南移动能耗管理平台</title>
    <script type="text/javascript" language="JavaScript">
        $(document).ready(function(){
            $(".nav li a").click(function(){
                var uri = $(this).attr("url");
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
        function clickMenu(item) {
            alert(item.text);
        }
        function clickMenuBar(obj) {
            if (selectedMenu != null) {
                jQuery(selectedMenu).removeClass("selected");
            }
            selectedMenu = obj;
            jQuery(selectedMenu).addClass("selected");
        }
        //function clickSubMenu(uri,mainMenuId,menuId){
        function clickSubMenu(item) {
            var ids = (item.id).split('#');
            var menuId = ids[0];
            var mainMenuId = ids[1];
            var uri = "";
            for (var i = 2; i < ids.length; i++) {
                uri += ids[i];
            }
            if (uri != "" && uri != "#") {
                if (uri.indexOf("?") > 0) {
                    uri += "&menuId=" + menuId;
                } else {
                    uri += "?menuId=" + menuId;
                }
                $("#maincontent").attr("src", "" + getDataURL + uri + "");
                //				window.open(getDataURL+uri,"maincontent");
                clickMenuBar(jQuery("#" + mainMenuId)[0]);
                //pageHeight();
            }
        }
    </script>
    <style>
        /*.clearfix li {*/
            /*width: 100px;*/
            /*height: 24px;*/
            /*float: left;*/
            /*text-align: center;*/
            /*line-height: 30px;*/
            /*font-size: 12px;*/
            /*position: relative;*/
            /*z-index: 300;*/
            /*display: inline-block;*/
        /*}*/
        /*.clearfix li :hover a {*/
            /*background-color:#fff;*/
            /*color: #000;*/
            /*height:31px;*/
            /*display: inline-block;*/
            /*width:100px;*/

        /*}*/


        <%--.head_v2{--%>
            <%--width:100%;--%>
        <%--}--%>
        <%--.m-btn-downarrow,  .s-btn-downarrow {--%>
            <%--background: none;--%>
        <%--}--%>
        <%--.m-btn-plain-active, .s-btn-plain-active {--%>
            <%--background: url(<%=request.getContextPath() %>/framework/layout/ui7/img/nav-hover.jpg);--%>
        <%--}--%>
        <%--.menu-line{--%>
            <%--border:0px;--%>
        <%--}--%>

    </style>
</head>
<body>
<%--<div class="nav" style="width: 100%;;z-index: 1110003;position: absolute;top:80px">--%>
    <%--<nav>--%>
        <%--<ul class="clearfix">--%>
            <%--<li> <a href="">颜值</a>--%>
                <%--<ul>--%>
                    <%--<li class="children"> <a href="#">妹子</a>--%>
                        <%--<ul>--%>
                            <%--<li><a href="#">女汉子</a></li>--%>
                            <%--<li><a href="#">大妈</a></li>--%>
                            <%--<li class="children"> <a href="#">绿茶婊</a>--%>
                                <%--<ul>--%>
                                    <%--<li><a href="#">女汉子</a></li>--%>
                                    <%--<li class="children"> <a href="#">大妈</a>--%>
                                        <%--<ul>--%>
                                            <%--<li><a href="#">女汉子</a></li>--%>
                                            <%--<li><a href="#">大妈</a></li>--%>
                                            <%--<li><a href="#">绿茶婊</a></li>--%>
                                        <%--</ul> </li>--%>
                                    <%--<li><a href="#">绿茶婊</a></li>--%>
                                <%--</ul> </li>--%>
                        <%--</ul> </li>--%>
                    <%--<li class="children"> <a href="#">高富帅</a>--%>
                        <%--<ul>--%>
                            <%--<li><a href="#">女汉子</a></li>--%>
                            <%--<li><a href="#">大妈</a></li>--%>
                            <%--<li><a href="#">绿茶婊</a></li>--%>
                        <%--</ul> </li>--%>
                    <%--<li><a href="#">吊丝</a></li>--%>
                    <%--<li><a href="#">逗逼</a></li>--%>
                <%--</ul> </li>--%>
            <%--<li><a href="#">逗比</a></li>--%>
            <%--<li><a href="#">约吗</a></li>--%>
            <%--<li><a href="#">我想静静</a></li>--%>
            <%--<li><a href="#">小鲜肉</a></li>--%>
            <%--<li><a href="#">单身狗</a></li>--%>
        <%--</ul>--%>
    <%--</nav>--%>
<%--</div>--%>
<div class="easyui-layout" fit="true">
<div data-options="region:'north',height:108,border:false" style="overflow: hidden">
    <div class="header-bg" id="top">
        <div class="main-logo"><img src="<%=request.getContextPath() %>/framework/layout/ui7/img/main-logo.png" width="443" height="64" alt=""></div>
        <div class="topnav">
            <div class="topnav-l"></div>
            <div class="topnav-c"><a href="#">退出系统</a>|<a href="#">帮助文档</a></div>
            <div class="topnav-r"></div>
        </div>
        <div class="highlight"></div>


    </div>
    <div class ="nav">
        <nav>
            <ul>
                <li>
                    <a id="1_menu"
                       class="easyui-menubutton" menu="#1_submenu" data-options="hideOnUnhover:false"
                       href="#">
                        系统管理
                    </a>
                </li>
                <div id="1_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">


                    <div id="464#1#/sysuser.do"

                         menu="#464_submenu">用户管理

                    </div>


                    <div id="465#1#/sysgroup.do"

                         menu="#465_submenu">用户分组管理

                    </div>


                    <div id="463#1#/sysrole.do"

                         menu="#463_submenu">管理角色定义

                    </div>


                    <div id="457#1##"

                         menu="#457_submenu">区域管理

                        <div id="457_submenu" data-options="onClick:clickSubMenu">


                            <div id="462#457#/netdevicerealm.do"

                                 menu="#462_submenu">设备域管理

                            </div>


                            <div id="461#457#/areaEdit.do"

                                 menu="#461_submenu">行政区域管理

                            </div>


                        </div>

                    </div>


                    <div id="2#1##"

                         menu="#2_submenu">系统设置

                        <div id="2_submenu" data-options="onClick:clickSubMenu">


                            <div id="583#2##"

                                 menu="#583_submenu">业务模型

                            </div>


                            <div id="582#2##"

                                 menu="#582_submenu">系统参数

                            </div>


                            <div id="3#2#/sysmenu.do"

                                 menu="#3_submenu">菜单信息配置

                            </div>


                        </div>

                    </div>


                    <div id="468#1#/taskManagerView.do?action=listTask&taskType=1"

                         menu="#468_submenu">任务调度

                    </div>


                </div>


                <li >
                    <a id="515_menu"


                       class="easyui-menubutton" menu="#515_submenu"


                       href="#">

                        资源管理
                    </a>
                </li>


                <div id="515_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">


                    <div id="516#515##"

                         menu="#516_submenu">存量管理

                        <div id="516_submenu" data-options="onClick:clickSubMenu">


                            <div id="586#516#/Equipment.do?action=toEquipmentJsp"

                                 menu="#586_submenu">网络设备管理

                            </div>


                            <div id="480#516#/views/ICS/vpnNetLineInfo/netLineInfo.jsp"

                                 menu="#480_submenu">链路信息管理

                            </div>


                            <div id="588#516#/netbackupline.do"

                                 menu="#588_submenu">双平面信息管理

                            </div>


                            <div id="591#516#/vpnmpls.do?action=getPortWithVpnBus"

                                 menu="#591_submenu">业务绑定规则

                            </div>


                        </div>

                    </div>


                    <div id="549#515##"

                         menu="#549_submenu">数据维护

                        <div id="549_submenu" data-options="onClick:clickSubMenu">


                            <div id="518#549#/group.do"

                                 menu="#518_submenu">设备分组

                            </div>


                            <div id="550#549#/NetTelnetflow.do"

                                 menu="#550_submenu">登录流程信息

                            </div>


                        </div>

                    </div>


                    <div id="519#515##"

                         menu="#519_submenu">设备采集

                        <div id="519_submenu" data-options="onClick:clickSubMenu">


                            <div id="520#519#/DsyNetdsyplan_res.do?action=listDsyResdeviceinfo"

                                 menu="#520_submenu">资源采集

                            </div>


                        </div>

                    </div>


                </div>


                <li >
                    <a id="511_menu"


                       class="easyui-menubutton" menu="#511_submenu"


                       href="#">

                        告警管理
                    </a>
                </li>


                <div id="511_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">


                    <div id="512#511#/vpnalarm.do"

                         menu="#512_submenu">告警事件

                    </div>


                    <div id="541#511#/vpnalarm.do?action=getalarmlevel"

                         menu="#541_submenu">告警阀值

                    </div>


                </div>


                <li >
                    <a id="553_menu"


                       class="easyui-menubutton" menu="#553_submenu"


                       href="#">

                        业务视图
                    </a>
                </li>


                <div id="553_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">


                    <div id="555#553##"

                         menu="#555_submenu">业务拓扑

                        <div id="555_submenu" data-options="onClick:clickSubMenu">


                            <div id="556#555#/views/flash/Topo.jsp"

                                 menu="#556_submenu">拓扑管理

                            </div>


                            <div id="557#555#/views/flash/TopologyPhysicsShow.jsp"

                                 menu="#557_submenu">拓扑监视

                            </div>


                        </div>

                    </div>


                    <div id="554#553#/vpnmpls.do"

                         menu="#554_submenu">业务管理

                    </div>


                </div>


                <li >
                    <a id="558_menu"


                       class="easyui-menubutton" menu="#558_submenu"


                       href="#">

                        流量分析
                    </a>
                </li>


                <div id="558_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">


                    <div id="559#558#/vpnController.do"

                         menu="#559_submenu">链路流量明细

                    </div>


                    <div id="589#558#/vpnController.do?action=getlineflowthred"

                         menu="#589_submenu">链路流量趋势

                    </div>


                    <div id="590#558#/vpnController.do?action=addrDeviceBusFlowRations"

                         menu="#590_submenu">设备流量占比

                    </div>


                    <div id="561#558#/vpnController.do?action=addrBusFlowRations"

                         menu="#561_submenu">地市流量占比

                    </div>


                    <div id="562#558#/vpnController.do?action=busFlowTrendsAnalysis"

                         menu="#562_submenu">业务流量趋势

                    </div>


                    <div id="560#558#/vpnController.do?action=busFlowAnalysisByDevice"

                         menu="#560_submenu">业务流量统计

                    </div>


                </div>


                <li >
                    <a id="563_menu"


                       class="easyui-menubutton" menu="#563_submenu"


                       href="#">

                        智能诊断工具
                    </a>
                </li>


                <div id="563_submenu" class="easyui-menu" data-options="onClick:clickSubMenu">


                    <div id="565#563##"

                         menu="#565_submenu">路由发布检测

                        <div id="565_submenu" data-options="onClick:clickSubMenu">


                            <div id="567#565#/views/ICS/routcompare/NetRoutcomparResultList.jsp"

                                 menu="#567_submenu">路由对比

                            </div>


                        </div>

                    </div>


                    <div id="566#563#/SmartTool.do?action=topackfilter"

                         menu="#566_submenu">业务报文筛查

                    </div>


                </div>


                <li >
                    <a id="522_menu"


                       class="easyui-linkbutton" plain="true" target="maincontent"


                       href="/vpnController.do?action=getVpnReportPages">

                        报表管理
                    </a>
                </li>


            </ul>
        </nav>
    </div>
</div>
<div data-options="region:'south'" style="height: 28px" >
       Copyright © 2007 - 2015 广州咨元. All Rights Reserved
</div>
<div id="content" data-options="region:'center',border:false,minHeight:500" style="margin:0;padding:1px 3px 1px 3px;text-align:center;line-height:100%;overflow:hidden;">
    <div id="iframe" style="width:100%;height:100%;"  class="ui-layout-center">
        <iframe id="maincontent" name="maincontent" width="100%" height="100%" frameborder="0" scrolling="yes" src="/t/userinfo/index.do"></iframe>
    </div>
</div>
<%--<div data-options="region:'center',fit:true">--%>
    <%--<iframe id="maincontent" name="maincontent" width="100%" height="100%" frameborder="0" onload="changeFrameHeight()" scrolling="no" src="/t/userinfo/index.do"></iframe>--%>
<%--</div>--%>
</div>
<script>
//    $(function () {
//        $(".cc").height($("#center").height()-28);
//        setTimeout(function(){
//        },500);
//    })
</script>
</body>
</html>