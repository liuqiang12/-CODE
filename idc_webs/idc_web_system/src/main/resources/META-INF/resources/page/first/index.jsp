<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>首屏</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/themes/portal.css">
    <script src="<%=request.getContextPath() %>/framework/jeasyui/plugins/jquery.portal.js"></script>
    <script src="<%=request.getContextPath() %>/framework/echar/echar.min.js"></script>
    <%--<script src="<%=request.getContextPath() %>/framework/echar/map/yunnan.js"></script>--%>
    <%--<script src="<%=request.getContextPath() %>/framework/echar/themes/theme1.js"></script>--%>
    <%--<script src="<%=request.getContextPath() %>/framework/echar/map/fujian.js"></script>--%>
    <style>
        /*.btn {*/
        /*padding: 2px 5px;*/
        /*margin: 2px;*/
        /*}*/
        .table tr {
            border-radius: 2px;
            border-bottom: 1px rgb(228,238,250) solid;
            /*padding: 10px;*/
            font-size: 13px;
            font-family: '微软雅黑';
        }

        .table tr td {
            padding: 10px;
            font-family: '微软雅黑';
            border-top: 1px rgb(228,238,250) solid;
        }

        .table tr:nth-child(odd) {
            /*background: #EEEEF1 none repeat scroll 0 0;*/

            /*font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;*/
        }

        .table tr:nth-child(even) {
            /*background: #fcf8f7 none repeat scroll 0 0;*/
            font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
        }
    </style>
</head>
<%--style="margin:0;padding:0;overflow:hidden;"--%>
<body class="easyui-layout">
<div data-options="region:'west',border:true" style="width: 40%">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'west',border:false" style="width: 300px;overflow: hidden">
            <div style="height:40px;font-size: 14px;font-family: arial 微软雅黑;padding-left: 10px;">
                <div style="position: relative;bottom: -10px">
                    用电分析(当月):
                </div>
            </div>
            <div style="height:108px;padding-left: 10px;">
                <table class="table" style="width: 100%;height: 100%;" ms-controller="btstable">
                    <tr>
                        <td style="width:35%;text-align: right">
                            <span >用电总量：</span>
                        </td>
                        <td><%--<img src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/IconsExtension/20130406125647919_easyicon_net_16.png">--%>
                            <strong >  {{usage0|number(2)}}</strong> Kwh <span ms-html="@usage0img" ></span></span>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:35%;text-align: right" >
                            <span> 移动用电： </span>
                        </td>
                        <td>
                            <strong > {{usage1|number(2)}}</strong>  Kwh <span ms-html="@usage1img" ></span>
                        </td>
                    </tr>
                    <tr>
                        <td style="width:35%;text-align: right" >
                            <span> 联通用电：</span>
                        </td>
                        <td>

                            <strong > {{usage2|number(2)}}</strong> Kwh <span ms-html="@usage2img" ></span></td>
                    </tr>
                    <tr>

                        <td style="width:35%;text-align: right" >
                            <span>电信用电：</span>
                        </td>
                        <td> <strong> {{usage3|number(2)}}</strong> Kwh <span ms-html="@usage3img" ></span></td>
                    </tr>
                </table>
            </div>
        </div>
        <div data-options="region:'center',border:false" id="mainp">
            <div id="main"></div>
        </div>
        <div data-options="region:'south'" style="height: 40%">
            <table class="easyui-datagrid" title="当月用电统计" data-options="fit:true,rownumbers:true,pagination:false,id:'dgtable',
                        striped:true,url:'<%=request.getContextPath() %>/index/usagebytype.do',loadFilter:loadFilter,showFooter: false">
                <thead>
                <tr>
                    <th data-options="field:'name',width:100">区域</th>
                    <th data-options="field:'移动用电',width:150">移动用电(Kwh)</th>
                    <th data-options="field:'联通用电',width:150">联通用电(Kwh)</th>
                    <th data-options="field:'电信用电',width:150">电信用电(Kwh)</th>
                    <%--<th data-options="field:'build3',width:100,align:''">外租用电</th>--%>
                    <%--<th data-options="field:'build4',width:100,align:''">支撑用房</th>--%>
                    <%--<th data-options="field:'jizhan',width:100,align:''">基站</th>--%>
                    <%--<th data-options="field:'allcollector',width:100,align:''">采集器</th>--%>
                    <%--<th data-options="field:'allammeter',width:100,align:''">电表数量</th>--%>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>
