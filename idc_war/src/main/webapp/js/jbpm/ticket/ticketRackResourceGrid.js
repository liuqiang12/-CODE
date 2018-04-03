/*
 * 有关流程模型的所有js都在这里
 */
/*  wwwwwwwwwwwwwwwwwwwwwwwww  wcg add  start   wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww*/
$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});

//如果是编辑  端口带宽
var editIndex = undefined;
//如果是编辑  端口带宽
function endEditing(){


    if (editIndex == undefined){return true}
    if ($('#ticket_port_gridId').datagrid('validateRow', editIndex)){
        $('#ticket_port_gridId').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

//如果是编辑  网关地址
var editIndexGateway = undefined;
//如果是编辑  网关地址
function endEditingGateway(){

    if (editIndexGateway == undefined){return true}
    if ($('#ticket_ip_gridId').datagrid('validateRow', editIndexGateway)){
        $('#ticket_ip_gridId').datagrid('endEdit', editIndexGateway);
        editIndexGateway = undefined;
        return true;
    } else {
        return false;
    }
}

function onClickCell(index, field){
    /*下面重要说明，下面是点击后，就可以进入输入，但是要保存，再看下面  --------start----------*/
    if(field == "portassigation"){       //如果是编辑  端口带宽
        if (endEditing()){
            $('#ticket_port_gridId').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
            editIndex = index;
        }
    }else if(field == "usedRackUnIt"){      //如果是编辑  网关地址
        if (endEditingGateway()){
            $('#ticket_ip_gridId').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
            editIndexGateway = index;
        }
    }
    /*上面重要说明，上面是点击后，就可以进入输入，但是要保存，再看下面  --------end----------*/

    /*下面重要说明，以上是启用编辑，如果要保存就要点击旁边的属性，进行保存  --------start----------*/
    if(field == "portbandwidth"){
        if (endEditing()){
            $('#ticket_port_gridId').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
            editIndex = index;
        }
    }else if(field == "ticketRackGrid"){
        if (endEditingGateway()){
            $('#ticket_ip_gridId').datagrid('selectRow', index)
                .datagrid('editCell', {index:index,field:field});
            editIndexGateway = index;
        }
    }
    /*上面重要说明，以上是启用编辑，如果要保存就要点击旁边的属性，进行保存  --------end----------*/
}

function onEndEdit(index,row,changes){
    $.ajax({
        type:"POST",
        url:contextPath+"/actJBPMController/editPortAssignation.do",
        data:{portMSG:JSON.stringify(row),"portChanges":JSON.stringify(changes)},
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        beforeSend:function(){
        },
        success:function(data){
             if(data.success == true){
                 top.layer.msg(data.msg, {
                     icon: 1,
                     time: 3000 //1.5秒关闭（如果不配置，默认是3秒）
                 });
             }else if(data.success == false){
                 /*top.layer.msg(data.msg, {
                     icon: 2,
                     time: 3000 //1.5秒关闭（如果不配置，默认是3秒）
                 });*/
             }
            //重新加载数据
            $("#ticket_port_gridId").datagrid("reload");
            $("#ticket_ip_gridId").datagrid("reload");
        },
        error: function(){
        }
    })
}
/*  wwwwwwwwwwwwwwwwwwwwwwww  wcg add  end   wwwwwwwwwwwwwwww   ticket_port_gridId    分配带宽         portassigation                wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww*/


$(function() {
    //判断是否存在资源分配
    if(!ticketResourceAllocation){
        ticketResourceAllocation = getTicketResourceAllocation();
    }
    var resourceAllocationStatus = ticketResourceAllocation['resourceAllocationStatus'];
    if(resourceAllocationStatus == 'true'){
        loadTicketResourceGrid();
    }
});
function loadTicketResourceEasyuiGrid(gridId){
    /*开始刷新*/

}
/**
 * 组装相应的数据
 * @param records:选中的资源信息
 * @param ticketInstId：工单id
 * @param prodInstId：订单id
 * @param category:订单类型,如果预勘流程、开通流程等。。。。
 * @returns {Array}
 */
function settingResource(choiceFlag,records,rackORRacunitSetting,resourceCategory,rackId){
    var sets = [];
    if(choiceFlag == 'ip'){
        /*分配IP的时候，与分配其他资源不一样。故重新填写*/
        var rows = records['rows'];
        var type = records['type'];

        for(var i = 0 ;i < rows.length; i++){
            var srd = {};
            srd['ticketInstId'] = rackORRacunitSetting['ticketInstId'];
            srd['resourceid'] = rows[i]['id'];
            srd['status'] = 1;/*新增*/
            srd['manual']= 1;//手工添加方式
            srd['category']= resourceCategory;//资源类别:100机架、600机位、700MCB,200端口、300IP等
            srd['ticketCategory']=rackORRacunitSetting['category'];//预勘/预占:100
            srd['prodInstId']=rackORRacunitSetting['prodInstId'];
            if(type == 'ipaddress'){
                srd['begip']=rows[i]['ipaddress'];
                srd['endip']=rows[i]['ipaddress'];
                srd['ticketRackGrid']=rows[i]['maskstr'];

            }else if(type == 'ipsubnet'){
                srd['begip']=rows[i]['begip'];
                srd['endip']=rows[i]['endip'];
                srd['ticketRackGrid']=rows[i]['maskstr'];
            }
            srd['ipType']=type;
            sets.push(srd);
        }
    }else{
        for(var i = 0 ; i < records.length ; i++){
            //此时只有资源ID
            var srd = {};
            srd['ticketInstId'] = rackORRacunitSetting['ticketInstId'];
            srd['resourceid'] = records[i];
            srd['status'] = 1;/*新增*/
            srd['manual']= 1;//手工添加方式
            srd['category']= resourceCategory;//资源类别:100机架、600机位、700MCB,200端口、300IP等
            srd['ticketCategory']=rackORRacunitSetting['category'];//预勘/预占:100
            srd['prodInstId']=rackORRacunitSetting['prodInstId'];
            /*如果是按照机位方式分配  则必须具有机架id参数*/
            if(resourceCategory == '600' && !rackId){
                alert("如果是按照机位方式分配  则必须具有机架id参数");
                return false;
            }
            if(rackId){
                srd['rackId'] = rackId;
            }
            sets.push(srd);
        }
    }
    return sets;
}
function getrackOrUnitOrMcbOrPortOrIpChoiceOperateTitle(choiceFlag){
    if(choiceFlag == 'rack'){
        return '<span style="color:blue">机架</span>'+"》选择";
    }else if(choiceFlag == 'unit'){
        return '<span style="color:blue">机位</span>'+"》选择";
    }else if(choiceFlag == 'mcb'){
        return '<span style="color:blue">动力配置</span>'+"》选择";
    }else if(choiceFlag == 'port'){
        return '<span style="color:blue">端口</span>'+"》选择";
    }else if(choiceFlag == 'ip'){
        return '<span style="color:red">IP</span>'+"》选择";
    }
}
function getrackOrUnitOrMcbOrPortOrIpChoiceOperateUrl(rackORRacunitSetting,choiceFlag,xxxx,rackId,roomId){

    if(choiceFlag == 'rack'){
        return "/winOpenController/rackGridPanel.do?xxxx="+xxxx+"&locationId="+rackORRacunitSetting['idcNameCode'];
    }else if(choiceFlag == 'unit'){
        return "/idcRackunit/preQueryRackunitList?rackId="+rackId;
    }else if(choiceFlag =='mcb'){
        return "/idcRack/preDistributionPdfRackList?roomId="+roomId+"&rackId="+rackId;
    }else if(choiceFlag == 'port'){
        //return "/netport/distributionNetPort?roomIds="+rackORRacunitSetting['roomidstr'];
        return "/netport/distributionNetPort";
    }else if(choiceFlag == 'ip'){
        return "/resource/ipsubnet/flowallc";
    }else if(choiceFlag == 'server'){
        return "/winOpenController/serverLayout.do";
    }
}
function getRackOrUnitOrMcbChoiceData(choiceFlag,childWin){
    var records = [];
    if(choiceFlag == 'rack'){
        records = childWin.getSelectionData();
    }else if(choiceFlag == 'unit'){
        var data=childWin.doSubmit();
        if(data){
            for(var i = 0 ;i < data.length; i++){
                records[i] = data[i]['ID'];
            }
        }
    }else if(choiceFlag == 'mcb'){
        var data=childWin.doSubmit();
        var ajaxBindRackMCBResourceParam = data['ajaxBindRackMCBResourceParam'];
        var rows = data['rows'];
        //绑定服务架
        $.ajax({
            url: contextPath + "/idcmcb/bindServiceRackById",
            async: false,
            type: "post",
            dataType: "json",
            data: ajaxBindRackMCBResourceParam,
            success: function (data) {
                if(rows){
                    for(var i = 0;i < rows.length; i++){
                        if(rows[i]['ID']){
                            records[i] = rows[i]['ID'];
                        }
                    }
                }
            },
            error: function (e) {

            }
        });
    }else if(choiceFlag == 'port'){
        /*端口*/
        var data=childWin.doSubmit();
        if(data){
            for(var i = 0;i < data.length; i++){
                if(data[i]['PORTID']){
                    records[i] = data[i]['PORTID'];
                }
            }
        }
    }else if(choiceFlag == 'ip'){
        /*ip地址情况*/
        var data=childWin.doSubmit();
        /*如果是IP的情况，需要多传递一个类型参数*/
        return data;
    }else if(choiceFlag == 'server'){
        var data=childWin.getSelectionData_();
        if(data){
            for(var i = 0;i < data.length; i++){
                if(data[i]['serverDeviceId']){
                    records[i] = data[i]['serverDeviceId'];
                }
            }
        }
    }
    return records;
}
function fmtEasyuiGridOperateAction(value,row,index) {
    var gridUrlParamKey = this.gridUrlParamKey;
    var rackORRacunitSetting = getRackORRacunitOrMCBSetting();
    if('ticket_rack_gridId' == gridUrlParamKey){
        var insert= [];
        var status = row.status;/*资源状态信息*/
        /*if(status == -1){
        }else{
            insert.push('<a class="easyui-linkbutton " title="配动力" data-options="plain:true,iconCls:\'icon-dlg\'" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate(\'700\',\''+row.resourceid+'\',\'mcb\',\''+row.belongId+'\')">配动力</a> ');
            //判断，如果是按照机架分配，就不显示配u位 ,66002代表安装u位（机位）分配

            /!*if(rackORRacunitSetting['rackOrracunit'] == 66002){
                insert.push('<a class="easyui-linkbutton " title="配U位"  data-options="plain:true,iconCls:\'icon-jw\'" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate(\'600\',\''+row.resourceid+'\',\'unit\',\'\')">配U位</a> ');
            }*!/
            insert.push('<a class="easyui-linkbutton " title="配U位"  data-options="plain:true,iconCls:\'icon-jw\'" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate(\'600\',\''+row.resourceid+'\',\'unit\',\'\')">配U位</a> ');

            //删除的时候，需要传递该资源parentId和currentId:【查询两个工单类型】
            insert.push('<a class="easyui-linkbutton " title="删除"   data-options="plain:true,iconCls:\'icon-scqd-resource\'" onclick="singleTicketResourceDeleteRow('+row.id+',\''+row.resourceid+'\',\'rack\',\'100\',\'\')"><span class="icon-scqd-resource-over-d">删除</span></a> ');
        }
        return insert.join('');*/


        //insert.push('<a class="easyui-linkbutton " title="配动力" data-options="plain:true,iconCls:\'icon-dlg\'" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate(\'700\',\''+row.resourceid+'\',\'mcb\',\''+row.belongId+'\',\''+row.rackOrRackUnit+'\')">配动力</a> ');
        //判断，如果是按照机架分配，就不显示配u位 ,66002代表安装u位（机位）分配

        /*if(rackORRacunitSetting['rackOrracunit'] == 66002){
            insert.push('<a class="easyui-linkbutton " title="配U位"  data-options="plain:true,iconCls:\'icon-jw\'" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate(\'600\',\''+row.resourceid+'\',\'unit\',\'\')">配U位</a> ');
        }*/
        insert.push('<a class="easyui-linkbutton " title="配U位"  data-options="plain:true,iconCls:\'icon-jw\'" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate(\'600\',\''+row.resourceid+'\',\'unit\',\'\',\''+row.rackOrRackUnit+'\')">配U位</a> ');

        //删除的时候，需要传递该资源parentId和currentId:【查询两个工单类型】
        insert.push('<a class="easyui-linkbutton " title="删除"   data-options="plain:true,iconCls:\'icon-scqd-resource\'" onclick="singleTicketResourceDeleteRow('+row.id+',\''+row.resourceid+'\',\'rack\',\'100\',\'\',\''+row.rackOrRackUnit+'\')"><span class="icon-scqd-resource-over-d">删除</span></a> ');
        return insert.join('');
    }else if('ticket_rackmcb_gridId' == gridUrlParamKey){
        var insert= [];
        insert.push('<a class="easyui-linkbutton " title="删除"   data-options="plain:true,iconCls:\'icon-scqd-resource\'" onclick="singleTicketResourceDeleteRow('+row.id+',\''+row.resourceid+'\',\'mcb\',\'700\',\'\')"><span class="icon-scqd-resource-over-d">删除</span></a> ');
        return insert.join('');
    }else if('ticket_port_gridId' == gridUrlParamKey){
        var insert= [];
        insert.push('<a class="easyui-linkbutton " title="删除" data-options="plain:true,iconCls:\'icon-scqd-resource\'" onclick="singleTicketResourceDeleteRow('+row.id+',\''+row.resourceid+'\',\'port\',\'200\',\'\',\'\')"><span class="icon-scqd-resource-over-d">删除</span></a> ');
        return insert.join('');
    }else if('ticket_ip_gridId' == gridUrlParamKey){
        var insert= [];
        /*获取ip类型*/
        var ipType = row.ipType;
        insert.push('<a class="easyui-linkbutton " title="删除" data-options="plain:true,iconCls:\'icon-scqd-resource\'" onclick="singleTicketResourceDeleteRow('+row.id+',\''+row.resourceid+'\',\'ip\',\'300\',\''+ipType+'\',\'\')"><span class="icon-scqd-resource-over-d">删除</span></a> ');
        return insert.join('');
    }else if('ticket_server_gridId' == gridUrlParamKey){
        var insert= [];
        insert.push('<a class="easyui-linkbutton " title="删除" data-options="plain:true,iconCls:\'icon-scqd-resource\'" onclick="singleTicketResourceDeleteRow('+row.id+',\''+row.resourceid+'\',\'server\',\'400\',\'\',\'\')"><span class="icon-scqd-resource-over-d">删除</span></a> ');
        return insert.join('');
    }else{
        /*没有任何列信息*/
        return "";
    }
}


function rackOrRackUnitChoice(idcNameCode,ticketInstId) {
    var laySum = top.layer.open({
        type: 2,
        id:'rackLayer',
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['900px', '450px'],
        content: contextPath+ "/actJBPMController/rackOrRackUnitChoicePage/"+idcNameCode+"/"+ticketInstId,
        /*弹出框*/
        yes: function(index){
            $("#ticket_rack_gridId").datagrid("reload");
            $("#ticket_rackmcb_gridId").datagrid("reload");
            top.layer.close(index);
        }
    });
}
function refreashRackGrid(){
    $("#ticket_rack_gridId").datagrid("reload");
    $("#ticket_rackmcb_gridId").datagrid("reload");
}

/**
 * [机架机位选择的方法]
 * @param gridId
 * @param resourceCategory
 * @param rackId
 * @param choiceFlag:rack选择的是机架  unit选择的是机位  mcb选择的是mcb
 * @param roomid:选择动力柜的时候[需要获取所属机房。。。。]
 */
function rackOrUnitOrMcbOrPortOrIpChoiceOperate(resourceCategory,rackId,choiceFlag,roomid){
    var rackORRacunitSetting = getRackORRacunitOrMCBSetting();
    var xxxx = "";

    //查询意向IDC的code
    $.ajax({
        type: "POST",
        async:false,
        url: contextPath + "/actJBPMController/getIdcNameByTicketID.do",
        data: {ticketInstId: rackORRacunitSetting['ticketInstId']},
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        beforeSend: function () {
        },
        success: function (data) {
            rackORRacunitSetting['idcNameCode']=data.result;
        }
    });

    if(choiceFlag == 'port'){
        //如果是选择端口，则需要传递工单机架所属的机房id，如果选择了机架，还要传机架的ID，没有选择机架就不用传机架的ID
        $.ajax({
            type: "POST",
            url: contextPath + "/actJBPMController/loadRoomsWithTicket.do",
            data: {ticketInstId: rackORRacunitSetting['ticketInstId']},
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            beforeSend: function () {
            },
            success: function (data) {
                //**所属机房IDS
                var res = data.result;
                var roomidstr = res.roomidStr;   //机房ID
                var rackIDStr = res.rackIDStr;   //机架ID
                rackORRacunitSetting['roomidstr'] = roomidstr;
                rackORRacunitSetting['rackIDStr'] = rackIDStr;
                callResourceChoiceOperate(rackORRacunitSetting,resourceCategory,choiceFlag,xxxx,rackId,roomid);
            }
    });
    }else if(choiceFlag == 'rack'){
        //选择机架时，要区别按机架还是机位分配，所以也走单独的方法
        rackOrRackUnitChoice(rackORRacunitSetting['idcNameCode'],rackORRacunitSetting['ticketInstId']);
    }else{
        //其他
        callResourceChoiceOperate(rackORRacunitSetting,resourceCategory,choiceFlag,xxxx,rackId,roomid);
    }
}
/*调用相应的资源传递*/
function callResourceChoiceOperate(rackORRacunitSetting,resourceCategory,choiceFlag,xxxx,rackId,roomid){
    var url = getrackOrUnitOrMcbOrPortOrIpChoiceOperateUrl(rackORRacunitSetting,choiceFlag,xxxx,rackId,roomid);
    var title = getrackOrUnitOrMcbOrPortOrIpChoiceOperateTitle(choiceFlag);
    if(resourceCategory == '200'){
        var data = {roomIds:rackORRacunitSetting['roomidstr'],
            rackIds:rackORRacunitSetting['rackIDStr'],
            locationId:rackORRacunitSetting['idcNameCode'],
            status:'40'};
        var laySum = top.layer.open({
            type: 2,
            area: ['1024px', '88%'],
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
                    var html=buildFormByParam(contextPath+url,data);
                    chidlwin.document.body.innerHTML="";//清空iframe内容，达到重新请求
                    chidlwin.document.write(html);
                    chidlwin.document.getElementById('postData_form').submit();
                }
            },
            yes: function(index, layero){
                var childWin = getJbpmChlidWin(layero);
                var records = getRackOrUnitOrMcbChoiceData(choiceFlag,childWin);
                if(records && records.length == 0){
                    top.layer.msg('没有选择记录', {
                        icon: 2,
                        time: 1500
                    });
                    return false;
                }
                var resourcesData = settingResource(choiceFlag,records,rackORRacunitSetting,resourceCategory,rackId);
                //调用资源配置方法
                callResourceSetting(choiceFlag,resourcesData,xxxx,index);
            },
            cancel: function (index) {
            }
        });


    }else{
        var laySum = top.layer.open({
            type: 2,
            title: title,
            shadeClose: false,
            shade: 0.8,
            btn: ['确定','关闭'],
            maxmin : true,
            area: ['1024px', '88%'],
            content: contextPath+url,
            /*弹出框*/
            cancel: function(index, layero){
                top.layer.close(index);
            },yes: function(index, layero){
                var childWin = getJbpmChlidWin(layero);
                var records = getRackOrUnitOrMcbChoiceData(choiceFlag,childWin);
                if(records && records.length == 0){
                    top.layer.msg('没有选择记录', {
                        icon: 2,
                        time: 1500
                    });
                    return false;
                }
                var resourcesData = settingResource(choiceFlag,records,rackORRacunitSetting,resourceCategory,rackId);

                //调用资源配置方法
                callResourceSetting(choiceFlag,resourcesData,xxxx,index);
            }
        });
    }
}
function callResourceSetting(choiceFlag,resourcesData,xxxx,layerIndex){
    $.ajax({
        type:"POST",
        url:contextPath+"/actJBPMController/resourceSetting.do",
        data:{resourceSettingData:JSON.stringify(resourcesData)},
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        beforeSend:function(){
            top.onclickProcessBar();  //打开进度条
        },
        success:function(data){
            top.closeProgressbar();    //关闭进度条
            //var obj = jQuery.parseJSON(data);
            top.layer.msg(data.msg, {
                icon: 1,
                time: 3000 //1.5秒关闭（如果不配置，默认是3秒）
            });
            //var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
            top.layer.close(layerIndex);
            easyuiRefreshGridByChoice(choiceFlag);
        },
        complete: function(XMLHttpRequest, textStatus){
        },
        error: function(){
        }
    })
}
/**
 * 加载工单资源列表信息
 */
