package com.idc.action.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import system.data.supper.action.BaseController;


/**
 * 点击登录后跳转的界面
 */
@Controller
@RequestMapping("/main")
public class MainAction extends BaseController {
    //跳转主界面
    @RequestMapping("index.do")
    public String index() {
        return "main/main";
    }

    //跳转界面top.html
    @RequestMapping("top.do")
    public String top() {
        return "main/top";
    }

    @RequestMapping("center.do")
    public String center() {
        return "main/center";
    }

    @RequestMapping("down.do")
    public String down() {
        return "main/down";
    }

    @RequestMapping("middel.do")
    public String middel() {
        return "main/middel";
    }

    @RequestMapping("left.do")
    public String left() {
        return "main/left";
    }

    @RequestMapping("right.do")
    public String right() {
        return "main/right";
    }

    /**
     * 创建流程
     *
     * @return
     */
    @RequestMapping("proccess_create.do")
    public String proccess_create() {
        return "activiti/drawProcessList";
    }

    @RequestMapping("resouces_applay.do")
    public String resouces_applay() {
        return "activiti/resouces_applay";
    }

    @RequestMapping("publishLayout.do")
    public String publishLayout() {
        return "bussness/publishLayout";
    }
//	@RequestMapping("proccess_create.do")
//	public String proccess_create() {
//		return "main/proccess_create";
//	}

    /**
     * 发布流程
     *
     * @return
     */
    @RequestMapping("proccess_publish.do")
    public String proccess_publish() {
        return "activiti/process/queryProcesslist";
    }

    /**
     * 发布流程
     *
     * @return
     */
    @RequestMapping("proccesPublish.do")
    public String proccesPublish() {
        return "activiti/process/processList";
    }

    /**
     * 代办流程
     *
     * @return
     */
    @RequestMapping("proccess_agent.do")
    public String proccess_agent() {
        return "bussness/dbLayout";
    }

    /**
     * 流程历史
     *
     * @return
     */
    @RequestMapping("proccess_his.do")
    public String proccess_his() {
        return "main/proccess_his";
    }

    /**
     * 流程归档
     *
     * @return
     */
    @RequestMapping("proccess_end.do")
    public String proccess_end() {
        return "main/proccess_end";
    }

    @RequestMapping("test1.do")
    public String test1() {
        return "main/test1";
    }

    @RequestMapping("test2.do")
    public String test2() {
        return "main/test2";
    }
}
