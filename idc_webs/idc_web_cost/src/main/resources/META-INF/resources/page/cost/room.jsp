<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <%--<link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css"/>--%>
    <%--<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/import_skin.css" rel="stylesheet"--%>
    <%--type="text/css" id="skin" themeColor="${skin}"/>--%>
    <%--<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_page.css" rel="stylesheet"--%>
    <%--type="text/css"/>--%>
    <%--<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer.css" rel="stylesheet"--%>
    <%--type="text/css"/>--%>
    <%--<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/swipers.css" rel="stylesheet"--%>
    <%--type="text/css"/>--%>

    <%--<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/normalize.css"--%>
    <%--rel="stylesheet" type="text/css"/>--%>
    <%--<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/main.css"--%>
    <%--rel="stylesheet" type="text/css"/>--%>

    <%--<link rel="stylesheet"--%>
    <%--href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/bootstrap-switch.css"/>--%>

    <%--<!----%>
    <%--<script src="<%=request.getContextPath() %>/framework/themes/js/jquery-1.4.js"></script>--%>
    <%--<script src="<%=request.getContextPath() %>/framework/themes/plugins/jquery/jquery-1.10.2.min.js"></script>--%>

    <%---->--%>
    <%--<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/jquery.jqDock.min.js"></script>--%>
    <%--<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/fisheye-iutil.min.js"></script>--%>

    <script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>
    <script src="<%=request.getContextPath() %>/framework/themes/js/echart/custom.theme.js"></script>
    <script src="<%=request.getContextPath() %>/framework/vuejs/vue.min.js"></script>

    <%--<script src="<%=request.getContextPath() %>/framework/themes/js/swipers.js"></script>--%>

    <%--<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/jquery.autocompleter.js"></script>--%>

    <%--<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/main.js"></script>--%>

    <%--<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/bootstrap-switch.js"></script>--%>
    <%--<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/highlight.js"></script>--%>
    <%--<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/main.js"></script>--%>
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
    <ul id="rtree" class="ztree" style="width:230px;;"></ul>
