<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>

    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script>
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
    </script>
    <style>
        table.kv-table {
             margin-bottom: 0px;
        }
        table.kv-table td.kv-content, table.kv-table td.kv-label {
             font-size: 12px;
             white-space:nowrap;
        }
    </style>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north'" style="height: 45px;overflow: hidden">
        <table class="kv-table">
            <tbody>
            <tr>
                <td class="kv-label">端口名字：</td>
                <td class="kv-content">${data.portname}</td>

                <td class="kv-label">报表类型:</td>
                <td class="kv-content">
                    <c:if test="${reportType=='portinmax'}">端口入流量峰值</c:if>
                    <c:if test="${reportType=='portoutmax'}">端口出流量峰值</c:if>
                    <c:if test="${reportType=='portcap'}">端口性能</c:if>
                </td>
                <td class="kv-label">开始时间：</td>
                <td class="kv-content">${startTime}</td>
                <td class="kv-label">结束时间：</td>
                <td class="kv-content">${endTime}</td>
                <td class="kv-label">统计力度:</td>
                <td class="kv-content">
                    <c:if test="${cyc=='mi'}">5分</c:if>
                    <c:if test="${cyc=='hour'}">小时</c:if>
                    <c:if test="${cyc=='day'}">天</c:if>

                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'north'" style="height: 250px;overflow: hidden">
                <div class="btn-cls-common" id="importData">导 出</div>
                <div id="charts" style="margin-top: 30px"></div>
            </div>
            <div data-options="region:'center'">
                <table class="kv-table">
                    <tbody>
                    <c:if test="${empty data}">
                        <tr>
                            <td class="kv-label">入流量峰值(Mbps):</td>
                            <td class="kv-content"></td>
                            <td class="kv-label">入流量谷值(Mbps)：</td>
                            <td class="kv-content"></td>
                            <td class="kv-label">入流量均值(Mbps):</td>
                            <td class="kv-content"></td>
                            <td class="kv-label">入峰值利用率(%)：</td>
                            <td class="kv-content"></td>
                            <td class="kv-label">入均值利用率(%)：</td>
                            <td class="kv-content"></td>
                        </tr><tr>
                        <td class="kv-label">出流量峰值(Mbps):</td>
                        <td class="kv-content"></td>
                        <td class="kv-label">出流量谷值(Mbps)：</td>
                        <td class="kv-content"></td>
                        <td class="kv-label">出流量均值(Mbps):</td>
                        <td class="kv-content"></td>
                        <td class="kv-label">出峰值利用率(%)：</td>
                        <td class="kv-content"></td>
                        <td class="kv-label">出均值利用率(%)：</td>
                        <td class="kv-content"></td>
                    </tr>
                    </c:if>
                    <c:if test="${not empty data}">
                        <tr>
                            <td class="kv-label">入流量峰值(Mbps):</td>
                            <td class="kv-content"><fmt:formatNumber value="${data.maxinflowMbps}" pattern="#0.0000" /></td>
                            <td class="kv-label">入流量谷值(Mbps)：</td>
                            <td class="kv-content"><fmt:formatNumber value="${data.mininflowMbps}" pattern="#0.0000" /></td>
                            <td class="kv-label">入流量均值(Mbps):</td>
                            <td class="kv-content"><fmt:formatNumber value="${data.avginflowMbps}" pattern="#0.0000" /></td>
                            <td class="kv-label">入峰值利用率(%)：</td>
                            <td class="kv-content"><fmt:formatNumber value="${data.maxinflowUR}" pattern="#0.0000" /></td>
                            <td class="kv-label">入均值利用率(%)：</td>
                            <td class="kv-content"><fmt:formatNumber value="${data.avginflowUR}" pattern="#0.0000" /></td>
                        </tr><tr>
                        <td class="kv-label">出流量峰值(Mbps):</td>
                        <td class="kv-content"><fmt:formatNumber value="${data.maxoutflowMbps}" pattern="#0.0000" /></td>
                        <td class="kv-label">出流量谷值(Mbps)：</td>
                        <td class="kv-content"><fmt:formatNumber value="${data.minoutflowMbps}" pattern="#0.0000" /></td>
                        <td class="kv-label">出流量均值(Mbps):</td>
                        <td class="kv-content"><fmt:formatNumber value="${data.avgoutflowMbps}" pattern="#0.0000" /></td>
                        <td class="kv-label">出峰值利用率(%)：</td>
                        <td class="kv-content"><fmt:formatNumber value="${data.maxoutflowUR}" pattern="#0.0000" /></td>
                        <td class="kv-label">出均值利用率(%)：</td>
                        <td class="kv-content"><fmt:formatNumber value="${data.avgoutflowUR}" pattern="#0.0000" /></td>
                    </tr>
                    </c:if>

                    </tbody>
                </table>

                <table id="dg1" class="easyui-datagrid"
                       data-options="singleSelect:true,remoteSort:false,checkbox:true,fitColumns:true,onLoadSuccess:LoadSuccess">
                    <thead>
                    <tr>
                        <th data-options="field:'recordTime',width:100">产生时间</th>
                        <th data-options="field:'inflow',width:100">入流量(Mbps)</th>
                        <th data-options="field:'inflowUR',width:100">入流量利用率(%)</th>
                        <th data-options="field:'outflow',width:100">出流量(Mbps)</th>
                        <th data-options="field:'outflowUR',width:100">出流量利用率(%)</th>
                     </tr>
                    </thead>
                    <tbody>

                    <c:if test="${flows!=null}">
                        <c:forEach var="iteam" items="${flows}">
                            <tr>
                                <td><fmt:formatDate value="${iteam.recordTime}" pattern="MM/dd HH:mm"/></td>
                                <td><fmt:formatNumber value="${iteam.inflowMbps}" pattern="#0.0000" /></td>
                                <td>
                                    <c:if test="${not empty iteam.bandwidth&&iteam.bandwidth>0}">
                                        <fmt:formatNumber value="${iteam.inflowMbps/iteam.bandwidth*100}" pattern="#0.0000" />
                                    </c:if>
                                </td>
                                <td><fmt:formatNumber value="${iteam.outflowMbps}" pattern="#0.0000" /></td>
                                <td>
                                    <c:if test="${not empty iteam.bandwidth&&iteam.bandwidth>0}">
                                        <fmt:formatNumber value="${iteam.outflowMbps/iteam.bandwidth*100}" pattern="#0.0000" />
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div id="toolbar">

