/**
 * Created by Victor on 2016/8/24.
 */
define(['tool/util', 'element/Node', 'element/Link', 'JTopo'], function (util, Node, Link) {
    var JtopoContainer = JTopo.Container;
    var defaultLayout = function (container, children) {
        if (children.length > 0) {
            var left = 1e7,
                right = -1e7,
                top = 1e7,
                bottom = -1e7,
                width = right - left,
                height = bottom - top;
            for (var i = 0; i < children.length; i++) {
                var child = children[i];
                child.x <= left && (left = child.x);
                (child.x+child.width) >= right && (right = child.x+child.width);
                child.y <= top && (top = child.y);
                (child.y+child.height) >= bottom && (bottom = child.y+child.height);
                width = right - left;
                height = bottom - top;
            }
            container.x = left;
            container.y = top;
            container.width = width;
            container.height = height;
        }
    };
    var SCENE;

    function Container(text) {
        JtopoContainer.call(this, text);
    }

    util.inherits(Container, JtopoContainer);
    Container.prototype.addNode = function (node) {
        var self = this;
        if (node.prop.nodeType != 'container' && !node.prop.container) {
            //if (self.prop.outLink) {
            //    $.each(self.prop.outLink, function (i, v) {
            //        if (v.nodeA == node || v.nodeZ == node) {
            //            SCENE.removeElement(v);
            //            util.arrayDelete(self.prop.outLink, v);
            //        }
            //    });
            //}
            node.prop.container = this;
            this.setMember(node);
            this.add(node);
        }
    };
    Container.prototype.removeNode = function (node) {
        node.prop.container = null;
        node.setTextPosition(node.textPosition);
        util.toggleElemnts(node.inLinks, true);
        util.toggleElemnts(node.outLinks, true);
        this.remove(node);
    };
    Container.prototype.isInside = function (node) {
        var centerX = node.x + node.width / 2;
        var centerY = node.y + node.height / 2;
        return !this.isChild(node) && centerX > this.x && centerX < this.x + this.width && centerY > this.y && centerY < this.y + this.height;
    };
    Container.prototype.isChild = function (element) {
        return element && element.prop.container && element.prop.container == this;
    };
    Container.prototype.setAttr = function (object) {
        util.setAttr(this, object);//批量修改属性
        util.setAttr(this.prop.containerNode, object);//批量修改属性
    };
    Container.prototype.setName = function (name) {
        this.text = name;
        this.prop.name = name;
        this.prop.containerNode.text = name;
        this.prop.containerNode.prop.name = name;
    };
    Container.prototype.setTextPosition = function (textPosition) {
        this.textPosition = textPosition;
        this.prop.textPosition = textPosition;
        this.text = this.prop.name;
        switch (textPosition) {
            case 'Hidden':
                this.text = '';
                break;
            case 'Bottom_Center':
                this.textOffsetX = 0;
                this.textOffsetY = 20;
                break;
            case 'Top_Center':
                this.textOffsetX = 0;
                this.textOffsetY = -20;
                break;
            case 'Middle_Left':
                this.textOffsetX = -util.getStringWidth(this.prop.name, this.font.substring(0, this.font.indexOf("px")) + 'px') - 20;
                this.textOffsetY = 0;
                break;
            case 'Middle_Right':
                this.textOffsetX = util.getStringWidth(this.prop.name, this.font.substring(0, this.font.indexOf("px")) + 'px') + 20;
                this.textOffsetY = 0;
                break;
        }
        this.prop.containerNode.setTextPosition(textPosition);
    };
    Container.prototype.setImage = function (image) {
        this.prop.image = image;
        this.prop.containerNode.setNodeImage(image);
    };
    Container.prototype.setLayout = function (layout) {
        var selected;
        if (layout) {
            switch (layout.type) {
                case 'flow':
                    // 流式布局（水平,垂直间隔)
                    selected = JTopo.layout.FlowLayout(layout.row, layout.column);
                    break;
                case 'grid':
                    // 网格布局(行,列)
                    selected = JTopo.layout.GridLayout(layout.row, layout.column);
                    break;
            }
            this.prop.layoutType = layout.type;
            this.prop.layoutRow = layout.row;
            this.prop.layoutColumn = layout.column;
        } else {
            selected = defaultLayout;
            this.prop.layoutType = 'set';
        }
        this.layout = selected;
    };
    Container.prototype.hide = function () {
        $.each(this.childs, function (i, value) {
            util.toggleElemnts(value.inLinks, false);
            util.toggleElemnts(value.outLinks, false);
            value.hide();
        });
        util.toggleElemnts(this.inLinks, false);
        util.toggleElemnts(this.outLinks, false);
        this.visible = false;
    };
    Container.prototype.show = function () {
        var self = this;
        $.each(this.childs, function (i, v) {
            self.setMember(v);
        });
        util.toggleElemnts(this.inLinks, true);
        util.toggleElemnts(this.outLinks, true);
        this.visible = true;
    };
    Container.prototype.getChildren = function (filter) {
        var children = [];
        if (filter && $.isFunction(filter)) {
            $.each(this.childs, function (i, value) {
                if (filter(value)) {
                    children.push(value);
                }
            });
        } else {
            children = this.childs;
        }
        return children;
    };
    //根据组属性设定成员
    Container.prototype.setMember = function (node) {
        var self = this;
        if (this.prop.setMember.showName) {
            node.setTextPosition('Bottom_Center');
        } else {
            node.setTextPosition('Hidden');
        }
        if (this.prop.setMember.showLink) {
            util.toggleElemnts(node.inLinks, true);
            util.toggleElemnts(node.outLinks, true);
        } else {
            if (node.inLinks) {
                $.each(node.inLinks, function (i, v) {
                    if (self.isChild(v.nodeA)) {//入线的源是否在组内
                        v.hide();
                    } else {
                        v.show();
                    }
                });
            }
            if (node.outLinks) {
                $.each(node.outLinks, function (i, v) {
                    if (self.isChild(v.nodeZ)) {//出线的尾是否在组内
                        v.hide();
                    } else {
                        v.show();
                    }
                });
            }
        }
        node.show();
    };
//计算分组切换所需数据
    function scanChildren(container) {
        var self = container;
        var inLines = [];//记录由外向内的链接
        var outLines = [];//记录由内向外的链接
        var outer = [];//记录有链接的外在对象
        var outNodes = [];//记录外联节点
        var outContainers = [];//记录外联容器
        var alarmLevel = 1000;
        var alarmColor = '';
        var alarm = 0;
        //计算成员的链接
        $.each(self.childs, function (i, node) {
            scanOutLink(node);
            if (self.prop.isExpand) {
                scanAlarm(node);
            }
        });
        //计算自己的链接
        scanOutLink(self);
        //画分组节点的告警
        if (self.prop.isExpand) {
            self.prop.containerNode.drawAlarm({
                color: alarmColor,
                text: alarm
            });
        }
        var temp;
        //计算外部链接
        function scanOutLink(node) {
            if (node.inLinks) {
                $.each(node.inLinks, function (i, line) {
                    temp = line.nodeA;
                    if (!self.isChild(temp)) {
                        if (temp.elementType == 'container' || (temp.elementType == 'node' && temp.prop.nodeType == 'container')) {

                        } else {
                            //检查是否已记录节点
                            var nodeAdd = util.arrayPush(outer, temp);
                            var lineAdd = util.arrayPush(inLines, line);
                            if (nodeAdd && lineAdd) {
                                //都插入成功,即未记录
                                outNodes.push({
                                    outer: temp,
                                    inLines: line.prop.num || 1,
                                    outLines: 0
                                });
                            } else if (lineAdd) {
                                $.each(outNodes, function (i, ob) {
                                    if (ob.outer == temp) {
                                        ob.inLines += line.prop.num || 1;
                                    }
                                });
                            }
                        }
                    }
                });
            }
            if (node.outLinks) {
                $.each(node.outLinks, function (i, line) {
                    temp = line.nodeZ;
                    if (!self.isChild(temp)) {
                        if (temp.elementType == 'container' || (temp.elementType == 'node' && temp.prop.nodeType == 'container')) {

                        } else {
                            //检查是否已记录节点
                            var nodeAdd = util.arrayPush(outer, temp);
                            var lineAdd = util.arrayPush(outLines, line);
                            if (nodeAdd && lineAdd) {
                                //都插入成功,即未记录
                                outNodes.push({
                                    outer: temp,
                                    inLines: 0,
                                    outLines: line.prop.num || 1
                                });
                            } else if (lineAdd) {
                                $.each(outNodes, function (i, ob) {
                                    if (ob.outer == temp) {
                                        ob.outLines += line.prop.num || 1;
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }

        function scanAlarm(node) {
            if (node.prop.alarmLevel && node.prop.alarmLevel < alarmLevel) {
                alarmLevel = node.prop.alarmLevel;
                alarmColor = node.prop.alarmColor;
            }
            if (node.alarm) {
                alarm += node.alarm;
            }
        }

        return {
            nodes: outNodes,
            containers: outContainers
        };
    }

//分组与分组之间不允许链接,会导致难解决的Bug
    Container.prototype.toggleExpand = function () {
        //缩放校验
        var self = this;
        //var outLineNum = scanChildren(self);
        //if (!self.prop.outLink) {
        //    self.prop.outLink = [];
        //    $.each(outLineNum.nodes, function (i, v) {
        //        var newLink = Link.directLink({
        //            start: self.prop.containerNode,
        //            end: v.outer,
        //            num: v.inLines + v.outLines,
        //            useType: 'container'
        //        });
        //        SCENE.addElement(newLink);
        //        self.prop.outLink.push(newLink);
        //    });
        //} else {
        //    var outLinks = self.prop.outLink;
        //    //查找已去除的外联对象并去除相关链接
        //    var temp = [];
        //    $.each(outLinks, function (i, v) {
        //        var deleted = true;
        //        $.each(outLineNum.nodes, function (index, object) {
        //            if (v.nodeA == object.outer || v.nodeZ == object.outer) {
        //                deleted = false;
        //            }
        //        });
        //        if (deleted) {
        //            SCENE.removeElement(v);
        //        } else {
        //            temp.push(v);
        //        }
        //    });
        //    outLinks = temp;
        //    //更新数据并检索新的外联对象
        //    $.each(outLineNum.nodes, function (i, v) {
        //        var totalLine = v.inLines + v.outLines;
        //        var outer = v.outer;
        //        var flag = true;//表示是否是新对象
        //        $.each(outLinks, function (index, link) {
        //            if (link.nodeZ == outer || link.nodeA == outer) {
        //                link.setNum(totalLine);
        //                flag = false;//查找到更新数据并标记为旧对象
        //                return;
        //            }
        //        });
        //        if (flag) {//新的连接对象处理
        //            var newLink = Link.directLink({
        //                start: self.prop.containerNode,
        //                end: v.outer,
        //                num: v.inLines + v.outLines,
        //                useType: 'container'
        //            });
        //            SCENE.addElement(newLink);
        //            self.prop.outLink.push(newLink);
        //        }
        //    });
        //}
        //切换缩放
        if (self.prop.isExpand) {
            self.hide();
            self.prop.containerNode.show();
            self.prop.containerNode.setLocation(self.x + self.width / 2, self.y + self.height / 2);
            //$.each(self.prop.outLink, function (i, value) {
            //    value.show();
            //});
            self.prop.isExpand = false;
        } else {
            self.show();
            self.prop.containerNode.hide();
            self.setLocation(self.prop.containerNode.x - self.width / 2, self.prop.containerNode.y - self.height / 2);
            //$.each(self.prop.outLink, function (i, value) {
            //    value.hide();
            //});
            self.prop.isExpand = true;
        }
    };
    return {
        create: function (object, scene) {
            var op = object || {};
            var name = op.name || '';
            var id = op.id || '';
            var pid = op.pid || '';
            var font = (op.fontSize || '30px') + ' ' + (op.font || '微软雅黑');
            var fontColor = op.fontColor ? util.transHex(op.fontColor) : '255,255,255';
            var fillColor = op.fillColor ? util.transHex(op.fillColor) : '10,10,100';
            var alpha = op.alpha || 0.5;
            var childDragble = typeof(op.childDragble) == 'boolean' ? op.dragable : true;
            var dragable = typeof(op.dragable) == 'boolean' ? op.dragable : true;
            var zIndex = op.zIndex || 10;
            var borderWidth = op.borderWidth || 0;
            var borderRadius = op.borderRadius || 30;//最大160 最小0
            var borderColor = op.borderColor || '255,0,0';
            var image = op.image || "img/mo/wlan_4.png";
            var textPosition = op.textPosition || 'Bottom_Center';//Bottom_Center Top_Center Middle_Left Middle_Right Hidden;
            var eweight = op.eweight || 10000;
            var container = new Container(name);
            var layout = op.layout;
            var showLink = typeof(op.showLink) == 'boolean' ? op.showLink : false;
            var showName = typeof(op.showName) == 'boolean' ? op.showName : true;
            SCENE = scene;
            //jtopo define
            container.fillColor = fillColor;
            container.fontColor = fontColor;
            container.font = font;
            container.borderColor = borderColor;
            container.borderWidth = borderWidth;
            container.borderRadius = borderRadius; // 圆角
            container.dragable = dragable;
            container.childDragble = childDragble;
            container.alpha = alpha;
            container.zIndex = zIndex;
            //new define
            var containerNode = Node.createNode({
                name: name,
                text: name,
                id: id,
                pid: pid,
                image: image,
                nodeType: 'container',
                eweight: eweight
            });//缩放节点
            containerNode.prop.expandTo = container;
            containerNode.hide();
            container.prop = {
                id: id,
                pid: pid,
                name: name,
                eweight: eweight,
                isExpand: true,
                image: image,
                containerNode: containerNode,
                setMember: {
                    showLink: showLink,
                    showName: showName
                }
            };
            container.setLayout(layout);
            container.setTextPosition(textPosition);
            container.exprop = {};
            return container;
        }
    }
})
;