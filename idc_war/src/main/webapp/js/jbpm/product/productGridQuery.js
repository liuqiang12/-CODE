/**
 * grid 行扩展
 */
$.extend($.fn.datagrid.defaults.view, {
    render : function(target, container, frozen) {
        var state = $.data(target, "datagrid");
        var opts = state.options;
        var rows = state.data.rows;
        var fields = $(target).datagrid("getColumnFields", frozen);
        if (frozen) {
            if (!(opts.rownumbers || (opts.frozenColumns && opts.frozenColumns.length))) {
                return;
            }
        }
        if (opts.singleSelect) {
            var ck = $(".datagrid-header-row .datagrid-header-check");
            $(ck).empty();
        }
        var table = ["<table class=\"datagrid-btable\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody>"];
        for (var i = 0; i < rows.length; i++) {
            var cls = (i % 2 && opts.striped)
                ? "class=\"datagrid-row datagrid-row-alt\""
                : "class=\"datagrid-row\"";
            var styleValue = opts.rowStyler ? opts.rowStyler.call(target, i,
                rows[i]) : "";
            var style = styleValue ? "style=\"" + styleValue + "\"" : "";
            var rowId = state.rowIdPrefix + "-" + (frozen ? 1 : 2) + "-" + i;
            table.push("<tr id=\"" + rowId + "\" datagrid-row-index=\"" + i
                + "\" " + cls + " " + style + ">");
            table.push(this.renderRow.call(this, target, fields, frozen, i,
                rows[i]));
            table.push("</tr>");
        }
        table.push("</tbody></table>");
        // $(container).html(table.join(""));
        $(container)[0].innerHTML = table.join("");
        // 增加此句以实现,formatter里面可以返回easyui的组件，以便实例化。例如：formatter:function(){
        // return "<a href='javascript:void(0)'
        // class='easyui-linkbutton'>按钮</a>" }}
        $.parser.parse(container);
    },
    renderRow : function(target, fields, frozen, rowIndex, rowData) {
        var opts = $.data(target, "datagrid").options;
        var cc = [];
        if (frozen && opts.rownumbers) {
            var rownumber = rowIndex + 1;
            if (opts.pagination) {
                rownumber += (opts.pageNumber - 1) * opts.pageSize;
            }
            cc
                .push("<td class=\"datagrid-td-rownumber\"><div class=\"datagrid-cell-rownumber\">"
                    + rownumber + "</div></td>");
        }
        for (var i = 0; i < fields.length; i++) {
            var field = fields[i];
            var col = $(target).datagrid("getColumnOption", field);
            if (col) {
                // 修改默认的value取值，改了此句之后field就可以用关联对象了。如：people.name
                var value = jQuery.proxy(function() {
                    try {
                        return eval('this.' + field);
                    } catch (e) {
                        return "";
                    }
                }, rowData)();
                var styleValue = col.styler ? (col.styler(value, rowData,
                    rowIndex) || "") : "";
                var style = col.hidden ? "style=\"display:none;" + styleValue
                    + "\"" : (styleValue
                    ? "style=\"" + styleValue + "\""
                    : "");
                cc.push("<td field=\"" + field + "\" " + style + ">");
                if (col.checkbox) {
                    var style = "";
                } else {
                    var style = styleValue;
                    if (col.align) {
                        style += ";text-align:" + col.align + ";";
                    }
                    if (!opts.nowrap) {
                        style += ";white-space:normal;height:auto;";
                    } else {
                        if (opts.autoRowHeight) {
                            style += ";height:auto;";
                        }
                    }
                }
                cc.push("<div style=\"" + style + "\" ");
                if (col.checkbox) {
                    cc.push("class=\"datagrid-cell-check ");
                } else {
                    cc.push("class=\"datagrid-cell " + col.cellClass);
                }
                cc.push("\">");
                if (col.checkbox) {
                    var type = opts.singleSelect == true ? "radio" : "checkbox";
                    cc.push("<input type=" + type + " name=\"" + field
                        + "\" value=\"" + (value != undefined ? value : "")
                        + "\"/>");
                } else {
                    if (col.formatter) {
                        cc.push(col.formatter(value, rowData, rowIndex));
                    } else {
                        cc.push(value);
                    }
                }
                cc.push("</div>");
                cc.push("</td>");
            }
        }
        return cc.join("");
    }
});
/*
 * 有关流程模型的所有js都在这里
 */
