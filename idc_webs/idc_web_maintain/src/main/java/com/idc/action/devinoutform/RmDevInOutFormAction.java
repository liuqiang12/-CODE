package com.idc.action.devinoutform;

import com.idc.model.ExecuteResult;
import com.idc.model.RmDevInOutForm;
import com.idc.model.RmDevList;
import com.idc.service.RmDevInOutFormService;
import com.idc.service.RmDevListService;
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
@RequestMapping("/rmDevInOutForm")
public class RmDevInOutFormAction extends BaseController {

    @Autowired
    private RmDevInOutFormService rmDevInOutFormService;
    @Autowired
    private RmDevListService rmDevListService;

    /**
     * 打开设备出入申请单表
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "/devinoutform/index";
    }

    /**
     * 获取登录人员列表
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
            list = rmDevInOutFormService.queryListMap(mapQ);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = rmDevInOutFormService.queryListPageMap(page, mapQ);
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
    @RequestMapping("/getRmDevInOutFormInfo/{buttonType}/{id}")
    public String getRmDevInOutFormInfo(ModelMap map, @PathVariable String buttonType, @PathVariable String id) {
        RmDevInOutForm rmDevInOutForm = null;
        List<RmDevList> rmDevLists = null;
        if (id != null && !"".equals(id) && !"0".equals(id)) {//修改   否则为新增
            rmDevInOutForm = rmDevInOutFormService.getModelById(id);
            //获取具体的出入设备信息列表
            rmDevLists = rmDevListService.queryRmDevListListByRmDevInOutFormId(id);
            rmDevInOutForm.setRmDevLists(rmDevLists);
            rmDevInOutForm.setRmCreateTimeStr(rmDevInOutForm.getRmCreateTime());
        }
        map.addAttribute("rmDevInOutForm", rmDevInOutForm);
        map.addAttribute("buttonType", buttonType);
        map.addAttribute("id", id);
        map.addAttribute("rmDevListSize", rmDevLists == null ? 0 : rmDevLists.size());
        return "/devinoutform/info";
    }

    /**
     * 新增或修改设备出入申请单信息  id为0则新增  否则修改
     *
     * @param id
     * @param rmDevInOutForm
     * @return
     */
    @RequestMapping("/saveOrUpdateRmDevInOutFormInfo")
    @ResponseBody
    public ExecuteResult saveOrUpdateRmDevInOutFormInfo(String id, RmDevInOutForm rmDevInOutForm) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            rmDevInOutFormService.saveOrUpdateRmDevInOutFormInfo(id, rmDevInOutForm, getPrincipal().getUsername());
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
    @RequestMapping("/deleteRmDevInOutFormInfo")
    @ResponseBody
    public ExecuteResult deleteRmDevInOutFormInfo(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            rmDevInOutFormService.deleteRmDevInOutFormAndRmDevListByRmDevInOutFormIds(idList);
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
