<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/start/start.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/framework/jeasyui\jquery-easyui-1.5\themes\custom\uimaker\css\basic_info.css"/>
    <title>合同信息列表</title>
</head>
<style type="text/css">
    .textbox .textbox-button, .textbox .textbox-button:hover{
        right: 0 !important;
    }
    .fieldsetHeadCls{
        border-color: #eee !important;
    }
</style>
<body>
    <input type="hidden" id="idcNameCode"  name="idcNameCode" value="${idcNameCode}">
    <input type="hidden" id="ticketInstId"  name="ticketInstId" value="${ticketInstId}">
    <div style="position:relative;width: 100%;height:100%;">
        <div style="margin: 10px 25px;">
            <fieldset class="fieldsetCls fieldsetHeadCls">
                <legend>&diams;<span style="color: #b540d4;font-size: 16px">按机架分配</span></legend>
                <div>
                    <p align="center"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="rackOrRackUnit('66001')"><span style="color: red;font-size: 20px">按照整个机架分配</span></a></p>
                    <br />
                    <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #b540d4;font-size: 16px">分配说明：此方法分配后，此机架所有的机位均属于同一个客户，其他客户不能再分配此机架的机位！</span></p>
                    <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #007bb9;font-size: 16px">步骤：选择需要分配的机架即可。</span></p>
                    <br />
                </div>
            </fieldset>
        </div>
        <br />
        <div style="margin: 10px 25px;">
            <fieldset class="fieldsetCls fieldsetHeadCls" >
                <legend>&diams;<span style="color: #b540d4;font-size: 16px">按机位分配</span></legend>
                <div>
                    <p align="center"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="rackOrRackUnit('66002')"><span style="color: red;font-size: 20px">按照机位逐个分配</span></a></p>
                    <br />
                    <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #b540d4;font-size: 16px">分配说明：将此机架里面的机架里面的部分机位分配给当前客户！</span></p>
                    <p align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: #007bb9;font-size: 16px">步骤：1.先选择需要分配的机架；2.关闭此页面，在机架右侧 “配U位” 的地方配置相应的U位。</span></p>
                    <br />
                </div>
            </fieldset>
        </div>
    </div>

</body>
<script type="text/javascript">
/*        return "/winOpenController/rackGridPanel.do?rackOrracunit="+rackOrracunit+"&locationId="+rackORRacunitSetting['idcNameCode'];
*/
/*        return "/winOpenController/rackGridPanel.do?rackOrracunit="+rackOrracunit+"&locationId="+rackORRacunitSetting['idcNameCode'];
*/
    function rackOrRackUnit(rackunit){

        var idcNameCode=$("#idcNameCode").val();
        var laySum = top.layer.open({
            type: 2,
            shadeClose: false,
            shade: 0.8,
            btn: ['确定','关闭'],
            maxmin : true,
            area: ['1024px', '90%'],
            content: contextPath+"/winOpenController/rackGridPanel.do?rackOrracunit="+rackunit+"&locationId="+idcNameCode,
            /*弹出框*/
            cancel: function(index, layero){
                top.layer.close(index);
            },yes: function(index, layero){
                var childWin = getJbpmChlidWin(layero);
                var records = childWin.getSelectionData();
                if(records && records.length == 0){
                    top.layer.msg('没有选择记录', {
                        icon: 2,
                        time: 1500
                    });
                    return false;
                }

                /*获得选择的数据*/
                var ticketInstId=$("#ticketInstId").val();
                var sets = [];   //选择的数据
                for(var i = 0 ; i < records.length ; i++){
                    //此时只有资源ID
                    var srd = {};
                    srd['ticketInstId'] = ticketInstId;
                    srd['resourceid'] = records[i];
                    srd['category']= 100;//资源类别:100机架、600机位、700MCB,200端口、300IP等
                    srd['ticketCategory']=100;//预勘/预占:100
                    srd['rackOrRackUnit'] = rackunit;  //这个是按机架还是机位分配，66001按机架分配，66002按机位分配
                    sets.push(srd);
                }
                //把选择的机架保存到数据库中去
                $.ajax({
                    type:"POST",
                    url:contextPath+"/actJBPMController/resourceSetting.do",
                    data:{resourceSettingData:JSON.stringify(sets)},
                    datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                    beforeSend:function(){
                        top.onclickProcessBar();  //打开进度条
                    },
                    success:function(data){
                        top.closeProgressbar();    //关闭进度条
                        top.layer.msg(data.msg, {
                            icon: 1,
                            time: 3000 //1.5秒关闭（如果不配置，默认是3秒）
                        });
                        top.layer.close(index);

                        var parentIndex = parent.layer.getFrameIndex("rackLayer");//获取了父窗口的索引
                        parent.layer.close(parentIndex);  // 关闭layer
                        var id = top.$("#jbpmApplyWinId > iframe").attr("id");

                        var parentWin = top.document.getElementById(id).contentWindow;
                        parentWin.refreashRackGrid()
                    },
                    complete: function(XMLHttpRequest, textStatus){
                    },
                    error: function(){
                    }
                })
            }
        });
    }
</script>
</html>