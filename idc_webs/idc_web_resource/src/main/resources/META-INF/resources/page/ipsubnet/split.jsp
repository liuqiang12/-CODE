<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <style>
        table.yes-not.kv-table td.kv-label {
            width: 130px
        }

        table.kv-table {
            margin-bottom: 20px
        }

        table.kv-table td.kv-label {
            height: 26px;
            font-size: 12px
        }

        table.kv-table td.kv-content {
            height: 26px;
            font-size: 12px
        }

        table.kv-table td.kv-content a {
            color: #1da02b;
            text-decoration: none
        }

        table.kv-table td.kv-content a:hover {
            text-decoration: underline
        }

        .kv-table {
            border-right: 1px solid #cacaca \9;
            *border-right: 1px solid #cacaca
        }

        .kv-table .kv-table-row {
            border-bottom: 1px solid #cacaca
        }

        .kv-table .kv-table-row .kv-item {
            padding-left: 134px
        }

        .kv-table .kv-table-row .kv-item .kv-label {
            float: left;
            padding: 0 10px;
            margin-left: -134px;
            width: 112px;
            background: #f5f5f5;
            border: 1px solid #cacaca;
            border-bottom: none;
            border-top: none
        }

        .kv-table .kv-table-row .kv-item .kv-content-wrap {
            float: left;
            width: 100%
        }

        .kv-table .kv-table-row .kv-item .kv-content {
            padding: 10px
        }

        .kv-table .kv-table-row.col-3 .kv-item-wrap {
            float: left;
            width: 33.33%
        }

        .kv-table .kv-table-row.col-2 .kv-item-wrap {
            float: left;
            width: 33.33%
        }

        table.kv-table {
            width: 100%
        }

        table.kv-table .kv-label {
            padding: 0 10px;
            width: 114px;
            background: #f5f5f5;
            border: 1px solid #cacaca;
            border-top: none
        }

        table.kv-table td.kv-content,
        table.kv-table td.kv-label {
            height: 29px;
            padding: 5px 0;
            border-bottom: 1px solid #cacaca;
            font-size: 14px;
            padding-left: 20px;
        }

        table.kv-table tr:first-child td.kv-content,
        table.kv-table tr:first-child td.kv-label {
            border-top: 1px solid #cacaca;
        }

        table.kv-table tr td.kv-content:last-child {
            border-right: 1px solid #cacaca
        }

        table.kv-table tr .button {
            text-align: center;
            border-radius: 0;
            text-indent: 0;
            height: 32px
        }

        table.kv-table .kv-content {
            width: 260px;
            padding: 5px 10px
        }

        table.kv-table .textarea-wrap textarea {
            width: 98%
        }

        .tabs-header {
            background-color: #fff;
            border-width: 0
        }

        .tabs li {
            border-top: 1px solid #bfbfbf;
            border-radius: 3px 3px 0 0
        }

        .tabs li.tabs-selected {
            border-top: 2px solid #1da02b
        }

        .tabs li.tabs-selected a.tabs-inner {
            color: #000;
            background-color: #fff
        }

        .tabs li a.tabs-inner {
            color: #000;
            background-color: #e3e3e3
        }

        .tabs li a.tabs-inner .tabs-title {
            font-size: 14px
        }

    </style>
    <title>编辑</title>

</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="locationForm" method="post" action="#">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">目标子网</td>
                    <td class="kv-content">
                        <input type="hidden" name="id" value="${id}" id="id"/>
                        <input class="easyui-textbox" data-options="required:true" readonly
                               name="subnetip"
                               id="subnetip" value="${idcIpSubnet.subnetip}"/>
                    </td>
                    <td class="kv-label">掩码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,max:32,min:1"
                               readonly
                               name="mask" id="mask"
                               value="${idcIpSubnet.maskstr}/${idcIpSubnet.mask}"/>
                    </td>
                </tr>
                <tr style="height: 70px">
                    <td class="kv-label">调整子网</td>
                    <td class="kv-content" colspan="3">
                        <input class="easyui-slider" style="width:300px" id="netmask" name="netmask"
                               data-options="
                                showTip:true,
                                mode: 'h',
                                max:32,
                                height:10,
                                min:0,
                                value:${idcIpSubnet.mask},
                                <%--onChange:getNetmask,--%>
				                onComplete:countDvalue,
				                tipFormatter: function(value){
				                     return value;
				                },
                                rule:[0,'|',4,'|',8,'|',12,'|',16,'|',20,'|',24,'|',28,'|',32]"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">编码</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="" name="code" id="code"
                               value="${idcIpSubnet.code}"/>
                    </td>
                    <td class="kv-label">用途</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="" name="usefor" id="usefor"
                               value="${idcIpSubnet.usefor}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">说明</td>
                    <td class="kv-content" colspan="3">
                        <textarea class="easyui-textbox" name="remark" id="remark">${idcIpSubnet.remark}</textarea>
                    </td>
                </tr>
                <tr style="height: 220px;">
                    <td colspan="4">
                        <table id="table" class="easyui-datagrid" style="height: 220px"
                               data-options="fit:true,rownumbers:true,pagination:false,height:220,striped:true,
                                onDblClickCell:enableEditor,
                                onClickCell:endEditor,
                                fitColumns:true">
                            <thead>
                            <tr>
                                <%--<th data-options="field:'id',checkbox:true"></th>--%>
                                <th data-options="field:'subnetip',width:200">调整后子网</th>
                                    <th data-options="field:'mask',width:200">掩码</th>
                                <th data-options="field:'usefor',width:150,editor:{
										type : 'text'
									}">用途</th>
                                <th data-options="field:'remark',width:200,editor:{
										type : 'text'
									}">备注
                                </th>
                            </tr>
                            </thead>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ipsubnet/ipcal.js"></script>
