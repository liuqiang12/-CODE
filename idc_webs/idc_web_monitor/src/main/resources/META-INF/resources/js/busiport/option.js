/**
 * Created by mylove on 2017/5/8.
 */
var selectNode = null;


//function initGroup(){
//    $("#groupbox").combotree({
//        url:contextPath+'/busiPort/group/list',
//        onChange:function(newValue, oldValue){
//            searchModels()
//        },
//        valueField:'id',
//        textField:'name'
//        //formatter: formatItem
//    })
//}
$(function () {
    $("#addgroup").click(function () {
        openDialog('添加业务分组',contextPath + '/busiPort/group/add','450px','200px')
    });
    var setting = {
        view: {
            selectedMulti: false
        },
        edit: {
            enable: true,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        data: {
            simpleData: {
                enable: true,
                pIdKey: 'parentId'
            }
        },
        async: {
            enable: true,
            url:contextPath+"/busiPort/group/list",
            dataFilter:function(treeId, parentNode, responseData){
                //var nodes = [{id:-1,name:'业务分组'}];
                if (responseData) {
                    for(var i =0; i < responseData.length; i++) {
                        if( responseData[i].parentId==null){
                            responseData[i].parentId= "-1";
                        }
                    }
                }else{
                    responseData=[];
                }
                responseData.push({id:-1,name:'业务分组',open:true});
                return responseData;
            }
        },
        callback: {
            onRightClick: function (e, treeId, treeNode) {
                var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                treeObj.checkAllNodes(false);
                treeObj.selectNode(treeNode);
                selectNode = treeNode;

                $('#mm').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            },
            //beforeDrop:function(treeId, treeNodes, targetNode, moveType){
            //        if(targetNode.id=="-1"){
            //            return false;
            //        }
            //    return true;
            //},
            beforeDrag:function(treeId, treeNodes){
                if(treeNodes[0].id=="-1"){
                    return false;
                }
                return true;
            },
            beforeRename: beforeRename,
            onClick:onClick
            ,onDrop:onDrop
            ,onRename: onRename
        }
    };
    $.fn.zTree.init($("#treeDemo"), setting);
    $("#dg").datagrid( getOptions())
    //新增
    $("#add").click(function () {
        openDialog('添加业务信息',contextPath + '/busiPort/info/add/0','90%','90%')
    });
    $("#addgroup").click(function () {
        openDialog('添加业务信息',contextPath + '/busiPort/addgroup','90%','90%')
    });
    top.hideLeft();
    var task = null;
    $("#buiName").bind('input propertychange', function() {
            //进行相关操作
        clearTimeout(task);
        task = setTimeout('searchModels()',300);
    });
    //修改
    $("#edit").click(function(){
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择业务");
            return;
        }else if(rows.length>1){
            layer.msg("只能选择一个业务");
            return;
        }
        openDialog('编辑业务信息',contextPath + '/busiPort/info/edit/'+rows[0].portid,'90%','90%')
    });
    //删除
    $("#del").click(function(){
        var rowArr = new Array();
        var rows = $('#dg').datagrid('getChecked');
        if(rows.length<1){
            layer.msg("没有选择业务信息");
            return;
        }
        for(var i=0;i<rows.length;i++){
            rowArr.push(rows[i].portid);
        };
        layer.confirm('确认删除业务?', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.post(contextPath+"/busiPort/delete",{typeid:rowArr.join(',')},function(result){
                if(result.state){
                    layer.msg('删除成功');
                    //刷新列表
                    reloadGrid();
                }else{
                    layer.msg(result.msg);
                }

            });
        });
    })
    function getOptions() {
        var option = {
            columns: [[
                {field: 'portid',hidden:true, width: 100},
                {field: 'portname', title: '业务名', width: 100},
                {field: 'bandwidth', title: '带宽', width: 100},
                {field: 'outflowMbps', title: '出流量(Mbps)', width: 100,formatter:function(value,row,index){
                    return '<a href="javascript:void(0)" onclick="showFlows(\''+row.portid+'\')">'+value.toFixed(4)+'</a>'
                }},
                {field: 'inflowMbps', title: '入流量(Mbps)', width: 100,formatter:function(value,row,index){
                    return '<a href="javascript:void(0)" onclick="showFlows(\''+row.portid+'\')">'+value.toFixed(4)+'</a>'
                }},
                {field: 'count', title: '关联端口(个)', width: 100}
            ]],
            toolbar: '#toolbar',
            singleSelect: true,
            selectOnCheck: true,
            checkOnSelect: true,
            fitColumns:true,
            url: contextPath+'/busiPort/list',
            onDblClickRow: function (index, row) {

            }
        }
        return option;
    }
});
//查询
function searchModels() {
    var name = $("#buiName").val();
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    var tnode = zTree.getSelectedNodes();
    var pid  = "";
    if(tnode.length>0){
        pid = tnode[0].id;
        if(pid=="-1"){
            pid=""
        }else{
            pid = getAllChildrenNodes(tnode[0],pid);
        }

    }
    $('#dg').datagrid({
        url:contextPath + "/busiPort/list",
        queryParams: {key:name,gids:pid}
    });
    $("#buiName").focus();
}
function getAllChildrenNodes(treeNode,result){
    if (treeNode.isParent) {
        var childrenNodes = treeNode.children;
        if (childrenNodes) {
            for (var i = 0; i < childrenNodes.length; i++) {
                result += ',' + childrenNodes[i].id;
                result = getAllChildrenNodes(childrenNodes[i], result);
            }
        }
    }
    return result;
}
function showFlows(businame){
    var index = top.layer.open({
        type: 2,
        area: ['90%', '90%'],
        title: '查看业务详细流量',
        maxmin: true, //开启最大化最小化按钮
        content: contextPath+'/capreport/busi/cap/'+businame,
        //btn: ['查看入流量','查看出流量'],
        success: function(layero, index){
            var name = layero.find('iframe')[0].name;
            top.winref[name] = window.name;
        }
        //yes: function (index, layero) {
        //    var body = top.layer.getChildFrame('body', index);
        //    var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
        //    iframeWin.contentWindow.doSubmit(true)
        //},
        //btn2: function (index, layero) {
        //    var body = top.layer.getChildFrame('body', index);
        //    var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
        //    iframeWin.contentWindow.doSubmit(false)
        //    return false ;//开启该代码可禁止点击该按钮关闭
        //}
    });
    //openDialogView('查看业务详细流量',contextPath+'/busiPort/deal/'+businame,'800px','350px')
}
//刷新table
function reloadGrid() {
    $("#dg").datagrid('reload');
}
function onClick(event,treeId, treeNode){
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.checkAllNodes(false);
    zTree.selectNode(treeNode);
    searchModels()
}
function beforeRename(treeId, treeNode, newName, isCancel) {
    if (newName.length == 0) {
        setTimeout(function() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.cancelEditName();
            layer.msg("节点名称不能为空")
//                alert("节点名称不能为空.");
        }, 0);
        return false;
    }
    return true;
}
var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

