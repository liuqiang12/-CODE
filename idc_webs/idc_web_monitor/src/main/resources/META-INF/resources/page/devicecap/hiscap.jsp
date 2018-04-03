<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/themes/css/form.css"/>
    <title>性能监视</title>
    <style>
        body {
            margin: 0px;
        }
    </style>
</head>
<body>
<div id="cpu" class="easyui-panel"
     style="height:300px;padding:0px;background:#fafafa;" data-options="">
    <div>
        开始时间：<input class="easyui-datebox" required="required" name="startTime" value=""
                    data-options="formatter:myformatter,parser:myparser"/>-
        结束时间：<input class="easyui-datebox" required="required" name="endTime" value=""
                    data-options="formatter:myformatter,parser:myparser"/>
        <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',onClick:setCPUData">查询</a>
    </div>
    <div class="chart" unit="%"></div>
</div>
<%--<div id="temperature" class="easyui-panel"--%>
     <%--style="height:300px;margin-top:10px;background:#fafafa;"--%>
     <%--data-options=" ">--%>
    <%--<div>--%>
        <%--开始时间：<input class="easyui-datebox startTime" required="required" name="startTime"--%>
                    <%--data-options="formatter:myformatter,parser:myparser"/>---%>
        <%--结束时间：<input class="easyui-datebox endTime" required="required" name="endTime"--%>
                    <%--data-options="formatter:myformatter,parser:myparser"/>--%>
        <%--<a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',onClick:setTempData">查询</a>--%>
    <%--</div>--%>
    <%--<div class="chart" unit="℃"></div>--%>
<%--</div>--%>
<div id="memory" class="easyui-panel"
     style="height:300px;padding:0px;background:#fafafa;"
     data-options=" ">
    <div>
        开始时间：<input class="easyui-datebox startTime" required="required" name="startTime"
                    data-options="formatter:myformatter,parser:myparser"/>-
        结束时间：<input class="easyui-datebox endTime" required="required" name="endTime"
                    data-options="formatter:myformatter,parser:myparser"/>
        <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',onClick:setMemData">查询</a>
    </div>
    <div class="chart" unit="%"></div>
