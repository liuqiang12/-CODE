package com.idc.action.ipsubnet;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcIpSubnet;
import com.idc.model.IdcSubnetCountVo;
import com.idc.service.IdcIpsubnetService;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by mylove on 2017/6/16.
 */
@Controller
@RequestMapping("/resource/ipsubnet")
public class IpSubnetAction {
    @Autowired
    private IdcIpsubnetService idcIpsubnetService;
    private static final Log logger = LogFactory.getLog(IpSubnetAction.class);

    @RequestMapping("/")
    public String index() {
        return "ipsubnet/index";
    }

    /***
     * 流程分配
     * @return
     */
    @RequestMapping("/flowallc")
    public String flowAllc(ModelMap map) {
        map.put("actiontype","flowallc");
        return "ipsubnet/index";
    }
    @RequestMapping("/{id}")
    public String form(@PathVariable long id, ModelMap modelMap) {
        IdcIpSubnet idcIpSubnet = new IdcIpSubnet();
        if (id > 0) {
            idcIpSubnet = idcIpsubnetService.getModelById(id);
        }
        modelMap.put("idcIpSubnet", idcIpSubnet);
        modelMap.put("id", id);
        return "ipsubnet/info";
    }

    /**
     * 查询IDC机楼
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page,String actionType, IdcIpSubnet idcIpSubnet) {
        long time = System.currentTimeMillis();
        EasyUIData easyUIData = new EasyUIData();
        List<IdcIpSubnet> idcIpSubnets = idcIpsubnetService.queryListAndCountPage(page, idcIpSubnet);
        List<IdcSubnetCountVo> countByState = idcIpsubnetService.getCountByState();
        ImmutableMap<Long, IdcSubnetCountVo> usageMap = Maps.uniqueIndex(countByState, new Function<IdcSubnetCountVo, Long>() {
            @Override
            public Long apply(IdcSubnetCountVo idcSubnetCountVo) {
                return idcSubnetCountVo.getId();
            }
        });
        for (IdcIpSubnet ipSubnet : idcIpSubnets) {
            try {
                net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(ipSubnet);
                jsonObject.put("free",usageMap.get(ipSubnet.getId()).getFree());
                jsonObject.put("allip",usageMap.get(ipSubnet.getId()).getAllip());
                jsonObject.put("preused",usageMap.get(ipSubnet.getId()).getPreused());
                jsonObject.put("recovery",usageMap.get(ipSubnet.getId()).getRecovery());
                jsonObject.put("used",usageMap.get(ipSubnet.getId()).getUsed());
                easyUIData.getRows().add(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("",e);
            }
        }
        easyUIData.setTotal(page.getTotalRecord());
//        easyUIData.setRows(page.getItems());
        return easyUIData;
    }

    @RequestMapping("/save")
    @ResponseBody
    public ExecuteResult save(IdcIpSubnet subnet, ModelMap modelMap) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            return  idcIpsubnetService.insertOrUpdate(subnet);
        } catch (Exception e) {
            logger.error("", e);
            executeResult.setMsg("添加子网失败");
        }

        return executeResult;
    }

    @RequestMapping("/del")
    @ResponseBody
    public ExecuteResult del(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            if (ids == null || "".equals(ids)) {
                executeResult.setMsg("传入ID错误");
                return executeResult;
            }
            idcIpsubnetService.deleteByList(Arrays.asList(ids.split(",")));
            executeResult.setState(true);
        } catch (Exception e) {
            logger.error("", e);
            executeResult.setMsg("删除子网失败");
        }

        return executeResult;
    }

    @RequestMapping("/splitsubnet/{id}")
    public String splitsubnet(@PathVariable long id, ModelMap map) {
        IdcIpSubnet idcIpSubnet = idcIpsubnetService.getModelById(id);
        map.put("idcIpSubnet", idcIpSubnet);
        return "ipsubnet/split";
    }

    /***
     * 合并网段
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/mergesubnet/{id}")
    public String mergesubnet(@PathVariable String id, ModelMap map) {
        String[] rows = id.split(",");
        if(rows.length>0){
            IdcIpSubnet idcIpSubnet = idcIpsubnetService.getModelById(Long.parseLong(rows[0]));
            map.put("idcIpSubnet", idcIpSubnet);
            if(idcIpSubnet.getPid()==null){
                map.put("error", "选择的网段"+idcIpSubnet.getSubnetip()+"无法合并");
                return "error";
            }
            IdcIpSubnet parent = idcIpsubnetService.getModelById(idcIpSubnet.getPid());
            IdcIpSubnet tmp= new IdcIpSubnet();
            tmp.setPid(parent.getId());//获取当前子网的所有下级子网
            List<IdcIpSubnet> idcIpSubnets = idcIpsubnetService.queryListByObject(tmp);
            map.put("parent", parent);
            map.put("rownum", rows.length);
            map.put("idcIpSubnets", idcIpSubnets);
        }else{
            map.put("error", "没有选择合并的网段");
            return "error";
        }
        return "ipsubnet/merge";
    }

    @RequestMapping("/savesplit")
    @ResponseBody
    public ExecuteResult saveSplit(long pid, String rows, ModelMap map) {
        JSONArray jArray= JSONArray.fromObject(rows);
        Collection collection = JSONArray.toCollection(jArray, IdcIpSubnet.class);
        List<IdcIpSubnet> subnetips = new ArrayList<IdcIpSubnet>();
        subnetips.addAll(collection);
        ExecuteResult executeResult = idcIpsubnetService.split(pid,subnetips);
        return  executeResult;
    }
    @RequestMapping("/savemerger")
    @ResponseBody
    public ExecuteResult savemerger(IdcIpSubnet idcIpSubnet,String delNetSegs,String updateIds,String targetNetSeg, ModelMap map) {
//        JSONArray jArray= JSONArray.fromObject(rows);
//        Collection collection = JSONArray.toCollection(jArray, IdcIpSubnet.class);
//        List<IdcIpSubnet> subnetips = new ArrayList<IdcIpSubnet>();
//        subnetips.addAll(collection);
        ExecuteResult executeResult = idcIpsubnetService.merger(idcIpSubnet, delNetSegs,updateIds,targetNetSeg);
        return  executeResult;
    }
    @RequestMapping("/checkMer")
    @ResponseBody
    public ExecuteResult checkMerger(String ids){
        ExecuteResult executeResult = new ExecuteResult();
        List<IdcSubnetCountVo> countByState = idcIpsubnetService.getCountByState();
        ImmutableMap<Long, IdcSubnetCountVo> usageMap = Maps.uniqueIndex(countByState, new Function<IdcSubnetCountVo, Long>() {
            @Override
            public Long apply(IdcSubnetCountVo idcSubnetCountVo) {
                return idcSubnetCountVo.getId();
            }
        });
        IdcIpSubnet idcIpSubnet = new IdcIpSubnet();
        EasyUIPageBean page =new EasyUIPageBean(1,20);
        for (String s : ids.split(",")) {
            IdcSubnetCountVo idcSubnetCountVo = usageMap.get(Long.parseLong(s));
            if(idcSubnetCountVo!=null){
                if(idcSubnetCountVo.getAllip()!=idcSubnetCountVo.getFree()){
                    return executeResult;
                }
            }
        }
        executeResult.setState(true);
        return executeResult;
    }
}
