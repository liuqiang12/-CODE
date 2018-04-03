package com.idc.action.idc;

import com.idc.bo.RoomPE;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcBuilding;
import com.idc.model.IdcLocation;
import com.idc.service.IdcBuildingService;
import com.idc.service.IdcLocationService;
import com.idc.service.PowerEnvService;
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
@RequestMapping("/idcBuilding")
public class IdcBuildingAction extends BaseController {
	@Autowired
	private IdcBuildingService idcBuildingService;
	@Autowired
	private IdcLocationService idcLocationService;
    @Autowired
    private PowerEnvService powerEnvService;
	
	/**
	 * 查询IDC机楼
	 * @return
	 */
    @RequestMapping("/list.do")
    @ResponseBody
	public EasyUIData list(EasyUIPageBean page,String name) {
	    EasyUIData easyUIData = new EasyUIData();
        IdcBuilding idcBuilding = new IdcBuilding();
        idcBuilding.setName(name);
        List<Map<String,Object>> list = new ArrayList<>();
        Map<Long, RoomPE> lastPEByBuildToMap = powerEnvService.getLastPEByBuildToMap(null);
        if(page==null||page.getPage()<0){//所有
			list= idcBuildingService.queryAllBuildingInfoList();
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        }else{
        	list = idcBuildingService.queryBuildingInfoListPage(page, idcBuilding);
            for (Map<String, Object> objectMap : list) {
                String id = objectMap.get("ID").toString();
                RoomPE roomPE = lastPEByBuildToMap.get(Long.parseLong(id));
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
     * 查询idc数据中心详情
     * @param id 主键
	 * @return
	 */
    @RequestMapping("/getIdcBuildingInfo.do")
    public String getIdcBuildingInfo(Integer id, ModelMap map, String buttonType) {
        IdcBuilding idcBuilding = null;
        try {
            idcBuilding = idcBuildingService.getModelById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.addAttribute("id", id);
        map.addAttribute("buttonType", buttonType);
        map.addAttribute("idcBuilding", idcBuilding);
        return "building/info";
    }

    /**
     * 新增或修改机楼信息
     * @param idcBuilding
     * @param id
     * @return
	 */
	@RequestMapping("/addBuildingInfo")
	@ResponseBody
    public ExecuteResult addOrUpdateResourceInfo(IdcBuilding idcBuilding, Integer id) {
        ExecuteResult executeResult = new ExecuteResult();
        if (id != null && id != 0) {//修改
            idcBuilding.setId(id);
            try {
                idcBuildingService.updateByObject(idcBuilding);
                executeResult.setState(true);
                executeResult.setMsg("修改机楼信息成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("修改机楼信息失败");
                e.printStackTrace();
            }
        } else {//新增   若新增机架原来存在   结果被删除了   这直接修改原机架
            try {
                Map<String,Object> mapQ = new HashMap<>();
                mapQ.put("name",idcBuilding.getName());
                IdcBuilding oldBuilding = idcBuildingService.getBuildingModelByMap(mapQ);
                if(oldBuilding!=null&&oldBuilding.getId()!=null){
                    idcBuilding.setId(oldBuilding.getId());
                    idcBuilding.setIsdelete(0);
                    idcBuildingService.updateByObject(idcBuilding);
                }else{
                    idcBuildingService.insert(idcBuilding);
                }
                executeResult.setState(true);
                executeResult.setMsg("新增机楼信息成功");
            } catch (Exception e) {
                executeResult.setState(false);
                executeResult.setMsg("新增机楼信息失败");
                e.printStackTrace();
            }
        }
		return executeResult;
	}
	
	/**
	 * 获取所有可用的数据中心
	 * @return
	 */
	@RequestMapping("/ajaxLocation")
	@ResponseBody
	public List<IdcLocation> ajaxLocation(){
		List<IdcLocation> idcL = idcLocationService.queryList();
		return idcL;
	}
	/**
     * 机楼删除
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
            //idcBuildingService.deleteByList(listIdc);
            idcBuildingService.updateBuildingToInvalidByIds(listIdc);
            executeResult.setState(true);
            executeResult.setMsg("机楼删除成功");
        } catch (Exception e) {
			executeResult.setState(false);
			executeResult.setMsg("机架删除失败");
		}
		return  executeResult;
	}

    //通过机楼IDS获取各个机楼机房数
    @RequestMapping("/queryRoomNumByBuildingId")
    @ResponseBody
    public ExecuteResult queryRoomNumByBuildingId(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        int roomNum = 0;
        String buildNameStr = "";
        if (ids != null && !"".equals(ids)) {
            List<String> idList = Arrays.asList(ids.split(","));
            List<Map<String, Object>> list = idcBuildingService.getRoomNumByIds(idList);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    roomNum = list.get(i).get("ROOMCOUNT") == null ? 0 : Integer.parseInt(list.get(i).get("ROOMCOUNT").toString());
                    if (roomNum > 0) {
                        buildNameStr += list.get(i).get("BUILDNAME") == null ? "" : list.get(i).get("BUILDNAME") + ",";
                    }
                }
            }
        }
        executeResult.setMsg(buildNameStr.substring(0, buildNameStr.length() - 1));
        return executeResult;
    }
}
