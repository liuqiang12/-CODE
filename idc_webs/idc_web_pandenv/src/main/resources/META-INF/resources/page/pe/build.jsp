<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <%--<script type="text/javascript"  src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/plugs/jquery.portal.js"></script>--%>
    <%--<link rel="stylesheet" type="text/css"--%>
          <%--href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/portal.css"/>--%>
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
            margin: 0px;
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
            width: 100%;
            height: 200px;
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
            margin-top: 20px;
            font-size: 18px;
        }
    </style>
</head>
<body>
<%--<div id="pp" style="position:relative">--%>
    <%--<div style="width:33%">1</div>--%>
    <%--<div style="width:33%">2</div>--%>
    <%--<div style="width:33%">3</div>--%>
<%--</div>--%>
<div class="easyui-layout" fit="true" style="overflow-x:hidden">
    <%--<div class="content_suspend">--%>
        <%--<div class="conter">--%>
            <%--<ul id="rtree" class="ztree" style="width:200px; overflow:auto;"></ul>--%>
        <%--</div>--%>
        <%--<div class="hoverbtn">--%>
            <%--<span>导</span><span>航</span>--%>

            <%--<div class="hoverimg" height="9" width="13"></div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div data-options="region:'center'" style="margin: 0px">
        <div class="layui-row " style="border-bottom: 5px solid #fff" id="layout">
            <%--<div class="layui-col-md6 layui-col-sm12" style="border-right: 5px solid #fff">--%>
                <%--<div class="layui-row">--%>
                    <%--<div class="title" style="height: 30px">YD_SC_CD_XY_B04</div>--%>
                    <%--<div class="layui-col-md5"--%>
                         <%--style="height: 170px;background-image:url(${contextPath}/framework/themes/img/build2.png);background-repeat: no-repeat ">--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md3" style="height: 170px;">--%>
                        <%--<div>--%>
                            <%--<div class="pro">总能耗:11</div>--%>
                            <%--<div class="pro">主设备:1156</div>--%>
                            <%--<div class="pro">空调:111</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md4" style="height: 170px;">--%>
                        <%--<div>--%>
                            <%--<div class="pro">其他:11</div>--%>
                            <%--<div class="pro">PUE:1156</div>--%>
                            <%--<div class="pro">环比增长:4.5%</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md4" style="height: 200px;">--%>
                        <%--<div class="pue" id="pie"></div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md8" style="height: 200px;">--%>
                        <%--<div class="pue" id="line"></div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-col-md6 layui-col-sm12" style="border-right: 1px solid #fff">--%>
                <%--<div style="height: 196px">--%>
                    <%--<div class="layui-row">--%>
                        <%--<div class="title" style="height: 30px">YD_SC_CD_XY_B04</div>--%>
                        <%--<div class="layui-col-md5"--%>
                             <%--style="height: 170px;background-image:url(${contextPath}/framework/themes/img/build2.png);background-repeat: no-repeat ">--%>

                        <%--</div>--%>
                        <%--<div class="layui-col-md7" style="height: 170px;">--%>
                            <%--<div class="layui-row">--%>
                                <%--<div class="layui-col-md6">--%>
                                    <%--<div>--%>
                                        <%--<div class="pro">总能耗:11</div>--%>
                                        <%--<div class="pro">主设备:1156</div>--%>
                                        <%--<div class="pro">空调:111</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="layui-col-md6">--%>
                                    <%--<div>--%>
                                        <%--<div class="pro">其他:11</div>--%>
                                        <%--<div class="pro">PUE:1156</div>--%>
                                        <%--<div class="pro">环比增长:4.5%</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div>PUE：主设备+空调/主设备</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div style="height: 200px">--%>
                    <%--<div class="layui-row layui-col-space10">--%>
                        <%--<div class="layui-col-md4" style="height: 200px;">--%>
                            <%--<div class="pue" id="pie1"></div>--%>
                        <%--</div>--%>
                        <%--<div class="layui-col-md8" style="height: 200px;">--%>
                            <%--<div class="pue" id="line1"></div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-col-md6 layui-col-sm12" style="border-right: 5px solid #fff">--%>
                <%--<div class="layui-row">--%>
                    <%--<div class="title" style="height: 30px">YD_SC_CD_XY_B04</div>--%>
                    <%--<div class="layui-col-xs12 layui-col-md5"--%>
                         <%--style="height: 170px;background-image:url(${contextPath}/framework/themes/img/build2.png);background-repeat: no-repeat ">--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-xs12 layui-col-md7" style="height: 170px;">--%>
                        <%--<div class="layui-row">--%>
                            <%--<div class="layui-col-xs6 layui-col-md6">--%>
                                <%--<div>--%>
                                    <%--<div class="pro">总能耗:11</div>--%>
                                    <%--<div class="pro">主设备:1156</div>--%>
                                    <%--<div class="pro">空调:111</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="layui-col-xs6 layui-col-md6">--%>
                                <%--<div>--%>
                                    <%--<div class="pro">其他:11</div>--%>
                                    <%--<div class="pro">PUE:1156</div>--%>
                                    <%--<div class="pro">环比增长:4.5%</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div style="color:yellow">PUE：主设备+空调/主设备</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md4" style="height: 200px;">--%>
                        <%--<div class="pue" id="pie2"></div>--%>
                    <%--</div>--%>
                    <%--<div class="layui-col-md8" style="height: 200px;">--%>
                        <%--<div class="pue" id="line2"></div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
        <%--<div data-options="region:'center'">--%>
        <%--<div class="idcpanel animated">--%>
        <%--<div class="idc">--%>
        <%--<img src="${contextPath}/framework/themes/img/build.jpg" id="1"/>--%>

        <%--<div>--%>
        <%--<div class="title">1</div>--%>
        <%--<div class="pro">1</div>--%>
        <%--<div class="pro">1</div>--%>
        <%--<div class="pro">机架使用率:1%</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="floars" style="padding:10px">--%>
        <%--<div> 返回上级</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="easyui-layout" fit="true">--%>
        <%--<div data-options="region:'west'" style="width: 30%">--%>
        <%--<div id="pie"></div>--%>
        <%--</div>--%>
        <%--<div data-options="region:'center'" border="false" style="color:#fff">--%>
        <%--<div class="easyui-layout" fit="true">--%>
        <%--<div data-options="region:'north'" border="false" style="height:38px;padding:5px 15px 5px 5px;color:#fff ">--%>
        <%--查看日期:<input id="datetime" type="text" class="easyui-datebox"--%>
        <%--/>--%>
        <%--<input id="cyc" class="easyui-switchbutton" data-options="onText:'当日',offText:'当月'"/>--%>
        <%--</div>--%>
        <%--<div data-options="region:'west'" border="false" style="width:300px">--%>
        <%--<div style="height:26px;margin-top:6px;color: #fff;font-size: 20px">--%>
        <%--用电统计：<span style="font-size: 10px;color:yellow">PUE:主设备+空调/主设备</span>--%>
        <%--</div>--%>
        <%--<div class="title_desc2" style="margin-top:5px;">--%>
        <%--<div class="width100 box-sizing right_border pedesc" style="float:left;">--%>
        <%--&lt;%&ndash;<h5 class="width100" style="float:left;text-align: center;font-weight:bold">硬盘</h5>&ndash;%&gt;--%>

        <%--<div>--%>
        <%--<h6 class="width50" style="text-align:right">总能耗：</h6><h6 class="width50 all">--%>
        <%--6000</h6>--%>
        <%--</div>--%>
        <%--<div>--%>
        <%--<h6 class="width50" style="text-align:right">主设备：</h6><h6 class="width50 device">--%>
        <%--6000</h6>--%>
        <%--</div>--%>
        <%--<div>--%>
        <%--<h6 class="width50" style="text-align:right">空调：</h6><h6--%>
        <%--class="width50 air">0</h6>--%>
        <%--</div>--%>
        <%--<div>--%>
        <%--<h6 class="width50" style="text-align:right">其他：</h6><h6--%>
        <%--class="width50 other">0</h6>--%>
        <%--</div>--%>
        <%--<div>--%>
        <%--<h6 class="width50" style="text-align:right">PUE：</h6><h6 class="width50 pue">--%>
        <%--6000</h6>--%>
        <%--</div>--%>
        <%--<div>--%>
        <%--<h6 class="width50" style="text-align:right">能耗环比：</h6><h6--%>
        <%--class="width50 add">0</h6>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div data-options="region:'center'" border="false">--%>
        <%--<div id="line"></div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--&lt;%&ndash;<div style="height: 30px;padding-top: 8px;padding-left: 10px">&ndash;%&gt;--%>
        <%--&lt;%&ndash;查看日期:<input  id="dd"  type= "text" class= "easyui-datebox" required ="required" style="width:100px"> </input>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<input class="easyui-switchbutton" data-options="onText:'当日',offText:'当月'">&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

        <%--</div>--%>

        <%--</div>--%>


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
<script type="text/template" id="template">
    <div class="layui-col-md6 layui-col-sm12 onepanel" style="border-right: 5px solid #fff;border-bottom: 5px solid #fff">
        <div class="layui-row">
            <div class="title" style="height: 30px">YD_SC_CD_XY_B04</div>
            <div class="layui-col-md5" style="height: 170px;">
                <img src="${contextPath}/framework/themes/img/tall2.png" style="height: 170px">
            </div>
            <%--<div class="layui-col-md5" style="height: 170px;background-size:300px 300px;background:url(${contextPath}/framework/themes/img/tall2.png) no-repeat scroll -140px -20px">--%>
                <%----%>
            <%--</div>--%>
            <div class="layui-col-md3" style="height: 170px;">
                <div>
                    <div class="pro ">总能耗:<span class="all"></span></div>
                    <div class="pro ">主设备:<span class="device"></span></div>
                    <div class="pro ">空调:<span class="air"></span></div>
                </div>
            </div>
            <div class="layui-col-md4" style="height: 170px;">
                <div>
                    <div class="pro ">其他:<span class="other"></span></div>
                    <div class="pro">PUE:<span class="pue"></span></div>
                    <div class="pro">用电环比:<span class="add"></span></div>
                </div>
            </div>
            <div class="layui-col-md4" style="height: 200px;">
                <div class="puechar" ></div>
            </div>
            <div class="layui-col-md8" style="height: 200px;">
                <div class="linechar"></div>
            </div>
        </div>
    </div>