</div>
</body>
<script type="text/javascript">

    (function (root, factory) {
        if (typeof define === 'function' && define.amd) {
            // AMD. Register as an anonymous module.
            define(['exports', 'echarts'], factory);
        } else if (typeof exports === 'object' && typeof exports.nodeName !== 'string') {
            // CommonJS
            factory(exports, require('echarts'));
        } else {
            // Browser globals
            factory({}, root.echarts);
        }
    }(this, function (exports, echarts) {
        var log = function (msg) {
            if (typeof console !== 'undefined') {
                console && console.error && console.error(msg);
            }
        };
        if (!echarts) {
            log('ECharts is not Loaded');
            return;
        }
        echarts.registerTheme('macarons', {
            "color": [
                "#2ec7c9",
                "#b6a2de",
                "#5ab1ef",
                "#ffb980",
                "#d87a80",
                "#8d98b3",
                "#e5cf0d",
                "#97b552",
                "#95706d",
                "#dc69aa",
                "#07a2a4",
                "#9a7fd1",
                "#588dd5",
                "#f5994e",
                "#c05050",
                "#59678c",
                "#c9ab00",
                "#7eb00a",
                "#6f5553",
                "#c14089"
            ],
            "backgroundColor": "rgba(0,0,0,0)",
            "textStyle": {},
            "title": {
                "textStyle": {
                    "color": "#008acd"
                },
                "subtextStyle": {
                    "color": "#aaaaaa"
                }
            },
            "line": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 1
                    }
                },
                "lineStyle": {
                    "normal": {
                        "width": 2
                    }
                },
                "symbolSize": 3,
                "symbol": "emptyCircle",
                "smooth": true
            },
            "radar": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 1
                    }
                },
                "lineStyle": {
                    "normal": {
                        "width": 2
                    }
                },
                "symbolSize": 3,
                "symbol": "emptyCircle",
                "smooth": true
            },
            "bar": {
                "itemStyle": {
                    "normal": {
                        "barBorderWidth": 0,
                        "barBorderColor": "#ccc"
                    },
                    "emphasis": {
                        "barBorderWidth": 0,
                        "barBorderColor": "#ccc"
                    }
                }
            },
            "pie": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "scatter": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "boxplot": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "parallel": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "sankey": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "funnel": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "gauge": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "candlestick": {
                "itemStyle": {
                    "normal": {
                        "color": "#d87a80",
                        "color0": "#2ec7c9",
                        "borderColor": "#d87a80",
                        "borderColor0": "#2ec7c9",
                        "borderWidth": 1
                    }
                }
            },
            "graph": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                },
                "lineStyle": {
                    "normal": {
                        "width": 1,
                        "color": "#aaaaaa"
                    }
                },
                "symbolSize": 3,
                "symbol": "emptyCircle",
                "smooth": true,
                "color": [
                    "#2ec7c9",
                    "#b6a2de",
                    "#5ab1ef",
                    "#ffb980",
                    "#d87a80",
                    "#8d98b3",
                    "#e5cf0d",
                    "#97b552",
                    "#95706d",
                    "#dc69aa",
                    "#07a2a4",
                    "#9a7fd1",
                    "#588dd5",
                    "#f5994e",
                    "#c05050",
                    "#59678c",
                    "#c9ab00",
                    "#7eb00a",
                    "#6f5553",
                    "#c14089"
                ],
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#eeeeee"
                        }
                    }
                }
            },
            "map": {
                "itemStyle": {
                    "normal": {
                        "areaColor": "#dddddd",
                        "borderColor": "#eeeeee",
                        "borderWidth": 0.5
                    },
                    "emphasis": {
                        "areaColor": "rgba(254,153,78,1)",
                        "borderColor": "#444444",
                        "borderWidth": 1
                    }
                },
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#d87a80"
                        }
                    },
                    "emphasis": {
                        "textStyle": {
                            "color": "rgb(100,0,0)"
                        }
                    }
                }
            },
            "geo": {
                "itemStyle": {
                    "normal": {
                        "areaColor": "#dddddd",
                        "borderColor": "#eeeeee",
                        "borderWidth": 0.5
                    },
                    "emphasis": {
                        "areaColor": "rgba(254,153,78,1)",
                        "borderColor": "#444444",
                        "borderWidth": 1
                    }
                },
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#d87a80"
                        }
                    },
                    "emphasis": {
                        "textStyle": {
                            "color": "rgb(100,0,0)"
                        }
                    }
                }
            },
            "categoryAxis": {
                "axisLine": {
                    "show": true,
                    "lineStyle": {
                        "color": "#008acd"
                    }
                },
                "axisTick": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisLabel": {
                    "show": true,
                    "textStyle": {
                        "color": "#333"
                    }
                },
                "splitLine": {
                    "show": false,
                    "lineStyle": {
                        "color": [
                            "#eee"
                        ]
                    }
                },
                "splitArea": {
                    "show": false,
                    "areaStyle": {
                        "color": [
                            "rgba(250,250,250,0.3)",
                            "rgba(200,200,200,0.3)"
                        ]
                    }
                }
            },
            "valueAxis": {
                "axisLine": {
                    "show": true,
                    "lineStyle": {
                        "color": "#008acd"
                    }
                },
                "axisTick": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisLabel": {
                    "show": true,
                    "textStyle": {
                        "color": "#333"
                    }
                },
                "splitLine": {
                    "show": true,
                    "lineStyle": {
                        "color": [
                            "#eee"
                        ]
                    }
                },
                "splitArea": {
                    "show": true,
                    "areaStyle": {
                        "color": [
                            "rgba(250,250,250,0.3)",
                            "rgba(200,200,200,0.3)"
                        ]
                    }
                }
            },
            "logAxis": {
                "axisLine": {
                    "show": true,
                    "lineStyle": {
                        "color": "#008acd"
                    }
                },
                "axisTick": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisLabel": {
                    "show": true,
                    "textStyle": {
                        "color": "#333"
                    }
                },
                "splitLine": {
                    "show": true,
                    "lineStyle": {
                        "color": [
                            "#eee"
                        ]
                    }
                },
                "splitArea": {
                    "show": true,
                    "areaStyle": {
                        "color": [
                            "rgba(250,250,250,0.3)",
                            "rgba(200,200,200,0.3)"
                        ]
                    }
                }
            },
            "timeAxis": {
                "axisLine": {
                    "show": true,
                    "lineStyle": {
                        "color": "#008acd"
                    }
                },
                "axisTick": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisLabel": {
                    "show": true,
                    "textStyle": {
                        "color": "#333"
                    }
                },
                "splitLine": {
                    "show": true,
                    "lineStyle": {
                        "color": [
                            "#eee"
                        ]
                    }
                },
                "splitArea": {
                    "show": false,
                    "areaStyle": {
                        "color": [
                            "rgba(250,250,250,0.3)",
                            "rgba(200,200,200,0.3)"
                        ]
                    }
                }
            },
            "toolbox": {
                "iconStyle": {
                    "normal": {
                        "borderColor": "#2ec7c9"
                    },
                    "emphasis": {
                        "borderColor": "#18a4a6"
                    }
                }
            },
            "legend": {
                "textStyle": {
                    "color": "#333333"
                }
            },
            "tooltip": {
                "axisPointer": {
                    "lineStyle": {
                        "color": "#008acd",
                        "width": "1"
                    },
                    "crossStyle": {
                        "color": "#008acd",
                        "width": "1"
                    }
                }
            },
            "timeline": {
                "lineStyle": {
                    "color": "#008acd",
                    "width": 1
                },
                "itemStyle": {
                    "normal": {
                        "color": "#008acd",
                        "borderWidth": 1
                    },
                    "emphasis": {
                        "color": "#a9334c"
                    }
                },
                "controlStyle": {
                    "normal": {
                        "color": "#008acd",
                        "borderColor": "#008acd",
                        "borderWidth": 0.5
                    },
                    "emphasis": {
                        "color": "#008acd",
                        "borderColor": "#008acd",
                        "borderWidth": 0.5
                    }
                },
                "checkpointStyle": {
                    "color": "#2ec7c9",
                    "borderColor": "rgba(46,199,201,0.4)"
                },
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#008acd"
                        }
                    },
                    "emphasis": {
                        "textStyle": {
                            "color": "#008acd"
                        }
                    }
                }
            },
            "visualMap": {
                "color": [
                    "#5ab1ef",
                    "#e0ffff"
                ]
            },
            "dataZoom": {
                "backgroundColor": "rgba(47,69,84,0)",
                "dataBackgroundColor": "rgba(239,239,255,1)",
                "fillerColor": "rgba(182,162,222,0.2)",
                "handleColor": "#008acd",
                "handleSize": "100%",
                "textStyle": {
                    "color": "#333333"
                }
            },
            "markPoint": {
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#eeeeee"
                        }
                    },
                    "emphasis": {
                        "textStyle": {
                            "color": "#eeeeee"
                        }
                    }
                }
            }
        });
    }));

    var charts = ['cpu',  'memory'];
    var deviceid = "${deviceid}";
    $(function () {
        initChars();
        $(".easyui-datebox").each(function (i,itam) {
             var name =  $(this).attr("textboxname");
            if(name=="startTime"){
                setDateBox(itam,true);
            }else{
                setDateBox(itam);
            }
        })
//        $(".easyui-linkbutton").linkbutton({
//            onClick:function(){
//                var type = $(this).parent().parent().attr("id");
//                var title = $(this).parent().parent().attr("title");
//                var startime  = $(this).parent().find("input[name='startTime']").val();
//                var endtime  = $(this).parent().find("input[name='endTime']").val();
//                getDataCharByType(type,title,startime,endtime);
//            }
//        });
        $(".easyui-linkbutton").click();
    })
    function getDataCharByType(type,title,startime,endtime){
        var chardiv = $("#"+type).find(".chart").eq(0);
        var unit = chardiv.attr("unit")
        var chart = echarts.getInstanceByDom(chardiv[0]);
        if(chart==null){
            chart = initChar(type,title,unit);
        }
    }
    function initChar(type,title,unit){
        var chardiv = $("#"+type).find(".chart").eq(0);
        var width = chardiv.width()
        var height =chardiv.height();
        var chart = echarts.init(chardiv[0],"macarons");
        var option = {

            title: {
                text: title,
                left: 0
            },
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                left: '2%',
                right: '4%',
                bottom: '30%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                axisLabel: {
                    rotate: 60//倾斜度 -90 至 90 默认为0
//                    margin: 1
                },
                data: []
            },
