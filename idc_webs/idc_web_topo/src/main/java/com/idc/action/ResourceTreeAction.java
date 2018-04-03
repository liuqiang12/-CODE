package com.idc.action;

import com.idc.service.ResourceTopoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2017/5/31.
 */
@Controller
public class ResourceTreeAction {

    @Autowired
    private ResourceTopoService resourceTopoService;
    private static final Logger logger = LoggerFactory.getLogger(ResourceTreeAction.class);

    /***
     * @param type     开始节点类型
     * @param Start    节点id
     * @param modelMap
     * @return
     */
    @RequestMapping("/resourcetree")
    public String getTree(String type, int startid, ModelMap modelMap) {
        List<Map<String, Object>> tree = resourceTopoService.getTree(type, startid);
        modelMap.put("rackunits",tree);
        return "resource/tree";
    }
}
