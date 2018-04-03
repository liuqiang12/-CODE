<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" "http://www.w3.org/TR/html4/loose.dtd">


<script language="javascript">
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    try {
        if (window.ActiveXObject)
            Sys.ie = ua.match(/msie ([\d.]+)/)[1];
        else if (document.getBoxObjectFor)
            Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1];
        else if (window.MessageEvent && !document.getBoxObjectFor)
            Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1];
        else if (window.opera)
            Sys.opera = ua.match(/opera.([\d.]+)/)[1];
        else if (window.openDatabase)
            Sys.safari = ua.match(/version\/([\d.]+)/)[1];
    } catch (e) {

    }
    var baseContextPath = "";
</script>
<style>
    table {
        font-size: 12px;
    }
</style>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>咨元网络自动化运维平台</title>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="P3P" content='CP="IDC DSP COR CURa ADMa OUR IND PHY ONL COM STA"'/>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <title>咨元网络自动化运维平台</title>
    <script src="/javascript/jquery/jquery.min.js"
            type="text/javascript" charset="UTF-8"></script>
    <script src="/javascript/jeasyui/jquery.easyui.min.js"
            type="text/javascript" charset="UTF-8"></script>
    <script src="/javascript/jeasyui/locale/easyui-lang-zh_CN.js"
            type="text/javascript" charset="UTF-8"></script>
    <script src="/javascript/jquery/jquery.validate.js"
            type="text/javascript" charset="UTF-8"></script>
    <script src="/javascript/jquery/jquery.form.js"
            type="text/javascript" charset="UTF-8"></script>

    <script src="/javascript/jquery/jquery.loadmask.js"
            type="text/javascript" charset="UTF-8"></script>
    <link href="/themes/common/css/jquery.loadmask.css"
          media="screen" rel="stylesheet" type="text/css"/>
    <link href="/themes/jeasyui/default/easyui.css"
          media="screen" rel="stylesheet" type="text/css"/>
    <link href="/themes/jeasyui/noliferaytheme/easyui.css"
          media="screen" rel="stylesheet" type="text/css"/>
    <link href="/themes/jeasyui/icon.css"
          media="screen" rel="stylesheet" type="text/css">
    <link href="/themes/common/css/form.css"
          media="screen" rel="stylesheet" type="text/css"/>
    <link href="/themes/common/css/button.css"
          media="screen" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        * {
            margin: 0 0 0 0;
            padding: 0 0 0 0;
        }

        body {
            font-size: 12px;
            font-family: "宋体";
            background: #e5f2fa;
        }

        .head_v2 {
            width: 100%;
        }

        .head_top_v2 {
            height: 62px;
            width: 100%;
            background-image: url(/themes/bluetheme/images/topbg_v22.gif);
            background-repeat: repeat-x;
        }

        .menu_v2 {
            height: 31px;
            width: 100%;
            background-image: url(/themes/bluetheme/images/menubg_v2.gif);

        }

        .logo_v2 {
            float: left;
            height: 27px;
            width: 147px;
            margin-top: 10px;
            margin-left: 10px;
        }

        .admin_v2 {
            height: 24px;
            width: 200px;
            float: left;
        }

        .menu_v2 {
            padding-top: 1px;
            padding-left: 3px;
        }

        .menu_v2 li {
            width: 100px;
            height: 24px;
            float: left;
            text-align: center;
            line-height: 30px;
            font-size: 12px;
            position: relative;
            z-index: 300;
            display: inline-block;
        }

        .menu_v2 .test a {

            line-height: 30px;
            color: #FFF;
            text-decoration: none;
        }

        .menu_v2 .test a:hover {

            line-height: 30px;
            color: #000;
            text-decoration: none;
        }

        .m-btn-plain-active .l-btn-text {
            color: #000;
        }

        .menu_content_v2 {
            width: 90%;
            height: 24px;
            float: left;

        }

        #fenge {
            width: 5px;
            height: 31px;
            float: left;
            margin-top: 10px;
            background-image: url(/themes/bluetheme/images/fenge_v2.gif);

            background-repeat: no-repeat;

        }

        .menu_v2 li:hover a {
            background-color: #fff;
            color: #000;
            height: 31px;
            display: inline-block;
            width: 100px;

        }

        .userdiv {
            background-image: url('themes/common/images/userbg.jpg');
            background-repeat: no-repeat;
            width: 60px;
            height: 24px;
            display: block;
            float: right;
            padding-left: 25px;
            padding-top: 1px;
            color: #fff;
            font-size: 14px;
        }

        /*密码强度*/
        .pw-strength {
            clear: both;
            position: relative;
            top: 8px;
            width: 180px;
        }

        .pw-bar {
            background: url(/themes/common/images/pwd-1.png) no-repeat;
            height: 14px;
            overflow: hidden;
            width: 179px;
        }

        .pw-bar-on {
            background: url(/themes/common/images/pwd-2.png) no-repeat;
            width: 0px;
            height: 14px;
            position: absolute;
            top: 1px;
            left: 2px;
            transition: width .5s ease-in;
            -moz-transition: width .5s ease-in;
            -webkit-transition: width .5s ease-in;
            -o-transition: width .5s ease-in;
        }

        .pw-weak .pw-defule {
            width: 0px;
        }

        .pw-weak .pw-bar-on {
            width: 60px;
        }

        .pw-medium .pw-bar-on {
            width: 120px;
        }

        .pw-strong .pw-bar-on {
            width: 179px;
        }

        .pw-txt {
            padding-top: 2px;
            padding-bottom: 10px;
            width: 180px;
            overflow: hidden;
        }

        .pw-txt span {
            color: #707070;
            float: left;
            font-size: 12px;
            text-align: center;
            width: 58px;
        }
    </style>


    <script type="text/javascript" charset="UTF-8">
        var getDataURL = "";
        var selectedMenu = null;
        var colors = ["#ff2e99", "#ff8a2d", "#ffe12a", "#caff2a", "#1fb5ff", "#5931ff", "#b848ff"];
        var mainPane = null;
        var smsSend = "true";
        jQuery(document).ready(function () {
            jQuery(document.body).mask("正在初始化，请等候...");
            jQuery("#maincontent").attr("src", "/ipHomepage/homepage.do");
            //pageHeight();
            jQuery("#copyright").hover(function () {
                $(this).data("text", $(this).text());
                $(this).html(colorize($(this).text()));
            }, function () {
                $(this).html($(this).data("text"));
            });
            $("#smsSendPic").hide();
            if (smsSend == 'true') {
                $("#smsSendPic").show();
            }

            $("#password_form").attr("action", getDataURL + "/sysuser.do?action=savePassword");
            $("#btnPwdSave").click(function (e) {
                $("#password_form").submit();
            });
            $("#btnPwdCancel").click(function (e) {
                $("#password_form")[0].reset();
                jQuery("#UserPwdForm").dialog("close");
            });
            var pwdoptions = {
                beforeSubmit: function () {
                    var opwd = jQuery("#oldPassword").val();
                    var npwd = jQuery("#newPassword").val();
                    var cpwd = jQuery("#confirmPassword").val();
                    if (opwd == "") {
                        alert("请输入旧密码！");
                        return false;
                    }
                    if (npwd != cpwd) {
                        alert("新密码和确认密码不一致！");
                        return false;
                    }
                    if (npwd.length > 24 || npwd.length < 8) {
                        alert("密码长度必须在8个到24个字符之内！");
                        return false;
                    }
                    var reg1 = /\d+?/;
                    var reg2 = /[a-z]+?/;
                    var reg3 = /[A-Z]+?/;
                    var reg4 = /((?=[\x21-\x7e]+)[^A-Za-z0-9])/;
                    if (!(reg1.test(npwd) && reg2.test(npwd) && reg3.test(npwd) && reg4.test(npwd))) {
                        alert("密码必须包含英文大小写字母、数字和特殊字符！");
                        return false;
                    }
                    return true;
                },
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        $("#password_form")[0].reset();
                        jQuery("#UserPwdForm").dialog("close");
                    } else {
                        $.messager.alert('', data.failed, 'error');
                    }
                },
                error: function () {
                    alert('错误！');
                }
            };
            $("#password_form").validate({
                submitHandler: function (form) {
                    $("#password_form").ajaxSubmit(pwdoptions);
                },
                rules: {
                    newPassword: {required: true, safe: true},
                    confirmPassword: {required: true, safe: true},
                    Sequence: {required: true, number: true}
                },
                messages: {
                    newPassword: {required: "新密码不能为空"},
                    confirmPassword: {required: "确认密码不能为空"}
                },
                errorElement: "div",
                errorClass: "cusErrorPanel",
                errorPlacement: showValidateError
            });
            $.validator.addMethod("safe", function (value, element) {
                return this.optional(element) || /^[^$\<\>]+$/.test(value);
            }, "不能包含以下符号: $<>");
            $('#newPassword').keyup(function () {
                var strongRegex = new RegExp("^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$", "g");
                var mediumRegex = new RegExp("^(?=.{7,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$", "g");
                var enoughRegex = new RegExp("(?=.{6,}).*", "g");

                if (false == enoughRegex.test($(this).val())) {
                    $('#level').removeClass('pw-weak');
                    $('#level').removeClass('pw-medium');
                    $('#level').removeClass('pw-strong');
                    $('#level').addClass(' pw-defule');
                    //密码小于六位的时候，密码强度图片都为灰色
                } else if (strongRegex.test($(this).val())) {
                    $('#level').removeClass('pw-weak');
                    $('#level').removeClass('pw-medium');
                    $('#level').removeClass('pw-strong');
                    $('#level').addClass(' pw-strong');
                    //密码为八位及以上并且字母数字特殊字符三项都包括,强度最强
                } else if (mediumRegex.test($(this).val())) {
                    $('#level').removeClass('pw-weak');
                    $('#level').removeClass('pw-medium');
                    $('#level').removeClass('pw-strong');
                    $('#level').addClass(' pw-medium');
                    //密码为七位及以上并且字母、数字、特殊字符三项中有两项，强度是中等
                } else {
                    $('#level').removeClass('pw-weak');
                    $('#level').removeClass('pw-medium');
                    $('#level').removeClass('pw-strong');
                    $('#level').addClass('pw-weak');
                    //如果密码为6为及以下，就算字母、数字、特殊字符三项都包括，强度也是弱的
                }
                return true;
            });
        });

        function showValidateError(error, element) {
            error.appendTo(element.parent());
        }
        function colorize(text) {
            var colorized = "";
            var bracket_color = "";
            for (i = 0; i < text.length; i++) {
                var index = Math.floor(Math.random() * 7)
                if (text[i] == "(")
                    bracket_color = colors[index];

                color = (bracket_color.length && (text[i] == "(" || text[i] == ")")) ? bracket_color : colors[index];
                colorized = colorized + '<span style="color: ' + color + ' !important">' + text.charAt(i) + '</span>';
            }
            return colorized;
        }
        //var temp = 0;
        function pageHeight() {
            //var gap=0;
            var browserHeight = 0;
            if (jQuery.browser.msie) {
                browserHeight = document.compatMode == "CSS1Compat" ? document.documentElement.clientHeight :
                        document.body.clientHeight;
            } else {
                browserHeight = self.innerHeight;
                //var iframeHeight = $("#maincontent").contents().find("body").height();

                //alert("iframeHeight:"+iframeHeight);
                //if(iframeHeight != null){
                //alert("iframeHeight:"+iframeHeight);
                //gap = browserHeight-iframeHeight;
                //temp =  browserHeight - gap;

                //alert("browserHeight:"+browserHeight);
                //$(".l-page-bottom").parent().css("top",browserHeight);
                //}else{
                //alert("temp:"+temp);
                //$(".l-page-bottom").parent().css("top",temp);
                //}
            }
            return browserHeight;
        }
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

        /* function refIframeAndDivHeight(){
         var ifDiv = parent.document.getElementById("iframe");
         var iframe = ifDiv.getElementsByTagName("iframe")[0];
         ifDiv.style.height = (document.body.offsetHeight) + "px";
         iframe.style.height = (document.body.offsetHeight) + "px";
         } */

        /**
         *提交短信发送请求
         */
        function submitSender() {
            $('#sms_data').form('submit', {
                success: function (data) {
                    $.messager.alert('Info', data, 'info');
                }
            });
        }

        /**
         *清理短信框填写的内容
         */
        function clearSender() {
            $('#sms_data').form('clear');
        }

        /**
         *查看短信发送记录
         */
        function smsSendList() {
            jQuery("#table_sms").html("");
            jQuery('<table id="list_sms" style="margin:0;padding:0; width:960;"></table>').appendTo(jQuery("#table_sms"));
            jQuery("#list_sms").datagrid({
                fit: true,
                fitColumns: false,
                title: false,
                url: 'SmsSend.do?action=getSmsSendList',
                pageList: [10, 15, 20],
                pageSize: 10,
                remoteSort: false,
                idField: "id",
                rownumbers: false,
                singleSelect: true,
                pagination: true,
                columns: [[{
                    title: '序号',
                    field: 'id',
                    width: 90,
                    hidden: true
                }, {
                    title: '接受手机',
                    field: 'phoneNumber',
                    width: 90,
                    align: 'left'
                }, {
                    title: '短信内容',
                    field: 'message',
                    width: 150,
                    align: 'left'
                }, {
                    title: '发送时间',
                    field: 'sendTime',
                    width: 123,
                    align: 'left'
                }, {
                    title: '状态',
                    field: 'stateStr',
                    width: 100,
                    align: 'left'
                }]]
            });
        }

        function loginout() {
            $.messager.confirm('', '<center><font size=3>确认要退出系统?</font></center>', function (r) {
                if (r) {
                    location.href = 'login.do?action=logout';
                }
            });
        }

        function showPasswordChange(userId, userName) {
            $("#password_form")[0].reset();
            $("#userPwdFormId").val(userId);
            $("#userPwdId").val(userId);
            $("#userPwdName").val(userName);
            jQuery("#UserPwdForm").dialog("open");
            jQuery("#newPassword").focus();
        }
    </script>
