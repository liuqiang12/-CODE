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
        /*.conn {*/
        /*width: 800px;*/
        /*}*/
        .conn {
            width: 26px;
            height: 26px;
            margin: 15px 0 0px 5px;
            border: 0;
            /*padding: 2px;*/
            border-radius: 26px;
            background: rgba(72, 72, 78, 0.3);
            cursor: pointer;
            float: left;
            text-align: center;
            position: relative;
        }

        .connMin {
            width: 8px;
            height: 8px;
            border: 0;
            /*padding: 2px;*/
            border-radius: 8px;
            background: #A1A1A1;
            position: absolute;
            top: 50%;
            left: 50%;
            margin-left: -4px;
            margin-top: -4px;
        }
        .conn_20{
            background: #86de2b;
        }
        .conn_30{
            background: #69deb4;
        }
        .conn_50{
            background: #beb6de;
        }
        .conn_55{
            background: #de6363;
        }
        .conn_60{
            background: #21c252;
        }
        .legend {
            list-style: none;
            margin: 1em 0 0 0;
            padding: 0;
            text-align: center;
        }

        .legend__item {
            font-size: 0.85em;
            font-weight: bold;
            margin: 0 9px 0 0;
            display: inline-block;
        }
        .legend__item::before {
            content: '';
            width: 34px;
            height: 34px;
            display: inline-block;
            margin: 0 5px 0 0;
            border-radius: 34px;
            background: rgba(72, 72, 78, 0.5);
        }

        .legend__item--cannotuse::before {
            background: #272424;
        }
        .legend__item--canuse::before {
            background: #86de2b;
        }
        .legend__item--perfree::before {
            background: #69deb4;
        }
        .legend__item--free::before {
            background: #92ded1;
        }
        .legend__item--preused::before {
            background: #beb6de;
        }
        .legend__item--stop::before {
            background: #de6363;
        }
        .legend__item--runing::before {
            background: #21c252;
        }
        .legend__item--reserved::before {
            background: #de6363;
        }
        .legend__item--selected::before,
        .rows--mini .row__seat--selected,
        .rows--mini .row__seat--selected:hover {
            background: #21c252;
        }

        .tipso_bubble, .tipso_bubble > .tipso_arrow {
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box
        }

        .tipso_bubble {
            position: absolute;
            text-align: center;
            border-radius: 6px;
            z-index: 9999;
            padding: 10px
        }

        <%--.tipso_style{border-bottom:1px dotted}--%>.tipso_bubble > .tipso_arrow {
            position: absolute;
            width: 0;
            height: 0;
            border: 8px solid;
            pointer-events: none
        }

        .tipso_bubble.top > .tipso_arrow {
            border-color: #000 transparent transparent;
            top: 100%;
            left: 50%;
            margin-left: -8px
        }

        .tipso_bubble.bottom > .tipso_arrow {
            border-color: transparent transparent #000;
            bottom: 100%;
            left: 50%;
            margin-left: -8px
        }

        .tipso_bubble.left > .tipso_arrow {
            border-color: transparent transparent transparent #000;
            top: 50%;
            left: 100%;
            margin-top: -8px
        }

        .tipso_bubble.right > .tipso_arrow {
            border-color: transparent #000 transparent transparent;
            top: 50%;
            right: 100%;
            margin-top: -8px
        }
    </STYLE>
</head>

<body class="easyui-layout">
<div  region="north" split="false" border="false" class="titleTool" style="background: #f1f1f1;">
    <span style="font-size: 20px;color: black">当前ODF：${rack.name}</span>
</div>
<div region="south" split="false" border="false" class="titleTool" style="height: 50px">
    <ul class="legend">
        <li class="legend__item legend__item--canuse">可用</li>
        <li class="legend__item legend__item--perfree">预留</li>
        <li class="legend__item legend__item--preused">预占</li>
        <li class="legend__item legend__item--stop">已停机</li>
        <li class="legend__item legend__item--runing">在服</li>
    </ul>
</div>
<div region="east" split="true" style="width: 450px;background: #f1f1f1;">
    <div class="easyui-layout" fit="true">
        <%--<div data-options="region:'south'" style="height:250px">--%>
        <%--<div id="piediv"></div>--%>
        <%--</div>--%>
        <div border="false" data-options="region:'center',title:'',iconCls:'icon-ok'">
            <table id="pg" class="easyui-propertygrid" fit="true" border="false"
                   data-options="showGroup:true,scrollbarSize:0"></table>
        </div>

    </div>
