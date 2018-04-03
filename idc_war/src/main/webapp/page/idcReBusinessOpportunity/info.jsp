<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/custom/uimaker/css/basic_info.css"/>
    <title>业务商机信息</title>
</head>
<script type="text/javascript">
    $(function(){
        //绑定是否成单
        var isordered = "${idcReBusinessOpportunity.isordered}";
        $("#isordered").combobox("setValue",isordered);

        //当为查看时  禁用所有操作
        var buttonType = "${buttonType}";
        if(buttonType!=null&&buttonType=="view"){
            $('input').attr("readonly", true);
            $("textarea").attr("readonly", true);
            $('select').combobox('readonly', true);
            $('.easyui-linkbutton').linkbutton('disable');
            $(".easyui-datetimebox").datetimebox('readonly', true);
            $("a").css("cursor","default");
        }
    })
</script>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="table" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">业务名称</td>
                    <td class="kv-content">
                        <input type="hidden" name="id" value="${idcReBusinessOpportunity.id}"/>
                        <input class="easyui-textbox" data-options="required:true,width:200" name="businessName" value="${idcReBusinessOpportunity.businessName}"/>
                    </td>
                    <td class="kv-label">客户</td>
                    <td class="kv-content">
                        <input id="customerName" name="customerName" class="easyui-textbox textbox-f" value="${idcReBusinessOpportunity.customerName}"
                               data-options="editable:false,required:true,iconAlign:'left',buttonText:'选择',width:200,
                           onClickButton:openWinCustomerNameFun" style="display: none;">
                        <input type="hidden" id="customerId" name="customerId" value="${idcReBusinessOpportunity.customerId}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">预计成单时间</td>
                    <td class="kv-content">
                        <input class="easyui-datetimebox" data-options="width:200" name="orderPredictTime" value="${idcReBusinessOpportunity.orderPredictTimeStr}"/>
                    </td>
                    <td class="kv-label">是否成单</td>
                    <td class="kv-content">
                        <select id="isordered" name="isordered" class="easyui-combobox" data-options="editable:false,width:200">
                            <option value="" >-----</option>
                            <option value="Y">已成单</option>
                            <option value="N">未成单</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">备注</td>
                    <td class="kv-content" colspan="3">
                        <input class="easyui-textbox" name="remark" value="${idcReBusinessOpportunity.remark}" data-options="width:600,multiline:true,
                        icons: [{
                            iconCls:'icon-clear',
                            handler: function(e){
                                if('${buttonType}' != 'view'){
                                    $(e.data.target).textbox('setValue', '');
                                }
                            }
                        }]" />
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    /*提交表单*/
    function doSubmit(){
        var url = contextPath +"/idcReBusinessOpportunity/saveIdcReBusinessOpportunity";
        $('#table').form('submit', {
            url: url,
            onSubmit: function () {
                return $("#table").form('validate');
            },
            success: function (data) {
                data = JSON.parse(data);
                alert(data.msg);
                if(data.state){
                    var parentWin = top.winref[window.name];
                    top[parentWin].$('#table').datagrid("reload");
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    top.layer.close(index); //再执行关闭
                }
            }
        });
    }
    /*打开客户列表*/
    function openWinCustomerNameFun(){
        if('${buttonType}'!='view'){
            var laySum = top.layer.open({
                type: 2,
                title: '客户列表',
                shadeClose: false,
                shade: 0.8,
                btn: ['确定','关闭'],
                maxmin : true,
                area: ['800px', '530px'],
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
                }
            });
        }
    }
</script>
</body>
</html>