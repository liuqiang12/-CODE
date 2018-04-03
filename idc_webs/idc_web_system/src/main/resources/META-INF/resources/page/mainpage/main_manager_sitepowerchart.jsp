<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
<link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>



<!--
<link rel="stylesheet" href="../plugins/bootstrap/css/bootstrap.min.css">
<script src="../plugins/bootstrap/js/bootstrap.min.js"></script>
-->

<style>
	/*
	.datagrid-htable, .datagrid-btable {
		width:100%;
	}
	*/
</style>

<script>
	
	$(function(){
		 
		
	}); 
	
	
</script>
 
<!--框架必需end-->
<body   style="overflow-y: hidden!important;height:465px!important;">
	 <!-- 界面布局情况 -->
    <div class="desklayout height100">
    	<!-- 分三个div并排  -->
        <div class="deskLayout001 height100">
        	<!-- 1DIV start -->
            <div class="float_left col width100 height100" style="background-color:#FFF;overflow-y: hidden;">
			
			
				<!-- 图形区域  start -->
					<div class="width100 height100"  >
						
						<div class="height100 width100 " id="chart9" ></div>
						
					</div>
					<!-- 图形区域  end --> 
				
				
				
				
            </div>
            <!-- 1DIV end -->
            
        </div>
        
		
		
    </div>
	
<script>
	var PortFlowInterval=5000;//10秒
	$(function(){
		//var url=window.location.href;
		//var chartType=url?url.split("?")[1]:0;
 		var chartType="${sitepowertype}";
 		chartType=chartType?parseInt(chartType):0;

		 // 基于准备好的dom，初始化echarts实例
        var 
			mychart9 = echarts.init(document.getElementById('chart9'));
		
		
		
		
		var topsize=7;
		 
		
		
		//机房日用电量TOP5
        mychart9.setOption(buildSiteTopChart(chartType,'机房日能耗TOP'+topsize,'用电量','kWh'));		
		 
		
		
	});
	
	
	//构建机房假数据
	function buildSiteDatas(type){
		var result=[];
		var now=new Date();
		var size=type!=0?mGetDate(now.getFullYear(),now.getMonth()+1):24;
		

		for(var i = 0 ; i < size ; i++ ){
			var item={daydata:randoms(6.25,20.83),monthdata:randoms(150,500)};
			if(type!=0){//月
				item['xname']=(i+1)+"日";
			}else{
				item['xname']=initDataByLen(i+"","0",2)+":00:00";
			}
			
			result.push(item);
		}
		return result;
	}
	
	function mGetDate(year, month){
		var d = new Date(year, month, 0);
		return d.getDate();
	}

	
	function getValsFromItemsBykey(items,key){
		var result=[];
		if(items&&items.length>0){
			for(var i = 0 ; i < items.length ; i++ ){
				var it=items[i];
				result.push(it[key]);
			}
		}
		return result;
	}
	
	//根据max\min生成随机数
	function randoms(min,max,isFixed){
		var result=Math.random()*max;
		if(min>result){
			result=randoms(min,max);
		}
		var fixed=2;
		if(typeof isFixed!="undefined"){
			fixed=isFixed;
		}
		return parseFloat(parseFloat(result).toFixed(fixed));
	}
	
	
	function initDataByLen(str,char,len){
		var result=str;
		if(result&&result.length<len){
			for(var i = result.length ; i < len ; i++ ){
				result=char+result;
			}
		}
		return result;
	}
	
	function formatDateStr(d){
		var result="";
		var year=initDataByLen(d.getFullYear()+"","0",4);
		var month=initDataByLen(d.getMonth()+1+"","0",2);
		var date=initDataByLen(d.getDate()+"","0",2);
		var hour=initDataByLen(d.getHours()+"","0",2);
		var miniute=initDataByLen(d.getMinutes()+"","0",2);
		var second=initDataByLen(d.getSeconds()+"","0",2);
		result=year+"-"+month+"-"+date+" "+hour+":"+miniute+":"+second;
		return result;
	}
	
	function formatHMSStr(d){
		var result="";
		var year=initDataByLen(d.getFullYear()+"","0",4);
		var month=initDataByLen(d.getMonth()+1+"","0",2);
		var date=initDataByLen(d.getDate()+"","0",2);
		var hour=initDataByLen(d.getHours()+"","0",2);
		var miniute=initDataByLen(d.getMinutes()+"","0",2);
		var second=initDataByLen(d.getSeconds()+"","0",2);
		result=hour+":"+miniute+":"+second;
		return result;
	}
 
	
	
	
	//构建机房统计数据
	function buildSiteTopChart(type,title,legendTitle,unitName){
		//var models=buildSiteDatas(size).sort(function(a,b){return a[key]<b[key]});
		var models=buildSiteDatas(type);
		var datas=getValsFromItemsBykey(models,(type!=0?"monthdata":"daydata"));
		var xnames=getValsFromItemsBykey(models,'xname');
	
		for(var i = 0 ; i < xnames.length ; i++ ){
			if((i+1)%2==0){
				//xnames[i]='\n'+xnames[i];
			}
		}
	
		var option = {
			/*
			title : {
				show:false,
				text: title,
				textStyle:{
					color:'#6E6A6F',
					fontSize:14
				},
				x:'center',
				y:'top'
			},
			*/
			tooltip : {
				trigger: 'axis',
				confine:true ,
				formatter: "机房 : {b} <br/>{a} : {c}("+unitName+")"
			},
			legend: {
				data:[legendTitle],
				orient:"horizontal",
				y: 'bottom'
			},
			toolbox: {
				show : true,
				orient:"vertical",
				x: 'right',
				y: 'center',
				feature : {
					magicType : {show: true, type: ['line', 'bar']},
					saveAsImage : {show: true}
				}
			},
			calculable : true,
			grid: {
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow',
						label: {
							show: true,
							formatter: function (params) {
								return params.value.replace('\n', '');
							}
						}
					}
				}
				//,x:100
			},
			itemStyle: {
				normal:{
					color:'#c23531',
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				},
				emphasis: {
					borderWidth:2,
					borderColor:'#c23531'
					
				}
			},
			xAxis : [
				{
					type : 'category',
					data : xnames,
					axisLabel:{
						rotate:20,
						interval:0
					},
					text:'123'
				}
			],
			yAxis : [
				{
					type : 'value',
                	name: legendTitle+'（'+unitName+'）'
				}
			],
			series : [
				{
					name:legendTitle,
					type:'bar',
					smooth: true,
					label: {
						normal: {
							show: true,
							position: 'top',
							formatter: "{c}"//"{c}("+unitName+")"
						}
					},
					data:datas
				}
			]
		};
		return option;
	}

	
</script>	
	
	

	
	
	
</body>
</html>