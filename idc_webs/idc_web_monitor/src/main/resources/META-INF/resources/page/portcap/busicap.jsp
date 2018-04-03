<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>

    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
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
<div style="overflow-x:hidden;width: 100%">
    <div style="height: 80px;text-align: right">
        <input type="hidden" id="id" value="${id}">
        <label id="busititle">${busi.busiportname}</label>
        <%--<input id="busititle" class="easyui-textbox" readonly="readonly" style="width:180px">--%>
        <input class="easyui-datetimebox" name="startTime" id="startTime"
               data-options="showSeconds:false,onChange:function(){
                                 $('#endTime').datetimebox('showPanel')
                               },prompt:'默认昨日',formatter:myformatter,parser:myparser" value="" style="width:180px">-
        <input class="easyui-datetimebox" name="endTime" id="endTime"
               data-options="prompt:'默认昨日',formatter:myformatter,parser:myparser" style="width:180px">
        <select class="easyui-combobox" data-options="onChange:changeDate,width:150" id="dateselect">
            <option value="-">-</option>
            <option value="24h">昨日</option>
            <option value="7day">最近7天</option>
            <option value="1month">最近1个月</option>
            <option value="3month">最近3个月</option>
            <%--<option value="currweek">本周</option>--%>
            <%--<option value="currmonth">本月</option>--%>
        </select>
        <select class="easyui-combobox" name="cyc" id="cyc" data-options="required:true,width:150">
            <option value="mi">5分钟</option>
            <option value="day">天</option>
            <option value="hour">小时</option>
        </select>
        <a class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="getData()">查询</a>
        <span><div class="btn-cls-common" id="importData">导 出</div></span>
        <span><div class="btn-cls-common" id="showData">显示数据</div></span>
    </div>
    <div style="height: 550px">
        <div id="charts" style=""></div>
    </div>
    <div>
        <table class="kv-table" id="captable">
            <tbody>
            <tr>
                <td class="kv-label">入流量峰值(Mbps):</td>
                <td class="kv-content inflowmax"></td>
                <td class="kv-label ">入流量谷值(Mbps)：</td>
                <td class="kv-content inflowmin"></td>
                <td class="kv-label">入流量均值(Mbps):</td>
                <td class="kv-content inflowavg"></td>
                <td class="kv-label">入峰值利用率(%)：</td>
                <td class="kv-content inflowmaxur"></td>
                <td class="kv-label">入均值利用率(%)：</td>
                <td class="kv-content inflowavgur"></td>
            </tr>
            <tr>
                <td class="kv-label">出流量峰值(Mbps):</td>
                <td class="kv-content outflowmax"></td>
                <td class="kv-label">出流量谷值(Mbps)：</td>
                <td class="kv-content outflowmin"></td>
                <td class="kv-label">出流量均值(Mbps):</td>
                <td class="kv-content outflowavg"></td>
                <td class="kv-label">出峰值利用率(%)：</td>
                <td class="kv-content outflowmaxur"></td>
                <td class="kv-label">出均值利用率(%)：</td>
                <td class="kv-content outflowavgur"></td>
            </tr>
            </tbody>
        </table>
    </div>
        <table id="dg1" class="easyui-datagrid"
               data-options="singleSelect:true,remoteSort:false,checkbox:true,fitColumns:true,onLoadSuccess:LoadSuccess">
            <thead>
            <tr>
                <th data-options="field:'portname',width:100">业务名称</th>
                <th data-options="field:'recordTime',width:100">产生时间</th>
                <th data-options="field:'inflow',width:100">入流量(Mbps)</th>
                <th data-options="field:'inflowUR',width:100">入流量利用率(%)</th>
                <th data-options="field:'outflow',width:100">出流量(Mbps)</th>
                <th data-options="field:'outflowUR',width:100">出流量利用率(%)</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
