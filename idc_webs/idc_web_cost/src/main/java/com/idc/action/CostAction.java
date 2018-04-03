package com.idc.action;

import com.idc.model.*;
import com.idc.service.CostService;
import com.idc.vo.CostRackFullVO;
import com.idc.vo.CostRoomVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import utils.typeHelper.MapHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 成本分析
 *
 * @author mylove
 *         Created by mylove on 2017/10/25.
 */
@Controller
public class CostAction {
    private static final Logger logger = LoggerFactory.getLogger(CostAction.class);
    @Autowired
    private CostService costService;

    /***
     * 机楼成本
     *
     * @return
     */
    @RequestMapping("/cost/building")
    public String Building() {
        return "cost/building";
    }

    /***
     * 机房成本
     *
     * @return
     */
    @RequestMapping("/cost/room")
    public String Room() {
        return "cost/room";
    }

    /***
     * 机架成本
     *
     * @return
     */
    @RequestMapping("/cost/rack")
    public String Rack() {
        return "cost/rack";
    }

    @RequestMapping("/cost/customer")
    public String Coutomer() {
        return "cost/customer";
    }

    /***
     * 机架成本
     *
     * @return
     */
    @RequestMapping("/cost/getByRack/{rackid}")
    @ResponseBody
    public CostRackFullVO getByRack(@PathVariable long rackid, String time) {
        CostRackFullVO costByRack = costService.getCostByRack(rackid, time);
        CostRackFullVO costByRacktmp = costService.getInCome(rackid, time);
        if (costByRacktmp != null) {
            costByRack.setBandwidthIncome(costByRacktmp.getBandwidthIncome());
            costByRack.setCostRockIncome(costByRacktmp.getCostRockIncome());
        }
        return costByRack;
    }

    @RequestMapping("/cost/getByBuild/{buildid}")
    @ResponseBody
    public Map<String, Object> getByBuild(@PathVariable String buildid) {
//        buildid = 3307L;
        Map<String, Object> results = new HashMap<>();
        CostFixed costFixed = costService.getCostFixedByBuild(buildid);
        if (costFixed == null) {
            costFixed = new CostFixed();
            costFixed.setCostFixedType("3");
            costFixed.setObjectCode(buildid + "");
        }
        if (costFixed != null) {
            Map<String, Object> objectMap = MapHelper.beanToMap(costFixed);
            results.putAll(objectMap);
        }
        return results;
    }

    @RequestMapping("/cost/getDynamicByBuild/{fixedId}")
    @ResponseBody
    public Map<String, Object> getDynamicByBuild(@PathVariable String fixedId, String time) {
//        buildid = 3307L;
        Map<String, Object> results = new HashMap<>();
        CostDynamic costFixed = costService.getDynamicByBuild(fixedId, time);
        if (costFixed == null) {
            costFixed = new CostDynamic();
            costFixed.setCostFixedId(fixedId + "");
        }
        if (costFixed != null) {
            Map<String, Object> objectMap = MapHelper.beanToMap(costFixed);
            results.putAll(objectMap);
            String costRoomId = costFixed.getCostRoomId();
            CostAnalysisRoom costAnalysisRoom = costService.getAnalyByRoom(costRoomId);
            if (costAnalysisRoom == null) {
                costAnalysisRoom = new CostAnalysisRoom();
                costAnalysisRoom.setCostRoomId(costRoomId);
            }
            results.putAll(MapHelper.beanToMap(costAnalysisRoom));
        }
        return results;
    }

    /***
     * 机架成本
     *
     * @return
     */
    @RequestMapping("/cost/getByRoom/{roomid}")
    @ResponseBody
    public CostRoomVO getByRoom(@PathVariable long roomid) {
        roomid = 3307L;
        return costService.getCostByRoom(roomid);
    }

    /***
     * 保存固定成本信息
     *
     * @param costFixedEntity
     * @return
     */
    @RequestMapping("/cost/saveBuildFixed")
    @ResponseBody
    public ExecuteResult saveBuildFixed(CostFixed costFixedEntity) {
        return costService.saveCostFixed(costFixedEntity);
    }

    @RequestMapping("/cost/saveBuildDyn")
    @ResponseBody
    public ExecuteResult saveBuildDyn(CostDynamic costDynamic, CostAnalysisRoom analysisRoom) {
        return costService.saveBuildDyn(costDynamic, analysisRoom);
    }

    @RequestMapping("/cost/saveRackDyn")
    @ResponseBody
    public ExecuteResult saveRackDyn(String time, double costEnergy, double bandwidthIncome, double costRockIncome, long rackid) {
        CostAnalysisRack costAnalysisRack = new CostAnalysisRack();
        costAnalysisRack.setCostRackPowerFee(new BigDecimal(costEnergy));
        costAnalysisRack.setBandwidthIncome(new BigDecimal(bandwidthIncome));
        costAnalysisRack.setCostRockIncome(new BigDecimal(costRockIncome));
        return costService.saveRackDyn(rackid, time, costAnalysisRack);
    }

    /***
     * 机架成本
     *
     * @return
     */
    @RequestMapping("/cost/getByCustomer/{rackid}")
    @ResponseBody
    public CostRackFullVO getByCustomer(@PathVariable long rackid, String time) {
        return costService.getByCustomer(rackid, time);
    }

    @RequestMapping("/cost/saveCostCustomer")
    @ResponseBody
    public ExecuteResult saveCostCustomer(String time, double bandwidthIncome, double costRockIncome, long customId) {
        CostAnalysisRack costAnalysisRack = new CostAnalysisRack();
        costAnalysisRack.setBandwidthIncome(new BigDecimal(bandwidthIncome));
        costAnalysisRack.setCostRockIncome(new BigDecimal(costRockIncome));
        return costService.saveCostCustomer(customId, time, costAnalysisRack);
    }
}
