<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>编辑数据中心</title>
    <script type="text/javascript">
        $(function(){
            //动态绑定机房
            var roomid = "${idcRack.roomid}";
            $("#roomid").combobox({
                onChange: function (newVal, oldVal) {
                    changeRoomValue(newVal,oldVal);
                }
            });
            $("#roomid").combobox("setValue",roomid);
            <%--//动态绑定机架类型--%>
            <%--var businesstypeId = "${idcRack.businesstypeId}";--%>
            <%--$("#businesstypeId").combobox("setValue",businesstypeId);--%>
            //动态绑定机架型号
            var rackmodelid = "${idcRack.rackmodelid}";
            $("#rackmodelid").combobox("setValue",rackmodelid);
            //动态绑定电源类型
            var pduPowertype = "${idcRack.pduPowertype}";
            $("#pduPowertype").combobox("setValue",pduPowertype);
            //绑定dftype
            var dftype = "${idcRack.dftype}";
            if (dftype == null || dftype == "") {
                dftype = "odf";
            }
            $("#dftype").combobox("setValue", dftype);

            //若为查看详情则只能看不能改
            var buttonType = "${buttonType}";
            if (buttonType != null && buttonType == 'view') {
                $('input').attr("readonly", true);
                $('select').combobox('readonly', true);
            }
        })
        //是否显示机房超链接
        function changeRoomValue(newValue,oldValue){
            var data = '所属机房';
            if(newValue != null && newValue != ''){
                data = '<a href="javascript:void(0)" onclick="showRoom()">所属机房</a>';
            }
            $("#isShowRoomLink").html(data);
        }
    </script>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="idcRackForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">ODF名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="required:true,width:200" name="name" id="name"
                               value="${idcRack.name}"/>
                    </td>
                    <td class="kv-label" id="isShowRoomLink">所属机房</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="roomid" id="roomid"
                                data-options="
                            required:true,
                            panelHeight:100,
                            editable:false,
                            width:200,
                            valueField:'id',
                            textField:'sitename',
                            url:'<%=request.getContextPath() %>/idcRack/ajaxZbMachineroom'
                          ">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">标准名称</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['ODFRackReg'],required:true,width:200" name="codeView"
                               value="${idcRack.codeView}"/>
                    </td>
                    <td class="kv-label">机架型号</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" name="rackmodelid" id="rackmodelid"
                                data-options="
                            panelHeight:100,
                            editable:false,
                            width:200,
                            valueField:'ID',
                            textField:'NAME',
                            url:'<%=request.getContextPath() %>/idcRack/getRackModel'
                          ">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">DF类型</td>
                    <td class="kv-content">
                        <input type="hidden" name="status" value="40"/>
                        <input type="hidden" name="businesstypeId" value="df"/>
                        <select class="easyui-combobox" name="dftype" id="dftype"
                                data-options="panelHeight:100,editable:false,width:200">
                            <option value="odf">ODF</option>
                            <option value="ddf">DDF</option>
                        </select>
                    </td>
                    <td class="kv-label"></td>
                    <td class="kv-content"></td>
                </tr>
                <tr>
                    <td class="kv-content" colspan="4" style="padding: 12px 1px 0px 16px;">电力信息</td>
                </tr>
                <tr>
                    <td class="kv-label">电源类型</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" data-options="panelHeight:100,width:200,editable:false"
                                name="pduPowertype" id="pduPowertype">
                            <option value="AC">AC</option>
                            <option value="DC">DC</option>
                            <option value="AC/DC">AC/DC</option>
                        </select>
                    </td>
                    <td class="kv-label">额定电量</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="validType:['intOrFloat'],width:200"
                               name="ratedelectricenergy"
                               id="ratedelectricenergy" value="${idcRack.ratedelectricenergy}"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var id = "${rackId}";
    function showRoom(){
        var roomid = $("#roomid").combobox("getValue");
        var url = contextPath + "/zbMachineroom/getZbMachineroomInfo.do?id=" + roomid + "&buttonType=view";
        openDialogShowView2d('机房信息', url, '800px', '530px', '查看机房视图');
    }
    function doSubmit(){
        addIdcRack();
    }
    function addIdcRack (){
        var linkUrl = "<%=request.getContextPath() %>/idcRack/addIdcRackInfo.do?rackId="+id;
        $('#idcRackForm').form('submit', {
            url: linkUrl,
            onSubmit: function () {
                return $("#idcRackForm").form('validate');
            },
            success: function (data) {
                var obj = eval('(' + data + ')');
                alert(obj.msg);
                if(obj.state){
                    var parentWin = top.winref[window.name];
                    top[parentWin].$('#dg').datagrid("reload");
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                }
            }
        });
    }
    //查看odf视图
    function showView2d() {
        openDialogView('机架视图', contextPath+'/idcRack/viewodf/'+id,'1200px','530px');
    }
</script>
</body>
</html>