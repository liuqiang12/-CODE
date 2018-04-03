var parentTopHeight_left;
var parentBottomHeight_left;
var parentTopHeight;
var parentTopHeight_notitle;
var broswerFlag;
var c;
$(function () {
    if (window.navigator.userAgent.indexOf("MSIE") >= 1) {
        var g = window.navigator.userAgent.substring(30, 33);
        if (g == "6.0") {
            broswerFlag = "IE6"
        } else {
            if (g == "7.0") {
                broswerFlag = "IE7"
            } else {
                if (g == "8.0") {
                    broswerFlag = "IE8"
                }
            }
        }
    } else {
        if (window.navigator.userAgent.indexOf("Firefox") >= 1) {
            broswerFlag = "Firefox"
        } else {
            if (window.navigator.userAgent.indexOf("Opera") >= 0) {
                broswerFlag = "Opera"
            } else {
                if (window.navigator.userAgent.indexOf("Safari") >= 1) {
                    broswerFlag = "Safari"
                } else {
                    broswerFlag = "Other"
                }
            }
        }
    }
    //table的高亮效果
    $(".tableStyle tr.tr").bind("click",function(){
        $(this).addClass("highlight");
        //同时移除其他tr的高亮效果
        $(this).siblings("tr.tr").removeClass("highlight");
    });
    
  //主内容上方高度
    parentTopHeight = $(window.parent.document.getElementById("mainHbox")).outerHeight() +
        $(window.parent.document.getElementById("rbox_topcenter")).outerHeight() +
        parseInt($(window.parent.document.getElementById("rbox")).css("paddingTop")) +
        parseInt($(window.parent.document.getElementById("rbox")).css("paddingBottom"));

    parentTopHeight_notitle = $(window.parent.document.getElementById("mainHbox")).outerHeight() +
	        parseInt($(window.parent.document.getElementById("rbox")).css("paddingTop")) +
	        parseInt($(window.parent.document.getElementById("rbox")).css("paddingBottom"));

    //菜单上方高度
    parentTopHeight_left = $(window.parent.document.getElementById("mainHbox")).outerHeight() +
        parseInt($(window.parent.document.getElementById("lbox")).css("paddingTop")) +
        parseInt($(window.parent.document.getElementById("lbox")).css("paddingBottom"));
    
    
    if ($("#scrollContent").length > 0) {
        $("#scrollContent").css({
            overflow: "hidden"
        });
        if (parentTopHeight > 0) {
            //曲靖
            $("#scrollContent").css({
                overflowY: "auto"
            })
        }
        //getFixHeight();
    }
    scrollContent();

    //window.onresize = function() {
    //    //如果窗口变化时，重新规划高度
    //    if (c) {
    //        clearTimeout(c)
    //    }
    //    c = setTimeout("scrollContent()", 100)
    //};


});
//根据内容是否添加滚动条
function scrollContent() {
    //顶部内容高度
    var topClientHeight = window.top.document.documentElement.clientHeight;
    var iframeheight=0;
    try {
        if (parentTopHeight > 0) {
            if ($("body").attr("leftFrame") == "true") {
            	iframeheight=topClientHeight - parentTopHeight_left;
                $("#scrollContent").height(topClientHeight - parentTopHeight_left)
            } else {
                //此时此刻需要减去查询高度
            	iframeheight=topClientHeight - parentTopHeight;
                $("#scrollContent").height(topClientHeight - parentTopHeight)
            }
        }
    } catch (c) {}

    try {
        if (parentTopHeight_left > 0) {
            //设置内容高度
            //  $("#scrollContent").height(topClientHeight - parentTopHeight_left - parentBottomHeight_left - fixHeight);
        }
    } catch (c) {
    }
    //菜单的高度
    try {
        //获取iframe的内容高度
        //顶层的可用高度
        var useHeight =  topClientHeight - parentTopHeight_left;
        //iframe的内容高度
        if(iframeheight>0)
        window.top.document.getElementsByTagName("iframe")["frmright"].style.height = iframeheight + "px";
        //alert(window.top.document.getElementsByTagName("iframe")["frmleft"].style.height)
    } catch (c) {
    }
}

