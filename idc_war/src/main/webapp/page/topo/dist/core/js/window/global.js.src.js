/**
 * Created by qiyc on 2016/9/12.
 */
define(['text!template/global.html', 'window/base', 'tool/util'], function (windowTemp, base, util) {
    var temp = $(windowTemp);
    var exProp = temp.find("[name=topo_exprop]").hide();
    var autoLayout = temp.find("[name=topo_auto_layout]").hide();
    var alarmConfig=temp.find("[name=alarm_config]").hide();
    $("body").append(temp);
    return {
        initAlarmConfig:function(scene){
            var head = alarmConfig.find(".panel-heading");
            var okBtn = alarmConfig.find("button[name='ok']");
            var cancelBtn = alarmConfig.find("button[name='cancel']");
            var sizeGroup=alarmConfig.find(".size_group");
            var size=alarmConfig.find("[name=size_num]");
            var config=scene.prop.config.alarm?scene.prop.config.alarm:scene.prop.config.alarm={};
            alarmConfig.find(".close").click(function(){
                cancelBtn.click();
            });
            base.moveable(head, alarmConfig);
            base.resizeNum(sizeGroup, {
                bottom: 10,
                step: 2
            });
            alarmConfig.css(base.defaultPosition);
            alarmConfig.on("resetWindow",function(){
                alarmConfig.css(base.defaultPosition);
                alarmConfig.hide();
            });
            var level=[];
            if(config.color&&config.color instanceof Array){
                size.val(config.size||12);
                var form;
                var value;
                $.each(config.color,function(i,v){
                    form=$("<div class='form-group'></div>").append("<label class='col-sm-4 control-label'>告警等级:"+(i+1)+"</label>");
                    alarmConfig.find(".form-horizontal").append(form);
                    form.append("<div class=' col-sm-7'><div class='input-group'></div></div>");
                    value=$("<input name='selected_color' type='text' class='form-control' readonly='true'>");
                    value.val(v);
                    level.push(value);
                    form.find('.input-group').append(value).append("<span class='input-group-btn'><button class='btn btn-primary' type='button' data-toggle='dropdown'><span class='glyphicon glyphicon-search'></span></button><ul class='dropdown-menu color_selected' ><li><div name='color_palette'></div></li></ul></span>");
                    base.colorSelect(value, form.find("[name=color_palette]"));
                });
            }
            okBtn.click(function(){
                var color=[];
                $.each(level,function(i,v){
                    color.push(v.val());
                });
                config.color=color;
                $.each(scene.findElements(function (e) {
                    return e.elementType == 'node' && e.alarm&&e.prop.alarmLevel;
                }),function(i,v){
                    v.drawAlarm({
                        size:size.val()
                    });
                });
            });
            return{
                win:alarmConfig,
                okBtn:okBtn,
                cancelBtn:cancelBtn
            }
        },
        initExProp: function (scene) {
            var head = exProp.find(".panel-heading");
            var form = exProp.find('form');
            var firstRow = exProp.find('.form-group');
            var firstKey = exProp.find('input:eq(0)');
            var firstValue = exProp.find('input:eq(1)');
            var addBtn = exProp.find("button[name='add']");
            var btnGroup = exProp.find('div[name="group"]');
            var okBtn = exProp.find("button[name='ok']");
            var cancelBtn = exProp.find("button[name='cancel']");
            exProp.find(".close").click(function(){
                cancelBtn.click();
            });
            base.moveable(head, exProp);
            exProp.css(base.defaultPosition);
            exProp.item = [];
            exProp.item.push({
                row: firstRow,
                key: firstKey,
                value: firstValue
            });
            var row = " <div class='form-group' >" +
                "<div class='col-sm-5'><input type='text' class='form-control' ></div>" +
                "<div class='col-sm-6'><input type='text' class='form-control'></div>" +
                "<div class='col-sm-1'><button type='button' class='btn btn-primary'name='remove'><span class='glyphicon glyphicon-minus-sign'></span></button></div></div>";
            addBtn.click(function () {
                addItem();
            });
            exProp.on('resetWindow', function () {
                $.each(exProp.item, function (i, v) {
                    if (i > 0) {
                        v.row.remove();
                    }
                });
                firstKey.val('');
                firstValue.val('');
                exProp.item.length = 1;
                exProp.css(base.defaultPosition);
                exProp.hide();
            });
            cancelBtn.click(function () {
                exProp.trigger('resetWindow');
            });
            function addItem() {
                var newrow = $(row);
                var key = newrow.find('input:eq(0)');
                var value = newrow.find('input:eq(1)');
                form.append(newrow);
                newrow.find("button[name='remove']").click(function () {
                    newrow.remove();
                    exProp.item.splice(exProp.item.indexOf(newrow));
                });
                exProp.item.push({
                    row: newrow,
                    key: key,
                    value: value
                });
            }

            //业务绑定
            function edit(element) {
                exProp.target = element;
                head.find("span:first").html('[业务属性配置]   Name: <span name="prop">' + element.prop.name + '</span>  id: <span name="prop">' + element.prop.id + '</span>');
                $.each(element.exprop, function (k, v) {
                    var row = exProp.item[exProp.item.length - 1];
                    row.key.val(k);
                    row.value.val(v);
                    addItem();
                });
                exProp.show();
            }

            okBtn.click(function () {
                $.each(exProp.item, function (i, v) {
                    if (v.key.val()) {
                        exProp.target.exprop[v.key.val().trim()] = v.value.val().trim();
                    }
                });
                exProp.trigger('resetWindow');
            });


            return {
                win: exProp,
                addBtn: addBtn,
                okBtn: okBtn,
                cancelBtn: cancelBtn,
                edit: edit
            }
        },
        initAutoLayout: function (scene) {
            var head = autoLayout.find(".panel-heading");
            var layout = autoLayout.find("[name=layout]");
            var eachRowGroup = autoLayout.find("[name=each_row_group]");
            var eachRow = eachRowGroup.find("[name=each_row_num]");
            var rowSpaceGroup = autoLayout.find("[name=row_space_group]");
            var rowSpace_progress = rowSpaceGroup.find(".progress");
            var rowSpace = rowSpaceGroup.find(".progress-bar");
            var columnSpaceGroup = autoLayout.find("[name=column_space_group]");
            var columnSpace_progress = columnSpaceGroup.find(".progress");
            var columnSpace = columnSpaceGroup.find(".progress-bar");
            var radiusGroup = autoLayout.find("[name=radius_group]");
            var radius_progress = radiusGroup.find(".progress");
            var radius_bar = radiusGroup.find(".progress-bar");
            var orderGroup = autoLayout.find("[name=order_group]");
            var order = orderGroup.find("[name=order]");
            var asc = orderGroup.find("[name=order][value=asc]");
            var des = orderGroup.find("[name=order][value=des]");
            var okBtn = autoLayout.find("[name=ok]");
            var cancelBtn = autoLayout.find("[name=cancel]");
            autoLayout.find(".close").click(function(){
                cancelBtn.click();
            });
            autoLayout.css(base.defaultPosition);
            base.resetHide([eachRow, rowSpace, columnSpace, radius_bar,order], [eachRowGroup, rowSpaceGroup, columnSpaceGroup, radiusGroup,orderGroup]);
            base.moveable(head, autoLayout);
            base.resizeNum(eachRowGroup, {
                bottom: 1,
                step: 1
            });
            base.progressClick(rowSpace_progress, rowSpace);
            base.progressClick(columnSpace_progress, columnSpace);
            base.progressClick(radius_progress, radius_bar);
            setPercent(rowSpace, 100);
            setPercent(columnSpace, 100);
            setPercent(radius_bar, 100);
            order.click(function(){
                order.mode=$(this).val();
            });
            base.initSelect(layout, scene);
            layout.baseChose = selectLayout;
            function setPercent(bar, percent) {
                bar.percent = percent;
                base.setPercent(bar, percent);
            }

            function selectLayout(value) {
                switch (value) {
                    case'default':
                        eachRow.show();
                        rowSpace.show();
                        columnSpace.show();
                        radius_bar.hide();
                        order.hide();
                        layout.val('default');
                        break;
                    case'star':
                        eachRow.hide();
                        rowSpace.hide();
                        columnSpace.hide();
                        radius_bar.show();
                        order.show();
                        asc.click();
                        layout.val('star');
                        break;
                    default:
                        eachRow.hide();
                        rowSpace.hide();
                        columnSpace.hide();
                        radius_bar.hide();
                        order.hide();
                        break;
                }
            }

            selectLayout('default');
            autoLayout.on("resetWindow", function () {
                autoLayout.css(base.defaultPosition);
                selectLayout('default');
                eachRow.val(5);
                setPercent(rowSpace, 100);
                setPercent(columnSpace, 100);
                setPercent(radius_bar, 100);
                autoLayout.hide();
            });
            cancelBtn.click(function () {
                autoLayout.triggerHandler('resetWindow');
            });
            //绑定触发按钮
            layout.triggerBtn(okBtn);
            layout.addItems([{
                value: 'default', name: "自定义", do: function (scene) {
                    layout_default(scene.findElements(function (e) {
                            return e.elementType == 'node' && !e.prop.container;
                        }),
                        eachRow.val(), rowSpace.percent * 1.5, columnSpace.percent * 1.5, {
                            x: 0 - scene.translateX,
                            y: 0 - scene.translateY
                        }
                    );
                }
            }, {
                value: 'star', name: "群星",
                do: function (scene) {
                    layout_star(scene.findElements(function (e) {
                        return e.elementType == 'node' && !e.prop.container;
                    }));
                }
            }, {
                value: 'ring', name: "圆环",
                do: function (scene) {
                    JTopo.layout.circleLayoutNodes(scene.stage.find('node'), {animate: {time: 1000}});
                }
            }]);
            function getDegree(node) {
                //获取节点的度
                return (node.inLinks ? node.inLinks.length : 0) + (node.outLinks ? node.outLinks.length : 0);
            }
            //移动动画
            function move(node, targetX, targetY) {
                targetX = parseInt(targetX);
                targetY = parseInt(targetY);
                var x = parseInt(node.x);
                var y = parseInt(node.y);
                var partX = parseInt((targetX - x)) / 10;
                var partY = parseInt((targetY - y)) / 10;
                node.prop._tempX = targetX;
                node.prop._tempY = targetY;
                var temp = setInterval(function () {
                    if (Math.abs(targetX - x) > 1) {
                        x += partX;
                    }
                    if (Math.abs(targetY - y) > 1) {
                        y += partY;
                    }
                    node.setLocation(parseInt(x), parseInt(y));
                    if (Math.abs(targetX - x) <= 1 && Math.abs(targetY - y) <= 1) {
                        clearInterval(temp);
                        delete node.prop._tempX;
                        delete node.prop._tempY;
                    }
                }, 100);
            }

            //布局函数
            function layout_default(targets, rows, rowSpace, columnSpace, begin) {
                $.each(targets.sort(function (a, b) {
                        return getDegree(b) - getDegree(a);
                }), function (i, v) {
                    //v.setLocation(begin.x + (i % rows) * columnSpace, begin.y);
                    move(v, begin.x + (i % rows) * columnSpace, begin.y);
                    if ((i + 1) % rows == 0) {
                        begin.y += rowSpace;
                    }
                });
            }

            function layout_star(notSetLocation) {
                console.info(order.mode);
                if (notSetLocation.length == 0) {
                    return;
                }
                var total = notSetLocation.slice().sort(function (a, b) {
                    return getDegree(b) - getDegree(a);
                });
                var baseRadius = (getDegree(total[0]) > 2 ? getDegree(total[0]) : total.length) * 25 * (radius_bar.percent / 100);//半径
                var maxRadius = 0;
                var center = util.getCenterPosition(scene);
                var location = {
                    x: center.x,
                    y: center.y
                };
                var column = 0;
                $.each(total, function (i, round_center) {
                    //校验圆心
                    if (getDegree(round_center) < 3) {
                        return true;
                    }
                    //开始画圆
                    if (util.arrayDelete(notSetLocation, round_center)) {
                        var edges = getEdges(round_center);
                        move(round_center, location.x, location.y);
                        recursion(round_center, edges, baseRadius);
                        column++;
                        location.x += maxRadius * 2;
                        if (column % 2 == 0) {
                            location.y += maxRadius * 2;
                            location.x = center.x;
                        }
                    }
                });
                if (notSetLocation.length > 0) {
                    layout_default(notSetLocation, Math.ceil(Math.sqrt(notSetLocation.length)), notSetLocation[0].width*3/2, notSetLocation[0].height*3/2, {
                        x: center.x- maxRadius*Math.cos(Math.PI/4) - Math.ceil(Math.sqrt(notSetLocation.length)) * notSetLocation[0].width*3/2,
                        y: center.y- maxRadius*Math.cos(Math.PI/4) - Math.ceil(Math.sqrt(notSetLocation.length)) * notSetLocation[0].height*3/2
                    });
                }
                function recursion(node, edges) {
                    if (edges.length > 0) {
                        makeLayout(node, edges);
                        $.each(edges, function (i, v) {
                            recursion(v, getEdges(v));
                        });
                    }
                }

                function makeLayout(center, edges) {
                    //画一个圆区
                    var incress=order.mode=='asc';
                    var degree = 1;
                    if(!incress){
                        $.each(edges,function(i,v){
                            if(getDegree(v)>degree){
                                degree=getDegree(v);
                            }
                        });
                    }
                    var radius = 0;
                    var inner = [];
                    var outer = [];
                    var lose = [];
                    var nodes = edges;
                    do {
                        inner = [];
                        outer = [];
                        $.each(nodes, function (i, node) {
                            if(incress){
                                if (getDegree(node) > degree) {
                                    outer.push(node);
                                } else {
                                    inner.push(node);
                                }
                            }else{
                                if (getDegree(node) > degree) {
                                    inner.push(node);
                                } else {
                                    outer.push(node);
                                }
                            }
                        });
                        if (inner.length >= 3) {
                            radius += baseRadius;
                            if (lose.length > 0) {
                                inner = inner.concat(lose);
                                lose = [];
                            }
                            makeRound(center, radius, inner);
                            if (radius > maxRadius) {
                                maxRadius = radius;
                            }
                        } else if (outer.length < 3) {
                            radius += baseRadius * 2 / 3;
                            if (lose.length > 0) {
                                inner = inner.concat(lose);
                                lose = [];
                            }
                            makeRound(center, radius || baseRadius / 2, inner.concat(outer));
                            if (radius > maxRadius) {
                                maxRadius = radius;
                            }
                            break;
                        } else {
                            lose = inner.concat(lose);
                        }
                        if(incress) {
                            degree++;
                        }else{
                            degree--;
                        }
                        nodes = outer;
                    }
                    while (nodes.length > 0)
                }

                function makeRound(center, radius, nodes, num) {
                    //画圆边
                    var random = Math.PI * Math.random();
                    if (num) {
                        random = num;
                    }
                    for (var i = 0; i < nodes.length; i++) {
                        var targetX = parseInt((center.prop._tempX || center.x) + radius * Math.cos(random + Math.PI * 2 * i / nodes.length));
                        var targetY = parseInt((center.prop._tempY || center.y) + radius * Math.sin(random + Math.PI * 2 * i / nodes.length));
                        move(nodes[i], targetX, targetY);
                    }
                }

                function getEdges(node) {
                    //获取与该节点相连的所有未设位置的节点
                    var edges = [];
                    if (node.outLinks) {
                        $.each(node.outLinks, function (i, v) {
                            if (util.arrayDelete(notSetLocation, v.nodeZ)) {
                                edges.push(v.nodeZ);
                            }
                        });
                    }
                    if (node.inLinks) {
                        $.each(node.inLinks, function (i, v) {
                            if (util.arrayDelete(notSetLocation, v.nodeA)) {
                                edges.push(v.nodeA);
                            }
                        });
                    }
                    return edges.sort(function (a, b) {
                        return getDegree(b) - getDegree(a);
                    });
                }
            }

            return {
                win: autoLayout,
                layout: layout,
                okBtn: okBtn,
                cancelBtn: cancelBtn
            }
        }
    }
});