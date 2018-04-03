$(function(){

    $("#fuwuNetServiceId").parents(".checkbackgrd").removeClass("on_check");
    $("#qtNetServiceId").parents(".checkbackgrd").addClass("on_check");
    otherAdd();

    $(".checkbackgrd").bind("click",function(){
        if(!$(this).hasClass("disabled_check")){
            //获取name属性
            var nameAttr = $(this).find("input:checkbox").attr("name");
            console.log((nameAttr));
            console.log((this.id));
            if(nameAttr == 'domainStatus'){
                $("input[name='"+nameAttr+"']").parents(".checkbackgrd").removeClass("on_check");

                $(this).hasClass("on_check")?$(this).removeClass("on_check"):$(this).addClass("on_check");

                var status = $(".checkbackgrd.on_check input[name='domainStatus']:checkbox").val();

                try {
                    $("#idcNetServiceinfo_domainStatus").val(status);
                    //$("input#idcRunProcCment_status_stand").val(status);
                }catch (e){}
            }
            if(this.id == 'dns_check'){
                console.log(this.id);
                dnsAdd()
            }else if(this.id == 'other_check'){
                otherAdd();
            }
        }
    });

});

function otherAdd(){
    $("#contract_content").empty();
    $("#vm_content").empty();
    var fieldsetHTML ='<fieldset class="fieldsetCls fieldsetHeadCls">'+
        '<legend>&diams;服务信息</legend>'+
        ' <table class="kv-table">'+
        '  <tr>'+
        '   <td class="kv-label" style="width:215px">服务开通时间<span style="color:red">&nbsp;*</span></td>'+
        ' <td class="kv-content" colspan="3">'+
        '   <input class="easyui-datetimebox"  name="idcNetServiceinfo.openTime" value="" data-options="editable:false,showSeconds:true,width:350">'+
        '    </td>'+
        '    </tr>'+
        '   </table>'+
        '    </fieldset>';
    var targetObj = $("#contract_content").prepend(fieldsetHTML);
    $.parser.parse(targetObj);
}


