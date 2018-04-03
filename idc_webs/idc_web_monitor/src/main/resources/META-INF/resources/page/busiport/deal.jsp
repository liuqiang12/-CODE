<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <title></title>
    <style>
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="canvs" style="overflow: hidden"></div>
</div>
<script type="text/javascript">
    var isin = true;
    var getData = function () {
        var index = top.layer.load(1);
        $.post(contextPath + "/busiPort/dealflow",{portid:${busiPortNames}}, function (data) {
            var xlabels = [];
            var invalues = []
            var outvalues = []
            var legend = ['入流量','出流量'];
            $.each(data, function (index, iteam) {
                xlabels.push(iteam.recordTimeStr)
                invalues.push(iteam.inflowMbps)
                outvalues.push(iteam.outflowMbps)
//                if(isin){
//
//                }else{
//
//                }
            })
            var series = [{
                name: "入流量",
                type: 'line',
                smooth: false,
                showSymbol: false,
                data: invalues,
                symbol: 'circle',
                symbolSize: 6,
                areaStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(199, 237, 250,0.5)'
                        }, {
                            offset: 1,
                            color: 'rgba(199, 237, 250,0.2)'
                        }], false)
                    }
                },
                lineStyle: {
                    normal: {
                        width: 3
                    }
                }
            },
                {
                    name: '出流量',
                    type: 'line',
                    smooth: false,
                    showSymbol: false,
                    data: outvalues,
                    symbol: 'circle',
                    symbolSize: 6,
                    areaStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: 'rgba(199, 237, 250,0.5)'
                            }, {
                                offset: 1,
                                color: 'rgba(199, 237, 250,0.2)'
                            }], false)
                        }
                    },
                    lineStyle: {
                        normal: {
                            width: 3
                        }
                    }
                }
            ];

//            $.each(data, function (index, iteam) {
////                var newDate = new Date();
////                newDate.setTime(iteam.recordTimeStr);
//                var dateStr = iteam.recordTimeStr;//newDate.toLocaleString();
//                var x = xlabel[dateStr];
//                if (typeof (x) == 'undefined') {
//                    xlabel[dateStr] = 'a';
//                }
//                var busi = Map[iteam.portname];
//                if (typeof (busi) == 'undefined') {
//                    busi = {};
//                }
//                if(isin){
//                    busi[dateStr] = iteam.inflowMbps;
//                }else{
//                    busi[dateStr] = iteam.outflowMbps;
//                }
//                Map[iteam.portname] = busi
//            });
//            var xlabels = [];
//            for (var key in xlabel) {
//                xlabels.push(key);
//            }
//            var series = [];
//            for (var key in Map) {
//                var sdata = [];
//                var obj = Map[key];
//                $.each(xlabels, function (index, iteam) {
//                    var flow = obj[iteam];
//                    if (typeof (flow) == 'undefined') {
//                        flow = '-'
//                    } else {
//                        flow = flow.toFixed(4)
//                    }
//                    sdata.push(flow);
//                })
//                var serie = [{
//                    name: key,
//                    type: 'line',
//                    smooth: false,
//                    showSymbol: false,
//                    data: sdata,
//                    symbol: 'circle',
//                    symbolSize: 6,
//                    areaStyle: {
//                        normal: {
//                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
//                                offset: 0,
//                                color: 'rgba(199, 237, 250,0.5)'
//                            }, {
//                                offset: 1,
//                                color: 'rgba(199, 237, 250,0.2)'
//                            }], false)
//                        }
//                    },
////                        itemStyle: {
////                            normal: {
////                                color: '#f7b851'
////                            }
////                        },
//                    lineStyle: {
//                        normal: {
//                            width: 3
//                        }
//                    }
//                };
//                series.push(serie);
//                legend.push(key)
//            }
            var flowchart = echarts.getInstanceByDom(document.getElementById("canvs"));
            var option = flowchart.getOption();
            var options = {
                title: {
                    text: '业务端口'+(isin==true?'入':'出')+'流量'
                },
                xAxis: [{
                    data: xlabels,
                }],
                legend: {
                    orient: 'vertical',
                    right:'0',
                    align:'right',
                    data: legend
                },
                series: series
            };
            var newoptions = $.extend({}, option, options);
            flowchart.setOption(newoptions);
            top.layer.close(index);
        })
    };
    $(function () {
        var initChar = function () {
            var width = $("#canvs").parent().width()
            var height = $("#canvs").parent().height();
            var flowchart = echarts.init(document.getElementById("canvs"));
            var option = {
                title: {
                    text: '端口流量变化'
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: "{a} <br/>{b} : {c}Mbps",
                    confine: true
                },
                legend: {
                    data: []
                },
                grid: {
                    left: '3%',
                    right: '40',
                    bottom: '30%',
                    containLabel: true
                },
//                toolbox: {
//                    feature: {
//                        saveAsImage: {}
//                    }
//                },
                dataZoom: [
                    {
                        show: true,
                        realtime: true,
                        start: 0,
                        end: 100
                    }
                ],
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: []
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} Mbps'
                    }
                },
                series: []
            };
            flowchart.setOption(option);
            flowchart.resize({width: width, height: height});
            flowchart.on("datazoom",function(data){
                console.log(data);
            })
        };

        initChar();
        getData();
//        setInterval("getData()", 1000 * 60);
    })
    function doSubmit(flag){
        isin= flag;
        getData()
    }
</script>
</body>
</html>