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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/start/start.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>合同信息列表</title>
</head>
<style type="text/css">
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0 !important;
    }
</style>
<body>


<form method="post" id="singleForm" action="<%=request.getContextPath() %>/actJBPMController/procCmentFormSaveOrUpdate.do">
    <table class="kv-table">
        <tr>
            <td class="kv-label" style="width:100px">满意度<span style="color:red">&nbsp;*</span></td>
            <td class="kv-content" colspan="3">

                <input name="ticketInstId" id="ticketInstId" value="${ticketInstId}" type="hidden"/>
                <input name="prodInstId" id="prodInstId" value="${prodInstId}" type="hidden"/>
                <input name="operationSign" id="operationSign" value="isRubbish" type="hidden"/>

                <input name="starNum" id="StarNum" value="" type="hidden"/>
                <div class="quiz">
                    <div class="quiz_content">
                        <div class="goods-comm">
                            <div class="goods-comm-stars">
                                <span class="star_l"></span>
                                <div id="rate-comm-1" class="rate-comm"></div>
                            </div>
                        </div>
                    </div><!--quiz_content end-->
                </div><!--quiz end-->
            </td>

            <div>
                <br />
                <p align="center"><span style="color: red;font-size: 26px">警告：作废属于危险操作！</span></p>

                <br />
                <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #007bb9;font-size: 16px">1.作废后此工单、如果有父工单 父工单可以再次开通！</span></p>

                <br />
                <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #007bb9;font-size: 16px">2.业务变更流程、变更开通流程作废，资源将全部异常，需人工处理！</span></p>

                <br />
                <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #007bb9;font-size: 16px">3.作废需要对满意度评分！</span></p>
                <br />
            </div>

        </tr>
    </table>
</form>

</body>
<script type="text/javascript">

    /*提交表单情况isDelete*/
    function form_sbmt_rubbish(grid){
        $("#singleForm").form('submit', {
            //删除
            url:contextPath+"/actJBPMController/rubbishOrDeleteTicket.do",
            onSubmit: function(){
                /*请给满意度打分 */
                if($("#StarNum").val() == '' || $("#StarNum").val() == null){
                    layer.msg('请给满意度打分', {
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
                    time: 3000 //如果不配置，默认是3秒关闭
                }, function(){
                    //do something
                });
                /*//选择端口后关闭当前窗口
                 top.layer.close(index);*/
                var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
                var parentIndex = parent.layer.getFrameIndex("jbpmApplyWinId");//获取了父窗口的索引
                parent.layer.close(parentIndex);  // 关闭layer
                grid.datagrid("reload");
            }
        });
    }
</script>
<script src="<%=request.getContextPath() %>/js/jbpm/ticket/star.js"></script>
</html>