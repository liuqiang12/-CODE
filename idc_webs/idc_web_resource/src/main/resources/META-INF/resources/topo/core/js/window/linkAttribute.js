/**
 * Created by qiyc on 2016/9/6.
 */
define(['text!template/linkAttribute.html', 'window/base', 'element/Link'], function (windowTemp, base, Link) {
    var template = $(windowTemp);
    var linkWin = template.find('[name=topo_link_attribute]').hide();
    var lineWin = template.find('[name=topo_line_attribute]').hide();
    $("body").append(template);
    return {
        initLink: function (scene) {
            linkWin.css(base.defaultPosition);//初始化位置
            var head = linkWin.find('[name=head]');
            var okBtn = linkWin.find('[name=ok]');
            var cancelBtn = linkWin.find('[name=cancel]');
            var name = linkWin.find('[name=name]');
            var nameGroup = linkWin.find('[name=name_group]');
            var number=linkWin.find('[name=number_num]');
            var numberGroup=linkWin.find('[name=number_group]');
            var style = linkWin.find('[name=style]');
            var styleGroup = linkWin.find('[name=style_group]');
            var color = linkWin.find('[name=selected_color]');
            var colorGroup = linkWin.find('[name=color_group]');
            var width = linkWin.find('[name=width_num]');
            var widthGroup = linkWin.find('[name=width_group]');
            var gapSize = linkWin.find('[name=gap_num]');
            var gapSizeGroup = linkWin.find('[name=gap_group]');
            var direction = linkWin.find('[name=direction]');
            var directionGroup = linkWin.find('[name=direction_group]');
            var cureOffsetGroup = linkWin.find('[name=cureOffset_group]');
            var cureOffset = linkWin.find('[name=cureOffset_num]');
            var targetArrow = linkWin.find('[name=arrow_check_target]');
            var sourceArrow = linkWin.find('[name=arrow_check_source]');
            var dashedCheck = linkWin.find('[name=dashed_check]');
            var checkGroup = linkWin.find('[name=check_group]');
            var arrowSize = linkWin.find('[name=arrow_size_num]');
            var arrowSizeGroup = linkWin.find('[name=arrow_size_group]');
            var arrowOffset = linkWin.find('[name=arrow_offset_num]');
            var arrowOffsetGroup = linkWin.find('[name=arrow_offset_group]');
            var dashedSize = linkWin.find('[name=dashed_size_num]');
            var dashedSizeGroup = linkWin.find('[name=dashed_size_group]');
            var group = [nameGroup, numberGroup,styleGroup, colorGroup, widthGroup, arrowSizeGroup, arrowOffsetGroup, dashedSizeGroup, gapSizeGroup, directionGroup, cureOffsetGroup];
            var attr = [name, number,style, color, width, arrowSize, arrowOffset, dashedSize, gapSize, direction, cureOffset];
            base.resetHide(attr, group);
            base.colorSelect(color, linkWin.find('[name=color_palette]'));
            base.resizeNum(widthGroup, {
                bottom: 1
            });
            base.resizeNum(arrowSizeGroup, {
                step: 5,
                bottom: 0
            });
            base.resizeNum(arrowOffsetGroup, {
                step: 5
            });
            base.resizeNum(dashedSizeGroup, {
                step: 5,
                bottom: 1
            });
            base.resizeNum(gapSizeGroup, {
                step: 5,
                bottom: 0
            });
            base.resizeNum(cureOffsetGroup, {
                step: 50
            });
            base.resizeNum(numberGroup, {
                step: 1,
                bottom:0
            });
            base.moveable(head, linkWin);
            linkWin.find('.close').click(function(){
                cancelBtn.click();
            });
            direction.hide();
            arrowSize.hide();
            arrowOffset.hide();
            dashedSize.hide();
            targetArrow.click(function (e) {
                if (!sourceArrow[0].checked) {
                    arrowSize.toggle(this.checked);
                    arrowOffset.toggle(this.checked);
                }
            });
            sourceArrow.click(function (e) {
                if (!targetArrow[0].checked) {
                    arrowSize.toggle(this.checked);
                    arrowOffset.toggle(this.checked);
                }
            });
            dashedCheck.click(function (e) {
                dashedSize.toggle(this.checked);
            });
            style.change(function () {
                switch (this.value) {
                    case'direct':
                        direction.hide();
                        gapSize.show();
                        cureOffset.hide();
                        break;
                    case'fold':
                        direction.show();
                        gapSize.hide();
                        cureOffset.hide();
                        break;
                    case'flexional':
                        direction.show();
                        gapSize.show();
                        cureOffset.hide();
                        break;
                    case'curve':
                        direction.hide();
                        gapSize.hide();
                        cureOffset.show();
                        break;
                }
            });
            linkWin.on('resetWindow', function (fn) {
                if (fn && $.isFunction(fn)) {
                    fn();
                }
                name.val('');
                name.attr('disabled', false);
                number.val(0);
                color.val('');
                style.val('direct');
                style.attr('disabled', false);
                gapSize.val(60);
                direction.val("horizontal");
                targetArrow.attr('checked', false);
                sourceArrow.attr('checked', false);
                dashedCheck.attr('checked', false);
                arrowSize.val(10).hide();
                arrowOffset.val(0).hide();
                dashedSize.val(6).hide();
                cureOffset.val(200).hide();
                linkWin.hide();
                linkWin.css(base.defaultPosition);
            });
            function edit(link) {
                linkWin.target = link;
                linkWin.mode = 'edit';
                name.val(link.prop.name|| '');
                number.val(link.prop.num||0);
                style.val(link.prop.linkType);
                style.trigger('change');
                color.val(link.strokeColor);
                width.val(link.lineWidth);
                if (link.prop.useType != 'normal'&&link.prop.useType != 'line') {
                    style.attr('disabled', true);
                    name.attr('disabled', true);
                }
                switch (link.prop.linkType) {
                    case'direct':
                        gapSize.val(link.bundleOffset || 0);
                        break;
                    case'fold':
                        direction.val(link.direction);
                        break;
                    case'flexional':
                        direction.val(link.direction);
                        gapSize.val(link.offsetGap);
                        break;
                    case'curve':
                        cureOffset.val(link.prop.cureOffset);
                        break;
                }
                if (link.arrowsRadius && link.arrowsRadius > 0) {
                    if (link.prop.sourceArrow) {
                        sourceArrow.click();
                    }
                    if (link.prop.targetArrow) {
                        targetArrow.click();
                    }
                    arrowSize.val(link.arrowsRadius);
                    arrowOffset.val(link.arrowsOffset);
                }
                if (link.dashedPattern) {
                    dashedCheck.click();
                    dashedSize.val(link.dashedPattern);
                }
                head.find("span:first").html('编辑链路');
                linkWin.show();
            }

            okBtn.click(function () {
                if (linkWin.mode == 'edit') {
                    var link = linkWin.target;
                    var newLink = link;
                    var options = {
                        name: name.val().trim(),
                        num: number.val(),
                        color: color.val(),
                        width: parseInt(width.val()),
                        arrowSize: parseInt(arrowSize.val()),
                        arrowOffset: parseInt(arrowOffset.val()),
                        dashedPattern: dashedCheck[0].checked ? parseInt(dashedSize.val()) : null,
                        sourceArrow: sourceArrow[0].checked,
                        targetArrow: targetArrow[0].checked,
                        cureOffset: cureOffset.val()
                    };
                    if (link.prop.linkType == style.val()) {
                        link.setNum(options.num, options.name);
                        link.setColor(options.color);
                        link.lineWidth = options.width;
                        link.prop.sourceArrow = options.sourceArrow;
                        link.prop.targetArrow = options.targetArrow;
                        switch (style.val()) {
                            case'direct':
                                link.bundleOffset = parseInt(gapSize.val());
                                break;
                            case'fold':
                                link.direction = direction.val();
                                break;
                            case'flexional':
                                link.direction = direction.val();
                                link.offsetGap = parseInt(gapSize.val());
                                break;
                            case'curve':
                                link.prop.cureOffset = cureOffset.val();
                                break;
                        }
                        if (sourceArrow[0].checked || targetArrow[0].checked) {
                            link.arrowsRadius = options.arrowSize;
                            link.arrowsOffset = options.arrowOffset;
                        } else {
                            link.arrowsRadius = null;
                        }
                        if (dashedCheck[0].checked) {
                            link.dashedPattern = options.dashedPattern;
                        } else {
                            link.dashedPattern = null;
                        }
                    } else {
                        options.start = link.nodeA;
                        options.end = link.nodeZ;
                        switch (style.val()) {
                            case'direct':
                                options.bundleOffset = parseInt(gapSize.val());
                                newLink = Link.directLink(options);
                                break;
                            case'fold':
                                options.direction = direction.val();
                                newLink = Link.foldLink(options);
                                break;
                            case'flexional':
                                options.direction = direction.val();
                                options.offsetGap = parseInt(gapSize.val());
                                newLink = Link.flexionalLink(options);
                                break;
                            case'curve':
                                newLink = Link.CurveLink(options);
                                break;
                        }
                        newLink.prop.id = link.prop.id;
                        newLink.prop.pid = link.prop.pid;
                        newLink.prop.eweight = link.prop.eweight;
                        newLink.exprop = link.exprop;
                        scene.removeElement(link);
                        scene.addElement(newLink);
                    }
                    linkWin.trigger('resetWindow');
                }
            });
            cancelBtn.click(function () {
                linkWin.trigger('resetWindow');
            });
            return {
                win: linkWin,
                okBtn: okBtn,
                cancelBtn: cancelBtn,
                name: name,
                style: style,
                color: color,
                width: width,
                gapSize: gapSize,
                direction: direction,
                arrowCheck_target: targetArrow,
                arrowCheck_source: sourceArrow,
                arrowSize: arrowSize,
                arrowOffset: arrowOffset,
                dashedCheck: dashedCheck,
                dashedSize: dashedSize,
                edit: edit
            }
        },
        initLine: function (scene) {
            lineWin.css(base.defaultPosition);//初始化位置
            var head = lineWin.find("[name=head]");
            var okBtn = lineWin.find(".ok");
            var cancelBtn = lineWin.find(".close");
            var cancel_before = lineWin.find(".cancel_before");
            var cancel_all = lineWin.find(".cancel_all");
            var name = lineWin.find('.name');
            var nameGroup = lineWin.find('.name_group');
            var color = lineWin.find('.selected_color');
            var colorGroup = lineWin.find('.color_group');
            var width = lineWin.find('[name=width_num]');
            var widthGroup = lineWin.find('.width_group');
            var group = [nameGroup, colorGroup, widthGroup];
            var attr = [name, color, width];
            base.resetHide(attr, group);
            base.moveable(head, lineWin);
            base.colorSelect(color, lineWin.find('.color_palette'));
            base.resizeNum(widthGroup, {
                bottom: 1
            });
            var mouseNode;
            var invisibleNode;
            var lineNodes = [];
            var newLine = true;
            var beginDraw = false;
            lineWin.on('drawLine', function (fn) {
                beginDraw = true;
                if (fn && $.isFunction(fn)) {
                    fn();
                }
                lineWin.show();
            });
            //画线
            scene.click(function (e) {
                if (beginDraw && e.button == 0) {
                    if (newLine) {
                        lineNodes = [];
                        mouseNode = new JTopo.Node().setLocation(e.x, e.y);
                        mouseNode.setSize(1, 1);
                        invisibleNode = new JTopo.Node().setLocation(e.x, e.y);
                        invisibleNode.setSize(1, 1);
                        scene.addElement(Link.directLink({
                            start: invisibleNode,
                            end: mouseNode,
                            name: name.val(),
                            color: color.val(),
                            width: width.val(),
                            useType: 'line'
                        }));
                        lineNodes.push(invisibleNode);
                        newLine = false;
                    } else {
                        invisibleNode = mouseNode;
                        mouseNode = new JTopo.Node().setLocation(e.x, e.y);
                        mouseNode.setSize(1, 1);
                        invisibleNode.setLocation(e.x, e.y);
                        scene.addElement(Link.directLink({
                            start: invisibleNode,
                            end: mouseNode,
                            name: name.val(),
                            color: color.val(),
                            width: width.val(),
                            useType: 'line'
                        }));
                        lineNodes.push(invisibleNode);
                    }
                }
            });
            //双击设终点
            scene.dbclick(function (e) {
                endDrawLine();
                if(lineNodes.length>0){
                    lineNodes.splice(lineNodes.length - 1, 1);//因为dbclick会触发2次click事件,需要清理一个重复的
                    if(lineNodes.length==1) {
                        lineNodes.splice(lineNodes.length - 1, 1);//若只有一个节点 那么肯定是无线的 需要清空路径
                    }
                }
            });
            scene.mousemove(function (e) {
                if (!beginDraw || e.button != 0) {
                    return;
                }
                if (mouseNode) {
                    mouseNode.setLocation(e.x, e.y);
                }
            });
            lineWin.on('resetWindow', function (fn) {
                if (fn && $.isFunction(fn)) {
                    fn();
                }
                endDrawLine();
                beginDraw = false;
                name.val('');
                color.val('');
                width.val(3);
                lineWin.hide();
                lineWin.css(base.defaultPosition);
            });
            okBtn.click(function () {
                endDrawLine();
            });
            function endDrawLine() {
                scene.removeElement(mouseNode);
                mouseNode = '';
                newLine = true;
            }

            cancel_before.click(function () {
                if (lineNodes.length > 0) {
                    scene.removeElement(lineNodes[lineNodes.length - 1]);
                    lineNodes.splice(lineNodes.length - 1, 1);
                    if (lineNodes.length > 0) {
                        if(mouseNode){
                            scene.addElement(Link.directLink({
                                start: lineNodes[lineNodes.length - 1],
                                end: mouseNode,
                                name: name.val(),
                                color: color.val(),
                                width: width.val(),
                                useType: 'line'
                            }));
                        }
                    } else {
                        endDrawLine();
                    }
                }
            });
            cancel_all.click(function(){
                endDrawLine();
                if (lineNodes.length > 0) {
                    $.each(lineNodes,function(i,v){
                        scene.removeElement(v);
                    });
                    lineNodes=[];
                }
            });
            cancelBtn.click(function () {
                lineWin.trigger('resetWindow');
            });
            return {
                win: lineWin,
                name: name,
                color: color,
                width: width,
                head: head,
                okBtn: okBtn,
                cancelBtn: cancelBtn

            }
        }
    }
});