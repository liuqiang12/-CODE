<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/import_skin.css" rel="stylesheet" type="text/css" id="skin" themeColor="${skin}"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_page.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/swipers.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/default/easyui.css">

    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/normalize.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/main.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/css/cutomer_infoview_table.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/jquery.autocompleter.js"></script>

    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/main.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/bootstrap/css/bootstrap.min.css"/>

    <title>客户资源统计</title>
    <script type="text/javascript">
        //客户ID
        var customerId = "${customerId}";
        var cusList = '${cusList}';
        if(cusList!=null&&cusList!=''){
            cusList = JSON.parse(cusList);
        }
        var portNameLength = 0;//端口名字个数
        var page = 1;//当前页码
        var pageTotal = 0;//总页数
        $(function () {
            loadAutocompleterContro();
            // getLoadPortFlowViewParams(page);
            if(customerId!=null&&customerId!=""){
                loadPortFlowView2d(page);
            }
        });
        //加载用户搜索框
        function loadAutocompleterContro(){
            $('#searchUser').autocompleter({
                highlightMatches: true,
                source: cusList,
                template: '{{ label }}<span>({{ value2 }})</span>',
                hint: true,
                empty: false,
                limit: 5,
                callback: function (value, index, selected) {
                    if (selected) {
                        customerId = selected.value1;
                        $('#searchUser').autocompleter('option',
                            {source: cusList}
                        );
                    }
                }
            });
        }
        //查询客户相关信息
        function seacherCusInfo(){
            var customerName = $('#searchUser').val();
            if(customerName==null||customerName==''){
                layer.msg('请输入客户名称');
                return;
            }
            if (customerId==null||customerId=="") {
                layer.msg('没有该用户,请重新输入');
                return;
            }
            var index = layer.load(0, {shade: 0.5});
            var url = contextPath + "/resource/main_customer_resource?customerId=" + customerId;
            window.location.href = url;
        }
        function openWinByType(type) {
            var url = contextPath + "/resource/preQueryResourceInfos?type="+type+"&customerId=" + customerId;
            openDialogView('工单-资源信息', url, '800px', '530px');
        }
        function getLoadPortFlowViewParams(value){
            if(value<1){
                return;
            }
            var portFlowMap = '${portFlowMap}';
            if(portFlowMap!=null&&portFlowMap!="") {
                portFlowMap = JSON.parse(portFlowMap);
                var portNameData = portFlowMap.portnameList;
                if (portNameData != null && portNameData.length > 0) {
                    portNameLength = portNameData.length;
                    pageTotal = Math.ceil(portNameLength/5);
                    var resultMap = portFlowMap.resultMap;
                    //组装X轴
                    var timeData = resultMap[portNameData[0]].timeStrs;
                    //组装series
                    var seriesArr = [];
                    var newPortNameData = [];
                    for (var i = 0; i < portNameData.length; i++) {
                        if (i < value*5&&i>=(value-1)*5) {
                            var portName = portNameData[i];
                            newPortNameData.push(portName);
                            seriesArr.push({
                                name: portName,
                                type: 'line',
                                smooth: true,
                                label: {
                                    normal: {
                                        show: false,
                                        position: 'top',
                                        formatter: "{c}"
                                    }
                                },
                                data: resultMap[portName].outFlows
                            });
                        }
                    }
                }
                loadPortFlowView(newPortNameData,timeData,seriesArr);
            }
        }
        //加载流量视图
        function loadPortFlowView(date,xDate,series){
            var option = {
                title: {
                    text: '客户流量TOP5',
                    textStyle: {
                        fontSize: 16,
                        color: '#057300'
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    confine: true
                },
                legend: {
                    data: date,
                    x: 120
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
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    top: '20%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    //                    axisLabel: {
                    //                        rotate: 25,
                    //                        interval: 0
                    //                    },
                    data: xDate
                },
                yAxis: {
                    type: 'value'
                },
                series: series
            };
            //初始化echarts实例
            var portChart = echarts.init(document.getElementById('portFlow'));
            //使用制定的配置项和数据显示图表
            portChart.setOption(option);

            // var portChart1 = echarts.init(document.getElementById('portFlow1'));
            // //使用制定的配置项和数据显示图表
            // portChart1.setOption(option);
        }
        /*上下翻页*/
        // function portFlowPage(type){
        //     if(type=="up"){
        //         if(page==1){
        //             layer.msg("没有上一页了！",{time:1000});
        //             return;
        //         }
        //         page-=1;
        //     }else{
        //         if(page==pageTotal){
        //             layer.msg("没有下一页了！",{time:1000});
        //             return;
        //         }
        //         page+=1;
        //     }
        //     // getLoadPortFlowViewParams(page);
        //     loadPortFlowView2d(page);
        // }
        function loadPortFlowView2d(pageNo){
            var index = layer.load(1, {shade: 0.5});
            $.post(contextPath+"/resource/queryPortFlowInfoByCustomerId",{customerId:customerId,rows:5,page:pageNo},function(result){
                layer.close(index);
                var portName1 = result.portNameList;
                var portFlowMap = result.portFlowMap;
                pageTotal = result.pageTotal;
                //组装X轴
                var xData = portFlowMap[portName1[0]].timeStrs;
                var seriesArr = [];
                for (var i = 0; i < portName1.length; i++) {
                    var portName = portName1[i];
                    seriesArr.push({
                        name: portName,
                        type: 'line',
                        smooth: true,
                        label: {
                            normal: {
                                show: false,
                                position: 'top',
                                formatter: "{c}"
                            }
                        },
                        data: portFlowMap[portName].outFlows
                    });
                }
                loadPortFlowView(portName1,xData,seriesArr);
            });
        }
    </script>
</head>
<!--框架必需end  id="scrollContent" -->
<body>
<!-- 查询 -->
<div class="width100 box-sizing " style="height:38px;">
    <div class="search_cus_input field " >
        <div class="search_btn" onclick="seacherCusInfo();">
            <span>
                搜索
            </span>
        </div>
        <input type="text" name="searchUser" id="searchUser" placeholder="快速搜索客户" maxlength="40" value="${idcReCustomer.name}"/>
    </div>
</div>
<!-- 界面布局情况 -->
<div class="desklayout height100" style="overflow-y: auto;overflow-x: hidden">
    <!-- 分三个div并排  -->
    <div class="deskLayout001 height100">
        <%--<div class="width100 box-sizing " style="height:300px;position: relative">--%>
        <%--<div id="portFlow1" style="width:100%;height: 100%;"></div>--%>
        <%--<button style="float:right;position:absolute;top:50px;right:0px;border:0px;" type="button" class="btn btn-default" onclick="portFlowPage('up')">--%>
        <%--<span class="glyphicon glyphicon-chevron-up"></span>--%>
        <%--</button>--%>
        <%--<button style="float:right;position:absolute;top:220px;right:0px;border:0px;" type="button" class="btn btn-default" onclick="portFlowPage('down')">--%>
        <%--<span class="glyphicon glyphicon-chevron-down"></span>--%>
        <%--</button>--%>
        <%--</div>--%>
        <!-- 1DIV start -->
        <div class="float_left col width100 height100" style="background-color:#E9ECF3">
            <div class="width100 relative-position box-sizing"
                 style="height:210px;border:5px solid transparent;border-bottom: hidden;">
                <div class="height100 width100 relative-position box-sizing" style="float:left;">
                    <!--  标题设置 -->
                    <div class="titleBox_center">
                        <div class="titleBox_left">
                            <div class="titleBox_right">
                                <!-- 图标标题操作 -->
                                <div class="box1_icon khywtjicon">
                                    <div class="box_title"></div>
                                </div>
                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                    <!--  下方内容 -->
                    <div class="box1_content quick_content" style="overflow:hidden;">

                        <div class="height100 box-sizing" style="width:100%;float:left;border-right: 5px solid transparent;">

                            <div class="title_desc2" style="margin-top:5px;height: 160px;overflow: hidden">
                                <div class="box-sizing right_border" style="width:13.4%;float:left;height: 160px">
                                    <div class="height50 width100 box-sizing" style="float:left;padding-top:20px;">
                                        <h5 class="width100" style="text-align: center;">订单数</h5>
                                        <div>
                                            <h6 class="width100"
                                                style="text-align:center;font-size: 40px;font-weight: 500;margin: 10px auto;">${ticketNum==null?"0":ticketNum}</h6>
                                            <h6 class="width100" style="text-align:center;margin: 5px auto;">
                                                <a href="javascript:void(0)" onclick="openWinByType('ticketNum')">
                                                    <span style="color:blue;">&nbsp;订单信息>></span></a>
                                            </h6>
                                        </div>
                                    </div>
                                </div>


                                <div class="box-sizing left_border right_border"
                                     style="width:13.4%;float:left;height: 160px">
                                    <div class="height50 width100 box-sizing" style="float:left;padding-top:20px;">
                                        <h5 class="width100" style="text-align: center;">占用机架</h5>
                                        <div>
                                            <h6 class="width100"
                                                style="text-align:center;font-size: 40px;font-weight: 500;margin: 10px auto;">${resourceNumInfos.RACKNUM==null?0:resourceNumInfos.RACKNUM}</h6>
                                            <h6 class="width100" style="text-align:center;margin: 5px auto;">
                                                <a href="javascript:void(0)" onclick="openWinByType('rackNum')">
                                                    <span style="color:blue;">&nbsp;机架信息>></span></a>
                                            </h6>
                                        </div>
                                    </div>
                                </div>

                                <div class="box-sizing left_border right_border"
                                     style="width:13.4%;float:left;height: 160px">
                                    <div class="height50 width100 box-sizing" style="float:left;padding-top:20px;">
                                        <h5 class="width100" style="text-align: center;">占用机位</h5>
                                        <div>
                                            <h6 class="width100"
                                                style="text-align:center;font-size: 40px;font-weight: 500;margin: 10px auto;">${resourceNumInfos.RACKUNITNUM==null?0:resourceNumInfos.RACKUNITNUM}</h6>
                                            <h6 class="width100" style="text-align:center;margin: 5px auto;">
                                                <a href="javascript:void(0)" onclick="openWinByType('rackunitNum')">
                                                    <span style="color:blue;">&nbsp;机位信息>></span></a>
                                            </h6>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-sizing left_border right_border"
                                     style="width:13.4%;float:left;height: 160px">
                                    <div class="height50 width100 box-sizing" style="float:left;padding-top:20px;">
                                        <h5 class="width100" style="text-align: center;">占用IP地址</h5>
                                        <div>
                                            <h6 class="width100"
                                                style="text-align:center;font-size: 40px;font-weight: 500;margin: 10px auto;">${resourceNumInfos.IPNUM==null?0:resourceNumInfos.IPNUM}</h6>
                                            <h6 class="width100" style="text-align:center;margin: 5px auto;">
                                                <a href="javascript:void(0)" onclick="openWinByType('ipNum')">
                                                    <span style="color:blue;">&nbsp;IP信息>></span></a>
                                            </h6>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-sizing left_border right_border"
                                     style="width:46.4%;float:left;height: 160px">
                                    <div id="portFlow" style="width:100%;height: 100%;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!--============================================table页====================================-->
            <div class="width100 box-sizing" style="height:370px;border-left:5px solid transparent;border-right:5px solid transparent;border-bottom:5px solid transparent;">
                <!--  空间资源 -->
                <div class="height100 relative-position box-sizing" style="width:33.33%;float:left" >
                    <!--  下方内容 -->
                    <div class="box1_content quick_content" style="overflow:hidden;">
                        <div class="width100 height60" style="float:left;">
                            <div style="height:22px;font-size: 14px;color: #666;font-weight: bold;margin:0px 0px 5px 1px;">
                                <div class="width100" style="float:left;">
                                    <span>占用机架</span>
                                    <a href="javascript:void(0)" style="float: right" onclick="openWinByType('rack')">
                                        <span style="color:blue;font-size: 12px;font-weight: normal;"> 更多>></span></a>
                                </div>
                            </div>
                            <div class="title_split" style="margin-bottom: 10px;"></div>
                            <div style="width:100%;height: 100%;">
                                <table id="table1" class="bordered">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>工单号</th>
                                        <th>资源名称</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${rackInfos!=null}">
                                        <c:forEach var="iteam" items="${rackInfos}" varStatus="uno">
                                            <tr>
                                                <td>${uno.index+1}</td>
                                                <td>${iteam.SERIALNUMBER}</td>
                                                <td>${iteam.RACKNAME}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <!--  IP地址 -->
                <div class="height100 relative-position box-sizing" style="width:33.33%;border-left:5px solid transparent;float:left;">
                    <!--  下方内容 -->
                    <div class="box1_content quick_content" style="overflow:hidden;">

                        <div class="width100 height60" style="float:left;">
                            <div style="height:22px;font-size: 14px;color: #666;font-weight: bold;margin:0px 0px 5px 1px;">
                                <div class="width100" style="float:left;">
                                    <span>IP地址</span>
                                    <a href="javascript:void(0)" style="float: right" onclick="openWinByType('ip')">
                                        <span style="color:blue;font-size: 12px;font-weight: normal;"> 更多>></span></a>
                                </div>
                            </div>
                            <div class="title_split" style="margin-bottom: 10px;"></div>
                            <div style="width:100%;height: 100%;">
                                <table id="table2" class="bordered">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>工单号</th>
                                        <th>IP地址起</th>
                                        <th>IP地址止</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${ipInfos!=null}">
                                        <c:forEach var="iteam" items="${ipInfos}" varStatus="uno">
                                            <tr>
                                                <td>${uno.index+1}</td>
                                                <td>${iteam.SERIALNUMBER}</td>
                                                <td>${iteam.BEGINIP}</td>
                                                <td>${iteam.ENDIP}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <!--  占用端口 -->
                <div class="height100 relative-position box-sizing" style="width:33.33%;border-left:5px solid transparent;float:left;">
                    <!--  下方内容 -->
                    <div class="box1_content quick_content" style="overflow:hidden;">

                        <div class="width100 height60" style="float:left;">
                            <div style="height:22px;font-size: 14px;color: #666;font-weight: bold;margin:0px 0px 5px 1px;">
                                <div class="width100" style="float:left;">
                                    <span>占用端口</span>
                                    <a href="javascript:void(0)" style="float: right" onclick="openWinByType('port')">
                                        <span style="color:blue;font-size: 12px;font-weight: normal;"> 更多>></span></a>
                                </div>
                            </div>
                            <div class="title_split" style="margin-bottom: 10px;"></div>
                            <div style="width:100%;height: 100%;">
                                <table id="table3" class="bordered">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>工单号</th>
                                        <th>端口名称</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${portInfos!=null}">
                                        <c:forEach var="iteam" items="${portInfos}" varStatus="uno">
                                            <tr>
                                                <td>${uno.index+1}</td>
                                                <td>${iteam.SERIALNUMBER}</td>
                                                <td>${iteam.PORTNAME}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>