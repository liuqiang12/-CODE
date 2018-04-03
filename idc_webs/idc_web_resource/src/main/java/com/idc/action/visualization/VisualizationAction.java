package com.idc.action.visualization;

import com.google.common.base.Function;
import com.google.common.collect.*;
import com.idc.model.IdcBuilding;
import com.idc.model.ZbMachineroom;
import com.idc.service.IdcBuildingService;
import com.idc.service.ZbMachineroomService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/9/25.
 */
@Controller
public class VisualizationAction {
    @Autowired
    private IdcBuildingService buildingService;
    @Autowired
    private ZbMachineroomService zbMachineroomService;
    @RequestMapping("/visualization/index")
    public String index() {
        return "visualization/index";
    }

    /***
     * 0西区移动 1gaoxing 2shuangliu
     * @param index
     * @return
     */
    @RequestMapping("/visualization/build/{index}")
    public String build(@PathVariable int index,ModelMap map) {
        IdcBuilding query = new IdcBuilding();
        switch (index) {
            case 0:
                query.setLocationid(1261);
                break;
            case 1:
                query.setLocationid(1260);
                break;
            case 2:
                query.setLocationid(1259);
                break;
            default:
                break;
        }
        List<IdcBuilding> idcBuildings = buildingService.queryListByObject(query);
        List<Map<BigDecimal, BigDecimal>> roomByIds = buildingService.getRoomByIds(idcBuildings);
        ImmutableMap<BigDecimal, Map<BigDecimal, BigDecimal>> buildMap = Maps.uniqueIndex(roomByIds, new Function<Map<BigDecimal, BigDecimal>, BigDecimal>() {
            @Override
            public BigDecimal apply(Map<BigDecimal, BigDecimal> longLongMap) {
                return longLongMap.get("BUILDID");
            }
        });
        List<JSONObject> builds = new ArrayList<>();
        JSONObject build;
        for (IdcBuilding idcBuilding : idcBuildings) {
            build =new JSONObject();
            build.put("id",idcBuilding.getId());
            build.put("name",idcBuilding.getName());
            build.put("rackcount",idcBuilding.getRackcount());
            build.put("rackusage",idcBuilding.getRackusage());
            Map<BigDecimal, BigDecimal> longLongMap = buildMap.get(new BigDecimal(idcBuilding.getId()));
            build.put("roomcount", longLongMap==null?0:longLongMap.get("ROOMCOUNT").toString());
            builds.add(build);
        }
        map.put("builds",builds);
        return "visualization/build";
    }
    @ResponseBody
    @RequestMapping("/visualization/getRooms/{buildid}")
    public Map<String, List<ZbMachineroom>> getRooms(@PathVariable String buildid){
        ZbMachineroom query = new ZbMachineroom();
        query.setBuildingid(buildid);
        List<ZbMachineroom> zbMachinerooms = zbMachineroomService.queryListByObject(query);
        Map<String,List<ZbMachineroom>> floars =new HashMap<>();
        for (ZbMachineroom zbMachineroom : zbMachinerooms) {
            String sitename = zbMachineroom.getSitename();
            String[] split = sitename.split("-");
            String s = split[split.length - 1];
            String substring = sitename.substring(sitename.length() - 3);
            char floar = sitename.charAt(sitename.length() - 3);
            String floarstr = sitename.substring(sitename.length() - 3, sitename.length()-1);
            String floarName= "其他";
            if(NumberUtils.isNumber(s)){
                floarName = String.valueOf(s.charAt(0));
            }
            List<ZbMachineroom> zbMachinerooms1 = floars.get(floarName);
            if(zbMachinerooms1==null){
                zbMachinerooms1=new ArrayList<>();
                floars.put(floarName, zbMachinerooms1);
            }
            zbMachinerooms1.add(zbMachineroom);
        }
//        List<JSONObject> jsons = new ArrayList<>();
//        for (String s : floars.keySet()) {
//            jsons.add(JSONObject.fromObject(floars.get(s)));
//        }
        return floars;
    }
}
