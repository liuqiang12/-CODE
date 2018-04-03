/*
 * 有关流程模型的所有js都在这里
 */
var category = $("#category_param").val();
$(function() {
        if(rack && rack == "true"){
            var params = getRequestRackParams();
            loadRackGrid("ticket_rack_gridId",params);
        }
});
/*选择机架*/
function preRackChoiceOperate(gridId,rackOrracunit,ticketInstId,prodInstId){
    //弹出框选择机架相关信息
    var rackOrracunit=$("#idcReRackModel_rackOrracunit").val();
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">机架</span>'+"》选择",
        shadeClose: false,
        shade: 0.8,
        btn: ['确定','关闭'],
        maxmin : true,
        area: ['1024px', '650px'],
        content: contextPath+"/winOpenController/rackGridPanel.do?rackOrracunit="+rackOrracunit,
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            //直接调用ajax保存信息
            var records = chidlwin.getSelectionData();
            /*判断是否是已经选中*/

            if(records && records.length == 0){
                /*弹出提示框*/
                top.layer.msg('没有选择记录', {
                    icon: 2,
                    time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                }, function(){
                    //do something
                });
                return false;
            }
            var sets = [];
            for(var i = 0 ; i < records.length ; i++){
                var singleRecordData = records[i];
                var srd = {};
                srd['ticketInstId'] = $("#ticketInstId_param").val();
                srd['resourceid'] = singleRecordData;
                srd['status'] = 1;/*新增*/
                srd['manual']= 1;//手工添加方式
                srd['category']= 100;//手工添加方式:机架机位
                srd['ticketCategory']=category;//预勘/预占
                sets.push(srd);


            }
            var customerId=$("#customerId").val();
            var prodInstId=$("#prodInstId_param").val();
            var customerName=$("#customerName").val();
            var ticketInstId=$("#ticketInstId_param").val();
            var ticketCategory=$("#category_param").val();
            var rackOrracunit=$("#idcReRackModel_rackOrracunit").val();
            //"rackOrracunit"是分配机架机位专用判断的。如果是按照机架分配，就不显示配u位 ,66002代表安装u位（机位）分配，66001代表机架分配
            //"resourceCategory"代表分配方式，100机架;200端口带宽;300ip租用;400主机租赁;500增值业务;600机位（u位）
            var rackunit=$("#idcReRackModel_rackOrracunit").val();
            $.ajax({
                type:"POST",
                url:contextPath+"/actJBPMController/createTicketServer.do",
                data:{winServerVoListStr:JSON.stringify(sets),
                    "customerId":customerId,
                    "customerName":customerName,
                    "prodInstId":prodInstId,
                    "ticketInstId":ticketInstId,
                    "ticketCategory":ticketCategory,
                    "rackOrracunit":rackOrracunit,
                    "resourceCategory":100
                },
                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                beforeSend:function(){
                },
                success:function(data){
                    var obj = jQuery.parseJSON(data);
                    top.layer.msg(obj.msg, {
                        icon: 1,
                        time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                    });
                    //var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
                    top.layer.close(index);
                    $("#"+gridId).datagrid("reload");
                    $("#ticket_rackmcb_gridId").datagrid("reload");
                },
                complete: function(XMLHttpRequest, textStatus){
                },
                error: function(){
                }
            });
        }
    });
}
function getRequestRackParams(){
    var rackName_params = $("#rackName_params").textbox("getValue");
    var isProcessEnd_param = $("#isProcessEnd_param").val();
    var is_author_apply_show = $("#is_author_apply_show").val();

    var isRubbish_param = $("#isRubbish_param").val();
    /*此时需要做处理*/
    /*参数组装*/
    var params = {};
    params['rackName'] = rackName_params;
    params['isProcessEnd'] = isProcessEnd_param;
    params['is_author_apply_show'] = is_author_apply_show;
    params['isRubbish'] = isRubbish_param;
    return params;
}
function loadRackGrid(gridId,params){

    if(!params){
        params = getRequestRackParams();
    }
    //查询信息机架列表
    $('#'+gridId).datagrid({
        /*url:contextPath+'/actJBPMController/ticketRackResourceQueryData/'+$("#ticketInstId_param").val()+'/100',*/
        url:contextPath+'/actJBPMController/ticketRackResourceGridQueryData/'+$("#ticketInstId_param").val()+'/100',
        title: '机架机位订单,选择可用的客户机架的同时体现MCB资源的信息...',
        iconCls:'',
        queryParams: params,
        toolbar: '#requestParamSettins_ticket_rack_gridId',
        border: false,
        singleSelect: true,
        striped : true,
        pagination: true,
        pageSize: 7,
        pageList:[7,10],
        rownumbers: true,
        fitColumns: false,
        autoRowHeight:true,
        columns: [
            [
                /*{field: 'rackName', title: '机架名称',halign:'left',width:210,formatter:fmtHxNameCss},*/
                {field: 'ticketRackGridObj.targetName', title: '机架名称',halign:'left',width:210,formatter:targetNameFmt },
                {field: 'ticketRackGridObj.belongName', title:'所属机房',halign:'left',width:170,formatter:belongNameFmt },
                {field: 'ticketRackGridObj.uheight', title:'U位数',halign:'left',width:70,formatter:uheightFmt },
                {field: 'ticketRackGridObj.usedRackUnIt', title:'占U位',halign:'left',width:150 ,formatter:usedRackUnItFmt},
                {field: 'ticketRackGridObj.pduPowertype', title: '电源类型',halign:'left',width:80,formatter:pduPowertypeFmt },
                {field: 'statusName', title: '状态',halign:'left',width:80  },
                {field: 'ticketRackGridObj.ratedelectricenergy', title: '额定电量',halign:'left',width:80,formatter:ratedelectricenergyFmt },
                {field: 'null',hidden : $("#isShowGridColumnHandlerFlag").val() == "true"?true:false, title: '占位用',halign:'left',width:250,formatter:placeholderUse},
                /*{field: 'operate' , title: '操作',width:100,halign:'center',formatter:fmtOperateRackAction }*/
                {field: 'operate',hidden : $("#isShowGridColumnHandlerFlag").val() == "true"?false:true, title: '操作',width:250,halign:'center',formatter:fmtOperateRackAction }
            ]
        ],
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
        }
    });
}
function targetNameFmt(value,row,index){
    console.log(row);
    return row.ticketRackGridObj.targetName;
}
function belongNameFmt(value,row,index){
    return row.ticketRackGridObj.belongName;
}
function uheightFmt(value,row,index){
    return row.ticketRackGridObj.uheight;
}
function usedRackUnItFmt(value,row,index){
    return row.ticketRackGridObj.usedRackUnIt;
}
function pduPowertypeFmt(value,row,index){
    return row.ticketRackGridObj.pduPowertype;
}
function ratedelectricenergyFmt(value,row,index){
    return row.ticketRackGridObj.ratedelectricenergy;
}
function placeholderUse(value,row,index){
    var html = value;
    return html = '<span style="color: #0e0e0e">占位用</span>';
}
/**
 * 操作
 * @param value
 * @param row
 * @param index
 * @returns {string}
 */
