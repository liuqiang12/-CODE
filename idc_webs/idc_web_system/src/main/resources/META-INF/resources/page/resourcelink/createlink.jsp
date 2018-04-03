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
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/contentsuspend/css/${skin}/style.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/framework/echarts/echart.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
</head>
<body class="easyui-layout" fit="true" border="true">
<c:if test="${type eq 'r_p'}">
    <script  type="application/javascript">
        /***
         *绑定角色的操作
         */
        $(function () {
            //保存当前权限
            var setting = {
                check: {
                    enable: true,
                    chkboxType: { "Y" : "ps", "N" : "ps" }
                },
                async:{
                    enable: true,
                    url: ''//contextPath+ "/sysmenu/tree.do"
                }
            };
            //TODO 菜单权限树
            var menusetting = $.extend({},setting);
            menusetting.async.url = contextPath+ "/sysmenu/tree.do";
            menutree =  $.fn.zTree.init($("#rpmt"), menusetting);
            //TODO 操作权限树
            var operatesetting =$.extend({},setting);
            operatesetting.async.url = contextPath+ "/sysoperate/tree.do";
            buttontree =  $.fn.zTree.init($("#rpbt"), operatesetting);
            //TODO 流程权限树
            var processsetting = $.extend({}, setting);
            processsetting.async.url = contextPath + "/processAuthority/tree.do";
            processsetting.check.enable = false;
            processtree = $.fn.zTree.init($("#rppt"), processsetting);
            //TODO 数据权限树
            /*var datatreesetting =  $.extend(setting,{check: { enable: true,
             chkboxType: { "Y" : "s", "N" : "s" }
             }});
             datatreesetting.async.url = contextPath+ "/sysregion/tree.do";
             datatree =  $.fn.zTree.init($("#rpdata"), setting);
             */
            $("#submit").on('click',function(){
                //选择的节点
                var checknodes = $("#roletree").tree('getChecked');
                if(checknodes.length==0){
                    top.layer.msg('没有选择角色,请选择');
                    return;
                }
                var menunodes = menutree.getCheckedNodes(true);
                var buttonnodes = buttontree.getCheckedNodes(true);
                //var datapermnodes = datatree.getCheckedNodes(true);
                if(menunodes.length==0||buttonnodes.length==0){
                    var msg = "没有选择菜单或按钮，这样操作将会清空该角色此类型权限！是否继续？";
                    /*if(datapermnodes.length==0){
                     msg = "没有选择数据区域，默认为不可查看数据,是否继续？";
                     }else{
                     msg= "没有选择菜单或按钮，这样操作将会清空该角色此类型权限！是否继续？"
                     }*/
                    top.layer.confirm(msg, {
                        icon:2,
                        btn: ['继续','取消'] //按钮
                    }, function(index){
                        top.layer.close(index);
                        save(checknodes,menunodes,buttonnodes);
                    });
                }else{
                    save(checknodes,menunodes,buttonnodes);
                }
            })
        });
        function save(roles,menunodes,buttonnodes){
            var roleids  = nodetostr(roles,'id');
            var menuids  = nodetostr(menunodes,'id');
            var operaids  = nodetostr(buttonnodes,'id');
            //var regionids  = nodetostr(regions,'id');
            $.post(contextPath+'/permissioninfo/bindByRole.do',{roleids:roleids,menuids:menuids,operaids:operaids},function(data){
                if(data.state){
                    top.layer.confirm('绑定成功,是否继续绑定其他角色？', {
                        icon:2,
                        btn: ['继续','取消'] //按钮
                    }, function(index){
                        top.layer.close(index);
                    },function(){
                        top.layer.closeAll();
                    });
                }
            })
        }
        function nodetostr(nodes,key){
            var Ids = [];
            $.each(nodes,function(index,iteam) {
                Ids.push(iteam[key]);
            });
            return Ids.join(',')
        }
        /***
         *显示当前节点（角色）的权限
         */
        function showRolePerm(node){
            var roleid = node.id;
            $.getJSON(contextPath+"/permissioninfo/permbyrole.do?roleid="+roleid,function(data){
                //获取到当前角色有的权限 过滤右边树
                console.log(data);
                menutree.checkAllNodes(false);
                buttontree.checkAllNodes(false);
//                datatree.checkAllNodes(false);
                var menus = data.sysmenuinfos;
                $.each(menus,function(index,iteam) {
                    var node = menutree.getNodeByParam("id", iteam.id, null);
                    menutree.checkNode(node,true,false)
//                      var node = $('#rpmt').tree('find', iteam.id);
//                      $('#rpmt').tree('check', node.target);
                });
                var sysoperates = data.sysoperates;
                $.each(sysoperates,function(index,iteam) {
                    var node = buttontree.getNodeByParam("id", iteam.id, null);
                    buttontree.checkNode(node,true,false)
                });
                var dataperms = data.dataperms;

                $.each(dataperms,function(index,iteam) {
//                    var node = datatree.getNodeByParam("id", iteam.permId, null);
//                    datatree.checkNode(node,true,false)
                });
            })
        }
    </script>
    <div data-options="region:'west'" border="true" collapsible="false" title="角色"  style="width: 200px;">
        <ul id="roletree"class="easyui-tree" data-options="url:'<%=request.getContextPath() %>/sysrole/list.do',checkbox:true,onClick:showRolePerm,loadFilter:function(data){
                   var rows = data.rows;
                   var length = rows.length;
                   for(var index=0;index<length;index++){
                       rows[index].text= rows[index].name
                  }
                  return rows;
                 }">
        </ul>
    </div>
    <div data-options="region:'south'"  collapsible="false"   style="height: 32px;">
        <div style="text-align:center;padding:0px 0">
            <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" iconCls="icon-save"  style="width:80px">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()" iconCls="icon-cancel"  style="width:80px">关闭</a>
        </div>
    </div>
    <div data-options="region:'center',border:false" style="margin-right: 10px">
        <div id="tt1" class="easyui-tabs" fit="true" >
            <div title="菜单权限">
                <ul id="rpmt" class="ztree" style="height: 350px;width: 350px"></ul>
                    <%--<ul id="rpmt"class="easyui-tree" data-options="url:'<%=request.getContextPath() %>/sysmenu/tree.do',checkbox:true,cascadeCheck:false">--%>
                    <%--</ul>--%>
            </div>
            <div title="操作权限">
                <ul id="rpbt" class="ztree"></ul>
                    <%--<ul id="rpbt"class="easyui-tree" data-options="url:'<%=request.getContextPath() %>/sysoperate/tree.do',checkbox:true,cascadeCheck:false">--%>
                    <%--</ul>--%>
            </div>
                <%--
                <div title="数据权限">
                    <label><b>可以查看的数据范围：</b></label>
                    <ul id="rpdata" class="ztree"></ul>
                </div>
                --%>
            <div title="流程权限">
                <ul id="rppt" class="ztree"></ul>
                    <%--<ul id="rpbt"class="easyui-tree" data-options="url:'<%=request.getContextPath() %>/sysoperate/tree.do',checkbox:true,cascadeCheck:false">--%>
                    <%--</ul>--%>
            </div>
        </div>
    </div>
