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
    </script>
    <style>
        .ztree li {
            background-color: white;
        }
        .dv-table td {
            padding: 5px 0px 5px 0px ;
        }
    </style>
</head>

<body style="height: 100%">
<div class="easyui-panel"  border="false" fit="true" style="width:100%;padding:10px 30px;">
    <form method="post" id="signupForm" action="<%=request.getContextPath() %>/permissioninfo/save.do">
        <table class="dv-table" style="width:100%;padding:5px;margin-top:1px;">
            <tr>
                <td>资源名称:</td>
                <td>
                    <input type="hidden"  name="type"  value="${type}" />
                    <input type="text" class="easyui-textbox" name="name" prompt="请输入资源名字" data-options="required:true,width:358" value="" />
                </td>
            </tr>
         <c:if test="${type eq 0}">
        <tr>
            <td>资源类型:</td>
            <td>
                <input name="permname" class="easyui-validatebox easyui-textbox" data-options="required:true,width:200"  readonly="readonly" value="菜单资源"/>
            </td>
        </tr>
        <tr>
            <td>菜单Url:</td>
            <td>
                <input type="text" class="easyui-textbox" name="url" prompt="请输入菜单url" data-options="required:true,width:358" value="" />
            </td>
        </tr>
        <tr>
            <td>上级菜单:</td>
            <td>
                <input id = "parent_id" class="easyui-combotree"  name="parentId"
                       data-options="url:'<%=request.getContextPath() %>/sysmenu/tree.do',
                            width:358,
                           onLoadSuccess:function(node, data){
                           },loadFilter:function(data){
                              data.unshift({text:'',id:''})
                               return data;
                           }"/>
            </td>
        </tr>
          <tr>
              <td>排序:</td>
              <td>
                  <input type="text" class="easyui-numberbox" name="sort" prompt="序号"  data-options="max:100,width:358" value="" />
              </td>
          </tr>
        <tr>
            <td>描述:</td>
            <td>
                <input class="easyui-textbox" value="${group.description}" name="description" id="description" data-options="required:true,multiline:true,width:358"/>
            </td>
        </tr>

    </c:if>
    <c:if test="${type eq 1}">
        <tr>
            <td>资源类型:</td>
            <td>
                <input name="permname" class="easyui-validatebox easyui-textbox" data-options="required:true,width:200"  readonly="readonly" value="操作资源"/>
            </td>

        </tr>
        <tr>
            <td>操作名称:</td>
            <td>
                <input type="text" class="easyui-textbox" name="optName" prompt="请输入操作资源KEY(如:sys_userinfo_add)" data-options="required:true,width:358" value="" />
            </td>
        </tr>
        <tr>
            <td>操作对应Url:</td>
            <td>
                <input type="text" class="easyui-textbox" name="optUrl" data-options="width:358" prompt="请输入操作资源对应url"  value="" />
            </td>
        </tr>
        <tr>
            <td>是否菜单中:</td>
            <td>
                <select id="cc" class="easyui-combobox" name="isInMenu" data-options="panelMaxHeight:100,width:358">
                    <option value="0">否</option>
                    <option value="1">是</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>上级节点:</td>
            <td>
                <input id = "parent_id" class="easyui-combotree"  name="parentId"
                       data-options="url:'<%=request.getContextPath() %>/sysoperate/tree.do',
                            width:358,
                           onLoadSuccess:function(node, data){
                           },loadFilter:function(data){
                              data.unshift({text:'',id:''})
                               return data;
                           }"/>
            </td>
        </tr>
        <tr>
            <td>排序:</td>
            <td>
                <input type="text" class="easyui-numberbox" name="sort" style="width: 80%;;" prompt="序号" data-options="max:100,width:358"  value="" />
            </td>
        </tr>
        <tr>
            <td>描述:</td>
            <td>
                <input class="easyui-textbox" value="${group.description}" name="description" id="description" data-options="required:true,multiline:true,width:358"/>
            </td>
        </tr>
    </c:if>
  </table>
        <div style="text-align:center;padding:0px 0">
            <%--<input class='submit' type='submit'class="easyui-linkbutton" iconCls="icon-save" value='提交'>--%>
            <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" iconCls="icon-save"  style="width:80px">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()" iconCls="icon-cancel"  style="width:80px">关闭</a>
        </div>
    </form>

