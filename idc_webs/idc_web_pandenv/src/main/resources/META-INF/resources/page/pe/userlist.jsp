<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>

    <link href="<%=request.getContextPath() %>/framework/themes/css/import_basic.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_page.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=request.getContextPath() %>/framework/themes/skins/${skin}/main_maintainer/jquery-autocompleter-master/css/main.css"
          rel="stylesheet" type="text/css"/>
    <title>能耗分析</title>
    <script src="<%=request.getContextPath() %>/framework/layui/layui.js"></script>
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/framework/layui/css/layui.css"/>
    <style></style>
</head>
<body>

<div class="easyui-layout" fit="true" style="overflow-x:hidden;background-color: rgb(73,73,73)">
    <div data-options="region:'west'" style="width: 230px" title="导航">
        <ul class="easyui-datalist" title="客户列表" fit="true" data-options="striped:false
        , url: '<%=request.getContextPath() %>/busiPort/getcustomer'
        ,onClickRow:loadCustomer
        ">
            <%--<li value="AL">Alabama</li>--%>
            <%--<li value="AK">Alaska</li>--%>
            <%--<li value="AZ">Arizona</li>--%>
            <%--<li value="AR">Arkansas</li>--%>
            <%--<li value="CA">California</li>--%>
            <%--<li value="CO">Colorado</li>--%>
        </ul>
        <%--<ul id="rtree" class="ztree" style="width:230px; overflow:auto;"></ul>--%>
    </div>
    <div data-options="region:'center'" title="用电信息">
        <table id="dg" class="easyui-datagrid"
               data-options="fit:true,fitColumns:true,pagination:false,singleSelect:true,showFooter:false,toolbar:'#tb'">
            <thead>
            <tr>
                <th rowspan="2" data-options="field:'rackName',width:200">机架名字</th>
                <th colspan="3" data-options="width:100">读数</th>
                <th colspan="3" data-options="width:100,align:'center'">能耗</th>
                <th rowspan="2" data-options="field:'b_12',width:100,align:'center',formatter:function(value,row,index){
                   return '<a href=\'javascript:void(0)\' name=\''+row.rackName+'\' rackid=\''+row.rackId+'\' onclick=\'showHis(this)\'><i class=\'layui-icon\'>&#xe62c;</i>查看</a>'
                }">操作</th>
            </tr>
            <tr>
                <th data-options="field:'read1',width:100">支路1</th>
                <th data-options="field:'read2',width:100">支路2</th>
                <th data-options="field:'readall',width:100,formatter:function(value,row,index){
                    if(row.read1&&row.read2){
                         return (row.read1+row.read2).toFixed(2)
                    }

                }">总计</th>
                <th data-options="field:'diff1',width:100">支路1</th>
                <th data-options="field:'diff2',width:100">支路2</th>
                <th data-options="field:'diffall',width:100,formatter:function(value,row,index){
                    if(row.diff1&&row.diff2){
                        return (row.diff1+row.diff2).toFixed(2)
                    }

                }">总计</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="tb">
    <div class="layui-inline" style="height: 30px;">
        <label class="layui-form-label">查看日期</label>

        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="datetime" placeholder="yyyy-MM-dd"
                   style="height: 26px;margin-top:2px ">
        </div>
    </div>
    <a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
       onclick="getData()">查询</a>
</div>
<script type="text/javascript">

    //一般直接写在一个js文件中
    layui.use(['layer', 'element'], function () {
        var element = layui.element;
    });
    var buildid = 0;
    $(function () {
        try {
            top.hideLeft();
        } catch (e) {
        }
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            var mydate  = new Date();
            mydate=mydate.add("d",-1);
            //常规用法
            laydate.render({
                elem: '#datetime'
                , zIndex: 99999999
                , btns: ['now', 'confirm']
                ,range: '~' //或 range: '~' 来自定义分割字符
                , max: -1
                , value: mydate.Format("yyyy-MM-dd")+" ~ "+mydate.Format("yyyy-MM-dd")
            });
        })
    })
    function loadCustomer(index, row) {
        buildid = row.id;
        getData();
    }
    function getData() {
        if (buildid && buildid > 0) {
            var dateTime = $("#datetime").val();
            if(dateTime==''){
                layer.msg('请选择日期');
                return;
            }
            var times = dateTime.split("~");
            var index = layer.load(3);
            $.post(contextPath + "/pe/racklist", {userid: buildid, startTime: times[0], endTime: times[1]}, function (data) {
                layer.close(index);
                $("#dg").datagrid("loadData", data.rows);
            });
        } else {
            layer.msg("请选择客户")
        }
    }
    function showHis(obj,roomName,idcPowerRoomCode){
        openDialogView('查看'+obj.name+'历史用电',contextPath+'/pe/torackhis/'+obj.getAttribute("rackid"),"800px","400px");
    }
</script>
</body>
</html>