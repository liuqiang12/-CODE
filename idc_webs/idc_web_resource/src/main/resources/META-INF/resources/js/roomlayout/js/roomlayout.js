

/*!
 * tipso - A Lightweight Responsive jQuery Tooltip Plugin v1.0.1
 * Copyright (c) 2014 Bojan Petkovski
 * /tipso.object505.com
 * Licensed under the MIT license
 * /object505.mit-license.org/
 */
!function (t, o, s, e) {
    function r(o, s) {
        this.element = t(o), this.settings = t.extend({}, l, s), this._defaults = l, this._name = d, this._title = this.element.attr("title"), this.mode = "hide", this.init()
    }

    function i() {
        var t = o.navigator.msMaxTouchPoints, e = "ontouchstart" in s.createElement("div");
        return t || e ? !0 : !1
    }

    function n(o) {
        var s = o.clone();
        s.css("visibility", "hidden"), t("body").append(s);
        var e = s.outerHeight();
        return s.remove(), e
    }

    function a(s) {
        var e, r, i, a = s.tooltip(), d = s.element, l = s, f = t(o), p = 10;
        switch (l.settings.position) {
            case"top":
                r = d.offset().left + d.outerWidth() / 2 - a.outerWidth() / 2, e = d.offset().top - n(a) - p, a.find(".tipso_arrow").css({marginLeft: -8}), e < f.scrollTop() ? (e = d.offset().top + d.outerHeight() + p, a.find(".tipso_arrow").css({
                    "border-bottom-color": l.settings.background,
                    "border-top-color": "transparent"
                }), a.removeClass("top bottom left right"), a.addClass("bottom")) : (a.find(".tipso_arrow").css({
                    "border-top-color": l.settings.background,
                    "border-bottom-color": "transparent"
                }), a.removeClass("top bottom left right"), a.addClass("top"));
                break;
            case"bottom":
                r = d.offset().left + d.outerWidth() / 2 - a.outerWidth() / 2, e = d.offset().top + d.outerHeight() + p, a.find(".tipso_arrow").css({marginLeft: -8}), e + n(a) > f.scrollTop() + f.outerHeight() ? (e = d.offset().top - n(a) - p, a.find(".tipso_arrow").css({
                    "border-top-color": l.settings.background,
                    "border-bottom-color": "transparent"
                }), a.removeClass("top bottom left right"), a.addClass("top")) : (a.find(".tipso_arrow").css({
                    "border-bottom-color": l.settings.background,
                    "border-top-color": "transparent"
                }), a.removeClass("top bottom left right"), a.addClass(l.settings.position));
                break;
            case"left":
                r = d.offset().left - a.outerWidth() - p, e = d.offset().top + d.outerHeight() / 2 - n(a) / 2, a.find(".tipso_arrow").css({
                    marginTop: -8,
                    marginLeft: ""
                }), r < f.scrollLeft() ? (r = d.offset().left + d.outerWidth() + p, a.find(".tipso_arrow").css({
                    "border-right-color": l.settings.background,
                    "border-left-color": "transparent",
                    "border-top-color": "transparent",
                    "border-bottom-color": "transparent"
                }), a.removeClass("top bottom left right"), a.addClass("right")) : (a.find(".tipso_arrow").css({
                    "border-left-color": l.settings.background,
                    "border-right-color": "transparent",
                    "border-top-color": "transparent",
                    "border-bottom-color": "transparent"
                }), a.removeClass("top bottom left right"), a.addClass(l.settings.position));
                break;
            case"right":
                r = d.offset().left + d.outerWidth() + p, e = d.offset().top + d.outerHeight() / 2 - n(a) / 2, a.find(".tipso_arrow").css({
                    marginTop: -8,
                    marginLeft: ""
                }), r + p + l.settings.width > f.scrollLeft() + f.outerWidth() ? (r = d.offset().left - a.outerWidth() - p, a.find(".tipso_arrow").css({
                    "border-left-color": l.settings.background,
                    "border-right-color": "transparent",
                    "border-top-color": "transparent",
                    "border-bottom-color": "transparent"
                }), a.removeClass("top bottom left right"), a.addClass("left")) : (a.find(".tipso_arrow").css({
                    "border-right-color": l.settings.background,
                    "border-left-color": "transparent",
                    "border-top-color": "transparent",
                    "border-bottom-color": "transparent"
                }), a.removeClass("top bottom left right"), a.addClass(l.settings.position))
        }
        r < f.scrollLeft() && ("bottom" == l.settings.position || "top" == l.settings.position) && (a.find(".tipso_arrow").css({marginLeft: r - 8}), r = 0), r + l.settings.width > f.outerWidth() && ("bottom" == l.settings.position || "top" == l.settings.position) && (i = f.outerWidth() - (r + l.settings.width), a.find(".tipso_arrow").css({
            marginLeft: -i - 8,
            marginTop: ""
        }), r += i), r < f.scrollLeft() && ("left" == l.settings.position || "right" == l.settings.position) && (r = d.offset().left + d.outerWidth() / 2 - a.outerWidth() / 2, a.find(".tipso_arrow").css({
            marginLeft: -8,
            marginTop: ""
        }), e = d.offset().top - n(a) - p, e < f.scrollTop() ? (e = d.offset().top + d.outerHeight() + p, a.find(".tipso_arrow").css({
            "border-bottom-color": l.settings.background,
            "border-top-color": "transparent",
            "border-left-color": "transparent",
            "border-right-color": "transparent"
        }), a.removeClass("top bottom left right"), a.addClass("bottom")) : (a.find(".tipso_arrow").css({
            "border-top-color": l.settings.background,
            "border-bottom-color": "transparent",
            "border-left-color": "transparent",
            "border-right-color": "transparent"
        }), a.removeClass("top bottom left right"), a.addClass("top")), r + l.settings.width > f.outerWidth() && (i = f.outerWidth() - (r + l.settings.width), a.find(".tipso_arrow").css({
            marginLeft: -i - 8,
            marginTop: ""
        }), r += i), r < f.scrollLeft() && (a.find(".tipso_arrow").css({marginLeft: r - 8}), r = 0)), r + l.settings.width > f.outerWidth() && ("left" == l.settings.position || "right" == l.settings.position) && (r = d.offset().left + d.outerWidth() / 2 - a.outerWidth() / 2, a.find(".tipso_arrow").css({
            marginLeft: -8,
            marginTop: ""
        }), e = d.offset().top - n(a) - p, e < f.scrollTop() ? (e = d.offset().top + d.outerHeight() + p, a.find(".tipso_arrow").css({
            "border-bottom-color": l.settings.background,
            "border-top-color": "transparent",
            "border-left-color": "transparent",
            "border-right-color": "transparent"
        }), a.removeClass("top bottom left right"), a.addClass("bottom")) : (a.find(".tipso_arrow").css({
            "border-top-color": l.settings.background,
            "border-bottom-color": "transparent",
            "border-left-color": "transparent",
            "border-right-color": "transparent"
        }), a.removeClass("top bottom left right"), a.addClass("top")), r + l.settings.width > f.outerWidth() && (i = f.outerWidth() - (r + l.settings.width), a.find(".tipso_arrow").css({
            marginLeft: -i - 8,
            marginTop: ""
        }), r += i), r < f.scrollLeft() && (a.find(".tipso_arrow").css({marginLeft: r - 8}), r = 0)), a.css({
            left: r + l.settings.offsetX,
            top: e + l.settings.offsetY
        })
    }

    var d = "tipso", l = {
        speed: 400,
        background: "#55b555",
        color: "#ffffff",
        position: "top",
        width: 300,
        delay: 200,
        offsetX: 0,
        offsetY: 0,
        content: null,
        ajaxContentUrl: null,
        useTitle: !0,
        onBeforeShow: null,
        onShow: null,
        onHide: null
    };
    t.extend(r.prototype, {
        init: function () {
            var o = this, e = this.element;
            e.addClass("tipso_style").removeAttr("title"), i() ? (e.on("click." + d, function (t) {
                "hide" == o.mode ? o.show() : o.hide(), t.stopPropagation()
            }), t(s).on("click", function () {
                "show" == o.mode && o.hide()
            })) : (e.on("mouseover." + d, function () {
                o.show()
            }), e.on("mouseout." + d, function () {
                o.hide()
            }))
        }, tooltip: function () {
            return this.tipso_bubble || (this.tipso_bubble = t('<div class="tipso_bubble"><div class="tipso_content"></div><div class="tipso_arrow"></div></div>')), this.tipso_bubble
        }, show: function () {
            var s = this.tooltip(), e = this, r = t(o);
            t.isFunction(e.settings.onBeforeShow) && e.settings.onBeforeShow(t(this)), s.css({
                background: e.settings.background,
                color: e.settings.color,
                width: e.settings.width
            }).hide(), s.find(".tipso_content").html(e.content()), a(e), r.resize(function () {
                a(e)
            }), e.timeout = o.setTimeout(function () {
                s.appendTo("body").stop(!0, !0).fadeIn(e.settings.speed, function () {
                    e.mode = "show", t.isFunction(e.settings.onShow) && e.settings.onShow(t(this))
                })
            }, e.settings.delay)
        }, hide: function () {
            var s = this, e = this.tooltip();
            o.clearTimeout(s.timeout), s.timeout = null, e.stop(!0, !0).fadeOut(s.settings.speed, function () {
                t(this).remove(), t.isFunction(s.settings.onHide) && "show" == s.mode && s.settings.onHide(t(this)), s.mode = "hide"
            })
        }, destroy: function () {
            var t = this.element;
            t.off("." + d), t.removeData(d), t.removeClass("tipso_style").attr("title", this._title)
        }, content: function () {
            var o, s = this.element, e = this, r = this._title;
            return o = e.settings.ajaxContentUrl ? t.ajax({
                type: "GET",
                url: e.settings.ajaxContentUrl,
                async: !1
            }).responseText : e.settings.content ? e.settings.content : e.settings.useTitle === !0 ? r : s.data("tipso")
        }, update: function (t, o) {
            var s = this;
            return o ? void(s.settings[t] = o) : s.settings[t]
        }
    }), t[d] = t.fn[d] = function (o) {
        var s = arguments;
        if (o === e || "object" == typeof o)return this instanceof t || t.extend(l, o), this.each(function () {
            t.data(this, "plugin_" + d) || t.data(this, "plugin_" + d, new r(this, o))
        });
        if ("string" == typeof o && "_" !== o[0] && "init" !== o) {
            var i;
            return this.each(function () {
                var e = t.data(this, "plugin_" + d);
                e || (e = t.data(this, "plugin_" + d, new r(this, o))), e instanceof r && "function" == typeof e[o] && (i = e[o].apply(e, Array.prototype.slice.call(s, 1))), "destroy" === o && t.data(this, "plugin_" + d, null)
            }), i !== e ? i : this
        }
    }
}(jQuery, window, document);
/**
 * Created by mylove on 2017/5/26.
 */