function iframeAutoFit()
{
    try
    {
        //是子界面情况
        if(window!=parent)
        {
            /**  FF**/
            var iframe = window.top.document.getElementsByTagName("frame");
            var iframe = window.top.window.frames;
            for(var i=0; i<iframe.length; i++) // author:meizz
            {

                //通过父界面查找本界面对应的iframe
                //判断是否是window类型
                var contentWindow = iframe[i].contentWindow || iframe[i];

                if(contentWindow==window)
                {
                    var h1=0, h2=0, d=document, dd=d.documentElement;
                    //iframe[i].style.height = "10px";
                    if(dd && dd.scrollHeight) h1=dd.scrollHeight;

                    if(d.body) h2=d.body.scrollHeight;

                    var h=Math.max(h1, h2);

                    if(document.all) {h += 4;}

                    if(window.opera) {h += 1;}
                    iframe[i].style.height = h +"px";
                }
            }

        }
    }
    catch (ex){}
}

function resetHeight() {
    try {
        scrollContent()
    } catch (a) {}
}

if (!window.console) {
    var console = {
        log: function (a) {}
    }
}

function iframeHeight(b) {
    var a = document.getElementById(b);
    a.style.height = a.contentWindow.document.body.scrollHeight + "px"
}

jQuery.fn.caps = function (a) {
    return this.keypress(function (f) {
        var b = f.which ? f.which : (f.keyCode ? f.keyCode : -1);
        var d = f.shiftKey ? f.shiftKey : (f.modifiers ? !! (f.modifiers & 4) : false);
        var g = ((b >= 65 && b <= 90) && !d) || ((b >= 97 && b <= 122) && d);
        a.call(this, g)
    })
};

jQuery.jCookie = function (i, b, l, j) {
    if (!navigator.cookieEnabled) {
        return false
    }
    var j = j || {};
    if (typeof(arguments[0]) !== "string" && arguments.length === 1) {
        j = arguments[0];
        i = j.name;
        b = j.value;
        l = j.expires
    }
    i = encodeURI(i);
    if (b && (typeof(b) !== "number" && typeof(b) !== "string" && b !== null)) {
        return false
    }
    var e = j.path ? "; path=" + j.path : "";
    var f = j.domain ? "; domain=" + j.domain : "";
    var d = j.secure ? "; secure" : "";
    var g = "";
    if (b || (b === null && arguments.length == 2)) {
        l = (l === null || (b === null && arguments.length == 2)) ? -1 : l;
        if (typeof(l) === "number" && l != "session" && l !== undefined) {
            var k = new Date();
            k.setTime(k.getTime() + (l * 24 * 60 * 60 * 1000));
            g = ["; expires=", k.toGMTString()].join("")
        }
        document.cookie = [i, "=", encodeURI(b), g, f, e, d].join("");
        return true
    }
    if (!b && typeof(arguments[0]) === "string" && arguments.length == 1 && document.cookie && document.cookie.length) {
        var a = document.cookie.split(";");
        var h = a.length;
        while (h--) {
            var c = a[h].split("=");
            if (jQuery.trim(c[0]) === i) {
                return decodeURI(c[1])
            }
        }
    }
    return false
};
// 打开对话框(添加修改)
function openDialog(title, url, width, height, target) {
    if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端，就使用自适应大小弹窗
        width = 'auto';
        height = 'auto';
    } else {//如果是PC端，根据用户设置的width和height显示。

    }
    var index = top.layer.open({
        type: 2,
        area: [width, height],
        title: title,
        maxmin: true, //开启最大化最小化按钮
        content: url,
        btn: ['确定', '关闭'],
        success: function(layero, index){
            var name = layero.find('iframe')[0].name;
            top.winref[name] = window.name;
        },
        yes: function (index, layero) {
            var body = top.layer.getChildFrame('body', index);
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
            iframeWin.contentWindow.doSubmit()
        },
        cancel: function (index) {
            //setTimeout(function () {
            //    window.location.reload(true);
            //}, 500);
        }
    });
        return index;
}

