package com.idc.action.idc;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.idc.action.ImportExcelUtils;
import com.idc.bo.RoomPE;
import com.idc.model.*;
import com.idc.service.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;
import utils.plugins.excel.ExcelHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;


@Controller
@RequestMapping("/idcRack")
public class IdcRackAction extends BaseController {

    @Autowired
    private IdcRackService idcRackService;
    @Autowired
    private ZbMachineroomService zbMachineroomService;
    @Autowired
    private IdcConnectorService idcConnectorService;
    @Autowired
    private IdcMcbService idcMcbService;
    @Autowired
    private PowerEnvService powerEnvService;

    /**
     * 查询IDC机架
     *
     * @return
     */
    @RequestMapping(value = "list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, IdcRack idcRack) {
        EasyUIData easyUIData = new EasyUIData();
        if(Lists.newArrayList("odf", "ddf", "wiring").contains(idcRack.getBusinesstypeId())){
            idcRack.setDftype(idcRack.getBusinesstypeId());
            idcRack.setBusinesstypeId(null);
        }
        List<Map<String,Object>> list = new ArrayList<>();
        if (page == null || page.getPage() < 0) {//所有
            list = idcRackService.queryAllIdcRackInfoList();
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = idcRackService.queryIdcRackInfoListPage(page, idcRack);
            //获取机架电量
            Map<String, RoomPE> rackPe = powerEnvService.getLastPEMap();
            RoomPE roomPE = null;
            for (int i = 0; i < list.size(); i++) {
                roomPE = rackPe.get(list.get(i).get("ID").toString());
                if (roomPE != null) {
                    list.get(i).put("ELECTRICNUM", roomPE.getElectric());
                } else {
                    list.get(i).put("ELECTRICNUM", "");
                }
            }
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(list);
        }
        return easyUIData;
    }

    /**
     * 查询idc数据详情
     *
     * @param id 主键
     * @return
     */
    @RequestMapping("/getIdcRackInfo")
    @ResponseBody
    public Map<String,Object> getIdcLocationInfo(@RequestParam("id") int id) {
        Map<String,Object> map = idcRackService.getMapModelById(id);
        return map;
    }
    /**
     * 查询idc数据详情
     * @param id 主键
     * @return
     */
    @RequestMapping("/{action}/{type}/{id}")
    public String toPage(@PathVariable String action,@PathVariable String type, @PathVariable int id, ModelMap map) {
        map.put("id", id);
        if(id>0){
            IdcRack modelById = idcRackService.getModelById(id);
            map.put("type", modelById.getBusinesstypeId());
        }else{
            map.put("type", type);
        }
        map.put("action", action);
        return "idcrack/info";
    }


    /**
     * 获取所有可用的机房
     * @return
     */
    @RequestMapping("/ajaxZbMachineroom")
    @ResponseBody
    public List<ZbMachineroom> ajaxZbMachineroom(String idcBuildingIds) {
        List<String> buildingIdList = null;
        List<ZbMachineroom> idcL = null;
        if (idcBuildingIds != null && !"".equals(idcBuildingIds)) {
            buildingIdList = Arrays.asList(idcBuildingIds.split(","));
        }
        idcL = zbMachineroomService.queryListByBuildingIds(buildingIdList);
        return idcL;
    }

