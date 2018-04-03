/**
 * Created by Victor on 2016/7/29.
 */
define(['tool/util', 'JTopo'], function (util) {
    $.each(['Node', 'TextNode'], function (i, v) {
        JTopo[v].prototype.setAlpha = setAlpha;
        JTopo[v].prototype.show = show;
        JTopo[v].prototype.hide = hide;
    });
    function show() {
        this.visible = true;
    }

    function hide() {
        this.visible = false;
    }

    function setAlpha(alpha) {
        if (alpha) {
            this.alpha = parseInt(alpha);
            this.alarmAlpha = parseInt(alpha);
        } else {
            this.alpha = 0.1;
            this.alarmAlpha = 0.1;
        }
    }

    JTopo.Node.prototype.setNodeImage = function (image) {
        this.setImage(image);
        this.prop.image = image;
    };
    JTopo.Node.prototype.setTextPosition = function (textPosition) {
        this.textPosition = textPosition;
        this.prop.textPosition = textPosition;
        this.text = this.prop.name;
        switch (textPosition) {
            case 'Hidden':
                this.text = '';
                break;
            case 'Bottom_Center':
                this.textOffsetX = 0;
                this.textOffsetY = 0;
                break;
            case 'Top_Center':
                this.textOffsetX = 0;
                this.textOffsetY = 0;
                break;
            case 'Middle_Left':
                this.textOffsetX = -5;
                this.textOffsetY = 0;

                break;
            case 'Middle_Right':
                this.textOffsetX = 5;
                this.textOffsetY = 0;
                break;
        }
    };
    JTopo.TextNode.prototype.setFontColor = function (color) {
        this.fontColor = util.transHex(color.toLowerCase());
        this.prop.fontColor = color;
    };
    JTopo.TextNode.prototype.setFont = function (size, font) {
        if (font) {
            this.font = size + 'px ' + font;
            this.prop.font = font;
        } else {
            this.font = size + 'px ' + this.prop.font;
        }
        this.prop.fontSize = size;
    };
    JTopo.Node.prototype.drawAlarm = function (ob) {
        if(this.prop.scene){
            var config = this.prop.scene.prop.config;
            var color = ob.color ? ob.color.toLowerCase(): (this.prop.alarmLevel?config.alarm.color[this.prop.alarmLevel-1]:'0,0,255');
            this.alarm = ob.text ||(this.alarm||'No Alarm Text!');
            this.alarmColor = util.transHex(color);
            this.alarmAlpha = 1;
            this.alarmFont = (ob.size || 20) + 'px ' + (ob.font || '微软雅黑');
            this.prop.alarmLevel = ob.level||(this.prop.alarmLevel||config.alarm.color.length);
            this.prop.alarmColor = color;
            shadowFlash(this, this.alarmColor);
        }
    };
    function shadowFlash(node, color) {
        node.shadow = true;
        node.shadowOffsetX = 0;
        node.shadowOffsetY = 0;
        node.shadowColor = "rgba(" + color + ",1)";
        node.shadowBlur = 10;
        //node.prop.animate={
        //    flash:JTopo.Animate.stepByStep(node, {shadowBlur: 100}, 3000, true)
        //};
        //node.prop.animate.flash.start();
        flash(node, true);
        function flash(node, flag) {
            if (node.prop.flash) {
                clearInterval(node.prop.flash);
            }
            node.prop.flash = setInterval(function () {
                if (flag) {
                    node.shadowBlur += 10;
                    if (node.shadowBlur >= 100) {
                        flag = false;
                    }
                }
                else {
                    node.shadowBlur -= 10;
                    if (node.shadowBlur <= 10) {
                        flag = true;
                    }
                }
            }, 100);
        }
    }

    var nodeEweight = {
        Segment: 100,
        'ztype.shape': 100000,
        'ztype.text': 1000000,
        'ztype.url': 10000000,
        'ztype.topo': 100000000,
        'ztype.layer': 1000000000,
        'ztype.view': 100000000000,
        'ztype.obj': 10000000000000,
        'ztype.grid': 100000000000000
    };
    return {
        createNode: function (options) {
            var object = options || {};
            var newNode = new JTopo.Node();
            var name = object.name || "node";
            var image = object.image || "img/mo/wlan_4.png";
            var width = object.width ? object.width : 64;
            var height = object.height ? object.height : 64;
            var alpha = object.alpha || 1;
            var x = (object.x || 0);
            var y = (object.y || 0);
            var fontSize = object.fontSize || 16;
            var font = object.font || '微软雅黑';
            var zIndex = object.zIndex || 200;//层级(10-999)
            var id = object.id || "";
            var pid = object.pid || '';
            var color = object.color || (image || JTopo.util.randomColor());
            var textPosition = object.textPosition || 'Bottom_Center';//Bottom_Center Top_Center Middle_Left Middle_Right Hidden
            var nodeType = object.nodeType || 'node';
            var text = object.text || '';
            var eweight = object.eweight || (nodeEweight[nodeType] || 10);
            //jtopo define
            newNode.font = fontSize + 'px ' + font;
            newNode.text = text;
            newNode.setLocation(x, y);
            newNode.setImage(image);
            newNode.zIndex = zIndex;
            newNode.alpha = alpha;
            newNode.setSize(width, height);
            newNode.fillColor = util.transHex(color.toLowerCase());
            //new define
            newNode.prop = {
                id: id,
                pid: pid,
                name: name,
                image: image,
                nodeType: nodeType,
                eweight: eweight
            };
            newNode.setTextPosition(textPosition);
            newNode.exprop = {};
            return newNode;
        },
        createTextNode: function (options) {
            var object = options || {};
            var textNode = new JTopo.TextNode();
            var x = object.x || 0;
            var y = object.y || 0;
            var fontSize = object.fontSize || 20;
            var font = object.font || '微软雅黑';
            var zIndex = object.zIndex || 200;//层级(10-999)
            var alpha = object.alpha || 1;
            var id = object.id || "";
            var pid = object.pid || '';
            var name = object.name || "";
            var text = object.text || 'no text here';
            var nodeType = object.nodeType || 'text';
            var eweight = object.eweight || (nodeEweight[nodeType] || 1000000);
            var fontColor = object.fontColor || '255,255,255';
            //jtopo define
            textNode.text = text;
            textNode.setLocation(x, y);
            textNode.zIndex = zIndex;//层级(10-999)
            textNode.alpha = alpha;
            textNode.font = fontSize + 'px ' + font;
            textNode.fontColor = util.transHex(fontColor.toLowerCase());
            //new define
            textNode.prop = {
                id: id,
                pid: pid,
                name: name,
                font: font,
                fontSize: fontSize,
                fontColor: fontColor,
                nodeType: nodeType,
                eweight: eweight
            };
            textNode.exprop = {};
            return textNode;
        },
        createShape: function (options) {
            var object = options || {};
            var objectNode = new JTopo.Node();
            var width = object.width > 0 ? object.width : 100;
            var height = object.height > 0 ? object.height : 50;
            var x = (object.x || 0);
            var y = (object.y || 0);
            var fontSize = object.fontSize || 20;
            var font = object.font || '微软雅黑';
            var id = object.id || "";
            var pid = object.pid || '';
            var name = object.name || "";
            var text = object.text || '';
            var zIndex = object.zIndex || 150;//层级(10-999)
            var alpha = object.alpha || 0.8;
            var color = 'rgba(' + (object.color ? object.color : "55,180,74") + "," + alpha + ")";
            var nodeType = object.nodeType || 'object';
            var eweight = object.eweight || (nodeEweight[nodeType] || 100000);
            var textPosition = object.textPosition || 'Bottom_Center';//Bottom_Center Top_Center Middle_Left Middle_Right Hidden
            //jtopo define
            objectNode.text = text;
            objectNode.setLocation(x, y);
            objectNode.setSize(width, height);
            objectNode.zIndex = zIndex;
            objectNode.font = fontSize + 'px ' + font;
            //特征设置
            objectNode.showSelected = true;//不显示选中矩形
            objectNode.dragable = true;//不能被拖动
            objectNode.editAble = true;//不能被修改
            objectNode.paint = function (g) {
                var a = this.width / 2;
                var b = this.height / 2;
                var ox = 0.5 * a, oy = 0.6 * b;
                g.save();
                g.beginPath();
                g.moveTo(0, b);
                g.bezierCurveTo(ox, b, a, oy, a, 0);
                g.bezierCurveTo(a, -oy, ox, -b, 0, -b);
                g.bezierCurveTo(-ox, -b, -a, -oy, -a, 0);
                g.bezierCurveTo(-a, oy, -ox, b, 0, b);
                g.closePath();
                g.fillStyle = color;
                g.fill();
                g.restore();
                this.paintText(g);
            };
            //new define
            objectNode.prop = {
                id: id,
                pid: pid,
                name: name,
                nodeType: nodeType,
                eweight: eweight
            };
            objectNode.setTextPosition(textPosition);
            objectNode.exprop = {};
            return objectNode;
        }

    };
});
