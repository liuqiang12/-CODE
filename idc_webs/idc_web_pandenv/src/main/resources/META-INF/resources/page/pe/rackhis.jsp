<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_page.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/main.css"
          rel="stylesheet" type="text/css"/>
    <title>能耗分析</title>
    <script src="<%=request.getContextPath() %>/framework/layui/layui.js"></script>
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/framework/layui/css/layui.css"/>

</head>
<body>
<div class="easyui-layout" fit="true" style="overflow-x:hidden;background-color: rgb(73,73,73)">
    <%--<div class="content_suspend">--%>
        <%--<div class="conter">--%>
            <%--<ul id="rtree" class="ztree" style="width:200px; overflow:auto;"></ul>--%>
        <%--</div>--%>
        <%--<div class="hoverbtn">--%>
            <%--<span>导</span><span>航</span>--%>

            <%--<div class="hoverimg" height="9" width="13"></div>--%>
        <%--</div>--%>
    <%--</div>--%>
    <%--<div data-options="region:'north',collapsible:false" style="height: 260px" title="机楼">--%>
        <%--<div class="floars">--%>

        <%--</div>--%>
    <%--</div>--%>
    <div data-options="region:'center'">
        <div id="line"></div>
        <%--<div class="easyui-layout" fit="true">--%>
            <%--<div data-options="region:'west'" style="width: 30%">--%>
                <%--<div id="pie"></div>--%>
            <%--</div>--%>
            <%--<div data-options="region:'center'" border="false" style="color:#fff">--%>
                <%--<div class="easyui-layout" fit="true">--%>
                    <%--<div data-options="region:'north'" border="false"--%>
                         <%--style="height:38px;padding:5px 15px 5px 5px;color:#fff ">--%>
                        <%--查看日期:<input id="datetime" type="text" class="easyui-datebox"--%>
                            <%--/>--%>
                        <%--<input id="cyc" class="easyui-switchbutton" data-options="onText:'当日',offText:'当月'"/>--%>
                    <%--</div>--%>
                    <%--<div data-options="region:'west'" border="false" style="width:300px">--%>
                        <%--<div style="height:26px;margin-top:6px;color: #fff;font-size: 20px">--%>
                            <%--用电统计：<span style="font-size: 10px;color:yellow">PUE:主设备+空调/主设备</span>--%>
                        <%--</div>--%>
                        <%--<div class="title_desc2" style="margin-top:5px;">--%>
                            <%--<div class="width100 box-sizing right_border pedesc" style="float:left;">--%>
                                <%--&lt;%&ndash;<h5 class="width100" style="float:left;text-align: center;font-weight:bold">硬盘</h5>&ndash;%&gt;--%>

                                <%--<div>--%>
                                    <%--<h6 class="width50" style="text-align:right">总能耗：</h6><h6 class="width50 all">--%>
                                    <%--1</h6>--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                    <%--<h6 class="width50" style="text-align:right">主设备：</h6><h6 class="width50 device">--%>
                                    <%--1</h6>--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                    <%--<h6 class="width50" style="text-align:right">空调：</h6><h6--%>
                                        <%--class="width50 air">0</h6>--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                    <%--<h6 class="width50" style="text-align:right">其他：</h6><h6--%>
                                        <%--class="width50 other">0</h6>--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                    <%--<h6 class="width50" style="text-align:right">PUE：</h6><h6 class="width50 pue">--%>
                                    <%--1</h6>--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                    <%--<h6 class="width50" style="text-align:right">能耗环比：</h6><h6--%>
                                        <%--class="width50 add">0</h6>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div data-options="region:'center'" border="false">--%>
                        <%--<div id="line"></div>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--&lt;%&ndash;<div style="height: 30px;padding-top: 8px;padding-left: 10px">&ndash;%&gt;--%>
                <%--&lt;%&ndash;查看日期:<input  id="dd"  type= "text" class= "easyui-datebox" required ="required" style="width:100px"> </input>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<input class="easyui-switchbutton" data-options="onText:'当日',offText:'当月'">&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>

            <%--</div>--%>

        <%--</div>--%>


        <%--<div class="layui-row">--%>
        <%--<div class="layui-col-xs3">--%>
        <%--<div id="pie">1</div>--%>
        <%--</div>--%>
        <%--<div class="layui-col-xs4">2</div>--%>
        <%--<div class="layui-col-xs5">--%>
        <%--<div id="line">3</div>--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
</div>
<script type="text/javascript">
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
    //一般直接写在一个js文件中
    layui.use(['layer', 'element'], function () {
        var element = layui.element;
    });
    $(function () {
        try {
            top.hideLeft();
        } catch (e) {
        }
        var rackid = "${rackid}";
        var isDay = 0
        var getLineData = function () {
            $.getJSON(contextPath+"/pe/his/rack/"+rackid,function(data){
                var chart = echarts.getInstanceByDom(document.getElementById("line"));
                var option = chart.getOption();
                var series = [];
                var xdates = [];
                if(data.ok){
                    xdates =  data.times
                    $.each(data.lines,function(i,item){
                        series.push({
                            name: item.name,
                            type: 'line',
                            areaStyle: {normal: {}},
                            showSymbol: false,
                            hoverAnimation: false,
                            data: item.data
                        })
                    })
                }else{
                    series.push({
                        name: "",
                        type: 'line',
                        areaStyle: {normal: {}},
                        showSymbol: false,
                        hoverAnimation: false,
                        data: []
                    })
                }
                option.xAxis[0].data = xdates;
                option.series = series;
                chart.clear();
                chart.setOption(option);
            })
        }
        initLine();
        getLineData();
    })

    function initLine() {
        var option = {
            title: {
                text: '机架用电趋势',
                left: 0
            },
            tooltip: {
                trigger: 'axis'
            },
            grid: {
                left: '2%',
                right: '4%',
//                bottom: '0%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                axisLabel: {
                    rotate: 60//倾斜度 -90 至 90 默认为0
                },
                data: []
            },
            yAxis: {
                type: 'value',
                //name: unit,
                min:0,
                position: 'left',
                axisLabel: {
                    formatter: '{value}'
                }
            },
            series: []
        };
        $("#line").css({"width": $("#line").parent().width() , "height": $("#line").parent().height()});  //获取p元素的样式颜色
        var pieChar = echarts.init(document.getElementById("line"));
        pieChar.setOption(option);
    }
</script>
</body>
</html>