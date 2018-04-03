<%--
  Created by IntelliJ IDEA.
  User: mylove
  Date: 2017/5/24
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script src="<%=request.getContextPath() %>/framework/vuejs/vue.min.js" type="text/javascript"></script>
    <%--<link href="<%=request.getContextPath() %>/js/roomlayout/css/bootstrap.css" type="text/css" rel="stylesheet" />--%>
    <%--<link href="<%=request.getContextPath() %>/js/roomlayout/css/bootstrap-responsive.css" type="text/css" rel="stylesheet" />--%>
    <%--<link id="flowPath" href="<%=request.getContextPath() %>/js/roomlayout/css/editor.css" type="text/css" rel="stylesheet" />--%>
    <%--<link href="<%=request.getContextPath() %>/js/roomlayout/css/jquery.alerts.css" type="text/css" rel="stylesheet" />--%>
    <%--<link href="<%=request.getContextPath() %>/js/roomlayout/css/header.css" type="text/css" rel="stylesheet" />--%>
    <%--<link href="<%=request.getContextPath() %>/js/roomlayout/css/main_topology.css" type="text/css" rel="stylesheet" />--%>
    <%--<link href="<%=request.getContextPath() %>/js/roomlayout/css/jquery.ipinput.css" type="text/css" rel="stylesheet"/>--%>
    <STYLE>
        /* two seats on each side for free space */
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
            /* depth */
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
            /* leave a margin */
        }

        .rows--mini {
            /* width: 830px; */
            /* margin: 0 auto; */
        }

        .row {
            display: -webkit-flex;
            /* display: flex; */
            width: 100%;
        }

        .rows--large .row {
            left: 0;
            height: 100%;
            position: absolute;
            -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
        }

        .row_seet_important {
            float: left !important;
            border: 1px solid white !important;
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
            /* margin: 0 1px 1px 0px;*/
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

        <%--272424--%>
        .rows--mini .row__seat--cannotuse,
        .rows--mini .row__seat--cannotuse:hover {
            background: #756262;
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

        /*.rows--mini .row__seat:nth-child(9) {*/
        /*margin-right: 15px;*/
        /*}*/

        /*.rows--large .row__seat:nth-child(9) {*/
        /*margin-right: 160px;*/
        /*}*/

        /* Row positioning */
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

        /* Second back row set of seats (starting point needs to have the previous sums of the Y and Z translates) */
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
            /* scale avoids flickering */
            -webkit-transition: -webkit-transform 0.6s;
            transition: transform 0.6s;
        }

        .plan--shown {
            -webkit-transform: scale3d(1, 1, 1);
            transform: scale3d(1, 1, 1);
            /* scale avoids flickering */
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
            background: #756262;
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

        /* mytooltip */
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

        /*.rows--mini .row:nth-child(5) {*/
        /*margin: 0 0 10px 0;*/
        /*}*/
        /*.rows--mini .row__seat:nth-child(7) {*/
        /*margin-right: 15px;*/
        /*}*/

        /*.row__seat::before {*/
        /*content: attr(data-mytooltip);*/
        /*bottom: 100%;*/
        /*border: solid transparent;*/
        /*por*/
        /*/!*height: 0;*!/*/
        /*/!*width: 0;*!/*/
        /*z-index: 1001;*/
        /*pointer-events: none;*/
        /*border-color: transparent;*/
        /*border-top-color: #57e683;*/
        /*border-width: 6px;*/
        /*margin-left: -6px;*/
        /*-webkit-transform: translate3d(0, 5px, 0);*/
        /*transform: translate3d(0, 5px, 0);*/
        /*}*/

        /*.row__seat:hover::before {*/
        /*-webkit-transform: translate3d(0, 0, 0);*/
        /*transform: translate3d(0, 0, 0);*/
        /*}*/

        /*.row__seat::after {*/
        /*content: attr(data-mytooltip);*/
        /*background: #57e683;*/
        /*border-radius: 3px;*/
        /*color: #fff;*/
        /*font-weight: bold;*/
        /*z-index: 1000;*/
        /*font-size: 14px;*/
        /*padding: 8px 10px;*/
        /*bottom: 100%;*/
        /*box-shadow: 4px 4px 8px rgba(0, 0, 0, 0.3);*/
        /*-webkit-transform: translate3d(-50%, -5px, 0);*/
        /*transform: translate3d(-50%, -5px, 0);*/
        /*}*/

        /*.row__seat:hover::after {*/
        /*-webkit-transform: translate3d(-50%, -10px, 0);*/
        /*transform: translate3d(-50%, -10px, 0);*/
        /*}*/

        /* Screen & video */
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

        /* Fallback */
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
    </STYLE>
</head>

<body class="bodySelectNone" id="body" onselectstart="return false" >
<div title='当前机房：${room.sitename}' class="easyui-panel" fit="true" >
    <div class="easyui-layout" fit="true">
        <div region="north" border="false" style="width:1810px;height:1920px;overflow: hidden">
            <div class="rows rows--mini" id="rowsdiv">
                <div class="row" v-for="row in rows">
                    <cell :key="cell.id" v-for="cell in row.cells" :data="cell.data"></cell>
                </div>
            </div>
        </div>
        <div region="center" fit="true">
            <ul class="legend" style="float: left;margin-left: 100px;">
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
    </div>
</div>
</body>
<script src="<%=request.getContextPath() %>/js/roomlayout/js/echar.min.js" type="text/javascript"></script>
<script type="text/x-template" id="celltmpl">
    <div class="row__seat row_seet_important" v-if="typeof(data.status)=='undefined'"
         @drop.navite="dropIn"
         :class="getStatus"></div>
    <div class="row__seat row_seet_important" :draggable="draggable" v-else :data-mytooltip="data.name"
         :data-tipso="data.name"
         @click="clickrow"
         @dblclick="dbclickrow"
         @dragstart='drag($event)'
         @dragenter="ondragenter($event)"
         :class="getStatus">
        <span v-if="selected==false" style="color: white;font-size: 11px;margin-left: 2px;word-wrap: break-word;">{{namestr}}</span>
        <div v-if="selected" style="width:38px;height: 38px;background-color: eedf2b;word-wrap: break-word;">
            {{namestr}}
        </div>
        <%--<img v-if="selected" src="<%=request.getContextPath() %>/page/roomlayout/select.png" style="width:35px;height: 35px"/>--%>
    </div>
    <%--<div class="row__seat"  draggable="true" :class="getStatus(cell.data)" @click="clickrow(cell.data)" @dblclick="dbclickrow(cell.data)" :data-mytooltip="cell.data.name"--%>
    <%--v-for="cell in row.cells"></div>--%>
</script>
<script type="text/javascript">
    var action = "${action}";
    var roomid = "${roomid}";
    var planstr = ${plan.rows};
    var timeFn = null;
    var xStart = 0,yStart=0,xEnd=0,yEnd=0,isShiftDown=false,isCtrlDown=false;
    //资源分配类型
    var distriType = "${distriType}";
    console.log(distriType);
</script>
<script src="<%=request.getContextPath() %>/js/roomlayout/js/roomlayout.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
        var $$ = function(selector) {
            if (!selector) { return []; }
            var arrEle = [];
            if (document.querySelectorAll) {
                arrEle = document.querySelectorAll(selector);
            } else {
                var oAll = document.getElementsByTagName("div"), lAll = oAll.length;
                if (lAll) {
                    var i = 0;
                    for (i; i<lAll; i+=1) {
                        if (/^\./.test(selector)) {
                            if (oAll[i].className === selector.replace(".", "")) {
                                arrEle.push(oAll[i]);
                            }
                        } else if(/^#/.test(selector)) {
                            if (oAll[i].id === selector.replace("#", "")) {
                                arrEle.push(oAll[i]);
                            }
                        }
                    }
                }
            }
            return arrEle;
        };
        var eleDrag = null;
    })
</script>
</html>