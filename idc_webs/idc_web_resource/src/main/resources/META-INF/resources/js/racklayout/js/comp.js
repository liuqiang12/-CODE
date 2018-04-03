Vue.component('jijia', {
    template: '#jijia',
    props: {
        uuid: 0,
    },
    computed: {
        statusfmt: function () {
            if (this.status == '110') {
                return '不可用'
            }
            else if (this.status == '20') {
                return '可用'
            }
            else if (this.status == '30') {
                return '预留'
            } else if (this.status == '40') {
                return '空闲'
            }
            else if (this.status == '50') {
                return '预占'
            } else if (this.status == '55') {
                return '已停机'
            }
            else if (this.status == '60') {
                return '在服'
            }
            else {
                return ''
            }
        },
        modelstr: function () {
            if (this.model == 'equipment') {
                return '客户机架'
            }
            else if (this.model == 'df') {
                return 'ODF/DDF'
            }
            else if (this.model == 'cabinet') {
                return '网络头柜'
            }
            else if (this.model == 'pdu') {
                return 'PDU'
            } else {
                return ''
            }
        },
        borrowedtypefmt: function () {
        }
    },
    methods: {
        setPanel: function (obj) {
            for (name in this._data) {
                var nvalue = obj[name];
                if (typeof (nvalue) != 'undefined')
                    this._data[name] = nvalue
            }
        },
        update: function () {
            console.log(new Date());
        }
    },
    data: function () {
        return this.$store.state.jijiadata
    },
});

Vue.component('jiwei', {
    template: '#jiwei',
    props: {
        uidd: ''
    },
    computed: {
        statusfmt: function () {
            if (this.status == '110') {
                return '不可用'
            }else if (this.status == '20'||this.status == '0') {
                return '空闲'
            }else if (this.status == '50') {
                return '预占'
            }else if (this.status == '55') {
                return '占用'
            }else if (this.status == '60') {
                return '在服'
            }else {
                return ''
            }
        },
        busfmt: function () {
            return '业务'
        }
    },
    methods: {
        create: function () {
            this.$store.dispatch('createjiwei');
        },
        add: function () {
        },
    },
    data: function () {
        return this.$store.state.jiweidata
    },
});
Vue.component('sigjiwei', {
    template: '#sigjiwei',
    props: {
        obj: Object,
        index: Number,
        iteam: Object
    },
    computed: {
        statusfmt: function () {
            if (this.status == '110') {
                return '不可用'
            }else if (this.status == '20'||this.status == '0') {
                return '空闲'
            }else if (this.status == '50') {
                return '预占'
            }else if (this.status == '55') {
                return '占用'
            }else if (this.status == '60') {
                return '在服'
            }else {
                return ''
            }
        },
        busfmt: function () {
            return '业务'
        },
        showimg: function () {
            // var style = {}
            // if (this.obj.status > 0) {
            //     style['background-image']= 'url(jiwei.png)'
            // }
            // return style;
        },
    },
    methods: {
        show: function (obj, index, event) {
            obj.index = index;
            this.$store.dispatch('showjiwei', obj);
            $(event.target).parents("div.available").addClass("availableSelected").siblings().removeClass("availableSelected");
        }
    },
    data: function () {
        return {}
    },
});
Vue.component('equipment', {
    template: '#equipment',
    props: {
        datas: Array
    },
    computed: {
        getpos: function (index) {
            return '';
        },
    },
    methods: {
        getClass: function (status) {
            if (status == '110') {
                sstr = "notavailable"
            } else if (status == '20') {
                sstr = "available"
            }else if (status == '55') {
                sstr = "occupied"
            }else if(status == '50'){
                sstr = "reserviced";
            }else if (status == '60') {
                sstr = "inservice"
            } else {
                sstr = "available"
            }
            return sstr;
        },

        serdata: function (data) {
            return data;
            //var i = 0;//当前索引0
            // for (var index = 0; index < data.length; index++) {
            //     data[index].posindex = i;
            //     data[index].useunum = parseInt(data[index].useunum || 1);
            //     i += parseInt(data[index].useunum || 1);
            // }
            // data.forEach(function (element) {
            //     element.posindex = i;
            //     i += element.useunum;
            //     // console.log(element)
            // }, this);
            // console.log(data)
            // return data;
        },

    },
    data: function () {
        return {}
    }
});