    @RequestMapping("/getRackModel")
    @ResponseBody
    public List<Map<String, Object>> getRackModel(){
        List<Map<String, Object>> result = idcRackService.getRackModel();
        return result;
    }
    @RequestMapping("/getInternetexport")
    @ResponseBody
    public List<Map<String, Object>> getInternetexport(){
        List<Map<String, Object>> result = idcRackService.getInternetexport();
        net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(result);
        return result;
    }
    /**
     * 导出机架数据
     * @return
     */
    @RequestMapping("/exportIdcRackData")
    @ResponseBody
    public ExecuteResult exportResourceData(String businesstype,String searchStr) {
        ExecuteResult executeResult = new ExecuteResult();
        String title = "机架信息";
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        if(businesstype!=null&&"pdu".equals(businesstype)){
            headMap.put("NAME","机架名称");headMap.put("CODE","机架编码");headMap.put("SITENAME","所属机房");
            headMap.put("PDU_POWERTYPE","电源类型");headMap.put("PDU_LOCATION","所在位置");
        }else if(businesstype!=null&&"df".equals(businesstype)){
            System.out.println("=====================ODF导出======================");
            headMap.put("NAME","机架名称");headMap.put("CODE","机架编码");headMap.put("SITENAME","所属机房");
            headMap.put("MNAME","机架型号");headMap.put("BUSINESSTYPEID","机架类型");headMap.put("CONNECTORNUM","端子数");
        }else{
            headMap.put("NAME","机架名称");headMap.put("CODE","机架编码");headMap.put("SITENAME","所属机房");
            headMap.put("ROOMAREAID", "所属VIP机房");
            headMap.put("RACKMODEL", "机架型号");
            headMap.put("MCODE", "机架尺寸");
            headMap.put("DIRECTION", "机架方向");
            headMap.put("USEFOR", "用途");
            headMap.put("RENTTYPE", "出租类型");
            headMap.put("ARRANGEMENT", "机位顺序");
            headMap.put("STATUS","业务状态");headMap.put("IPRESOURCE","ip资源");headMap.put("TOPPORTPROPERTY","上联端口");
            headMap.put("BUSINESSTYPEID", "机架类型");
            headMap.put("PDU_POWERTYPE", "电源类型");
            headMap.put("RATEDELECTRICENERGY", "额定电量");
            headMap.put("ELECTRICTYPE","用电类型");headMap.put("ELECTRICENERGY","电量KWH");headMap.put("APPARENETPOWER","电量KVA");
            headMap.put("PDU_LOCATION","所在位置");headMap.put("X","X机房平面图中的坐标");headMap.put("Y","Y机房平面图中的坐标");
            headMap.put("ISRACKINSTALLED", "机架是否已安装");
            headMap.put("HEIGHT", "机架U数");
            headMap.put("RESERVECUSTOMERID", "预留客户");
            headMap.put("INTERNETEXPORT_ID", "互联网出入口");
            headMap.put("TICKET_ID", "工单编号");
            headMap.put("ACTUALCUSTOMER", "归属客户");
            headMap.put("DFTYPE", "类型");
            headMap.put("IDCNO", "IDC编号");
        }
        OutputStream outXlsx = null;
        List<Map<String,Object>> list = null;
        IdcRack idcRack = new IdcRack();
        idcRack.setBusinesstypeId(businesstype);
        if(searchStr!=null&&!"".equals(searchStr)){
            String[] strArr = searchStr.split("_");
            idcRack.setSearchType(strArr[0]);
            idcRack.setSearchId(Long.valueOf(strArr[1]));
        }
        if(businesstype!=null&&"df".equals(businesstype)){
            list = idcRackService.getAllOdfRackInfoList(idcRack);
        }else{
            list = idcRackService.getAllRackInfo(idcRack);
        }
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            //response.setHeader("Content-type", "text/html;charset=UTF-8");
            if(businesstype!=null &&"pdu".equals(businesstype)){
                response.setHeader("Content-Disposition","attachment;filename=pdfRack.xlsx");
            }else if(businesstype!=null &&"df".equals(businesstype)){
                response.setHeader("Content-Disposition","attachment;filename=odfRack.xlsx");
            }else{
                response.setHeader("Content-Disposition","attachment;filename=rack.xlsx");
            }
            //response.setCharacterEncoding("utf-8");
            outXlsx = response.getOutputStream();
            ExcelHelper.exportExcelX(title,headMap, JSONArray.parseArray(JSON.toJSONString(list)),null,0,outXlsx);
            outXlsx.close();
            executeResult.setState(true);
            executeResult.setMsg("数据导出成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("数据导出失败");
        }
        return  executeResult;
    }