//            dataZoom: [
//                {   type: 'slider',
//                    show: true,
//                    realtime: true,
//                    start: 80,
//                    end: 100
//                }
//            ],
            yAxis: {
                type: 'value',
                name: unit,
                position: 'left',
                axisLabel: {
                    formatter: '{value}'
                }
            },
            series: []
        };
        chart.setOption(option);
        chart.resize({width: width, height: height});
        return chart;
    }
    function initChars() {
        $.each(charts, function (i, iteam) {
            var chartdiv = $("#" + iteam).find(".chart");
            var height = $("#" + iteam).height() - chartdiv.prev().height();
            chartdiv.height(height - 2);
        })
    }
    function setCPUData(){
        var chardiv = $("#cpu").find(".chart").eq(0);
        var title = $(this).parent().parent().attr("title");
        var chart = echarts.getInstanceByDom(chardiv[0]);
        if(chart==null){
            var unit = chardiv.attr("unit")
            chart = initChar("cpu",title,unit);
        }
        var startTime  = $(this).parent().find("input[name='startTime']").val();
        var endTime  = $(this).parent().find("input[name='endTime']").val();
        getData("cpu","CPU历史","{value}%",deviceid,startTime,endTime,chart,function(option,xlabels,datas){
        });
//        $.post(contextPath+"/devicecap/gethis",{
//            type: "cpu",
//            deviceid:deviceid,
//            startTime: startTime,
//            endTime: endTime,
//        },function(data){
//            var option = chart.getOption();
//            option.title = {
//                text:"CPU历史"
//            };
//            var xlabels = [],datas = [];
//            $.each(data.data, function (i, iteam) {
//                xlabels.push(iteam.name);
//                datas.push(iteam.value);
//            })
//            if(xlabels.length>20){
//                option.dataZoom= [
//                    {   type: 'slider',
//                        show: true,
//                        realtime: true,
//                        start: 80,
//                        end: 100
//                    }
//                ]
//            }else{
//                option.dataZoom = [];
//            }
//            option.xAxis[0].data = xlabels;
//            option.yAxis[0].axisLabel.formatter = '{value}%';
//            option.series =[{
//                    name: 'CPU历史',
//                    type: 'line',
//                    showSymbol: false,
//                    hoverAnimation: false,
//                    data: datas
//            }]
//            chart.setOption(option);
//        })
    }
    function getData(type,title,unit,deviceid,startTime,endTime,chart,callback){
        var option = chart.getOption();
        option.title = {
            text:title
        };
        chart.setOption(option);
        $.post(contextPath+"/devicecap/gethis",{
            type: type,
            deviceid:deviceid,
            startTime: startTime,
            endTime: endTime,
        },function(data){

            var xlabels = [],datas = [];
            $.each(data.data, function (i, iteam) {
                xlabels.push(iteam.name);
                datas.push(iteam.value);
            })
            if(xlabels.length>20){
                option.dataZoom= [
                    {   type: 'slider',
                        show: true,
                        realtime: true,
                        start: 80,
                        end: 100
                    }
                ]
            }else{
                option.dataZoom = [];
            }
            option.xAxis[0].data = xlabels;
            option.yAxis[0].axisLabel.formatter = unit;
            option.series = [{
                name: title,
                type: 'line',
                showSymbol: false,
                hoverAnimation: false,
                data: datas
            }]
            chart.setOption(option);
            callback(option,xlabels,datas);
        })
    }
    function setTempData(){
        var chardiv = $("#temperature").find(".chart").eq(0);
        var title = $(this).parent().parent().attr("title");
        var chart = echarts.getInstanceByDom(chardiv[0]);
        if(chart==null){
            var unit = chardiv.attr("unit")
            chart = initChar("temperature",title,unit);
        }
        var startTime  = $(this).parent().find("input[name='startTime']").val();
        var endTime  = $(this).parent().find("input[name='endTime']").val();
        getData("temperature","温度历史","℃",deviceid,startTime,endTime,chart,function(option,xlabels,datas){
        });
    }
    function setMemData(){
        var chardiv = $("#memory").find(".chart").eq(0);
        var title = $(this).parent().parent().attr("title");
        var chart = echarts.getInstanceByDom(chardiv[0]);
        if(chart==null){
            var unit = chardiv.attr("unit")
            chart = initChar("memory",title,unit);
        }
        var startTime  = $(this).parent().find("input[name='startTime']").val();
        var endTime  = $(this).parent().find("input[name='endTime']").val();
        getData("memory", "内存历史", "{value}%", deviceid, startTime, endTime, chart, function (option, xlabels, datas) {
        });
//        $.post(contextPath+"/devicecap/gethis",{
//            type: 'memory',
//            deviceid:deviceid,
//            startTime: startTime,
//            endTime: endTime,
//        },function(data){
//            var option = chart.getOption();
//            option.title = {
//                text:"内存历史"
//            };
//            var xlabels = [],datas = [];
//            $.each(data.data, function (i, iteam) {
//                xlabels.push(iteam.name);
//                datas.push(iteam.value);
//            })
//            if(xlabels.length>20){
//                option.dataZoom= [
//                    {   type: 'slider',
//                        show: true,
//                        realtime: true,
//                        start: 80,
//                        end: 100
//                    }
//                ]
//            }else{
//                option.dataZoom = [];
//            }
//            option.xAxis[0].data = xlabels;
//            option.yAxis[0].axisLabel.formatter = '{value}%';
//            option.series =[{
//                name: '内存历史',
//                type: 'line',
//                showSymbol: false,
//                hoverAnimation: false,
//                data: datas
//            }]
//            chart.setOption(option);
//        })
    }
    function myformatter(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
    }

    function myparser(s) {
        if (!s) return new Date();
        var ss = (s.split('-'));
        var y = parseInt(ss[0], 10);
        var m = parseInt(ss[1], 10);
        var d = parseInt(ss[2], 10);
        if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
            return new Date(y, m - 1, d);
        } else {
            return new Date();
        }
    }
    function setDateBox(obj,isBeg) {
        var myDate = new Date();
        //昨天
        if(isBeg){
           myDate.setDate(myDate.getDate() - 7);
        }
//        myDate.setDate(myDate.getDate() - 1);
        $(obj).datebox('setValue', myformatter(myDate));
    }
    function addDate(dd, dadd) {
        var a = new Date(dd)
        a = a.valueOf()
        a = a + dadd * 24 * 60 * 60 * 1000
        a = new Date(a)
        return a;
    }
</script>
</html>