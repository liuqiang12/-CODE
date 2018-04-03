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
    <script src="<%=request.getContextPath() %>/framework/themes/js/echart/custom.theme.js"></script>
    <style>
        table.kv-table {
            margin-bottom: 0px;
        }

        .red {
            background-color: red;
        }
    </style>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north'" style="height: 70px;overflow: hidden">
        <table class="kv-table">
            <tbody>
            <tr>
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
        <div class="btn-cls-common" id="importData">导 出</div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'north'" style="height: 250px;overflow: hidden">
                <%--<div class="btn-cls-common" id="importData">导 出</div>--%>
                <div id="charts" style="margin-top: 0px"></div>
            </div>
            <div data-options="region:'center'">
                <table id="dg" class="easyui-datagrid"
                       data-options="fit:true,singleSelect:true,remoteSort:false,checkbox:true,fitColumns:true,toolbar:'#toolbar1',rowStyler:isMax">
                    <thead>
                    <tr>
                        <%--<th data-options="field:'portname',width:200">端口名字</th>--%>
                        <th data-options="field:'recordTime',width:100,sortable:true,">产生时间</th>
                        <%--<th data-options="field:'bandwidth',width:100">带宽(Mbps)</th>--%>
                        <th data-options="field:'maxflow',sortable:true,sorter:function(a,b){
                             return (parseFloat(a)>parseFloat(b)?1:-1);
                        },width:100"><c:if
                                test="${reportType=='portinmax'}">入</c:if><c:if
                                test="${reportType=='portoutmax'}">出</c:if>流量(Mbps)
                        </th>
                        <th data-options="field:'uag',width:100,sortable:true">端口利用率(%)
                        </th>

                    </tr>
                    </thead>
                    <c:if test="${data!=null}">
                        <c:forEach var="iteam" items="${data}">
                            <%--<td>${iteam.portname}</td>--%>
                            <td>${iteam.recordTime}</td>
                            <%--<td>${iteam.bandwidth}</td>--%>
                            <c:if test="${reportType=='portinmax'}">
                                <td><fmt:formatNumber value="${iteam.inflowMbps}" pattern="#0.0000"/></td>
                                <td>
                                    <c:if test="${not empty iteam.bandwidth&&iteam.bandwidth>0}">
                                        <fmt:formatNumber value="${iteam.inflowMbps /  iteam.bandwidth  * 100}"
                                                pattern="#0.0000"/>%
                                    </c:if>
                                </td>
                            </c:if>
                            <c:if test="${reportType=='portoutmax'}">
                                <td><fmt:formatNumber value="${iteam.outflowMbps}" pattern="#0.0000"/></td>
                                <td>
                                    <c:if test="${not empty iteam.bandwidth&&iteam.bandwidth>0}">
                                        <fmt:formatNumber value="${iteam.outflowMbps / iteam.bandwidth  * 100}"
                                                          pattern="#0.0000"/>%
                                    </c:if>

                                </td>
                            </c:if>

                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%--<div id="toolbar">--%>

