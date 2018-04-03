/**
 * Created by Victor on 2016/8/23.
 */
define(['tool/winHandler', 'tool/brush', 'tool/rightMenu', 'tool/util', 'JTopo'], function (winHandler, brush, rightMenu, util) {
    var isInScene=false;
    return {
        loadTopo: function (stage, scene) {
            winHandler.getTools().progress({now: 10, text: '10%'});
            winHandler.init(stage, scene,scene.prop.config['toolbar']);
            winHandler.setMode(scene.prop.config['default_mode'] || 'drag');
            util.ajax({
                url: scene.prop.config['rightMenu_url'],type:'get', do: function (data) {
                    rightMenu.init(stage, scene, data);
                    if (scene.prop.config['urlMenu']) {
                        rightMenu.addUrlMenu(scene.prop.config['urlMenu']);
                    }
                }
            });
            brush[scene.prop.config.brush].drawTopo({
                scene: scene,
                fn: function () {
                    scene.prop.path.push(scene.prop.pid);
                }
            });
        },
        addSceneListener: function (scene) {
            //快捷键设置
            var scrollFunc = function (e) {
                if(!isInScene){
                    return;
                }
                e.preventDefault();
                e = e || window.event;
                if (e.wheelDelta) {//IE/Opera/Chrome
                    e.returnValue = false;
                    quick(e, e.wheelDelta);
                } else if (e.detail) {//Firefox
                    e.returnValue = false;
                    quick(e, e.detail);
                }
            };

            function quick(e, value) {
                if (e.ctrlKey) {
                    if (!scene.stage.wheelZoom) {
                        if (value > 0) {
                            scene.stage.zoomOut();
                        } else {
                            scene.stage.zoomIn();
                        }
                    }
                } else if (e.shiftKey) {
                    if (value > 0) {
                        scene.translateX += 200;
                    } else {
                        scene.translateX -= 200;
                    }
                } else if (e.altKey) {

                } else {
                    if (value > 0) {
                        scene.translateY += 200;
                    } else {
                        scene.translateY -= 200;
                    }
                }
            }

            /*注册鼠标滑轮事件*/
            if (document.addEventListener) {
                document.addEventListener('DOMMouseScroll', scrollFunc, false);
            }
            window.onmousewheel = document.onmousewheel = scrollFunc;//IE/Opera/Chrome/Safari
            $(document).keydown(function(e){
                // ctrl + s
                if( e.ctrlKey  == true && e.keyCode == 83 ){
                    winHandler.getToolBar().edit_save.click();
                    return false;
                }
            });

            //图形事件
            scene.dbclick(function (e) {
                var element = e.target;
                if (!element) {
                    console.info(this);
                    return;
                }
                console.info(element);
                if (element && element.elementType == 'container') {
                    element.toggleExpand();
                } else if (element && element.elementType == 'node') {
                    if (element.prop.nodeType == 'node') {
                        scene.goDown(element.prop.id);
                    }else
                    if (element.prop.nodeType == 'container') {
                        var container = element.prop.expandTo;
                        container.toggleExpand();
                    }
                } else if (element && element.elementType == 'link') {
                    element.toggleExpend(scene);
                }
            });
            scene.mouseout(function(e){
                isInScene=false;
            });
            scene.mousemove(function (e) {
                isInScene=true;
                var element = e.target;
                if (element && element.elementType == 'node') {
                    var info = {
                        name: element.prop.name,
                        pid: element.prop.pid || '无'
                    };
                    var content = '';
                    var width = 0;
                    var len;
                    $.each(info, function (k, v) {
                        content += '<h5>&nbsp;&nbsp;' + k + ':' + v + '</h5>';
                        len = util.getStringWidth(k + ':' + v) + 20;
                        if (len > width) {
                            width = len;
                        }
                    });
                    winHandler.getTools().tips({
                        x: e.pageX + 30,
                        y: e.pageY + 30,
                        width: width,
                        content: content
                    });
                } else {
                    winHandler.getTools().tips('hide');
                }
            });
            scene.mouseup(function (e) {
                if (e.button == 0 && this.prop.mode == 'edit') {
                    var element = e.target;
                    if (element && (element.elementType == 'node' ) && element.prop.nodeType != 'container' && !element.prop.container) {
                        $.each(scene.findContainer(), function (i, v) {
                            if (v.elementType == 'container') {
                                if (v.isInside(element) && v.prop.isExpand) {
                                    winHandler.getTools().confirm({
                                        content: '是否加入分组?',
                                        handler: function (e) {
                                            if (e) {
                                                v.addNode(element);
                                                v.show();
                                            }
                                        }
                                    });
                                    return false;
                                }
                            }
                        });
                    }
                }
            });
            scene.mousedown(function (e) {
                winHandler.getTools().tips('hide');
            });
        }
    }
});
