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
            src="<%=request.getContextPath() %>/js/${type}/option.js"></script>

    <%--<link rel="stylesheet" type="text/css"--%>
    <%--href="<%=request.getContextPath() %>/framework/jeasyui/jquery-easyui-1.5/themes/custom/uimaker/css/basic_info.css"/>--%>
    <title>资源占用流程</title>
    <style>
        .flex-center {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .container {
            display: inline-block;
            width: 50%;
            height: 20px;
            padding-right: 10px;
            border: 1px solid #999;
            border-radius: 5px;
        }

        .h-100P {
            height: 100%;
        }

        .bar {
            display: inline-block;
            background: #90bf46;
            color: white;
            font-weight: bold;
            padding: 0 5px;
            text-align: right;
            border-radius: 5px;
            border-right: 1px solid #999;
        }
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="cc" class="easyui-layout" fit="true">
        <div data-options="region:'center'" style="background:#eee;">
            <table id="dg" fit="true" class="easyui-datagrid"></table>
        </div>
    </div>
    <div id="toolbar" class="paramContent">
        <div class="param-fieldset">
            <input type="input" id="buiName" class="param-input" placeholder="${searchStr}" style="width: 200px;margin-right: 10px;"/>
            <c:choose>
                <c:when test="${type eq 'idcrack'}">
                    <label>机架类型:</label>
                    <select id="rackType" class="easyui-combobox" name="dept"
                            data-options="panelHeight:'auto',editable:false,width:100,height:22">
                        <option value="">----</option>
                        <option value="equipment">客户机柜</option>
                        <option value="cabinet">网络柜</option>
                        <option value="wiring">配线柜</option>
                    </select>
                </c:when>
                <c:when test="${type eq 'device'}">
                    <label>设备层次:</label>
                    <select id="netType" class="easyui-combobox"
                            data-options="panelHeight:'auto',editable:false,width:100,height:22">
                        <option value="">----</option>
                        <option value="1">核心</option>
                        <option value="2">接入</option>
                        <option value="3">汇聚</option>
                    </select>
                </c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
        </div>
        <div class="btn-cls-search" onClick="searchModels();"></div>
        <%--<div class="btn-cls-reset" onClick="javascript:$('#buiName').val('')"></div>--%>

        <div class="param-actionset">
            <c:choose>
                <c:when test="${type == 'location'}">
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_location_add','ROLE_admin')">
                        <div class="btn-cls-common" id="add">新 增</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_location_edit','ROLE_admin')">
                        <div class="btn-cls-common" id="edit">修 改</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_location_del','ROLE_admin')">
                        <div class="btn-cls-common" id="del">删 除</div>
                    </sec:authorize>
                </c:when>
                <c:when test="${type == 'building'}">
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_building_add','ROLE_admin')">
                        <div class="btn-cls-common" id="add">新 增</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_building_edit','ROLE_admin')">
                        <div class="btn-cls-common" id="edit">修 改</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_building_del','ROLE_admin')">
                        <div class="btn-cls-common" id="del">删 除</div>
                    </sec:authorize>
                </c:when>
                <c:when test="${type == 'machineroom'}">
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_machineroom_add','ROLE_admin')">
                        <div class="btn-cls-common" id="add">新 增</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_machineroom_edit','ROLE_admin')">
                        <div class="btn-cls-common" id="edit">修 改</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_machineroom_del','ROLE_admin')">
                        <div class="btn-cls-common" id="del">删 除</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_machineroom_import','ROLE_admin')">
                        <div class="btn-cls-common" id="importData">导 入</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_machineroom_export','ROLE_admin')">
                        <div class="btn-cls-common" id="exportData">导 出</div>
                    </sec:authorize>
                    <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_machineroom_viewroom2d','ROLE_admin')">--%>
                    <div class="btn-cls-common" id="view2d">查看机房平面</div>
                    <%--</sec:authorize>--%>
                </c:when>
                <c:when test="${type == 'idcrack'}">
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idcrack_add','ROLE_admin')">
                        <div class="btn-cls-common" id="add">新 增</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idcrack_edit','ROLE_admin')">
                        <div class="btn-cls-common" id="edit">修 改</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idcrack_del','ROLE_admin')">
                        <div class="btn-cls-common" id="del">删 除</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idcrack_import','ROLE_admin')">
                        <div class="btn-cls-common" id="importData">导 入</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idcrack_export','ROLE_admin')">
                        <div class="btn-cls-common" id="exportData">导 出</div>
                    </sec:authorize>
                    <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_idcrack_viewrack2d','ROLE_admin')">--%>
                    <div class="btn-cls-common" id="viewrack2d">查看机架视图</div>
                    <div class="btn-cls-common" id="bingConnecter">建立链路</div>
                    <%--</sec:authorize>--%>
                    <div class="btn-cls-common" id="isp_rack_upload">ISP上报</div>
                    <div class="btn-cls-common" id="isp_rack_delete">ISP删除</div>
                </c:when>
                <c:when test="${type == 'pdf'}">
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_pdf_add','ROLE_admin')">
                        <div class="btn-cls-common" id="add">新 增</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_pdf_edit','ROLE_admin')">
                        <div class="btn-cls-common" id="edit">修 改</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_pdf_del','ROLE_admin')">
                        <div class="btn-cls-common" id="del">删 除</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_pdf_import','ROLE_admin')">
                        <div class="btn-cls-common" id="importData">导 入</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_pdf_export','ROLE_admin')">
                        <div class="btn-cls-common" id="exportData">导 出</div>
                    </sec:authorize>
                    <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_pdf_viwepdf2d','ROLE_admin')">--%>
                    <div class="btn-cls-common" id="viwePdf2d">查看PDF架视图</div>
                    <%--</sec:authorize>--%>
                    <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_pdf_mcb','ROLE_admin')">--%>
                    <div class="btn-cls-common" id="mcbList">PDU列表</div>
                    <%--</sec:authorize>--%>
                </c:when>
                <c:when test="${type == 'odf'}">
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_odf_add','ROLE_admin')">
                        <div class="btn-cls-common" id="add">新 增</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_odf_edit','ROLE_admin')">
                        <div class="btn-cls-common" id="edit">修 改</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_odf_del','ROLE_admin')">
                        <div class="btn-cls-common" id="del">删 除</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_odf_import','ROLE_admin')">
                        <div class="btn-cls-common" id="importData">导 入</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_odf_export','ROLE_admin')">
                        <div class="btn-cls-common" id="exportData">导 出</div>
                    </sec:authorize>
                    <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_odf_viweodf2d','ROLE_admin')">--%>
                    <div class="btn-cls-common" id="viweOdf2d">查看ODF架视图</div>
                    <%--</sec:authorize>--%>
                    <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_odf_connector','ROLE_admin')">--%>
                    <div class="btn-cls-common" id="connectorList">端子列表</div>
                    <%--</sec:authorize>--%>
                </c:when>
                <c:when test="${type == 'device'}">
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_add','ROLE_admin')">
                        <div class="btn-cls-common" id="add">新 增</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_edit','ROLE_admin')">
                        <div class="btn-cls-common" id="edit">修 改</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_del','ROLE_admin')">
                        <div class="btn-cls-common" id="del">删 除</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_import','ROLE_admin')">
                        <div class="btn-cls-common" id="importData">导 入</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_export','ROLE_admin')">
                        <div class="btn-cls-common" id="exportData">导 出</div>
                    </sec:authorize>
                    <%--<sec:authorize access="hasAnyRole('ROLE_sys_resource_device_prot','ROLE_admin')">--%>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_upordown','ROLE_admin')">
                        <div class="btn-cls-common" id="upordownDevice">视图上下架</div>
                    </sec:authorize>
                    <div class="btn-cls-common" id="netProt">端口列表</div>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_online','ROLE_admin')">
                        <input type="hidden" name="downOrUp" value="1"/>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_down','ROLE_admin')">
                        <input type="hidden" name="downOrUp" value="2"/>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_quickOnline','ROLE_admin')">
                        <input type="hidden" name="quickDownOrUp" value="1"/>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_quickDown','ROLE_admin')">
                        <input type="hidden" name="quickDownOrUp" value="2"/>
                    </sec:authorize>
                    <%--</sec:authorize>--%>
                </c:when>
                <c:when test="${type == 'idchost'}">
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idchost_add','ROLE_admin')">
                        <div class="btn-cls-common" id="add">新 增</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idchost_edit','ROLE_admin')">
                        <div class="btn-cls-common" id="edit">修 改</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idchost_del','ROLE_admin')">
                        <div class="btn-cls-common" id="del">删 除</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idchost_import','ROLE_admin')">
                        <div class="btn-cls-common" id="importData">导 入</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idchost_export','ROLE_admin')">
                        <div class="btn-cls-common" id="exportData">导 出</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_idchost_upordown','ROLE_admin')">
                        <div class="btn-cls-common" id="upordownDevice">视图上下架</div>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_online','ROLE_admin')">
                        <input type="hidden" name="downOrUp" value="1"/>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_down','ROLE_admin')">
                        <input type="hidden" name="downOrUp" value="2"/>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_quickOnline','ROLE_admin')">
                        <input type="hidden" name="quickDownOrUp" value="1"/>
                    </sec:authorize>
                    <sec:authorize access="hasAnyRole('ROLE_sys_resource_device_quickDown','ROLE_admin')">
                        <input type="hidden" name="quickDownOrUp" value="2"/>
                    </sec:authorize>
                </c:when>
                <c:otherwise>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<div id="dlg_pic" class="easyui-dialog" style="width:400px;height:290px;padding:10px 20px"
     closed="true" closable="false">
    <form id="fm_pic" method="post" enctype="multipart/form-data" novalidate>
        <div class="fitem">
            <label style="font-size: 14px;margin-bottom: 10px;">EXCEL文件:</label>
            <input class="easyui-filebox" name="uploadFile" id="uploadFileid" style="width: 100%;"
                   data-options="prompt:'请选择要上传的文件',buttonText:'&nbsp;浏&nbsp;&nbsp;览&nbsp;'">
        </div>
        <div style="text-align:center;padding:0px 0px;margin: 10px;">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="importFileClick(),showJdt()" id="importFile"
               style="width:60px">导入</a>&nbsp;&nbsp;
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDialog()" style="width:60px">关闭</a>
        </div>
        <div class="flex-center" id="dlg_jdt">
            <h3>正在导入数据，请稍等...</h3>
            <span class="container">
            <span id="progressBar" class="h-100P bar"></span>
        </span>
        </div>
    </form>
</div>
<script type="text/javascript">
    var tableId = 'dg';
    var flag = null;
    $(function () {
        hideJdt();
    });
    function hideJdt() {
        $("#dlg_jdt").hide();
    }
    function closeDialog(){
        $('#progressBar').css('width', '0%');
        $('#progressBar').text('');
        $('h3').html('<span>正在导入数据，请稍等...</span>');
        setFlagValue(null);
        $('#dlg_pic').dialog('close');
        $('#dg').datagrid("reload");
    }
    function setFlagValue(value) {
        flag = value;
    }
    function showJdt() {
        $("#dlg_jdt").show();
        var percentage = 0;
        var interval = setInterval(function () {
            if (percentage < 95) {
                percentage += Math.floor(Math.random() * 5);
                var widthTemp = percentage + '%';
                $('#progressBar').css('width', widthTemp);
                $('#progressBar').text(widthTemp);
            }
            if (flag != null && flag == 'success') {
                clearInterval(interval);
                $('h3').html('<span style="color: #19c211">数据导入成功</span>');
                $('#progressBar').css('width', '100%');
                $('#progressBar').text('100%');
            } else if (flag != null && flag == 'error') {
                clearInterval(interval);
                $('h3').html('<span style="color: red">数据导入失败</span>');
                $('#progressBar').css('width', '0%');
                $('#progressBar').text('');
            }
        }, 100);
    }
</script>
</body>
</html>