/**
 * Created by Victor on 2016/8/26.
 */
define(['tool/util', 'tool/brush', 'tool/winHandler', 'element/Link', 'JTopo'], function (util, brush, winHandler, Link) {
    var JtopoScene = JTopo.Scene;

    function Scene() {
        JtopoScene.call(this);
        var self = this;
        self.mode = 'drag';
        self.prop = {
            name: '',
            id: '',
            pid: '',
            mode: 'drag',
            eweight: 1,
            path: [],
            mousemove: {
                target: '',
                x: 0,
                y: 0
            },
            offsetX: 0,
            offsetY: 0
        };
        self.exprop = {};
        this.addEventListener('mousemove', function (e) {
            self.prop.mousemove.target = e.target;
            self.prop.mousemove.x = e.x;
            self.prop.mousemove.y = e.y;
            self.prop.originX = -self.translateX;
            self.prop.originY = -self.translateY;
        });
    }

    util.inherits(Scene, JtopoScene);
    Scene.prototype.addElement = function (element) {
        element.prop.scene=this;
        if (element.elementType == "container") {
            this.add(element.prop.containerNode);
        }
        this.add(element);
    };
    Scene.prototype.removeElement = function (element) {
        var self=this;
        if (element) {
            if(element.prop){
                element.prop.scene='deleted';
            }
            switch (element.elementType) {
                case 'container':
                    element.show();
                    $.each(element.childs, function (i, v) {
                        v.prop.container = null;
                    });
                    this.remove(element.prop.containerNode);
                    break;
                case 'link':
                    if(element.prop.linkType!='child'&&element.prop.children){
                        $.each(element.prop.children,function(i,v){
                            self.remove(v);
                        });
                    }
            }
            this.remove(element);
        }
    };
    Scene.prototype.setMode = function (mode) {
        switch (mode) {
            case 'select':
                this.mode = mode;
                this.prop.mode = mode;
                break;
            case 'edit':
                this.mode = mode;
                this.prop.mode = mode;
                break;
            case 'drag':
                this.mode = mode;
                this.prop.mode = mode;
                break;
            case 'linkEdit':
                this.mode = 'select';
                this.prop.mode = mode;
                break;
            default :
                this.mode = 'drag';
                this.prop.mode = 'drag'
        }
    };
    Scene.prototype.getAweight = function () {
        var aweight = 0;
        switch (this.prop.mode) {
            case 'drag':
                aweight = 0;
                break;
            case 'select':
                aweight = 1;
                break;
            case 'edit':
                aweight = 10;
                break;
            case 'linkEdit':
                aweight = 100;
                break;
        }
        return aweight;
    };
    Scene.prototype.findNode = function (filter, isexprop) {
        var elements = [];
        if (filter) {
            if ($.isFunction(filter)) {
                elements = this.findElements(function (e) {
                    return e.elementType == 'node' && filter(e);
                });
            } else {
                var arr = filter.split("=");
                if (arr.length == 2) {
                    var key = arr[0].trim();
                    var value = arr[1].trim();
                    if (isexprop) {
                        elements = this.findElements(function (e) {
                            return e.elementType == 'node' && e.exprop[key] == value;
                        });
                    } else {
                        elements = this.findElements(function (e) {
                            return e.elementType == 'node' && e.prop[key] == value;
                        });
                    }
                }
            }
        } else {
            elements = this.findElements(function (e) {
                return e.elementType == 'node' && e.prop.nodeType != "container";
            });
        }
        return elements;
    };
    Scene.prototype.findLink = function (filter, isexprop) {
        var elements = [];
        if (filter) {
            if ($.isFunction(filter)) {
                elements = this.findElements(function (e) {
                    return e.elementType == 'link' && filter(e);
                });
            } else {
                var arr = filter.split("=");
                if (arr.length == 2) {
                    var key = arr[0];
                    var value = arr[1];
                    if (isexprop) {
                        elements = this.findElements(function (e) {
                            return e.elementType == 'link' && e.exprop[key] == value;
                        });
                    } else {
                        elements = this.findElements(function (e) {
                            return e.elementType == 'link' && e.prop[key] == value;
                        });
                    }
                }
            }
        } else {
            elements = this.findElements(function (e) {
                return e.elementType == 'link';
            });
        }
        return elements;
    };
    Scene.prototype.findContainer = function (filter, isexprop) {
        var elements = [];
        if (filter) {
            if ($.isFunction(filter)) {
                elements = this.findElements(function (e) {
                    return e.elementType == 'container' && filter(e);
                });
            } else {
                var arr = filter.split("=");
                if (arr.length == 2) {
                    var key = arr[0];
                    var value = arr[1];
                    if (isexprop) {
                        elements = this.findElements(function (e) {
                            return e.elementType == 'container' && e.exprop[key] == value;
                        });
                    } else {
                        elements = this.findElements(function (e) {
                            return e.elementType == 'container' && e.prop[key] == value;
                        });
                    }
                }
            }
        } else {
            elements = this.findElements(function (e) {
                return e.elementType == 'container';
            });
        }
        return elements;
    };
    Scene.prototype.goBack = function (fn) {
        var self = this;
        if (this.prop.path.length > 1) {
            var id = this.prop.path[this.prop.path.length - 2];
            while (id == this.prop.path[this.prop.path.length - 1] && this.prop.path.length - 2 > 0) {
                self.prop.path.pop();
                id = this.prop.path[this.prop.path.length - 2];
            }
            brush[this.prop.config.brush].drawTopo({
                scene: this,
                id: id,
                type: 'goBack',
                fn: function (data) {
                    self.prop.path.pop();
                    if (fn && $.isFunction(fn)) {
                        fn(data);
                    }
                }
            });
        } else {
            winHandler.getTools().alert({content: '<h4>已到达最顶层</h4>'});
        }
    };
    Scene.prototype.goDown = function (id, fn) {
        var self = this;
        brush[this.prop.config.brush].drawTopo({
            scene: this,
            id: id,
            type: 'goDown',
            fn: function (data) {
                self.prop.path.push(id);
                if (fn && $.isFunction(fn)) {
                    fn(data);
                }
            }
        });
    };
    Scene.prototype.refresh = function () {
        brush[this.prop.config.brush].drawTopo({
            scene: this,
            id: this.prop.pid,
            type: 'refresh'
        });
    };
    Scene.prototype.saveTopo = function () {
        var self = this;
        brush[self.prop.config.brush].saveTopo(self, function (data) {
            if (data == 1) {
                winHandler.getTools().alert({
                    content: '<h4>保存成功！</h4>'
                });
            } else {
                winHandler.getTools().alert({
                    content: '<h4>保存失败!请登录后尝试！</h4>'
                });
            }
        });
    };
    //注册链路编辑模式
    var inited = false;
    Scene.prototype.initLinkEditMode = function (btn) {
        if (inited) {
            return;
        } else {
            inited = true;
        }
        var self = this;
        var beginNode = null;
        var tempNodeA = new JTopo.Node();
        tempNodeA.setSize(1, 1);
        var tempNodeZ = new JTopo.Node();
        tempNodeZ.setSize(1, 1);
        var link = Link.directLink({
            start: tempNodeA,
            end: tempNodeZ
        });
        self.mouseup(function (e) {
            if (self.prop.mode != 'linkEdit' || !(btn && btn._isClick) || e.button == 2) {
                self.removeElement(link);
                return;
            }
            if (e.target != null && (e.target instanceof JTopo.Node || e.target instanceof JTopo.Container)) {
                if (beginNode == null) {
                    beginNode = e.target;
                    self.add(link);
                    tempNodeA.setLocation(e.x, e.y);
                    tempNodeZ.setLocation(e.x, e.y);
                } else if (beginNode !== e.target) {
                    var endNode = e.target;
                    if (beginNode.elementType == 'node' && beginNode.prop.nodeType == "container" || endNode.elementType == 'node' && endNode.prop.nodeType == "container") {
                        //分组的缩放节点
                    } else if (beginNode.elementType == 'node' && beginNode.prop.container && endNode.elementType == 'node' && endNode.prop.container) {
                        //分组成员节点之间
                    } else if (beginNode.elementType == 'container' || endNode.elementType == 'container') {
                        //分组之间
                    } else {
                        var l = Link.directLink({
                            start: beginNode,
                            end: endNode
                        });
                        self.add(l);
                        beginNode = null;
                        self.remove(link);
                    }
                } else {
                    beginNode = null;
                }
            } else {
                beginNode = null;
                self.remove(link);
            }
        });
        self.mousedown(function (e) {
            if ((e.target == null || e.target === beginNode || e.target === link) && self.prop.mode == 'linkEdit' && (btn && btn._isClick)) {
                self.remove(link);
            }
        });
        self.mousemove(function (e) {
            if (self.prop.mode == 'linkEdit' && (btn && btn._isClick)) {
                tempNodeZ.setLocation(e.x, e.y);
            }
        });
    };
    return Scene;
});