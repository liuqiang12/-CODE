/**
 * Created by qiyc on 2016/9/13.
 */
define([ 'text!template/containerAttribute.html', 'window/base', 'tool/util', 'element/Container'], function (windowTemp, base, util, Container) {
    var temp = $(windowTemp);
    var containerWin = temp.find("[name=topo_container_attribute]").hide();
    $('body').append(temp);
    return {
        init: function (scene) {
            var imageSelectWindow = temp.find("[name=image_select]");
            var imageAccordion=imageSelectWindow.find("[name=topo_container_selected_image]");
            containerWin.css(base.defaultPosition);//初始化位置
            //主页面
            var head = containerWin.find("[name=head]");
            var nameGroup = containerWin.find("[name=name_group]");
            var name = nameGroup.find("[name=name]");
            var positionGroup = containerWin.find("[name=position_group]");
            var position = positionGroup.find("[name=position]");
            var imageGroup = containerWin.find("[name=image_group]");
            var image = imageGroup.find("[name=image]");
            var imageBtn = imageGroup.find("[name=find_image]");
            var fillColorGroup = containerWin.find("[name=fill_color_group]");
            var fillColor = fillColorGroup.find("[name=selected_color]");
            var fill_color_palette = fillColorGroup.find("[name=color_palette]");
            var alphaGroup = containerWin.find("[name=alpha_group]");
            var alpha = alphaGroup.find("[name=alpha]");
            var alpha_progress = alphaGroup.find(".progress");
            var borderWidthGroup = containerWin.find("[name=border_width_group]");
            var borderWidth = borderWidthGroup.find("[name=border_width_num]");
            var borderColorGroup = containerWin.find("[name=border_color_group]");
            var borderColor = borderColorGroup.find("[name=selected_color]");
            var border_color_palette = borderColorGroup.find("[name=color_palette]");
            var borderRadiusGroup = containerWin.find("[name=border_radius_group]");
            var borderRadius = borderRadiusGroup.find("[name=border_radius]");
            var memberSetGroup=containerWin.find('[name=set_member_group]');
            var showName=memberSetGroup.find('[name=show_name]');
            var showLink=memberSetGroup.find('[name=show_link]');
            var layoutGroup = containerWin.find("[name=layout_group]");
            var layout = layoutGroup.find("[name=layout]");
            var auto = layoutGroup.find("[name=layout][value=auto]");
            var set = layoutGroup.find("[name=layout][value=set]");
            var widthGroup = containerWin.find("[name=width_group]");
            var width = widthGroup.find("[name=width]");
            var heightGroup = containerWin.find("[name=height_group]");
            var height = heightGroup.find("[name=height]");
            var selectLayoutGroup = containerWin.find("[name=select_layout_group]");
            var selectLayout = selectLayoutGroup.find("[name=select_layout]");
            var layoutRowGroup = containerWin.find("[name=row_space_group]");
            var layoutRow = layoutRowGroup.find("[name=row_space_num]");
            var layoutColumnGroup = containerWin.find("[name=column_space_group]");
            var layoutColumn = layoutColumnGroup.find("[name=column_space_num]");
            var okBtn = containerWin.find("[name=ok]");
            var cancelBtn = containerWin.find("[name=cancel]");
            //子页面
            var submit = imageSelectWindow.find('[name=ok]');
            var imageShow = imageSelectWindow.find('[name=image_show]');
            var group = [nameGroup, positionGroup, imageGroup, fillColorGroup, alphaGroup, borderWidthGroup, borderColorGroup, borderRadiusGroup, widthGroup, heightGroup, selectLayoutGroup, layoutRowGroup, layoutColumnGroup];
            var attr = [name, position, image, fillColor, alpha, borderWidth, borderColor, borderRadius, width, height, selectLayout, layoutRow, layoutColumn];
            head.find('.close').click(function(){
                cancelBtn.click();
            });
            base.resetHide(attr, group);
            base.moveable(head, containerWin);
            base.colorSelect(fillColor, fill_color_palette);
            base.colorSelect(borderColor, border_color_palette);
            //数值框设定
            base.resizeNum(borderWidthGroup, {
                bottom: 0
            });
            base.resizeNum(layoutRowGroup, {
                bottom: 1,
                step: 1
            });
            base.resizeNum(layoutColumnGroup,{
                bottom: 1,
                step: 1
            });
            //透明度选择
            base.progressClick(alpha_progress, alpha);
            setPercent(alpha, 100);
            //图形选择
            var first_time = true;
            imageBtn.click(function (e) {
                if (first_time) {
                    var imageSrc=scene.prop.config.image||{};
                    $.each(imageSrc,function(panelName,images){
                        var panel=$("<div class='panel panel-default'><div class='panel-heading' role='tab'><h4 class='panel-title'>"+
                            "<a data-toggle='collapse' data-parent='[name=topo_container_selected_image]' href='[name=topo_container_selected_image_"+panelName+"]'  aria-controls='collapseOne'>"+panelName+
                            "</a></h4></div><div name='topo_container_selected_image_"+panelName+"' class='panel-collapse collapse' role='tabpanel' ><div class='panel-body'>"+
                            "<div class='container'><div class='row'><div name='image_show' class='btn-group col-md-6' data-toggle='buttons'></div></div></div></div></div></div>");
                        imageAccordion.append(panel);
                        $.each(images,function(k,v){
                            base.makeImageBtn(panel.find('[name=image_show]'), v);
                        });
                    });
                    first_time = false;
                }
                imageAccordion.find('.panel:first').find('a').click();
                imageSelectWindow.modal('toggle');
            });
            //模拟框确定按钮
            submit.click(function () {
                image.attr('src', imageAccordion.find(".in").find('.active img').attr('src'));
                imageSelectWindow.modal('toggle');
            });
            //模拟框隐藏后重置
            imageSelectWindow.on('hidden.bs.modal', function (e) {
                imageAccordion.find('[name=image_show]').find('.active').removeClass('active');//重置选择
                imageAccordion.find('.in').collapse('hide');
            });
            //布局选择
            layout.click(function () {
                var target = containerWin.target;
                var autoWidth = 0;
                var autoHeight = 0;
                if (target && target instanceof Array) {
                    var rows = Math.ceil(Math.sqrt(target.length));
                    autoWidth = (target[0].width + 20) * rows;
                    autoHeight = (target[0].height + 20) * rows;
                    width.val(autoWidth);
                    height.val(autoHeight);
                }
                containerWin.layout = $(this).val();
                if (containerWin.layout == 'auto') {
                    width.show();
                    height.show();
                    selectLayout.show();
                    layoutRow.show();
                    layoutColumn.show();
                } else if (containerWin.layout == 'set') {
                    width.hide();
                    height.hide();
                    selectLayout.hide();
                    layoutRow.hide();
                    layoutColumn.hide();
                }
            });
            set.click();
            //布局样式
            selectLayout.change(function () {
                changeLayout($(this).val());
            });
            //窗口重置事件
            var defaultImage = image.attr('src');
            containerWin.on('resetWindow', function () {
                name.val('');
                position.val('Bottom_Center');
                image.attr('src', defaultImage);
                setPercent(alpha, 100);
                showName[0].checked= true;
                showLink[0].checked= true;
                fillColor.val('');
                borderColor.val('');
                set.click();
                width.val('');
                height.val('');
                selectLayout.val('flow');
                layoutRow.val(1);
                layoutColumn.val(1);
                containerWin.hide();
                containerWin.css(base.defaultPosition);
            });
            cancelBtn.click(function () {
                containerWin.trigger('resetWindow');
            });
            function changeLayout(value) {
                var total = 0;
                var target = containerWin.target;
                if (containerWin.mode == 'create' && target && target instanceof Array) {
                    total = target.length;
                } else if (containerWin.mode == 'edit') {
                    total = target.childs.length;
                }
                var rows = Math.ceil(Math.sqrt(total));
                switch (value) {
                    case'flow':
                        selectLayout.val(value);
                        layoutRowGroup.find('label').html('行间距');
                        layoutColumnGroup.find('label').html('列间距');
                        layoutRow.val(0);
                        layoutColumn.val(0);
                        break;
                    case'grid':
                        selectLayout.val(value);
                        layoutRowGroup.find('label').html('行数');
                        layoutColumnGroup.find('label').html('列数');
                        layoutRow.val(rows || 4);
                        layoutColumn.val(rows || 4);
                        break;
                }
            }

            function setPercent(bar, percent) {
                bar.percent = percent;
                base.setPercent(bar, parseInt(percent));
            }

            function edit(element) {
                head.find('span:first').text("编辑分组");
                containerWin.show();
                containerWin.target = element;
                containerWin.mode = 'edit';
                name.val(element.prop.name);
                position.val(element.prop.textPosition);
                image.attr('src', element.prop.image);
                fillColor.val(element.fillColor);
                setPercent(alpha, element.alpha*100);
                borderWidth.val(element.borderWidth);
                borderColor.val(element.borderColor);
                borderRadius.val(element.borderRadius);
                showName[0].checked= element.prop.setMember.showName;
                showLink[0].checked=element.prop.setMember.showLink;
                if (element.prop.layoutType == 'set') {
                    set.click();
                } else {
                    auto.click();
                    changeLayout(element.prop.layoutType);
                    layoutRow.val(element.prop.layoutRow);
                    layoutColumn.val(element.prop.layoutColumn);
                }
                width.val(element.width);
                height.val(element.height);
            }

            function create(nodes) {
                head.find('span:first').text("创建分组");
                containerWin.show();
                containerWin.mode = 'create';
                containerWin.target = nodes;
            }

            okBtn.click(function () {
                var target = containerWin.target;
                var newName = name.val();
                var newPosition = position.val();
                var newImage = image.attr('src');
                var newFillColor = util.transHex(fillColor.val());
                var newAlpha = alpha.percent/100;
                var newBorderWidth = parseInt(borderWidth.val());
                var newBorderColor = util.transHex(borderColor.val());
                var newBorderRadius = parseInt(borderRadius.val() || 0);
                var show_Link=showLink[0].checked;
                var show_Name=showName[0].checked;
                if (containerWin.mode == 'edit') {
                    target.setName(newName);
                    target.setTextPosition(newPosition);
                    target.setImage(newImage);
                    target.fillColor = newFillColor;
                    target.alpha = newAlpha;
                    target.borderWidth = newBorderWidth;
                    target.borderColor = newBorderColor;
                    target.borderRadius = newBorderRadius;
                    target.prop.setMember.showLink=show_Link;
                    target.prop.setMember.showName=show_Name;
                    if (containerWin.layout == 'auto') {
                        $.each(target.childs, function (i, v) {
                            if (v.prop.nodeType != 'node') {
                                target.removeNode(v);
                            }
                        });
                        target.setLayout({
                            type: selectLayout.val(),
                            row: parseInt(layoutRow.val()),
                            column: parseInt(layoutColumn.val())
                        });
                        target.width = parseInt(width.val());
                        target.height = parseInt(height.val());
                    } else if (containerWin.layout == 'set') {
                        target.setLayout();
                    }
                    target.show();
                } else if (containerWin.mode == 'create') {
                    var newContainer = Container.create({
                        name: newName,
                        textPosition: newPosition,
                        image: newImage,
                        fillColor: newFillColor,
                        alpha: newAlpha,
                        borderWidth: newBorderWidth,
                        borderColor: newBorderColor,
                        borderRadius: newBorderRadius,
                        showName:show_Name,
                        showLink:show_Link
                    },scene);
                    var center=util.getCenterPosition(scene);
                    if (containerWin.layout == 'auto') {
                        newContainer.setLayout({
                            type: selectLayout.val(),
                            row: parseInt(layoutRow.val()),
                            column: parseInt(layoutColumn.val())
                        });
                        newContainer.width = parseInt(width.val());
                        newContainer.height = parseInt(height.val());
                        newContainer.setLocation(center.x-newContainer.width/2,center.y-newContainer.height/2);
                    }else{
                        newContainer.setLocation(center.x,center.y);//不明bug，自适应的分组必须要制定大于0的位置后才会出现!
                    }
                    if (target instanceof Array) {
                        $.each(target, function (i, v) {
                            newContainer.addNode(v);
                        });
                    } else {
                        newContainer.addNode(target);
                    }
                    scene.addElement(newContainer);
                }
                containerWin.trigger('resetWindow');
            });
            return {
                win: containerWin,
                name: name,
                position: position,
                image: image,
                fillColor: fillColor,
                alpha: alpha,
                borderWidth: borderWidth,
                borderColor: borderColor,
                borderRadius: borderRadius,
                auto: auto,
                set: set,
                width: width,
                height: height,
                layoutType: selectLayout,
                layoutRow: layoutRow,
                layoutColumn: layoutColumn,
                okBtn: okBtn,
                cancelBtn: cancelBtn,
                edit: edit,
                create: create
            }
        }
    }
});