</script>
<script type="text/javascript">
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
//
//    Vue.component('build', {
//        template: '#template',
//        props: ['data'],
////        data:function(){
////            return { selected: false }
////        },
//        computed:{
//        },
//        methods:{
//
//        }
//    });
    $(function () {
        try {
            top.hideLeft();
        } catch (e) {
        }
        $.post(contextPath+"/pe/getBuild",{localid:0},function(data){
            $.each(data,function(i,item){
                var html = $("#template").clone();
                var q = $(html.html())
                q.attr("id",item.id)
                q.find(".title").text(item.name)
                $("#layout").append(q);
            })
            $(".onepanel").each(function(i,iteam){
                initPie(iteam)
                initLine(iteam)
                var id = $(iteam).attr("id");
                $.post(contextPath+"/pe/getLineByBuild/"+id,{date:null,cyc:0},function(data){
                    var xdata= data.datestr;
                    var values= data.values;
                    var pues= data.pues
                    var div = $(iteam).find(".linechar");
                    var chart = echarts.getInstanceByDom(div[0]);
                    var option = chart.getOption();
                    option.xAxis[0].data = xdata;
                    option.series[0].data = values
                    option.series[1].data = pues
                    chart.setOption(option);

                    if(typeof(data.all)!='undefined'){
                        $(iteam).find(".all").text(data.all.toFixed(2))
                    }else{
                        $(iteam).find(".all").text("")
                    }
                    if(typeof(data.device)!='undefined'){
                        $(iteam).find(".device").text(data.device.toFixed(2))
                    }else{
                        $(iteam).find(".device").text("")
                    }
                    if(typeof(data.air)!='undefined'){
                        $(iteam).find(".air").text(data.air.toFixed(2))
                    }else{
                        $(iteam).find(".air").text("")
                    }
                    var pue = 0;
                    if(typeof(data.pue)!='undefined'){
                        pue =data.pue.toFixed(2)
                        $(iteam).find(".pue").text(data.pue.toFixed(2))
                    }else{
                        $(iteam).find(".pue").text("")
                    }
                    if(typeof(data.add)!='undefined'){
                        $(iteam).find(".add").text(data.add.toFixed(2)+"%")
                    }else{
                        $(iteam).find(".add").text("")
                    }
                    if(typeof(data.other)!='undefined'){
                        $(iteam).find(".other").text(data.other.toFixed(2))
                    }else{
                        $(iteam).find(".other").text("")
                    }
                    var chart = echarts.getInstanceByDom($(iteam).find(".puechar")[0]);
                    var option = chart.getOption();
                    option.series[3].data = [{
                        value: pue,
                        name: ''
                    }]
                    chart.setOption(option)
                })
            })

        });

        var roomid = 0;
        var isDay = 0
//        var getPieData = function () {
//            $.post(contextPath + "/pe/getPUE/" + roomid, function (data) {
//                var chart = echarts.getInstanceByDom(document.getElementById("pie"));
//                var option = chart.getOption();
//                option.series[3].data = [{
//                    value: data.toFixed(1),
//                    name: ''
//                }]
//                chart.setOption(option)
//            })
//        }
        var getLineData = function () {
            var datetime = $("#datetime").datebox("getValue");
            console.log(datetime)
            $.post(contextPath + "/pe/getLine/" + roomid, {date: datetime, cyc: isDay}, function (data) {
                var xdata = data.datestr;
                var values = data.values;
                var pues = data.pues
                var chart = echarts.getInstanceByDom(document.getElementById("line"));
                var option = chart.getOption();
                option.xAxis[0].data = xdata;
                option.series[0].data = values
                option.series[1].data = pues
                chart.setOption(option)
            })
        }
        //initPie()
       // initLine();
    })

    function initPie(dom) {
        var option = {
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
                splitNumber: 4,
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
                splitNumber: 5,
                clockwise: false,
                animation: false,
                detail: {
                    textStyle: {
                        fontSize: 16,
                        color: '#f00'
                    },
                    offsetCenter: [0, '60%'],
                    borderRadius: 1,
                    formatter: function (e) {
//                        let eStr = e + '';
//                        let eArr = eStr.split('');
//                        let len = eArr.length;
//                        let str = '';
//                        for (let i = 0; i < len - 1; i++) {
//                            str += '{per|' + eArr[i] + '} ';
//                        }
//                        console.log(str + '{per|' + eArr[len - 1] + '}')
//                        //return str + '{per|' + eArr[len - 1] + '}';
                        return "{per|"+e+"}"
                    },
                    rich: {
                        per: {
                            color: '#fff',
                            backgroundColor: '#f00',
                            padding: [1, 1, 1, 1],
                            borderRadius: 2,
                            //borderColor: '#aaa',
                            borderWidth: 1,
                            fontSize: 12,
                            fontStyle: 'bold'
                        }
                    }
                },
                data: [{
                    value: 0,
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
        var pie = $(dom).find(".puechar");
        pie.css({"width": pie.parent().width(), "height":pie.parent().height()});  //获取p元素的样式颜色
        var mychart = echarts.init(pie[0]);
        mychart.setOption(option);
    }
    function initLine(dom) {
        var option = {
            backgroundColor: 'rgb(102,102,102)',
            color: ['#38b4ee', "#00BFFF", "#FF00FF", "#1ce322", "#000000"],
//            grid: {
//                left: '150',
////                right: '3%',
////                bottom: '3%',
////                containLabel: true
//            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            title: {
                text: '能耗及PUE趋势',
                subtext: '用电量,单位:(Kw/h）',
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
                data: ['用电', 'PUE']
            },
            tooltip: {
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
                    show: true,
                    lineStyle: {
                        color: ['#23303f'],
                        type: 'solid'
                    }
                },
                data: ['2017-1', '2017-2', '2017-3', '2017-4', '2017-5', '2017-6', '2017-7', '2017-8', '现在']
            },
            yAxis: [{
                type: 'value',
                name: '用电',
                min: 0,
//                max: 100,
//                interval: 400,
//                axisTick: {
//                    show: false
//                },
                axisLine: {
                    show: false,
                    //    onZero:false
                },
                axisLabel: {
                    textStyle: {
                        color: '#dededf'
                    }
                },
                splitLine: { //网格线
                    show: true,
                    lineStyle: {
                        color: ['#23303f'],
                        type: 'solid'
                    }
                }
            }, {
                type: 'value',
                name: 'PUE',
                min: 1,
                max: 2,
                interval: 0.2,
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false,
                    //    onZero:false
                },
                axisLabel: {
                    textStyle: {
                        color: 'white'
                    }
                },
            }],
            series: [{
                name: '用电',
                type: 'line',
                smooth: true,
                symbolSize: 12,
                color: ["#FF0000"],
                data: ['48', '643', '41', '40', '24', '53', '47', '50', '49'],
                label: {
                    normal: {
                        show: false,
                        position: 'top' //值显示
                    }
                }
            }, {
                name: 'PUE',
                type: 'line',
                yAxisIndex: 1,
                smooth: true,
                symbolSize: 12,
                color: ["#00BFFF"],
                data: ['1.48', '1.43', '1.41', '1.40', '1.24', '0.53', '0.47', '0.50', '0.49'],
                label: {
                    normal: {
                        show: false,
                        position: 'top' //值显示
                    }
                }
            }
            ]
        };
        var pie = $(dom).find(".linechar");
        pie.css({"width": pie.parent().width(), "height":pie.parent().height()});  //获取p元素的样式颜色
        var mychart = echarts.init(pie[0]);
        mychart.setOption(option);
    }

</script>
</body>
</html>