function loadTicketResourceGrid(){
    var ticketResourceCategory = getTicketResourceCategory();
    if(!ticketResourceAllocation){
        ticketResourceAllocation = getTicketResourceAllocation();
    }
    var resourceAllocationStatus = ticketResourceAllocation['resourceAllocationStatus'];
    if(!resourceAllocationStatus){
        return ;
    }
    var easyuiGridParam = getEasyuiGridParam();
    for(var ticketResourceCategorykey in ticketResourceCategory){
        if(ticketResourceCategory[ticketResourceCategorykey] == 'true'){
            /** 则需要加载相应的grid  **/
            var gridUrlParam = easyuiGridParam[ticketResourceCategorykey];
            loadEasyuiGrid(gridUrlParam);
        }
    }
}
function loadEasyuiGrid(gridUrlParam){
    for(var gridUrlParamKey in gridUrlParam){
        $('#'+gridUrlParamKey).datagrid({
            url:contextPath+gridUrlParam[gridUrlParamKey],
            title: getEasyuiGridTitleParam()[gridUrlParamKey],
            iconCls:'',
            queryParams: getEasyuiGridRequestParam(gridUrlParamKey),
            toolbar: getEasyuiGridToolbarParam()[gridUrlParamKey],
            border: false,
            singleSelect: true,
            striped : true,
            pagination: true,
            pageSize: 7,
            pageList:[7],
            rownumbers: true,
            fitColumns: true,
            autoRowHeight:true,
            columns: getEasyuiGridColumnsParam(gridUrlParamKey),
            onBeforeLoad:function(param){
                param['pageNo'] = param['page'];
                param['pageSize'] = param['rows'];
                return true;
            },
            loadFilter:function(data){
                return {total : data.totalRecord,rows : data.items}},
            onLoadSuccess:function(){
                $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
                    $(this).linkbutton();
                });
                $(".targetName_tip").tooltip({
                    onShow: function(){
                        $(this).tooltip('tip').css({
                            width:'250',
                            height:'50',
                            boxShadow: '1px 1px 3px #292929'
                        });
                    }}
                );
                $(".UName_tip").tooltip({
                    onShow: function(){
                        $(this).tooltip('tip').css({
                            width:'80',
                            height:'20',
                            boxShadow: '1px 1px 3px #292929'
                        });
                    }}
                );
            },
            onClickCell: onClickCell,
            onEndEdit: onEndEdit
            /*分配带宽   ticket_port_gridId       */
        });
    }
}

