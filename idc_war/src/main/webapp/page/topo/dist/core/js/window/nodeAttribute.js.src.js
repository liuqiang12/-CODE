/**
 * normald by Victor on 2016/8/16.
 */
define([ 'text!template/nodeAttribute.html', 'window/base', 'element/Node', 'tool/util'], function ( windowTemp, base, Node, util) {
    var template = $(windowTemp);
    var imageNodeWin = template.find('[name=topo_image_node]').hide();
    var textNodeWin = template.find('[name=topo_text_node]').hide();
    $("body").append(template);
    return {
        initImageNode: function (scene) {
            //初始化窗口
            var selectWin = template.find('[name=image_select]');//颜色选择弹出框
            var imageAccordion=selectWin.find('[name=topo_node_selected_image]');
            imageNodeWin.css(base.defaultPosition);//初始化位置
            var head = imageNodeWin.find('[name=head]');
            var cancelBtn = imageNodeWin.find('[name=cancel]');
            var okBtn = imageNodeWin.find('[name=ok]');
            var imageBtn = imageNodeWin.find('[name=find_image]');
            var id = imageNodeWin.find('[name=id]');
            var idCheck = imageNodeWin.find('[name=id_check]');
            var idGroup = imageNodeWin.find('[name=id_group]');
            var name = imageNodeWin.find('[name=name]');
            var nameGroup = imageNodeWin.find('[name=name_group]');
            var image = imageNodeWin.find('[name=image]');
            var imageGroup = imageNodeWin.find('[name=image_group]');
            var position = imageNodeWin.find("[name=position]");
            var positionGroup = imageNodeWin.find('[name=position_group]');
            var group = [idGroup, nameGroup, imageGroup, positionGroup];
            var attr = [id, name, image, position];
            base.resetHide(attr, group);
            base.moveable(head, imageNodeWin);
            imageNodeWin.find('.close').click(function(){
                cancelBtn.click();
            });
            //锁定ID输入框
            idCheck.click(function (e) {
                id.attr('disabled', !this.checked);
            });
            id.attr('disabled', true);
            //改变图片
            var first_time = true;
            imageBtn.click(function (e) {
                if (first_time) {
                    var imageSrc=scene.prop.config.image||{};
                    $.each(imageSrc,function(panelName,images){
                        var panel=$("<div class='panel panel-default'><div class='panel-heading' role='tab'><h4 class='panel-title'>"+
                            "<a data-toggle='collapse' data-parent='[name=topo_node_selected_image]' href='[name=topo_node_selected_image_"+panelName+"]'  aria-controls='collapseOne'>"+panelName+
                            "</a></h4></div><div name='topo_node_selected_image_"+panelName+"' class='panel-collapse collapse' role='tabpanel' ><div class='panel-body'>"+
                            "<div class='container'><div class='row'><div name='image_show' class='btn-group col-md-6' data-toggle='buttons'></div></div></div></div></div></div>");
                        imageAccordion.append(panel);
                        $.each(images,function(k,v){
                            base.makeImageBtn(panel.find('[name=image_show]'), v);
                        });
                    });
                    first_time = false;
                }
                imageAccordion.find('.panel:first').find('a').click();
                selectWin.modal('toggle');
            });
            //模拟框隐藏后重置
            selectWin.on('hidden.bs.modal', function (e) {
                imageAccordion.find('[name=image_show]').find('.active').removeClass('active');//重置选择
                imageAccordion.find('.in').collapse('hide');
            });
            //窗口清理事件
            var defaultImage = image.attr('src');
            imageNodeWin.on('resetWindow', function (fn) {
                if(fn&& $.isFunction(fn)){
                    fn();
                }
                id.val('');
                id.attr('disabled', true);
                idCheck.attr('checked', false);
                name.val('');
                position.val('Bottom_Center');
                image.attr('src', defaultImage);
                imageNodeWin.hide();
                imageNodeWin.css(base.defaultPosition);
            });
            //模拟框确定按钮
            selectWin.find('[name=ok]').click(function () {
                image.attr('src', selectWin.find(".in").find('.active img').attr('src'));
                selectWin.modal('toggle');
            });
            //填充窗口
            function edit(node) {
                if (node.elementType == 'node') {
                    head.find("span:first").text('编辑节点');
                    imageNodeWin.target = node;
                    imageNodeWin.mode = 'edit';
                    name.val(node.prop.name);
                    id.val(node.prop.id);
                    position.val(node.prop.textPosition);
                    image.attr('src', node.prop.image);
                    imageNodeWin.show();
                }
            }

            okBtn.click(function () {
                var newName = name.val().trim();
                var newId = id.val();
                var newImage = image.attr('src');
                var newPosition = position.val();
                if (imageNodeWin.mode == 'create') {
                    scene.addElement(Node.createNode({
                        name: newName,
                        id: newId,
                        pid: scene.prop.pid || scene.prop.id,
                        image: newImage,
                        textPosition: newPosition,
                        x: util.getCenterPosition(scene).x,
                        y: util.getCenterPosition(scene).y
                    }));
                }
                if (imageNodeWin.mode == 'edit') {
                    var node = imageNodeWin.target;
                    node.prop.name = newName;
                    node.prop.id = newId;
                    node.setNodeImage(newImage);
                    node.setTextPosition(newPosition);
                    imageNodeWin.trigger('resetWindow');
                }
            });
            return {
                win: imageNodeWin,
                head:head,
                okBtn: okBtn,
                cancelBtn: cancelBtn,
                id: id,
                idCheck: idCheck,
                name: name,
                image: image,
                position: position,
                edit: edit
            };
        },
        initTextNode: function (scene) {
            textNodeWin.css(base.defaultPosition);
            var head = textNodeWin.find('[name=head]');
            var cancelBtn = textNodeWin.find('[name=cancel]');
            var okBtn = textNodeWin.find('[name=ok]');
            var size = textNodeWin.find('[name=size_num]');
            var color = textNodeWin.find('[name=color]');
            var text = textNodeWin.find('[name=text]');
            var textGroup = textNodeWin.find('[name-text_group]');
            var sizeGroup = textNodeWin.find('[name=size_group]');
            var colorGroup = textNodeWin.find('[name=color_group]');
            var attr = [size, color, text];
            var group = [sizeGroup, colorGroup, textGroup];
            textNodeWin.find('.close').click(function(){
                cancelBtn.click();
            });
            base.resetHide(attr, group);
            base.moveable(head, textNodeWin);
            //初始化颜色选择器
            base.colorSelect(color, textNodeWin.find('[name=color_palette]'));
            //数字修改框
            base.resizeNum(sizeGroup,{
                bottom: 2
            });
            //窗口清理事件
            textNodeWin.on('resetWindow', function (fn) {
                if(fn&& $.isFunction(fn)){
                    fn();
                }
                text.val('');
                color.val('');
                size.val(12);
                textNodeWin.hide();
                textNodeWin.css(base.defaultPosition);
            });
            //业务事件
            function edit(node) {
                if (node.elementType == 'TextNode') {
                    head.find("span:first").text('编辑文本');
                    textNodeWin.target = node;
                    textNodeWin.mode = 'edit';
                    text.val(textNodeWin.target.text);
                    color.val(textNodeWin.target.prop.fontColor);
                    size.val(parseInt(textNodeWin.target.prop.fontSize));
                    textNodeWin.show();
                }
            }

            okBtn.click(function () {
                if (textNodeWin.mode == 'create') {
                    scene.addElement(Node.createTextNode({
                        text: text.val().trim(),
                        fontSize: size.val(),
                        fontColor: color.val(),
                        x: util.getCenterPosition(scene).x,
                        y: util.getCenterPosition(scene).y
                    }));
                }
                if (textNodeWin.mode == 'edit') {
                    var node = textNodeWin.target;
                    node.text = text.val();
                    node.setFontColor(color.val());
                    node.setFont(size.val());
                    textNodeWin.trigger('resetWindow');
                }
            });
            return {
                win: textNodeWin,
                head:head,
                okBtn: okBtn,
                cancelBtn: cancelBtn,
                size: size,
                color: color,
                text: text,
                edit: edit
            }
        }
    }
});
