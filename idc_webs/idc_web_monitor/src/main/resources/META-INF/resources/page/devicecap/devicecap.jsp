<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
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

</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',border:true" style="padding-left:2px;overflow:hidden;">
        <div class="easyui-tabs easyui-tabs1" style="width:100%;height:100%;">
            <div title="常规信息" data-options="closable:false,border:true" style="padding:5px">
                <div class="easyui-layout" fit="true">
                    <div data-options="region:'north',border:true,height:50,collapsible:false,border:true"
                         style="overflow: hidden">
                        <table class="kv-table">
                            <tbody>
                            <tr>
                                <td class="kv-label">设备名称</td>
                                <td class="kv-content" id="_deviceName"></td>
                                <td class="kv-label">设备型号</td>
                                <td class="kv-content" id="_model"></td>
                                <%--<td class="kv-label">IP地址</td>--%>
                                <%--<td class="kv-content" id="_IP"></td>--%>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div data-options="region:'center',border:true">
                        <table id="dg" class="easyui-datagrid" style="width:100%" fit="true"
                               title="端口流量 | <a href='javascript:void(0)' onclick='showPortBoard()'>查看端口面板</a>| <a href='javascript:void(0)' onclick='addTask()'>同步资源</a>"
                               data-options="
                rownumbers:true,
                singleSelect:true,
                autoRowHeight:false,
                fitColumns:true,
                striped:true,
                checkOnSelect:false,
                selectOnCheck:false,
                onClickRow:ClickPort,
                remoteSort:false,
                <%--toolbar:'#tb',--%>
                pageSize:10">
                            <thead>
                            <tr>
                                <%--<th field="deviceName" width="210" data-options="formatter:function(value,row,index){--%>
                                <%--return '<a href=\'javascript:void(0)\' onclick=\'showdevice('+row.deviceid+')\'>'+value+'</a>'--%>
                            <%--}">所属设备[IP]--%>
                                <%--</th>--%>
                                <th field="portname" width="210" data-options="sortable:true,formatter:function(value,row,index){
                                return '<a href=\'javascript:void(0)\' onclick=\'showport('+row.portid+')\'>'+value+'</a>'
                            }">端口名称
                                </th>
                                    <th field="adminstatus" width="100" data-options="sortable:true,formatter:function(value,row,index){
                                       if(value==1){
                                         return '<span style=\'color:green\'>UP</span>'
                                       }
                                       return '<span style=\'color:red\'>DOWN</span>'
                                  }">端口状态
                                    </th>
                                    <th field="portactive" width="100" data-options="sortable:true,formatter:function(value,row,index){
                                    var rval =''
                                    switch(value)
                                        {
                                        case 1:
                                          rval= '<span style=\'color:black\'>在用</span>'
                                          break;
                                        case 2:
                                          rval= '<span style=\'color:black\'>空闲</span>'
                                          break;
                                        case 3:
                                             rval= '<span style=\'color:black\'>测试</span>'
                                             break;
                                        case 4:
                                          rval= '<span style=\'color:black\'>未知</span>'
                                          break;
                                        case 5:
                                             rval= '<span style=\'color:black\'>休眠</span>'
                                             break;
                                         case 6:
                                          rval= '<span style=\'color:black\'>模块不在</span>'
                                          break;
                                        case 7:
                                             rval= '<span style=\'color:black\'>下层关闭</span>'
                                             break;
                                        default:
                                          rval= '<span style=\'color:black\'>其他</span>'
                                        }
                                        console.log(rval)
                                        return rval;
                                       <%--if(value==1){--%>
                                         <%--return '<span style=\'color:black\'>在用</span>'--%>
                                       <%--}--%>
                                       <%--if(value==2){--%>
                                         <%--return '<span style=\'color:black\'>空闲</span>'--%>
                                       <%--}--%>
                                         <%--if(value==3){--%>
                                         <%--return '<span style=\'color:black\'>测试</span>'--%>
                                       <%--}--%>
                                         <%--if(value==4){--%>
                                         <%--return '<span style=\'color:black\'>．未知</span>'--%>
                                       <%--}--%>
                                       <%--return '<span style=\'color:black\'>其他</span>'--%>
                                  }">管理状态
                                    </th>
                                 <th field="alias" width="210" data-options="sortable:true">别名</th>
                                <th field="bandwidth" width="110">带宽(Mb)</th>
                                <%--<th field="ip" width="110">端口地址</th>--%>
                                <%--<th field="mac" width="110">MAC</th>--%>
                                <th field="inflowMbps" width="112" data-options="sortable:true,formatter:function(value,row){
                                    if(row.recordTimeStr)
                                   return value.toFixed(4)
                                }">入流量(Mbps)</th>
                                <th field="outflowMbps" width="170" data-options="sortable:true,formatter:function(value,row){
                                if(row.recordTimeStr)
                                   return value.toFixed(4)
                                }">出流量(Mbps)</th>
                                <th field="inflowusage" width="110" data-options="sortable:true,formatter:function(value,row){
                                   if(row.recordTimeStr&&typeof(value)!='undefined'){
                                    return value.toFixed(4)
                                   }else{
                                     return ''
                                   }

                                }">端口入利用率(%)
                                </th>
                                <th field="outinflowusage" width="110" data-options="sortable:true,formatter:function(value,row){
                                   if(row.recordTimeStr&&typeof(value)!='undefined'){
                                       return value.toFixed(4)
                                   }else{
                                     return ''
                                   }
                                }">端口出利用率(%)</th>
                                 <th field="recordTimeStr" width="160">记录时间</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                    <div data-options="region:'south',border:true,height:200">
                        <div class="easyui-layout" fit="true">
                            <div data-options="region:'center',border:false">
                                <div id="flowchart"></div>
                            </div>
                            <div data-options="region:'east',width:480">
                                <div class="easyui-layout" fit="true">
                                    <div data-options="region:'west',width:235" style="overflow: hidden">
                                        <div id="cpu" ></div>
                                    </div>
                                    <div data-options="region:'center'" style="overflow: hidden">
                                        <div id="memory"></div>
                                    </div>
                                    <%--<div data-options="region:'center'">--%>
                                        <%--<div id="temperature"></div>--%>
                                    <%--</div>--%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var CurrNode = {};
    <c:if test="${not empty deviceid}">
     CurrNode.id = "device_${deviceid}";
    CurrNode.name = "${name}";
    CurrNode.ip = "${ip}";
    CurrNode.model=  "${model}";
    </c:if>

    <%--<c:if test="${not empty obj}">--%>
    <%--CurrNode.id = "device_${deviceid}";--%>
    <%--CurrNode.name = "${routname}";--%>
    <%--CurrNode.ip = "${ipaddress}";--%>
    <%--&lt;%&ndash;CurrNode.ip=  "${obj.serverIpaddress}";&ndash;%&gt;--%>
    <%--CurrNode.model=  "${model}";--%>
    <%--</c:if>--%>
    (function ($) {
        function pagerFilter(data) {
            if ($.isArray(data)) {   // is array
                data = {
                    total: data.length,
                    rows: data
                }
            }
            var target = this;
            var dg = $(target);
            var state = dg.data('datagrid');
            var opts = dg.datagrid('options');
            if (!state.allRows) {
                state.allRows = (data.rows);
            }
            if (!opts.remoteSort && opts.sortName) {
                var names = opts.sortName.split(',');
                var orders = opts.sortOrder.split(',');
                state.allRows.sort(function (r1, r2) {
                    var r = 0;
                    for (var i = 0; i < names.length; i++) {
                        var sn = names[i];
                        var so = orders[i];
                        var col = $(target).datagrid('getColumnOption', sn);
                        var sortFunc = col.sorter || function (a, b) {
                                    return a == b ? 0 : (a > b ? 1 : -1);
                                };
                        r = sortFunc(r1[sn], r2[sn]) * (so == 'asc' ? 1 : -1);
                        if (r != 0) {
                            return r;
                        }
                    }
                    return r;
                });
            }
            var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
            var end = start + parseInt(opts.pageSize);
            data.rows = state.allRows.slice(start, end);
            return data;
        }

        var loadDataMethod = $.fn.datagrid.methods.loadData;
        var deleteRowMethod = $.fn.datagrid.methods.deleteRow;
        $.extend($.fn.datagrid.methods, {
            clientPaging: function (jq) {
                return jq.each(function () {
                    var dg = $(this);
                    var state = dg.data('datagrid');
                    var opts = state.options;
                    opts.loadFilter = pagerFilter;
                    var onBeforeLoad = opts.onBeforeLoad;
                    opts.onBeforeLoad = function (param) {
                        state.allRows = null;
                        return onBeforeLoad.call(this, param);
                    }
                    var pager = dg.datagrid('getPager');
                    pager.pagination({
                        onSelectPage: function (pageNum, pageSize) {
                            opts.pageNumber = pageNum;
                            opts.pageSize = pageSize;
                            pager.pagination('refresh', {
                                pageNumber: pageNum,
                                pageSize: pageSize
                            });
                            dg.datagrid('loadData', state.allRows);
                        }
                    });
                    $(this).datagrid('loadData', state.data);
                    if (opts.url) {
                        $(this).datagrid('reload');
                    }
                });
            },
            loadData: function (jq, data) {
                jq.each(function () {
                    $(this).data('datagrid').allRows = null;
                });
                return loadDataMethod.call($.fn.datagrid.methods, jq, data);
            },
            deleteRow: function (jq, index) {
                return jq.each(function () {
                    var row = $(this).datagrid('getRows')[index];
                    deleteRowMethod.call($.fn.datagrid.methods, $(this), index);
                    var state = $(this).data('datagrid');
                    if (state.options.loadFilter == pagerFilter) {
                        for (var i = 0; i < state.allRows.length; i++) {
                            if (state.allRows[i] == row) {
                                state.allRows.splice(i, 1);
                                break;
                            }
                        }
                        $(this).datagrid('loadData', state.allRows);
                    }
                });
            },
            getAllRows: function (jq) {
                return jq.data('datagrid').allRows;
            }
        })
    })(jQuery);
    function showport(id) {
        top.layer.open({
            //iframe层-父子操作
            type: 2,
            area: ['90%', '90%'],
            fixed: false, //不固定
            maxmin: true,
            content: '${contextPath}/port/portinfo/' + id
        })
    }
    function initFlowChart() {
        var width = $("#flowchart").parent().width()
        var height = $("#flowchart").parent().height();
        var flowchart = echarts.init(document.getElementById("flowchart"));
        var option = {
            title: {
                text: '端口流量',
                subtext: ''
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['入流量', '出流量'],
                right: '0',
                align: 'right'
            },
            grid: {
                left: '2%',
                right: '4%',
                bottom: '30%',
                containLabel: true
            },
//            toolbox: {
//                feature: {
//                    saveAsImage: {}
//                }
//            },
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
                    formatter: '{value} MB'
                }
            },
            series: []
        };
        flowchart.setOption(option);
        flowchart.resize({width: width, height: height});
    }
    function setGaugeValue(id, value) {
        var flowchart = echarts.getInstanceByDom(document.getElementById(id));
        var option = flowchart.getOption();
        option.series[0].data[0].value = value
        flowchart.setOption(option);
    }
    /***
     *仪表盘
     */
    function initGauge(id, title, data) {
        var width = $("#" + id).parent().width()
        var height = $("#" + id).parent().height();
        var chart = echarts.init(document.getElementById(id));
        var option = {
            title: {
                text: title
            },
            tooltip: {
                formatter: "{a} <br/>{b} : {c}%",
                confine: true
            },
            series: [
                {
                    name: title,
                    type: 'gauge',
                    center: ['50%', 120],    // 默认全局居中
//                    radius: '75%',
                    splitNumber: 5,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            width: 4
                        },
                        color: [[0.2, '#7CBB55'], [0.4, '#9CD6CE'], [0.6, '#DDBD4D'], [0.8, '#E98E2C'], [1, '#E43F3D']]
                    },
                    axisTick: {            // 坐标轴小标记
                        length: 12,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto'
                        }
                    },
                    splitLine: {           // 分隔线
                        length: 10,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            color: 'auto'
                        }
                    },
                    pointer: {
                        width: 4
                    },
                    detail: {
                        textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                            fontWeight: 'bolder'
                        }
                    },
                    data: data
                },
            ]
        };
        chart.setOption(option);
        chart.resize({width: width-20, height: height-20});
        $("#"+id).find("canvas").on('dblclick', function (params) {
            if (CurrNode == null||typeof(CurrNode.id)=='undefined') {
                layer.msg('没有选择设备');
            } else {
                var deviceId = CurrNode.id.split("device_")[1];
                top.layer.open({
                    type: 2,
                    area: ['90%', '80%'],
                    title: CurrNode.name + "设备性能历史",
                    maxmin: true, //开启最大化最小化按钮
                    content: contextPath + "/devicecap/hiscap/"+id+"/" + deviceId,
                    btn: ['关闭']
                });
            }
        });
    }
    $(function () {
        try {
            top.hideLeft();
        } catch (e) {
        }
        initGauge("cpu", "CPU利用率", [{value: 0, name: 'CPU利用率'}])
        initGauge("memory", "内存利用率", [{value: 0, name: '内存利用率'}])
//        initGauge("temperature", "温度", [{value: 0, name: '温度'}])
//        setInterval(function(){initFlowChart()}, 500);
        setTimeout(initFlowChart, 300);
        $("#_deviceName").empty().append("<a href='javascript:void(0)' onclick='showdevice(" + CurrNode.id.split("device_")[1] + ")'>" + CurrNode.name +"</a>")
//        $("#_IP").text(CurrNode.ip);
        $("#_model").text(CurrNode.model+ "["+CurrNode.ip+"]")
        var deviceId = CurrNode.id.split("device_")[1];
        searchModels(CurrNode);
        setInterval(function(){ getUsage(deviceId)},5000);
    });
    function showdevice(id) {
        var url = contextPath + "/device/deviceDetails.do?id=" + id + "&buttonType=view&deviceclass=1";
        openDialogView('设备信息', url, '800px', '530px');
    }

    function ClickPort(index, row) {
        var date = new Date();
        var startTime = dateToStr(cala(date,-1))
        var endTime = dateToStr(date)
        $.getJSON("<%=request.getContextPath() %>/port/flowchart/" + row.portid,{
            startTime:startTime,
            endTime:endTime
        }, function (data) {
            var length = data.labels.length;
            var tdata = {
                title: {
                    text: '端口最近24小时流量',
                    subtext: row.portname
                },
                xAxis: [{
                    data: data.labels,
                }],
                series: [{
                    name: '入流量',
                    type: 'line',
                    data: data.inflows,
                }, {
                    name: '出流量',
                    type: 'line',
                    data: data.outflows,
                }
//                    , {
//                    name: '总流量',
//                    type: 'line',
//                    data: data.allflows,
//                }
                ]
            };
            var flowchart = echarts.getInstanceByDom(document.getElementById("flowchart"));
            var option = flowchart.getOption();
            var newoptions = $.extend({}, option, tdata);
            flowchart.setOption(newoptions);
            var width = $("#flowchart").parent().width()
            var height = $("#flowchart").parent().height();
            flowchart.resize({width: width, height: height});
        });
    }
    function cala(date, decday, type) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var mi = date.getMinutes();
        var ms = date.getSeconds();
        var ddd = decday;
        var theday = new Date();
        if (typeof (type) == 'undefined' || type == 'd') {
            var ttt = new Date(y, m - 1, d,h,mi,ms).getTime() + ddd * 24000 * 3600;
            theday.setTime(ttt);
        }
        if (type == 'm') {
            var ttt = new Date(y, m - 1 + ddd, d,h,mi,ms).getTime();
            theday.setTime(ttt);
        }
        else if (type == 'y') {
            var ttt = new Date(y + ddd, m - 1, d,h,mi,ms).getTime();
            theday.setTime(ttt);
        }

        return theday
    }
    function dateToStr(date) {
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            var h = date.getHours();
            var mi = date.getMinutes();
            var ms = date.getSeconds();
            return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d)+" "+(h < 10 ? ('0' + h) : h)+":"+(mi < 10 ? ('0' + mi) : mi)+":"+(ms < 10 ? ('0' + ms) : ms);
    }
    function searchModels(node) {
        var deviceId = node.id.split("device_")[1];

        $("#dg").datagrid({
            url: '<%=request.getContextPath() %>/port/portsflow/' + deviceId
        });
        getUsage(deviceId);
    }
    function getUsage(deviceId){
        $.getJSON(contextPath + "/deviceperforms/usage/" + deviceId, function (data) {
            setGaugeValue("cpu", data.CPU);
            setGaugeValue("memory", data.MEMORY);
//            setGaugeValue("temperature", data.TEMPERATURE);
        })
    }
    function addTask(){
        if (CurrNode == null||typeof(CurrNode.id)=='undefined') {
            layer.msg('没有选择设备');
        } else {
            var deviceId = CurrNode.id.split("device_")[1];
            var index = layer.load(1);
            $.post(contextPath+"/devicecap/syncDevice/"+deviceId,function(data){
                layer.close(index);
                if(data.state){
                    layer.msg("任务添加成功");
                }else{
                    layer.msg("任务添加失败");
                }
            });
        }
    }
    //面板图
    function showPortBoard(portId) {
        if (CurrNode == null||typeof(CurrNode.id)=='undefined') {
            layer.msg('没有选择设备');
        } else {
            var deviceId = CurrNode.id.split("device_")[1];
//            deviceId=663
            top.layer.open({
                type: 2,
                area: ['650px', '450px'],
                title: CurrNode.name + "端口面板图",
                maxmin: true, //开启最大化最小化按钮
                content: contextPath + "/port/portBoard/" + deviceId,
                btn: ['关闭'],
                cancel: function (index) {
                },
                success: function (layero, index) {
                    //top.layer.iframeAuto(index)
//                    layer.setTop(layero); //重点2
                }
            });
//            openDialogView(CurrNode.name+"端口面板图",contextPath+"/port/portBoard/"+deviceId,"700px","450px")
        }
    }
</script>
</body>
</html>