<script type="text/javascript">
    var baseMask = ${idcIpSubnet.mask};
    var oldMaskValue = ${idcIpSubnet.mask};
    var subnetip = "${idcIpSubnet.subnetip}";
    var remark = "${idcIpSubnet.remark}";
    var usefor = "${idcIpSubnet.usefor}";
    var id = "${idcIpSubnet.id}";
    function countDvalue(maskValue) {
        var createMesk = maskValue - baseMask;
        if (createMesk > 8) {
            top.layer.msg('折分子网，一次不能拆分太多');
            $("#netmask").slider("setValue", baseMask);
            return;
        }
        if (maskValue < baseMask || maskValue == 31) {
            top.layer.msg('拆分网段，掩码值不能小于原值：' + oldMaskValue + "并且掩码值不能为31");
            $("#netmask").slider("setValue", baseMask);
            var data={total:1,rows:[{
                subnetip:subnetip,
                mask:baseMask,
                //netsegmentAdjust:subnetip+"/"+baseMask,
                remark:remark,
                usefor:usefor
            }]};
            $("#table").datagrid("loadData",data).datagrid('acceptChanges');
            return;
        }
        if(maskValue==baseMask){
            var data={total:1,rows:[{
                subnetip:subnetip,
                mask:baseMask,
                //netsegmentAdjust:subnetip+"/"+baseMask,
                remark:remark,
                usefor:usefor
            }]};
            $("#table").datagrid("loadData",data).datagrid('acceptChanges');
            return ;
        }
        //oldMaskValue = maskValue;
        createTable(maskValue,oldMaskValue);
        function createTable(maskValue) {
            var countData =[];
            if (maskValue > baseMask) {
                 countData = subNetSplit(subnetip, oldMaskValue, maskValue);
            }
            createTr(countData,maskValue);
            function createTr(countData,maskValue,index){
                if(!isEmpty(index)){
                    //netsegmentDatagrid.datagrid('deleteRow',index);
                }
                var countTr = countData.length;
                var countRow = $("#table").datagrid('getRows').length;
                //每页显示16  当isShowMore小于16条时不再显示 “显示更多”
                var isShowMore = countTr-countRow;
                var tempCount=256;
                if(countTr<256){
                    tempCount=countData.length;
                }else if(isShowMore<256){
                    tempCount = isShowMore;
                }
                var data = getJsonDataGrid(tempCount,maskValue);
                $("#table").datagrid("loadData",data);
            }
            function getJsonDataGrid(tempCount,maskValue){
                var rows = []
                var data ={total:tempCount,rows:rows};
//                var data = '{"total":'+tempCount+',"rows":[';

                for(var i=0;i<tempCount;i++){
                    var netData = countData[i];
                    var tempEnd="},";
                    if(tempCount==1){
                        tempEnd="}";
                    }
                    var ipstr = netData;
                    if(i==0){
                        ipstr  =  subnetip.split("\/")[0];
                    }
                    rows.push({
                        id:i,
                        subnetip:ipstr,
                        mask:maskValue,
                        //netsegmentAdjust:ipstr,
                        usefor:usefor,
                        remark:remark
                    })
//                    if(i==0){
//                        var scrsegIp =  subnetip.split("\/")[0];
//                        data+='{"id":"'+i+'","netsegmentAdjust":"'+scrsegIp+"/"+maskValue+'","usefor":"'+usefor+
//                                '","remark":"'+remark+'"'+tempEnd+'';
//
//                    }else{
//                        if(i==tempCount-1){
//                            tempEnd="}";
//                        }
//                        data+='{"id":"'+i+'","netsegmentAdjust":"'+netData+'","remark":"'+remark+'","toAreaId":"'+toAearIdOld+'","toBusiId":"'+toBusIdOld+'","status":"'+statusOld+'"'+tempEnd+'';
//                    }
//                    recordRow++;
                }


//                data+=']}';
//                data = $.parseJSON(data);
                return data;
            }
        }
        /**
         * 子网拆分
         * @param netVal    网段地址
         * @param oldMask    拆分前掩码位
         * @param newMask    拆分后掩码位
         * @returns {Array}    拆分后的网段Array
         */
        function subNetSplit(netVal, oldMask, newMask) {
            // 第一步 将网段地址 换算成 32位的二进制数
            netVal = ipTo32BitBinary(netVal);
            //alert(netVal+":length:"+netVal.length);

            // 第二步 根据掩码位 对 网段进行 位移拆分运算
            var binaryArr = new Array();
            binaryArr.push(netVal);
            for (var i = oldMask; i < newMask; i++) {
                binaryArr = splitFun(i, binaryArr);
            }
            // 第三步 对拆分后的 32位二进制数形式的 网段 转换成 IP形式的地址
            var result = bit32BinaryToIP(binaryArr);
            binaryArr = null;
            return result;
        }

        /**
         * 按位移运算拆分 子网信息
         * @param index		起始位
         * @param arr		被拆分前的网段Array
         * @returns {Array} 被拆分后的网段Array
         */
        function splitFun(index,arr){
            var netVal=null,net2=null;
            var returnArr = new Array();
            var regex=new RegExp("^(\\d{"+index+"})\\d","g");
            for(var i=0;i<arr.length;i++){
                netVal=arr[i];
                returnArr.push(netVal);
                net2=netVal.replace(regex,"$11");
                returnArr.push(net2);
            }
            return returnArr;
        }
        /**
         * 将IP地址转换为 32位 二进制数
         * @param ipaddress        ip地址
         * @returns {String}    32位二进制数
         */
        function ipTo32BitBinary(ipaddress) {
            var result = ipaddress.replace(/(\d+?)(?:\.|$)/g, function (matcher, parm1) {
                var binary = parseInt(parm1).toString(2);
                if (binary.length == 8)
                    return binary;
                else if (binary == 0) {
                    return "00000000";
                } else {
                    var tempVal = "";
                    for (var x = 0; x < (8 - binary.length); x++)
                        tempVal += "0";
                    return tempVal + binary;
                }
            });

            return result;
        }

        /**
         * 将32位 二进制数转换为IP地址
         * @param binaryArr        32位二进制数
         * @returns {Array}    ip地址
         */
        function bit32BinaryToIP(binaryArr) {
            var result = new Array();
            var netSegment = "";
            for (var i = 0; i < binaryArr.length; i++) {
                //netSegment=arr[i].replace(/(\d)(?=(\d{8})+(?!\d))/g,"$1.");
                netSegment = binaryArr[i].replace(/(\d{8})/g, function () {
                    var args = arguments;
                    return "." + parseInt(args[1], 2);
                });
                netSegment = netSegment.substring(1);
                result.push(netSegment);
            }
            return result;
        }

    }
    function isEmpty(obj) {
        return ((obj == undefined || obj == null || obj == "") ? true : false);
    }
    function enableEditor(index,field,value){
        $("#table").datagrid('beginEdit', index);
        var ed = $("#table").datagrid('getEditor', {index:index,field:field});
        $(ed.target).focus();

    }
    /***
    *结束编辑
    * @param index
    * @param field
    * @param value
     */
    function endEditor(index,field,value){
        $("#table").datagrid('endEdit', index);
    }
    $(function () {
        var table = $("#table");
        console.log(table.parent().width())
        console.log(table.parent().height())
        var data={total:1,rows:[{
            subnetip:subnetip,
            mask:baseMask,
            remark:remark,
            usefor:usefor
        }]};
        $("#table").datagrid("loadData",data).datagrid('acceptChanges');
    })
    function doSubmit(){
        var changeRows = $("#table").datagrid('getRows');
        var str = JSON.stringify(changeRows);
        var index = layer.load(1, {
            shade: [0.5] //0.1透明度的白色背景
            ,content:'jiazai'
        });
        $.post("<%=request.getContextPath() %>/resource/ipsubnet/savesplit",{pid:id,rows:str,subnets:changeRows}, function (data) {
            layer.close(index);
            if(data.state){
                top.layer.msg('拆分成功');
                var parentWin = top.winref[window.name];
                top[parentWin].$('#dg').datagrid("reload");
                var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(indexT); //再执行关闭
            }else{
                top.layer.msg(data.msg)
            }

        });
        console.log(changeRows);
    }
</script>
</html>