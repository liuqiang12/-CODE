<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script src="<%=request.getContextPath() %>/framework/layui/layui.js"></script>
    <link rel="stylesheet"
          href="<%=request.getContextPath() %>/framework/layui/css/layui.css"/>
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
                <tr style="">
                    <td class="kv-label">报表类型</td>
                    <td class="kv-content">
                        <input type="hidden" id="reportType" name="reportType" value="busicapweek" />
                        <input class="easyui-textbox"  value="节假日报表" data-options="required:true,width:200,readonly:true" />
                    </td>
                </tr>
                <tr style="display: none">
                    <td class="kv-label">端口类型</td>
                    <td class="kv-content">
                        <select class="easyui-combotree" id="portType" name="portType" data-options="required:true,width:200<c:if test="${not empty mode}">,readonly:true</c:if>">
                            <option value="0" <c:if test="${mode=='port'}">selected</c:if>>物理端口</option>
                            <option value="1" <c:if test="${mode=='busi'}">selected</c:if>>业务端口</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">端口</td>
                    <td class="kv-content">
                        <input id="ports" name="ports" style="width:300px;"/>
                        <%--<select id="ports" class="easyui-combotree" style="width:200px;"--%>
                        <%--data-options="url:'get_data.php',e"></select>--%>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">统计时间</td>
                    <td class="kv-content">
                        <input class="easyui-datetimebox" name="startTime" id="startTime"
                               data-options="showSeconds:false,required:true,onChange:function(){
                                 $('#endTime').datetimebox('showPanel')
                               },prompt:'默认昨日',formatter:myformatter,parser:myparser" value="" style="width:200px">-
                        <input class="easyui-datetimebox" name="endTime" id="endTime"
                               data-options="prompt:'默认昨日',required:true,formatter:myformatter,parser:myparser" style="width:200px">
                        <input type="hidden" value="all" id="cyc" name="cyc"/>
                    </td>
                </tr>
                <%--<tr>--%>
                    <%--<td class="kv-label">周期</td>--%>
                    <%--<td class="kv-content">--%>
                        <%--<select class="easyui-combobox" name="cyc" id="cyc" data-options="required:true,width:200">--%>
                            <%--<option value="1hour">1小时</option>--%>
                            <%--<option value="2hour">2小时</option>--%>
                            <%--<option value="3hour">3小时</option>--%>
                            <%--<option value="24hour">天</option>--%>
                            <%--<option value="all">自定义时间</option>--%>
                        <%--</select>--%>
                    <%--</td>--%>
                <%--</tr>--%>
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
<div id="toolbar">
    <input class="easyui-textbox" id="keyword" style="width:200px"/>
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
    function addDate(d, days) {
        var now = new Date(d.getTime() + (days*24*60*60*1000));
        return now;
    }
    $(function () {
//        layui.use('laydate', function () {
//            var laydate = layui.laydate;
//            var mydate  = new Date();
////            mydate=mydate.add("d",-1);
//            //常规用法
//            laydate.render({
//                elem: '#datetime'
//                , zIndex: 99999999
//                ,type:'datetime'
//                ,format:'yyyy-MM-dd HH:mm:ss'
//                , btns: ['now', 'confirm']
////                , max: -1
//                ,range: '~' //或 range: '~' 来自定义分割字符
//                //, value: mydate.add()Format("yyyy-MM-dd hh:mm:ss")+" ~ "+mydate.Format("yyyy-MM-dd hh:mm:ss")
//            });
//        })

//        $("#startTime").datetimebox({
//            required:true
////            onSelect:function(now1){
////                var now =  addDate(now1,+6)
////                var tstr =+now.getFullYear()+"-"+(now.getMonth()+1) + "-" + now.getDate();
////                $("#endTime").datebox('setValue',tstr)
////            }
//
//        })
//                .datebox('calendar').calendar({
//            validator: function(date){
//                var now = new Date();
//                var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate()-6);
//                return  date<=d2;
//            }
//        }).calendar('moveTo',new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate()-6));

        var isMuilSelect = true ;

        $("#ports").combotree({
            url: contextPath+'/busiPort/listbytree?isSimple=1',
            checkbox:true,
            cascadeCheck:false,
            valueField: 'id',
            textField: 'name',
            required:true,
            multiple:true,
            onBeforeCheck:function(node){
                if(node.id.indexOf("g_")>-1){
                    return false;
                }
            }
        });

        $("#btn").linkbutton({
            onClick:function(){
                var flag = $("#form").form('enableValidation').form('validate');
                if(flag){
                    var datetype= $("#cyc").val();;//("getValue");
                    if(datetype=='all'){
                        $("#form").attr("action", contextPath + "/capreport/reportCustomer");
                    }else{
                        $("#form").attr("action", contextPath + "/capreport/report");
                    }
                    $("#form").submit();
                }
            }
        })
    })
</script>
</body>
</html>