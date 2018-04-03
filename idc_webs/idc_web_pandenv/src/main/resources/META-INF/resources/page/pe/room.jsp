<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/black/easyui.css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_page.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/main.css"
          rel="stylesheet" type="text/css"/>
    <title>能耗分析</title>
    <script src="<%=request.getContextPath() %>/framework/layui/layui.js"></script>
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/framework/layui/css/layui.css"/>
    <style>
        body {
            background-color: #000;
        }

        .idcpanel {
            /*width: 90%;*/
            /*height: 100%;*/
            display: -webkit-flex;
            /* Safari */
            display: flex;
            flex-wrap: wrap;
            /*justify-content: lef;*/
        }

        .idc {
            width: 550px;
            height: 250px;
            /* //float: left; */
            margin-left: 5px;
            margin-top: 20px;
            border: 1px solid black;
            display: -webkit-flex;
            /* Safari */
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;
            flex-flow: row;

            /*伸缩项目单行排列*/
        }

        .idc img {
            border-bottom: 1px solid black;
            border-right: 2px dotted black;
            width: 350px;
            height: 250px;
            cursor: pointer
        }

        .idc div {
            flex: 1;
            /*这里设置为占比1，填充满剩余空间*/
        }

        .title {
            text-align: center;
            border-bottom: 1px solid black;
            font-size: 18px;

        }

        .pro {
            margin-left: 5px;
            margin-top: 10px;
        }

        /*.floars {*/
        /*display: none;*/
        /*}*/

        .floar {
            /*width: 100%;*/
            /* height: 200px; */
            /* //float: left; */
            /*margin-left: 5px;*/
            margin-bottom: 10px;
            border: 1px solid black;
            display: -webkit-flex;
            /* Safari */
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;
            background-color: rgba(72, 72, 78, 0.5);
            color: white;
            /* flex-flow: row; */
            /*伸缩项目单行排列*/
        }

        .floars :last-child {
            margin-bottom: 0px;
        }

        .floartitle {
            width: 100px;
            font-size: 40px;
        }

        .rooms {
            flex: 1;
            /* //width: 90%; */
            display: -webkit-flex;
            /* Safari */
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;

        }

        .room {
            width: 100px;
            height: 95px;
            border: 1px solid black;
            margin: 1px;
            padding: 3px;
            background-color: rgb(103, 98, 105);
        }

        .select {
            background-color: #92ded1
        }

        .room:hover {
            /* border: 2px solid #2E82D1;
            height: 93px;
            width: 98px; */
            background-color: #92ded1
        }

        .roomtitle {
            font-size: 12px;
            font-weight: bold;
            word-wrap: break-word;
        }

        .roomdesc {
            margin: 2px;
            font-size: 12px;
        }

        .layout-body {

            min-width: 1px;
            min-height: 1px;

        }

        .panel-body {
            background-color: rgb(73, 73, 73);
            color: #000000;
            font-size: 12px;
        }

        h5 {
            color: #fff
        }

        h6 {
            color: #fff
        }

        .pedesc div {
            margin: 10px
        }
    </style>
    <script>

    </script>
