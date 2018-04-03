<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script src="<%=request.getContextPath() %>/framework/vuejs/vue.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath() %>/framework/vuejs/vuex.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath() %>/js/racklayout/js/comp.js"></script>
    <link rel="Stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/racklayout/css/style.css"/>
</head>
<style type="text/css">
    .availableSelected {
        border-bottom: 2px solid black;
    }
</style>
<body style="width:100%; margin:0px auto;">
<div id="test" style="padding:10px">
    <div style="width:500px; float:left;">
        <jijia :uuid="1"></jijia>
        <jiwei></jiwei>
    </div>
    <div style="border: 1px solid black; margin-left:10px;float: left">
        <equipment v-bind:datas="$store.state.datas"></equipment>
    </div>

    <div style="width:300px; float:left;">
        <fieldset>
            <legend><strong>机位图例:</strong></legend>
            <div style="height: 28px;margin-left:10px">
                <div style="float: left; width: 120px;;">
                    <span class="title" style="line-height: 18px;">可用:</span></div>
                <div class="legend available" style="float: left;"></div>


            </div>
            <div style="height: 28px;margin-left:10px">
                <div style="float: left; width: 120px;;">
                    <span class="title" style="line-height: 18px;">在服:</span></div>
                <div class="legend inservice" style="float: left;"><label class="slideThree_label_no"></label></div>

            </div>
            <div style="height: 28px;margin-left:10px">
                <div style="float: left; width: 120px;;">
                    <span class="title" style="line-height: 18px;">占用:</span></div>
                <div class=" legend occupied" style="float: left;"><label class="slideThree_label_no"></label></div>

            </div>
            <div style="height: 28px;margin-left:10px">
                <div style="float: left; width: 120px;;">
                    <span class="title" style="line-height: 18px;">预占:</span></div>
                <div class="legend reserviced" style="float: left;"><label class="slideThree_label_no"></label></div>

            </div>
            <div style="height: 28px;margin-left:10px">
                <div style="float: left; width: 120px;;">
                    <span class="title" style="line-height: 18px;">不可用:</span></div>
                <div class="legend notavailable" style="float: left;"><label class="slideThree_label_no"></label></div>
            </div>
        </fieldset>
        <!--<div style="background: gray;text-align: center;margin:5px">机位图例:</div>-->
        <div style="margin:5px">
            <%--<button v-on:click="create">生成机位</button>--%>
            <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_online','ROLE_admin')">
                <button v-on:click="add">上架设备</button>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_down','ROLE_admin')">
                <button v-on:click="deleteeq">下架设备</button>
            </sec:authorize>
            <button v-on:click="rtree">查看资源树</button>
            <!--<button v-on:click="deleteeq">资源树配置</button>-->
        </div>
    </div>
</div>
</body>
<script type="text/x-template" id="equipment">
    <!--机架-->
    <div>
        <div style="min-width:350px;max-width:450px;" class="jijia">
            <div style="width:60px; float:left; background-image:url(<%=request.getContextPath() %>/js/racklayout/css/left.png);background-repeat:repeat-y"></div>
            <div style="width:60px; float:right; background-image:url(<%=request.getContextPath() %>/js/racklayout/css/right.png);background-repeat:repeat-y"></div>
            <div class="jijiadiv" style="margin-left:60px;margin-right:60px;position: relative;">
                <sigjiwei v-for="(iteam,i) in serdata(datas)" :obj="iteam" :index="i"
                          :style="{bottom:i*10+'px',height:12+'px'}" :uid="i+1"
                          :class="getClass(iteam.status)">
                </sigjiwei>
            </div>
        </div>
        <div style="min-width:350px;max-width:450px">
            <div style="width:100px; float:left;height:40px; background-image:url(<%=request.getContextPath() %>/js/racklayout/css/buttom.png);    background-repeat: no-repeat;"></div>
            <div style="width:100px; float:right; height:40px; background-image:url(<%=request.getContextPath() %>/js/racklayout/css/buttom.png);    background-repeat: no-repeat;"></div>
            <div style="margin-left:60px;margin-right:60px;height:40px; background-image:url(<%=request.getContextPath() %>/js/racklayout/css/bottom_center.png); background-repeat: repeat-x;"></div>
        </div>
    </div>

