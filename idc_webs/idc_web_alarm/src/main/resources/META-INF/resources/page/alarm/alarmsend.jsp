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
    <title>告警信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script>
        (function($){
            var buttonDir = {north:'down',south:'up',east:'left',west:'right'};
            $.extend($.fn.layout.paneldefaults,{
                onBeforeCollapse:function(){
                    /**/
                    var popts = $(this).panel('options');
                    var dir = popts.region;
                    var btnDir = buttonDir[dir];
                    if(!btnDir) return false;
                    setTimeout(function(){
                        var pDiv = $('.layout-button-'+btnDir).closest('.layout-expand').css({
                            textAlign:'center',lineHeight:'18px',fontWeight:'bold'
                        });
                        if(popts.title){
                            var vTitle = popts.title;
                            if(dir == "east" || dir == "west"){
                                var vTitle = popts.title.split('').join('<br/>');
                                pDiv.find('.panel-body').html(vTitle);
                            }else{
                                $('.layout-button-'+btnDir).closest('.layout-expand').find('.panel-title')
                                        .css({textAlign:'left'})
                                        .html(vTitle)
                            }

                        }
                    },100);
                }
            });
        })(jQuery);
    </script>
    <style>
        .btn {
            padding: 2px 5px;
            margin: 2px;
        }
    </style>
</head>
<body style="margin:0;padding:0;overflow:hidden;">
<div class="easyui-layout" fit="true">
    <%--<div data-options="region:'west',width:240,border:true" title="区域">--%>
        <%--<ul id="treeregoin" class="ztree" style="margin-top:0; width:240px;"></ul>--%>
    <%--</div>--%>
    <div data-options="region:'east',collapsible:true,collapsed:true,width:350,border:true" title="告警发送定义">
        <table id="alarmsendtable" class="easyui-datagrid" data-options="fit:true,pagination:false,singleSelect:true,
        toolbar:'#toolbarkpi', onLoadSuccess:loadsuccess,striped:true,url:'<%=request.getContextPath() %>/alarm/alarmconfigsendlist.do'">
            <thead>
            <tr>
                <th data-options="field:'kpiname',width:150">指标名称</th>
                <th data-options="field:'alarmlevel',width:100,formatter:function(value,row,index){
                                       if(value==0){
                                          return '轻微'
                                       }
                                       if(value==1){
                                          return '一般 '
                                       }
                                       if(value==2){
                                          return '严重'
                                       }
                                        if(value==3){
                                          return '关键'
                                       }
                                    }">告警等级</th>
                               <th data-options="field:'alarmmode',width:$(this).width()*0.1,formatter:function(value,row,index){
                                       if(value==0){
                                          return '不做任何处理'
                                       }
                                       if(value==1){
                                          return '声音 '
                                       }
                                       if(value==2){
                                          return '短信'
                                       }
                                       if(value==3){
                                          return '邮件'
                                       }
                                    }">提醒方式</th>
                    <th data-options="field:'alarmaddress',width:$(this).width()*0.2">发送地址</th>
            </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'center',border:false"  style="padding-left: 2px;" title="告警发送信息">
        <div class="easyui-layout" fit="true">
            <div data-options="region:'north',height:32,border:true">
                <div style="float: left;padding-left: 10px" id="toolbar"> <span>查询条件：</span>
                    <input type="hidden" id="regionid" name="regionid" />
                    <input name="keyword" class="easyui-textbox" type="text" prompt="信息关键字"/>
                    <span>发送时间从：</span><input name="starttime" class="easyui-datetimebox" type="text" prompt="发送时间"> <span>至</span>
                    <input name="endtime" class="easyui-datetimebox" type="text" prompt="发送时间">
                    <a id="ok" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                </div>
            </div>
            <div data-options="region:'center',border:false"  style="padding-left: 2px;">
                <table id="table" class="easyui-datagrid"
                       data-options="fit:true,rownumbers:true,pagination:true,singleSelect:true,
                       onLoadSuccess:loadsuccess,pageSize:15,pageList:[10,15,20,50],striped:true,
                       url:'<%=request.getContextPath() %>/alarm/alarmsendlist.do',  ">
                    <thead >
                    <tr>
                        <th data-options="field:'sendtype',width:$(this).width()*0.05,formatter:function(value,row,index){
                                       if(value==0){
                                         return '短信'
                                       }
                                       if(value==1){
                                         return '邮件'
                                       }
                                    }">发送方式</th>
                        <th data-options="field:'sendresult',width:$(this).width()*0.1,formatter:function(value,row,index){
                                       if(value==1){
                                         return '成功'
                                       }
                                       if(value==2){
                                         return '失败'
                                       }
                                    }">发送状态</th>
                        <th data-options="field:'sendinfo',width:$(this).width()*0.4">发送信息</th>
                        <th data-options="field:'sendtime',width:$(this).width()*0.15">发送时间</th>
                        <th data-options="field:'sendmembers',width:$(this).width()*0.15">发送成功人员</th>
                        <th data-options="field:'unsendmembers',width:$(this).width()*0.15">发送失败人员</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<div id="toolbarkpi" style="height: 28px">
    <div style="float: left">
        <select class="easyui-combobox" name="kpiid" id="kpiid" data-options="onChange:filterData">
            <option value="">所有</option>
            <c:forEach items="${kpis}" var="kpi">
                <option value="${kpi.kpiid}">${kpi.kpiname}</option>
            </c:forEach>
        </select>
        <select class="easyui-combobox" style="width: 150px" name="alarmLevel" id="alarmLevel" data-options="onChange:filterData">
            <option value="">所有</option>
            <option value="0">一般</option>
            <option value="1">重要</option>
            <option value="2">严重</option>
        </select>
        <a id="addsendconfig" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
        <a id="editsendconfig" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
        <a id="delsendconfig" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
    </div>
