package com.idc.service.impl;

import com.idc.bo.IdcPdfDayPowerInfoVo;
import com.idc.mapper.*;
import com.idc.model.*;
import com.idc.service.CostService;
import com.idc.service.IdcRackService;
import com.idc.vo.CostRackFullVO;
import com.idc.vo.CostRoomVO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import system.Global;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;

/**
 * @author mylove
 *         Created by mylove on 2017/10/27.
 */
@Service
public class CostServiceImpl implements CostService {

    @Value(value = "${ELECTRICITY_TARIFF}")
    private Double ELECTRICITY_TARIFF=0D;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CostMapper mapper;
    @Autowired
    private CostFixedMapper costFixedMapper;
    @Autowired
    private CostDynamicMapper costDynamicMapper;
    @Autowired
    private CostAnalysisRoomMapper costAnalysisRoomMapper;
    @Autowired
    private CostAnalysisRackMapper costAnalysisRackMapper;
    @Autowired
    private CostRackBaseMapper costRackBaseMapper;
    @Autowired
    private IdcPdfDayPowerInfoMapper idcPdfDayPowerInfoMapper;
    @Autowired
    private IdcRackService idcRackService;

    @Override
    public CostRackFullVO getCostByRack(long rackid, String time) {
        logger.info("电价{}",ELECTRICITY_TARIFF);
        CostRackFullVO costRackVO = new CostRackFullVO();
        String buildingid = mapper.getBuilding(rackid);
        CostFixed costFixedByBuild = getCostFixedByBuild(buildingid);
        //获取 机楼成本信息 通过机楼成本/分摊机架数 得到机架成本信息
        if (costFixedByBuild != null) {
            BigDecimal costShareRackCount = costFixedByBuild.getCostShareRackCount();
            if (costShareRackCount.intValue() > 0) {
                String costFixedId = null;
                if (costFixedByBuild != null) {
                    costFixedId = costFixedByBuild.getCostFixedId();
                    costRackVO.setCostBuildingMonth(costFixedByBuild.getCostBuildingMonth().divide(costShareRackCount, 2, RoundingMode.UP));
                    costRackVO.setCostBaseDevMonth(costFixedByBuild.getCostBaseDevMonth().divide(costShareRackCount, 2, RoundingMode.UP));
                    costRackVO.setCostItMonth(costFixedByBuild.getCostItMonth().divide(costShareRackCount, 2, RoundingMode.UP));

                    CostDynamic dynamicByBuild = getDynamicByBuild(costFixedId, time);
                    //电有自己的逻辑(电量*电费)
                    if (dynamicByBuild != null) {
                        costRackVO.setCostWaterRate(dynamicByBuild.getCostWaterRate().divide(costShareRackCount, 2, RoundingMode.UP));
                        costRackVO.setCostRepair(dynamicByBuild.getCostRepair().divide(costShareRackCount, 2, RoundingMode.UP));
                        costRackVO.setCostMaintenance(dynamicByBuild.getCostMaintenance().divide(costShareRackCount, 2, RoundingMode.UP));
                        costRackVO.setCostWork(dynamicByBuild.getCostWork().divide(costShareRackCount, 2, RoundingMode.UP));
                        costRackVO.setCostMarketting(dynamicByBuild.getCostMarketting().divide(costShareRackCount, 2, RoundingMode.UP));
                    }
                }
            }
        }
        Calendar calendar = Calendar.getInstance();
        try {
            //获取电量*电价信息
            Date date = DateUtils.parseDate(time, "yyyy-MM");
            calendar.setTime(date);
            int maximum = calendar.getLeastMaximum(Calendar.DAY_OF_MONTH);
            List<IdcPdfDayPowerInfoVo> hisMonthBtTime = idcPdfDayPowerInfoMapper.getHisMonthBtTime(rackid, time + "-01", time + "-" + maximum);
            if (hisMonthBtTime.size() > 0) {
                double costEnergy = 0D;
                for (IdcPdfDayPowerInfoVo idcPdfDayPowerInfoVo : hisMonthBtTime) {
                    costEnergy += idcPdfDayPowerInfoVo.getIdcBeforeDiff() * ELECTRICITY_TARIFF;
                }
                costRackVO.setCostEnergy(new BigDecimal(costEnergy / 10000).divide(new BigDecimal(1), 3, RoundingMode.UP));
            }
        } catch (ParseException e) {
            logger.error("", e);
        }
        return costRackVO;
    }

