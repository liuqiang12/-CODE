<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>

    <script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>
    <script src="<%=request.getContextPath() %>/framework/themes/js/echart/custom.theme.js"></script>
    <script src="<%=request.getContextPath() %>/framework/vuejs/vue.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>

    <title>机架成本分析</title>
    <style>
        table.kv-table td.kv-label {
            height: 24px;
            font-size: 12px;
            width: 150px;
            padding: 0px 10px;
            background: rgb(245, 245, 245);
            border-width: 1px 1px 1px;
            border-style: none solid solid;
            border-color: rgb(202, 202, 202) rgb(202, 202, 202) rgb(202, 202, 202);
            border-image: initial;
            border-top: none;
        }

        table.kv-table td.kv-content {
            /*width: 150px;*/
            height: 24px;
            padding: 0px 0px;
        }
    </style>
</head>
<body class="easyui-layout" fit="true">
<div data-options="region:'west'" style="width: 250px;">
    <ul class="easyui-datalist" title="客户列表" fit="true" data-options="striped:false
        , url: '<%=request.getContextPath() %>/busiPort/getcustomer'
        ,onClickRow:loadCustomer
        ">
        <%--<li value="AL">Alabama</li>--%>
        <%--<li value="AK">Alaska</li>--%>
        <%--<li value="AZ">Arizona</li>--%>
        <%--<li value="AR">Arkansas</li>--%>
        <%--<li value="CA">California</li>--%>
        <%--<li value="CO">Colorado</li>--%>
    </ul>
    <%--<ul id="rtree" class="ztree" style="width:230px;;"></ul>--%>
