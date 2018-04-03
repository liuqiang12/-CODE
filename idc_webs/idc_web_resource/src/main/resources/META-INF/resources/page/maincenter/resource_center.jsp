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
<script src="<%=request.getContextPath() %>/framework/vuejs/vue.min.js"></script>


<!--
<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/themes/plugins/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/framework/themes/plugins/bootstrap/js/bootstrap.min.js"></script>
-->

<style>
    /*
    .datagrid-htable, .datagrid-btable {
        width:100%;
    }
    */
</style>

<script>
    //查看数据中心详细信息
    function showLocationInfo(obj) {
        var locationId = obj.getAttribute("idcid");
        var url = contextPath + "/idcLocation/getIdcLocationInfo.do?id=" + locationId + "&buttonType=view";
        openDialogView('数据中心信息', url, '800px', '530px');
    }
</script>

<!--框架必需end-->
<body id="scrollContent">
<!-- 界面布局情况 -->
<div class="desklayout height100">
    <!-- 分三个div并排  -->
    <div class="deskLayout001 height100">
        <!-- 1DIV start -->
        <div id="center" class="float_left col width100 height100" style="background-color:#E9ECF3;overflow-y: auto;">
            <div class="width100 relative-position box-sizing" style="height:240px;border:5px solid transparent;"
                 v-for="item in items">
                <div class="height100 width100 relative-position box-sizing" style="float:left;">
                    <!--  标题设置 start  -->
                    <div class="titleBox_center">
                        <div class="titleBox_left">
                            <div class="titleBox_right">
                                <!-- 图标标题操作 -->
                                <div class="box1_icon khywtjicon">
                                    <div class="box_title">{{item.name}}</div>
                                    <div class="box1_status">
                                        <a href="javascript:void(0)" :idcid="item.id"
                                           onclick="showLocationInfo(this)">数据中心详细信息&gt;&gt;</a>
                                    </div>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <!--  下方内容 -->
                    <div class="box1_content quick_content" style="overflow:hidden;">
                        <div class="width333 height100 box-sizing"
                             style="float:left;border-right: 5px solid transparent;">
                            <div style="height:22px;font-size: 12px;color: #666;font-weight: bold;">
                                <div class="width100" style="float:left;">当月能耗：{{item.energyNum}}万kWh</div>
                            </div>
                            <div class="title_split"></div>

                            <div style="height:22px;margin-top:6px;color: #6c6c6c;font-weight: 600;">
									<span>
										资源统计(数量)：
									</span>
                            </div>
                            <div class="title_desc2" style="margin-top:5px;">
                                <div class="width30 box-sizing " style="float:left;">
                                    <h5 class="width100" style="float:left;text-align: center;">空间</h5>

                                    <div>
                                        <h6 class="width50" style="text-align:right">机楼：</h6><h6 class="width50">
                                        {{item.buildtotal}}</h6>
                                    </div>
                                    <div>
                                        <h6 class="width50" style="text-align:right">机房：</h6><h6 class="width50">
                                        {{item.roomtotal}}</h6>
                                    </div>
                                    <div>
                                        <h6 class="width50" style="text-align:right">机架：</h6><h6 class="width50">
                                        {{item.racktotal}}</h6>
                                    </div>

                                </div>
                                <div class="width40 box-sizing left_border right_border" style="float:left;">
                                    <h5 class="width100" style="float:left;text-align: center;">网络</h5>

                                    <div>
                                        <h6 class="width60" style="text-align:right">带宽：</h6><h6 class="width40">
                                        {{item.bandwidthtotal}}</h6>
                                    </div>
                                    <div>
                                        <h6 class="width60" style="text-align:right">IP：</h6><h6 class="width40">
                                        {{item.iptotal}}</h6>
                                    </div>
                                    <div>
                                        <h6 class="width60" style="text-align:right">核心端口数：</h6><h6 class="width40">
                                        {{item.coreporttotal}}</h6>
                                    </div>
                                    <div>
                                        <h6 class="width60" style="text-align:right">ODF：</h6><h6 class="width40">
                                        {{item.odftotal}}</h6>
                                    </div>
                                </div>

                                <div class="width30 box-sizing" style="float:left;">
                                    <div class="height50 width100 box-sizing" style="float:left;">
                                        <h5 class="width100" style="float:left;text-align: center;">动力</h5>

                                        <div>
                                            <h6 class="width60" style="text-align:right">PDF：</h6><h6 class="width40">
                                            {{item.pdftotal}}</h6>
                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>
                        <div class="width666 height100 box-sizing left_border chartarea" style="float:left;">
                            <Pie title="机柜占用率" :legends="['空闲','占用']" :value="item.usedracknum" :resourcetotal="item.racktotal"></Pie>
                            <Pie title="出口带宽利用率" :legends="['空闲','占用']" :value="item.usedbandwidth" :resourcetotal="item.bandwidthtotal"></Pie>
                            <Pie title="IP占用率" :legends="['空闲','占用']" :value="item.usedipnum" :resourcetotal="item.iptotal"></Pie>
                            <Pie title="核心端口占用率" :legends="['空闲','占用']" :value="item.usedcoreport" :resourcetotal="item.coreporttotal"></Pie>
                        </div>
                    </div>
                    <!--  标题设置 end  -->
                </div>
            </div>

        </div>
        <!-- 1DIV end -->

    </div>


</div>
<script>
    function randomNum(minNum, maxNum) {
        switch (arguments.length) {
            case 1:
                return parseInt(Math.random() * minNum + 1);
                break;
            case 2:
                return parseInt(Math.random() * (maxNum - minNum + 1) + minNum);
                break;
            default:
                return 0;
                break;
        }
    }
    Vue.component('Pie', {
        // 选项
        template: '<div class="height100 width25 box-margin10" :id="tid" style="float:left"></div>',
        props: ['title', 'legends', 'value','resourcetotal'],
        data: function () {
            return {
                tid: 'id_' + randomNum(1, 100) + "_" + randomNum(1, 100)+ "_" + randomNum(1, 100)
            }
        },
        updated: function () {
            if (!this.myChart) {
                this.setEchart();
            }
            this.chartChange();
        },
        computed: {
            values: function () {
                return [{value: this.value, name: '占用'},
                    {value: this.resourcetotal - this.value, name: '闲置'}]
            },
            opt: function () {
                var options = {
                    title: {
                        text: this.title,
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
                        formatter: "{b} : {d}% ({c}/"+this.resourcetotal+")" //"{b} : {c} ({d}%)"
                    },
                    legend: {
                        data: ['占用','闲置'],
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
                            data: this.values,
                            label: {
                                normal: {
                                    position: 'inner',
                                    formatter: "{d}"
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            itemStyle: {
                                normal: {
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
                return options;
            },
        },
        mounted: function () {
            this.setEchart();
        },
        methods: {
            setEchart: function () {
                var self = this
                this.myChart = function () {
                    var mychart = echarts.init(document.getElementById(self.tid), 'custom');
                    mychart.setOption(self.opt);
                    return mychart;
                }();
            },
            chartChange: function () {
                this.myChart.setOption(this.opt);
            }
        }
    })
    $(function () {
        $.getJSON(contextPath + "/idcLocation/idcView", function (data) {
            var center = new Vue({
                el: '#center',
                data: {
                    items: data
                }
            })

        })
    })
</script>
</body>
</html>