package com.idc.action;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.bo.IdcPdfDayPowerInfoVo;
import com.idc.bo.RoomPE;
import com.idc.model.*;
import com.idc.service.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * 动环
 * Created by mylove on 2017/10/12.
 */
@Controller
public class PEAction {
    private static final Logger logger = LoggerFactory.getLogger(PEAction.class);

    @Autowired
    private PowerEnvService powerEnvService;
    @Autowired
    private IdcPowerBuidingInfoService idcPowerBuidingInfoService;
    @Autowired
    private ZbMachineroomService zbMachineroomService;
    @Autowired
    private IdcRackService idcRackService;
    @Autowired
    private IdcBuildingService idcBuildingService;
    @Autowired
    private IdcPowerRoomInfoService idcPowerRoomInfoService;
    @Autowired
    private IdcPdfDayPowerInfoService idcPdfDayPowerInfoService;
    @Autowired
    private IdcOuterCodeService idcOuterCodeService;
    @Autowired
    private IdcMcbService idcMcbService;
//    @Autowired
//    public IdcPowerBuidingInfoService idcPowerBuidingInfoService;

    public static void main(String[] args) {
        //ToDo
    }
    @RequestMapping("/pe")
    public String index() {
        return "pe/index";
    }
    @RequestMapping("/pe/{type}/")
    public String build(@PathVariable String type, ModelMap map) {
        if ("build".equals(type)) {
            map.put("group", "build");
            return "pe/build";
        } else {
            map.put("group", "room");
            return "pe/room";
        }
    }
/*
    @RequestMapping("/pe/build/")
    public String build(ModelMap map) {
        map.put("group", "build");
        return "pe/index";
    }

    @RequestMapping("/pe/room/")
    public String room(ModelMap map) {
        map.put("group", "room");
        return "pe/index";
    }*/

    @RequestMapping("/pe/hispe/{type}/{roomid}")
    public String hispe(@PathVariable String type, @PathVariable String roomid, ModelMap map) {
        List<RoomPE> roomPEs = powerEnvService.getHisPE(type, roomid);
        map.put("datas", roomPEs);
        return "pe/his";
    }

    @RequestMapping("/pe/list/{type}")
    @ResponseBody
    public List<RoomPE> list(@PathVariable String type, String buildid) {
        return powerEnvService.getRoomPEByBuildid(type, buildid);
    }

