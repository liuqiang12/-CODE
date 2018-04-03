<div title="资源分配" data-options="closable:false" style="overflow:auto;padding:10px;display:none;">
    <iframe id="downloadFile" src="" style="display: none"></iframe>
    <!-- 【附件列表信息】 -->
    <jbpm:jbpmTag module="TICKET_ATTACHMENT" count="${ticketAttachCount}" gridId="ticketAttachmentGridId" prodInstId="${prodInstId}" title="工单附件列表"  maxHeight="150" toolbar="ticketAttachmentBbar" ticketInstId="${ticketInstId}" isShowGridColumnHandlerFlag="${isShowGridColumnHandlerFlag}"></jbpm:jbpmTag>

    <!-- 机架机位 start -->
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${serviceApplyImgStatus.rack}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_rack_gridId">
            机架名称: <input class="easyui-textbox"  id="rackName_params" style="width:100px;text-align: left;" data-options="">
            <!-- 选择机架 -->
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('rack')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('100','','rack','')">选择机架</a>
            <span style="color:red">申请机架/机位个数为: ${idcReRackModelNum} 个</span>
        </div>
        <table class="easyui-datagrid" id="ticket_rack_gridId"></table>

        <div style="padding: 5px;" id="requestParamSettins_ticket_rackmcb_gridId">
            MCB: <input class="easyui-textbox"  id="rackmcbName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('mcb')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        </div>
        <table class="easyui-datagrid" id="ticket_rackmcb_gridId"></table>
    </jbpmSecurity:security>
    <!-- 机架机位 end -->
    <!-- 端口带宽 start -->
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${serviceApplyImgStatus.port}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_port_gridId">
            名称: <input class="easyui-textbox"  id="portName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('port')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('200','','port','')">选择带宽端口</a>
            <span style="color:red">申请带宽端口个数为: ${idcRePortModelNum} 个</span>


                <%--<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('200','','port','')">选择</a>--%>
        </div>
        <table class="easyui-datagrid" id="ticket_port_gridId"></table>
    </jbpmSecurity:security>
    <!-- 端口带宽 end -->
    <!-- ip租用 start -->
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${serviceApplyImgStatus.ip}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_ip_gridId">
            名称: <input class="easyui-textbox"  id="ipName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('ip')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="rackOrUnitOrMcbOrPortOrIpChoiceOperate('300','','ip','')">选择IP</a>
            <span style="color:red">申请IP个数为: ${idcReIpModelNum} 个</span>
        </div>
        <table class="easyui-datagrid" id="ticket_ip_gridId"></table>
    </jbpmSecurity:security>
    <!-- ip租用 end -->
    <!-- 主机租赁 start -->
    <jbpmSecurity:security module="SIMPLE_MODULE" hasSecurity="${serviceApplyImgStatus.server}">
        <div style="padding: 5px;" id="requestParamSettins_ticket_server_gridId">
            主机名称: <input class="easyui-textbox"  id="serverName_params" style="width:100px;text-align: left;" data-options="">
            <a href="javascript:void(0);" onclick="easyuiRefreshGridByChoice('server')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="preServerChoiceOperate('ticket_server_gridId')">选择</a>
            <span style="color:red">申请主机数为: ${idcReServerModelNum} 台</span>
        </div>
        <table class="easyui-datagrid" id="ticket_server_gridId"></table>
    </jbpmSecurity:security>
</div>