</script>
<!--单个机位-->
<script type="text/x-template" id="sigjiwei">
    <div v-on:click="show(obj,index,$event)">
        <div style="float:left;width:29px;font-size: 10px;left:0px;"> {{index+1}}</div>
        <%--<div style="float:left;left:30px;right:150px;">&nbsp;</div>--%>
        <div style="float:right;width:200px;right:0px;" :style="showimg">&nbsp;</div>
    </div>
</script>
<script type="text/x-template" id="jiwei">
    <div class="jiwei">
        <fieldset>
            <legend><strong>机位信息:</strong></legend>
            <div class="jiweipro">
                <div class="title">
                    <span>机位:</span></div>
                <div style="height: 28px;margin-left:10px">
                    {{upos}}
                </div>
            </div>
            <div class="jiweipro">
                <div class="title">
                    <span>状态:</span></div>
                <div>
                    {{statusfmt}}
                </div>
            </div>
            <div class="jiweipro">
                <div class="title">
                    <span>业务:</span></div>
                <div>
                    {{busfmt}}
                </div>
            </div>
            <div class="jiweipro">

                <div class="title">
                    <span>客户名称:</span></div>
                <div>
                    {{customername}}
                </div>
            </div>
            <%--<div class="jiweipro">--%>
            <%--<div class="title">--%>
            <%--<span>客户编号:</span></div>--%>
            <%--<div>--%>
            <%--{{customerid}}--%>
            <%--</div>--%>
            <%--</div>--%>
            <div class="jiweipro">
                <div class="title">
                    <span>设备名称:</span></div>
                <div>
                    {{equipmentname}}
                </div>
            </div>
            <div class="jiweipro">
                <div class="title">
                    <span>设备子类型:</span></div>
                <div>
                    {{submodel}}
                </div>
            </div>
            <div class="jiweipro">
                <div class="title">
                    <span>占用U位:</span></div>
                <div>
                    {{useunum}}
                </div>
            </div>
        </fieldset>
    </div>
</script>
<script type="text/x-template" id="jijia">
    <fieldset>
        <legend><strong>机架信息:</strong></legend>
        <!--<div style="background: rgb(252,98,15);;text-align: center;margin:1px">机架信息:</div>-->
        <div class="jiweipro">
            <div class="title">
                <span>机架名称:</span>
            </div>
            <div>
                {{name}}
            </div>

        </div>
        <div class="jiweipro">
            <div class="title">
                <span>机架型号:</span></div>
            <div>
                {{modelstr}}
            </div>

        </div>
        <div class="jiweipro">
            <div class="title">
                <span>机架编码:</span></div>
            <div>
                {{code}}
            </div>

        </div>
        <div class="jiweipro">
            <div class="title">
                <span>业务状态:</span></div>
            <div>
                {{statusfmt}}
            </div>

        </div>
        <%--<div class="jiweipro">--%>
        <%--<div class="title">--%>
        <%--<span>IP总数:</span></div>--%>
        <%--<div>--%>
        <%--{{iptotal}}--%>
        <%--</div>--%>
        <%--</div>--%>
        <div class="jiweipro">
            <div class="title">
                <span>IDC编号:</span></div>
            <div>
                {{idccode}}
            </div>
        </div>
        <div class="jiweipro">
            <div class="title">
                <span>出租类型:</span></div>
            <div>
                {{borrowedtype}}
            </div>
        </div>
        <div class="jiweipro">
            <div class="title">
                <span>客户名称:</span></div>
            <div>
                {{customername}}
            </div>
        </div>
        <div class="jiweipro">
            <div class="title">
                <span>额定带宽:</span></div>
            <div>
                {{bandwidth}}
            </div>
        </div>
        <div class="jiweipro">
            <div class="title">
                <span>额定电量:</span></div>
            <div>
                {{electricity}}
            </div>
        </div>
    </fieldset>