    @Override
    public CostRoomVO getCostByRoom(long roomid) {
        return mapper.getCostByRoom(roomid);
    }

    @Override
    public ExecuteResult saveCostFixed(CostFixed costFixed) {
        ExecuteResult result = new ExecuteResult();
        try {
            if (StringUtils.isNotEmpty(costFixed.getCostFixedId()) && !"0".equals(costFixed.getCostFixedId())) {
                CostFixedExample example = new CostFixedExample();
                example.createCriteria().andCostFixedIdEqualTo(costFixed.getCostFixedId());
                costFixedMapper.updateByExample(costFixed, example);
            } else {
                costFixed.setCostFixedId(UUID.randomUUID().toString().replaceAll("-", ""));
                costFixedMapper.insertSelective(costFixed);
            }
            result.setMsg(costFixed.getCostFixedId());
            result.setState(true);
        } catch (Exception e) {
            result.setMsg("修改固定成本失败");
            logger.error("", e);
        }
        return result;
    }

    @Override
    public CostFixed getCostFixedByBuild(String buildid) {
        CostFixedExample example = new CostFixedExample();
        example.createCriteria().andObjectCodeEqualTo(buildid + "");
        List<CostFixed> costFixeds = costFixedMapper.selectByExample(example);
        if (costFixeds != null && costFixeds.size() > 0) {
            return costFixeds.get(0);
        }
        return null;
    }

    @Override
    public CostDynamic getDynamicByBuild(String buildid, String time) {
        CostDynamicExample example = new CostDynamicExample();
        example.createCriteria().andCostFixedIdEqualTo(buildid + "").andCostYearOrMonthEqualTo(new BigDecimal(time.replace("-", "")));
        List<CostDynamic> costFixeds = costDynamicMapper.selectByExample(example);
        if (costFixeds != null && costFixeds.size() > 0) {
            return costFixeds.get(0);
        }
        return null;
    }

    //
//    @Override
//    public ExecuteResult saveBuildDyn(CostDynamic costFixed) {
//        ExecuteResult result = new ExecuteResult();
//        try {
//            if (StringUtils.isNotEmpty(costFixed.getCostRoomId()) && !"0".equals(costFixed.getCostRoomId())) {
//                CostDynamicExample example = new CostDynamicExample();
//                example.createCriteria().andCostRoomIdEqualTo(costFixed.getCostRoomId());
//                costDynamicMapper.updateByExample(costFixed, example);
//            } else {
//                costFixed.setCostRoomId(UUID.randomUUID().toString().replaceAll("-", ""));
//                costDynamicMapper.insertSelective(costFixed);
//            }
//            result.setMsg(costFixed.getCostRoomId());
//            result.setState(true);
//        } catch (Exception e) {
//            result.setMsg("修改动态成本失败");
//            logger.error("", e);
//        }
//        return result;
//    }
    @Deprecated
    public CostAnalysisRack getCostAnalysisRoomByRack(String rackid, String time) {
        CostRackBaseExample example = new CostRackBaseExample();
        example.createCriteria().andRackCodeEqualTo(rackid);
        List<CostRackBase> costRackBases = costRackBaseMapper.selectByExample(example);
        if (costRackBases.size() == 0) {
            return null;
        }
        CostRackBase costRackBase = costRackBases.get(0);
        CostAnalysisRackExample analysisRackExample = new CostAnalysisRackExample();
        try {
            time = DateFormatUtils.format(DateUtils.parseDate(time, "yyyy-MM"), "yyMM");
        } catch (ParseException e) {
            logger.error("", e);
        }
        analysisRackExample.createCriteria().andCostRackIdEqualTo(costRackBase.getCostRackId()).andCostYearEqualTo(time);
        List<CostAnalysisRack> costAnalysisRacks = costAnalysisRackMapper.selectByExample(analysisRackExample);
        if (costAnalysisRacks.size() == 0) {
            return null;
        } else {
            return costAnalysisRacks.get(0);
        }
    }

