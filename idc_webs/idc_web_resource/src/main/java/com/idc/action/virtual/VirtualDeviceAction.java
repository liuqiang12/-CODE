package com.idc.action.virtual;

import com.idc.model.IdcVmHosts;
import com.idc.service.IdcVmHostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import system.data.page.EasyUIData;
import system.data.page.EasyUIPageBean;

/**
 * Created by mylove on 2017/10/18.
 */
@Controller
public class VirtualDeviceAction {
    private static final Logger logger = LoggerFactory.getLogger(VirtualDeviceAction.class);
    @Autowired
    private IdcVmHostsService idcVmHostsService;

    @RequestMapping("/virtualdevice/")
    public String index() {
        return "virtualdevice/index";
    }

    @RequestMapping("/virtualdevice/list")
    @ResponseBody
    public EasyUIData List(EasyUIPageBean pageBean, IdcVmHosts idcVmHosts) {
        idcVmHostsService.queryListVoPage(pageBean, idcVmHosts);
        EasyUIData easyUIData = new EasyUIData();
        easyUIData.setTotal(pageBean.getTotalRecord());
        easyUIData.setRows(pageBean.getItems());
        return easyUIData;
    }
}
