package com.bpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DELL on 2017/6/6.
 * 暂停工单
 */
@Controller
@RequestMapping("/pauseTicketController")
public class PauseTicketController {
    private Logger logger = LoggerFactory.getLogger(PauseTicketController.class);

}
