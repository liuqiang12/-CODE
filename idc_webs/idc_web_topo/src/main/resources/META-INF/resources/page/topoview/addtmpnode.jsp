<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <style>
        table.icon-picker-table td {
            width: 48px;
            text-align: center;
            vertical-align: middle;
        }
        .icon-container {
            border: 1px solid #95b3d7;
            border-radius: 4px;
            padding: 2px 2px;
            width: 46px;
            height: 46px;
        }
        .icon-container-checked {
            border: 1px solid;
            border-radius: 4px;
            padding: 2px 2px;
            width: 46px;
            height: 46px;
            background-color: #fff;
            border-color: rgba(23,52,92,0.8);
            box-shadow: 0 3px 6px rgba(0,0,0,0.1) inset, 0 0 8px rgba(23,52,92,0.6);
            -moz-box-shadow: 0 3px 6px rgba(0,0,0,0.1) inset,0 0 8px rgba(23,52,92,0.6);
            -webkit-box-shadow: 0 3px 6px rgba(0,0,0,0.1) inset, 0 0 8px rgba(23,52,92,0.6);
        }
    </style>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="viewForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">对象类别</td>
                    <td class="kv-content">
                        <input type="hidden" name="viewid" value="${viewId}"/>
                        <select class="easyui-combobox" data-options="required:true,width:200" name="objtype"
                                id="objtype">
                            <option value="0">设备</option>
                            <option value="2">云图</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">关联对象</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" data-options="required:true,width:200" name="objfid"
                                id="objfid">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">视图坐标</td>
                    <td class="kv-content">
                        <input id="viewposition" name="viewposition" class="easyui-textbox" data-options="required:true"
                               readonly value="${x}_${y}"
                               style="width:200px;"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <table class="icon-picker-table">
                <tbody>
               <%-- <c:set var="end" value="20"></c:set>
                <c:set var="rowlength" value="10"></c:set>
                <c:forEach var="i" begin="1" end="${end}" step="1">
                    <c:if test="${i==0}">
                        <tr>
                    </c:if>
                        <td>
                            <div id="icon-container-h3_cpu" class="icon-container"><img id="icon-h3_cpu"
                                                                                        src="${contextPath}/moudles/topo/topoimg/topo_${i}.png"
                                                                                        title="topo_${i}" width="38" height="38"
                                                                                        class="icon-item"></div>
                            <input name="icon" type="radio" value="topo_${i}.png" id="h3_cpu">
                        </td>
                        <c:if test="${i==end || i%rowlength==0}">
                             </tr>
                            <c:if test="${i!=end}">
                                <tr>
                            </c:if>
                    </c:if>
                </c:forEach>--%>
                <tr id="tool">
                    <td colspan="10" style="text-align: center">
                        <a href="javascript:void(0)" onclick="insertcode()">加载更多</a>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var page = 0;
    var pageSize = 10;
    function insertcode() {
        for(var i = 0;i<30;i++){
            var $tr = $("<tr></tr>");
            for(var index=1;index<=10;index++){
                var j =(page*pageSize)+index;
                if(j>2500) {
                    $("#tool td").empty().append("已经到最后了");
                    break;
                }else{
                    $tr.append('<td><div id="icon-container-h3_cpu" class="icon-container">' +
                            '<img id="icon-h3_cpu"  src="${contextPath}/moudles/topo/topoimg/topo_'+j+'.png" title="topo_'+j+'" width="38" height="38"  class="icon-item"></div>' +
                            '<input name="icon" type="radio" value="topo_'+j+'.png" id="h3_cpu"></td>')
                }
            }
            $("#tool").before($tr)
            page++;
        }
    }
    var viewId = "${viewId}";
    $(function () {
        insertcode();
        $(".icon-picker-table tbody").on('click','.icon-container',function(){
            $(this).next().click();
        })
        $(".icon-picker-table tbody").on('click',"input[name='icon']",function(){
            $(".icon-picker-table div").removeClass("icon-container-checked").addClass("icon-container");
            $(this).prev().removeClass("icon-container").addClass("icon-container-checked");
        })
        function getOptions(type) {
            var options = {}
            if (type == 0) {
                options = {
                    idField: 'id',
                    textField: 'name',
                    url: contextPath + '/device/list.do',
                    pagination: false,
                    columns: [[
                        {field: 'id', hidden: true, width: 60},
                        {field: 'objtype', hidden: true, width: 60},
                        {field: 'name', title: '设备名字', width: 200}
                    ]],
                    loadFilter: function (data) {
                        var datas = [];
                        console.log(data)
                        $.each(data.rows, function (index, iteam) {
                            datas.push({
                                name: iteam.NAME,
                                id: iteam.DEVICEID,
                                objtype: 0
                            })
                        });
                        return datas;
                    }
                };
            } else {
                options = {
                    idField: 'id',
                    textField: 'name',
                    url: contextPath + '/topoview/list',
                    pagination: false,
                    columns: [[
                        {field: 'id', hidden: true, width: 60},
                        {field: 'objtype', hidden: true, width: 60},
                        {field: 'name', title: '视图名字', width: 200}
                    ]],
                    loadFilter: function (data) {
                        console.log(data)
                        var datas = [];
                        $.each(data, function (index, iteam) {
                            if(iteam.viewid!=viewId){
                                datas.push({
                                    name: iteam.viewname,
                                    id: iteam.viewid,
                                    objtype: 2
                                })
                            }
                        });
                        return datas;
                    }
                };
            }
            return options;
        }

        $("#objfid").combogrid({
            idField: 'id',
            textField: 'name',
            panelWidth: 350,
            panelHeight: 220,
            multiple: false,
//            fit: true,
            mode: 'local',
            fitColumns: true,
            filter:function(q,row){
                var opts = $(this).combogrid('options');
                return row[opts.textField].indexOf(q) > -1;
            },
            columns: [[
                {field: 'id', hidden: true, width: 60},
                {field: 'objtype', hidden: true, width: 60},
                {field: 'name', title: '设备名字', width: 200}
            ]],
        });
        $("#objfid").combogrid('reset').combogrid(getOptions(0))
        $("#objtype").combobox({
            onChange: function (newValue, oldValue) {
                $("#objfid").combogrid('reset').combogrid(getOptions(newValue))
            }
        });
    })
    function doSubmit(fn) {
        var objtype = $("#objtype").combobox("getValue");
        var icon = $("input[name='icon']:checked").val();
        var objfid = $("#objfid").combogrid("getValue");
        var objfidText = $("#objfid").combogrid("getText");
        $('#viewForm').form('submit', {
            url: "<%=request.getContextPath() %>/topoview/addObj",
            success: function (data) {
                data = JSON.parse(data)
                if (data.state) {
                    top.layer.msg("添加节点成功")
                    fn({
                        id: data.msg,
                        name: objfidText,
                        objfid: objfid,
                        objpid: data.msg,
                        objtype: objtype,
                        icon: icon
                    });
                    var parentWin = top.winref[window.name];
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                } else {
                    top.layer.msg(data.msg)
                }
            }
        });
    }
</script>
</body>
</html>