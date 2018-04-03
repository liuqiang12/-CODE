<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
<link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/import_skin.css" rel="stylesheet" type="text/css" id="skin" themeColor="${skin}"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_page.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/swipers.css" rel="stylesheet" type="text/css"/>

<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/normalize.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/main.css" rel="stylesheet" type="text/css"/>

<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/bootstrap-switch.css" />

<!--
<script src="<%=request.getContextPath() %>/framework/themes/js/jquery-1.4.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/plugins/jquery/jquery-1.10.2.min.js"></script>

-->
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/jquery.jqDock.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/fisheye-iutil.min.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/js/echart/custom.theme.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/js/swipers.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/jquery.autocompleter.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/js/main.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/bootstrap-switch.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/highlight.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_manager/switch/main.js"></script>


<!--
<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/themes/plugins/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/framework/themes/plugins/bootstrap/js/bootstrap.min.js"></script>
-->

<style>
	/*
	.datagrid-htable, .datagrid-btable {
		width:100%;
	}
	*/
</style>

<script>

</script>
 
<!--框架必需end-->
<body id="scrollContent" >
	 <!-- 界面布局情况 -->
    <div class="desklayout height100">
    	<!-- 分三个div并排  -->
        <div class="deskLayout001 height100">
        	<!-- 1DIV start -->
            <div class="float_left col width100 height100" style="background-color:#E9ECF3;overflow-y: auto;">
			
			
			
				<div class="box-margin10 width25 box-sizing" id="area_left">



					<div class="width100 box-sizing" style="height:630px;float:left;">
						<div class="width100 height100 relative-position" >
							<!--  标题设置 start  -->
							<div class="titleBox_center">
								<div class="titleBox_left">
									<div class="titleBox_right">
										<!-- 图标标题操作 -->
										<div class="box1_icon dbicon">
											<div class="box_title">IDC服务质量统计</div>
											<div class="box1_status">

												<!--
												<a href="javascript:alert('链接到其他界面')" target="_self">更多&gt;&gt;</a>
												 -->

											</div>
										</div>
										<div class="clear"></div>
									</div>
								</div>
							</div>
							<!--  下方内容 -->
							<div class="box1_content quick_content" style="overflow:hidden;">

								<div class="width100 height100">

									<div class="height100 width100" id="ticketChartID" style="float:left"></div>
								</div>

							</div>
							<!--  标题设置 end  -->
						</div>
					</div>

				</div>

				<div class="box-margin10 width75 box-sizing " id="area_right">

					<!-- 图形区域  start -->
					<div class="width100"  style="height:180px;float:left;	">
						
						<div class="height100 width100 relative-position">
							<!--  标题设置 start  -->
							<div class="titleBox_center">
								<div class="titleBox_left">
									<div class="titleBox_right">
										<!-- 图标标题操作 -->
										<div class="box1_icon khywtjicon">
											<div class="box_title">资源占比</div>
											
											<div style="float:right;margin-right: 5px;">
											
												<div class="swiper_wrap" >
												
													<ul class="font_inner" style="height: 120px; top: 0px;margin-top: -2px;">
														<li>
															<a href="###">机房【YD_SC_CD-XY-28】资源占用率过高，请及时处理!</a>
														</li>
														<li>
															<a href="###">机房【YD_SC_CD-XY-54】资源占用率过高请及时处理!</a>
														</li>

													</ul>
													<a href="javascript:void(0)" class="lt">&lt;</a>
													<a href="javascript:void(0)" class="gt">&gt;</a>
												</div>
												
												
											</div>
											
											<div class="warn_tip" title="提示信息" style="float:right;"></div>
											
										</div> 
										<div class="clear"></div>
									</div>
								</div>
							</div>
							<!--  下方内容 -->
							<div class="box1_content quick_content" style="overflow:hidden;">
								<div class="width100 height100">
									<div class="height100 width25 box-margin10" id="chart5" style="float:left"></div>
									<div class="height100 width25 box-margin10" id="chart6" style="float:left"></div>
									<div class="height100 width25 box-margin10" id="chart7" style="float:left"></div>
									<div class="height100 width25 box-margin10" id="chart8" style="float:left"></div>
								</div>
								
			
								
									
							</div>
							<!--  标题设置 end  -->
						</div>
						
					</div>
					<!-- 图形区域  end --> 
					
					
					<!-- 图形区域  start -->
					<div class="width100 box-sizing"  style="height:220px;float:left;border-top:10px solid transparent;	">
						
						<div class="height100 width100 relative-position">
							<!--  标题设置 start  -->
							<div class="titleBox_center">
								<div class="titleBox_left">
									<div class="titleBox_right">
										<!-- 图标标题操作 -->
										<div class="box1_icon khywtjicon">
											<div class="box_title">客户统计</div>
										</div>
										<div class="clear"></div>
									</div>
								</div>
							</div>
							<!--  下方内容 -->
							<div class="box1_content quick_content" style="overflow:hidden;">
								<div class="height100 width30 box-sizing right_border" style="float:left;" id="customerChartID" ></div>
								<div class="height100 width70 " style="float:left" id="customerGrowthID" ></div>
							</div>
							<!--  标题设置 end  -->
						</div>
						
					</div>
					<!-- 图形区域  end --> 
	 
				
					<!-- 图形区域  start -->
					<div class="width100"  style="height:220px;float:left;border-top:10px solid transparent;	">
						
						<div class="height100 width50 relative-position  box-sizing" style="float:left;border-right:5px solid transparent;">
							<!--  标题设置 start  -->
							<div class="titleBox_center">
								<div class="titleBox_left">
									<div class="titleBox_right">
										<!-- 图标标题操作 -->
										<div class="box1_icon khywtjicon">
											<div class="box_title">用电量统计</div>
											<div class="box1_status">
												<!--
												<a href="javascript:alert('链接到其他界面')" target="_self">更多&gt;&gt;</a>
												-->
											</div>
										</div> 
										<div class="clear"></div>
									</div>
								</div>
							</div>
							<!--  下方内容 -->
							<div class="box1_content quick_content" style="overflow:hidden;">
								<div style="position:absolute;z-index:19890990">
									<input id="switch-sitemonthdaytop" type="checkbox" data-size="mini" class="switchselect" checked data-on-text="日能耗" data-off-text="月能耗" data-off-color="primary" >
								</div>
								<div class="height100 width100 " id="chart9" ></div>
								
									
							</div>
							<!--  标题设置 end  -->
						</div>
						
						
						
						<div class="height100 width50 relative-position box-sizing" style="float:left;border-left:5px solid transparent;">
							<!--  标题设置 start  -->
							<div class="titleBox_center">
								<div class="titleBox_left">
									<div class="titleBox_right">
										<!-- 图标标题操作 -->
										<div class="box1_icon khywtjicon">
											<div class="box_title">IDC成本报告</div>
											<div class="box1_status">
												<!--
												<a href="javascript:alert('链接到其他界面')" target="_self">更多&gt;&gt;</a>
												-->
											</div>
										</div> 
										<div class="clear"></div>
									</div>
								</div>
							</div>
							<!--  下方内容 -->
							<div class="box1_content quick_content" style="overflow:hidden;">
							
								<div class="width60 height100 box-sizing" style="float:left;border-top: 13px solid transparent;border-right: 5px solid transparent;">
									<div style="text-align:center;height:22px;font-size: 12px;color: #666;font-weight: bold;">
										<div class="width333" style="float:left;">能源0W</div>
										<div class="width333" style="float:left">人工0W</div>
										<div class="width333" style="float:left">网络0W</div>
									</div>
									<div class="title_split"></div>
									<div style="height:22px;">
										<div class="width50" style="float:left;text-align: center;">房屋成本</div>
										<div class="width50" style="float:left;text-align: center;">设备成本</div>
									</div>
									
									<div class="title_desc2" style="margin-top:5px;">
										<div class="width50 box-sizing right_border" style="float:left;">
											<div>
												<h6 class="width60" style="text-align:right">折旧摊销：</h6><h6 class="width40">0W</h6>
											</div>
											<div>
												<h6 class="width60" style="text-align:right">维修费：</h6><h6 class="width40">0W</h6>
											</div>
											<div>
												<h6 class="width60" style="text-align:right">装修摊销：</h6><h6 class="width40">0W</h6>
											</div>
											<div>
												<h6 class="width60" style="text-align:right">其他房屋：</h6><h6 class="width40">0W</h6>
											</div>
										</div>
										
										
										<div class="width50 box-sizing" style="float:left;">
											<div>
												<h6 class="width60" style="text-align:right">折旧摊销：</h6><h6 class="width40">0W</h6>
											</div>
											<div>
												<h6 class="width60" style="text-align:right">维修费：</h6><h6 class="width40">0W</h6>
											</div>
										</div>
										
									</div>
								
								</div>

								
								<div class="width40 height100 box-sizing left_border" style="float:left;">
									<div class="width100 height100" id="chart10"></div>
								</div>
								
						
								
									
							</div>
							<!--  标题设置 end  -->
						</div>
						
					</div>
					<!-- 图形区域  end --> 
						
						
					</div>
					<!-- 图形区域  end --> 
				
					
					
					
	
				</div>
			
				
				
				
				
            </div>
            <!-- 1DIV end -->
            
        </div>
        
		
		
    </div>
	
