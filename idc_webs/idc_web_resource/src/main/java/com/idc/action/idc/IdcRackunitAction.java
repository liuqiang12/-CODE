package com.idc.action.idc;

import com.idc.model.IdcRackunit;
import com.idc.service.IdcRackService;
import com.idc.service.IdcRackunitService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
 * Created by Administrator on 2017/8/2.
 */
@Controller
@RequestMapping("/idcRackunit")
public class IdcRackunitAction extends BaseController {
    @Autowired
    private IdcRackunitService idcRackunitService;

    @RequestMapping("/preQueryRackunitList")
    public String preQueryRackunitList(String rackId, ModelMap map) {
        map.addAttribute("rackId", rackId);
        return "idcrackunit/rackunitList";
    }

    @RequestMapping("/queryRackunitList")
    @ResponseBody
    public List<Map<String, Object>> queryRackunitList(String code, Long rackId) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (rackId != null) {
            IdcRackunit idcRackunit = new IdcRackunit();
            idcRackunit.setCode(code);
            idcRackunit.setRackid(rackId);
//            idcRackunit.setStatus(20);
            list = idcRackunitService.queryIdcRackunitInfoList(idcRackunit);
        }
        return list;
    }
}
