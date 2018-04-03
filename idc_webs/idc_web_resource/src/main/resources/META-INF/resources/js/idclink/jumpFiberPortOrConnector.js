//添加端口
function addPortLink(event, obj) {
    //获取当前节点的所有端口
    var portIds = obj.nodePortStr;
    //获取当前节点的类型
    var type = obj.type;
    if (type == 'equipment') {//设备配端口  先选择A端端口，再选择要绑定的端口
        deviceConfigPort(obj, portIds);
    } else if (type == 'rack_df' || type == 'rack_cabinet') {//ODF架或者网络架配置端口   先选择A端端子，再选择要绑定的端口
        rackConfigPort(obj, portIds);
    } else {//机架组  直接配置端口
        var url = contextPath + "/device/preDistributionDeviceList";
        var data = {roomId:roomId,rackIds:obj.key};
        openDialogUsePost("Z端端口--" + obj.text, url, "1000px", "530px",data,"linkZPort");
    }
}
//添加端子
function addConnectorLink(event, obj) {
    //获取当前节点的所有端口
    var portIds = obj.nodePortStr;
    //获取当前节点的类型
    var type = obj.type;
    if (type == 'equipment') {//设备配端子  先选择A端端口，再选择要绑定的端子
        deviceConfigConnector(obj, portIds);
    } else if (type == 'rack_df' || type == 'rack_cabinet') {//ODF架或者网络架配置端子   先选择A端端子，再选择要绑定的端子
        rackConfigConnector(obj, portIds);
    } else {//机架组 直接配置端子
        var url = contextPath + "/idcRack/preDistributionOdfRackList";
        var data = {roomId:roomId,rackIds:obj.key};
        openDialogUsePost("Z端端子--" + obj.text, url, '1000px', '530px',data,"linkZPort");
    }
}
//设备配端口  先选择A端端口，再选择要绑定的端口
function deviceConfigPort(obj, portIds) {
    var url = contextPath + "/netport/preQueryBindedPortList";
    var data = {portIds:portIds};
    top.layer.open({
        type: 2,
        title: 'A端端口——' + obj.text,
        id:"linkAPort",
        area: ['800px', '530px'],
        shadeClose: false,
        shade: 0.8,
        btn: ['确定', '关闭'],
        maxmin: true, //开启最大化最小化按钮
        content: 'blankpage',
        scrollbar: false,
        success: function (layero, index) {
            var name = layero.find('iframe')[0].name;
            top.winref[name] = window.name;
            var chidlwin = layero.find('iframe')[0].contentWindow;
            if (chidlwin.location.href.indexOf('blankpage') > -1) {
                var html = buildFormByParam(url, data);
                chidlwin.document.body.innerHTML = "";//清空iframe内容，达到重新请求
                chidlwin.document.write(html);
                chidlwin.document.getElementById('postData_form').submit();
            }
        },
        yes: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
            var portIdA = iframeWin.contentWindow.doSubmit();
            console.log("portIdA===========" + portIdA);
            if (portIdA == null) {
                layer.msg("没有选择A端端口");
                return;
            }
            top.layer.close(index);
            //选择Z端信息
            chooseZPort(obj,portIdA);
        }
    })
}
//ODF架或者网络架配置端口   先选择A端端子，再选择要绑定的端口
function rackConfigPort(obj, portIds) {
    var url = contextPath + "/idcConnector/preQueryBindedConnectorList";
    var data = {portIds:portIds};
    top.layer.open({
        type: 2,
        title: 'A端端口——' + obj.text,
        id:"linkAPort",
        area: ['800px', '530px'],
        shadeClose: false,
        shade: 0.8,
        btn: ['确定', '关闭'],
        maxmin: true, //开启最大化最小化按钮
        content: 'blankpage',
        scrollbar: false,
        success: function (layero, index) {
            var name = layero.find('iframe')[0].name;
            top.winref[name] = window.name;
            var chidlwin = layero.find('iframe')[0].contentWindow;
            if (chidlwin.location.href.indexOf('blankpage') > -1) {
                var html = buildFormByParam(url, data);
                chidlwin.document.body.innerHTML = "";//清空iframe内容，达到重新请求
                chidlwin.document.write(html);
                chidlwin.document.getElementById('postData_form').submit();
            }
        },
        yes: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
            var portIdA = iframeWin.contentWindow.doSubmit();
            console.log("portIdA===========" + portIdA);
            if (portIdA == null) {
                layer.msg("没有选择A端端子");
                return;
            }
            top.layer.close(index);
            //选择Z端信息
            chooseZPort(obj,portIdA);
        }
    })
}
//设备配端子  先选择A端端口，再选择要绑定的端子
function deviceConfigConnector(obj, portIds) {
    var url = contextPath + "/netport/preQueryBindedPortList";
    var data = {portIds:portIds};
    top.layer.open({
        type: 2,
        title: 'A端端口——' + obj.text,
        id:"linkAPort",
        area: ['800px', '530px'],
        shadeClose: false,
        shade: 0.8,
        btn: ['确定', '关闭'],
        maxmin: true, //开启最大化最小化按钮
        content: 'blankpage',
        scrollbar: false,
        success: function (layero, index) {
            var name = layero.find('iframe')[0].name;
            top.winref[name] = window.name;
            var chidlwin = layero.find('iframe')[0].contentWindow;
            if (chidlwin.location.href.indexOf('blankpage') > -1) {
                var html = buildFormByParam(url, data);
                chidlwin.document.body.innerHTML = "";//清空iframe内容，达到重新请求
                chidlwin.document.write(html);
                chidlwin.document.getElementById('postData_form').submit();
            }
        },
        yes: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
            var portIdA = iframeWin.contentWindow.doSubmit();
            console.log("portIdA===========" + portIdA);
            if (portIdA == null) {
                layer.msg("没有选择A端端口");
                return;
            }
            top.layer.close(index);
            //选择Z端信息
            chooseZConnector(obj,portIdA);
        }
    })
}
//ODF架或者网络架配置端子   先选择A端端子，再选择要绑定的端子
function rackConfigConnector(obj, portIds) {
    var url = contextPath + "/idcConnector/preQueryBindedConnectorList";
    var data = {portIds:portIds};
    top.layer.open({
        type: 2,
        title: 'A端端子——' + obj.text,
        id:"linkAPort",
        area: ['800px', '530px'],
        shadeClose: false,
        shade: 0.8,
        btn: ['确定', '关闭'],
        maxmin: true, //开启最大化最小化按钮
        content: 'blankpage',
        scrollbar: false,
        success: function (layero, index) {
            var name = layero.find('iframe')[0].name;
            top.winref[name] = window.name;
            var chidlwin = layero.find('iframe')[0].contentWindow;
            if (chidlwin.location.href.indexOf('blankpage') > -1) {
                var html = buildFormByParam(url, data);
                chidlwin.document.body.innerHTML = "";//清空iframe内容，达到重新请求
                chidlwin.document.write(html);
                chidlwin.document.getElementById('postData_form').submit();
            }
        },
        yes: function (index, layero) {
            var iframeWin = layero.find('iframe')[0]; // 得到iframe页的窗口对象，执行 iframe 页的方法：iframeWin.method();
            var portIdA = iframeWin.contentWindow.doSubmit();
            console.log("portIdA===========" + portIdA);
            if (portIdA == null) {
                layer.msg("没有选择A端端子");
                return;
            }
            top.layer.close(index);
            //选择Z端信息
            chooseZConnector(obj,portIdA);
        }
    })
}
//设备或机架选择Z端端子
function chooseZConnector(obj,portIdA){
    var url = contextPath + "/idcRack/preDistributionOdfRackList";
    var data = {roomId:obj.roomId,rackIds:obj.key,portIdA:portIdA};
    openDialogUsePost("Z端端子--" + obj.text,url,"1000px", "530px",data,"linkZPort");
}
//设备或机架选择Z端端口
function chooseZPort(obj,portIdA){
    var url = contextPath + "/device/preDistributionDeviceList";
    var data = {roomId:obj.roomId,rackIds:obj.key,portIdA:portIdA};
    openDialogUsePost("Z端端口--" + obj.text, url, "1000px", "530px",data,"linkZPort");
}
//删除链路
function deleteIdcLink(event, obj){
    var portStr = obj.linkPortStr;
    var aKey = obj.nodeA.key;
    var zKey = obj.nodeZ.key;
    if(obj.lineWidth>1){//表示有多条连线   需要进入列表删除
        var url = contextPath + '/idclink/getPreLinksByAZ';
        var data = {aKey:aKey,zKey:zKey,portStr:portStr};
        openDialogUsePost('链接信息',url, '1000px', '530px',data,'showLinkInfo');
    }else{//表示只有1条链路，可直接删除
        layer.confirm('是否确认删除',{ btn : [ '确定', '取消' ]},function(index){
            $.post(contextPath +'/idclink/deleteLinkByParams',{portStr:portStr},function(result){
                alert(result.msg);
                if(result.state){
                    reloadWin();
                }
            });
            layer.close(index);
        });
    }
}