function  fmtOperateRackAction(value,row,index) {
    var insert= [];
    //订单ID
    var id = row.id;/*工单资源中间表ID*/
    var rackId = row.rackId;/*机架ID*/

    var ticketInstId = row.ticketInstId;/*工单id*/
    /*机房id*/
    var roomId = row.machineroomId;
    console.log(">>>>>>>列的数据:"+row);
    var status = row.status;/*资源状态信息*/
    /*其实就只需要中间表id和id和设备id*/
    /* 目前只有申请的时候才能修改和删除，即：form是pre_accept_apply_form */
     if(status == -1){
        //insert.push('<a class="easyui-linkbutton " style="margin-left: 28px;" title="可以撤销" data-options="plain:true,iconCls:\'icon-kycx\'" onclick="singleRackCxRow('+id+',\''+rackId+'\',\''+roomId+'\')"></a> ');
    }else{
        insert.push('<a class="easyui-linkbutton " title="配动力" data-options="plain:true,iconCls:\'icon-dlg\'" onclick="singleMCBSettingsRow('+id+',\''+rackId+'\',\''+roomId+'\')">配动力</a> ');

        //判断，如果是按照机架分配，就不显示配u位 ,66002代表安装u位（机位）分配
         var rackOrracunit=$("#idcReRackModel_rackOrracunit").val();
         if(rackOrracunit == 66002){
             insert.push('<a class="easyui-linkbutton " title="配U位"  data-options="plain:true,iconCls:\'icon-jw\'" onclick="idcRackunitSettingsRow('+id+',\''+rackId+'\',\''+roomId+'\')">配U位</a> ');
         }

        insert.push('<a class="easyui-linkbutton " title="删除"   data-options="plain:true,iconCls:\'icon-scqd\'" onclick="singleRackDeleteRow('+id+',\''+rackId+'\',\''+roomId+'\',\''+rackOrracunit+'\',\''+ticketInstId+'\')">删除</a> ');
    }
    return insert.join('');
}
/**
 * 配置MCB信息
 * @param id
 * @param rackId
 */