    @Override
    public ExecuteResult saveBuildDyn(CostDynamic costFixed, CostAnalysisRoom analysisRoom) {
        ExecuteResult result = new ExecuteResult();
        try {
            if (costFixed != null) {
                //保存固定成本和动态成本
                if (StringUtils.isNotEmpty(costFixed.getCostRoomId()) && !"0".equals(costFixed.getCostRoomId())) {
                    CostDynamicExample example = new CostDynamicExample();
                    example.createCriteria().andCostRoomIdEqualTo(costFixed.getCostRoomId());
                    costDynamicMapper.updateByExample(costFixed, example);
                } else {
                    costFixed.setCostRoomId(UUID.randomUUID().toString().replaceAll("-", ""));
                    costDynamicMapper.insertSelective(costFixed);
                }
                if (analysisRoom != null) {
                    CostAnalysisRoomExample criteria = new CostAnalysisRoomExample();
                    criteria.createCriteria().andCostRoomIdEqualTo(costFixed.getCostRoomId());
                    List<CostAnalysisRoom> costAnalysisRooms = costAnalysisRoomMapper.selectByExample(criteria);
                    if (costAnalysisRooms != null && costAnalysisRooms.size() > 0) {
                        CostAnalysisRoom costAnalysisRoom = costAnalysisRooms.get(0);
                        analysisRoom.setCostRoomId(costAnalysisRoom.getCostRoomId());
                        analysisRoom.setCostAnalysisRoomId(costAnalysisRoom.getCostAnalysisRoomId());
                        costAnalysisRoomMapper.updateByExample(analysisRoom, criteria);
                    } else {
                        analysisRoom.setCostRoomId(costFixed.getCostRoomId());
                        analysisRoom.setCostAnalysisRoomId(UUID.randomUUID().toString().replaceAll("-", ""));
                        costAnalysisRoomMapper.insert(analysisRoom);
                    }
                }
                result.setMsg(costFixed.getCostRoomId());
            }
            result.setState(true);
        } catch (Exception e) {
            result.setMsg("修改动态成本失败");
            logger.error("", e);
        }
        return result;
    }

    @Override
    public CostAnalysisRoom getAnalyByRoom(String costRoomId) {
        //通过分析表ID获取分析对象
        CostAnalysisRoomExample example = new CostAnalysisRoomExample();
        example.createCriteria().andCostRoomIdEqualTo(costRoomId + "");
        List<CostAnalysisRoom> costAnalysisRooms = costAnalysisRoomMapper.selectByExample(example);
        if (costAnalysisRooms != null && costAnalysisRooms.size() > 0) {
            return costAnalysisRooms.get(0);
        }
        return null;
    }

    @Override
    public ExecuteResult saveRackDyn(long rackid, String time, CostAnalysisRack pcostAnalysisRack) {
        ExecuteResult result = new ExecuteResult();
        try {
            CostRackBaseExample example = new CostRackBaseExample();
            example.createCriteria().andRackCodeEqualTo(rackid + "");
            List<CostRackBase> costRackBases = costRackBaseMapper.selectByExample(example);
            CostRackBase costRackBase = null;
            if (costRackBases != null && costRackBases.size() > 0) {
                costRackBase = costRackBases.get(0);
            } else {
                //插入一个信息 做位中间值
                costRackBase = new CostRackBase();
                String id = UUID.randomUUID().toString().replace("-", "");
                costRackBase.setCostRackId(id);
                costRackBase.setRackCode(rackid + "");
                costRackBaseMapper.insert(costRackBase);
            }
            //获取机架分析信息
            String costRackId = costRackBase.getCostRackId();
            CostAnalysisRackExample analysisRackExample = new CostAnalysisRackExample();
            time = DateFormatUtils.format(DateUtils.parseDate(time, "yyyy-MM"), "yyMM");
            analysisRackExample.createCriteria().andCostRackIdEqualTo(costRackId).andCostYearEqualTo(time);
            List<CostAnalysisRack> costAnalysisRacks = costAnalysisRackMapper.selectByExample(analysisRackExample);
            CostAnalysisRack costAnalysisRack = null;
            if (costAnalysisRacks != null && costAnalysisRacks.size() > 0) {
                costAnalysisRack = costAnalysisRacks.get(0);
                costAnalysisRack.setCostRackPowerFee(pcostAnalysisRack.getCostRackPowerFee());
//                costAnalysisRack.setCostRockIncome(pcostAnalysisRack.getCostRockIncome());
//                costAnalysisRack.setBandwidthIncome(pcostAnalysisRack.getBandwidthIncome());
                analysisRackExample.clear();
                analysisRackExample.createCriteria().andCostAnalysisRackIdEqualTo(costAnalysisRack.getCostAnalysisRackId());
                costAnalysisRackMapper.updateByExample(costAnalysisRack, analysisRackExample);
            } else {
                costAnalysisRack = new CostAnalysisRack();
                costAnalysisRack.setCostRackId(costRackId);
                costAnalysisRack.setCostAnalysisRackId(UUID.randomUUID().toString().replace("-", ""));
                costAnalysisRack.setCostRackPowerFee(pcostAnalysisRack.getCostRackPowerFee());
//                costAnalysisRack.setCostRockIncome(pcostAnalysisRack.getCostRockIncome());
//                costAnalysisRack.setBandwidthIncome(pcostAnalysisRack.getBandwidthIncome());
                costAnalysisRack.setCostYear(time);
                costAnalysisRackMapper.insert(costAnalysisRack);
            }
            result.setMsg(costAnalysisRack.getCostAnalysisRackId());
            result.setState(true);
        } catch (Exception e) {
            result.setMsg("修改机架成本失败");
            logger.error("", e);
        }
        return result;
    }

