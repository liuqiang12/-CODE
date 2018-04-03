<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
<link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/import_skin.css" rel="stylesheet" type="text/css" id="skin" themeColor="${skin}"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_page.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/swipers.css" rel="stylesheet" type="text/css"/>

<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/jquery.jqDock.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_cus_manager/dock/js/fisheye-iutil.min.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/js/echart/echarts.min.js"></script>

<script src="<%=request.getContextPath() %>/framework/themes/js/swipers.js"></script>


<!--框架必需end-->
<body id="scrollContent" >
	 <!-- 界面布局情况 -->
    <div class="desklayout height100">
    	<!-- 分三个div并排  -->
        <div class="deskLayout001 height100">
        	<!-- 1DIV start -->
            <div class="float_left col width100 height100" style="background-color:#E9ECF3;overflow-y: auto;">

				<div class="box-margin10  width40" id="waittodo" style="height:100%">
				
						<!-- 快捷区域  start -->
					<div class="height50 width100 box-sizing" style="border-bottom:5px solid transparent;">
						
						<div class="height100 width100 relative-position">
							<!--  标题设置 start  -->
							<div class="titleBox_center">
								<div class="titleBox_left">
									<div class="titleBox_right">
										<!-- 图标标题操作 -->
										<div class="box1_icon dbicon">
											<div class="box_title">查看待处理工单</div>
											<div class="box1_status">
												<a href="<%=request.getContextPath() %>/actJBPMController/ticketTaskGridQueryWithGroupId.do?prodCategory=1&isCustomerView=yes" target="_self">更多&gt;&gt;</a>

												<input id="processDefinitonKey" value="${processDefinitonKey}" type="hidden"/>
												<input id="prodCategory" value="0" type="hidden"/>
												<input id="loginUserID" value="${loginUserID}" type="hidden"/>
												<input id="category" value="${category}" type="hidden"/>
												
											</div>
										</div> 
										<div class="clear"></div>
									</div>
								</div>
							</div>
							<!--  下方内容 -->
							<div class="box1_content quick_content" >
							   <!-- table界面:该table自定义  start -->
								<%--<table class="easyui-datagrid "   id="waitgrid"   data-options="rownumbers:false">--%>
								<table class="easyui-datagrid" id="waitgrid" data-options="singleSelect:true,nowrap: true,striped: true,rownumbers:true,pagination:true,pageSize:15,pageList:[10,15],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;},fitColumns:true"></table>
								</table>
							   <!-- table界面:该table自定义  end -->

							</div>
							<!--  标题设置 end  -->
						</div>
						
					</div>
					<!-- 快捷区域  end --> 
				
				
					<!-- 快捷区域  start -->
					<div class="height50 width100 box-sizing" style="border-top:5px solid transparent;">
						
						<div class="height100 width100 relative-position">
							<!--  标题设置 start  -->
							<div class="titleBox_center">
								<div class="titleBox_left">
									<div class="titleBox_right">
										<!-- 图标标题操作 -->
										<div class="box1_icon dbicon">
											<div class="box_title">查看发起的工单</div>
											<div class="box1_status">
												
												<a href="<%=request.getContextPath() %>/actJBPMController/jbpmManagerViewMore.do" target="_self">更多&gt;&gt;</a>
												
											</div>
										</div> 
										<div class="clear"></div>
									</div>
								</div>
							</div>
							<!--  下方内容 -->
							<div class="box1_content quick_content">
							   
									 
							   <!-- table界面:该table自定义  start -->
									<%--<table class="easyui-datagrid "   id="waitgrid2"   data-options="rownumbers:false">--%>
                                    <table class="easyui-datagrid" id="waitgrid2" data-options="singleSelect:true,nowrap: true,striped: true,
                                         rownumbers:true,pagination:true,pageSize:15,pageList:[10,15],fit:true,
                                         fitColumns:true,toolbar:'#requestParamSettins_taskQuery',loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;} ">
                                    </table>

                                    </table>
								   <!-- table界面:该table自定义  end -->

							</div>
							<!--  标题设置 end  -->
						</div>
						
					</div>
					<!-- 快捷区域  end --> 

				</div>

				<!-- 快捷区域  start -->
            	<div class="box-margin10 width60" id="quickarea" style="height:50%;">
                	
                	<div class="height100 width100 relative-position">
                    	<!--  标题设置 start  -->
                        <div class="titleBox_center">
                            <div class="titleBox_left">
                                <%--<div class="titleBox_right">
                                	<!-- 图标标题操作 -->
                                	<div class="box1_icon dbicon"  >
                                    	<div class="box_title">快捷操作区域</div>

                                    	<div style="float:right;margin-right: 5px;">
											
											<div class="swiper_wrap" >

												<ul class="font_inner" style="height: 120px; top: 0px;margin-top:0px;">

													<c:forEach items="${idcRunTickets}" var="item" >
														<li>
															<a href="javascript:void(0)"; onclick="jbpmApply(${item.ticketInstId},${item.prodInstId},${item.prodInstId},${item.prodInstId})">工单:${item.serialNumber}待处理！</a>
														</li>
													</c:forEach>

												</ul>
											</div>
										</div>
										
										<div class="warn_tip" title="提示信息" style="float:right;"></div>
										
                                    </div> 
                                    <div class="clear"></div>
                                </div>--%>
                            </div>
                        </div>
                        <!--  下方内容 -->
                        <div class="box1_content quick_content">

						  <div class="height50 width333 box-sizing title_box">
						   		<%--<div class="title_container box-sizing" id="title_content_1" onclick="window.location.href='https://www.baidu.com/'">--%>
						   		<div class="title_container box-sizing" id="title_content_1"
									 onclick="singleCreateOrUpdateFromROOTMainView('100','main_cus_manager')">
						   		<%--<div class="title_container box-sizing" id="title_content_1" onclick="window.location.href='https://www.baidu.com/'">--%>
									<h5>政企业务预勘察发起</h5>
									<div class="title_split"></div>
									<div class="title_desc2">
										<h6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开通前一定要先预勘察哦，确保我们的资源可满足客户要求!</h6>
									</div>
								
								</div>
						   </div>
						   
						   <div class="height50 width333 box-sizing title_box">
						   		<div class="title_container box-sizing" id="title_content_2" onclick="location='<%=request.getContextPath() %>/actJBPMController/ticketAllTaskGridQuery.do?prodCategory=0&isCustomerView=yes'">
									<h5>开通发起</h5>
									<div class="title_split"></div>
									<div class="title_desc2">
										<h6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确定已经预勘察过，找到预勘单才可以开通，开通前确定预勘的资源正确的!</h6>
									</div>
								
								</div>
						   </div>
						   
						   <div class="height50 width333 box-sizing title_box">
						   		<div class="title_container box-sizing" id="title_content_3" onclick="location='<%=request.getContextPath() %>/actJBPMController/ticketAllTaskGridQuery.do?prodCategory=1'">
									<h5>历史单查询</h5>
									<div class="title_split"></div>
									<div class="title_desc2">
										<h6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看与自己相关的历史工单,可以根据各种条件筛选查询!</h6>
									</div>
								
								</div>
						   </div>
						   
						   <div class="height50 width333 box-sizing title_box">
						   		<div class="title_container box-sizing" id="title_content_4" onclick="location='<%=request.getContextPath() %>/customerController/customerMngQuery.do'">
									<h5>客户信息维护</h5>
									<div class="title_split"></div>
									<div class="title_desc2">
										<h6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;维护自己开通的客户信息,包括对客户信息新增、修改和删除！</h6>
									</div>
								
								</div>
						   </div>
						   
						   <div class="height50 width333 box-sizing title_box">
						   		<div class="title_container box-sizing" id="title_content_5" onclick="location='<%=request.getContextPath() %>/contractController/contractQuery.do'">
									<h5>合同查询</h5>
									<div class="title_split"></div>
									<div class="title_desc2">
										<h6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查看签署的合同信息,可根据不同条件进行筛选!</h6>

									</div>
								
									
								</div>
						   </div>

                        </div>
                        <!--  标题设置 end  -->
                    </div>
                    
                </div>
				<!-- 快捷区域  end --> 

				<!-- 图形区域  start -->
            	<div class="box-margin10 width60" id="chartarea" style="height:50%;">
                	
                	<div class="height100 width100 relative-position">
                    	<!--  标题设置 start  -->
                        <div class="titleBox_center">
                            <div class="titleBox_left">
                                <div class="titleBox_right">
                                	<!-- 图标标题操作 -->
                                	<div class="box1_icon khywtjicon">
                                    	<div class="box_title">统计图</div>
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
                        <div class="box1_content quick_content">
                            
						
							<div class="height100 width50 box-margin10" id="chart1" style="float:left;">
							</div>
						    <div class="height100 width50 box-margin10" id="chart2" style="float:left">

							</div>
                             	
                        </div>
                        <!--  标题设置 end  -->
                    </div>
                    
                </div>
				<!-- 图形区域  end --> 

            </div>
            <!-- 1DIV end -->
            
        </div>
        
    </div>
	
