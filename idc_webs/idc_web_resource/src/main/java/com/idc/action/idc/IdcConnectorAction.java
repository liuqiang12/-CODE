package com.idc.action.idc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcConnector;
import com.idc.model.IdcRack;
import com.idc.service.IdcConnectorService;
import com.idc.service.IdcLinkService;
import com.idc.service.IdcRackService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;
import utils.plugins.excel.ExcelHelper;

import java.io.OutputStream;
import java.util.*;

/**
 * Created by gaowei on 2017/6/5.
 */
@Controller
@RequestMapping("/idcConnector")
public class IdcConnectorAction extends BaseController {

    @Autowired
    private IdcConnectorService idcConnectorService;
    @Autowired
    private IdcRackService idcRackService;
    @Autowired
    private IdcLinkService linkService;

    /**
     * 查询端子信息
     *
     * @param page
     * @param name
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page,String name,String rackId) {
        EasyUIData easyUIData = new EasyUIData();
        IdcConnector idcConnector = new IdcConnector();
        idcConnector.setName(name);
        idcConnector.setRackId(rackId);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (page == null || page.getPage() < 0) {//所有
            list = idcConnectorService.queryAllIdcConnectorInfoList(idcConnector);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = idcConnectorService.queryIdcConnectorInfoListPage(page, idcConnector);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }
    //准备新增端子
    @RequestMapping("/preAddIdcConnectorInfo.do")
    public String preAddIdcConnectorInfo(String odfRackId,ModelMap map){
        map.addAttribute("odfRackId",odfRackId);
        return "connector/info";
    }
    /**
     * 新增/修改端子
     * @return
     */
    @RequestMapping("/addIdcConnector.do")
    @ResponseBody
    public ExecuteResult addIdcConnector(IdcConnector idcConnector,Integer id) {
        ExecuteResult executeResult = new ExecuteResult();
        if (id != null) {//修改
            idcConnector.setId(id);
            idcConnector.setUpdatedate(new Date());
            try{
                idcConnectorService.updateByObject(idcConnector);
                executeResult.setState(true);
                executeResult.setMsg("修改端子信息成功");
            }catch(Exception e){
                executeResult.setState(false);
                executeResult.setMsg("修改端子信息失败");
                e.printStackTrace();
            }
        } else {//新增
            try{
                idcConnector.setCreatedate(new Date());
                idcConnectorService.insert(idcConnector);
                executeResult.setState(true);
                executeResult.setMsg("新增端子成功");
            }catch (Exception e){
                executeResult.setState(false);
                executeResult.setMsg("新增端子失败");
                e.printStackTrace();
            }
        }
        return executeResult;
    }
    /**
     * 查询端子详情
     * @param id 主键
     * @return
     */
    @RequestMapping("/getIdcConnectorInfo")
    public String getIdcConnectorInfo(Long id,ModelMap map,String buttonType,String odfRackId) {
        IdcConnector idcConnector = null;
        IdcRack idcrack = null;
        try{
            idcConnector = idcConnectorService.getModelById(id);
            idcrack = idcRackService.getModelById(Integer.parseInt(idcConnector.getRackId()));
        }catch(Exception e ){
            e.printStackTrace();
        }
        map.addAttribute("id",id);
        map.addAttribute("buttonType",buttonType);
        map.addAttribute("odfRackId",odfRackId);
        map.addAttribute("idcrack", idcrack);
        map.addAttribute("idcConnector",idcConnector);
        return "connector/info";
    }

    /**
     * 端子删除
     * @return
     */
    @RequestMapping("/deleteConnector.do")
    @ResponseBody
    public ExecuteResult delIdcConnector(String ids){
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> listIdc = Arrays.asList(ids.split(","));
            idcConnectorService.deleteByList(listIdc);
            executeResult.setState(true);
            executeResult.setMsg("端子删除成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("端子删除失败");
        }
        return  executeResult;
    }

