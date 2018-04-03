package com.idc.action;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.model.*;
import com.idc.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * topo视图
 * Created by mylove on 2017/7/25.
 */
@RequestMapping("/topoview")
@Controller
public class TopoViewAction {
    private static final Logger logger = LoggerFactory.getLogger(TopoViewAction.class);
    @Autowired
    private NetTopoviewService netTopoviewService;
    @Autowired
    private NetTopoviewobjService netTopoviewobjService;
    @Autowired
    private NetTopoObjLinkService netTopoObjLinkService;
    @Autowired
    private NetPortService netPortService;
    @Autowired
    private NetDeviceService netDeviceService;
    @RequestMapping("/")
    public String index(ModelMap map) {
        return index(141,map);
//        "topoview/index";
    }

    /***
     * 查看某一视图
     * @param viewid
     * @return
     */
    @RequestMapping("/view/{viewid}")
    public String index(@PathVariable long viewid,ModelMap map) {
        NetTopoView modelById = netTopoviewService.getModelById(viewid);
        map.put("viewobj",modelById);
        map.put("typeMode","view");
        map.put("viewid",viewid);
        return "topoview/index";
    }

    /***
     * 编辑某一视图 进入编辑模式
     * @param viewid
     * @return
     */
    @RequestMapping("/edit/{viewid}")
    public String edit(@PathVariable long viewid,ModelMap map) {
        NetTopoView modelById = netTopoviewService.getModelById(viewid);
        map.put("viewobj",modelById);
        map.put("typeMode","edit");
        return "topoview/edittopo";
    }
    @RequestMapping("/toViewForm")
    public String toViewForm() {
        return "topoview/addnode";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<NetTopoView> list() {
        List<NetTopoView> netTopoViews = netTopoviewService.queryList();
        return netTopoViews;
    }
    /***
     * 更具Id 获取场景的节点和连线
     *
     * @param viewid
     * @return
     */
    @RequestMapping("/viewinfo/{viewid}")
    @ResponseBody
    public Map<String, Object> getViewInfoById(@PathVariable long viewid) {
        NetTopoView netTopoView = netTopoviewService.getModelDetailById(viewid);
        Map<String, Object> map = new HashMap<>();

        List<NetDevice> netDevices = netDeviceService.queryList();
        ImmutableMap<Long, NetDevice> deviceMap = Maps.uniqueIndex(netDevices, new Function<NetDevice, Long>() {
            @Override
            public Long apply(NetDevice netDevice) {
                return netDevice.getDeviceid();
            }
        });
        List<JSONObject> list = new ArrayList<>();
        for (NetTopoViewObj netTopoViewObj : netTopoView.getViewObjs()) {
            JSONObject jsonObject = JSONObject.fromObject(netTopoViewObj);
            list.add(jsonObject);
            NetDevice netDevice = deviceMap.get(netTopoViewObj.getObjfid());
            if(netDevice!=null){
                jsonObject.put("deviceType",netDevice.getDeviceclass()+"_"+netDevice.getRouttype());
            }
        }
        map.put("viewObjs", list);
        map.put("viewinfo", netTopoView);
        map.put("links", netTopoView.getViewLinks());
        return map;
    }
    /***
     * 更具Id 获取场景的节点和连线
     * 流量统计
     * @param viewid
     * @return
     */
    @RequestMapping("/{viewid}")
    @ResponseBody
    public Map<String, Object> getById(@PathVariable long viewid) {
        NetTopoView netTopoView = netTopoviewService.getModelDetailById(viewid);
//        List<NetTopoObjLinkFlow> links = netTopoviewService.getFlowByLinks(netTopoView.getViewLinks());
        List<NetTopoObjLinkFlow> links = null;
        try {
            links = netTopoviewService.getFlow(netTopoView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<NetDevice> netDevices = netDeviceService.queryList();
        ImmutableMap<Long, NetDevice> deviceMap = Maps.uniqueIndex(netDevices, new Function<NetDevice, Long>() {
            @Override
            public Long apply(NetDevice netDevice) {
                return netDevice.getDeviceid();
            }
        });
        Map<String, Object> map = new HashMap<>();
        List<JSONObject> list = new ArrayList<>();
        for (NetTopoViewObj netTopoViewObj : netTopoView.getViewObjs()) {
            JSONObject jsonObject = JSONObject.fromObject(netTopoViewObj);
            list.add(jsonObject);
            NetDevice netDevice = deviceMap.get(netTopoViewObj.getObjfid());
            if(netDevice!=null){
                jsonObject.put("deviceType",netDevice.getDeviceclass()+"_"+netDevice.getRouttype());
            }
        }
        map.put("viewinfo", netTopoView);
        map.put("viewObjs", list);
//        map.put("objs",netTopoView.getViewObjs());
        map.put("links", links);
        return map;
    }

    /***
     * 保存视图
     *
     * @param topoview
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ExecuteResult add(NetTopoView topoview, HttpServletRequest request) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            Integer userid = (Integer) request.getSession().getAttribute("userid");
            if (userid == null) {
                executeResult.setMsg("没有找到登录用户信息 ");
                return executeResult;
            }
            NetTopoView query = new NetTopoView();
            query.setViewname(topoview.getViewname());
            NetTopoView queryBean = netTopoviewService.getModelByObject(query);
            if (queryBean != null) {
                executeResult.setMsg("名字重复");
                return executeResult;
            }
            topoview.setUserid(Long.parseLong(userid.toString()));
            netTopoviewService.insert(topoview);
            executeResult.setMsg(topoview.getViewid().toString());
            executeResult.setState(true);
        } catch (Exception e) {
            logger.error("", e);
            executeResult.setMsg("添加TOPO视图失败");
        }
        return executeResult;
    }

    /***
     * 创建节点
     *
     * @param obj
     * @return
     */
    @RequestMapping("/addObj")
    @ResponseBody
    public ExecuteResult addObj(NetTopoViewObj obj) {
        ExecuteResult executeResult = netTopoviewobjService.add(obj);
        return executeResult;
    }

    /***
     * 创建链接关系
     *
     * @param ida
     * @param idz
     * @return
     */
    @RequestMapping("/addLink/{viewId}/{ida}/{idz}")
    public String addLink(@PathVariable long viewId, @PathVariable long ida, @PathVariable long idz, ModelMap map) {
        NetTopoViewObj nodeA = netTopoviewobjService.getModelById(ida);
        NetTopoViewObj nodeZ = netTopoviewobjService.getModelById(idz);
        map.put("nodea", nodeA);
        map.put("nodez", nodeZ);
        return "topoview/addlink";
    }

    /***
     * 创建链接关系
     *
     * @return
     */
    @RequestMapping(value = "/addLink", method = RequestMethod.POST)
    @ResponseBody
    public ExecuteResult addLink(NetTopoObjLink link) {
        return netTopoObjLinkService.add(link);
    }

    /***
     * 删除节点
     *
     * @param type 0 节点 1 连线 2 视图
     * @param id
     * @return
     */
    @RequestMapping("/del/{type}/{id}")
    @ResponseBody
    public ExecuteResult delObj(@PathVariable long type, @PathVariable long id) {
        return new ExecuteResult();
    }

    @RequestMapping("/addViewObj/{viewId}/{x}/{y}")
    public String addViewObj(@PathVariable long viewId, @PathVariable double x, @PathVariable double y) {
        return "topoview/addtmpnode";
    }

    @RequestMapping("/save/{viewId}")
    @ResponseBody
    public ExecuteResult saveView(@PathVariable long viewId, String topoStr) {
        JSONArray jsonArray = JSONArray.fromObject(topoStr);
        List<NetTopoViewObj> nodes = getNode(jsonArray);
        List<NetTopoObjLink> links = getLink(jsonArray);
        ExecuteResult executeResult = new ExecuteResult();
        try {
            netTopoviewService.save(viewId, nodes, links);
            executeResult.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            executeResult.setMsg("保存失败");
        }
        return executeResult;
    }

    private List<NetTopoObjLink> getLink(JSONArray jsonArray) {
        List<NetTopoObjLink> links = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonobj = (JSONObject) obj;
            String srcA = (String) jsonobj.get("srca");
            if (srcA != null) {//节点
                String srcZ = (String) jsonobj.get("srcz");
                NetTopoObjLink netTopoObjLink = new NetTopoObjLink();
                netTopoObjLink.setSrcid(srcA);
                netTopoObjLink.setDesid(srcZ);
                links.add(netTopoObjLink);

            }
        }
        return links;
    }

    private List<NetTopoViewObj> getNode(JSONArray jsonArray) {
        List<NetTopoViewObj> objs = new ArrayList<>();
        for (Object obj : jsonArray) {
            JSONObject jsonobj = (JSONObject) obj;
            String name = (String) jsonobj.get("name");
            if (name != null) {//节点
                NetTopoViewObj netTopoViewObj = new NetTopoViewObj();
                Integer objtype = (Integer) jsonobj.get("objtype");
                Integer id = (Integer) jsonobj.get("id");
                Double x = Double.parseDouble(jsonobj.get("x").toString());
                Double y = Double.parseDouble(jsonobj.get("y").toString());
                netTopoViewObj.setName(name);
                netTopoViewObj.setObjfid(Long.parseLong(id.toString()));
                netTopoViewObj.setObjtype(Long.parseLong(objtype.toString()));
                netTopoViewObj.setName(name);
                netTopoViewObj.setViewposition(x + "_" + y);
                objs.add(netTopoViewObj);
            }
        }
        return objs;
    }

    @RequestMapping("/objs/{type}")
    @ResponseBody
    public EasyUIData objs(EasyUIPageBean page, @PathVariable int type, String q) {
        EasyUIData easyUIData = new EasyUIData();
        List list = netTopoviewService.getObjs(page, type, q);
        easyUIData.setTotal(list.size());
        easyUIData.setRows(list);
        return easyUIData;
    }

    @RequestMapping("/ports/{deviceId}")
    @ResponseBody
    public List<Map<String, Object>> list(String name, @PathVariable Long deviceId) {
        NetPort netProt = new NetPort();
        netProt.setPortname(name);
        netProt.setDeviceid(deviceId);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = netPortService.queryListByObjectMap(netProt);
        List<NetTopoObjLink> netTopoObjLinks = netTopoObjLinkService.queryList();
        Set<String> names = new HashSet<>();
        for (NetTopoObjLink netTopoObjLink : netTopoObjLinks) {
            names.add(netTopoObjLink.getSrcid());
            names.add(netTopoObjLink.getDesid());
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> objectMap : list) {
//            String deviceid = objectMap.get("DEVICEID").toString();
            String portid = objectMap.get("PORTID").toString();
            if (!names.contains(portid)) {
                result.add(objectMap);//排除已经添加的端口
            }
        }
        return result;
    }

    @RequestMapping("/updateNodeLocation/{id}")
    @ResponseBody
    public ExecuteResult updateNodeLocation(@PathVariable long id, String pos) {
        NetTopoViewObj modelById = netTopoviewobjService.getModelById(id);
        ExecuteResult executeResult = new ExecuteResult();
        modelById.setViewposition(pos);
        try {
            netTopoviewobjService.updateByObject(modelById);
            executeResult.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            executeResult.setMsg("更新失败");
        }
        return executeResult;
    }

    /***
     * 查看链路信息 主要是下面真实链路信息 流量之类的
     *
     * @param
     * @return
     */
    @RequestMapping("/showlink/{src}/{des}")
    public String showLink(@PathVariable long src, @PathVariable long des, ModelMap map) {
        List<NetTopoObjLinkFlow> srlinkFlows = netTopoviewService.getLinkFlow(src, des);
        List<NetTopoObjLinkFlow> dsLinks = netTopoviewService.getLinkFlow(des, src);
        srlinkFlows.addAll(dsLinks);
        map.put("links", srlinkFlows);
        return "topoview/showlink";
    }
    @RequestMapping("/deletelink/{linkid}")
    @ResponseBody
    public ExecuteResult deleteLink(@PathVariable long linkid) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            netTopoObjLinkService.deleteById(linkid);
            executeResult.setMsg(linkid+"");
            executeResult.setState(true);
        } catch (Exception e) {
            executeResult.setMsg("删除失败");
            logger.error("", e);
        }
        return executeResult;
    }
    @RequestMapping("/deleteobj/{objid}")
    @ResponseBody
    public ExecuteResult deleteObj(@PathVariable long objid) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            netTopoviewobjService.deleteById(objid);
            executeResult.setMsg(objid+"");
            executeResult.setState(true);
        } catch (Exception e) {
            executeResult.setMsg("删除失败");
            logger.error("", e);
        }
        return executeResult;
    }
    @RequestMapping("/deletelink")
    @ResponseBody
    public ExecuteResult deleteLink(Long srcportname, String desportname) {
        ExecuteResult executeResult = new ExecuteResult();
//        NetTopoObjLink netTopoObjLink = new NetTopoObjLink();
//        netTopoObjLink.setTopoLinkID(srcportname);
////        netTopoObjLink.setDesportname(desportname);
//        NetTopoObjLink modelByObject = netTopoObjLinkService.getModelByObject(netTopoObjLink);
//        if (modelByObject == null) {
//            netTopoObjLink.setDesportname(srcportname);
//            netTopoObjLink.setSrcportname(desportname);
//            modelByObject = netTopoObjLinkService.getModelByObject(netTopoObjLink);
//        }
//        if (modelByObject == null) {
//            executeResult.setMsg("没有找到链路");
//            return executeResult;
//        }
        try {
            netTopoObjLinkService.deleteById(srcportname);
            executeResult.setMsg(srcportname.toString());
            executeResult.setState(true);

        } catch (Exception e) {
            executeResult.setMsg("删除失败");
            logger.error("", e);
        }
        return executeResult;
    }

//    /***
//     * 查看链路信息 主要是下面真实链路信息 流量之类的
//     *
//     * @param linkId
//     * @return
//     */
//    @RequestMapping("/showFlow/{linkid}")
//    public String showFlow(@PathVariable long linkId) {
//        return "topoview/showFlow";
//    }
    @RequestMapping("/showChildTopo/{viewid}")
    public String showChildTopo(@PathVariable long viewid,ModelMap map){
        NetTopoView modelById = netTopoviewService.getModelById(viewid);
        map.put("viewobj",modelById);
        map.put("typeMode","childtopo");
        return "topoview/index";
    }
}