</div>
<script type="text/javascript" language="JavaScript">
//    $(document).ready(function () {
//        $("#treeregoin").regionTree({},treecall);
//    });
    function filterData(){
        var alarmLevel = $("#alarmLevel").combobox("getValue")
        var kpiid = $("#kpiid").combobox("getValue");
        var paramstmp = {alarmLevel:alarmLevel,kpiid:kpiid};
        $('#alarmsendtable').datagrid('options').queryParams=paramstmp;
        $('#alarmsendtable').datagrid('reload');
    }
    function treecall(event, treeId, treeNode){
        if(treeNode.parentId==null){

            $('#toolbar #regionid').val("");//查询所有
        }else{
            $('#toolbar #regionid').val(treeNode.id);
        }
        refreshTable();
    }
    function loadsuccess(data){
        $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
            $(this).linkbutton();
        });
        $(".datagrid-cell").find('.easyui-tooltip').each(function () {
            $(this).tooltip();
        });
        $('#table').datagrid('fixRowHeight')
    }
    var $table = $('#table'),
     $table1 = $('#table1'),
            $ok = $('#ok');
    function queryByPid(event, treeId, treeNode){
        if(treeNode.children&&treeNode.children.length>0){
            $("input[name='groupname']").val('');
            $("input[name='groupPid']").val(treeNode.id);
            refreshTable();
        }
    }
    function refreshTable(){
        var paramstmp = {};
        $('#toolbar').find('input[name]').each(function () {
            paramstmp[$(this).attr('name')] = $(this).val();
        });
        $table.datagrid('options').queryParams=paramstmp;
        $table.datagrid('reload');

        $table1.datagrid('options').queryParams=paramstmp;
        $table1.datagrid('reload');
    }
    function refreshalarmsendtable(){
        $("#alarmsendtable").datagrid('reload');
    }
    $(function () {
        $ok.on('click',function () {
            refreshTable();
        });
        $(".sys_usergroup_add").on('click',function () {
            editRow(0,0);
        });

        $("#addsendconfig").on("click",function(){
            editkpi(0);
        })
        $("#editsendconfig").on("click",function(){
            var row = $("#alarmsendtable").datagrid("getSelected");
            if(!row){
                top.layer.msg("没有选择要修改的指标");
                return ;
            }
            editkpi(row.id);
        }) ;
//        $("#editsendconfig").on("click",function(){
//            var row = $("#alarmsendtable").datagrid("getSelected");
//            if(!row){
//                top.layer.msg("没有选择要修改的信息");
//                return ;
//            }
//            editkpi(row.id);
//        })
        $("#delsendconfig").on("click",function(){
            var row = $("#alarmsendtable").datagrid("getSelected");
            if(!row){
                top.layer.msg("没有选择要删除的信息");
                return ;
            }
            delsendconfig(row.id);
        })

    });
    function delsendconfig(id){
        top.layer.msg('确定删除么？', {
            time: 0, //不自动关闭
            btn: ['删除', '取消'],
            yes: function(index){
                $.getJSON(contextPath + "/alarm/sendconfigdelete.do?id="+id,function(data){
                    if(data.state){
                        top.layer.msg('删除成功');
                        refreshalarmsendtable();
                    }else{
                        top.layer.msg(data.msg);
                    }
                });
            },
            no:function(index){
                layer.close(index);
            }
        });
    }
    function editkpi(id){
        var  url = contextPath + "/alarm/editsendconfig.do?id="+id;
        top.layer.open({
            type: 2,
            area: ["500px","300px"],
            title: "告警发送定义编辑",
            maxmin: true, //开启最大化最小化按钮
            content: url,
            btn: ['确定', '关闭'],
            success: function(layero, index){
                var name = layero.find('iframe')[0].name;
                top.winref[name] = window.name;
            },
            yes: function (index, layero) {
                var body = top.layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
                iframeWin.contentWindow.doSubmit(function(data){
                    $("#alarmsendtable").datagrid("reload");
                    top.layer.msg("保存成功")
                    top.layer.close(index);
                })
            }
        });
//        top.layer.open({
//            type : 2,
//            title : '告警发送定义编辑',
//            maxmin : true,
//            id: 'kpiwin', //设定一个id，防止重复弹出
//            closeBtn:1,
//            area : [ '400px', '250px' ],
//            content :url
//        });
    }
    function editRow(groupid,pid){
        var  url = contextPath + "/usergroup/form.do";
        if(groupid!=0){//修改
            url+="?groupid="+groupid;

        }else{//添加
            if(pid!=0)
                url+="?pid="+pid;
        }
        top.layer.open({
            type : 2,
            title : '用户组信息',
            maxmin : true,
            id: 'groupwin', //设定一个id，防止重复弹出
            closeBtn:1,
            area : [ '500px', '300px' ],
            content :url
        });
    }
    function refTree(){
        var treeObj = $.fn.zTree.getZTreeObj("treegroup");
        treeObj.reAsyncChildNodes(null, "refresh");
    }
    function deleteRow(id){
        top.layer.msg('确定删除么？', {
             time: 0, //不自动关闭
             btn: ['删除', '取消'],
             yes: function(index){
                 $.getJSON(contextPath + "/usergroup/delete.do?id="+id,function(data){
                     if(data.state){
                         top.layer.msg('删除成功');
                         refTree();
                         refreshTable();
                     }else{
                         top.layer.msg(data.msg);
                     }
                 });
             },
            no:function(index){
                layer.close(index);
            }
        });
    }

    /***
    *角色绑定
    * @param id
     */
    function bindRole(id){
        top.layer.open({
            type : 2,
            title : '组-角色分配',
            maxmin : true,
            id: 'bindwin', //设定一个id，防止重复弹出
            closeBtn:1,
            area : [ '600px','450px' ],
            content :contextPath + "/bindlink.do?type=g_r&id="+id
        })
    }
    function formmataction(value,row,index){
        var insert = new Array();
        <sec:authorize access="hasAnyRole('ROLE_sys_alarm_del','ROLE_admin')">
//          insert.push('<a class="easyui-linkbutton " data-options="plain:true,iconCls:\'icon-edit\'" onclick="del('+row.id+')">确认告警</a> ');
        </sec:authorize>
        return insert.join('');
    }
</script>
</body>

</html>