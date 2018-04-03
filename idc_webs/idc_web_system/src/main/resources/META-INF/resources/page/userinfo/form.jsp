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

    <script type="text/javascript" language="JavaScript">
           parent.window.onresize=function(){
               ThisiframeAuto();
            }
        function ThisiframeAuto(){
            var h=($(top).height()||0);
            var w=($(top).width()||0);
//            $(window).width(w*0.7);
//            $(window).height(h*0.7)
             $(document.body).height(h*0.7);
             $(document.body).width(w*0.7);
            var index = parent.layer.getFrameIndex(window.name);
            layer.iframeAuto(index);// - 指定iframe层自适应
        }

    </script>
    <style>
        .ztree li {
            background-color: white;
        }
        .dv-table td {
            padding: 5px 0px 5px 0px ;
        }
    </style>
    <script type="text/javascript">
        $(function(){
            //绑定数据中心
            var locationCode = "${user.locationCode}";
            $("#locationCode").combobox("setValue", locationCode);
        })
    </script>
</head>
<body>
<div class="easyui-panel"  border="false" style="width:100%;padding:10px 30px;">
    <form method="post" id="signupForm" action="<%=request.getContextPath() %>/userinfo/save.do">
        <table class="dv-table" style="width:100%;padding:5px;margin-top:1px;">
            <tr>
                <td>用户名:</td>
                <td>
                    <input value="${user.id}" name="id" type="hidden">
                    <input name="username" class="easyui-validatebox easyui-textbox" data-options="width:200" required="true" value="${user.username}"/>
                </td>
                <td>昵称:</td>
                <td><input name="nick" class="easyui-validatebox easyui-textbox" data-options="width:200" required="true" value="${user.nick}"/></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td>
                    <input name="oldpassword" type="hidden" value="${user.password}"/>
                    <input name="password" id="password" type="password" class="easyui-validatebox easyui-textbox"
                           required="true"
                           value="<c:if test="${!empty user.password}">******</c:if>"
                           data-options="prompt: '******',width:200"/>
                </td>
                <td>确认密码:</td>
                <td><input type="password" class="easyui-validatebox easyui-textbox" required="true"
                           validType="equalTo['#password']"
                           value="<c:if test="${!empty user.password}">******</c:if>"
                           data-options="prompt: '******',width:200"
                           invalidMessage="两次输入密码不匹配"/></td>
            </tr>
            <tr>
                <td>联系电话：</td>
                <td><input name="phone" class="easyui-textbox" value="${user.phone}"
                           data-options="validType:['mobileTelephone'],width:200"/></td>
                <td>邮箱：</td>
                <td><input name="email" class="easyui-textbox" value="${user.email}"
                           data-options="validType:['email'],width:200"/></td>
            </tr>
            <tr>
                <td>数据中心</td>
                <td>
                    <select class="easyui-combobox" name="locationCode" id="locationCode" data-options="
                             panelHeight:100,
                            editable:false,
                            width:200,
                            valueField:'code',
                            textField:'name',
                            url:'<%=request.getContextPath() %>/idcBuilding/ajaxLocation'
                            ">
                    </select>
                </td>
                <td>所属用户组：
                </td>
                <td >
                    <input id = "groupid" class="easyui-combotree" name="groupid"
                           data-options="url:'<%=request.getContextPath() %>/usergroup/tree.do',
                           width:200,
                           checkbox:true,multiple:true,
                           onLoadSuccess:function(node, data){
                               var defaultval = new Array()
                                 <c:if test="${ !empty groups}">
                                    <c:forEach items="${groups}" var="group" >
                                            defaultval.push(${group.id});
                                     </c:forEach>
                                  </c:if>
                                 $('#groupid').combotree('setValues',defaultval);
                           }"/>
                </td>
            </tr>
            <tr>
                <%--<td>用户角色：</td>--%>
                <%--<td>--%>
                <%--<input id = "roleids" class="easyui-combotree"  name="roleids" style="width: 80%"--%>
                <%--data-options="url:'<%=request.getContextPath() %>/sysrole/list.do',checkbox:true,multiple:true,--%>
                <%--onLoadSuccess:function(node, data){--%>
                <%--var defaultval = new Array()--%>
                <%--<c:if test="${ !empty roles}">--%>
                <%--<c:forEach items="${roles}" var="role" >--%>
                <%--defaultval.push(${role.id});--%>
                <%--</c:forEach>--%>
                <%--</c:if>--%>
                <%--$('#roleids').combotree('setValues',defaultval);--%>
                <%--},loadFilter:function(data){--%>
                <%--for(var i=0;i<data.rows.length;i++){--%>
                <%--data.rows[i].text=data.rows[i].name--%>
                <%--}--%>
                <%--console.log(data.rows)--%>
                <%--return data.rows;--%>
                <%--}"/>--%>
                <%--</td>--%>
                <td>所属区域：</td>
                <td>
                    <input id = "regions" class="easyui-combotree" name="regions"
                           data-options="url:'<%=request.getContextPath() %>/sysregion/regionTree',
                           width:200,
                            required:true,
                           <%--checkbox:true,multiple:true,cascadeCheck: false,--%>
                           onLoadSuccess:function(node, data){
                               var defaultval = new Array()
                                 <c:if test="${ !empty regions}">
                                    <c:forEach items="${regions}" var="region" >
                                            defaultval.push(${region.id});
                                     </c:forEach>
                                  </c:if>
                                 $('#regions').combotree('setValues',defaultval);
                           } "/>
                </td>
                <td>所属部门：</td>
                <td>
                    <input id = "departmentIds" class="easyui-combotree" name="departmentIds"
                           data-options="url:'<%=request.getContextPath() %>/sysdepartment/list1',
                           width:200,
                            required:true,
                           checkbox:true,multiple:true,cascadeCheck: false,
                           onLoadSuccess:function(node, data){
                               var defaultval = new Array()
                                 <c:if test="${ !empty departments}">
                                    <c:forEach items="${departments}" var="department" >
                                            defaultval.push(${department.id});
                                     </c:forEach>
                                  </c:if>
                                 $('#departmentIds').combotree('setValues',defaultval);
                           },loadFilter:function(data){
                               for(var i=0;i<data.rows.length;i++){
                                    data.rows[i].text=data.rows[i].name
                               }
                               return data.rows;
                           }"/>
                </td>
            </tr>
            <tr>
                <td colspan="4"></td>
            </tr>
        </table>
        <div style="text-align:center;padding:0px 0">
            <c:if test="${empty flag}">
                <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" iconCls="icon-save"  style="width:80px">保存</a>
            </c:if>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()" iconCls="icon-cancel"  style="width:80px">关闭</a>
        </div>
    </form>
</div>
<script>
    $.extend($.fn.validatebox.defaults.rules, {
        /*必须和某个字段相等*/
        equalTo: {
            validator: function (value, param) {
                return $(param[0]).val() == value;
            },
            message: '字段不匹配'
        }
    });
   function loadregion(){
       $(document.body).loadRegionInNewWin({callback:call});
    }
    function call(ids,names){
        $("#regionname").textbox('setValue', names);
        $("#region").val(ids);
    }
    function closeWin(){
      var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
       parent.layer.close(index); //再执行关闭
    }
    $(function () {
        $("#submit").on('click',function(){
            $("#signupForm").form('submit', {
                onSubmit: function () {
                    var flag = $(this).form('validate');
                    if(flag){
                        $.post(contextPath + "/userinfo/save.do", $("#signupForm").serialize(), function (data) {
                            alert(data.msg);
                            if (data.state) {
                                var parentWin = top.winref[window.name];
                                top[parentWin].$('#table').datagrid("reload");
                                var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                top.layer.close(index); //再执行关闭
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