<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<script>
    var contextPath = "${pageContext.request.contextPath}";
</script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <link rel="stylesheet" href="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/style.css" type="text/css" media="screen">
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/jstools.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/raphael.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/jquery/jquery.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/jquery/jquery.progressbar.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/jquery/jquery.asyncqueue.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/Color.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/Polyline.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/ActivityImpl.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/ActivitiRest.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/LineBreakMeasurer.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/ProcessDiagramGenerator.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=request.getContextPath() %>/activiti-moduler/diagram-viewer/js/ProcessDiagramCanvas.js" type="text/javascript" charset="utf-8"></script>

    <style type="text/css" media="screen">

    </style>
</head>
<body>
<div class="wrapper" style="width: 100%;height: 100%;">
    <%--<div id="pb1"></div>--%>
    <div id="overlayBox"  style="position: relative">
        <%--<div id="diagramBreadCrumbs" class="diagramBreadCrumbs" onmousedown="return false" onselectstart="return false"></div>--%>
        <div id="diagramHolder" class="diagramHolder"></div>
    </div>
    <div class="diagram-info" id="diagramInfo"></div>

</div>
<script language='javascript'>
    var DiagramGenerator = {};
    var pb1;
    $(document).ready(function(){

        var processDefinitionId = "${processDefinitionId}";//query_string["processDefinitionId"];
        var processInstanceId = "${processInstanceId}";//query_string["processInstanceId"];

        console.log("Initialize progress bar");

        pb1 = new $.ProgressBar({

            on: {
                complete: function() {
                    console.log("Progress Bar COMPLETE");
                    //this.set('label', 'complete!');
                    if (processInstanceId) {
                        ProcessDiagramGenerator.drawHighLights(processInstanceId);
                    }
                },
                valueChange: function(e) {
                    this.set('label', e.newVal + '%');
                }
            },
            value: 0
        });
        console.log("Progress bar inited");

        ProcessDiagramGenerator.options = {
            /*diagramBreadCrumbsId: "diagramBreadCrumbs",*/
            diagramHolderId: "diagramHolder",
             diagramInfoId: "diagramInfo",
            on: {
                click: function(canvas, element, contextObject){
                    var mouseEvent = this;
                    console.log("[CLICK] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);

                    if (contextObject.getProperty("type") == "callActivity") {
                        var processDefinitonKey = contextObject.getProperty("processDefinitonKey");
                        var processDefinitons = contextObject.getProperty("processDefinitons");
                        var processDefiniton = processDefinitons[0];
                        console.log("Load callActivity '" + processDefiniton.processDefinitionKey + "', contextObject: ", contextObject);

                        // Load processDefinition
                        ProcessDiagramGenerator.drawDiagram(processDefiniton.processDefinitionId);
                    }
                },
                rightClick: function(canvas, element, contextObject){
                    var mouseEvent = this;
                    console.log("[RIGHTCLICK] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);
                },
                over: function(canvas, element, contextObject,offsetX,offsetY){
                    var mouseEvent = this;
                    //console.log("[OVER] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);

                    // TODO: show tooltip-window with contextObject info

                    ProcessDiagramGenerator.showActivityInfo(contextObject,offsetX,offsetY);
                },
                out: function(canvas, element, contextObject){
                    var mouseEvent = this;
                    //console.log("[OUT] mouseEvent: %o, canvas: %o, clicked element: %o, contextObject: %o", mouseEvent, canvas, element, contextObject);

                    ProcessDiagramGenerator.hideInfo();
                }
            }
        };

        ActivitiRest.options = {
            processInstanceHighLightsUrl: contextPath + "/service/process-instance/${processInstanceId}/highlights?callback=?",
            processDefinitionUrl: contextPath + "/service/process-definition/${processDefinitionId}/diagram-layout?callback=?",
            processDefinitionByKeyUrl: contextPath + "/service/process-definition/${processDefinitionKey}/diagram-layout?callback=?"
        };

        if (processDefinitionId) {
            ProcessDiagramGenerator.drawDiagram(processDefinitionId);

        } else {
            alert("processDefinitionId parameter is required");
        }
    });


</script>
</body>
</html>
