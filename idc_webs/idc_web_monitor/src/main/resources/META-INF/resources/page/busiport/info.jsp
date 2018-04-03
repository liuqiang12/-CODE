<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <title></title>
    <style>
    </style>
</head>
<body>
<div class="easyui-panel" fit="true">
    <div id="cc" class="easyui-layout" fit="true">
        <div data-options="region:'north'" style="height: 220px;overflow: hidden">
            <table class="kv-table">
                <tbody>
                <tr>
                    <td class="kv-label">业务名称</td>
                    <td class="kv-content">
                        <input type="hidden" id="id" value="${id}"/>
                        <input class="easyui-textbox" data-options="width:300,required:true" id="busiPortName"
                               value="${businame}" />
                    </td>

                </tr>
                <tr>
                    <td class="kv-label">分组</td>
                    <td class="kv-content">
                        <input class="easyui-combotree" value="${groupids}" id="groupName" style="width:300px" data-options="
                          url: '${contextPath}/busiPort/group/list?isSimple=1',
                          checkbox:true,
                          multiple:true,
                          cascadeCheck:false,
                          valueField: 'id',
                          textField: 'name',
                          <%--multiple:false,--%>
                          onLoadSuccess:function(node,data){
                           console.log(data)
                            var nodes = [];
                            <c:forEach var="group" items="${groups}">
                                   nodes.push({ id: ${group.id}, text: '${group.name}' })
                            </c:forEach>
                            $('#groupName').combotree('setValues', nodes);
                          }
                          <%--onChange:function(newValue, oldValue){--%>
                            <%--if(newValue.length>1){--%>
                              <%--$(this).tagbox('setValue',newValue[newValue.length-1]);--%>
                            <%--}--%>
                          <%--},--%>

                        ">
                        <%--<input class="easyui-combobox" data-options="width:300,url:'${contextPath}/busiPort/grouplist',valueField:'groupname',textField:'groupname'" id="groupname"--%>
                               <%--value="${groupname}"/>--%>
                    </td>

                </tr>
                <tr>
                    <td class="kv-label">所属客户</td>
                    <td class="kv-content">
                        <input class="easyui-combobox" data-options="width:300,url:'${contextPath}/busiPort/getcustomer',valueField:'id',textField:'text', editable:false" id="by_customer"
                               value="${customer}" />
                    </td>

                </tr>
                <tr>
                    <td class="kv-label">分派带宽</td>
                    <td class="kv-content">
                        <input class="easyui-numberbox" data-options="min:0,precision:2,width:300,required:true" id="bandwidth"
                               value="${bandwidth}"/>
                    </td>
                </tr>
                <tr>
                    <td class="kv-label">说明</td>
                    <td class="kv-content">
                        <input class="easyui-textbox" data-options="width:300,multiline:true,height:50" id="descr"
                               value="${descr}"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div data-options="region:'west'" style="width: 50%;">
            <table id="west" fit="true" class="easyui-datagrid" data-options="
            title:'可以选择',
                url:'${contextPath}/capreport/list.do',
                fitColumns:true,
                pagination:true,
                pageList:[20,50,100],
                pageSize:50,
                toolbar:'#toolbar',
                singleSelect:false">
                <thead>
                <tr>
                    <th data-options="field:'PORTID',hidden:true">ID</th>
                    <th data-options="field:'DNAME',width:100">设备名字</th>
                    <th data-options="field:'PORTNAME',width:100">端口名字</th>
                    <th data-options="field:'ALIAS',width:100">别名</th>
                    <%--<th data-options="field:'DNAME',width:100">设备</th>--%>
                </tr>
                </thead>
            </table>
        </div>
        <div data-options="region:'center'" style="background:#eee;">
            <div  class="easyui-layout" fit="true">
                <div data-options="region:'west'" style="width: 50px;padding-top: 100px;text-align: center">
                    <div class="buts">
                        <div><input type="button" value=">>>"/></div>
                        <div><input type="button" value="<<<"/></div>
                    </div>
                </div>
                <div data-options="region:'center'">
                    <table id="east" class="easyui-datagrid" title="已经选择" data-options="
                      singleSelect:false,
                      fit:true,
                      <%--pagination:true,--%>
                      onLoadSuccess:function(data){
                              var all = $('#east').datagrid('getData');
                               var panel = $('#east').datagrid('getPanel');
                               panel.panel('setTitle','已经选择'+all.total+'条');
                      },
                      fitColumns:true">
                        <thead>
                        <tr>
                            <th data-options="field:'PORTID',hidden:true">端口名字</th>
                            <th data-options="field:'DEVICEID',hidden:true">端口名字</th>
                            <th data-options="field:'DNAME',width:100">设备名字</th>
                            <th data-options="field:'PORTNAME',width:60">端口名字</th>
                            <th data-options="field:'ALIAS',width:100">别名</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="port" items="${ports}">
                            <%--<c:if test="${ not empty port.PORTID}">--%>
                                <tr>
                                    <td>${port.portid}</td>
                                    <td>${port.deviceid}</td>
                                    <td>${port.customername}</td>
                                    <td>${port.portname}</td>
                                    <td>${port.alias}</td>
                                </tr>
                            <%--</c:if>--%>
                        </c:forEach>
                        </tbody>
                    </table>
                    <%--<table id="east" fit="true" class="easyui-datalist" data-options="--%>
                    <%--title:'已经选择',--%>
                    <%--url:'datagrid_data.json',--%>
                    <%--fitColumns:true,--%>
                    <%--singleSelect:false">--%>
                        <%--<thead>--%>
                        <%--<tr>--%>
                            <%--<th data-options="field:'portName',width:100">端口名字</th>--%>
                            <%--<th data-options="field:'deviceName',width:100">设备</th>--%>
                        <%--</tr>--%>
                        <%--</thead>--%>
                    <%--</table>--%>
                </div>
            </div>
        </div>
    </div>
    <div id="toolbar" class="paramContent">
        <div class="param-fieldset">
            <select class="easyui-combobox" style="width:300px" data-options="
    valueField:'DEVICEID',
    textField:'NAME',
    method:'post',
    onSelect:changeinput,
    url:'<%=request.getContextPath() %>/device/getDeviceList.do'
    ">
            </select>
            <%--<input  class="easyui-combobox"--%>
                   <%--data-options="valueField:'NAME',textField:'CODE',url:'${contextPath}/device/list.do',onLoadSuccess:function(data){--%>
                        <%--console.log(data.rows)--%>
                        <%--return data.rows;--%>
                   <%--}" />--%>
                <%--<input class="textbox" style="width:300px" data-options="onChange:searchModels "/>--%>
                <%--</select>--%>
        <%--<input type="input" id="buiName" class="param-input" placeholder="关键字" />--%>
        </div>
        <%--<div class="btn-cls-search" onClick="searchModels();"></div>--%>
    </div>
