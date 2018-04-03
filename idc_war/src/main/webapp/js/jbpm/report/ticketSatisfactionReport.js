
    var nullObj=new Object();

    nullObj.预占流程=0;  //预占流程默认值评价0个
    nullObj.开通流程=0; //开通流程默认值评价0个
    nullObj.停机流程=0; //停机流程评价0个
    nullObj.复通流程=0; //复通流程默认值评价0个
    nullObj.下线流程=0; //下线流程默认值评价0个
    nullObj.变更开通流程=0; //变更开通流程默认值评价0个
    nullObj.临时测试流程=0; //临时测试流程默认值评价0个
    nullObj.业务变更流程=0; //业务变更流程默认值评价0个

//Echart的值
 var government_five=nullObj;    //政企  五星评价
 var government_four=nullObj;    //政企  四星评价
 var government_three=nullObj;    //政企  三星评价
 var government_two=nullObj;    //政企  二星评价
 var government_one=nullObj;    //政企  一星评价

 var myself_five=nullObj;    //自有  五星评价
 var myself_four=nullObj;    //自有  四星评价
 var myself_three=nullObj;    //自有  三星评价
 var myself_two=nullObj;    //自有  二星评价
 var myself_one=nullObj;    //自有  一星评价




$(function() {
    loadTableGrid("gridId");
    $('#jbpm_tabs').tabs({
        border:false,
        onSelect:function(title){
            if(title == '列表展示'){

                loadTableGrid("gridId");
            }else if(title == '图形展示'){
                //加载echart的数据
                loadEchartData();
                loadEchart();
            }
        }
    });


});

function loadEchart() {
    var myChart = echarts.init(document.getElementById('main'));

    var option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data:['预勘','开通','停机','复通','下线','变更开通','临时测试','业务变更','其他']
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
                data : ['预勘','开通','停机','复通','下线','变更开通','临时测试','业务变更']
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'政企_五星评价',
                type:'bar',
                stack: '政企业务',
                data:[government_five.预占流程, government_five.开通流程, government_five.停机流程, government_five.复通流程, government_five.下线流程, government_five.业务变更流程, government_five.临时测试流程, government_five.变更开通流程]
            },
            {
                name:'政企_四星评价',
                type:'bar',
                stack: '政企业务',
                data:[government_four.预占流程, government_four.开通流程, government_four.停机流程, government_four.复通流程, government_four.下线流程, government_four.变更开通流程, government_four.临时测试流程, government_four.业务变更流程]
            },
            {
                name:'政企_三星评价',
                type:'bar',
                stack: '政企业务',
                data:[government_three.预占流程, government_three.开通流程, government_three.停机流程, government_three.复通流程, government_three.下线流程, government_three.变更开通流程, government_three.临时测试流程, government_three.业务变更流程]
            },
            {
                name:'政企_二星评价',
                type:'bar',
                stack: '政企业务',
                data:[government_two.预占流程, government_two.开通流程, government_two.停机流程, government_two.复通流程, government_two.下线流程, government_two.变更开通流程, government_two.临时测试流程, government_two.业务变更流程]
            },
            {
                name:'政企_一星评价',
                type:'bar',
                stack: '政企业务',
                data:[government_one.预占流程, government_one.开通流程, government_one.停机流程, government_one.复通流程, government_one.下线流程, government_one.变更开通流程, government_one.临时测试流程, government_one.业务变更流程]
            },{
                name:'自有_五星评价',
                barWidth:20,
                type:'bar',
                stack: '自有业务',
                data:[myself_five.预占流程, myself_five.开通流程, myself_five.停机流程, myself_five.复通流程, myself_five.下线流程, myself_five.变更开通流程, myself_five.临时测试流程, myself_five.业务变更流程]
            },
            {
                name:'自有_四星评价',
                type:'bar',
                stack: '自有业务',
                data:[myself_four.预占流程, myself_four.开通流程, myself_four.停机流程, myself_four.复通流程, myself_four.下线流程, myself_four.变更开通流程, myself_four.临时测试流程, myself_four.业务变更流程]
            },
            {
                name:'自有_三星评价',
                type:'bar',
                stack: '自有业务',
                data:[myself_three.预占流程, myself_three.开通流程, myself_three.停机流程, myself_three.复通流程, myself_three.下线流程, myself_three.变更开通流程, myself_three.临时测试流程, myself_three.业务变更流程]
            },
            {
                name:'自有_二星评价',
                type:'bar',
                stack: '自有业务',
                data:[myself_two.预占流程, myself_two.开通流程, myself_two.停机流程, myself_two.复通流程, myself_two.下线流程, myself_two.变更开通流程, myself_two.临时测试流程, myself_two.业务变更流程]
            },
            {
                name:'自有_一星评价',
                type:'bar',
                stack: '自有业务',
                data:[myself_one.预占流程, myself_one.开通流程, myself_one.停机流程, myself_one.复通流程, myself_one.下线流程, myself_one.变更开通流程, myself_one.临时测试流程, myself_one.业务变更流程]
            }
        ]
    };

    myChart.setOption(option);

}

