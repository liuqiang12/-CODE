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
    <div data-options="region:'west'" style="width: 260px" title="导航">
        <ul id="rtree" class="ztree" style="width:260px; overflow:auto;"></ul>
    </div>
    <div data-options="region:'center'" title="用电信息">
        <table id="dg" class="easyui-datagrid"
               data-options="fit:true,fitColumns:true,pagination:false,singleSelect:true,showFooter:false,toolbar:'#tb'">
            <thead>
            <tr>
                <th rowspan="2" data-options="field:'idcPowerRoomName',width:200">机房名字</th>
                <th colspan="3" data-options="width:100">读数</th>
                <th colspan="4" data-options="width:100,align:'center'">能耗</th>
                <th rowspan="2" data-options="field:'b_12',width:100,align:'center',formatter:function(value,row,index){

                   return '<a href=\'javascript:void(0)\' name=\''+row.idcPowerRoomName+'\' roomid=\''+row.idcPowerRoomId+'\' onclick=\'showHis(this)\'><i class=\'layui-icon\'>&#xe62c;</i>查看</a>'
                }">操作</th>
            </tr>
            <tr>
                <th data-options="field:'idcAmout',width:100">电表读数</th>
                <th data-options="field:'idcAirAdjustAmout',width:100">空调读数</th>
                <th data-options="field:'idcDeviceAmout',width:100">主设备读数</th>
                <th data-options="field:'idcBeforeDiff',width:100,formatter:function(value,row,index){
                if(value){
                  return value.toFixed(2)
                }
                }">总能耗</th>
                <th data-options="field:'idcAirAdjustBeforeDiff',width:100,formatter:function(value,row,index){
                        if(value!=null){
                         if(value<0){
                            return '<span style=\'color:red;font-size:16px\'>'+value+'</span>'
                         }else{
                          return value.toFixed(2)
                        }
                      }
                }">空调能耗</th>
                <th data-options="field:'idcDeviceBeforeDiff',width:100,formatter:function(value,row,index){
                   if(value){
                    return value.toFixed(2)
                  }

                }">主设备能耗</th>
                <th data-options="field:'b_11',width:100,formatter:function(value,row,index){
                        if(row.idcAirAdjustBeforeDiff>=0&&row.idcDeviceBeforeDiff>0){
                            return ((row.idcAirAdjustBeforeDiff+row.idcDeviceBeforeDiff)/row.idcDeviceBeforeDiff).toFixed(2);
                        }else{
                          return ''
                        }
                }">PUE</th>

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
    Date.prototype.add = function (part, value) {
        value *= 1;
        if (isNaN(value)) {
            value = 0;
        }
        switch (part) {
            case "y":
                this.setFullYear(this.getFullYear() + value);
                break;
            case "m":
                this.setMonth(this.getMonth() + value);
                break;
            case "d":
                this.setDate(this.getDate() + value);
                break;
            case "h":
                this.setHours(this.getHours() + value);
                break;
            case "n":
                this.setMinutes(this.getMinutes() + value);
                break;
            case "s":
                this.setSeconds(this.getSeconds() + value);
                break;
            default:

        }
        return this;
    }
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
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
                , max: -1
                ,range: '~' //或 range: '~' 来自定义分割字符
                , value: mydate.Format("yyyy-MM-dd")+" ~ "+mydate.Format("yyyy-MM-dd")
            });
        })
        var date = new Date();
        //initLine();
//        $("#datetime").datebox({value: date.add("d", -1).Format("yyyy-MM-dd")})
        var tree = $("#rtree").rtree({
            r_type: 1
            , onClick: function (iteam, treeId, treeNode) {
                if (treeNode.id.indexOf("building_") > -1) {
                    buildid = treeNode.id.split("building_")[1];
                    getData();
                }
            }
        });

        var timeer = setInterval(function () {
                    if (typeof tree.rtree != 'undefined' && tree.rtree != null) {
                        clearInterval(timeer)
                        var nodes = tree.rtree.getNodesByParamFuzzy("id", "building_", null);
                        if (nodes != null && nodes.length > 0) {
                            $("#" + nodes[0].tId + "_a").trigger('click');
                        }

                    }
                }, 200
        )
    })

    function showHis(obj,roomName,idcPowerRoomCode){
//        layer.open({
//            type: 1,
//            content: $('#line') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
//        });
        openDialogView('查看'+obj.name+'历史用电',contextPath+'/pe/toroomhis/'+obj.getAttribute("roomid"),"800px","400px");
    }
    function getData() {
        if (buildid && buildid > 0) {
            var dateTime = $("#datetime").val();
            if(dateTime==''){
                layer.msg('请选择日期');
                return;
            }
            var times = dateTime.split("~");
            var index= layer.load(3);
            $.post(contextPath + "/pe/roomlist", {buildid: buildid,startTime: times[0], endTime: times[1]}, function (data) {

                $("#dg").datagrid("loadData", data.rows);
                layer.close(index);
            });
        } else {
            layer.msg("请选择机楼")
        }

    }

</script>
</body>
</html>