</head>
<body>
<div class="easyui-layout" fit="true" style="overflow-x:hidden;background-color: rgb(73,73,73)">
    <div class="content_suspend">
        <div class="conter">
            <ul id="rtree" class="ztree" style="width:200px; overflow:auto;"></ul>
        </div>
        <div class="hoverbtn">
            <span>导</span><span>航</span>

            <div class="hoverimg" height="9" width="13"></div>
        </div>
    </div>
    <div data-options="region:'north',collapsible:false" style="height: 260px" title="机楼(Kw/h)">
        <div class="floars">

        </div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'west'" style="width: 30%">
                <div id="pie"></div>
            </div>
            <div data-options="region:'center'" border="false" style="color:#fff">
                <div class="easyui-layout" fit="true" id="chartlayout">
                    <div data-options="region:'north'" border="false"
                         style="height:38px;padding:5px 15px 5px 5px;color:#fff;text-align: center;border-bottom: 1px solid white">
                        查看日期:<input id="datetime" type="text" class="easyui-datebox"
                            />
                        <select id="dataType" class="easyui-combobox">
                            <option value="0">按日(当前日期最近1月)</option>
                            <option value="1">按月(当前日期最近3月)</option>
                        </select>
                        <%--<input id="cyc" class="easyui-switchbutton" data-options="onText:'当日',offText:'当月'"/>--%>
                    </div>
                    <div data-options="region:'west'" border="false" style="width:300px">
                        <div style="height:26px;margin-top:6px;color: #fff;font-size: 20px">
                            <span style="width: 40%">用电统计：</span>
                            <div style="font-size: 10px;color:yellow">
                                <div>PUE:主设备+空调/主设备</div>
                                <div>单位:Kw/h</div>
                            </div>
                        </div>
                        <div class="title_desc2" style="margin-top:5px;">
                            <div class="width100 box-sizing  pedesc" style="float:left;">
                                <%--<h5 class="width100" style="float:left;text-align: center;font-weight:bold">硬盘</h5>--%>

                                <div>
                                    <h6 class="width50" style="text-align:right">总能耗：</h6><h6 class="width50 all">
                                    1</h6>
                                </div>
                                <div>
                                    <h6 class="width50" style="text-align:right">主设备：</h6><h6 class="width50 device">
                                    1</h6>
                                </div>
                                <div>
                                    <h6 class="width50" style="text-align:right">空调：</h6><h6
                                        class="width50 air">0</h6>
                                </div>
                                <div>
                                    <h6 class="width50" style="text-align:right">其他：</h6><h6
                                        class="width50 other">0</h6>
                                </div>
                                <div>
                                    <h6 class="width50" style="text-align:right">PUE：</h6><h6 class="width50 pue">
                                    1</h6>
                                </div>
                                <div>
                                    <h6 class="width50" style="text-align:right">能耗环比：</h6><h6
                                        class="width50 add">0</h6>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div data-options="region:'center'" border="false" data-options="onResize:function(width,height){

                    }">
                        <div id="line"></div>
                    </div>
                </div>
                <%--<div style="height: 30px;padding-top: 8px;padding-left: 10px">--%>
                <%--查看日期:<input  id="dd"  type= "text" class= "easyui-datebox" required ="required" style="width:100px"> </input>--%>
                <%--<input class="easyui-switchbutton" data-options="onText:'当日',offText:'当月'">--%>
                <%--</div>--%>

            </div>

        </div>


        <%--<div class="layui-row">--%>
        <%--<div class="layui-col-xs3">--%>
        <%--<div id="pie">1</div>--%>
        <%--</div>--%>
        <%--<div class="layui-col-xs4">2</div>--%>
        <%--<div class="layui-col-xs5">--%>
        <%--<div id="line">3</div>--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
