<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <title>端口面板</title>
    <style type="text/css">
        .demo {
            width: 700px;
            margin: 10px auto 0 auto;
            min-height: 250px;
        }

        @media screen and (max-width: 360px) {
            .demo {
                width: 340px
            }
        }

        .front {
            width: 300px;
            margin: 5px 32px 45px 32px;
            background-color: #f0f0f0;
            color: #666;
            text-align: center;
            padding: 3px;
            border-radius: 5px;
        }

        .booking-details {
            float: right;
            position: relative;
            width: 200px;
            /*height: 450px;*/
        }

        .booking-details h3 {
            margin: 5px 5px 0 0;
            font-size: 16px;
        }

        .booking-details p {
            line-height: 26px;
            font-size: 16px;
            color: #999
        }

        .booking-details p span {
            color: #666
        }

        div.seatCharts-cell {
            color: #182C4E;
            height: 25px;
            width: 25px;
            line-height: 25px;
            margin: 3px;
            float: left;
            text-align: center;
            outline: none;
            font-size: 13px;
        }

        div.seatCharts-seat {
            color: #fff;
            cursor: pointer;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
        }

        div.seatCharts-row {
            height: 35px;
        }

        div.seatCharts-seat.available {
            background-color: #B9DEA0;
        }

        div.seatCharts-seat.focused {
            background-color: #76B474;
            border: none;
        }

        div.seatCharts-seat.selected {
            background-color: #E6CAC4;
        }

        div.seatCharts-seat.unavailable {
            background-color: #472B34;
            cursor: not-allowed;
        }

        div.seatCharts-container {
            border-right: 1px dotted #adadad;
            width: 400px;
            padding: 20px;
            float: left;
        }

        div.seatCharts-legend {
            padding-left: 0px;
            position: absolute;
            bottom: 16px;
        }

        ul.seatCharts-legendList {
            padding-left: 0px;
        }

        .seatCharts-legendItem {
            float: left;
            width: 90px;
            margin-top: 10px;
            line-height: 2;
        }

        span.seatCharts-legendDescription {
            margin-left: 5px;
            line-height: 30px;
        }

        .checkout-button {
            display: block;
            width: 80px;
            height: 24px;
            line-height: 20px;
            margin: 10px auto;
            border: 1px solid #999;
            font-size: 14px;
            cursor: pointer
        }

        #selected-seats {
            max-height: 150px;
            overflow-y: auto;
            overflow-x: none;
            width: 200px;
        }

        #selected-seats li {
            float: left;
            width: 72px;
            height: 26px;
            line-height: 26px;
            border: 1px solid #d3d3d3;
            background: #f7f7f7;
            margin: 6px;
            font-size: 14px;
            font-weight: bold;
            text-align: center
        }

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

        .row__seat {
            -webkit-flex: none;
            flex: none;
            height: 100%;
            margin: 0;
        }

        .rows--mini .row__seat {
            width: 24px;
            height: 24px;
            background: rgba(72, 72, 78, 0.5);
            margin: 0 0 1px 1px;
            border-radius: 30px;
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
            background: #69deb4;
        }

        .rows--mini .row__seat--free,
        .rows--mini .row__seat--free:hover {
            background: #92ded1;
        }

        .rows--mini .row__seat--preused,
        .rows--mini .row__seat--preused:hover {
            background: #beb6de;
        }

        .rows--mini .row__seat--stop,
        .rows--mini .row__seat--stop:hover {
            background: #de6363;
        }

        .rows--mini .row__seat--runing,
        .rows--mini .row__seat--runing:hover {
            background: #76c23c;
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
            background: #272424;
        }

        .legend__item--canuse::before {
            background: #86de2b;
        }

        .legend__item--perfree::before {
            background: #69deb4;
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

        .tipso_bubble, .tipso_bubble > .tipso_arrow {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box
        }

        .tipso_bubble {
            position: absolute;
            text-align: center;
            border-radius: 6px;
            z-index: 9999;
            padding: 10px
        }

        .tipso_style {
            cursor: help;
            border-bottom: 1px dotted
        }

        .tipso_bubble > .tipso_arrow {
            position: absolute;
            width: 0;
            height: 0;
            border: 8px solid;
            pointer-events: none
        }

        .tipso_bubble.top > .tipso_arrow {
            border-color: #000 transparent transparent;
            top: 100%;
            left: 50%;
            margin-left: -8px
        }

        .tipso_bubble.bottom > .tipso_arrow {
            border-color: transparent transparent #000;
            bottom: 100%;
            left: 50%;
            margin-left: -8px
        }

        .tipso_bubble.left > .tipso_arrow {
            border-color: transparent transparent transparent #000;
            top: 50%;
            left: 100%;
            margin-top: -8px
        }

        .tipso_bubble.right > .tipso_arrow {
            border-color: transparent #000 transparent transparent;
            top: 50%;
            right: 100%;
            margin-top: -8px
        }
    </style>
    <script>
    </script>
</head>
<body>
<div style="width: 600px;height: 350px">
    <c:forEach items="${portMaps}" var="ports">
        <div class="rows rows--mini" id="rowsdiv">
            <c:forEach var="port" items="${ports.value}" varStatus="status">
                <div class="row__seat
            <c:choose>
            <c:when test="${port.portactive !=null&&port.portactive == 1}">row__seat--runing</c:when>
            <c:when test="${port.portactive !=null&&port.portactive == 2}">row__seat--free </c:when>
            </c:choose> " data-mytooltip="${port.portname}" data-tipso="${port.portname}" style="text-align: center">
                    <div style="width: 5px;height: 5px;background-color: black;margin: 10px 10px; border-radius: 4px;"></div>
                </div>
            </c:forEach>
        </div>
    </c:forEach>

    <%--<div class="rows rows--mini" id="rowsdiv">--%>
        <%--<c:forEach var="port" items="${ports}" varStatus="status">--%>
            <%--<div class="row__seat--%>
            <%--<c:choose>--%>
            <%--<c:when test="${port.portactive !=null&&port.portactive == 1}">row__seat--runing</c:when>--%>
            <%--<c:when test="${port.portactive !=null&&port.portactive == 2}">row__seat--free </c:when>--%>
            <%--</c:choose> " data-mytooltip="${port.portname}" data-tipso="${port.portname}">--%>
            <%--</div>--%>
        <%--</c:forEach>--%>
    <%--</div>--%>
    <%--<div class="rows rows--mini" id="rowsdiv">--%>
        <%--<c:forEach var="port" items="${ports}" varStatus="status">--%>
            <%--<div class="row__seat--%>
            <%--<c:choose>--%>
            <%--<c:when test="${port.portactive !=null&&port.portactive == 1}">row__seat--runing</c:when>--%>
            <%--<c:when test="${port.portactive !=null&&port.portactive == 2}">row__seat--free </c:when>--%>
            <%--</c:choose> " data-mytooltip="${port.portname}" data-tipso="${port.portname}">--%>
            <%--</div>--%>
        <%--</c:forEach>--%>
    <%--</div>--%>
</div>
<script type="text/javascript">
    !function (t, o, s, e) {
        function r(o, s) {
            this.element = t(o), this.settings = t.extend({}, l, s), this._defaults = l, this._name = d, this._title = this.element.attr("title"), this.mode = "hide", this.init()
        }

        function i() {
            var t = o.navigator.msMaxTouchPoints, e = "ontouchstart"in s.createElement("div");
            return t || e ? !0 : !1
        }

        function n(o) {
            var s = o.clone();
            s.css("visibility", "hidden"), t("body").append(s);
            var e = s.outerHeight();
            return s.remove(), e
        }

        function a(s) {
            var e, r, i, a = s.tooltip(), d = s.element, l = s, f = t(o), p = 10;
            switch (l.settings.position) {
                case"top":
                    r = d.offset().left + d.outerWidth() / 2 - a.outerWidth() / 2, e = d.offset().top - n(a) - p, a.find(".tipso_arrow").css({marginLeft: -8}), e < f.scrollTop() ? (e = d.offset().top + d.outerHeight() + p, a.find(".tipso_arrow").css({
                        "border-bottom-color": l.settings.background,
                        "border-top-color": "transparent"
                    }), a.removeClass("top bottom left right"), a.addClass("bottom")) : (a.find(".tipso_arrow").css({
                        "border-top-color": l.settings.background,
                        "border-bottom-color": "transparent"
                    }), a.removeClass("top bottom left right"), a.addClass("top"));
                    break;
                case"bottom":
                    r = d.offset().left + d.outerWidth() / 2 - a.outerWidth() / 2, e = d.offset().top + d.outerHeight() + p, a.find(".tipso_arrow").css({marginLeft: -8}), e + n(a) > f.scrollTop() + f.outerHeight() ? (e = d.offset().top - n(a) - p, a.find(".tipso_arrow").css({
                        "border-top-color": l.settings.background,
                        "border-bottom-color": "transparent"
                    }), a.removeClass("top bottom left right"), a.addClass("top")) : (a.find(".tipso_arrow").css({
                        "border-bottom-color": l.settings.background,
                        "border-top-color": "transparent"
                    }), a.removeClass("top bottom left right"), a.addClass(l.settings.position));
                    break;
                case"left":
                    r = d.offset().left - a.outerWidth() - p, e = d.offset().top + d.outerHeight() / 2 - n(a) / 2, a.find(".tipso_arrow").css({
                        marginTop: -8,
                        marginLeft: ""
                    }), r < f.scrollLeft() ? (r = d.offset().left + d.outerWidth() + p, a.find(".tipso_arrow").css({
                        "border-right-color": l.settings.background,
                        "border-left-color": "transparent",
                        "border-top-color": "transparent",
                        "border-bottom-color": "transparent"
                    }), a.removeClass("top bottom left right"), a.addClass("right")) : (a.find(".tipso_arrow").css({
                        "border-left-color": l.settings.background,
                        "border-right-color": "transparent",
                        "border-top-color": "transparent",
                        "border-bottom-color": "transparent"
                    }), a.removeClass("top bottom left right"), a.addClass(l.settings.position));
                    break;
                case"right":
                    r = d.offset().left + d.outerWidth() + p, e = d.offset().top + d.outerHeight() / 2 - n(a) / 2, a.find(".tipso_arrow").css({
                        marginTop: -8,
                        marginLeft: ""
                    }), r + p + l.settings.width > f.scrollLeft() + f.outerWidth() ? (r = d.offset().left - a.outerWidth() - p, a.find(".tipso_arrow").css({
                        "border-left-color": l.settings.background,
                        "border-right-color": "transparent",
                        "border-top-color": "transparent",
                        "border-bottom-color": "transparent"
                    }), a.removeClass("top bottom left right"), a.addClass("left")) : (a.find(".tipso_arrow").css({
                        "border-right-color": l.settings.background,
                        "border-left-color": "transparent",
                        "border-top-color": "transparent",
                        "border-bottom-color": "transparent"
                    }), a.removeClass("top bottom left right"), a.addClass(l.settings.position))
            }
            r < f.scrollLeft() && ("bottom" == l.settings.position || "top" == l.settings.position) && (a.find(".tipso_arrow").css({marginLeft: r - 8}), r = 0), r + l.settings.width > f.outerWidth() && ("bottom" == l.settings.position || "top" == l.settings.position) && (i = f.outerWidth() - (r + l.settings.width), a.find(".tipso_arrow").css({
                marginLeft: -i - 8,
                marginTop: ""
            }), r += i), r < f.scrollLeft() && ("left" == l.settings.position || "right" == l.settings.position) && (r = d.offset().left + d.outerWidth() / 2 - a.outerWidth() / 2, a.find(".tipso_arrow").css({
                marginLeft: -8,
                marginTop: ""
            }), e = d.offset().top - n(a) - p, e < f.scrollTop() ? (e = d.offset().top + d.outerHeight() + p, a.find(".tipso_arrow").css({
                "border-bottom-color": l.settings.background,
                "border-top-color": "transparent",
                "border-left-color": "transparent",
                "border-right-color": "transparent"
            }), a.removeClass("top bottom left right"), a.addClass("bottom")) : (a.find(".tipso_arrow").css({
                "border-top-color": l.settings.background,
                "border-bottom-color": "transparent",
                "border-left-color": "transparent",
                "border-right-color": "transparent"
            }), a.removeClass("top bottom left right"), a.addClass("top")), r + l.settings.width > f.outerWidth() && (i = f.outerWidth() - (r + l.settings.width), a.find(".tipso_arrow").css({
                marginLeft: -i - 8,
                marginTop: ""
            }), r += i), r < f.scrollLeft() && (a.find(".tipso_arrow").css({marginLeft: r - 8}), r = 0)), r + l.settings.width > f.outerWidth() && ("left" == l.settings.position || "right" == l.settings.position) && (r = d.offset().left + d.outerWidth() / 2 - a.outerWidth() / 2, a.find(".tipso_arrow").css({
                marginLeft: -8,
                marginTop: ""
            }), e = d.offset().top - n(a) - p, e < f.scrollTop() ? (e = d.offset().top + d.outerHeight() + p, a.find(".tipso_arrow").css({
                "border-bottom-color": l.settings.background,
                "border-top-color": "transparent",
                "border-left-color": "transparent",
                "border-right-color": "transparent"
            }), a.removeClass("top bottom left right"), a.addClass("bottom")) : (a.find(".tipso_arrow").css({
                "border-top-color": l.settings.background,
                "border-bottom-color": "transparent",
                "border-left-color": "transparent",
                "border-right-color": "transparent"
            }), a.removeClass("top bottom left right"), a.addClass("top")), r + l.settings.width > f.outerWidth() && (i = f.outerWidth() - (r + l.settings.width), a.find(".tipso_arrow").css({
                marginLeft: -i - 8,
                marginTop: ""
            }), r += i), r < f.scrollLeft() && (a.find(".tipso_arrow").css({marginLeft: r - 8}), r = 0)), a.css({
                left: r + l.settings.offsetX,
                top: e + l.settings.offsetY
            })
        }

        var d = "tipso", l = {
            speed: 400,
            background: "#55b555",
            color: "#ffffff",
            position: "top",
            width: 200,
            delay: 200,
            offsetX: 0,
            offsetY: 0,
            content: null,
            ajaxContentUrl: null,
            useTitle: !0,
            onBeforeShow: null,
            onShow: null,
            onHide: null
        };
        t.extend(r.prototype, {
            init: function () {
                var o = this, e = this.element;
                e.addClass("tipso_style").removeAttr("title"), i() ? (e.on("click." + d, function (t) {
                    "hide" == o.mode ? o.show() : o.hide(), t.stopPropagation()
                }), t(s).on("click", function () {
                    "show" == o.mode && o.hide()
                })) : (e.on("mouseover." + d, function () {
                    o.show()
                }), e.on("mouseout." + d, function () {
                    o.hide()
                }))
            }, tooltip: function () {
                return this.tipso_bubble || (this.tipso_bubble = t('<div class="tipso_bubble"><div class="tipso_content"></div><div class="tipso_arrow"></div></div>')), this.tipso_bubble
            }, show: function () {
                var s = this.tooltip(), e = this, r = t(o);
                t.isFunction(e.settings.onBeforeShow) && e.settings.onBeforeShow(t(this)), s.css({
                    background: e.settings.background,
                    color: e.settings.color,
                    width: e.settings.width
                }).hide(), s.find(".tipso_content").html(e.content()), a(e), r.resize(function () {
                    a(e)
                }), e.timeout = o.setTimeout(function () {
                    s.appendTo("body").stop(!0, !0).fadeIn(e.settings.speed, function () {
                        e.mode = "show", t.isFunction(e.settings.onShow) && e.settings.onShow(t(this))
                    })
                }, e.settings.delay)
            }, hide: function () {
                var s = this, e = this.tooltip();
                o.clearTimeout(s.timeout), s.timeout = null, e.stop(!0, !0).fadeOut(s.settings.speed, function () {
                    t(this).remove(), t.isFunction(s.settings.onHide) && "show" == s.mode && s.settings.onHide(t(this)), s.mode = "hide"
                })
            }, destroy: function () {
                var t = this.element;
                t.off("." + d), t.removeData(d), t.removeClass("tipso_style").attr("title", this._title)
            }, content: function () {
                var o, s = this.element, e = this, r = this._title;
                return o = e.settings.ajaxContentUrl ? t.ajax({
                    type: "GET",
                    url: e.settings.ajaxContentUrl,
                    async: !1
                }).responseText : e.settings.content ? e.settings.content : e.settings.useTitle === !0 ? r : s.data("tipso")
            }, update: function (t, o) {
                var s = this;
                return o ? void(s.settings[t] = o) : s.settings[t]
            }
        }), t[d] = t.fn[d] = function (o) {
            var s = arguments;
            if (o === e || "object" == typeof o)return this instanceof t || t.extend(l, o), this.each(function () {
                t.data(this, "plugin_" + d) || t.data(this, "plugin_" + d, new r(this, o))
            });
            if ("string" == typeof o && "_" !== o[0] && "init" !== o) {
                var i;
                return this.each(function () {
                    var e = t.data(this, "plugin_" + d);
                    e || (e = t.data(this, "plugin_" + d, new r(this, o))), e instanceof r && "function" == typeof e[o] && (i = e[o].apply(e, Array.prototype.slice.call(s, 1))), "destroy" === o && t.data(this, "plugin_" + d, null)
                }), i !== e ? i : this
            }
        }
    }(jQuery, window, document);
    $(function () {
        $('.row__seat').each(function () {
            $(this).tipso({useTitle: false});
//            if($(this).attr("data-tipso")!=""&&typeof($(this).attr("data-tipso"))!='undefined'){
//                $(this).tipso({ useTitle: false});
//            }
        })
    })
    <%--$(document).ready(function() {--%>
    <%--var $cart = $('#selected-seats'), //座位区--%>
    <%--$status = $('#status'), //票数--%>
    <%--$portname = $('#portname'); //总计金额--%>
    <%--var space =[];--%>
    <%--var tmp = [];--%>
    <%--var map = [];--%>
    <%--var mapstr = [];--%>
    <%--<c:forEach var="port" items="${ports}" varStatus="status">--%>
    <%--tmp.push({--%>
    <%--id:'${port.portid}',--%>
    <%--name:'${port.portname}',--%>
    <%--status:'${port.portactive}'--%>
    <%--});--%>
    <%--mapstr.push('a');--%>
    <%--<c:if test="status.index%10==0">--%>
    <%--space.push(tmp);--%>
    <%--map.push(mapstr.join(""));--%>
    <%--tmp = new Array();--%>
    <%--mapstr  = new Array();--%>
    <%--</c:if>--%>
    <%--</c:forEach>--%>
    <%--space.push(tmp);--%>
    <%--map.push(mapstr.join(""));--%>
    <%--var sc = $('#seat-map').seatCharts({--%>
    <%--map:map,--%>
    <%--//            map: [  //座位图--%>
    <%--//                'aaaaaaaaaa',--%>
    <%--//                'aaaaaaaaaa',--%>
    <%--//                '__________',--%>
    <%--//                'aaaaaaaa__',--%>
    <%--//                'aaaaaaaaaa',--%>
    <%--//                'aaaaaaaaaa',--%>
    <%--//                'aaaaaaaaaa',--%>
    <%--//                'aaaaaaaaaa',--%>
    <%--//                'aaaaaaaaaa',--%>
    <%--//                'aa__aa__aa'--%>
    <%--//            ],--%>
    <%--naming : {--%>
    <%--top : false--%>
    <%--},--%>
    <%--//            legend : { //定义图例--%>
    <%--//                node : $('#legend'),--%>
    <%--//                items : [--%>
    <%--//                    [ 'a', 'available',   '在用'],--%>
    <%--//                    [ 'a', 'free',   '空闲' ],--%>
    <%--//                    [ 'a', 'test',   '测试' ]--%>
    <%--//                ]--%>
    <%--//            },--%>
    <%--click: function () { //点击事件--%>
    <%--$('#seat-map').seatCharts().each(function(index,data){--%>
    <%--this.status('available');--%>
    <%--});--%>
    <%--var row  = space[this.settings.row][this.settings.label-1];--%>
    <%--$portname.text(row.name);--%>
    <%--$status.text(getStatus(row.status));--%>
    <%--//                if (this.status() == 'available') { //可选座--%>
    <%--////                    $('<li>'+(this.settings.row+1)+'排'+this.settings.label+'座</li>')--%>
    <%--////                            .attr('id', 'cart-item-'+this.settings.id)--%>
    <%--////                            .data('seatId', this.settings.id)--%>
    <%--////                            .appendTo($cart);--%>
    <%--//--%>
    <%--//                    $counter.text(sc.find('selected').length+1);--%>
    <%--//                    $total.text(recalculateTotal(sc)+price);--%>
    <%--//--%>
    <%--return 'selected';--%>
    <%--//                } else if (this.status() == 'selected') { //已选中--%>
    <%--//                    //更新数量--%>
    <%--//                    $counter.text(sc.find('selected').length-1);--%>
    <%--//                    //更新总计--%>
    <%--//                    $total.text(recalculateTotal(sc)-price);--%>
    <%--//--%>
    <%--//                    //删除已预订座位--%>
    <%--//                    $('#cart-item-'+this.settings.id).remove();--%>
    <%--//                    //可选座--%>
    <%--//                    return 'available';--%>
    <%--//                } else if (this.status() == 'unavailable') { //已售出--%>
    <%--//                    return 'unavailable';--%>
    <%--//                } else {--%>
    <%--//                    return this.style();--%>
    <%--//                }--%>
    <%--}--%>
    <%--});--%>
    <%--//已售出的座位--%>
    <%--//        sc.get(['1_2', '4_4','4_5','6_6','6_7','8_5','8_6','8_7','8_8', '10_1', '10_2']).status('unavailable');--%>

    <%--});--%>
    <%--function getStatus(status){--%>
    <%--if(status==1){--%>
    <%--return '在用'--%>
    <%--}--%>
    <%--if(status==2){--%>
    <%--return '空闲'--%>
    <%--}--%>
    <%--return ''--%>
    <%--}--%>
    <%--//计算总金额--%>
    <%--function recalculateTotal(sc) {--%>
    <%--var total = 0;--%>
    <%--sc.find('selected').each(function () {--%>
    <%--total += price;--%>
    <%--});--%>

    <%--return total;--%>
    <%--}--%>
</script>
</body>
</html>