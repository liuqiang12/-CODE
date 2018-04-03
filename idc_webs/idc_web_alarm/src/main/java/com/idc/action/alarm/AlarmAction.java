package com.idc.action.alarm;//package com.idc.action.alarm;

import com.google.common.collect.ImmutableMap;
import com.idc.model.*;
import com.idc.service.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2016/12/23.
 */
@Controller
@RequestMapping("/alarm")
public class AlarmAction {
    @Autowired
    private NetAlarminfoService netAlarminfoService;
    @Autowired
    private NetKpibaseService netKpibaseService;
    @Autowired
    private NetKpiAlarmLevelConfigService alarmConfigLevelService;
    @Autowired
    private NetAlarmSendinfoService netAlarmSendinfoService;
    @Autowired
    private NetAlarmConfigSendService netAlarmConfigSendService;
    //    private alarmIn
    private static final Logger logger = LoggerFactory.getLogger(AlarmAction.class);

    public static void main(String[] args) {
        //ToDo
    }

    /***
     * 进入界面
     *
     * @return
     */
    @RequestMapping("index.do")
    public String index(Integer objid,ModelMap map) {
        List<NetKpibase> netKpibases = netKpibaseService.queryList();
        map.put("kpis", netKpibases);
        map.put("objid", objid);
        return "alarm/index";
    }


    @RequestMapping("list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean pageBean, String keyword, Integer regionid, Long objid, Integer type, String starttime, String endtime, Integer alarmtype) {
        List<NetAlarmInfoVo> result = netAlarminfoService.queryListPageByMap(pageBean, keyword, regionid, objid, (type == null || type == 0) ? true : false, starttime, endtime, alarmtype);
        return new EasyUIData(pageBean);
    }

    @RequestMapping("alarmkpilist.do")
    @ResponseBody
    public List<Map<String, Object>> alarmKpiList(Integer kpiid) {
        Map t = new HashMap();
        if (kpiid != null) {
            t.put("kpiid", kpiid);
        }
        List<NetKpiAlarmLevelConfig> netKpiAlarmLevelConfigs = alarmConfigLevelService.queryListByMap(t);
        ImmutableMap<Long, NetKpibase> kpiBaseMap = netKpibaseService.getKpiBaseMap();
        List<Map<String, Object>> result = new ArrayList<>();
        for (NetKpiAlarmLevelConfig netKpiAlarmLevelConfig : netKpiAlarmLevelConfigs) {
            JSONObject jsonObject = JSONObject.fromObject(netKpiAlarmLevelConfig);
            NetKpibase netKpibase = kpiBaseMap.get(netKpiAlarmLevelConfig.getKpiid());
            jsonObject.put("kpiname", netKpibase == null ? "" : netKpibase.getKpiname());
            result.add(jsonObject);
        }
        return result;
    }

    /***
     * 进入界面
     * 告警发送
     *
     * @return
     */
    @RequestMapping("alarmsend.do")
    public String alarmSend(ModelMap modelMap) {
        List<NetKpibase> netKpibases = netKpibaseService.queryList();
        modelMap.put("kpis", netKpibases);
        return "alarm/alarmsend";
    }

    @RequestMapping("editkpi.do")
    public String editKpi(Long id, ModelMap modelMap) {
        if (id != null && id.intValue() > 0) {
            NetKpiAlarmLevelConfig modelById = alarmConfigLevelService.getModelById(id);
            modelMap.put("alarmconfiglevel", modelById);
        }
        List<NetKpibase> netKpibases = netKpibaseService.queryList();
        modelMap.put("kpis", netKpibases);
        return "alarm/kpiform";
    }

    @RequestMapping("alarmkpisave.do")
    @ResponseBody
    public ExecuteResult alarmKpiSave(NetKpiAlarmLevelConfig alarmKpi) {
        ExecuteResult executeResult = new ExecuteResult();
        if (alarmKpi != null) {
            try {
                if (alarmKpi.getId() != null) {
                    alarmConfigLevelService.updateByObject(alarmKpi);
                } else {
                    alarmConfigLevelService.insert(alarmKpi);
                }
                executeResult.setState(true);
            } catch (Exception e) {
                logger.error("", e);
                executeResult.setMsg("保存失败，请检查重试");
            }
        } else {
            executeResult.setMsg("没有阀值信息");
        }
        return executeResult;
    }