</div>
<%--<div style="position:absolute;z-index:100010;width: 100%;height: 100%;overflow-x:hidden;text-align: center">--%>
    <%--<div style="z-index:100011; width: 100%;height: 100%;background-color:black;filter:alpha(Opacity=20);-moz-opacity:0.2;opacity: 0.2; position:absolute; top:0px; left:0px;"></div>--%>
    <%--<div style="width:400px;height:200px;border:solid 1px #f7dd8c;background-color:#FFF;position:absolute;z-index:100012; left: 50%; top: 40%;transform: translate(-50%, -40%);">--%>
        <%--<input class="easyui-combotree" value="${groupids}" id="groupName1" style="width:300px" data-options="--%>
                          <%--url: '${contextPath}/busiPort/group/list?isSimple=1',--%>
                          <%--checkbox:true,--%>
                          <%--multiple:false,--%>
                          <%--cascadeCheck:false,--%>
                          <%--valueField: 'id',--%>
                          <%--textField: 'name'--%>
                        <%--">--%>
    <%--</div>--%>
<%--</div>--%>
<%--<div class="easyui-layout" fit="true" id="cc">--%>
    <%--&lt;%&ndash;<div class="content_suspend">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="conter">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<ul id="rtree" class="ztree" style="width:300px; overflow:auto;"></ul>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<div class="hoverbtn"><span>资</span><span>源</span><span>信</span><span>息</span>&ndash;%&gt;--%>

            <%--&lt;%&ndash;<div class="hoverimg" height="9" width="13"></div>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--<div data-options="region:'center'">--%>
        <%--<div class="easyui-layout" fit="true">--%>
            <%--<div data-options="region:'north'" style="height: 30px;overflow: hidden;text-align: right;padding-right: 50px">--%>
                <%--<input type="hidden" id="id" value="${id}">--%>
                <%--&lt;%&ndash;<span>业务：<input class="easyui-combotree" value="${groupids}" id="groupName" style="width:300px" data-options="&ndash;%&gt;--%>
                          <%--&lt;%&ndash;url: '${contextPath}/busiPort/listbytree?isSimple=1',&ndash;%&gt;--%>
                          <%--&lt;%&ndash;checkbox:true,&ndash;%&gt;--%>
                          <%--&lt;%&ndash;multiple:false,&ndash;%&gt;--%>
                          <%--&lt;%&ndash;cascadeCheck:false,&ndash;%&gt;--%>
                          <%--&lt;%&ndash;valueField: 'id',&ndash;%&gt;--%>
                          <%--&lt;%&ndash;textField: 'name',&ndash;%&gt;--%>
                          <%--&lt;%&ndash;onLoadSuccess:function(node,data){&ndash;%&gt;--%>
                           <%--&lt;%&ndash;&lt;%&ndash;console.log(data)&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;var nodes = [];&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;<c:forEach var="group" items="${groups}">&ndash;%&gt;&ndash;%&gt;--%>
                                   <%--&lt;%&ndash;&lt;%&ndash;nodes.push({ id: ${group.id}, text: '${group.name}' })&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;</c:forEach>&ndash;%&gt;&ndash;%&gt;--%>
                            <%--&lt;%&ndash;&lt;%&ndash;$('#groupName').combotree('setValues', nodes);&ndash;%&gt;&ndash;%&gt;--%>
                          <%--&lt;%&ndash;}&ndash;%&gt;--%>
                        <%--&lt;%&ndash;"></span>&ndash;%&gt;--%>
                <%--<input class="easyui-datetimebox" name="startTime" id="startTime"--%>
                       <%--data-options="showSeconds:false,onChange:function(){--%>
                                 <%--$('#endTime').datetimebox('showPanel')--%>
                               <%--},prompt:'默认昨日',formatter:myformatter,parser:myparser" value="" style="width:180px">---%>
                <%--<input class="easyui-datetimebox" name="endTime" id="endTime"--%>
                       <%--data-options="prompt:'默认昨日',formatter:myformatter,parser:myparser" style="width:180px">--%>
                <%--<select class="easyui-combobox" data-options="onChange:changeDate,width:150" id="dateselect" >--%>
                    <%--<option value="-">-</option>--%>
                    <%--<option value="24h">昨日</option>--%>
                    <%--<option value="7day">最近7天</option>--%>
                    <%--<option value="1month">最近1个月</option>--%>
                    <%--<option value="3month">最近3个月</option>--%>
                    <%--&lt;%&ndash;<option value="currweek">本周</option>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<option value="currmonth">本月</option>&ndash;%&gt;--%>
                <%--</select>--%>
                <%--<select class="easyui-combobox" name="cyc" id="cyc" data-options="required:true,width:150">--%>
                    <%--<option value="mi">5分钟</option>--%>
                    <%--<option value="day">天</option>--%>
                    <%--<option value="hour">小时</option>--%>
                <%--</select>--%>
                <%--<a  class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="getData()">查询</a>--%>
                <%--&lt;%&ndash;<a  class="easyui-linkbutton" data-options="iconCls:'icon-search'">导出</a>&ndash;%&gt;--%>
                <%--<span><div class="btn-cls-common" id="importData">导 出</div></span>--%>
            <%--</div>--%>

            <%--<div data-options="region:'center'">--%>
                <%--<div class="easyui-layout" fit="true">--%>
                    <%--<div data-options="region:'north'" style="height: 250px;overflow: hidden">--%>
                        <%--&lt;%&ndash;<div class="btn-cls-common" id="importData">导 出</div>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<div style="position:absolute;z-index:19890990;right: 50px;width: 200px;height: 100px;overflow-x:hidden ">&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<div style="height: 300px">1111</div>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                        <%--<div id="charts" style="margin-top: 30px"></div>--%>
                    <%--</div>--%>
                    <%--<div data-options="region:'center'">--%>
                        <%--<table class="kv-table" id="captable">--%>
                            <%--<tbody>--%>
                            <%--<tr>--%>
                                <%--<td class="kv-label">入流量峰值(Mbps):</td>--%>
                                <%--<td class="kv-content inflowmax"></td>--%>
                                <%--<td class="kv-label ">入流量谷值(Mbps)：</td>--%>
                                <%--<td class="kv-content inflowmin"></td>--%>
                                <%--<td class="kv-label">入流量均值(Mbps):</td>--%>
                                <%--<td class="kv-content inflowavg"></td>--%>
                                <%--<td class="kv-label">入峰值利用率(%)：</td>--%>
                                <%--<td class="kv-content inflowmaxur"></td>--%>
                                <%--<td class="kv-label">入均值利用率(%)：</td>--%>
                                <%--<td class="kv-content inflowavgur"></td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td class="kv-label">出流量峰值(Mbps):</td>--%>
                                <%--<td class="kv-content outflowmax"></td>--%>
                                <%--<td class="kv-label">出流量谷值(Mbps)：</td>--%>
                                <%--<td class="kv-content outflowmin"></td>--%>
                                <%--<td class="kv-label">出流量均值(Mbps):</td>--%>
                                <%--<td class="kv-content outflowavg"></td>--%>
                                <%--<td class="kv-label">出峰值利用率(%)：</td>--%>
                                <%--<td class="kv-content outflowmaxur"></td>--%>
                                <%--<td class="kv-label">出均值利用率(%)：</td>--%>
                                <%--<td class="kv-content outflowavgur"></td>--%>
                            <%--</tr>--%>
                            <%--</tbody>--%>
                        <%--</table>--%>

                        <%--<table id="dg1" class="easyui-datagrid"--%>
                               <%--data-options="singleSelect:true,remoteSort:false,checkbox:true,fitColumns:true,onLoadSuccess:LoadSuccess">--%>
                            <%--<thead>--%>
                            <%--<tr>--%>
                                <%--<th data-options="field:'recordTime',width:100">产生时间</th>--%>
                                <%--<th data-options="field:'inflow',width:100">入流量(Mbps)</th>--%>
                                <%--<th data-options="field:'inflowUR',width:100">入流量利用率(%)</th>--%>
                                <%--<th data-options="field:'outflow',width:100">出流量(Mbps)</th>--%>
                                <%--<th data-options="field:'outflowUR',width:100">出流量利用率(%)</th>--%>
                            <%--</tr>--%>
                            <%--</thead>--%>
                            <%--<tbody>--%>
                            <%--</tbody>--%>
                        <%--</table>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<input type="hidden" value="" id="guid">