<script>

	var pendingTicket=0;   //待处理工单数量
	var foundingTicket=0;   //发起的工单数量

    $(function(){
        $.ajax({
            type: "GET",
            async: false,
            url: contextPath+"/actJBPMController/customerViewData.do",
            data: {"loginUserID":$("#loginUserID").val()},
            dataType: "json",
            success: function(data){
                pendingTicket=data.result.PENDING;
                foundingTicket=data.result.FOUNDING;
			}
        });

		var option = {
			title : {
				text: '待处理工单、发起的工单统计',
				textStyle:{
					color:'#6E6A6F'
				},
				x:'center'
			},
			tooltip : {
				trigger: 'axis'
			},
			legend: {
				data:['数量'],
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
					data : ['待处理工单','发起的工单']
				}
			],
			yAxis : [
				{
					type : 'value'
				}
			],
			series : [
				{
					name:'数量',
					type:'line',
					barWidth:top.Echart_barWidth,
					smooth: true,
					label: {
						normal: {
							show: true,
							position: 'top',
							formatter: "{c}"//"{c}("+unitName+")"
						}
					},
					data:[foundingTicket,pendingTicket]
				}
			]
		};

		 // 基于准备好的dom，初始化echarts实例
        var myChart1=echarts.init(document.getElementById('chart2')),myChart = echarts.init(document.getElementById('chart1'));
		
		 // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);

		var option1 = {
			title : {
				text: '待处理工单、发起的工单占比',
				textStyle:{
					color:'#6E6A6F'
				},
				x:'center'
			},
			tooltip : {
				trigger: 'item',
				formatter: "{a} <br/>{b} : {c} ({d}%)"
			},
			legend: {
				data: ['待处理工单','发起的工单'],
				orient:"horizontal",
				y: 'bottom'
				
			},
			itemStyle: {
				normal:{
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				},
				emphasis: {
					borderWidth:2,
					borderColor:'#CCC'
					
				}
			},
			series : [
				{
					name: '数量占比',
					type: 'pie',
					radius : ['20%','50%'],
					center: ['50%', '50%'],
					data:[
						{value:foundingTicket, name:'待处理工单'},
						{value:pendingTicket, name:'发起的工单'}
					],
					itemStyle: {
						emphasis: {
							shadowBlur: 10,
							shadowOffsetX: 0,
							shadowColor: 'rgba(0, 0, 0, 0.5)'
						}
					}
				}
			]
		};

		 // 使用刚指定的配置项和数据显示图表。
        myChart1.setOption(option1);

	});

