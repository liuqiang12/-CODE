var zTree;

var setting = {
    view: {
        dblClickExpand: false,
        showLine: true,
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable:true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: ""
        }
    },
    callback: {
        beforeClick: function(treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("tree");
            if (treeNode.isParent) {
                zTree.expandNode(treeNode);
                return false;
            } else {
                var url=treeNode.openurl;
                setNavTitle(treeNode.name);
                if(url){
                    loadUrl(url);
                }

                //做菜单跳转
                return true;
            }
        }
    }
};
//var iconPath="skins/blue/mainframe/menu/";
//var zNodes =[
//    {id:1, pId:0, name:"资源管理222", open:true,icon:iconPath+"menu-pre-icon001.png"},
//    {id:101, pId:1, name:"设备管理", icon:iconPath+"menu-pre-icon00102png.png"},//iconSkin:"icon10002"
//    {id:102, pId:1, name:"端子管理", icon:iconPath+"menu-pre-icon00101png.png"},//iconSkin:"icon10002"
//    {id:103, pId:1, name:"带宽管理", icon:iconPath+"menu-pre-icon00103png.png"},//iconSkin:"icon10002"
//    {id:104, pId:1, name:"动力设备管理", icon:iconPath+"menu-pre-icon00104png.png"},//iconSkin:"icon10002"
//    {id:105, pId:1, name:"IP地址管理", icon:iconPath+"menu-pre-icon00105png.png"},//iconSkin:"icon10002"
//    {id:106, pId:1, name:"连接管理", icon:iconPath+"menu-pre-icon00106png.png"},//iconSkin:"icon10002"
//
//    {id:2, pId:0, name:"资源管理", icon:iconPath+"menu-pre-icon001.png"},
//    {id:201, pId:2, name:"设备管理", icon:iconPath+"menu-pre-icon00102png.png"},//iconSkin:"icon10002"
//    {id:202, pId:2, name:"端子管理", icon:iconPath+"menu-pre-icon00101png.png"},//iconSkin:"icon10002"
//    {id:203, pId:2, name:"带宽管理", icon:iconPath+"menu-pre-icon00103png.png"},//iconSkin:"icon10002"
//    {id:204, pId:2, name:"动力设备管理", icon:iconPath+"menu-pre-icon00104png.png"},//iconSkin:"icon10002"
//    {id:205, pId:2, name:"IP地址管理", icon:iconPath+"menu-pre-icon00105png.png"},//iconSkin:"icon10002"
//    {id:206, pId:2, name:"连接管理1", icon:iconPath+"menu-pre-icon00106png.png"}//iconSkin:"icon10002"
//
//];

$(document).ready(function(){
    var t = $("#tree");
    t = $.fn.zTree.init(t, setting, nodes);
    var zTree = $.fn.zTree.getZTreeObj("tree");
    zTree.selectNode(zTree.getNodeByParam("id", 101));

});

function setNavTitle(titleText){
	$(".topNavItems").empty();
    var nav = $('<li class="topNav-item">' + titleText + '</li>');
    $(".topNavItems").append(nav);
}

function loadUrl(url){
    $("#frmright").attr("src",contextPath+url);
}

function conVisible(visible){
	if(visible){
		$("#main_shutiao").show();
	}else{
		$("#main_shutiao").hide();
	}
}

function menuVisible(visible,force){
    $("#main_shutiao").show();
    if(visible){
        //        var className = $("#bs_center").attr("class");
//        if (className != "bs_rightArr") {
//            $("#bs_center").click();
//        }
        var className = $("#bs_center").attr("class");
        if (className == "bs_rightArr") {
            $("#bs_center").click();
        }
        //$("#hideCon").show();
        $("#main_shutiao").show();
    }else{
        var className = $("#bs_center").attr("class");
        if (className != "bs_rightArr") {
            $("#bs_center").click();
        }
        //$("#hideCon").hide();
        if(force){
        	$("#main_shutiao").hide();
        	$(".rbox_left_navcontent").css("marginLeft",0);
        }
    }
}

function gridPage(){
    menuVisible(true);

    ///var url="plugins/jquery-easyui-1.5.2/demo/datagrid/frozencolumns.html";
    //loadUrl(url);
}

function mainPage(){
    menuVisible(false);
    $("#frmright").css("overflow-y","auto");

    var url="templete/main_manager.html";
    loadUrl(url);
}