$(function() {
    //初始化时就加载数据:构造列表信息
    loadGrid("gridId");


});
function loadGrid(gridId){
    //创建表信息
    var url = getRequestUrl();
    var params = getRequestParams();
    var columns = createColumns();
    $("#"+gridId).datagrid({
        url : contextPath + url,
        queryParams: params,
        columns : columns,
        onLoadSuccess:myLoadsuccess,
        toolbar:"#requestParamSettins"
    })
}
//初始化按钮事件
function myLoadsuccess(data){
    $(".datagrid-cell").find('.easyui-linkbutton').each(function () {
        $(this).linkbutton();
    });

}
function getRequestUrl(){
    return "/resourceApplyController/productGridQueryData.do";
}

// 获取查询条件
function getRequestParams(){
    var prodStatus = $("#prodStatus").combobox("getValue");
    var prodCategory = $("#prodCategory").combobox("getValue");
    var customerId = $("#customerId").val();
    var ticket_ctl = $("#ticket_ctl").val();
    /*参数组装*/
    var params = {};
    params['prodStatus'] = prodStatus;
    params['prodCategory'] = prodCategory;
    params['customerId'] = customerId;
    params['ticket_ctl'] = ticket_ctl;
    return params;
}
//获取列信息
function createColumns(){
    //创建列表信息
    var headTr1Cols = [];
    //修改单元格字体:颜色
    headTr1Cols.push({field:"ck",checkbox:true,halign:'left'});
    headTr1Cols.push({field:'prodName',title:'订单名称',halign:'center',width:200,formatter:fmtLinkAction});
    headTr1Cols.push({field:'customerName',title:'客户名称',halign:'center',width:100});
    headTr1Cols.push({field:'prodStatus',title:'业务状态',halign:'center',width:100,formatter:fmtProdStatusAction});
    headTr1Cols.push({field:'prodCategory',title:'订单类别',halign:'center',width:100,formatter:fmtProdCategoryAction});
    headTr1Cols.push({field:'categoryLinked',title:'服务类型',width:100,halign:'center'});
    headTr1Cols.push({field:'applyName',title:'申请人',width:100,halign:'center',formatter:columnCSS});
    headTr1Cols.push({field:'createTime',title:'申请时间',width:150,halign:'center',formatter:fmtDateWithCssAction});
    headTr1Cols.push({field:'endTime',title:'订单结束时间',width:50,halign:'center',formatter:fmtDateWithCssAction});
    headTr1Cols.push({field:'procticketStatusName',title:'工单节点',width:70,halign:'center'});
    //停机操作
   /* headTr1Cols.push({field:"prodName",title:"停机",halign:'center',width:100,formatter:fmtPauseAction});*/
    //操作
    headTr1Cols.push({field:"option",title:"操作",halign:'center',width:100,formatter:fmtAction});
    return [headTr1Cols];
}
function fmtAction(value,row,index){
    var insert= [];
    //订单ID
    var id = row.id;
    //如果该订单中有正在运行的工单，则不能再次创建
    var isRunTiket = row.isRunTiket;
    var prodName = row.prodName;
    var procticketStatus = row.procticketStatus;//流程状态
    var prodCategory = row.prodCategory;//订单类型
    /*拼接下来情况*/
    //不显示的是：预勘中、开通中  停机中  下线中  变更中  复通中
    if(procticketStatus != 20 &&
        procticketStatus != 30 &&
        procticketStatus !=40 &&
        procticketStatus != 50 &&
        procticketStatus != 60 &&
        procticketStatus != 70&&
        procticketStatus != 80&&
        procticketStatus != 90&&
        procticketStatus != 100){
            var menus = menubtn(row, index, {
                option : [{
                    text : '更多',
                    icon : 'icon_list_grid',
                    items : [{
                        text : '创建预勘/预占工单',
                        icon : 'ticket_create',
                        onclick : 'singleStartRow',
                        hide:procticketStatus == 10?false:true,
                        catalog: 100
                    }, {
                        text : '创建变更预勘工单',
                        icon : 'icon_chage',
                        onclick : 'testFun',
                        hide: procticketStatus == 21?false:true,//已经预勘
                        catalog: 300
                    }, {
                        text : '创建变更开通工单',
                        icon : 'icon_chage',
                        onclick : 'singleStartRow',
                        hide:(procticketStatus == 31|| procticketStatus == 81||procticketStatus == 71||procticketStatus == 101)?false:true,//已开通
                        catalog: 700
                    }, {
                        text : '创建业务变更工单',
                        icon : 'icon_chage',
                        onclick : 'singleBusinessStartRow',
                        hide:(procticketStatus == 31 || procticketStatus == 51 || procticketStatus == 81||procticketStatus == 71||procticketStatus == 101)?false:true,//已开通
                        catalog: 900
                    }, {
                        text : '创建停机工单',
                        icon : 'icon_pause',
                        onclick : 'singleStartRow',
                        hide:(procticketStatus == 31|| procticketStatus == 81||procticketStatus == 71||procticketStatus == 101)?false:true,//已开通
                        catalog: 400
                    }, {
                        text : '创建复通工单',
                        icon : 'icon_resover',
                        onclick : 'singleStartRow',
                        hide:(procticketStatus == 31||procticketStatus == 41)?false:true,//已经停机
                        catalog: 500
                    }, {
                        text : '创建下线工单',
                        icon : 'icon_halt',
                        onclick : 'singleStartRow',
                        hide:(procticketStatus == 41 || procticketStatus == 81 ||procticketStatus == 31 || procticketStatus == 71||procticketStatus == 101)?false:true,//已停机或者已开通或者已经复通
                        catalog: 600
                    }, {
                        text : '创建临时测试工单',
                        icon : 'icon_test',
                        onclick : 'singleStartRow',
                        hide:procticketStatus == 31?false:true,//已开通
                        catalog: 800
                    }]
                }]
            });
        return menus;
    }else{
        /*进行中*/
        return '<a class="easyui-linkbutton " title="进行中" data-options="plain:true,iconCls:\'tiketing\'"></a> ';
    }
}

