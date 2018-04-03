<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mylove
  Date: 2017/5/24
  Time: 11:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script src="<%=request.getContextPath() %>/framework/vuejs/vue.min.js" type="text/javascript"></script>
    <STYLE>
        .switch {
            width: 200px;
            height: 30px;
        }

        .status_open {
            background: black
        }

        .status_close {
            background: red
        }

        .status_no {
            background: darkblue
        }

        .toggle {
            position: relative;
            display: block;
            margin: 0 auto;
            width: 70px;
            height: 26px;
            color: white;
            outline: 0;
            text-decoration: none;
            border-radius: 100px;
            border: 2px solid #546E7A;

            -webkit-transition: all 500ms;
            -moz-transition: all 500ms;
            -o-transition: all 500ms;
            transition: all 500ms;
        }
        .toggle:after {
            display: block;
            position: absolute;
            top: 2px;
            bottom: 2px;
            left: 2px;
            width: calc(50% - 4px);
            line-height: 20px;
            text-align: center;
            text-transform: uppercase;
            font-size: 12px;
            color: white;
            background-color: #37474F;
            border: 2px solid;
            -webkit-transition: all 500ms;
            -moz-transition: all 500ms;
            -o-transition: all 500ms;
            transition: all 500ms;
        }

        .toggle--on:after {
            content: 'On';
            border-radius: 50px 5px 5px 50px;
            color: #66BB6A;
        }

        .toggle--off:after {
            content: 'Off';
            border-radius: 5px 50px 50px 5px;
            float: right;
            color:lightgray;
            -webkit-transform: translate(100%, 0);
            -moz-transform: translate(100%, 0);
            -o-transform: translate(100%, 0);
            transform: translate(100%, 0);
        }

        /* end .slideTwo */
        /* .slideThree */
        .slideThree {
            width: 130px;
            height: 26px;
            border:1px solid black;
            /*background: #333;*/
            /*margin: 1px auto;*/
            position: relative;
            /*-moz-border-radius: 50px;
            -webkit-border-radius: 50px;
            border-radius: 50px;*/
            -moz-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px rgba(255, 255, 255, 0.2);
            -webkit-box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px rgba(255, 255, 255, 0.2);
            box-shadow: inset 0px 1px 1px rgba(0, 0, 0, 0.5), 0px 1px 0px rgba(255, 255, 255, 0.2);
        }
        .slideThree .title {
            height: 26px;
            border-right: 2px solid black;
            font: 14px/26px Arial, sans-serif;
            font-weight: bold;
            text-shadow: 1px 1px 0px rgba(255, 255, 255, 0.15);
        }
        .slideThree:after {
            content: 'OFF';
            color: #000;
            position: absolute;
            right: 10px;
            z-index: 0;
            font: 12px/26px Arial, sans-serif;
            font-weight: bold;
            text-shadow: 1px 1px 0px rgba(255, 255, 255, 0.15);
        }
        .slideThree:before {
            content: 'ON';
            color: #00bf00;
            position: absolute;
            left: 10px;
            z-index: 0;
            font: 12px/26px Arial, sans-serif;
            font-weight: bold;
        }
        .slideThree label {
            /*display: block;*/
            width: 26px;
            height: 20px;
            /*cursor: pointer;*/
            position: absolute;
            top: 3px;
            /*left: 5px;*/
            z-index: 1;
            background: #fcfff4;
            background: -moz-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
            background: -webkit-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
            background: linear-gradient(to bottom, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
            -moz-border-radius: 50px;
            -webkit-border-radius: 50px;
            border-radius: 50px;
            -moz-transition: all 0.4s ease;
            -o-transition: all 0.4s ease;
            -webkit-transition: all 0.4s ease;
            transition: all 0.4s ease;
            -moz-box-shadow: 0px 2px 5px 0px rgba(0, 0, 0, 0.3);
            -webkit-box-shadow: 0px 2px 5px 0px rgba(0, 0, 0, 0.3);
            box-shadow: 0px 2px 5px 0px rgba(0, 0, 0, 0.3);
        }
        .slideThree_label_open {
            left: 5px;
        }
        .slideThree_label_close {
            left: 45px;
            background:#222
        }
        .slideThree_label_no {
            display: none;
            left: 90px;
        }
        .slideThree_bus_free {
            background: #86de2b
        }
        .slideThree_bus_preused {
            background:#beb6de
        }
        .slideThree_bus_prefree {
            background:#69deb4
        }
        .slideThree_bus_runing {
            background: #c0c25d
        }
        .slideThree_bus_no {
            background: #ff1325
        }
    </STYLE>
    <script>
        Vue.component('swbutton', {
            template: '#swbutton',
            props: {
                obj: {}
            },
            methods: {
                clicksw: function () {
                    try {
                        var time = new Date().getTime();
                        clearTimeout(this.TimeFn);
                        var clicktime = this.clicktime;
                        var myobj = this;
                        var obj = this.obj;
                        this.TimeFn = setTimeout(function () {
                            if (time - clicktime < 500) {//双击
//                                console.log(myobj.status)
//                                if(myobj.status!=2)
//                                    parent.layer.open({
//                                        type: 2,
//                                        area: ['800px', '630px'],
//                                        fixed: false, //不固定
//                                        maxmin: true,
//                                        content: '../odf.html'
//                                    });
                            } else {
                                onclick(myobj, obj)
                            }
                        }, 10);
                        this.clicktime = time;
                    } catch (e) {

                    }
                },
                dbshow: function () {
                    parent.layer.open({

                    })
                }
            },
            data: function () {
                return {
                    clicktime: 0,
                    TimeFn: null
                }
            },
            computed: {
                busClass: function () {
                    var className = null;// "slideThree_label_no"
                    var status = parseInt(this.obj.pwrPwrstatus);
                    switch (status) {
                        case 20:
                            className = 'slideThree_bus_free';
                            break;
                        case 50:
                            className = 'slideThree_bus_preused';
                            break;
                        case 55:
                            className = 'slideThree_bus_prefree';
                            break;
                        case 60:
                            className = 'slideThree_bus_runing';
                            break;
                        case 110:
                            className = 'slideThree_bus_no';
                            break;
                        default:
                            break;
                    }
                    if (className != null)
                        return className;
                },
                statusClass: function () {
                    var className = "slideThree_label_no"
//                    switch (this.status) {
//                        case 0:
//                            className = 'slideThree_label_open';
//                            break;
//                        case 1:
//                            className = 'slideThree_label_close';
//                            break;
//                        default:
//                            break;
//                    }
                    return className;
                }

            }
        });
        //开关分组
        Vue.component('swgroup', {
            template: '#swgroup',
            props: {
                obj: {}
            },
            data:function(){
                return {
                    L1:'L1',
                    L2:'L2',
                }
            }
        });
        Vue.component('swpanel', {
            template: '#swpanel',
            props: {
                swgroups: null,
                panelname: ''

                // busa: 0,
                // busb: 0,
                // statusa: 0,
                // statusb: 0,
                // namea: 0,
                // nameb: 0
            },
            computed: {
            },
            data: function () {
                return {
                    swgroupsdata: this.swgroups.value
                }
            }
        });
        Vue.component('demo', {
            template: '#grid-template',
            props: {
                panels: Array
            },
        });
        Vue.component('sigsw', {
            template: '#sigsw',
            props: {
                singdata: {},
                obj: {}
            },
            computed: {
                classObject: function (prefix) {
                    var classs = '';
                    switch (this.obj.status) {
                        case 0:
                            classs = ''
                            break;
                        case 1:
                            classs = ' left: 36px'
                            break;
                        case 2:
                        default:
                            classs = 'display:none'

                    }
                    return classs;
                },
                busclassObject: function (prefix) {
                    var classs = '';
                    switch (this.obj.busstatus) {
                        case 0:
                            classs = ''
                            break;
                        case 1:
                            classs = 'background:#222'
                            break;
                        case 2:
                            classs = 'background:111'
                            break;
                        default:
                            classs = 'background:#e8a0a0'

                    }
                    return classs;
                }
            },
            data: function () {
                return {
                    datas: [
                        { name: this.obj.order + 'A', status: this.obj.statusA, busstatus: this.obj.busstatusA },
                        { name: this.obj.order + 'B', status: this.obj.statusB, busstatus: this.obj.busstatusB }]
                }
            }

        });
        Vue.component("singlesubcompwitch", {
            template: '<div><div style="float:left">{{obj.name}}</div><div class="slideThree" style="float:left" v-bind:style="busclassObject"><label v-bind:style="classObject"  for="slideThree"></label><span style="width:1px"></span></div></div>',
            props: {
                obj: {}
            },
            computed: {
                classObject: function (prefix) {
                    var classs = '';
                    switch (this.obj.status) {
                        case 0:
                            classs = ''
                            break;
                        case 1:
                            classs = ' left: 36px'
                            break;
                        case 2:
                        default:
                            classs = 'display:none'

                    }
                    return classs;
                },
                busclassObject: function (prefix) {
                    var classs = '';
                    switch (this.obj.busstatus) {
                        case 0:
                            classs = ''
                            break;
                        case 1:
                            classs = 'background:#222'
                            break;
                        case 2:
                            classs = 'background:111'
                            break;
                        default:
                            classs = 'background:#e8a0a0'

                    }
                    return classs;
                }
            },
            data: function () {
                return {}
            }
        });
        Vue.component("subcompwitch", {
            template: '<div><singlesubcompwitch v-for="ite in list" :obj="ite"></singlesubcompwitch></div>',
            props: {
                obj: {}
            },
            data: function () {
                return {
                    list: [
                        { name: this.obj.order + 'A', status: this.obj.statusA, busstatus: this.obj.busstatusA },
                        { name: this.obj.order + 'B', status: this.obj.statusB, busstatus: this.obj.busstatusB }]
                }
            }
        });
        Vue.component("compwitch", {
            template: '<div><subcompwitch v-for="iteam in list" class="switch" v-on:click="doit" :obj="iteam"></subcompwitch></div>',
            props: {
                stauts: 0,
                busstatus: 0,
                obj: {},
                list: null
            },
            computed: {
            },
            methods: {
                doit: function (iteam, e) {
                }
            },
            data: function () {
                return {

                };
            }
        })
    </script>
</head>
<div id="test"
     style="border: 1px solid black;max-width:65%;max-height:90%;overflow-x: auto; white-space: nowrap; overflow-y:auto;float: left">
    <%--<div><button v-on:click="add">添加面板</button>--%>
        <!--<button v-on:click="addpanel">添加面板</button>-->
    <%--</div>--%>
    <div style="border:1px solid black;width:380px;display: inline-block;margin:2px">
        <div style="text-align:center;background:#ccc"><span>${rack.name}</span></div>
        <swgroup v-for="(iteam,index) in switchs" :key="iteam.id" :obj="iteam"></swgroup>
    </div>
    <%--<swpanel style="width:240px;display: inline-block" v-for="(item,index) in panels" :swgroups="item" :panelname="item.name"></swpanel>--%>
</div>
<div id="progrid" style="width:50%;margin-left:2px;float: left;">
    <fieldset>
        <legend><strong>业务状态:</strong></legend>
        <div style="height: 28px;margin-left:10px">
            <div style="float: left; width: 60px;">
                <span class="title" style="line-height: 28px;">可用:</span></div>
            <div class="slideThree slideThree_bus_free" style="float: left;"><label class="slideThree_label_no"></label></div>

        </div>
        <div style="height: 28px;margin-left:10px">
            <div style="float: left; width: 60px;">
                <span class="title" style="line-height: 28px;">预占:</span></div>
            <div class="slideThree slideThree_bus_preused" style="float: left;"><label class="slideThree_label_no"></label></div>

        </div>
        <div style="height: 28px;margin-left:10px">
            <div style="float: left; width: 60px;">
                <span class="title" style="line-height: 28px;">预释:</span></div>
            <div class="slideThree slideThree_bus_prefree" style="float: left;"><label class="slideThree_label_no"></label></div>

        </div>
        <div style="height: 28px;margin-left:10px">
            <div style="float: left; width: 60px;">
                <span class="title" style="line-height: 28px;">在服:</span></div>
            <div class="slideThree slideThree_bus_runing" style="float: left;"><label class="slideThree_label_no"></label></div>

        </div>
        <div style="height: 28px;margin-left:10px">
            <div style="float: left; width: 60px;">
                <span class="title" style="line-height: 28px;">不可用:</span></div>
            <div class="slideThree slideThree_bus_no" style="float: left;"><label class="slideThree_label_no"></label></div>
        </div>
    </fieldset>
    <fieldset>
        <legend><strong>端口信息:</strong></legend>
        <div style="height: 28px;margin-left:10px">
            <div style="float: left; width: 100px;">
                <span class="title" style="line-height: 28px;">PDU名字:</span></div>
            <div style="float: left;">
                {{name}}
            </div>
        </div>
        <div style="height: 28px;margin-left:10px">
            <div style="float: left; width: 100px;">
                <span class="title" style="line-height: 28px;">业务状态:</span></div>
            <div style="float: left;">
                {{busname}}
            </div>
        </div>
        <div style="height: 28px;margin-left:10px">
            <div style="float: left; width: 100px;">
                <span class="title" style="line-height: 28px;">客户名称:</span></div>
            <div style="float: left;">
                {{customername}}
            </div>
        </div>
        <div style="height: 28px;margin-left:10px">
            <div style="float: left; width: 100px;">
                <span class="title" style="line-height: 28px;">服务机架:</span></div>
            <div style="float: left;">
                <a href="javascript:void(0)" onclick="openrack(this)" :rackid="rackid">{{rackname}}</a>
            </div>
        </div>
    </fieldset>
</div>

</body>
<script type="text/x-template" id="swbutton">
    <!--开关-->
    <div  v-on:click="clicksw" >
        <div style="float:left;width:50px"><span style="line-height:28px" class="title">{{obj.sort}}</span></div>
        <diV class="slideThree" :class="busClass" style="float:left"  >
            <label :class="statusClass"></label>
        </div>
    </div>
</script>
<script type="text/x-template" id="swgroup">
    <!--开关组-->
    <div style="margin-top:3px;height:28px;border-top:1px solid black">
        <swbutton style="float:left;"  :obj="obj.L1"></swbutton>
        <swbutton style="float:left;" :obj="obj.L2"></swbutton>
        <%--<swbutton style="float:left;" :bus="bus_A" :status="status_A" :name="name_A" :obj="getobjs" :panelname="panelname"></swbutton>--%>
        <%--<swbutton style="float:left;" :bus="bus_B" :status="status_B" :name="name_B" :obj="getobjs" :panelname="panelname"></swbutton>--%>
    </div>
</script>
<script>
    var mcbs = {};
    var idcMcbs = []
    <c:forEach items="${idcMcbs}" var="mcb">
        idcMcbs.push({
            id:'${mcb.id}',
            name:'${mcb.name}',
            pwrPwrstatus:'${mcb.pwrPwrstatus}',
            sysdescr:'${mcb.sysdescr}',
            pwrInstalledrackId:'${mcb.pwrInstalledrackId}',
            pwrServicerackId:'${mcb.pwrServicerackId}',
            customername:'${mcb.customername}'
        })
    </c:forEach>
    var reg = /(\w*_)+L(\d*)-(\d)/;
    for(var i=0;i<idcMcbs.length;i++){
        var mcbname = idcMcbs[i].name;
        var result = reg.exec(mcbname);
        if(result){
            var ordernum = result[2];
            var orderType = result[3];
            var sing = mcbs['L'+ordernum];
            if(typeof(sing)=='undefined'){
                mcbs['L'+ordernum] = sing = {
                    order:'L'+ordernum
                };
            }
            sing['L'+orderType]= idcMcbs[i]
            sing['L'+orderType]['sort'] = 'L'+ordernum+"-"+orderType;
        }
    }
    var mcblist = [];
    for(var key in mcbs){
        mcblist.push(mcbs[key])
    }
    console.log(mcblist)
    /***
     * 点击的默认回调事件
     * */
    function onclick(e, v) {
        progrid.setPanel(e, v);

    }
    function openrack(obj){
        var id = $(obj).attr('rackid');
        var url = contextPath + "/idcRack/idcRackDetails.do?rackId=" + id + "&businesstype=other&buttonType=view";
        openDialogShowView2d('服务机架', url, '800px', '530px', '查看机架视图');
        //openDialogView("服务机架",contextPath+"/idcRack/view/equipment/"+id,"800px","300px");
    }
    var progrid = new Vue({
        el: '#progrid',
        data: {
            rackid: '',
            name: '',
            rackname: '',
            busname: '',
            customername : '',
        },
        methods: {
            setPanel: function (e, value) {
                this.name = value.name;
                this.rackid = value.pwrServicerackId;
                var rackname = this.rackname;
                $.getJSON(contextPath+"/idcRack/getIdcRackInfo",{id:this.rackid},function(data){
                    progrid.rackname = data.NAME;
                });
                this.customername = value.customername;
                var status = parseInt(value.pwrPwrstatus);
                switch (status) {
                    case 20:
                        this.busname = '可用'
                        break;
                    case 50:
                        this.busname = '预占'
                        break;
                    case 55:
                        this.busname = '预释'
                        break;
                    case 60:
                        this.busname = '在服'
                        break;
                    case 110:
                        this.busname = '不可用'
                        break;
                    default:
                        this.busname = '可用'
                        break;
                }

            }
        }
    });
    var Root = new Vue({
        el: '#test',
        methods: {
            add: function () {
            },
            deletefn: function () {
                if (this.deleteindex > 0) {
                    this.panels.splice(this.deleteindex, 1);
                }
            }
        },
        data: {
            deleteindex: 0,
            switchs:function(){
                return mcblist;
            }()
        },
    })

</script>

</html>