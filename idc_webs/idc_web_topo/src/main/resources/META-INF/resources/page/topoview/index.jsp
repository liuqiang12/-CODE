<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="<%=request.getContextPath()%>/framework/bootstrap/css/bootstrap.min.css" rel="stylesheet"/>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script src="<%=request.getContextPath()%>/framework/vuejs/vue.min.js"></script>
    <script src="<%=request.getContextPath()%>/framework/jtopo/jtopo.min.js?v=1"></script>
    <%--<script src="<%=request.getContextPath()%>/js/roomlayout/js/jtopo-0.4.8-dev.js"></script>--%>
    <link href="<%=request.getContextPath()%>/moudles/topo/css/topo.css" rel="stylesheet">
    <link href="<%=request.getContextPath()%>/moudles/topo/css/font-awesome.min.css" rel="stylesheet">
    <script src="<%=request.getContextPath()%>/framework/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/ztree/js/jquery.ztree.exhide.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <style>
        div.panel[name=topo_toolbar] {
            /*color: white;*/
            /*background-color: #337ab7;*/
            /*rgba(51, 122, 183, 0.5);*/
            position: absolute;
            float: right;right: 0px;
            padding: 0;
            border: 0;
        }

        div[name=topo_toolbar_body] {
            /*background: rgba(51, 122, 183, 0.5);*/
        }

    </style>
</head>

<body>
<div class="easyui-layout" fit="true" id="main">
    <c:if test="${typeMode !='edit'&&typeMode!='childtopo'}">
        <div data-options="region:'west'" style="width: 200px;padding: 0px" title="TOPO视图">
            <ul class="ztree" id="ztree1" style=""></ul>
        </div>
    </c:if>
        <%--<div data-options="region:'east',collapsed:true" style="width: 200px;padding: 0px" title="节点属性">--%>
            <%--暂时不用  后期有可能添加节点属性调整功能--%>
        <%--</div>--%>
    <%--style="style="background-color: #f9f9f9" background:url(<%=request.getContextPath()%>/moudles/topo/img/bg1.jpg) top center no-repeat;overflow: hidden;background-size:cover"--%>
    <div data-options="region:'center'" id="canvasCenter"  title="查看视图"
         style="position:relative;overflow:hidden;background:url('<%=request.getContextPath()%>/moudles/topo/img/grid.gif');cursor:default;">
        <%--<div id="content" style="height: 100%;width: 100%;">--%>
        <%--<div><label>默认<input type="radio" name="option"/></label>--%>
        <%--<label>连线<input type="radio"--%>
        <%--name="option"/></label></div>--%>
         <%--<div style="color: white; font-size: 20px;height: 25px;background-color: #337ab7"><span id="topomodespan">查看</span>当前视图:<span id="viewnamespan">${viewobj.viewname}</span>&nbsp;&nbsp;</div>--%>
          <%--<div name="topo_toolbar_template">--%>
            <%--<div name='topo_toolbar' class='panel panel-default'>--%>
                <%--<div class='panel-body' name="topo_toolbar_body">--%>
                    <%--<div class='btn-group'>--%>
                        <%--<c:if test="${typeMode!='childtopo'}">--%>
                        <%--<button name='addview' type='button' id="addview" class='btn btn-sm btn-primary'--%>
                                <%--data-toggle='button;tooltip'--%>
                                <%--title='添加' data-placement='bottom'>--%>
                            <%--<span class='glyphicon glyphicon glyphicon-plus'></span>--%>
                        <%--</button>--%>
                            <%--<div class='btn-group' data-toggle='buttons'>--%>
                                <%--<label class='btn btn-sm btn-primary' name='topo_mode' data-toggle='tooltip' title='查看'--%>
                                       <%--data-placement='bottom'>--%>
                                    <%--<input type='radio' value='select'>--%>
                                    <%--<span class='glyphicon glyphicon-check'></span>--%>
                                <%--</label>--%>
                                <%--<label class='btn btn-sm btn-primary' name='topo_mode' data-toggle='tooltip' title='编辑'--%>
                                       <%--data-placement='bottom'>--%>
                                    <%--<input type='radio' value='edit'><span class='glyphicon glyphicon-edit'></span>--%>
                                <%--</label>--%>
                            <%--</div>--%>
                        <%--</c:if>--%>

                        <%--<button name='center' type='button' class='btn btn-sm btn-primary' data-toggle='tooltip'--%>
                                <%--title='居中缩放' data-placement='bottom'>--%>
                            <%--<span class='glyphicon glyphicon-screenshot'></span>--%>
                        <%--</button>--%>
                        <%--<button name='eagle_eye' type='button' class='btn btn-sm btn-primary'--%>
                                <%--data-toggle='button;tooltip'--%>
                                <%--title='鹰眼' data-placement='bottom'>--%>
                            <%--<span class='glyphicon glyphicon-eye-open'></span>--%>
                        <%--</button>--%>
                        <%--<button name='help' type='button' class='btn btn-sm btn-primary'--%>
                                <%--data-toggle='button;tooltip'--%>
                                <%--title='帮助' data-placement='bottom'>--%>
                            <%--<span class='glyphicon glyphicon-question-sign'></span>--%>
                        <%--</button>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
            <canvas id="canvas"></canvas>
        <%--</div>--%>

    </div>