Vue.component('cell', {
    template: '#celltmpl',
    props: ['data'],
    data:function(){
        return { selected: false }
    },
    computed:{
        namestr: function () {
            var newName = this.data.name;
            var businesttype = this.data.nameType;
            var returnStr = '';
            if (businesttype == 'KH' || businesttype == 'PX' || businesttype == 'WL') {//客户柜/配线/网络柜
                returnStr = newName.split('_')[6];
            } else if (businesttype == 'ODF') {//odf
                returnStr = 'ODF' + newName.split('ODF')[1];
            } else if (businesttype == 'PDF') {//pdf
                returnStr = 'PDF' + newName.split('PDF')[1];
            }
            return returnStr;
        },
        draggable:function(){
            if(this.data.status&&this.data.status>0){
                return true
            }else{
                return false
            }
        },
        getStatus: function () {
            var data  = this.data;
            var sstr = "mytooltip";
            var businessType = data.businesstypeId;
            if (data.status) {
                if (data.status == '110') {
                    sstr = "row__seat--cannotuse"
                } else if (data.status == '20') {
                    sstr = "row__seat--canuse"
                } else if (data.status == '30') {
                    sstr = "row__seat--perfree"
                } else if (data.status == '40') {
                    sstr = "row__seat--free"
                } else if (data.status == '50') {
                    sstr = "row__seat--preused"
                } else if (data.status == '55') {
                    sstr = "row__seat--stop"
                } else if (data.status == '60') {
                    sstr = "row__seat--runing"
                } else {
                    sstr = "row__seat--cannotuse"
                }
                //66001位按机架分配  66002按机位分配    //只有客户架 可用
                if (businessType == 'df' || businessType == 'pdu') {
                    sstr = "row__seat--cannotuse";
                }
            }
            return sstr;
        }
    },
    methods:{
        drag:function(event){
            if(typeof(this.data.status)== 'undefined'){
                return false;
            }else{
                return true
            }
        },
        afterDrop:function(){
            console.log(111)
        },
        ondragenter:function(event){

        },
        dropIn:function(event){
            alert(11111);
            //event.preventDefault()
            if(typeof(this.data.status)&&parseInt(this.data.status)>0){
                top.layer.msg('当前位置已有机柜')
            }
        },
        dbclickrow: function () {
            clearTimeout(timeFn);
            openrackView(this.data.id, this.data.businesstypeId, this.data.dftype);
        },
        clickrow: function () {
            clearTimeout(timeFn);
            var iteam = this.data;
            var obj = this;
            var classStr = this.getStatus;
            timeFn = setTimeout(function () {
                if (classStr != 'row__seat--canuse' && classStr != 'row__seat--free') {
                    return;
                }
                if (distriType == 66001 && iteam.status != 40) {
                    return;
                }
                if(isCtrlDown){//按下ctrl，可多选
                    if(!obj.selected){
                        xStart = iteam.x,yStart=iteam.y,xEnd=0,yEnd=0;
                    }
                    obj.selected = !obj.selected;
                }else if(isShiftDown){//按下shift
                    if(!obj.selected){
                        xEnd=iteam.x,yEnd=iteam.y;
                    }
                    if(xEnd!=0&&yEnd!=0&&xStart!=0&&yStart!=0){
                        if(xStart > xEnd){
                            var vv = xEnd;
                            xEnd = xStart;
                            xStart = vv;
                        }
                        if(yStart > yEnd){
                            var vv = yEnd;
                            yEnd = yStart;
                            yStart = vv;
                        }
                        for(var j=0;j<planstr.length;j++){
                            var row = planstr[j].rownum;
                            for(var k = 0;k<planstr[j].cells.length;k++){
                                var cell = planstr[j].cells[k].cellnum;
                                var rackId = planstr[j].cells[k].data.id;
                                if((row>xStart && row<xEnd)||(xStart==xEnd&&xStart==row&&cell>=yStart&&cell<=yEnd)||
                                    (row==xStart&&xStart<xEnd&&cell>=yStart)||(row==xEnd&&xStart<xEnd&&cell<=yEnd)){
                                    $.each(rowsdiv.$children, function (i, param) {
                                        if (typeof param.data.id != 'undefined' && rackId == param.data.id && param.selected == false) {
                                            if (param.getStatus != 'row__seat--canuse' && param.getStatus != 'row__seat--free') {
                                                return;
                                            }
                                            if (distriType == 66001 && iteam.status != 40) {
                                                return;
                                            }
                                            param.selected = true;
                                        }
                                    });
                                }
                            }
                        }
                    }
                }else{//只单选
                    $.each(rowsdiv.$children, function (i, iteam) {
                        if (obj != iteam && iteam.selected == true) {
                            iteam.selected = false;
                        }
                    });
                    if(!obj.selected){
                        xStart = iteam.x,yStart=iteam.y,xEnd=0,yEnd=0;
                    }
                    obj.selected = !obj.selected;
                }
            }, 200);
        }

    }
});
function openrack(id, type, dftype) {
    if (type == 'df' && dftype != 'wiring') {
        openDialogShowView2d('查看ODF信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + id + '&businesstype=odf&buttonType=view', '800px', '400px', '查看机架视图');
    } else if (type == 'pdu') {
        openDialogShowView2d('查看PDF信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + id + '&businesstype=pdu&buttonType=view', '800px', '400px', '查看机架视图');
    } else {
        openDialogShowView2d('查看机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + id + '&businesstype=other&buttonType=view', '800px', '430px', '查看机架视图');
    }
}
function openrackView(id, type, dftype) {
    if (type == 'df' && dftype != 'wiring') {
        openDialogView('ODF架视图', contextPath + '/idcRack/viewodf/' + id, '1120px', '530px');
    } else if (type == 'pdu') {
        openDialogView('PDF架视图', contextPath + '/idcRack/viewpdf/' + id, '800px', '530px');
    } else {
        window.open(contextPath + '/racklayout/' + id);
    }
}
var rowsdiv = new Vue({
    el: '#rowsdiv',
    data: {
        /****
         * 生成的格子
         */
        rows: function () {
            var rowData  = planstr;//$.parseJSON(planstr);
            return rowData;
        }()
    },
    methods: {
        //点击格子时候时间 当前为显示机架信息
        dbclickrow: function (iteam) {
            openrack(iteam.id);
        },
        clickrow: function (iteam) {
            console.log(iteam);
        },
        getStatus: function (data) {
            console.log(data);
            var sstr = "mytooltip";
            if (data.status) {
                if (data.status == '110') {
                    sstr = "row__seat--cannotuse"
                } else if (data.status == '20') {
                    sstr = "row__seat--canuse"
                } else if (data.status == '30') {
                    sstr = "row__seat--perfree"
                } else if (data.status == '40') {
                    sstr = "row__seat--free"
                } else if (data.status == '50') {
                    sstr = "row__seat--preused"
                } else if (data.status == '55') {
                    sstr = "row__seat--stop"
                } else if (data.status == '60') {
                    sstr = "row__seat--runing"
                } else {
                    sstr = "row__seat--cannotuse"
                }
            }
            return sstr
        }
    }
});
//响应键盘按下事件
$(document).keydown(function (event) {
    var e = event || window.event;
    var code = e.keyCode | e.which | e.charCode;
    switch (code) {
        case KEY.SHIFT:
            isShiftDown = true;
            break;
        case KEY.CTRL:
            isCtrlDown = true;
            break;
        default:
    }
});
//响应键盘按键放开的事件
$(document).keyup(function (event) {
    var e = event || window.event;
    var code = e.keyCode | e.which | e.charCode;
    switch (code) {
        case KEY.SHIFT:
            isShiftDown = false;
            break;
        case KEY.CTRL:
            isCtrlDown = false;
            break;
        default:
    }
});
function doSubmit(){
    var ids = [];
    $.each(rowsdiv.$children,function(i,iteam){
        if(iteam.selected==true){
            ids.push(iteam.data.id);
        }
    });
    return ids;
}

