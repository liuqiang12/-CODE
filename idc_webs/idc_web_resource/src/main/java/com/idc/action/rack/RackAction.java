package com.idc.action.rack;

import com.idc.model.IdcRack;
import com.idc.service.IdcRackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/5/8.
 */

/***
 * 数据中心
 */
@Controller
@RequestMapping("/rack")
public class RackAction {

    @Autowired
    private IdcRackService idcRackService;

    @RequestMapping("/list")
    @ResponseBody
    public EasyUIData List(EasyUIPageBean pageBean, String key) {
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("keywork", key);
        IdcRack d=new IdcRack();

        List<IdcRack> idcRacks = idcRackService.queryListPage(pageBean, queryMap);
        EasyUIData easyUIData = new EasyUIData();
        easyUIData.setRows(idcRacks);
        easyUIData.setTotal(pageBean.getTotalRecord());
        return easyUIData;
    }
}