    /**
     * 导入
     *
     * @param request
     * @param response
     * @param type
     * @return
     */
    @RequestMapping("/importIdcRackData")
    @ResponseBody
    public ExecuteResult importResourceData(HttpServletRequest request, HttpServletResponse response, String type) {
        ExecuteResult executeResult = new ExecuteResult();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("uploadFile");
        Map<String, Object> map = new HashMap<String, Object>();
        if (file.isEmpty()) {
            map.put("result", "error");
            map.put("msg", "上传文件不能为空");
        } else {
            String originalFilename = file.getOriginalFilename();
            try {
                List<List<Object>> list = ImportExcelUtils.getDataListByExcel(file.getInputStream(), originalFilename);
                idcRackService.importIdcRackByExcel(list, type);
                executeResult.setState(true);
                executeResult.setMsg("导入成功");
            } catch (Exception e) {
                e.printStackTrace();
                executeResult.setState(false);
                executeResult.setMsg("导入失败");
            }
        }
        return executeResult;
    }
    /**
     * 机架删除
     * @param list
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public ExecuteResult deleteResourceInfos(@RequestParam("list") String list) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            String[] arr = list.split(",");
            List<String> listIdc = Arrays.asList(arr);
            //idcRackService.deleteByList(listIdc);
            idcRackService.updateRackToInvalidByIds(listIdc);
            executeResult.setState(true);
            executeResult.setMsg("机架删除成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("机架删除失败");
            e.printStackTrace();
        }
        return  executeResult;
    }
    //获取PDF列表add by lilj
    @RequestMapping(value = "pdfList.do")
    @ResponseBody
    public EasyUIData pdfList(EasyUIPageBean page, String name,String businesstype,String roomId,Integer statu,String searchType,Long searchId) {
        EasyUIData easyUIData = new EasyUIData();
        IdcRack idcRack = new IdcRack();
        idcRack.setBusinesstypeId(businesstype);
        idcRack.setRoomid(roomId);
        idcRack.setStatus(statu);
        idcRack.setSearchKey(name);
        idcRack.setSearchType(searchType);
        idcRack.setSearchId(searchId);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if (page == null || page.getPage() < 0) {//所有
            list = idcRackService.queryPdfRackInfoList(idcRack);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = idcRackService.queryPdfRackInfoListPage(page, idcRack);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    //新增机架
    @RequestMapping("/addIdcRackInfo.do")
    @ResponseBody
    public ExecuteResult addOrUpdateResourceInfo(IdcRack idcRack, Integer rackId, Integer mcbNum) {
        ExecuteResult executeResult = new ExecuteResult();
        if(idcRack != null){
            try {
                idcRackService.insertOrUpdate(idcRack, rackId, mcbNum);
                executeResult.setState(true);
                executeResult.setMsg("保存机架成功");
            } catch (Exception e) {
                e.printStackTrace();
                executeResult.setState(false);
                executeResult.setMsg("保存机架失败");
            }
        }else{
            executeResult.setState(false);
            executeResult.setMsg("新增机架失败,没有机架信息");
        }
        return executeResult;
    }
    //根据ID获取机架详细信息
    @RequestMapping("/idcRackDetails.do")
    public String getDeviceDetails(Integer rackId, ModelMap map, String businesstype, String buttonType) {
        logger.debug("==================根据主键加载机架信息================");
        IdcRack idcRack = null;
        try {
            idcRack = idcRackService.getModelById(rackId);
        }catch (Exception e){
            e.printStackTrace();
            idcRack=new IdcRack();
        }
        //添加日志
        map.addAttribute("idcRack",idcRack);
        map.addAttribute("rackId",rackId);
        map.addAttribute("businesstype",businesstype);
        map.addAttribute("buttonType", buttonType);
        if(businesstype!=null&&"pdu".equals(businesstype)){
            return "pdf/info";
        } else if (businesstype != null && "odf".equals(businesstype)) {
            return "odf/info";
        } else {
            return "idcrack/info";
        }
    }
    //获取ODF架和段子对应的机架集合
    @RequestMapping(value = "odfList.do")
    @ResponseBody
    public EasyUIData odfList(EasyUIPageBean page, String name,String businesstype,String roomId,String searchType,Long searchId) {
        EasyUIData easyUIData = new EasyUIData();
        IdcRack idcRack = new IdcRack();
        idcRack.setBusinesstypeId(businesstype);
        idcRack.setRoomid(roomId);
        idcRack.setSearchKey(name);
        idcRack.setSearchType(searchType);
        idcRack.setSearchId(searchId);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        if (page == null || page.getPage() < 0) {//所有
            list = idcRackService.queryOdfRackInfoList(idcRack);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = idcRackService.queryOdfRackInfoListPage(page, idcRack);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    /*资源分配，获取当前机房下所有客户柜*/
    @RequestMapping("/getIdcRackList")
    public String getIdcRackList(String roomId, ModelMap map) {
        map.addAttribute("roomId", roomId);
        return "idcrack/idcRackList";
    }


    @RequestMapping("/viewodf/{id}")
    public String viewodf(@PathVariable String id,ModelMap map){
        IdcConnector idcConnector =new IdcConnector();
        idcConnector.setRackId(id);
        IdcRack modelById = idcRackService.getModelById(Integer.parseInt(id));
        List<IdcConnector> idcConnectors = idcConnectorService.queryListByObject(idcConnector);
        map.put("conns", idcConnectors);
        map.put("rack", modelById);
        return "odf/viewodf";
    }
    @RequestMapping("/viewpdf/{id}")
    public String viewpdf(@PathVariable long id,ModelMap map){
        IdcMcb idcMcb =new IdcMcb();
        idcMcb.setPwrInstalledrackId(id);
        IdcRack modelById = idcRackService.getModelById(Integer.valueOf(id+""));
        List<IdcMcb> idcMcbs = idcMcbService.queryListByObject(idcMcb);
        map.put("idcMcbs", idcMcbs);
        map.put("rack", modelById);
        return "pdf/viewpdf";
    }

    /**
     * 资源分配-准备选择ODF机架
     * @param roomId
     * @param rackIds
     * @param map
     * @return
     */
    @RequestMapping("/preDistributionOdfRackList")
    public String distributionOdfRackList(Integer roomId, String rackIds, Long portIdA, ModelMap map) {
        map.addAttribute("roomId",roomId);
        map.addAttribute("rackIds", rackIds);
        map.addAttribute("portIdA", portIdA);
        return "idcrack/distributionOdfRackList";
    }

