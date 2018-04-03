/**
 * Created by mylove on 2017/5/22.
 * 资源树 数据中心》机楼》机房》机架
 * r_type  0  数据中心 1机楼 2机房 3机架
 */
$.fn.rtree = function (options) {
    var beforeAsync = function(treeId, treeNode){
        var id = treeNode.id;
        if(id.indexOf('idcroom_')>-1||id.indexOf('idcrack_')>-1){
            return true;
        }else{
            return false;
        }
    };
    var defaults = {
        r_type: 0,
        isShowRack:'Y',
        deviceclass:null,
        locationId:null,
        roomIds:null,
        'url': contextPath + "/resource/tree",
        'otherParam': [],
        'type': 'post',
        callback: {

        },
        async:{
            type:'post',
            enable:true,
            autoParam: ["id"],
            'url': contextPath + "/resource/tree"
        },
        onClick: function () {
            beforeAsync
        },
        data: {
            simpleData: {
                enable: true
            }
        }
    };
    var _self = this;
    var rtree = null;
    _self.rtree = rtree;
    var settings = $.extend({}, defaults, options);//将一个空对象做为第一个参数
    settings.callback.onClick = settings.onClick;
    var types = [];//['idc_location', 'idc_building', 'idc_roomarea', 'idc_rack', 'idc_device'];
    var r_type = settings.r_type;
    var isShow_rack = settings.isShowRack;
    var device_class = settings.deviceclass;
    var location_id = settings.locationId;
    var roomIds_ = settings.roomIds;
    types.push('idc_location');
    if (r_type > 0) {
        types.push('idc_building');
    }
    if (r_type > 1) {
        types.push('idc_roomarea');
    }
    if (r_type > 2) {
        types.push('idc_rack');
    }
    if (r_type > 3) {
        types.push('idc_device');
    }
    $.post(contextPath + "/resource/tree", {itype: r_type,locationId:location_id,deviceclass:device_class,isShowRack:isShow_rack,roomIds:roomIds_}, function (data) {
        settings.otherParam.rtype = types;
        //$.each(data,function(i,item){
        //    item.icon = contextPath+"/moudles/topo/topoimg/"+item.icon
        //})
        var tree = $.fn.zTree.init(_self, settings, data);
        _self.rtree = tree;
    },'json');
    return _self
};