    @Override
    public CostRackFullVO getByCustomer(long customerid, String time) {
        //获取客户所有机架
        IdcRack rackQuery = new IdcRack();
        rackQuery.setActualcustomerid(customerid);
        List<IdcRack> idcRacks = idcRackService.queryListByObject(rackQuery);
        List<CostRackFullVO> rackAll = new ArrayList<>();
        for (IdcRack idcRack : idcRacks) {
            CostRackFullVO costByRack = getCostByRack(idcRack.getId(), time);
            if (costByRack != null) {
                rackAll.add(costByRack);
            }
        }
        CostRackFullVO vo = null;
        for (CostRackFullVO costRackFullVO : rackAll) {
            if (vo == null) {
                vo = costRackFullVO;
            } else {
                //累加结果
                vo.getCostBaseDevMonth().add(costRackFullVO.getCostBaseDevMonth());
                vo.getCostItMonth().add(costRackFullVO.getCostItMonth());
                vo.getCostBuildingMonth().add(costRackFullVO.getCostBuildingMonth());
                vo.getCostEnergy().add(costRackFullVO.getCostEnergy());
                vo.getCostWaterRate().add(costRackFullVO.getCostWaterRate());
                vo.getCostWork().add(costRackFullVO.getCostWork());
                vo.getCostRepair().add(costRackFullVO.getCostRepair());
                vo.getCostMaintenance().add(costRackFullVO.getCostMaintenance());
                vo.getCostMarketting().add(costRackFullVO.getCostMarketting());
            }
        }
        if (vo == null) {
            vo = new CostRackFullVO();
        }
        //获取 收入信息
        getIncome(customerid, time, vo);
        return vo;
        //获取没一个机架的成本信息

    }

    private void getIncome(long customerid, String time, CostRackFullVO vo) {
        CostDynamicExample costDynamicExample = new CostDynamicExample();
        costDynamicExample.createCriteria().andCustomIdEqualTo(customerid + "").andCostYearOrMonthEqualTo(new BigDecimal(time.replace("-", "")));
        List<CostDynamic> costDynamics = costDynamicMapper.selectByExample(costDynamicExample);
        if (costDynamics.size() > 0) {
            //获取客户的收入 燃后在回去客户的宽带收入 通过
            CostDynamic dynamic = costDynamics.get(0);
            vo.setCostRockIncome(dynamic.getCostCustomIncome());
            CostAnalysisRoomExample costAnalysisRoomExample = new CostAnalysisRoomExample();
            costAnalysisRoomExample.createCriteria().andCostRoomIdEqualTo(dynamic.getCostRoomId());
            List<CostAnalysisRoom> costAnalysisRooms = costAnalysisRoomMapper.selectByExample(costAnalysisRoomExample);
            if (costAnalysisRooms.size() > 0) {
                vo.setBandwidthIncome(costAnalysisRooms.get(0).getCostBandwidthIncome());
            } else {
                vo.setBandwidthIncome(new BigDecimal(0));
            }
        } else {
            vo.setCostRockIncome(new BigDecimal(0));
            vo.setBandwidthIncome(new BigDecimal(0));
        }
    }

