<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <title>能耗分析</title>
    <style>
        body {
            margin: 0px;
        }
    </style>
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
</head>
<body>
<div id="pue" class="easyui-panel"
     style="padding:0px;background:#fafafa;" data-options="fit:true">
    <%--<div>--%>
    <%--开始时间：<input class="easyui-datebox" required="required" name="startTime" value=""--%>
    <%--data-options="formatter:myformatter,parser:myparser"/>---%>
    <%--结束时间：<input class="easyui-datebox" required="required" name="endTime" value=""--%>
    <%--data-options="formatter:myformatter,parser:myparser"/>--%>
    <%--<a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',onClick:setCPUData">查询</a>--%>
    <%--</div>--%>
    <div class="chart" unit="kw/h"></div>
</div>
<script type="text/javascript">
    $(function () {
        var chardiv = $("#pue").find(".chart");
        var height = $("#pue").height()
        chardiv.height(height - 2);
        setPUEData();
    })
    function setPUEData(){
        var chardiv = $("#pue").find(".chart").eq(0);
        var title = "最近一月能耗";
        var chart = echarts.getInstanceByDom(chardiv[0]);
        var unit = chardiv.attr("unit")
        if(chart==null){
            chart = initChar(chardiv,title,unit);
        }
        var startTime  = $(this).parent().find("input[name='startTime']").val();
        var endTime  = $(this).parent().find("input[name='endTime']").val();
        var xlabels = [],datas = [];
        <c:forEach items="${datas}" var="item">
            xlabels.push('${item.IDC_START_TIME}');
            datas.push(${item.electric});
        </c:forEach>
        var option = chart.getOption();
        option.xAxis[0].data = xlabels;
        //option.yAxis[0].axisLabel.formatter = unit;
        option.series = [{
            name: '历史能耗',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: datas
        }]
        chart.setOption(option);
//        callback(option,xlabels,datas);
    }
    function initChar(chardiv,title,unit){
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
                bottom: '10%',
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
</script>
</body>
</html>