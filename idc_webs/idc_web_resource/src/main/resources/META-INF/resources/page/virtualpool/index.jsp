<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
<link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/import_skin.css" rel="stylesheet"
      type="text/css" id="skin" themeColor="${skin}"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_page.css" rel="stylesheet"
      type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer.css" rel="stylesheet"
      type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/swipers.css" rel="stylesheet"
      type="text/css"/>

<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/normalize.css"
      rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/main.css"
      rel="stylesheet" type="text/css"/>

<link rel="stylesheet"
      href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/bootstrap-switch.css"/>

<!--
<script src="<%=request.getContextPath() %>/framework/themes/js/jquery-1.4.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/plugins/jquery/jquery-1.10.2.min.js"></script>

-->
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/jquery.jqDock.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/fisheye-iutil.min.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/js/echart/custom.theme.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/js/swipers.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/jquery.autocompleter.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/main.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/bootstrap-switch.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/highlight.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/main.js"></script>


<style>
</style>
</head>
<body id="scrollContent">
<!-- 界面布局情况 -->
<div class="desklayout height100">
    <!-- 分三个div并排  -->
    <div class="deskLayout001 height100">
        <!-- 1DIV start -->
        <div id="layout" class="float_left col width100 height100" style="background-color:#E9ECF3;overflow-y: auto;">
            <div class="width100 relative-position box-sizing" style="height:240px;border:5px solid transparent;">
                <div style="text-align: center;font-size: 80px;margin-top: 50px;height:100px;" class="addbtn">
                    +点击添加
                </div>
            </div>
            <%--<div id="layout" class="width100 relative-position box-sizing"--%>
                 <%--style="border:5px solid transparent;">--%>
                <%----%>
                <%--<div class="width100 relative-position box-sizing" style="float:left;height:120px">--%>
                    <%--<div style="text-align: center;font-size: 80px;margin-top: 50px;height:100px;">--%>
                        <%--+点击添加--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div  class="width100 box-sizing" style="border:5px solid transparent;">--%>

            <%--</div>--%>
        </div>
    </div>
</div>
<script type="text/template" id="dtemplate">
    <div class="width100 relative-position box-sizing" style="height:240px;border:5px solid transparent;">
        <div class="height100 width100 relative-position box-sizing" style="float:left;">
        <!--  标题设置 start  -->
        <div class="titleBox_center">
            <div class="titleBox_left">
                <div class="titleBox_right">
                    <!-- 图标标题操作 -->
                    <div class="box1_icon khywtjicon">
                        <div class="box_title"></div>
                        <div class="box1_status">
                        </div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
        <!--  下方内容 -->
        <div class="box1_content quick_content" style="overflow:hidden;" >
            <div class="width40 height100 box-sizing"
                 style="float:left;border-right: 5px solid transparent;">
                <div style="height:22px;margin-top:6px;color: #6c6c6c;font-weight: 400;">
                    资源统计：
                </div>
                <div class="title_desc2" style="margin-top:5px;">
                    <div class="width30 box-sizing " style="float:left;">
                        <h5 class="width100" style="float:left;text-align: center;font-weight:bold">硬盘</h5>

                        <div>
                            <h6 class="width50" style="text-align:right">总量：</h6><h6
                                class="width50 disksize"></h6>
                        </div>
                        <div>
                            <h6 class="width50" style="text-align:right">使用：</h6><h6
                                class="width50 diskuserate"></h6>
                        </div>
                    </div>


                    <div class="width40 box-sizing left_border right_border" style="float:left;"><h5
                            class="width100" style="float:left;text-align: center;font-weight:bold">内存</h5>

                        <div>
                            <h6 class="width60" style="text-align:right">总量：</h6><h6
                                class="width40 memsize"></h6>
                        </div>
                        <div>
                            <h6 class="width60" style="text-align:right">使用：</h6><h6
                                class="width40 memuserage"></h6>
                        </div>
                    </div>

                    <div class="width30 box-sizing" style="float:left;">
                        <div class="height50 width100 box-sizing" style="float:left;">
                            <h5 class="width100" style="float:left;text-align: center;font-weight:bold">CPU</h5>

                            <div>
                                <h6 class="width60" style="text-align:right">总量：</h6>
                                <h6 class=" cpusize"></h6>
                            </div>
                            <div>
                                <h6 class="width60" style="text-align:right">使用：</h6>
                                <h6 class=" cpuuserate"></h6>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="width60 height100 box-sizing left_border" style="float:left;">
                <div class="height100 width33  chartdisk" style="float:left;"></div>
                <div class="height100 width33  chartmem"  style="float:left;"></div>
                <div class="height100 width33  chartcpu"  style="float:left"></div>
            </div>
        </div>
    </div>
        </div>
