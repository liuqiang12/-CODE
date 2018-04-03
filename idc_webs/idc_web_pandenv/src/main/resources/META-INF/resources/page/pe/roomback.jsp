<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <title>能耗分析</title>
    <script src="<%=request.getContextPath() %>/framework/layui/layui.js"></script>
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/framework/layui/css/layui.css"/>
    <style>
        body {
            background-color: #fff;
        }

        /*fieldset{*/
        /*border-color: blue;*/
        /*}*/
        /*legend{*/
        /*color:blue*/
        /*}*/
        .layui-table {
            margin-top: 0;
            border-left: 1px solid #e2e2e2;
        }

        .layui-elem-quote {
            line-height: 26px;
            position: relative;
            border-left: 5px solid rgb(0, 167, 238);
            background-color: rgb(0, 167, 238);
        }

        .layui-elem-quote.title {
            padding: 10px 15px;
            margin-bottom: 0;
            font-size: 20px;
            font-weight: bold;
            color: #fff;
        }

        /*.layui-table{*/
        /*background-color: rgb(211,213,14);*/
        /*}*/
        tr td:last-child {
            border-right: 1px solid rgb(0, 167, 238);
        }

        .layui-table[lay-size="sm"] td, .layui-table[lay-size="sm"] th {
            text-align: center;
            font-size: 20px;
            word-wrap: break-word;
        }

        /*.layui-table tbody tr:hover, .layui-table thead tr, .layui-table-click, .layui-table-header, .layui-table-hover, .layui-table-mend, .layui-table-patch, .layui-table-tool, .layui-table[lay-even] tr:nth-child(even) {*/
        /*background-color: rgb(211,213,14);*/
        /*}*/
        /*ul {*/
        /*list-style: none;*/
        /*padding: 10px 0;*/
        /*text-align: center;*/
        /*}*/

        /*ul li {*/
        /*list-style: none;*/
        /*}*/

        /*ul li {*/
        /*display: block;*/
        /*width: 50%;*/
        /*float: left;*/
        /*}*/

        .everyone {
            border-right: 1px solid blue;
            border-bottom: 1px solid blue;
            margin: 0px;
        }

        .layui-row .data {
            text-align: center;

        }

        .layui-row .data strong {
            display: block;
            color: #797979 font-size : 18 px;
        }

        .layui-row .data span {
            color: blue;
            font-weight: 500;
            text-transform: uppercase;
            font-size: 12px;
        }

        ul li {
            list-style: none;
            margin-left: 50px;
        }
        .layui-elem-field {
            /*margin-bottom: 10px;*/
            padding: 0;
            border-width: 1px;
            border-style: solid;
        }
    </style>
