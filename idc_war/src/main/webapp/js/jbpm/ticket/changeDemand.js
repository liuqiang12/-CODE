
$(function() {
    var rack_Num = $("#rack_Num").val() || null;
    var port_num = $("#port_num").val() || null;
    var ip_num = $("#ip_num").val() || null;
    var server_num = $("#server_num").val() || null;
    var rack_Other = $("#rack_Other").val() || null;
    var port_bandwidth = $("#port_bandwidth").val() || null;

    var history_demand = $("#history_demand").val() || null;

    if(history_demand != null || history_demand != ""){
        var prodInstId =$("#prodInstId").val();
        var ticketInstId =$("#ticketInstId").val();
        //loadHisDemand(prodInstId,ticketInstId);
    }

    if(rack_Num != null){
        changeTips(rack_Num,"rack_Num");
    }
    if(port_num != null){
        changeTips(port_num,"port_num");
    }
    if(ip_num != null){
        changeTips(ip_num,"ip_num");
    }
    if(server_num != null){
        changeTips(server_num,"server_num");
    }
    if(rack_Other != null){
        changeTips(rack_Other,"rack_Other");
    }
    if(port_bandwidth != null){
        changeTips(port_bandwidth,"port_bandwidth");
    }
});

function loadHisDemand(prodInstId,ticketInstId){

    $('#history_demand').datagrid({
        url:contextPath+"/actJBPMController/historyDemand.do",
        queryParams: { 'prodInstId': prodInstId, 'ticketInstId': ticketInstId},
        width: 950,
        height: 150,
        fitColumns: false,
        singleSelect: true,
        columns:[[
            {field:'SERIAL_NUMBER',title:'工单号',width:100,align:'center'},
            {field:'TICKET_INST_ID',title:'工单ID',width:100,align:'center'},
            {field:'RACK_SPEC',title:'机架规格',width:100,align:'center'},
            {field:'OTHER_MSG',title:'机位个数（U）',width:100,align:'center'},
            {field:'RACK_NUM',title:'机架个数(个)',width:100,align:'center'},
            {field:'RACK_SUPPLYTYPE',title:'供电类型',width:100,align:'center'},
            {field:'RACK_DESC',title:'机架描述',width:100,align:'center'},
            {field:'PORT_PORTMODE',title:'端口带宽占用方式',width:100,align:'center'},
            {field:'PORT_BANDWIDTH',title:'端口带宽总需求',width:100,align:'center'},
            {field:'PORT_NUM',title:'端口端口数量（个）',width:100,align:'center'},
            {field:'PORT_DESC',title:'端口描述',width:100,align:'center'},
            {field:'IP_PORTMODE',title:'IP性质',width:100,align:'center'},
            {field:'IP_NUM',title:'IP数量（个）',width:100,align:'center'},
            {field:'IP_DESC',title:'IP描述',width:100,align:'center'},
            {field:'SERVER_TYPEMODE',title:'主机资源类型',width:100,align:'center'},
            {field:'SERVER_SPECNUMBER',title:'主机设备型号',width:100,align:'center'},
            {field:'SERVER_NUM',title:'主机数量（个)',width:100,align:'center'},
            {field:'SERVER_DESC',title:'主机描述',width:100,align:'center'},
            {field:'NEWLY_NAME',title:'业务增值名称',width:100,align:'center'},
            {field:'NEWLY_CATEGORY',title:'业务增值业务分类',width:100,align:'center'},
            {field:'NEWLY_DESC',title:'业务增值描述',width:100,align:'center'}
        ]]
    });
}


function changeTips(idValue,idName){
    // console.log("准备打印！！！！！！！！！！");
    // console.log(idValue);
    if(idValue > 0){
        $("#"+idName+"_x").text("需求新增");
        //console.log("需求新增");
    }else if(idValue < 0){
        $("#"+idName+"_x").text("需求减少");
        //console.log("需求减少");
    }else if(idValue == 0){
        $("#"+idName+"_x").text(" ");
        //console.log(idValue);
    }
}

