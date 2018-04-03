/**
 * Created by Victor on 2016/8/30.
 */
define([ 'text!template/toolWindow.html'], function ( windowTemp) {
    var temp = $(windowTemp);
    var confirmWin = temp.find('div[name=topo_confirm]');
    var alertWin=temp.find('div[name=topo_alert]');
    var tipsWin = temp.find('div[name=topo_tips]').hide();
    var progress = temp.find('div[name=topo_progress]');
    $("body").append(temp);
    //进度条
    var progressBar=progress.find('.progress-bar');
    //载入时触发
    progress.modal({
        keyboard: false,
        backdrop: 'static',
        show: true
    });
    //confirm子项
    var confirm_content = confirmWin.find('.modal-body');
    var confirm_title = confirmWin.find('[name=title]');
    var confirm_okBtn = confirmWin.find('[name=ok]');
    var confirm_cancelBtn = confirmWin.find('[name=cancel]');
    var confirm_makeSize = confirmWin.find('.modal-content');//窗体大小和位置只有修改content才有效
    confirm_okBtn.on('click', function () {
        confirmWin.modal('hide');
    });
    confirm_cancelBtn.on('click', function () {
        confirmWin.modal('hide');
    });
    //alert框
    var alert_content = alertWin.find('.modal-body');
    var alert_title = alertWin.find('[name=title]');
    var alert_okBtn = alertWin.find('[name=ok]');
    var alert_makeSize = alertWin.find('.modal-content');//窗体大小和位置只有修改content才有效
    alert_okBtn.on('click',function(){
        alertWin.modal('hide');
    });
    //tips子项
    var tips_head = tipsWin.find('.panel-heading');
    var tips_body = tipsWin.find('.panel-body');
    var tips_foot = tipsWin.find('.panel-footer');
    return {
        confirm: function (options) {
            var op = options || {};
            var title = op.title || '操作框';
            var content = op.content || '';
            var width = op.width || '500';
            var left = (500 - width) / 2;
            confirm_title.html(title);
            confirm_content.html(content);
            confirmWin.modal({
                keyboard: false,
                backdrop: 'static',
                show: true
            });
            if (op.handler && $.isFunction(op.handler)) {
                confirm_okBtn.on('click', {flag: true}, onclick);
                confirm_cancelBtn.on('click', {flag: false}, onclick);
            }
            //确定窗体大小和展示位置
            confirm_makeSize.css({
                width: width,
                left: $(window).width() > 500 ? left : 0
            });
            function onclick(e) {
                op.handler(e.data.flag);
                confirm_okBtn.off("click", onclick);
                confirm_cancelBtn.off("click", onclick);
            }
        },
        alert: function (options) {
            var op = options || {};
            var title = op.title || '提示框';
            var content = op.content || '';
            var width = op.width || '500';
            var left = (500 - width) / 2;
            alert_title.html(title);
            alert_content.html(content);
            alertWin.modal({
                show: true
            });
            //确定窗体大小和展示位置
            alert_makeSize.css({
                width: width,
                left: $(window).width() > 500 ? left : 0
            });
        },
        tips: function (options) {
            var op = options || {};
            var title = op.title;
            var foot = op.foot;
            var x = op.x;
            var y = op.y;
            var width = op.width || 200;
            var content = op.content || '';
            if (title) {
                tips_head.show();
                tips_head.html(title);
            } else {
                tips_head.hide();
            }
            if (foot) {
                tips_foot.show();
                tips_foot.html(foot);
            } else {
                tips_foot.hide();
            }
            if (content) {
                tips_body.show();
                tips_body.html(content);
                tipsWin.css({
                    left: x,
                    top: y,
                    width: width
                }).show();
            } else {
                tips_body.hide();
                tipsWin.hide();
            }
        },
        progress: function (options) {
            if (options) {
                var now = options.now;
                var text = options.text;
                var error = options.error;
                var time = 1000;
                if (now >= 0 && now <= 100) {
                    if (error) {
                        progressBar.removeClass('progress-bar-info').addClass('progress-bar-danger');
                        time = 1000;
                    } else {
                        progressBar.removeClass('progress-bar-danger').addClass('progress-bar-info');
                    }
                    progressBar.css({width: now + '%'});
                    progressBar.html(text);
                    progress.modal({
                        keyboard: false,
                        backdrop: 'static',
                        show: true
                    });
                    if (now == 100) {
                        setTimeout(function () {
                            progress.modal('hide');
                        }, time);
                    }
                } else {
                    progress.modal('hide');
                }
            } else {
                progress.modal('hide');
            }
        }
    };
});