</head>
<body style="padding:0;overflow:hidden;">
<div id="wrapper" class="easyui-layout" fit="true">
    <div id="pageloading" style="display:none;"></div>
    <div id="banner" data-options="region:'north',border:false,inline:false"
         style="height:95px;overflow:hidden;padding:0;position:static;background: none;">
        <div class="head_v2">
            <div class="head_top_v2">
                <div class="logo_v2"></div>
                <div style="float: right;font-size: 12px;font-weight: 400;margin-top: 1px;margin-right: 10px;height:20px; color: #000; font-family: '微软雅黑';cursor:default;vertical-align:bottom;">
                    <span style="vertical-align:bottom;top: 1px;position: relative;"><img
                            src="themes/common/images/user_red.png" border="0" align="absMiddle"/><a
                            href="javascript:void(0)"
                            onClick="showPasswordChange('2','admin')">admin</a>&nbsp;&nbsp;</span>
							<span id="smsSendPic" style="cursor: pointer"
                                  onmouseover="this.style.textDecoration='underline';"
                                  onmouseout="this.style.textDecoration='none';"
                                  style="vertical-align:bottom;top: 1px;position: relative;"><img
                                    src="themes/common/images/rule16.png" borer="0" align="absMiddle"/>
								<a href="javascript:void(0)" onclick="$('#smssend').window('open')"
                                   style="text-decoration: none;color: black;">发送短信</a>
							</span>
                    <span onclick="loginout()" style="cursor: pointer"
                          onmouseover="this.style.textDecoration='underline';"
                          onmouseout="this.style.textDecoration='none';"
                          style="vertical-align:bottom;top: 1px;position: relative;"><img
                            src="themes/common/images/looout.png" borer="0" align="absMiddle"/> 退出登录</span>
                </div>

                <div id="smssend" class="easyui-window" title="发送短信" data-options="closed:true, iconCls:'icon-save'"
                     style="width:500px;height:250px;padding:5px;">
                    <div class="easyui-layout" data-options="fit:true">
                        <div data-options="region:'center'" style="padding:10px;">
                            <form id="sms_data" method="post" action="SmsSend.do?action=smsSender">
                                <table width="100%" cellspacing="5">
                                    <tr>
                                        <td style="width:70px;" align="center">手机号码</td>
                                        <td>
                                            <textarea id="phoneNumber" name="phoneNumber" style="width:95%;height:60px;"
                                                      class="easyui-validatebox" required="true"></textarea>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width:70px;" align="center">短信内容</td>
                                        <td>
                                            <textarea id="message" name="message" style="width:95%;height:60px;"
                                                      class="easyui-validatebox" required="true"></textarea>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                            <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" href="javascript:void(0)"
                               onclick="smsSendList();$('#smssendlist').window('open')" style="width:80px">记录</a>
                            <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)"
                               onclick="submitSender()" style="width:80px">发送</a>
                            <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
                               onclick="clearSender();$('#smssend').window('close')" style="width:80px">取消</a>
                        </div>
                    </div>
                </div>

                <div id="smssendlist" class="easyui-window" title="短信记录" data-options="closed:true, iconCls:'icon-save'"
                     style="width:500px;height:500px;padding:5px;">
                    <div id="table_sms" class="easyui-panel" data-options="fit:true,border:false"
                         style="position:relative;">
                    </div>
                </div>

            </div>
            <div class="menu_v2">
                <div class="menu_content_v2">
                    <ul>


                        <li class="test">
                            <a id="1_menu"


                               class="easyui-menubutton" menu="#1_submenu"


                               href="#">

                                系统管理
                            </a>
                        </li>


                        <div id="clickSubMenu" class="easyui-menu" data-options="onClick:clickSubMenu">


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


                        <li class="test">
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


                        <li class="test">
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


                        <li class="test">
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


                        <li class="test">
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


                        <li class="test">
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


                        <li class="test">
                            <a id="522_menu"


                               class="easyui-linkbutton" plain="true" target="maincontent"


                               href="/vpnController.do?action=getVpnReportPages">

                                报表管理
                            </a>
                        </li>


                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div id="content" data-options="region:'center',border:false,minHeight:500"
         style="margin:0;padding:1px 3px 1px 3px;text-align:center;line-height:100%;overflow:hidden;">
        <div id="iframe" style="width:100%;height:100%;" class="ui-layout-center">
            <iframe id="maincontent" name="maincontent" width="100%" height="100%" frameborder="0" scrolling="yes"
                    src="about:blank"></iframe>
        </div>
    </div>
    <div data-options="region:'south',border:false,split:false"
         style="height:30px;line-height:30px;text-align:center;overflow:hidden;font-size:13px;">
        <span id="copyright" style="cursor:default;">广州咨元信息科技有限公司 </span>版权所有
    </div>
