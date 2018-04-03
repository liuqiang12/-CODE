<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>

    <title>资源占用流程</title>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div>
        开始时间：<input class="easyui-datetimebox" required="required" name="startTime" id="startTime" value=""
                    data-options="formatter:myformatter,parser:myparser"/>-
        结束时间：<input class="easyui-datetimebox" required="required" name="endTime" id="endTime" value=""
                    data-options="formatter:myformatter,parser:myparser"/>
        <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',onClick:setPortData">查询</a>
        <a  href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'',onClick:exportData">导出</a>
    </div>
    <div id="flowchart"></div>
</div>

<script type="text/javascript">
    var portid = "${id}";
    function initFlowChart() {
        var width = $("#flowchart").parent().width()
//        var height = $("#flowchart").parent().height();
        var chartdiv = $("#flowchart")
        var height = chartdiv.parent().height() - chartdiv.prev().height();
        var flowchart = echarts.init(document.getElementById("flowchart"));
        var option = {
            title: {
                text: '端口流量变化'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['入流量', '出流量']
            },
            grid: {
                left: '3%',
                right: '4%',
//                bottom: '30%',
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
                    formatter: '{value} Mbps'
                }
            },
            series: []
        };
        flowchart.setOption(option);
        flowchart.resize({width: width, height: height});
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
    function dateToStr(theday) {
//        var m  = (theday.getMonth() + 1)
//        if(m<10){
//            m="0"+m;
//        }
//        var d =  theday.getDate();
//        if(d<10){
//            d="0"+d;
//        }
        return myformatter(theday)
//        return theday.getFullYear()+ "-" + m + "-" + d ;
    }
    var uuid = "";
    function exportData(){
        if(uuid&&uuid!=""){
            var myChart = echarts.getInstanceByDom(document.getElementById("flowchart"));
            var guid= $("#guid").val();
            var imgUrl = myChart.getDataURL("png");//获得img对象base64编码
            jQuery('<form action="'+contextPath +'/capreport/downcap" method="post">' +  // action请求路径及推送方法
                    '<input type="text" name="uuid" value="'+uuid+'"/>' + // 文件路径
                    '<input type="text" name="reportType" value="1"/>' + // 文件路径
                    '<input type="text" name="portType" value="1"/>' + // 文件名称
                    '<input type="text" name="ports" value=""/>' + // 文件名称
                    '<input type="text" name="startTime" value=""/>' + // 文件名称
                    '<input type="text" name="endTime" value=""/>' + // 文件名称
                    '<input type="text" name="cyc" value=""/>' + // 文件名称
                    '<input type="text" name="img" value="'+imgUrl+'"/>' + // 文件名称
                    '</form>').attr('target', '_blank')
                    .appendTo('body').submit().remove();
        }
    }
    function setPortData() {
        var startTime =$("#startTime").datebox("getValue");
        var endTime =$("#endTime").datebox("getValue");
        $.getJSON("<%=request.getContextPath() %>/port/flowchart/" + portid,{
            startTime:startTime,
            endTime:endTime
        }, function (data) {
            uuid = data.uuid;
            var length = data.labels.length;
            var tdata = {
                title: {
                    text: '端口流量变化'
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
                }]
            };
            var flowchart = echarts.getInstanceByDom(document.getElementById("flowchart"));
            var option = flowchart.getOption();
            var newoptions = $.extend({}, option, tdata);
            flowchart.setOption(newoptions);
        });
    }
    function myformatter(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var mi = date.getMinutes();
        var ms = date.getSeconds();
        return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d)+" "+(h < 10 ? ('0' + h) : h)+":"+(mi < 10 ? ('0' + mi) : mi)+":"+(ms < 10 ? ('0' + ms) : ms);
    }

    function myparser(s) {
        if (!s) return new Date();
        var sst = (s.split(' '));
        var ss = (sst[0].split('-'));
        var y = parseInt(ss[0], 10);
        var m = parseInt(ss[1], 10);
        var d = parseInt(ss[2], 10);

        var mi = (sst[1].split(':'));
        var hh = parseInt(mi[0], 10);
        var mm = parseInt(mi[1], 10);
        var ss = parseInt(mi[2], 10);

        if (!isNaN(y) && !isNaN(m) && !isNaN(d)&& !isNaN(hh)&& !isNaN(mm)&& !isNaN(ss)) {
            return new Date(y, m - 1, d,hh,mm,ss);
        } else {
            return new Date();
        }
    }
    $(function () {
        var date = new Date();
        var startTime = dateToStr(cala(date,-6));//+" 00:00:00"
        var endTime =  dateToStr(cala(date,0));//+" 23:59:59";
        $("#startTime").datetimebox('setValue',startTime);
        $("#endTime").datetimebox('setValue',endTime);
        initFlowChart();
        setPortData();
    });
</script>
</body>
</html>