    /**
     * 资源分配-选择ODF机架
     * @param page
     * @param name
     * @param roomId
     * @return
     */
    @RequestMapping("/distributionOdfRackList.do")
    @ResponseBody
    public EasyUIData distributionOdfRackList(EasyUIPageBean page,String name,String roomId) {
        EasyUIData easyUIData = new EasyUIData();
        IdcRack idcRack = new IdcRack();
        idcRack.setRoomid(roomId);
        idcRack.setSearchKey(name);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        list = idcRackService.queryDistributionOdfRackInfoListPage(page,idcRack);
        easyUIData.setTotal(page.getTotalRecord());
        easyUIData.setRows(page.getItems());
        return easyUIData;
    }

    /**
     * 准备获取机架组列表
     *
     * @param rackIds
     * @param map
     * @return
     */
    @RequestMapping("/getPreRackListByRackIds")
    public String getPreRackListByRackIds(String rackIds, ModelMap map) {
        map.addAttribute("rackIds", rackIds);
        return "idclink/idcRackGroupList";
    }

    /**
     * 获取机架组列表
     *
     * @param page
     * @param name
     * @param rackIds
     * @return
     */
    @RequestMapping("/getRackListByRackIds")
    @ResponseBody
    public EasyUIData getRackListByRackIds(EasyUIPageBean page, String name, String rackIds) {
        EasyUIData easyUIData = new EasyUIData();
        if (rackIds != null && !"".equals(rackIds)) {
            List<String> rackList = Arrays.asList(rackIds.split("-"));
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashedMap();
            map.put("ids", rackList);
            map.put("name", name);
            list = idcRackService.getRackListByRackIds(page, map);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    /*资源分配，获取当前机房PDF列表*/
    @RequestMapping("/preDistributionPdfRackList")
    public String preDistributionPdfRackList(String roomId, String rackId, ModelMap map) {
        map.addAttribute("roomId", roomId);
        map.addAttribute("rackId", rackId);
        return "pdf/distributionPdfRackList";
    }

    /*资源分配，获取当前机房PDF列表*/
    @RequestMapping("/distributionPdfRackList")
    @ResponseBody
    public EasyUIData distributionPdfRackList(EasyUIPageBean page, String name, String roomId) {
        EasyUIData easyUIData = new EasyUIData();
        if (roomId != null && !"".equals(roomId)) {
            IdcRack idcRack = new IdcRack();
            idcRack.setRoomid(roomId);
            idcRack.setBusinesstypeId("pdu");
            idcRack.setSearchKey(name);
            List<IdcRack> list = idcRackService.distributionPdfRackList(page, idcRack);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    /*获取上下架设备所要选择的机架*/
    @RequestMapping("/preUpordownForDeviceRackList")
    public String preUpordownForDeviceRackList(ModelMap map, String type) {
        map.addAttribute("type", type);
        return "idcrack/upordownRackList";
    }

    @RequestMapping("/upordownForDeviceRackList")
    @ResponseBody
    public EasyUIData upordownForDeviceRackList(EasyUIPageBean page, String name, String type) {
        EasyUIData easyUIData = new EasyUIData();
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("name", name);
        mapQ.put("type", type);
        List<Map<String, Object>> list = idcRackService.upordownForDeviceRackList(page, mapQ);
        easyUIData.setTotal(page.getTotalRecord());
        easyUIData.setRows(page.getItems());
        return easyUIData;
    }

    /*通过机房ID获取该机房所有模块*/
    @RequestMapping("/queryModuleByRoomId")
    @ResponseBody
    public List<Map<String, Object>> queryModuleByRoomId(Long roomId) {
        List<Map<String, Object>> list = idcRackService.queryModuleByRoomId(roomId);
        return list;
    }

    /*资源分配-准备跳纤*/
    @RequestMapping("/preDsitributionJumpFiber")
    public String preDsitributionBindConnecter(String rackIds,String roomIds,ModelMap map){
        map.put("rackIds",rackIds);
        map.put("roomIds",roomIds);
        return "/idcrack/dsitributionJumpFiber";
    }

    /*跳纤-机架列表*/
    @RequestMapping("/queryRackListByIds")
    @ResponseBody
    public List<IdcRack> queryRackListByIds(String rackIds,String roomId,String rackName){
        List<String> rackIdList = removeDuplicate(Arrays.asList(rackIds.split("_")));
        Map<String,Object> mapQ = new HashMap<>();
        mapQ.put("roomId",roomId);
        mapQ.put("rackIdList",rackIdList);
        mapQ.put("rackName",rackName);
        return idcRackService.queryRackListByIds(mapQ);
    }
}
