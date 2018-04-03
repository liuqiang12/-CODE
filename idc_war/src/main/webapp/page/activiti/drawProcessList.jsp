<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>jQuery网页在线流程图</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/assets/activiti/css/common.css" />
 <style type="text/css">
 </style>
<!--[if lt IE 9]>
<![endif]-->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/assets/activiti/plugins/GooFlow/codebase/GooFlow.css"/>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/assets/activiti/default.css"/>
    </head>
    <body>
        
        <div class="container" style="width:1218px;">
            <div id="demo" style="margin:10px"></div>
            <input id="submit" type="button" class="btn" value='导出结果' onclick="Export()"/>
            <textarea id="result" row="6"></textarea>
        </div>
        <script type="text/javascript" src="<%=request.getContextPath() %>/assets/activiti/js/jquery.js"></script> 
        <script type="text/javascript" src="<%=request.getContextPath() %>/assets/activiti/plugins/GooFlow/data.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/assets/activiti/js/GooFunc.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/assets/activiti/js/json2.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/assets/activiti/plugins/GooFlow/codebase/GooFlow.js"></script>
        <script type="text/javascript">
                var property = {
                    width: 1200,
                    height: 600,
                    toolBtns: ["start round", "end round", "task round", "node", "chat", "state", "plug", "join", "fork", "complex mix"],
                    haveHead: true,
                    headBtns: ["new", "open", "save", "undo", "redo", "reload"], //如果haveHead=true，则定义HEAD区的按钮
                    haveTool: true,
                    haveGroup: true,
                    useOperStack: true
                };
                var remark = {
                    cursor: "选择指针",
                    direct: "结点连线",
                    start: "入口结点",
                    "end": "结束结点",
                    "task": "任务结点",
                    node: "自动结点",
                    chat: "决策结点",
                    state: "状态结点",
                    plug: "附加插件",
                    fork: "分支结点",
                    "join": "联合结点",
                    "complex mix": "复合结点",
                    group: "组织划分框编辑开关"
                };
                var demo;
                $(document).ready(function() {
                    demo = $.createGooFlow($("#demo"), property);
                    demo.setNodeRemarks(remark);
                    //demo.onItemDel=function(id,type){
                    //	return confirm("确定要删除该单元吗?");
                    //}
                    demo.loadData(jsondata);
                });
                var out;
                function Export() {
                    document.getElementById("result").value = JSON.stringify(demo.exportData());
                }
        </script>
    </body>
</html>