function getEasyuiGridParam(){
    var rackORRacunitSetting = getRackORRacunitOrMCBSetting();
    var gridParam = {};
        /*这里就需要加载三个gridId*/
    gridParam['rack'] = { ticket_rack_gridId:'/actJBPMController/ticketRackResourceGridQueryData/'+rackORRacunitSetting['ticketInstId']+'/'+rackORRacunitSetting['ticketStatus']+'/100',
                          ticket_rackmcb_gridId:'/actJBPMController/ticketRackResourceGridQueryData/'+rackORRacunitSetting['ticketInstId']+'/'+rackORRacunitSetting['ticketStatus']+'/700'
                        };

    gridParam['port'] = {ticket_port_gridId:'/actJBPMController/ticketRackResourceGridQueryData/'+rackORRacunitSetting['ticketInstId']+'/'+rackORRacunitSetting['ticketStatus']+'/200'};
    gridParam['ip'] = {ticket_ip_gridId:'/actJBPMController/ticketRackResourceGridQueryData/'+rackORRacunitSetting['ticketInstId']+'/'+rackORRacunitSetting['ticketStatus']+'/300'};
    gridParam['server'] = {ticket_server_gridId:'/actJBPMController/ticketRackResourceGridQueryData/'+rackORRacunitSetting['ticketInstId']+'/'+rackORRacunitSetting['ticketStatus']+'/400'};
    return gridParam;
}
function getEasyuiGridTitleParam(){
    var gridParam = {};
    /*这里就需要加载三个gridId*/
    gridParam['ticket_rack_gridId'] = '机架机位订单,选择可用的客户机架的同时体现MCB资源的信息...';
    gridParam['ticket_rackmcb_gridId'] = '';
    gridParam['ticket_port_gridId'] = '带宽端口订单...';
    gridParam['ticket_ip_gridId'] = 'IP租用订单...';
    gridParam['ticket_server_gridId'] = '主机租赁订单...';
    return gridParam;
}
function getEasyuiGridRequestParam(ticketResourceCategorykey){
    var params = {};
    if('ticket_rack_gridId' == ticketResourceCategorykey){
        var rackName_params = $("#rackName_params").textbox("getValue");
        params['resourcename'] = rackName_params;
    }else if('ticket_rackmcb_gridId' == ticketResourceCategorykey){
        var rackmcbName_params = $("#rackmcbName_params").textbox("getValue");
        params['resourcename'] = rackmcbName_params;
    }else if('ticket_port_gridId' == ticketResourceCategorykey){
        var portName_params = $("#portName_params").val();
        params['resourcename'] = portName_params;
    }else if('ticket_ip_gridId' == ticketResourceCategorykey){
        var ipName_params = $("#ipName_params").val();
        params['resourcename'] = ipName_params;
    }else if('ticket_server_gridId' == ticketResourceCategorykey){
        var serverName_params = $("#serverName_params").textbox("getValue");
        params['resourcename'] = serverName_params;
    }
    return params;
}

