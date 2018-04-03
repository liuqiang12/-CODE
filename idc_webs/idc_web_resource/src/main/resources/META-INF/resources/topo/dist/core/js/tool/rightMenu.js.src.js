/**
 * Created by Victor on 2016/8/29.
 */
define([ 'tool/util', 'window/Menu', 'tool/winHandler', 'element/Node'], function ( util, Menu, winHandler, Node) {
    //初始化常量
    var STAGE;
    var SCENE;
    //运行时变量
    var storeElement;//用于剪切，取消剪切和黏贴剪切
    var copyElement;//用于复制和黏贴
    var mytarget;
    var Default = {
        item: {
            TopoEvent_DEBUG: function (menu) {
                return {
                    name: "Debug",
                    icon: 'fa fa-info',
                    handler: function () {
                        var text = '';
                        element = menu.target;
                        if (!element) {
                            element = SCENE;
                        }
                        console.info(element);
                        if (element.elementType == 'node' || element.elementType == 'container') {
                            text += "<h5>X:&nbsp;" + element.x + "&nbsp;&nbsp;Y:&nbsp;" + element.y + "</h5>";
                        } else if (element.elementType == 'link'&&element.prop.useType!='line') {
                            text += "<h4>From:</h4>" +
                                "<h5>&nbsp;&nbsp;&nbsp;&nbsp;name:" + element.nodeA.prop.name + "</h5>" +
                                "<h5>&nbsp;&nbsp;&nbsp;&nbsp;id:" + element.nodeA.prop.id + "</h5>" +
                                "<h4>To:</h4>" +
                                "<h5>&nbsp;&nbsp;&nbsp;&nbsp;name:" + element.nodeZ.prop.name + "</h5>" +
                                "<h5>&nbsp;&nbsp;&nbsp;&nbsp;id:" + element.nodeZ.prop.id + "</h5>";
                        } else if (element.elementType == "scene") {
                            text += "<h4>Elements:" + element.childs.length + "</h4>"
                        }
                        text += '<h4>Prop:</h4>';
                        $.each(element.prop, function (k, v) {
                            if (v instanceof Array) {
                                text += "<h5>&nbsp;&nbsp;&nbsp;&nbsp;" + k + ':&nbsp;' + "[";
                                $.each(v, function (i, v) {
                                    text += "&nbsp;" + v
                                });
                                text += "]</h5>";
                            } else if (v && v.elementType && (v.elementType == 'node' || v.elementType == 'container')) {
                                text += "<h5>&nbsp;&nbsp;&nbsp;&nbsp;" + k + ":&nbsp;" + (v.prop.name || v) + "</h5>"
                            } else if (v && v instanceof Object && !v._id) {//没有_id说明是设定的属性
                                text += "<h5>&nbsp;&nbsp;&nbsp;&nbsp;" + k + ':&nbsp;' + "[";
                                $.each(v, function (i, v) {
                                    text += "&nbsp;" + i + "&nbsp;:" + v + ','
                                });
                                text += "]</h5>";
                            }
                            else {
                                text += "<h5>&nbsp;&nbsp;&nbsp;&nbsp;" + k + ":&nbsp;" + v + "</h5>";
                            }
                        });
                        winHandler.getTools().alert({
                            title: 'Debug',
                            content: text
                        });
                    }
                }
            },
            TopoEvent_REFRESH: function () {
                return {
                    name: '刷新',
                    icon: 'fa fa-refresh fa-spin',
                    handler: function () {
                        SCENE.refresh();
                    },
                    filter: function (e) {
                        return !e;
                    }
                }
            },
            TopoEvent_SHOWTOOLBAR: function () {
                return {
                    name: '显示工具栏',
                    icon: 'fa fa-gears',
                    handler: function () {
                        winHandler.getToolBar().show();
                    },
                    filter: function (e) {
                        return !e && winHandler.getToolBar().fatherBar.css('display') == 'none';
                    }
                }
            },
            TopoEvent_HIDETOOLBAR: function () {
                return {
                    name: '隐藏工具栏',
                    icon: 'fa fa-gears',
                    handler: function () {
                        winHandler.getToolBar().hide();
                    },
                    filter: function (e) {
                        return !e && winHandler.getToolBar().fatherBar.css('display') != 'none';
                    }
                }
            },
            TopoEvent_DELETE: function (menu) {
                return {
                    name: '删除对象',
                    icon: 'fa fa-trash-o',
                    handler: function () {
                        var selected = SCENE.selectedElements;
                        winHandler.getTools().confirm({
                            content: '<h4>尝试删除' + selected.length + '个对象？</h4>',
                            width: 300,
                            handler: function (e) {
                                if (e && selected.length > 1) {
                                    $.each(selected, function (i, v) {
                                        SCENE.removeElement(v);
                                    });
                                } else if (e) {
                                    SCENE.removeElement(menu.target);
                                }
                            }
                        });
                    },
                    filter: function (element) {
                        return (element && !(element.elementType == 'container' || (element.elementType == 'node' && element.prop.nodeType == 'container')));
                    }
                }
            },
            TopoEvent_GO_BACK: function () {
                return {
                    name: '返回上一层',
                    icon: 'fa fa-reply-all',
                    handler: function () {
                        SCENE.goBack();
                    },
                    filter: function () {
                        return SCENE.prop.path.length > 1;
                    }
                }
            },
            TopoEvent_CUT: function (menu) {
                return {
                    name: '剪切单个节点',
                    icon: 'glyphicon glyphicon-copyright-mark',
                    handler: function () {
                        if (storeElement) {
                            return;
                        }
                        if (menu.target && menu.target.elementType == 'node') {
                            storeElement = menu.target;
                            storeElement.hide();
                            util.toggleElemnts(storeElement.inLinks);
                            util.toggleElemnts(storeElement.outLinks);
                        }
                    },
                    filter: function (element) {
                        return element && element.elementType == 'node' && element.prop.nodeType == 'node' && !storeElement;
                    }
                }
            },
            TopoEvent_CANCAL_CUT: function () {
                return {
                    name: '取消剪切',
                    icon: 'fa fa-close',
                    handler: function () {
                        if (storeElement) {
                            storeElement.show();
                            util.toggleElemnts(storeElement.inLinks);
                            util.toggleElemnts(storeElement.outLinks);
                            storeElement = '';
                        }
                    },
                    filter: function () {
                        return storeElement && !storeElement.visible;
                    }
                }
            },
            TopoEvent_PASTE: function (menu) {
                return {
                    name: '黏贴剪切',
                    icon: 'fa fa-paypal',
                    handler: function () {
                        if (storeElement) {
                            storeElement.show();
                            if (SCENE.childs.indexOf(storeElement) > 0) {
                                util.toggleElemnts(storeElement.inLinks);
                                util.toggleElemnts(storeElement.outLinks);
                            } else {
                                SCENE.addElement(storeElement);
                            }
                            storeElement.setLocation(menu.x, menu.y);
                            storeElement = '';
                        }
                    },
                    filter: function () {
                        return storeElement && !storeElement.visible;
                    }
                }
            },
            TopoEvent_COPY: function (menu) {
                return {
                    name: '复制单个对象',
                    icon: 'glyphicon glyphicon-copyright-mark',
                    handler: function (e) {
                        var target = menu.target;
                        if (target && target.elementType == 'node' && target.prop.nodeType == 'node') {
                            copyElement = Node.createNode({
                                textPosition: target.textPosition,
                                text: target.text,
                                zIndex: target.zIndex,
                                alpha: target.alpha,
                                image: target.prop.image,
                                width: target.width,
                                height: target.height
                            });
                            $.each(target.prop, function (key, value) {
                                copyElement.prop[key] = value;
                            });
                            $.each(target.exprop, function (key, value) {
                                copyElement.exprop[key] = value;
                            });
                        }
                    },
                    filter: function (element) {
                        return element && element.elementType == 'node' && element.prop.nodeType == 'node';
                    }
                }
            },
            TopoEvent_PASTE_COPY: function (menu) {
                return {
                    name: '黏贴复制',
                    icon: 'fa fa-paypal',
                    handler: function (e) {
                        if (copyElement) {
                            copyElement.setLocation(menu.x, menu.y);
                            SCENE.addElement(copyElement);
                            copyElement = '';
                        }
                    },
                    filter: function (element) {
                        return copyElement && copyElement.visible;
                    }
                }
            },
            TopoEvent_SELECT_ALL: function () {
                return {
                    name: '全选',
                    icon: 'fa fa-filter',
                    handler: function () {
                        $.each(SCENE.childs, function (i, v) {
                            if (v.elementType == 'node' && v.prop.nodeType == 'node') {
                                v.selected = true;
                                SCENE.selectedElements.push(v);
                            }
                        });
                    },
                    filter: function (element) {
                        return !element && SCENE.getAweight() > 0;
                    }
                }
            },
            TopoEvent_SELECT_UNSELECTED: function () {
                return {
                    name: '反选',
                    icon: 'fa fa-close',
                    handler: function () {
                        $.each(SCENE.childs, function (i, v) {
                            if (v.elementType == 'node' && v.prop.nodeType == 'node') {
                                v.selected = false;
                            }
                        });
                    },
                    filter: function (element) {
                        return !element && SCENE.getAweight() > 0;
                    }
                }
            },
            TopoEvent_MODIFY_NODE: function (menu) {
                return {
                    name: '编辑节点',
                    icon: 'fa fa-cog fa-spin',
                    handler: function () {
                        winHandler.editImage(menu.target);
                    },
                    filter: function (element) {
                        return element && element.elementType == 'node' && element.prop.nodeType == 'node';
                    }
                }
            },
            TopoEvent_MODIFY_TEXT: function (menu) {
                return {
                    name: '编辑文本',
                    icon: 'fa fa-cog fa-spin',
                    handler: function () {
                        winHandler.editText(menu.target);
                    },
                    filter: function (element) {
                        return element && element.elementType == 'TextNode';
                    }
                }
            },
            TopoEvent_MODIFY_LINK: function (menu) {
                return {
                    name: '编辑链路',
                    icon: 'fa fa-cog fa-spin',
                    handler: function () {
                        winHandler.editLink(menu.target);
                    },
                    filter: function (element) {
                        return element && element.elementType == 'link' && element.prop.linkType != 'line'&& element.prop.linkType != 'child';
                    }
                }
            },
            TopoEvent_ELEMENT_VISIABLE_FALSE: function (menu) {
                return {
                    name: '链路设为隐藏',
                    icon: 'glyphicon glyphicon-eye-close',
                    handler: function () {
                        menu.target.hide();
                    },
                    filter: function (element) {
                        return element && element.elementType == 'link';
                    }
                }
            },
            TopoEvent_ELEMENT_VISIABLE_TRUE: function (menu) {
                return {
                    name: '显示全部',
                    icon: 'glyphicon glyphicon-eye-open',
                    handler: function () {
                        $.each(SCENE.childs, function (i, v) {
                            if(v.elementType == 'node' && v.prop.nodeType == 'container'){
                                return true;
                            }
                            if(v.show){
                                v.show();
                            }
                        });
                    }
                }
            },
            TopoEvent_HIGHTLIGHT_CONNECT: function (menu) {
                return {
                    name: '高亮相关网元',
                    icon: 'glyphicon glyphicon-eye-open',
                    handler: function () {
                        var node = menu.target;
                        var highLight = [];
                        highLight.push(node);
                        $.each(SCENE.childs, function (i, v) {
                            if (v.elementType == 'node' || v.elementType == 'link') {
                                v.setAlpha();
                            }
                        });
                        $.each(node.inLinks, function (i, v) {
                            util.arrayPush(highLight, v);
                            util.arrayPush(highLight, v.nodeA);
                            util.arrayPush(highLight, v.nodeZ);
                        });
                        $.each(node.outLinks, function (i, v) {
                            util.arrayPush(highLight, v);
                            util.arrayPush(highLight, v.nodeA);
                            util.arrayPush(highLight, v.nodeZ);
                        });
                        $.each(highLight, function (i, v) {
                            v.setAlpha(1);
                        })
                    },
                    filter: function (element) {
                        return element && element.elementType == 'node' && element.prop.nodeType != 'container';
                    }
                }
            },
            TopoEvent_HIGHTLIGHT_CANCEL: function (menu) {
                return {
                    name: '取消高亮',
                    icon: 'glyphicon glyphicon-eye-close',
                    handler: function () {
                        $.each(SCENE.childs, function (i, v) {
                            if (v.elementType == 'node' || v.elementType == 'link') {
                                v.setAlpha(1);
                            }
                        });
                    },
                    filter: function (element) {
                        return element && element.elementType == 'node' && element.prop.nodeType != 'container';
                    }
                }
            },
            TopoEvent_OPENWINDOW: function () {
                return {
                    name: '查看图层信息',
                    icon: 'glyphicon glyphicon-folder-open',
                    handler: function () {
                        if (SCENE.prop.url) {
                            window.open(SCENE.prop.url, '_blank');
                        }
                    },
                    filter: function (element) {
                        return !element;
                    }
                }
            },
            TopoEvent_CREATE_GROUP: function (menu) {
                return {
                    name: '创建分组',
                    icon: 'fa fa-plus',
                    handler: function () {
                        var selected = SCENE.selectedElements;
                        if (menu.target) {
                            winHandler.getTools().confirm({
                                content: '<h4>将尝试为' + selected.length + '个对象创建分组？</h4>',
                                width: 300,
                                handler: function (e) {
                                    if (e) {
                                        winHandler.createContainer(selected);
                                    }
                                }
                            });
                        }
                    },
                    filter: function (element) {
                        return element && element.elementType == 'node' && !element.prop.container && element.prop.nodeType != 'container';
                    }
                }
            },
            TopoEvent_MODIFY_GROUP: function (menu) {
                return {
                    name: '编辑分组',
                    icon: 'fa fa-cog fa-spin',
                    handler: function () {
                        var target = menu.target;
                        if (target.elementType == 'container') {
                            winHandler.editContainer(target);
                        } else {
                            console.info();
                            winHandler.editContainer(target.prop.expandTo);
                        }

                    },
                    filter: function (element) {
                        return element && (element.elementType == 'container' || (element.elementType == 'node' && element.prop.nodeType == 'container'));
                    }
                }
            },
            TopoEvent_REMOVE_FROM_GROUP: function (menu) {
                return {
                    name: '从组中删除',
                    icon: 'fa fa-minus',
                    handler: function () {
                        menu.target.prop.container.removeNode(menu.target);
                    },
                    filter: function (element) {
                        return element && element.prop.container;
                    }
                }
            },
            TopoEvent_DELETE_GROUP: function (menu) {
                return {
                    name: '解散分组',
                    icon: 'fa fa-trash-o',
                    handler: function () {
                        var target;
                        if (menu.target.elementType == 'node' && menu.target.prop.nodeType == 'container') {
                            target = menu.target.prop.expandTo;
                        } else {
                            target = menu.target;
                        }
                        target.prop.setMember = {showName: true, showLink: true};
                        target.toggleExpand();
                        SCENE.removeElement(target);
                    },
                    filter: function (element) {
                        return element && (element.elementType == 'container' || (element.elementType == 'node' && element.prop.nodeType == 'container'));
                    }
                }
            },
            TopoEvent_CONFIG_PROPERTY: function (menu) {
                return {
                    name: '配置业务属性',
                    icon: 'fa fa-cog fa-spin',
                    handler: function () {
                        if (menu.target) {
                            winHandler.editExprop(menu.target);
                        } else {
                            winHandler.editExprop(SCENE);
                        }
                    }
                }
            }
        },
        subItem: {}

    };

    function filterForMenu(json) {
        var menu = [];
        if (json) {
            $.each(json.menus.menu, function (i, v) {
                if (typeof v.dblclick == 'undefined') {
                    menu.push(v);
                }
            });
        }
        return menu;
    }

    function isInclude(a, b) {
        if (!a || !b) {
            return false;
        }
        if (typeof a != 'string') {
            a = a + '';
        }
        if (typeof b != 'string') {
            b = b + '';
        }
        return a.charAt(a.length - b.length) == '1';
    }

    var rightMenu;
    return {
        init: function (stage, scene, json) {
            //定义常量
            STAGE = stage;
            SCENE = scene;
            rightMenu = new Menu('topo_rightMenu');
            rightMenu.setTrigger(SCENE);
            $.each(Default.item, function (i, v) {
                rightMenu.addItem(v(rightMenu));
            });
            if (!json) {
                return;
            }
            var menuJson = filterForMenu(json);
            var level_Two = rightMenu.addSubMenu({
                name: "(参考)",
                divide: true
            });
            $.each(menuJson, function (i, v) {
                level_Two.addItem({
                    name: v.name,
                    handler: function () {
                        console.info(v.action);
                    },
                    filter: function (e) {
                        if (!e) {
                            e = SCENE;
                        }
                        return isInclude(v.aweight, SCENE.getAweight()) && isInclude(v.eweight, e.prop.eweight);
                    }
                });
            });
        },
        addUrlMenu: function (json) {
            $.each(json, function (k, v) {
                var level_Two = rightMenu.addSubMenu({
                    name: k,
                    filter: function (e) {
                        if (!e) {
                            e = SCENE;
                        }
                        if (v.filter) {
                            return eval("(function () {return " + v.filter + "})()")(e);
                        } else {
                            return true;
                        }
                    }
                });
                $.each(v.item, function (i, m) {
                    level_Two.addItem({
                        name: m.name,
                        icon: 'glyphicon glyphicon-folder-open',
                        handler: function () {
                            var param = '';
                            $.each(m.data, function (j, d) {
                                if (j > 0) {
                                    param += '&';
                                } else {
                                    param += '?';
                                }
                                param += d.key + '=';
                                if (d.fixed) {
                                    if (d.value) {
                                        param += d.value;
                                    } else if (d.fn) {
                                        param += eval("(function () {return " + d.fn + "})()")();
                                    }
                                } else {
                                    if (d.fn) {
                                        try {
                                            param += eval("(function () {return " + d.fn + "})()")(rightMenu.target.prop[d.value]);
                                        } catch (e) {
                                            console.info(e);
                                            console.info("url menu run fn error");
                                        }
                                    } else {
                                        param += rightMenu.target.prop[d.value];
                                    }
                                }
                            });
                            if (m.url) {
                                window.open(m.url + param, '_blank');
                                console.info(m.url + param);
                            } else if (m.path) {
                                window.open('http://'+window.location.host + m.path + param, '_blank');
                                console.info('http://'+window.location.host+ m.path + param);
                            }
                        },
                        filter: function (e) {
                            if (!e) {
                                e = SCENE;
                            }
                            if (m.filter) {
                                return eval("(function () {return " + m.filter + "})()")(e);
                            } else {
                                return true;
                            }
                        }
                    });
                });
            });
        }

    }
});