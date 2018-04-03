<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <title>无标题文档</title>
    <style type="text/css">
        <!--
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
        }

        .STYLE1 {
            color: #FFFFFF;
            font-weight: bold;
            font-size: 12px;
        }

        .STYLE2 {
            font-size: 12px;
            color: #03515d;
        }

        a:link {
            font-size: 12px;
            text-decoration: none;
            color: #03515d;
        }

        a:visited {
            font-size: 12px;
            text-decoration: none;
            color: #03515d;
        }

        -->
    </style>
    <script type="text/javascript">
        //创建流程
        function process_create() {
            var rightbuss = parent.frames['rightbuss'];
            //然后更换
            rightbuss.src = "proccess_create.do";
        }
        //发布流程
        function proccess_publish() {
            var rightbuss = parent.frames['rightbuss'];
            //然后更换
            rightbuss.src = "proccess_publish.do";
        }

        //发布流程
        function proccesPublish() {
            var rightbuss = parent.frames['rightbuss'];
            //然后更换
            rightbuss.src = "proccesPublish.do";
        }

        //待办流程
        function proccess_agent() {
            var rightbuss = parent.frames['rightbuss'];
            //然后更换
            rightbuss.src = "proccess_agent.do";
        }
        //流程历史
        function proccess_his() {
            var rightbuss = parent.frames['rightbuss'];
            //然后更换
            rightbuss.src = "proccess_his.do";
        }
        //流程归档
        function proccess_end() {
            var rightbuss = parent.frames['rightbuss'];
            //然后更换
            rightbuss.src = "proccess_end.do";
        }

        function resouces_applay() {
            var rightbuss = parent.frames['rightbuss'];
            //然后更换
            rightbuss.src = "publishLayout.do";
        }
        function resouces_list(){
            var rightbuss = parent.frames['rightbuss'];
            //然后更换
            rightbuss.src = "/pdu/";
        }
    </script>

</head>

