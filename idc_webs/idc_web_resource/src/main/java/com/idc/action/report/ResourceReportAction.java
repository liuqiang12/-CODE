package com.idc.action.report;

import com.idc.model.ExecuteResult;
import com.idc.service.IdcBuildingService;
import com.idc.service.IdcLocationService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;
import utils.plugins.excel.ExcelHelper;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/9/15.
 * 资源报表控制类
 */
@Controller
@RequestMapping("/resourceReport")
public class ResourceReportAction extends BaseController {
    @Autowired
    private IdcBuildingService idcBuildingService;
    @Autowired
    private IdcLocationService idcLocationService;

    @RequestMapping("/preRackResourceReport")
    public String preResourceReport() {
        return "/report/index";
    }

    //机架占用表报
    @RequestMapping("/rackResourceReport")
    @ResponseBody
    public Object list(String idcLocationIds, String idcBuildingIds, String roomIds) {
        Map<String, Object> mapQ = new HashedMap();
        List<Map<String, Object>> list = new ArrayList<>();
        List<String> listQ = null;
        if (idcLocationIds != null && !"".equals(idcLocationIds)) {
            listQ = Arrays.asList(idcLocationIds.split(","));
            mapQ.put("locationIds", listQ);
        }
        if (idcBuildingIds != null && !"".equals(idcBuildingIds)) {//机楼
            listQ = Arrays.asList(idcBuildingIds.split(","));
            mapQ.put("buildingIds", listQ);
        }
        if (roomIds != null && !"".equals(roomIds)) {//机房
            listQ = Arrays.asList(roomIds.split(","));
            mapQ.put("roomIds", listQ);
        }
        //获取各个机楼机架统计数据
        list = idcBuildingService.queryResourceReportInfo(mapQ);
        Integer rackCount = 0;
        Integer usedRackCount = 0;
        float rackusage = 0;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> mapR = list.get(i);
            rackCount += mapR.get("RACKCOUNT") == null ? 0 : Integer.parseInt(mapR.get("RACKCOUNT").toString());
            usedRackCount += mapR.get("USEDRACKCOUNT") == null ? 0 : Integer.parseInt(mapR.get("USEDRACKCOUNT").toString());
        }
        rackusage = rackCount == 0 ? 0 : (usedRackCount * 100 / Float.parseFloat(rackCount.toString()));
        BigDecimal hv = new BigDecimal(rackusage);
        hv = hv.setScale(2, 4);
        rackusage = hv.floatValue();