</div>
<script type="text/javascript">
    $(function () {
        var xlabel = [];
        <c:forEach items="${times}" var="time">
            xlabel.push("${time}")
        </c:forEach>
        var inflows = ${inflows};
        var outflows = ${outflows};
        var initChar = function () {
            var width = $("#charts").parent().width()
            var height = $("#charts").parent().height()-30;
            var flowchart = echarts.init(document.getElementById("charts"),"macarons");
            var option = {
                title: {
                    text: '端口流量变化'
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: "{a} <br/>{b} : {c}Mbps",
                    confine: true
                },
//                textStyle:{
//                    color:'red'
//                },
                legend: {
                    data: ['入流量','出流量']
                },
                grid: {
                    left: '3%',
                    right: '40',
                    bottom: '30%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
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
                    data: xlabel
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} Mbps'
                    }
                },
                series: [
                 {
                    name: "入流量",
                    type: 'line',
                    smooth: false,
                    showSymbol: false,
                    data: inflows,
                    symbol: 'circle',
                    symbolSize: 6,
                     markPoint: {
                         label: {
                             normal: {
                                 textStyle: {
                                     color: 'black'
                                 }
                             }
                         },
                         data: [{
                             symbolSize:50,
                             type: 'max',
                             name: '峰值'
                         }, {
                             type: 'min',
                             name: '谷值'
                         }]
                     },
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
                        name: "出流量",
                        type: 'line',
                        smooth: false,
                        showSymbol: false,
                        data: outflows,
                        symbol: 'circle',
                        symbolSize: 6,
                        markPoint: {
                            label: {
                                normal: {
                                    textStyle: {
                                        color: 'black'
                                    }
                                }
                            },
                            data: [{
                                symbolSize:50,
                                type: 'max',
                                name: '峰值'

                            }, {
                                type: 'min',
                                name: '谷值'
                            }]
                        },
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
                ]
            };
            flowchart.setOption(option);
            flowchart.resize({width: width, height: height});
        };
        initChar();
    })
    function LoadSuccess(){
//        $("#dg").datagrid("sort", {	        // 指定了排序顺序的列
//            sortName: 'uag',
//            sortOrder: 'desc'
//        });
    }
    $(function () {

        $("#importData").click(function () {
            var myChart = echarts.getInstanceByDom(document.getElementById("charts"));
            var imgUrl = myChart.getDataURL("png");//获得img对象base64编码
            jQuery('<form action="'+contextPath +'/capreport/downcap" method="post">' +  // action请求路径及推送方法
                    '<input type="text" name="uuid" value="${uuid}"/>' + // 文件路径
                    '<input type="text" name="reportType" value="${reportType}"/>' + // 文件路径
                    '<input type="text" name="portType" value="${portType}"/>' + // 文件名称
                    '<input type="text" name="ports" value="${ports}"/>' + // 文件名称
                    '<input type="text" name="startTime" value="${startTime}"/>' + // 文件名称
                    '<input type="text" name="endTime" value="${endTime}"/>' + // 文件名称
                    '<input type="text" name="cyc" value="${cyc}"/>' + // 文件名称
                    '<input type="text" name="img" value="'+imgUrl+'"/>' + // 文件名称
                    '</form>').attr('target', '_blank')
                    .appendTo('body').submit().remove();
        })
    })
</script>
</body>
</html>