</div>
<div data-options="region:'center'" title="成本分析">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'north'" style="height: 30px;overflow: hidden">
            <div style=";text-align:left;padding-left: 10px">
                选择月份： <input id="attYearMonth" editable="false" name="attYearMonth" class="easyui-datebox"/>
            </div>
        </div>
        <div data-options="region:'center'">
            <div class="easyui-layout" fit="true">
                <div data-options="region:'north'" style="height: 252px;overflow: hidden" id="dtable">
                    <table class="kv-table" id="dtable">
                        <tbody>
                        <tr>
                            <td class="kv-label" colspan="6"><span
                                    style="font-weight: bold;font-size: 20px">静态成本：</span></td>
                        </tr>
                        <tr>
                            <td class="kv-label">房屋</td>
                            <td class="kv-content">
                                <label class="easyui-textbox11" data-options="width:200"
                                       readonly>{{costBuildingMonth}}</label>
                            </td>
                            <td class="kv-label">基础设备</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200"
                                       readonly>{{costBaseDevMonth}}</label>
                            </td>
                            <td class="kv-label">IT设备</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200"
                                       readonly>{{costItMonth}}</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label" colspan="6"><span
                                    style="font-weight: bold;font-size: 20px">动态成本：</span></td>
                        </tr>
                        <tr>
                            <td class="kv-label">电</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="costEnergy"/>
                                <%--<a id="btn" href="#" v-on:click="saveCostDynForRack" class="easyui-linkbutton"--%>
                                <%--data-options="iconCls:'icon-search'">保存</a>--%>
                                <%--<span>{{rack_cost_dyn.messages}}</span>--%>
                            </td>
                            <td class="kv-label">水</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200"
                                       readonly>{{costWaterRate}}</label>
                            </td>
                            <td class="kv-label">维修</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200"
                                       readonly>{{costRepair}}</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">维保</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200"
                                       readonly>{{costMaintenance}}</label>
                            </td>
                            <td class="kv-label">人工</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200"
                                       readonly>{{costWork}}</label>
                            </td>
                            <td class="kv-label">营销成本</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200"
                                       readonly>{{costMarketting}}</label>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label" colspan="6"><span style="font-weight: bold;font-size: 20px">收入：</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">机架收入</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200"
                                       v-model.number="costRockIncome"/>
                            </td>
                            <td class="kv-label">带宽收入</td>
                            <td class="kv-content" colspan="3">
                                <input class="easyui-textbox1" data-options="width:200"
                                       v-model.number="bandwidthIncome"/>
                                <a href="#" v-on:click="saveCostDynForRack" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search'">保存</a>
                            </td>
                            <%--<td class="kv-label">其他收入</td>--%>
                            <%--<td class="kv-content">--%>
                            <%--<input class="easyui-textbox1" data-options="width:200" --%>
                            <%--v-model.number="cost_other_income"/>--%>
                            <%--</td>--%>
                        </tr>
                        <tr>
                            <td class="kv-label" colspan="6"><span style="font-weight: bold;font-size: 20px">利润：</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">经营利润</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200"
                                        >{{costOperatingProfit}}</label>
                            </td>
                            <td class="kv-label">收入利润</td>
                            <td class="kv-content" colspan="5">
                                <%--<label class="easyui-textbox1" data-options="width:200"--%>
                                <%-->{{srlirun}}</label>--%>
                            </td>
                            <%--<td class="kv-label">利润环比增长率</td>--%>
                            <%--<td class="kv-content">--%>
                            <%--<label class="easyui-textbox1" data-options="width:200" >{{huanbi}}</label>--%>
                            <%--</td>--%>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div data-options="region:'center'">
                    <div style="width: 100%;height: 100%;display: flex">
                        <div id="chart" style="width:33%;"></div>
                        <div id="chart1" style="width:33%;"></div>
                        <div id="chart2" style="width: 33%">1</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $('#attYearMonth').datebox({
            //显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            onChange: function () {
                searchModels()
            },
            onShowPanel: function () {
                //触发click事件弹出月份层
                span.trigger('click');
                if (!tds)
                //延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                    setTimeout(function () {
                        tds = p.find('div.calendar-menu-month-inner td');
                        tds.click(function (e) {
                            //禁止冒泡执行easyui给月份绑定的事件
                            e.stopPropagation();
                            //得到年份
                            var year = /\d{4}/.exec(span.html())[0],
                            //月份
                            //之前是这样的month = parseInt($(this).attr('abbr'), 10) + 1;
                                    month = parseInt($(this).attr('abbr'), 10);

                            //隐藏日期对象
                            $('#attYearMonth').datebox('hidePanel')
                                //设置日期的值
                                    .datebox('setValue', year + '-' + month);
                        });
                    }, 0);
            },
            //配置parser，返回选择的日期
            parser: function (s) {
                if (!s) return new Date();
                var arr = s.split('-');
                return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
            },
            //配置formatter，只返回年月 之前是这样的d.getFullYear() + '-' +(d.getMonth());
            formatter: function (d) {
                var currentMonth = (d.getMonth() + 1);
                var currentMonthStr = currentMonth < 10 ? ('0' + currentMonth) : (currentMonth + '');
                return d.getFullYear() + '-' + currentMonthStr;
            }
        });

        //日期选择对象
        var p = $('#attYearMonth').datebox('panel'),
        //日期选择对象中月份
                tds = false,
        //显示月份层的触发控件
                span = p.find('span.calendar-text');
        var curr_time = new Date();

        //设置前当月
        $("#attYearMonth").datebox("setValue", myformatter(curr_time));

    });

    $(function () {
        top.hideLeft();
        if (typeof vue == 'undefined') {
            initVue()
        }
    })
    function initVue() {
        vue = new Vue({
            el: '#dtable',
            data: {
                costBuildingMonth: 0,
                costBaseDevMonth: 0,
                costItMonth: 0,
                costEnergy: 0,
                costWaterRate: 0,
                costRepair: 0,
                costMaintenance: 0,
                costWork: 0,
                costMarketting: 0,
                costRockIncome: 0,
                bandwidthIncome: 0,
                rack_cost_dyn: {
                    messages: ''
                }
            },
            updated: function () {
                if (!this.myChart) {
                    this.setEchart();
                }
                this.chartChange();
            },
            mounted: function () {
                this.setEchart();
            },
            computed: {
                costOperatingProfit: function () {
                    return (this.costRockIncome + this.bandwidthIncome -
                    this.costBuildingMonth - this.
                            costBaseDevMonth - this.
                            costItMonth - this.
                            costEnergy - this.
                            costWaterRate - this.costRepair - this.
                            costMaintenance - this.
                            costWork - this.
                            costMarketting).toFixed(2)
                }
                ,
                srlirun: function () {
                    return this.jijia;
                }
                ,
                huanbi: function () {
                    return this.kuandai;
                },
                opt: function () {
                    var options = {
                        title: {
                            text: '成本与收入',
                            //subtext: '虚构数据',
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                            }
                        },
                        xAxis: [
                            {
                                type: 'category',
                                data: ['总成本', '总收入']
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '金额',
                                type: 'bar',
                                barWidth: 20,
                                data: [this.costBuildingMonth + this.
                                        costBaseDevMonth + this.
                                        costItMonth + this.
                                        costEnergy + this.
                                        costWaterRate + this.
                                        costRepair + this.
                                        costMaintenance + this.
                                        costWork + this.
                                        costMarketting,
                                    this.costRockIncome + this.bandwidthIncome]
                            }
                        ]
                    }
                    return options;
                },
                opt1: function () {
                    var options = {
                        title: {
                            text: '收入占比',
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            bottom: 10,
                            left: 'center',
                            data: ['机架收入', '带宽收入']
                        },
                        series: [
                            {
                                type: 'pie',
                                radius: '65%',
                                center: ['50%', '50%'],
                                selectedMode: 'single',
                                data: [
                                    {value: this.costRockIncome, name: '机架收入'},
                                    {value: this.bandwidthIncome, name: '带宽收入'},
//                                    {value: this.cost_other_income, name: '其他收入'}
                                ],
                                itemStyle: {
                                    emphasis: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    return options;
                },
                opt2: function () {
                    var options = {
                        title: {
                            text: '成本分析',
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        series: [{
                            name: '动/静成本',
                            type: 'pie',
                            radius: ['20%', '40%'],
                            data: [{
                                value: this.
                                        costEnergy + this.
                                        costWaterRate + this.
                                        costRepair + this.
                                        costMaintenance + this.
                                        costWork + this.
                                        costMarketting,
                                name: '动态成本'
                            }, {
                                value: this.costBuildingMonth + this.
                                        costBaseDevMonth + this.costItMonth,
                                name: '静态成本'
                            }]
                        }, {
                            name: '详细成本',
                            type: 'pie',
                            radius: ['50%', '70%'],
                            data: [{
                                value: this.costBuildingMonth,
                                name: '房屋'
                            }, {
                                value: this.costBaseDevMonth,
                                name: '基础设备'
                            }, {
                                value: this.costItMonth,
                                name: 'IT设备'
                            }, {
                                value: this.costEnergy,
                                name: '电'
                            }, {
                                value: this.costWaterRate,
                                name: '水'
                            }, {
                                value: this.costRepair,
                                name: '维修'
                            }, {
                                value: this.costMaintenance,
                                name: '维保'
                            }, {
                                value: this.costWork,
                                name: '人工'
                            }, {
                                value: this.costMarketting,
                                name: '营销成本'
                            }]
                        }]
                    }
                    return options;
                }
            }
            , methods: {
                saveCostDynForRack: function () {
                    var time = $("#attYearMonth").datebox("getValue");
                    var ps = {
                        time: time,
//                        costEnergy: this.costEnergy,
                        bandwidthIncome: this.bandwidthIncome,
                        costRockIncome: this.costRockIncome,
                        customId: this.customId
                    }
                    $.post(contextPath + "/cost/saveCostCustomer", ps, function (data) {
                        if (data.state) {
                            self.costRoomId = data.msg;
                            layer.msg("保存成功")
                        } else {
                            layer.msg(data.msg)
                        }
                    })
                },
                showRoom: function (data) {
//                    top.layer.open(contextPath+"/")
                },
                setEchart: function () {
                    var self = this
                    this.myChart = function () {
                        var mychart = echarts.init(document.getElementById('chart'), 'custom');
                        mychart.setOption(self.opt);
                        return mychart;
                    }();
                    this.myChart1 = function () {
                        var mychart = echarts.init(document.getElementById('chart1'), 'custom');
                        mychart.setOption(self.opt1);
                        return mychart;
                    }();
                    this.myChart2 = function () {
                        var mychart = echarts.init(document.getElementById('chart2'), 'custom');
                        mychart.setOption(self.opt2);
                        return mychart;
                    }();
                },
                chartChange: function () {
                    this.myChart.setOption(this.opt);
                    this.myChart1.setOption(this.opt1);
                    this.myChart2.setOption(this.opt2);
                }
            }
        })
    }
    function getData() {
        var customerid = vue.customId;
        if (customerid && customerid != 0) {
            var time = $("#attYearMonth").datebox("getValue");
            $.post(contextPath + "/cost/getByCustomer/" + customerid, {
                        time: time
                    }, function (result) {
                        vue.customId = customerid;
                        for (var i in result) {
                            if (result[i] && result[i] != null) {
                                vue[i] = result[i]
                            }
                            else {
                                vue[i] = 0
                            }

                        }
                    }
            )
            ;
        }
        else {
            layer.msg('没有选择客户')
        }
    }

    function searchModels(CurrNode) {
        if (typeof vue == 'undefined') {
            initVue()
        }
        getData();
//        var data = {
//            fangwu: Math.ceil(Math.random() * 1),
//            jichu: Math.ceil(Math.random() * 100),
//            it: Math.ceil(Math.random() * 100),
//            dian: Math.ceil(Math.random() * 100),
//            shui: Math.ceil(Math.random() * 100),
//            weixiu: Math.ceil(Math.random() * 100),
//            weibao: Math.ceil(Math.random() * 100),
//            rengong: Math.ceil(Math.random() * 100),
//            yinxiao: Math.ceil(Math.random() * 100),
//            jijia: Math.ceil(Math.random() * 100),
//            kuandai: Math.ceil(Math.random() * 100)
//        }
//        for (var i in data) {
//            vue[i] = data[i]
//        }
//        return;
//        var rackid = 0;
//        if (typeof CurrNode != 'undefined' && CurrNode != null) {
//            rackid = CurrNode.id.split("idcrack_")[1];
//        }
//        $.getJSON(contextPath + "/cost/getByRack/" + rackid, function (result) {
//            for (var i in result) {
//                vue[i] = result[i]
//            }
//        });
    }
    function loadCustomer(index, row) {
        var curr_time = new Date();
        $("#attYearMonth").datebox("setValue", myformatter(curr_time));
        console.log(row);
        vue.customId = row.ID
        searchModels()
    }
    function myformatter(date) {
        //获取年份
        var y = date.getFullYear();
        //获取月份
        var m = date.getMonth() + 1;
        return y + '-' + m;
    }
</script>
</html>