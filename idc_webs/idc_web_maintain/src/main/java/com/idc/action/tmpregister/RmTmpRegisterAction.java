package com.idc.action.tmpregister;

import com.idc.service.RmTmpRegisterService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;
import system.data.supper.action.BaseController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/3.
 */
@Controller
@RequestMapping("/rmTmpRegister")
public class RmTmpRegisterAction extends BaseController {

    @Autowired
    private RmTmpRegisterService rmTmpRegisterService;

    /**
     * 打开登录人员首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "/tmpregister/index";
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
            list = rmTmpRegisterService.queryListMap(mapQ);
            easyUIData.setTotal(list.size());
            easyUIData.setRows(list);
        } else {
            list = rmTmpRegisterService.queryListPageMap(page, mapQ);
            easyUIData.setTotal(page.getTotalRecord());
            easyUIData.setRows(page.getItems());
        }
        return easyUIData;
    }
}
