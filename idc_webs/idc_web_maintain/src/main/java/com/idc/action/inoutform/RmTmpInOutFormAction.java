package com.idc.action.inoutform;

import com.idc.model.ExecuteResult;
import com.idc.model.RmTmpInOutForm;
import com.idc.model.RmTmpRegister;
import com.idc.service.RmTmpInOutFormService;
import com.idc.service.RmTmpRegisterService;
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
 * Created by Administrator on 2017/11/1.
 */
@Controller
@RequestMapping("/rmTmpInOutForm")
public class RmTmpInOutFormAction extends BaseController {

    @Autowired
    private RmTmpInOutFormService rmTmpInOutFormService;
    @Autowired
    private RmTmpRegisterService rmTmpRegisterService;

    /**
     * 打开申请单首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "/inoutform/index";
    }

    /**
     * 获取申请单列表
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
            list = rmTmpInOutFormService.queryListMap(mapQ);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = rmTmpInOutFormService.queryListPageMap(page, mapQ);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }

    /**
     * 打开新增或修改申请单页面
     *
     * @param map
     * @param buttonType
     * @param id
     * @return
     */
    @RequestMapping("/getRmInOutFormInfo/{buttonType}/{id}")
    public String getRmInOutFormInfo(ModelMap map, @PathVariable String buttonType, @PathVariable String id) {
        RmTmpInOutForm rmTmpInOutForm = null;
        if (id != null && !"".equals(id) && !"0".equals(id)) {//修改   否则为新增
            rmTmpInOutForm = rmTmpInOutFormService.getModelById(id);
            //获取进出的人员列表
            List<RmTmpRegister> rmTmpRegisterList = rmTmpRegisterService.queryRmTmpRegisterListByRmTmpInOutFormId(id);
            rmTmpInOutForm.setRmTmpRegisters(rmTmpRegisterList);
            rmTmpInOutForm.setRmStartTimeStr(rmTmpInOutForm.getRmStartTime());
            rmTmpInOutForm.setRmEndTimeStr(rmTmpInOutForm.getRmEndTime());
            rmTmpInOutForm.setRmCreateTimeStr(rmTmpInOutForm.getRmCreateTime());
        }
        map.addAttribute("rmTmpInOutForm", rmTmpInOutForm);
        map.addAttribute("buttonType", buttonType);
        map.addAttribute("id", id);
        return "/inoutform/info";
    }

    /**
     * 新增或修改申请单信息  id为空则新增  否则修改
     *
     * @param id
     * @param rmTmpInOutForm
     * @return
     */
    @RequestMapping("/saveOrUpdateRmInOutFormInfo")
    @ResponseBody
    public ExecuteResult saveOrUpdateRmInOutFormInfo(String id, RmTmpInOutForm rmTmpInOutForm) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            rmTmpInOutFormService.saveOrUpdateRmInOutFormInfo(id, rmTmpInOutForm, getPrincipal().getUsername());
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
    @RequestMapping("/deleteRmInOutFormInfo")
    @ResponseBody
    public ExecuteResult deleteRmInOutFormInfo(String ids) {
        ExecuteResult executeResult = new ExecuteResult();
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            rmTmpInOutFormService.deleteRmTmpInOutFormAndRmTmpRegisterByRmTmpInOutFormIds(idList);
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