</script>
<script type="text/x-template" id="mymenu">
    <div class="_context-menu" v-show="show">
        <ul>
            <li>1
            </li>
            <li>1
            </li>
            <li>1
            </li>
        </ul>
    </div>
</script>
<script>
    //最大U位数
    var rackid = "${rack.id}";
    var businesstype = "${rack.businesstypeId}";
    var maxunum = '${rackunitsize}' || 42;
    var database = [];
    <c:if test="${rackunits!=null}">
    <c:forEach var="rackunit" items="${rackunits}">
    database.push({
        usercode: '${rackunit.USERNAME}',
        upos: '${rackunit.UNO}' || 0,
        index: '${rackunit.DEVICEID}' || 0,
        username: '',
        status: '${rackunit.STATUS}',
        equipmentname: '${rackunit.NAME}',
        startu: '${rackunit.UNO}' || 0,
        useunum: 1,
        customerid: '${rackunit.CUSTOMERID}',
        customername: '${rackunit.CUSTOMERNAME}'
    });
    </c:forEach>
    </c:if>
    function add(obj){
        if(check( vuex_store.state.selectNode,obj.UHEIGHT)){
            $.post(contextPath+"/racklayout/add",
                {
                    startu: vuex_store.state.selectNode,
                    uheight: obj.UHEIGHT,
                    rackid: rackid,
                    deviceid: obj.DEVICEID
                },
                function (data) {
                    if(data=="ok"){
                        all.update({
                            usercode: '',
                            index: obj.DEVICEID,
                            upos: '0',
                            username: '',
                            status: 60,
                            equipmentname: obj.NAME,
                            startu: vuex_store.state.selectNode + 1,
                            useunum: obj.UHEIGHT,
                        })
                        top.layer.closeAll();
                    }
                })
        }

    }
    function check(start,max){
        if (parseInt(start)+parseInt(max)> maxunum) {
            alert('当前机位数不够')
            return false;
        }
        for (var i =parseInt(start); i < parseInt(start)+parseInt(max); i++) {
            //先校验数据
            if (vuex_store.state.datas[i].status == '60') {
                alert('当前机位数不够')
                return false;
            }
        }
        return true;
    }
    const vuex_store = new Vuex.Store({
        state: {
            jiweidata: {
                upos: '',
                status: 0,
                username: '标准机柜',
                usercode: '',
                equipmentname: '',
                submodel: '',
                startu: 1,
                useunum: 1,
                customername: '',
                customerid: '',
            },
            jijiadata: {
                name: '${rack.name}',
                model: '${rack.businesstypeId}',
                code: '${rack.code}',
                status: '${rack.status}',
                username: '',
                <%--iptotal: '${rack.iptotal}',--%>
                idccode: '${rack.idcno}',
                customerid: '${rack.actualcustomerid}',
                customername: '${rack.actualcustomer}',
                borrowedtype: "${rack.renttype=='0'?'整架出租':(rack.renttype=='1'?'按机位出租':'')}",
                bandwidth: '${idcinternetexport.bandwidth}',
                electricity: '${rack.ratedelectricenergy!=null?rack.ratedelectricenergy:0}KWH',
            },
            selectNode: 1,
            datas: function () {//初始化机位
                var datas = [];
                for (var i = 0; i < maxunum; i++) {
                    datas.push(
                        {
                            usercode: '',
                            index: i,
                            upos: 0,
                            username: '',
                            status: 0,
                            key: '',
                            equipmentname: '',
                            startu: i + 1,
                            useunum: 1
                        }
                    )
                }
                return datas;
            }(),

        },
        actions: {
            showjiwei: function (e, obj) {
                for (var k in e.state.jiweidata) {
                    if (obj[k] != undefined) {
                        e.state.jiweidata[k] = obj[k];
                    } else {
                        e.state.jiweidata[k] = ''
                    }
                }
                e.state.jiweidata.upos = obj.index + 1;
                e.state.selectNode = obj.index;
                e.state.jiweidata.customername = obj.customername;
            },
            createjiwei: function () {
            }
        }
    })

    var bus = new Vue();
    var all = new Vue({
        el: '#test',
        bus: bus,
        store: vuex_store,
        mounted: function () {
            for (var i = 0; i < database.length; i++) {
                if (database[i].status != 20) {
                    this.update(database[i])
                }
            }
        },
        methods: {
            rtree: function () {
                mylayer.open({
                    type: 2,
                    area: ['1200px', '600px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: contextPath + "/idclink/rack/tree/"+rackid
                });
            },
            deleteeq: function () {
                var selectNode = vuex_store.state.selectNode;
                var node = vuex_store.state.datas[selectNode];
                if(node==null||node.key==''){
                    mylayer.msg("没有选择下线设备");
                    return ;
                }
                console.log('-----删除的key------' + node.key);
                var deviceid =  node.key.split("_")[1];
                $.get(contextPath+"/racklayout/down/"+rackid+"/"+deviceid,function(data){
                    if(data!='ok'){
                        mylayer.msg("下线设备出了问题");
                        return ;
                    }
                    for (var i = 0; i < vuex_store.state.datas.length; i++) {
                        if (vuex_store.state.datas[i].key == node.key&&node.key!='') {
                            console.log('-----下架设备索引------' + i);
                            Vue.set(vuex_store.state.datas, i, {
                                usercode: '',
                                index: i,
                                upos: 0,
                                username: '',
                                status: 0,
                                key: '',
                                equipmentname: '',
                                startu: i + 1,
                                useunum: 1
                            })
                        }
                    }
                })
            },
            update: function (obj) {
                var startu = (parseInt(obj.startu) || 1) - 1;
                var useunum = parseInt(obj.useunum);
                var last =startu+useunum;
                if (last> maxunum) {
                    alert('当前机位数不够')
                    return;
                }
                for (var i =startu; i < last; i++) {
                    //先校验数据
                    if (vuex_store.state.datas[i].status == '60') {
                        alert('当前机位数不够')
                        return;
                    }
                }
                var key = 'key_'+obj.index;// + (new Date().getTime())+"_"+Math.round(Math.random() * 500 + 1);//分组
                obj.key = key;
                for (var i = startu; i < last; i++) {
                    Vue.set(vuex_store.state.datas, i, obj)
                }
            },
            add: function () {
                var selectNode = vuex_store.state.selectNode;
                if (vuex_store.state.datas[selectNode].status == '60') {
                    alert('当前机位不是空闲')
                    return;
                }
                //console.log(contextPath+'/racklayout/devicelist/'+rackid)
                var device = '${device}';
                if (device != null && device != '') {
                    device = eval('(' + device + ')');
                    console.log(device);
                    add(device);
                } else {
                    if (businesstype != 'equipment' && businesstype != 'cabinet') {
                        alert("此机架不是客户架或网络架，不能上架设备");
                        return;
                    }
                    openDialog("选择上架设备", contextPath + '/racklayout/devicelist/' + rackid, "800px", "530px")
                }
            },
            create: function () {
                if (vuex_store.state.datas.length > 0) {
                    alert('已经生成机位')
                    return;
                }
                var datas = vuex_store.state.datas;
                for (var i = 0; i < maxunum; i++) {
                    datas.push(
                        {
                            usercode: '',
                            index: i,
                            upos: 0,
                            username: '',
                            status: 0,
                            key: '',
                            equipmentname: '',
                            startu: i + 1,
                            useunum: 1
                        })
                }
            }
        },
        data: {},
    })
</script>

</html>