function getEasyuiGridToolbarParam(){
    var gridParam = {};
    /*这里就需要加载三个gridId*/
    gridParam['ticket_rack_gridId'] = '#requestParamSettins_ticket_rack_gridId';
    gridParam['ticket_rackmcb_gridId'] = '#requestParamSettins_ticket_rackmcb_gridId';
    gridParam['ticket_port_gridId'] = '#requestParamSettins_ticket_port_gridId';
    gridParam['ticket_ip_gridId'] = '#requestParamSettins_ticket_ip_gridId';
    gridParam['ticket_server_gridId'] = '#requestParamSettins_ticket_server_gridId';
    return gridParam;
}
function getEasyuiGridColumnsParam(gridUrlParamKey){
    if(!ticketResourceAllocation){
        ticketResourceAllocation = getTicketResourceAllocation();
    }
    var ticketResourceHandStatus_tmp = ticketResourceAllocation['ticketResourceHandStatus'];
    var pageQueryDataStatus_tmp = ticketResourceAllocation['pageQueryDataStatus'];
    var ticketResourceHandStatus = true;//默认是显示
    var pageQueryDataStatus = true;//默认是显示
    if(ticketResourceHandStatus_tmp == 'false'){
        ticketResourceHandStatus = false;//隐藏
    }
    if(pageQueryDataStatus_tmp == 'false'){
        pageQueryDataStatus = false;
    }
    var gridParam = {};
    /*通过判断设置*/
    if('ticket_rack_gridId' == gridUrlParamKey){
        /*这里需要注意。获取是否是按照机位分配*/
        var rackORRacunitSetting = getRackORRacunitOrMCBSetting();
        var xxxx = "";
        return [
                    [
                        {field: 'resourcename', title: '机架名称',halign:'left',width:210,formatter:targetNameFmt },
                        {field: 'belongName', title:'所属机房',halign:'left',width:170,formatter:belongNameFmt },
                        /*{field: 'resourceStatus', title: '状态(机架)',halign:'left',width:80,formatter:cssFmt },*/
                        {field: 'pduPowertype', title: '电源类型',halign:'left',width:40,formatter:cssFmt },
                        {field: 'ratedelectricenergy', title: '额定电量',halign:'left',width:80,formatter:ratedelectricenergyFmt },
                        {field: 'uheight', title:'U位数',halign:'left',width:45,formatter:cssFmt  },
                        {field: 'usedRackUnIt', title:'占U位',halign:'left',width:150,formatter:usedRackUnItFmt },
                        {field: 'rackOrRackUnit', title:'业务类型',halign:'left',width:100,formatter:fmtRackOrRackUnit },
                        {field: 'operate',gridUrlParamKey:gridUrlParamKey,hidden : (!ticketResourceHandStatus || pageQueryDataStatus), title: '操作',width:250,halign:'center',align:'center',formatter:fmtEasyuiGridOperateAction }
                    ]
        ];
    }else if('ticket_rackmcb_gridId' == gridUrlParamKey){
        return [
            [
                {field: 'resourcename', title: 'PDU名称 ',halign:'left',width:130,formatter:targetNameFmt },
                {field: 'belongName', title: '所属PDF ',halign:'left',width:130,formatter:belongNameFmt },
                {field: 'resourceStatus', title: '状态 ',halign:'left',width:80,formatter:cssFmt},
                {field: 'pduPowertype', title: '电源类型 ',halign:'left',width:80,formatter:cssFmt},
                {field: 'mcbBelongName', title: '客户机架 ',halign:'left',width:130,formatter:cssFmt},
                /*{field: 'parentTicketInstId', title: '测试用',halign:'left',width:80 ,formatter:cssFmt},
                {field: 'ticketCategoryFrom', title: '测试用',halign:'left',width:80 ,formatter:cssFmt},
                {field: 'status', title: '测试用',halign:'left',width:80 ,formatter:cssFmt},*/
                {field: 'ratedelectricenergy', title: '额定电流 ',halign:'left',width:80,formatter: edFmt}/*,/!*KWH*!/
             {field: 'operate', title: '操作',width:100,halign:'center',formatter:fmtOperateRackMCBAction }*/
                /*{field: 'operate',gridUrlParamKey:gridUrlParamKey,hidden :(!ticketResourceHandStatus || pageQueryDataStatus), title: '操作',width:80,halign:'center',align:'center',formatter:fmtEasyuiGridOperateAction }*/
            ]
        ];
    }else if('ticket_port_gridId' == gridUrlParamKey){
        return [
            [
                {field: 'resourcename', title: '端口名称',halign:'left',width:100,formatter:targetNameFmt },
                {field: 'belongName', title: '所属设备 ',halign:'left',width:280,formatter:belongNameFmt },
                {field: 'resourceStatus', title: '状态 ',halign:'left',width:100,formatter:cssFmt},
                {field: 'ipName', title: '地址',halign:'left',width:100,formatter:cssFmt},
                {field: 'portbandwidth', title: '端口带宽 ',halign:'left',width:100,formatter:cssFmt},
                {field: 'portassigation', title: '分配带宽(<span style="color:red">必填</span>)',halign:'left',width:100,editor:'numberbox'},    /*  ticket_port_gridId  */
                /*{field: 'parentTicketInstId', title: '测试用',halign:'left',width:80 },
                {field: 'ticketCategoryFrom', title: '测试用',halign:'left',width:80 },
                {field: 'status', title: '测试用',halign:'left',width:80 },*/
                {field: 'operate',gridUrlParamKey:gridUrlParamKey,hidden : (!ticketResourceHandStatus || pageQueryDataStatus), title: '操作',width:80,halign:'center',align:'center',formatter:fmtEasyuiGridOperateAction }
            ]
        ];
    }else if('ticket_ip_gridId' == gridUrlParamKey){
        return [
            [
                {field: 'ipName', title: '起始地址',halign:'left',width:130,formatter:cssFmt},
                {field: 'endip', title: '结束地址',halign:'left',width:130,formatter:cssFmt},
                {field: 'ticketRackGrid', title: '掩码',halign:'left',width:130,formatter:cssFmt},
                {field: 'usedRackUnIt', title: '网关地址(<span style="color:#1E9FFF">可手动输入</span>)',halign:'left',width:130,editor:'text'},
                {field: 'resourceStatus', title: '状态',halign:'left',width:130,formatter:cssFmt},
                {field: 'operate',gridUrlParamKey:gridUrlParamKey,hidden : (!ticketResourceHandStatus || pageQueryDataStatus),width:80,halign:'center',align:'center',formatter:fmtEasyuiGridOperateAction }
            ]
        ];
    }else if('ticket_server_gridId' == gridUrlParamKey){
        return [
            [
                {field: 'resourcename', title: '设备的名称',halign:'left',width:140,formatter:cssFmt},
                {field: 'os', title: '操作系统',halign:'left',width:80,formatter:cssFmt},
                {field: 'resourceStatus', title: '状态 ',halign:'left',width:80,formatter:cssFmt},
                {field: 'vendor', title: '厂商',halign:'left',width:80,formatter:cssFmt},
                {field: 'ownerType', title: '产权性质',halign:'left',width:80,formatter:cssFmt},
                /*{field: 'parentTicketInstId', title: '测试用',halign:'left',width:80 },
                {field: 'ticketCategoryFrom', title: '测试用',halign:'left',width:80 },
                {field: 'status', title: '测试用',halign:'left',width:80 },*/
                {field: 'operate',gridUrlParamKey:gridUrlParamKey,hidden :(!ticketResourceHandStatus || pageQueryDataStatus),width:80,halign:'center',align:'center',formatter:fmtEasyuiGridOperateAction }
            ]
        ];
    }else{
        /*没有任何列信息*/
        return [[]];
    }
}
function targetNameFmt(value,row,index){
    /**/
    if(!row.resourcename){
        return "";
    }
    var status = row.status;
    /*if(status == -1){
        /!*被删除的资源:增加样式*!/
        return '<span class="targetName_tip resourceDelete" title="'+row.resourcename+'">'+row.resourcename+'</span>';
    }else{
        return '<span class="targetName_tip" title="'+row.resourcename+'">'+row.resourcename+'</span>';
    }*/
    return '<span class="targetName_tip" title="'+row.resourcename+'">'+row.resourcename+'</span>';

}
function edFmt(value,row,index){
    var varStr = value?value+" A":"0 A";
    var status = row.status;
   /* if(status == -1){
        /!*被删除的资源:增加样式*!/
        return '<span class="resourceDelete">'+varStr+'</span>';
    }else{
        return varStr;
    }*/
    return varStr;

}
function cssFmt(value,row,index){
    var status = row.status;
    /*if(status == -1){
        /!*被删除的资源:增加样式*!/
        return '<span class="resourceDelete">'+value+'</span>';
    }else{
        return value;
    }*/
    return value;

}
function belongNameFmt(value,row,index){
    if(!row.belongName){
        return "";
    }
    var status = row.status;
    /*if(status == -1){
        /!*被删除的资源:增加样式*!/
        return '<span class="targetName_tip resourceDelete" title="'+row.belongName+'">'+row.belongName+'</span>';
    }else{
        return '<span class="targetName_tip" title="'+row.belongName+'">'+row.belongName+'</span>';
    }*/
    return '<span class="targetName_tip" title="'+row.belongName+'">'+row.belongName+'</span>';
}

