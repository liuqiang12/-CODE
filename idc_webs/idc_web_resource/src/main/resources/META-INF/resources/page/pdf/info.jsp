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
            //动态绑定电源类型
            var pduPowertype = "${idcRack.pduPowertype}";
            $("#pduPowertype").combobox("setValue",pduPowertype);

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
                    <td class="kv-label">PDF架名称</td>
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
                            width:200,
                            editable:false,
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
                        <input class="easyui-textbox" data-options="validType:['PDFRackReg'],required:true,width:200" name="codeView"
                               value="${idcRack.codeView}"/>
                    </td>
                    <td class="kv-label">电源类型</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" data-options="editable:false,width:200" name="pduPowertype"
                                id="pduPowertype">
                            <option value="DC">DC</option>
                            <option value="AC">AC</option>
                            <option value="AC/DC">AC/DC</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">所在位置</td>
                    <td class="kv-content">
                        <input type="hidden" name="status" value="40"/>
                        <input type="hidden" name="businesstypeId" value="pdu"/>
                        <input class="easyui-textbox" data-options="width:200" name="pduLocation" id="pduLocation"
                               value="${idcRack.pduLocation}"/>
                    </td>
                    <td class="kv-label"></td>
                    <td class="kv-content"></td>
                </tr>
                <c:if test="${empty idcRack}">
                    <tr>
                        <td class="kv-content" colspan="4" style="padding: 12px 1px 0px 16px;">MCB数</td>
                    </tr>
                    <tr>
                        <td class="kv-label">生成MCB组数</td>
                        <td class="kv-content">
                            <input class="easyui-textbox" data-options="validType:['number'],required:true,width:200"
                                   name="mcbNum" id="mcbNum"/>
                        </td>
                        <td class="kv-label"></td>
                        <td class="kv-content"></td>
                    </tr>
                </c:if>
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
    //查看pdf架视图
    function showView2d() {
        openDialogView('机架视图', contextPath+'/idcRack/viewpdf/'+id,'800px','530px');
    }
</script>
</body>
</html>