<%--<div id="qq" style="display: none">--%>
    <%--<div>--%>
        <%--<input class="easyui-combotree" value="${groupids}" id="groupName" style="width:300px" data-options="--%>
                          <%--url: '${contextPath}/busiPort/group/list?isSimple=1',--%>
                          <%--checkbox:true,--%>
                          <%--multiple:false,--%>
                          <%--cascadeCheck:false,--%>
                          <%--valueField: 'id',--%>
                          <%--textField: 'name',--%>
                          <%--onLoadSuccess:function(node,data){--%>
                           <%--&lt;%&ndash;console.log(data)&ndash;%&gt;--%>
                            <%--&lt;%&ndash;var nodes = [];&ndash;%&gt;--%>
                            <%--&lt;%&ndash;<c:forEach var="group" items="${groups}">&ndash;%&gt;--%>
                                   <%--&lt;%&ndash;nodes.push({ id: ${group.id}, text: '${group.name}' })&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</c:forEach>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;$('#groupName').combotree('setValues', nodes);&ndash;%&gt;--%>
                          <%--}--%>
                        <%--">--%>
    <%--</div>--%>
    <%--<div>--%>
    <%--<input class="easyui-datetimebox" name="startTime" id="startTime"--%>
           <%--data-options="showSeconds:false,onChange:function(){--%>
                                 <%--$('#endTime').datetimebox('showPanel')--%>
                               <%--},prompt:'默认昨日',formatter:myformatter,parser:myparser" value="" style="width:180px">---%>
    <%--<input class="easyui-datetimebox" name="endTime" id="endTime"--%>
           <%--data-options="prompt:'默认昨日',formatter:myformatter,parser:myparser" style="width:180px">--%>
    <%--<select class="easyui-combobox" data-options="onChange:changeDate,width:150" id="dateselect" >--%>
        <%--<option value="-">-</option>--%>
        <%--<option value="24h">昨日</option>--%>
        <%--<option value="7day">最近7天</option>--%>
        <%--<option value="1month">最近1个月</option>--%>
        <%--<option value="3month">最近3个月</option>--%>
        <%--&lt;%&ndash;<option value="currweek">本周</option>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<option value="currmonth">本月</option>&ndash;%&gt;--%>
    <%--</select></div>--%>
    <%--<select class="easyui-combobox" name="cyc" data-options="required:true,width:150">--%>
        <%--<option value="mi">5分钟</option>--%>
        <%--<option value="day">天</option>--%>
        <%--<option value="hour">小时</option>--%>
    <%--</select>--%>
    <%--<a  class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>--%>
    <%--<a  class="easyui-linkbutton" data-options="iconCls:'icon-search'">导出</a>--%>
    <%--<span><div class="btn-cls-common" id="importData">导 出</div></span>--%>