function resourceStatusFmt(value,row,index){
    if(!row.usedRackUnIt){
        return '<span style="color:darkred"  class="UName_tip" title="没有配置U位">没有配置U位</span>';
    }
    var status = row.status;
   /* if(status == -1){
        /!*被删除的资源:增加样式*!/
        return '<span class="resourceDelete">'+value+'</span>';
    }else{
        return value;
    }*/
    return value;

}
function usedRackUnItFmt(value,row,index){
    if(!row.usedRackUnIt){
        return "";
    }
    var status = row.status;
    /*if(status == -1){
        return '<span class="targetName_tip " title="'+row.usedRackUnIt+'">'+row.usedRackUnIt+'</span>';
    }else{
        return '<span class="targetName_tip" title="'+row.usedRackUnIt+'">'+row.usedRackUnIt+'</span>';
    }*/
    return '<span class="targetName_tip" title="'+row.usedRackUnIt+'">'+row.usedRackUnIt+'</span>';

}
function fmtRackOrRackUnit(value,row,index){
    if(value == 66001){
        return '<span style="color: #b540d4">'+'按整架出租'+'</span>';
    }else if(value == 66002){
        return '<span style="color: red">'+'按机位出租'+'</span>';
    }else{
        return '<span style="color: #dd1144">'+'未知分配类型'+'</span>';
    }
}
function ratedelectricenergyFmt(value,row,index){
    var status = row.status;
   /* if(status == -1){
        /!*被删除的资源:增加样式*!/
        return '<span class="resourceDelete">'+value?value:0+'</span>';
    }else{
        return value?value:0;
    }*/
    return value?value:0;

}
function placeholderUse(value,row,index){
    var html = value;
    return html = '<span style="color: #0e0e0e">占位用</span>';
}
function easyuiRefreshGridByChoice(choiceFlag){
    if(choiceFlag == 'rack'){
        $('#ticket_rack_gridId').datagrid('options').queryParams = getEasyuiGridRequestParam('ticket_rack_gridId');
        $('#ticket_rackmcb_gridId').datagrid('options').queryParams = getEasyuiGridRequestParam('ticket_rackmcb_gridId');

        $("#ticket_rack_gridId").datagrid("reload");
        $("#ticket_rackmcb_gridId").datagrid("reload");
    }else if(choiceFlag == 'unit'){
        $('#ticket_rack_gridId').datagrid('options').queryParams = getEasyuiGridRequestParam('ticket_rack_gridId');
        $("#ticket_rack_gridId").datagrid("reload");
    }else if(choiceFlag == 'mcb'){
        $('#ticket_rackmcb_gridId').datagrid('options').queryParams = getEasyuiGridRequestParam('ticket_rackmcb_gridId');
        $("#ticket_rackmcb_gridId").datagrid("reload");
    }else if(choiceFlag == 'port'){
        $('#ticket_port_gridId').datagrid('options').queryParams = getEasyuiGridRequestParam('ticket_port_gridId');
        $("#ticket_port_gridId").datagrid("reload");
    }else if(choiceFlag == 'ip'){
        $('#ticket_ip_gridId').datagrid('options').queryParams = getEasyuiGridRequestParam('ticket_ip_gridId');
        $("#ticket_ip_gridId").datagrid("reload");
    }else if(choiceFlag == 'server'){
        $('#ticket_server_gridId').datagrid('options').queryParams = getEasyuiGridRequestParam('ticket_server_gridId');
        $("#ticket_server_gridId").datagrid("reload");
    }
}
function singleTicketResourceDeleteRow(id,resourceid,choiceFlag,resourceCategory,ipType,rackOrRackUnit){
    /*其他额外的参数:如果工单Id*/
    var rackORRacunitSetting = getRackORRacunitOrMCBSetting();
    var ticketInstId = rackORRacunitSetting['ticketInstId'];
    var ajaxParams = {primaryId:id,resourceid:resourceid,resourceCategory:resourceCategory,ipType:ipType,rackOrRackUnit:rackOrRackUnit};
    ajaxParams = jQuery.extend({}, ajaxParams, rackORRacunitSetting);
    top.layer.confirm('你确定要删除该资源吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/actJBPMController/deleteTicketResource.do",
            data:ajaxParams,
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            beforeSend:function(){
            },
            success:function(data){
                //var obj = jQuery.parseJSON(data);
                //top.layer.msg(obj.msg, {
                top.layer.msg(data.msg, {
                    icon: 1,
                    time: 3000
                });
                top.layer.close(index);
                easyuiRefreshGridByChoice(choiceFlag);
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
            },
            error: function(){
            }
        });
    }, function(index, layero){
        top.layer.close(index)
    });
}