<div data-options="region:'center',border:false">
    <div class="easyui-layout" fit="true">
        <div data-options="region:'center',border:false">
            <div class="easyui-layout" fit="true">
                <div data-options="region:'center',border:false">
                    <div id="pie1" style="float: left"></div><%--超表点亮--%>
                    <%--<div style="height:100%;width: 1px;background-color: grey; float: left"></div>--%>
                    <%--<div id="pie2" style="float: left"></div>--%>
                </div>
                <div data-options="region:'north'" style="height: 50%">
                    <div id="bar1" style="float: left"></div>
                    <div style="height:100%;width: 1px;background-color: grey; float: left"></div>
                    <div id="bar2" style="float: left"></div>
                </div>
            </div>
        </div>
        <div data-options="region:'south'" style="height: 30%">
            <div id="bar3"></div>
        </div>
    </div>
</div>


<script type="text/javascript" src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/global/plugins/tree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/framework/avalon/avalon.js"></script>

<script type="text/javascript" language="JavaScript">
    //    $(window).resize(function() {
    //        $('#pp').portal('resize');
    //    });
    var btstable = avalon.define({
        $id: "btstable",
        allpuepre: '0%',
        allalarmcount: '0',
        yoyadd: '0%',
        usage1: 0,
        usage2: 0,
        usage3:0,
        usage0:0,
        usage1img: "---",
        usage2img: "---",
        usage3img:"---",
        usage0img:"---"
    });
    $(function(){
//        $('#pp').portal({
//            border:false,
//            fit:true,
//            onStateChange:function(){
//                return false;
//            }
//        });
//        $('#pp').portal('resize');
//        var panels = $('#pp').portal('getPanels');
//        $.each(panels,function(i,iteam){
//            $('#pp').portal('disableDragging',iteam);
//        })

        $.post(contextPath+"/index/res_group",function(data){

        })
        $.getJSON("<%=request.getContextPath() %>/index/usagebytype.do",function(data){
            console.log(data);
        });
        $.getJSON("<%=request.getContextPath() %>/usageGroup.do",function(data){
//            initChar2("pie1","全省各场所占比");
            if(data.buildtypedata){
                // initChar2("pie1","全省各场所能耗占比",['通信机楼','营业厅','管理用房','支撑用房'],data.buildtypedata);
            }
            if(data.usetypedata){
                // initChar2("pie2","机房各项能耗占比",['主设备','空调设备','其他设备'],data.usetypedata);
            }
        })
        initBarChar("bar1");
        initBar3Char("bar3");
        initChar1();
    });
    function initBarChar(div){
        var width = $("#bar1").parent().width();//$("#piediv").width();
        var height =  $("#bar1").parent().height();//$("#piediv").height();
//        var width = $("#bardiv").width();
//        var height = $("#bardiv").height();
        var top5chart = echarts.init(document.getElementById("bar1"));
        var last5chart = echarts.init(document.getElementById("bar2"));
        var xlabel=[];
        var option = {
            color: ['#367DA2'],
            title: {
                text: 'PUE TOP5',
                subtext:'昨天',
                left: 'center',
                textStyle: {
                    fontSize: 14,
                    fontWeight: 'normal',
                }
            },
            tooltip: {
                "trigger": "axis",
                confine:true,
                "axisPointer": {
                    "type": "shadow"
                }
            },
            xAxis: [{
                type: 'category',
                label:{
                    rotate:45
                },
                "axisLabel": {
                    "interval": 0,
                    "rotate": 45,
                    "show": true,
                    "splitNumber": 6,
                    "textStyle": {
//                        "fontFamily": "微软雅黑",
                        "fontSize": 6
                    }
                },
                data: xlabel
            }],
            yAxis: [{
                type: 'value'
            }],
            grid: {
                left: '0%',
                right: '4%',
                bottom: '20%',
                top: '30%',
                containLabel: true
            },
            series: [{
                name: "PUE",
                type: "bar",
                barWidth: '45%',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                data:[]
            }]
        };
        top5chart.setOption(option);
        top5chart.resize({width:width/2-20,height:height});

        last5chart.setOption(option);
        last5chart.resize({width:width/2-20,height:height});
        $.getJSON("<%=request.getContextPath() %>/top5.do",function(data){
            if(data.top5){
                var xlabel=new Array();
                var xvalue=new Array();
                $.each(data.top5,function(i,iteam){
                    xlabel.push(iteam.machname);
                    xvalue.push(iteam.pue);
                });
                drawbar(top5chart,option,'PUE 差TOP5',data.top5);
                top5chart.resize({width:width/2-20,height:height});
            }
            if(data.last5){
                var newoptions = $.extend({}, option);
//                newoptions.xAxis[0].data=["玉溪"]
//                dom.setOption(newoptions);
                drawbar(last5chart,option,'PUE 优TOP5',data.last5);
                last5chart.resize({width:width/2-20,height:height});
            }
        });
    }
    function drawbar(dom,option,title,data){
        var xlabel=new Array();
        var xvalue=new Array();
        $.each(data,function(i,iteam){
            xlabel.push(iteam.machname);
            xvalue.push(iteam.pue);
        })
        var tdata = {
            title:{
                text:title
            },
            xAxis: [{
                data: xlabel,
                inverse:true
            }],
            series:[{
                data:xvalue
            }]
        };
        var newoptions = $.extend({}, option, tdata);
        dom.setOption(newoptions);
    }
    function initChar2(div,title,legend,series){
        var width = $("#main").parent().width();//$("#piediv").width();
        var height =  $("#main").parent().height();//$("#piediv").height();
        var option = {
            backgroundColor: '#fff',
            color:['#8fc31f','#f35833','#00ccff','#ffcc00'],
            title: {
                text: title,
//                subtext: '昨天',
//                x: 'center',
//                y: 'top',
                textStyle: {
                    fontWeight: 'normal',
                    fontSize: 14
                }
            },
            tooltip: {
                show: true,
                confine:true,
                trigger: 'item',
                formatter: "{b}:</br>{c}kWh ({d}%)"
            },
            legend: {
                orient: 'vertical',
//                left: 'right',
                data: legend
            },
//            grid:{
////                zlevel:11,
//                left:80,
////                bottom:10
//            },
            series: [{
                type: 'pie',
                selectedMode: 'single',
                radius : '60%',
//                radius: ['50%', '60%'],
                label: {
                    normal: {
                        show: false,
//                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        formatter: function(param) {
                            return param.percent + '%';
                        },
                        textStyle: {
                            fontSize: '12',
                            fontWeight: 'bold'
                        }
                    }
                },
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
//                itemStyle: {
//                    normal: {
//                        label:{
//                            show: true,
////	                            position:'inside',
//                            formatter: '{b} : {c} ({d}%)'
//                        }
//                    },
//                    labelLine :{show:false}
//                },
                data: series
            }]
        };
        var chart = echarts.init(document.getElementById(div));
        chart.setOption(option);
        chart.resize({width:width-20,height:height});
    }
    function randomData() {
        return Math.round(Math.random()*100);
    }
    function initChar1(){
        var width = $("#mainp").width();
        var height = $("#mainp").height();
        var chart = echarts.init(document.getElementById('main'));
        var data = [{
            name: '丽江',
            value: 10
        },{
            name: '保山',
            value: 50
        },{
            name: '曲靖',
            value: 20
        },{
            name: '版纳',
            value: 50
        },{
            name: '文山',
            value: 30
        },{
            name: '玉溪',
            value: 70
        }];
        var geoCoordMap = {
            '丽江': [100.25,26.86],
            '保山': [99.18,25.12],
            '昆明': [102.73,25.04	],
            '玉溪': [102.52,24.35],
            '文山': [104.24,23.37	],
            '怒江': [98.85,25.85],
            '临沧': [99.18,25.12],
            '版纳': [100.8,22.02],
            '曲靖': [103.8,25.5],
            '红河': [103.4,23.37],
            '昭通': [103.72,27.33],
            '德宏': [98.58,24.43],
            '迪庆': [99.7,27.83],
            '普洱': [101.03,23.07	],
            '楚雄': [101.54,25.01	],
            '大理': [100.19,25.69	]

        };
        var convertData = function(data) {
            var res = [];
            for (var i = 0; i < data.length; i++) {
                var geoCoord = geoCoordMap[data[i].name.substring(0,2)];
                if (geoCoord) {
                    res.push({
                        name:data[i].name,
                        value: geoCoord.concat(data[i]).concat(data[i].value)
                    });
                }
            }
            return res;
        };
        //把简写地市转换为标准地市名称
        var geoGson = echarts.getMap('yunnan').geoJson.features;//已经注册的地图
        var convertCity = function(data){
            $.each(data,function(i,datai){
                $.each(geoGson,function(j,iteam){
                    if(iteam.properties.name.indexOf(data[i].name) != -1){
                        data[i].oldname =  data[i].name;
                        data[i].name = iteam.properties.name;
                    }
                });
            });
            return data;
        }

        var series = new Array();
        series.push({
                    name: '能耗',
                    type: 'map',
                    mapType: 'yunnan',
                    zoom:1.2,
                    symbolSize: 12,
                    label: {
                        normal: {
                            show: true,
                            formatter:function(a,b){

                                if(isNaN(a.value)){
                                    return ' ';
                                }else{
//                                return a.data['oldname']
                                    return a.value
                                }
                            }
                        },
                        emphasis: {
                            show: true,
                            formatter:'{b}'
                        }
                    },
                    itemStyle: {
                        normal: {
                            areaColor: "#a5e7f0",
                            borderColor: "#ffffff",
                            borderWidth: 0.5,
                            borderType:'dashed',
                            shadowColor: 'rgba(0, 0, 0, 0.5)',
                            shadowBlur: 10
                        },
                        emphasis: {
                            areaColor: "rgba(230,182,0,1)",
                            borderColor: "#dddddd",
                            borderWidth: 1,
                            shadowColor: 'rgba(0, 0, 0, 0.5)',
                            shadowBlur: 15
                        }
                    },
                    data:[]
                }
                ,{
                    name: '告警',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    symbol: 'pin',
                    symbolSize: 50,
                    label: {
                        normal: {
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#d43030',
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },

                    zlevel: 6,
                    data:[],
                    effect: {
                        show: true,
                        shadowBlur: 1
                    }
                });
        var option = {
//        graphic: [
//            {
//                type: 'text',
//                z: 100,
//                left: '5',
//                top: '15',
//                style: {
//                    fill: '#000000',
//                    text: [
//                        ' 全省总能耗预算使用比例：',
//                        ' 全省告警总数：0'
//                    ].join('\n'),
//                    font: 'bold 14px Microsoft YaHei'
//                }
//            },
//            {
//                type: 'text',
//                z: 100,
//                left: '5',
//                top: '45',
//                onmouseover: function (a,b) {
//                    console.log(a);
//                    console.log(b);
//                },
//                style: {
//                    fill: '#000000',
//                    text: [
//                        ' 全省同期能耗增涨：0%',
//                        ' 全省告警总数：0'
//                    ].join('\n'),
//                    font: 'bold 15px Microsoft YaHei'
//                }
//            }
//        ],
            title: {
                text: '云南能耗监视',
                subtext: '说明：统计昨天能耗\r\n与当前告警',
                left: 'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: function (params) {
                    if(params.seriesType=='scatter'){
                        return params.name + '</br>告警 : ' + params.value[params.value.length-1];
                    }else if(params.seriesType=='map'){
                        return params.name + ' </br>能耗: ' +(isNaN(params.value)?0:params.value)+'kWh';
                    }
                }
            },
            geo: {
                map: 'yunnan',
                zoom:1.2,
                itemStyle: {
                    normal: {
                        areaColor: "#a5e7f0",
                        borderColor: "#ffffff",
                        borderWidth: 0.5,
                        borderType:'dashed',
                        shadowColor: 'rgba(0, 0, 0, 0.5)',
                        shadowBlur: 10
                    },
                    emphasis: {
                        areaColor: "rgba(230,182,0,1)",
                        borderColor: "#dddddd",
                        borderWidth: 1,
                        shadowColor: 'rgba(0, 0, 0, 0.5)',
                        shadowBlur: 15
                    }
                }
//            itemStyle: {
//                normal: {
//                    areaColor: '#323c48',
//                    borderColor: '#111'
//                },
//                emphasis: {
//                    areaColor: '#2a333d'
//                }
//            }
            },
            series: series
        };
        chart.setOption(option);
        chart.resize({width:width,height:height});
        $.getJSON("<%=request.getContextPath() %>/alarm/alarmbyregion.do",function(data){
            series[1].data=convertData(data);
            option.series=series;
            chart.setOption(option);
            chart.resize({width:width,height:height});
            chart.on('click', function (params) {
                if(params.seriesName=='告警'){
                    var obj = params.value[2];
                    showallbyRegion(obj.regionID,obj.name);
                }
            });
        });
        $.getJSON("<%=request.getContextPath() %>/puebyregion.do",function(data){
            series[0].data=convertCity(data);
            option.series=series;
            chart.setOption(option);
            chart.resize({width:width,height:height});
        })
    }
    function showallbyRegion(regionid,name){
        top.layer.open({
            type : 2,
            title : name+'当前告警信息信息',
            maxmin : true,
            id: 'alarmwin', //设定一个id，防止重复弹出
            closeBtn:1,
            area : [ '800px', '450px' ],
            content :'<%=request.getContextPath() %>/alarm/getAlarmByRegion.do?regionid='+regionid
        });
    }
    function initBar3Char(div){
        initline();
        return ;
        var width = $("#bar3").parent().width();
        var height = $("#bar3").parent().height();
        var chart = echarts.init(document.getElementById("bar3"));
        var option = {
            title : {
                text: '能耗同比',
                x:'center'
            },
            tooltip : {
                trigger: 'axis',
                confine:true,
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
//                formatter:function(a,b,c){
//                    if(a.seriesType=='bar'){//总数据
//                        return ""+(a.seriesName.replace('年总能耗', "年"+a.name+"总能耗"))+":</br>"+ a.value+'kWh';
//                    }else if(a.seriesType=='line'){
//                        return ""+(a.seriesName.replace('年机楼能耗', "年"+a.name+"年机楼能耗"))+":</br>"+ a.value+'kWh';
//                    }
//                }
            },
            legend: {
                orient: 'vertical',
                left: 'right',
//                data:['2015总能耗','2016全省能耗','2015机楼能耗','2016机楼能耗']
            },
            grid:{
                zlevel:10,
//               left:30,
                bottom:20
            },
            calculable : true,
            xAxis : [
                {   position:'',
                    type : 'category',
                    data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                }

            ],
            yAxis : [
                {
                    type : 'value',
                    name: '单位：kWh',
                    axisLine: {
                        lineStyle: {
                            color: 'red'
                        }
                    },
//                    axisLabel: {
//                        formatter: '{value} kWh'
//                    }
                }
            ],
            series : [

            ]
        };
        $.getJSON("<%=request.getContextPath() %>/usagetrend.do",function(data){
            var series = [];
            var legend = [];
            if(data.alldata){
                for(var k in data.alldata){
                    var t ={name:k,type:'bar',data: data.alldata[k]};
                    legend.push(k);
                    series.push(t);
                }
            }
//            if(data.allroomdata){
//                for(var k in data.allroomdata){
//                    var t ={name:k,type:'line',data: data.allroomdata[k]};
//                    legend.push(k);
//                    series.push(t);
//                }
//            }
//            if(data.allroomdata){
//                for(var k in data.allroomdata){
//                    var t ={name:k,type:'line',
//                        symbolSize:10,
//                        symbol:'circle',
//                         data: data.allroomdata[k]};
//                    legend.push(k);
//                    series.push(t);
//                }
//            }
//            series.push(
//                    {
//                        name:'2019全省能耗',
//                        type:'bar',
//                        data:[1,{value:5,name:'qq',adf:'sdfdf'},2,3,4,6,8,9,7,10,11,12]
//                    }
//            );
            chart.setOption({legend:{data:legend},series:series});
        });
        chart.setOption(option);
        chart.resize({width:width,height:height});
    }
    function onLoadSuccess(data) {
        //添加“合计”列
        $('#dgtable').datagrid('appendRow', {
            Saler: '<span class="subtotal">合计</span>',
            build1: '<span class="subtotal">' + compute("build1") + '</span>',
            build2: '<span class="subtotal">' + compute("build2") + '</span>',
            build3: '<span class="subtotal">' + compute("build3") + '</span>',
            build4: '<span class="subtotal">' + compute("4") + '</span>',
            build4: '<span class="subtotal">' + compute("4") + '</span>',
            build4: '<span class="subtotal">' + compute("4") + '</span>',
            Rate: '<span class="subtotal">' + ((compute("TotalOrderScore") / compute("TotalTrailCount")) * 100).toFixed(2) + '</span>'
        });
    }
    function loadFilter(data){
        var xmap = {};
        // 上月用电

        var data=[];
        data.push({name:'昆明', '移动用电':13342, "联通用电":12342,"电信用电":14311});
        data.push({name:'丽江', '移动用电':16342, "联通用电":11341,"电信用电":16078});
        data.push({name:'玉溪', '移动用电':17342, "联通用电":13342,"电信用电":11311});

        var lastdata=[];
        lastdata.push({name:'昆明', '移动用电':11342, "联通用电":10342,"电信用电":13311});
        lastdata.push({name:'丽江', '移动用电':14342, "联通用电":10781,"电信用电":14078});
        lastdata.push({name:'玉溪', '移动用电':13452, "联通用电":11442,"电信用电":10311});

        $.post(contextPath+"/index/usagebytype.do?lastmonth=true",function(lastdata1){
            var last =groupByName(lastdata,xmap,"pre");
            var now =groupByName(data,xmap,"now");
            btstable.usage1img  ="<strong style='color: red'>↑↑↑</strong>";//getImg(last.usage1,now.usage1);
            btstable.usage0img  ="<strong style='color: red'>↑↑↑</strong>";//getImg(last.usage0,now.usage0);
            btstable.usage2img  ="<strong style='color: red'>↑↑↑</strong>";//getImg(last.usage2,now.usage2);
            btstable.usage3img  ="<strong style='color: red'>↑↑↑</strong>";//getImg(last.usage3,now.usage3);

            btstable.usage1= now.usage1;
            btstable.usage2= now.usage2;
            btstable.usage3= now.usage3;
            btstable.usage0= btstable.usage1+btstable.usage2+btstable.usage3;
            initMoMbar(xmap);
            initChar2("main","用电分析",['移动用电','联通用电','电信用电'],[{name:'移动用电',value: btstable.usage1},{name:'联通用电',value: btstable.usage2},{name:'电信用电',value: btstable.usage3}]);
        });
        return data;
    }
    function getImg(a1,a2){
        if(a1==a2){
            return "<strong>---</strong>"
        }
        if(a1<a2){
            return "<strong style='color: red'>↑↑↑</strong>"
        }
        else {
            return "<strong style='color: green'>↓↓↓</strong>"
        }
    }
    function groupByName(data,xmap,xname){
        var tmp={usage1:0,usage2:0,usage3:0,usage0:0};
        $.each(data,function(j,iteam){
            var ydata = xmap[iteam['name']];
            if(typeof(ydata)=='undefined'){
                ydata={pre:0,now:0};
                xmap[iteam['name']]=ydata;
            }
            ydata[xname]=Math.floor((iteam['移动用电']+iteam['联通用电']+iteam['电信用电'])*100/10000)/100;
            tmp.usage1+=iteam['移动用电'];
            tmp.usage2+=iteam['联通用电'];
            tmp.usage3+=iteam['电信用电'];
        });
        tmp.usage0=tmp.usage1+tmp.usage2+tmp.usage3
        return tmp;
    }

    function compute(colName) {
        var rows = $('#dgtable').datagrid('getRows');
        var total = 0;
        for (var i = 0; i < rows.length; i++) {
            total += parseFloat(rows[i][colName]);
        }
        return total;
    }
    function initMoMbar(xmap){
        var xlabels = [];
        var ylastmonth = [];
        var ymonth = [];
        for (var key in xmap){
            xlabels.push(key);
            ylastmonth.push(xmap[key]['pre'])
            ymonth.push(xmap[key]['now'])
        }
        var option = {
            title : {
                text: '用电量同比分析'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['去年同月用电','本月用电'],
                orient: 'vertical',
                left: 'right'
            },
            toolbox: {
                show : true,
                feature : {
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            grid:{
                zlevel:10,
//               left:30,
                bottom:50
            },
//            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : xlabels,
                    label:{
                        rotate:45
                    },
                    "axisLabel": {
                        "interval": 0,
                        "rotate": 45,
                        "show": true,
                        "splitNumber": 6,
                        "textStyle": {
//                        "fontFamily": "微软雅黑",
                            "fontSize": 6
                        }
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    name: '单位：W kWh'
                }
            ],
            series : [
                {
                    name:'去年同月用电',
                    type:'bar',
                    data:ylastmonth,
                    markLine : {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    }
                },
                {
                    name:'本月用电',
                    type:'bar',
                    data:ymonth,
                    markLine : {
                        data : [
                            {type : 'average', name : '平均值'}
                        ]
                    }
                }
            ]
        };
        var last5chart = echarts.init(document.getElementById("pie1"));
        var width = $("#pie1").parent().width();//$("#piediv").width();
        var height =  $("#pie1").parent().height();//$("#piediv").height();
        last5chart.setOption(option);
        last5chart.resize({width:width-20,height:height});
    }
//    function getLast12Months(){
//        var last12Months = [];
//        var today = new Date();
//
//        today.setMonth(today.getMonth()+1);
//        for(var i=0;i<12;i++){
//            var lastMonth = today.setMonth(today.getMonth()-1);
//            last12Months[11-i] = today.getFullYear() + "-" + fillZero((Number(today.getMonth())+1),2);
//        }
//        return last12Months;
//    }

    function getLineData(){

        var yd =[];
        var lt =[];
        var dx =[];
        var all =[];
        for(var i=0;i<13;i++){
            yd.push(parseInt(Math.random()*(10000-8000+1)+8000));
            lt.push(parseInt(Math.random()*(9000-8000+1)+8000));
            dx.push(parseInt(Math.random()*(10000-8000+1)+8000));
            all.push(parseInt(yd[i]+dx[i]+lt[i]));
        }
        return {yd:yd,lt:lt,dx:dx,all:all}
    }
    /***
     *单端口单载频能耗
     */
    function initline(){

        var width = $("#bar3").parent().width();
        var height = $("#bar3").parent().height();
        var chart = echarts.init(document.getElementById("bar3"));
        var data =getLineData();
        var option = {
            title : {
                text: '用电趋势',
                x:'left'
            },
            tooltip : {
                trigger: 'axis',
                confine:true,
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
//                orient: 'vertical',
//                left: 'right',
                data:['总量','移动','联通','电信']
            },
            grid:{
                zlevel:10,
//               left:30,
                bottom:50
            },
            calculable : true,
            xAxis : [
                {   position:'',
                    type : 'category',
                    data :(function(){
                        var t=['2016-03','2016-04','2016-05','2016-06','2016-07','2016-08','2016-09','2016-10','2016-11','2016-12','2017-01','2017-02','2017-03'];
                       // var t = getLast12Months();
                        return t;
                    })(),//['玉溪','江川县','通海县','华宁县','澄江县','易门县','峨山彝族自治县','新平彝族傣族自治县'],
                    "axisLabel": {
                        "interval": 0,
                        "rotate": 45,
                        "show": true,
                        "splitNumber": 6,
                        "textStyle": {
//                        "fontFamily": "微软雅黑",
                            "fontSize": 5
                        }
                    }
                }

            ],
            yAxis : [
                {
                    type : 'value',
                }
            ],
            series : [
                {
                    name:"总量",type:'line',areaStyle: {normal: {}},data: data.all
                },
                {
                    name:"移动",type:'line',areaStyle: {normal: {}},data: data.yd
                },
                {
                    name:"联通",type:'line',areaStyle: {normal: {}},data: data.lt
                },
                {
                    name:"电信",type:'line',areaStyle: {normal: {}},data: data.dx
                }
            ]
        };
        console.log(option)
        chart.setOption(option);
        chart.resize({width:width,height:height});
//        $.post(contextPath+"/index/usageline.do",function(data){
//            var xlables  =[];
//            var avgcarrier  =[];
//            var avgwbport  =[];
//            $.each(data,function(i,iteam){
//                xlables.push(iteam.cityname);
//                avgcarrier.push(iteam.avgcarrier);
//                avgwbport.push(iteam.avgwbport);
//            });
//            option.xAxis[0]['data']=xlables;
//            option.series[0]['data']=avgwbport;
//            option.series[1]['data']=avgcarrier;
//            chart.setOption(option);
//            chart.resize({width:width,height:height});
//        })
    }
    /***
     *抄表电量
     */
    function initbarusage(xmap){
        var xlabels = [];
        var ymonth = [];
        for (var key in xmap){
            xlabels.push(key);
            ymonth.push(xmap[key]['now'])
        }
        var width = $("#pie1").parent().width();
        var height = $("#pie1").parent().height();
        var chart = echarts.init(document.getElementById("pie1"));
        var option = {
            title : {
                text: '抄表电量',
                x:'center'
            },
            tooltip : {
                trigger: 'axis',
                confine:true,
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                orient: 'vertical',
                left: 'right',
//                data:['2015总能耗','2016全省能耗','2015机楼能耗','2016机楼能耗']
            },
            grid:{
                zlevel:10,
//               left:30,
                bottom:20
            },
            calculable : true,
            xAxis : [
                {   position:'',
                    type : 'category',
                    data : xlabels
                }

            ],
            yAxis : [
                {
                    type : 'value',
                    name: '单位：W kWh',
                }
            ],
            series : [
                {
                    name:'抄表电量',
                    type:'bar',
                    data:ymonth,
                    markLine : {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    }
                }
            ]
        };
        chart.setOption(option);
        chart.resize({width:width,height:height});
//        chart.setOption({legend:{data:legend},series:series});
        <%--$.getJSON("<%=request.getContextPath() %>/usagetrend.do",function(data){--%>
        <%--var series = [];--%>
        <%--var legend = [];--%>
        <%--if(data.alldata){--%>
        <%--for(var k in data.alldata){--%>
        <%--var t ={name:k,type:'bar',data: data.alldata[k]};--%>
        <%--legend.push(k);--%>
        <%--series.push(t);--%>
        <%--}--%>
        <%--}--%>
        <%--chart.setOption({legend:{data:legend},series:series});--%>
        <%--});--%>
        <%--chart.setOption(option);--%>
        <%--chart.resize({width:width,height:height});--%>
    }


</script>
</body>
</html>