    /***
     * 保存客户的收入 计算机架收入的时候用来分摊
     *
     * @param customId
     * @param time
     * @param costAnalysisRack
     * @return
     */
    @Override
    public ExecuteResult saveCostCustomer(long customId, String time, CostAnalysisRack costAnalysisRack) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            CostDynamicExample costDynamicExample = new CostDynamicExample();
            BigDecimal timeBig = new BigDecimal(time.replace("-", ""));
            costDynamicExample.createCriteria().andCustomIdEqualTo(customId + "").andCostYearOrMonthEqualTo(timeBig);
            List<CostDynamic> costDynamics = costDynamicMapper.selectByExample(costDynamicExample);
            CostDynamic dynamic = null;
            if (costDynamics.size() > 0) {
                dynamic = costDynamics.get(0);
                costDynamicExample.clear();
                costDynamicExample.createCriteria().andCostRoomIdEqualTo(dynamic.getCostRoomId());
                dynamic.setCostCustomIncome(costAnalysisRack.getCostRockIncome());
                costDynamicMapper.updateByExample(dynamic, costDynamicExample);
            } else {
                dynamic = new CostDynamic();
                dynamic.setCustomId(customId + "");
                dynamic.setCostRoomId(UUID.randomUUID().toString().replace("-", ""));
                dynamic.setCostCustomIncome(costAnalysisRack.getCostRockIncome());
                dynamic.setCostYearOrMonth(timeBig);
                costDynamicMapper.insert(dynamic);
            }
            if (dynamic != null) {
                CostAnalysisRoomExample costAnalysisRoomExample = new CostAnalysisRoomExample();
                costAnalysisRoomExample.createCriteria().andCostRoomIdEqualTo(dynamic.getCostRoomId());
                List<CostAnalysisRoom> costAnalysisRooms = costAnalysisRoomMapper.selectByExample(costAnalysisRoomExample);
                if (costAnalysisRooms.size() > 0) {
                    CostAnalysisRoom costAnalysisRoom = costAnalysisRooms.get(0);
                    costAnalysisRoom.setCostBandwidthIncome(costAnalysisRack.getBandwidthIncome());
                    costAnalysisRoomMapper.updateByExample(costAnalysisRoom, costAnalysisRoomExample);
                } else {
                    CostAnalysisRoom costAnalysisRoom = new CostAnalysisRoom();
                    costAnalysisRoom.setCostAnalysisRoomId(UUID.randomUUID().toString().replace("-", ""));
                    costAnalysisRoom.setCostRoomId(dynamic.getCostRoomId());
                    costAnalysisRoom.setCostBandwidthIncome(costAnalysisRack.getBandwidthIncome());
                    costAnalysisRoomMapper.insert(costAnalysisRoom);
                }
            }
            executeResult.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("", e);
            executeResult.setMsg("保存失败");
        }

        return executeResult;
    }

    @Override
    public CostRackFullVO getInCome(long rackid, String time) {
        IdcRack modelById = idcRackService.getModelById(Integer.valueOf(rackid + ""));
        Long actualcustomerid = modelById.getActualcustomerid();
        CostRackFullVO costRackFullVO = new CostRackFullVO();
        if (actualcustomerid != null) {
            getIncome(actualcustomerid, time, costRackFullVO);
            IdcRack rackQuery = new IdcRack();
            rackQuery.setActualcustomerid(actualcustomerid);
            List<IdcRack> idcRacks = idcRackService.queryListByObject(rackQuery);
            if (idcRacks.size() > 0) {
                costRackFullVO.setBandwidthIncome(costRackFullVO.getBandwidthIncome().divide(BigDecimal.valueOf(idcRacks.size()), 2, RoundingMode.UP));
                costRackFullVO.setCostRockIncome(costRackFullVO.getCostRockIncome().divide(BigDecimal.valueOf(idcRacks.size()), 2, RoundingMode.UP));
            }
        }
        return costRackFullVO;
    }
}