        String rowStr = JSONArray.fromObject(list) + "";
        if (list == null || list.size() < 1) {
            rowStr = "[{\"SITENAME\":\"总计\",\"ROOMID\":1,\"USEDRACKCOUNT\":" + usedRackCount + ",\"RACKCOUNT\":" + rackCount + ",\"BUILDINGID\":1,\"RACKUSAGE\":" + rackusage + "}]";
        } else {
            rowStr = "[" + rowStr.substring(1, rowStr.length() - 1) + ",{\"SITENAME\":\"总计\",\"ROOMID\":1,\"USEDRACKCOUNT\":" + usedRackCount + ",\"RACKCOUNT\":" + rackCount + ",\"BUILDINGID\":1,\"RACKUSAGE\":" + rackusage + "}]";
        }
        String jsonStr = "{\"total\":" + list.size() + ",\"rows\":" + rowStr + "}";
        JSONObject result = JSONObject.fromObject(jsonStr);
        return result;
    }

    //资源分配报表
    @RequestMapping("/preDistributionResourceReport")
    public String preDistributionResourceReport() {
        return "/report/distributionResourceReport";
    }

    @RequestMapping("/distributionResourceReport")
    @ResponseBody
    public EasyUIData distributionResourceReport(EasyUIPageBean page, String name, String beginTime, String endTime) {
        EasyUIData easyUIData = new EasyUIData();
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("name", name);
        mapQ.put("beginTime", beginTime);
        mapQ.put("endTime", endTime);
        List<Map<String, Object>> list = idcLocationService.queryDistributionResourceList(page, mapQ);
        easyUIData.setTotal(page.getTotalRecord());
        easyUIData.setRows(page.getItems());
        return easyUIData;
    }

    //ip占用报表
    @RequestMapping("/preDistributionIpReport")
    public String preDistributionIpReport() {
        return "/report/distributionIpReport";
    }

    @RequestMapping("/distributionIpReport")
    @ResponseBody
    public EasyUIData distributionIpReport(EasyUIPageBean page, String name) {
        EasyUIData easyUIData = new EasyUIData();
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("name", name);
        List<Map<String, Object>> list = idcLocationService.queryDistributionIpList(page, mapQ);
        easyUIData.setTotal(page.getTotalRecord());
        easyUIData.setRows(page.getItems());
        return easyUIData;
    }

    //资源回收报表
    @RequestMapping("/preRecycleResourceReport")
    public String preRecycleResourceReport() {
        return "/report/recycleResourceReport";
    }

    @RequestMapping("/recycleResourceReport")
    @ResponseBody
    public EasyUIData recycleResourceReport(EasyUIPageBean page, String name) {
        EasyUIData easyUIData = new EasyUIData();
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("name", name);
        List<Map<String, Object>> list = idcLocationService.recycleResourceReport(page, mapQ);
        easyUIData.setTotal(page.getTotalRecord());
        easyUIData.setRows(page.getItems());
        return easyUIData;
    }

    /*==========================================导出===================================================*/

    //导出占用机架报表数据
    @RequestMapping("/exportUsedRackReportData")
    @ResponseBody
    public ExecuteResult exportUsedRackReportData() {
        ExecuteResult executeResult = new ExecuteResult();
        String title = "占用机架报表.xlsx";
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("SITENAME", "机房名称");
        headMap.put("RACKCOUNT", "机架总数");
        headMap.put("USEDRACKCOUNT", "机架占用数");
        headMap.put("RACKUSAGE", "机架占用率");
        OutputStream outXlsx = null;
        Map<String, Object> mapQ = new HashedMap();
        List<Map<String, Object>> list = idcBuildingService.queryResourceReportInfo(mapQ);
        Integer rackCount = 0;
        Integer usedRackCount = 0;
        float rackusage = 0;
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> mapR = list.get(i);
            rackCount += mapR.get("RACKCOUNT") == null ? 0 : Integer.parseInt(mapR.get("RACKCOUNT").toString());
            usedRackCount += mapR.get("USEDRACKCOUNT") == null ? 0 : Integer.parseInt(mapR.get("USEDRACKCOUNT").toString());
        }
        rackusage = rackCount == 0 ? 0 : (usedRackCount * 100 / Float.parseFloat(rackCount.toString()));
        BigDecimal hv = new BigDecimal(rackusage);
        hv = hv.setScale(2, 4);
        rackusage = hv.floatValue();
        Map<String, Object> map = new HashedMap();
        map.put("SITENAME", "总计");
        map.put("ROOMID", "");
        map.put("USEDRACKCOUNT", usedRackCount);
        map.put("RACKCOUNT", rackCount);
        map.put("BUILDINGID", "");
        map.put("RACKUSAGE", rackusage);
        list.add(map);
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(title.getBytes("gb2312"), "ISO8859-1"));
            outXlsx = response.getOutputStream();
            ExcelHelper.exportExcelX(title, headMap, com.alibaba.fastjson.JSONArray.parseArray(com.alibaba.fastjson.JSON.toJSONString(list)), null, 0, outXlsx);
            outXlsx.close();
            executeResult.setState(true);
            executeResult.setMsg("数据导出成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("数据导出失败");
        }
        return executeResult;
    }

    //导出资源分配报表数据
    @RequestMapping("/exportUsedResourceReportData")
    @ResponseBody
    public ExecuteResult exportUsedResourceReportData() {
        ExecuteResult executeResult = new ExecuteResult();
        String title = "资源分配报表.xlsx";
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("XQNAME", "需求名称");
        headMap.put("BUSNAME", "业务名称");
        headMap.put("TICKETCATEGORY", "业务类型");
        headMap.put("CUSTOMERNAME", "客户名称");
        headMap.put("CONTACTPHONE", "联系电话");
        headMap.put("ROOMSTR", "占用机房");
        headMap.put("RACKNAMESTR", "占用机架");
        headMap.put("RACKNUM", "占用机架数");
        headMap.put("IPSTR", "占用IP");
        headMap.put("IPNUM", "占用IP数");
        headMap.put("BANDWIDTH", "分配带宽");
        headMap.put("PROCTICKETSTATUS", "业务状态");
        headMap.put("CREATETIME", "开通时间");
        headMap.put("DISTRITIME", "分配时间");
        headMap.put("REMARK", "备注");
        OutputStream outXlsx = null;
        List<Map<String, Object>> list = idcLocationService.queryAllDistributionResourceList();
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(title.getBytes("gb2312"), "ISO8859-1"));
            outXlsx = response.getOutputStream();
            ExcelHelper.exportExcelX(title, headMap, com.alibaba.fastjson.JSONArray.parseArray(com.alibaba.fastjson.JSON.toJSONString(list)), null, 0, outXlsx);
            outXlsx.close();
            executeResult.setState(true);
            executeResult.setMsg("数据导出成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("数据导出失败");
        }
        return executeResult;
    }

    //导出回收资源报表数据
    @RequestMapping("/exportRecycleResourceReportData")
    @ResponseBody
    public ExecuteResult exportRecycleResourceReportData() {
        ExecuteResult executeResult = new ExecuteResult();
        String title = "资源回收报表.xlsx";
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("BUSITYPE", "需求名称");
        headMap.put("BUSNAME", "业务名称");
        headMap.put("TICKETCATEGORY", "业务类型");
        headMap.put("ROOMSTR", "占用机房");
        headMap.put("RACKSTR", "占用机架");
        headMap.put("RACKNUM", "占用机架数");
        headMap.put("IPSTR", "占用IP");
        headMap.put("IPNUM", "占用IP数");
        headMap.put("BANDWIDTH", "分配带宽");
        headMap.put("CYSTIME", "回收时间");
        headMap.put("CONNECTSTR", "联系方式");
        OutputStream outXlsx = null;
        List<Map<String, Object>> list = idcLocationService.recycleAllResourceReport();
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(title.getBytes("gb2312"), "ISO8859-1"));
            outXlsx = response.getOutputStream();
            ExcelHelper.exportExcelX(title, headMap, com.alibaba.fastjson.JSONArray.parseArray(com.alibaba.fastjson.JSON.toJSONString(list)), null, 0, outXlsx);
            outXlsx.close();
            executeResult.setState(true);
            executeResult.setMsg("数据导出成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("数据导出失败");
        }
        return executeResult;
    }

    //导出IP占用报表数据
    @RequestMapping("/exportUsedIpReportData")
    @ResponseBody
    public ExecuteResult exportUsedIpReportData() {
        ExecuteResult executeResult = new ExecuteResult();
        String title = "IP占用报表报表.xlsx";
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("BUSNAME", "业务名称");
        headMap.put("BEGINIP", "占用IP");
        headMap.put("ENDIP", "占用IP");
        headMap.put("BANDWIDTH", "占用带宽");
        headMap.put("LINKNUM", "链路条数");
        headMap.put("RACKNAMESTR", "占用机架");
        headMap.put("CONTACTNAME", "联系人");
        headMap.put("CONTACTPHONE", "联系电话");
        headMap.put("CONTACTEMAIL", "邮箱");
        OutputStream outXlsx = null;
        List<Map<String, Object>> list = idcLocationService.queryAllDistributionIpList();
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(title.getBytes("gb2312"), "ISO8859-1"));
            outXlsx = response.getOutputStream();
            ExcelHelper.exportExcelX(title, headMap, com.alibaba.fastjson.JSONArray.parseArray(com.alibaba.fastjson.JSON.toJSONString(list)), null, 0, outXlsx);
            outXlsx.close();
            executeResult.setState(true);
            executeResult.setMsg("数据导出成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("数据导出失败");
        }
        return executeResult;
    }
}
