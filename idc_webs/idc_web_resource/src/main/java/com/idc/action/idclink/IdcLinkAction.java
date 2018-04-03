package com.idc.action.idclink;

import com.idc.model.ExecuteResult;
import com.idc.model.IdcLink;
import com.idc.model.LinkNode;
import com.idc.service.IdcLinkService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by mylove on 2017/6/1.
 */
@Controller
public class IdcLinkAction {
    private static final Logger logger = LoggerFactory.getLogger(IdcLinkAction.class);
    @Autowired
    private IdcLinkService idcLinkService;

    @RequestMapping("/idclink/{type}/{id}")
    public String getTreeToMap(@PathVariable String type, @PathVariable long id, ModelMap map) {
        List<LinkNode> results = idcLinkService.getTree(type, id);
        List<LinkNode> links = new ArrayList<>();
        List<LinkNode> nodes = new ArrayList<>();
        for (LinkNode node : results) {
            if (node == null) continue;
            if (node.getA() != null && node.getZ() != null && !node.getA().equals(node.getZ())) {
                links.add(node);
            } else {
                nodes.add(node);
            }
        }
        map.put("rackid", id);
        map.put("nodes", nodes);
        map.put("links", links);
        return "idclink/index";
    }
    @RequestMapping("/idclink/rack/tree/{rackId}")
    public String getResourceTreeMap(@PathVariable Long rackId, ModelMap map){
        Map<String,Object> results = idcLinkService.getRackResourceTree(rackId);
        map.put("rackid",rackId);
        map.put("nodes", results.get("nodes"));
        map.put("links", results.get("links"));
        return "idclink/showRackResourceTree";
    }

    /*资源分配-获取相关节点及链路关系*/
    @RequestMapping("/idclink/{type}")
    public String getTreeToMap1(@PathVariable String type, String ids, Long roomId, String flag, ModelMap map) {
        Map<String,Object> results = idcLinkService.getDistributionTree(type, ids, roomId);
        map.put("rackid", ids);
        map.put("roomId", roomId);
        map.put("flag", flag);
        map.put("nodes", results.get("nodes"));
        map.put("links", results.get("links"));
        return "idclink/distributionIndex";
    }

    /*资源分配-准备查看链路信息*/
    @RequestMapping("/idclink/getPreLinksByAZ")
    public String getPreLinksByAZ(String aKey, String zKey, String portStr,Long wlRackId, ModelMap map) {
        map.addAttribute("aKey", aKey);
        map.addAttribute("zKey", zKey);
        map.addAttribute("portStr", portStr);
        map.addAttribute("wlRackId", wlRackId);
        return "idclink/linkInfo";
    }

    /*资源分配-查看链路信息*/
    @RequestMapping("/idclink/getLinksByAZ")
    @ResponseBody
    public List<Map<String, Object>> getLinksByAZ(String aKey, String zKey, String name, String portStr,Long wlRackId) {
        return idcLinkService.queryLinksByAZ(aKey, zKey, name, portStr,wlRackId);
    }

    /*根据A端信息获取Z端信息*/
    @RequestMapping("/idclink/getIdcLinkInfo")
    @ResponseBody
    public Map<String, Object> getIdcLinkInfo(Long id) {
        Map<String, Object> map = new HashedMap();
        IdcLink idcLink = new IdcLink();
        idcLink.setAendportId(id);
        List<Map<String, Object>> idcLinks = idcLinkService.queryListByObjectMap(idcLink);
        if (idcLinks != null && idcLinks.size() > 0) {
            Map<String, Object> obj = idcLinks.get(0);
            if (obj.get("ZDEVICENAME") != null && !"".equals(obj.get("ZDEVICENAME"))) {//端口
                map.put("obj", obj);
                map.put("type", "netPort");
            } else {//端子
                map.put("obj", obj);
                map.put("type", "idcConnector");
            }
        }
        return map;
    }

    /*跟据端子ID，获取上行和下行端口信息*/
    @RequestMapping("/idclink/queryUpAndDownPortInfo")
    @ResponseBody
    public Map<String,Object> queryUpAndDownPortInfo(Long id){
        Map<String,Object> resultMap = idcLinkService.queryUpAndDownPortInfo(id);
        return resultMap;
    }

    //删除链路信息
    @RequestMapping("/idclink/delete")
    @ResponseBody
    public ExecuteResult deleteIdcLink() {
        ExecuteResult executeResult = new ExecuteResult();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        List linkIdList = (List) session.getAttribute("linkIdList");
        if (linkIdList != null) {
            try {
                //idcLinkService.deleteByList(linkIdList);
                idcLinkService.unBindConnectToLink(linkIdList);
                session.setAttribute("linkIdList",null);
                executeResult.setState(true);
                executeResult.setMsg("删除成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("删除失败");
                e.printStackTrace();
            }
        }else{
            executeResult.setState(true);
        }
        return executeResult;
    }

    //确认建立  清楚session
    @RequestMapping("/idclink/cleanSession")
    @ResponseBody
    public ExecuteResult cleanSessionLinkIdList() {
        ExecuteResult executeResult = new ExecuteResult();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //清除session中linkIdList
        session.setAttribute("linkIdList",null);
        executeResult.setState(true);
        return executeResult;
    }
    /*通过AZ端端口或ids 如：2424_32442 删除链路信息*/
    @RequestMapping("/idclink/deleteLinkByParams")
    @ResponseBody
    public ExecuteResult deleteLinkByParams(String portStr,String ids){
        ExecuteResult executeResult = new ExecuteResult();
        try{
            if(portStr!=null&&!"".equals(portStr)){//AZ端口删除
                List<String> portStrList = Arrays.asList(portStr.replace("null","").split(","));
                idcLinkService.deleteLinkByAZPortStrList(portStrList);
            }
            if(ids!=null&&!"".equals(ids)){//IDS删除
                List<String> idList = Arrays.asList(ids.replace("null","").split(","));
                idcLinkService.deleteByList(idList);
            }
            executeResult.setState(true);
            executeResult.setMsg("删除成功");
        }catch(Exception e){
            executeResult.setState(false);
            executeResult.setMsg("删除失败");
            e.printStackTrace();
        }
        return executeResult;
    }
}
