/**
 * Created by Victor on 2016/8/26.
 */
define([], function () {
    var base = {
        defaultPosition: {
            right: 'auto',
            left: 0,
            top: '25%',
            bottom: 'auto'
        },
        mutex: [],
        //使窗体可移动,且互斥
        moveable: function (head, win) {
            //移动
            win.movement = false;
            head.mousedown(function (e) {
                e.stopPropagation();
                win.movePageX = e.pageX - win.offset().left;
                win.movePageY = e.pageY - win.offset().top;
                win.movement = true;
            });
            head.mouseup(function (e) {
                e.stopPropagation();
                win.movement = false;
            });
            $('body').mousemove(function (e) {
                e.stopPropagation();
                if (win.movement) {
                    win.css({
                        left: e.pageX - win.movePageX,
                        top: e.pageY - win.movePageY
                    });
                }
            });
            //互斥
            base.mutex.push(win);
            win.show = function () {
                $(win).show();
                $.each(base.mutex, function (i, v) {
                    if (win != v&& v.css("display")!="none") {
                        v.find(".close").click();
                    }
                });
            }
        },
        //重写窗体部分隐藏
        resetHide: function (attr, group) {
            $.each(attr, function (i, v) {
                v.hide = function () {
                    group[i].hide();
                };
                v.show = function () {
                    group[i].show();
                };
            });
        },
        //绑定数字修改
        resizeNum: function (group, options) {
            var op = options || {};
            var input = group.find('[name*=num]');
            var up = group.find('[name*=up]');
            var down = group.find('[name*=down]');
            var bottom = op.bottom;
            var top = op.top;
            var step = op.step || 2;
            up.on('click', function () {
                var value = parseInt(input.val(), 10) || 0;
                if (typeof top == 'number') {
                    if (value < top) {
                        input.val(value + step);
                    }
                } else {
                    input.val(value + step);
                }
            });
            down.on('click', function () {
                var value = parseInt(input.val(), 10) || 0;
                if (typeof bottom == 'number') {
                    if (value > bottom) {
                        input.val(value - step);
                    }
                } else {
                    input.val(value - step);
                }
            });
        },
        //颜色选择框绑定
        colorSelect: function (input, btn) {
            btn.colorPalette().on('selectColor', function (e) {
                input.val(e.color);
            });
        },
        //按钮切换样式
        toggleClick: function (botton, aClass, bClass) {
            botton.click(function () {
                if (!botton._isClick) {
                    botton.find('span').removeClass(aClass).addClass(bClass);
                    botton._isClick = true;
                } else {
                    botton.find('span').removeClass(bClass).addClass(aClass);
                    botton._isClick = false;
                }
            });
        },//添加图片按钮
        makeImageBtn: function (div, src) {
            var imageBtn = "<label class='btn  btn-primary col-md-2'>" +
                "<input type='radio'><img src='" + src + "'></label>";
            div.append(imageBtn);
        },
        //设定进度条百分比
        setPercent: function (bar, percent) {
            bar.html(percent + '%');
            bar.css({
                width: percent + '%'
            });
        },
        //设定进度条出发点击
        progressClick: function (progress, bar) {
            progress.click(function (e) {
                var v = 10 * e.offsetX / progress.width();
                if (v > 9.6) {
                    v = 10;
                }
                var percent = parseInt(v);
                bar.percent = percent * 10;
                base.setPercent(bar, percent * 10);
            });
        },
        //初始化选择框
        initSelect: function (select, scene) {
            select.item = [];
            select.addItems = function (items) {
                if (items) {
                    if (items instanceof Array) {
                        $.each(items, function (i, v) {
                            select.append("<option value='" + v.value + "'>" + v.name + "</option>");
                            select.item.push(v);
                        });
                    } else {
                        select.append("<option value='" + items.value + "'>" + items.name + "</option>");
                        select.item.push(items);
                    }
                }
            };
            select.change(function () {
                $.each(select.item, function (i, v) {
                    if (v.value == select.val()) {
                        select.mode = v;
                        if (v.onChose && $.isFunction(v.onChose)) {
                            v.onChose(scene);
                        }
                        if (select.baseChose) {
                            select.baseChose(select.val());
                        }
                        return false;
                    }
                });
            });
            select.triggerBtn = function (btn) {
                btn.click(function () {
                    $.each(select.item, function (i, v) {
                        if (v.value == select.val()) {
                            if (v.do) {
                                v.do(scene);
                            }
                        }
                    });
                });
            }
        }
    };
    return base;
});
