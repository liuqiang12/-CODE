package com.idc.action.materialtype;

import com.idc.model.ExecuteResult;
import com.idc.model.RmMaterialType;
import com.idc.service.RmMaterialTypeService;
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
@RequestMapping("/rmMaterialType")
public class RmMaterialTypeAction extends BaseController {


    @Autowired
    private RmMaterialTypeService rmMaterialTypeService;

    /**
     * 打开物资类别首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "/materialtype/index";
    }

    /**
     * 获取物资类别列表
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
            list = rmMaterialTypeService.queryListMap(mapQ);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = rmMaterialTypeService.queryListPageMap(page, mapQ);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    /**
     * 打开新增或修改物资类别页面
     *
     * @param map
     * @param buttonType
     * @param id
     * @return
     */
    @RequestMapping("/getRmMaterialTypeInfo/{buttonType}/{id}")
    public String getRmMaterialTypeInfo(ModelMap map, @PathVariable String buttonType, @PathVariable String id) {
        RmMaterialType rmMaterialType = null;
        if (id != null && !"".equals(id) && !"0".equals(id)) {//修改   否则为新增
            rmMaterialType = rmMaterialTypeService.getModelById(id);
        }
        map.addAttribute("rmMaterialType", rmMaterialType);
        map.addAttribute("buttonType", buttonType);
        map.addAttribute("id", id);
        return "/materialtype/info";
    }

    /**
     * 新增或修改物资类别信息  id为0则新增  否则修改
     *
     * @param id
     * @param rmMaterialType
     * @return
     */
    @RequestMapping("/saveOrUpdateRmMaterialTypeInfo")
    @ResponseBody
    public ExecuteResult saveOrUpdateRmMaterialDatailInfo(String id, RmMaterialType rmMaterialType) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            rmMaterialTypeService.saveOrUpdateRmMaterialTypeInfo(id, rmMaterialType);
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
     * 删除物资类别信息
     *
     * @param ids
     * @return
     */
    @RequestMapping("/deleteRmMaterialTypeInfo")
    @ResponseBody
    public ExecuteResult deleteRmMaterialTypeInfo(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            rmMaterialTypeService.deleteByList(idList);
            executeResult.setState(true);
            executeResult.setMsg("删除申请单成功");
        } catch (Exception e) {
            executeResult.setState(false);
            executeResult.setMsg("删除申请单失败");
            e.printStackTrace();
        }
        return executeResult;
    }

    /**
     * 获取所有物资类型 id   name
     *
     * @return
     */
    @RequestMapping("/queryAllMaterialTypeModel")
    @ResponseBody
    public List<Map<String, Object>> queryAllMaterialTypeModel() {
        List<Map<String, Object>> list = rmMaterialTypeService.queryAllMaterialTypeModel();
        return list;
    }
}
