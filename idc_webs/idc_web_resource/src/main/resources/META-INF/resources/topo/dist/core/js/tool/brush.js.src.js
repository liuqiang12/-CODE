/**
 * Created by Victor on 2016/8/15.
 */
define([ 'element/Link', 'element/Node', 'element/Container', 'tool/util', 'tool/winHandler'],
    function ( Link, Node, Container, util, winHandler) {
        function getData(json, path) {
            var data = json;
            $.each(path, function (i, v) {
                data = data[v];
            });
            if(!data){
                throw new Error('topo_brush\'s data path error'+path);
            }
            return data;
        }
        var firstTime=true;
        function drawTopo(options) {
            var scene = options.scene;
            var id = options.id;
            var fn = options.fn;
            winHandler.getTools().progress({now: 30, text: '30%'});
            var config = scene.prop.config;
            //topo数据来源获取
            if (!config.topo||!config.topo.url) {
                console.info("need config topo");
                winHandler.getTools().progress({now: 100, text: 'need config topo', error: true});
                return;
            }
            //构造URL获取数据
            var TOPO_URL = config.topo.url;
            if (config.topo.params) {
                var fromUrl = '';
                $.each(config.topo.params, function (i, param) {
                    if(firstTime){
                        fromUrl = util.getParameter(param.key);
                    }
                    if (i == 0) {
                        TOPO_URL += "?" + param.key + "=";
                    } else {
                        TOPO_URL += "&" + param.key + "=";
                    }
                    if (fromUrl) {
                        TOPO_URL += fromUrl;
                    } else {
                        switch (param.type) {
                            case"scenePid":
                                if (id) {
                                    TOPO_URL += id;
                                } else {
                                    TOPO_URL += param.value;
                                }
                                break;
                            case "fixed":
                                TOPO_URL += param.value;
                                break;
                        }
                    }
                });
                firstTime=false;//首次载入控制
            }
            $.ajax({
                url: TOPO_URL,
                async: true,
                type: "get",
                success: function (data) {
                    var flag=draw(data);
                    scene.prop.url = TOPO_URL;
                    if (fn && $.isFunction(fn)&&flag=='success') {
                        fn(data);
                    }
                },
                error: function (e) {
                    console.info('topo data empty');
                    winHandler.getTools().progress({now: 100, text: 'topo data load empty', error: true});
                }
            });
            function draw(json) {
                console.info("TOPO data:");
                console.info(json);
                if (typeof json == 'string') {
                    json = $.parseJSON(json.replace(/'/g, '"'));
                }//自定义数据数据校验，不通过则暂停绘制
                console.info("TOPO json:");
                console.info(json);
                if (config.topo.stopFlag) {
                    var flag = getData(json, config.topo.stopFlag.split("."));
                    if (!flag || (typeof flag.length == 'number' && flag.length == 0)) {
                        console.info('trigger the stop flag');
                        winHandler.getTools().progress({now: 100, text: 'no data to draw', error: true});
                        return 'stop';
                    }
                }
                //开始绘制
                scene.clear();
                var props;
                //图层属性绑定
                if (config.scene) {
                    props = config.scene.prop || {};
                    var view = getData(json, config.scene.source.split("."));
                    scene.prop.id = (view.id || view[props.id])||'';
                    scene.prop.pid = (view.pid || view[props.pid])||'';
                    scene.prop.name = (view.name || view[props.name])||'';
                    if(config.scene.exprop){
                        $.each(config.scene.exprop,function(k,v){
                            scene.exprop[k]=v;
                        });
                    }
                }
                //坐标偏移量绑定
                var offsetX = 0;
                var offsetY = 0;
                if (config.xy) {
                    props = config.xy.prop || {};
                    var xy = getData(json, config.xy.source.split("."));
                    offsetX = parseInt((xy.offsetX || xy[props.offsetX]) || 0);
                    offsetY = parseInt((xy.offsetY || xy[props.offsetY]) || 0);
                    if (config.xy.option ) {
                        switch (config.xy.option) {
                            case'minus':
                                offsetX = -offsetX;
                                offsetY = -offsetY;
                                break;
                            case 'add':
                                break;
                        }
                    }
                }
                scene.prop.offsetX = offsetX;
                scene.prop.offsetY = offsetY;
                //画node
                if (config.node) {
                    props = config.node.prop || {};
                    $.each(getData(json, config.node.source.split(".")), function (i, node) {
                        var newNode = Node.createNode({
                            name: node.name || node[props.name],
                            text: node.name || node[props.name],
                            x: parseInt(node.x || node[props.x]) + offsetX,
                            y: parseInt(node.y || node[props.y]) + offsetY,
                            image: ((config.node.imagePath || config.imagePath) || '') + ((node.image || node[props.image]) || ''),
                            id: node.id || node[props.id],
                            pid: node.pid || node[props.pid],
                        });
                        if(config.node.exprop){
                            $.each(config.node.exprop,function(k,v){
                                newNode.exprop[k]=node[v];
                            });
                        }
                        scene.addElement(newNode);
                    });
                }
                //画Object
                if (config.object) {
                    props = config.object.prop || {};
                    $.each(getData(json, config.object.source.split(".")), function (i, object) {
                        var newObject;
                        switch (object.type) {
                            case'ztype.shape':
                                newObject = Node.createShape({
                                    name: object.name || object[props.name],
                                    text: object.name || object[props.name],
                                    id: object.id || object[props.id],
                                    pid: object.pid || object[props.pid],
                                    x: parseInt(object.x || object[props.x]) + offsetX,
                                    y: parseInt(object.y || object[props.y]) + offsetY,
                                    width: parseInt(object.width || object[props.width]),
                                    height: parseInt(object.height || object[props.height]),
                                    alpha: 0.5,
                                    nodeType: 'ztype.shape',
                                });
                                break;
                            case 'ztype.topo':
                                newObject = Node.createNode({
                                    name: object.name || object[props.name],
                                    text: object.name || object[props.name],
                                    x: parseInt(object.x || object[props.x]) + offsetX,
                                    y: parseInt(object.y || object[props.y]) + offsetY,
                                    image: ((config.object.imagePath || config.imagePath) || '') + ((object.image || object[props.image]) || ''),
                                    nodeType: 'ztype.topo',
                                    id: object.id || object[props.id],
                                    pid: object.pid || object[props.pid],
                                });
                                break;
                        }
                        if(config.object.exprop){
                            $.each(config.object.exprop,function(k,v){
                                newObject.exprop[k]=object[v];
                            });
                        }
                        scene.addElement(newObject);
                    });
                }
                //画线
                if (config.link) {
                    props = config.link.prop || {};
                    var newLine;
                    $.each(getData(json, config.link.source.split(".")), function (i, link) {
                        newLine = Link.directLink({
                            start: scene.findNode('id=' + (link.start || link[props.start]))[0],
                            end: scene.findNode('id=' + (link.end || link[props.end]))[0],
                            text: link.name || link[props.name],
                            name: link.name || link[props.name],
                            id: link.id || link[props.id],
                            pid: link.pid || link[props.pid]
                        });
                        if(config.link.exprop){
                            $.each(config.link.exprop,function(k,v){
                                newLine.exprop[k]=link[v];
                            });
                        }
                        scene.addElement(newLine);
                    });
                }
                //画分组
                if (config.container) {
                    props = config.container.prop || {};
                    var container;
                    $.each(getData(json, config.container.source.split(".")), function (i, group) {
                        container = Container.create({
                            name: group.name || group[props.name],
                            text: group.name || group[props.name],
                            id: group.id || group[props.id],
                            pid: group.pid || group[props.pid]
                        }, scene);
                        //宽的节点要先进container
                        $.each(group.object || group[props.object], function (index, value) {
                            var object = scene.findNode('id=' + value['id'])[0];
                            if (object && object.prop.nodeType != 'container') {
                                container.addNode(object);
                            }
                        });
                        $.each(group.node || group[props.node], function (index, value) {
                            var node = scene.findNode('id=' + value['id'])[0];
                            if (node) {
                                container.addNode(node);
                            }
                        });
                        if(config.container.exprop){
                            $.each(config.container.exprop,function(k,v){
                                container.exprop[k]=group[v];
                            });
                        }
                        scene.addElement(container);
                    });
                }
                winHandler.getToolBar().centerBtn.click();
                winHandler.getTools().progress({now: 70, text: '正在绘制告警!'});
                brush[scene.prop.config.brush].drawAlarm(scene, id);
                return 'success';
            }
        }
        function checkAlarmConfig(config){
            if (!config.alarm || !config.alarm.url) {
                console.info("need config alarm_url");
                winHandler.getTools().progress({now: 100, text: '100%'});
                return false;
            }
            if(!config.alarm.color){
                config.alarm.color=["255,0,0", "255, 102, 0", "255,204,0", "0,0,255"];
            }
            if(!config.alarm.size){
                config.alarm.size=20;
            }
            if(!config.alarm.font){
                config.alarm.font="微软雅黑";
            }
            return true;
        }
        function drawAlarm_ZK(scene) {
            var config = scene.prop.config;
            if(checkAlarmConfig(config)){
                console.info("告警url:"+config.alarm.url);
                $.ajax({
                    url: config.alarm.url,
                    async: true,
                    type: "get",
                    success: function (data) {
                        console.info("告警数据:");
                        console.info(data);
                        draw(data);
                        winHandler.getTools().progress({now: 100, text: '100%'});
                    },
                    error: function (e) {
                        console.info('no alarm to draw');
                        winHandler.getTools().progress({now: 100, text: '100%', error: true});
                    }
                });
            }
            function draw(json) {
                var nodes = json.node;
                if(!nodes){
                    console.info("alarm data error");
                    return false;
                }
                $.each(nodes, function (k, v) {
                    var node = scene.findNode("id=" + v.id)[0];
                    if (node) {
                        var total = 0;
                        var level = config.alarm.color.length;
                        for (var i = config.alarm.color.length; i > 0; i--) {
                            num = parseInt(v[i]);
                            total = total + num;
                            if (num > 0) {
                                level = i;
                            }
                        }
                        node.drawAlarm({
                            text: total,
                            color: config.alarm.color[level - 1],
                            size: config.alarm.size,
                            font: config.alarm.font,
                            level:v.level
                        });
                    }
                });
            }
        }

        function drawAlarm_IPOSS(scene) {
            var config = scene.prop.config;
            if(checkAlarmConfig(config)){
                var IPOSS_NODE_ID = '';
                var IPOSS_LINK_ID = '';
                $.each(scene.findNode(), function (i, v) {
                    if (v.prop.id) {
                        IPOSS_NODE_ID += v.prop.id + ',';
                    }
                });
                $.each(scene.findLink(), function (i, v) {
                    if (v.prop.id) {
                        IPOSS_NODE_ID += v.prop.id + ',';
                    }
                });
                var url = config.alarm.url + "?objId=" + IPOSS_NODE_ID + "&linkId=" + IPOSS_LINK_ID + '&areaId=1';
                console.info("告警url:"+url);
                $.ajax({
                    url: url,
                    async: true,
                    type: "get",
                    success: function (data) {
                        console.info("告警数据:");
                        console.info(data);
                        draw(data);
                        winHandler.getTools().progress({now: 100, text: '100%'});
                    },
                    error: function (e) {
                        console.info('no alarm to draw');
                        winHandler.getTools().progress({now: 100, text: '100%', error: true});
                    }
                });
            }
            function draw(json) {
                json = parseAlarmJson(json);
                if(!json){
                    console.info("alarm data error");
                    return ;
                }
                var nodes = json.dev;
                $.each(nodes, function (k, v) {
                    var node = scene.findNode("id=" + k)[0];
                    if (node) {
                        node.drawAlarm({
                            text: v.num,
                            color: config.alarm.color[v.level - 1],
                            size: config.alarm.size,
                            font: config.alarm.font,
                            level:v.level
                        });
                    }
                });
            }

            function parseAlarmJson(data) {
                if (typeof data == 'string') {
                    data = $.parseJSON(data.replace(/'/g, '"'));
                }
                if(!data.seg){
                    console.info("alarm data error");
                    return ;
                }
                //处理seg节点，将5,4,3,2,1的顺序替换为1,2,3,4,5
                $.each(data.seg, function (i, d) {
                    if (i == 5) {
                        data.seg["1"] = d;
                        delete data.seg[i];
                    } else if (i == 4) {
                        data.seg["2"] = d;
                        delete data.seg[i];
                    }

                });
                //处理dev节点，将5,4,3,2,1的顺序替换为1,2,3,4,5
                $.each(data.dev, function (i, d) {
                    if (d.level == 5) {
                        d["level"] = 1;
                    }
                    if (d.level == 4) {
                        d["level"] = 2;
                    }
                });
                return data;
            }
        }

        function saveTopo(scene, fn) {
            var config = scene.prop.config;
            if (config.save) {
                var SAVE_URL = config.save.url;
                $.each(config.save.params, function (i, param) {
                    if (i == 0) {
                        SAVE_URL += "?" + param.key + "=";
                    } else {
                        SAVE_URL += "&" + param.key + "=";
                    }
                    switch (param.type) {
                        case"scene":
                            SAVE_URL += scene.prop[param.value];
                            break;
                        case "fixed":
                            SAVE_URL += param.value;
                            break;
                        case "prop":
                            var offsetX = 0;
                            var offsetY = 0;
                            if (config.save.xy) {
                                switch (config.save.xy) {
                                    case'minus':
                                        offsetX = -scene.prop.offsetX;
                                        offsetY = -scene.prop.offsetY;
                                        break;
                                    case 'add':
                                        offsetX = scene.prop.offsetX;
                                        offsetY = scene.prop.offsetY;
                                        break;
                                }
                            }
                            if(config.save.offsetX&&config.save.offsetY){
                                offsetX+=config.save.offsetX;
                                offsetY+=config.save.offsetY;
                            }
                            $.each(scene.findNode(), function (k, node) {
                                $.each(param.format.prop, function (j, prop) {
                                    switch (prop) {
                                        case'x':
                                            SAVE_URL += parseInt(node.x) + offsetX;
                                            break;
                                        case'y':
                                            SAVE_URL += parseInt(node.y) + offsetY;
                                            break;
                                        default:
                                            SAVE_URL += node.prop[prop];
                                            break;
                                    }
                                    if (j < param.format.prop.length - 1) {
                                        SAVE_URL += param.format.divide;
                                    }
                                });
                                SAVE_URL += param.divide;
                            });
                            break;
                    }
                });
            }
            util.ajax({
                url: SAVE_URL, do: function (data) {
                    if (fn && $.isFunction(fn)) {
                        fn(data);
                    }
                }, error: function (e) {
                    console.info(e);
                    winHandler.getTools().alert({
                        content: '<h4>保存失败！</h4>'
                    });
                }
            });
        }

        var brush = [{
            drawTopo: drawTopo,
            drawAlarm: drawAlarm_ZK,
            saveTopo: saveTopo
        }, {
            drawTopo: drawTopo,
            drawAlarm: drawAlarm_IPOSS,
            saveTopo: saveTopo
        }];
        return brush;
    });