function loadEchartData(gridId){

    $.ajax({
        type:"POST",
        url:contextPath+"/actJBPMController/ticketSatisfactionReportData.do",
        async: false,
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        //在请求之前调用的函数
        beforeSend:function(){
        },
        //成功返回之后调用的函数
        success:function(data){

            for(i = 0; i < data.length; i++) {
                if(data[i].PROD_CATEGORY == 1){
                    if(data[i].SATISFACTION == 'five_star'){
                        government_five = data[i];
                    }else if(data[i].SATISFACTION == 'four_star'){
                        government_four = data[i];
                    }else if(data[i].SATISFACTION == 'three_star'){
                        government_three = data[i];
                    }else if(data[i].SATISFACTION == 'two_star'){
                        government_two = data[i];
                    }else if(data[i].SATISFACTION == 'one_star'){
                        government_one = data[i];
                    }
                }else if(data[i].PROD_CATEGORY == 0){
                    if(data[i].SATISFACTION == 'five_star'){
                        myself_five = data[i];
                    }else if(data[i].SATISFACTION == 'four_star'){
                        myself_four = data[i];
                    }else if(data[i].SATISFACTION == 'three_star'){
                        myself_three = data[i];
                    }else if(data[i].SATISFACTION == 'two_star'){
                        myself_two = data[i];
                    }else if(data[i].SATISFACTION == 'one_star'){
                        myself_one = data[i];
                    }
                }
            }
        },
    error: function(){
        }
    })
}

function loadTableGrid(gridId){
    var columns = createColumns();
    $("#"+gridId).datagrid({
        url:contextPath+"/actJBPMController/ticketSatisfactionReportData.do",
        queryParams: {'createTime': $('#createTime').val(),
            'endTime': $("#endTime").val()},
        /*onLoadSuccess:myLoadsuccess,*/
        columns:columns,
        toolbar:"#requestParamSettins_taskQuery"
    });
}

function createColumns(){
    var headCols = [];
    headCols.push({field:"PROD_CATEGORY",title:"业务类型",align:'center',width:30,formatter:fmtProdCategory});
    headCols.push({field:"SATISFACTION",title:"好评分数",align:'center',width:30,formatter:fmtSatisfaction});
    headCols.push({field:"预占流程",title:"预占流程好评（个）",align:'center',width:40});
    headCols.push({field:"开通流程",title:"开通流程好评（个）",align:'center',width:40});
    headCols.push({field:"停机流程",title:"停机流程好评（个）",align:'center',width:40});
    headCols.push({field:"复通流程",title:"复通流程好评（个）",align:'center',width:40});
    headCols.push({field:"下线流程",title:"下线流程好评（个）",align:'center',width:40});
    headCols.push({field:"变更开通流程",title:"变更开通流程好评（个）",align:'center',width:40});
    headCols.push({field:"临时测试流程",title:"临时测试流程好评（个）",align:'center',width:40});
    headCols.push({field:"业务变更流程",title:"业务变更流程好评（个）",align:'center',width:40});
    return [headCols];
}

function fmtProdCategory(value,row,index){
    if(value == '0'){
        return "自有业务";
    }else if(value == '1'){
        return "政企业务";
    }
}

function fmtSatisfaction(value,row,index){
    if(value == 'five_star'){
        return "五星好评";
    }else if(value == 'four_star'){
        return "四星评分";
    }else if(value == 'three_star'){
        return "三星评分";
    }else if(value == 'two_star'){
        return "二星评分";
    }else if(value == 'one_star'){
        return "一星差评";
    }
}

