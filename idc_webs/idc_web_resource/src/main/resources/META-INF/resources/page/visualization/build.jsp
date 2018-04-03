<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript">
    </script>
    <Style>
        body{
            margin: 0px;
        }
        .idcpanel {
            /*width: 90%;*/
            /*height: 100%;*/
            display: -webkit-flex;
            /* Safari */
            display: flex;
            flex-wrap: wrap;
            /*justify-content: lef;*/
        }

        .idc {
            width: 550px;
            height: 250px;
            /* //float: left; */
            margin-left: 5px;
            margin-top: 20px;
            border: 1px solid black;
            display: -webkit-flex;
            /* Safari */
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;
            flex-flow: row;

            /*伸缩项目单行排列*/
        }

        .idc img {
            border-bottom: 1px solid black;
            border-right: 2px dotted black;
            width: 350px;
            height: 250px;
            cursor: pointer
        }

        .idc div {
            flex: 1;
            /*这里设置为占比1，填充满剩余空间*/
        }

        .title {
            text-align: center;
            border-bottom: 1px solid black;
            font-size: 18px;

        }

        .pro {
            margin-left: 5px;
            margin-top: 10px;
        }

        .floars {
            display: none;
        }

        .floar {
            /*width: 100%;*/
            /* height: 200px; */
            /* //float: left; */
            /*margin-left: 5px;*/
            margin-bottom: 20px;
            border: 1px solid black;
            display: -webkit-flex;
            /* Safari */
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;
            background-color: rgba(72, 72, 78, 0.5);
            color:white;
            /* flex-flow: row; */
            /*伸缩项目单行排列*/
        }

        .floartitle {
            width: 100px;
        }

        .rooms {
            flex: 1;
            /* //width: 90%; */
            display: -webkit-flex;
            /* Safari */
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;

        }

        .room {
            width: 100px;
            height: 95px;
            border: 1px solid black;
            margin: 1px;
            padding: 3px;
            background-color: rgb(103, 98, 105);
        }

        .room:hover {
            /* border: 2px solid #2E82D1;
            height: 93px;
            width: 98px; */
            background-color: #92ded1
        }

        .roomtitle {
            font-size: 12px;
            font-weight: bold;
            word-wrap: break-word;
        }

        .roomdesc {
            margin: 2px;
            font-size: 12px;
        }
    </Style>
    <title>可视化</title>
</head>
<body class="easyui-layout" fit="true">
 <div data-options="region:'center'">
     <div class="idcpanel animated">
         <c:forEach var="build" items="${builds}">
             <div class="idc">
                 <img src="${contextPath}/framework/themes/img/build.jpg" id="${build.id}"/>

                 <div>
                     <div class="title">${build.name}</div>
                     <div class="pro">机房数:${build.roomcount}</div>
                     <div class="pro">机架数:${build.rackcount}</div>
                     <div class="pro">机架使用率:${build.rackusage}%</div>
                 </div>
             </div>
         </c:forEach>
     </div>
     <div class="floars" style="padding:10px">
         <div> 返回上级</div>
     </div>
 </div>


</body>
<script>
    $(function () {
        $(".idc img").click(function () {
            var buildid = $(this).attr("id");
            $.getJSON(contextPath + "/visualization/getRooms/" + buildid, function (data) {
                $(".floars").empty();
                for (var k in data) {
                    var floar = $('<div class="floar"></div>');
                    var floartitle = $('<div class="floartitle">' + k + 'F</div>');
                    floar.append(floartitle)
                    var rooms = $(' <div class="rooms"></div>');
                    floar.append(rooms)
                    console.log(data[k])
                    for (var i = 0; i < data[k].length; i++) {
                        var room = $('<div class="room" roomid="'+data[k][i].id+'">' +
                                '<div class="roomtitle">' + data[k][i].sitename + '</div>' +
                                '<div class="roomdesc">机架数:' + data[k][i].rackcount + '</div>' +
                                '<div class="roomdesc">空闲率:' + data[k][i].rackspare + '</div> ' +
                                '</div>');
                        rooms.append(room);
                    }
                    $(".floars").append(floar)
                }
                $(".idcpanel").hide({
                    duration: 2000,
                })
                $(".floars").show({
                    duration: 2000,
                })
            });
        })
        $(".room").click(function () {
            var id  = $(this).attr("roomid");
            window.open(contextPath+'/roomlayout/'+id);
        })
    })
    $(".floars").on('click' ,'.room',function(e){
        var id  = $(this).attr("roomid");
        window.open(contextPath+'/roomlayout/'+id);
    })
</script>
</html>