    /***
     * 告警发送信息
     *
     * @param pageBean
     * @param keyword
     * @param
     * @param
     * @param starttime
     * @param endtime
     * @return
     */

    @RequestMapping("alarmsendlist.do")
    @ResponseBody
    public EasyUIData alarmSendList(EasyUIPageBean pageBean, String keyword, String starttime, String endtime) {
        List<NetAlarmSendinfo> result = netAlarmSendinfoService.queryListPageByMap(pageBean, keyword, starttime, endtime);
        return new EasyUIData(pageBean);
    }

    @RequestMapping("alarmconfigsendlist.do")
    @ResponseBody
    public List<JSONObject> alarmSendConfigList(Integer kpiid,Integer alarmLevel) {
        NetKpiAlarmSendConfig netKpiAlarmSendConfig = new NetKpiAlarmSendConfig();
        netKpiAlarmSendConfig.setAlarmlevel(alarmLevel);
        netKpiAlarmSendConfig.setKpiid(kpiid);
        List<NetKpiAlarmSendConfig> result = netAlarmConfigSendService.queryListByObject(netKpiAlarmSendConfig);
        ImmutableMap<Long, NetKpibase> kpiBaseMap = netKpibaseService.getKpiBaseMap();
        List<JSONObject> list =new ArrayList<>();
        for (NetKpiAlarmSendConfig kpiAlarmSendConfig : result) {
            JSONObject jsonObject = JSONObject.fromObject(kpiAlarmSendConfig);
            NetKpibase netKpibase = kpiBaseMap.get(Long.parseLong(kpiAlarmSendConfig.getKpiid().toString()));
            jsonObject.put("kpiname",netKpibase==null?"":netKpibase.getKpiname());
            list.add(jsonObject);
        }
        return list;
    }

    @RequestMapping("editsendconfig.do")
    public String editSendConfig(Integer id, ModelMap modelMap) {
        if (id != null && id.intValue() > 0) {
            NetKpiAlarmSendConfig modelById1 = netAlarmConfigSendService.getModelById(id);
            modelMap.put("alarmconfigsend", modelById1);
        }
        List<NetKpibase> netKpibases = netKpibaseService.queryList();
        modelMap.put("kpis", netKpibases);
        return "alarm/sendconfigform";
    }

    @RequestMapping("sendconfigsave.do")
    @ResponseBody
    public ExecuteResult sendConfigSave(NetKpiAlarmSendConfig netKpiAlarmSendConfig) {
        ExecuteResult executeResult = new ExecuteResult();
        if (netKpiAlarmSendConfig != null) {
            try {
                if (netKpiAlarmSendConfig.getId() != null) {
                    netAlarmConfigSendService.updateByObject(netKpiAlarmSendConfig);
                } else {
                    netAlarmConfigSendService.insert(netKpiAlarmSendConfig);
                }
                executeResult.setState(true);
            } catch (Exception e) {
                logger.error("", e);
                executeResult.setMsg("保存失败，请检查重试");
            }
        } else {
            executeResult.setMsg("没有发送信息");
        }
        return executeResult;
    }


    @ResponseBody
    @RequestMapping(value = "/sendconfigdelete.do")
    public ExecuteResult sendConfigDelete(int id) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            netAlarmConfigSendService.deleteById(id);
            executeResult.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除信息失败:", e);
            executeResult.setMsg("删除信息失败");
        }
        return executeResult;
    }

    @ResponseBody
    @RequestMapping(value = "/delete.do")
    public ExecuteResult delete(int id) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            netAlarminfoService.deleteCurrAlarmById(id);
            executeResult.setState(true);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除信息失败:", e);
            executeResult.setMsg("删除信息失败");
        }
        return executeResult;
    }
}