<%--</div>--%>
<script type="text/javascript">

    //    (function ($) {
    //        var $defaults = {
    //            containerid: null
    //            , datatype: 'table'
    //            , dataset: null
    //            , columns: null
    //            , returnUri: false
    //            , worksheetName: "My Worksheet"
    //            , encoding: "utf-8"
    //        };
    //
    //        var $settings = $defaults;
    //
    //        $.fn.excelexportjs = function (options) {
    //            $settings = $.extend({}, $defaults, options);
    //
    //            var gridData = [];
    //            var excelData;
    //
    //            return Initialize();
    //
    //            function Initialize() {
    //                var type = $settings.datatype.toLowerCase();
    //
    //                BuildDataStructure(type);
    //
    //                switch (type) {
    //                    case 'table':
    //                        excelData = Export(ConvertFromTable());
    //                        break;
    //                    case 'json':
    //                        excelData = Export(ConvertDataStructureToTable());
    //                        break;
    //                    case 'xml':
    //                        excelData = Export(ConvertDataStructureToTable());
    //                        break;
    //                    case 'jqgrid':
    //                        excelData = Export(ConvertDataStructureToTable());
    //                        break;
    //                }
    //
    //                if ($settings.returnUri) {
    //                    return excelData;
    //                }
    //                else {
    //                    window.open(excelData);
    //                }
    //            }
    //
    //            function BuildDataStructure(type) {
    //                switch (type) {
    //                    case 'table':
    //                        break;
    //                    case 'json':
    //                        gridData = $settings.dataset;
    //                        break;
    //                    case 'xml':
    //                        $($settings.dataset).find("row").each(function (key, value) {
    //                            var item = {};
    //
    //                            if (this.attributes != null && this.attributes.length > 0) {
    //                                $(this.attributes).each(function () {
    //                                    item[this.name] = this.value;
    //                                });
    //
    //                                gridData.push(item);
    //                            }
    //                        });
    //                        break;
    //                    case 'jqgrid':
    //                        $($settings.dataset).find("rows > row").each(function (key, value) {
    //                            var item = {};
    //
    //                            if (this.children != null && this.children.length > 0) {
    //                                $(this.children).each(function () {
    //                                    item[this.tagName] = $(this).text();
    //                                });
    //
    //                                gridData.push(item);
    //                            }
    //                        });
    //                        break;
    //                }
    //            }
    //
    //            function ConvertFromTable() {
    //                var result = $('<div>').append($('#' + $settings.containerid).clone()).html();
    //                console.log(result)
    //                return result;
    //            }
    //
    //            function ConvertDataStructureToTable() {
    //                var result = "<table>";
    //
    //                result += "<thead><tr>";
    //                $($settings.columns).each(function (key, value) {
    //                    if (this.ishidden != true) {
    //                        result += "<th";
    //                        if (this.width != null) {
    //                            result += " style='width: " + this.width + "'";
    //                        }
    //                        result += ">";
    //                        result += this.headertext;
    //                        result += "</th>";
    //                    }
    //                });
    //                result += "</tr></thead>";
    //
    //                result += "<tbody>";
    //                $(gridData).each(function (key, value) {
    //                    result += "<tr>";
    //                    $($settings.columns).each(function (k, v) {
    //                        if (value.hasOwnProperty(this.datafield)) {
    //                            if (this.ishidden != true) {
    //                                result += "<td";
    //                                if (this.width != null) {
    //                                    result += " style='width: " + this.width + "'";
    //                                }
    //                                result += ">";
    //                                result += value[this.datafield];
    //                                result += "</td>";
    //                            }
    //                        }
    //                    });
    //                    result += "</tr>";
    //                });
    //                result += "</tbody>";
    //
    //                result += "</table>";
    //                return result;
    //            }
    //
    //            function Export(htmltable) {
    //                var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
    //                excelFile += "<head>";
    //                excelFile += '<meta http-equiv="Content-type" content="text/html;charset=' + $defaults.encoding + '" />';
    //                excelFile += "<!--[if gte mso 9]>";
    //                excelFile += "<xml>";
    //                excelFile += "<x:ExcelWorkbook>";
    //                excelFile += "<x:ExcelWorksheets>";
    //                excelFile += "<x:ExcelWorksheet>";
    //                excelFile += "<x:Name>";
    //                excelFile += "{worksheet}";
    //                excelFile += "</x:Name>";
    //                excelFile += "<x:WorksheetOptions>";
    //                excelFile += "<x:DisplayGridlines/>";
    //                excelFile += "</x:WorksheetOptions>";
    //                excelFile += "</x:ExcelWorksheet>";
    //                excelFile += "</x:ExcelWorksheets>";
    //                excelFile += "</x:ExcelWorkbook>";
    //                excelFile += "</xml>";
    //                excelFile += "<![endif]-->";
    //                excelFile += "</head>";
    //                excelFile += "<body>";
    //                excelFile += htmltable.replace(/"/g, '\'');
    //                excelFile += "</body>";
    //                excelFile += "</html>";
    //
    //                var uri = "data:application/vnd.ms-excel;base64,";
    //                var ctx = {worksheet: $settings.worksheetName, table: htmltable};
    //
    //                return (uri + base64(format(excelFile, ctx)));
    //            }
    //
    //            function base64(s) {
    //                return window.btoa(unescape(encodeURIComponent(s)));
    //            }
    //
    //            function format(s, c) {
    //                return s.replace(/{(\w+)}/g, function (m, p) {
    //                    return c[p];
    //                });
    //            }
    //        };
    //    })(jQuery);
    function LoadSuccess() {
//        $("#dg").datagrid("sort", {	        // 指定了排序顺序的列
//            sortName: 'uag',
//            sortOrder: 'desc'
//        });
    }
    var xlabel = [];
    var flows = [];
    var bandwidth = [];
    var legend = ''
    <c:forEach items="${data}" var="iteam">
    xlabel.push("<fmt:formatDate value="${iteam.recordTime}" pattern="MM/dd HH:mm"/>");
    <c:if test="${reportType=='portinmax'}">
    legend = '入流量';
    flows.push(<fmt:formatNumber value="${iteam.inflowMbps}" pattern="#0.0000"/>);
    <c:if test="${not empty iteam.bandwidth && iteam.bandwidth>0}">
    bandwidth.push(<fmt:formatNumber value="${iteam.inflowMbps/iteam.bandwidth  * 100}" pattern="#0.0000"/>);
    </c:if>
    </c:if>
    <c:if test="${reportType=='portoutmax'}">
    legend = '出流量';
    flows.push(<fmt:formatNumber value="${iteam.outflowMbps}" pattern="#0.0000"/>);
    <c:if test="${not empty iteam.bandwidth&&iteam.bandwidth>0}">
      bandwidth.push(<fmt:formatNumber value="${iteam.outflowMbps/iteam.bandwidth  * 100}" pattern="#0.0000"/>);
    </c:if>
    </c:if>
    </c:forEach>
    $(function () {
        $('#dg').datagrid('sort', {	        // 指定了排序顺序的列
            sortName: 'maxflow',
            sortOrder: 'desc'
        });    // 排序一个列
        $("#importData").click(function () {
            var myChart = echarts.getInstanceByDom(document.getElementById("charts"));
            var imgUrl = myChart.getDataURL("png");//获得img对象base64编码
            jQuery('<form action="' + contextPath + '/capreport/downreport" method="post">' +  // action请求路径及推送方法
                    '<input type="text" name="reportid" value="${uuid}"/>' + // 文件路径
                    '<input type="text" name="img" value="' + imgUrl + '"/>' + // 文件名称
                    '</form>').attr('target', '_blank')
                    .appendTo('body').submit().remove();
        })

        var initChar = function () {
            var width = $("#charts").parent().width()
            var height = $("#charts").parent().height() - 0;
            var flowchart = echarts.init(document.getElementById("charts"), "custom");
            var option = {
                title: {
                    text: '端口流量变化'
                },
//                backgroundColor:'#f5f5f5',
                textStyle: {
                    color: 'black'
                },
                tooltip: {
                    trigger: 'axis',
                    formatter: function (params, ticket, callback) {
                        console.log(params)
                        var res = params[0].name;

                        for (var i = 0, l = params.length; i < l; i++) {
                            if (params[i].seriesIndex === 1) {
                                res += '<br/>' + params[i].seriesName + ' : ' + (params[i].value ? params[i].value : '-') + '%';
                            } else {
                                res += '<br/>' + params[i].seriesName + ' : ' + (params[i].value ? params[i].value : '-') + 'Mbps';
                            }
                        }
                        return res;
                    }
                },
                legend: {
                    data: [legend, '带宽利用率']
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
                yAxis: [{
                    type: 'value',
                    name: '流量',
                    position: 'left',
                    axisLabel: {
                        formatter: '{value} Mbps'
                    }
                },
                    {
                        name: '带宽利用率',
                        max: 100,
                        position: 'right',
                        axisLabel: {
                            formatter: '{value} %'
                        },
                        type: 'value'
                    }],
                series: [
                    {
                        name: legend,
                        type: 'line',
                        smooth: false,
                        showSymbol: false,
                        data: flows,
                        symbol: 'circle',
                        symbolSize: 9,
                        showSymbol: false,
                        lineStyle: {
                            normal: {
                                width: 1
                            }
                        },
                        markPoint: {
                            data: [{
                                symbolSize: 50,
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
                        name: '带宽利用率',
                        type: 'line',
                        smooth: false,
                        showSymbol: false,
                        yAxisIndex: 1,
                        data: bandwidth,
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

                ]
            };
            flowchart.setOption(option);
            flowchart.resize({width: width, height: height});
        };
        initChar();
    })
    /***
     *判断是否最大
     */
    function isMax(index, row) {
        var maxindex = flows.indexOf(Math.max.apply(null, flows));
        console.log(row.maxflow);
        if (row.maxflow == flows[maxindex]) {
            return 'background-color:red;color:#fff;';
        }
    }
</script>
</body>
</html>