<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ page isErrorPage="true" %>--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <%--<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">--%>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">


</head>
<body>
<div class="easyui-panel" title=""  border="true" style="width:100%;padding:10px 30px;">
    <form method="post" id="signupForm" action="<%=request.getContextPath() %>/userinfo/save.do">
        <table class="dv-table" style="width:100%;padding:5px;margin-top:1px;">
            <tr>
                <td>角色名称:</td>
                <td>
                    <input type="hidden"  name="id"  value="${roleinfo.id}" />
                    <input type="text" data-options="width:200" id="name" name="name" prompt="请输入角色名" class="easyui-textbox" required="true" value="${roleinfo.name}" />
                </td>
            </tr>
            <tr>
                <td>角色关键字:</td>
                <td>
                    <input type="text" data-options="width:200" id="role_key" name="role_key" prompt="请输入角色关键字"
                           class="easyui-textbox" required="true" value="${roleinfo.role_key}"/>
                </td>
            </tr>
            <tr>
                <td>角色类型:</td>
                <td>
                    <select name="type" class="easyui-combobox" data-options="width:200">
                        <option value="user">USER</option>
                        <option value="dba">DBA</option>
                        <option value="admin">ADMIN</option>
                        <option value="root">ROOT</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>角色状态:</td>
                <td>
                    <select name="enabled" class="easyui-combobox">
                        <option value="1" <c:if test="${empty roleinfo.enabled||roleinfo.enabled eq 1}">selected</c:if>>激活</option>
                        <option value="0" <c:if test="${not empty roleinfo.enabled && roleinfo.enabled eq 0}">selected</c:if>>未激活</option>
                    </select>
                    <%--<input class="easyui-switchbutton" value="<c:if test="${empty roleinfo.enabled||roleinfo.enabled eq 1}">on</c:if>" data-options="onText:'启用',offText:'禁用',handleText:'${roleinfo.enabled}',onChange:function(checked){if(checked){ $('#enabled').val(0)}else {$('#enabled').val(1)}}">--%>
                    <%--<input type="hidden" id="enabled" name="enabled" value="${roleinfo.enabled}">--%>
                    <%--<label> <input type="radio" <c:if test="${empty roleinfo.enabled||roleinfo.enabled eq 1}">checked=""</c:if> value="1" name="enabled"> <i></i> 启用</label>--%>
                    <%--<label> <input type="radio" <c:if test="${roleinfo.enabled eq 0}">checked=""</c:if> value="0" name="enabled"> <i></i> 禁用</label>--%>
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td>角色权限:</td>--%>
                <%--<td>--%>
                    <%--<select multiple="multiple" size="5" style="width: 80%">--%>
                        <%--<option value ="volvo">Volvo</option>--%>
                        <%--<option value ="saab">Saab</option>--%>
                        <%--<option value="opel">Opel</option>--%>
                        <%--<option value="audi">Audi</option>--%>
                    <%--</select>--%>
                    <%--<select multiple="multiple" size="5" style="width: 80%">--%>
                        <%--<option value ="volvo">Volvo</option>--%>
                        <%--<option value ="saab">Saab</option>--%>
                        <%--<option value="opel">Opel</option>--%>
                        <%--<option value="audi">Audi</option>--%>
                    <%--</select>--%>
                    <%--&lt;%&ndash;<input class="easyui-textbox" value="${roleinfo.description}" name="description" id="description" data-options="required:true,multiline:true" style="width:80%;height:60px"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input type="button" value="浏览">&ndash;%&gt;--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>描述:</td>
                <td>
                    <input class="easyui-textbox" value="${roleinfo.description}" name="description" id="description" data-options="required:true,multiline:true,width:300"/>
                </td>
            </tr>
        </table>
        <div style="text-align:center;padding:0px 0">
            <%--<input class='submit' type='submit'class="easyui-linkbutton" iconCls="icon-save" value='提交'>--%>
            <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" iconCls="icon-save"  style="width:80px">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()" iconCls="icon-cancel"  style="width:80px">关闭</a>
        </div>
    </form>