function testFun(obj){
    alert("清除缓存成功");
    alert("测试创建成功");
}

function menubtn(value, index, options) {
    var id = value.id;
    //如果该订单中有正在运行的工单，则不能再次创建
    var isRunTiket = value.isRunTiket;
    var prodName = value.prodName;
    var procticketStatus = value.procticketStatus;//流程状态
    var prodCategory = value.prodCategory;//订单类型
    console.log(value);
    console.log(id);
    console.log(isRunTiket);
    console.log(prodName);
    console.log(prodCategory);
    var opt = options || {};
    var len = opt.option.length;
    var div = $('<div></div>');
    if ($("div[id^='submenu']"))
        $("div[id^='submenu']").remove();
    for (var i = 0; i < len; i++) {
        var op = opt.option[i];
        var span = $('<a href="javascript:void(0)" class="easyui-menubutton" style="color:#166DCC" id="menubtn'
            + index
            + '"  plain="true" iconCls="'
            + op.icon
            + '" menu="#submenu' + index + '"></a>');

        span.append(op.text);
        span.appendTo(div);
        var items = op.items || {};
        var subdiv = $('<div id="submenu' + index + '"></div>');
        for (var j = 0; j < items.length; j++) {
            var item = items[j];
            var hide = '';
            if (item.hide)
                hide = 'style=display:none';
            var sub = $('<div  iconCls="' + item.icon + '" onclick='
                + item.onclick + '(' + id + ',\'' + prodName + '\',\'' + item.catalog + '\',' + prodCategory + ') ' + hide + '>'
                + item.text + '</div>');
            sub.appendTo(subdiv);
        }
        subdiv.appendTo(div);
    }
    return div.html();
}
//业务变更
function singleBusinessStartRow(id,prodName,catalog,prodCategory){
    /* 创建工单 */
    top.layer.confirm('确定要创建['+prodName+']订单的工单吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /!*ajax*!/;
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/actJBPMController/singleBusinessCreateTicket/"+id,
            //提交的数据
            data:{
                prodName: prodName,
                catalog:catalog,
                prodCategory:prodCategory
            },
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
            },
            //成功返回之后调用的函数
            success:function(data){
                //提示删除成功
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                $("#gridId").datagrid("reload");
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
            },
            //调用出错执行的函数
            error: function(){
                //请求出错处理
            }
        });
    }, function(index, layero){
        top.layer.close(index)
    });
}
//创建工单    //    这个是创建预勘/预占工单
function singleStartRow(id,prodName,catalog,prodCategory){
    /* 创建工单 */
     top.layer.confirm('确定要创建['+prodName+']订单的工单吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /!*ajax*!/;
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/actJBPMController/singleCreateTicket/"+id,
            //提交的数据
            data:{
                prodName: prodName,
                catalog:catalog,
                prodCategory:prodCategory
            },
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
            },
            //成功返回之后调用的函数
            success:function(data){
                //提示删除成功
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                $("#gridId").datagrid("reload");
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
            },
            //调用出错执行的函数
            error: function(){
                //请求出错处理
            }
        });
    }, function(index, layero){
        top.layer.close(index)
    });
}
function columnCSS(value,row,index){
    var css = "#036513";
    return '<span style="color:'+css+'">'+value+'</span>';//改变表格中内容字体的大小
}
function fmtDateWithCssAction(value,row,index){
    if (value != null) {
        var date = new Date(value);
        var year = date.getFullYear().toString();
        var month = (date.getMonth() + 1);
        var day = date.getDate().toString();
        var hour = date.getHours().toString();
        var minutes = date.getMinutes().toString();
        var seconds = date.getSeconds().toString();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        if (hour < 10) {
            hour = "0" + hour;
        }
        if (minutes < 10) {
            minutes = "0" + minutes;
        }
        if (seconds < 10) {
            seconds = "0" + seconds;
        }
        var css = "#036513";
        return '<span style="color:'+css+'">'+year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds+'</span>';//改变表格中内容字体的大小
    }
}