</div>
<script type="text/javascript">
    function changeinput(newValue, oldValue){
        clearTimeout(f);
        var f = setTimeout('load("'+newValue.DEVICEID+'")',300);
//        $("#west").datagrid({
//            queryParams:{ q:newValue,page:1,rows:10}
//        })
    }
    function load(newValue){
        $("#west").datagrid({
            queryParams:{ q:newValue,page:1,rows:10}
        })
    }
    $(function(){
        var toSelect = function(){
            var westrows = $("#west").datagrid("getChecked");
            var eastrows = $("#east").datagrid("getData").rows;
            $(westrows).each(function(index,iteam){
                var result = Each(iteam,eastrows,function(list,obj){
                    var flag = true;
                    $.each(list,function (index,iteam) {
                        if (iteam.PORTNAME==obj.PORTNAME&&iteam.DEVICEID==obj.DEVICEID) {
                            flag=false;
                            return false;
                        }
                    })
                    return flag;
                })
                if(result){
                    $("#east").datagrid("appendRow",iteam)
//                    var all = $("#east").datagrid("getData");
//                    var panel = $("#east").datagrid("getPanel");
//                     panel.panel("setTitle","已经选择"+all.total+"条");
                }
                $("#west").datagrid("unselectAll")
            })
            function Each(obj, list,fun) {
                return fun(list, obj);
            }
        };
        var toFree = function(){
            var eastrows = $("#east").datagrid("getChecked");
            $.each(eastrows,function(index,iteam){
                var rowindex = $("#east").datagrid("getRowIndex",iteam);
                $("#east").datagrid("uncheckRow",rowindex).datagrid("deleteRow",rowindex);

            });
//            var all = $("#east").datagrid("getData");
//            var panel = $("#east").datagrid("getPanel");
//            panel.panel("setTitle","已经选择"+all.total+"条");
        };
        $(".buts div").click(function(){
            var index = $(this).index();
            if(index==0){
                toSelect();

            }else{
                toFree();
            }
            var all = $("#east").datagrid("getData");
            var panel = $("#east").datagrid("getPanel");
            panel.panel("setTitle","已经选择"+all.total+"条");
        })
    })
    function searchModels(searchKey){
//        if(typeof (searchKey)== 'undefined'){
//            searchKey = $("#buiName").val();
//        }
        $("#west").datagrid({
            queryParams:{ name:searchKey}
        })
    }
    function submit(busiPortName,descr,portids,customer,id,groupName,bandwidth){
        $.post(contextPath+"/busiPort/bindPort",{
            ports:portids,
            action:'${action}'
            ,busiportname:busiPortName
            ,desc:descr
            ,customer:customer
            ,id:id
            ,groupIds:groupName
            ,bandwidth:bandwidth
        },function(data){
            if(data.state){
                closeModel();
                top.layer.msg('保存成功');
                var parentWin = top.winref[window.name];
                top[parentWin].$('#dg').datagrid("reload");
                var indexT = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                top.layer.close(indexT); //再执行关闭
            }else{
                closeModel();
                top.layer.msg(data.msg)
            }
        })
    }
    var openM = null;
    function openModel(text){
        if(openM!=null){
            layer.close(openM)
        }
        openM = layer.load(2,{
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
    }
    function closeModel(){
        layer.close(openM)
    }
    function doSubmit(){
        openModel();
//        var id = $("#id").val();
        var busiPortName = $("#busiPortName").val();
        if(busiPortName==""){
            layer.msg("没有输入业务名");
            closeModel();
            return ;
        }
        var bandwidth = $("#bandwidth").val();
        if(bandwidth==""){
            layer.msg("请输入带宽");
            closeModel();
            return ;
        }
        var descr = $("#descr").val();
        var id =$("#id").val();
        var by_customer = $("#by_customer").val();
        var rowindex = $("#east").datagrid("getData").rows;
        var groupName = $("#groupName").combotree('getValues');
        var portids = [];
        $.each(rowindex,function(index,iteam){
            portids.push(iteam.PORTID)
        })
        if(portids.length==0){
            closeModel();
            layer.confirm('没有选择端口，将会清空现存端口？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                openModel();
               submit(busiPortName,descr,"",by_customer,id,groupName,bandwidth)
            });
        }else{
            submit(busiPortName,descr,portids.join(","),by_customer,id,groupName.join(","),bandwidth)
        }
    }
</script>
</body>
</html>