function checkField(newValue,oldValue){

    if(newValue > 0){
        $("#"+this.id+"_x").text("需求新增")
    }else if(newValue < 0){
        $("#"+this.id+"_x").text("需求减少")
    }else if(newValue == 0){
        $("#"+this.id+"_x").text(" ")
    }

    var idName=this.id;  //id名字
    var idValue=$("#"+idName).val();   //id的值
    var ticketInstId=$("#idcTicket_ticketInstId").val();   //工单id
    var prodInstId=$("#idcTicket_prodInstId").val();        //产品id

    $.ajax({
        type:"POST",
        async:false,
        url:contextPath+"/actJBPMController/updateTicketDemand.do",
        //提交的数据
        data:{ idName: idName,idValue: idValue,ticketInstId: ticketInstId ,prodInstId: prodInstId },
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        success:function(data){
            top.layer.msg(data.msg, {
                icon: 1,
                time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
            });
        },
        error:function(data){
            top.layer.msg("出现异常！", {
                icon: 1,
                time: 2000
            });
        },
    });
}

$('.numedit').numberspinner({
    min: -1000000,
    max: 1000000,
    editable: true,
    obj:this,
    onSpinUp:downNumInput,
    onSpinDown:upNumInput,
    onChange:checkField
});

function downNumInput(){
    var lay = layer.tips('数量 +1', $(this).siblings("span.tipCls"), {
        tips: 1 ,
        time: 1000
    });
}

function upNumInput(){
    var lay = layer.tips('数量 -1', $(this).siblings("span.tipCls"), {
        tips: 1 ,
        time: 1000
    });
}
/*---------------------wcg -----华丽的分隔线！！！！！！！！！！---------上面是变更需求的更改----------------------------------------------*/

/*---------------------wcg -----华丽的分隔线！！！！！！！！！！---------下面是首次需求的更改----------------------------------------------*/

$('.first_numedit').numberspinner({
    min: -1000000,
    max: 1000000,
    editable: true,
    obj:this,
    onSpinUp:downNumInput,
    onSpinDown:upNumInput,
    onChange:firstCheckField
});

function firstCheckField(newValue,oldValue){
    if(newValue > 0){
        $("#"+this.id+"_x").text("需求新增")
    }else if(newValue < 0){
        $("#"+this.id+"_x").text("需求减少")
    }else if(newValue == 0){
        $("#"+this.id+"_x").text(" ")
    }

    var idName=this.id;  //id名字
    var idValue=$("#"+idName).val();   //id的值
    var ticketInstId=$("#idcTicket_ticketInstId").val();   //工单id
    var prodInstId=$("#idcTicket_prodInstId").val();        //产品id

    $.ajax({
        type:"POST",
        async:false,
        url:contextPath+"/actJBPMController/updateTicketFirstDemand.do",
        //提交的数据
        data:{ idName: idName,idValue: idValue,ticketInstId: ticketInstId ,prodInstId: prodInstId },
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        success:function(data){
            top.layer.msg(data.msg, {
                icon: 1,
                time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
            });
        },
        error:function(data){
            top.layer.msg("出现异常！", {
                icon: 1,
                time: 2000
            });
        },
    });
}


/*---------------------wcg -----华丽的分隔线！！！！！！！！！！---------下面是测试流程需求的更改----------------------------------------------*/

function updateTestJbpmDemand(newValue,oldValue){

    var idName=this.id;  //id名字
    var idValue=$("#"+idName).val();   //id的值
    var ticketInstId=$("#idcTicket_ticketInstId").val();   //工单id
    var prodInstId=$("#idcTicket_prodInstId").val();        //产品id
    console.log("DDDDDDDDDDDDDDDDDDDDDD")
    console.log(idName)
    console.log(idValue)
    console.log(ticketInstId)
    console.log(prodInstId)

    $.ajax({
        type:"POST",
        async:false,
        url:contextPath+"/actJBPMController/updateTestJbpmDemand.do",
        //提交的数据
        data:{ idName: idName,idValue: idValue,ticketInstId: ticketInstId ,prodInstId: prodInstId },
        datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
        success:function(data){
            top.layer.msg(data.msg, {
                icon: 1,
                time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
            });
        },
        error:function(data){
            top.layer.msg("出现异常！", {
                icon: 1,
                time: 2000
            });
        },
    });
}