    @RequestMapping("/pe/getRooms/{build}")
    @ResponseBody
    public Map<String, List<RoomPE>> list(@PathVariable String build) {
        ZbMachineroom query = new ZbMachineroom();
        query.setBuildingid(build);
        List<ZbMachineroom> zbMachinerooms = zbMachineroomService.queryListByObject(query);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -1);
        List<IdcPowerRoomInfo> day = idcPowerRoomInfoService.queryByTime(null, null, DateFormatUtils.format(c, "yyyy-MM-dd"), "day");
        ImmutableMap<String, IdcPowerRoomInfo> roomMap = Maps.uniqueIndex(day, new Function<IdcPowerRoomInfo, String>() {
            @Override
            public String apply(IdcPowerRoomInfo idcPowerRoomInfo) {
                return idcPowerRoomInfo.getSysCode();
            }
        });
        Map<String, List<RoomPE>> floars = new HashMap<>();
        DecimalFormat df = new DecimalFormat("######0.00");
        for (ZbMachineroom zbMachineroom : zbMachinerooms) {
            String sitename = zbMachineroom.getSitename();
            String[] split = sitename.split("-");
            String s = split[split.length - 1];
            String substring = sitename.substring(sitename.length() - 3);
            char floar = sitename.charAt(sitename.length() - 3);
            String floarstr = sitename.substring(sitename.length() - 3, sitename.length() - 1);
            String floarName = "其他";
            if (NumberUtils.isNumber(s)) {
                floarName = String.valueOf(s.charAt(0));
            }
            List<RoomPE> zbMachinerooms1 = floars.get(floarName);
            if (zbMachinerooms1 == null) {
                zbMachinerooms1 = new ArrayList<>();
                floars.put(floarName, zbMachinerooms1);
            }
            RoomPE pe = new RoomPE();
            pe.setRoomName(zbMachineroom.getSitename());
            pe.setRoomid(zbMachineroom.getId());
            IdcPowerRoomInfo idcPowerRoomInfo = roomMap.get(zbMachineroom.getSitename());
            pe.setPue((idcPowerRoomInfo == null || idcPowerRoomInfo.getIdcDeviceBeforeDiff() == 0) ? 0 : (Double.parseDouble(df.format((idcPowerRoomInfo.getIdcAirAdjustBeforeDiff() + idcPowerRoomInfo.getIdcDeviceBeforeDiff()) / idcPowerRoomInfo.getIdcDeviceBeforeDiff()))));
            pe.setElectric(idcPowerRoomInfo == null ? 0 : idcPowerRoomInfo.getIdcBeforeDiff());
            zbMachinerooms1.add(pe);
        }
        return floars;
    }

    @RequestMapping("/pe/getBuild")
    @ResponseBody
    public List<IdcBuilding> getBuild(Integer localid) {
        IdcBuilding query = new IdcBuilding();
        if (localid != null && localid > 0) {
            query.setLocationid(localid);
        }

        List<IdcBuilding> idcBuildings = idcBuildingService.queryListByObject(query);
        return idcBuildings;
    }

    @RequestMapping("/pe/getLineByBuild/{build}")
    @ResponseBody
    public HashMap<String, Object> getLineByBuild(@PathVariable Integer build, String date, int cyc) {
        HashMap<String, Object> map = new HashMap<>();
        List<String> datestr = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<String> pues = new ArrayList<>();
        List<String> devices = new ArrayList<>();
        List<String> airs = new ArrayList<>();
        IdcBuilding buildingBean = idcBuildingService.getModelById(build);
        Calendar c = Calendar.getInstance();
        String starTime = "";
        try {
            Date end = new Date();
            c.setTime(end);
            c.add(Calendar.DAY_OF_MONTH, -1);
            date = DateFormatUtils.format(c, "yyyy-MM-dd");
            c.add(Calendar.MONTH, -1);
            starTime = DateFormatUtils.format(c, "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<IdcPowerBuidingInfo> list = getTest(build.toString(), starTime, date, cyc);//idcPowerBuidingInfoService.queryByTime(buildingBean.getCode(), starTime, date, "day");

        IdcPowerBuidingInfo prev = null;
        DecimalFormat df = new DecimalFormat("######0.00");
        for (int i = 0; i < list.size(); i++) {
            IdcPowerBuidingInfo idcPowerRoomInfo = list.get(i);
            String dateStr = DateFormatUtils.format(idcPowerRoomInfo.getIdcCreateTime(), "yyyy-MM-dd");
            datestr.add(dateStr);
            values.add(df.format(idcPowerRoomInfo.getIdcBeforeDiff()));
            devices.add(df.format(idcPowerRoomInfo.getIdcDeviceBeforeDiff()));
            airs.add(df.format(idcPowerRoomInfo.getIdcAirAdjustBeforeDiff()));
            double pue = 0;
            if (idcPowerRoomInfo.getIdcDeviceBeforeDiff() > 0) {
                pue = Double.parseDouble(df.format((idcPowerRoomInfo.getIdcAirAdjustBeforeDiff() + idcPowerRoomInfo.getIdcDeviceBeforeDiff()) / idcPowerRoomInfo.getIdcDeviceBeforeDiff()));
            }
            pues.add(df.format(pue));

            if (i == list.size() - 1) {
                if (date.equals(dateStr)) {
                    map.put("all", Double.parseDouble(df.format(idcPowerRoomInfo.getIdcBeforeDiff())));
                    map.put("device", Double.parseDouble(df.format(idcPowerRoomInfo.getIdcDeviceBeforeDiff())));
                    map.put("air", Double.parseDouble(df.format(idcPowerRoomInfo.getIdcAirAdjustBeforeDiff())));
                    map.put("pue", pue);
                    if (prev != null && prev.getIdcBeforeDiff() > 0) {
                        map.put("add", Double.parseDouble(df.format(idcPowerRoomInfo.getIdcBeforeDiff() - prev.getIdcBeforeDiff())) / prev.getIdcBeforeDiff() * 100);
                    } else {
                        map.put("add", 0);
                    }
                }

            } else {
                prev = idcPowerRoomInfo;
            }
        }
        map.put("datestr", datestr);
        map.put("values", values);
        map.put("devices", devices);
        map.put("airs", airs);
        map.put("pues", pues);
        return map;
    }

    private List<IdcPowerBuidingInfo> getTest(String build, String starTime, String date, int cyc) {
        ZbMachineroom query = new ZbMachineroom();
        query.setBuildingid(build);
        List<ZbMachineroom> zbMachinerooms = zbMachineroomService.queryListByObject(query);
        List<IdcBuilding> idcBuildings = idcBuildingService.queryList();
        ImmutableMap<String, IdcBuilding> buildMap = Maps.uniqueIndex(idcBuildings, new Function<IdcBuilding, String>() {
            @Override
            public String apply(IdcBuilding idcBuilding) {
                return idcBuilding.getId().toString();
            }
        });


        ImmutableMap<String, ZbMachineroom> roomInfoMap = Maps.uniqueIndex(zbMachinerooms, new Function<ZbMachineroom, String>() {
            @Override
            public String apply(ZbMachineroom idcBuilding) {
                return idcBuilding.getSitename().toString();
            }
        });
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -1);
        List<IdcPowerRoomInfo> day = idcPowerRoomInfoService.queryByTime(null, starTime, date, "day");
//        ImmutableMap<String, IdcPowerRoomInfo> roomMap = Maps.uniqueIndex(day, new Function<IdcPowerRoomInfo, String>() {
//            @Override
//            public String apply(IdcPowerRoomInfo idcPowerRoomInfo) {
//                return idcPowerRoomInfo.getSysCode();
//            }
//        });
        Map<Long, Map<String, IdcPowerBuidingInfo>> TimebuildPowerMap = new HashMap<>();
        Map<Long, IdcPowerBuidingInfo> myMap = new TreeMap<>();
        for (IdcPowerRoomInfo idcPowerRoomInfo : day) {
            String sysCode = idcPowerRoomInfo.getSysCode();
            ZbMachineroom zbMachineroom = roomInfoMap.get(sysCode);
            if(zbMachineroom==null){
                continue;
            }
            long time = idcPowerRoomInfo.getIdcCreateTime().getTime();
            String buildingid = zbMachineroom.getBuildingid();
            if(!buildingid.equals(build)){
                continue;
            }
            IdcBuilding idcBuilding = buildMap.get(buildingid);

            IdcPowerBuidingInfo idcPowerBuidingInfo = myMap.get(time);
            if (idcPowerBuidingInfo == null) {
                idcPowerBuidingInfo = new IdcPowerBuidingInfo();
                idcPowerBuidingInfo.setIdcCreateTime(idcPowerRoomInfo == null ? null : idcPowerRoomInfo.getIdcCreateTime());
                idcPowerBuidingInfo.setIdcPowerBuidingCode(idcBuilding == null ? "" : idcBuilding.getCode());
                idcPowerBuidingInfo.setIdcPowerBuidingName(idcBuilding == null ? "" : idcBuilding.getName());
                idcPowerBuidingInfo.setIdcAirAdjustAmout(0D);
                idcPowerBuidingInfo.setIdcAirAdjustBeforeDiff(0D);
                idcPowerBuidingInfo.setIdcAmout(0D);
                idcPowerBuidingInfo.setIdcBeforeDiff(0D);
                idcPowerBuidingInfo.setIdcDeviceAmout(0D);
                idcPowerBuidingInfo.setIdcDeviceBeforeDiff(0D);
                myMap.put(time, idcPowerBuidingInfo);
            }
            if (idcPowerRoomInfo != null) {
                idcPowerBuidingInfo.setIdcAirAdjustAmout(idcPowerBuidingInfo.getIdcAirAdjustAmout() + idcPowerRoomInfo.getIdcAirAdjustAmout());
                idcPowerBuidingInfo.setIdcAirAdjustBeforeDiff(idcPowerBuidingInfo.getIdcAirAdjustBeforeDiff() + idcPowerRoomInfo.getIdcAirAdjustBeforeDiff());
                idcPowerBuidingInfo.setIdcAmout(idcPowerBuidingInfo.getIdcAmout() + idcPowerRoomInfo.getIdcAmout());
                idcPowerBuidingInfo.setIdcBeforeDiff(idcPowerBuidingInfo.getIdcBeforeDiff() + idcPowerRoomInfo.getIdcBeforeDiff());
                idcPowerBuidingInfo.setIdcDeviceAmout(idcPowerBuidingInfo.getIdcDeviceAmout() + idcPowerRoomInfo.getIdcDeviceAmout());
                idcPowerBuidingInfo.setIdcDeviceBeforeDiff(idcPowerBuidingInfo.getIdcDeviceBeforeDiff() + idcPowerRoomInfo.getIdcDeviceBeforeDiff());
            }
        }
//
//        DecimalFormat df = new DecimalFormat("######0.00");
//        for (ZbMachineroom zbMachineroom : zbMachinerooms) {
//            IdcPowerRoomInfo idcPowerRoomInfo = roomMap.get(zbMachineroom.getSitename());
//            if (idcPowerRoomInfo == null) {
//                continue;
//            }
//            Timestamp idcCreateTime = idcPowerRoomInfo.getIdcCreateTime();
//            Map<String, IdcPowerBuidingInfo> buildPowerMap = TimebuildPowerMap.get(idcCreateTime.getTime());
//            if(buildPowerMap==null){
//                buildPowerMap=new HashMap<>();
//                TimebuildPowerMap.put(idcCreateTime.getTime(),buildPowerMap);
//            }
//            String buildingid = zbMachineroom.getBuildingid();
//            IdcBuilding idcBuilding = buildMap.get(buildingid);
//            IdcPowerBuidingInfo idcPowerBuidingInfo = buildPowerMap.get(buildingid);
//            if (idcPowerBuidingInfo == null) {
//                idcPowerBuidingInfo = new IdcPowerBuidingInfo();
//                idcPowerBuidingInfo.setIdcCreateTime(idcPowerRoomInfo == null ? null : idcPowerRoomInfo.getIdcCreateTime());
//                idcPowerBuidingInfo.setIdcPowerBuidingCode(idcBuilding == null ? "" : idcBuilding.getCode());
//                idcPowerBuidingInfo.setIdcPowerBuidingName(idcBuilding == null ? "" : idcBuilding.getName());
//                idcPowerBuidingInfo.setIdcAirAdjustAmout(0D);
//                idcPowerBuidingInfo.setIdcAirAdjustBeforeDiff(0D);
//                idcPowerBuidingInfo.setIdcAmout(0D);
//                idcPowerBuidingInfo.setIdcBeforeDiff(0D);
//                idcPowerBuidingInfo.setIdcDeviceAmout(0D);
//                idcPowerBuidingInfo.setIdcDeviceBeforeDiff(0D);
//                buildPowerMap.put(buildingid, idcPowerBuidingInfo);
//            }
//            if (idcPowerRoomInfo != null) {
//                idcPowerBuidingInfo.setIdcAirAdjustAmout(idcPowerBuidingInfo.getIdcAirAdjustAmout() + idcPowerRoomInfo.getIdcAirAdjustAmout());
//                idcPowerBuidingInfo.setIdcAirAdjustBeforeDiff(idcPowerBuidingInfo.getIdcAirAdjustBeforeDiff() + idcPowerRoomInfo.getIdcAirAdjustBeforeDiff());
//                idcPowerBuidingInfo.setIdcAmout(idcPowerBuidingInfo.getIdcAmout() + idcPowerRoomInfo.getIdcAmout());
//                idcPowerBuidingInfo.setIdcBeforeDiff(idcPowerBuidingInfo.getIdcBeforeDiff() + idcPowerRoomInfo.getIdcBeforeDiff());
//                idcPowerBuidingInfo.setIdcDeviceAmout(idcPowerBuidingInfo.getIdcDeviceAmout() + idcPowerRoomInfo.getIdcDeviceAmout());
//                idcPowerBuidingInfo.setIdcDeviceBeforeDiff(idcPowerBuidingInfo.getIdcDeviceBeforeDiff() + idcPowerRoomInfo.getIdcDeviceBeforeDiff());
//            }
//        }
        ArrayList<IdcPowerBuidingInfo> objects = new ArrayList<>();

        objects.addAll(myMap.values());
        return objects;
    }

    @RequestMapping("/pe/getPUE/{roomid}")
    @ResponseBody
    public float getPUE(@PathVariable Long roomid, String date, String cyc) {
        Random random = new Random();
        return random.nextFloat() * 2;
    }

    @RequestMapping("/pe/getLine/{roomid}")
    @ResponseBody
    public Map<String, Object> getLine(@PathVariable Integer roomid, String date, int cyc) {
        HashMap<String, Object> map = new HashMap<>();
        List<String> datestrs = new ArrayList<>();
        List<Double> values = new ArrayList<>();
        List<Double> pues = new ArrayList<>();
        List<Double> devices = new ArrayList<>();
        List<Double> airs = new ArrayList<>();
        ZbMachineroom roomBean = zbMachineroomService.getModelById(roomid);
        Calendar c = Calendar.getInstance();
        String starTime = date;
        String endTime = date;
        try {
            Date end = DateUtils.parseDate(date, "yyyy-MM-dd");
            c.setTime(end);
            if (cyc == 1) {
                endTime = DateFormatUtils.format(c, "yyyy-MM");
                c.add(Calendar.MONTH, -3);
                starTime = DateFormatUtils.format(c, "yyyy-MM");

            } else {
                endTime = DateFormatUtils.format(c, "yyyy-MM-dd");
                c.add(Calendar.MONTH, -1);
                starTime = DateFormatUtils.format(c, "yyyy-MM-dd");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<IdcPowerRoomInfo> list = idcPowerRoomInfoService.queryByTime(roomBean.getSitename(), starTime, endTime, cyc == 1 ? "month" : "day");
        IdcPowerRoomInfo prev = null;
        DecimalFormat df = new DecimalFormat("######0.00");
        for (int i = 0; i < list.size(); i++) {
            IdcPowerRoomInfo idcPowerRoomInfo = list.get(i);
            String dateStr = "";
            if (cyc == 1) {
                dateStr = DateFormatUtils.format(idcPowerRoomInfo.getIdcCreateTime(), "yyyy-MM");
            } else {
                dateStr = DateFormatUtils.format(idcPowerRoomInfo.getIdcCreateTime(), "yyyy-MM-dd");
            }
            datestrs.add(dateStr);
            values.add(idcPowerRoomInfo.getIdcBeforeDiff());
            devices.add(idcPowerRoomInfo.getIdcDeviceBeforeDiff());
            airs.add(idcPowerRoomInfo.getIdcAirAdjustBeforeDiff());
            double pue = 0;
            if (idcPowerRoomInfo.getIdcDeviceBeforeDiff() > 0) {
                pue = Double.parseDouble(df.format((idcPowerRoomInfo.getIdcAirAdjustBeforeDiff() + idcPowerRoomInfo.getIdcDeviceBeforeDiff()) / idcPowerRoomInfo.getIdcDeviceBeforeDiff()));
            }
            pues.add(pue);
            if (i == list.size() - 1) {
                if (endTime.equals(dateStr)) {
                    map.put("all", Double.parseDouble(df.format(idcPowerRoomInfo.getIdcBeforeDiff())));
                    map.put("device", Double.parseDouble(df.format(idcPowerRoomInfo.getIdcDeviceBeforeDiff())));
                    map.put("air", Double.parseDouble(df.format(idcPowerRoomInfo.getIdcAirAdjustBeforeDiff())));
                    map.put("pue", pue);
                    if (prev != null) {
                        map.put("add", Double.parseDouble(df.format(idcPowerRoomInfo.getIdcBeforeDiff() - prev.getIdcBeforeDiff())) / prev.getIdcBeforeDiff() * 100);
                    } else {
                        map.put("add", 0);
                    }
                }

            } else {
                prev = idcPowerRoomInfo;
            }
        }
        map.put("datestrs", datestrs);
        map.put("values", values);
        map.put("devices", devices);
        map.put("airs", airs);
        map.put("pues", pues);
        return map;
    }

    @RequestMapping("/pe/roomlayout/index")
    public String index(ModelMap map) {
        return "pe/rackindex";
    }

    @RequestMapping("/pe/roomlayout/{roomid}")
    public String roomLayout(@PathVariable int roomid, ModelMap map) {
        ZbMachineroom modelById = null;
        if (roomid == 0) {
            List<ZbMachineroom> zbMachinerooms = zbMachineroomService.queryList();
            if (zbMachinerooms != null && zbMachinerooms.size() > 0) {
                modelById = zbMachinerooms.get(0);
                roomid = modelById.getId();
            }
        } else {
            modelById = zbMachineroomService.getModelById(roomid);
        }

        //获取机架统计信息   只统计客户架 总数、占用数
        Map<String, Object> racks = zbMachineroomService.getRoomStatistics(roomid);
        JSONObject jsonObject = JSONObject.fromObject(modelById);
        IdcRack idcRack = new IdcRack();
        idcRack.setRoomid(roomid + "");
        //获取所有机架
        List<IdcRack> idcRacks = idcRackService.queryListByObject(idcRack);
        //获取当前机内所有模块
        List<Map<String, Object>> moduleList = zbMachineroomService.queryAllModuleByRoomId(roomid);
        RoomLayoutTemp roomTemp = new RoomLayoutTemp();
        //初始化机架位置
        List<Row1> resultList = roomTemp.updateRackXAndY(idcRacks);

        //初始化模块位置
        List<Map<String, Object>> resultModuleList = new ArrayList<>();
        for (int i = 0; i < moduleList.size(); i++) {//遍历模块
            Map<String, Object> resultMap = new HashedMap();
            int r = 0, c = 0, w = 0, h = 0, sty = i % 2;
            String moduleName = moduleList.get(i).get("NAME").toString();
            for (int j = 0; j < resultList.size(); j++) {//遍历行
                Row1 row1 = resultList.get(j);
                int rownum = row1.rownum;
                for (int k = 0; k < row1.getCells().size(); k++) {//遍历列
                    IdcRack param = (IdcRack) row1.getCells().get(k).getData();
                    if (param.getModulename() != null && param.getModulename().equals(moduleName)) {
                        r = r > rownum ? r : rownum;
                        w = w > row1.getCells().size() + 3 ? w : row1.getCells().size() + 3;
                        h = 2;
                        break;
                    }
                }
            }
            resultMap.put("r", r);
            resultMap.put("c", c);
            resultMap.put("w", w);
            resultMap.put("h", h);
            resultMap.put("n", moduleName);
            resultMap.put("sty", sty);
            resultModuleList.add(resultMap);
        }

        map.put("resultModuleList", com.alibaba.fastjson.JSONObject.toJSON(resultModuleList));
        map.put("room", jsonObject);
        map.put("roomid", modelById.getId());
        map.put("rackStatistics", racks);


        //获取机架id对应关系
        ImmutableMap<String, IdcRack> stringIdcRackImmutableMap = Maps.uniqueIndex(idcRacks, new Function<IdcRack, String>() {
            @Override
            public String apply(IdcRack idcRack) {
                return idcRack.getId().toString();
            }
        });
        List<IdcPdfDayPowerInfoVo> roomPowers = idcPdfDayPowerInfoService.getRoomPowers(roomid);
        //获取机架用电信息 格式为 pdf架Code+mcb号
        Map<String, IdcPdfDayPowerInfoVo> everyMcbMap = new HashMap<>();
        //每路pdf的用电信息
//        Map<String,Double> pdfAmout = new HashMap<>();
        for (IdcPdfDayPowerInfoVo idcPdfDayPowerInfoVo : roomPowers) {
            String pdfCode = idcPdfDayPowerInfoVo.getIdcPdfCode().toLowerCase() + "__" + idcPdfDayPowerInfoVo.getIdcMcbCode();
            everyMcbMap.put(pdfCode, idcPdfDayPowerInfoVo);
        }
        //获取客户架 和pdf架对应关系
        Map<String, List<String>> mcbMaps = new HashMap<>();
        IdcMcb query = new IdcMcb();
        List<IdcMcb> idcMcbs = idcMcbService.queryListByObject(query);
        for (IdcMcb idcMcb : idcMcbs) {
            Long pwrServicerackId = idcMcb.getPwrServicerackId();
            if (pwrServicerackId != null) {
                //获取服务架名称
                IdcRack serviceRack = stringIdcRackImmutableMap.get(pwrServicerackId.toString());
                //安装架即pdf位置
                IdcRack installRack = stringIdcRackImmutableMap.get(idcMcb.getPwrInstalledrackId().toString());
                if (serviceRack == null || installRack == null) {
                    continue;
                }
                String pwrMcbno = idcMcb.getPwrMcbno();
                //通过pdf位置获取每个机架对应的两路 mcb
                String pdfNo = installRack.getCode().toLowerCase() + "__" + pwrMcbno;
                //该客户的服务架是否已经存在
                List<String> strings = mcbMaps.get(serviceRack.getCode());
                if (strings == null) {
                    strings = new ArrayList<>();
                    mcbMaps.put(serviceRack.getCode(), strings);
                }
                strings.add(pdfNo);
            }
        }
//        Map<String,Map<String,Object>> rackDates = new HashMap<>();
//        Map<String,Double> result = new HashMap<>();
//        Map<String,Object> resultdeta = new HashMap<>();
//        for (IdcRack rack : idcRacks) {
//            String rackCode = rack.getId().toString();
//            if("equipment".equals(rack.getBusinesstypeId())||"cabinet".equals(rack.getBusinesstypeId()) ){
//                List<Map<String,Object>> dates = new ArrayList<>();
//                double idcAmout = 0.0d;
//                //获取对应的架的两路mcb
//                List<String> strings = mcbMaps.get(rack.getCode());
//                Map<String, Object> objectMap = rackDates.get(rackCode);
//                if(objectMap==null){
//                    objectMap = new HashMap<>();
//                }
//                if(strings!=null){
//                    //每一个mcb 对应的数据
//                    for (String string : strings) {
//                        //获取每路的电量
//                        IdcPdfDayPowerInfoVo idcPdfDayPowerInfoVo = everyMcbMap.get(string);
//                        Map<String,Object> tmap = new HashMap<>();
//                        tmap.put("mcb",string);
//                        if(idcPdfDayPowerInfoVo!=null){
//                            tmap.put("power",idcPdfDayPowerInfoVo.getIdcAmout());
//                            idcAmout+= idcPdfDayPowerInfoVo.getIdcAmout();
//                        }else{
//                            tmap.put("power",0);
//                        }
//                        dates.add(tmap);
//                    }
//                }
//                resultdeta.put(rackCode,dates);
//                result.put(rackCode,idcAmout);
//            }
//        }
        com.alibaba.fastjson.JSONArray jsonArray = (com.alibaba.fastjson.JSONArray) com.alibaba.fastjson.JSONArray.toJSON(resultList);
        Map<String, Object> tMap;
        List<String> strings;
        List<Map<String, Object>> dates;
        IdcPdfDayPowerInfoVo idcPdfDayPowerInfoVo;
        double allpower = 0D, diff = 0D;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        for (Object object : jsonArray) {
            com.alibaba.fastjson.JSONObject o = (com.alibaba.fastjson.JSONObject) object;
            com.alibaba.fastjson.JSONArray childArr = (com.alibaba.fastjson.JSONArray) o.get("cells");
            if (childArr != null) {
                for (Object childobject : childArr) {
                    com.alibaba.fastjson.JSONObject childJson = (com.alibaba.fastjson.JSONObject) childobject;
                    com.alibaba.fastjson.JSONObject data = (com.alibaba.fastjson.JSONObject) childJson.get("data");
                    if (data != null) {
                        Object code = data.get("code");
                        if (code == null) continue;
                        String rackid = code.toString();
                        strings = mcbMaps.get(rackid);
                        if (strings == null) continue;
                        allpower = 0D;
                        diff = 0D;
                        dates = new ArrayList<>();
                        for (String string : strings) {
                            idcPdfDayPowerInfoVo = everyMcbMap.get(string);
                            if (idcPdfDayPowerInfoVo != null) {
                                allpower += idcPdfDayPowerInfoVo.getIdcAmout();
                                diff += idcPdfDayPowerInfoVo.getIdcBeforeDiff();
                                tMap = new HashMap<>();
                                tMap.put("mcb", string.split("__")[1]);
                                tMap.put("read", idcPdfDayPowerInfoVo.getIdcAmout());
                                tMap.put("power", idcPdfDayPowerInfoVo.getIdcBeforeDiff());
                                dates.add(tMap);
                            }
                        }
                        data.put("powerdetail", dates);
                        data.put("power", numberFormat.format(diff));
                        data.put("powerread", numberFormat.format(allpower));
                    }
                }
            }
        }
        map.put("resultList", jsonArray);
        return "pe/rack";
    }

    @RequestMapping("/pe/his/rack/{rackid}")
    @ResponseBody
    public Map<String, Object> getRackHis(@PathVariable long rackid) {
        Map<String, Object> result = new HashMap<>();
        List<IdcPdfDayPowerInfoVo> list = idcPdfDayPowerInfoService.getHisMonth(rackid);
        Map<String, Map<Long, Double>> dtmap = new HashMap<>();
        Set<Long> times = new TreeSet<>();
        for (IdcPdfDayPowerInfoVo idcPdfDayPowerInfoVo : list) {
            times.add(idcPdfDayPowerInfoVo.getIdcCreateTime().getTime());
            String idcMcbCode = idcPdfDayPowerInfoVo.getIdcMcbCode();
            Map<Long, Double> stringDoubleMap = dtmap.get(idcMcbCode);
            if (stringDoubleMap == null) {
                stringDoubleMap = new HashMap<>();
                dtmap.put(idcMcbCode, stringDoubleMap);
            }
            stringDoubleMap.put(idcPdfDayPowerInfoVo.getIdcCreateTime().getTime(), idcPdfDayPowerInfoVo.getIdcBeforeDiff());
        }
        List<String> timestr = new ArrayList<>();
        List<Map<String, Object>> lines = new ArrayList<>();
        for (Map.Entry<String, Map<Long, Double>> stringMapEntry : dtmap.entrySet()) {
            List<Double> datas = new ArrayList<>();
            Map<String, Object> line = new HashMap<>();
            Map<Long, Double> value = stringMapEntry.getValue();
            for (Long time : times) {
                String format = DateFormatUtils.format(new Date(time), "yyyy-MM-dd");
                if (!timestr.contains(format)) {
                    timestr.add(format);
                }
                Double idcPdfDayPowerInfoVo = value.get(time);
                if (idcPdfDayPowerInfoVo == null) {
                    idcPdfDayPowerInfoVo = 0D;
                }
                datas.add(idcPdfDayPowerInfoVo);
            }
            line.put("name", stringMapEntry.getKey());
            line.put("data", datas);
            lines.add(line);
        }
        result.put("times", timestr);
        result.put("lines", lines);
        result.put("ok", true);
        return result;
    }

    @RequestMapping("/pe/toroomlist")
    public String toRoomList() {
        return "pe/roomlist";
    }

    @RequestMapping("/pe/roomlist")
    @ResponseBody
    public EasyUIData RoomList(Long buildid, String startTime, String endTime) {
        EasyUIData easyUIData = new EasyUIData();
        try {
            ZbMachineroom query = new ZbMachineroom();
            query.setBuildingid(buildid.toString());
            List<ZbMachineroom> zbMachinerooms = zbMachineroomService.queryListByObject(query);

            List<IdcPowerRoomInfo> day = idcPowerRoomInfoService.queryByTime(null, startTime, endTime, "day");
            Map<String, IdcPowerRoomInfo> stringIdcPowerRoomInfoImmutableMap = new HashMap<>();
            IdcPowerRoomInfo tmp;
            for (IdcPowerRoomInfo idcPowerRoomInfo : day) {
                String sysCode = idcPowerRoomInfo.getSysCode();
                tmp = stringIdcPowerRoomInfoImmutableMap.get(sysCode);
                if (tmp == null) {
                    stringIdcPowerRoomInfoImmutableMap.put(sysCode, idcPowerRoomInfo);
                } else {
                    if (tmp.getIdcCreateTime().before(idcPowerRoomInfo.getIdcCreateTime())) {
                        idcPowerRoomInfo.setIdcBeforeDiff(tmp.getIdcBeforeDiff() + idcPowerRoomInfo.getIdcBeforeDiff());
                        idcPowerRoomInfo.setIdcDeviceBeforeDiff(tmp.getIdcDeviceBeforeDiff() + idcPowerRoomInfo.getIdcDeviceBeforeDiff());
                        idcPowerRoomInfo.setIdcAirAdjustBeforeDiff(tmp.getIdcAirAdjustBeforeDiff() + idcPowerRoomInfo.getIdcAirAdjustBeforeDiff());
                        stringIdcPowerRoomInfoImmutableMap.put(sysCode, idcPowerRoomInfo);
                    } else {
                        tmp.setIdcBeforeDiff(tmp.getIdcBeforeDiff() + idcPowerRoomInfo.getIdcBeforeDiff());
                        tmp.setIdcDeviceBeforeDiff(tmp.getIdcDeviceBeforeDiff() + idcPowerRoomInfo.getIdcDeviceBeforeDiff());
                        tmp.setIdcAirAdjustBeforeDiff(tmp.getIdcAirAdjustBeforeDiff() + idcPowerRoomInfo.getIdcAirAdjustBeforeDiff());
                    }
                }
            }
//            ImmutableMap<String, IdcPowerRoomInfo> stringIdcPowerRoomInfoImmutableMap = Maps.uniqueIndex(day, new Function<IdcPowerRoomInfo, String>() {
//                @Override
//                public String apply(IdcPowerRoomInfo idcPowerRoomInfo) {
//                    return idcPowerRoomInfo.getSysCode();
//                }
//            });
            List<IdcPowerRoomInfo> results = new ArrayList<>();
            for (ZbMachineroom zbMachineroom : zbMachinerooms) {
                IdcPowerRoomInfo idcPowerRoomInfo = stringIdcPowerRoomInfoImmutableMap.get(zbMachineroom.getSitename());
                if (idcPowerRoomInfo == null) {
                    idcPowerRoomInfo = new IdcPowerRoomInfo();
                }
                idcPowerRoomInfo.setIdcPowerRoomId(zbMachineroom.getId().toString());
                idcPowerRoomInfo.setIdcPowerRoomName(zbMachineroom.getSitename());
                results.add(idcPowerRoomInfo);
            }
            easyUIData.setRows(results);
            easyUIData.setTotal(results.size());
        } catch (Exception e) {
            logger.error("", e);
        }
        return easyUIData;
    }

    @RequestMapping("/pe/toracklist")
    public String toRackList() {
        return "pe/racklist";
    }

    @RequestMapping("/pe/racklist")
    @ResponseBody
    public EasyUIData RackList(Long roomid, Long userid, String startTime, String endTime) {
        EasyUIData easyUIData = new EasyUIData();
        try {
            IdcRack rackQuery = new IdcRack();
            if (roomid != null) {
                rackQuery.setRoomid(roomid.toString());
            }
            if (userid != null) {
                rackQuery.setActualcustomerid(userid);
            }
            List<IdcRack> idcRacks = idcRackService.queryListByObject(rackQuery);
            if (idcRacks != null && idcRacks.size() > 0) {


                //获取机架id对应关系
                final List<String> rackids = new ArrayList<>();
                ImmutableMap<String, IdcRack> stringIdcRackImmutableMap = Maps.uniqueIndex(idcRacks, new Function<IdcRack, String>() {
                    @Override
                    public String apply(IdcRack idcRack) {
                        rackids.add(idcRack.getId().toString());
                        return idcRack.getId().toString();
                    }
                });
                List<IdcPdfDayPowerInfoVo> roomPowers = idcPdfDayPowerInfoService.getRoomPowers(roomid, userid, startTime, endTime);
                Map<String, IdcPdfDayPowerInfoVo> everyMcbMap = new HashMap<>();
                //每路pdf的用电信息
//        Map<String,Double> pdfAmout = new HashMap<>();
                for (IdcPdfDayPowerInfoVo idcPdfDayPowerInfoVo : roomPowers) {
                    String pdfCode = idcPdfDayPowerInfoVo.getIdcPdfCode().toLowerCase() + "__" + idcPdfDayPowerInfoVo.getIdcMcbCode();
                    everyMcbMap.put(pdfCode, idcPdfDayPowerInfoVo);
                }
                Map<String, List<String>> mcbMaps = new HashMap<>();
                Map<String, Object> query = new HashMap<>();
                query.put("ids", rackids);
                List<IdcMcb> idcMcbs = idcMcbService.queryListByMap(query);
                for (IdcMcb idcMcb : idcMcbs) {
                    Long pwrServicerackId = idcMcb.getPwrServicerackId();
                    if (pwrServicerackId != null) {
                        //获取服务架名称
                        IdcRack serviceRack = stringIdcRackImmutableMap.get(pwrServicerackId.toString());
                        //安装架即pdf位置
                        IdcRack installRack = stringIdcRackImmutableMap.get(idcMcb.getPwrInstalledrackId().toString());
                        if (serviceRack == null || installRack == null) {
                            continue;
                        }
                        String pwrMcbno = idcMcb.getPwrMcbno();
                        //通过pdf位置获取每个机架对应的两路 mcb
                        String pdfNo = installRack.getCode().toLowerCase() + "__" + pwrMcbno;
                        //该客户的服务架是否已经存在
                        List<String> strings = mcbMaps.get(serviceRack.getCode());
                        if (strings == null) {
                            strings = new ArrayList<>();
                            mcbMaps.put(serviceRack.getCode(), strings);
                        }
                        strings.add(pdfNo);
                    }
                }
                List<Map<String, Object>> result = new ArrayList<>();
                Map<String, Object> map;
                for (IdcRack idcRack : idcRacks) {
                    map = new HashMap<>();
                    map.put("rackName", idcRack.getName());
                    map.put("rackId", idcRack.getId());
                    String code = idcRack.getCode();
                    List<String> strings = mcbMaps.get(code);
                    if (strings != null) {
                        for (int i = 1; i <= 2; i++) {
                            String s = strings.get(i - 1);
                            IdcPdfDayPowerInfoVo idcPdfDayPowerInfoVo = everyMcbMap.get(s);
                            if (idcPdfDayPowerInfoVo != null) {
                                map.put("read" + i, idcPdfDayPowerInfoVo.getIdcAmout());
                                map.put("diff" + i, idcPdfDayPowerInfoVo.getIdcBeforeDiff());
                            } else {
                                map.put("read" + i, 0);
                                map.put("diff" + i, 0);
                            }
                        }
                    } else {
                        for (int i = 0; i <= 2; i++) {
                            map.put("read" + i, 0);
                            map.put("diff" + i, 0);
                        }
                    }
                    result.add(map);
                }
                easyUIData.setRows(result);
                easyUIData.setTotal(result.size());
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return easyUIData;
    }

    @RequestMapping("/pe/touserlist")
    public String toUserList() {
        return "pe/userlist";
    }


    @RequestMapping("/pe/toroomhis/{roomid}")
    public String toroomHis(@PathVariable String roomid) {
        return "/pe/roomhis";
    }

    @RequestMapping("/pe/torackhis/{rackid}")
    public String torackHis(@PathVariable String rackid) {
        return "/pe/rackhis";
    }
}
