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
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link href="<%=request.getContextPath() %>/framework/bootstrap/css/ztree/zTreeStyle.css" rel="stylesheet">
</head>
<body>
<div class="easyui-panel" title=""  border="true" style="width:100%;padding:10px 30px;">
    <form method="post" id="signupForm" action="<%=request.getContextPath() %>/userinfo/save.do">
        <table class="dv-table" style="width:100%;padding:5px;margin-top:1px;">
            <tr>
                <td>组名:</td>
                <td>
                    <input type="hidden"  name="id"  value="${group.id}" />
                    <input type="text" class="easyui-textbox" name="name" placeholder="请输入组名"
                           data-options="required:true,width:320" value="${group.name}"/>
                </td>
            </tr>
            <tr>
                <td>上级组:</td>
                <td>
                    <input type="hidden" name="parentId" value="${empty pgroup?"":pgroup.id}"/>
                    <input type="text" class="easyui-textbox" name="parentname" data-options="width:320"
                           placeholder="没有选择上级组" readonly="readonly" value="${pgroup.name}"/>
                </td>
            </tr>
            <tr>
                <td>用户组编码:</td>
                <td>
                    <input type="text" class="easyui-textbox" name="grpCode" data-options="required:true,width:320"
                           placeholder="请输入编码" value="${group.grpCode}"/>
                </td>
            </tr>
            <tr>
                <td>绑定角色：
                </td>
                <td colspan="3">
                    <input id = "roleids" class="easyui-combotree"  name="roleids"
                           data-options="url:'<%=request.getContextPath() %>/sysrole/list.do',checkbox:true,multiple:true,
                           width:320,
                           onLoadSuccess:function(node, data){
                               var defaultval = new Array()
                                 <c:if test="${ !empty roles}">
                                    <c:forEach items="${roles}" var="role" >
                                            defaultval.push(${role.id});
                                     </c:forEach>
                                  </c:if>
                                 $('#roleids').combotree('setValues',defaultval);
                           },loadFilter:function(data){
                               for(var i=0;i<data.rows.length;i++){
                                    data.rows[i].text=data.rows[i].name
                               }
                               console.log(data.rows)
                               return data.rows;
                           }"/>
                </td>
            </tr>
            <tr>
                <td>描述:</td>
                <td>
                    <input class="easyui-textbox" value="${group.description}" name="description" id="description" data-options="required:true,multiline:true,width:320"/>
                </td>
            </tr>
        </table>
        <div style="text-align:center;padding:0px 0">
            <%--<input class='submit' type='submit'class="easyui-linkbutton" iconCls="icon-save" value='提交'>--%>
            <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" iconCls="icon-save"  style="width:80px">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()" iconCls="icon-cancel"  style="width:80px">关闭</a>
        </div>
    </form>
    <%--</div>--%>
    <%--<script src="<%=request.getContextPath() %>/framework/jeasyui/plugins/jquery.form.js"></script>--%>
    <%--<script src="<%=request.getContextPath() %>/framework/jeasyui/validationPlus.js"></script>--%>
    <%--<script type="text/javascript"  src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>--%>
<script>
    function closeWin(){
//        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
//         parent.layer.close(index); //再执行关闭
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
                        $.post(contextPath+"/usergroup/save.do",$(this).serialize(),function(data){
                            alert(data.msg);
                            if(data.state){
//                                parent.document.getElementById("maincontent").contentWindow.refTree();
//                                parent.document.getElementById("maincontent").contentWindow.refreshTable();
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