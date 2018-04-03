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



<!--
<link rel="stylesheet" href="<%=request.getContextPath() %>/framework/themes/plugins/bootstrap/css/bootstrap.min.css">
<script src="<%=request.getContextPath() %>/framework/themes/plugins/bootstrap/js/bootstrap.min.js"></script>
-->



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

    (function (root, factory) {
        if (typeof define === 'function' && define.amd) {
            // AMD. Register as an anonymous module.
            define(['exports', 'echarts'], factory);
        } else if (typeof exports === 'object' && typeof exports.nodeName !== 'string') {
            // CommonJS
            factory(exports, require('echarts'));
        } else {
            // Browser globals
            factory({}, root.echarts);
        }
    }(this, function (exports, echarts) {
        var log = function (msg) {
            if (typeof console !== 'undefined') {
                console && console.error && console.error(msg);
            }
        };
        if (!echarts) {
            log('ECharts is not Loaded');
            return;
        }
        echarts.registerTheme('customed', {
            "color": [
                "#B8D2C7",
                "#F5E8C8",
                "#C6B38E",
                "#E7DAC9",
                "#001852",
                "#ca8622",
                "#bda29a",
                "#6e7074",
                "#546570",
                "#c4ccd3"
            ],
            "backgroundColor": "rgba(0, 0, 0, 0)",
            "textStyle": {},
            "title": {
                "textStyle": {
                    "color": "black"
                },
                "subtextStyle": {
                    "color": "#aaa"
                }
            },
            textStyle:{
                color:'black'
            },
            "line": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 1
                    }
                },
                "lineStyle": {
                    "normal": {
                        "width": 2
                    }
                },
                "symbolSize": 4,
                "symbol": "emptyCircle",
                "smooth": false
            },
            "radar": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 1
                    }
                },
                "lineStyle": {
                    "normal": {
                        "width": 2
                    }
                },
                "symbolSize": 4,
                "symbol": "emptyCircle",
                "smooth": false
            },
            "bar": {
                "itemStyle": {
                    "normal": {
                        "barBorderWidth": 0,
                        "barBorderColor": "#ccc"
                    },
                    "emphasis": {
                        "barBorderWidth": 0,
                        "barBorderColor": "#ccc"
                    }
                }
            },
            "pie": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "scatter": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "boxplot": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "parallel": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "sankey": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "funnel": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "gauge": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    },
                    "emphasis": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                }
            },
            "candlestick": {
                "itemStyle": {
                    "normal": {
                        "color": "#c23531",
                        "color0": "#314656",
                        "borderColor": "#c23531",
                        "borderColor0": "#314656",
                        "borderWidth": 1
                    }
                }
            },
            "graph": {
                "itemStyle": {
                    "normal": {
                        "borderWidth": 0,
                        "borderColor": "#ccc"
                    }
                },
                "lineStyle": {
                    "normal": {
                        "width": 1,
                        "color": "#aaa"
                    }
                },
                "symbolSize": 4,
                "symbol": "emptyCircle",
                "smooth": false,
                "color": [
                    "#c23531",
                    "#2f4554",
                    "#61a0a8",
                    "#d48265",
                    "#91c7ae",
                    "#749f83",
                    "#ca8622",
                    "#bda29a",
                    "#6e7074",
                    "#546570",
                    "#c4ccd3"
                ],
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#eee"
                        }
                    }
                }
            },
            "map": {
                "itemStyle": {
                    "normal": {
                        "areaColor": "#eee",
                        "borderColor": "#444",
                        "borderWidth": 0.5
                    },
                    "emphasis": {
                        "areaColor": "rgba(255,215,0,0.8)",
                        "borderColor": "#444",
                        "borderWidth": 1
                    }
                },
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#000"
                        }
                    },
                    "emphasis": {
                        "textStyle": {
                            "color": "rgb(100,0,0)"
                        }
                    }
                }
            },
            "geo": {
                "itemStyle": {
                    "normal": {
                        "areaColor": "#eee",
                        "borderColor": "#444",
                        "borderWidth": 0.5
                    },
                    "emphasis": {
                        "areaColor": "rgba(255,215,0,0.8)",
                        "borderColor": "#444",
                        "borderWidth": 1
                    }
                },
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#000"
                        }
                    },
                    "emphasis": {
                        "textStyle": {
                            "color": "rgb(100,0,0)"
                        }
                    }
                }
            },
            "categoryAxis": {
                "axisLine": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisTick": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisLabel": {
                    "show": true,
                    "textStyle": {
                        "color": "#333"
                    }
                },
                "splitLine": {
                    "show": false,
                    "lineStyle": {
                        "color": [
                            "#ccc"
                        ]
                    }
                },
                "splitArea": {
                    "show": false,
                    "areaStyle": {
                        "color": [
                            "rgba(250,250,250,0.3)",
                            "rgba(200,200,200,0.3)"
                        ]
                    }
                }
            },
            "valueAxis": {
                "axisLine": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisTick": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisLabel": {
                    "show": true,
                    "textStyle": {
                        "color": "#333"
                    }
                },
                "splitLine": {
                    "show": true,
                    "lineStyle": {
                        "color": [
                            "#ccc"
                        ]
                    }
                },
                "splitArea": {
                    "show": false,
                    "areaStyle": {
                        "color": [
                            "rgba(250,250,250,0.3)",
                            "rgba(200,200,200,0.3)"
                        ]
                    }
                }
            },
            "logAxis": {
                "axisLine": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisTick": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisLabel": {
                    "show": true,
                    "textStyle": {
                        "color": "#333"
                    }
                },
                "splitLine": {
                    "show": true,
                    "lineStyle": {
                        "color": [
                            "#ccc"
                        ]
                    }
                },
                "splitArea": {
                    "show": false,
                    "areaStyle": {
                        "color": [
                            "rgba(250,250,250,0.3)",
                            "rgba(200,200,200,0.3)"
                        ]
                    }
                }
            },
            "timeAxis": {
                "axisLine": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisTick": {
                    "show": true,
                    "lineStyle": {
                        "color": "#333"
                    }
                },
                "axisLabel": {
                    "show": true,
                    "textStyle": {
                        "color": "#333"
                    }
                },
                "splitLine": {
                    "show": true,
                    "lineStyle": {
                        "color": [
                            "#ccc"
                        ]
                    }
                },
                "splitArea": {
                    "show": false,
                    "areaStyle": {
                        "color": [
                            "rgba(250,250,250,0.3)",
                            "rgba(200,200,200,0.3)"
                        ]
                    }
                }
            },
            "toolbox": {
                "iconStyle": {
                    "normal": {
                        "borderColor": "#999"
                    },
                    "emphasis": {
                        "borderColor": "#666"
                    }
                }
            },
            "legend": {
                "textStyle": {
                    "color": "#333"
                }
            },
            "tooltip": {
                "axisPointer": {
                    "lineStyle": {
                        "color": "#ccc",
                        "width": 1
                    },
                    "crossStyle": {
                        "color": "#ccc",
                        "width": 1
                    }
                }
            },
            "timeline": {
                "lineStyle": {
                    "color": "#293c55",
                    "width": 1
                },
                "itemStyle": {
                    "normal": {
                        "color": "#293c55",
                        "borderWidth": 1
                    },
                    "emphasis": {
                        "color": "#a9334c"
                    }
                },
                "controlStyle": {
                    "normal": {
                        "color": "#293c55",
                        "borderColor": "#293c55",
                        "borderWidth": 0.5
                    },
                    "emphasis": {
                        "color": "#293c55",
                        "borderColor": "#293c55",
                        "borderWidth": 0.5
                    }
                },
                "checkpointStyle": {
                    "color": "#e43c59",
                    "borderColor": "rgba(194,53,49, 0.5)"
                },
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#293c55"
                        }
                    },
                    "emphasis": {
                        "textStyle": {
                            "color": "#293c55"
                        }
                    }
                }
            },
            "visualMap": {
                "color": [
                    "#bf444c",
                    "#d88273",
                    "#f6efa6"
                ]
            },
            "dataZoom": {
                "backgroundColor": "rgba(47,69,84,0)",
                "dataBackgroundColor": "rgba(47,69,84,0.3)",
                "fillerColor": "rgba(167,183,204,0.4)",
                "handleColor": "#a7b7cc",
                "handleSize": "100%",
                "textStyle": {
                    "color": "#333"
                }
            },
            "markPoint": {
                "label": {
                    "normal": {
                        "textStyle": {
                            "color": "#eee"
                        }
                    },
                    "emphasis": {
                        "textStyle": {
                            "color": "#eee"
                        }
                    }
                }
            }
        });
    }));