function generateMixed(n) {
    var res = "";
    for(var i = 0; i < n ; i ++) {
        var id = Math.ceil(Math.random()*35);
        res += chars[id];
    }
    return res;
}
function onDrop(event, treeId, treeNodes,targetNode){
    var node = treeNodes[0]
    var id =  node.id,parentId=targetNode.id,name = node.name;
    save(id,name,parentId,function(data){
        if(data.state){

        }else{
            layer.msg("更新失败")
        }
    })
}
function onRename(e, treeId, treeNode, isCancel) {
    var id =  treeNode.id,parentId=treeNode.parentId,name = treeNode.name;
    save(id,name,parentId,function(data){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        if(data.state){
            zTree.updateNode(treeNode);
        }else{
            treeNode.name = treeNode.text;
            zTree.updateNode(treeNode);
            top.layer.msg("保存失败")
        }
    })
}
function menuHandler(item){
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    if(selectNode.id=="-1"){
        if(item.name!="new"){
            layer.msg("禁止操作");
            return;
        }
    }
    if(item.name=="new"){
        var groupname= "node is "+generateMixed(5);
        var parentid = selectNode.id;
        save("",groupname,parentid,function(data){
            if(data.state){
                zTree.addNodes(selectNode, {id:data.msg, pId:selectNode.id, name:groupname});
                return false;
            }else{
                top.layer.msg("保存失败")
            }
        })
    }else if(item.name=="edit"){
        zTree.editName(selectNode);
    }else{
        if(selectNode.children.length>0){
            top.layer.msg('请先清空下级节点');
            return ;
        }
        top.layer.confirm('确定删除？', {
            btn: ['确定','取消'] //按钮
        }, function(index){
            top.layer.close(index);
            $.post(contextPath+"/busiPort/group/delete/"+selectNode.id,function(data){
                if(data.state){
                    zTree.removeNode(selectNode);
                    return false;
                }else{
                    top.layer.msg("删除失败失败")
                }
            })})
    }
}

function save(id,name,pid,callback){
    $.post(contextPath+"/busiPort/group/save",{
        id:id,
        name:name
        ,parentId:pid
    },function(data){
        callback(data);
    })
}