</div>
<div id="tips" style="left:1px;top:1px;width: 1px;height: 1px;position: absolute;z-index: 5555;display:block"></div>
<ul name="topo_rightMenu" id="topo_rightMenu1" class="dropdown-menu" role="menu"
    :style="right_menu_style">
    <li v-for="(rightMenu,index) in rightMenus" v-if="rightMenu.show==true" v-on:click="menuclick(rightMenu)">
        <div class="buttonSquare bluelight">
            <i class="fa fa-info"></i>
            <span>{{rightMenu.name}}</span>
        </div>
    </li>
</ul>
<ul name="detailinfo" id="detailinfo" class="dropdown-menu" role="menu"
    :style="right_menu_style">
    <li v-for="(rightMenu,index) in rightMenus" v-if="rightMenu.show==true" v-on:click="menuclick(rightMenu)">
        <div class="buttonSquare bluelight">
            <i class="fa fa-info"></i>
            <span>{{rightMenu.name}}</span>
        </div>
    </li>
</ul>
</body>
<div id="addnode" style="">
</div>
<script id='code'>
        var userid = "${userInfo.id}";
        var _viewid= "${viewid}";
        var srcbase = contextPath + "/moudles/topo/topoimg/";
        var imgs = {
            "src_1_1": srcbase + "topo_202.png",
            "src_1_2": srcbase + "topo_203.png",
            "src_1_3": srcbase + "topo_185.png",
            "src_1_4": srcbase + "topo_182.png",
            "src_1_91": srcbase + "topo_214.png",
            "src_2_10": srcbase + "topo_459.png",
            "src_2_11": srcbase + "topo_217.png",
            "src_2_92": srcbase + "topo_217.png",
            "src_3_93": srcbase + "topo_2356.png"
        }
    /***
     *
     * 工作对象
     **/
    function TopoEditor() {
        this.viewId = 0;
        this.selectView = null;
        this.eagleEye = false;
        this.isSelectedMode = false;
        this.helpText = '';
        this.viewTree = null;
        this.deviceTree = null;
        this.state = null
        this.scene = null;
        this.selectNode = null;
        this.isDragMode = false;
        this.dragNode = null;
        this.canvas = document.getElementById('canvas');
        this.rightMenu = null;
        this.tempNodeA = null;
        this.tempNodeZ = null;
        this.link = null;
        this.beginNode = null;
        this.editMode = true;
        this.isChildTopo =false;
    }
    /***
     * 初始化
     * **/
    TopoEditor.prototype.init = function () {
        var _self = this;
        this.initTree();
        this.initMenu();
        this.initCanvas();//窗口自适应
        this.updateLister();

        $("#canvasCenter").panel({
            onResize: function () {
                $("#canvas").attr("width", $("#canvas").parent().width())
                $("#canvas").attr("height", $("#canvas").parent().height()-30);
                _self.stage.width = $("#canvas").parent().width();
                _self.stage.height = $("#canvas").parent().height()-30;
                _self.stage.paint()
            }
        })
    }
    TopoEditor.prototype.addView =function(){
        var self = this;
        var index = top.layer.open({
            type: 2,
            area: ['500px', '300px'],
            title: '创建视图',
            maxmin: false, //开启最大化最小化按钮
            content: contextPath + '/topoview/toViewForm',
            btn: ['确定', '关闭'],
            success: function (layero, index) {
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
            },
            yes: function (index, layero) {
                var body = top.layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0];
                var result = iframeWin.contentWindow.doSubmit(function (id, name) {
                    self.initTree();
                    self.initCanvas()
                    self.loadData(id)
                    setTimeout(function(){
                        var nodes = self.viewTree.getNodesByParam("id", id+"", null);
                        self.viewTree.selectNode(nodes);
                        $("#"+nodes[0]['parentTId']).find("#"+nodes[0]['tId']+'_a').dblclick();
                    },300)
                    $("label[name='topo_mode']").eq(1).click()
                });
            }
        });
    }
    TopoEditor.prototype.updateLister =function(){
        var self = this;
        var stage = self.stage;
        var toobarDiv = $('<div class="jtopo_toolbar">').html(''

//                +'<input type="radio" name="modeRadio" value="normal" checked id="r1"/>'
//                +'<label for="r1"> 默认</label>'
//                +'&nbsp;<input type="radio" name="modeRadio" value="select" id="r2"/><label for="r2"> 框选</label>'
//                +'&nbsp;<input type="radio" name="modeRadio" value="edit" id="r4"/><label for="r4"> 加线</label>'
                +'&nbsp;&nbsp;<input type="button" id="addview" value="添加视图"/>'
                +'&nbsp;&nbsp;<input type="button" id="editview" value="编辑"/>'
//                +'&nbsp;&nbsp;<input type="button" id="centerButton" value="居中显示"/>'
                +'<input type="button" id="zoomOutButton" value=" 放 大 " />'
                +'<input type="button" id="zoomInButton" value=" 缩 小 " />'
                +'&nbsp;&nbsp;<input type="checkbox" id="zoomCheckbox"/><label for="zoomCheckbox">鼠标缩放</label>'
                +'&nbsp;&nbsp;<input type="text" id="findText" style="width: 100px;" value="" onkeydown="enterPressHandler(event)">'
                + '<input type="button" id="findButton" value=" 查 询 ">');

        $('#canvas').before(toobarDiv);
        if("childtopo"=="${typeMode}"){
            $("#addview").hide();
            $("#editview").hide();
        }
        // 工具栏按钮处理
//        $("input[name='modeRadio']").click(function(){
//            stage.mode = $("input[name='modeRadio']:checked").val();
//        });
        $('#centerButton').click(function(){
            stage.centerAndZoom(); //缩放并居中显示
        });
        $('#zoomOutButton').click(function(){
            stage.zoomOut();
        });
        $('#zoomInButton').click(function(){
            stage.zoomIn();
        });
        $('#cloneButton').click(function(){
            stage.saveImageInfo();
        });
        $('#exportButton').click(function() {
            stage.saveImageInfo();
        });
        $('#printButton').click(function() {
            stage.saveImageInfo();
        });
        $('#zoomCheckbox').click(function(){
            if($('#zoomCheckbox').is(':checked')){
                stage.wheelZoom = 1.2; // 设置鼠标缩放比例
            }else{
                stage.wheelZoom = null; // 取消鼠标缩放比例
            }
        });
        $('#editview').click(function(){
            if(self.viewId==0){
                layer.msg("没有选择默认编辑的视图")
                return ;
            }
            $.getJSON(contextPath+"/topoview//viewinfo/"+self.viewId,function(data){
                var obj = data.viewinfo;
                if (obj.viewmode == 3 || userid == obj.userid) {
                    location=contextPath+"/topoview/edit/"+self.viewId;
//                        $("label[name='topo_mode']").eq(1).show();
                } else {
                    layer.msg("没有权限编辑")
//                        $("label[name='topo_mode']").eq(1).hide();
                }
            })
        });
        $('#addview').click(function(){
            self.addView();
            //location=contextPath+"/topoview/edit/"+editor.viewId;
        });
        window.enterPressHandler = function (event){
            if(event.keyCode == 13 || event.which == 13){
                $('#findButton').click();
            }
        };

        // 查询
        $('#findButton').click(function(){
            var text = $('#findText').val().trim();
            //var nodes = stage.find('node[text="'+text+'"]');
            var scene = stage.childs[0];
            var nodes = scene.childs.filter(function(e){
                return e instanceof JTopo.Node;
            });
            nodes = nodes.filter(function(e){
                if(e.text == null) return false;
                return e.text.indexOf(text) != -1;
            });

            if(nodes.length > 0){
                var node = nodes[0];
                node.selected = true;
                var location = node.getCenterLocation();
                // 查询到的节点居中显示
                stage.setCenter(location.x, location.y);

                function nodeFlash(node, n){
                    if(n == 0) {
                        node.selected = false;
                        return;
                    };
                    node.selected = !node.selected;
                    setTimeout(function(){
                        nodeFlash(node, n-1);
                    }, 300);
                }
                // 闪烁几下
                nodeFlash(node, 6);
            }
        });
//        $("label[name='topo_mode']").click(function () {
//            var mode = $(this).find('input').val();
//            if(mode =='edit'){
//                if(self.viewId==0){
//                    layer.msg("没有选择默认编辑的视图")
//                    return ;
//                }
//                $.getJSON(contextPath+"/topoview//viewinfo/"+self.viewId,function(data){
//                    var obj = data.viewinfo;
//                    if (obj.viewmode == 3 || userid == obj.userid) {
//                        location=contextPath+"/topoview/edit/"+self.viewId;
////                        $("label[name='topo_mode']").eq(1).show();
//                    } else {
//                        layer.msg("没有权限编辑")
////                        $("label[name='topo_mode']").eq(1).hide();
//                    }
//                })
////                self.editMode = true
////                $("#topomodespan").text("编辑")
////                location=contextPath+"/topoview/edit/"+self.viewId;
//            }else{
//                $("#topomodespan").text("查看")
//                self.editMode = false;
//                location=contextPath+"/topoview/view/"+self.viewId;
//            }
//        });
//        $("button[name='center']").click(function () {
//            self.stage.centerAndZoom(); //缩放并居中显示
//        });
    }
    //加载视图数据
    TopoEditor.prototype.loadData = function (viewId) {
        this.viewId = viewId;
        var self = this;
        $.getJSON(contextPath + "/topoview/" + viewId, function (data) {
            var idMap = {};
            $.each(data.viewObjs, function (index, iteam) {
                var xy = iteam.viewposition.split("_");
                var newNode = {
                    x: parseInt(xy[0]),//减去左侧模型导航宽度
                    y: parseInt(xy[1])
                };
                newNode.id = iteam.objpid;
                newNode.icon = iteam.icon;
                newNode.name = iteam.name;
                newNode.objtype = iteam.objtype;
                newNode.objfid = iteam.objfid;
                newNode.devicetype = iteam.deviceType;
                var nodeResoutle = self.addNode(newNode)
                if (nodeResoutle) {
                    idMap[iteam.objpid] = nodeResoutle;
                }

            });
            function createLink(NodeA,NodeZ,lineWidth,text){
                var link=null;
                if(text){
                    link = new JTopo.Link(NodeA, NodeZ,text);
                }else{
                    link = new JTopo.Link(NodeA, NodeZ);
                }
                link.bundleGap = 20; // 线条之间的间隔
                link.arrowsRadius = 10;
                link.fontColor = JTopo.util.randomColor();
                link.strokeColor = JTopo.util.randomColor(); // 线条颜色随机
                link.lineWidth = lineWidth>5?5:2;
                link.textOffsetY = 3; // 文本偏移量（向下3个像素）
                self.scene.add(link);
                return link;
            }
            $.each(data.links, function (index, iteam) {
                var NodeA = idMap[iteam.srcid];
                var NodeZ = idMap[iteam.desid];
                if (NodeA && NodeZ) {
                    if(NodeA.objtype==0&&NodeZ.objtype==0){
                        self.addMLink(NodeA,NodeZ,iteam.childLinks,(iteam.outflowMbps).toFixed(2)+'Mbps',(iteam.inflowMbps).toFixed(2)+'Mbps',iteam.topoLinkID,true);

                    }else{
                        self.addMLink(NodeA,NodeZ,2,"","",iteam.topoLinkID,false);
                    }

                }
            });
            self.stage.centerAndZoom();
//            if(self.scene.scaleX!=1){//缩放比例要出问题，强制手动
//                self.scene.scaleX=1
//            }
//            if(self.scene.scaleY!=1){
//                self.scene.scaleY=1
//            }
        })
    }
    TopoEditor.prototype.addNode = function(node){
        var self = this ;
        var newNode = new JTopo.Node(node.name);
        newNode.id = node.id;
        newNode.objpid = node.id;
        newNode.objfid = node.objfid;
        newNode.name = node.name;
        newNode.objtype = node.objtype;
        newNode.setLocation(node.x||100, node.y||100)
        if (newNode.objtype == 0) {
            newNode.setSize(48, 48);
            var img = imgs["src_"+node.devicetype];
            if(typeof img!='undefined'){
                newNode.setImage(img)
            }else{
                if(node.icon){
                    newNode.setImage(contextPath + "/moudles/topo/topoimg/"+node.icon)
                }else{
                    newNode.setImage(contextPath + "/moudles/topo/img/mo/site_4.png")
                }
            }
            newNode.dbclick(function(e){
                openDialogView('查看设备信息',contextPath+"/devicecap/"+node.objfid,"1000px","500px")
            })
        } else {
//            if(node.icon){
//                newNode.setSize(48, 48);
//                newNode.setImage(contextPath + "/moudles/topo/topoimg/"+node.icon)
//            }else{
//                newNode.setSize(100, 80);
//                newNode.font="24px Consolas";
//                newNode.textPosition = 'Middle_Center';
//                newNode.setImage(contextPath + "/moudles/topo/topoimg/topo_626.png")
//            }
            newNode.setSize(100, 80);
            newNode.font="24px Consolas";
            newNode.textPosition = 'Middle_Center';
            newNode.setImage(contextPath + "/moudles/topo/topoimg/topo_626.png")
            if(newNode.objtype!=1){
                newNode.dbclick(function(e){
                    self.showChildTopo(e.target);
                })
            }

        }
        newNode.fontColor  = "#fff";//JTopo.util.randomColor(); // 线条颜色随机
        self.scene.add(newNode)
        return newNode;
    };
        /***
         * 子图
         * */
     TopoEditor.prototype.showChildTopo = function (node) {
         openDialogView('子图【'+node.name+'】',contextPath+"/topoview/showChildTopo/"+node.objfid,"1200px","580px");
     }
    TopoEditor.prototype.showAlarm = function (node) {
        openDialogView('查看告警信息',contextPath+"/alarm/index.do?objid="+node.objfid,"1000px","500px");
    }
    TopoEditor.prototype.showLine = function (selectNode) {
        var src= selectNode.nodeA.objpid
        var des = selectNode.nodeZ.objpid
        openDialogView('查看链路信息',contextPath+"/topoview/showlink/"+src+"/"+des,"800px","500px")
    }
    TopoEditor.prototype.showFlow = function (selectNode) {
        openDialogView('查看设备信息',contextPath+"/devicecap/"+selectNode.objfid,"1000px","500px")

    }
    TopoEditor.prototype.initMenu = function () {
        var self = this ;
        var eventHandle = function(event, e){
            self.rightMenu.hide();
            if (event.eventName == 'addNode') {
                var node = self.selectView
                if(node.viewmode!=3&&userid!=node.userid){
                    top.layer.msg("当前用户没有权限操作")
                    return;
                }
                if(!self.editMode){//添加节点必须在编辑模式下
                    top.layer.msg("当前为查看模式，如需添加节点，请点击右上角编辑按钮");
                    return;
                }
                var x = self.layerx;
                var y = self.layery;
                var index = top.layer.open({
                    type: 2,
                    area: ['600px', '400px'],
                    title: '创建视图对象',
                    maxmin: false, //开启最大化最小化按钮
                    content: contextPath + '/topoview/addViewObj/'+self.viewId+'/'+x+"/"+y,
                    btn: ['确定', '关闭'],
                    success: function (layero, index) {
                        var name = layero.find('iframe')[0].name;
                        top.winref[name] = window.name;
                    },
                    yes: function (index, layero) {
                        var body = top.layer.getChildFrame('body', index);
                        var iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.doSubmit(function (node) {
                            node.x = x;
                            node.y = y;
                            self.addNode(node)
                        })
                    }
                });
            }
            if (event.eventName == 'showLine') {
                self.showLine(self.selectNode);
            }
            if (event.eventName == 'showAlarm') {
                self.showAlarm(self.selectNode);
            }
            if (event.eventName == 'showFlow') {
                self.showFlow(self.selectNode);
            }
            if (event.eventName == 'delNode') {
                var url = "";
                if(!self.editMode){
                    top.layer.msg("当前为查看模式")
                    return ;
                }
                if(event.type=="node"){
                    url= contextPath+"/topoview/deleteobj/"+self.selectNode.objpid
                }else if(event.type=="link"){
                    url= contextPath+"/topoview/deletelink/"+self.selectNode.id
                }
                var btn = ['删除', '取消'];
                 top.layer.msg('确定删除该对象？', {
                    time: 0 //不自动关闭
                    ,btn: btn
                    ,yes: function(index){
                        $.post(url,function(data){
                            top.layer.close(index)
                            if(data.state){
                                if(event.type=="node"){
                                    self.removeNode(data.msg)
                                }else if(event.type=="link"){
                                    self.removeLink(data.msg)
                                }
                            }else{
                                top.layer.msg(data.msg)
                            }
                        })
                    }
                });
            }
            self.selectNode  =null;
        };
        var topo_rightMenu = new Vue({
            el: '#topo_rightMenu1',
            computed: {
                right_menu_style: function () {
                    return {
                        'z-index': 5555,
                        left: this.x,
                        top: this.y,
                        display: this.isShow ? 'block' : 'none'
                    }
                }
            },
            data: {
                isShow: false,
                x: 0,
                y: 0,
                rightMenus: [
                    {name: '查看链路信息', show: false, eventName: 'showLine', type: 'link'},
                    {name: '查看设备信息', show: false, eventName: 'showFlow', type: 'node'},
                    {name: '查看告警', show: false, eventName: 'showAlarm', type: 'node'}
                ]
            },
            methods: {
                show: function () {
                    this.isShow = true;
                },
                hide: function () {
                    this.isShow = false;
                },
                menuclick: function (event, e) {
                    this.isShow = false;
                    eventHandle(event, e)
                }
            }
        });
        this.rightMenu = topo_rightMenu;
    }
        TopoEditor.prototype.checkToolBar = function (obj) {
            if (obj.viewmode == 3 || userid == obj.userid) {
                $("label[name='topo_mode']").eq(1).show();
            } else {
                $("label[name='topo_mode']").eq(1).hide();
            }
        }
    TopoEditor.prototype.initTree = function () {
        var _self = this;
        var defaults = {
            callback: {
                onClick: function (event, treeId, treeNode) {c
                    if(treeNode.viewmode=="1"&&userid!=treeNode.userid){
                            top.layer.msg("当前用户没有权限查看")
                            return false;
                    }else{
                        _self.selectView = treeNode;
                        $("#viewnamespan").text(treeNode.name)
                        $("#canvasCenter").panel({
                            title:'查看视图【'+treeNode.name+'】'
                        })
                        _self.checkToolBar(treeNode);
                        _self.initCanvas();
                        _self.loadData(treeNode.id);
                    }

                },
                beforeDrag: function (treeId, treeNodes) {
                    var dragNode = treeNodes[0];
                    if (dragNode) {
                        if (dragNode.isParent)return false;
                        if (dragNode.dataType == 2 && dragNode.id == _self.viewId)return false;//拖动视图 并且该试图是正在编辑的视图 禁止拖动
                        if (_self.scene) {
                            var disNodes = _self.scene.getDisplayedElements();
                            var flag = true;
                            $.each(disNodes, function (i, iteam) {
                                if (iteam.name == dragNode.name) {
                                    flag = false;
                                    return false;
                                }
                            })
                            if (!flag) {//存在的节点不在添加
                                top.layer.msg("该节点已经存在");
                                return false;
                            }
                        } else return false;
                    }
                },
                onDrag: function (event, treeId, treeNodes) {
//                    _self.isDragMode = true;
//                    _self.dragNode = treeNodes[0];
                }
            },
            edit: {
                enable: false,
                showRemoveBtn: false,
                showRenameBtn: false
            },
            data: {
                key: {
                    name: 'name',
                    idKey: 'id'
                },
                simpleData: {
                    enable: true
                }
            }
        };
        $.getJSON(contextPath + "/topoview/list", function (data) {
            $.each(data, function (index, iteam) {
                iteam.name = iteam.viewname;
                iteam.userid = iteam.userid;
                iteam.viewmode = iteam.viewmode;
                iteam.id = iteam.viewid;
                iteam.dataType = 2;
            });
            _self.viewTree = $.fn.zTree.init($("#ztree1"), defaults, {
                id: '-1',
                open: true,
                name: '视图列表',
                children: data
            });
        });
//        $.post(contextPath + "/device/list.do", function (data) {
//            $.each(data.rows,function(index,iteam){
//                iteam.name = iteam.NAME;
//                iteam.id = iteam.DEVICEID;
//                iteam.dataType = 0;
//            });
//            _self.deviceTree = $.fn.zTree.init($("#ztree"),defaults, {
//                id: '-1',
//                open: true,
//                name: '设备列表',
//                children: data.rows
//            });
//        }, 'json');
    };
    TopoEditor.prototype.addMLink = function (NodeA,NodeZ,lineWidth,atext,btext,linkid,isMuti) {
        var _self = this;
        var linka = createLink(NodeA,NodeZ,lineWidth,atext);
        linka.textOffsetY = 10; // 文本偏移量（向下3个像素）
        linka.textOffsetX = 10; // 文本偏移量（向下3个像素）
        linka.id = linkid;
        linka.bundleGap = 0;
        if(typeof(isMuti)!='undefined'&&isMuti){
            var linkb = createLink(NodeZ,NodeA,lineWidth,btext);
            linkb.textOffsetY = -10; // 文本偏移量（向下3个像素）
            linkb.textOffsetX = -10; // 文本偏移量（向下3个像素）
            linkb.id = linkid;
            linkb.bundleGap = 0; // 线条之间的间隔
        }
        function createLink(NodeA,NodeZ,lineWidth,text){
            var link=null;
            if(text){
                link = new JTopo.Link(NodeA, NodeZ,text);
            }else{
                link = new JTopo.Link(NodeA, NodeZ);
            }
            link.bundleGap = 20; // 线条之间的间隔
            console.log(JTopo.util.randomColor())
//            link.arrowsRadius = 10;
            link.fontColor ="#fff";// JTopo.util.randomColor();
            link.strokeColor = "#fff"//JTopo.util.randomColor(); // 线条颜色随机
            link.lineWidth = lineWidth>5?5:2;
            link.textOffsetY = 3; // 文本偏移量（向下3个像素）
            link.dbclick(function(node){
                if(node.target.nodeA.objtype==0&&node.target.nodeZ.objtype==0){
                    _self.showLine(node.target)
                }
            })
            _self.scene.add(link);
            return link;
        }
     }
    TopoEditor.prototype.addLink = function (endNode) {
        var self = this;
        if(self.beginNode.objtype==2||endNode.objtype==2){
            var sendData = {
                viewid:self.viewId,
                srcdeviceid:self.beginNode.objpid,
                desdeviceid:endNode.objpid
            }
            $.post(contextPath+"/topoview/addLink",sendData,function(data){
                if (data.state) {
                    top.layer.msg("添加链接成功")
                    self.addMLink(self.beginNode,endNode,1,"","",data.msg,false);
                    self.beginNode = null;
                    self.link = null;
                } else {
                    top.layer.msg(data.msg)
                }
            })
        }else{
            var index = top.layer.open({
                type: 2,
                area: ['800px', '500px'],
                title: '创建链接',
                maxmin: false, //开启最大化最小化按钮
                content: contextPath + '/topoview/addLink/'+self.viewId+"/"+self.beginNode.id+"/"+endNode.id,
                btn: ['确定', '关闭'],
                success: function (layero, index) {
                    var name = layero.find('iframe')[0].name;
                    top.winref[name] = window.name;
                },
                yes: function (index, layero) {
                    var body = top.layer.getChildFrame('body', index);
                    var iframeWin = layero.find('iframe')[0];
                    var result = iframeWin.contentWindow.doSubmit(function (id) {
                        self.addMLink(self.beginNode,endNode,1,"","",id,true);
//                    var l = new JTopo.Link(self.beginNode,endNode );
//                    l.id = id;
//                    l.lineWidth = 3;
//                    self.scene.add(l);
                        self.beginNode = null;
                        self.link = null;
                    })

                }
            });
        }

    }
    /***
     * 拖动后更新节点位置信息
     * **/
    TopoEditor.prototype.updateNodeLocation = function(id,x,y){
        if(this.editMode){
            $.getJSON(contextPath+"/topoview/updateNodeLocation/"+id+"/"+x+"_"+y,function(data){
//                console.log(data)
            })
        }

    }
    TopoEditor.prototype.removeLink = function (id) {
            var self = this;
           var eles = self.scene.getDisplayedElements();
            $.each(eles,function(i,iteam){
                if ( iteam instanceof JTopo.Link) {
                    if(iteam.id ==id){
                        self.scene.remove(iteam)
                    }
                }
            })
     }
        TopoEditor.prototype.removeNode = function (id) {
            var self = this;
            var eles = self.scene.getDisplayedElements();
            $.each(eles,function(i,iteam){
                if ( iteam instanceof JTopo.Node) {
                    if(iteam.id ==id){
                        self.scene.remove(iteam)
                    }
                }
            })
        }
    TopoEditor.prototype.initCanvas = function () {
        var self = this;
        if (this.stage && this.scene) {
            this.scene.clear();
            return;
        }
        $(this.canvas).attr("width", $(this.canvas).parent().width())
        $(this.canvas).attr("height", $(this.canvas).parent().height()-30);
        this.stage = new JTopo.Stage(this.canvas);
        this.scene = new JTopo.Scene(this.stage);
//        this.stage.mode = 'drag';
        this.stage.wheelZoom = 1.2;
        this.stage.eagleEye.visible = this.eagleEye;
        this.tempNodeA = new JTopo.Node('tempA');
        this.tempNodeA.setSize(1, 1);
        this.tempNodeZ = new JTopo.Node('tempZ');
        this.tempNodeZ.setSize(1, 1);
        //清除起始节点不完整的拖放线
        this.scene.mousedown(function (e) {
            if (self.link && (e.target == null || e.target === self.beginNode || e.target === self.link)) {
                this.remove(self.link);
            }
        });
        //画布上单击事件
        this.timer=null;
        this.scene.mouseover(function (e) {
            if(e.target!=null&& e.target instanceof  JTopo.Node){
                clearTimeout(self.timer);
                self.timer = setTimeout(function(){
                    $("#tips").css("left", e.pageX)
                    $("#tips").css("top", e.pageY)
                    layer.tips('<div style="white-space:nowrap">'+ e.target.name+'</div>', '#tips', {
                        tips: [1, '#3595CC'],
                        time: 2000
                    });
                    $(".layui-layer-tips").css("width","");
                },1);
            }
        });
        //处理右键菜单，左键连线
        this.scene.mouseup(function (e) {
            self.rightMenu.hide();//隐藏所有菜单
            if (e.target)
                self.selectNode = e.target;
            if (e.button == 2) {//右键菜单
                self.dragNode = null;
                self.isDragMode = false;
                var isShowMenu = false;
                if(e.target){
                    if (e.target instanceof JTopo.Node) {
                        for (var i = 0; i < self.rightMenu.rightMenus.length; i++) {
                            if (self.rightMenu.rightMenus[i].type.indexOf('node')>-1) {
                                self.rightMenu.rightMenus[i].show = true;
                                isShowMenu =true;
                            } else {
                                self.rightMenu.rightMenus[i].show = false;
                            }
                        }
                    }
                    else if (e.target instanceof JTopo.Link) {// 链路
                        for (var i = 0; i < self.rightMenu.rightMenus.length; i++) {
                            if (self.rightMenu.rightMenus[i].type.indexOf('link')>-1) {
                                isShowMenu =true;
                                self.rightMenu.rightMenus[i].show = true
                            } else {
                                self.rightMenu.rightMenus[i].show = false;
                            }
                        }
                    }
                }else{
                    for (var i = 0; i < self.rightMenu.rightMenus.length; i++) {
                        if (self.rightMenu.rightMenus[i].type.indexOf('scene')>-1) {
                            isShowMenu =true;
                            self.rightMenu.rightMenus[i].show = true
                        } else {
                            self.rightMenu.rightMenus[i].show = false;
                        }
                    }
                }
                //如果一个显示的条目都没有 不显示
                if(!isShowMenu){
                    return ;
                }
                var height = $("#canvas").height();
                var width = $("#canvas").width();
                var menuy = e.pageY;
                var menux = e.pageX;
                if (menuy >= height - 100) {
                    menuy = menuy - 100;
                }
                if (menux >= width - 100) {
                    menux = menux - 100;
                }
                self.layerx = e.x;
                self.layery = e.y;
                self.rightMenu.x = menux;
                self.rightMenu.y = menuy;
                self.rightMenu.show();
            } else if (e.button == 1) {//中键

            } else if (e.button == 0) {//左键


            }
        });
    }
    $(function () {
        topoEditor = new TopoEditor();
        topoEditor.init();
        if(_viewid&&_viewid!=""){
            topoEditor.isChildTopo = true ;
            topoEditor.loadData(_viewid);
            <%--topoEditor.checkToolBar({--%>
                <%--viewmode:"${viewobj.viewmode}",--%>
                <%--userid:"${viewobj.userid}"--%>
            <%--});--%>
        }
        try{
            top.hideLeft();
        }catch(e){

        }

    })
    function deleteLink(id){
        topoEditor.removeLink(id)
    }
        // 页面工具栏
        function showJTopoToobar(stage,editor){

        }
</script>

</html>