</div>
<%--<div class="container" style="padding: 10px 20px 10px 20px; ">--%>
    <%--<form class="form-horizontal" role="form" id="signupForm">--%>
        <%--<div class="form-group" >--%>
            <%--<label class="col-sm-1 control-label" for="name"> 角色名称 </label>--%>
            <%--<div class="col-sm-3">--%>
                <%--<input type="hidden"  name="id"  value="${roleinfo.id}" />--%>
                <%--<input type="text"  id="name" name="name" placeholder="请输入角色名" class="form-control" value="${roleinfo.name}" />--%>
            <%--</div>--%>
            <%--<label class="col-sm-1 control-label" for="type"> 角色类型</label>--%>
            <%--<div class="col-sm-3">--%>
                <%--<select name="type" class="form-control">--%>
                    <%--&lt;%&ndash;<option value="">角色类型</option>&ndash;%&gt;--%>
                    <%--<option value="user">USER</option>--%>
                    <%--<option value="dba">DBA</option>--%>
                    <%--<option value="admin">ADMIN</option>--%>
                    <%--<option value="root">ROOT</option>--%>
                <%--</select>--%>
            <%--</div>--%>
            <%--<label class="col-sm-1 control-label" for="enabled"> 角色状态</label>--%>
            <%--<div class="col-sm-3">--%>
                <%--<div class="radio i-checks">--%>
                    <%--<label>  <input type="radio" <c:if test="${empty roleinfo.enabled||roleinfo.enabled eq 1}">checked=""</c:if> value="1" name="enabled"> <i></i> 启用</label>--%>
                    <%--<label>  <input type="radio" <c:if test="${roleinfo.enabled eq 0}">checked=""</c:if> value="0" name="enabled"> <i></i> 禁用</label>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--&lt;%&ndash;<div class="col-sm-8">&ndash;%&gt;--%>
                <%--&lt;%&ndash;<div class="radio i-checks">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<label>  <input type="radio" <c:if test="${user.sex ne '女'}">checked=""</c:if> value="男" name="sex"> <i></i> 男</label>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<label>  <input type="radio" <c:if test="${user.sex eq '女'}">checked=""</c:if> value="女" name="sex"> <i></i> 女</label>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
            <%--<label class="col-sm-1 control-label" for="description"> 描述 </label>--%>
            <%--<div class="col-sm-11">--%>
                <%--<textarea class="form-control"  name="description" id="description" rows="4">${roleinfo.description}</textarea>--%>
            <%--</div>--%>
        <%--</div>--%>

        <%--<div class="clearfix form-actions" style="text-align: center;margin-top: 10px">--%>
            <%--<div>--%>
                <%--<button id="submit" class="btn btn-primary" type="submit">提交</button>--%>
                <%--&nbsp; &nbsp; &nbsp;--%>
                <%--<button class="btn" type="reset">--%>
                    <%--<i class="icon-undo bigger-110"></i>--%>
                    <%--重置--%>
                <%--</button>--%>
            <%--</div>--%>
        <%--</div>--%>

    <%--</form>--%>
<%--</div><!-- /.row -->--%>
<%--</div><!-- /.page-content -->--%>
<script src="<%=request.getContextPath() %>/framework/jeasyui/plugins/jquery.form.js"></script>
<script src="<%=request.getContextPath() %>/framework/jeasyui/validationPlus.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>
<script>
    function closeWin(){
//        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//        parent.layer.close(index); //再执行关闭
        var parentWin = top.winref[window.name];
        top[parentWin].$('#table').datagrid("reload");
        var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        top.layer.close(index); //再执行关闭
    }
    $(document).ready(function () {

        $("#submit").on('click',function(){
            $("#signupForm").form('submit', {
                onSubmit: function () {
                    var flag = $(this).form('validate');
                    if(flag){
                        $.post(contextPath+"/sysrole/save.do",$(this).serialize(),function(data){
                            alert(data.msg);
                            if(data.state){
                                //parent.document.getElementById("maincontent").contentWindow.refreshTable();
                                closeWin();
                            }
                        });
                    }
                    return false;
                }
            });
        });
    });
</script>
</body>
</html>