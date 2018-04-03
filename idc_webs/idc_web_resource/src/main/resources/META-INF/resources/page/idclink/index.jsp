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
</head>
<body style="background:url(<%=request.getContextPath()%>/moudles/topo/img/bg1.jpg) top center no-repeat;overflow: hidden;background-size:cover">
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
    var topo_rightMenu = new Vue({
        el: '#topo_rightMenu',
        computed: {},
        data: {
            rightMenus: [
                {name: '查看详细信息', show: true, eventName: 'show', type: 'node'},
                {name: '新建跳线', show: true, eventName: 'show', type: 'node'},
                {name: '新建跳接', show: true, eventName: 'show', type: 'node'},
                {name: '配置PWR', show: true, eventName: 'show', type: 'node'},
                {name: '查看链路信息', show: true, eventName: 'del', type: 'link'},
            ]
        },
        methods: {
            menuclick: function (obj) {
                eventHandle(obj, currentNode)
            }
        }
    });
    var detail = new Vue({
        el: '#detaildiv',
        computed: {
            styleobj: function () {
                var style = {}
                if (typeof this.obj.name === "undefined") {
                    style['display'] = 'none'
                }
                return style
            }
        },
        data: {
            title: '节点信息',
            obj: {},
        },
        methods: {
            hide: function () {
                this.obj.name = undefined;
            },
            setData: function (query) {
                if (query.type == 'device') {
                    this.obj = {
                        name: query.name,
                        type: query.type,
                    };
                } else if (query.type == 'link') {
                    //AJAx请求数据
                    this.obj = {
                        name: query.name,
                        type: query.type,
                        nodeA: query.nodeA,
                        nodeZ: query.nodeZ,
                    };
                }

            }
        }
    })
    /****
     * 事件分发
     */
    function eventHandle(event, obj) {
        console.log(obj);
        parent.layer.open({
            type: 2,
            title: '链路',
            area: ['1200px', '630px'],
            fixed: false, //不固定
            maxmin: true,
            content: 'equipments/index.html'
        });
    }

    function showdetail(obj) {
        var url =contextPath;
        var target = obj.target;
        if(target.type.indexOf('rack_')>-1){
            var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + target.id + "&businesstype=other&buttonType=view";
            openDialogShowView2d('查看机架信息', url, '800px', '530px', '查看机架视图');
            //openDialogView('查看机架信息', contextPath + '/idcRack/edit/equipment/'+target.id,'800px','530px')
//            url+="/rack/"+target.id;
        }
//        if(target.type.indexOf('equipment')){
//            url+="//"+obj.id;
//        }
//        if(target.type.indexOf('rack_')){
//            url+="/rack/"+obj.id;
//        }

//        parent.layer.open({
//            type: 2,
//            area: ['1200px', '630px'],
//            fixed: false, //不固定
//            maxmin: true,
//            content: url //context+'/rack/'+obj.target.id
//        });
    }
    var currentNode = null;
    $(document).ready(function () {
        $("#canvas").attr("width", $("#canvas").parent().width());
        $("#canvas").attr("height", $(window).height());
        var cx = 300, cy = $(window).height() / 2;
        var canvas = document.getElementById('canvas');
        var nodes = [];
        var links = [];
        <c:forEach items="${nodes}" var="node">
        nodes.push({
            name: '${node.name}',
            <c:choose>
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
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/webserver.png',
            </c:when>
            <c:when test="${node.typeStr=='idcconn'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/py_VR.png',
            </c:when>
            <c:when test="${node.typeStr=='idcport'}">
            img: '<%=request.getContextPath()%>/js/roomlayout/icon/py_VR.png',
            </c:when>
            <c:otherwise>
            img: 'img/mo/GGSN_4.png',
            </c:otherwise>
            </c:choose>
            type: '${node.typeStr}',
            key: '${node.key}',
            x: 150,
            y: 150,
            id: '${node.id}',
        })
        </c:forEach>
        <c:forEach items="${links}" var="link">
        links.push({
            aname: '${link.a.name}',
            zname: '${link.z.name}',
            akey: '${link.a.key}',
            zkey: '${link.z.key}'
        })
        </c:forEach>
        var stage = new JTopo.Stage(canvas);
        //显示工具栏
        //showJTopoToobar(stage);

        var scene = new JTopo.Scene();
        //开启鹰眼
        stage.eagleEye.visible = true;
        stage.add(scene);


        function handler(event) {
            var target = event.target;

            if (target instanceof JTopo.Node) {
                for (var i = 0; i < topo_rightMenu.rightMenus.length; i++) {
                    if (topo_rightMenu.rightMenus[i].type == 'node') {
                        topo_rightMenu.rightMenus[i].show = true
                    } else {
                        topo_rightMenu.rightMenus[i].show = false;
                    }
                }
            }
            else if (target instanceof JTopo.Link) {// 链路
                for (var i = 0; i < topo_rightMenu.rightMenus.length; i++) {
                    if (topo_rightMenu.rightMenus[i].type == 'link') {
                        topo_rightMenu.rightMenus[i].show = true
                    } else {
                        topo_rightMenu.rightMenus[i].show = false;
                    }
                }
            }

            if (event.button == 2) {// 右键
                // 当前位置弹出菜单（div）
                $("#topo_rightMenu").css({
                    top: event.pageY,
                    left: event.pageX
                }).show();

            }
        }

        function rightshow(event) {
            var node = event.target;
            var obj = {};
            if (node instanceof JTopo.Node) {
                obj.name = node.text;
                obj.type = 'device';

            } else if (node instanceof JTopo.Link) {
                obj.name = node.nodeA.text + ">>" + node.nodeZ.text;
                obj.nodeA = node.nodeA;
                obj.nodeZ = node.nodeZ;
                obj.type = 'link';
            }
            detail.setData(obj)
        }

        $.each(nodes, function (i, node) {
            createNode(node)
        })

        $.each(links, function (i, link) {
            var LinkA = null, LinkZ = null;
            var nodes = scene.childs.filter(function (e) {
                return e instanceof JTopo.Node;
            });
            var nodeAs = nodes.filter(function (e) {
                if (e.text == null) return false;
                return e.text == link.aname;
            });
            var nodeZs = nodes.filter(function (e) {
                if (e.text == null) return false;
                return e.text == link.zname;
            });
            //所亦link
//            var links = scene.childs.filter(function (e) {
//                return e instanceof JTopo.Link;
//            });
            if (nodeAs.length > 0 && nodeZs.length > 0) {
                LinkA = nodeAs[0];
                LinkZ = nodeZs[0];
                var link = new JTopo.FoldLink(LinkA, LinkZ);
                link.addEventListener('mouseup', function (event) {
                    currentNode = this;
                    handler(event);
                });
                scene.add(link);
            }
        })

        function createNode(node) {
            var cloudNode;
            var nodeName = node.name;
            var scene = stage.childs[0];
            var nodes = scene.childs.filter(function (e) {
                return e instanceof JTopo.Node;
            });
            nodes = nodes.filter(function (e) {
                if (e.text == null) return false;
                return e.text == nodeName;
            });
            if (nodes.length > 0) {
                return;
            } else {
                var newNode = new JTopo.Node(node.name);
                newNode.setSize(30, 26);
                if(node.type.indexOf('rack_')>-1){
                    newNode.setSize(30, 50);
                }
                newNode.key = node.key;
                newNode.type = node.type;
                newNode.id = node.id;
                newNode.setLocation(100, 100);
                newNode.setImage(node.img);
                newNode.zIndex = 150;
                newNode.layout = {type: 'tree', width: 150, height: 120};
                scene.add(newNode);
                newNode.addEventListener('mouseup', function (event) {
                    currentNode = this;
                    handler(event);
                });
                newNode.addEventListener('dbclick', function (event) {
                    showdetail(event);
                });
                newNode.addEventListener('click', function (event) {
                    currentNode = this;
                    rightshow(event)
                });
            }
        }

        //        $.each(scene.childs,function(ix,iteam){
        //            if(iteam instanceof JTopo.Node&&iteam.outLinks.length>10){
        //                //iteam.layout = {type: 'circle', radius: 180};
        //            }
        //        });
        var roots = scene.childs;//JTopo.layout.getRootNodes(scene.childs);
        $.each(roots, function (ix, iteam) {
            if (iteam.key == "rack_${rackid}") {
                iteam.setLocation(500, 100);
                iteam.layout = {type: 'tree', width: 150, height: 150};
                JTopo.layout.layoutNode(scene, iteam, true);
            }
        })
        //        var container2 = new JTopo.Container("客户机柜");
        //        var flowLayout = JTopo.layout.FlowLayout(40, 30);
        //        container2.layout = flowLayout;
        //        container2.fillColor = '10,10,100';
        //        container2.fontColor = '255,255,255';
        //        container2.zIndex = 10;
        //        container2.add(scene.childs[1]);
        //        container2.textOffsetX = 0;
        //        container2.textOffsetY = 20;
        //        container2.setBound(400, 200, 150, 200);
        //        container2.borderRadius = 30; // 圆角
        //        scene.add(container2);
        stage.click(function (event) {
            if (event.button == 0) {// 右键
                // 关闭弹出菜单（div）
                $("#topo_rightMenu").hide();
            }
        });
        scene.addEventListener('mouseup', function (e) {
            if (e.target && e.target.layout) {
                JTopo.layout.layoutNode(scene, e.target, true);
            }
        });
    })
    ;

</script>

</html>