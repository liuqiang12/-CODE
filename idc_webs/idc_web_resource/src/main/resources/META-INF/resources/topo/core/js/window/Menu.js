/**
 * Created by Victor on 2016/8/29.
 */
define([], function () {
    function Menu(name) {
        this.body = $("<ul "+(name?'name='+name:'') +" class='dropdown-menu' role='menu' >");
        this.item = [];
    }

    Menu.prototype = {
        setTrigger: function (scene, options) {
            var self = this;
            var op = options || {};
            var filter = op.filter;
            var listener = op.listener || scene;
            var buttonKey = typeof op.buttonKey != 'undefined' ? op.buttonKey : 2;
            var action = op.action || 'mouseup';
            //若已经绑触发条件，则覆盖原有
            if (this.trigger) {
                $(this.trigger.listener).off(this.trigger.action, this.trigger.fn);//删除原条件
                this.trigger.listener = listener;
                this.trigger.buttonKey = buttonKey;
                this.trigger.action = action;
                this.trigger.filter = filter;
                $(this.trigger.listener).on(this.trigger.action, this.trigger.fn);//重新绑定触发条件
                return;
            }
            //若未绑定触发条件,则添加
            this.trigger = {
                listener: listener,
                action: action,
                buttonKey: buttonKey,//左键0中键1右键是2,
                filter: filter,
                fn: function (e) {
                    if (filter) {
                        if (filter(e)) {
                            menuShow(e);
                        }
                    } else {
                        if (e.button == self.trigger.buttonKey) {
                            menuShow(e);
                        } else if (self.trigger.buttonKey == -1) {
                            menuShow(e);
                        }
                    }
                }
            };
            $('body').append(this.body);//加入到界面中
            $(this.trigger.listener).on(this.trigger.action, this.trigger.fn);//绑定触发条件
            this.body.on('mouseleave mouseup click', function (e) {
                e.stopPropagation();
                self.body.hide();
            });//注册隐藏条件
            function menuShow(e) {
                self.target = scene.prop.mousemove.target;
                self.x= scene.prop.mousemove.x;
                self.y=scene.prop.mousemove.y;
                toggleItem(self.item, self.target);
                self.body.css({
                    left:e.pageX-50 ,
                    top: e.pageY-20
                }).show();
            }

            //切换菜单栏显示
            function toggleItem(array, target) {
                $.each(array, function (i, v) {
                    if (v.filter && v.type == 'item') {
                        if (v.filter(target)) {
                            v.body.show();
                            v.divider ? v.divider.show() : '';
                        } else {
                            v.body.hide();
                            v.divider ? v.divider.hide() : '';
                        }
                    } else if (v.type == 'subMenu') {
                        if (v.filter) {
                            if (v.filter(target)) {
                                v.includer.show();
                                v.divider ? v.divider.show() : '';
                            } else {
                                v.includer.hide();
                                v.divider ? v.divider.hide() : '';
                            }
                        }
                        toggleItem(v.body.item, target);//递归子项
                    }
                });
            }
        },
        addItem: function (options) {
            var op = options || {};
            var name = op.name || 'NoName';
            var icon=op.icon||'';
            var color=op.color||'bluelight';
            var divider;
            if (op.divide) {
                divider = $("<li class='divider'>");
                this.body.append(divider);
            }
            //var item = $("<li><a>" + name + "</a></li>");
            var item=$("<li><div class='buttonSquare "+color+"'><i class='"+icon+"'></i><span>"+name+"</span></div></li>");
            item.click(op.handler);
            this.body.append(item);
            this.item.push({
                body: item,
                type: 'item',
                name: name,
                divider: divider,
                filter: op.filter
            });
        },
        addSubMenu: function (options) {
            var op = options || {};
            var name = op.name || 'NoName';
            var icon=op.icon||'fa fa-navicon';
            var color=op.color||'bluelight';
            var subMenu = new Menu();
            var includer = $("<li class='dropdown-submenu'><div class='buttonSquare "+color+"'><i class='"+icon+"'></i><span>" + name + "</span></div></li>");
            var divider;
            if (op.divide) {
                divider = $("<li class='divider'>");
                this.body.append(divider);
            }
            this.body.append(includer);
            includer.append(subMenu.body);

            this.item.push({
                body: subMenu,
                type: 'subMenu',
                name: name,
                divider: divider,
                includer: includer,
                filter: op.filter
            });
            return subMenu;
        },
        removeItem: function () {
        }
    };
    return Menu;
});
