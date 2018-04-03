<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/resource.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/ajaxfileupload.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/ipsubnet/option.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/themes\css\form.css"/>
    <%--<link rel="stylesheet" type="text/css"--%>
    <%--href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/custom/uimaker/css/basic_info.css"/>--%>
    <title>子网管理</title>
    <style>
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="cc" class="easyui-layout" fit="true">
        <div class="content_suspend">
            <div class="conter">
                <ul id="tt" style="width:200px; overflow:auto;"></ul>
            </div>
            <div class="hoverbtn">
                <span>IP</span><span>导</span><span>航</span>
                <div class="hoverimg" height="9" width="13"></div>
            </div>
        </div>
        <%--<div data-options="region:'west'" style="width:200px">--%>
            <%--<ul id="tt"></ul>--%>
        <%--</div>--%>
        <div data-options="region:'center'" style="padding:0px;background:#eee;">
            <div class="easyui-panel" title="" id="dgtitle" fit="true">
                <table id="dg" fit="true" class="easyui-datagrid"></table>
            </div>
            <%--<div class="easyui-tabs" fit="true" id="tabs">--%>
               <%----%>
                <%--&lt;%&ndash;<div title="网段信息">&ndash;%&gt;--%>
                   <%--&lt;%&ndash;&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div title="IP信息">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<div class="easyui-panel" title="" id="ipdgtitle" fit="true">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<table id="ipdg" fit="true" class="easyui-datagrid"></table>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--</div>--%>

        </div>
    </div>
    <div id="toolbar" class="paramContent">
        <div class="param-fieldset">
            <%--<label id="lableName">子网地址:</label>--%>
            <input type="input" id="buiName" class="param-input" placeholder="子网地址"/>
        </div>
        <div class="btn-cls-search" onClick="searchModels();"></div>
        <%--<div class="btn-cls-reset" onClick="$('#buiName').val('')"></div>--%>

        <div class="param-actionset">
            <sec:authorize access="hasAnyRole('ROLE_resource_subnet_add','ROLE_admin')">
                <div class="btn-cls-common" id="add">新 增</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_resource_subnet_edit','ROLE_admin')">
                <div class="btn-cls-common" id="edit">修 改</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_resource_subnet_del','ROLE_admin')">
                <div class="btn-cls-common" id="del">删 除</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_resource_subnet_split','ROLE_admin')">
                <div class="btn-cls-common" id="splitsubnet">拆分子网</div>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_resource_subnet_merge','ROLE_admin')">
                <div class="btn-cls-common" id="mergesubnet">合并子网</div>
            </sec:authorize>
            <c:if test="${actiontype == null}">
                <sec:authorize access="hasAnyRole('ROLE_resource_ip_allocation','ROLE_admin')">
                    <div class="btn-cls-common" id="allocation" style="display: none">分配IP</div>
                </sec:authorize>
                <sec:authorize access="hasAnyRole('ROLE_resource_ip_recovery','ROLE_admin')">
                    <div class="btn-cls-common" id="recovery" style="display: none">回收IP</div>
                </sec:authorize>
            </c:if>
        </div>
    </div>
</div>
<div id="allocationwin" style="width: 600px;">
    <div class="easyui-panel" fit="true" style="background:#fafafa;">
        <table class="kv-table">
            <tbody>
            <tr>
                <td class="kv-label">子网地址</td>
                <td class="kv-content">
                    <input type="hidden" name="id" value="${id}" id="id"/>
                    <input class="easyui-textbox" data-options="required:true" name="subnetip" style="width: 300px"
                           id="subnetip"
                           value=""/>
                </td>

            </tr>
            <tr>
                <td class="kv-label">备注</td>
                <td class="kv-content">
                    <input class="easyui-textbox" style="width: 300px" data-options="required:true,multiline:true"  name="remark" id="remark" value=""/>
                </td>
            </tr>
            </tbody>
        </table>
        <%--<form id="ff" method="post">--%>
            <%--<div>--%>
                <%--<label for="name">Name:</label>--%>
                <%--<input class="easyui-validatebox" type="text" name="name" data-options="required:true" />--%>
            <%--</div>--%>
        <%--</form>--%>
        <%--<input class="easyui-textbox" data-options=""/>--%>
        <%--<input class="easyui-textbox" data-options=""/>--%>
    </div>
</div>
<div id="dlg_pic" class="easyui-dialog" style="width:400px;height:290px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">导入</div>
    <form id="fm_pic" method="post" enctype="multipart/form-data" novalidate>
        <div class="fitem">
            <label>excel文件:</label>
            <input type="file" name="uploadFile" id="uploadFileid" name="file"/>
            <%--	<input class="easyui-filebox" name="uploadFile" id="uploadFileid" data-options="prompt:'上传文件'">--%>
        </div>
        <div class="fitem">
            <input type="button" onclick="importFileClick()" value="导入">
            <input type="button" onclick="closeDialog()" value="取消">
        </div>
    </form>
</div>
<script type="text/javascript">
    var actiontype = "${actiontype}";

    function closeDialog(){
        $('#dlg_pic').dialog('close');
    }
    function doMy_Submit(ticketInstId,category) {
        var Rows = $("#dg").datagrid('getChecked');
        if(Rows.length==0){
            top.layer.msg("没有选择子网");
            return false;
        }
        var data = {rows:[]};

        $.each(Rows,function(index,iteam){
            if(iteam.ipaddress){
                data['type']  = 'ipaddress'
            }else{
                data['type']  = 'ipsubnet';
            }
            data.rows.push(iteam.id)
        });
        console.log(data);
        /*手动添加*/
        var sets = [];
        for(var i = 0 ; i < data.rows.length ; i++){
            var singleRecordData = data.rows[i];
            console.log(singleRecordData);
            var srd = {};
            srd['ticketInstId'] = ticketInstId;
            srd['resourceid'] = singleRecordData;
            srd['status'] = 1;/*新增*/
            srd['manual']= 1;//手工添加方式
            srd['category']= category;//手工添加方式
            srd['ipType']= data.type;//手工添加方式
            srd['ticketCategory']='100';//预勘/预占
            sets.push(srd);
        }
         $.ajax({
            type:"POST",
            url:contextPath+"/actJBPMController/createTicketServer.do",
            data:{winServerVoListStr:JSON.stringify(sets)},
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            beforeSend:function(){
            },
            success:function(data){
                layer.msg(data.msg, {
                    icon: 1,
                    time: 3000 //（默认是3秒）
                }, function(){
                    var parentIndex = parent.layer.getFrameIndex(window.name);//获取了父窗口的索引
                    parent.layer.close(parentIndex);  // 关闭layer
                });

            },
            complete: function(XMLHttpRequest, textStatus){
                return true;
            },
            error: function(){
                return false;
            }
        });

    }
    function doSubmit() {
        var selectRows = $("#dg").datagrid('getChecked');
        if(selectRows.length==0){
            top.layer.msg("没有选择子网");
            return ;
        }
        var data = {};
        var rows = [];
        var type = null;
        layer.load(2);
        var isflag = true;
        $.each(selectRows,function(index,iteam){
            if(iteam.parent){
                isflag=false
            }
            if(type==null){
                if(iteam.ipaddress){
                    type = 'ipaddress'
                }else{
                    type = 'ipsubnet'
                }
            }
          /* 以前是 rows.push(iteam.id)
          此处我需要整行的记录:刘强 */
          rows.push(iteam)
        });
        layer.closeAll('loading');
        if(!isflag){
            top.layer.msg("不能选择分类");
            return;
        }
        data['type']  = type;
        data['rows'] = rows;

        return data;
    }
</script>
</body>
</html>