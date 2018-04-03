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
<script type="text/javascript">
    //A端机架IDs
    var rackid = "${rackid}";
    //style="background:url(<%=request.getContextPath()%>/moudles/topo/img/bg11.png) top center no-repeat;overflow: hidden;background-size:cover"
</script>
<body>
<canvas id="canvas" style="width: 100%;height: 100%;"></canvas>
</body>

<script id='code'>
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
                roomId: '${node.roomId}'
            })
        </c:forEach>
        //连线
        <c:forEach items="${links}" var="link">
            links.push({
                name:'${link.name}',
                aname:'${link.a.name}',
                zname:'${link.z.name}',
                akey:'${link.a.key}',
                zkey:'${link.z.key}'
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
        newNode.setLocation(100, 100);
        newNode.setImage(node.img);
        newNode.zIndex = 150;
        scene.add(newNode);
        newNode.addEventListener('dbclick', function (event) {
            showdetail(event);
        });
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
            linkNode.lineWidth = 2;
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
                var url = contextPath + '/idclink/getPreLinksByAZ';
                var data = {aKey:aKey,zKey:zKey};
                if(event.target.nodeA.type=="rack_df"){
                    data.wlRackId = getAWlRackId(aKey);
                }
                openDialogUsePostView('链接信息',url, '1000px', '530px',data,'showLinkInfo');
            });
            linkNode.addEventListener('mouseover', function (event) {
                this.text = event.target.nodeA.text + ">>" + event.target.nodeZ.text;
            });
            linkNode.addEventListener('mouseout', function (event) {
                this.text = "";
            });
            scene.add(linkNode);
        }
    }
    //查看节点信息
    function showdetail(obj) {
        var target = obj.target;
        if(target.type=='equipment'){//设备
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
    //ODF获取其A端机架ID
    function getAWlRackId(value){
        var wlkey = "";
        for(var i=0;i<links.length;i++){
            if(links[i].zkey==value){
                wlkey = links[i].akey;
                break;
            }
        }
        var wlRackId = "";
        for(var i=0;i<nodes.length;i++){
            if(nodes[i].key==wlkey){
                wlRackId = nodes[i].id;
                break;
            }
        }
        console.log("=====================================wlRackId:"+wlRackId);
        return wlRackId;
    }

</script>

</html>