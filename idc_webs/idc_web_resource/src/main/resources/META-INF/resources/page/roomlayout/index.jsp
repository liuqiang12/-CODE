<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script src="<%=request.getContextPath() %>/framework/vuejs/vue.min.js" type="text/javascript"></script>
    <STYLE>
        .container {
            position: relative;
            -webkit-perspective: 2000px;
            perspective: 2000px;
            width: 100vw;
            height: 100vh;
        }
        .cube {
            position: absolute;
            top: 50%;
            left: 50%;
            margin: -500px 0 0 -960px;
            width: 1920px;
            height: 1000px;
            -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
        }
        .cube__side {
            position: absolute;
            display: block;
        }
        .cube__side--front,
        .cube__side--back {
            width: 1920px;
            height: 1000px;
        }

        .cube__side--left,
        .cube__side--right {
            background: #2b2b2d;
            width: 3000px;
            /* depth */
            height: 1000px;
        }
        .cube__side--top,
        .cube__side--bottom {
            width: 1920px;
            height: 3000px;
        }
        .cube__side--back {
            background: #232325;
            box-shadow: 0 0 0 1px #232325;
            -webkit-transform: translate3d(0, 0, -3000px);
            transform: translate3d(0, 0, -3000px);
        }

        .cube__side--right {
            right: 0;
            -webkit-transform: rotate3d(0, 1, 0, -90deg);
            transform: rotate3d(0, 1, 0, -90deg);
            -webkit-transform-origin: 100% 50%;
            transform-origin: 100% 50%;
        }
        .cube__side--left {
            -webkit-transform: rotate3d(0, 1, 0, 90deg);
            transform: rotate3d(0, 1, 0, 90deg);
            -webkit-transform-origin: 0 50%;
            transform-origin: 0 50%;
        }
        .cube__side--top {
            background: #272729;
            -webkit-transform: rotate3d(1, 0, 0, 90deg) translate3d(0, -3000px, 0);
            transform: rotate3d(1, 0, 0, 90deg) translate3d(0, -3000px, 0);
            -webkit-transform-origin: 50% 0%;
            transform-origin: 50% 0%;
        }
        .rows--large {
            height: 113px;
            left: 160px;
            bottom: 0;
            position: absolute;
            -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
            width: calc(100% - 320px);
        }
        .row {
            display: -webkit-flex;
            width: 100%;
        }
        .rows--large .row {
            left: 0;
            height: 100%;
            position: absolute;
            -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
        }
        .row__seat {
            -webkit-flex: none;
            flex: none;
            height: 100%;
            margin: 0;
        }
        .rows--mini .row__seat {
            width: 39px;
            height: 39px;
            background: rgba(72, 72, 78, 0.5);
            margin: 0 0 1px 1px;
            border-radius: 1px;
            cursor: pointer;
        }
        .rows--mini .row__seat:hover {
            background: #57e683;
        }
        .rows--mini .row__seat--reserved,
        .rows--mini .row__seat--reserved:hover {
            background: #de6363;
        }
        .rows--mini .row__seat--cannotuse,
        .rows--mini .row__seat--cannotuse:hover {
            background: #272424;
        }
        .rows--mini .row__seat--canuse,
        .rows--mini .row__seat--canuse:hover {
            background: #86de2b;
        }
        .rows--mini .row__seat--perfree,
        .rows--mini .row__seat--perfree:hover {
            background: #de6828;
        }
        .rows--mini .row__seat--free,
        .rows--mini .row__seat--free:hover {
            background: #92ded1;
        }
        .rows--mini .row__seat--preused,
        .rows--mini .row__seat--preused:hover {
            background:#beb6de;
        }
        .rows--mini .row__seat--stop,
        .rows--mini .row__seat--stop:hover {
            background: #de6363;
        }
        .rows--mini .row__seat--runing,
        .rows--mini .row__seat--runing:hover {
            background: #21c252;
        }
        .rows--large .row__seat {
            width: 80px;
            background: url(../img/seat.svg) no-repeat 50% 0;
            background-size: 100%;
        }
        .rows--large .row:nth-child(1) {
            -webkit-transform: translate3d(0, 0px, -2200px);
            transform: translate3d(0, 0px, -2200px);
        }
        .rows--large .row:nth-child(2) {
            -webkit-transform: translate3d(0, -18px, -2100px);
            transform: translate3d(0, -18px, -2100px);
        }
        .rows--large .row:nth-child(3) {
            -webkit-transform: translate3d(0, -36px, -2000px);
            transform: translate3d(0, -36px, -2000px);
        }
        .rows--large .row:nth-child(4) {
            -webkit-transform: translate3d(0, -54px, -1900px);
            transform: translate3d(0, -54px, -1900px);
        }
        .rows--large .row:nth-child(5) {
            -webkit-transform: translate3d(0, -72px, -1800px);
            transform: translate3d(0, -72px, -1800px);
        }
        .rows--large .row:nth-child(6) {
            -webkit-transform: translate3d(0, -90px, -1700px);
            transform: translate3d(0, -90px, -1700px);
        }
        .rows--large .row:nth-child(7) {
            -webkit-transform: translate3d(0, -108px, -1600px);
            transform: translate3d(0, -108px, -1600px);
        }
        .rows--large .row:nth-child(8) {
            -webkit-transform: translate3d(0, -126px, -1500px);
            transform: translate3d(0, -126px, -1500px);
        }
        .rows--large .row:nth-child(9) {
            -webkit-transform: translate3d(0, -144px, -1400px);
            transform: translate3d(0, -144px, -1400px);
        }
        .rows--large .row:nth-child(10) {
            -webkit-transform: translate3d(0, -198px, -1100px);
            transform: translate3d(0, -198px, -1100px);
        }
        .rows--large .row:nth-child(11) {
            -webkit-transform: translate3d(0, -216px, -1000px);
            transform: translate3d(0, -216px, -1000px);
        }
        .rows--large .row:nth-child(12) {
            -webkit-transform: translate3d(0, -234px, -900px);
            transform: translate3d(0, -234px, -900px);
        }
        .rows--large .row:nth-child(13) {
            -webkit-transform: translate3d(0, -252px, -800px);
            transform: translate3d(0, -252px, -800px);
        }
        .rows--large .row:nth-child(14) {
            -webkit-transform: translate3d(0, -270px, -700px);
            transform: translate3d(0, -270px, -700px);
        }
        .rows--large .row:nth-child(15) {
            -webkit-transform: translate3d(0, -288px, -600px);
            transform: translate3d(0, -288px, -600px);
        }
        .rows--large .row:nth-child(16) {
            -webkit-transform: translate3d(0, -306px, -500px);
            transform: translate3d(0, -306px, -500px);
        }
        .rows--large .row:nth-child(17) {
            -webkit-transform: translate3d(0, -324px, -400px);
            transform: translate3d(0, -324px, -400px);
        }
        .rows--large .row:nth-child(18) {
            -webkit-transform: translate3d(0, -342px, -300px);
            transform: translate3d(0, -342px, -300px);
        }
        .plan {
            top: 0;
            right: 0;
            padding: 10px 20px;
            position: fixed;
            z-index: 1000;
            -webkit-transform: scale3d(1, 1, 1) translate3d(100%, 0, 0);
            transform: scale3d(1, 1, 1) translate3d(100%, 0, 0);
            -webkit-transition: -webkit-transform 0.6s;
            transition: transform 0.6s;
        }
        .plan--shown {
            -webkit-transform: scale3d(1, 1, 1);
            transform: scale3d(1, 1, 1);
        }
        .plan__title {
            text-align: center;
            font-size: 1em;
            margin: 0.25em 0 0.5em;
        }
        .legend {
            list-style: none;
            margin: 1em 0 0 0;
            padding: 0;
            text-align: center;
        }
        .legend__item {
            font-size: 0.85em;
            font-weight: bold;
            margin: 0 9px 0 0;
            display: inline-block;
        }
        .legend__item::before {
            content: '';
            width: 30px;
            height: 30px;
            display: inline-block;
            margin: 0 5px 0 0;
            border-radius: 2px;
            background: rgba(72, 72, 78, 0.5);
        }
        .legend__item--cannotuse::before {
            background: #272424;
        }
        .legend__item--canuse::before {
            background: #86de2b;
        }
        .legend__item--perfree::before {
            background: #de6828;
        }
        .legend__item--free::before {
            background: #92ded1;
        }
        .legend__item--preused::before {
            background: #beb6de;
        }
        .legend__item--stop::before {
            background: #de6363;
        }
        .legend__item--runing::before {
            background: #21c252;
        }
        .legend__item--reserved::before {
            background: #de6363;
        }
        .legend__item--selected::before,
        .rows--mini .row__seat--selected,
        .rows--mini .row__seat--selected:hover {
            background: #21c252;
        }
        .row__seat {
            position: relative;
            display: inline-block;
        }
        .row__seat::before,
        .row__seat::after {
            position: absolute;
            opacity: 0;
            pointer-events: none;
            left: 50%;
        }
        .row__seat:hover::before,
        .row__seat:hover::after {
            opacity: 1;
            -webkit-transition: opacity 0.3s ease, -webkit-transform 0.3s ease;
            transition: opacity 0.3s ease, transform 0.3s ease;
            -webkit-transition-delay: 0.1s;
            transition-delay: 0.1s;
        }
        .screen {
            position: relative;
            background: #6f6f7b;
            width: 1280px;
            height: 720px;
            margin: 100px auto 0;
            overflow: hidden;
            border-radius: 7px;
        }
        .video {
            width: 100%;
            height: 100%;
            overflow: hidden;
            border-radius: 7px;
            position: absolute;
        }
        .video-player {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            position: absolute;
            top: 0;
            border-radius: 7px;
        }
        .poster {
            position: absolute;
            width: 100%;
            height: 100%;
            background: url(../media/sintel.jpg) no-repeat center top;
            top: 0;
            background-size: cover;
            z-index: 10;
        }
        .intro {
            position: absolute;
            width: 100%;
            bottom: 0;
            z-index: 20;
            display: -webkit-flex;
            display: flex;
            -webkit-align-items: center;
            align-items: center;
            min-height: 120px;
            background: #000;
            border-radius: 0 0 7px 7px;
            -webkit-transform: translate3d(0, 100%, 0);
            transform: translate3d(0, 100%, 0);
            -webkit-transition: -webkit-transform 0.3s;
            transition: transform 0.3s;
        }
        .intro--shown {
            -webkit-transform: translate3d(0, 0, 0);
            transform: translate3d(0, 0, 0);
        }
        .intro__side {
            width: 50%;
            padding: 0em 1em;
        }
        .intro__side:first-child {
            border-right: 1px solid #121213;
        }
        .intro__side:last-child {
            text-align: center;
        }
        .intro__title {
            padding: 0 0 0 3em;
            margin: 0 0 0 0.75em;
            background: url(../img/camera.svg) no-repeat 0% 50%;
            background-size: auto 70%;
        }
        .intro__up {
            display: block;
            font-size: 0.65em;
            color: #393941;
        }
        .intro__down {
            font-size: 1.5em;
            font-weight: 700;
            color: #adadad;
        }
        .intro__partial {
            font-size: 0.5em;
            color: #63636F;
        }
        .action {
            border: none;
            padding: 0;
            background: none;
            margin: 0;
            font-size: 2em;
            font-weight: 700;
            color: #fff;
        }
        .action:hover,
        .action:focus {
            outline: none;
        }
        .action--seats {
            padding: 0.5em 0 0.5em 2.5em;
            margin: 0 0 0 1em;
            color: #21c252;
            margin: 0 auto;
            display: inline-block;
            background: url(../img/seats.svg) no-repeat 0% 50%;
            background-size: auto 70%;
        }
        .action--play {
            position: absolute;
            pointer-events: none;
            z-index: 30;
            top: 50%;
            left: 50%;
            width: 2.5em;
            height: 2.5em;
            margin-top: -20px;
            opacity: 0;
            -webkit-transform: translate3d(-50%, -50%, 0);
            transform: translate3d(-50%, -50%, 0);
            background: url(../img/play.svg) no-repeat 0% 50%;
            background-size: auto 100%;
            -webkit-transition: opacity 0.3s;
            transition: opacity 0.3s;
        }
        .action--lookaround {
            position: fixed;
            pointer-events: none;
            z-index: 100;
            top: 50%;
            left: 50%;
            width: 2em;
            height: 2em;
            margin-top: -20px;
            opacity: 0;
            -webkit-transform: translate3d(-50%, -50%, 0) translate3d(0, 10px, 0);
            transform: translate3d(-50%, -50%, 0) translate3d(0, 10px, 0);
            background: url(../img/lookaround.svg) no-repeat 50% 50%;
            background-size: auto 80%;
            border-radius: 50%;
            border: 4px solid transparent;
            -webkit-transition: opacity 0.3s, -webkit-transform 0.3s;
            transition: opacity 0.3s, transform 0.3s;
        }
        .action--lookaround.action--disabled {
            border-color: rgba(72, 72, 78, 0.5);
        }
        .action--lookaround.action--shown {
            -webkit-transform: translate3d(-50%, -50%, 0);
            transform: translate3d(-50%, -50%, 0);
        }
        .action--shown {
            opacity: 1;
            pointer-events: auto;
        }
        .action--shown.action--faded {
            opacity: 0.2;
        }
        .action--buy {
            margin: 1em auto;
            background: #26AD4F;
            font-size: 1em;
            width: 100%;
            padding: 0.75em 1.5em;
            border-radius: 3px;
            display: block;
            max-width: 230px;
        }
        .action--buy:hover {
            background: #21c252;
        }
        .no-preserve3d .header {
            display: block;
            text-align: center;
            position: relative;
            padding: 2em;
            background: transparent;
        }
        .no-preserve3d .header__title {
            padding: 1em 0 0;
            font-size: 2em;
        }
        .no-preserve3d .container {
            display: none;
        }
        .no-preserve3d .plan {
            -webkit-transform: translate3d(0, 0, 0);
            transform: translate3d(0, 0, 0);
            position: relative;
            margin: 0 auto;
        }
        @media screen and (max-width: 50em), screen and (max-height: 38em) {
            .header {
                display: block;
                text-align: center;
                position: relative;
                padding: 2em;
                background: transparent;
            }
            .header__title {
                padding: 1em 0 0;
                font-size: 2em;
            }
            .note--screen {
                display: block;
            }
            .container {
                visibility: hidden;
                pointer-events: none;
                z-index: -1;
                position: absolute;
            }
            .plan {
                -webkit-transform: translate3d(0, 0, 0);
                transform: translate3d(0, 0, 0);
                position: relative;
                margin: 0 auto;
            }
            .rows--mini .row__seat:hover {
                background: rgba(72, 72, 78, 0.5);
            }
            .rows--mini .row__seat--selected:hover {
                background: #21c252;
            }
            .github-icon {
                position: fixed;
                top: 20px;
                right: 20px;
                z-index: 1000;
            }
        }
        .tipso_bubble,.tipso_bubble>.tipso_arrow{-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box}.tipso_bubble{position:absolute;text-align:center;border-radius:6px;z-index:9999;padding:10px}.tipso_style{cursor:help;border-bottom:1px dotted}.tipso_bubble>.tipso_arrow{position:absolute;width:0;height:0;border:8px solid;pointer-events:none}.tipso_bubble.top>.tipso_arrow{border-color:#000 transparent transparent;top:100%;left:50%;margin-left:-8px}.tipso_bubble.bottom>.tipso_arrow{border-color:transparent transparent #000;bottom:100%;left:50%;margin-left:-8px}.tipso_bubble.left>.tipso_arrow{border-color:transparent transparent transparent #000;top:50%;left:100%;margin-top:-8px}.tipso_bubble.right>.tipso_arrow{border-color:transparent #000 transparent transparent;top:50%;right:100%;margin-top:-8px}

        .selectedSty {
            width: 35px;
            height: 35px;
            background-color: #eedf2b;
            color: white;
            font-size: 11px;
        }

        .moduleStyle1 {
            background-color: #676269;
        }

        .moduleStyle2 {
            background-color: #845B93;
        }
    </STYLE>
</head>
<body class="easyui-layout bodySelectNone" id="body" onselectstart="return false">
<div id="title" region="north" split="false" border="false" class="titleTool" style="background: #f1f1f1;">
    <span style="font-size: 20px;color: black">当前机房：${room.sitename}</span>
    <span style="font-size: 20px;color: black;margin-left: 100px;">机架总数：${rackStatistics.RACKCOUNT}</span>
    <span style="font-size: 20px;color: black;margin-left: 100px;">占用机架数：${rackStatistics.UNFREERACKNUM}</span>
</div>
<div region="south" split="false" border="false" class="titleTool" style="height: 50px">
    <ul class="legend">
        <li class="legend__item">未安装机架</li>
        <li class="legend__item legend__item--cannotuse">不可用</li>
        <li class="legend__item legend__item--canuse">可用</li>
        <li class="legend__item legend__item--perfree">预留</li>
        <li class="legend__item legend__item--free">空闲</li>
        <li class="legend__item legend__item--preused">预占</li>
        <li class="legend__item legend__item--stop">已停机</li>
        <li class="legend__item legend__item--runing">在服</li>
    </ul>
</div>
<div region="east" class="history" style="width: 360px">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'south'" style="height:250px">
            <div id="piediv"></div>
        </div>
        <div data-options="region:'center',title:'',iconCls:'icon-ok'">
            <table id="pg" class="easyui-propertygrid" fit="true"
                   data-options="showGroup:true,scrollbarSize:0"></table>
        </div>

    </div>
</div>
<div region="center" split="true" title="" class="history" style="position: relative;overflow: auto">
    <div style="width: 900px;height: 540px;">

        <div class="rows rows--mini" id="rowsdiv">
            <%-- 创建底层格子 --%>
            <mycell v-for="trow in celldata" :data="trow" :key="trow.key" style="position: absolute;z-index: 1;width: 39px;height: 39px;background: rgba(72, 72, 78, 0.5);opacity: 1.5;
                   margin: 0 0 1px 1px;border-radius: 1px;">
            </mycell>
            <%-- 创建机架 --%>
            <rackcell v-for="trow in rackata" :data="trow" :key="trow.data.id"
                      style="position: absolute;z-index: 3;width: 35px;height: 35px;cursor: pointer">
            </rackcell>
            <%-- 创建模块 --%>
            <moudle v-for="trow in moudata" :data="trow" key="trow.n" style="position: absolute;z-index: 2;
                   margin: 0 0 1px 1px;border-radius: 1px;">
            </moudle>
        </div>
    </div>
</div>
</body>
<script src="<%=request.getContextPath() %>/js/roomlayout/js/echar.min.js" type="text/javascript"></script>
<script type="text/x-template" id="Mycelltmpl">
    <div :style="{left:left+'px',top:top+'px'}" ></div>
</script>
<script type="text/x-template" id="rackcelltmpl">
    <div v-else :data-mytooltip="data.data.name" :data-tipso="data.data.name"
         @click="clickrow"
         @dblclick="dbclickrow"
         :class="getStatus" :style="{left:left+'px',top:top+'px'}">
        <span v-if="selected==false" style="color: white;font-size: 11px;margin-left: 2px;word-wrap: break-word;">{{namestr}}</span>
        <div v-if="selected" class="selectedSty"><span
                style="margin-left: 2px;word-wrap: break-word;">{{namestr}}</span></div>
    </div>
</script>
<script type="text/x-template" id="moudle">
    <div :class="getModuleClass" :style="{left:left+'px',top:top+'px',width:width+'px',height:height+'px'}" style="position: relative">
        <div style="float:right;position:relative;right:10px;top:20px;color: white;font-weight:400 ;font-size: 30px;">
            {{moduleName}}
        </div>
    </div>
</script>
<script type="text/javascript">
    var action = "${action}";
    var roomid = "${roomid}";
    var roomName = "${room.sitename}";
    var resultList = ${resultList};
    var resultModuleList = ${resultModuleList};
    var idcRacks = ${idcRacks};
    var rackTotal = "${rackStatistics.RACKCOUNT}";
    var roomInfo = [];
    var timeFn = null;
</script>
<script src="<%=request.getContextPath() %>/js/roomlayout/js/roomlayoutview.js" type="text/javascript"></script>
</html>