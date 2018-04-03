package com.idc.action.idc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.idc.action.ImportExcelUtils;
import com.idc.bo.RoomPE;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcBuilding;
import com.idc.model.ZbMachineroom;
import com.idc.service.IdcBuildingService;
import com.idc.service.PowerEnvService;
import com.idc.service.ZbMachineroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/zbMachineroom")
public class ZbMachineroomAction extends BaseController {

    @Autowired
    private ZbMachineroomService zbMachineroomService;
    @Autowired
    private IdcBuildingService idcBuildingService;
    @Autowired
    private PowerEnvService powerEnvService;


    /**
     * 查询IDC机房
     *
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, ZbMachineroom zbMachineroom) {
        EasyUIData easyUIData = new EasyUIData();
        List<Map<String, Object>> list = new ArrayList<>();
        if (page == null || page.getPage() < 0) {//所有
            list = zbMachineroomService.queryAllZbMachineroomInfoList();
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            Map<Long, RoomPE> lastPEByRoomToMap = powerEnvService.getLastPEByRoomToMap(null);
            list = zbMachineroomService.queryZbMachineroomgInfoListPage(page, zbMachineroom);
            for (Map<String, Object> objectMap : list) {
                String id = objectMap.get("ID").toString();
                RoomPE roomPE = lastPEByRoomToMap.get(Long.parseLong(id));
                if(roomPE!=null){
                    objectMap.put("pue",roomPE.getElectric());
                }
            }
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    /**
     * 新增或修改机房信息
     *
     * @param zbMachineroom
     * @param id
     * @return
     */
    @RequestMapping("/addZbMachineroomInfo.do")
    @ResponseBody
    public ExecuteResult addOrUpdateResourceInfo(ZbMachineroom zbMachineroom, Integer id) {
        ExecuteResult executeResult = new ExecuteResult();
        if (id != null && id != 0) {
            try {//修改
                zbMachineroom.setId(id);
                zbMachineroom.setModifytime(new Timestamp(new Date().getTime()));
                zbMachineroomService.updateByObject(zbMachineroom);
                executeResult.setState(true);
                executeResult.setMsg("机房信息修改成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("机房信息修改失败");
                e.printStackTrace();
            }
        } else {//新增
            try {
                zbMachineroomService.insert(zbMachineroom);
                executeResult.setState(true);
                executeResult.setMsg("机房信息添加成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("机房信息添加失败");
                e.printStackTrace();
            }
        }
        return executeResult;
    }

    /**
     * 查询机房详情
     *
     * @param id 主键
     * @return
     */
    @RequestMapping("/getZbMachineroomInfo.do")
    public String getZbMachineroomInfo(Integer id, ModelMap map, String buttonType) {
        ZbMachineroom zbMachineroom = null;
        try {
            zbMachineroom = zbMachineroomService.getModelById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.addAttribute("id", id);
        map.addAttribute("buttonType", buttonType);
        map.addAttribute("zbMachineroom", zbMachineroom);
        return "machineroom/info";
    }


    /**
     * 获取所有可用的机楼
     *
     * @return
     */
    @RequestMapping("/ajaxBuilding")
    @ResponseBody
    public List<IdcBuilding> ajaxBuilding(String idcLocationIds) {
        List<String> locationIdList = null;
        List<IdcBuilding> idcB = null;
        if (idcLocationIds != null && !"".equals(idcLocationIds)) {
            locationIdList = Arrays.asList(idcLocationIds.split(","));
        }
        idcB = idcBuildingService.queryListByLocationIdList(locationIdList);
        return idcB;
    }

    //通过机房IDs获取各个机房的机架数
    @RequestMapping("/queryRackNumByRoomId")
    @ResponseBody
    public ExecuteResult queryRackNumByRoomId(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        int rackNum = 0;
        String roomNameStr = "";
        if (ids != null && !"".equals(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            List<Map<String, Object>> list = zbMachineroomService.getRackNumByIds(idList);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    rackNum = list.get(i).get("RACKCOUNT") == null ? 0 : Integer.parseInt(list.get(i).get("RACKCOUNT").toString());
                    if (rackNum > 0) {
                        roomNameStr += list.get(i).get("ROOMNAME") == null ? "" : list.get(i).get("ROOMNAME") + ",";
                    }
                }
            }
        }
        executeResult.setMsg(roomNameStr.substring(0, roomNameStr.length() - 1));
        return executeResult;
    }

    /**
     * 机房删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public ExecuteResult deleteResourceInfos(@RequestParam("ids") String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            String[] arr = ids.split(",");
            List<String> listIdc = java.util.Arrays.asList(arr);
            //zbMachineroomService.deleteByList(listIdc);
            zbMachineroomService.updateRoomToInvalidByIds(listIdc);
            executeResult.setState(true);
            executeResult.setMsg("机房删除成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("机房删除失败");
        }
        return executeResult;
    }

    /**
     * 导出机房数据
     *
     * @return
     */
    @RequestMapping("/exportRoomData")
    @ResponseBody
    public ExecuteResult exportResourceData(HttpServletResponse response,String searchStr) {
        ExecuteResult executeResult = new ExecuteResult();
        String title = "机房信息";
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("NAME", "归属机楼");
        headMap.put("SITENAMESN", "机房编码");
        headMap.put("SITENAME", "机房名称");
        headMap.put("ROOMCATEGORY", "机房类别");
        headMap.put("GRADE", "机房等级");
        headMap.put("USEFOR", "业务类型");
        headMap.put("REMARK", "备注");
        headMap.put("AREA", "机房面积");
        headMap.put("WIDTH_M", "宽度（米）");
        headMap.put("HEIGHT_M", "长度（米）");
        headMap.put("FLOORHEIGHT", "楼层高度（米）");
        headMap.put("DESIGNELECTRICITY", "总设计电量(KVA)");
        headMap.put("TOTALBANDWIDTH", "机房出入口带宽");

        Map<String,Object> mapQ = new HashMap<>();
        if(searchStr!=null&&!"".equals(searchStr)){
            String[] strArr = searchStr.split("_");
            mapQ.put("searchType",strArr[0]);
            mapQ.put("searchId",strArr[1]);
        }
        OutputStream outXlsx = null;
        List<Map<String, Object>> list = zbMachineroomService.getAllZbMachineroomInfo(mapQ);
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;filename=room.xlsx");
            //response.setCharacterEncoding("utf-8");
            outXlsx = response.getOutputStream();//new FileOutputStream("E://机房信息.xlsx");
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

    /**
     * 导入
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/importRoomData")
    @ResponseBody
    public ExecuteResult importResourceData(HttpServletRequest request, HttpServletResponse response) {
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
                zbMachineroomService.importZbMachineroomByExcel(list);
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

    /*跳纤-机房列表*/
    @RequestMapping("/queryZbMachineroomListByIds")
    @ResponseBody
    public List<ZbMachineroom> queryZbMachineroomListByIds(String roomIds,String roomName){
        List<String> roomIdList = Arrays.asList(roomIds.split("_"));
        roomIdList = removeDuplicate(roomIdList);
        Map<String,Object> mapQ = new HashMap<>();
        mapQ.put("roomIdList",roomIdList);
        mapQ.put("roomName",roomName);
        return zbMachineroomService.queryZbMachineroomListByIds(mapQ);
    }
}