<%--</div>--%>
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
    function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();;
    }
    function myparser(ps){
        if (!ps) return new Date();
        var st =ps.split(" ");
        var ss = st[0].split('-');
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);

        var ss1 = (st[1].split(':'));
        var h = parseInt(ss1[0],10);
        var mm = parseInt(ss1[1],10);
        var s = parseInt(ss1[2],10);

        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d,h,mm,s);
        } else {
            return new Date();
        }
    }
    function changeDate(cvalue) {
        var date = new Date();
        if ("24h" == cvalue){
//            var ndate = cala(date, -1);
            $("#endTime").datetimebox({value: date.Format("yyyy-MM-dd hh:mm:ss")})
            $("#startTime").datetimebox({value: date.add("d",-1).Format("yyyy-MM-dd hh:mm:ss")})
            console.log(date.add("d",-1).Format("yyyy-MM-dd hh:mm:ss"))
        } else if ("7day" == cvalue) {
//            var ndate = cala(date, -7);
//            var edate = cala(date, -1);
            $("#endTime").datetimebox({value: date.Format("yyyy-MM-dd hh:mm:ss")})
            $("#startTime").datetimebox({value: date.add("d",-7).Format("yyyy-MM-dd hh:mm:ss")})
//            $("#startTime").datetimebox({value: ndate + " 00:00:00"})
//            $("#endTime").datetimebox({value: edate + " 23:59:59"})
        }
        else if ("1month" == cvalue) {
            $("#endTime").datetimebox({value: date.Format("yyyy-MM-dd hh:mm:ss")})
            $("#startTime").datetimebox({value: date.add("m",-1).Format("yyyy-MM-dd hh:mm:ss")})
//            var ndate = cala(date, -1,'m');
//            var edate = cala(date, -1);
//            $("#startTime").datetimebox({value: ndate + " 00:00:00"})
//            $("#endTime").datetimebox({value: edate + " 23:59:59"})
        }
        else if ("3month" == cvalue) {
            $("#endTime").datetimebox({value: date.Format("yyyy-MM-dd hh:mm:ss")})
            $("#startTime").datetimebox({value: date.add("m",-3).Format("yyyy-MM-dd hh:mm:ss")})
//            var ndate = cala(date, -3,'m');
//            var edate = cala(date, -1);
//            $("#startTime").datetimebox({value: ndate + " 00:00:00"})
//            $("#endTime").datetimebox({value: edate + " 23:59:59"})
        }
    }
    function cala(date, decday, type) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var ddd = decday;
        var theday = new Date();
        if (typeof (type) == 'undefined' || type == 'd') {
            var ttt = new Date(y, m - 1, d).getTime() + ddd * 24000 * 3600;
            theday.setTime(ttt);
        }
        if (type == 'm') {
            var ttt = new Date(y, m - 1 + ddd, d).getTime();
            theday.setTime(ttt);
        }
        else if (type == 'y') {
            var ttt = new Date(y + ddd, m - 1, d).getTime();
            theday.setTime(ttt);
        }