</div>
<div region="center" split="true"  class="history">
    <c:forEach var="conn" items="${conns}">
        <div class="conn conn_${conn.status}" data-tipso="${conn.name}" memo="${conn.memo}" id="${conn.id}" status="${conn.status}" nick="${conn.nickname}" he="${conn.boxIdx}" pan="${conn.discIdx}" kou="${conn.innisIdx}">
            <div class="connMin"></div>
        </div>
    </c:forEach>
</div>
</body>
<script type="text/javascript">

    !function(t,o,s,e){function r(o,s){this.element=t(o),this.settings=t.extend({},l,s),this._defaults=l,this._name=d,this._title=this.element.attr("title"),this.mode="hide",this.init()}function i(){var t=o.navigator.msMaxTouchPoints,e="ontouchstart"in s.createElement("div");return t||e?!0:!1}function n(o){var s=o.clone();s.css("visibility","hidden"),t("body").append(s);var e=s.outerHeight();return s.remove(),e}function a(s){var e,r,i,a=s.tooltip(),d=s.element,l=s,f=t(o),p=10;switch(l.settings.position){case"top":r=d.offset().left+d.outerWidth()/2-a.outerWidth()/2,e=d.offset().top-n(a)-p,a.find(".tipso_arrow").css({marginLeft:-8}),e<f.scrollTop()?(e=d.offset().top+d.outerHeight()+p,a.find(".tipso_arrow").css({"border-bottom-color":l.settings.background,"border-top-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass("bottom")):(a.find(".tipso_arrow").css({"border-top-color":l.settings.background,"border-bottom-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass("top"));break;case"bottom":r=d.offset().left+d.outerWidth()/2-a.outerWidth()/2,e=d.offset().top+d.outerHeight()+p,a.find(".tipso_arrow").css({marginLeft:-8}),e+n(a)>f.scrollTop()+f.outerHeight()?(e=d.offset().top-n(a)-p,a.find(".tipso_arrow").css({"border-top-color":l.settings.background,"border-bottom-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass("top")):(a.find(".tipso_arrow").css({"border-bottom-color":l.settings.background,"border-top-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass(l.settings.position));break;case"left":r=d.offset().left-a.outerWidth()-p,e=d.offset().top+d.outerHeight()/2-n(a)/2,a.find(".tipso_arrow").css({marginTop:-8,marginLeft:""}),r<f.scrollLeft()?(r=d.offset().left+d.outerWidth()+p,a.find(".tipso_arrow").css({"border-right-color":l.settings.background,"border-left-color":"transparent","border-top-color":"transparent","border-bottom-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass("right")):(a.find(".tipso_arrow").css({"border-left-color":l.settings.background,"border-right-color":"transparent","border-top-color":"transparent","border-bottom-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass(l.settings.position));break;case"right":r=d.offset().left+d.outerWidth()+p,e=d.offset().top+d.outerHeight()/2-n(a)/2,a.find(".tipso_arrow").css({marginTop:-8,marginLeft:""}),r+p+l.settings.width>f.scrollLeft()+f.outerWidth()?(r=d.offset().left-a.outerWidth()-p,a.find(".tipso_arrow").css({"border-left-color":l.settings.background,"border-right-color":"transparent","border-top-color":"transparent","border-bottom-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass("left")):(a.find(".tipso_arrow").css({"border-right-color":l.settings.background,"border-left-color":"transparent","border-top-color":"transparent","border-bottom-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass(l.settings.position))}r<f.scrollLeft()&&("bottom"==l.settings.position||"top"==l.settings.position)&&(a.find(".tipso_arrow").css({marginLeft:r-8}),r=0),r+l.settings.width>f.outerWidth()&&("bottom"==l.settings.position||"top"==l.settings.position)&&(i=f.outerWidth()-(r+l.settings.width),a.find(".tipso_arrow").css({marginLeft:-i-8,marginTop:""}),r+=i),r<f.scrollLeft()&&("left"==l.settings.position||"right"==l.settings.position)&&(r=d.offset().left+d.outerWidth()/2-a.outerWidth()/2,a.find(".tipso_arrow").css({marginLeft:-8,marginTop:""}),e=d.offset().top-n(a)-p,e<f.scrollTop()?(e=d.offset().top+d.outerHeight()+p,a.find(".tipso_arrow").css({"border-bottom-color":l.settings.background,"border-top-color":"transparent","border-left-color":"transparent","border-right-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass("bottom")):(a.find(".tipso_arrow").css({"border-top-color":l.settings.background,"border-bottom-color":"transparent","border-left-color":"transparent","border-right-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass("top")),r+l.settings.width>f.outerWidth()&&(i=f.outerWidth()-(r+l.settings.width),a.find(".tipso_arrow").css({marginLeft:-i-8,marginTop:""}),r+=i),r<f.scrollLeft()&&(a.find(".tipso_arrow").css({marginLeft:r-8}),r=0)),r+l.settings.width>f.outerWidth()&&("left"==l.settings.position||"right"==l.settings.position)&&(r=d.offset().left+d.outerWidth()/2-a.outerWidth()/2,a.find(".tipso_arrow").css({marginLeft:-8,marginTop:""}),e=d.offset().top-n(a)-p,e<f.scrollTop()?(e=d.offset().top+d.outerHeight()+p,a.find(".tipso_arrow").css({"border-bottom-color":l.settings.background,"border-top-color":"transparent","border-left-color":"transparent","border-right-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass("bottom")):(a.find(".tipso_arrow").css({"border-top-color":l.settings.background,"border-bottom-color":"transparent","border-left-color":"transparent","border-right-color":"transparent"}),a.removeClass("top bottom left right"),a.addClass("top")),r+l.settings.width>f.outerWidth()&&(i=f.outerWidth()-(r+l.settings.width),a.find(".tipso_arrow").css({marginLeft:-i-8,marginTop:""}),r+=i),r<f.scrollLeft()&&(a.find(".tipso_arrow").css({marginLeft:r-8}),r=0)),a.css({left:r+l.settings.offsetX,top:e+l.settings.offsetY})}var d="tipso",l={speed:400,background:"#55b555",color:"#ffffff",position:"top",width:400,delay:200,offsetX:0,offsetY:0,content:null,ajaxContentUrl:null,useTitle:!0,onBeforeShow:null,onShow:null,onHide:null};t.extend(r.prototype,{init:function(){var o=this,e=this.element;e.addClass("tipso_style").removeAttr("title"),i()?(e.on("click."+d,function(t){"hide"==o.mode?o.show():o.hide(),t.stopPropagation()}),t(s).on("click",function(){"show"==o.mode&&o.hide()})):(e.on("mouseover."+d,function(){o.show()}),e.on("mouseout."+d,function(){o.hide()}))},tooltip:function(){return this.tipso_bubble||(this.tipso_bubble=t('<div class="tipso_bubble"><div class="tipso_content"></div><div class="tipso_arrow"></div></div>')),this.tipso_bubble},show:function(){var s=this.tooltip(),e=this,r=t(o);t.isFunction(e.settings.onBeforeShow)&&e.settings.onBeforeShow(t(this)),s.css({background:e.settings.background,color:e.settings.color,width:e.settings.width}).hide(),s.find(".tipso_content").html(e.content()),a(e),r.resize(function(){a(e)}),e.timeout=o.setTimeout(function(){s.appendTo("body").stop(!0,!0).fadeIn(e.settings.speed,function(){e.mode="show",t.isFunction(e.settings.onShow)&&e.settings.onShow(t(this))})},e.settings.delay)},hide:function(){var s=this,e=this.tooltip();o.clearTimeout(s.timeout),s.timeout=null,e.stop(!0,!0).fadeOut(s.settings.speed,function(){t(this).remove(),t.isFunction(s.settings.onHide)&&"show"==s.mode&&s.settings.onHide(t(this)),s.mode="hide"})},destroy:function(){var t=this.element;t.off("."+d),t.removeData(d),t.removeClass("tipso_style").attr("title",this._title)},content:function(){var o,s=this.element,e=this,r=this._title;return o=e.settings.ajaxContentUrl?t.ajax({type:"GET",url:e.settings.ajaxContentUrl,async:!1}).responseText:e.settings.content?e.settings.content:e.settings.useTitle===!0?r:s.data("tipso")},update:function(t,o){var s=this;return o?void(s.settings[t]=o):s.settings[t]}}),t[d]=t.fn[d]=function(o){var s=arguments;if(o===e||"object"==typeof o)return this instanceof t||t.extend(l,o),this.each(function(){t.data(this,"plugin_"+d)||t.data(this,"plugin_"+d,new r(this,o))});if("string"==typeof o&&"_"!==o[0]&&"init"!==o){var i;return this.each(function(){var e=t.data(this,"plugin_"+d);e||(e=t.data(this,"plugin_"+d,new r(this,o))),e instanceof r&&"function"==typeof e[o]&&(i=e[o].apply(e,Array.prototype.slice.call(s,1))),"destroy"===o&&t.data(this,"plugin_"+d,null)}),i!==e?i:this}}}(jQuery,window,document);
    var action = "${action}"
    var roomid = "${roomid}";
    $(function(){
        $('.conn').tipso({ useTitle: false});
        $('#pg').propertygrid({
            showGroup: true,
            title: 'ODF',
            columns: [[
                {field: 'name', title: '名字', width: 120, sortable: false},
                {field: 'value', title: '值', width: 400, resizable: false}
            ]],
            data: [[]],
            scrollbarSize: 0
        });
        $('.conn').click(function(e){
            console.log(e.target);
            var mydata = [{
                name: '端子名字',
                value: $(this).attr("data-tipso"),
                group: '端子信息',
            }, {
                name: '所属机架',
                value: '<a href="javascript:void(0)" onclick="openrack(${rack.id},\'${rack.businesstypeId}\')">${rack.name}</a>',
                group: '端子信息',
            }, {
                name: '状态',
                value: getStatus($(this).attr("status")),
                group: '端子信息',
            },{
                name: '昵称',
                value: getNickName($(this).attr("he"),$(this).attr("pan"),$(this).attr("kou")),
                group: '端子信息',
            }, {
                name: '备注',
                value: $(this).attr("memo"),
                group: '端子信息',
            }];
            //单击端子是获取其Z端信息
            var connectorId = $(this).attr("id");
            $.post(contextPath + "/idclink/queryUpAndDownPortInfo", {id: connectorId}, function (result) {
                console.log(result);
                //上行端口信息
                if (result != null && result.upPort != null && result.upPort.type == 'netPort') {
                    mydata.push({
                        name: '对端端口名称',
                        value: '<a href="javascript:void(0)" onclick="openport(' + result.upPort.obj.AENDPORTID + ',\'' + result.upPort.type + '\')">' + result.upPort.obj.APORTNAME==null||typeof(result.upPort.obj.APORTNAME) == 'undefined'?null:result.upPort.obj.APORTNAME + '</a>',
                        group: '上行端口信息',
                    }, {
                        name: '对端设备名称',
                        value: '<a href="javascript:void(0)" onclick="opendevice(' + result.upPort.obj.AENDDEVICEID + ')">' + result.upPort.obj.ADEVICENAME == null || typeof(result.upPort.obj.ADEVICENAME) == 'undefined' ? null : result.upPort.obj.ADEVICENAME + '</a>',
                        group: '上行端口信息',
                    }, {
                        name: '对端机架名称',
                        value: '<a href="javascript:void(0)" onclick="openrack(' + result.upPort.obj.AENDRACKID + ',\'' + result.upPort.obj.ABUSINESSTYPE + '\')">' + result.upPort.obj.ARACKNAME + '</a>',
                        group: '上行端口信息',
                    })
                } else if (result != null && result.upPort != null && result.upPort.type == 'idcConnector') {
                    mydata.push({
                        name: '对端端子名称',
                        value: '<a href="javascript:void(0)" onclick="openport(' + result.upPort.obj.AENDPORTID + ',\'' + result.upPort.type + '\')">' + result.upPort.obj.APORTNAME==null||typeof(result.upPort.obj.APORTNAME) == 'undefined'?null:result.upPort.obj.APORTNAME + '</a>',
                        group: '上行端口信息',
                    }, {
                        name: '对端机架名称',
                        value: '<a href="javascript:void(0)" onclick="openrack(' + result.upPort.obj.AENDRACKID + ',\'' + result.upPort.obj.ABUSINESSTYPE + '\')">' + result.upPort.obj.ARACKNAME + '</a>',
                        group: '上行端口信息',
                    })
                }
                //下行端口信息
                if (result != null && result.downPort != null && result.downPort.type == 'netPort') {
                    mydata.push({
                        name: '对端端口名称',
                        value: '<a href="javascript:void(0)" onclick="openport(' + result.downPort.obj.ZENDPORTID + ',\'' + result.downPort.type + '\')">' + result.downPort.obj.ZPORTNAME==null||typeof(result.downPort.obj.ZPORTNAME) == 'undefined'?null:result.downPort.obj.ZPORTNAME + '</a>',
                        group: '下行端口信息',
                    }, {
                        name: '对端设备名称',
                        value: '<a href="javascript:void(0)" onclick="opendevice(' + result.downPort.obj.ZENDDEVICEID + ')">' + result.downPort.obj.ZDEVICENAME == null || typeof(result.downPort.obj.ZDEVICENAME) == 'undefined' ? null : result.downPort.obj.ZDEVICENAME + '</a>',
                        group: '下行端口信息',
                    }, {
                        name: '对端机架名称',
                        value: '<a href="javascript:void(0)" onclick="openrack(' + result.downPort.obj.ZENDRACKID + ',\'' + result.downPort.obj.ZBUSINESSTYPE + '\')">' + result.downPort.obj.ZRACKNAME + '</a>',
                        group: '下行端口信息',
                    })
                } else if (result != null && result.downPort != null && result.downPort.type == 'idcConnector') {
                    mydata.push({
                        name: '对端端子名称',
                        value: '<a href="javascript:void(0)" onclick="openport(' + result.downPort.obj.ZENDPORTID + ',\'' + result.downPort.type + '\')">' + result.downPort.obj.ZPORTNAME==null||typeof(result.downPort.obj.ZPORTNAME) == 'undefined'?null:result.downPort.obj.ZPORTNAME + '</a>',
                        group: '下行端口信息',
                    }, {
                        name: '对端机架名称',
                        value: '<a href="javascript:void(0)" onclick="openrack(' + result.downPort.obj.ZENDRACKID + ',\'' + result.downPort.obj.ZBUSINESSTYPE + '\')">' + result.downPort.obj.ZRACKNAME + '</a>',
                        group: '下行端口信息',
                    })
                }
                $('#pg').propertygrid('loadData', mydata);
            });
        });
    })
    //查看机架信息
    function openrack(rackid, type) {
        if (type == 'df') {
            openDialogShowView2d('查看ODF信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + rackid + '&businesstype=odf&buttonType=view', '800px', '400px', '查看机架视图');
        } else if (type == 'pdu') {
            openDialogShowView2d('查看PDF信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + rackid + '&businesstype=pdu&buttonType=view', '800px', '400px', '查看机架视图');
        } else {
            openDialogShowView2d('查看机架信息', contextPath + '/idcRack/idcRackDetails.do?rackId=' + rackid + '&businesstype=other&buttonType=view', '800px', '430px', '查看机架视图');
        }
    }
    //查看设备信息
    function opendevice(deviceid) {
        var url = contextPath + "/device/deviceDetails.do?id=" + deviceid + "&buttonType=view&deviceclass=1";
        openDialogView('设备信息', url, '800px', '530px');
    }
    //查看Z端信息
    function openport(portId, type) {
        if (type == 'netPort') {
            var url = contextPath + "/netport/netportDetails.do?id=" + portId + "&buttonType=view";
            openDialogView('端口信息', url, '800px', '530px');
        } else {
            var url = contextPath + "/idcConnector/getIdcConnectorInfo.do?id=" + portId + "&buttonType=view";
            openDialogView('端子信息', url, '800px', '330px');
        }
    }
    function getStatus(status){
        if(status==20){
            return '可用'
        }
        if(status==30){
            return '预留'
        }
        if(status==50){
            return '预占'
        }
        if(status==55){
            return '停机'
        }
        if(status==60){
            return '在服'
        }
        return ''
    }
    function getNickName(he,pan,kou){
        if(he!=null && he>0){
            return he+"盒"+pan+"盘"+kou+"口";
        }else{
            return pan+"盘"+kou+"口";
        }
    }
</script>
</html>