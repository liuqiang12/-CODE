<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title></title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<style> 
.navPoint { 
COLOR: white; CURSOR: hand; FONT-FAMILY: Webdings; FONT-SIZE: 9pt 
} 
</style> 
<script>
function switchSysBar(){ 
var locate=location.href.replace('/main/middel.do','');
var ssrc=document.all("img1").src.replace(locate,'');
if (ssrc=="<%=request.getContextPath() %>/assets/login/images/main_30.gif")
{ 
document.all("img1").src="<%=request.getContextPath() %>/assets/login/images/main_30_1.gif";
document.all("frmTitle").style.display="none" 
} 
else
{ 
document.all("img1").src="<%=request.getContextPath() %>/assets/login/images/main_30.gif";
document.all("frmTitle").style.display="" 
} 
} 
</script>

</head>

<body style="overflow:hidden">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="156" id=frmTitle noWrap name="fmTitle" align="center" valign="top">
	<iframe name="I1" height="100%" width="156" src="<%=request.getContextPath() %>/main/left.do" border="0" frameborder="0" scrolling="no">
	</iframe>	</td>
    <td width="4" valign="middle" background="<%=request.getContextPath() %>/META-INF/resources/x/images/main_27.gif"><SPAN class=navPoint
                                                                                                                            id=switchPoint title=关闭/打开左栏><img src="<%=request.getContextPath() %>/META-INF/resources/x/images/main_30.gif" name="img1" width=4 height=47 id=img1></SPAN></td>
    <td align="center" valign="top"><iframe name="I2" id="rightbuss" height="100%" width="100%" border="0" frameborder="0" src="<%=request.getContextPath() %>/main/right.do"></iframe></td>
  </tr>
</table>
</body>
</html>