</c:if>
<%--用户或组绑定角色--%>
<c:if test="${type eq 'g_r'||type eq 'u_r'}">
    <script  type="application/javascript">
        /***
         *绑定角色的操作${type}
         */
        $(function () {
            <c:if test="${type eq 'g_r'}">
            initGroupTree();
            </c:if>
            <c:if test="${type eq 'u_r'}">
            initUserTree();
            </c:if>
            initRoleTree();
            $("#submit").on('click',function(){
                var treegroup = $.fn.zTree.getZTreeObj("treegroup");
                var treerole = $.fn.zTree.getZTreeObj("treerole");
                var checkgroups = treegroup.getCheckedNodes(true);
                if(checkgroups.length==0){
                    top.layer.msg('没有选择${type eq 'g_r'?'组':'用户'},请选择');
                    return;
                }
                var checkroles = treerole.getCheckedNodes(true);
                if(checkroles.length==0){
                    top.layer.confirm('没有选择角色，确定清空该${type eq 'g_r'?'组':'用户'}角色？', {
                        icon:2,
                        btn: ['继续','取消'] //按钮
                    }, function(index){
                        top.layer.close(index);
                        save(checkgroups,checkroles);
                    });
                }else{
                    save(checkgroups,checkroles);
                }
            })
        });
        function save(checkgroups,checkroles){
            var roleids  = nodetostr(checkroles,'id');
            var groups = nodetostr(checkgroups, 'id');
            $.getJSON(contextPath+'/${type eq 'g_r'?'usergroup':'userinfo'}/bindrole.do',{ids:groups,roleids:roleids},function(data){
                if(data.state){
                    top.layer.confirm('绑定成功,是否继续绑定其他${type eq 'g_r'?'组':'用户'}？', {
                        icon:2,
                        btn: ['继续','取消'] //按钮
                    }, function(index){
                        top.layer.close(index);
                    },function(){
                        top.layer.closeAll();
                    });
                }
            })
        }
        function nodetostr(nodes,key){
            var Ids = [];
            $.each(nodes,function(index,iteam) {
                Ids.push(iteam[key]);
            });
            return Ids.join(',')
        }
        var setting = {
            async:{
                enable: true
            },
            check: {
                enable: true,
                chkboxType: { "Y" : "", "N" : "s" }
            },
            data: {
                simpleData: {
                    enable:true,
                    idKey: "id",
                    pIdKey: "parentId",
                    rootPId: ""
                }
            },
            callback:{}
        };
        function initRoleTree(callback){
            var rolesettting = $.extend(true,{},setting);
            rolesettting.async.url=contextPath + "/sysrole/list.do";
            rolesettting.async.dataFilter=function(treeId, parentNode, responseData){
                var rows = responseData.rows;
                for(var i=0;i<rows.length;i++){
                    if(rows[i].enabled!=1){
                        rows[i].chkDisabled=true;
                    }
                }
                return rows;
            };
            var treerole = $.fn.zTree.init($("#treerole"), rolesettting);
        }
        /***
         * 用户列表
         * */
        function initUserTree(callback){
            var usersetting = $.extend(true,{},setting);
            usersetting.data.key={
                name:"NICK"
            };
            usersetting.async.url=contextPath + "/userinfo/list.do";
            usersetting.async.dataFilter=function(treeId, parentNode, responseData){
                return responseData.rows;
            };
            usersetting.callback.onClick=showGroupRole;
            $.fn.zTree.init($("#treegroup"), usersetting);
        }
        function initGroupTree(callback){
            var newsetting = $.extend(true,{},setting);
            newsetting.async.url=contextPath + "/usergroup/tree.do";
            newsetting.callback.onClick=showGroupRole;
            $.fn.zTree.init($("#treegroup"), newsetting);
        }
        /***
         *显示当前节点（）的角色
         */
        function showGroupRole(event, treeId, treeNode){
            var groupid = treeNode.id;
            $.getJSON(contextPath+"/sysrole/rolebytype.do?type=${type eq 'g_r'?'gr':'ur'}&id="+groupid,function(data){
                data = eval('(' + data + ')');
                console.log(data);
                var treerole = $.fn.zTree.getZTreeObj("treerole");
                treerole.checkAllNodes(false);
                var roles = data.roles;
                $.each(roles,function(index,iteam) {
                    var node = treerole.getNodeByParam("id", iteam.id, null);
                    treerole.checkNode(node,true,false)
                });
            })
        }
    </script>
    <div data-options="region:'west'" border="false" collapsible="false" title="${type eq 'g_r'?'组':'用户1'}"  style="width: 200px;">
            <%--<ul id="grouptree"class="easyui-tree" data-options="url:'<%=request.getContextPath() %>/usergroup/tree.do',checkbox:true,onClick:showGroupRole">--%>
            <%--</ul>--%>
        <ul id="treegroup" class="ztree" style="margin-top:0;margin-left: 2px;"></ul>

    </div>
    <div data-options="region:'south'"  collapsible="false"   style="height: 32px;">
        <div style="text-align:center;padding:0px 0">
            <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" iconCls="icon-save"  style="width:80px">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()" iconCls="icon-cancel"  style="width:80px">关闭</a>
        </div>
    </div>
    <div data-options="region:'center'"  collapsible="false" title="角色">
        <ul id="treerole" class="ztree" style="margin-top:0;margin-left: 2px;"></ul>

    </div>
</c:if>


<script>
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
//        $("#submit").on('click',function(){
////            $('#signupForm').form({
////                url: contextPath + "/userinfo/save.do",
////                onSubmit: function () {
////                    //进行表单验证
////                    //如果返回false阻止提交
////                },
////                success: function (data) {
////                    alert(data)
////                }
////            });
//            $("#signupForm").form('submit', {
//                onSubmit: function () {
//                    var flag = $(this).form('validate');
//                    console.log(flag)
//                    if(flag){
//                        $.post(contextPath + "/userinfo/save.do", $(this).serialize(), function (data) {
//                            if (data.state) {
//                                layer.msg('保存成功');
//                                parent.document.getElementById("maincontent").contentWindow.refreshTable()
//                                closeWin();
//                            } else {
//                                layer.msg(data.msg);
//                            }
//
//                        });
//                    }
//                    return false;
//                }
//            });
//        });
    });
</script>




</body>
</html>