// 打开对话框(查看)
function openDialogView(title, url, width, height) {
    if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端，就使用自适应大小弹窗
        width = 'auto';
        height = 'auto';
    } else {
        //如果是PC端，根据用户设置的width和height显示。
    }
    top.layer.open({
        type: 2,
        area: [width, height],
        title: title,
        maxmin: true, //开启最大化最小化按钮
        content: url,
        btn: ['关闭'],
        success: function(layero, index){
            var name = layero.find('iframe')[0].name;
            top.winref[name] = window.name;
        },
        cancel: function (index) {
        }
    });
}
//查看机架信息，并能查看机架视图
function openDialogShowView2d(title, url, width, height, btname) {
    if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端，就使用自适应大小弹窗
        width = 'auto';
        height = 'auto';
    } else {
        //如果是PC端，根据用户设置的width和height显示。
    }
    top.layer.open({
        type: 2,
        area: [width, height],
        title: title,
        maxmin: true, //开启最大化最小化按钮
        content: url,
        btn: [btname, '关闭'],
        btn1:function(index, layero){
            //var body = top.layer.getChildFrame('body', index);
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
            console.log(iframeWin.contentWindow.showView2d());
            return false;
        },
        cancel: function (index) {
        }
    });
}
//自定义验证
(function ($) {
    $.extend($.fn.textbox.defaults.rules, {
        number: {//数字验证
            validator: function (value) {
                return /^[0-9]*$/.test(value);
            },
            message : "请输入数字"
        },
        zipCode: {//验证邮编
            validator: function (value) {
                var reg = /^[1-9]\d{5}$/;
                return reg.test(value);
            },
            message: "请输入正确的邮编"
        },
        mobileTelephone: {//验证电话
            validator: function (value) {
                var reg = /^(((((010)|(02\d)))[2-8]\d{7})|(0[3-9]\d{2}[2-8]\d{6,7})|(0?(?:147|1[3578]\d)\d{8}))$/i;
                return value.length == 11 && reg.test(value);
            },
            message: "请正确填写您的联系电话"
        },
        faxno: {// 验证传真
            validator: function (value) {
                var reg = /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i;
                return reg.test(value);
            },
            message: '请输入正确的传真号码'
        },
        chinese : {
            validator : function(value, param) {
                var reg = /^[\u4e00-\u9fa5]+$/i;
                return reg.test(value);
            },
            message : "请输入中文"
        },
        checkLength: {
            validator: function(value, param){
                return param[0] >= get_length(value);
            },
            message: '请输入最大{0}位字符'
        },
        specialCharacter: {
            validator: function(value, param){
                var reg = new RegExp("[`~!@#$^&*()=|{}':;'\\[\\]<>~！@#￥……&*（）——|{}【】‘；：”“'、？]");
                return !reg.test(value);
            },
            message: '不允许输入特殊字符'
        },
        englishLowerCase  : {// 验证英语小写
            validator: function (value) {
                return /^[a-z]+$/.test(value);
            },
            message: '请输入小写字母'
        },
        ip: {// 验证IP地址
            validator: function (value) {
                return /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(value);
            },
            message: 'IP地址格式不正确'
        },
        intOrFloat: {// 验证整数或小数
            validator: function (value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message: '请输入数字，并确保格式正确'
        },
        zbMachineroomReg:{//机房名称验证
            validator:function(value){
                return /^([A-Za-z0-9]+_){4}([A-Za-z0-9]+)-\d+$/.test(value);
            },
            message:'请输入正确格式机房，如：YD_SC_CD_XY_B04-110'
        },
        wlOrPxOrPtRackReg:{//普通机柜|网络柜|配线柜验证
            validator:function(value){
                return /^([A-Za-z0-9]+_){5}\d+_[A-Z]{1}[0-9]{2}$|^([A-Za-z0-9]+_){5}\d+_WL-[A-Z]{1}$|^([A-Za-z0-9]+_){5}\d+_PX-[A-Z]{1}$/.test(value);
            },
            message:'请输入正确格式机架，如：<\/br>&nbsp;&nbsp;客户柜：YD_SC_CD_XY_B04_111_B12<\/br>&nbsp;&nbsp;网络柜：YD_SC_CD_XQ_B01_201_WL-F</br>&nbsp;&nbsp;配线柜：YD_SC_CD_XQ_B01_201_PX-F'
        },
        PDFRackReg:{//PDF架验证
            validator:function(value){
                return /^([A-Za-z0-9]+_){5}\d+_PDF_[A-Z]{1}$|^([A-Za-z0-9]+_){5}\d+_PDF_[A-Z]{1}_[a-b]{1}$/.test(value);
            },
            message:'请输入正确格式机架，如：<\/br>&nbsp;&nbsp;YD_SC_CD_XY_B04_107_PDF_A<\/br>&nbsp;&nbsp;YD_SC_CD_XY_B04_107_PDF_A_a'
        },
        ODFRackReg:{//ODF架验证
            validator:function(value){
                return /^([A-Za-z0-9]+_){5}\d+_ODF_[A-Z]{1}[0-9]{2}$|^([A-Za-z0-9]+_){5}\d+_ODF_[A-Z]{1}[0-9]{2}_[a-b]{1}$/.test(value);
            },
            message:'请输入正确格式机架，如：<\/br>&nbsp;&nbsp;YD_SC_CD_XY_B04_107_ODF_A01<\/br>&nbsp;&nbsp;YD_SC_CD_XY_B04_107_ODF_A01_a'
        }
    });
})(jQuery);

function fmtDateAction(value,row,index){
    if (value != null) {
        var date = new Date(value);
        var year = date.getFullYear().toString();
        var month = (date.getMonth() + 1);
        var day = date.getDate().toString();
        var hour = date.getHours().toString();
        var minutes = date.getMinutes().toString();
        var seconds = date.getSeconds().toString();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
        //return date.getFullYear() + '-' + (date.getMonth() + 1) + '-'+ date.getDate() +' '+date.getHours()+':'+date.getMinutes()+':'+date.getMilliseconds();
    }
}
/*layer post提交*/
function openDialogUsePost(title, url, width, height, data,id){
    if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端，就使用自适应大小弹窗
        width = 'auto';
        height = 'auto';
    } else {
        //如果是PC端，根据用户设置的width和height显示。
    }
    top.layer.open({
        type: 2,
        id:id,
        area: [width , height],
        title: title,
        maxmin: true, //开启最大化最小化按钮
        content: 'blankpage',
        scrollbar:false,
        btn: ['确定','关闭'],
        success: function(layero, index){
            var name = layero.find('iframe')[0].name;
            top.winref[name] = window.name;
            var chidlwin = layero.find('iframe')[0].contentWindow;
            if(chidlwin.location.href.indexOf('blankpage')>-1){
                var html=buildFormByParam(url,data);
                chidlwin.document.body.innerHTML="";//清空iframe内容，达到重新请求
                chidlwin.document.write(html);
                chidlwin.document.getElementById('postData_form').submit();
            }
        },
        yes: function(index, layero){
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
            iframeWin.contentWindow.doSubmit();
        },
        cancel: function (index) {
        }
    });
}
function openDialogUsePostView(title, url, width, height, data){
    if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {//如果是移动端，就使用自适应大小弹窗
        width = 'auto';
        height = 'auto';
    } else {
        //如果是PC端，根据用户设置的width和height显示。
    }
    top.layer.open({
        type: 2,
        area: [width , height],
        title: title,
        maxmin: true, //开启最大化最小化按钮
        content: 'blankpage',
        scrollbar:false,
        btn: ['关闭'],
        success: function(layero, index){
            var chidlwin = layero.find('iframe')[0].contentWindow;
            if(chidlwin.location.href.indexOf('blankpage')>-1){
                var html=buildFormByParam(url,data);
                chidlwin.document.body.innerHTML="";//清空iframe内容，达到重新请求
                chidlwin.document.write(html);
                chidlwin.document.getElementById('postData_form').submit();
            }
        },
        cancel: function (index) {
        }
    });
}
function buildFormByParam(url,params){
    var result='<form action="'+url+'" method="post" target="_self" id="postData_form">';
    for(var k in params){
        result+='<input name="'+k+'" type="hidden" value="'+params[k]+'"/>';
    }
    result+='</form>';
    return result;
}