</script>
<script>
    var customerId = "${customerId}";
    var cusList = '${cusList}';
    if(cusList!=null&&cusList!=''){
        cusList = JSON.parse(cusList);
    }
    $(function () {
        $('#searchUser').autocompleter({
            // marker for autocomplete matches
            highlightMatches: true,
            // object to local or url to remote search
            source: cusList,//main.js or url  colors
            // custom template
            template: '{{ label }}<span>({{ value2 }})</span>',
            // show hint
            hint: true,
            // abort source if empty field
            empty: false,
            // max results
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
    });
    //查询客户相关信息
    function seacherCusInfo(){
        var customerName = $('#searchUser').val();
        if(customerName==null||customerName==''){
            layer.msg('请输入客户名称');
            return;
        }
        if (customerId == null || customerId=="") {
            layer.msg('没有该用户,请重新输入');
            return;
        }
        var url = contextPath + "/resource/main_customer_info?customerId=" + customerId;
        openDialogView(customerName, url, '1200px', '600px');
    }
</script>

<!--框架必需end-->
<body id="scrollContent" >
<!-- 界面布局情况 -->
<div class="desklayout height100">
	<!-- 分三个div并排  -->
	<div class="deskLayout001 height100">
		<!-- 1DIV start -->
		<div class="float_left col width100 height100" style="background-color:#E9ECF3;overflow-y: auto;">



			<div class="box-margin10 width50 box-sizing" id="area_left">
                <div class="width100 box-sizing " style="height:30px;">
                    <div class="search_cus_input field " >
                        <div class="search_btn" onclick="seacherCusInfo();">
								<span>
									搜索
								</span>
                        </div>

                        <input type="text" name="searchUser" id="searchUser" placeholder="快速搜索客户" maxlength="40"/>
                        <%--<input class="easyui-combobox" name="searchUser" id="searchUser" placeholder="快速搜索客户1" maxlength="40"--%>
                        <%--data-options="" >--%>
                    </div>


                </div>
				<div class="width100 relative-position" style="height:250px">
					<!--  标题设置 start  -->
					<div class="titleBox_center">
						<div class="titleBox_left">
							<div class="titleBox_right">
								<!-- 图标标题操作 -->
								<div class="box1_icon dbicon">
									<div class="box_title">关键业务端口流量实时图</div>
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
							<div class="height100 width50 " id="chart1" style="float:left"></div>
							<!--
                            <div class="height100 width333 " id="chart2" style="float:left"></div>
                            <div class="height100 width333 " id="chart3" style="float:left"></div>
                            -->
							<div class="height100 width50" id="chart" style="float:left"></div>
						</div>


					</div>
					<!--  标题设置 end  -->
				</div>



				<div class="width100 box-sizing" style="height:300px;float:left;border-top:10px solid transparent;border-right:5px solid transparent;">
					<div class=" width100 height100 relative-position" >
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



				<%--<div class="width50 box-sizing" style="height:300px;float:left;border-top:10px solid transparent;border-left:5px solid transparent;">
					<div class=" width100 height100 relative-position" >
						<!--  标题设置 start  -->
						<div class="titleBox_center">
							<div class="titleBox_left">
								<div class="titleBox_right">
									<!-- 图标标题操作 -->
									<div class="box1_icon dbicon">
										<div class="box_title">告警</div>
										<div class="box1_status">

											<a href="javascript:alert('链接到其他界面')" target="_self">更多&gt;&gt;</a>

										</div>
									</div>
									<div class="clear"></div>
								</div>
							</div>
						</div>
						<!--  下方内容 -->
						<div class="box1_content quick_content" >


							<!-- table界面:该table自定义  start -->
							<table class="easyui-datagrid "   id="waitgrid2"   data-options="rownumbers:false">

							</table>
							<!-- table界面:该table自定义  end -->



						</div>
						<!--  标题设置 end  -->
					</div>
				</div>--%>

			</div>




			<div class="box-margin10 width50 box-sizing " id="area_right">
				<!-- 图形区域  start -->
				<div class="width100"  style="height:330px;float:left;border-top:10px solid transparent;	">
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
							<div class="width100 height40">
								<div class="height100 width25 box-margin10" id="chart5" style="float:left"></div>
								<div class="height100 width25 box-margin10" id="chart6" style="float:left"></div>
								<div class="height100 width25 box-margin10" id="chart7" style="float:left"></div>
								<div class="height100 width25 box-margin10" id="chart8" style="float:left"></div>
							</div>


							<div class="title_split"></div>

							<div class="height60 width100 box-margin10" id="chart4" ></div>


						</div>
						<!--  标题设置 end  -->
					</div>

				</div>
				<!-- 图形区域  end -->




				<!-- 图形区域  start -->
				<div class="width100"  style="height:240px;float:left;border-top:10px solid transparent;	">

					<div class="height100 width100 relative-position">
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

							<div class="height100 width100 box-margin10" id="chart9" ></div>


						</div>
						<!--  标题设置 end  -->
					</div>

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
		//加载待处理工单表格
        loadGridWaitgrid("waitgrid");

//        top.hideLeft();
        // 基于准备好的dom，初始化echarts实例
        var mychart = echarts.init(document.getElementById('chart'),"custom"),
            mychart4 = echarts.init(document.getElementById('chart4'),"custom"),
            mychart5 = echarts.init(document.getElementById('chart5'),"custom"),
            mychart6 = echarts.init(document.getElementById('chart6'),"custom"),
            mychart7 = echarts.init(document.getElementById('chart7'),"custom"),
            mychart8 = echarts.init(document.getElementById('chart8'),"custom"),
            mychart9 = echarts.init(document.getElementById('chart9'),"custom");

        var realtimeCharts=[];
        for(var i =0;i<portParams.length;i++){
            var param=portParams[i];
            var size=5;
            var models=buildPortFlowBySize(size,param);
            if(i==0){
                var chart=echarts.init(document.getElementById(param["chartid"]));
                chart.setOption(buildPortFlowRealTimeChart(models,param["title"],"速度","kbps"));
                realtimeCharts.push(chart);
            }

        }
        buildPortFlowTopChart(topDatas,"今日端口流量TOP5","下行流量","kb",mychart);;
        //mychart.setOption(buildPortFlowTopChart(topDatas,"端口流量TOP10","下行速度","kbps"));

//
//		setInterval(function () {
//
//			for(var i =0;i<portParams.length;i++){
//				var param=portParams[i];
//				var size=5;
//				var newFlow=buildPortFlowSingle(param,new Date());
//
//				topDatas[i]=newFlow;
//
//				mychart.setOption(buildPortFlowTopChart(topDatas,"端口流量TOP10","下行速度","kbps"));
//				if(i==0){
//
//					var opt=realtimeCharts[i].getOption();
//
//					var timeSet=opt.xAxis[0].data;
//					timeSet.shift()&&timeSet.push(newFlow["hmsstr"]);
//					var upSet=opt.series[0].data;
//					upSet.shift()&&upSet.push(newFlow["upload"]);
//					var dSet=opt.series[1].data;
//					dSet.shift()&&dSet.push(newFlow["download"]);
//					realtimeCharts[i].setOption(opt);
//				}
//
//			}
//
//		}, PortFlowInterval);



        var top=5;
        // 机房资源占用TOP5
        buildSiteTopChartAPI(top,'usedata','机房空间资源可用率TOP'+top,'可用率','%',mychart4)
//        mychart4.setOption(buildSiteTopChart(top,'usedata','机房空间资源可用率TOP'+top,'可用率','%'));

        // 分项占用比例(总)

        // var idcCountData  = null;
        // var charindex = 0;
//         $.getJSON(contextPath+"/main_manager/getidccount",function(data){
//             idcCountData = data;
//             var idcData =  idcCountData[charindex];
//             if(idcData){
//                 // 分项占用比例(总)
//                 var idccount = idcData['idccount'];
//                 mychart5.setOption(buildResPercentOptData("机架占用数","",idccount,'rackusage'));
//                 mychart6.setOption(buildResPercentOptData("IP占用数","",idccount,'ipusage'));
//                 mychart7.setOption(buildResPercentOptData("核心端口占用数","",idccount,'coreportusage'));
//                 mychart8.setOption(buildResPercentOptData("带宽占用数","",idccount,'bandwidthusage'));
//             }
//             if(charindex==idcCountData.length-1){
//                 charindex = 0
//             }else{
//                 charindex++;
//             }
// //            setInterval(function(){
// //
// //            },1000*10);
//         })
		/*资源占比*/
        $.getJSON(contextPath+"/main_manager/getIdcLocationResourceRatio",function(data){
            if(data){
                mychart5.setOption(buildResPercentOpt("机架占用率",data.USEDRACKNUM,data.RACKCOUNT-data.USEDRACKNUM));
                mychart6.setOption(buildResPercentOpt("IP占用率",data.USEDIPNUM,data.IPCOUNT-data.USEDIPNUM));
                mychart7.setOption(buildResPercentOpt("核心端口占用率",data.USEDHPORTNUM,data.HPORTCOUNT-data.USEDHPORTNUM));
                mychart8.setOption(buildResPercentOpt("出口带宽利用率",data.USEDBANDWIDTHNUM,data.BANDWIDTHCOUNT-data.USEDBANDWIDTHNUM));
            }
        })
        // function buildResPercentOptData(title,keyword,dataobj,datakey){
        //     var value =30;
        //     if(dataobj!=""){
        //         value = dataobj[datakey];
        //     }
        //     return buildResPercentOpt(title,keyword,parseFloat(value.toFixed(2)),parseFloat((100-value).toFixed(2)))
        //
        // }
        // mychart5.setOption(buildResPercentOpt("机架占用数","",34 ,94));
        // mychart6.setOption(buildResPercentOpt("IP占用数","",52 ,385));
        // mychart7.setOption(buildResPercentOpt("端口占用数","",14 ,152));
        // mychart8.setOption(buildResPercentOpt("带宽占用数","",53 ,241));

        //机房日用电量TOP5
        mychart9.setOption(buildSiteTopChart(top,'powerdata','机房日用电量TOP'+top,'用电量','kWh'));
        $.getJSON(contextPath+"/main_manager/pueTop",function(result){
            var datas = result.datas;
            var sitenames = result.sitenames;
            var option = mychart9.getOption();
            option.xAxis[0].data=sitenames.slice(0,top);
            option.series[0].data=datas.slice(0,top);
            mychart9.setOption(option);
        })

    });
    function buildResPercentOptData(title,keyword,dataobj,datakey){
        var value =30;
        if(dataobj!=""){
            value = dataobj[datakey];
        }
        return buildResPercentOpt(title,keyword,parseFloat(value.toFixed(2)),parseFloat((100-value).toFixed(2)))

    }

    //构建机房假数据
    function buildSiteDatas(size){
        var result=[];
        for(var i = 0 ; i < size ; i++ ){
            result.push({usedata:randoms(50,95),sitename:'YD_SC_CD_XY-'+(i+1)+'机房',powerdata:randoms(5000,10000)});
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
    function randoms(min,max){
        var result=Math.random()*max;
        if(min>result){
            result=randoms(min,max);
        }
        return parseFloat(result).toFixed(2);
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

    var portDatas=[];
    var portParams=[
        {title:"IDC出口",umax:50000,umin:41000,dmax:60000,dmin:47000,chartid:"chart1"},
        {title:"端口流量1",umax:28000,umin:19000,dmax:38000,dmin:26000,chartid:"chart2"},
        {title:"端口流量2",umax:28000,umin:19000,dmax:38000,dmin:26000,chartid:"chart3"},
        {title:"端口流量3",umax:28000,umin:19000,dmax:38000,dmin:26000,chartid:"chart2"},
        {title:"端口流量4",umax:28000,umin:19000,dmax:38000,dmin:26000,chartid:"chart3"},
        {title:"端口流量5",umax:28000,umin:19000,dmax:38000,dmin:26000,chartid:"chart2"},
        {title:"端口流量6",umax:28000,umin:19000,dmax:38000,dmin:26000,chartid:"chart3"},
        {title:"端口流量7",umax:28000,umin:19000,dmax:38000,dmin:26000,chartid:"chart2"},
        {title:"端口流量8",umax:28000,umin:19000,dmax:38000,dmin:26000,chartid:"chart3"},
        {title:"端口流量9",umax:28000,umin:19000,dmax:38000,dmin:26000,chartid:"chart2"}
    ];
    var topDatas=[];

    function buildPortFlowSingle(param,d){
        var result={upload:randoms(param["umin"],param["umax"]),download:randoms(param["dmin"],param["dmax"]),tagname:param["title"],timestr:formatDateStr(d),hmsstr:formatHMSStr(d)};
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


    //构建端口流量假数据
    function buildPortFlowBySize(size,param){//{title:"idc出口",umax:50000,umin:41000,dmax:60000,dmin:47000}
        var result=[];
        var d=new Date();
        d.setTime(d.getTime()-(size*PortFlowInterval));
        for(var i = 0 ; i < size ; i++ ){
            result.push(buildPortFlowSingle(param,d));
            d.setTime(d.getTime()+(PortFlowInterval));
            if(i+1==size){
                topDatas.push(result[i]);
            }
        }
        portDatas.push(result);
        return result;
    }

    //构建端口流量统计数据
    function buildPortFlowRealTimeChart(models,title,legendTitle,unitName){

        var times=getValsFromItemsBykey(models,"hmsstr");//timestr
        var uploads=getValsFromItemsBykey(models,"upload");
        var downloads=getValsFromItemsBykey(models,"download");

        for(var i = 0 ; i < times.length ; i++ ){
            if((i+1)%2==0){
                //times[i]='\n'+times[i];
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
                confine:true
                //,formatter: "日期 : {b} <br/>{a} : {c}("+unitName+") <br/> {d} : {e} "
            },
            legend: {
                data:["上行","下行"],
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
                },
                x: 50
            },
            itemStyle: {
                normal:{
                    color:'#c23531',
                    shadowBlur: 1,
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
                    data : times,
                    axisLabel:{
                        rotate:25,
                        interval:0
                    },
                    text:'123'
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    boundaryGap: [0, '100%'],
                    name: legendTitle+'（'+unitName+'）'
                }
            ],
            series : [
                {
                    name:"上行",
                    type:'line',
                    smooth: true,
                    itemStyle:{
                        normal:{
                            color:"#C23531"
                        }
                    },
                    label: {
                        normal: {
                            show: false,
                            position: 'top',
                            formatter: "{c}"//"{c}("+unitName+")"
                        }
                    },
                    data:uploads
                },
                {
                    name:"下行",
                    type:'line',
                    smooth: true,
                    itemStyle:{
                        normal:{
                            color:"#057300"
                        }
                    },
                    label: {
                        normal: {
                            show: false,
                            position: 'top',
                            formatter: "{c}"//"{c}("+unitName+")"
                        }
                    },
                    data:downloads
                }
            ]
        };
        return option;
    }

    //构建端口流量top统计数据
    function buildPortFlowTopChart(items,title,legendTitle,unitName,mychart){
        //var times=getValsFromItemsBykey(models,"hmsstr");//timestr
        var models=items.sort(function(a,b){return a["download"]<b["download"]});
        var tagnames=[];//getValsFromItemsBykey(models,"tagname");
        var uploads=getValsFromItemsBykey(models,"upload");
        var downloads=[];//getValsFromItemsBykey(models,"download");



        var option = {
            title : {
                text: title,
                textStyle:{
//                    color:'#6E6A6F',
                    fontSize:14
                },
                x:'center',
                y:'top'
            },
            tooltip : {
                trigger: 'axis',
                confine:true
                //,formatter: "端口 : {b} <br/>{a} : {c}("+unitName+")"
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
                },
                x: 50
            },
            itemStyle: {
                normal:{
                    color:'#c12e34',
//                    shadowBlur: 1,
                    shadowOffsetX: 0,
                    //shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                emphasis: {
                    borderWidth:2,
                    //borderColor:'#c23531'

                }
            },
            xAxis : [
                {
                    type : 'category',
                    data : tagnames,
                    axisLabel:{
                        rotate:25,
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
                    barWidth:20,
                    smooth: true,
                    itemStyle:{
                        normal:{
//                            color:"#DBD2BF"
                        }
                    },
                    label: {
                        normal: {
                            show: false,
                            position: 'top',
                            formatter: "{c}"//"{c}("+unitName+")"
                        }
                    },
                    data:downloads
                }
            ]
        };
        mychart.setOption(option);
        $.getJSON(contextPath+"/main_manager/getflowtop",function(data){
            var tagnames = [],downloads=[];
            for(var i =0;i<data.length&&i<5;i++){
                tagnames.push(data[i].portname);
                downloads.push(data[i].outflow);
            }
            option.xAxis[0].data = tagnames;
            option.series[0]. data= downloads;
            mychart.setOption(option);
        })
    }



    function buildSiteTopChartAPI(size,key,title,legendTitle,unitName,myChart){
        var options = buildSiteTopChart(size,key,title,legendTitle,unitName)
        myChart.setOption(options);
        $.getJSON(contextPath+"/main_manager/getroomfreeusge",function(data){
            var xlabel = [];
            var sdatas = [];
            for(var i=0;i<data.length&&i<size;i++){
                xlabel.push(data[i].ROOMNAME)
                sdatas.push(data[i].FREEUAGE)
            }
            options.xAxis[0].data =xlabel.slice(0,size);
            options.series[0].data = sdatas.slice(0,size);
            myChart.setOption(options);
        })
    }
    //构建机房统计数据
    function buildSiteTopChart(size,key,title,legendTitle,unitName){
        var models=buildSiteDatas(size).sort(function(a,b){return a[key]<b[key]});
        var datas=getValsFromItemsBykey(models,key);
        var sitenames=getValsFromItemsBykey(models,'sitename');
        var option = {
            title : {
                text: title,
                textStyle:{
                    color:'#2b821d',
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
                y: 'top',
                x:'right'
            },
            calculable : true,
            itemStyle: {
                normal:{
                    color:function(){
                        if(unitName=='%'){
                            return '#e6b600'
                        }
                        return '#2b821d'
                    },
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
                    barWidth:20,
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
                    color:'#6E6A6F',
                    fontSize:14
                },
                x:'center',
                y:'top'
            },
            tooltip : {
                trigger: 'item',
                confine:true ,
                formatter: "{b} : {d}% ({c}/"+resourceTotal+")" // "{b} : {c} ({d}%)"
            },
            legend: {
                data: ['占用','闲置'],
                orient:"horizontal",
                y: 'bottom',
                itemWidth: 15,
                itemHeight: 10
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
                    }
                }
            ]
        };
        return option;
    }
</script>
<script>
    var gridids=["waitgrid2","waitgrid3"];
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
                {field: 'createTimeStr', title: '创建时间',halign:'left',width:'24%'}
            ]]
        })
    }

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
            url:getURL(idx)
            //data:initData(idx)

        });
    }
    function getURL(idx){
        if(idx==0){
            return contextPath+"/main_manager/getwaitticket";
        }else{
            return contextPath+"/main_manager/getalarm";
        }
    }
    function initColumns(idx){
        var result=[];
        var columns=[];
        if(idx==0){
            //{field:'ck',checkbox:true},
            columns.push({title:'工单名称',field:'ordername',width:150,align:'center'});
//            columns.push({title:'工单类型',field:'ordertype',width:80,align:'center'});
            columns.push({title:'创建时间',field:'createtime',width:150,align:'center'});

        }else if(idx==1){
            //{field:'ck',checkbox:true},
            columns.push({title:'告警对象',field:'objName',width:150,align:'center'});
            columns.push({title:'告警信息',field:'alarminfo',width:80,align:'center'});
            columns.push({title:'告警时间',field:'alarmtimeStr',width:150,align:'center'});

        }else{
            result=initColumns(0);
        }
        if(columns.length!=0)result.push(columns);
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


</script>



</body>
</html>