//订单类别
function fmtProdCategoryAction(value,row,index){
    //分类说明
    var label = value == '1'?"政企业务":
                    value == '0'?"自由业务":
                        null;
    return label;
}
//订单状态
function fmtProdStatusAction(value,row,index){
    //订单状态
    var label = value == '10'?"在途":
                    value == '20'?"已竣工":
                        value == '30'?"已停机":
                            value == '40'?"已撤销":
                                null;
    return label;
}
function fmtIsActiveAction(value,row,index){
    var label = value == '1'?"激活":
                    value == '0'?"禁用":
                        null;
    return label;
}
function fmtLinkAction(value,row,index){
    return '<a href="javascript:void(0);" onclick="linkQueryWin(\''+row.id+'\',\''+row.prodName+'\')">'+value+'</a>';
}

function openWinCustomerNameFun(){
    //选择弹出框
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">客户人员</span>'+"》选择",
        shadeClose: false,
        shade: 0.8,
        btn: ['确定','关闭'],
        maxmin : true,
        area: ['884px', '90%'],
        content: contextPath+"/customerController/customerQuery.do",
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            //返回相应的数据
            var selectedRecord = chidlwin.gridSelectedRecord("gridId");
            $("#customerId").val(selectedRecord?selectedRecord['id']:null);
            $("#customerName").textbox('setValue', selectedRecord?selectedRecord['name']:null);
            top.layer.close(index)
            //刷新列表信息
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            //top.layer.close(index);
        }
    });
}
//新增相应的模型
function singleCreateOrUpdate(gridId){
    /**
     * 添加
     */
    /*var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">订单服务</span>'+"》新增",
        shadeClose: false,
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['1014px', '670px'],
        content: contextPath+"/resourceApplyController/singleCreateOrUpdate.do",
        /!*弹出框*!/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            chidlwin.form_sbmt("gridId");
            //刷新列表信息
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });*/
    /*此时需要重新写*/
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">订单服务</span>'+"》新增",
        shadeClose: false,
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['1014px', '90%'],
        content: contextPath+"/resourceApplyController/singleCreateOrUpdate_new.do",
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            chidlwin.form_sbmt("gridId");
            //刷新列表信息
        },no: function(index, layero){
            top.layer.close(index)
        }
    });

}
/**
 * 修改
 * @param gridId
 * @returns {boolean}
 */
