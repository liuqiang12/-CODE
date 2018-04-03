package com.idc.action.topo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mylove on 2017/4/21.
 */
@Controller
//@RequestMapping("/")
public class Web {
    @RequestMapping("/topo/")
    public String index() {
        return "topo/table";
    }

    @RequestMapping("/pdu/")
    public String pdu() {
        return "pdu/index";
    }

    /***
     * 端口界面
     * @return
     */
    @RequestMapping("/port/monitorInfo/")
    public String portMonitorInfo() {
        return "port/portMonitorInfo";
    }
}
