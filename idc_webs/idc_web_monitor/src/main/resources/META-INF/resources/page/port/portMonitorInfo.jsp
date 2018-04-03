<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css/base.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\easyui.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\icon.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css/providers.css"/>
    <title>资源占用流程</title>
</head>
<body>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',title:'资源树',width:230,border:true,headerCls:'panel-background'"
         style="padding:2px;">
        <div id="treeMenu_device" class="easyui-panel" data-options="fit:true,border:false"
             style="overflow: auto;"></div>
    </div>
    <div data-options="region:'center',border:false" style="padding-left:2px;overflow:hidden;">
        <div class="easyui-panel" border="false" fit="true" id="showAccessUserListTabel">
            <table id="dg" style="width:100%" fit="true" title="端口流量" data-options="
                rownumbers:true,
                singleSelect:false,
                autoRowHeight:false,
                pagination:true,
                fitColumns:true,
                striped:true,
                checkOnSelect:false,
                selectOnCheck:false,
                collapsible:true,
                toolbar:'#tb',
                pageSize:10">
                <thead>
                <tr>
                    <th field="device" width="110">所属设备</th>
                    <th field="name" width="226">端口名称</th>
                    <th field="createtime" width="110">采集时间</th>
                    <th field="inflow" width="112">入流量</th>
                    <th field="outflow" width="170">出流量</th>
                    <th field="allflow" width="130">总流量</th>
                </tr>
                </thead>
            </table>
            <div id="tb" style="padding:0 30px;">
                设备: <input class="easyui-textbox" type="text" name="code" style="width:166px;height:35px;line-height:35px;"></input>
                端口名称: <input class="easyui-textbox" type="text" name="name" style="width:166px;height:35px;line-height:35px;"></input>
                <a href="#" class="easyui-linkbutton" iconCls="icon-search" data-options="selected:true">查询</a>
                <a href="#" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    (function($){
        function pagerFilter(data){
            if ($.isArray(data)){   // is array
                data = {
                    total: data.length,
                    rows: data
                }
            }
            var target = this;
            var dg = $(target);
            var state = dg.data('datagrid');
            var opts = dg.datagrid('options');
            if (!state.allRows){
                state.allRows = (data.rows);
            }
            if (!opts.remoteSort && opts.sortName){
                var names = opts.sortName.split(',');
                var orders = opts.sortOrder.split(',');
                state.allRows.sort(function(r1,r2){
                    var r = 0;
                    for(var i=0; i<names.length; i++){
                        var sn = names[i];
                        var so = orders[i];
                        var col = $(target).datagrid('getColumnOption', sn);
                        var sortFunc = col.sorter || function(a,b){
                                    return a==b ? 0 : (a>b?1:-1);
                                };
                        r = sortFunc(r1[sn], r2[sn]) * (so=='asc'?1:-1);
                        if (r != 0){
                            return r;
                        }
                    }
                    return r;
                });
            }
            var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
            var end = start + parseInt(opts.pageSize);
            data.rows = state.allRows.slice(start, end);
            return data;
        }

        var loadDataMethod = $.fn.datagrid.methods.loadData;
        var deleteRowMethod = $.fn.datagrid.methods.deleteRow;
        $.extend($.fn.datagrid.methods, {
            clientPaging: function(jq){
                return jq.each(function(){
                    var dg = $(this);
                    var state = dg.data('datagrid');
                    var opts = state.options;
                    opts.loadFilter = pagerFilter;
                    var onBeforeLoad = opts.onBeforeLoad;
                    opts.onBeforeLoad = function(param){
                        state.allRows = null;
                        return onBeforeLoad.call(this, param);
                    }
                    var pager = dg.datagrid('getPager');
                    pager.pagination({
                        onSelectPage:function(pageNum, pageSize){
                            opts.pageNumber = pageNum;
                            opts.pageSize = pageSize;
                            pager.pagination('refresh',{
                                pageNumber:pageNum,
                                pageSize:pageSize
                            });
                            dg.datagrid('loadData',state.allRows);
                        }
                    });
                    $(this).datagrid('loadData', state.data);
                    if (opts.url){
                        $(this).datagrid('reload');
                    }
                });
            },
            loadData: function(jq, data){
                jq.each(function(){
                    $(this).data('datagrid').allRows = null;
                });
                return loadDataMethod.call($.fn.datagrid.methods, jq, data);
            },
            deleteRow: function(jq, index){
                return jq.each(function(){
                    var row = $(this).datagrid('getRows')[index];
                    deleteRowMethod.call($.fn.datagrid.methods, $(this), index);
                    var state = $(this).data('datagrid');
                    if (state.options.loadFilter == pagerFilter){
                        for(var i=0; i<state.allRows.length; i++){
                            if (state.allRows[i] == row){
                                state.allRows.splice(i,1);
                                break;
                            }
                        }
                        $(this).datagrid('loadData', state.allRows);
                    }
                });
            },
            getAllRows: function(jq){
                return jq.data('datagrid').allRows;
            }
        })
    })(jQuery);

    function getData(){
        var rows = [];
        for(var i=1; i<=800; i++){
            rows.push({
                device: '设备01',
                name: '端口'+i,
                inflow: '100Mb',
                createtime:new Date(),
                outflow: '100Mb',
                allflow: '200Mb'
            });
        }
        return rows;
    }

    $(function(){
        $('#dg').datagrid({data:getData()}).datagrid('clientPaging');
    });
</script>
</body>
</html>