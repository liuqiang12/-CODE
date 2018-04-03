<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑资源设备</title>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="idcHostForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">关联主机</td>
                    <td class="kv-content" colspan="3">
                        <c:if test="${empty info}">
                        机房：<select class="easyui-combobox" name="roomid" id="roomid"
                                data-options="
                            required:true,
                            panelHeight:200,
                            editable:false,
                            width:150,
                            panelWidth:220,
                            valueField:'id',
                            textField:'sitename',
                            url:'<%=request.getContextPath() %>/idcRack/ajaxZbMachineroom',
                            onChange:getRack
                          ">
                        </select>
                       机架：<select class="easyui-combobox" name="rackid" id="rackid"
                                data-options="
                            required:true,
                            panelHeight:200,
                            editable:false,
                            panelWidth:220,
                            width:150,
                            valueField:'ID',
                            textField:'NAME',
                            loadFilter:function(data){
                                console.log(data)
                                data.unshift({
                                    ID:0,
                                    NAME:'全部'
                                 })
                                return data;
                            },
                            onLoadSuccess:function(data){
                              var values = $(this).combobox('getValues');//获取选中的值的values
                                console.log(values)
                               $(this).combobox('setValue','0');
                            },
                            onChange:getHost
                          "> </select>
                            设备：
                            <select class="easyui-combobox" name="hostId" id="hostId"
                                    data-options="
                            required:true,
                            panelHeight:200,
                            editable:false,
                            panelWidth:220,
                            width:150,
                            valueField:'DEVICEID',
                            textField:'NAME',
                             onChange:getHostInfo
                          ">
                            </select>
                        </c:if>
                        <c:if test="${not empty info}">
                            <input name="idcPhysicsHostsId" value="${info.idcPhysicsHostsId}" type="hidden"/>
                            设备：<input class="easyui-textbox" data-options="required:true,width:200"  value="${info.idcPhysicsHostsName}" readonly="readonly"/>
                        </c:if>

                    </td>
                </tr>
                <tr>
                    <td class="kv-label">主机硬盘</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" data-options="required:true,width:200" id="idcPhysicsHostDiskSize" name="idcPhysicsHostDiskSize"  value="${info.idcPhysicsHostDiskSize}" />GB
                    </td>
                    <td class="kv-label" >预留磁盘</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" data-options="required:true,width:200,min:0" id="idcReserveDiskSize" name="idcReserveDiskSize" value="${info.idcReserveDiskSize}" />GB
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">主机CPU核数</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" data-options="required:true,width:200" id="idcPhysicsHostsCpuCore" name="idcPhysicsHostsCpuCore"  value="${info.idcPhysicsHostsCpuCore}" />
                    </td>
                    <td class="kv-label" >预留CPU核数</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" data-options="required:true,width:200,min:0" id="idcReserveCpuCore" name="idcReserveCpuCore" value="${info.idcReserveCpuCore}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">主机内存</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" data-options="required:true,width:200" id="idcPhysicsHostsMemSize" name="idcPhysicsHostsMemSize"  value="${info.idcPhysicsHostsMemSize}"/>GB
                    </td>
                    <td class="kv-label" >预留内存</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" data-options="required:true,width:200,min:0,precision:2" id="idcReserveMemSize" name="idcReserveMemSize"  value="${info.idcReserveMemSize}" />GB
                    </td>
                </tr>
                 </tbody>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var rackId = "${rackId}";

    function getRack(newvalue){

        $.post(contextPath+"/idcRack/list.do",{ roomid:newvalue,page:1,rows:100000},function(result){
            console.log(result.rows);
            $('#rackid').combobox({
                data:result.rows
            }) ;
            if(result&&result.rows&&result.rows.length>0){
                $('#rackid').combobox("select",result.rows[0]["ID"]);
            }
        })
    }
    function getHost(newvalue){
        var roomid = $("#roomid").combobox("getValue");
        $.post(contextPath+"/device/list.do",{deviceclass:2,roomId:roomid,rackId:newvalue,page:1,rows:100000},function(result){
            $('#hostId').combobox({
                data:result.rows
            });
            if(result&&result.rows&&result.rows.length>0){
                $('#hostId').combobox("select",result.rows[0]["DEVICEID"]);
            }
        })
    }
    function getHostInfo(deviceid){
        $("#idcPhysicsHostDiskSize").textbox("setValue",0);
        $("#idcPhysicsHostsCpuCore").textbox("setValue",0);
        $("#idcPhysicsHostsMemSize").textbox("setValue",0);
        $.getJSON(contextPath+"/physicshost/getHostInfo/"+deviceid,function(result){
            console.log("============================")
            console.log(result)
            $("#idcPhysicsHostDiskSize").textbox("setValue",result.host.disksize);
            $("#idcPhysicsHostsCpuCore").textbox("setValue",result.host.cpusize);
            $("#idcPhysicsHostsMemSize").textbox("setValue",result.host.memsize);
//            if(result.idcPhysicsHost!=null){
//                var idcPhysicsHost = result.idcPhysicsHost
//                $("idcReserveCpuCore").textbox("setValue",idcPhysicsHost.idcReserveCpuCore);
//                $("idcPhysicsHostsCpuCore").textbox("setValue",idcPhysicsHost.idcReserveDiskSize;
//                $("idcReserveMemSize").textbox("setValue",idcPhysicsHost.idcReserveMemSize);
//            }

        })
    }

    function showRoom(){
        var roomid = $("#roomid").combobox("getValue");
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + roomid + "&buttonType=view";
        openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
    }
    function doSubmit(){
        addIdcRack();
    }
    function addIdcRack (){
        $('#idcHostForm').form('submit', {
            url: "<%=request.getContextPath() %>/physicshost/save",
            //queryParams: {},
            onSubmit: function () {
                return $("#idcHostForm").form('validate');
            },
            success: function (data) {
                var re = eval("("+data+")");
                if(re.state=true){
                    top.layer.msg("保存成功")
                    var parentWin = top.winref[window.name];
                    top[parentWin].$('#dg').datagrid("reload");
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                }else{
                    top.layer.msg("保存失败")
                }

            }
        });
    }
    function changeType(newValue,oldValue){
        if (newValue == 'equipment') {
            $(".equipment").css("display", "");
        } else if (newValue == 'cabinet' || newValue == 'wiring') {
            $(".equipment").css("display", "none");
        }
    }
    //查看机架视图
    function showView2d() {
        window.open(contextPath + '/racklayout/' + rackId);
    }
</script>
</body>
</html>