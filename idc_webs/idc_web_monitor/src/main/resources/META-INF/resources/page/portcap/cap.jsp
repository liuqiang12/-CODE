<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/src/jquery.datebox.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/date/dateutil.js"></script>
    <style>
        body {
            background-color: white;
        }

        .mainback {
            background: url('../../moudles/monitor/img/back1.png') no-repeat right bottom;
            /*width: 100%;*/
            /*height: 100%;*/
            /*overflow: hidden;*/
            /*background-size: cover;*/
            /*/!*background-img:url(../images/bj.jpg);*!/*/
            /*background-repeat: no-repeat;*/
            /*background-position: right bottom;*/
        }
    </style>
</head>
<body>
<div class="easyui-panel mainback" fit="true" style="">
    <div style="width: 50%;margin-left: 150px;margin-top: 50px;">
        <form id="form" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">端口</td>
                    <td class="kv-content">
                        <input id="portname" name="portname" style="width:200px;" />
                        <input type="hidden" id="ports" name="ports" style="width:200px;" />
                        <%--<select id="ports" class="easyui-combotree" style="width:200px;"--%>
                        <%--data-options="url:'get_data.php',e"></select>--%>
                    </td>

                </tr>
                <tr style="display: none">
                    <td class="kv-label">报表类型</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" id="reportType" name="reportType"
                                data-options="required:true,width:200<c:if test="${not empty type}">,readonly:true</c:if>">
                            <option value="portinmax" <c:if test="${type=='maxin'}">selected</c:if>>端口入流量峰值</option>
                            <option value="portoutmax" <c:if test="${type=='maxout'}">selected</c:if>>端口出流量峰值</option>
                            <option value="portcap" <c:if test="${type=='portcap'}">selected</c:if>>端口性能</option>
                        </select>
                    </td>
                </tr>
                <tr style="display: none">
                    <td class="kv-label">端口类型</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" id="portType" name="portType"
                                data-options="required:true,width:200<c:if test="${not empty mode}">,readonly:true</c:if>">
                            <option value="0" <c:if test="${mode=='port'}">selected</c:if>>物理端口</option>
                            <option value="1" <c:if test="${mode=='busi'}">selected</c:if>>业务端口</option>
                        </select>
                    </td>
                </tr>

                <tr>
                    <td class="kv-label">统计时间段</td>
                    <td class="kv-content">
                        <input class="easyui-datetimebox" name="startTime" id="startTime"
                               data-options="showSeconds:false,onChange:function(){
                                 $('#endTime').datetimebox('showPanel')
                               },prompt:'默认昨日',formatter:myformatter,parser:myparser" value="" style="width:120px">-
                        <input class="easyui-datetimebox" name="endTime" id="endTime"
                               data-options="prompt:'默认昨日',formatter:myformatter,parser:myparser" style="width:120px">
                        <select class="easyui-combobox" data-options="onChange:changeDate" id="dateselect">
                            <option value="-">-</option>
                            <option value="24h">昨日</option>
                            <option value="7day">最近7天</option>
                            <option value="1month">最近1个月</option>
                            <option value="3month">最近3个月</option>
                            <%--<option value="currweek">本周</option>--%>
                            <%--<option value="currmonth">本月</option>--%>
                        </select>
                    </td>

                </tr>
                <tr>
                    <td class="kv-label">周期</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="cyc" data-options="required:true,width:200">
                            <option value="mi">5分钟</option>
                            <option value="day">天</option>
                            <option value="hour">小时</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-content" colspan="2">
                        <div style="text-align: center">
                            <a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
    function myformatter(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();;
    }
    function myparser(ps){
        if (!ps) return new Date();
        var st =ps.split(" ");
        var ss = st[0].split('-');
        var y = parseInt(ss[0],10);
        var m = parseInt(ss[1],10);
        var d = parseInt(ss[2],10);

        var ss1 = (st[1].split(':'));
        var h = parseInt(ss1[0],10);
        var mm = parseInt(ss1[1],10);
        var s = parseInt(ss1[2],10);

        if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
            return new Date(y,m-1,d,h,mm,s);
        } else {
            return new Date();
        }
    }
    function changeDate(cvalue) {
        var date = new Date();
        if ("24h" == cvalue) {
            var ndate = cala(date, -1);
            $("#startTime").datetimebox({value: ndate + " 00:00:00"})
            $("#endTime").datetimebox({value: ndate + " 23:59:59"})
        } else if ("7day" == cvalue) {
            var ndate = cala(date, -7);
            var edate = cala(date, -1);
            $("#startTime").datetimebox({value: ndate + " 00:00:00"})
            $("#endTime").datetimebox({value: edate + " 23:59:59"})
        }
        else if ("1month" == cvalue) {
            var ndate = cala(date, -1,'m');
            var edate = cala(date, -1);
            $("#startTime").datetimebox({value: ndate + " 00:00:00"})
            $("#endTime").datetimebox({value: edate + " 23:59:59"})
        }
        else if ("3month" == cvalue) {
            var ndate = cala(date, -3,'m');
            var edate = cala(date, -1);
            $("#startTime").datetimebox({value: ndate + " 00:00:00"})
            $("#endTime").datetimebox({value: edate + " 23:59:59"})
        }
    }
    function cala(date, decday, type) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var ddd = decday;
        var theday = new Date();
        if (typeof (type) == 'undefined' || type == 'd') {
            var ttt = new Date(y, m - 1, d).getTime() + ddd * 24000 * 3600;
            theday.setTime(ttt);
        }
        if (type == 'm') {
            var ttt = new Date(y, m - 1 + ddd, d).getTime();
            theday.setTime(ttt);
        }
        else if (type == 'y') {
            var ttt = new Date(y + ddd, m - 1, d).getTime();
            theday.setTime(ttt);
        }


