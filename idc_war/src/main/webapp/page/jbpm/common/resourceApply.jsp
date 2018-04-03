<div title="预勘审批" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
    <div id="apply_info_layout" class="easyui-layout"  fit="true">
        <div data-options="region:'north',title:'历史审批...'" style="height:200px;">
            <table class="easyui-datagrid" id="ticket_hisComment_gridId" fit="true"></table>
        </div>
        <div data-options="region:'center'" style="padding:2px;">
            <form method="post" id="singleForm" action="<%=request.getContextPath() %>/actJBPMController/procCmentFormSaveOrUpdate.do">
                <table class="kv-table">
                    <tr>

                        <!-- 隐藏的属性 -->
                        <!-- 隐藏的属性 -->
                        <input name="id" id="idcRunProcCment_id" type="hidden" value="${idcRunProcCment.id}">
                        <!-- 工单实例 -->
                        <input name="ticketInstId" id="ticketInstId_param" type="hidden" value="${ticketInstId}"><input name="is_author_apply_show" id="is_author_apply_show" type="hidden" value="${is_author_apply_show}">
                        <!-- 订单实例 -->
                        <input name="prodInstId" id="prodInstId_param" type="hidden" value="${prodInstId}"><input  id="category_param" type="hidden" value="${category}">
                        <!-- 流程实例 -->
                        <input name="procInstId" type="hidden" value="${processInstanceId}">
                        <!-- 执行实例 -->
                        <input name="executionid" type="hidden" value="${processInstanceId}">
                        <!-- 任务ID -->
                        <input name="taskId" type="hidden" value="${taskid}">
                        <!-- formKey -->
                        <input name="formKey" type="hidden" value="${formKey}">
                        <!-- 流程实例定义的KEY -->
                        <input name="processDefinitionKey" type="hidden" value="${processDefinitionKey}" id="processDefinitionKey">
                        <!-- 申请人ID -->
                        <input name="authorId" type="hidden" value="${authorId}">
                        <!-- 控制操作列的显示与否 -->
                        <input id="isShowGridColumnHandlerFlag" type="hidden" value="${isShowGridColumnHandlerFlag}">
                        <input name="status" id="idcRunProcCment_status_stand" value="<c:if test="${empty idcRunProcCment.status}">1</c:if><c:if test="${not empty idcRunProcCment.status}">${idcRunProcCment.status}</c:if>" type="hidden"/>
                    </tr>
                    <tr>
                        <td class="kv-label"style="width:100px" >预勘描述<span style="color:red">&nbsp;*</span></td>
                        <td class="kv-content" colspan="3">
                            <input class="easyui-textbox" data-options="required:true,width:780,height:100,multiline:true" name="comment" value="${idcRunProcCment.comment}"  data-options="validType:'length[0,255]'"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>