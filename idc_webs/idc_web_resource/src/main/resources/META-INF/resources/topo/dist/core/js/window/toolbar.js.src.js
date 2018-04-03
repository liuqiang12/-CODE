/**
 * Created by qiyc on 2016/9/9.
 */
define([ 'text!template/toolbar.html', 'window/toolWindow', 'tool/util', 'window/base', 'element/Link'], function ( template, toolWindow, util, base, Link) {
    var temp = $(template);
    return {
        init: function (scene) {
            var fatherBar = temp.find('div[name=topo_toolbar]');
            var edit_childBar = temp.find('div[name=topo_toolbar_child]').hide();
            $("body").append(temp);
            //主工具栏
            var returnBtn = fatherBar.find('button[name=return]');
            var modeBtns = fatherBar.find("label[name=topo_mode]");
            var fullScreen = fatherBar.find('button[name=full_screen]');
            var centerBtn = fatherBar.find('button[name=center]');
            var commonBtn = fatherBar.find('button[name=common]');
            var zoomCheck = fatherBar.find('button[name=zoom_checkbox]');
            var zoomIn = fatherBar.find('button[name=zoom_in]');
            var zoomOut = fatherBar.find('button[name=zoom_out]');
            var exportImg = fatherBar.find('button[name=export_image]');
            var printBtn = fatherBar.find('button[name=print]');
            var eagleEye = fatherBar.find('button[name=eagle_eye]');
            var searchMedo = fatherBar.find('select[name=search_mode]');
            var searchInput = fatherBar.find('input[name=search_text]');
            var searchBtn = fatherBar.find('button[name=search]');
            var searchResult = fatherBar.find('select[name=search_result]').hide();
            //编辑子栏
            var edit_autoLayout = edit_childBar.find('button[name=auto_layout]');
            var edit_createImage = edit_childBar.find('button[name=create_image]');
            var edit_createText = edit_childBar.find('button[name=create_text]');
            var edit_createLink = edit_childBar.find('button[name=create_link]');
            var edit_createLine = edit_childBar.find('button[name=create_line]');
            var edit_save = edit_childBar.find('button[name=edit_save]');
            var edit_alarm_config = edit_childBar.find('button[name=draw_alarm]');
            //常量
            var cancel = "glyphicon-remove-sign";
            var editBtn = fatherBar.find("label[name='topo_mode'][title='编辑']");
            //开启全部tips插件
            fatherBar.find("[data-toggle*='tooltip']").tooltip();
            edit_childBar.find("[data-toggle*='tooltip']").tooltip();
            //子菜单定位
            $(window).resize(function () {
                var left = editBtn.offset().left + 17 - edit_childBar.width() / 2;
                var top = fatherBar.offset().top + fatherBar.height() + 2;
                edit_childBar.css({'left': left, 'top': top});
            });
            modeBtns.click(function () {
                modeBtns.mode = $(this).find("input").val();
                if (modeBtns.mode == "edit") {
                    var left = editBtn.offset().left + 17 - edit_childBar.width() / 2;
                    var top = fatherBar.offset().top + fatherBar.height() + 2;
                    edit_childBar.css({'left': left, 'top': top});
                    edit_childBar.show();
                } else {
                    edit_childBar.hide();
                }
            });
            base.toggleClick(fullScreen,'glyphicon-fullscreen',cancel);
            //鼠标缩放
            base.toggleClick(zoomCheck, 'glyphicon-resize-small', cancel);
            //鹰眼
            base.toggleClick(eagleEye, "glyphicon-eye-open", "glyphicon-eye-close");
            //输入框
            searchInput.keydown(function (event) {
                if (event.keyCode == 13) {
                    searchBtn.click();
                }
            });
            //自动布局
            base.toggleClick(edit_autoLayout, "glyphicon-th", cancel);
            //创建图片对象
            base.toggleClick(edit_createImage, "glyphicon-picture", cancel);
            //创建文本对象
            base.toggleClick(edit_createText, "glyphicon-font", cancel);
            //画线
            base.toggleClick(edit_createLine, "glyphicon-pencil", cancel);
            //创建链路模式切换
            base.toggleClick(edit_createLink, "glyphicon-sort", cancel);
            //告警渲染
            base.toggleClick(edit_alarm_config, "glyphicon-warning-sign", cancel);
            searchMedo.item = [];
            //搜索模式切换
            searchMedo.change(function () {
                var self = this;
                $.each(searchMedo.item, function (i, v) {
                    if (v.value == self.value) {
                        searchMedo.mode = v;
                        return false;
                    }
                });
            });
            searchResult.change(function () {
                var self = this;
                if (self.value == "default") {
                    return;
                }
                $.each(searchResult.item, function (i, v) {
                    if (v.value == self.value) {
                        v.event();
                        searchResult.hide();
                        return false;
                    }
                });

            });
            function makeOption(select, item) {
                var option = "<option value='" + item.value + "'>" + item.name + "</option>";
                select.append(option);
                select.item.push(item);
            }

            function addSearchResult(items) {
                $.each(items, function (i, item) {
                    makeOption(searchResult, item);
                });
            }

            function resetSearchResult() {
                searchResult.item = [];
                searchResult.html('');
                makeOption(searchResult, {
                    name: "请选择结果",
                    value: "default"
                });
            }

            return {
                hide: function () {
                    fatherBar.hide();
                    edit_childBar.hide();
                },
                show: function () {
                    fatherBar.show();
                    if (modeBtns.mode == 'edit') {
                        edit_childBar.show();
                    }
                },
                setMode: function (mode) {
                    modeBtns.find("input[value=" + mode + "]").click();
                },
                addSearchMode: function (items) {
                    if (items instanceof Array) {
                        $.each(items, function (i, v) {
                            makeOption(searchMedo, v);
                        });
                    } else {
                        makeOption(searchMedo, items);
                    }
                },
                doSearch: function () {
                    var text = searchInput.val().trim();
                    if (text == '') {
                        toolWindow.alert({
                            content: '<h4>输入不能为空！</h4>',
                            width: 300
                        });
                        return;
                    }
                    resetSearchResult();
                    var feedBack = {};
                    var results = [];
                    var mode = searchMedo.mode || searchMedo.item[0];
                    if (mode.search) {
                        feedBack = mode.search(text);
                    }
                    if (feedBack.results && feedBack.results.length > 0) {
                        $.each(feedBack.results, function (i, v) {
                            results.push({
                                name: v.prop.name + ' | ' + (v.prop.content || '') + "(该图层)",
                                value: results.length,
                                event: function () {
                                    feedBack.event(v);
                                }
                            });
                        });
                        addSearchResult(results);
                        searchResult.show();
                    } else if (mode.deepSearch) {
                        feedBack = mode.deepSearch(text);
                        $.ajax({
                            url: feedBack.url,
                            type: "POST",
                            success: function (data) {
                                $.each(feedBack.filter(data), function (i, v) {
                                    results.push({
                                        name: v.prop.name + ' | ' + (v.prop.content || ''),
                                        value: results.length,
                                        event: function () {
                                            feedBack.event(v);
                                        }
                                    });
                                });
                                if (results.length > 0) {
                                    addSearchResult(results);
                                    searchResult.show();
                                }else{
                                    toolWindow.alert({
                                        content: '<h3> 无查询结果！</h3>',
                                        width: 300
                                    });
                                }
                            },
                            error: function (e) {
                                toolWindow.alert({
                                    content: '<h3>查询失败!若未登陆请登录后再试！</h3>',
                                    width: 300
                                });
                            }
                        });
                    } else {
                        toolWindow.alert({
                            content: '<h3> 无查询结果！</h3>',
                            width: 300
                        });
                    }
                },
                fatherBar: fatherBar,
                edit_childBar: edit_childBar,
                returnBtn: returnBtn,
                modeBtns: modeBtns,
                fullScreen: fullScreen,
                centerBtn: centerBtn,
                commonBtn: commonBtn,
                zoomIn: zoomIn,
                zoomOut: zoomOut,
                zoomCheck: zoomCheck,
                exportImg: exportImg,
                printBtn: printBtn,
                eagleEye: eagleEye,
                searchInput: searchInput,
                searchBtn: searchBtn,
                edit_autoLayout:edit_autoLayout,
                edit_createImage: edit_createImage,
                edit_createText: edit_createText,
                edit_createLink: edit_createLink,
                edit_createLine: edit_createLine,
                edit_save: edit_save,
                edit_alarm_config: edit_alarm_config
            }

        }
    }
});
