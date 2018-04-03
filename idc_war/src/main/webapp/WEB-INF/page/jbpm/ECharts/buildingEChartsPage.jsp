<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
<script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/js/echart/custom.theme.js"></script>

<style>
	/*
	.datagrid-htable, .datagrid-btable {
		width:100%;
	}
	*/
</style>

<!--框架必需end-->
<body id="scrollContent" >
	<div class="easyui-layout" fit="true">
		<div data-options="region:'west',split:true" title="数据中心菜单" style="width:180px;">
			<div id="dataCenterTree" style="width: 500px"> </div>
		</div>


		<div data-options="region:'center',title:'图表展示',iconCls:'icon-ok'">

			<div id="chartmain" style="width:1100px; height: 500px;"></div>

		</div>
	</div>
</body>

<script>
    var serveRack = 0;    //在服的机架
    var serveRackUnit = 0;    //在服的U位
    var serveMCB = 0;	//在服的MCB
    var servePort = 0;	//在服的端口
    var serveIP = 0;		//在服的IP
    var serveDevice = 0;		//在服的设备

    var leisureRack = 0;    //空闲的机架
    var leisureRackUnit = 0;    //空闲的U位
    var leisureMCB = 0;	//空闲的MCB
    var leisurePort = 0;	//空闲的端口
    var leisureIP = 0;		//空闲的IP
    var leisureDevice = 0;		//空闲的设备

    $(function() {
        //初始化时就加载树菜单
        loadTree();

		//首次进入时，没有数据中心id，没有机房id，也没有机楼id,所以传""
        initializeEchart("","","");

        //初始化时就加载图表的数据
        loadEchart();
    });

    function loadTree(){
        $('#dataCenterTree').tree({
            lines:true,  //显示线条
            animate:true,   //折叠动画效果
            url:contextPath+"/actJBPMController/buildingEChartsTree.do",
			/*url:contextPath+"/assetBaseinfo/checkboxOpenMSG/1000",*/
            loadFilter: function (data) {
                if (data.d) {
                    return data.d;
                } else {
                    return data;
                }
            },
            onClick:function (row) {
                var dataCenterID;  //数据中心ID、
                var buildingID;   //机楼ID
                var roomID;      //机房ID

                if(row.text.indexOf("数据中心")>-1){   //text包含相关字段
                    dataCenterID=row.id;
                }else if(row.text.indexOf("机楼")>-1){
                    buildingID=row.id;
                }else if(row.text.indexOf("机房")>-1){
                    roomID=row.id;
                }else{

				}
                initializeEchart(dataCenterID,buildingID,roomID);
                loadEchart();
            }
        });

    }

    function initializeEchart(dataCenterID,buildingID,roomID){
		/*点击机房就加载对应机房的数据*/
        $.ajax({
            url:contextPath+"/actJBPMController/buildingEChartsData.do",
            type:"post",
            async:false,  /* 关闭ajax的异步，否则初次进入页面没有数据  */
            data:{"dataCenterID":dataCenterID,"buildingID":buildingID,"roomID":roomID},
            success:function(data){

                serveRack = data.rackStatusUse  ? data.rackStatusUse : 0;    //在服的机架
                serveRackUnit = data.rackUnitStatusUse  ? data.rackUnitStatusUse : 0;    //在服的U位
                //var serveMCB = data.;	//在服的MCB
                servePort = data.portStatusUse  ? data.portStatusUse : 0;	//在服的端口
                serveIP = data.ipStatusUse  ? data.ipStatusUse : 0;		//在服的IP
                serveDevice = data.serverStatusUse  ? data.serverStatusUse : 0;		//在服的设备

                leisureRack = data.rackStatusFree  ? data.rackStatusFree : 0;    //空闲的机架
                leisureRackUnit = data.rackUnitStatusFree  ? data.rackUnitStatusFree : 0;    //空闲的U位
                //leisureMCB = data.;	//空闲的MCB
                leisurePort = data.portStatusFree  ? data.portStatusFree : 0;	//空闲的端口
                leisureIP = data.ipStatusFree  ? data.ipStatusFree : 0;		//空闲的IP
                leisureDevice = data.serverStatusFree  ? data.serverStatusFree : 0	;		//空闲的设备
            },
            error:function(e){
                alert("错误！！");
                window.clearInterval(timer);
            }
        });


    }


    /*加载图表*/
    function  loadEchart() {
        //指定图标的配置和数据
        var option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data:[{
                    name: '系列1',
                    // 强制设置图形为圆。
                    icon: 'circle',
                    // 设置文本为红色
                    textStyle: {
                        color: 'green'
                    }
                }]
            },
            toolbox: {
                show : true,
                orient: 'vertical',
                x: 'right',
                y: 'center',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : ['机架','U位','MCB','端口','IP(所有的IP)','设备']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],

            series : [
                {
                    name:'在服数量',
                    type:'bar',
                    stack: 'wcg',
                    data:[serveRack, serveRackUnit, serveMCB, servePort, serveIP, serveDevice]
                },
                {
                    name:'空闲数量',
                    type:'bar',
                    stack: 'wcg',
                    data:[leisureRack, leisureRackUnit, leisureMCB, leisurePort, leisureIP, leisureDevice]
                }
            ]};
        var myChart = echarts.init(document.getElementById('chartmain'));

        //使用制定的配置项和数据显示图表
        myChart.setOption(option);
    }


</script>
</html>