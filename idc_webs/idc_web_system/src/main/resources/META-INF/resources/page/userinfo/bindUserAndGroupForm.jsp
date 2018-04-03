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
    <title>用户信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/ztree/css/zTreeStyle/resource.css"/>
    <script type="text/javascript"
            src="<%=request.getContextPath() %>/js/base/rtree.js"></script>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
</head>
<script type="text/javascript">
    $(function () {
        var setting = {
            check: {
                enable: true,
                chkboxType: {"Y": "ps", "N": "ps"}
            },
            async: {
                enable: true,
                url: ''
            }
        };
        var groupsetting = $.extend({}, setting);
        groupsetting.async.url = contextPath + "/usergroup/tree.do";
        grouptree = $.fn.zTree.init($("#group"), groupsetting);
    })
    //通过用户显示用户所在组
    function showGroupPerm(node) {
        //获取用户ID
        var userId = node.ID;
        $.getJSON(contextPath + "/usergroup/queryUserGroupListByUserId?userId=" + userId, function (data) {
            //获取到当前用户有的组 过滤右边树
            console.log(data);
            grouptree.checkAllNodes(false);
            $.each(data, function (index, iteam) {
                var node = grouptree.getNodeByParam("id", iteam.id, null);
                grouptree.checkNode(node, true, false)
            });
        })
    }
    //关闭当前窗口
    function closeWin() {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    }
    //绑定用户组
    function bindUserAndGroup() {
        var checknodes = $("#roletree").tree('getChecked');
        if (checknodes.length == 0) {
            top.layer.msg('没有选择用户,请选择');
            return;
        }
        //获取选中的组节点
        var groupnodes = grouptree.getCheckedNodes(true);
        //获取选中的用户userIdStr
        var userIdStr = nodetostr(checknodes, 'ID');
        //获取选中的组groupIdStr
        var groupIdStr = nodetostr(groupnodes, 'id');
        console.log("选中的用户ID：" + userIdStr);
        console.log("选中的组ID：" + groupIdStr);
        $.post(contextPath + "/userinfo/bindusergroup.do", {userid: userIdStr, groupids: groupIdStr}, function (data) {
            if (data.state) {
                alert('绑定成功');
                closeWin();
            } else {
                alert('绑定失败');
            }
        })
    }
    //获取选中IDs
    function nodetostr(nodes, key) {
        var Ids = [];
        $.each(nodes, function (index, iteam) {
            Ids.push(iteam[key]);
        });
        return Ids.join(',')
    }
</script>
<body class="easyui-layout" fit="true" border="true">
<div data-options="region:'west'" border='true' collapsible="false" title="用户" style="width: 200px;">
    <ul id="roletree" class="easyui-tree" data-options="url:'<%=request.getContextPath() %>/userinfo/list.do',checkbox:true,onClick:showGroupPerm,loadFilter:function(data){
                   var rows = data.rows;
                   var length = rows.length;
                   for(var index=0;index<length;index++){
                       rows[index].text= rows[index].USERNAME
                  }
                  return rows;
                 }">
    </ul>
</div>
<div data-options="region:'center'" border='true' collapsible="false" title="用户组" style="margin-right: 10px;">
    <ul id="group" class="ztree"></ul>
</div>
<div data-options="region:'south'" collapsible="false" style="height: 40px;padding: 5px;">
    <div style="text-align:center;padding:0px 0">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="bindUserAndGroup()" iconCls="icon-save"
           style="width:80px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeWin()" iconCls="icon-cancel"
           style="width:80px">关闭</a>
    </div>
</div>
</body>
</html>