//        return theday;
        return dateToStr(theday)

    }
    function dateToStr(theday) {
        return theday.getFullYear()+"-"+(theday.getMonth() + 1) + "-" + theday.getDate();
        //return (theday.getMonth() + 1) + "/" + theday.getDate() + "/" + theday.getFullYear();
    }

    $(function () {
        $("#form").attr("action", contextPath + "/capreport/report");
//        $(".checkData").change(function() {
//            var checkAll = $(this).is(':checked');
//            if(checkAll){
//                $("#ports").combogrid("grid").datagrid("checkAll")
//            }else{
//                $("#ports").combogrid("grid").datagrid("uncheckAll")
//            }
//        });
        $("#dateselect").combobox("select","24h");
        var isMuilSelect = true;
//        $("#reportType").combobox({
//            onChange: function (newValue, oldValue) {
//                if (newValue == 'portcap') {
//                    $("#ports").combogrid('reset').combogrid({
//                        multiple: false,
//                    })
//                    isMuilSelect = false;
//                } else {
//                    isMuilSelect = true;
//                    $("#ports").combogrid('reset').combogrid({
//                        multiple: true,
//                    })
//                }
//            }
//        });
//        $("#portType").combobox({
//            onChange: function (newValue, oldValue) {
//                if (newValue == 0) {
//                    $("#ports").combogrid('reset').combogrid({
//                        idField: 'PORTID',
//                        textField: 'PORTNAME',
//                        multiple: isMuilSelect,
//                        url: contextPath + '/capreport/list.do',
//                        pagination: true,
//                        columns: [[
//                            {field: 'PORTID', hidden: true, width: 60},
//                            {field: 'DNAME', title: '设备名字', width: 150},
//                            {field: 'PORTNAME', title: '端口名字', width: 50}
//                        ]],
//                        loadFilter: function (data) {
//                            return data
//                        }
//                    });
//                } else {
//                    $("#ports").combogrid('reset').combogrid({
//                        idField: 'BUSINAME',
//                        textField: 'BUSINAME',
//                        multiple: isMuilSelect,
//                        url: contextPath + '/capreport/list.do?type=busiport',
//                        pagination: false,
//                        columns: [[
//                            {field: 'BUSINAME', title: '端口名字', width: 200}
//                        ]],
//                        loadFilter: function (data) {
//                            return data.rows
//                        }
//                    });
//                }
//            }
//        });
        <%--<c:if test="${mode=='port'}">--%>
        <%--$("#ports").combogrid({--%>
            <%--idField: 'PORTID',--%>
            <%--textField: 'PORTNAME',--%>
            <%--panelWidth: 500,--%>
            <%--panelHeight: 250,--%>
            <%--editable:false,--%>
            <%--multiple: false,--%>
            <%--fit: true,--%>
<%--//            mode: 'remote',--%>
            <%--toolbar:'#toolbar',--%>
            <%--url: contextPath + '/capreport/list.do',--%>
            <%--pagination: true,--%>
            <%--pageList:[20,50,100],--%>
            <%--pageSize:20,--%>
            <%--fitColumns: true,--%>
            <%--columns: [[--%>
                <%--{field: 'PORTID', hidden: true, width: 60},--%>
<%--//                {field: 'ck',checkbox:true},--%>
                <%--{field: 'DNAME', title: '设备名字', width: 100},--%>
                <%--{field: 'PORTNAME', title: '端口名字', width: 50}--%>
            <%--]]--%>
        <%--});--%>
        <%--</c:if>--%>
        <%--<c:if test="${mode=='busi'}">--%>
        <%--isMuilSelect = false;--%>
        <%--$("#ports").combotree({--%>
            <%--valueField: 'id',--%>
            <%--textField: 'name',--%>
            <%--panelWidth: 500,--%>
            <%--panelHeight: 250,--%>
            <%--multiple: false,--%>
            <%--fit: true,--%>

<%--//            mode: 'remote',--%>
<%--//            pagination: false,--%>
<%--//            fitColumns: true,--%>
            <%--url: contextPath + '${contextPath}/busiPort/listbytree?isSimple=1',--%>
            <%--onBeforeSelect:function(node){--%>
                <%--console.log(node)--%>
                <%--if(node.id.indexOf("g_")>-1){--%>
                    <%--return false;--%>
                <%--}--%>
                <%--return true;--%>
            <%--}--%>
<%--//            columns: [[--%>
<%--//                {field: 'portid', hidden: true, width: 60},--%>
<%--//                {field: 'portname', title: '端口名字', width: 200}--%>
<%--//            ]],--%>
<%--//            loadFilter: function (data) {--%>
<%--//                return data.rows--%>
<%--//            }--%>
        <%--});--%>
        <%--</c:if>--%>
        $("#portname").click(function(){
            openWin()
        })
        $("#btn").linkbutton({
            onClick: function () {
                $("#form").submit();
            }
        })
    })
    function openWin(){
        var index = top.layer.open({
            type: 2,
            area: ["900px", "400px"],
            title: "选择端口",
            maxmin: true, //开启最大化最小化按钮
            content: contextPath+"/netport/distributionNetPort",
            btn: ['确定', '关闭'],
            success: function(layero, index){
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
            },
            yes: function (index, layero) {
                var body = top.layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                var selectRows = iframeWin.contentWindow.doSubmit();
                if(selectRows&&selectRows.length>0){
                    var portid  =selectRows[0].PORTID;
                    $("#portname").val(selectRows[0].PORTNAME);
                    $("#ports").val(selectRows[0].PORTID)
                }
                top.layer.close(index)
            }
        });
    }
    function changeinput(newValue, oldValue){
        clearTimeout(f);
        var f = setTimeout('load("'+newValue+'")',1000);
    }
    function load(newValue){
        $('#ports').combogrid({
            url: contextPath + '/capreport/list.do',
            queryParams: {
                q: newValue
            }
        })
    }
</script>
</body>
<div id="toolbar">
    <%--<input id="ss" class="easyui-searchbox" style="width:300px" data-options="searcher:qq,prompt:'Please Input Value'"/>--%>
<%--<label>全选<input type="checkbox" class="checkData"/></label>--%>
    <select class="easyui-combobox" style="width:300px" data-options="
    valueField:'NAME',
    textField:'NAME',
    method:'post',
    onChange:changeinput,
    url:'<%=request.getContextPath() %>/device/getDeviceList.do'
    ">
    </select>
</div>
</html>