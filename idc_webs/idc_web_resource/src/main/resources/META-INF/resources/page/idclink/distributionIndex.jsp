<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script src="<%=request.getContextPath()%>/framework/vuejs/vue.min.js"></script>
    <script src="<%=request.getContextPath()%>/framework/jtopo/jtopo.min.js"></script>
    <link href="<%=request.getContextPath()%>/framework/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/moudles/topo/css/topo.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/moudles/topo/css/font-awesome.min.css" rel="stylesheet">
    <script src="<%=request.getContextPath()%>/framework/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/idclink/jumpFiberPortOrConnector.js"></script>
</head>
<script type="text/javascript">
    //是否可以分配资源
    var flag = "${flag}";
    //机房ID
    var roomId = "${roomId}";
    //A端机架IDs
    var rackids = "${rackid}";
    //重新加载TOPO图
    function reloadWin() {
        window.location.reload();
    }
    //单击确认   表示要建立这些链路信息   并清空session中保存值
    function doSubmit(){
        $.post(contextPath + "/idclink/cleanSession", {}, function (result) {
            if (result.state) {
                closeParentWin();
            }
        });
    }
    function cancelWin(){
        $.post(contextPath + "/idclink/delete", {}, function (result) {
            if (result.state) {
                closeParentWin();
            }
        });
    }
    function closeParentWin(){
        var topIndex = top.layer.getFrameIndex("linkrack")-1;
        top.layer.close(topIndex);
        var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        top.layer.close(indexT); //再执行关闭
    }
    //style="background:url(<%=request.getContextPath()%>/moudles/topo/img/bg11.png) top center no-repeat;overflow: hidden;background-size:cover"
</script>
<body>
<canvas id="canvas" style="width: 100%;height: 100%;"></canvas>
<%--<div id="detaildiv">--%>
<%--<div name="topo_auto_layout" class="panel panel-primary" v-if="typeof obj.name!='undefined'"--%>
<%--style="left: auto; right: 0px; top: 15px; bottom: auto;"--%>
<%--:style="{styleobj}">--%>
<%--<div name="head" class="panel-heading">--%>
<%--{{title}}--%>
<%--<button type="button" class="close" aria-hidden="true" @click="hide">--%>
<%--×--%>
<%--</button>--%>
<%--</div>--%>
<%--<div class="panel-body ">--%>
<%--<table class="table">--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th>{{obj.name}}:</th>--%>
<%--<th></th>--%>
<%--</tr>--%>

<%--</thead>--%>
<%--<tbody>--%>
<%--<tr v-if="obj.type=='link'">--%>
<%--<th>{{obj.nodeA.text}}流量:</th>--%>
<%--<th>{{obj.nodeA.flows}}</th>--%>
<%--</tr>--%>
<%--<tr v-if="obj.type=='link'">--%>
<%--<th>{{obj.nodeZ.text}}流量:</th>--%>
<%--<th>{{obj.nodeA.flows}}</th>--%>
<%--</tr>--%>

<%--</tbody>--%>
<%--</table>--%>

<%--</div>--%>
<%--</div>--%>
<%--</div>--%>

<ul name="topo_rightMenu" id="topo_rightMenu" class="dropdown-menu" role="menu"
    style="left: 441px; top: 129px; display: none;">
    <li v-for="(rightMenu,index) in rightMenus" v-if="rightMenu.show==true" v-on:click="menuclick(rightMenu)">
        <div class="buttonSquare bluelight">
            <i class="fa fa-info"></i>
            <span>{{rightMenu.name}}</span>
        </div>
    </li>
</ul>
</body>

