<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
<form method="post" id="singleForm" action="<%=request.getContextPath() %>/contractController/contractFormSaveOrUpdate.do" enctype="multipart/form-data">
    <table class="kv-table">
        <tr>
            <td class="kv-label" style="width: 200px;">合同名称</td>
            <td class="kv-content">
                <input type="hidden" name="id" value="${idcContract.id}" id="id"/>
                <input class="easyui-textbox" readonly="readonly" name="contractname" value="${idcContract.contractname}" id="contractname" data-options="required:true,validType:'length[0,128]'"/>
            </td>
            <td class="kv-label">编码</td>
            <td class="kv-content">
                <input class="easyui-textbox" readonly="readonly" name="contractno" value="${idcContract.contractno}"  id="contractno" data-options="required:true,validType:'length[0,128]'"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">客户名称</td>
            <td class="kv-content">
                <input type="hidden" name="customerId" value="${idcContract.customerId}" id="customerId"/>
                <input id="customerName" readonly="readonly" value="${idcContract.customerName}" class="easyui-textbox" data-options="editable:false,required:true,iconAlign:'left'">
            </td>

            <td class="kv-label">开始时间</td>
            <td class="kv-content">
                <input class="easyui-datetimebox" readonly="readonly"  name="contractstart" value="${idcContract.contractstart}" data-options="required:true,editable:false,showSeconds:true">
            </td>
        </tr>
        <tr>
            <td class="kv-label">合同期限(月)</td>
            <td class="kv-content">
                <input class="easyui-numberbox" readonly="readonly" name="contractperiod" value="${idcContract.contractperiod}" id="contractperiod" data-options="required:true,precision:0,min:0,validType:'length[0,10]'"/>
            </td>

            <td class="kv-label">签订日期</td>
            <td class="kv-content">
                <input class="easyui-datetimebox" readonly="readonly"  name="contractsigndate" value="${idcContract.contractsigndate}" data-options="required:true,editable:false,showSeconds:true">
            </td>
        </tr>
        <tr>
            <td class="kv-label">到期提醒方案</td>
            <td class="kv-content">
                <input class="easyui-combobox" readonly="readonly" data-options="
								valueField: 'value',
								textField: 'label',
								required:true,
								editable:false,
								data: [{
									label: '联系客户经理',
									value: '0'
								},{
									label: '短信',
									value: '1'
								},{
									label: '邮件',
									value: '2'
								}]" value="${idcContract.contractexpirreminder}"  name="contractexpirreminder" id="contractexpirreminder"/>
            </td>
            <!-- 客户经理和政企经理才能看的写法 -->
            <sec:authorize access="hasAnyRole('ROLE_idc_customer_manager','idc_city_government_manager','idc_province_government_manager','ROLE_admin')">
                <td class="kv-label">价 格</td>
                <td class="kv-content" >
                    <input class="easyui-numberbox" readonly="readonly" name="contractprice" value="${idcContract.contractprice}" id="contractprice" data-options="required:true,min:0,validType:'length[0,12]'"/>
                </td>
            </sec:authorize>
        <tr>

        <%--<tr>
            &lt;%&ndash;创建人名称为当前登陆的用户&ndash;%&gt;
            <td class="kv-label">创建人名称</td>
            <td class="kv-content" colspan="3">
                <input type="hidden" name="applyId" id="applyId" value="${idcContract.applyId}">
                <input class="easyui-textbox" name="applyName" readonly="true" value="${idcContract.applyName}" id="applyName" data-options="validType:'length[0,255]'"/>
            </td>
        </tr>　--%>

        <tr>
            <td class="kv-label">合同备注</td>
            <td class="kv-content" colspan="3">
                <input class="easyui-textbox" readonly="readonly" multiline="true"  name="contractremark"  value="${idcContract.contractremark}" style="width:83%;height:120px" data-options="validType:'length[0,255]'">
            </td>
        </tr>

        <tr>
            <td class="kv-label">合同附件</td>
            <td class="kv-content" colspan="3">
                <%--<span class="icon_item_apply legend-cls" onclick="addAttachContract()">添加附件</span>--%>
                <div id="muiltFilesDiv">
                    <!-- 罗列出已经添加了的附件信息 -->
                    <c:forEach items="${attachmentinfoList}" var="item" varStatus="index">
                        <div class="attachHasedCls">
                            <input value="${item.attachName}" class="easyui-textbox" data-options="editable:false,width:550" />
                           <%-- <a href="javascript:void(0)" class="easyui-linkbutton" onclick="removeAttach(this,${item.id})" style="width:38px">删除</a>--%>
                            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="downLoadAttach(${item.id})" style="width:38px">下载</a>
                        </div>
                    </c:forEach>
                    <%--<div id="fileDiv0">
                        <input class="easyui-filebox" name="contractFileList0" data-options="prompt:'选择文件',buttonText:'选择'" style="width:508px">
                    </div>--%>
                </div>
            </td>
        </tr>

    </table>
</form>
</body>
<script type="application/javascript">
    /*下载附件信息*/
    function downLoadAttach(id){
        $("#downloadFile").attr("src",contextPath+"/assetAttachmentinfoController/downLoadFile/"+id);
    }
    function removeAttach(obj,id){
        //删除文件:
        top.layer.confirm('你确定删除吗？', {
            btn: ['确定','取消'] //按钮
        }, function(index, layero){
            /*ajax*/
            $.ajax({
                type:"POST",
                url:contextPath+"/assetAttachmentinfoController/removeAttach/"+id,

                datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                //在请求之前调用的函数
                beforeSend:function(){
                },
                //成功返回之后调用的函数
                success:function(data){
                    var obj = jQuery.parseJSON(data);
                    //提示删除成功
                    //提示框
                    top.layer.msg(obj.msg, {
                        icon: 1,
                        time: 3000 //（默认是3秒）
                    });
                    $(obj).parents("div.attachHasedCls").remove();
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


    function addAttachContract(){
        //添加附件信息
        var fileListLength = $("input[name^='contractFileList']").length;
        var addAttachHtml = '<div id="fileDiv'+fileListLength+'"><input class="easyui-filebox" name="contractFileList'+fileListLength+'" data-options="prompt:\'选择文件\',buttonText:\'选择\'" style="width:508px"><a href="javascript:void(0)" class="easyui-linkbutton" onclick="removeBotton(this,'+fileListLength+')" style="width:38px">删除</a></div>';

        var targetObj = $("#muiltFilesDiv").append(addAttachHtml);
        $.parser.parse($("#fileDiv"+fileListLength));
    }

    function form_sbmt(grid,jbpmFunFlag){
        if(jbpmFunFlag && jbpmFunFlag == 'rejectJbpm'){
            $("#idcRunProcCment_status_stand").val(0);//驳回情况
        }
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
                var parentIndex = parent.layer.getFrameIndex("jbpmApplyWinId");//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
                $("#"+grid).datagrid("reload");
            }
        });
    }

</script>
</html>