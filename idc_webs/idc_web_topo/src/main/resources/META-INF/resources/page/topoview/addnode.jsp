<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>

</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'center',iconCls:'icon-ok'">
        <form id="viewForm" method="post">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">视图类别</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" data-options="required:true,width:200" name="viewtype"
                                id="viewtype">
                            <option value="0">物理视图</option>
                            <option value="1">业务视图</option>
                            <option value="2">机房视图</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">视图名称</td>
                    <td class="kv-content">
                        <input id="viewname" name="viewname" class="easyui-textbox" data-options="required:true"
                               style="width:200px;"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">视图模式</td>
                    <td class="kv-content">
                        <select class="easyui-combobox" data-options="required:true,width:200" name="viewmode"
                                id="viewmode">
                            <option value="3">公有</option>
                            <option value="1">私有</option>
                            <option value="2">共享</option>
                        </select>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    function doSubmit(fn) {
        var viewname = $("#viewname").val();
        $('#viewForm').form('submit', {
            url:"<%=request.getContextPath() %>/topoview/add",
            success:function(data){
                data =JSON.parse(data)
                if(data.state){
                    top.layer.msg("添加视图成功，进入编辑模式")
                    fn(data.msg,viewname);
                    var parentWin = top.winref[window.name];
                    var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//                    setTimeout()
                    top.layer.close(index); //再执行关闭
                }else{
                    top.layer.msg(data.msg)
                }
            }
        });
    }
</script>
</body>
</html>