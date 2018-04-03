package com.bpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DELL on 2017/6/6.
 * 下线工单
 */
@Controller
@RequestMapping("/haltTicketController")
public class HaltTicketController {
    private Logger logger = LoggerFactory.getLogger(HaltTicketController.class);

}
