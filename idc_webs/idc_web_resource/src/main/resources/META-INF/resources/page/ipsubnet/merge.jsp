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
                    <td class="kv-label">原子网</td>
                    <td class="kv-content" colspan="3">
                        <input type="hidden" name="id" value="${id}" id="id"/>
                        <input class="easyui-textbox" data-options="required:true" readonly
                               name="subnetip"
                               id="subnetip" value="${idcIpSubnet.subnetip}"/>
                    </td>
                </tr>
                <tr style="height: 120px">
                    <td class="kv-label">掩码</td>
                    <td class="kv-content" >
                        <input class="easyui-slider" style="width:300px" id="netmask" name="netmask"
                               data-options="
                                showTip:true,
                                mode: 'h',
                                max:32,
                                height:10,
                                min:0,
                                value:${idcIpSubnet.mask},
				                onComplete:countDvalue,
				                tipFormatter: function(value){
				                     return value;
				                },
                                rule:[0,'|',4,'|',8,'|',12,'|',16,'|',20,'|',24,'|',28,'|',32]"/>
                    </td>
                    <td class="kv-label">将被合并子网</td>
                    <td class="kv-content" style="padding: 0px;">
                        <ul id="lasttable" class="easyui-datalist" data-options="
                         	textFormatter:function(value,row,index){
							                   if (index > 0) {
								                      return '<span style=\'color:red\'>'+value+'</span>';
                                                }else{
								                        return value;
							                    }
						            }
                                "style="height:120px">
                        </ul>
                        <%--<table id="lasttable" class="easyui-datalist" style="height: 120px"--%>
                               <%--data-options="fit:true,rownumbers:true,pagination:false,height:220,striped:true,--%>
                               	<%--styler:function(value,row,index){--%>
							                    <%--if (index > 0) {--%>
								                        <%--return 'color:red';--%>
							                    <%--}else{--%>
								                        <%--return ;--%>
							                    <%--}--%>
						            <%--},--%>
                                <%--fitColumns:true">--%>
                            <%--<thead>--%>
                            <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
                                <%--&lt;%&ndash;<th data-options="field:'subnetip',width:200">将合并子网</th>&ndash;%&gt;--%>
                            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
                            <%--</thead>--%>
                        <%--</table>--%>
                    </td>
                </tr>
                <tr style="height: 150px">
                         <td class="kv-label">合并后结果</td>
                         <td class="kv-content" style="padding: 0px;" colspan="3">
                         <table id="table" class="easyui-datagrid" style="height: 150px"
                               data-options="fit:true,rownumbers:true,pagination:false,height:150,striped:true,
                                onDblClickCell:enableEditor,
                                onClickCell:endEditor,
                                fitColumns:true">
                            <thead>
                            <tr>
                                <th data-options="field:'subnetip',width:200">合并后子网</th>
                                <th data-options="field:'mask',width:200">合并后掩码</th>
                                <th data-options="field:'usefor',width:200,editor:{
										type : 'text'
									}">用途</th>
                                <th data-options="field:'remark',width:200,editor:{
                                    type : 'text'
                                }">说明</th>
                            </tr>
                            </thead>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
        <input type="hidden" id="delNetSegs"/>
        <input type="hidden" id="curNetsegAdjust" />
        <input type="hidden" id="updateIds" value="${idcIpSubnet.id}"/>
        <input type="hidden" id="targetNetSeg" value="${idcIpSubnet.subnetip}"/>

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
    var parentMask = ${parent.mask};
    var parentIP = '${parent.subnetip}';
    var rowNums = ${rownum};
    //"${parent.subnetip}"],rowids=["${parent.id}"
    var rowsips= [],rowids=[];
    var rowMap = {};
    <c:forEach items="${idcIpSubnets}" var="row" >
    rowsips.push("${row.subnetip}");
    rowids.push("${row.id}");
    rowMap["${row.id}"]={
        isparent:${row.parent}
    };
    </c:forEach>
    function init(){
        $("#netmask").slider("setValue", baseMask);
        var data = {
            total: 1, rows: [{
                subnetip: subnetip,
                mask: baseMask,
                remark: remark,
                usefor: usefor
            }]
        };
        $("#table").datagrid("loadData", data).datagrid('acceptChanges');
        var dataTr = subnetip+'/'+baseMask;
        var data = {rows:[{text:dataTr,value:dataTr}]};
        $("#lasttable").datalist('loadData', data);
    }
    function countDvalue(maskValue) {
        if (maskValue > baseMask || maskValue == 31) {
            top.layer.msg('合并网段，掩码值不能大于于原值：' + baseMask + "并且掩码值不能为31");
            init();
            return;
        }else if(maskValue<parentMask){
            top.layer.msg('合并网段，掩码值不能小于上级网段掩码值：' + parentMask);
            init();
            return;
        }else if (maskValue == baseMask) {
            init();
            return;
        }
        var countData=[];
        if (maskValue < baseMask) {
            countData = subNetMerger(subnetip,oldMaskValue,maskValue);
        }
        if(ipToNum(countData)<ipToNum(subnetip)){
            top.layer.msg('网段只能向下合并');
            init();
            return;
        }
        var dataUpdateDel = proIsMerger(rowsips,countData,maskValue,rowids);
//        if(dataUpdateDel==false){
//            top.layer.msg('存在下级网段,请先合并');
//            init();
//            return;
//        }
        var mergerSegnets = dataUpdateDel[1];
        $.post(contextPath + "/resource/ipsubnet/checkMer", {
            ids: mergerSegnets[mergerSegnets.length - 1]
        }, function (data) {
            if (data.state == true) {
                createTable(maskValue, oldMaskValue);
            } else {
                init();
                top.layer.msg("IP已经使用，禁止合并");
            }
        })
        function createTable(maskValue) {
            var countData = [];
            if (maskValue < baseMask) {
                countData = subNetMerger(subnetip,oldMaskValue,maskValue);
            }
            createTr(countData, maskValue);
            function createTr(countData, maskValue, index) {
                var data = {
                    total: 1, rows: [{
                        subnetip: countData,
                        mask: maskValue,
                        remark: remark,
                        usefor: usefor
                    }]
                };
                $("#table").datagrid('loadData', data).datagrid('beginEdit', 0);
                var dataUpdateDel = proIsMerger(rowsips,countData,maskValue,rowids);
                var mergerSegnets = dataUpdateDel[1];
                var updateSegnets = dataUpdateDel[0];
                if(updateSegnets.length>0){
                    var updateIds = updateSegnets[0];
                    $("#updateIds").val(updateIds);
                }
                if(mergerSegnets.length>0){
                    var dataTr = subnetip+'/'+baseMask;
                    var data = {rows:[{text:dataTr,value:dataTr}]};
                    $("#lasttable").datalist('loadData', data);
                }
                var data = {total:mergerSegnets.length,rows:[]};//'{"total":'+mergerSegnets.length+',"rows":[';
                var delNetSegs = [];
                var delMask = [];
                for (var i = 0; i < mergerSegnets.length; i++) {
                    if(i==0){
                        data.rows.push({
                            text:subnetip+"/"+oldMaskValue,
                            value:subnetip+"/"+oldMaskValue
                        })
                    }
                    if(i!=mergerSegnets.length-1){
                        data.rows.push({
                            text:mergerSegnets[i],
                            value:mergerSegnets[i]
                        });
                        delMask.push(mergerSegnets[i].split("/")[1]);
                    }else{
                        delNetSegs.push(mergerSegnets[i]);
//                        delMask = delMask.substring(1);
                    }
                }
                $("#delNetSegs").val(delNetSegs.join(","));
                $("#lasttable").datalist('loadData', data);
            }

            function getJsonDataGrid(tempCount, maskValue) {
                var rows = []
                var data = {total: tempCount, rows: rows};
//                var data = '{"total":'+tempCount+',"rows":[';

                for (var i = 0; i < tempCount; i++) {
                    var netData = countData[i];
                    var tempEnd = "},";
                    if (tempCount == 1) {
                        tempEnd = "}";
                    }
                    var ipstr = netData;
                    if (i == 0) {
                        ipstr = subnetip.split("\/")[0];
                    }
                    rows.push({
                        id: i,
                        subnetip: ipstr,
                        mask: maskValue,
                        //netsegmentAdjust:ipstr,
                        usefor: usefor,
                        remark: remark
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
         * 判断集合中的子网是否需要与新的网段进行合并
         * @param netSegArray	已分配的网段号(Array)
         * @param newNetSeg		新分配的网段号(String)
         * @param newMask		新分配的子网掩码位
         * @returns {Array}		返回原分配中需要被合并的网段地址
         */
        function proIsMerger(netSegArray,newNetSeg,newMask,queryIds){
            var len=netSegArray.length;
            if(len==0)
                return null;
            var newMaskBit=bitToMaskStr(newMask);
            var mergers=new Array();
            var tempStr=null;
            var updateAr =new Array();
            var updateSeg =new Array();
            var deleteAr =new Array();
            var updateids = "";
            var deleteids = "";
            newNetSeg=validateNetSeg(newNetSeg,newMaskBit);
            for(var i=0;i<len;i++){
                tempStr=netSegArray[i].replace(/\/\d+/g,"");
                tempStr=validateNetSeg(tempStr,newMaskBit);
                if(tempStr==null || newNetSeg!=tempStr){
                    continue;
                }
                var tag=proContains(updateSeg,tempStr);
                var qid = queryIds[i];
//                if(rowMap[qid]==false){
//                   return false;
//                }
                if(tag==false){
                    updateSeg.push(tempStr);

                    updateids +=","+ qid;
                }else{
                    deleteAr.push(netSegArray[i]);
                    deleteids +=","+ qid;
                }
            }
            if(updateSeg.length>0)
                updateids=updateids.substring(1);
            if(deleteAr.length>0)
                deleteids=deleteids.substring(1);
            deleteAr.push(deleteids);
            updateAr.push(updateids);
            mergers.push(updateAr);
            mergers.push(deleteAr);
            return mergers;
        }
        function proContains(arrays,val){
            var tag=false;
            for(var i=0;i<arrays.length;i++){
                if(arrays[i]==val){
                    tag=true;
                    break;
                }
            }
            return tag;
        }
        /**
         * 根据输入的地址 和掩码 计算 地址网段
         * @param ipAddress	ip地址
         * @param netMask	掩码(IP形式)
         * @returns {String}网段地址
         */
        function validateNetSeg(ipAddress,netMask){
            netMask=netMask.toString();
            var tag=netMask.match(/^\d+$/);
            if(tag!=null){
                netMask=bitToMaskStr(netMask);
            }
            var ipAddressElements = ipAddress.split(".");
            var netMaskElements = netMask.split(".");
            try {
                var result ="";
                var size = ipAddressElements.length;
                for (var i = 0; i < size; i++) {
                    var ipAddresInt = parseInt(ipAddressElements[i]);
                    var maskInt = parseInt(netMaskElements[i]);
                    if (i == 3)
                        result+=(((ipAddresInt & maskInt) | 0));
                    else
                        result+=(ipAddresInt & maskInt);
                    if (i < size - 1) {
                        result+=".";
                    }
                }
                return result;
            } catch (e) {
                return null;
            }
        }
        /**
         * 将掩码位 转换为 IP 形式的掩码
         * @param maskBit		掩码位
         * @returns {String}	IP形式的掩码
         */
        function bitToMaskStr(maskBit){
            var bitNetMask = "";
            for (var i = 0; i < maskBit; i++)
                bitNetMask += "1";
            for (var i = 0; i < 32 - maskBit; i++)
                bitNetMask += "0";

            var netMask = "";
            for (var i = 0; i < 4; i++) {
                if (i != 0)
                    netMask += ".";
                netMask += parseInt(bitNetMask.substring(8 * i, 8 * (i + 1)),2);
            }
            return netMask;
        }
        /**
         * 子网合并
         * @param netSeg	网段地址
         * @param oldMask	合并前掩码位
         * @param newMask	合并后掩码位
         */
        function subNetMerger(netSeg,oldMask,newMask){
            // 第一步 将网段地址 换算成 32位的二进制数
            netSeg=ipTo32BitBinary(netSeg);
            //alert(netVal+":length:"+netVal.length);

            // 第二步 根据掩码位 对 网段进行 合并运算
            var temp1=netSeg.substring(0, newMask);
            var temp2=netSeg.substring(oldMask);
            var temp3=netSeg.substring(newMask,oldMask).replace(/1/g,"0");
            netSeg=temp1+temp3+temp2;
            /*
             var regex=null;
             for(var i=oldMask;i>newMask;i--){
             regex=new RegExp("^(\\d{"+(i-1)+"})\\d","g");
             netSeg=netSeg.replace(regex,"$10");
             }*/
            netSeg=netSeg.replace(/(\d{8})/g,function(){
                var args=arguments;
                return "."+parseInt(args[1],2);
            });
            netSeg=netSeg.substring(1);
            return netSeg;
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
         * @param index        起始位
         * @param arr        被拆分前的网段Array
         * @returns {Array} 被拆分后的网段Array
         */
        function splitFun(index, arr) {
            var netVal = null, net2 = null;
            var returnArr = new Array();
            var regex = new RegExp("^(\\d{" + index + "})\\d", "g");
            for (var i = 0; i < arr.length; i++) {
                netVal = arr[i];
                returnArr.push(netVal);
                net2 = netVal.replace(regex, "$11");
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

        function ipToNum(f) {
            var ips = f.split(".")
            if (ips[0] > 255)ips[0]= 255;
            if (ips[1]  > 255)ips[1] = 255;
            if (ips[2]  > 255)ips[2]= 255;
            if (ips[3] > 255)ips[3] = 255;
            return  eval(ips[0]  * 16777216) + eval(ips[1]  * 65536) + eval(ips[2]  * 256) + eval(ips[3]);
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
    function enableEditor(index, field, value) {
        $("#table").datagrid('beginEdit', index);
        var ed = $("#table").datagrid('getEditor', {index: index, field: field});
        $(ed.target).focus();

    }
    /***
     *结束编辑
     * @param index
     * @param field
     * @param value
     */
    function endEditor(index, field, value) {
        $("#table").datagrid('endEdit', index);
    }
    $(function () {
        var table = $("#table");
        var data = {
            total: 1, rows: [{
                subnetip: subnetip,
                mask: baseMask,
                remark: remark,
                usefor: usefor
            }]
        };
        init();
//        var srcNetsegData = subnetip+'/'+oldMaskValue;
//        $("#lasttable").datalist('appendRow',{
//            value: srcNetsegData,
//            text: srcNetsegData,
//        });
//        $("#table").datagrid('appendRow',{
//            subnetip: subnetip,
//            mask:oldMaskValue,
//            usefor:usefor,
//            remark:remark
//        });
        $("#table").datagrid('beginEdit', 0);
    })
    function doSubmit() {
        var curRows = $("#lasttable").datagrid('getRows');
        if(curRows.length<2){
            top.layer.msg("提示:当前无合并数保存据！");
            return false;
        }
        top.layer.confirm("确定合并这些网段？",{
            btn:['合并','取消']
        },function(){
            $("#table").datagrid('endEdit', 0).datagrid('rejectChanges');
            var index = layer.load(1, {
                shade: [0.5] //0.1透明度的白色背景
                , content: '合并中...'
            });
            var row = $("#table").datagrid("getRows")[0];
            $.post("<%=request.getContextPath() %>/resource/ipsubnet/savemerger", {
                subnetip: row.subnetip,
                mask: row.mask,
                remark: row.remark,
                usefor: row.usefor,
                delNetSegs: $("#delNetSegs").val(),
                updateIds: $("#updateIds").val(),
                targetNetSeg: $("#targetNetSeg").val(),
            }, function (data) {
                layer.close(index);
                if (data.state) {
                    top.layer.msg('合并成功');
                    var parentWin = top.winref[window.name];
                    if(parentMask==row.mask&&parentIP==row.subnetip){
                        top[parentWin].navto(0)
                    }else{
                        top[parentWin].$('#dg').datagrid("reload");
                    }
                    var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(indexT); //再执行关闭
                } else {
                    top.layer.msg(data.msg)
                }

            });
        });
    }
</script>
</html>