//        return theday;
        return dateToStr(theday)

    }
    function dateToStr(theday) {
        return theday.getFullYear()+"-"+(theday.getMonth() + 1) + "-" + theday.getDate();
        //return (theday.getMonth() + 1) + "/" + theday.getDate() + "/" + theday.getFullYear();
    }
    var tableData = null;
    $(function () {
            $("#showData").click(function(){
                var index = top.layer.load(1);
                initTable(tableData);
                top.layer.close(index)
            })
//        layer.open({
//            type: 1,
//            shade: false,
//            area: ['500px', '300px'],
//            title: false, //不显示标题
//            zIndex:99,
//            content: $('#qq'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
//            cancel: function(){
//                layer.msg('捕获就是从页面已经存在的元素上，包裹layer的结构', {time: 5000, icon:6});
//            }
//        });

        changeDate("24h");
        var xlabel=[],inflows=[],outflows;
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
//                    formatter: "{a} <br/>{b} : {c}Mbps",
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
//                    bottom: '30%',
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
                    axisLabel:{
                        interval:24,
                        rotate:55
                    },
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
        getData();
    })

    function getData(){
        var index = top.layer.open({
            type:3
            ,shade:0.5
            ,content:'加载中...'
            ,icon:1
        }); //换了种风格
        var id =$("#id").val();
        var startTime =$("#startTime").val();
        var endTime =$("#endTime").val();
        var cyc =$("#cyc").val();
        initTable([])
        $.post(contextPath+"/capreport/busicap",{
            id:id
            ,startTime:startTime
            ,endTime:endTime
            ,cyc:cyc
        },function(data){
            top.layer.close(index);
            if(typeof(data.max) !='undefined'){
                initCap(data.max);
            }
            if(typeof(data.times) !='undefined'){
                var portname = "";
                if(data.flows){
                    portname = data.flows[0].portname
                   // $("#busititle").text(portname);
                }
                setChartData(portname,data.times,data.inflows,data.outflows);
            }

//            if(typeof(data.flows) !='undefined'){
            if(typeof(data.flows) !='undefined'){
                tableData=data.flows
            }
            $("#guid").val(data.guid);

        })
    }
    function initTable(list){
        $("#dg1").datagrid("loadData",list)
    }
    function setChartData(title,times,inflows,outflows){
        var series =     [
            {
                name: "入流量",
                type: 'line',
                smooth: false,
                showSymbol: false,
                data: inflows,
                symbol: 'circle',
                symbolSize: 6,
                markLine : {
                    data : [
                        {type : 'average', name: '入流量均值'}
                    ]
                },

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
                markLine : {
                    data : [
                        {type : 'average', name: '出流量均值'}
                    ]
                },
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
        var flowchart = echarts.getInstanceByDom(document.getElementById("charts"));

        var option = flowchart.getOption();
        var options = {
//            title:{
//                text:title+ '端口流量变化'
//            },
            xAxis: [{
                axisLabel: {
                    interval: function () {
                        if(times.length>288*30){
                            return 287;
                        }
                        if(times.length>288*15){
                            return 143;
                        }
                       if(times.length>288*7){
                           return 50;
                       }
                        if(times.length>288){
                            return 40;
                        }
                        if(times.length>100){
                            return 8;
                        }
                        if(times.length>50){
                            return 2;
                        }
                        else{
                            return 0;
                        }

                    }(),
                    rotate: 55
                },
                data: times
                }],
//            xAxis: [{
//                data: times,
//            }],
            series: series
        };
        var newoptions = $.extend({}, option, options);
        flowchart.setOption(newoptions);
    }
    function initCap(data){
        $("#captable .inflowmax").text(data.maxinflowMbps.toFixed(4))
        $("#captable .inflowmin").text(data.mininflowMbps.toFixed(4))
        $("#captable .inflowavg").text(data.avginflowMbps.toFixed(4))
        $("#captable .inflowmaxur").text(data.maxinflowUR.toFixed(4))
        $("#captable .inflowavgur").text(data.avginflowUR.toFixed(4))
        $("#captable .outflowmax").text(data.maxoutflowMbps.toFixed(4))
        $("#captable .outflowmin").text(data.minoutflowMbps.toFixed(4))
        $("#captable .outflowavg").text(data.avgoutflowMbps.toFixed(4))
        $("#captable .outflowmaxur").text(data.maxoutflowUR.toFixed(4))
        $("#captable .outflowavgur").text(data.avgoutflowUR.toFixed(4))
    }
    function LoadSuccess(){
//        $("#dg").datagrid("sort", {	        // 指定了排序顺序的列
//            sortName: 'uag',
//            sortOrder: 'desc'
//        });
    }
    $(function () {
        $("#importData").click(function () {
            var myChart = echarts.getInstanceByDom(document.getElementById("charts"));
            var guid= $("#guid").val();
            var imgUrl = myChart.getDataURL("png");//获得img对象base64编码
            jQuery('<form action="'+contextPath +'/capreport/downbusicap" method="post">' +  // action请求路径及推送方法
                    '<input type="text" name="uuid" value="'+guid+'"/>' + // 文件路径
                    '<input type="text" name="reportType" value="1"/>' + // 文件路径
                    '<input type="text" name="portType" value="1"/>' + // 文件名称
                    '<input type="text" name="ports" value=""/>' + // 文件名称
                    '<input type="text" name="startTime" value=""/>' + // 文件名称
                    '<input type="text" name="endTime" value=""/>' + // 文件名称
                    '<input type="text" name="cyc" value=""/>' + // 文件名称
                    '<input type="text" name="img" value="'+imgUrl+'"/>' + // 文件名称
                    '</form>').attr('target', '_blank')
                    .appendTo('body').submit().remove();
        })
    })
</script>
</body>
</html>