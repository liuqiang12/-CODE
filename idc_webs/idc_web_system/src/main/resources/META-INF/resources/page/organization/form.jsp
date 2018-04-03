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
                <td>机构名称:</td>
                <td>
                    <input type="hidden"  name="id"  value="${org.id}" />
                    <input type="text" class="easyui-textbox" name="name" style="width: 80%;;" placeholder="请输入组名" data-options="required:true" value="${org.name}" />
                </td>
            </tr>
            <tr>
                <td>上级机构:</td>
                <td>
                    <%--<input type="hidden"  name="parentId"  value="${empty porg?"":porg.id}" />--%>
                    <input id = "porgid" class="easyui-combotree"  name="porgid" style="width: 80%"
                           data-options="url:'<%=request.getContextPath() %>/organization/list.do',checkbox:true,panelHeight:120,
                           onLoadSuccess:function(node, data){
                                  var defaultval = new Array()
                                 <c:if test="${ !empty porg}">
                                            defaultval.push(${porg.id});
                                  </c:if>
                                 $('#porgid').combotree('setValues',defaultval);
                           },loadFilter:function(data){
                               var rows =new Array();
                                $.each(data.rows,function(i,row){
                                   <c:if test="${ !empty org && org.id ne ''}">
                                           if(row.id!=${org.id})
                                  </c:if>
                                  rows.push(row);
                                });
                                return convert(rows);
                           }"/>
                </td>
            </tr>
            <tr>
                <td>所属区域：
                </td>
                <td colspan="3">
                    <input id = "regionId" class="easyui-combotree"  name="regionId" style="width: 80%"
                           data-options="url:'<%=request.getContextPath() %>/sysregion/tree.do',checkbox:true,panelHeight:110,required:true,
                           onLoadSuccess:function(node, data){
                               var defaultval = new Array()
                                 <c:if test="${ !empty org.regionId}">
                                            defaultval.push(${org.regionId});
                                  </c:if>
                                 $('#regionId').combotree('setValues',defaultval);
                           },loadFilter:function(data){
                               for(var i=0;i<data.length;i++){
                                    data[i].text=data[i].name
                               }
                               return data;
                           }"/>
                </td>
            </tr>
            <tr>
                <td>描述:</td>
                <td>
                    <input class="easyui-textbox" value="${org.description}" name="description" id="description" data-options="required:true,multiline:true" style="width:80%;height:60px"/>
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
                        $.post(contextPath+"/organization/save.do",$(this).serialize(),function(data){
                            if(data.state){
                                layer.msg('保存成功');
                                parent.document.getElementById("maincontent").contentWindow.refreshTable();
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