define(['tool/util', 'JTopo'], function (util) {
    $.each(['Link', 'FoldLink', 'FlexionalLink', 'CurveLink'], function (i, v) {
        JTopo[v].prototype.setNum = setNum;
        JTopo[v].prototype.setColor = setColor;
        JTopo[v].prototype.setAlpha = setAlpha;
        JTopo[v].prototype.show = show;
        JTopo[v].prototype.hide = hide;
        JTopo[v].prototype.toggleExpend = toggleExpend;
    });
    function show() {
        this.visible = true;
    }

    function hide() {
        this.visible = false;
    }

    function setNum(num, name) {
        this.prop.num = num;
        if (name) {
            this.prop.name = name;
            this.text = name;
        }
        if (num > 0) {
            this.text = '(+' + num + ')';
        } else {
            this.text = this.prop.name || '';
        }

    }

    function setColor(color) {
        this.strokeColor = util.transHex(color.toLowerCase());
        this.prop.color = color;
    }

    function setAlpha(alpha) {
        if (alpha) {
            this.alpha = parseInt(alpha);
        } else {
            this.alpha = 0.1;
        }
    }

    function toggleExpend(scene) {
        if (this.prop.linkType == 'child') {
            this.prop.father.show();
            $.each(this.prop.father.prop.children, function (i, v) {
                scene.removeElement(v);
            });
            this.prop.father.prop.children = [];
        } else if (this.prop.num > 1) {
            this.prop.children = drawChildLink(this, this.prop.num, scene);
            this.hide();
        }
    }

    function drawChildLink(father, num, scene) {
        var children = [];
        var child;
        for (var i = 0; i < num; i++) {
            child = new JTopo.Link(father.nodeA, father.nodeZ);
            setNormalAttr(child, {}, 'child');
            child.prop.father = father;
            children.push(child);
            scene.addElement(child);
        }
        return children;
    }

    function setNormalAttr(link, op, linkType) {
        var num = op.num || 0;
        var name = op.name || '';
        var id = op.id || '';
        var pid = op.pid || '';
        var eweight = op.eweight || 1000;
        var color = op.color || '22,124,255';
        var arrowSize = op.arrowSize || null;
        var arrowOffset = op.arrowOffset || 0;
        var text = op.text || (num > 0 ? '(+' + num + ')' : '');
        var width = op.width || 3;
        var dashedPattern = op.dashedPattern ? parseInt(op.dashedPattern) : null;
        var fontSize = op.fontSize || 16;
        var font = op.font || '微软雅黑';
        var useType = op.useType || 'normal';
        //jtopo define
        link.text = text || name;
        link.lineWidth = width; // 线宽
        link.arrowsRadius = arrowSize;
        link.arrowsOffset = arrowOffset;
        link.bundleGap = 20; // 线条之间的间隔
        link.textOffsetY = 3; // 文本偏移量（向下3个像素）
        link.strokeColor = util.transHex(color.toLowerCase());
        link.dashedPattern = dashedPattern;//虚线的线段长度
        link.zIndex = 100;//层级
        link.font = fontSize + 'px ' + font;
        //new define
        link.prop = {
            id: id,
            pid: pid,
            num: num,
            name: name,
            color: color,
            eweight: eweight,
            linkType: linkType,
            useType: useType,
            sourceArrow: op.sourceArrow || false,
            targetArrow: op.targetArrow || false

        };
        link.exprop = {};
    }

    return {
        directLink: function (options) {
            var op = options || {};
            var bundleOffset = op.bundleOffset || 60;
            var link = new JTopo.Link(options.start, options.end);
            var linkType = op.linkType || 'direct';
            setNormalAttr(link, op, linkType);
            link.bundleOffset = parseInt(bundleOffset); // 多条直线时，线条折线拐角处的长度
            //双向箭头
            link.getStartPosition = function () {
                var a;
                return null != this.arrowsRadius && (a = (function (thisl) {
                    var b = thisl.nodeA, c = thisl.nodeZ;
                    var d = JTopo.util.lineF(b.cx, b.cy, c.cx, c.cy);
                    var e = b.getBound();
                    return f = JTopo.util.intersectionLineBound(d, e);
                })(this)), null == a && (a = {
                    x: this.nodeA.cx,
                    y: this.nodeA.cy
                }), a;
            };
            link.paintPath = function (a, b) {
                if (this.nodeA === this.nodeZ) return void this.paintLoop(a);
                a.beginPath(),
                    a.moveTo(b[0].x, b[0].y);
                for (var c = 1; c < b.length; c++) {
                    null == this.dashedPattern ? (
                        (null == this.PointPathColor ? a.lineTo(b[c].x, b[c].y) : a.JtopoDrawPointPath(b[c - 1].x, b[c - 1].y, b[c].x, b[c].y, a.strokeStyle, this.PointPathColor))
                    ) : a.JTopoDashedLineTo(b[c - 1].x, b[c - 1].y, b[c].x, b[c].y, this.dashedPattern)
                }
                ;
                if (a.stroke(), a.closePath(), null != this.arrowsRadius) {
                    if (this.prop.targetArrow) {
                        this.paintArrow(a, b[0], b[b.length - 1]);
                    }//终点箭头
                    if (this.prop.sourceArrow) {
                        this.paintArrow(a, b[b.length - 1], b[0]);
                    }//起点箭头
                }
            };
            return link;
        },
        foldLink: function (options) {
            var op = options || {};
            var link = new JTopo.FoldLink(options.start, options.end);
            var linkType = op.linkType || 'fold';
            setNormalAttr(link, options, linkType);
            //jtopo define
            link.direction = op.direction || 'horizontal';//折线方向 horizontal 水平 "vertical"垂直
            //修改源码
            link.paintPath = function (a, b) {
                if (this.nodeA === this.nodeZ)return void this.paintLoop(a);
                a.beginPath(), a.moveTo(b[0].x, b[0].y);
                for (var c = 1; c < b.length; c++)
                    null == this.dashedPattern ? a.lineTo(b[c].x, b[c].y) : a.JTopoDashedLineTo(b[c - 1].x, b[c - 1].y, b[c].x, b[c].y, this.dashedPattern);
                if (a.stroke(), a.closePath(), null != this.arrowsRadius) {
                    if (this.prop.targetArrow) {
                        this.paintArrow(a, b[b.length - 2], b[b.length - 1]);
                    }
                    if (this.prop.sourceArrow) {
                        this.paintArrow(a, b[1], b[0]);//添加双向箭头
                    }
                }
            };
            return link;
        },
        flexionalLink: function (options) {
            var op = options || {};
            var offsetGap = op.offsetGap || 60;
            var link = new JTopo.FlexionalLink(options.start, options.end);
            var linkType = op.linkType || 'flexional';
            setNormalAttr(link, op, linkType);
            link.direction = op.direction || 'horizontal';
            link.offsetGap = parseInt(offsetGap);// 折线拐角处的长度
            //修改源码
            link.paintPath = function (a, b) {
                if (this.nodeA === this.nodeZ)return void this.paintLoop(a);
                a.beginPath(), a.moveTo(b[0].x, b[0].y);
                for (var c = 1; c < b.length; c++)
                    null == this.dashedPattern ? a.lineTo(b[c].x, b[c].y) : a.JTopoDashedLineTo(b[c - 1].x, b[c - 1].y, b[c].x, b[c].y, this.dashedPattern);
                if (a.stroke(), a.closePath(), null != this.arrowsRadius) {
                    if (this.prop.targetArrow) {
                        this.paintArrow(a, b[b.length - 2], b[b.length - 1]);
                    }
                    if (this.prop.sourceArrow) {
                        this.paintArrow(a, b[1], b[0]);//添加双向箭头
                    }
                }
            };
            return link;
        },
        CurveLink: function (options) {
            var op = options || {};
            var link = new JTopo.CurveLink(options.start, options.end);
            var linkType = op.linkType || 'curve';
            setNormalAttr(link, op, linkType);
            //自定义曲线偏移量
            link.prop.cureOffset = options.cureOffset || 200;
            //改写源码绘制曲线
            link.getStartPosition = function () {
                var a;
                return (a = (function (thisl) {
                    var b = thisl.nodeA, c = thisl.nodeZ;
                    var d = JTopo.util.lineF(b.cx, b.cy, c.cx, c.cy);
                    var e = b.getBound();
                    return f = JTopo.util.intersectionLineBound(d, e);
                })(this)), a;
            };
            link.getEndPosition = function () {
                var a;
                return (a = (function (thisl) {
                    var b = thisl.nodeZ, c = thisl.nodeA;
                    var d = JTopo.util.lineF(b.cx, b.cy, c.cx, c.cy);
                    var e = b.getBound();
                    return f = JTopo.util.intersectionLineBound(d, e);
                })(this)), a;
            };
            link.paintPath = function (cx, path) {
                if (this.nodeA === this.nodeZ)return void this.paintLoop(cx);
                var s = path[0];
                var t = path[path.length - 1];
                cx.beginPath();
                cx.moveTo(s.x, s.y);
                var angle = Math.atan(Math.abs(t.y - s.y) / Math.abs(t.x - s.x));
                var mX = (s.x + t.x) / 2 + this.prop.cureOffset * Math.cos(angle - Math.PI / 2);
                var mY = (s.y + t.y) / 2 + this.prop.cureOffset * Math.sin(angle - Math.PI / 2);
                cx.strokeStyle = "rgba(" + this.strokeColor + "," + this.alpha + ")";
                cx.lineWidth = this.lineWidth;
                cx.moveTo(s.x, s.cy);
                cx.quadraticCurveTo(mX, mY, t.x, t.y);
                cx.stroke();
                if (cx.stroke(), cx.closePath(), null != this.arrowsRadius) {
                    if (this.prop.targetArrow) {
                        this.paintArrow(cx, s, t);
                    }
                    if (this.prop.sourceArrow) {
                        this.paintArrow(cx, t, s);
                    }
                }
            };
            return link;
        }

    };
});