</div>
<script type="text/javascript"  src="<%=request.getContextPath() %>/framework/jqueryui/frame/layer/layer.js"></script>
<script src="<%=request.getContextPath() %>/framework/jeasyui/validationPlus.js"></script>
<script src="<%=request.getContextPath() %>/framework/jeasyui/plugins/jquery.form.js"></script>
<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree.all-3.5.min.js"></script>
<script src="<%=request.getContextPath() %>/framework/bootstrap/js/jquery.ztree-combotree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/global/plugins/tree.js"></script>
<script>
   function loadregion(){
       $(document.body).loadRegionInNewWin({callback:call});
    }
    function call(ids,names){
        $("#regionname").textbox('setValue', names);
        $("#region").val(ids);
    }

//    $(document).ready(function () {
////        $("#submit").click(function(){
//////            $("#signupForm").submit();
////            $("#signupForm").form('submit',{
////                onSubmit:function(){
////                    return $(this).form('enableValidation').form('validate');
////                }
////            });
////        });
////        $(".i-checks").iCheck({checkboxClass: "icheckbox_square-green", radioClass: "iradio_square-green"});
////        $.validator.setDefaults({
////            highlight: function (e) {
////                $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
////            }, success: function (e) {
////                e.closest(".form-group").removeClass("has-error").addClass("has-success")
////            }, errorElement: "span", errorPlacement: function (e, r) {
////                e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
////            }, errorClass: "help-block m-b-none", validClass: "help-block m-b-none"
////        })
//    })
    function closeWin(){
      var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
       parent.layer.close(index); //再执行关闭
}
    $(function () {
//        var e = "<i class='fa fa-times-circle'></i> ";
//        var options={
//            dataType: "json",
//            success: function (data) {
//            },
//            error : function(){
//                alert('保存错误！');
//            }
//        };
//          var validator =  $("#signupForm").validate({
////            debug:true,
//            submitHandler: function (form) {
//                //return true;
//                //$(form).submit();
////                $(form).ajaxSubmit(options);
////                $.post(contextPath+"/userinfo/save.do",$(form).serialize(),function(data){
////                    if(data.status){
////                        layer.msg('保存成功');
////                        parent.document.getElementById("maincontent").contentWindow.refreshTable()
////                        closeWin();
////                    }else{
////                        layer.msg(data.msg);
////                    }
////
////                });
//            },
//            rules: {
//                nick: {required: !0, minlength: 2},
//                username: {required: !0, minlength: 2},
//                age: {required: !0, number: true,max:110,min:1,idcard:true},
//                password: {required: !0, minlength: 5}
//
//            },
//            messages: {
//                nick: e + "请输入您的昵称",
//                age: {required:e + "请输入您的年龄",nubmer:e+'输入数字'},
//                username: {required: e + "请输入您的用户名", minlength: e + "用户名必须两个字符以上"},
//                password: {required: e + "请输入您的密码", minlength: e + "密码必须5个字符以上"}
//               // confirm_password: {required: e + "请再次输入密码", minlength: e + "密码必须5个字符以上", equalTo: e + "两次输入的密码不一致"}
//            }
//        })
        $("#submit").on('click',function(){
//            $('#signupForm').form({
//                url: contextPath + "/userinfo/save.do",
//                onSubmit: function () {
//                    //进行表单验证
//                    //如果返回false阻止提交
//                },
//                success: function (data) {
//                    alert(data)
//                }
//            });
            $("#signupForm").form('submit', {
                onSubmit: function () {
                    var flag = $(this).form('validate');
                    if(flag){
                        $.post(contextPath + "/permissioninfo/save.do", $("#signupForm").serialize(), function (data) {
                            if (data.state) {
                                parent.layer.msg('保存成功');
                                //刷新父页面数据
                                var parentWin = top.winref[window.name];
                                top[parentWin].refreshTable();
                                //关闭当前窗口
                                closeWin();
                            } else {
                                parent.layer.msg(data.msg);
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