function dnsAdd(){
    var customerName = $("#customerName").val();
    $("#contract_content").empty();
    var fieldsetHTML = '<fieldset class="fieldsetCls fieldsetHeadCls">'+
        '<legend>&diams;服务信息</legend>'+
        '<table class="kv-table">'+
        '<tr>'+
        '<td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span></td>'+
        ' <td class="kv-content">'+
        ' <input type="hidden" name="idcNetServiceinfo.id" value="" id="idcNetServiceinfo.id"/>'+
        ' <input name="idcNetServiceinfo.ticketInstId" type="hidden" value="">'+
        '  <input class="easyui-textbox" name="idcNetServiceinfo.icpsrvname" value=""  id="idcNetServiceinfo.icpsrvname" data-options="validType:\'length[0,64]\',width:250"/>'+
        '  </td>'+
        '  <td class="kv-label">所属用户</td>'+
        '   <td class="kv-content">'+
        '   <input name="idcNetServiceinfo.customerId" value="" type="hidden" />'+
        '  <input class="easyui-textbox" readonly="readonly" name="idcNetServiceinfo.customerName" value="'+customerName+'"  id="idcNetServiceinfo.customerName" data-options="validType:\'length[0,128]\',width:250"/>'+

        '   </td>'+
        '   </tr>'+

        '   <tr>'+
        '   <td class="kv-label">服务内容<span style="color:red">&nbsp;*</span></td>'+
        '<td class="kv-content">'+
        '<div>  <ul id="ttFWNR" checkbox="true" data-options="lines:true,width:250" value=""  name="idcNetServiceinfo.icpsrvcontentcode" id="idcNetServiceinfo.icpsrvcontentcode"></ul>  </div> ' +

        '</td>'+
        '<td class="kv-label">备案类型<span style="color:red">&nbsp;*</span></td>'+
        '<td class="kv-content">'+
        '    <input class="easyui-combobox" data-options="'+
        'valueField: \'value\','+
        '    textField: \'label\','+
        '    editable:false,'+
        '   width:250,'+
        '   data: [{'+
        '   label: \'无\','+
        '   value: \'0\''+
        '},{'+
        '    label: \'经营性网站\','+
        '   value: \'1\''+
        '},{'+
        '    label: \'非经营性网站\','+
        '   value: \'2\''+
        '},{'+
        '    label: \'SP\','+
        '     value: \'3\''+
        ' },{'+
        '    label: \'BBS\','+
        '    value: \'4\''+
        ' },{'+
        '      label: \'其它\','+
        '      value: \'999\''+
        '  }]" value=""  name="idcNetServiceinfo.icprecordtype" id="idcNetServiceinfo.icprecordtype"/>'+
        '  </td>'+
        '   </tr>'+
        '   <tr>'+
        '  <td class="kv-label">备案号[许可证号]<span style="color:red">&nbsp;*</span></td>'+
        ' <td class="kv-content">'+
        '   <input class="easyui-textbox" name="idcNetServiceinfo.icprecordno" value=""  id="idcNetServiceinfo.icprecordno" data-options="validType:\'length[0,4000]\',width:250"/>'+
        '   </td>'+
        '   <td class="kv-label">接入方式<span style="color:red">&nbsp;*</span></td>'+
        '<td class="kv-content">'+
        '   <input class="easyui-combobox" data-options="'+
        'valueField: \'value\','+
        '   textField: \'label\','+
        '   width:250,'+
        '   editable:false,'+
        '   onChange:icpaccesstypeOnChange,'+
        '   data: [{'+
        '   label: \'专线\','+
        '   value: \'0\''+
        '},{'+
        '   label: \'虚拟主机\','+
        '   value: \'1\''+
        '   },{'+
        '    label: \'主机托管\','+
        '      value: \'2\''+
        '     },{'+
        '      label: \'整机租用\','+
        '       value: \'3\''+
        '   },{'+
        '       label: \'其它\','+
        '      value: \'999\''+
        '  }]" value=""  name="idcNetServiceinfo.icpaccesstype" id="idcNetServiceinfo.icpaccesstype"/>'+
        '  </td>'+
        '   </tr>'+
        '   <tr>'+
        '   <td class="kv-label">域名信息<span style="color:red">&nbsp;*</span></td>'+
        '   <td class="kv-content">'+
        '     <input class="easyui-textbox" name="idcNetServiceinfo.icpdomain" value=""  id="idcNetServiceinfo.icpdomain" data-options="validType:\'length[0,4000]\',width:250"/>'+
        '     </td>'+
        '     <td class="kv-label">业务类型<span style="color:red">&nbsp;*</span></td>'+
        '  <td class="kv-content">'+
        '      <input class="easyui-combobox" data-options="'+
        '  valueField: \'value\','+
        '     textField: \'label\','+
        '     width:250,'+
        '    editable:false,'+
        '    data: [{'+
        '     label: \'IDC业务\','+
        '     value: \'1\''+
        '        },{'+
        '       label: \'ISP业务\','+
        '       value: \'2\''+
        '    }]" value=""  name="idcNetServiceinfo.icpbusinesstype" id="idcNetServiceinfo.icpbusinesstype"/>'+
        '    </td>'+
        '   </tr>'+
        ' </table>'+
        '</fieldset>';

    var targetObj = $("#contract_content").prepend(fieldsetHTML);
    $.parser.parse(targetObj);
    /*如果选择的是虚拟机*/
    if("${idcNetServiceinfo.icpaccesstype}" == 1){
        $("#vm_content").empty();
        var fieldsetHTML = '<fieldset class="fieldsetCls fieldsetHeadCls">'+
            '<legend>&diams;虚拟机信息</legend>'+
            '    <table class="kv-table">'+
            '       <tr>'+
            '       <td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmName" value=""  id="idcNetServiceinfo.vmName" data-options="validType:\'length[0,128]\',width:200"/>'+
            '       </td>'+
            '       <td class="kv-label">状态<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       editable:false,'+
            '       width:200,'+
            '       data: [{'+
            '       label: \'可用\','+
            '       value: \'1\''+
            '   },{'+
            '       label: \'不可用\','+
            '       value: \'-1\''+
            '   }]" value=""  name="idcNetServiceinfo.vmStatus" id="idcNetServiceinfo.vmStatus"/>'+
            '   </td>'+
            '   </tr>'+
            '   <tr>'+
            '   <td class="kv-label">类型<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       width:200,'+
            '       editable:false,'+
            '       data: [{'+
            '       label: \'共享式\','+
            '       value: \'1\''+
            '   },{'+
            '       label: \'专用式\','+
            '       value: \'2\''+
            '   },{'+
            '       label: \'云虚拟\','+
            '       value: \'3\''+
            '   }]" value=""  name="idcNetServiceinfo.vmCategory" id="idcNetServiceinfo.vmCategory"/>'+

            '   </td>'+
            '   <td class="kv-label">网络地址<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <!-- idcNetServiceinfo.vmNetAddr -->'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmNetAddr" value=""  id="idcNetServiceinfo.vmNetAddr" data-options="validType:\'length[0,128]\',width:200"/>'+
            '       </td>'+
            '       </tr>'+
            '       <tr>'+
            '       <td class="kv-label">管理地址<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmManagerAddr" value=""  id="idcNetServiceinfo.vmManagerAddr" data-options="validType:\'length[0,128]\',width:200"/>'+
            '       </td>'+
            '       <td class="kv-label"></td>'+
            '       <td class="kv-content">'+
            '       </td>'+
            '       </tr>'+
            '   </table>'+
            '</fieldset>';
        var targetObj = $("#vm_content").prepend(fieldsetHTML);
        $.parser.parse(targetObj);
    }

    $('#ttFWNR').combotree({
        multiple:true,
        data:[],
        url:contextPath+"/service.json",
        method:'get',
        onClick:function(data){
            var t = $('#tt').combotree('tree');	// get the tree object
            var n = t.tree('getSelected');		// get selected node
        }
    });
}