</script>	
	
<script>
     $(function () {

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

         //页面一打开就加载 待处理工单 和 发起的工单
	 	loadGridWaitgrid("waitgrid");
	 	loadGridWaitgrid2("waitgrid2");

	 });

	/*=====================================加载 待处理工单 和 发起的工单 start======================================================*/
      function loadGridWaitgrid(gridId){
         var serialNumber = "";
         var processDefinitonKey = "";
         var prodCategory = "";

         /*参数组装*/
         var params = {};
         params['serialNumber'] = serialNumber;
         params['processDefinitonKey'] = processDefinitonKey;
         params['prodCategory'] = prodCategory;
         params['ticketWaitPending'] = 66;

         $("#"+gridId).datagrid({
             url : contextPath + "/actJBPMController/runTicketTaskData.do",
             queryParams: params,
             columns: [[
                 {field: 'busname', title: '业务名称',halign:'left',width:'18%'},
                 {field: 'serialNumber', title: '工单号',halign:'left',width:'29%'},
                 {field: 'customerName', title: '客户名称',halign:'left',width:'29%'},
                 /*{field: 'PRODCATEGORY', title: '工单类型',halign:'left',width:150,formatter: function(value,row,index){
                     if (row.PRODCATEGORY == 1){return '政企业务';}
                     else if(row.PRODCATEGORY == 0) {return '自有业务';}  }},*/
                 {field: 'createTimeStr', title: '创建时间',halign:'left',width:'24%'}
                 /*,{field:"TASK_NAME",title:"状态",halign:'left',width:100}*/
             ]]/*,
             onLoadSuccess:myLoadsuccess,
             toolbar:"#requestParamSettins_taskQuery"*/
         })
     }

    function loadGridWaitgrid2(grid) {
        var serialNumber = "";
        var processDefinitonKey = ""　
        var prodCategory = "";　

		/*参数组装*/
        var params = {};
        params['serialNumber'] = serialNumber;
        params['processDefinitonKey'] = processDefinitonKey;
        params['prodCategory'] = prodCategory;　
        params['ticketWaitPending'] = 66;
        var columns = createColumns2();

        $('#'+grid).datagrid({
            url : contextPath + "/actJBPMController/jbpmManagerView.do",
            /*title: '查看发起的工单...',*/
            /*iconCls:'',*/
            fit:true,
            columns: columns
        });
    }
     //获取列信息
     function createColumns2(){
         //创建列表信息
         var headCols = [];
         headCols.push({field:"BUSNAME",title:"业务名称",halign:'left',width:'18%'});
         headCols.push({field:"SERIAL_NUMBER",title:"工单号",halign:'left',width:'29%'});
         headCols.push({field:"CUSTOMERNAME",title:"客户名称",halign:'left',width:'29%'});
         headCols.push({field:"CREATE_TIME",title:"创建时间",halign:'left',width:'24%'});
         return [headCols]
     }

    /*=====================================加载 待处理工单 end======================================================*/

</script>
 <script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketTaskGridQuery.js"></script>
 <script src="<%=request.getContextPath() %>/js/jbpm/ticket/ticketPreTaskGridQuery.js"></script>
 <script src="<%=request.getContextPath() %>/js/jbpm/contract/contractGridQuery.js"></script>
</body>
</html>