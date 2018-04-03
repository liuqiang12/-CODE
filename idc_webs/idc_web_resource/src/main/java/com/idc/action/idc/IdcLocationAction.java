package com.idc.action.idc;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcLocation;
import com.idc.model.IdcLocationCount;
import com.idc.service.IdcLocationService;
import com.idc.service.ResourceViewService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;
import java.util.*;


@Controller
@RequestMapping("/idcLocation")
public class IdcLocationAction extends BaseController {
    @Autowired
    private IdcLocationService idcLocationService;
    @Autowired
    private ResourceViewService resourceViewService;
    /**
     * 查询IDC数据中心数据
     *
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, IdcLocation idcLocation) {
        EasyUIData easyUIData = new EasyUIData();
        List<IdcLocation> list = new ArrayList<IdcLocation>();
        if (page == null || page.getPage() < 0) {//所有
            list = idcLocationService.queryList();
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = idcLocationService.queryListPage(page, idcLocation);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }
    /**
     * 查询IDC数据中心数据
     *
     * @return
     */
    @RequestMapping("/idcView")
    @ResponseBody
    public List<Map<String, Object>> idcView() {
        List<Map<String,Object>> result=new ArrayList<>();
        List<IdcLocation> idcLocations = idcLocationService.queryList();
        List<IdcLocationCount> idcLocationCounts = resourceViewService.queryList();
        ImmutableMap<String, IdcLocationCount> idcCountMap = Maps.uniqueIndex(idcLocationCounts, new Function<IdcLocationCount, String>() {
            @Override
            public String apply(IdcLocationCount idcLocation) {
                return idcLocation.getId().toString();
            }
        });
        for (IdcLocation idcLocation : idcLocations) {
            int localId = idcLocation.getId().intValue();
            IdcLocationCount idcLocationCount = idcCountMap.get(localId+"");
            if(idcLocationCount==null){
                idcLocationCount = new IdcLocationCount();
            }
            long rooms = resourceViewService.getRoomCount(localId);
            long builds =  resourceViewService.getBuildCount(localId);
            long odfs =  resourceViewService.getOdfCount(localId);
            long pdfs = resourceViewService.getPdfCount(localId);
            JSONObject jsonObject = JSONObject.fromObject(idcLocationCount);
            jsonObject.put("name",idcLocation.getName());
            jsonObject.put("buildtotal",builds);
            jsonObject.put("odftotal",odfs);
            jsonObject.put("pdftotal",pdfs);
            jsonObject.put("roomtotal",rooms);
            result.add(jsonObject);
        }
//
//        for (IdcLocationCount idcLocationCount : idcLocationCounts) {
//            Map<String,Object> map = new HashMap<>();
//            int localId = idcLocationCount.getId().intValue();
//            IdcLocation idcLocation = idcMap.get(localId+"");
//            if(idcLocation==null){
//                continue;
//            }
//            long rooms = resourceViewService.getRoomCount(localId);
//            long builds =  resourceViewService.getBuildCount(localId);
//            long odfs =  resourceViewService.getOdfCount(localId);
//            long pdfs = resourceViewService.getPdfCount(localId);
//            JSONObject jsonObject = JSONObject.fromObject(idcLocationCount);
//            jsonObject.put("name",idcLocation.getName());
//            jsonObject.put("buildtotal",builds);
//            jsonObject.put("odftotal",odfs);
//            jsonObject.put("pdftotal",pdfs);
//            jsonObject.put("roomtotal",rooms);
//            result.add(jsonObject);
//        }
        return result;
    }
    /**
     * 查询idc数据详情
     *
     * @param id 主键
     * @return
     */
    @RequestMapping("/getIdcLocationInfo.do")
    public String getIdcLocationInfo(Integer id, ModelMap map, String buttonType) {
        IdcLocation idcLocation = null;
        try{
            idcLocation = idcLocationService.getModelById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        map.addAttribute("id",id);
        map.addAttribute("buttonType", buttonType);
        map.addAttribute("idcLocation",idcLocation);
        return "location/info";
    }
    /**
     * 数据中心删除
     * @param list
     * @return
     */
    @RequestMapping("/del")
    @ResponseBody
    public ExecuteResult deleteResourceInfos(@RequestParam("list") String list) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            String[] arr = list.split(",");
            List<String> listIdc = java.util.Arrays.asList(arr);
            idcLocationService.deleteByList(listIdc);
            executeResult.setState(true);
            executeResult.setMsg("数据中心删除成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("数据中心删除失败");
        }
        return  executeResult;
    }


    /**
     * 添加修改数据中心
     * @return
     */
    @RequestMapping("/add.do")
    @ResponseBody
    public ExecuteResult addOrUpdateResourceInfo(IdcLocation idcLocation, Integer id) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            if (id != null && id != 0) {
                idcLocation.setId(id);
                idcLocationService.updateByObject(idcLocation);
                executeResult.setState(true);
                executeResult.setMsg("修改成功");
            } else {
                idcLocationService.insert(idcLocation);
                executeResult.setState(true);
                executeResult.setMsg("添加成功");
            }
        } catch (Exception e) {
            executeResult.setState(true);
            executeResult.setMsg("操作失败");
            e.printStackTrace();
        }
        return executeResult;
    }
}
