package com.idc.action.process;

import com.idc.model.IdcMcb;
import com.idc.model.IdcRack;
import com.idc.service.IdcDeviceService;
import com.idc.service.IdcMcbService;
import com.idc.service.IdcRackService;
import com.idc.service.IdcRackunitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import system.data.supper.action.BaseController;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/22.
 */
@Controller
@RequestMapping("/process")
public class ProcessManagementAction extends BaseController {
    @Autowired
    IdcRackService idcRackService;
    @Autowired
    IdcDeviceService idcDeviceService;
    @Autowired
    IdcMcbService idcMcbService;

    /**
     * 加载资源信息
     * @param map
     * @param ticketId 工单编号
     * @param resourceId 资源编号
     * @param resourceType 资源类型
     * @param businessType 业务类型
     * @return
     */
    @RequestMapping("/resourceConfig.do")
    public String processManagement(ModelMap map, Long ticketId,Long resourceId,String resourceType,String businessType){
        //List<Map<String,Object>> idcRackList = idcRackService.queryAllIdcRackInfoList();
        map.addAttribute("ticketId",ticketId);
        map.addAttribute("resourceId",resourceId);
        map.addAttribute("resourceType",resourceType);
        map.addAttribute("businessType",businessType);
        //map.addAttribute("idcRackList",idcRackList);
        return "/process/resourceConfig";
    }
}