    /**
     * 导出端子数据
     *
     * @return
     */
    @RequestMapping("/exportDeviceData.do")
    @ResponseBody
    public ExecuteResult exportResourceData() {
        ExecuteResult executeResult = new ExecuteResult();
        String title = "端子信息";
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("NAME", "名称");
        headMap.put("CODE", "编码");
        headMap.put("BNAME", "所属机架");
        headMap.put("CONNECTORTYPE", "连接类型");
        headMap.put("TYPE", "端口类型");
        headMap.put("PORTMODE", "光口模式");
        headMap.put("STATUS", "业务状态");
        headMap.put("TICKETID", "工单编号");
        headMap.put("CREATEDATE", "创建时间");
        headMap.put("UPDATEDATE", "更新时间");
        headMap.put("MEMO", "备注");
        OutputStream outXlsx = null;
        List<Map<String, Object>> list = idcConnectorService.getAllIdcConnectorInfo();
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename=idcConnector.xlsx");
            //response.setCharacterEncoding("utf-8");
            outXlsx = response.getOutputStream();
            ExcelHelper.exportExcelX(title, headMap, JSONArray.parseArray(JSON.toJSONString(list)), null, 0, outXlsx);
            outXlsx.close();
            executeResult.setState(true);
            executeResult.setMsg("数据导出成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("数据导出失败");
        }
        return executeResult;
    }


    //获取端子列表
    @RequestMapping("/getIdcConnectorList.do")
    public String getIdcConnectorList(Long rackId, ModelMap map,Integer rackIdA,Integer roomId,String operateType){
        map.addAttribute("rackId",rackId);
        map.addAttribute("rackIdA",rackIdA);
        map.addAttribute("roomId",roomId);
        map.addAttribute("operateType",operateType);
        return "connector/connectorList";
    }

    /**
     * 绑定关联关系device--idcLink
     * @param ids
     * @param roomId
     * @param rackIds
     * @param odfRackId
     * @return
     */
    @RequestMapping("/bindConnectionToIdcLink.do")
    @ResponseBody
    public ExecuteResult bindConnectionToIdcLink(String ids, Integer roomId, String rackIds, Integer odfRackId, Long portIdA) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            idcRackService.bindRackAndConnector(ids, roomId, rackIds, odfRackId, portIdA);
            executeResult.setState(true);
            executeResult.setMsg("绑定成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("绑定失败");
            e.printStackTrace();
        }
        return executeResult;
    }

    /**
     * 资源分配端子列表
     *
     * @param name
     * @param rackId
     * @return
     */
    @RequestMapping("/distributionList.do")
    @ResponseBody
    public List<Map<String, Object>> distributionList(String name, String rackId) {
        IdcConnector idcConnector = new IdcConnector();
        idcConnector.setName(name);
        idcConnector.setRackId(rackId);
        idcConnector.setStatus(20L);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = idcConnectorService.queryAllIdcConnectorInfoList(idcConnector);
        return list;
    }

    //通过机架IDS 机架ID获取链路中的端子
    @RequestMapping("/preQueryBindedConnectorList")
    public String preQueryBindedConnectorList(String portIds, ModelMap map) {
        map.addAttribute("portIds", portIds);
        return "connector/bindedConnectorList";
    }

    //通过机架IDS 机架ID获取链路中的端子
    @RequestMapping("/queryBindedConnectorList")
    @ResponseBody
    public EasyUIData queryBindedConnectorList(EasyUIPageBean page, String portIds, String name) {
        EasyUIData easyUIData = new EasyUIData();
        if (page != null || page.getPage() > 0) {
            Map<String, Object> mapQ = new HashedMap();
            mapQ.put("name", name);
            if (portIds != null && !"".equals(portIds)) {
                List<String> portIdList = Arrays.asList(portIds.split(","));
                mapQ.put("portIds", portIdList);
            }
            List<IdcConnector> list = idcConnectorService.queryBindedConnectorList(page, mapQ);
            easyUIData.setRows(page.getItems());
            easyUIData.setTotal(page.getTotalRecord());
        }
        return easyUIData;
    }
}