</head>
<body>
<div class="easyui-panel" fit="true" style="overflow-x:hidden">
    <div class="layui-row layui-col-space5">
        <div class="layui-col-xs12 layui-col-sm3 layui-col-md4 everyone" style="margin-top:5px;">
            <blockquote class="layui-elem-quote title">机房:IDC-XY-B04-207</blockquote>
            <fieldset class="layui-elem-field">
                <legend>12月12日</legend>
                <div class="layui-field-box">
                    <div class="layui-row layui-col-space1">
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>3361Kw/h</strong>
                                <span>总耗电量</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data" style="visibility:hidden">
                                <strong>&nbsp;</strong>
                                <span >总耗电量</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>3361Kw/h</strong>
                                <span>空调耗电</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>1134Kw/h</strong>

                                <span>设备耗电</span>
                            </div>
                        </div>

                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>0Kw/h</strong>
                                <span>其他耗电</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>1.34</strong>
                                <span>PUE值</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>-5%<span style="color: red;font-size: 20px">↓</span></strong>
                                <span>总能耗环比增长</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>10%<span style="color: green;font-size: 20px">↑</span></strong>

                                <span>总能耗同比增长</span>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="layui-elem-field">
                <legend>11月</legend>
                <div class="layui-field-box">
                    <div class="layui-row layui-col-space1">
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>3361Kw/h</strong>
                                <span>总耗电量</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data" style="visibility:hidden">
                                <strong>&nbsp;</strong>
                                <span >总耗电量</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>3361Kw/h</strong>
                                <span>空调耗电</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>1134Kw/h</strong>

                                <span>设备耗电</span>
                            </div>
                        </div>

                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>0Kw/h</strong>
                                <span>其他耗电</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>1.34</strong>
                                <span>PUE值</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>-5%<span style="color: red;font-size: 20px">↓</span></strong>
                                <span>总能耗环比增长</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>10%<span style="color: green;font-size: 20px">↑</span></strong>

                                <span>总能耗同比增长</span>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
        </div>
        <div class="layui-col-xs12 layui-col-sm3 layui-col-md4 everyone" style="margin-top:5px;">
                <blockquote class="layui-elem-quote title">机房:IDC-XY-B04-208</blockquote>
                <fieldset class="layui-elem-field">
                    <legend>12月12日</legend>
                    <div class="layui-field-box">
                        <div class="layui-row layui-col-space1">
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>3361Kw/h</strong>
                                    <span>总耗电量</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data" style="visibility:hidden">
                                    <strong>&nbsp;</strong>
                                    <span >总耗电量</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>3361Kw/h</strong>
                                    <span>空调耗电</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>1134Kw/h</strong>

                                    <span>设备耗电</span>
                                </div>
                            </div>

                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>0Kw/h</strong>
                                    <span>其他耗电</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>1.34</strong>
                                    <span>PUE值</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>-5%<span style="color: red;font-size: 20px">↓</span></strong>
                                    <span>总能耗环比增长</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>10%<span style="color: green;font-size: 20px">↑</span></strong>

                                    <span>总能耗同比增长</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </fieldset>
                <fieldset class="layui-elem-field">
                    <legend>11月</legend>
                    <div class="layui-field-box">
                        <div class="layui-row layui-col-space1">
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>3361Kw/h</strong>
                                    <span>总耗电量</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data" style="visibility:hidden">
                                    <strong>&nbsp;</strong>
                                    <span >总耗电量</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>3361Kw/h</strong>
                                    <span>空调耗电</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>1134Kw/h</strong>

                                    <span>设备耗电</span>
                                </div>
                            </div>

                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>0Kw/h</strong>
                                    <span>其他耗电</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>1.34</strong>
                                    <span>PUE值</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>-5%<span style="color: red;font-size: 20px">↓</span></strong>
                                    <span>总能耗环比增长</span>
                                </div>
                            </div>
                            <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                                <div class="data">
                                    <strong>10%<span style="color: green;font-size: 20px">↑</span></strong>

                                    <span>总能耗同比增长</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </fieldset>
            </div>
        <div class="layui-col-xs12 layui-col-sm3 layui-col-md4 everyone" style="margin-top:5px;">
            <blockquote class="layui-elem-quote title">机房:IDC-XY-B04-209</blockquote>
            <fieldset class="layui-elem-field">
                <legend>12月12日</legend>
                <div class="layui-field-box">
                    <div class="layui-row layui-col-space1">
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>3361Kw/h</strong>
                                <span>总耗电量</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data" style="visibility:hidden">
                                <strong>&nbsp;</strong>
                                <span >总耗电量</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>3361Kw/h</strong>
                                <span>空调耗电</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>1134Kw/h</strong>

                                <span>设备耗电</span>
                            </div>
                        </div>

                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>0Kw/h</strong>
                                <span>其他耗电</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>1.34</strong>
                                <span>PUE值</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>-5%<span style="color: red;font-size: 20px">↓</span></strong>
                                <span>总能耗环比增长</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>10%<span style="color: green;font-size: 20px">↑</span></strong>

                                <span>总能耗同比增长</span>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
            <fieldset class="layui-elem-field">
                <legend>11月</legend>
                <div class="layui-field-box">
                    <div class="layui-row layui-col-space1">
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>3361Kw/h</strong>
                                <span>总耗电量</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data" style="visibility:hidden">
                                <strong>&nbsp;</strong>
                                <span >总耗电量</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>3361Kw/h</strong>
                                <span>空调耗电</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>1134Kw/h</strong>

                                <span>设备耗电</span>
                            </div>
                        </div>

                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>0Kw/h</strong>
                                <span>其他耗电</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>1.34</strong>
                                <span>PUE值</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>-5%<span style="color: red;font-size: 20px">↓</span></strong>
                                <span>总能耗环比增长</span>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6" style="margin-top:5px; ">
                            <div class="data">
                                <strong>10%<span style="color: green;font-size: 20px">↑</span></strong>

                                <span>总能耗同比增长</span>
                            </div>
                        </div>
                    </div>
                </div>
            </fieldset>
        </div>
    </div>

</div>
<script type="text/javascript">
    //一般直接写在一个js文件中
    layui.use(['layer', 'element'], function () {
        var element = layui.element;
    });
    function showroom(id) {
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + id + "&buttonType=view";
        openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
    }
    /***
     *历史用电情况
     * @param roomid
     */
    function showhispuebyroom(roomid, roomname) {
        var url = contextPath + "/pe/hispe/${group}/" + roomid;
        openDialogView(roomname + '历史用电情况', url, '800px', '530px');
    }
    var CurrNode = null;
    function searchModels() {
        var queryParams = {};
        if (typeof CurrNode != 'undefined' && CurrNode != null) {
            var id = CurrNode.id;
            var datas = id.split("_");
            queryParams.buildid = datas[1]
        }
        $('#dg').datagrid({
            url: contextPath + "/pe/list",
            queryParams: queryParams
        });
    }
    function getOptions() {
        var option = {
            columns: [[
                {
                    field: 'roomName',
                    title: ' <c:if test="${group==null || group!='build'}">机房名字</c:if><c:if test="${group!=null && group=='build'}">机楼名字</c:if>',
                    width: 100,
                    formatter: function (value, row, index) {
                        return '<a href="javascript:void(0)" onclick="showroom(' + row.roomid + ')">' + row.roomName + '</a>'
                    }
                },

                {
                    field: 'electric', title: '昨12月12日耗电量(KW/h)', width: 100, formatter: function (value, row, index) {
                    return '<a href="javascript:void(0)" onclick="showhispuebyroom(' + row.roomid + ',\'' + row.roomName + '\')">' + value + '</a>'
                }
                },
                {
                    field: 'pue', title: 'PUE', width: 100, formatter: function () {
                    return ((Math.random() * (13 - 8) + 8) / 10).toFixed(2);
                }
                }

            ]],
            title: '昨12月12日耗电量',
            pagination: false,
            singleSelect: true,
            fitColumns: true,
            pageSize: 20,
            selectOnCheck: false,
            checkOnSelect: false,
            url: contextPath + "/pe/list/${group}",
            <c:if test="${group==null || group!='build'}">
            rtree: {
                r_type: 1,
                onClick: function (iteam, treeId, treeNode) {
                    if (treeNode.id.indexOf("building_") > -1) {
                        CurrNode = treeNode;
                        searchModels();
                    } else {
                        CurrNode = null;
                        searchModels();
                    }
                }
            }
            </c:if>
        }
        return option;
    }
    $(function () {
        $("#dg").MyResource(
                getOptions()
        );
//        top.hideLeft();
    })
</script>
</body>
</html>