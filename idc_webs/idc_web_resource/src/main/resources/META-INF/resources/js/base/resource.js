(function ($) {
    $.fn.MyResource = function (options) {
        var me = this;
        var type = options.type || '';
        var defualts = {
            fit: true,
            pagination: true,
            singleSelect: true,
            frozenColumns: [[]],
            columns: [[]],
            title: '',
            url: '',
            onDblClickRow: function (index, row) {

                openDialogView('查看信息', contextPath + '/resource/' + type + "/" + row.id,'800px','530px')
                //me.open(type, row.id)
            }
        };
        //融合配置项
        var opts = $.extend({}, defualts, options);
        var $e = $(this);
        me.init = function () {
            return $(this).datagrid(opts)
        }
        me.open = opts.open || function (type, id) {
                openDialog('编辑信息',contextPath + '/resource/' + type + "/" + id,'800px','530px')
            }
        var grid = me.init();
        me.inittree = function () {
            var dom = $("#rtree");
            if (dom != null) {
                if(typeof(opts.rtree)=="function"){
                    var tree = opts.rtree(dom);
                    return tree
                }else{
                    var tree = dom.rtree(opts.rtree)
                    return tree
                }

            }
        };
        var rtree = me.rtree;
        if (opts.rtree) {
            /*$('#cc').layout('add',{
                region: 'west',
                width: 250,
                title: '资源树',
                //split: true,
                content:'<div data-options="region:\'west\',iconCls:\'icon-reload\',split:true" style="width:300px;"> <ul id="rtree" class="ztree" style="width:300px; overflow:auto;"></ul></div>'
            });*/
        	$('#cc').append(
    			'<div class="content_suspend">'+
        			'<div class="conter">'+
        				'<ul id="rtree" class="ztree" style="width:300px; overflow:auto;"></ul>'+
        			'</div>'+
        			'<div class="hoverbtn">'+
        				'<span>资</span><span>源</span><span>信</span><span>息</span>'+
        				'<div class="hoverimg" height="9" width="13"></div>'+
        			'</div>'+
        		'</div>');
            rtree = me.inittree();
        }
        me.getTree = function () {
            return rtree;
        }
        //返回函数对象
        return me;
    };
})(jQuery);