//配置u位的方法
/*================配置u位的方法开始=======================*/
function idcRackunitSettingsRow(id,rackId,roomId){
    //弹出相应的对话框

    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">配置U位</span>',
        shadeClose: false,
        shade: 0.8,
        btn: ['确定','关闭'],
        maxmin : true,
        area: ['1024px', '700px'],

        content: contextPath+"/idcRackunit/preQueryRackunitList?rackId="+rackId,
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes:function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            //返回相应的数据
            //保存form信息,提示是否关闭窗口
            var data=chidlwin.doSubmit();

            //判断，如果是按照机架分配，就不显示配u位 ,66002代表安装u位（机位）分配，66001代表机架分配
            var customerId=$("#customerId").val();
            var prodInstId=$("#prodInstId_param").val();
            var customerName=$("#customerName").val();
            var ticketInstId=$("#ticketInstId_param").val();
            var ticketCategory=$("#category_param").val();
            var rackOrracunit=$("#idcReRackModel_rackOrracunit").val();

            //调用ajax保持选择的u位的id
            $.ajax({
                type:"POST",
                url:contextPath+"/actJBPMController/createTicketServer.do",
                data:{winServerVoListStr:JSON.stringify(data),
                    "customerId":customerId,
                    "prodInstId":prodInstId,
                    "customerName":customerName,
                    "resourceCategory":600,
                    "ticketInstId":ticketInstId,
                    "ticketCategory":ticketCategory,
                    "rackOrracunit":rackOrracunit},
                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                beforeSend:function(){
                },
                success:function(data){
                    var obj = jQuery.parseJSON(data);
                    top.layer.msg(obj.msg, {
                        icon: 1,
                        time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
                    });
                    //var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
                    top.layer.close(index);
                    $("#ticket_rack_gridId").datagrid("reload");
                    var params = getRequestRackParams();
                    loadRackGrid("ticket_rack_gridId",params);
                },
                complete: function(XMLHttpRequest, textStatus){
                },
                error: function(){
                }
            });
        }
    });
}
/*================配置u位的方法结束=======================*/

function singleMCBSettingsRow(id,rackId,roomId){
    //弹出相应的对话框
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:red">配置动力柜</span>',
        shadeClose: false,
        shade: 0.8,
        btn: ['确定','关闭'],
        maxmin : true,
        area: ['1024px', '650px'],

        content: contextPath+"/idcRack/preDistributionPdfRackList?roomId="+roomId+"&rackId="+rackId,
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes:function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            //返回相应的数据
            //保存form信息,提示是否关闭窗口
            chidlwin.doSubmit();
            top.layer.close(index);
            $("#ticket_rackmcb_gridId").datagrid("reload");
        }
    });
}
/**
 *
 * @param id
 * @param rackId
 */
function singleRackDeleteRow(id,rackId,roomId,rackOrracunit,ticketInstId) {
    var prodInstId=$("#prodInstId_param").val();
    var ticketInstId=$("#ticketInstId_param").val();
    var ticketCategory=$("#category_param").val();

    /*ajax*/
    top.layer.confirm('你确定要移除所选机架吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/actJBPMController/deleteTicketResource.do",
            //提交的数据
            //"resourceCategory"代表分配方式，100机架;200端口带宽;300ip租用;400主机租赁;500增值业务;600机位（u位）
            data:{
                'ticketInstId' :ticketInstId,
                'primaryId' : id
            },
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
            },
            //成功返回之后调用的函数
            success:function(data){
                //提示删除成功
                //提示框
                layer.msg(data.msg, {
                    icon: 1,
                    time: 3000 // （如果不配置，默认是3秒）
                }, function(){
                    //do something
                });
                top.layer.close(index);
                $("#ticket_rack_gridId").datagrid("reload");
                $("#ticket_rackmcb_gridId").datagrid("reload");
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
            },
            //调用出错执行的函数
            error: function(){
                //请求出错处理
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
function findRackIsHaveUnit(){
    var isHaveUnitNotSettingRack=false;
    $.ajax({
        url:contextPath+"/actJBPMController/findRackIsHaveUnit.do",
        async:false,  /*意思是关闭ajax 的异步，改为同步*/
        type:'POST',
        data:{"ticketInstId":$("#ticketInstId_param").val()},
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
