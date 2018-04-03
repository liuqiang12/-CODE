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
Vue.component('mycell', {
    template: '#Mycelltmpl',
    props: ['data'],
    computed: {
        left:function(){
            return this.data.c * 40
        },
        top:function(){
            return this.data.r * 40
        }
    }
});
Vue.component('rackcell', {
    template: '#rackcelltmpl',
    props: ['data'],
    data: function () {
        return {selected: false}
    },
    computed: {
        left:function(){
            return this.data.c * 40 + 1.5
        },
        top:function(){
            return this.data.r * 40 + 1.5
        },
        namestr: function () {
            var newName = this.data.data.name;
            var businesttype = this.data.data.nameType;
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
        getStatus: function () {
            var data = this.data.data;
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
            if (data.businesstypeId == 'df' || data.businesstypeId == 'pdu') {
                sstr = "row__seat--cannotuse";
            }
            sstr += " row__seat";
            return sstr
        }
    },
    methods: {
        drag: function (event) {
            if (typeof(this.data.data.status) == 'undefined') {
                return false;
            } else {
                return true
            }
        },
        dropIn: function (event) {
            alert(11111);
            if (typeof(this.data.status) && parseInt(this.data.status) > 0) {
                top.layer.msg('当前位置已有机柜')
            }
        },
        dbclickrow: function () {
            clearTimeout(timeFn);
            openrackView(this.data.data.id, this.data.data.businesstypeId, this.data.data.dftype);
        },
        clickrow: function () {
            clearTimeout(timeFn);
            var mydata = [];
            var iteam = this.data.data;
            var obj = this;
            timeFn = setTimeout(function () {
                mydata = mydata.concat(roomInfo);
                mydata.push({
                    name: '机架名称',
                    value: function () {
                        return '<a href="javascript:void(0)" onclick="openrack(' + iteam.id + ',\'' + iteam.businesstypeId + '\',\'' + iteam.dftype + '\')">' + iteam.name + '</a>'
                    }(),
                    group: '机架信息',
                });
                mydata.push({
                    name: '机架类型',
                    value: function () {
                        var businesstype_id = iteam.businesstypeId;
                        var dftype = iteam.dftype;
                        if (businesstype_id == "equipment") {
                            return '客户机架'
                        }
                        if (businesstype_id == "df" && dftype != 'wiring') {
                            return 'ODF/DDF'
                        }
                        if (businesstype_id == "df" && dftype == 'wiring') {
                            return '配线柜'
                        }
                        if (businesstype_id == "cabinet") {
                            return '网络头柜'
                        }
                        if (businesstype_id == "pdu") {
                            return 'PDU'
                        }
                    }(),
                    group: '机架信息',
                });
                mydata.push({
                    name: '出租类型',
                    value: function () {
                        var renttype = iteam.renttype;
                        if (renttype == "0") {
                            return '整架出租'
                        }
                        if (renttype == "1") {
                            return '按机位出租'
                        }
                    }(),
                    group: '机架信息',
                });
                mydata.push({
                    name: '用途',
                    value: function () {
                        var usefor = iteam.usefor;
                        if (usefor == "1") {
                            return "自用";
                        } else if (usefor == "2") {
                            return "客用";
                        }
                    }(),
                    group: '机架信息'
                }, {
                    name: 'U位数',
                    value: iteam.height,
                    group: '机架信息'
                }, {
                    name: '客户',
                    value: iteam.actualcustomer,
                    group: '机架信息'
                }, {
                    name: '电源类型',
                    value: iteam.pduPowertype,
                    group: '机架信息'
                }, {
                    name: '额定电量',
                    value: iteam.ratedelectricenergy,
                    group: '机架信息'
                });
                $('#pg').propertygrid('loadData', mydata);
                //只能选择一个
                //var obj = this;
                $.each(rowsdiv.$children, function (i, iteam) {
                    if (obj != iteam && iteam.selected == true) {
                        iteam.selected = false;
                    }
                });
                obj.selected = !obj.selected
            }, 200);
        }
    }
});
Vue.component('moudle', {
    template: '#moudle',
    props: ['data'],
    data: function () {
        return {
            moduleName: this.data.n
        }
    },
    computed: {
        left:function(){
            return this.data.c * 40
        },
        top:function(){
            return this.data.r * 40
        },
        width:function(){
            return  this.data.w * 40
        },
        height:function(){
            return this.data.h * 40
        },
        getModuleClass: function () {
            var sty = this.data.sty;
            if (sty == 0) {
                return "moduleStyle1";
            } else {
                return "moduleStyle2";
            }
        }
    },
    methods: {
        clickdiv: function () {
            console.log(this)
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
        rackata: (function () {
            //机架数据
            var data = [];
            for (var i = 0; i < resultList.length; i++) {
                var row = resultList[i].rownum + 1;
                for (var k = 0; k < resultList[i].cells.length; k++) {
                    var cell = resultList[i].cells[k].cellnum;
                    var rack = resultList[i].cells[k].data;
                    //放入机架
                    data.push({r: row, c: cell, data: rack});
                }
            }
            return data;
        })(),
        moudata: (function () {
            //模块数据
            var data = [];
            for (var i = 0; i < resultModuleList.length; i++) {
                var moduleInfo = resultModuleList[i];
                data.push({
                    r: moduleInfo.r,
                    c: moduleInfo.c,
                    w: moduleInfo.w,
                    h: moduleInfo.h,
                    n: moduleInfo.n,
                    sty: moduleInfo.sty
                });
            }
            return data;
        })(),
        celldata: (function () {
            var data = [];
            var index = 0;
            for (var r = 0; r < 50; r++) {
                for (var c = 0; c < 50; c++) {
                    index += 1;
                    data.push({r: r, c: c,key:index});
                }
            }
            return data;
        })()
    },
    created: function () {
    }
});

jQuery(document).ready(function () {
    if (roomid == "") roomid = 0;
    if (action != 'select') {
        initPie(idcRacks);
        $('#pg').propertygrid({
            showGroup: true,
            title: '机房' + roomName,
            columns: [[
                {field: 'name', title: '名字', width: 100, sortable: false},
                {field: 'value', title: '值', width: 200, resizable: false}
            ]],
            data: roomInfo,
            scrollbarSize: 0
        });
    }
    $('.row__seat').each(function () {
        if ($(this).attr("data-tipso") != "" && typeof($(this).attr("data-tipso")) != 'undefined') {
            $(this).tipso({useTitle: false});
        }
    })
});
function doSubmit() {
    var ids = [];
    $.each(rowsdiv.$children, function (i, iteam) {
        if (iteam.selected == true) {
            ids.push(iteam.data.id);
        }
    });
    return ids;
}
var initPie = function (rows) {
    var width = $("#piediv").parent().width();
    var height = $("#piediv").parent().height();
    var pieChar = echarts.init(document.getElementById("piediv"));
    var datatemplate = [
        {name: '不可用', value: 0},
        {name: '可用', value: 0},
        {name: '预留', value: 0},
        {name: '空闲', value: 0},
        {name: '预占', value: 0},
        {name: '停机', value: 0},
        {name: '在服', value: 0}
    ];
    var option = {
        title: {
            text: '机房状态分布',
            x: 'center'
        },
        mytooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'right',
            data: ['不可用', '可用', '预留', '空闲', '预占', '停机', '在服'],
            formatter: function (name) {
                var oa = option.series[0].data;
                for (var i = 0; i < option.series[0].data.length; i++) {
                    if (name == oa[i].name) {
                        return name + ' ' + (oa[i].value / rackTotal * 100).toFixed(2) + '%';
                    }
                }
            },
            y: 40
        },
        series: [
            {
                name: '状态分布',
                type: 'pie',
                radius: '55%',
                color: ['#272424', '#86de2b', '#69deb4', '#92ded1', '#beb6de', '#de6363', '#21c252'],
                center: ['35%', '60%'],
                data: datatemplate,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    pieChar.setOption(option);
    pieChar.resize({width: width - 20, height: height});
    var rdata = {};
    var inputdata = function (status) {
        if (rdata[status]) {
            rdata[status] += 1;
        } else {
            rdata[status] = 1;
        }
    };
    $(rows).each(function (i, data) {
        if (data.status && data.businesstypeId == 'equipment') {
            if (data.status == '110') {
                inputdata('不可用')
            } else if (data.status == '20') {
                inputdata('可用')
            } else if (data.status == '30') {
                inputdata('预留')
            } else if (data.status == '40') {
                inputdata('空闲')
            } else if (data.status == '50') {
                inputdata('预占')
            } else if (data.status == '55') {
                inputdata('停机')
            } else if (data.status == '60') {
                inputdata('在服')
            } else {
                inputdata('不可用')
            }
        }
    });
    $.each(datatemplate, function (i, iteam) {
        iteam.value = rdata[iteam.name] || 0;
    });
    option.series[0].data = datatemplate;
    pieChar.setOption(option);
};