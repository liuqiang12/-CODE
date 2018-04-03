
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../demo.css">
    <link rel="stylesheet" href="../../css/common.css">
    <link rel="stylesheet" type="text/css" href="../../css/style.css">
    <script type="text/javascript" src="../../jquery.min.js"></script>
    <script type="text/javascript" src="../../jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../../js/ztab.js"></script><!--选项卡-->
    <script type="text/javascript" src="../../js/menu.js"></script><!--展开菜单-->
    <title>main</title>
</head>
<body class="easyui-layout">
<!--header-->
<div data-options="region:'north',border:false" class="header">
    <div class="header-bg">
        <div class="main-logo"><img src="../../img/main-logo.png" width="443" height="64" alt=""></div>
        <div class="topnav">
            <div class="topnav-l"></div>
            <div class="topnav-c"><a href="#">退出系统</a>|<a href="#">帮助文档</a></div>
            <div class="topnav-r"></div>
        </div>
        <div class="highlight"></div>
    </div>
    <div class="nav">
      <jsp:include page="/menu.jsp"></jsp:include>
    </div>
</div>
<!--header END-->

<!--left-->
<div data-options="region:'west',split:true,title:'West'" style="width:150px;padding:10px;background:#e3f4fe;">left content</div>
<!--left END-->

<!--side
<div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">12</div>
side END-->

<!--content-->
<div data-options="region:'center',title:'Center'" style="padding:10px;">

</div>
<!--content END-->

<!--footer-->
<div data-options="region:'south',border:false" style="" class="footer">
    Copyright © 2007 - 2015 广州咨元. All Rights Reserved
</div>
<!--footer END-->
</body>
</html>