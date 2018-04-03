package com.idc.action.alarm;

import com.idc.model.NetKpibase;
import com.idc.service.NetKpibaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * KPi指标库
 * Created by mylove on 2017/8/3.
 */
@Controller
@RequestMapping("/netkpibase")
public class NetKpiAction {
    private NetKpibaseService netKpibaseService;

    @RequestMapping("/")
    public String index() {
        return "netkpibase/index";
    }

    @RequestMapping("/kpis")
    @ResponseBody
    public List<NetKpibase> getKpis() {
        List<NetKpibase> netKpibases = netKpibaseService.queryList();
        return netKpibases;
    }
}