</div>
<div data-options="region:'center'" title="上月成本分析">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'north'" style="height: 30px;margin-left: 10px;overflow: hidden">
            <span>选择月份： <input id="attYearMonth" editable="false" name="attYearMonth" class="easyui-datebox"/></span>
            <a href="javascript:void(0)" class="easui-linkbutton">导入静态成本</a>
            <%--<div style=";text-align:left;padding-left: 10px">--%>
                <%--选择月份： <input id="attYearMonth" editable="false" name="attYearMonth" class="easyui-datebox"/>--%>
            <%--</div>--%>
            <%--<div style=";text-align:left;padding-left: 10px">--%>
                <%--<a href="javascript:void(0)" class="easui-linkbutton">导入静态成本</a>--%>
            <%--</div>--%>

        </div>
        <div data-options="region:'center'">
            <div class="easyui-layout" fit="true">
                <div data-options="region:'north'" style="height: 252px;overflow: hidden">
                    <table class="kv-table" id="dtable">
                        <tbody>
                        <tr>
                            <td class="kv-label" colspan="6"><span
                                    style="font-weight: bold;font-size: 20px">静态成本：</span></td>
                        </tr>
                        <tr>
                            <td class="kv-label">房屋</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="COST_FIXED"/>
                                <%--<label class="easyui-textbox11" data-options="width:200" name="name"--%>
                                       <%--readonly>{{COST_FIXED}}</label>--%>
                            </td>
                            <td class="kv-label">基础设备</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="COST_BASE_DEV"/>
                                <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                                       <%--readonly>{{COST_BASE_DEV}}</label>--%>
                            </td>
                            <td class="kv-label">IT设备</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="COST_IT_DEV"/>
                                <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                                       <%--readonly>{{COST_IT_DEV}}</label>--%>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label" colspan="6"><span
                                    style="font-weight: bold;font-size: 20px">动态成本：</span></td>
                        </tr>
                        <tr>
                            <td class="kv-label">电</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="COST_ENERGY"/>
                                <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                                       <%--readonly>{{COST_ENERGY}}</label>--%>
                            </td>
                            <td class="kv-label">水</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="COST_WATER_RATE"/>
                                <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                                       <%--readonly>{{COST_WATER_ RATE}}</label>--%>
                            </td>
                            <td class="kv-label">维修</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="weixiu"/>
                                <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                                       <%--readonly>{{weixiu}}</label>--%>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">维保</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="weibao"/>
                                <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                                       <%--readonly>{{weibao}}</label>--%>
                            </td>
                            <td class="kv-label">人工</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="rengong"/>
                                <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                                       <%--readonly>{{rengong}}</label>--%>
                            </td>
                            <td class="kv-label">营销成本</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="yinxiao"/>
                                <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                                       <%--readonly>{{yinxiao}}</label>--%>
                            </td>
                        </tr>
                        <%--<tr>--%>
                        <%--<td class="kv-label">设备折旧摊销</td>--%>
                        <%--<td class="kv-content">--%>
                        <%--<label class="easyui-textbox1" data-options="width:200" name="name" readonly>{{cost_dev_depreciation}}</label>--%>
                        <%--</td>--%>
                        <%--<td class="kv-label">设备修理费</td>--%>
                        <%--<td class="kv-content" colspan="3">--%>
                        <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                        <%--readonly>{{cost_dev_repair}}</label>--%>
                        <%--</td>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                        <%--<td class="kv-label">能源费用</td>--%>
                        <%--<td class="kv-content">--%>
                        <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                        <%--readonly>{{cost_energy}}</label>--%>
                        <%--</td>--%>
                        <%--<td class="kv-label">人工成本</td>--%>
                        <%--<td class="kv-content">--%>
                        <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                        <%--readonly>{{cost_work}}</label>--%>
                        <%--</td>--%>
                        <%--<td class="kv-label">网络费用</td>--%>
                        <%--<td class="kv-content">--%>
                        <%--<label class="easyui-textbox1" data-options="width:200" name="name"--%>
                        <%--readonly>{{cost_network}}</label>--%>
                        <%--</td>--%>
                        <%--</tr>--%>

                        <tr>
                            <td class="kv-label" colspan="6"><span style="font-weight: bold;font-size: 20px">收入：</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="kv-label">机架收入</td>
                            <td class="kv-content">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="jijia"/>
                                <%--<a href="javascript:void(0)" id="openRack" class="easyui-linkbutton"--%>
                                <%--data-options="iconCls:'icon-edit'"></a>--%>
                            </td>
                            <td class="kv-label">带宽收入</td>
                            <td class="kv-content" colspan="3">
                                <input class="easyui-textbox1" data-options="width:200" v-model.number="kuandai"/>
                            </td>
                            <%--<td class="kv-label">其他收入</td>--%>
                            <%--<td class="kv-content">--%>
                            <%--<input class="easyui-textbox1" data-options="width:200" name="name"--%>
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
                                       name="name">{{jylirun}}</label>
                            </td>
                            <td class="kv-label">收入利润</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200"
                                       name="name">{{srlirun}}</label>
                            </td>
                            <td class="kv-label">利润环比增长率</td>
                            <td class="kv-content">
                                <label class="easyui-textbox1" data-options="width:200" name="name">{{huanbi}}</label>
                            </td>
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
        top.hideLeft();
        $("#rtree").rtree({
            r_type: 2,
            onClick: function (iteam, treeId, treeNode) {
                if (treeNode.id.indexOf("idcroom_") > -1) {
                    CurrNode = treeNode;
                    searchModels(CurrNode);
                }
            }
        })

        var vue = new Vue({
            el: '#dtable',
            data: {
                COST_FIXED: 0,
                COST_BASE_DEV: 0,
                COST_IT_DEV: 0,
                COST_ENERGY: 0,
                COST_WATER_RATE: 0,
                weixiu: 0,
                weibao: 0,
                rengong: 0,
                yinxiao: 0,
                jijia: 0,
                kuandai: 0
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
                jylirun: function () {
                    return this.jijia;
                }
                ,
                srlirun: function () {
                    return this.jijia;
                }
                ,
                huanbi: function () {
                    return this.kuandai;
                }
                ,
                origin: function () {
                    return this.data;
                }
                ,
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
                                data: [this.COST_FIXED + this.
                                        COST_BASE_DEV + this.
                                        COST_IT_DEV + this.
                                        COST_ENERGY + this.
                                        COST_WATER_RATE + this.
                                        weixiu + this.
                                        weibao + this.
                                        rengong + this.
                                        yinxiao,
                                    this.jijia + this.kuandai]
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
                                    {value: this.jijia, name: '机架收入'},
                                    {value: this.kuandai, name: '带宽收入'},
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
                                        COST_ENERGY + this.
                                        COST_WATER_RATE + this.
                                        weixiu + this.
                                        weibao + this.
                                        rengong + this.
                                        yinxiao,
                                name: '动态成本'
                            }, {
                                value: this.COST_FIXED + this.
                                        COST_BASE_DEV + this.COST_IT_DEV,
                                name: '静态成本'
                            }]
                        }, {
                            name: '详细成本',
                            type: 'pie',
                            radius: ['50%', '70%'],
                            data: [{
                                value: this.COST_FIXED,
                                name: '房屋'
                            }, {
                                value: this.COST_BASE_DEV,
                                name: '基础设备'
                            }, {
                                value: this.COST_IT_DEV,
                                name: 'IT设备'
                            }, {
                                value: this.COST_ENERGY,
                                name: '电'
                            }, {
                                value: this.COST_WATER_RATE,
                                name: '水'
                            }, {
                                value: this.weixiu,
                                name: '维修'
                            }, {
                                value: this.weibao,
                                name: '维保'
                            }, {
                                value: this.rengong,
                                name: '人工'
                            }, {
                                value: this.yinxiao,
                                name: '营销成本'
                            }]
                        }]
                    }
                    return options;
                }
            }
            , methods: {
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
        });

        function searchModels(CurrNode) {

//            var roomid = 0;
//            if (typeof CurrNode != 'undefined' && CurrNode != null) {
//                roomid = CurrNode.id.split("idcroom_")[1];
//            }
//            $.getJSON(contextPath + "/cost/getByRoom/" + roomid, function (result) {
//                for (var i in result) {
//                    vue[i] = result[i]
//                }
//            })
            var data = {
                COST_FIXED: Math.ceil(Math.random() * 1),
                COST_BASE_DEV: Math.ceil(Math.random() * 100),
                COST_IT_DEV: Math.ceil(Math.random() * 100),
                COST_ENERGY: Math.ceil(Math.random() * 100),
                COST_WATER_RATE: Math.ceil(Math.random() * 100),
                weixiu: Math.ceil(Math.random() * 100),
                weibao: Math.ceil(Math.random() * 100),
                rengong: Math.ceil(Math.random() * 100),
                yinxiao: Math.ceil(Math.random() * 100),
                jijia: Math.ceil(Math.random() * 100),
                kuandai: Math.ceil(Math.random() * 100)
            }
            for (var i in data) {
                vue[i] = data[i]
            }
        }

        $('#attYearMonth').datebox({
            //显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            onChange: function () {
                if (typeof CurrNode != 'undefined' && CurrNode != null) {
                    searchModels()
                }
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
    })
    function myformatter(date) {
        //获取年份
        var y = date.getFullYear();
        //获取月份
        var m = date.getMonth() + 1;
        return y + '-' + m;
    }
</script>
</html>