</script>
<script>
    (function ($) {
//        function SinPanel(options) {
//            var me = $(this);
//            var disksize = 0, diskrate = 0, memsize = 0, memrate = 0, cpunum = 0, cpurate = 0, name = "";
//            var defualts = {
//                name: name,
//                disksize: disksize,
//                diskrate: diskrate,
//                memsize: memsize,
//                memrate: memrate,
//                cpunum: cpunum,
//                cpurate: cpurate
//            }
//            var opts = $.extend({}, defualts, options);
//        }
        $.fn.SinPanel = function (options) {
            var me = $(this);
            var disksize = 0, diskrate = 0, memsize = 0, memrate = 0, cpunum = 0, cpurate = 0, name = "";
            var defualts = {
                name: name,
                disksize: disksize,
                diskrate: diskrate,
                memsize: memsize,
                memrate: memrate,
                cpunum: cpunum,
                cpurate: cpurate
            }
            var opts = $.extend({}, defualts, options);
            var $e = $(this);
            me.init = function () {
                console.log(self)
            }
            me.getValue=function(){
                return me.disksize;
            }
            return me;
        }
    })(jQuery);
    function buildChart(){
        var template = $("#dtemplate").html();
        $.getJSON(contextPath + "/virtualpool/list", function (data) {
            $.each(data.rows, function (i, item) {
                var panel = $(template).clone();
                panel.find(".box_title").text(item.idcResourcesPoolName);
                panel.find(".quick_content .disksize").text(item.idcResourcesDiskSize);
                panel.find(".quick_content .memsize").text(item.idcResourcesMemSize);
                panel.find(".quick_content .cpusize").text(item.idcResourcesCpuNum);
                panel.find(".quick_content .diskuserate").text(item.idcResourcesDiskUseRate);
                panel.find(".quick_content .memuserage").text(item.idcResourcesMemUseRate);
                panel.find(".quick_content .cpuuserate").text(item.idcResourcesCpuUseRate);
                panel.data("poolid",item.idcResourcesPoolId)
                $("#layout").prepend(panel);
                panel.click(function(){
                    var id = $(this).data("poolid");
                    location.href=contextPath+"/virtualpool/usedbybusi/"+id
                })
                initCharts(panel,item);
            })
        })
    }
    function initCharts(panel,iteam){
        var diskdom = panel.find(".chartdisk")[0];
        var memdom = panel.find(".chartmem")[0];
        var cpudom = panel.find(".chartcpu")[0];
        initChart(diskdom,{
            title:'硬盘使用情况(%)',
            used:iteam.idcResourcesDiskUseRate,
            notUsed:100-iteam.idcResourcesDiskUseRate
        })
        initChart(memdom,{
            title:'内存使用情况(%)',
            used:iteam.idcResourcesMemUseRate,
            notUsed:100-iteam.idcResourcesMemUseRate
        })
        initChart(cpudom,{
            title:'CPU使用情况(核)',
            used:iteam.idcResourcesCpuUseRate,
            notUsed:iteam.idcResourcesCpuNum-iteam.idcResourcesCpuUseRate
        })
    }
    function initChart(dom,data){
        var mychart = echarts.init(dom,'custom');
        mychart.setOption(buildChartOpts(data));
    }
    function buildChartOpts(data) {
        var datas = [
            {value: data.used, name: '使用率'},
            {value: data.notUsed, name: '空闲率'}
        ];
        var legends = ['使用率' , '空闲率' ];
        var option = {
            title: {
                text: data.title,
                textStyle: {
                    //color: '#6E6A6F',
                    fontSize: 14
                },
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'item',
                confine: true,
                formatter: "{b} : {c} ({d}%)"
            },
            legend: {
                data: legends,
                orient: "horizontal",
                y: 'bottom'
            },
            series: [
                {
                    name: '占比',
                    type: 'pie',
                    selectedMode: 'single',
                    radius: ['10%', '55%'],
                    center: ['50%', '50%'],
                    //roseType : 'area',
                    data: datas,
                    label: {
                        normal: {
                            position: 'inner',
                            formatter: "{c}"
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            //shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        emphasis: {
                            borderWidth: 1,
                            borderColor: '#CCC'

                        }
                    }
                }
            ]
        };
        return option;
    }
    /***
     * 单个区域
     * */
    $(function () {
        $(".addbtn").on("click",function(){
            openDialog('编辑信息',contextPath + '/virtualpool/form/0','800px','530px')
        })
        buildChart();
//        $.each([{idcResourcesPoolName:'name1',idcResourcesDiskSize:100,idcResourcesMemSize:32,idcResourcesCpuNum:16,idcResourcesDiskUseRate:15,idcResourcesMemUseRate:55,idcResourcesCpuUseRate:5},
//                {idcResourcesPoolName:'name2',idcResourcesDiskSize:150,idcResourcesMemSize:22,idcResourcesCpuNum:12,idcResourcesDiskUseRate:75,idcResourcesMemUseRate:65,idcResourcesCpuUseRate:9},
//                {idcResourcesPoolName:'name3',idcResourcesDiskSize:120,idcResourcesMemSize:62,idcResourcesCpuNum:10,idcResourcesDiskUseRate:65,idcResourcesMemUseRate:35,idcResourcesCpuUseRate:10}], function (i, item) {
//            var panel = $(template).clone();
//            panel.find(".box_title").text(item.idcResourcesPoolName);
//            panel.find(".quick_content .disksize").text(item.idcResourcesDiskSize);
//            panel.find(".quick_content .memsize").text(item.idcResourcesMemSize);
//            panel.find(".quick_content .cpusize").text(item.idcResourcesCpuNum);
//            panel.find(".quick_content .diskuserate").text(item.idcResourcesDiskUseRate);
//            panel.find(".quick_content .memuserate").text(item.idcResourcesMemUseRate);
//            panel.find(".quick_content .cpuuserate").text(item.idcResourcesCpuUseRate);
//            //initCharts(panel,item);
//            $("#layout").prepend(panel);
//        })


    })
    var idc1 = "";
    <c:if test="${dataviewstr!=null}">
    idc1 =${dataviewstr};
    </c:if>
    var charts = [
//		["chart","chart1","chart2","chart3","chart4"],
        ["chart11", "chart12", "chart13", "chart14", "chart15"],
        ["chart21", "chart22", "chart23", "chart24", "chart25"]
    ];
    $(function () {
//        var tmp = $("#dtemplate").html();
//        $("#layout").append(tmp);
//        $("#layout").append(tmp);
//        $("#layout").append(tmp);
//        $("#layout").append(tmp);
        var localcharts = ["chart", "chart1", "chart2", "chart3"];

        localcharts.forEach(function (iteam, j) {
            var mychart = echarts.init(document.getElementById(iteam));
            // 分项占用比例(总)
            if (j == 0) {
                mychart.setOption(buildResPercentOptData("机柜占用数", "", idc1, "rackusage"));
            } else if (j == 1) {
                mychart.setOption(buildResPercentOptData("带宽占用数", "", idc1, "bandwidthusage"));
            } else if (j == 2) {
                mychart.setOption(buildResPercentOptData("IP占用数", "", idc1, "ipusage"));
            } else if (j == 3) {
                mychart.setOption(buildResPercentOptData("核心端口占用数", "", idc1, "coreportusage"));
            } else if (j == 4) {
                mychart.setOption(buildResPercentOptData("主机占用数", "", idc1, "hostDeviceUsage"));
            }
        });

        for (var i = 0; i < charts.length; i++) {
            for (var j = 0; j < charts[i].length; j++) {
                if ($(charts[i][j])) {
                    var mychart = echarts.init(document.getElementById(charts[i][j]));
                    // 分项占用比例(总)
                    if (j == 0) {
                        mychart.setOption(buildResPercentOpt("客户机柜占用数", "", 34, 94));
                    } else if (j == 1) {
                        mychart.setOption(buildResPercentOpt("带宽占用数", "", 52, 385));
                    } else if (j == 2) {
                        mychart.setOption(buildResPercentOpt("IP占用数", "", 14, 152));
                    } else if (j == 3) {
                        mychart.setOption(buildResPercentOpt("网络设备占用数", "", 53, 241));
                    } else if (j == 4) {
                        mychart.setOption(buildResPercentOpt("主机占用数", "", 53, 241));
                    }
                }
            }
        }


    });


    //构建机房假数据
    function buildSiteDatas(size) {
        var result = [];
        for (var i = 0; i < size; i++) {
            result.push({
                daydata: randoms(150, 500),
                sitename: 'YD_SC_CD_XY-' + (i + 1) + '机房',
                monthdata: randoms(4500, 15000)
            });
        }
        return result;
    }

    function getValsFromItemsBykey(items, key) {
        var result = [];
        if (items && items.length > 0) {
            for (var i = 0; i < items.length; i++) {
                var it = items[i];
                result.push(it[key]);
            }
        }
        return result;
    }

    //根据max\min生成随机数
    function randoms(min, max, isFixed) {
        var result = Math.random() * max;
        if (min > result) {
            result = randoms(min, max);
        }
        var fixed = 2;
        if (typeof isFixed != "undefined") {
            fixed = isFixed;
        }
        return parseFloat(parseFloat(result).toFixed(fixed));
    }


    function initDataByLen(str, char, len) {
        var result = str;
        if (result && result.length < len) {
            for (var i = result.length; i < len; i++) {
                result = char + result;
            }
        }
        return result;
    }

    function formatDateStr(d) {
        var result = "";
        var year = initDataByLen(d.getFullYear() + "", "0", 4);
        var month = initDataByLen(d.getMonth() + 1 + "", "0", 2);
        var date = initDataByLen(d.getDate() + "", "0", 2);
        var hour = initDataByLen(d.getHours() + "", "0", 2);
        var miniute = initDataByLen(d.getMinutes() + "", "0", 2);
        var second = initDataByLen(d.getSeconds() + "", "0", 2);
        result = year + "-" + month + "-" + date + " " + hour + ":" + miniute + ":" + second;
        return result;
    }

    function formatHMSStr(d) {
        var result = "";
        var year = initDataByLen(d.getFullYear() + "", "0", 4);
        var month = initDataByLen(d.getMonth() + 1 + "", "0", 2);
        var date = initDataByLen(d.getDate() + "", "0", 2);
        var hour = initDataByLen(d.getHours() + "", "0", 2);
        var miniute = initDataByLen(d.getMinutes() + "", "0", 2);
        var second = initDataByLen(d.getSeconds() + "", "0", 2);
        result = hour + ":" + miniute + ":" + second;
        return result;
    }

    function buildIdcServePercent() {
        var result =
        {
            title: {
                text: "政企、自有业务好评统计",
                textStyle: {
                    color: '#6E6A6F',
                    fontSize: 14
                },
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'axis',
                confine: true
            },
            legend: {
                x: 'center',
                y: 'bottom',
                data: ['政企业务', '自有业务']
            },
            radar: [
                {
                    indicator: [
                        {text: '预受理'},
                        {text: '业务开通', max: 85},
                        {text: '业务变更', max: 85},
                        {text: '变更开通', max: 85},
                        {text: '业务停机', max: 85},
                        {text: '业务复通', max: 85},
                        {text: '业务下线', max: 85}
                    ],
                    name: {
                        formatter: '{value}',
                        textStyle: {
                            color: '#057300',
                            fontSize: 11
                        }
                    },
                    nameGap: 5,
                    center: ['50%', '32%'],
                    radius: 40
                },
                {
                    indicator: [
                        {text: '预受理'},
                        {text: '业务开通', max: 95},
                        {text: '业务变更', max: 95},
                        {text: '变更开通', max: 95},
                        {text: '业务停机', max: 95},
                        {text: '业务复通', max: 95},
                        {text: '业务下线', max: 95}
                    ],
                    name: {
                        formatter: '{value}',
                        textStyle: {
                            color: '#3276B1',
                            fontSize: 11
                        }
                    },
                    nameGap: 5,
                    radius: 40,
                    center: ['50%', '70%'],
                }
            ],
            series: [
                {
                    type: 'radar',
                    tooltip: {
                        trigger: 'item'
                    },
                    itemStyle: {
                        normal: {
                            color: "#057300",
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)',
                            areaStyle: {type: 'default'}
                        },
                        emphasis: {
                            borderWidth: 2,
                            borderColor: '#CCC'
                        }
                    },
                    data: [
                        {
                            value: [60, 73, 85, 40, 73, 85, 40],
                            name: '政企业务'
                        }
                    ]
                },
                {
                    type: 'radar',
                    radarIndex: 1,
                    tooltip: {
                        trigger: 'item'
                    },
                    itemStyle: {
                        normal: {
                            color: "#3276B1",
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)',
                            areaStyle: {type: 'default'}
                        },
                        emphasis: {
                            borderWidth: 2,
                            borderColor: '#CCC'
                        }
                    },
                    data: [
                        {
                            value: [85, 90, 90, 55, 88, 22, 95],
                            name: '自有业务'
                        }
                    ]
                }
            ]
        };
        return result;
    }

    function buildIdcServePercentTemp() {
        var result = {
            title: {
                text: "预勘察、开通数量占比",
                textStyle: {
                    color: '#6E6A6F',
                    fontSize: 14
                },
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}: {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                x: 'left',
                y: 'bottom',
                data: ['预勘', '预勘【好评】', '预勘【其他】', '开通', '开通【好评】', '开通【其他】']
            },
            series: [
                {
                    name: '条数',
                    type: 'pie',
                    selectedMode: 'single',
                    radius: ["5%", '30%'],

                    label: {
                        normal: {
                            position: 'inner'
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: function (it) {
                                var color = "#057300";
                                var seriesIndex = it.seriesIndex;
                                var dataIndex = it.dataIndex;
                                if (dataIndex == 0) {
                                    color = "#3276B1";//#c23531
                                } else {
                                    color = "#D5D500";
                                }
                                return color;
                            },
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        emphasis: {
                            borderWidth: 2,
                            borderColor: '#CCC'

                        }
                    },
                    data: [
                        {value: 335, name: '预勘'/*, selected:true*/},
                        {value: 679, name: '开通'}
                    ]
                },
                {
                    name: '条数',
                    type: 'pie',
                    radius: ['40%', '55%'],
                    itemStyle: {
                        normal: {
                            color: function (it) {
                                var color = "#c23531";
                                var seriesIndex = it.seriesIndex;
                                var dataIndex = it.dataIndex;
                                if (dataIndex == 0 || dataIndex == 2) {
                                    color = "#057300";
                                }
                                return color;
                            },
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        emphasis: {
                            borderWidth: 2,
                            borderColor: '#CCC'

                        }
                    },
                    data: [
                        {value: 251, name: '预勘【好评】'},
                        {value: 84, name: '预勘【其他】'},
                        {value: 544, name: '开通【好评】'},
                        {value: 135, name: '开通【其他】'}
                    ]
                }
            ]
        };
        return result;
    }


    //成本占用率
    function buildCostPercentOpt() {

        var datas = [
            {value: 180, name: '能源'},
            {value: 90, name: '人工'},
            {value: 220, name: '网络'}
        ];
        var legends = ['能源', '人工', '网络'];


        var option = {
            title: {
                text: "成本占比",
                textStyle: {
                    color: '#6E6A6F',
                    fontSize: 14
                },
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'item',
                confine: true,
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                data: legends,
                orient: "horizontal",
                y: 'bottom'

            },
            series: [
                {
                    name: '成本',
                    type: 'pie',
                    selectedMode: 'single',
                    radius: ['10%', '55%'],
                    center: ['50%', '50%'],
                    //roseType : 'area',
                    data: datas,
                    label: {
                        normal: {
                            position: 'inner',
                            formatter: "{c}"
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: function (it) {
                                var color = "#057300";
                                var seriesIndex = it.seriesIndex;
                                var dataIndex = it.dataIndex;
                                if (dataIndex == 0) {
                                    color = "#c23531";
                                } else if (dataIndex == 1) {
                                    color = "#057300";
                                } else if (dataIndex == 2) {
                                    color = "#3276B1";
                                }
                                return color;
                            },
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        emphasis: {
                            borderWidth: 2,
                            borderColor: '#CCC'

                        }
                    }
                }
            ]
        };

        return option;

    }


    //资源占用率
    function buildCusPercentOpt() {

        var datas = [
            {value: 421, name: '有域名客户'},
            {value: 1532, name: '其他客户数'}
        ];
        var legends = ['有域名客户', '其他客户数'];


        var option = {
            title: {
                text: "客户占比",
                textStyle: {
                    color: '#6E6A6F',
                    fontSize: 14
                },
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'item',
                confine: true,
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                data: legends,
                orient: "horizontal",
                y: 'bottom'

            },
            series: [
                {
                    name: '数量',
                    type: 'pie',
                    selectedMode: 'single',
                    radius: ['10%', '55%'],
                    center: ['50%', '50%'],
                    //roseType : 'area',
                    data: datas,
                    label: {
                        normal: {
                            position: 'inner',
                            formatter: "{c}"
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: function (it) {
                                var color = "#057300";
                                var seriesIndex = it.seriesIndex;
                                var dataIndex = it.dataIndex;
                                if (dataIndex == 0) {
                                    color = "#c23531";
                                }
                                return color;
                            },
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        emphasis: {
                            borderWidth: 2,
                            borderColor: '#CCC'

                        }
                    }
                }
            ]
        };

        return option;

    }


    //构建增长统计数据
    function buildCusGrowChart() {
        var limit = {in: [60, 150], out: [30, 250]};
        var models = [
            {
                insum: randoms(limit["in"][0], limit["in"][1], 0),
                outsum: randoms(limit["out"][0], limit["out"][1], 0),
                month: "2017-01"
            },
            {
                insum: randoms(limit["in"][0], limit["in"][1], 0),
                outsum: randoms(limit["out"][0], limit["out"][1], 0),
                month: "2017-02"
            },
            {
                insum: randoms(limit["in"][0], limit["in"][1], 0),
                outsum: randoms(limit["out"][0], limit["out"][1], 0),
                month: "2017-03"
            },
            {
                insum: randoms(limit["in"][0], limit["in"][1], 0),
                outsum: randoms(limit["out"][0], limit["out"][1], 0),
                month: "2017-04"
            },
            {
                insum: randoms(limit["in"][0], limit["in"][1], 0),
                outsum: randoms(limit["out"][0], limit["out"][1], 0),
                month: "2017-05"
            }

        ];

        var initCusNum = 2145;
        for (var i = 0; i < models.length; i++) {
            var item = models[i];
            if (i != 0) {
                initCusNum += (item["insum"] - item["outsum"]);
            }
            item["cussum"] = initCusNum;
        }


        var indatas = getValsFromItemsBykey(models, "insum");
        var outdatas = getValsFromItemsBykey(models, "outsum");
        var cusdatas = getValsFromItemsBykey(models, "cussum");
        var months = getValsFromItemsBykey(models, 'month');


        var option = {
            title: {
                text: "客户增长情况",
                textStyle: {
                    color: '#6E6A6F',
                    fontSize: 14
                },
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'axis',
                confine: true
            },
            legend: {
                data: ["当前客户", "入网客户", "退网客户"],
                orient: "horizontal",
                y: 'bottom'
            },
            toolbox: {
                show: false,
                orient: "vertical",
                x: 'right',
                y: 'center',
                feature: {
                    magicType: {show: true, type: ['line', 'bar']},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            grid: {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow',
                        label: {
                            show: true,
                            formatter: function (params) {
                                return params.value.replace('\n', '');
                            }
                        }
                    }
                }
                //,x:100
            },
            itemStyle: {
                normal: {
                    color: '#c23531',
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                emphasis: {
                    borderWidth: 2,
                    borderColor: '#c23531'

                }
            },
            xAxis: [
                {
                    type: 'category',
                    data: months,
                    axisLabel: {
                        rotate: 15,
                        interval: 0
                    },
                    text: '123'
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '当前客户（人）'
                },
                {
                    type: 'value',
                    name: '入、退网客户（人）'
                }
            ],
            series: [
                {
                    name: "当前客户",
                    type: 'bar',
                    barWidth: top.Echart_barWidth,
                    smooth: true,
                    label: {
                        normal: {
                            show: true,
                            position: 'top',
                            formatter: "{c}"//"{c}("+unitName+")"
                        }
                    }, itemStyle: {
                    normal: {
                        color: "#3276B1"
                    }
                },
                    data: cusdatas
                }, {
                    name: "入网客户",
                    type: 'line',
                    smooth: true,
                    yAxisIndex: 1,
                    label: {
                        normal: {
                            show: true,
                            position: 'top',
                            formatter: "{c}"//"{c}("+unitName+")"
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: "#C23531"
                        }
                    },
                    data: indatas
                }, {
                    name: "退网客户",
                    type: 'line',
                    smooth: true,
                    yAxisIndex: 1,
                    label: {
                        normal: {
                            show: true,
                            position: 'top',
                            formatter: "{c}"//"{c}("+unitName+")"
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: "#057300"
                        }
                    },
                    data: outdatas
                }
            ]
        };
        return option;
    }


    //构建机房统计数据
    function buildSiteTopChart(size, key, title, legendTitle, unitName) {
        var models = buildSiteDatas(size).sort(function (a, b) {
            return a[key] < b[key]
        });
        var datas = getValsFromItemsBykey(models, key);
        var sitenames = getValsFromItemsBykey(models, 'sitename');

        for (var i = 0; i < sitenames.length; i++) {
            if ((i + 1) % 2 == 0) {
                //sitenames[i]='\n'+sitenames[i];
            }
        }

        var option = {
            title: {
                text: title,
                textStyle: {
                    color: '#6E6A6F',
                    fontSize: 14
                },
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'axis',
                confine: true,
                formatter: "机房 : {b} <br/>{a} : {c}(" + unitName + ")"
            },
            legend: {
                data: [legendTitle],
                orient: "horizontal",
                y: 'bottom'
            },
            toolbox: {
                show: true,
                orient: "vertical",
                x: 'right',
                y: 'center',
                feature: {
                    magicType: {show: true, type: ['line', 'bar']},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            grid: {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow',
                        label: {
                            show: true,
                            formatter: function (params) {
                                return params.value.replace('\n', '');
                            }
                        }
                    }
                },
                x: 100
            },
            itemStyle: {
                normal: {
                    color: '#c23531',
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                emphasis: {
                    borderWidth: 2,
                    borderColor: '#c23531'

                }
            },
            xAxis: [
                {
                    type: 'category',
                    data: sitenames,
                    axisLabel: {
                        rotate: 15,
                        interval: 0
                    },
                    text: '123'
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: legendTitle + '（' + unitName + '）'
                }
            ],
            series: [
                {
                    name: legendTitle,
                    type: 'bar',
                    barWidth: top.Echart_barWidth,
                    smooth: true,
                    label: {
                        normal: {
                            show: true,
                            position: 'top',
                            formatter: "{c}"//"{c}("+unitName+")"
                        }
                    },
                    data: datas
                }
            ]
        };
        return option;
    }
    function buildResPercentOptData(title, keyword, dataobj, datakey) {
        var value = 30;
        if (idc1 != "") {
            value = dataobj[datakey];
        }
        return buildResPercentOpt(title, keyword, parseFloat(value.toFixed(2)), parseFloat((100 - value).toFixed(2)))

    }
    //资源占用率
    function buildResPercentOpt(title, keyword, used, notUsed) {

        var datas = [
            {value: used, name: '占用' + keyword},
            {value: notUsed, name: '闲置' + keyword}
        ];
        var legends = ['占用' + keyword, '闲置' + keyword];


        var option = {
            title: {
                text: title,
                textStyle: {
                    color: '#6E6A6F',
                    fontSize: 14
                },
                x: 'center',
                y: 'top'
            },
            tooltip: {
                trigger: 'item',
                confine: true,
                formatter: "{b} : {c} ({d}%)"
            },
            legend: {
                data: legends,
                orient: "horizontal",
                y: 'bottom'

            },
            series: [
                {
                    name: '数量',
                    type: 'pie',
                    selectedMode: 'single',
                    radius: ['10%', '55%'],
                    center: ['50%', '50%'],
                    //roseType : 'area',
                    data: datas,
                    label: {
                        normal: {
                            position: 'inner',
                            formatter: "{c}"
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: function (it) {
                                var color = "#057300";
                                var seriesIndex = it.seriesIndex;
                                var dataIndex = it.dataIndex;
                                if (dataIndex == 0) {
                                    color = "#c23531";
                                }
                                return color;
                            },
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        emphasis: {
                            borderWidth: 2,
                            borderColor: '#CCC'

                        }
                    }
                }
            ]
        };

        return option;


    }

</script>


<script>
    var gridids = ["waitgrid", "waitgrid2", "waitgrid3"];


    $(function () {
        for (var i = 0; i < gridids.length; i++) {
            var id = gridids[i];
            var grid = document.getElementById(id);
            if (grid) {
                initGrid(id, i);
                //pages(id);
            }

        }

        // Dock initialize
        $('#dock').Fisheye(
                {
                    maxWidth: 40,
                    items: 'a',
                    itemsText: 'span',
                    container: '.dock-container',
                    itemWidth: 60,
                    proximity: 180,
                    alignment: 'left',
                    valign: 'bottom',
                    halign: 'center'
                }
        );

    });

    function initGrid(id, idx) {
        $("#" + id).datagrid({
            singleSelect: true,
            emptyMsg: '<span>无记录</span>',
            loadMsg: '正在加载中，请稍等... ',
            nowrap: false,
            striped: true,
            //rownumbers:true,
            //pagination:true,
            fitColumns: true,
            rowStyler: function (index, row) {
                return "";
            },
            showRefresh: true,
            //toolbar:"#toolbar",
            pageSize: 5,
            pageList: [5, 10, 15, 20, 30],
            fit: true,
            displayMsg: "总条数{total}，显示{from}到{to}条",
            loadFilter: function (data) {
                return {total: data.total, rows: data.rows}
            },
            onLoadSuccess: myLoadsuccess,
            columns: initColumns(idx),
            data: initData(idx)

        });
    }

    function initColumns(idx) {
        var result = [];
        var columns = [];
        if (idx == 0) {
            //{field:'ck',checkbox:true},
            columns.push({title: '工单名称', field: 'ordername', width: 80, align: 'center'});
            columns.push({title: '工单类型', field: 'ordertype', width: 80, align: 'center'});
            columns.push({title: '创建时间', field: 'createtime', width: 150, align: 'center'});

        } else if (idx == 1) {
            //{field:'ck',checkbox:true},
            columns.push({title: '告警信息', field: 'ordername', width: 100, align: 'center'});
            columns.push({title: '告警类型', field: 'ordertype', width: 100, align: 'center'});
            columns.push({title: '告警时间', field: 'createtime', width: 150, align: 'center'});

        } else {
            result = initColumns(0);
        }
        if (columns.length != 0)result.push(columns);
        return result;
    }

    function initData(idx) {
        var result = {};
        if (idx == 0) {
            result = {
                gridid: gridids[idx], "total": 10, "rows": [
                    {"ordername": "FI-SW-01", "ordertype": "Koi", "createtime": '2017-04-11 11:22:42', "handler": "P"},
                    {
                        "ordername": "K9-DL-01",
                        "ordertype": "Dalmation",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "RP-SN-01",
                        "ordertype": "Rattlesnake",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "RP-SN-01",
                        "ordertype": "Rattlesnake",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "RP-LI-02",
                        "ordertype": "Iguana",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "FL-DSH-01",
                        "ordertype": "Manx",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "FL-DSH-01",
                        "ordertype": "Manx",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "FL-DLH-02",
                        "ordertype": "Persian",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P",
                    },
                    {
                        "ordername": "FL-DLH-02",
                        "ordertype": "Persian",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    }
                ]
            };
        } else if (idx == 1) {
            result = {
                gridid: gridids[idx], "total": 10, "rows": [
                    {"ordername": "FI-SW-01", "ordertype": "Koi", "createtime": '2017-04-11 11:22:42', "handler": "P"},
                    {
                        "ordername": "K9-DL-01",
                        "ordertype": "Dalmation",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "RP-SN-01",
                        "ordertype": "Rattlesnake",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "RP-SN-01",
                        "ordertype": "Rattlesnake",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "RP-LI-02",
                        "ordertype": "Iguana",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "FL-DSH-01",
                        "ordertype": "Manx",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "FL-DSH-01",
                        "ordertype": "Manx",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    },
                    {
                        "ordername": "FL-DLH-02",
                        "ordertype": "Persian",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P",
                    },
                    {
                        "ordername": "FL-DLH-02",
                        "ordertype": "Persian",
                        "createtime": '2017-04-11 11:22:42',
                        "handler": "P"
                    }
                ]
            };
        } else {
            result = initData(0);
        }
        return result;
    }

    function pages(id) {
        var pager = $('#' + id).datagrid('getPager');
        pager.pagination({
            //showPageList:false,
            layout: ['list', 'sep', 'first', 'prev', 'manual', 'next', 'last', 'refresh'],
            beforePageText: "第",
            afterPageText: "共{pages}",
            displayMsg: "总条数{total}，显示{from}到{to}条"
        });
    }

    function myLoadsuccess(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#' + data['gridid']).datagrid('fixRowHeight')
    }


</script>
</body>
</html>