</div>
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
    //一般直接写在一个js文件中
    layui.use(['layer', 'element'], function () {
        var element = layui.element;
    });
    $(function () {
        try {
            top.hideLeft();
        } catch (e) {
        }
        $('#chartlayout').layout('panel','center').panel({
            onResize:function(w,h){
                $("#line").css({"width": $("#line").parent().width() - 80, "height": $("#line").parent().height() - 50});  //获取p元素的样式颜色
                var chart = echarts.getInstanceByDom(document.getElementById("line"));
                if(chart){
                    var width = $("#line").parent().width();
                    var height = $("#line").parent().height();
                    chart.resize({width: width - 20, height: height});
                }
            }
        });
        var date = new Date();
        $("#datetime").datebox({value: date.add("d", -1).Format("yyyy-MM-dd")})
        var tree = $("#rtree").rtree({
            r_type: 1
            , onClick: function (iteam, treeId, treeNode) {
                if (treeNode.id.indexOf("building_") > -1) {
                    var buildid = treeNode.id.split("building_")[1];
                    $.getJSON(contextPath + "/pe/getRooms/" + buildid, function (data) {
                        $(".floars").empty();
                        for (var k in data) {
                            var floar = $('<div class="floar"></div>');
                            var floartitle = $('<div class="floartitle">' + k + 'F</div>');
                            floar.append(floartitle)
                            var rooms = $(' <div class="rooms"></div>');
                            floar.append(rooms)
                            for (var i = 0; i < data[k].length; i++) {
                                var room = $('<div class="room" roomid="' + data[k][i].roomid + '">' +
                                        '<div class="roomtitle"><a href="javascript:void(0)" onclick="showRoom(' + data[k][i].roomid + ')">' + data[k][i].roomName + '</a></div>' +
                                        '<div class="roomdesc">机房PUE:' + data[k][i].pue + '</div>' +
                                        '<div class="roomdesc">用电量:' + data[k][i].electric + '</div> ' +
                                        '</div>');
                                rooms.append(room);
                            }
                            $(".floars").append(floar);
                        }
                        $(".floar:first-child").find(".room:first-child").click();
                    });
                }
            }
        });

        var roomid = 0;
        var isDay = 0
        var getPieData = function (pue) {
            if (typeof(pue) == 'undefined') {
                pue = 1;
            }
            var chart = echarts.getInstanceByDom(document.getElementById("pie"));
            var option = chart.getOption();
            option.series[3].data = [{
                value: pue.toFixed(2),
                name: ''
            }]
            chart.setOption(option)
//            $.post(contextPath+"/pe/getPUE/"+roomid,function(data){
//                var chart = echarts.getInstanceByDom(document.getElementById("pie"));
//                var option = chart.getOption();
//                option.series[3].data=[{
//                    value: data.toFixed(1),
//                    name: ''
//                }]
//                chart.setOption(option)
//            })
        }
        var getLineData = function () {
            var datetime = $("#datetime").datebox("getValue");
            $.post(contextPath + "/pe/getLine/" + roomid, {date: datetime, cyc: isDay}, function (data) {
                var xdata = data.datestrs;
                var values = data.values;
                var pues = data.pues;
                var devices = data.devices;
                var airs = data.airs;
                var chart = echarts.getInstanceByDom(document.getElementById("line"));
                var option = chart.getOption();
                option.xAxis[0].data = xdata;
                if(isDay==0){
                    option.title.text = '最近一月用电及PUE';
                }else{
                    option.title.text = '最近6月用电及PUE';
                }
                option.series[0].data = values
                option.series[1].data = pues;
                option.series[2].data = devices;
                option.series[3].data = airs
                chart.setOption(option)
                if (typeof(data.all) != 'undefined') {
                    $(".all").text(data.all)
                } else {
                    $(".all").text("")
                }
                if (typeof(data.device) != 'undefined') {
                    $(".device").text(data.device)
                } else {
                    $(".device").text("")
                }
                if (typeof(data.air) != 'undefined') {
                    $(".air").text(data.air)
                } else {
                    $(".air").text("")
                }
                if (typeof(data.pue) != 'undefined') {
                    $(".pue").text(data.pue)
                } else {
                    $(".pue").text("")
                }
                if (typeof(data.add) != 'undefined') {
                    $(".add").text(data.add.toFixed(2))
                } else {
                    $(".add").text("")
                }
                if (typeof(data.other) != 'undefined') {
                    $(".other").text(data.other)
                } else {
                    $(".other").text("")
                }
                getPieData(data.pue);
            })
        }
        $("#dataType").combobox({
            onChange:function(value){
                isDay = value;
                getLineData();
            }
        })
//        $("#cyc").switchbutton({
//            onChange: function (checked) {
//                if (checked == true) {
//                    isDay = 1
//                } else {
//                    isDay = 0;
//                }
//                getLineData();
//            }
//        })
        $("#datetime").datebox({
            onSelect: function (beginDate) {
                getLineData();
            }
        }).datebox('calendar').calendar({
            validator: function (beginDate) {
                var date = new Date();
                return beginDate < date;//<=
            }
        });
        $(".floars").on("click", ".room", function () {
            $(".floars .room").removeClass("select")
            $(this).addClass("select")
            roomid = $(this).attr("roomid");
//            getPieData();
            getLineData();
        });
        var timeer = setInterval(function () {
                    if (typeof tree.rtree != 'undefined' && tree.rtree != null) {
                        clearInterval(timeer)
                        var nodes = tree.rtree.getNodesByParamFuzzy("id", "building_", null);
                        if (nodes != null && nodes.length > 0) {
                            $("#" + nodes[0].tId + "_a").trigger('click');
                        }

                    }
                }, 200
        )
        initPie()
        initLine();
    })
    function initPie() {
        var option = {
//            backgroundColor: 'rgb(0,65,107)',
            backgroundColor: 'rgba(73,73,73,0.5)',
            title: {
                text: '当前PUE',
                textStyle: {
                    color: "#fff",
                    fontSize: 16
                },
                top: "5%",
                left: "center"
            },
            grid: {
                top: 200
            },
            textStyle: {
                fontSize: 12
            },
            series: [{
                name: '辅助饼图最外层',
                type: 'pie',
                radius: '70%',
                z: -1,
                center: ["50%", "50%"],
                hoverAnimation: false,
                label: {
                    normal: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                itemStyle: {
                    normal: {
                        color: "rgba(0,0,0,0.35)"
                    }
                },
                data: [{
                    value: 1,
                    name: '辅助饼图最外层'
                }]
            }, {
                name: '辅助饼图红色',
                type: 'pie',
                radius: '4%',
                z: 4,
                center: ["50%", "50%"],
                hoverAnimation: false,
                label: {
                    normal: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                itemStyle: {
                    normal: {
                        color: "#E50505"
                    }
                },
                data: [{
                    value: 1,
                    name: '辅助饼图红色'
                }],
            }, {
                name: '', //大仪表盘左侧
                type: 'gauge',
                min: 0,
                max: 2,
                z: 2,
                radius: '65%',
                center: ["50%", "50%"],
                startAngle: -50,
                endAngle: 230,
                splitNumber: 10,
                clockwise: false,
                animation: false,
                detail: {
                    show: false
                },
                data: [{
                    value: 0,
                    name: ''
                }],
                axisLine: {
                    show: true,
                    lineStyle: {
                        color: [
                            [0.33, "red"],
                            [0.66, 'green'],
                            [1, 'yellow']
                        ],
                        width: 4
                    }
                },
                splitLine: {
                    length: 15,
                    lineStyle: {
                        color: '#fff',
                        width: 2
                    }
                },

                axisLabel: {
                    show: true,
                    textStyle: {
                        color: "#fff",
                    },
                    formatter: function (e) {
                        return e.toFixed(1);
                    }
                },
//                itemStyle: {
//                    normal: {
//                        color: '#E50505'
//                    }
//                },
                pointer: {
                    width: 0
                }
            }, {
                name: '分钟', //大仪表盘(控制指针指向)
                type: 'gauge',
                min: 0,
                max: 2,
                z: 2,
                radius: '70%',
                center: ["50%", "50%"],
                startAngle: -50,
                endAngle: 230,
                splitNumber: 0.2,
                clockwise: false,
                animation: false,
                detail: {
                    textStyle: {
                        fontSize: 24,
                        color: '#f00'
                    },
                    // backgroundColor: "rgba(0,0,0,0.35)",
                    offsetCenter: [0, '60%'],
                    borderRadius: 1,
                    formatter: function (e) {
                        let eStr = e + '';
                        let eArr = eStr.split('');
                        let len = eArr.length;
                        let str = '';
                        for (let i = 0; i < len - 1; i++) {
                            str += '{per|' + eArr[i] + '} ';
                        }
                        return str + '{per|' + eArr[len - 1] + '}';
                    },
                    rich: {
                        per: {
                            color: '#fff',
                            backgroundColor: '#f00',
                            padding: [3, 3, 3, 3],
                            borderRadius: 5,
                            //borderColor: '#aaa',
                            borderWidth: 1,
                            fontSize: 24,
                            fontStyle: 'bold'
                        }
                    }
                },
                data: [{
                    value: 1,
                    name: ''
                }],
//                axisLine: {
//                    show: false,
//                    lineStyle: {
//                        color: [
//                            [1, 'rgba(0,0,0,0)']
//                        ],
//                        width: 0
//                    }
//                },
                axisLine: {
                    show: true,
                    lineStyle: {
                        width: 2,
                        shadowBlur: 0,
                        color: [[0, 'yellow'], [, '#E98E2C'], [0.6, '#DDBD4D'], [0.8, '#7CBB55'], [2, '#9CD6CE']]
                    }
                },
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    show: false
                },
//                itemStyle: {
//                    normal: {
//                        color: '#E50505'
//                    }
//                },
                pointer: {
                    width: 2
                } //大仪表盘指针
            }]
        };
        $("#pie").css({"width": $("#pie").parent().width(), "height": $("#pie").parent().height()});  //获取p元素的样式颜色
        var mychart = echarts.init(document.getElementById("pie"));
        mychart.setOption(option);
    }
    function initLine() {
        var option = {
            backgroundColor: 'rgb(73,73,73)',
            color: ['#38b4ee', "#00BFFF", "#FF00FF", "#1ce322", "#000000"],
            grid: {
                left: 10,
                right: 10,
                bottom: 2,
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            title: {
                text: '30天用电及PUE',
//                subtext: '用电量,单位:(Kw/h）',
                textStyle: {
                    color: 'white'
                }
            },
            legend: {
                x: 300,
                top: '7%',
                textStyle: {
                    color: '#ffd285',
                },
                data: ['用电', 'PUE', '主设备', '空调']
            },
            tooltip: {
                confine: true,
                trigger: 'axis'
            },

            xAxis: {
                type: 'category',
                boundaryGap: false,
                axisTick: {
                    show: false
                },
                axisLabel: {
                    textStyle: {
                        color: '#dededf'
                    }
                },
                splitLine: { //网格线
                    show: false,
                    lineStyle: {
                        color: ['#23303f'],
                        type: 'solid'
                    }
                },
                data: []
            },
            yAxis: [{
                type: 'value',
                name: '用电',
                nameTextStyle: {
                    color: '#fff'
                },
                min: 0,
                position: 'left',
                splitLine: { //网格线
                    show: false,
                },
                axisLine: {
                    show: false,
                    //    onZero:false
                },
                axisLabel: {
                    formatter: '{value} Kw/h',
                    textStyle: {
                        color: 'white'
                    }
                }
            }, {
                type: 'value',
                name: 'PUE',
                nameTextStyle: {
                    color: '#fff'
                },
                axisLine: {
                    show: false,
                    //    onZero:false
                },
                min: 1,
                position: 'right',
                splitLine: { //网格线
                    show: false,
                },
                axisLabel: {
                    formatter: '{value}',
                    textStyle: {
                        color: 'white'
                    }
                }

            }],
//            yAxis: [{
//                type: 'value',
//                name: '用电(单位：Kw/h)',
//                nameTextStyle:{
//                    color:'#fff'
//                },
//                min: 0,
////                max: 100,
//                interval: 400,
//                axisTick: {
//                    show: false
//                },
//                axisLine: {
//                    show: false,
//                    //    onZero:false
//                },
//                axisLabel: {
//                    textStyle: {
//                        color: '#dededf'
//                    }
//                },
//                splitLine: { //网格线
//                    show: false,
//                    lineStyle: {
//                        color: ['#23303f'],
//                        type: 'solid'
//                    }
//                }
//            }, {
//                type: 'value',
//                name: 'PUE',
//                nameTextStyle:{
//                    color:'#fff'
//                },
//                min: 0,
//                max: 2,
//                interval: 0.4,
//                axisTick: {
//                    show: true
//                },
//                axisLine: {
//                    show: false,
//                    //    onZero:false
//                },
//                axisLabel: {
//                    textStyle: {
//                        color: 'white'
//                    }
//                },
//
//                splitLine: { //网格线
//                    show: false,
//                    lineStyle: {
//                        color: ['#23303f'],
//                        type: 'solid'
//                    }
//                }
//            }],
            series: [{
                name: '用电',
                type: 'line',
                smooth: false,
                symbolSize: 12,
                color: ["#FF0000"],
                data: [],
//                label: {
//                    normal: {
//                        show: false,
//                        position: 'top' //值显示
//                    }
//                }
            }, {
                name: 'PUE',
                type: 'line',
                yAxisIndex: 1,
                smooth: true,
                symbolSize: 12,
                color: ["#00BFFF"],
                data: [],
//                label: {
//                    normal: {
//                        show: false,
//                        position: 'top' //值显示
//                    }
//                }
            }, {
                name: '主设备',
                type: 'line',
                smooth: false,
                symbolSize: 12,
                color: ["#FF00FF"],
                data: [],
//                label: {
//                    normal: {
//                        show: false,
//                        position: 'top' //值显示
//                    }
//                }
            }, {
                name: '空调',
                type: 'line',
                smooth: true,
                symbolSize: 12,
                color: ["#fff"],
                data: [],
//                label: {
//                    normal: {
//                        show: false,
//                        position: 'top' //值显示
//                    }
//                }
            }
            ]
        };
        $("#line").css({"width": $("#line").parent().width() - 80, "height": $("#line").parent().height() - 50});  //获取p元素的样式颜色
        var mychart = echarts.init(document.getElementById("line"));
        mychart.setOption(option);
    }
    function showRoom(roomid) {
        open(contextPath + '/pe/roomlayout/' + roomid);
    }
</script>
</body>
</html>