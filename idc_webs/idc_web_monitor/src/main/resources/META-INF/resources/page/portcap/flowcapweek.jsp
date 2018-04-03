<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'north'" style="height: 45px;overflow: hidden">
        <table class="kv-table">
            <tbody>
            <tr>
                <td class="kv-label">报表类型:</td>
                <td class="kv-content">
                    端口性能周报
                </td>
                <td class="kv-label">开始时间：</td>
                <td class="kv-content">${startTime}</td>
                <td class="kv-label">结束时间：</td>
                <td class="kv-content">${endTime}</td>
                <td class="kv-label">统计力度:</td>
                <td class="kv-content">
                    ${cyc}
                    <%--<c:if test="${cyc=='mi'}">5分</c:if>--%>
                    <%--<c:if test="${cyc=='hour'}">小时</c:if>--%>
                    <%--<c:if test="${cyc=='day'}">天</c:if>--%>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'north'" style="height: 25px;overflow: hidden">
                <div class="btn-cls-common" id="importData">导 出</div>
            </div>
            <div data-options="region:'center'">
                <table id="dg1"class="easyui-datagrid" data-options="fit:true,fitColumns:true,singleSelect:true,showFooter:false,checkbox:true,onLoadSuccess:onLoadSuccess,rowStyler:rowStyler">
                    <thead>
                    <%--<tr >--%>
                        <%--<th colspan="8" data-options="field:'ddd',width:100">1</th>--%>
                    <%--</tr>--%>
                    <tr>
                        <th rowspan="2" data-options="field:'d_t',width:100">端口名字</th>
                        <th rowspan="2" data-options="field:'flowtype',width:100">流量类型</th>
                        <th colspan="6" data-options="field:'flowdata',width:100,align:'center'">流量数据</th>
                    </tr>
                    <tr>
                        <th data-options="field:'a_1',width:100">峰值(Mbps)</th>
                        <th data-options="field:'z_1',width:100">峰值利用率(%)</th>
                        <th data-options="field:'x_1',width:100">谷值(Mbps)</th>
                        <th data-options="field:'c_1',width:100">谷值利用率(%)</th>
                        <th data-options="field:'v_1',width:100">均值(Mbps)</th>
                        <th data-options="field:'b_1',width:100">均值利用率(%)</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${rows}" var="row" varStatus="status">
                        <tr>
                            <td>${row.key}</td>
                        </tr>
                      <c:forEach items="${row.value}" var="cellt" varStatus="status">
                          <tr>
                              <td>${cellt.key}</td>
                              <td>入流量</td>
                              <td><fmt:formatNumber value="${cellt.value.maxinflowMbps}"
                                                    pattern="#0.000"/></td>
                              <td>
                                  <c:if test="${not empty cellt.value.bandwidth&&cellt.value.bandwidth>0}">
                                      <fmt:formatNumber
                                              value="${cellt.value.maxinflowMbps/cellt.value.bandwidth*100}"
                                              pattern="#0.000"/>%
                                  </c:if>
                              </td>
                              <td><fmt:formatNumber value="${cellt.value.mininflowMbps}"
                                                    pattern="#0.000"/></td>
                              <td>
                                  <c:if test="${not empty cellt.value.bandwidth&&cellt.value.bandwidth>0}">
                                      <fmt:formatNumber
                                              value="${cellt.value.mininflowMbps/cellt.value.bandwidth*100}"
                                              pattern="#0.000"/>%
                                  </c:if>
                              </td>
                              <td><fmt:formatNumber value="${cellt.value.avginflowMbps}"
                                                    pattern="#0.000"/></td>
                              <td>
                                  <c:if test="${not empty cellt.value.bandwidth&&cellt.value.bandwidth>0}">
                                      <fmt:formatNumber
                                              value="${cellt.value.avginflowMbps/cellt.value.bandwidth*100}"
                                              pattern="#0.000"/>%
                                  </c:if>

                              </td>
                          </tr>
                          <tr>
                              <td>${cellt.key}</td>
                              <td>出流量</td>
                              <td><fmt:formatNumber value="${cellt.value.maxoutflowMbps}"
                                                    pattern="#0.000"/></td>
                              <td>
                                  <c:if test="${not empty cellt.value.bandwidth&&cellt.value.bandwidth>0}">
                                      <fmt:formatNumber
                                              value="${cellt.value.maxoutflowMbps/cellt.value.bandwidth*100}"
                                              pattern="#0.000"/>%
                                  </c:if>
                              </td>
                              <td><fmt:formatNumber value="${cellt.value.minoutflowMbps}"
                                                    pattern="#0.000"/></td>
                              <td>
                                  <c:if test="${not empty cellt.value.bandwidth&&cellt.value.bandwidth>0}">
                                      <fmt:formatNumber
                                              value="${cellt.value.minoutflowMbps/cellt.value.bandwidth*100}"
                                              pattern="#0.000"/>%
                                  </c:if>

                              </td>
                              <td><fmt:formatNumber value="${cellt.value.avgoutflowMbps}"
                                                    pattern="#0.000"/></td>
                              <td>
                                  <c:if test="${not empty cellt.value.bandwidth&&cellt.value.bandwidth>0}">
                                      <fmt:formatNumber  value="${cellt.value.avgoutflowMbps/cellt.value.bandwidth*100}"
                                                         pattern="#0.000"/>%
                                  </c:if>
                              </td>
                          </tr>
                      </c:forEach>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%--<div id="toolbar">--%>
    <%--<div class="btn-cls-common" id="importData">导 出</div>--%>
