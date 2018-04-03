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
                <td>部门名称:</td>
                <td>
                    <input type="hidden"  name="id"  value="${dpt.id}" />
                    <input type="text" class="easyui-textbox" name="name" style="width: 80%;;" placeholder="请输入组名" data-options="required:true" value="${dpt.name}" />
                </td>
            </tr>
            <tr>
                <td>上级部门:</td>
                <td>
                    <input id = "pdptid" class="easyui-combotree"  name="pdptid" style="width: 80%"
                           data-options="url:'<%=request.getContextPath() %>/sysdepartment/list.do',checkbox:true,panelHeight:120,
                           onLoadSuccess:function(node, data){
                                  var defaultval = new Array()
                                 <c:if test="${ !empty pdpt}">
                                            defaultval.push(${pdpt.id});
                                  </c:if>
                                 $('#pdptid').combotree('setValues',defaultval);
                           },loadFilter:function(data){
                                var rows =new Array();
                                $.each(data.rows,function(i,row){
                                   <c:if test="${ !empty dpt && dpt.id ne ''}">
                                           if(row.id!=${dpt.id})
                                  </c:if>
                                  rows.push(row);
                                })
                                return convert(rows);
                           }"/>
                </td>
            </tr>
            <tr>
                <td>所属机构:</td>
                <td>
                    <input id = "orgId" class="easyui-combotree"  name="orgId" style="width: 80%"
                           data-options="url:'<%=request.getContextPath() %>/organization/list.do',checkbox:true,panelHeight:120,
                           onLoadSuccess:function(node, data){
                                  var defaultval = new Array()
                                 <c:if test="${ !empty dpt}">
                                            defaultval.push(${dpt.orgId});
                                  </c:if>
                                 $('#orgId').combotree('setValues',defaultval);
                           },loadFilter:function(data){
                                return convert(data.rows);
                           }"/>
                </td>
            </tr>
            <tr>
                <td>描述:</td>
                <td>
                    <input class="easyui-textbox" value="${dpt.description}" name="description" id="description" data-options="required:true,multiline:true" style="width:80%;height:60px"/>
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
<script src="<%=request.getContextPath() %>/framework/jeasyui/plugins/jquery.form.js"></script>
<script src="<%=request.getContextPath() %>/framework/jeasyui/validationPlus.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>
<script>
    function convert(rows){
        function exists(rows, parentId){
            for(var i=0; i<rows.length; i++){
                if (rows[i].id == parentId) return true;
            }
            return false;
        }

        var nodes = [];
        // get the top level nodes
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (!exists(rows, row._parentId)){
                nodes.push({
                    id:row.id,
                    text:row.name
                });
            }
        }

        var toDo = [];
        for(var i=0; i<nodes.length; i++){
            toDo.push(nodes[i]);
        }
        while(toDo.length){
            var node = toDo.shift();	// the parent node
            // get the children nodes
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                if (row._parentId == node.id){
                    var child = {id:row.id,text:row.name};
                    if (node.children){
                        node.children.push(child);
                    } else {
                        node.children = [child];
                    }
                    toDo.push(child);
                }
            }
        }
        return nodes;
    }
    function closeWin(){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
         parent.layer.close(index); //再执行关闭
    }
    $(document).ready(function () {
        $("#submit").on('click',function(){
            $("#signupForm").form('submit', {
                onSubmit: function () {
                    var flag = $(this).form('validate');
                    if(flag){
                        $.post(contextPath+"/sysdepartment/save.do",$(this).serialize(),function(data){
                            if(data.state){
                                layer.msg('保存成功');
                                parent.document.getElementById("maincontent").contentWindow.refreshTable1();
                                closeWin();
                            }else{
                                layer.msg(data.msg);
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