<body>
<table width="156" height="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td align="center" valign="top">
            <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="33" background="<%=request.getContextPath() %>/META-INF/resources/x/images/main_21.gif">
                        &nbsp;</td>
                </tr>
                <tr>
                    <td height="20" background="<%=request.getContextPath() %>/META-INF/resources/x/images/main_25.gif">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="13%">&nbsp;</td>
                                <td width="72%" height="20">
                                    <div align="center">
                                        <table width="78%" height="21" border="0" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td>
                                                    <div align="center"><img
                                                            src="<%=request.getContextPath() %>/META-INF/resources/x/images/top_8.gif"
                                                            width="16" height="16"></div>
                                                </td>
                                                <td valign="middle">
                                                    <div align="center" class="STYLE1">流程引擎</div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td width="15%">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" valign="top">
                        <table width="145" border="0" align="center" cellpadding="0" cellspacing="0">

                            <tr>
                                <td>
                                    <table width="130" border="0" align="center" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td width="41" height="35">
                                                <div align="center"><img
                                                        src="<%=request.getContextPath() %>/META-INF/resources/x/images/left_1.gif"
                                                        width="31" height="31"></div>
                                            </td>
                                            <td width="89" height="35">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="23" style="cursor:hand"
                                                            onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "
                                                            onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                                                            <span class="STYLE2">&nbsp;<a href="#"
                                                                                          onclick="process_create()">新建流程</a></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="41" height="35">
                                                <div align="center"><img
                                                        src="<%=request.getContextPath() %>/META-INF/resources/x/images/left_2.gif"
                                                        width="31" height="31"></div>
                                            </td>
                                            <td height="35">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="23" style="cursor:hand"
                                                            onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "
                                                            onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                                                            <span class="STYLE2">&nbsp;<a href="#"
                                                                                          onclick="proccess_publish()">流程定义</a></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="41" height="35">
                                                <div align="center"><img
                                                        src="<%=request.getContextPath() %>/META-INF/resources/x/images/left_2.gif"
                                                        width="31" height="31"></div>
                                            </td>
                                            <td height="35">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="23" style="cursor:hand"
                                                            onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "
                                                            onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                                                            <span class="STYLE2">&nbsp;<a href="#"
                                                                                          onclick="proccesPublish()">流程发布</a></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="41" height="35">
                                                <div align="center"><img
                                                        src="<%=request.getContextPath() %>/META-INF/resources/x/images/left_3.gif"
                                                        width="31" height="31"></div>
                                            </td>
                                            <td height="35">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="23" style="cursor:hand"
                                                            onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "
                                                            onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                                                            <span class="STYLE2">&nbsp;<a href="#"
                                                                                          onclick="proccess_agent()">待办流程</a></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="41" height="35">
                                                <div align="center"><img
                                                        src="<%=request.getContextPath() %>/META-INF/resources/x/images/left_4.gif"
                                                        width="31" height="31"></div>
                                            </td>
                                            <td height="35">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="23" style="cursor:hand"
                                                            onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "
                                                            onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                                                            <span class="STYLE2">&nbsp;<a href="#"
                                                                                          onclick="proccess_his()">流程历史</a></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="41" height="35">
                                                <div align="center"><img
                                                        src="<%=request.getContextPath() %>/META-INF/resources/x/images/left_4.gif"
                                                        width="31" height="31"></div>
                                            </td>
                                            <td height="35">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="23" style="cursor:hand"
                                                            onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "
                                                            onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                                                            <span class="STYLE2">&nbsp;<a href="#"
                                                                                          onclick="proccess_end()">归档流程</a></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="41" height="35">
                                                <div align="center"><img
                                                        src="<%=request.getContextPath() %>/META-INF/resources/x/images/left_4.gif"
                                                        width="31" height="31"></div>
                                            </td>
                                            <td height="35">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="23" style="cursor:hand"
                                                            onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "
                                                            onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                                                            <span class="STYLE2">&nbsp;<a href="#"
                                                                                          onclick="resouces_applay()">资源申请</a></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td width="41" height="35">
                                                <div align="center"><img
                                                        src="<%=request.getContextPath() %>/META-INF/resources/x/images/left_4.gif"
                                                        width="31" height="31"></div>
                                            </td>
                                            <td height="35">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="23" style="cursor:hand"
                                                            onMouseOver="this.style.borderStyle='solid';this.style.borderWidth='1';borderColor='#adb9c2'; "
                                                            onmouseout="this.style.backgroundImage='url()';this.style.borderStyle='none'">
                                                            <span class="STYLE2">&nbsp;<a href="#"
                                                                                          onclick="resouces_list()">1111</a></span>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="22" background="<%=request.getContextPath() %>/META-INF/resources/x/images/main_36.gif">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="13%">&nbsp;</td>
                                <td width="72%" height="20">
                                    <div align="center">
                                        <table width="78%" height="21" border="0" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td>
                                                    <div align="center"><img
                                                            src="<%=request.getContextPath() %>/META-INF/resources/x/images/top_17.gif"
                                                            width="16" height="16"></div>
                                                </td>
                                                <td valign="middle">
                                                    <div align="center"><span class="STYLE1">xxxx</span></div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td width="15%">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="22" background="<%=request.getContextPath() %>/META-INF/resources/x/images/main_36.gif">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="13%">&nbsp;</td>
                                <td width="72%" height="20">
                                    <div align="center">
                                        <table width="78%" height="21" border="0" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td>
                                                    <div align="center"><img
                                                            src="<%=request.getContextPath() %>/META-INF/resources/x/images/top_16.gif"
                                                            width="16" height="16"></div>
                                                </td>
                                                <td valign="middle">
                                                    <div align="center"><span class="STYLE1">xxxx</span></div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td width="15%">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="22" background="<%=request.getContextPath() %>/META-INF/resources/x/images/main_36.gif">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="13%">&nbsp;</td>
                                <td width="72%" height="20">
                                    <div align="center">
                                        <table width="78%" height="21" border="0" cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td>
                                                    <div align="center"><img
                                                            src="<%=request.getContextPath() %>/META-INF/resources/x/images/top_18.gif"
                                                            width="16" height="16"></div>
                                                </td>
                                                <td valign="middle">
                                                    <div align="center" class="STYLE1">xxxx</div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                <td width="15%">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <!--       <tr> -->
                <%--         <td height="39" background="<%=request.getContextPath() %>/assets/login/images/main_37.gif">&nbsp;</td> --%>
                <!--       </tr> -->
            </table>
        </td>
    </tr>
</table>
</body>
</html>