</div>
<div id="UserPwdForm" class="easyui-dialog" title="用户密码修改"
     data-options="iconCls:'icon-tip',resizable:true,modal:true,closed:true,width:500,height:245,buttons:'#UserPWDButton'">
    <div class="easyui-panel" data-options="fit:true,border:false">
        <form id="password_form" modelAttribute="user" method="post">
            <input type="hidden" id="userPwdFormId" name="userId" value=""/>
            <table id="xeasy-form-table" width="100%" class="xeasy-form" cellspacing="4" cellpadding="1">
                <tr>
                    <td style="width:70px;" align="center">用户ID</td>
                    <td><input id="userPwdId" name="userPwdId" style="width:90%" disabled="disabled"/></td>
                </tr>
                <tr>
                    <td style="width:70px;" align="center">用户名称</td>
                    <td><input id="userPwdName" name="userName" style="width:90%" disabled="disabled"/></td>
                </tr>
                <tr>
                    <td style="width:70px;" align="center">旧密码</td>
                    <td><input type="password" id="oldPassword" name="oldPassword" style="width:90%"/><span
                            class="xeasy-formItemMust">*</span></td>
                </tr>
                <tr>
                    <td style="width:70px;" align="center">用户密码</td>
                    <td><input type="password" id="newPassword" name="newPassword" style="width:90%"/><span
                            class="xeasy-formItemMust">*</span></td>
                </tr>
                <tr>
                    <td></td>
                    <td id="level" class="pw-strength">
                        <div class="pw-bar"></div>
                        <div class="pw-bar-on"></div>
                        <div class="pw-txt">
                            <span>弱</span>
                            <span>中</span>
                            <span>强</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="center">密码确认</td>
                    <td><input type="password" id="confirmPassword" name="confirmPassword" style="width:90%"/><span
                            class="xeasy-formItemMust">*</span></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td align="left" style="color:red">密码设置提示：英文字母大小写+特殊字符+数字,长度为8~24</td>
                </tr>
            </table>
        </form>
    </div>
    <div id="UserPWDButton">
        <a id="btnPwdSave" class="easyui-linkbutton" href="javascript:void(0);"><span>确定</span></a>
        <a id="btnPwdCancel" class="easyui-linkbutton" href="javascript:void(0);"><span>取消</span></a>
    </div>
</div>
<script type="text/javascript" charset="UTF-8">
    jQuery(document).ready(function () {
        jQuery(document.body).unmask();
    });
</script>
</body>
<script src="/javascript/common/ldialog.js"
        type="text/javascript" charset="UTF-8"></script>

</html>