/**
 * 后台验证 按照机架分配是否选择了机位选择机架
 * @param category
 */
function findRackIsHaveUnit(ticketInstId){
    var isHaveUnitNotSettingRack=false;
    $.ajax({
        url:contextPath+"/actJBPMController/findRackIsHaveUnit.do",
        async:false,  /*意思是关闭ajax 的异步，改为同步*/
        type:'POST',
        data:{"ticketInstId":ticketInstId},
        dataType:'json',
        success:function(data){
            if(data.success && data.count > 0){
                isHaveUnitNotSettingRack=true;

                var error="机架中没有配置机位，请检查数据...";
                top.layer.msg(error, {
                    icon: 2,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                });
                //跳转到资源分配选择机位的窗口
                $("#jbpmApply_tabs").tabs("select","资源分配");
                return false;
            }
        },
        error:function(data){
            alert("错误，请联系管理员！");
        }
    });
    return isHaveUnitNotSettingRack;
}

function getTicketResourceCount(ticketInstId){
    var isHaveTicketResource = false;
    $.ajax({
        //提交数据的类型 POST GET
        type:"POST",
        async:false,  /*意思是关闭ajax 的异步，改为同步*/
        //提交的网址
        url:contextPath+"/actJBPMController/getTicketResourceCount.do",
        //提交的数据
        data:{ ticketInstId: ticketInstId },
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        success:function(data){
            /*这里先使用ajax判断是否资源已经存在，如果不存在，则不能进行下一步操作*/
            if(data.success){
                isHaveTicketResource = true;
            }
        }
    });
    return isHaveTicketResource;
}