function icpaccesstypeOnChange(newValue,oldValue){
    $("#vm_content").empty();
    if(newValue != oldValue && newValue == '1'){
        //虚拟主机
        var fieldsetHTML = '<fieldset class="fieldsetCls fieldsetHeadCls">'+
            '<legend>&diams;虚拟机信息</legend>'+
            '    <table class="kv-table">'+
            '       <tr>'+
            '       <td class="kv-label" style="width: 200px;">名称<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmName" value=""  id="idcNetServiceinfo.vmName" data-options="validType:\'length[0,128]\',width:150"/>'+
            '       </td>'+
            '       <td class="kv-label">状态<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       editable:false,'+
            '       width:150,'+
            '       data: [{'+
            '       label: \'可用\','+
            '       value: \'1\''+
            '   },{'+
            '       label: \'不可用\','+
            '       value: \'-1\''+
            '   }]" value=""  name="idcNetServiceinfo.vmStatus" id="idcNetServiceinfo.vmStatus"/>'+
            '   </td>'+
            '   </tr>'+
            '   <tr>'+
            '   <td class="kv-label">类型<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-combobox" data-options="'+
            '   valueField: \'value\','+
            '       textField: \'label\','+
            '       width:150,'+
            '       editable:false,'+
            '       data: [{'+
            '       label: \'共享式\','+
            '       value: \'1\''+
            '   },{'+
            '       label: \'专用式\','+
            '       value: \'2\''+
            '   },{'+
            '       label: \'云虚拟\','+
            '       value: \'3\''+
            '   }]" value=""  name="idcNetServiceinfo.vmCategory" id="idcNetServiceinfo.vmCategory"/>'+

            '   </td>'+
            '   <td class="kv-label">网络地址<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <!-- idcNetServiceinfo.vmNetAddr -->'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmNetAddr" value=""  id="idcNetServiceinfo.vmNetAddr" data-options="validType:\'length[0,128]\',width:150"/>'+
            '       </td>'+
            '       </tr>'+
            '       <tr>'+
            '       <td class="kv-label">管理地址<span style="color:red">&nbsp;*</span></td>'+
            '       <td class="kv-content">'+
            '       <input class="easyui-textbox" name="idcNetServiceinfo.vmManagerAddr" value=""  id="idcNetServiceinfo.vmManagerAddr" data-options="validType:\'length[0,128]\',width:150"/>'+
            '       </td>'+
            '       <td class="kv-label"></td>'+
            '       <td class="kv-content">'+
            '       </td>'+
            '       </tr>'+
            '   </table>'+
            '</fieldset>';
        var targetObj = $("#vm_content").prepend(fieldsetHTML);
        $.parser.parse(targetObj);
    }else{
        $("#vm_content").empty();
    }
}




