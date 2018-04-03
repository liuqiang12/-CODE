<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <!-- 引用 -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/framework/jqDock/jquery.jqDock.min.js"></script>
    <title>合同信息列表</title>
</head>
<style type="text/css">
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0 !important;
    }
    table.kv-table{
        margin-bottom: 0 !important;
    }
    .fieldsetCls{
        border-color: #eee !important;
    }
    .fieldsetCls:hover{
        /*增加border变色*/
        border-color: #bbb !important;
    }
    .fieldsetCls:hover > *{
        /*增加border变色*/
        color: #3a3a3a !important;
    }
    .fieldsetCls > *{
        /*增加border变色*/
        color: #1e1e1e !important;
    }
    table.kv-table td.kv-content, table.kv-table td.kv-label{
        padding:0px !important;
        padding-left:6px;
        border:none;
    }
    table.kv-table .kv-label{
        border:none;
    }
    table.kv-table tr:first-child td.kv-content, table.kv-table tr:first-child td.kv-label{
        border: none;
    }
    table.kv-table tr td.kv-content:last-child{
        border: none;
    }
    html {overflow-x:hidden; overflow-y:auto;} /*remove horizontal scroll bar in IE6!*/
    html, body {width:100%; height:100%; margin:0; padding:0; border:0 none;}
    body {padding:0; margin:0; border:0 none; font-family:arial; font-size:12px; color:#333333; background-color:#333333;}
    a, a:link, a:visited, a:hover, a:active {color:#0000ff; text-decoration:none; outline:0 none;}
    img {vertical-align:bottom; border:0 none;}
    div, pre {margin:0; padding:0;}
    pre {font-size:11px; color:#0000cc; border:1px solid #cccccc; width:100%; overflow-x:auto; overflow-y:hidden; padding-bottom:17px;}
    pre span {color:#808080;}
    div.codex {padding:4px 8px 10px 8px;display:none;}
    div.excowrap {padding-top:3px;}
    a.exco, a.exco:link, a.exco:visited, a.exco:hover, a.exco:active {display:block; background:#e8e8ff url(arrows.gif) no-repeat 99% 0; color:#333333; padding:1px 8px;}
    a.exco:hover {background-color:#ccccff; background-position: 99% -20px;}
    a.descFile {padding-right:10px;}
    a.targetBlank {background:transparent url(target_blank.gif) no-repeat 100% 0;}
    #description {background-color:#ffffff; width:620px; margin:0 auto; padding:5px 0;}
    #descInner {margin:0 15px 0 10px;}
    #descHead {text-align:right;}
    #descHead span {float:left;font-size:10px;}
    #descFoot {text-align:center; font-size:11px; padding-top:3px;}
    #description a.descFile:hover {text-decoration:underline;}
    /*position and hide the menu initially - leave room for the menu to expand...*/
    #description {margin-left:150px; width:510px;}
    #page {padding:16px 0; width:100%;}
    #menu {position:absolute; bottom: 0px;
        left: 41px; display:none;}
    /*dock styling...*/
    /*...set the cursor...*/
    #menu div.jqDock {cursor:pointer;}
    /*label styling...*/
    div.jqDockLabel {padding-bottom:8px; font-weight:bold; white-space:nowrap; color:#ffff00; cursor:pointer; padding:0 1px;}
    .fieldsetContent{
        margin-left: 36px;
        margin-right: 5px;
    }
    .fieldsetHeadCls{
        margin:-32px 25px 5px 25px;
    }
    .styCls{
        margin-left: 167px;
        margin-right: 28px;
        margin-top: 25px;
    }
    body{
        background:#cbd4da;
        font-size:12px;
        font-family:Verdana;
        -webkit-text-size-adjust:none;
        background-color: #dfdcd8;
        background-image: -*-radial-gradient(#c8c8c8 15%, transparent 16%), -*-radial-gradient(#c8c8c8 15%, transparent 16%), -*-radial-gradient(rgba(200, 200, 200, 0.1) 15%, transparent 20%), -*-radial-gradient(rgba(200, 200, 200, 0.1) 15%, transparent 20%);
        background-position: 0 0px, 8px 8px, 0 1px, 8px 9px;
        background-size: 16px 16px;
    }

    .tableWrap{
        margin:5px auto;
        padding:0px 0;
    }
    .tableWrap .table{
        width:800px;
        border-style:solid;
        border-width:2px;
        border-radius:12px;
        background:#fff;
        position:relative;
    }
    .tableWrap .table:after{
        content:"";
        position:absolute;
        left:50%;
        top:5%;
        width:51%;
        height:90%;
        box-shadow:8px 0 10px rgba(0, 0, 0, 0.2);
        z-index:-1;
        border-radius:50%;
    }
    .tableWrap .table{
        right: 25px;
        left: 100px;
        top: 15px;
        z-index:10;
        border-color:#bbb;
    }


    .tableWrap .table > span{
        display: inline-block;
        border: solid 2px #bbb;
        border-radius: 50%;
        width: 53px;
        height: 53px;
        background: #fff;
        font-weight: bold;
        position: absolute;
        left: -26px;
        top: -18px;
    }

    .tableWrap .table > span:before{
        display:inline-block;
        font-size:32px;
        padding-top:15px;
        font-family:Arial;
        position:absolute;
        z-index:10;
        left:15px;
        top:-2px;
        color:#f8b22c;
    }
    .tableWrap .table > span:after{
        display:inline-block;
        font-size:10px;
        color:#666;
        text-shadow:0 0 1px #a5a090;
        position:absolute;
        z-index:10;
        top:50px;
        left:12px;
    }


    .tableWrap .table h2{
        text-align:center;
        font-size:15px;
        /*border-bottom:solid 1px #bbb;*/
        padding:8px 0;
        margin:0;
        background:#fff;
        border-radius:0 12px 0 0;
        width:100%;
        position:relative;
        z-index:1;
        color:#3b6c73;
    }

    .tableWrap .table ul{
        list-style:none;
        width:100%;
        padding:0;
        margin:0;
        background:#fff;
    }
    .tableWrap .table li{
        height: 38px;
        line-height: 28px;
        border-top: 1px solid #fff;
        font-size: 12px;
        text-align: center;
        font-weight: bold;
        text-shadow: 0 0 1px #a5a090;
    }
    .tableWrap .table li:nth-child(2n+1){
        background:#eee;
    }
    .tableWrap .table li:nth-child(2n){
        background:#fff;
    }

    .tableWrap .btnbox{
        display:block;
        padding:25px 0;
        border:none;
        border-radius:0 0 12px 12px;
        text-align:center;
        background:#fff;
    }
    .tableWrap .btnbox input{
        background:#a6d154;
        border:none;
        border-radius:25px;
        padding:15px;
        color:#fff;
        font-weight:bold;
        font-family:Arial;
        cursor:pointer;
    }
    .tableWrap .btnbox input:hover{
        background:#f60;
        box-shadow:inset 0 0 2px rgba(0,0,0,.3);
    }
    .serviceCls_{
        position: absolute;
        top: 17px;
        left: 15px;
        z-index: 2;
    }
    .text-head-cls{

    }


</style>
<script type="application/javascript">
    jQuery(document).ready(function($){

        // set up the options to be used for jqDock...
        var dockOptions =
            { align: 'center' // vertical menu, with expansion LEFT/RIGHT from the center
                , labels: 'br'  // add labels (override the 'tl' default)
                , inactivity: 4000 // set inactivity timeout to 4 seconds
            };
        // ...and apply...
        $('#menu').jqDock(dockOptions);
    });

</script>
<body style="background-color: #e9f6fa">
<div id='page'>
    <div id='menu'>
        <c:if test="${serviceApplyImgStatus.rack == 'true'}">
        <img src='<%=request.getContextPath() %>/framework/jqDock/icons/rack.png' title='' alt='' id="rack_img" />
        </c:if>
        <c:if test="${serviceApplyImgStatus.port == 'true'}">
        <img src='<%=request.getContextPath() %>/framework/jqDock/icons/port.png' title='' alt='' id="port_img"  />
        </c:if>
        <c:if test="${serviceApplyImgStatus.ip == 'true'}">
        <img src='<%=request.getContextPath() %>/framework/jqDock/icons/IP.png' title='' alt='' id="ip_img" />
        </c:if>
        <c:if test="${serviceApplyImgStatus.server == 'true'}">
        <img src='<%=request.getContextPath() %>/framework/jqDock/icons/server.png' title='' alt='' id="server_img" />
        </c:if>
        <c:if test="${serviceApplyImgStatus.add == 'true'}">
        <img src='<%=request.getContextPath() %>/framework/jqDock/icons/add.png' title='' alt='' id="add_img" />
        </c:if>
        <img src='<%=request.getContextPath() %>/framework/jqDock/icons/customer.png' title='' alt='' onclick="openWinCustomerIdFun('customerId','customerName')" id="customer_img" />
        <img src='<%=request.getContextPath() %>/framework/jqDock/icons/tikit.png' title='' alt='' id="tikit_img" />
    </div>
</div>

<form method="post" id="singleForm" action="<%=request.getContextPath() %>/resourceApplyController/productFormSaveOrUpdate.do">
    <fieldset class="fieldsetCls fieldsetHeadCls">
        <legend>&diams;客户需求</legend>
        <table class="kv-table">
            <tr>
                <td class="kv-label" style="width: 200px;">订单名称</td>
                <td class="kv-content">
                    <input type="hidden" value="${idcReProduct.id}" name="id"/>
                    <input class="easyui-textbox" data-options="required:true" readonly="readonly" value="${idcReProduct.prodName}" name="prodName" id="prodName" data-options="validType:'length[0,255]',width:150"/>
                </td>
                <td class="kv-label">订单类别</td>
                <td class="kv-content">
                    <input class="easyui-combobox" readonly="readonly"  data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								required:true,
								width:100,
								data: [{
									label: '政企业务',
									value: '1'
								},{
									label: '自有业务',
									value: '0'
								}]" name="prodCategory" id="prodCategory" value="${idcReProduct.prodCategory}" />
                </td>
                <td class="kv-label">所属客户</td>
                <td class="kv-content">
                    <!-- 客户选择框 -->
                    <input type="hidden" name="customerId" value="${idcReProduct.customerId}" id="customerId"/>
                    <input id="customerName"readonly="readonly"   value="${idcReProduct.customerName}" class="easyui-textbox" data-options="editable:false,required:true,iconAlign:'left',buttonText:'选择',onClickButton:openWinCustomerNameFun">
                </td>
            </tr>
        </table>
    </fieldset>
    <img src='<%=request.getContextPath() %>/framework/jqDock/icons/syt.png' title='' alt='' class="styCls" />
    <fieldset class="fieldsetCls fieldsetContent" style="border:  none;display: none">

    </fieldset>


</form>
</body>
<script type="application/javascript">
    $(function(){
        try {
            var rack = "${serviceApplyImgStatus.rack}" || null;
            var port = "${serviceApplyImgStatus.port}"||null;
            var ip = "${serviceApplyImgStatus.ip}"||null;
            var server = "${serviceApplyImgStatus.server}"||null;
            var add = "${serviceApplyImgStatus.add}"||null;

            if(rack && rack == "true"){
                var rack_img = $("#rack_img");
                addServiceItem(rack_img,'100','机架机位');
            }

            if(port&& port == "true"){
                var port_img = $("#port_img");
                addServiceItem(port_img,'200','端口带宽');
            }

            if(ip&& ip == "true"){
                var ip_img = $("#ip_img");
                addServiceItem(ip_img,'300','IP租用');
            }

            if(server&& server == "true"){
                var server_img = $("#server_img");
                addServiceItem(server_img,'400','主机租赁');
            }

            if(add&& add == "true"){
                var add_img = $("#add_img");
                addServiceItem(add_img,'500','增值业务');
            }

        }catch(err) {

        }finally {

        }
    });
    //查看客户基本信息
    function openWinCustomerIdFun(customerId,customerName){
        //弹出form表单查看客户基本信息
        var laySum = top.layer.open({
            type: 2,
            title: '<span style="color:blue">客户人员['+$("#"+customerName).textbox("getValue")+']</span>'+"》详情",
            shadeClose: false,
            shade: 0.8,
            btn: ['关闭'],
            maxmin : true,
            area: ['784px', '600px'],
            content: contextPath+"/customerController/linkQueryWin.do?viewQuery=1&id="+$("#"+customerId).val(),
            cancel: function(index, layero){
                top.layer.close(index);
            },no: function(index, layero){
                top.layer.close(index)
            }
        });
    }
    //选择客户信息
    function openWinCustomerNameFun(){
        //选择弹出框
        var laySum = top.layer.open({
            type: 2,
            title: '<span style="color:blue">客户人员</span>'+"》选择",
            shadeClose: false,
            shade: 0.8,
            btn: ['确定','关闭'],
            maxmin : true,
            area: ['884px', '650px'],
            content: contextPath+"/customerController/customerQuery.do",
            /*弹出框*/
            cancel: function(index, layero){
                top.layer.close(index);
            },yes: function(index, layero){
                var childIframeid = layero.find('iframe')[0]['name'];
                var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                //返回相应的数据
                var selectedRecord = chidlwin.gridSelectedRecord("gridId");
                $("#customerId").val(selectedRecord?selectedRecord['id']:null);
                $("#customerName").textbox('setValue', selectedRecord?selectedRecord['name']:null);
                top.layer.close(index)
                //刷新列表信息
            },no: function(index, layero){
                top.layer.close(index)
            },end:function(){
                //top.layer.close(index);
            }
        });
    }
    /*动态的添加form信息*/
    function addServiceItem(obj,category,text){

        if (category == '100'){
            //添加机架位form表单
            $(".fieldsetContent").css("display","block");
            $(".styCls").css("display","none");
            //点击就消失
            if($(".fieldsetContent").find("#rack_wrap") && $(".fieldsetContent").find("#rack_wrap").length == 0){
                //增加机架机位信息
                var rackHTML =
                    '<div class="tableWrap" id="rack_wrap">'+
                    '        <div class="table table-personal">'+
                    '           <span>'+
                    '               <div class="serviceCls_">服务</div>'+
                    '           </span>'+
                    '           <h2><span class="text-head-cls">机架机位</span></h2>'+
                    '           <ul>'+
                    '               <li>'+
                    '                   产品规格:<input class="easyui-combobox" ' +
                    '   data-options=\"valueField: \'value\',textField: \'label\',editable:false,required:true,width:580,data: [{label: \'开放式机架\',value: \'1\'},{label: \'-------\',value: \'0\'}]" name="idcReRackModel.spec" id="idcReRackModel_spec" readonly="readonly"  value="${idcReRackModel.SPEC}" />'+
                    '                </li>'+
                    '               <li>'+
                    '                  额定功率:<input class="easyui-numberbox" readonly="readonly"  name="idcReRackModel.ratedPower" value="${idcReRackModel.RATEDPOWER}"'+
                    '                 id="idcReRackModel_ratedPower" data-options="width:580,precision:0,suffix:\'千瓦(KW)\',min:0,validType:\'length[0,12]\'"/>'+
                    '</li>'+
                    ' <li>'+
                    '      &nbsp;&nbsp;&nbsp;机架数:<input class="easyui-numberbox" readonly="readonly"  name="idcReRackModel.rackNum" value="${idcReRackModel.RACKNUM}"'+
                    '       id="idcReRackModel_rackNum" data-options="width:580,precision:0,suffix:\'个\',min:0,validType:\'length[0,11]\'"/>'+
                    '    </li>'+
                    '     <li>'+
                    '          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述:<input class="easyui-textbox" readonly="readonly"  value="${idcReRackModel.DESC}"'+
                    '           name="idcReRackModel.desc" id="idcReRackModel_desc"'+
                    '            data-options="multiline:true,width:580,height:28"/>'+
                    '         </li>'+
                    '      </ul>'+
                    '   </div>'+
                    '</div>';
                var targetObj = $(".fieldsetContent").prepend(rackHTML);
                $.parser.parse(targetObj);
                //更换图片
                $(obj).attr("src",contextPath+"/framework/jqDock/icons/rack_ok.png");
            }else{
                $(".fieldsetContent > #rack_wrap").remove();
                //判断是否是最后一个 如果是则显示图标
                if($(".fieldsetContent .tableWrap")  && $(".fieldsetContent .tableWrap").length == 0){
                    $(".fieldsetContent").css("display","none");
                    $(".styCls").css("display","block");
                }
                $(obj).attr("src",contextPath+"/framework/jqDock/icons/rack.png");
            }
        }else if(category == '200'){
            //添加机架位form表单
            $(".fieldsetContent").css("display","block");
            $(".styCls").css("display","none");
            //点击就消失
            if($(".fieldsetContent").find("#port_wrap") && $(".fieldsetContent").find("#port_wrap").length == 0){
                //增加机架机位信息
                var portHTML =
                    '<div class="tableWrap" id="port_wrap">'+
                    '        <div class="table table-personal">'+
                    '                   <span>'+
                    '                   <div class="serviceCls_">服务</div>'+
                    '                   </span>'+
                    '                   <h2><span class="text-head-cls">端口带宽</span></h2>'+
                    '                   <ul>'+
                    '                   <li>'+
                    '                   带宽模式:<input class="easyui-combobox"  readonly="readonly"  data-options="'+
                    '               valueField: \'value\','+
                    '                   textField: \'label\','+
                    '                   editable:false,'+
                    '                   required:true,'+
                    '                   width:580,'+
                    '                   data: [{'+
                    '                   label: \'B类独享\','+
                    '                   value: \'1\''+
                    '               },{'+
                    '                   label: \'-------\','+
                    '                   value: \'0\''+
                    '               }]" name="idcRePortModel.portMode" id="idcRePortModel_portMode" value="${idcRePortModel.PORTMODE}" />'+
                    '               </li>'+
                    '               <li>'+
                    '               带宽大小(兆):<input class="easyui-numberbox" readonly="readonly"  data-options="'+
                    '               valueField: \'value\','+
                    '                   textField: \'label\','+
                    '                   editable:false,'+
                    '                   width:580" name="idcRePortModel.bandwidth" id="idcRePortModel_bandwidth" value="${idcRePortModel.bandwidth}" />'+
                    '               </li>'+
                    '               <li>'+
                    '               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数量:<input readonly="readonly"  class="easyui-numberbox"'+
                    '               name="idcRePortModel.num"'+
                    '               value="${idcRePortModel.NUM}"'+
                    '               id="idcReRackModel_num"'+
                    '               data-options="width:580,precision:0,suffix:\'个\',min:0,validType:\'length[0,11]\'"/>'+
                    '                   </li>'+
                    '                   <li>'+
                    '                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述:<input readonly="readonly"  class="easyui-textbox" value="${idcRePortModel.DESC}"'+
                    '               name="idcRePortModel.desc" id="idcRePortModel_desc"'+
                    '               data-options="multiline:true,width:580,height:28"/>'+
                    '                   </li>'+
                    '                   </ul>'+
                    '                   </div>'+
                    '                   </div>';

                var targetObj = $(".fieldsetContent").prepend(portHTML);
                $.parser.parse(targetObj);
                //更换图片
                $(obj).attr("src",contextPath+"/framework/jqDock/icons/port_ok.png");
            }else{
                $(".fieldsetContent > #port_wrap").remove();
                if($(".fieldsetContent .tableWrap")  && $(".fieldsetContent .tableWrap").length == 0){
                    $(".fieldsetContent").css("display","none");
                    $(".styCls").css("display","block");
                }
                $(obj).attr("src",contextPath+"/framework/jqDock/icons/port.png");
            }
        }else if(category == '300'){
            //添加机架位form表单
            $(".fieldsetContent").css("display","block");
            $(".styCls").css("display","none");
            //点击就消失
            if($(".fieldsetContent").find("#ip_wrap") && $(".fieldsetContent").find("#ip_wrap").length == 0){
                //增加机架机位信息
                var ipHTML =
                    <!-- IP租用 -->
                    '<div class="tableWrap" id="ip_wrap">'+
                    '        <div class="table table-personal">'+
                    '               <span>'+
                    '               <div class="serviceCls_">服务</div>'+
                    '               </span>'+
                    '               <h2><span class="text-head-cls">IP租用</span></h2>'+
                    '               <ul>'+
                    '               <li>'+
                    '               <input class="easyui-combobox" readonly="readonly"  data-options="'+
                    '           valueField: \'value\','+
                    '               textField: \'label\','+
                    '               editable:false,'+
                    '               required:true,'+
                    '               width:580,'+
                    '               data: [{'+
                    '               label: \'静态\','+
                    '               value: \'1\''+
                    '           },{'+
                    '               label: \'动态\','+
                    '               value: \'2\''+
                    '           },{'+
                    '               label: \'保留\','+
                    '               value: \'3\''+
                    '           }]" name="idcReIpModel.portMode" id="idcReIpModel_portMode" value="${idcReIpModel.PORTMODE}" />'+
                    '           </li>'+
                    '           <li>'+
                    '           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数量:<input readonly="readonly"  class="easyui-numberbox"'+
                    '           name="idcReIpModel.num"'+
                    '           value="${idcReIpModel.NUM}"'+
                    '           id="idcReIpModel_num"'+
                    '           data-options="width:580,precision:0,suffix:\'个\',min:0,validType:\'length[0,11]\'"/>'+
                    '               </li>'+
                    '               <li>'+
                    '               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述:<input readonly="readonly"  class="easyui-textbox" value="${idcReIpModel.DESC}"'+
                    '           name="idcReIpModel.desc" id="idcReIpModel_desc"'+
                    '           data-options="multiline:true,width:580,height:28"/>'+
                    '               </li>'+
                    '               <li></li>'+
                    '               </ul>'+
                    '               </div>'+
                    '               </div>';
                var targetObj = $(".fieldsetContent").prepend(ipHTML);
                $.parser.parse(targetObj);
                //更换图片
                $(obj).attr("src",contextPath+"/framework/jqDock/icons/IP_ok.png");
            }else{
                $(".fieldsetContent > #ip_wrap").remove();
                $(obj).attr("src",contextPath+"/framework/jqDock/icons/IP.png");
                if($(".fieldsetContent .tableWrap")  && $(".fieldsetContent .tableWrap").length == 0){
                    $(".fieldsetContent").css("display","none");
                    $(".styCls").css("display","block");
                }
            }
        }else if(category == '400'){
            $(".fieldsetContent").css("display","block");
            $(".styCls").css("display","none");
            //点击就消失
            if($(".fieldsetContent").find("#zjserver_wrap") && $(".fieldsetContent").find("#zjserver_wrap").length == 0){

                var serverHTML =
                    '  <div class="tableWrap" id="zjserver_wrap">'+
                    '          <div class="table table-personal">'+
                    '                   <span>'+
                    '                   <div class="serviceCls_">服务</div>'+
                    '                   </span>'+
                    '                   <h2><span class="text-head-cls">主机租赁</span></h2>'+
                    '                   <ul>'+
                    '                   <li>'+
                    '                   资源类型:<input readonly="readonly"  class="easyui-combobox" data-options="'+
                    '               valueField: \'value\','+
                    '                   textField: \'label\','+
                    '                   editable:false,'+
                    '                   required:true,'+
                    '                   width:580,'+
                    '                   data: [{'+
                    '                   label: \'----\','+
                    '                   value: \'1\''+
                    '               },{'+
                    '                   label: \'XXXX\','+
                    '                   value: \'2\''+
                    '               }]" name="idcReServerModel.typeMode" id="idcReServerModel_typeMode" value="${idcReServerModel.typeMode}" />'+
                    '               </li>'+
                    '               <li>'+
                    '               设备型号:<input class="easyui-textbox" readonly="readonly"  value="${idcReServerModel.specNumber}"'+
                    '               name="idcReServerModel.specNumber" id="idcReServerModel_specNumber"'+
                    '               data-options="width:580"/>'+
                    '                   </li>'+
                    '                   <li>'+
                    '                   设备编码:<input class="easyui-textbox" readonly="readonly"  value="${idcReServerModel.code}"'+
                    '               name="idcReServerModel.code" id="idcReServerModel_code"'+
                    '               data-options="width:580"/>'+
                    '                   </li>'+
                    '                   <li>'+
                    '                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;品牌:<input readonly="readonly"  class="easyui-textbox" value="${idcReServerModel.brand}"'+
                    '               name="idcReServerModel.brand" id="idcReServerModel_brand"'+
                    '               data-options="width:580"/>'+
                    '                   </li>'+
                    '                   <li>'+
                    '                   额定功率:<input class="easyui-numberbox" readonly="readonly"  name="idcReServerModel.ratedPower" value="${idcReServerModel.ratedPower}"'+
                    '               id="idcReServerModel_ratedPower" data-options="width:580,precision:0,suffix:\'千瓦(KW)\',min:0,validType:\'length[0,12]\'"/>'+
                    '                   </li>'+
                    '                   <li>'+
                    '                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数量:<input readonly="readonly"  class="easyui-numberbox"'+
                    '               name="idcReServerModel.num"'+
                    '               value="${idcReServerModel.num}"'+
                    '               id="idcReServerModel_num"'+
                    '               data-options="width:580,precision:0,suffix:\'个\',min:0,validType:\'length[0,11]\'"/>'+
                    '                   </li>'+
                    '                   <li>'+
                    '                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述:<input readonly="readonly"  class="easyui-textbox" value="${idcReServerModel.desc}"'+
                    '               name="idcReServerModel.desc" id="idcReServerModel_desc"'+
                    '               data-options="multiline:true,width:580,height:28"/>'+
                    '                   </li>'+
                    '                   </ul>'+
                    '                   </div>'+
                    '                   </div>';

                var targetObj = $(".fieldsetContent").prepend(serverHTML);
                $.parser.parse(targetObj);
                //更换图片
                $(obj).attr("src",contextPath+"/framework/jqDock/icons/server_ok.png");
            }else{
                $(".fieldsetContent > #zjserver_wrap").remove();

                $(obj).attr("src",contextPath+"/framework/jqDock/icons/server.png");

                if($(".fieldsetContent .tableWrap")  && $(".fieldsetContent .tableWrap").length == 0){
                    $(".fieldsetContent").css("display","none");
                    $(".styCls").css("display","block");
                }

            }
        }else if(category == '500'){
            $(".fieldsetContent").css("display","block");
            $(".styCls").css("display","none");
            //点击就消失
            if($(".fieldsetContent").find("#add_wrap") && $(".fieldsetContent").find("#add_wrap").length == 0){

                var addHTML =
                    '   <div class="tableWrap" id="add_wrap">'+
                    '           <div class="table table-personal">'+
                    '                       <span>'+
                    '                       <div class="serviceCls_">服务</div>'+
                    '                       </span>'+
                    '                       <h2><span class="text-head-cls">增值业务</span></h2>'+
                    '                       <ul>'+
                    '                       <li>'+
                    '                       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名称:<input readonly="readonly"  class="easyui-textbox" value="${idcReNewlyModel.NAME}"'+
                    '                   name="idcReNewlyModel.name" id="idcReNewlyModel_name"'+
                    '                   data-options="width:580"/>'+
                    '                       </li>'+
                    '                       <li>'+
                    '                       业务分类:<input class="easyui-combobox" readonly="readonly"  data-options="'+
                    '                   valueField: \'value\','+
                    '                       textField: \'label\','+
                    '                       editable:false,'+
                    '                       width:580,'+
                    '                       data: [{'+
                    '                       label: \'基础应用\','+
                    '                       value: \'1\''+
                    '                   },{'+
                    '                       label: \'----\','+
                    '                       value: \'2\''+
                    '                   }]" name="idcReNewlyModel.category" readonly="readonly"  id="idcReNewlyModel_category" value="${idcReNewlyModel.CATEGORY}" />'+
                    '                   </li>'+
                    '                   <li>'+
                    '                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;描述:<input readonly="readonly"  class="easyui-textbox" value="${idcReNewlyModel.DESC}"'+
                    '                   name="idcReNewlyModel.desc" id="idcReNewlyModel_desc"'+
                    '                   data-options="multiline:true,width:580,height:28"/>'+
                    '                       </li>'+
                    '                       <li></li>'+
                    '                       </ul>'+
                    '                       </div>'+
                    '                       </div>  ';
                var targetObj = $(".fieldsetContent").prepend(addHTML);
                $.parser.parse(targetObj);
                //更换图片
                $(obj).attr("src",contextPath+"/framework/jqDock/icons/add_ok.png");
            }else{
                $(".fieldsetContent > #add_wrap").remove();

                $(obj).attr("src",contextPath+"/framework/jqDock/icons/add.png");

                if($(".fieldsetContent .tableWrap")  && $(".fieldsetContent .tableWrap").length == 0){
                    $(".fieldsetContent").css("display","none");
                    $(".styCls").css("display","block");
                }
            }
        }
    }
</script>
</html>