function portHaveAssignation(ticketInstId){
    var isHaveTicketResource = false;
    $.ajax({
        type:"POST",
        async:false,  /*意思是关闭ajax 的异步，改为同步*/
        url:contextPath+"/actJBPMController/portHaveAssignation.do",
        //提交的数据
        data:{ ticketInstId: ticketInstId },
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        success:function(data){
            /*这里先使用ajax判断是否资源已经存在，如果不存在，则不能进行下一步操作*/
            if(data.success){
                isHaveTicketResource = true;
            }
        },
    });
    return isHaveTicketResource;
}

function removeAllResource(resourceCategory,ticketInstId){
    var laySum = top.layer.open({
        type: 2,
        title: '一键删除资源',
        shadeClose: false,
        shade: 0.8,
        btn: ['一键删除','关闭'],
        maxmin : true,
        area: ['500px', '200px'],
        content:contextPath+"/actJBPMController/removeAllResourcePage/"+resourceCategory+"/"+ticketInstId,
        yes: function(index, layero){
            //开始删除资源
            $.ajax({
                type:"POST",
                async:false,  /*意思是关闭ajax 的异步，改为同步*/
                url:contextPath+"/actJBPMController/removeAllResourceMake.do",
                //提交的数据
                data:{ resourceCategory: resourceCategory,ticketInstId: ticketInstId },
                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                success:function(data){
                    if(data.success){
                        top.layer.close(index);
                    }
                    //重新加载页面
                    if(resourceCategory == "100"){
                        $("#ticket_rack_gridId").datagrid("reload");
                        $("#ticket_rackmcb_gridId").datagrid("reload");
                    }else if(resourceCategory == "200"){
                        $("#ticket_port_gridId").datagrid("reload");
                    }else if(resourceCategory == "300"){
                        $("#ticket_ip_gridId").datagrid("reload");
                    }else if(resourceCategory == "400"){
                        $("#ticket_server_gridId").datagrid("reload");
                    }
                    top.layer.msg(data.msg, {
                        icon: 1,
                        time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                    });
                },
            });
        },cancel: function(index, layero){
            top.layer.close(index)
        }
    });


}