function singleUpdateRow(gridId){
    //至少选中一行
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">订单['+singleRecordData[0]['prodName']+']管理</span>'+"》修改",
        shadeClose: false,
        shade: 0.8,
        btn: ['保存','关闭'],
        maxmin : true,
        area: ['1014px', '90%'],
        content: contextPath+"/resourceApplyController/singleCreateOrUpdate_new.do?id="+singleRecordData[0]['id'],
        /*弹出框*/
        cancel: function(index, layero){
            top.layer.close(index);
        },yes: function(index, layero){
            var childIframeid = layero.find('iframe')[0]['name'];
            var chidlwin = top.document.getElementById(childIframeid).contentWindow;
            chidlwin.form_sbmt("gridId");
            //刷新列表信息
        },no: function(index, layero){
            top.layer.close(index)
        },end:function(){
            $("#gridId").datagrid("reload");
        }
    });
}
//删除
function singleDeleteRow(gridId){
    //至少选中一行
    var singleRecordData = $("#"+gridId).datagrid("getChecked");
    if(singleRecordData && singleRecordData.length == 0){
        /*弹出提示框*/
        top.layer.msg('至少选择一条记录', {
            icon: 2,
            time: 1500 //1.5秒关闭（如果不配置，默认是3秒）
        });
        return false;
    }
    top.layer.confirm('你确定要删除['+singleRecordData[0]['prodName']+']订单吗？', {
        btn: ['确定','取消'] //按钮
    }, function(index, layero){
        /*ajax*/
        $.ajax({
            //提交数据的类型 POST GET
            type:"POST",
            //提交的网址
            url:contextPath+"/resourceApplyController/singleDelete/"+singleRecordData[0]['id'],
            //提交的数据
            data:{
                name: singleRecordData[0]['prodName']
            },
            //返回数据的格式
            datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
            //在请求之前调用的函数
            beforeSend:function(){
            },
            //成功返回之后调用的函数
            success:function(data){
                //提示删除成功
                top.layer.msg(obj.msg, {
                    icon: 1,
                    time: 3000 //3秒关闭（如果不配置，默认是3秒）
                });
                $("#"+gridId).datagrid("reload");
            },
            //调用执行后调用的函数
            complete: function(XMLHttpRequest, textStatus){
            },
            //调用出错执行的函数
            error: function(){
                //请求出错处理
            }
        });
    }, function(index, layero){
        top.layer.close(index)
    });
}
//查看详情界面
function linkQueryWin(id,name){

    var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">订单['+name+']管理</span>'+"》详情",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['1014px', '90%'],
        content: contextPath+"/resourceApplyController/linkQueryWin.do?viewQuery=1&id="+id,
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        }
    });
}
/* 根据类型弹出不同的界面表单 */
function linkCategoryWin(category,prodInstId){
    //alert(category+","+prodInstId);
    //弹出框显示不同的form信息
    /*var laySum = top.layer.open({
        type: 2,
        title: '<span style="color:blue">订单['+name+']管理</span>'+"》详情",
        shadeClose: false,
        shade: 0.8,
        btn: ['关闭'],
        maxmin : true,
        area: ['1014px', '670px'],
        content: contextPath+"/resourceApplyController/linkQueryWin.do?viewQuery=1&id="+prodInstId,
        cancel: function(index, layero){
            top.layer.close(index);
        },no: function(index, layero){
            top.layer.close(index)
        }
    });*/
}