<script>
	var PortFlowInterval=5000;//10秒
	
	$(function(){

		 // 基于准备好的dom，初始化echarts实例
        var ticketChart = echarts.init(document.getElementById('ticketChartID'),'custom'),  //工单的echart图
			customerChart = echarts.init(document.getElementById('customerChartID'),'custom'),  //有域名客户和无域名客户占比
            customerGrowth = echarts.init(document.getElementById('customerGrowthID'),'custom'),  //客户增长情况
			mychart5 = echarts.init(document.getElementById('chart5'),'custom'),
			mychart6 = echarts.init(document.getElementById('chart6'),'custom'),
			mychart7 = echarts.init(document.getElementById('chart7'),'custom'),
			mychart8 = echarts.init(document.getElementById('chart8'),'custom'),
			mychart9 = echarts.init(document.getElementById('chart9'),'custom'),
			mychart10 = echarts.init(document.getElementById('chart10'),'custom');

		//加载工单的echart图
        $.post(contextPath+"/actJBPMController/buildTicketChart",function(data){
            ticketChart.setOption(buildTicketChart(data));
        })

        //加载有域名客户和无域名客户占比echart
        buildCusPercentOptAPI(customerChart);

		//客户增长情况
        $.getJSON(contextPath+"/main_manager/getuseraddtrend",function(data){
            //customerGrowth.setOption(buildCusGrowChart(data))
            customerGrowth.setOption(buildCusGrowChart(data))
        })

		/*资源占比*/
        $.getJSON(contextPath+"/main_manager/getIdcLocationResourceRatio",function(data){
            if(data){
                mychart5.setOption(buildResPercentOpt("机架占用率",data.USEDRACKNUM,data.RACKCOUNT-data.USEDRACKNUM));
                mychart6.setOption(buildResPercentOpt("IP占用率",data.USEDIPNUM,data.IPCOUNT-data.USEDIPNUM));
                mychart7.setOption(buildResPercentOpt("核心端口占用率",data.USEDHPORTNUM,data.HPORTCOUNT-data.USEDHPORTNUM));
                mychart8.setOption(buildResPercentOpt("出口带宽利用率",data.USEDBANDWIDTHNUM,data.BANDWIDTHCOUNT-data.USEDBANDWIDTHNUM));
            }
        })

        //加载有域名客户和无域名客户占比echart数据
        function buildCusPercentOptAPI(customerChart){
            var options = buildCusPercentOpt();
            customerChart.setOption(options);
            $.getJSON(contextPath+"/main_manager/havdomainuser", function (data){
                var datas = [];
                var legent = [];
                for(var k in data){
                    legent.push(k);
                    datas.push({
                        value: data[k],
                        name: k
                    })
                }
                options.series[0].data = datas;
                options.legend.data = legent;
                customerChart.setOption(options);
            });
        }

        //机房日/月用电量
        var topsize=7;
		var sitepowertype=0;
        mychart9.setOption(buildSiteTopChart(topsize,'daydata','机房日能耗TOP'+topsize,'用电量','kWh'));
        $.getJSON(contextPath+"/main_manager/pueTop",function(result){
            var datas = result.datas;
            var sitenames = result.sitenames;
            var option = mychart9.getOption();
            option.xAxis[0].data=sitenames;
            option.series[0].data=datas;
            mychart9.setOption(option);
        })
        // $.getJSON(contextPath+"/main_manager/pueTop",function(result){
        //     if(result!=null){
        //         var datas = result.datas;
        //         var sitenames = result.sitenames;
        //         mychart9.setOption(buildSiteTopChart1('机房日能耗TOP'+topsize,topsize,'daydata','用电量','kWh',datas,sitenames));
			// }
        // })
		$('#switch-sitemonthdaytop').on('switchChange.bootstrapSwitch',function(event, state) {
			mychart9.clear();
		   if(state){
		   	  sitepowertype=0;
			  mychart9.setOption(buildSiteTopChart(topsize,'daydata','机房日能耗TOP'+topsize,'用电量','kWh'));		
		   }else{
			  mychart9.setOption(buildSiteTopChart(topsize,'monthdata','机房月能耗TOP'+topsize,'用电量','kWh'));		
			  sitepowertype=1;
		   }
			$.getJSON(contextPath+"/main_manager/pueTop",function(result){
				var datas = result.datas;
				var sitenames = result.sitenames;
				var option = mychart9.getOption();
				option.xAxis[0].data=sitenames;
				option.series[0].data=datas;
				mychart9.setOption(option);
			})
		});
		
		mychart9.on("click", function(param){
			var seriesIndex=param["seriesIndex"];
			var seriesName=param["seriesName"];
			var seriesType=param["seriesType"];
			var name=param["name"];
			var data=param["data"];
			top.layer.open({
				type: 2,
				title: "【"+name+"】"+(sitepowertype!=0?"当月":"当日")+"能耗趋势图",
				area: ['1000px', '500px'],
				content: 'main_manager_sitepowerchart.do?sitepowertype='+sitepowertype,
				end: function(){
				  
				}
			});
		});
		mychart10.setOption(buildCostPercentOpt());
	});
	
	
	//构建机房假数据
	function buildSiteDatas(size){
		var result=[];
		for(var i = 0 ; i < size ; i++ ){
			result.push({daydata:randoms(150,500),sitename:'YD_SC_CD_XY-'+(i+1)+'机房',monthdata:randoms(4500,15000)});
		}
		return result;
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

	
	//成本占用率
	function buildCostPercentOpt(){
		
		var datas=[
					{value:0, name:'能源'},
					{value:0, name:'人工'},
					{value:0, name:'网络'}
				 ];
		var legends=['能源','人工','网络'];
		
		
		var option = {
			title : {
				text: "成本占比",
				textStyle:{
					color:'#6E6A6F',
					fontSize:14
				},
				x:'center',
				y:'top'
			},
            textStyle:{
                color:'black'
            },
//            color: [
//                "#B8D2C7",
//                "#F5E8C8",
//                "#C6B38E",
//                "#E7DAC9",
//                "#001852",
//                "#8B5F65",
//                "#a4d8c2",
//                "#f3d999",
//                "#d3758f",
//                "#dcc392",
//                "#2e4783",
//                "#82b6e9",
//                "#eaf889",
//                "#6699FF",
//                "#d5b158",
//                "#38b6b6"],
			tooltip : {
				trigger: 'item',
				confine:true ,
				formatter: "{a} <br/>{b} : {c} ({d}%)"
			},
			legend: {
				data: legends,
				orient:"horizontal",
				y: 'bottom'
				
			},
			series : [
				{
					name: '成本',
					type: 'pie',
					selectedMode: 'single',
					radius: ['10%', '55%'],
					center: ['50%', '50%'],
					//roseType : 'area',
					data:datas,
					label: {
						normal: {
							position: 'inner',
							formatter:"{c}"
						}
					},
					 labelLine: {
						normal: {
							show: false
						}
					},
					itemStyle: {
						normal:{
//							color:function(it){
//								var color="#057300";
//								var seriesIndex=it.seriesIndex;
//								var dataIndex=it.dataIndex;
//								if(dataIndex==0){
//									color="#c23531";
//								}else if(dataIndex==1){
//									color="#057300";
//								}else if(dataIndex==2){
//									color="#3276B1";
//								}
//								return color;
//							},
//							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						},
						emphasis: {
							borderWidth:2,
                   			borderColor:'#CCC'
							
						}
					}
				}
			]
		};

		return option;
		
	}
	
	
	
	//有域名客户和无域名客户
	function buildCusPercentOpt(){
		var datas=[
					{value:421, name:'有域名客户'},
					{value:1532, name:'其他客户数'}
				 ];
		var legends=['有域名客户','其他客户数'];
		var option = {
			title : {
				text: "客户占比",
				textStyle:{
//					color:'#6E6A6F',
					fontSize:14
				},
				x:'center',
				y:'top'
			},
            textStyle:{
                color:'black'
            },
			tooltip : {
				trigger: 'item',
				confine:true ,
				formatter: "{a} <br/>{b} : {c} ({d}%)"
			},
			legend: {
				data: legends,
				orient:"horizontal",
				y: 'bottom'
			},
			series : [
				{
					name: '数量',
					type: 'pie',
					selectedMode: 'single',
					radius: ['10%', '55%'],
					center: ['50%', '50%'],
					//roseType : 'area',
					data:datas,
					label: {
						normal: {
							position: 'inner',
							formatter:"{c}"
						}
					},
					 labelLine: {
						normal: {
							show: false
						}
					},
					itemStyle: {
						normal:{
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						},
						emphasis: {
							borderWidth:2,
                   			borderColor:'#CCC'
						}
					}
				}
			]
		};
		return option;
	}
	
	
	//构建增长统计数据
	function buildCusGrowChart(models){

		//入网客户
		var indatas=getValsFromItemsBykey(models,"insum");
        //退网客户
		var outdatas=getValsFromItemsBykey(models,"outsum");
		//当前客户
		var cusdatas=getValsFromItemsBykey(models,"cussum");
		//月份
		var months=getValsFromItemsBykey(models,'month');

		var option = {
			title : {
				text: "工单开通情况",
				textStyle:{
					color:'#6E6A6F',
					fontSize:14
				},
				x:'center',
				y:'top'
			},
			tooltip : {
				trigger: 'axis',
				confine:true 
			},
			legend: {
				data:["当前在服工单","新开通工单","下线工单"],
				orient:"horizontal",
				y: 'bottom'
			},
			toolbox: {
				show : false,
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
//					color:'#c23531',
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				},
				emphasis: {
					borderWidth:2,
					//borderColor:'#c23531'
					
				}
			},
			xAxis : [
				{
					type : 'category',
					data : months,
					axisLabel:{
						rotate:15,
						interval:0
					},
					text:'123'
				}
			],
			yAxis : [
				{
					type : 'value',
                	name: '当前在服工单'
				},
				{
					type : 'value',
                	name: '下线工单'
				}
			],
			series : [
				{
					name:"当前在服工单",
					type:'bar',
					barWidth:top.Echart_barWidth,
					smooth: true,
					label: {
						normal: {
							show: true,
							position: 'top',
							formatter: "{c}"//"{c}("+unitName+")"
						}
					},itemStyle:{
						normal:{
							color:"#44B7D3"
						}
					},
					data:cusdatas
				},{
					name:"新开通工单",
					type:'bar',
					smooth: true,
					yAxisIndex: 1,
					label: {
						normal: {
							show: true,
							position: 'top',
							formatter: "{c}"//"{c}("+unitName+")"
						}
					},
					itemStyle:{
						normal:{
							//color:"#C23531"
						}
					},
					data:indatas
				},{
					name:"下线工单",
					type:'bar',
					smooth: true,
					yAxisIndex: 1,
					label: {
						normal: {
							show: true,
							position: 'top',
							formatter: "{c}"//"{c}("+unitName+")"
						}
					},
					itemStyle:{
						normal:{
							//color:"#057300"
						}
					},
					data:outdatas
				}
			]
		};
		return option;
	}
	
	
	//构建机房统计数据
    // function buildSiteTopChart1(title,size,key,legendTitle,unitName,datas,sitenames){
     //    var option = {
     //        title : {
     //            text: title,
     //            textStyle:{
     //                color:'#6E6A6F',
     //                fontSize:14
     //            },
     //            x:'center',
     //            y:'top'
     //        },
     //        tooltip : {
     //            trigger: 'axis',
     //            confine:true ,
     //            formatter: "机房 : {b} <br/>{a} : {c}("+unitName+")"
     //        },
     //        legend: {
     //            data:[legendTitle],
     //            orient:"horizontal",
     //            y: 'bottom'
     //        },
     //        calculable : true,
     //        grid: {
     //            tooltip: {
     //                trigger: 'axis',
     //                axisPointer: {
     //                    type: 'shadow',
     //                    label: {
     //                        show: true,
     //                        formatter: function (params) {
     //                            return params.value.replace('\n', '');
     //                        }
     //                    }
     //                }
     //            }
     //        },
     //        itemStyle: {
     //            normal:{
     //                color:'#2b821d',
     //                shadowOffsetX: 0,
     //                shadowColor: 'rgba(0, 0, 0, 0.5)'
     //            },
     //            emphasis: {
     //                borderWidth:2
     //            }
     //        },
     //        xAxis : [
     //            {
     //                type : 'category',
     //                data : sitenames,
     //                axisLabel:{
     //                    rotate:15,
     //                    interval:0
     //                },
     //                text:'123'
     //            }
     //        ],
     //        yAxis : [
     //            {
     //                type : 'value',
     //                name: legendTitle+'（'+unitName+'）'
     //            }
     //        ],
     //        series : [
     //            {
     //                name:legendTitle,
     //                type:'bar',
     //                barWidth:top.Echart_barWidth,
     //                smooth: true,
     //                label: {
     //                    normal: {
     //                        show: true,
     //                        position: 'top',
     //                        formatter: "{c}"
     //                    }
     //                },
     //                data:datas
     //            }
     //        ]
     //    };
     //    return option;
	// }
	function buildSiteTopChart(size,key,title,legendTitle,unitName){
		var models=buildSiteDatas(size).sort(function(a,b){return a[key]<b[key]});
		var datas=getValsFromItemsBykey(models,key);
		var sitenames=getValsFromItemsBykey(models,'sitename');

		for(var i = 0 ; i < sitenames.length ; i++ ){
			if((i+1)%2==0){
				//sitenames[i]='\n'+sitenames[i];
			}
		}
		var option = {
			title : {
				text: title,
				textStyle:{
					color:'#6E6A6F',
					fontSize:14
				},
				x:'center',
				y:'top'
			},
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
			},
			itemStyle: {
				normal:{
					color:'#2b821d',
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				},
				emphasis: {
					borderWidth:2
				}
			},
			xAxis : [
				{
					type : 'category',
					data : sitenames,
					axisLabel:{
						rotate:15,
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
					barWidth:top.Echart_barWidth,
					smooth: true,
					label: {
						normal: {
							show: true,
							position: 'top',
							formatter: "{c}"
						}
					},
					data:datas
				}
			]
		};
		return option;
	}
	//资源占用率
	function buildResPercentOpt(title,used,notUsed){
		var datas=[
					{value:used, name:'占用'},
					{value:notUsed, name:'闲置'}
				 ];
		var resourceTotal = used + notUsed;
		var option = {
			title : {
				text: title,
				textStyle:{
					fontSize:14
				},
				x:'center',
				y:'top'
			},
			tooltip : {
				trigger: 'item',
				confine:true ,
				formatter: "{b} : {d}% ({c}/"+resourceTotal+")" //"{b} : {c} ({d}%)"
			},
			legend: {
				data: ['占用','闲置'],
				orient:"horizontal",
				y: 'bottom'
				
			},
            textStyle:{
                color:'black'
            },
			series : [
				{
					name: '数量',
					type: 'pie',
					selectedMode: 'single',
					radius: ['10%', '55%'],
					center: ['50%', '50%'],
					data:datas,
					label: {
						normal: {
							position: 'inner',
							formatter:"{d}"
						}
					},
					 labelLine: {
						normal: {
							show: false
						}
					},
					itemStyle: {
						normal:{
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						},
						emphasis: {
						}
					}
				}
			]
		};
		return option;
	}
</script>
<script>
	var gridids=["waitgrid","waitgrid2","waitgrid3"];


     $(function () {
	 	for(var i=0;i<gridids.length;i++){
			var id=gridids[i];
			var grid=document.getElementById(id);
			if(grid){
				initGrid(id,i);
				//pages(id);
			}
		    
		}
		 
		// Dock initialize
		$('#dock').Fisheye(
			{
				maxWidth: 40,
				items: 'a',
				itemsText: 'span',
				container: '.dock-container',
				itemWidth: 60,
				proximity: 180,
				alignment : 'left',
				valign: 'bottom',
				halign : 'center'
			}
		);
		 
	 });
	
	function initGrid(id,idx){
		$("#"+id).datagrid({
			 singleSelect:true,
	 		 emptyMsg: '<span>无记录</span>',
			 loadMsg: '正在加载中，请稍等... ',
			 nowrap: false,
			 striped: true,
			 //rownumbers:true,
			 //pagination:true,
			 fitColumns:true,
			 rowStyler: function(index,row){
				return "";
			},
			 showRefresh:true,
			 //toolbar:"#toolbar",
			 pageSize:5,
			 pageList:[5,10,15,20,30],
			 fit:true,
			 displayMsg:"总条数{total}，显示{from}到{to}条",
			 loadFilter:function(data){
			 	return {total : data.total,rows : data.rows}
			 },
			 onLoadSuccess:myLoadsuccess,
			 columns:initColumns(idx),
             url:getTicketUrl(idx)
		     //data:initData(idx)

		 });
	}
	
	function initColumns(idx){
		var result=[];
		var columns=[];
		if(idx==0){
			//{field:'ck',checkbox:true},
			columns.push({title:'工单名称',field:'ordername',width:150,align:'center'});
//			columns.push({title:'工单类型',field:'ordertype',width:80,align:'center'});
			columns.push({title:'创建时间',field:'createtime',width:150,align:'center'});
			
		}else if(idx==1){
			//{field:'ck',checkbox:true},
			columns.push({title:'告警信息',field:'ordername',width:100,align:'center'});
			columns.push({title:'告警类型',field:'ordertype',width:100,align:'center'});
			columns.push({title:'告警时间',field:'createtime',width:150,align:'center'});
			
		}else{
			result=initColumns(0);
		}
		if(columns.length!=0)result.push(columns);
		return result;
	}
	function getTicketUrl(idx){
        var result=contextPath+"/main_manager/getwaitticket";
//        if(idx ==1){
//
//        }
        return result;
    }
	function initData(idx){
		var result={};
		if(idx==0){
			result={gridid:gridids[idx],"total":10,"rows":[
				{"ordername":"FI-SW-01","ordertype":"Koi","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"K9-DL-01","ordertype":"Dalmation","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"RP-SN-01","ordertype":"Rattlesnake","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"RP-SN-01","ordertype":"Rattlesnake","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"RP-LI-02","ordertype":"Iguana","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"FL-DSH-01","ordertype":"Manx","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"FL-DSH-01","ordertype":"Manx","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"FL-DLH-02","ordertype":"Persian","createtime":'2017-04-11 11:22:42',"handler":"P",},
				{"ordername":"FL-DLH-02","ordertype":"Persian","createtime":'2017-04-11 11:22:42',"handler":"P"}
			]};
		}else if(idx==1){
			result={gridid:gridids[idx],"total":10,"rows":[
				{"ordername":"FI-SW-01","ordertype":"Koi","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"K9-DL-01","ordertype":"Dalmation","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"RP-SN-01","ordertype":"Rattlesnake","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"RP-SN-01","ordertype":"Rattlesnake","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"RP-LI-02","ordertype":"Iguana","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"FL-DSH-01","ordertype":"Manx","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"FL-DSH-01","ordertype":"Manx","createtime":'2017-04-11 11:22:42',"handler":"P"},
				{"ordername":"FL-DLH-02","ordertype":"Persian","createtime":'2017-04-11 11:22:42',"handler":"P",},
				{"ordername":"FL-DLH-02","ordertype":"Persian","createtime":'2017-04-11 11:22:42',"handler":"P"}
			]};
		}else{
			result=initData(0);
		}
		return result;
	}
	
	function pages(id){
		var pager=$('#'+id).datagrid('getPager'); 
		pager.pagination({    
			//showPageList:false,  
			layout : ['list','sep','first','prev','manual','next','last','refresh'], 
			beforePageText:"第", 
			afterPageText:"共{pages}",
			displayMsg:"总条数{total}，显示{from}到{to}条"
		});    
	}
	
	function myLoadsuccess(data) {
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $('#'+data['gridid']).datagrid('fixRowHeight')
    }

	function buildTicketChart(data) {
	    //政企业务五星评分
        var governmentTicket;
        //自有业务五星评分
        var myselfTicket;
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
                            governmentTicket = data[i];
                            delete governmentTicket.PROD_CATEGORY;//删除无用的属性
                            delete governmentTicket.SATISFACTION;//删除无用的属性
                        }
                    }else if(data[i].PROD_CATEGORY == 0){
                        if(data[i].SATISFACTION == 'five_star'){
                            myselfTicket = data[i];
                            delete myselfTicket.PROD_CATEGORY;//删除无用的属性
                            delete myselfTicket.SATISFACTION;//删除无用的属性
                            delete myselfTicket.临时测试流程;//删除无用的属性
                        }
                    }
                }
            },
            error: function(){
            }
        });
		//水印
        var waterMarkText = 'ECHARTS';

        var canvas = document.createElement('canvas');

        var waterMarkText = '四川众力佳华';

        var canvas = document.createElement('canvas');
        var ctx = canvas.getContext('2d');
        canvas.width = canvas.height = 100;
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.globalAlpha = 0.1;
        ctx.font = '20px Microsoft Yahei';
        ctx.translate(50, 50);
        ctx.rotate(-Math.PI / 4);
        ctx.fillText(waterMarkText, 0, 0);

       var option = {
           backgroundColor: {
               type: 'pattern',
               image: canvas,
               repeat: 'repeat'
           },
           tooltip: {},
           title: [ {
               text: '政企业务五星好评',
               subtext: '总计 ' + Object.keys(governmentTicket).reduce(function (all, key) {
                   return all + governmentTicket[key];
               }, 0),
               x: '50%',
               textAlign: 'center'
           }, {
               text: '自有业务五星好评',
               subtext: '总计 ' + Object.keys(myselfTicket).reduce(function (all, key) {
                   return all + myselfTicket[key];
               }, 0),
               x: '50%',
               y: '50%',
               textAlign: 'center'
           }],
           grid: [{
               top: 50,
               width: '50%',
               bottom: '45%',
               left: 10,
               containLabel: true
           }, {
               top: '50%',
               width: '50%',
               bottom: 0,
               left: 10,
               containLabel: true
           }],
           series: [{
               type: 'pie',
               radius: [0, '47%'],
               center: ['50%', '25%'],
               data: Object.keys(governmentTicket).map(function (key) {
                   return {
                       name: key.replace('.js', ''),
                       value: governmentTicket[key]
                   }
               })
           }, {
               type: 'pie',
               radius: [0, '47%'],
               center: ['50%', '75%'],
               data: Object.keys(myselfTicket).map(function (key) {
                   return {
                       name: key.replace('.js', ''),
                       value: myselfTicket[key]
                   }
               })
           }]
       };
        return option;
    }

	 
</script>
	
	
	
</body>
</html>