<%--</div>--%>
<script type="text/javascript">
    function mergeGridColCells(grid, rowFildName) {
        var rows = grid.datagrid('getRows');
        var startIndex = 0;
        var endIndex = 0;
        if (rows.length < 1) {
            return;
        }
        $.each(rows, function (i, row) {
            if (row[rowFildName] == rows[startIndex][rowFildName]) {
                endIndex = i;
            }
            else {
                grid.datagrid('mergeCells', {
                    index: startIndex,
                    field: rowFildName,
                    rowspan: endIndex - startIndex + 1
                });
                startIndex = i;
                endIndex = i;
            }

        });
        grid.datagrid('mergeCells', {
            index: startIndex,
            field: rowFildName,
            rowspan: endIndex - startIndex + 1
        });
    }
    function mergeGridtimeCell(grid, rowFildName) {
        var rows = grid.datagrid('getRows');
        var startIndex = 0;
        var endIndex = 0;
        if (rows.length < 1) {
            return;
        }
        $.each(rows, function (i, row) {
            console.log(row[rowFildName])
            if (row['a_1'] === row['z_1']&&typeof (row['z_1'])=='undefined') {
                grid.datagrid('mergeCells', {
                    index: i,
                    field: rowFildName,
                    colspan: 8
                });
            }
        });
//        grid.datagrid('mergeCells', {
//            index: startIndex,
//            field: rowFildName,
//            rowspan: endIndex - startIndex + 1
//        });
    }
    $(function () {

       // mergeGridColCells($("#dg1"),"d");
    })
    function LoadSuccess(){

    }
    (function ($) {
        var $defaults = {
            containerid: null
            , datatype: 'table'
            , dataset: null
            , columns: null
            , returnUri: false
            , worksheetName: "My Worksheet"
            , encoding: "utf-8"
        };

        var $settings = $defaults;

        $.fn.excelexportjs = function (options) {
            $settings = $.extend({}, $defaults, options);

            var gridData = [];
            var excelData;

            return Initialize();

            function Initialize() {
                var type = $settings.datatype.toLowerCase();

                BuildDataStructure(type);

                switch (type) {
                    case 'table':
                        excelData = Export(ConvertFromTable());
                        break;
                    case 'json':
                        excelData = Export(ConvertDataStructureToTable());
                        break;
                    case 'xml':
                        excelData = Export(ConvertDataStructureToTable());
                        break;
                    case 'jqgrid':
                        excelData = Export(ConvertDataStructureToTable());
                        break;
                }

                if ($settings.returnUri) {
                    return excelData;
                }
                else {
                    window.open(excelData);
                }
            }

            function BuildDataStructure(type) {
                switch (type) {
                    case 'table':
                        break;
                    case 'json':
                        gridData = $settings.dataset;
                        break;
                    case 'xml':
                        $($settings.dataset).find("row").each(function (key, value) {
                            var item = {};

                            if (this.attributes != null && this.attributes.length > 0) {
                                $(this.attributes).each(function () {
                                    item[this.name] = this.value;
                                });

                                gridData.push(item);
                            }
                        });
                        break;
                    case 'jqgrid':
                        $($settings.dataset).find("rows > row").each(function (key, value) {
                            var item = {};

                            if (this.children != null && this.children.length > 0) {
                                $(this.children).each(function () {
                                    item[this.tagName] = $(this).text();
                                });

                                gridData.push(item);
                            }
                        });
                        break;
                }
            }

            function ConvertFromTable() {
                var result = $('<div>').append($('#' + $settings.containerid).clone()).html();
                console.log(result)
                return result;
            }

            function ConvertDataStructureToTable() {
                var result = "<table>";

                result += "<thead><tr>";
                $($settings.columns).each(function (key, value) {
                    if (this.ishidden != true) {
                        result += "<th";
                        if (this.width != null) {
                            result += " style='width: " + this.width + "'";
                        }
                        result += ">";
                        result += this.headertext;
                        result += "</th>";
                    }
                });
                result += "</tr></thead>";

                result += "<tbody>";
                $(gridData).each(function (key, value) {
                    result += "<tr>";
                    $($settings.columns).each(function (k, v) {
                        if (value.hasOwnProperty(this.datafield)) {
                            if (this.ishidden != true) {
                                result += "<td";
                                if (this.width != null) {
                                    result += " style='width: " + this.width + "'";
                                }
                                result += ">";
                                result += value[this.datafield];
                                result += "</td>";
                            }
                        }
                    });
                    result += "</tr>";
                });
                result += "</tbody>";

                result += "</table>";
                return result;
            }

            function Export(htmltable) {
                var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
                excelFile += "<head>";
                excelFile += '<meta http-equiv="Content-type" content="text/html;charset=' + $defaults.encoding + '" />';
                excelFile += "<!--[if gte mso 9]>";
                excelFile += "<xml>";
                excelFile += "<x:ExcelWorkbook>";
                excelFile += "<x:ExcelWorksheets>";
                excelFile += "<x:ExcelWorksheet>";
                excelFile += "<x:Name>";
                excelFile += "{worksheet}";
                excelFile += "</x:Name>";
                excelFile += "<x:WorksheetOptions>";
                excelFile += "<x:DisplayGridlines/>";
                excelFile += "</x:WorksheetOptions>";
                excelFile += "</x:ExcelWorksheet>";
                excelFile += "</x:ExcelWorksheets>";
                excelFile += "</x:ExcelWorkbook>";
                excelFile += "</xml>";
                excelFile += "<![endif]-->";
                excelFile += "</head>";
                excelFile += "<body>";
                excelFile += htmltable.replace(/"/g, '\'');
                excelFile += "</body>";
                excelFile += "</html>";

                var uri = "data:application/vnd.ms-excel;base64,";
                var ctx = {worksheet: $settings.worksheetName, table: htmltable};

                return (uri + base64(format(excelFile, ctx)));
            }

            function base64(s) {
                return window.btoa(unescape(encodeURIComponent(s)));
            }

            function format(s, c) {
                return s.replace(/{(\w+)}/g, function (m, p) {
                    return c[p];
                });
            }
        };
    })(jQuery);
    $(function () {

        $("#importData").click(function () {
//            $("#dg1").excelexportjs({
//                containerid: "d${uuid}g1"
//                , datatype: 'table'
//            });
            jQuery('<form action="'+contextPath +'/capreport/downreport" method="post">' +  // action请求路径及推送方法
                    '<input type="text" name="reportid" value="${uuid}"/>' + // 文件路径
//                    '<input type="text" name="img" value="'+imgUrl+'"/>' + // 文件名称
                    '</form>').attr('target', '_blank')
                    .appendTo('body').submit().remove();
            <%--jQuery('<form action="'+contextPath +'/capreport/downcap" method="post">' +  // action请求路径及推送方法--%>
                    <%--'<input type="text" name="reportType" value="${reportType}"/>' + // 文件路径--%>
                    <%--'<input type="text" name="portType" value="${portType}"/>' + // 文件名称--%>
                    <%--'<input type="text" name="ports" value="${ports}"/>' + // 文件名称--%>
                    <%--'<input type="text" name="startTime" value="${startTime}"/>' + // 文件名称--%>
                    <%--'<input type="text" name="endTime" value="${endTime}"/>' + // 文件名称--%>
                    <%--'<input type="text" name="cyc" value="${cyc}"/>' + // 文件名称--%>
                    <%--'<input type="text" name="img" value="'+imgUrl+'"/>' + // 文件名称--%>
                    <%--'</form>').attr('target', '_blank')--%>
                    <%--.appendTo('body').submit().remove();--%>
        })
    })
    function onLoadSuccess(data){
        mergeGridColCells($("#dg1"),"d_t");
        mergeGridtimeCell($("#dg1"),"d_t")
//        var merges = [{
//            index:1,
//            colspan:8
//        }];
//        for(var i=0; i<merges.length; i++)
//            $('#dg1').datagrid('mergeCells',{
//                index:merges[i].index,
//                field:'d_t',
//                colspan:merges[i].colspan
//            });
    }
    function rowStyler(index,row){
        if (row['a_1'] === row['z_1']&&typeof (row['z_1'])=='undefined') {//时间行
            return 'background-color:#6293BB;color:#fff;text-align:center';
        }
    }
</script>
</body>
</html>