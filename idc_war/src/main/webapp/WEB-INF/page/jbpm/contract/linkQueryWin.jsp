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
    <title>合同信息列表</title>
</head>
<style type="text/css">
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0 !important;
    }
</style>
<body>
    <form method="post" id="singleForm" action="<%=request.getContextPath() %>/contractController/contractFormSaveOrUpdate.do">
        <table class="kv-table">
            <tr>
                <td class="kv-label" style="width: 200px;">名称</td>
                <td class="kv-content">
                    <input type="hidden" name="id" value="${idcContract.id}" id="id"/>
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contractname" value="${idcContract.contractname}"  id="contractname" data-options="validType:'length[0,128]'"/>
                </td>
                <td class="kv-label">编码</td>
                <td class="kv-content">
                    <input class="easyui-textbox" readonly="readonly" data-options="required:true" name="contractno" value="${idcContract.contractno}"  id="contractno" data-options="validType:'length[0,128]'"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">客户</td>
                <td class="kv-content">
                    <input type="hidden" name="customerId" value="${idcContract.customerId}" id="customerId"/>
                    <input id="customerName" readonly="readonly" value="${idcContract.customerName}" class="easyui-textbox" data-options="editable:false,required:true,iconAlign:'left',buttonText:'选择',onClickButton:openWinCustomerNameFun">
                </td>
                <td class="kv-label">价格(元)</td>
                <td class="kv-content">
                    <input class="easyui-numberbox"readonly="readonly"  name="contractprice" value="${idcContract.contractprice}" id="contractprice" data-options="precision:2,min:0,validType:'length[0,12]'"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">开始时间</td>
                <td class="kv-content">
                    <input class="easyui-datetimebox" readonly="readonly"  name="contractstart" value="${idcContract.contractstart}" data-options="editable:false,showSeconds:true">
                </td>
                <td class="kv-label">合同期限(月)</td>
                <td class="kv-content">
                    <input class="easyui-numberbox" readonly="readonly"  name="contractperiod" value="${idcContract.contractperiod}" id="contractperiod" data-options="precision:0,min:0,validType:'length[0,10]'"/>
                </td>
            </tr>
            <tr>
                <td class="kv-label">签订日期</td>
                <td class="kv-content">
                <input class="easyui-datetimebox" readonly="readonly"  name="contractsigndate" value="${idcContract.contractsigndate}" data-options="editable:false,showSeconds:true">
            </td>
                <td class="kv-label">到期提醒方案</td>
                <td class="kv-content">
                    <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								editable:false,
								data: [{
									label: '短信',
									value: '0'
								},{
									label: '电话',
									value: '1'
								},{
									label: '邮件',
									value: '2'
								}]" value="${idcContract.contractexpirreminder}"  name="contractexpirreminder" id="contractexpirreminder"/>
                </td>
            </tr>

            <tr>
                <td class="kv-label">合同备注</td>
                <td class="kv-content" colspan="3">
                    <input class="easyui-textbox" multiline="true" readonly="readonly"  name="contractremark"  value="${idcContract.contractremark}" style="width:83%;height:120px" data-options="validType:'length[0,255]'">
                </td>
            </tr>
        </table>
    </form>
</body>
<script type="application/javascript">
    function form_sbmt(gridId){
        $("#singleForm").form('submit', {
            url:contextPath+"/contractController/contractFormSaveOrUpdate.do",
            onSubmit: function(){
                if(!$("#singleForm").form("validate")){
                    //验证未通过
                    top.layer.msg('验证未通过', {
                        icon: 2,
                        time: 3000 //（默认是3秒）
                    });
                    return false;
                }
            },
            success:function(data){
                //然后修改下一个form
                var obj = jQuery.parseJSON(data);

                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
            }
        });
    }
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
                console.log(chidlwin);
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
</script>
</html>