<script id='code'>
    //右键菜单
    var topo_rightMenu = new Vue({
        el: '#topo_rightMenu',
        computed: {},
        data: {
            rightMenus: [
                {name: '配置端口', show: true, eventName: 'choosePort', type: 'node'},
                {name: '配置端子', show: true, eventName: 'chooseConnector', type: 'node'},
                {name: '重新建立链路', show: false, eventName: 'reBuildingLink', type: 'node'},
                {name: '删除链路', show: false, eventName: 'delLink', type: 'link'}
            ]
        },
        methods: {
            menuclick: function (obj) {
                eventHandle(obj, currentNode)
            }
        }
    });
    var currentNode = null;
    var nodes = [];//节点数组
    var links = [];//连线数组
    var stage = "";
    $(function(){
        /********************************创建画布*****************************/
        $("#canvas").attr("width", $("#canvas").parent().width());
        $("#canvas").attr("height", $(window).height());
        var canvas = document.getElementById('canvas');
        stage = new JTopo.Stage(canvas);
        //显示工具栏
        //showJTopoToobar(stage);
        var scene = new JTopo.Scene();
        //开启鹰眼
        stage.eagleEye.visible = true;
        stage.add(scene);
        /*******************************组装节点、连线数据*********************/
            //节点
            <c:forEach items="${nodes}" var="node">
        var nodePortStr = '${node.nodePorts}';
        nodePortStr = nodePortStr.replace('[','').replace(']','').replace(" ","");
        nodes.push({
            name: '${node.name}',
            <c:choose>
            <c:when test="${node.typeStr=='racks_group'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/jijia.png',
            </c:when>
            <c:when test="${node.typeStr=='rack_df'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/rack_odf.png',
            </c:when>
            <c:when test="${node.typeStr=='rack_cabinet'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/rack_net.png',
            </c:when>
            <c:when test="${node.typeStr=='rack_equipment'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/rack.png',
            </c:when>
            <c:when test="${node.typeStr=='rack_pdu'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/rack_pdu.png',
            </c:when>
            <c:when test="${node.typeStr=='equipment'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/py_VR1.png',
            </c:when>
            <c:when test="${node.typeStr=='idcconn'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/py_VR1.png',
            </c:when>
            <c:when test="${node.typeStr=='idcport'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/py_VR1.png',
            </c:when>
            <c:otherwise>
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/mypc.png',
            </c:otherwise>
            </c:choose>
            type: '${node.typeStr}',
            key: '${node.key}',
            x: 150,
            y: 150,
            id: '${node.id}',
            roomId: '${node.roomId}',
            nodePortStr:nodePortStr
        })
        </c:forEach>
        console.log(nodes);
        //连线
        <c:forEach items="${links}" var="link">
        var portStr = '${link.portStrList}';
        portStr = portStr.replace('[','').replace(']','').replace(" ","");
        var postList = portStr.split(",");
        links.push({
            name:'${link.name}',
            aname:'${link.a.name}',
            zname:'${link.z.name}',
            akey:'${link.a.key}',
            zkey:'${link.z.key}',
            linkPortStr:portStr,
            portSize:postList.length
        })
        </c:forEach>
        //创建节点
        $.each(nodes, function (i, node) {
            createNode(node);
        })
        //创建连线
        $.each(links, function (i, link) {
            createLink(link);
        })
        var roots = scene.childs;
        //设置机架组位置
        $.each(roots, function (ix, iteam) {
            if (iteam.key == "racks_${rackid}") {
                iteam.setLocation(500, 100);
                JTopo.layout.layoutNode(scene, iteam, true);
            }
        })
        stage.click(function (event) {
            if (event.button == 0) {
                // 关闭弹出菜单（div）
                $("#topo_rightMenu").hide();
            }
        });
        scene.addEventListener('mouseup', function (e) {
            if (e.target && e.target.layout) {
                JTopo.layout.layoutNode(scene, e.target, true);
            }
        });
        scene.doLayout(JTopo.layout.TreeLayout('down', 250, 150));
    })
    /*************************自定义函数************************/
    function createNode(node) {
        var scene = stage.childs[0];
        var nodeName = node.name;
        var newNode = new JTopo.Node(nodeName);
        if (node.type.indexOf('rack') > -1) {
            newNode.setSize(30, 50);
        }else{
            newNode.setSize(30, 26);
        }
        newNode.fontColor = "5,46,255";//节点名字颜色
        // newNode.fontColor = JTopo.util.randomColor();
        newNode.font = '14px 微软雅黑';
        newNode.key = node.key;
        newNode.type = node.type;
        newNode.id = node.id;
        newNode.roomId = node.roomId;
        newNode.nodePortStr = node.nodePortStr;
        newNode.setLocation(100, 100);
        newNode.setImage(node.img);
        newNode.zIndex = 150;
        scene.add(newNode);
        newNode.addEventListener('mouseup', function (event) {
            currentNode = this;
            if (flag != 'view') {
                handler(event);
            }
        });
        newNode.addEventListener('dbclick', function (event) {
            showdetail(event);
        });
        // newNode.addEventListener('click', function (event) {
        //     currentNode = this;
        //     //rightshow(event)
        // });
    }
    function createLink(link){
        var scene = stage.childs[0];
        var nodeList = scene.childs.filter(function (e) {
            return e instanceof JTopo.Node;
        });
        var nodeAs = nodeList.filter(function (e) {
            if (e.text == null) return false;
            return e.text == link.aname;
        });
        var nodeZs = nodeList.filter(function (e) {
            if (e.text == null) return false;
            return e.text == link.zname;
        });
        if (nodeAs.length > 0 && nodeZs.length > 0) {
            var linkNode = new JTopo.FoldLink(nodeAs[0], nodeZs[0]);
            if(link.portSize>1){
                linkNode.lineWidth = 5;
            }else{
                linkNode.lineWidth = 2;
            }
            linkNode.linkPortStr = link.linkPortStr;
            linkNode.name = link.name;
            linkNode.strokeColor = "222,139,19";//设置连线颜色
            linkNode.fontColor = "5,46,255";//设置名字颜色
            linkNode.font = '14px 微软雅黑';
            linkNode.bundleOffset = 60; // 折线拐角处的长度
            linkNode.bundleGap = 20; // 线条之间的间隔
            // linkNode.strokeColor = JTopo.util.randomColor();
            // linkNode.fontColor = JTopo.util.randomColor();
            linkNode.addEventListener('dbclick', function (event) {
                var aKey = event.target.nodeA.key;
                var zKey = event.target.nodeZ.key;
                var portStr = event.target.linkPortStr;
                var url = contextPath + '/idclink/getPreLinksByAZ';
                var data = {aKey:aKey,zKey:zKey,portStr:portStr};
                openDialogUsePostView('链接信息',url, '1000px', '530px',data,'showLinkInfo');
            });
            linkNode.addEventListener('mouseover', function (event) {
                this.text = event.target.nodeA.text + ">>" + event.target.nodeZ.text;
            });
            linkNode.addEventListener('mouseout', function (event) {
                this.text = "";
            });
            linkNode.addEventListener('mouseup', function (event) {
                currentNode = this;
                if (flag != 'view') {
                    handler(event);
                }
            });
            scene.add(linkNode);
        }
    }
    //右键菜单
    function handler(event) {
        var target = event.target;
        if (target instanceof JTopo.Node) {
            for (var i = 0; i < topo_rightMenu.rightMenus.length; i++) {
                if (topo_rightMenu.rightMenus[i].type == 'node') {
                    if(topo_rightMenu.rightMenus[i].eventName == 'reBuildingLink'||
                        (topo_rightMenu.rightMenus[i].eventName == 'chooseConnector' && target.text != 'racks')||
                        (topo_rightMenu.rightMenus[i].eventName == 'choosePort'&&target.text.indexOf('ODF')==-1)){
                        topo_rightMenu.rightMenus[i].show = false;
                    }else{
                        topo_rightMenu.rightMenus[i].show = true;
                    }
                } else {
                    topo_rightMenu.rightMenus[i].show = false;
                }
            }
        }else if (target instanceof JTopo.Link) {// 链路
            for (var i = 0; i < topo_rightMenu.rightMenus.length; i++) {
                if (topo_rightMenu.rightMenus[i].type == 'link') {
                    topo_rightMenu.rightMenus[i].show = true;
                } else {
                    topo_rightMenu.rightMenus[i].show = false;
                }
            }
        }
        if (event.button == 2) {// 右键
            //只有ODF和机架组才能配端子、端口  链路
            if(event.target.text=='racks'||event.target.text.indexOf('ODF')>-1||target instanceof JTopo.Link){
                // 当前位置弹出菜单（div）
                $("#topo_rightMenu").css({
                    top: event.pageY,
                    left: event.pageX
                }).show();
            }else{
                $("#topo_rightMenu").hide();
            }

        }
    }
    //右键菜单事件分发
    function eventHandle(event, obj) {
        if (event.eventName == 'choosePort') {//配置端口
            addPortLink(event, obj);
        } else if (event.eventName == 'chooseConnector') {//配置端子
            addConnectorLink(event, obj);
        } else if (event.eventName == 'reBuildingLink') {//重新建立链路关系
            $.post(contextPath + "/idclink/delete", {}, function (result) {
                if (result.state) {
                    reloadWin();
                }
            });
        }else if(event.eventName == 'delLink'){
            deleteIdcLink(event, obj);
        }
    }
    //查看节点信息
    function showdetail(obj) {
        var target = obj.target;
        // if (target.key.indexOf('rack') > -1) {
        //     if (target.key.indexOf('rack') > -1 && target.key.indexOf('-') == -1) {
        //         var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + target.id + "&businesstype=other&buttonType=view";
        //         openDialogShowView2d('查看机架信息', url, '800px', '530px', '查看机架视图');
        //     } else {
        //         var url = contextPath + '/idcRack/getPreRackListByRackIds?rackIds=' + target.id;
        //         openDialogView('机架组机架列表', url, '800px', '530px');
        //     }
        // } else if (target.key.indexOf('device') > -1) {
        //     var url = contextPath + "/device/deviceDetails.do?id=" + target.id + "&buttonType=view&deviceclass=1";
        //     openDialogView('设备信息', url, '800px', '530px');
        // }

        if(target.type=='racks_group'){//机架组
            var url = contextPath + '/idcRack/getPreRackListByRackIds?rackIds=' + target.id;
            openDialogView('机架组机架列表', url, '800px', '530px');
        }else if(target.type=='equipment'){//设备
            var url = contextPath + "/device/deviceDetails.do?id=" + target.id + "&buttonType=view&deviceclass=1";
            openDialogView('设备信息', url, '800px', '530px');
        }else if(target.type=='rack_df'){//ODF架
            var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + target.id + "&businesstype=odf&buttonType=view";
            openDialogShowView2d('查看机架信息', url, '800px', '400px', '查看机架视图');
        }else{//客户架或网络架
            var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + target.id + "&businesstype=other&buttonType=view";
            openDialogShowView2d('查看机架信息', url, '800px', '530px', '查看机架视图');
        }
    }
    //右边窗口  节点详细信息
    // function rightshow(event) {
    //     var node = event.target;
    //     var obj = {};
    //     if (node instanceof JTopo.Node) {
    //         obj.name = node.text;
    //         obj.type = 'device';
    //
    //     } else if (node instanceof JTopo.Link) {
    //         obj.name = node.nodeA.text + ">>" + node.nodeZ.text;
    //         obj.nodeA = node.nodeA;
    //         obj.nodeZ = node.nodeZ;
    //         obj.type = 'link';
    //     }
    //     detail.setData(obj)
    // }

</script>

</html>