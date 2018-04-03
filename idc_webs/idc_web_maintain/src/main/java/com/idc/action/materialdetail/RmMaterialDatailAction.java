package com.idc.action.materialdetail;

import com.idc.model.ExecuteResult;
import com.idc.model.RmMaterialDetail;
import com.idc.service.RmMaterialDetailService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/3.
 */
@Controller
@RequestMapping("/rmMaterialDatail")
public class RmMaterialDatailAction extends BaseController {

    @Autowired
    private RmMaterialDetailService rmMaterialDetailService;

    /**
     * 打开物资出入申请单表
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "/materialdetail/index";
    }

    /**
     * 获取物资出入单列表
     *
     * @param page
     * @param name
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData list(EasyUIPageBean page, String name) {
        EasyUIData easyUIData = new EasyUIData();
        Map<String, Object> mapQ = new HashedMap();
        mapQ.put("name", name);
        List<Map<String, Object>> list = new ArrayList<>();
        if (page == null || page.getPage() < 0) {
            list = rmMaterialDetailService.queryListMap(mapQ);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = rmMaterialDetailService.queryListPageMap(page, mapQ);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    /**
     * 打开新增或修改设备出入申请单页面
     *
     * @param map
     * @param buttonType
     * @param id
     * @return
     */
    @RequestMapping("/getRmMaterialDatailInfo/{buttonType}/{id}")
    public String getRmMaterialDatailInfo(ModelMap map, @PathVariable String buttonType, @PathVariable String id) {
        RmMaterialDetail rmMaterialDetail = null;
        if (id != null && !"".equals(id) && !"0".equals(id)) {//修改   否则为新增
            rmMaterialDetail = rmMaterialDetailService.getModelById(id);
            rmMaterialDetail.setRmMaterialInOutTimeStr(rmMaterialDetail.getRmMaterialInOutTime());
            rmMaterialDetail.setRmCreateTimeStr(rmMaterialDetail.getRmCreateTime());
        }
        map.addAttribute("rmMaterialDetail", rmMaterialDetail);
        map.addAttribute("buttonType", buttonType);
        map.addAttribute("id", id);
        return "/materialdetail/info";
    }

    /**
     * 新增或修改设备出入申请单信息  id为0则新增  否则修改
     *
     * @param id
     * @param rmMaterialDetail
     * @return
     */
    @RequestMapping("/saveOrUpdateRmMaterialDatailInfo")
    @ResponseBody
    public ExecuteResult saveOrUpdateRmMaterialDatailInfo(String id, RmMaterialDetail rmMaterialDetail) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            rmMaterialDetailService.saveOrUpdateRmMaterialDatailInfo(id, rmMaterialDetail, getPrincipal().getUsername());
            executeResult.setState(true);
            executeResult.setMsg("保存申请单信息成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("保存申请单信息失败");
            e.printStackTrace();
        }
        return executeResult;
    }

    /**
     * 删除申请单信息
     *
     * @param ids
     * @return
     */
    @RequestMapping("/deleteRmMaterialDatailInfo")
    @ResponseBody
    public ExecuteResult deleteRmMaterialDatailInfo(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            rmMaterialDetailService.deleteByList(idList);
            executeResult.setState(true);
            executeResult.setMsg("删除申请单成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("删除申请单失败");
            e.printStackTrace();
        }
        return executeResult;
    }
}
