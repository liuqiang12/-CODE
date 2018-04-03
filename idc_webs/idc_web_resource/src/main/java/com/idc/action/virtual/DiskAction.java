package com.idc.action.virtual;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 磁盘阵列
 * Created by mylove on 2017/12/12.
 */
@Controller
public class DiskAction {
    private static final Logger logger = LoggerFactory.getLogger(DiskAction.class);
    @RequestMapping("/disk")
    public String Index() {
        return "disk/index";
    }
}
