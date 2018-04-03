/**
 * Created by Victor on 2016/8/5.
 */
//
// 页面工具栏
define(['window/toolbar', 'window/nodeAttribute', 'window/linkAttribute', 'window/containerAttribute', 'window/toolWindow', 'window/global', 'element/Node', 'element/Link', 'tool/util','JTopo'], function (toolbar, nodeAttribute, linkAttribute, containerAttribute, toolWindow, global, Node, Link, util) {
    //获取要管理的窗口
    var toolbarItem;
    var imageNodeItem;
    var textNodeItem;
    var linkItem;
    var lineItem;
    var containerItem;
    var exPropItem;
    var autoLayoutItem;
    var alarmConfigItem;

    function init(stage, scene, options) {
        if(toolbarItem){
            return true;
        }
        inteColorPalette();
        toolbarItem = toolbar.init(scene);
        imageNodeItem = nodeAttribute.initImageNode(scene);
        textNodeItem = nodeAttribute.initTextNode(scene);
        linkItem = linkAttribute.initLink(scene);
        lineItem=linkAttribute.initLine(scene);
        containerItem = containerAttribute.init(scene);
        exPropItem = global.initExProp(scene);
        autoLayoutItem = global.initAutoLayout(scene);
        alarmConfigItem=global.initAlarmConfig(scene);
        var op = options || {};
        makeToolbar(stage, scene, op);
        //模式切换
        toolbarItem.modeBtns.click(function () {
            var modle = $(this).find("input").val();
            scene.setMode(modle);//设定Jtopo模式
        });
        //自动布局窗口
        autoLayoutItem.cancelBtn.click(function () {
            toolbarItem.edit_autoLayout.click();
        });
        //图片节点窗口
        imageNodeItem.okBtn.click(function () {
            if (imageNodeItem.win.mode == 'create') {
                toolbarItem.edit_createImage.click();
            }
        });
        imageNodeItem.cancelBtn.click(function () {
            if (imageNodeItem.win.mode == 'create') {
                toolbarItem.edit_createImage.click();
            } else {
                imageNodeItem.win.trigger('resetWindow');
            }
        });
        //文字节点窗口
        textNodeItem.okBtn.click(function () {
            if (textNodeItem.win.mode == 'create') {
                toolbarItem.edit_createText.click();
            }
        });
        textNodeItem.cancelBtn.click(function () {
            if (textNodeItem.win.mode == 'create') {
                toolbarItem.edit_createText.click();
            } else {
                textNodeItem.win.trigger('resetWindow');
            }
        });
        lineItem.cancelBtn.click(function(){
            toolbarItem.edit_createLine.click();
        });
        alarmConfigItem.cancelBtn.click(function(){
            toolbarItem.edit_alarm_config.click();
        });
        alarmConfigItem.okBtn.click(function(){
            toolbarItem.edit_alarm_config.click();
        });
    }

    function makeToolbar(stage, scene, options) {
        var config = scene.prop.config;
        if (!toolbarItem) {
            return;
        }
        if (options) {
            $.each(options, function (k, v) {
                if (v) {
                    toolbarItem[k].show();
                } else {
                    toolbarItem[k].hide();
                }
            });
        }
        //返回上一层
        toolbarItem.returnBtn.click(function () {
            scene.goBack();
        });
        //全屏
        var frameWidth = 0;
        var frameHeight = 0;
        toolbarItem.fullScreen.click(function (e) {
            //util.runPrefixMethod(stage.canvas, "RequestFullScreen");仅仅画布全屏显示
            if (window != top) {
                var frame = $(window.parent.document.getElementById(config.frameId));
                // 页面在iframe中时处理
                if (config.frameId) {
                    if (toolbarItem.fullScreen._isClick) {
                        frameWidth = frame.width();
                        frameHeight = frame.height();
                        if (frameWidth && frameHeight) {
                            frame.width($(window.parent).width());
                            frame.height($(window.parent).height());
                            util.launchFullScreen(window.parent.document.documentElement); // 整个网页
                        } else {
                            throw new Error("can't launch fullScreen frameId not exist");
                        }
                    } else {
                        if (frameWidth && frameHeight) {
                            frame.width(frameWidth);
                            frame.height(frameHeight);
                            frameWidth=frameHeight=0;
                        }
                        util.exitFullScreen(window.parent.document);
                    }
                } else {
                    throw new Error("can't launch fullScreen need config frameId");
                }
            } else {
                if (toolbarItem.fullScreen._isClick) {
                    // 启动全屏!
                    util.launchFullScreen(document.documentElement); // 整个网页
                } else {
                    // 退出全屏模式!
                    util.exitFullScreen(document);
                }
            }
        });
        //居中展示
        toolbarItem.centerBtn.click(function () {
            stage.centerAndZoom();
        });
        //正常展示
        toolbarItem.commonBtn.click(function () {
            scene.scaleX = 1;
            scene.scaleY = 1;
        });
        //导出PNG
        toolbarItem.exportImg.click(function () {
            //stage.saveImageInfo();
            //在新页面打开图片
            var image = stage.canvas.toDataURL("image/png");
            var w = window.open('about:blank', 'image from canvas');
            w.document.write("<img src='" + image + "' alt='from canvas'/>");
            //下载图片
            // here is the most important part because if you dont replace you will get a DOM 18 exception.
            // var image =  stage.canvas.toDataURL("image/png").replace("image/png", "image/octet-stream;Content-Disposition: attachment;filename=foobar.png");
            //var image = stage.canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
            //window.location.href=image; // it will save locally
        });
        //放大
        toolbarItem.zoomOut.click(function () {
            stage.zoomOut();
        });
        //缩小
        toolbarItem.zoomIn.click(function () {
            stage.zoomIn();
        });

        //鼠标缩放
        toolbarItem.zoomCheck.click(function () {
            if (toolbarItem.zoomCheck._isClick) {
                if (!stage.wheelZoom) {
                    stage.wheelZoom = 0.85; // 设置鼠标缩放比例
                }
            } else {
                stage.wheelZoom = null; // 取消鼠标缩放比例
            }
        });
        //打印功能
        toolbarItem.printBtn.click(function () {
            window.print();
        });
        //鹰眼
        toolbarItem.eagleEye.click(function () {
            stage.eagleEye.visible = toolbarItem.eagleEye._isClick;
        });
        // 搜索,优先找id，其次找名字
        toolbarItem.addSearchMode([{
            name: '名称',
            value: 'name',
            search: function (text) {
                return {
                    results: scene.findNode('name=' + text.trim()),
                    event: function (node) {
                        util.nodeAsCenter(scene, node);
                    }
                };
            },
            deepSearch: function (text) {
                return {
                    url: "http://202.102.36.137:9004/newui/liposs/plattopo/topo/platTopo!findDev.action?areaId=1&type=2&s=" + text.trim(),
                    filter: function (json) {
                        var data = [];
                        if (json) {
                            console.info(json);
                            json = json.replace(/'/g, '"');
                            json = $.parseJSON(json);
                            $.each(json, function (i, v) {
                                data.push({
                                    id: v.id,
                                    pid: v.pid,
                                    prop: {
                                        name: v.name,
                                        content: v.ip + ' | ' + v.model + ' | '
                                    }
                                });
                            });
                        }
                        return data;
                    },
                    event: function (node) {
                        scene.goDown(node.pid, function () {
                            util.nodeAsCenter(scene, scene.findNode('id=' + node.id)[0]);
                        });
                    }
                };
            }
        }, {
            name: 'ID',
            value: 'id',
            search: function (text) {
                return {
                    results: scene.findNode('id=' + text.trim()),
                    event: function (node) {
                        util.nodeAsCenter(scene, node);
                    }
                };
            },
            deepSearch: function (text) {
                return {
                    url: "http://202.102.36.137:9004/newui/liposs/plattopo/topo/platTopo!findDev.action?areaId=1&type=4&s=" + text.trim(),
                    filter: function (json) {
                        var data = [];
                        if (json) {
                            json = json.replace(/'/g, '"');
                            console.info(json);
                            json = $.parseJSON(json);
                            $.each(json, function (i, v) {
                                data.push({
                                    id: v.id,
                                    pid: v.pid,
                                    prop: {
                                        name: v.name,
                                        content: v.ip + ' | ' + v.model + ' | '
                                    }
                                });
                            });
                        }
                        return data;
                    },
                    event: function (node) {
                        scene.goDown(node.pid, function () {
                            util.nodeAsCenter(scene, scene.findNode('id=' + node.id)[0]);
                        });
                    }
                };
            }
        }, {
            name: 'IP',
            value: 'ip',
            deepSearch: function (text) {
                return {
                    url: "http://202.102.36.137:9004/newui/liposs/plattopo/topo/platTopo!findDev.action?areaId=1&type=1&s=" + text.trim(),
                    filter: function (json) {
                        var data = [];
                        if (json) {
                            json = json.replace(/'/g, '"');
                            console.info(json);
                            json = $.parseJSON(json);
                            $.each(json, function (i, v) {
                                data.push({
                                    id: v.id,
                                    pid: v.pid,
                                    prop: {
                                        name: v.name,
                                        content: v.ip + ' | ' + v.model + ' | '
                                    }
                                });
                            });
                        }
                        return data;
                    },
                    event: function (node) {
                        scene.goDown(node.pid, function () {
                            util.nodeAsCenter(scene, scene.findNode('id=' + node.id)[0]);
                        });
                    }
                };
            }
        }, {
            name: '基本属性',
            value: 'prop',
            search: function (text) {
                return {
                    results: scene.findNode(text.trim()),
                    event: function (node) {
                        util.nodeAsCenter(scene, node);
                    }
                };
            }
        }, {
            name: '业务属性',
            value: 'exprop',
            search: function (text) {
                return {
                    results: scene.findNode(text.trim(), true),
                    event: function (node) {
                        util.nodeAsCenter(scene, node);
                    }
                };
            }
        }
        ]);
        toolbarItem.searchBtn.click(function () {
            toolbarItem.doSearch();
        });
        //自动布局
        toolbarItem.edit_autoLayout.click(function () {
            if (toolbarItem.edit_autoLayout._isClick) {
                autoLayoutItem.win.show();
            } else {
                autoLayoutItem.win.trigger('resetWindow');
            }
        });
        //创建图形
        toolbarItem.edit_createImage.click(function () {
            if (toolbarItem.edit_createImage._isClick) {
                imageNodeItem.head.find("span:first").text('创建节点');
                imageNodeItem.win.show();
                imageNodeItem.win.mode = 'create';
            } else {
                imageNodeItem.win.trigger('resetWindow');
            }
        });
        //创建文本
        toolbarItem.edit_createText.click(function () {
            if (toolbarItem.edit_createText._isClick) {
                textNodeItem.head.find("span:first").text('创建文本');
                textNodeItem.win.show();
                textNodeItem.win.mode = 'create';
            } else {
                textNodeItem.win.trigger('resetWindow');
            }
        });
        //创建链路模式切换
        toolbarItem.edit_createLink.click(function () {
            if (toolbarItem.edit_createLink._isClick) {
                //注册链路编辑模式
                scene.initLinkEditMode(toolbarItem.edit_createLink);
                scene.setMode('linkEdit');
                toolWindow.alert({
                    title: '链路编辑模式',
                    content: '<h3>使用说明:</h3><h4>首次左键选中节点作为链路起点，次选节点为终点，连续两次选中不同节点则建立链接。</h4><h3>Error:</h3><h4>暂不支持对分组,分组的缩放节点,分组成员之间创建链接!</h4>'
                });
            } else {
                scene.setMode('edit');
            }
        });
        //画线
        toolbarItem.edit_createLine.click(function () {
            if (toolbarItem.edit_createLine._isClick) {
                scene.setMode('linkEdit');
                lineItem.win.trigger('drawLine');
            }else{
                lineItem.win.trigger('resetWindow');
                scene.setMode('edit');
            }
        });
        //保存
        toolbarItem.edit_save.click(function () {
            toolWindow.confirm({
                title: '保存拓扑',
                content: '<h4>确定保存当前拓扑数据?</h4>',
                handler: function (e) {
                    if (e) {
                        scene.saveTopo();
                    } else {
                        console.info("cancel save");
                    }
                }
            });
        });
        //告警渲染
        toolbarItem.edit_alarm_config.click(function () {
            if(toolbarItem.edit_alarm_config._isClick){
                alarmConfigItem.win.show();
            }else{
                alarmConfigItem.win.trigger('resetWindow');
            }
        });
    }

    function inteColorPalette() {
        "use strict";
        var aaColor = [
            ['#000000', '#424242', '#636363', '#9C9C94', '#CEC6CE', '#EFEFEF', '#F7F7F7', '#FFFFFF'],
            ['#FF0000', '#FF9C00', '#FFFF00', '#00FF00', '#00FFFF', '#0000FF', '#9C00FF', '#FF00FF'],
            ['#F7C6CE', '#FFE7CE', '#FFEFC6', '#D6EFD6', '#CEDEE7', '#CEE7F7', '#D6D6E7', '#E7D6DE'],
            ['#E79C9C', '#FFC69C', '#FFE79C', '#B5D6A5', '#A5C6CE', '#9CC6EF', '#B5A5D6', '#D6A5BD'],
            ['#E76363', '#F7AD6B', '#FFD663', '#94BD7B', '#73A5AD', '#6BADDE', '#8C7BC6', '#C67BA5'],
            ['#CE0000', '#E79439', '#EFC631', '#6BA54A', '#4A7B8C', '#3984C6', '#634AA5', '#A54A7B'],
            ['#9C0000', '#B56308', '#BD9400', '#397B21', '#104A5A', '#085294', '#311873', '#731842'],
            ['#630000', '#7B3900', '#846300', '#295218', '#083139', '#003163', '#21104A', '#4A1031']
        ];

        var createPaletteElement = function (element, _aaColor) {
            element.addClass('bootstrap-colorpalette');
            var aHTML = [];
            $.each(_aaColor, function (i, aColor) {
                aHTML.push('<div>');
                $.each(aColor, function (i, sColor) {
                    var sButton = ['<button type="button" class="btn-color" style="background-color:', sColor,
                        '" data-value="', sColor,
                        '" title="', sColor,
                        '"></button>'].join('');
                    aHTML.push(sButton);
                });
                aHTML.push('</div>');
            });
            element.html(aHTML.join(''));
        };

        var attachEvent = function (palette) {
            palette.element.on('click', function (e) {
                var welTarget = $(e.target),
                    welBtn = welTarget.closest('.btn-color');

                if (!welBtn[0]) {
                    return;
                }

                var value = welBtn.attr('data-value');
                palette.value = value;
                palette.element.trigger({
                    type: 'selectColor',
                    color: value,
                    element: palette.element
                });
            });
        };

        var Palette = function (element, options) {
            this.element = element;
            createPaletteElement(element, options && options.colors || aaColor);
            attachEvent(this);
        };

        $.fn.extend({
            colorPalette: function (options) {
                this.each(function () {
                    var $this = $(this),
                        data = $this.data('colorpalette');
                    if (!data) {
                        $this.data('colorpalette', new Palette($this, options));
                    }
                });
                return this;
            }
        });
    }

    return {
        init: init,
        getTools: function () {
            return toolWindow;
        },
        setMode: function (mode) {
            toolbarItem.setMode(mode);
        },
        getToolBar: function () {
            return toolbarItem;
        },
        editImage: function (node) {
            imageNodeItem.edit(node);
        },
        editText: function (node) {
            textNodeItem.edit(node);
        },
        editLink: function (link) {
            linkItem.edit(link);
        },
        editExprop: function (element) {
            exPropItem.edit(element);
        },
        createContainer: function (element) {
            containerItem.create(element);
        },
        editContainer: function (element) {
            containerItem.edit(element);
        }
    };
});