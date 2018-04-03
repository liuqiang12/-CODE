<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/themes/css/form.css"/>
    <title>性能监视</title>

</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',title:'资源树',width:230,border:true,headerCls:'panel-background'"
         style="padding:2px;">
        <ul id="rtree" class="ztree"></ul>
        <%--<div class="left-tree">--%>
            <%--<ul id="rtree" class="ztree" style="width:500px; overflow:auto;"></ul>--%>
        <%--</div>--%>
    </div>
    <div data-options="region:'center',border:true" style="padding-left:2px;overflow:hidden;">
        <iframe scrolling="yes"  width="100%" height="100%" frameBorder=0 id="devicecap"  name="devicecap" allowTransparency="true"  src="<%=request.getContextPath() %>/devicecap/0"></iframe>
    </div>
</div>
<script type="text/javascript">
    var  CurrNode = null;
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

    function getData() {
        var rows = [];
        for (var i = 1; i <= 800; i++) {
            rows.push({
                device: '设备01',
                name: '端口' + i,
                inflow: '100Mb',
                createtime: new Date(),
                outflow: '100Mb',
                allflow: '200Mb'
            });
        }
        return rows;
    }
    function showport(id) {
        top.layer.open({
            //iframe层-父子操作
            type: 2,
            area: ['800px', '330px'],
            fixed: false, //不固定
            maxmin: true,
            content: '<%=request.getContextPath() %>/port/portinfo/' + id
        })
    }
    function initFlowChart() {
        var width = $("#flowchart").parent().width()
        var height = $("#flowchart").parent().height();
        var flowchart = echarts.init(document.getElementById("flowchart"));
        var option = {
            title: {
                text: '端口流量',
                subtext:''
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['入流量', '出流量', '总流量'],
                right:'0',
                align:'right'
            },
            grid: {
                left: '2%',
                right: '4%',
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
//        ClickPort(1,{portid:3});
    }
    function setGaugeValue(id,value){
        var flowchart = echarts.getInstanceByDom(document.getElementById(id));
        var option = flowchart.getOption();
        option.series[0].data[0].value=value
        flowchart.setOption(option);
        flowchart.on("click",function(ps){
            console.log(ps)
        })
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
                confine:true
            },
            series: [
                {
                    name: title,
                    type: 'gauge',
                    center: ['50%',120],    // 默认全局居中
//                    radius: '75%',
                    splitNumber:5,
                    axisLine: {            // 坐标轴线
                        lineStyle: {       // 属性lineStyle控制线条样式
                            width: 4
                        },
                        color: [[0.2, '#7CBB55'], [0.4, '#9CD6CE'], [0.6, '#DDBD4D'], [0.8, '#E98E2C'], [1, '#E43F3D']]
                    },
                    axisTick: {            // 坐标轴小标记
                        length:12,        // 属性length控制线长
                        lineStyle: {       // 属性lineStyle控制线条样式
                            color: 'auto'
                        }
                    },
                    splitLine: {           // 分隔线
                        length:10,         // 属性length控制线长
                        lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                            color: 'auto'
                        }
                    },
                    pointer: {
                        width:4
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
        chart.resize({width: width, height: height});
        $("#"+id).find("canvas").on('dblclick', function (params) {
           alert(1)
        });
    }
    function showHis(type,id){
        if(CurrNode==null){
            layer.msg('没有选择设备');
        }else{
            var deviceId = CurrNode.id.split("device_")[1];
            top.layer.open({
                type: 2,
                area: ['650px', '450px'],
                title: CurrNode.name+type.toUpperCase()+"历史记录",
                maxmin: true, //开启最大化最小化按钮
                content: contextPath+"/devicecap/hiscap/"+type+"/"+deviceId,
                btn: ['关闭']
            });
        }
    }
    $(function () {
        top.hideLeft();
        //$('#dg1').datagrid({data: getData()}).datagrid('clientPaging');
        var setting = {
            view: {
                //showIcon: showIconForTree
            },
            data: {
                key:{
                    name:'text'
                }
            }
        };
        var dom = $("#rtree");
        if (dom != null) {
            var tree = dom.rtree({
                r_type: 4,
                isShowRack:'N',
                deviceclass:'1',
                onClick: function (iteam, treeId, treeNode) {
                    if (treeNode.id.indexOf("device_") > -1) {
                        CurrNode = treeNode;
                        var deviceid = treeNode.id.split("device_")[1];
                        $("#devicecap").attr("src",contextPath+"/devicecap/"+deviceid);
//                        $("#_deviceName").empty().append("<a href='javascript:void(0)' onclick='showdevice("+treeNode.id.split("device_")[1]+")'>"+treeNode.name+"</a>")
//                        //$("#_deviceName").text("<a href='javascript:void(0)' onclick='showdevice("+treeNode.id.split("device_")[1]+")'>"+treeNode.name+"</a>")
//                        $("#_IP").text(treeNode.ip);
//                        $("#_model").text(treeNode.model)
//                        $("#_phone").text(treeNode.phone?treeNode.phone:'')
//                        $("#_desc").text(treeNode.desc?treeNode.desc:'')
//                        searchModels(treeNode);
                    }
//                    if (treeNode.id.indexOf("idcroom_") > -1) {
//                        CurrNode = treeNode;
//                        searchModels();
//                    }
                }})
        }
//        initGauge("cpu", "CPU利用率", [{value: 0, name: 'CPU利用率'}])
//        initGauge("memory", "内存利用率", [{value: 0, name: '内存利用率'}])
//        initGauge("temperature", "温度", [{value: 0, name: '温度'}])
//        setTimeout(initFlowChart,300)
//        ();
    });
    function showdevice(id){
        var url = contextPath + "/device/deviceDetails.do?id=" + id + "&buttonType=view&deviceclass=1";
        openDialogView('设备信息', url,'800px','530px');
    }
    function ClickPort(index,row){
        $.getJSON("<%=request.getContextPath() %>/port/flowchart/" + row.portid, function (data) {
            var length = data.labels.length ;
            var tdata = {
                title: {
                    text: '端口流量',
                    subtext:row.portname
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
                }, {
                    name: '总流量',
                    type: 'line',
                    data: data.allflows,
                }]
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
    function searchModels(node){
        var deviceId = node.id.split("device_")[1];
//        deviceId = 439;
        $("#dg").datagrid({
            url:'<%=request.getContextPath() %>/port/portsflow/'+deviceId
        });
        $.getJSON(contextPath+"/deviceperforms/usage/"+deviceId,function(data){
            setGaugeValue("cpu",data.CPU);
            setGaugeValue("memory",data.MEMORY);
            setGaugeValue("temperature",data.TEMPERATURE);
        })
        <%--$("#dg2").datagrid({--%>
            <%--url:'<%=request.getContextPath() %>/port/portsflow/634'//+deviceId--%>
        <%--});--%>

    }
    //面板图
    function showPortBoard(portId){
        if(CurrNode==null){
            layer.msg('没有选择设备');
        }else{
            var deviceId = CurrNode.id.split("device_")[1];
//            deviceId=663
            top.layer.open({
                type: 2,
                area: ['650px', '450px'],
                title: CurrNode.name+"端口面板图",
                maxmin: true, //开启最大化最小化按钮
                content: contextPath+"/port/portBoard/"+deviceId,
                btn: ['关闭'],
                cancel: function (index) {
                },
                success: function(layero,index){
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